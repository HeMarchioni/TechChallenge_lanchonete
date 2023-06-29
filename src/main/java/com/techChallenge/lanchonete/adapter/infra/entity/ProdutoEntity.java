package com.techChallenge.lanchonete.adapter.infra.entity;

import com.techChallenge.lanchonete.core.domain.Enum.CategoriaProduto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "produto")
public class ProdutoEntity extends AbstractEntity {

    @Column(name = "nm_produto",nullable = false, length = 50)
    private String nomeProduto;

    @Column(name = "nm_categoria")
    @Enumerated(EnumType.STRING)
    private CategoriaProduto categoria;

    @Column (name = "ds_produto",length = 150)
    private String descricaoProduto;

    @Column (name = "vl_produto",nullable = false)
    private Float preco;

    @Column (name = "cd_imagem",length = 150)
    private String imagem;

}
