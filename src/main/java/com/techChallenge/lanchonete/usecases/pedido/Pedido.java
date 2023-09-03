package com.techChallenge.lanchonete.usecases.pedido;

import com.techChallenge.lanchonete.usecases.produto.Produto;
import com.techChallenge.lanchonete.usecases.cliente.Cliente;

import java.time.LocalDateTime;
import java.util.List;

public class Pedido {

    private Long id;
    private Cliente cliente;
    private List<Produto> listaProduto;
    private String observacao;
    private StatusPedido statusPedido;
    private LocalDateTime dataPedido;
    private Float valorTotal;
    private String informacoesPagamento;

    public Pedido() {
    }


    public Pedido(Long id, Cliente cliente, List<Produto> listaProduto, String observacao, StatusPedido statusPedido, LocalDateTime dataPedido, Float valorTotal, String informacoesPagamento) {
        this.id = id;
        this.cliente = cliente;
        this.listaProduto = listaProduto;
        this.observacao = observacao;
        this.statusPedido = statusPedido;
        this.dataPedido = dataPedido;
        this.valorTotal = valorTotal;
        this.informacoesPagamento = informacoesPagamento;
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

    public Float getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getInformacoesPagamento() {
        return informacoesPagamento;
    }

    public void setInformacoesPagamento(String informacoesPagamento) {
        this.informacoesPagamento = informacoesPagamento;
    }
}
