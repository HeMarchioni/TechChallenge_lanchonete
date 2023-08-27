package com.techChallenge.lanchonete.usecases.pedido;

import com.techChallenge.lanchonete.entity.PedidoEntity;
import com.techChallenge.lanchonete.usecases.produto.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface PedidoMapper {

    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

    @Mapping(source = "cliente", target = "cliente.id")
    @Mapping(target = "listaProduto", source = "listaProduto", qualifiedByName = "mapListaProdutoIds")
    @Mapping(target = "id" , ignore = true)
    Pedido toModel(PedidoDTO pedidoDTO);

    @Mapping(target = "dataPedido", source = "dataPedido", dateFormat = "yyyy-MM-dd HH:mm:ss")
    PedidoEntity toEntity(Pedido pedido);

    @Mapping(target = "dataPedido", source = "dataPedido", dateFormat = "yyyy-MM-dd HH:mm:ss")
    PedidoOutDTO toOutDTO(Pedido pedido);

    @Mapping(target = "dataPedido", source = "dataPedido", dateFormat = "yyyy-MM-dd HH:mm:ss")
    PedidoOutDTO toOutDTO(PedidoEntity pedidoEntity);

    @Mapping(target = "dataPedido", source = "dataPedido", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Pedido toModel(PedidoEntity pedidoEntity);

    @Mapping(target = "dataPedido", source = "dataPedido", dateFormat = "yyyy-MM-dd HH:mm:ss")
    List<Pedido> toModelList(List<PedidoEntity> pedidos);

    @Mapping(target = "dataPedido", source = "dataPedido", dateFormat = "yyyy-MM-dd HH:mm:ss")
    List<PedidoOutDTO> toOutDTOList(List<Pedido> pedidos);

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
