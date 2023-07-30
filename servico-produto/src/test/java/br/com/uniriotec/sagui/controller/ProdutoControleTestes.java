package br.com.uniriotec.sagui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.uniriotec.sagui.model.Produto;
import br.com.uniriotec.sagui.model.dto.ProdutoData;
import br.com.uniriotec.sagui.model.dto.ProdutoForm;
import br.com.uniriotec.sagui.model.dto.ProdutoRepresentationAssembler;
import br.com.uniriotec.sagui.services.ProdutoNaoEncontradoException;
import br.com.uniriotec.sagui.services.ProdutoRetornoPaginacao;
import br.com.uniriotec.sagui.services.ProdutoServico;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProdutoControlador.class)
@Import({ ProdutoRepresentationAssembler.class })
@TestInstance(Lifecycle.PER_CLASS)
public class ProdutoControleTestes {
	@MockBean
	private ProdutoServico produtoServico;
	private MockMvc mockMvc;
	@Autowired
	private ProdutoRepresentationAssembler produtoRepresentation;

	
	private Produto produto;
	private Produto produto2;
	
	@BeforeAll
	public void setup() {
		mockMvc =
				MockMvcBuilders.standaloneSetup(new ProdutoControlador( produtoServico ))
				.setControllerAdvice(new AconselhadorDeExcecaoControlador())
				.build();
		setupProduto();
	}

	private void setupProduto() {
		produto = Produto.builder()
				.id("STRING00001b")
				.nome("Teste de livro")
				.descricao("Descriçao teste de livro")
				.preco( new BigDecimal(2.5) )
				.ativo(true)
				.build();
		produto2 = Produto.builder()
				.id("STRING00001c")
				.nome("Teste de livro2")
				.descricao("Descriçao teste de livro2")
				.preco( new BigDecimal(2.5) )
				.ativo(true)
				.build();
	}
	@Test
	public void testaRetornoCorretoQuandoProdutoNaoEncontrado() throws Exception {
		given( produtoServico.buscarPorId(produto.getId()) ).willThrow( new ProdutoNaoEncontradoException("Produto não encontrado") );
		String url = "/api/produtos/" + produto.getId();
		mockMvc.perform( get( url  ) )
				//.andDo(print())
				.andExpect( status().isNotFound() );
	}
	@Test
	public void testaRetornoCorretoQuandoInserirProduto() throws Exception {
		ProdutoForm pf = ProdutoForm.builder()
				.id(produto.getId())
				.nome(produto.getNome())
				.descricao(produto.getDescricao())
				.ativo(produto.getAtivo())
				.build();
		ProdutoData pd = ProdutoData.builder()
				.id(pf.getId())
				.nome(pf.getNome())
				.descricao(pf.getDescricao())
				.preco(pf.getPreco())
				.ativo(pf.getAtivo())
				.build();
		
		given( produtoServico.salvar(pf) ).willReturn( pd );
		
		String url = "/api/produtos/";
		mockMvc.perform( post( url  )
				.content( JsonUtil.asJsonString( pf ) )
				.contentType(MediaType.APPLICATION_JSON)
				)
			.andDo(print())
			.andExpect( status().isCreated() );
			//.andExpect(jsonPath("$.id", is(pf.getId())))
			//.andExpect(jsonPath("$.nome", is(pf.getNome())))
			//.andExpect(jsonPath("$.ativo", is(pf.getAtivo())));
	}
	@Test
	public void testaRetorno200DeBuscaPorID() throws Exception {
		given( produtoServico.buscarPorId(produto.getId()) ).willReturn(produtoRepresentation.toModel(produto));
		
		mockMvc.perform( get("/api/produtos/{id}", produto.getId()) )
			//.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(produto.getId())))
			.andExpect(jsonPath("$.nome", is(produto.getNome())))
			.andExpect(jsonPath("$.ativo", is(produto.getAtivo())));
	}
	/**
	 * Testa o json vazio na chamada do endpoint get:/api/proditos?page=x&size=x
	 * @throws Exception
	 */
	@Test
	public void testaRetornoVazioDeProdutosPaginados() throws Exception {
		int page = 0;
		int size = 2;//não altera o caso de teste
		List<Produto> produtos = new ArrayList<Produto>();
		
		given( produtoServico.buscarTodosAtivosPaginavel(page, size) ).willReturn( 
				ProdutoRetornoPaginacao.builder()
				.produtos( produtoRepresentation.toCollectionModel( produtos ) )	
				.paginaAtual( page )
				.totalItems( produtos.size() )
				.totalPaginas( produtos.size()/size )
				.build()
				);

		//String url = "/api/produtos?page=" + page + "&size=" + size;
		mockMvc.perform(get("/api/produtos?page=1&size=2")
				.accept(MediaTypes.HAL_JSON_VALUE))
			//	.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").doesNotExist());
	}
	/**
	 * Testa o retorno exato da lista de produtos na chamada ao endpoint get:/api/proditos?page=x&size=x
	 * @throws Exception
	 */
	@Test
	public void testaRetornoDeProdutosPaginados() throws Exception {
		int page = 0;
		int size = 2;//não altera o caso de teste
		List<Produto> produtos = new ArrayList<Produto>();
		
		produtos.add(produto);
		produtos.add(produto2);
		
		given( produtoServico.buscarTodosAtivosPaginavel(page, size) ).willReturn( 
				ProdutoRetornoPaginacao.builder()
				.produtos( produtoRepresentation.toCollectionModel( produtos ) )	
				.paginaAtual( page )
				.totalItems( produtos.size() )
				.totalPaginas( produtos.size()/size )
				.build()
				);

		String url = "/api/produtos?page=" + page + "&size=" + size;
		mockMvc.perform(get(url)
				.accept(MediaTypes.HAL_JSON_VALUE))
				//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.produtos.content", hasSize(2)))
				.andExpect(jsonPath("$.produtos.content[0].id", is(produto.getId())))
				.andExpect(jsonPath("$.produtos.content[0].nome", is(produto.getNome())))
				.andExpect(jsonPath("$.produtos.content[0].ativo", is(produto.getAtivo())))
				.andExpect(jsonPath("$.produtos.content[1].id", is(produto2.getId())))
				.andExpect(jsonPath("$.produtos.content[1].nome", is(produto2.getNome())))
				.andExpect(jsonPath("$.produtos.content[1].ativo", is(produto2.getAtivo())));
	}
}
