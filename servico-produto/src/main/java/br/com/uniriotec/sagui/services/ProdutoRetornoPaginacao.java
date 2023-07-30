package br.com.uniriotec.sagui.services;

import br.com.uniriotec.sagui.model.dto.ProdutoData;
import org.springframework.hateoas.CollectionModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode
public class ProdutoRetornoPaginacao {
	CollectionModel<ProdutoData> produtos;
	Integer paginaAtual;
	Integer totalPaginas;
	Integer totalItems;
}
