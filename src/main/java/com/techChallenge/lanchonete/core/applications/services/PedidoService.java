package com.techChallenge.lanchonete.core.applications.services;

import com.google.gson.Gson;
import com.techChallenge.lanchonete.adapter.infra.entity.PedidoEntity;
import com.techChallenge.lanchonete.core.applications.Enum.StatusPedido;
import com.techChallenge.lanchonete.core.applications.dtos.in.ConfirmacaoPagamentoDTO;
import com.techChallenge.lanchonete.core.applications.dtos.in.PedidoDTO;
import com.techChallenge.lanchonete.core.applications.dtos.in.ProdutoDTO;
import com.techChallenge.lanchonete.core.applications.dtos.out.PedidoOutDTO;
import com.techChallenge.lanchonete.core.applications.mapper.ClienteMapper;
import com.techChallenge.lanchonete.core.applications.mapper.PedidoMapper;
import com.techChallenge.lanchonete.core.applications.mapper.ProdutoMapper;
import com.techChallenge.lanchonete.core.applications.ports.interfaces.PedidoServicePort;
import com.techChallenge.lanchonete.core.applications.ports.repositories.PedidoRepositoryPort;
import com.techChallenge.lanchonete.core.domain.Cliente;
import com.techChallenge.lanchonete.core.domain.Pedido;
import com.techChallenge.lanchonete.core.domain.Produto;
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
public class PedidoService implements PedidoServicePort {


    private final PedidoRepositoryPort pedidoRepositoryPort;
    private final PedidoMapper pedidoMapper;
    private final ProdutoMapper produtoMapper;
    private final ClienteMapper clienteMapper;
    private final ProdutoService produtoService;
    private final PagamentoService pagamentoService;
    private final ClienteService clienteService;
    private final int numeroMaximoPedidosPreparacao;


    public PedidoService(PedidoRepositoryPort pedidoRepositoryPort, ProdutoService produtoService, PagamentoService pagamentoService, ClienteService clienteService,@Value("${numero.pedidos.preparacao}") int numeroMaximoPedidosPreparacao) {
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
        if (pedidoRepositoryPort.existsById(id)) {
            pedidoRepositoryPort.deleteById(id);
            return true;
        }
        return false;
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
            // Possivel LOG ou sistema de monitoramento. Webhooks para informar a tela pagamento aprovado.
        }
        this.rotinaVerificacaoStatusPreparacao();
    }

    @Override
    public List<PedidoOutDTO> buscarPorStatus(StatusPedido statusPedido) {
        return pedidoMapper.toOutDTOList(pedidoRepositoryPort.findPedidoByStatus(statusPedido));
    }


    public boolean alteraStatusPedidoPronto (Long id){


        return false;
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
