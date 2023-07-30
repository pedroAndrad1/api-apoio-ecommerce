package br.com.uniriotec.sagui.model.dto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import br.com.uniriotec.sagui.model.Produto;
import br.com.uniriotec.sagui.controller.ProdutoControlador;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * Classe para tratar a conversao de produto para produtoData. implementa o HATEOAS
 * @author Jean Carlos
 */
@Component
public class ProdutoRepresentationAssembler implements RepresentationModelAssembler<Produto, ProdutoData>{
	/**
	 * Converte uma entidade Produto, anotada para persistencia, e retorna uma ProdutoData
	 * @param entity
	 * @return bean ProdutoData
	 */
	@Override
	public ProdutoData toModel(Produto entity) {
		return ProdutoData.builder()
			.id( entity.getId() )
			.nome( entity.getNome() )
			.descricao( entity.getDescricao() )
			.preco( entity.getPreco() )
				.tipo( entity.getTipo())
				.imageUrl(entity.getImageUrl())
			.ativo( entity.getAtivo() )
			.build().add( WebMvcLinkBuilder.linkTo( methodOn( ProdutoControlador.class ).produto(entity.getId()) ).withSelfRel() );
	}

	/**
	 * Conversão de uma coleção de Produtos para ProdutoData
	 * @param entities must not be {@literal null}.
	 * @return CollectionModel<ProdutoData>
	 */
	@Override
	public CollectionModel<ProdutoData> toCollectionModel(Iterable<? extends Produto> entities) {
		return RepresentationModelAssembler.super.toCollectionModel(entities);
	}
	
	
	
}
