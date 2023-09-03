package com.techChallenge.lanchonete.usecases.produto;

import com.techChallenge.lanchonete.usecases.UseCaseInterface;

import java.util.List;

public interface ProdutoUseCasePort extends UseCaseInterface<ProdutoDTO, ProdutoDTO> {


    List<ProdutoDTO> buscarPorCategoria(CategoriaProduto categoriaProduto);

}
