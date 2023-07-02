package com.techChallenge.lanchonete.core.applications.services;

import com.techChallenge.lanchonete.core.applications.dtos.in.ProdutoDTO;
import com.techChallenge.lanchonete.core.applications.mapper.ProdutoMapper;
import com.techChallenge.lanchonete.core.applications.ports.interfaces.ProdutoServicePort;
import com.techChallenge.lanchonete.core.applications.ports.repositories.ProdutoRepositoryPort;
import com.techChallenge.lanchonete.core.domain.Produto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService implements ProdutoServicePort {

    private final ProdutoRepositoryPort produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoService(ProdutoRepositoryPort produtoRepository) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = ProdutoMapper.INSTANCE;
    }


    @Override
    public ProdutoDTO create(ProdutoDTO produtoDTO) {
        Produto produto = produtoMapper.toModel(produtoDTO);
        produtoRepository.save(produto);
        return produtoDTO;
    }

    @Override
    public ProdutoDTO findById(Long id) {
        Produto produto = produtoRepository.findById(id);
        return produtoMapper.toDTO(produto);
    }

    @Override
    public List<ProdutoDTO> findAll() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream().map(produtoMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public boolean update(ProdutoDTO produtoDTO) {

        if (produtoRepository.existsById(produtoDTO.getId())) {
            Produto produto = produtoMapper.toModel(produtoDTO);
            produtoRepository.save(produto);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
