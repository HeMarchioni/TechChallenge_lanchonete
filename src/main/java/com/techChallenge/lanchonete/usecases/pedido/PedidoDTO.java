package com.techChallenge.lanchonete.usecases.pedido;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class PedidoDTO {

    private Long id;

    private Long cliente;
    @NotEmpty(message = "Obrigatorio passar o id de um Produto")
    private List<Long> listaProduto;

    @Size(max = 300, message = "Numero maximo de 300 caracteres")
    private String observacao;


    public PedidoDTO() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCliente() {
        return cliente;
    }

    public void setCliente(Long cliente) {
        this.cliente = cliente;
    }

    public List<Long> getListaProduto() {
        return listaProduto;
    }

    public void setListaProduto(List<Long> listaProduto) {
        this.listaProduto = listaProduto;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
