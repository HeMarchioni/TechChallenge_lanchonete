package com.techChallenge.lanchonete.core.applications.ports.repositories;

import com.techChallenge.lanchonete.adapter.infra.entity.ProdutoEntity;
import com.techChallenge.lanchonete.core.applications.Enum.CategoriaProduto;
import com.techChallenge.lanchonete.core.domain.Produto;

import java.util.List;

public interface ProdutoRepositoryPort extends RepositoryInterface<Produto, Long, ProdutoEntity>{

    List<Produto> findProdutoByCategoria(CategoriaProduto categoriaProduto);

}
