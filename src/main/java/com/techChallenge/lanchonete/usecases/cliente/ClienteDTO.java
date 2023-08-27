package com.techChallenge.lanchonete.usecases.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

public class ClienteDTO {


    private Long id;

    @NotEmpty
    @Size(min = 2 , max = 50)
    private String nomeCliente;

    @NotEmpty
    @Email(message = "Informe um email Valido")
    @Size(min = 3,max = 150)
    private String email;

    @NotEmpty
    @CPF(message = "Informe um CPF Valido")
    private String cpf;


    public ClienteDTO() {
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
