package com.techChallenge.lanchonete.core.applications.services;

import com.techChallenge.lanchonete.core.applications.Enum.StatusPedido;
import com.techChallenge.lanchonete.core.applications.dtos.in.PedidoDTO;
import com.techChallenge.lanchonete.core.applications.dtos.out.PedidoOutDTO;
import com.techChallenge.lanchonete.core.applications.mapper.PedidoMapper;
import com.techChallenge.lanchonete.core.applications.ports.interfaces.PedidoServicePort;
import com.techChallenge.lanchonete.core.applications.ports.repositories.PedidoRepositoryPort;
import com.techChallenge.lanchonete.core.domain.Pedido;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService implements PedidoServicePort {


    private final PedidoRepositoryPort pedidoRepositoryPort;
    private final PedidoMapper pedidoMapper;
    private final ProdutoService produtoService;

    public PedidoService(PedidoRepositoryPort pedidoRepositoryPort, ProdutoService produtoService) {
        this.pedidoRepositoryPort = pedidoRepositoryPort;
        this.produtoService = produtoService;
        this.pedidoMapper = PedidoMapper.INSTANCE;
    }

    @Override
    public PedidoDTO create(PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoMapper.toModel(pedidoDTO);
        pedido.setDataPedido(getHoraDataAtual());
        pedido.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);
        if (pedido.getCliente().getId() == null) pedido.setCliente(null);
        calcularValorTotal(pedido);
        pedidoRepositoryPort.save(pedido);
        // continuar enviar solicitação de QR code
        return pedidoDTO;
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

    public void calcularValorTotal(Pedido pedido) {
        double soma = pedido.getListaProduto().stream()
                .mapToDouble(produto -> produtoService.findById(produto.getId()).getPreco())
                .sum();
        pedido.setValorTotal((float) soma);
    }


}
