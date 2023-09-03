package com.techChallenge.lanchonete.repositories;

import com.techChallenge.lanchonete.entity.ClienteEntity;
import com.techChallenge.lanchonete.usecases.cliente.Cliente;

public interface ClienteRepositoryPort extends RepositoryInterface<Cliente, Long,ClienteEntity> {


    Cliente findClienteByDs_Email(String email);

    Cliente findClienteByCd_Cpf(String cpf);




}
