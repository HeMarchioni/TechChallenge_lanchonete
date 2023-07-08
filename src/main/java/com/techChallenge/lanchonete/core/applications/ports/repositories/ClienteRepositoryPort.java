package com.techChallenge.lanchonete.core.applications.ports.repositories;

import com.techChallenge.lanchonete.adapter.infra.entity.ClienteEntity;
import com.techChallenge.lanchonete.core.applications.dtos.in.ProdutoDTO;
import com.techChallenge.lanchonete.core.applications.ports.interfaces.ServiceInterface;
import com.techChallenge.lanchonete.core.domain.Cliente;

public interface ClienteRepositoryPort extends RepositoryInterface<Cliente, Long,ClienteEntity> {



}
