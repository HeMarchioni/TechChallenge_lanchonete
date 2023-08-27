package com.techChallenge.lanchonete.repositories;

import com.techChallenge.lanchonete.entity.PedidoEntity;
import com.techChallenge.lanchonete.usecases.pedido.StatusPedido;
import com.techChallenge.lanchonete.usecases.pedido.Pedido;

import java.util.List;

public interface PedidoRepositoryPort extends RepositoryInterface<Pedido, Long, PedidoEntity> {

    List<Pedido> findPedidoByStatus(StatusPedido statusPedido);

    List<Pedido> findPedidoByClienteEmAberto(Long idCliente);

    List<Pedido> findPedidoByProdutoEmAberto(Long idProduto);

}
