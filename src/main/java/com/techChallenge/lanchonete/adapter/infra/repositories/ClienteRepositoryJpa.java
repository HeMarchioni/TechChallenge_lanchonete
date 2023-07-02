package com.techChallenge.lanchonete.adapter.infra.repositories;

import com.techChallenge.lanchonete.adapter.infra.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepositoryJpa extends JpaRepository<ClienteEntity,Long> {


}
