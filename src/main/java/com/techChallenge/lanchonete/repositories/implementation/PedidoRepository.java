package com.techChallenge.lanchonete.repositories.implementation;

import com.techChallenge.lanchonete.entity.PedidoEntity;
import com.techChallenge.lanchonete.usecases.pedido.StatusPedido;
import com.techChallenge.lanchonete.usecases.pedido.PedidoMapper;
import com.techChallenge.lanchonete.repositories.PedidoRepositoryPort;
import com.techChallenge.lanchonete.usecases.pedido.Pedido;
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

    @Override
    public List<Pedido> findPedidoByStatus(StatusPedido statusPedido) {
        return pedidoMapper.toModelList(pedidoRepositoryJpa.findPedidoByStatus(statusPedido));
    }

    @Override
    public List<Pedido> findPedidoByClienteEmAberto(Long idCliente) {
        return pedidoMapper.toModelList(pedidoRepositoryJpa.findPedidoByClienteEmAberto(idCliente));
    }

    @Override
    public List<Pedido> findPedidoByProdutoEmAberto(Long idProduto) {
        return pedidoMapper.toModelList(pedidoRepositoryJpa.findPedidoByProdutoEmAberto(idProduto));
    }


}
