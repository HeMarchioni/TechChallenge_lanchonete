package com.techChallenge.lanchonete.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "cliente")
public class ClienteEntity extends AbstractEntity{

    @Column(name = "nm_cliente",nullable = false, length = 50)
    private String nomeCliente;

    @Column(name = "ds_email",nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "cd_cpf",nullable = false, unique = true, length = 14)
    private String cpf;


}
