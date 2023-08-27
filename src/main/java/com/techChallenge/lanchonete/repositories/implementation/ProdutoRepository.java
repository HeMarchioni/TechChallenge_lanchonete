package com.techChallenge.lanchonete.repositories.implementation;

import com.techChallenge.lanchonete.entity.ProdutoEntity;
import com.techChallenge.lanchonete.usecases.produto.CategoriaProduto;
import com.techChallenge.lanchonete.usecases.produto.ProdutoMapper;
import com.techChallenge.lanchonete.repositories.ProdutoRepositoryPort;
import com.techChallenge.lanchonete.usecases.produto.Produto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProdutoRepository implements ProdutoRepositoryPort {

    private final ProdutoRepositoryJpa produtoRepositoryJpa;
    private final ProdutoMapper produtoMapper = ProdutoMapper.INSTANCE;


    @Override
    public ProdutoEntity save(Produto produto) {
        ProdutoEntity produtoEntity = produtoMapper.toEntity(produto);
        produtoRepositoryJpa.save(produtoEntity);
        return produtoEntity;
    }

    @Override
    public Produto findById(Long id) {
        Optional<ProdutoEntity> produtoEntity = produtoRepositoryJpa.findById(id);
        return produtoMapper.toModel(produtoEntity.orElse(null));
    }

    @Override
    public List<Produto> findAll() {
        List<ProdutoEntity> produtoEntityList = produtoRepositoryJpa.findAll();
        return produtoEntityList.stream().map(produtoMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return produtoRepositoryJpa.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        produtoRepositoryJpa.deleteById(id);
    }

    @Override
    public List<Produto> findProdutoByCategoria(CategoriaProduto categoriaProduto) {
        return produtoMapper.toModelListEntity(produtoRepositoryJpa.findProdutoByCategoria(categoriaProduto));
    }
}
