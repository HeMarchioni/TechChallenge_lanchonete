package com.techChallenge.lanchonete.core.applications.services;

import com.techChallenge.lanchonete.core.applications.Enum.StatusPedido;
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


    public PedidoService(PedidoRepositoryPort pedidoRepositoryPort, ProdutoService produtoService, PagamentoService pagamentoService, ClienteService clienteService) {
        this.pedidoRepositoryPort = pedidoRepositoryPort;
        this.pedidoMapper = PedidoMapper.INSTANCE;
        this.produtoMapper = ProdutoMapper.INSTANCE;
        this.clienteMapper = ClienteMapper.INSTANCE;
        this.produtoService = produtoService;
        this.pagamentoService = pagamentoService;
        this.clienteService = clienteService;
    }

    @Override
    public PedidoOutDTO create(PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoMapper.toModel(pedidoDTO);
        pedido.setDataPedido(getHoraDataAtual());
        pedido.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);

        preencheCliente(pedido);
        calcularValorTotalEPreencheProduto(pedido);
        pedido = pedidoMapper.toModel(pedidoRepositoryPort.save(pedido));

        try {
            String teste = pagamentoService.gerarQrCodeMercadoPago(pedido);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
            Pedido pedido = pedidoMapper.toModel(pedidoDTO);
            pedido.setDataPedido(pedidoDoBanco.getDataPedido());
            pedido.setStatusPedido(pedidoDoBanco.getStatusPedido());
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

    public LocalDateTime getHoraDataAtual() {
        final ZoneId ZONE_ID = ZoneId.of("America/Sao_Paulo");
        final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return LocalDateTime.parse(LocalDateTime.now(ZONE_ID).format(FORMATTER), FORMATTER);
    }

    public void calcularValorTotalEPreencheProduto(Pedido pedido) {
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

    public void preencheCliente(Pedido pedido) {

        if (pedido.getCliente().getId() == null) {
            pedido.setCliente(null);
            return;
        }

        Cliente cliente =  clienteMapper.toModelInterno(clienteService.findById(pedido.getCliente().getId()));
        pedido.setCliente(cliente);
    }
}
