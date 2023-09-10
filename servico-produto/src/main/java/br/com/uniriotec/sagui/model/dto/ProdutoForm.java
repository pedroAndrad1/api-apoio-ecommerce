package br.com.uniriotec.sagui.model.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoForm {
	
	@Getter @Setter private String id;
	@NotEmpty(message="{form.produto.nome.nao.vazio}")
	@Size(min = 3, max = 200, message = "{form.produto.nome.min-max}")
	@Getter @Setter private String nome;
	@Size(min = 0, max = 2000, message = "{form.produto.descricao.max}")
	@Getter @Setter private String descricao;
	@Min(value = 0, message = "{form.produto.preco.min}")
	@Getter @Setter private BigDecimal preco;
	//Lista de tipo do produto
	@Getter @Setter private List<String> tipo;
	//Url da imagem do produto
	@Getter @Setter private String imageUrl;
	@NotNull(message="{form.produto.is.valid}")
	@Getter @Setter private Boolean ativo;
}
