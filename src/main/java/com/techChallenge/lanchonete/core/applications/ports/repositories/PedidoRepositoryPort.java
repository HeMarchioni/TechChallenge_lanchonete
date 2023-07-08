package com.techChallenge.lanchonete.core.applications.ports.repositories;

import com.techChallenge.lanchonete.adapter.infra.entity.PedidoEntity;
import com.techChallenge.lanchonete.core.domain.Pedido;

public interface PedidoRepositoryPort extends RepositoryInterface<Pedido, Long, PedidoEntity> {
}
