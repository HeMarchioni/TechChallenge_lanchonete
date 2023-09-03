package com.techChallenge.lanchonete.usecases.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public class BuscaCpfEmailDTO {

    @Email(message = "Informe um email Valido")
    @Size(min = 3,max = 150)
    private String email;

    @CPF(message = "Informe um CPF Valido")
    private String cpf;


    public BuscaCpfEmailDTO() {
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
