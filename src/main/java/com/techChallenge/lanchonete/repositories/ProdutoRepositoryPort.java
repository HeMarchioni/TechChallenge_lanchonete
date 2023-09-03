package com.techChallenge.lanchonete.repositories;

import com.techChallenge.lanchonete.entity.ProdutoEntity;
import com.techChallenge.lanchonete.usecases.produto.CategoriaProduto;
import com.techChallenge.lanchonete.usecases.produto.Produto;

import java.util.List;

public interface ProdutoRepositoryPort extends RepositoryInterface<Produto, Long, ProdutoEntity>{

    List<Produto> findProdutoByCategoria(CategoriaProduto categoriaProduto);

}
