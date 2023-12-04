package br.com.uniriotec.sagui.model.dto;

import br.com.uniriotec.sagui.model.LinhaItemPedido;
import br.com.uniriotec.sagui.model.Pedido;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class LinhaItemPedidoRepresentationAssembler implements RepresentationModelAssembler<LinhaItemPedido, LinhaItemPedidoData> {
    @Override
    public LinhaItemPedidoData toModel(LinhaItemPedido entity) {
        return LinhaItemPedidoData.builder()
                .codigoSku( entity.getCodigoSku() )
                .quantidade( entity.getQuantidade() )
                .preco( entity.getPreco() )
                .build();
                //.add( linkTo( methodOn( LinhaItemPedidoControlador.class ).linhaItemPedido( entity.getId() ) ).withSelfRel() );

    }

    @Override
    public CollectionModel<LinhaItemPedidoData> toCollectionModel(Iterable<? extends LinhaItemPedido> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
    public LinhaItemPedido mapToPersistense( LinhaItemPedidoData linhaItemPedidoData, Pedido pedido){
        return LinhaItemPedido.builder()
                .codigoSku(linhaItemPedidoData.getCodigoSku())
                .quantidade(linhaItemPedidoData.getQuantidade())
                .preco(linhaItemPedidoData.getPreco())
                .pedido( pedido )
                .build();
    }

    public List<LinhaItemPedido> mapToPersistenseCollection(CollectionModel<LinhaItemPedidoData> linhaItemPedidoDataList, Pedido pedido) {
        return linhaItemPedidoDataList.getContent()
                .stream()
                .map( linhaItemPedidoData ->  mapToPersistense(linhaItemPedidoData, pedido ) )
                .toList();
    }
}
