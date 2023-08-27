package com.techChallenge.lanchonete.repositories.implementation;

import com.techChallenge.lanchonete.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ClienteRepositoryJpa extends JpaRepository<ClienteEntity,Long> {

    @Query("select cli from ClienteEntity cli where cli.email = ?1") // -> busca o cliente pelo email
    ClienteEntity findClienteByDs_Email(String email);


    @Query("select cli from ClienteEntity cli where cli.cpf = ?1") // -> busca o cliente pelo Cpf
    ClienteEntity findClienteByCd_Cpf(String cpf);


}
