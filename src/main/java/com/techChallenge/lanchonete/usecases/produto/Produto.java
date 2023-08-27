package com.techChallenge.lanchonete.usecases.produto;

public class Produto {


    private Long id;
    private String nomeProduto;

    private CategoriaProduto categoria;

    private String descricaoProduto;

    private Float preco;

    private String imagen;


    public Produto() {
    }

    public Produto(Long id, String nomeProduto, CategoriaProduto categoria, String descricaoProduto, Float preco, String imagen) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.categoria = categoria;
        this.descricaoProduto = descricaoProduto;
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

    public CategoriaProduto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaProduto categoria) {
        this.categoria = categoria;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
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
