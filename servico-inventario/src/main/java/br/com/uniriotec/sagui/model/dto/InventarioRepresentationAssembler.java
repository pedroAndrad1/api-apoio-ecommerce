package br.com.uniriotec.sagui.model.dto;

import br.com.uniriotec.sagui.model.Inventario;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


/**
 * Classe para tratar a conversao de produto para produtoData. implementa o HATEOAS
 * @author Jean Carlos
 */
@Component
public class InventarioRepresentationAssembler implements RepresentationModelAssembler<Inventario, InventarioData>{
	/**
	 * Converte uma entidade Produto, anotada para persistencia, e retorna uma ProdutoData
	 * @return bean ProdutoData
	 */
	@Override
	public @NotNull InventarioData toModel(Inventario entity) {
		return InventarioData.builder()
			.id( entity.getId() )
				.skuCode( entity.getSkuCode() )
				.quantity( entity.getQuantity() )
			.build();
	}

	/**
	 * Conversão de uma coleção de items de inventario para inventarioData
	 * @param entities must not be {@literal null}.
	 * @return CollectionModel<InventarioData>
	 */
	@Override
	public @NotNull CollectionModel<InventarioData> toCollectionModel(Iterable<? extends Inventario> entities) {
		return RepresentationModelAssembler.super.toCollectionModel(entities);
	}

	public Inventario toEntity(InventarioData model){
		return Inventario.builder()
				.id( model.getId() )
				.skuCode( model.getSkuCode() )
				.quantity( model.getQuantity() )
				.build();
	}
	public Inventario fromFormToModel(InventarioForm model){
		return Inventario.builder()
				.id( model.getId() )
				.skuCode( model.getSkuCode() )
				.quantity( model.getQuantity() )
				.build();
	}
}
