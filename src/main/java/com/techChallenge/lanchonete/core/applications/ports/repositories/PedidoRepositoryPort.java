package com.techChallenge.lanchonete.core.applications.ports.repositories;

import com.techChallenge.lanchonete.adapter.infra.entity.PedidoEntity;
import com.techChallenge.lanchonete.core.applications.Enum.StatusPedido;
import com.techChallenge.lanchonete.core.domain.Pedido;

import java.util.List;

public interface PedidoRepositoryPort extends RepositoryInterface<Pedido, Long, PedidoEntity> {

    List<Pedido> findPedidoByStatus(StatusPedido statusPedido);

    List<Pedido> findPedidoByClienteEmAberto(Long idCliente);

    List<Pedido> findPedidoByProdutoEmAberto(Long idProduto);

}
