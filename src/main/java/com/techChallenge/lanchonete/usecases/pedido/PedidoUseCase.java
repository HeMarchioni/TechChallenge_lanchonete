package com.techChallenge.lanchonete.usecases.pedido;

import com.google.gson.Gson;
import com.techChallenge.lanchonete.usecases.cliente.ClienteUseCase;
import com.techChallenge.lanchonete.usecases.pagamento.PagamentoUseCase;
import com.techChallenge.lanchonete.usecases.pagamento.ConfirmacaoPagamentoDTO;
import com.techChallenge.lanchonete.usecases.produto.ProdutoDTO;
import com.techChallenge.lanchonete.usecases.cliente.ClienteMapper;
import com.techChallenge.lanchonete.usecases.produto.ProdutoMapper;
import com.techChallenge.lanchonete.repositories.PedidoRepositoryPort;
import com.techChallenge.lanchonete.usecases.cliente.Cliente;
import com.techChallenge.lanchonete.usecases.produto.Produto;
import com.techChallenge.lanchonete.usecases.produto.ProdutoUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoUseCase implements PedidoUseCasePort {


    private final PedidoRepositoryPort pedidoRepositoryPort;
    private final PedidoMapper pedidoMapper;
    private final ProdutoMapper produtoMapper;
    private final ClienteMapper clienteMapper;
    private final ProdutoUseCase produtoService;
    private final PagamentoUseCase pagamentoService;
    private final ClienteUseCase clienteService;
    private final int numeroMaximoPedidosPreparacao;


    public PedidoUseCase(PedidoRepositoryPort pedidoRepositoryPort, ProdutoUseCase produtoService, PagamentoUseCase pagamentoService, ClienteUseCase clienteService, @Value("${numero.pedidos.preparacao}") int numeroMaximoPedidosPreparacao) {
        this.pedidoRepositoryPort = pedidoRepositoryPort;
        this.pedidoMapper = PedidoMapper.INSTANCE;
        this.produtoMapper = ProdutoMapper.INSTANCE;
        this.clienteMapper = ClienteMapper.INSTANCE;
        this.produtoService = produtoService;
        this.pagamentoService = pagamentoService;
        this.clienteService = clienteService;
        this.numeroMaximoPedidosPreparacao = numeroMaximoPedidosPreparacao;
    }

    @Override
    public PedidoOutDTO create(PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoMapper.toModel(pedidoDTO);
        pedido.setDataPedido(getHoraDataAtual());
        pedido.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);

        preencheCliente(pedido);
        calcularValorTotalEPreencheProduto(pedido);

        try {
            pedido = pedidoMapper.toModel(pedidoRepositoryPort.save(pedido));

            pagamentoService.gerarQrCodeMercadoPago(pedido);


        } catch (DataAccessException e) {
            throw new DataIntegrityViolationException("Não foi possível salvar o pedido no banco de dados", e);
        }
        catch (IOException e) {
            throw new RuntimeException("Ocorreu um erro ao gerar o QR Code para o pedido", e);
        }

        return pedidoMapper.toOutDTO(pedido) ;
    }

    @Override
    public PedidoOutDTO findById(Long id) {
        Pedido pedido = pedidoRepositoryPort.findById(id);
        return pedidoMapper.toOutDTO(pedido);
    }

    @Override
    public List<PedidoOutDTO> findAll() {
        List<Pedido> pedidos = pedidoRepositoryPort.findAll();
        return pedidos.stream().map(pedidoMapper::toOutDTO).collect(Collectors.toList());
    }

    @Override
    public boolean update(PedidoDTO pedidoDTO) {
        Pedido pedidoDoBanco = pedidoRepositoryPort.findById(pedidoDTO.getId());
        if (pedidoDoBanco != null) {

            if (pedidoDoBanco.getStatusPedido() != StatusPedido.AGUARDANDO_PAGAMENTO){
                throw new IllegalStateException("Não se pode alterar um Pedido depois que foi pago pelo auto Atendimento");
            }

            Pedido pedido = pedidoMapper.toModel(pedidoDTO);
            pedido.setDataPedido(pedidoDoBanco.getDataPedido());
            pedido.setStatusPedido(pedidoDoBanco.getStatusPedido());
            pedidoRepositoryPort.save(pedido);
            return true;
        }
        return false;
    }


    public boolean updateInterno(Pedido pedido) {
        if (pedido != null) {
            pedidoRepositoryPort.save(pedido);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {

        Pedido pedidoDoBanco = pedidoRepositoryPort.findById(id);

        if (pedidoDoBanco != null) {

            if(!StatusPedido.AGUARDANDO_PAGAMENTO.equals(pedidoDoBanco.getStatusPedido()) && !StatusPedido.FINALIZADO.equals(pedidoDoBanco.getStatusPedido()))
                throw new IllegalStateException("Pedido somente pode ser excluido se Ainda estiver AGUARDANDO_PAGAMENTO ou Foi FINALIZADO");

            pedidoRepositoryPort.deleteById(id);
            return true;
        }
        return false;
    }


    @Override
    public List<PedidoOutDTO> getListaPrioridade() {

        List<Pedido> todosOsPedidos = new ArrayList<>();
        todosOsPedidos.addAll(pedidoRepositoryPort.findPedidoByStatus(StatusPedido.PRONTO));
        todosOsPedidos.addAll(pedidoRepositoryPort.findPedidoByStatus(StatusPedido.PREPARACAO));
        todosOsPedidos.addAll(pedidoRepositoryPort.findPedidoByStatus(StatusPedido.RECEBIDO));

        return pedidoMapper.toOutDTOList(todosOsPedidos);
    }

    @Override
    public void pagamentoPedidoConfirmado(ConfirmacaoPagamentoDTO confirmacaoPagamentoDTO) {

        Pedido pedido = pedidoRepositoryPort.findById(Long.valueOf(confirmacaoPagamentoDTO.getExternalReference()));

        pedido.setStatusPedido(StatusPedido.RECEBIDO);
        pedido.setInformacoesPagamento(new Gson().toJson(confirmacaoPagamentoDTO));

        try {
            if (!this.updateInterno(pedido))
                throw new RuntimeException("Erro: Confirmacao de pagamento nao foi possivel alterar status para recebido informações de Pagamento:"+ pedido.getInformacoesPagamento());

        }catch (Exception e){
            e.printStackTrace();
            // Possivel ‘LOG’ ou sistema de monitoramento. Webhooks para informar a tela pagamento aprovado.
        }
        this.rotinaVerificacaoStatusPreparacao();
    }

    @Override
    public List<PedidoOutDTO> buscarPorStatus(StatusPedido statusPedido) {
        return pedidoMapper.toOutDTOList(pedidoRepositoryPort.findPedidoByStatus(statusPedido));
    }


    public boolean alteraStatusPedidoPronto (Long id){

        Pedido pedidoDoBanco = pedidoRepositoryPort.findById(id);
        if (pedidoDoBanco != null) {

            if ( StatusPedido.AGUARDANDO_PAGAMENTO.equals(pedidoDoBanco.getStatusPedido()) || StatusPedido.FINALIZADO.equals(pedidoDoBanco.getStatusPedido())    ){
                throw new IllegalStateException("Status do Pedido não pode ser alterado para PRONTO se o mesmo Ainda estiver AGUARDANDO_PAGAMENTO ou ja foi FINALIZADO");
            }

            pedidoDoBanco.setStatusPedido(StatusPedido.PRONTO);
            pedidoRepositoryPort.save(pedidoDoBanco);
            rotinaVerificacaoStatusPreparacao();
            // Possivel ‘LOG’ ou sistema de monitoramento. Webhooks para informar a tela pedido Pronto.
            return true;
        }
        return false;
    }

    public boolean alteraStatusPedidoFinalizado (Long id){

        Pedido pedidoDoBanco = pedidoRepositoryPort.findById(id);
        if (pedidoDoBanco != null) {

            if (!StatusPedido.PRONTO.equals(pedidoDoBanco.getStatusPedido())){
                throw new IllegalStateException("Status do Pedido não pode ser alterado para FINALIZADO se o mesmo Ainda não estiver com Status de PRONTO");
            }

            pedidoDoBanco.setStatusPedido(StatusPedido.FINALIZADO);
            pedidoRepositoryPort.save(pedidoDoBanco);
            return true;
        }
        return false;
    }


    public boolean pedidosClienteEmAberto (Long id){
        List<Pedido> pedidosDoBanco = pedidoRepositoryPort.findPedidoByClienteEmAberto(id);
        return !pedidosDoBanco.isEmpty();
    }

    public boolean pedidoEmAbertoComProduto (Long id){
        List<Pedido> pedidosDoBanco = pedidoRepositoryPort.findPedidoByProdutoEmAberto(id);
        return !pedidosDoBanco.isEmpty();
    }

    private void rotinaVerificacaoStatusPreparacao(){

        List<Pedido> pedidosEmPreparacao = pedidoRepositoryPort.findPedidoByStatus(StatusPedido.PREPARACAO);

        int numeroDePedidosEmPreparacao = pedidosEmPreparacao.size();

        if (numeroDePedidosEmPreparacao <  numeroMaximoPedidosPreparacao){

            int numeroPedidosMudarStatus = numeroMaximoPedidosPreparacao - numeroDePedidosEmPreparacao;

            List<Pedido> pedidosEmRecebidos = pedidoRepositoryPort.findPedidoByStatus(StatusPedido.RECEBIDO);

            pedidosEmRecebidos.stream()
                    .limit(numeroPedidosMudarStatus)
                    .forEach(pedido -> {
                        pedido.setStatusPedido(StatusPedido.PREPARACAO);
                        this.updateInterno(pedido);
                    });
        }
    }


    private LocalDateTime getHoraDataAtual() {
        final ZoneId ZONE_ID = ZoneId.of("America/Sao_Paulo");
        final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return LocalDateTime.parse(LocalDateTime.now(ZONE_ID).format(FORMATTER), FORMATTER);
    }

    private void calcularValorTotalEPreencheProduto(Pedido pedido) {
        double soma = 0.0;
        List<Produto> produtosAtualizados = new ArrayList<>();

        for (Produto produto : pedido.getListaProduto()) {
            ProdutoDTO produtoAtualizado = produtoService.findById(produto.getId());
            if (produtoAtualizado != null) {
                soma += produtoAtualizado.getPreco();
                produtosAtualizados.add(produtoMapper.toModelInterno(produtoAtualizado));
            }
        }
        pedido.setValorTotal((float) soma);
        pedido.setListaProduto(produtosAtualizados);
    }

    private void preencheCliente(Pedido pedido) {

        if (pedido.getCliente().getId() == null) {
            pedido.setCliente(null);
            return;
        }

        Cliente cliente =  clienteMapper.toModelInterno(clienteService.findById(pedido.getCliente().getId()));
        pedido.setCliente(cliente);
    }
}
