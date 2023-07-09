package com.techChallenge.lanchonete.core.applications.mapper;

import com.techChallenge.lanchonete.adapter.infra.entity.ProdutoEntity;
import com.techChallenge.lanchonete.core.applications.dtos.in.ProdutoDTO;
import com.techChallenge.lanchonete.core.domain.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProdutoMapper {

    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    @Mapping(target = "id" , ignore = true)
    Produto toModel(ProdutoDTO produtoDTO);

    Produto toModelInterno(ProdutoDTO produtoDTO);
    ProdutoEntity toEntity(Produto produto);
    ProdutoDTO toDTO(Produto produto);
    Produto toModel(ProdutoEntity produtoEntity);
    List<Produto> toModelListEntity(List<ProdutoEntity> produtos);
    List<ProdutoDTO> toModelList(List<Produto> produtos);
}
