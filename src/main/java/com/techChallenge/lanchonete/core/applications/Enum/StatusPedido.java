package com.techChallenge.lanchonete.core.applications.Enum;

public enum StatusPedido {


    RECEBIDO("Recebido"),
    PREPARACAO("Preparacao"),
    PRONTO("Pronto"),
    FINALIZADO("Finalizado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

