package com.techChallenge.lanchonete.usecases.produto;

import com.techChallenge.lanchonete.repositories.ProdutoRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoUseCase implements ProdutoUseCasePort {

    private final ProdutoRepositoryPort produtoRepositoryPort;
    private final ProdutoMapper produtoMapper;

    public ProdutoUseCase(ProdutoRepositoryPort produtoRepositoryPort) {
        this.produtoRepositoryPort = produtoRepositoryPort;
        this.produtoMapper = ProdutoMapper.INSTANCE;
    }


    @Override
    public ProdutoDTO create(ProdutoDTO produtoDTO) {
        Produto produto = produtoMapper.toModel(produtoDTO);
        produtoRepositoryPort.save(produto);
        return produtoDTO;
    }

    @Override
    public ProdutoDTO findById(Long id) {
        Produto produto = produtoRepositoryPort.findById(id);
        return produtoMapper.toDTO(produto);
    }

    @Override
    public List<ProdutoDTO> findAll() {
        List<Produto> produtos = produtoRepositoryPort.findAll();
        return produtos.stream().map(produtoMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public boolean update(ProdutoDTO produtoDTO) {

        if (produtoRepositoryPort.existsById(produtoDTO.getId())) {
            Produto produto = produtoMapper.toModel(produtoDTO);
            produtoRepositoryPort.save(produto);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (produtoRepositoryPort.existsById(id)) {
            produtoRepositoryPort.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<ProdutoDTO> buscarPorCategoria(CategoriaProduto categoriaProduto) {
        return produtoMapper.toModelList(produtoRepositoryPort.findProdutoByCategoria(categoriaProduto));
    }
}
