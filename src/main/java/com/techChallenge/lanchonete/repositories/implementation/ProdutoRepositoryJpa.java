package com.techChallenge.lanchonete.repositories.implementation;

import com.techChallenge.lanchonete.entity.ProdutoEntity;
import com.techChallenge.lanchonete.usecases.produto.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProdutoRepositoryJpa extends JpaRepository<ProdutoEntity,Long> {

    @Query("select pr from ProdutoEntity pr where pr.categoria = ?1")   // -> busca os Produtos pela categoria
    List<ProdutoEntity> findProdutoByCategoria(CategoriaProduto categoriaProduto);


}
