package com.techChallenge.lanchonete.adapter.infra.repositories;

import com.techChallenge.lanchonete.adapter.infra.entity.PedidoEntity;
import com.techChallenge.lanchonete.core.applications.Enum.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepositoryJpa extends JpaRepository<PedidoEntity,Long> {

    @Query("select p from PedidoEntity p where p.statusPedido = ?1 ORDER BY p.dataPedido") // — > busca os pedidos pelo status em ordem de data mais antiga
    List<PedidoEntity> findPedidoByStatus(StatusPedido statusPedido);



}
