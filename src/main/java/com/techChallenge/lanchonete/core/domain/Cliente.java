package com.techChallenge.lanchonete.core.domain;

public class Cliente {

    private Long id;
    private String nomeCliente;
    private String email;
    private String cpf;


    public Cliente() {
    }

    public Cliente(Long id, String nomeCliente, String email, String cpf) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.email = email;
        this.cpf = cpf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
