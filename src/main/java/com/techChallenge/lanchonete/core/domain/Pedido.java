package com.techChallenge.lanchonete.core.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Pedido {

    private Long id;
    private long codigoPedido;
    private List<Produto> produtolist;

    private LocalDateTime dataPedido;

    private String observacao;

    private String status;


    public Pedido() {
    }

    public Pedido(Long id, long codigoPedido, List<Produto> produtolist, LocalDateTime dataPedido, String observacao, String status) {
        this.id = id;
        this.codigoPedido = codigoPedido;
        this.produtolist = produtolist;
        this.dataPedido = dataPedido;
        this.observacao = observacao;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(long codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public List<Produto> getProdutolist() {
        return produtolist;
    }

    public void setProdutolist(List<Produto> produtolist) {
        this.produtolist = produtolist;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
