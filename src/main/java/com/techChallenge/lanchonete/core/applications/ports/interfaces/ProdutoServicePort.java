package com.techChallenge.lanchonete.core.applications.ports.interfaces;

import com.techChallenge.lanchonete.core.applications.Enum.CategoriaProduto;
import com.techChallenge.lanchonete.core.applications.dtos.in.ProdutoDTO;

import java.util.List;

public interface ProdutoServicePort extends ServiceInterface<ProdutoDTO, ProdutoDTO>{


    List<ProdutoDTO> buscarPorCategoria(CategoriaProduto categoriaProduto);

}
