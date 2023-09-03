package com.techChallenge.lanchonete.usecases.pedido;

public enum StatusPedido {


    AGUARDANDO_PAGAMENTO("Aguardando Pagamento"),
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

