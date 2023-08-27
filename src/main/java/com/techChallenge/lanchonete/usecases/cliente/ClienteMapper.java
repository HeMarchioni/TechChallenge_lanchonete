package com.techChallenge.lanchonete.usecases.cliente;

import com.techChallenge.lanchonete.entity.ClienteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(target = "id" , ignore = true)
    Cliente toModel(ClienteDTO clienteDTO);

    Cliente toModelInterno(ClienteDTO clienteDTO);
    ClienteEntity toEntity(Cliente cliente);
    ClienteDTO toDTO(Cliente cliente);
    Cliente toModel(ClienteEntity clienteEntity);


}
