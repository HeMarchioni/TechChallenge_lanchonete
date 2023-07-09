package com.techChallenge.lanchonete.adapter.infra.repositories;

import com.techChallenge.lanchonete.adapter.infra.entity.ProdutoEntity;
import com.techChallenge.lanchonete.core.applications.Enum.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProdutoRepositoryJpa extends JpaRepository<ProdutoEntity,Long> {

    @Query("select pr from ProdutoEntity pr where pr.categoria = ?1")   // -> busca os Produtos pela categoria
    List<ProdutoEntity> findProdutoByCategoria(CategoriaProduto categoriaProduto);


}
