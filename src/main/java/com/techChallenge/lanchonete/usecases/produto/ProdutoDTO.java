package com.techChallenge.lanchonete.usecases.produto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProdutoDTO {

    private Long id;
    @NotEmpty
    @Size(min = 2 , max = 50,message = "O nome do Produto nao pode conter menos q 2 caracteres e no maximo 50")
    private String nomeProduto;

    private CategoriaProduto categoria;

    @NotEmpty
    @Size(max = 150,message = "A Descrição do Produto não pode ser maior que 150 caracteres")
    private String descricaoProduto;

    @NotNull
    @Digits(integer=6, fraction=2,message = "Formato de preço aceito 000000.00 (no maximo 6 inteiros e 2 casas fracionarias")
    private Float preco;

    private String imagen;


    public ProdutoDTO() {
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
