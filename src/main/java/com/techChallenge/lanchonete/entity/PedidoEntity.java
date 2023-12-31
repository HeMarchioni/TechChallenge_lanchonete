package com.techChallenge.lanchonete.entity;

import com.techChallenge.lanchonete.usecases.pedido.StatusPedido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Pedidos")
public class PedidoEntity extends AbstractEntity{

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "cliente_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private ClienteEntity cliente;

    @ManyToMany
    @JoinTable(
            name = "pedido_produto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private List<ProdutoEntity> listaProduto;

    @Column(name = "ds_observacao",length = 300)
    private String observacao;

    @Column(name = "ds_status")
    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

    @Column(name = "dt_Pedido",nullable = false, length = 20)
    private LocalDateTime dataPedido;

    @Column (name = "vl_total",nullable = false)
    private Float valorTotal;

    @Column(name = "ds_pagamento", columnDefinition = "text" ,length = 300)
    private String informacoesPagamento;

}
