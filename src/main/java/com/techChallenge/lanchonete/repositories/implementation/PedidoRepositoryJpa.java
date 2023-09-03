package com.techChallenge.lanchonete.repositories.implementation;

import com.techChallenge.lanchonete.entity.PedidoEntity;
import com.techChallenge.lanchonete.usecases.pedido.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepositoryJpa extends JpaRepository<PedidoEntity,Long> {

    @Query("select p from PedidoEntity p where p.statusPedido = ?1 ORDER BY p.dataPedido") // — > busca os pedidos pelo status em ordem de data mais antiga
    List<PedidoEntity> findPedidoByStatus(StatusPedido statusPedido);

    @Query("select p from PedidoEntity p where p.cliente.id = ?1 and p.statusPedido != 'FINALIZADO'")
    List<PedidoEntity> findPedidoByClienteEmAberto(Long idCliente);

    @Query("SELECT p FROM PedidoEntity p JOIN p.listaProduto lp WHERE lp.id = ?1 AND p.statusPedido != 'FINALIZADO'")
    List<PedidoEntity> findPedidoByProdutoEmAberto(Long idProduto);

}
