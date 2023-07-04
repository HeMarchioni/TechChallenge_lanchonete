package com.techChallenge.lanchonete.core.applications.mapper;

import com.techChallenge.lanchonete.adapter.infra.entity.PedidoEntity;
import com.techChallenge.lanchonete.core.applications.dtos.in.PedidoDTO;
import com.techChallenge.lanchonete.core.applications.dtos.out.PedidoOutDTO;
import com.techChallenge.lanchonete.core.domain.Pedido;
import com.techChallenge.lanchonete.core.domain.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface PedidoMapper {


    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

    @Mapping(target = "dataPedido", source = "dataPedido", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "cliente", target = "cliente.id")
    @Mapping(target = "listaProduto", source = "listaProduto", qualifiedByName = "mapListaProdutoIds")
    Pedido toModel(PedidoDTO pedidoDTO);

    @Mapping(target = "dataPedido", source = "dataPedido", dateFormat = "yyyy-MM-dd HH:mm:ss")
    PedidoEntity toEntity(Pedido cliente);

    @Mapping(target = "dataPedido", source = "dataPedido", dateFormat = "yyyy-MM-dd HH:mm:ss")
    PedidoOutDTO toOutDTO(Pedido cliente);

    @Mapping(target = "dataPedido", source = "dataPedido", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Pedido toModel(PedidoEntity clienteEntity);




    @Named("mapListaProdutoIds")
    default List<Produto> mapListaProdutoIds(List<Long> listaProdutoIds) {
        return listaProdutoIds.stream()
                .map(Id -> {
                    Produto produto = new Produto();
                    produto.setId(Id);
                    return produto;
                })
                .collect(Collectors.toList());
    }


}
