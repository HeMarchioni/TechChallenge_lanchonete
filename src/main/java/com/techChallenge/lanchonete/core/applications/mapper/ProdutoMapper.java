package com.techChallenge.lanchonete.core.applications.mapper;

import com.techChallenge.lanchonete.adapter.infra.entity.ProdutoEntity;
import com.techChallenge.lanchonete.core.applications.dtos.in.ProdutoDTO;
import com.techChallenge.lanchonete.core.domain.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProdutoMapper {

    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    Produto toModel(ProdutoDTO produtoDTO);
    ProdutoEntity toEntity(Produto produto);
    ProdutoDTO toDTO(Produto produto);
    Produto toModel(ProdutoEntity produtoEntity);

}
