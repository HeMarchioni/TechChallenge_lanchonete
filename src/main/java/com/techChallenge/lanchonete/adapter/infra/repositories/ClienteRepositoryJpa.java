package com.techChallenge.lanchonete.adapter.infra.repositories;

import com.techChallenge.lanchonete.adapter.infra.entity.ClienteEntity;
import com.techChallenge.lanchonete.core.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ClienteRepositoryJpa extends JpaRepository<ClienteEntity,Long> {

    @Query("select cli from ClienteEntity cli where cli.email = ?1") // -> busca o cliente pelo email
    ClienteEntity findClienteByDs_Email(String ds_email);


}
