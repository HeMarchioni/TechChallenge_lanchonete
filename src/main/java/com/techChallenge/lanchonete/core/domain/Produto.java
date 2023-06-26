package com.techChallenge.lanchonete.core.domain;

import com.techChallenge.lanchonete.core.domain.Enum.CategoriaProduto;

public class Produto {


    private Long id;
    private String nomeProduto;

    private CategoriaProduto descricaoProduto;

    private String categoria;

    private Float preco;

    private String imagen;


    public Produto() {
    }

    public Produto(Long id, String nomeProduto, CategoriaProduto descricaoProduto, String categoria, Float preco, String imagen) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.descricaoProduto = descricaoProduto;
        this.categoria = categoria;
        this.preco = preco;
        this.imagen = imagen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public CategoriaProduto getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(CategoriaProduto descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
