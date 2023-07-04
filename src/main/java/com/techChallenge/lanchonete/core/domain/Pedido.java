package com.techChallenge.lanchonete.core.domain;

import com.techChallenge.lanchonete.core.applications.Enum.StatusPedido;

import java.time.LocalDateTime;
import java.util.List;

public class Pedido {

    private Long id;
    private Cliente cliente;
    private List<Produto> listaProduto;
    private String observacao;
    private StatusPedido statusPedido;
    private LocalDateTime dataPedido;

    public Pedido() {
    }


    public Pedido(Long id, Cliente cliente, List<Produto> listaProduto, String observacao, StatusPedido statusPedido, LocalDateTime dataPedido) {
        this.id = id;
        this.cliente = cliente;
        this.listaProduto = listaProduto;
        this.observacao = observacao;
        this.statusPedido = statusPedido;
        this.dataPedido = dataPedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Produto> getListaProduto() {
        return listaProduto;
    }

    public void setListaProduto(List<Produto> listaProduto) {
        this.listaProduto = listaProduto;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }
}
