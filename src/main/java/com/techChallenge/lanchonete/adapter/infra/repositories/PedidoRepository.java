package com.techChallenge.lanchonete.adapter.infra.repositories;

import com.techChallenge.lanchonete.adapter.infra.entity.PedidoEntity;
import com.techChallenge.lanchonete.core.applications.mapper.PedidoMapper;
import com.techChallenge.lanchonete.core.applications.ports.repositories.PedidoRepositoryPort;
import com.techChallenge.lanchonete.core.domain.Pedido;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PedidoRepository implements PedidoRepositoryPort {


    private final PedidoRepositoryJpa pedidoRepositoryJpa;
    private final PedidoMapper pedidoMapper = PedidoMapper.INSTANCE;


    @Override
    public PedidoEntity save(Pedido pedido) {
        PedidoEntity pedidoEntity = pedidoMapper.toEntity(pedido);
        pedidoRepositoryJpa.save(pedidoEntity);
        return pedidoEntity;
    }

    @Override
    public Pedido findById(Long id) {
        Optional<PedidoEntity> pedidoEntity = pedidoRepositoryJpa.findById(id);
        return pedidoMapper.toModel(pedidoEntity.orElse(null));
    }

    @Override
    public List<Pedido> findAll() {
        List<PedidoEntity> pedidoEntityList = pedidoRepositoryJpa.findAll();
        return pedidoEntityList.stream().map(pedidoMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return pedidoRepositoryJpa.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        pedidoRepositoryJpa.deleteById(id);
    }
}
