package com.techChallenge.lanchonete.core.applications.dtos.in;

import com.techChallenge.lanchonete.core.applications.Enum.StatusPedido;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO {

    private Long id;

    private Long cliente;
    @NotEmpty(message = "Obrigatorio passar o id de um Produto")
    private List<Long> listaProduto;

    @Size(max = 300, message = "Numero maximo de 300 caracteres")
    private String observacao;
    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;


    @NotNull(message = "Obrigatório passar uma Data")
    @Pattern(regexp = "^([0-9]{4})-([0-1][0-9])-([0-3][0-9])\\s([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])$",
            message = " A data deve estar no formato 2007-08-04 18:01:01")
    @Size(min = 15 , max = 19)
    private LocalDateTime dataPedido;


    public PedidoDTO() {
    }

    public PedidoDTO(Long id, Long cliente, List<Long> listaProduto, String observacao, StatusPedido statusPedido, @NotNull(message = "Obrigatório passar uma Data") LocalDateTime dataPedido) {
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
