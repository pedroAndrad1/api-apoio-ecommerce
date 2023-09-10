package br.com.uniriotec.sagui;

import br.com.uniriotec.sagui.model.Produto;
import br.com.uniriotec.sagui.model.dto.ProdutoForm;
import br.com.uniriotec.sagui.repository.ProdutoRepositorio;
import br.com.uniriotec.sagui.utils.ProdutoFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import br.com.uniriotec.sagui.utils.KeycloakRequest;

import static org.springframework.test.util.AssertionErrors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @Author Jean Carlos
 * CLasse de testes de integração de produto serviço.
 */
@SpringBootTest( classes = {ServicoProdutoApplication.class, ProdutoFixture.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Getter
@Slf4j
class ProdutoIntegrationTests extends KeycloakRequest{
	private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoIntegrationTests.class.getName());
	/*
	produto têm as seguintes restrições:
	- nome: não nulo e tamanho deve ser de 3 a 200 caracteres
	- Descrição: dev ter de 0 a 2000 caracteres
	- Preço: deve ser maior que ZERO
	- Ativo: Não nulo e só aceita valores booleanos 'true' ou 'false'
	--- Os testes estão ordenados pois há um test post antes dos tests de get, isso aumenta a quantidade de
	--- produttos no banco e pra refletir isso os testes foram colocados em ordem.
	 */
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProdutoFixture produtoFixture;
	@Autowired
	private ProdutoRepositorio produtoRepositorio;

	@Container
	public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest")
			.withExposedPorts(27017)
			.withReuse(true);
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		mongoDBContainer
				.start();
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl );
	}
	@BeforeAll
	public void setUp() {
		//mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		produtoFixture.saveFixture();
	}

	private final ProdutoForm produtoFormM = ProdutoForm.builder()
			.nome("Monster")
			.descricao("Naokiurasawa")
			.preco( BigDecimal.valueOf(89.99) )
			.ativo(true)
			.build();

	/**
	 * Testa post request à produtos com o cabeçalho de autorização do keycloak.
	 * @throws Exception
	 */
	@Test
	@Order(1)
	void givenAdminUser_whenPost_ThenCriaProdutoComRetorno200() throws Exception {
		String requisicaoProdutoString = objectMapper.writeValueAsString(produtoFormM);
		String token = getUserBearer(getUserTypeAdmin());
		mockMvc.perform(MockMvcRequestBuilders.post("/produtos")
				.header("Authorization",token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requisicaoProdutoString)
				.accept(MediaType.APPLICATION_JSON)
		)
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(produtoFormM.getNome()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(produtoFormM.getDescricao()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.preco").value(produtoFormM.getPreco()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.tipo").value(produtoFormM.getTipo()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.imageUrl").value(produtoFormM.getImageUrl()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.ativo").value(produtoFormM.getAtivo()));
		produtoFixture.saveProdutoFormInProdutos(produtoFormM);
	}
	@Test
	@Order(2)
	void givenUser_whenPost_ThenCriaProdutoComRetorno403() throws Exception {
		String requisicaoProdutoString = objectMapper.writeValueAsString(produtoFormM);
		String token = getUserBearer(getUserTypeUser());
		mockMvc.perform(MockMvcRequestBuilders.post("/produtos")
						.header("Authorization",token)
						.contentType(MediaType.APPLICATION_JSON)
						.content(requisicaoProdutoString)
						.accept(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isForbidden())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").doesNotExist())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nome").doesNotExist())
				.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").doesNotExist())
				.andExpect(MockMvcResultMatchers.jsonPath("$.preco").doesNotExist())
				.andExpect(MockMvcResultMatchers.jsonPath("$.tipo").doesNotExist())
				.andExpect(MockMvcResultMatchers.jsonPath("$.imageUrl").doesNotExist())
				.andExpect(MockMvcResultMatchers.jsonPath("$.ativo").doesNotExist());
	}
	@Test
	@Order(3)
	void givenUsers_whenTriesToRetriveAllProducts_ThenReturnsStatus200And403() throws Exception {
		String token = getUserBearer(getUserTypeAdmin());
		mockMvc.perform(MockMvcRequestBuilders.get("/produtos/admin")
				.header("Authorization",token)
				.contentType(MediaType.APPLICATION_JSON)
				).andDo(print())
				.andExpect(status().isOk());
		token = getUserBearer(getUserTypeUser());
		mockMvc.perform(MockMvcRequestBuilders.get("/produtos/admin")
						.header("Authorization",token)
						.contentType(MediaType.APPLICATION_JSON)
				).andDo(print())
				.andExpect(status().isForbidden());
	}
	@Test
	@Order(4)
	void givenUsers_WhenTriesToInactivateProduto_ThenReturnsStatus403And200() throws Exception {
		List<Produto> produtosFromDb= produtoRepositorio.findAll();
		assertEquals( "O tamanho da lista deve ser igual", produtoFixture.getProdutos().size(), produtosFromDb.size());
		//token de usuário comum
		String token = getUserBearer(getUserTypeUser());
		assertNotNull("Não deveria ser nulo", produtosFromDb.get(0).getId());
		String id = produtosFromDb.get(0).getId();
		String url = "/produtos/" + id;
		//Realiza a requisição como um usuário comum
		mockMvc.perform(MockMvcRequestBuilders.patch(url)
						.header("Authorization",token)
						.contentType(MediaType.APPLICATION_JSON)
				).andDo(print())
				.andExpect(status().isForbidden());
		//token de administrador
		token = getUserBearer(getUserTypeAdmin());
		//Realiza a requisição como um usuário administrador.
		mockMvc.perform(MockMvcRequestBuilders.patch(url)
						.header("Authorization",token)
						.contentType(MediaType.APPLICATION_JSON)
				).andDo(print())
				.andExpect(status().isOk());
		Optional<Produto> produtoFromDb = produtoRepositorio.findById( id );
		assertTrue( "Produto deve ser recuperado do banco", produtoFromDb.isPresent() );
		assertFalse("O produto precisa estar inativado", produtoFromDb.get().getAtivo() );
		assertNotEquals("O produto no fixture deve ser ativo e o retorno do banco deve estar inativo",
				produtosFromDb.get(0).getAtivo(),
				produtoFromDb.get().getAtivo());
	}

	@Test
	@Order(5)
	void givenUser_whenTriesToRetriveProductsPaginated_ThenReturnsStatus200() throws Exception {
		String token = getUserBearer(getUserTypeAdmin());
		mockMvc.perform(MockMvcRequestBuilders.get("/produtos")
						.header("Authorization",token)
						.contentType(MediaType.APPLICATION_JSON)
						.param("page", "1")
						.param("size", "1")
				).andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.produtos.content").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$.paginaAtual").value(1) )
				.andExpect(MockMvcResultMatchers.jsonPath("$.totalItems").value(1));
		token = getUserBearer(getUserTypeUser());
		mockMvc.perform(MockMvcRequestBuilders.get("/produtos")
						.header("Authorization",token)
						.contentType(MediaType.APPLICATION_JSON)
						.param("page", "1")
						.param("size", "1")
				).andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.produtos.content").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$.paginaAtual").value(1) )
				.andExpect(MockMvcResultMatchers.jsonPath("$.totalItems").value(1));
	}
	//recuperar por id
	@Test
	@Order(6)
	void givenUsers_whenTriesToRetriveProdutoById_ThenReturns200and200() throws Exception{
		String token = getUserBearer(getUserTypeUser());
		List<Produto> produtos = produtoRepositorio.findAll();
		Produto produto = produtos.get(0);
		String url = "/produtos/" + produto.getId();
		mockMvc.perform(MockMvcRequestBuilders.get(url)
						.header("Authorization",token)
						.contentType(MediaType.APPLICATION_JSON)
				).andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(produto.getId()) )
				.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(produto.getNome()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(produto.getDescricao()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.preco").value(produto.getPreco()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.tipo").value(produto.getTipo()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.imageUrl").value(produto.getImageUrl()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.ativo").value(produto.getAtivo()));
		token = getUserBearer(getUserTypeAdmin());
		mockMvc.perform(MockMvcRequestBuilders.get(url)
						.header("Authorization",token)
						.contentType(MediaType.APPLICATION_JSON)
				).andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(produto.getId()) )
				.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(produto.getNome()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(produto.getDescricao()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.preco").value(produto.getPreco()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.tipo").value(produto.getTipo()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.imageUrl").value(produto.getImageUrl()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.ativo").value(produto.getAtivo()));
	}
	//alterar produto
	@Test
	@Order(7)
	void givenAdmin_whenTriesToUpdateProduto_thenReturn200() throws  Exception{
		String token = getUserBearer(getUserTypeAdmin());
		List<Produto> produtos = produtoRepositorio.findAll();
		Produto produto = produtos.get(0);
		ProdutoForm produtoForm = ProdutoForm.builder()
				.id( produto.getId() )
				.nome(produto.getNome())
				.descricao( "Teste" )
				.preco( BigDecimal.valueOf(2.99) )
				.ativo(produto.getAtivo())
				.build();
		String requisicaoProdutoString = objectMapper.writeValueAsString(produtoForm);
		mockMvc.perform(MockMvcRequestBuilders.put("/produtos")
						.header("Authorization",token)
						.contentType(MediaType.APPLICATION_JSON)
						.content(requisicaoProdutoString)
						.accept(MediaType.APPLICATION_JSON)
				).andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(produto.getId()) )
				.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(produto.getNome()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(produtoForm.getDescricao()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.preco").value(produtoForm.getPreco()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.tipo").value(produto.getTipo()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.imageUrl").value(produto.getImageUrl()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.ativo").value(produto.getAtivo()));
		token = getUserBearer(getUserTypeUser());
		mockMvc.perform(MockMvcRequestBuilders.put("/produtos/")
						.header("Authorization",token)
						.contentType(MediaType.APPLICATION_JSON)
						.content(requisicaoProdutoString)
						.accept(MediaType.APPLICATION_JSON)
				).andDo(print())
				.andExpect(status().isForbidden());
	}
	@Test
	@Order(8)
	void givenAdmin_whenTriesToPostWithWrongValues_thenReturn400() throws  Exception{
		String token = getUserBearer(getUserTypeAdmin());
		List<Produto> produtos = produtoRepositorio.findAll();
		Produto produto = produtos.get(0);
		ProdutoForm produtoForm = ProdutoForm.builder()
				.id( produto.getId() )
				.nome(null)
				.descricao( null )
				.preco( BigDecimal.valueOf(-1) )
				.ativo(null)
				.build();
		String requisicaoProdutoString = objectMapper.writeValueAsString(produtoForm);
		mockMvc.perform(MockMvcRequestBuilders.post("/produtos")
						.header("Authorization",token)
						.contentType(MediaType.APPLICATION_JSON)
						.content(requisicaoProdutoString)
						.accept(MediaType.APPLICATION_JSON)
				).andDo(print())
				.andExpect(status().isBadRequest());
		token = getUserBearer(getUserTypeUser());
		mockMvc.perform(MockMvcRequestBuilders.put("/produtos/")
						.header("Authorization",token)
						.contentType(MediaType.APPLICATION_JSON)
						.content(requisicaoProdutoString)
						.accept(MediaType.APPLICATION_JSON)
				).andDo(print())
				.andExpect(status().isForbidden());
	}
}
