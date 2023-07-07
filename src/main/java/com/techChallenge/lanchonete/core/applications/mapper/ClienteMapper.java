package com.techChallenge.lanchonete.core.applications.mapper;

import com.techChallenge.lanchonete.adapter.infra.entity.ClienteEntity;
import com.techChallenge.lanchonete.core.applications.dtos.in.ClienteDTO;
import com.techChallenge.lanchonete.core.domain.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(target = "id" , ignore = true)
    Cliente toModel(ClienteDTO clienteDTO);
    ClienteEntity toEntity(Cliente cliente);
    ClienteDTO toDTO(Cliente cliente);
    Cliente toModel(ClienteEntity clienteEntity);


}
