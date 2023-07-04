package com.techChallenge.lanchonete.core.applications.services;

import com.techChallenge.lanchonete.core.applications.dtos.in.PedidoDTO;
import com.techChallenge.lanchonete.core.applications.dtos.out.PedidoOutDTO;
import com.techChallenge.lanchonete.core.applications.mapper.PedidoMapper;
import com.techChallenge.lanchonete.core.applications.ports.interfaces.PedidoServicePort;
import com.techChallenge.lanchonete.core.applications.ports.repositories.PedidoRepositoryPort;
import com.techChallenge.lanchonete.core.domain.Pedido;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoService implements PedidoServicePort {


    private final PedidoRepositoryPort pedidoRepositoryPort;
    private final PedidoMapper pedidoMapper;

    public PedidoService(PedidoRepositoryPort pedidoRepositoryPort, PedidoMapper pedidoMapper) {
        this.pedidoRepositoryPort = pedidoRepositoryPort;
        this.pedidoMapper = PedidoMapper.INSTANCE;
    }

    @Override
    public PedidoDTO create(PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoMapper.toModel(pedidoDTO);
        pedidoRepositoryPort.save(pedido);
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
        if (pedidoRepositoryPort.existsById(pedidoDTO.getId())) {
            Pedido pedido = pedidoMapper.toModel(pedidoDTO);
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
}
