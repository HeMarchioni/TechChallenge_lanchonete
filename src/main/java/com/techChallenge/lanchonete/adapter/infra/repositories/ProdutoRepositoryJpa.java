package com.techChallenge.lanchonete.adapter.infra.repositories;

import com.techChallenge.lanchonete.adapter.infra.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProdutoRepositoryJpa extends JpaRepository<ProdutoEntity,Long> {


}
