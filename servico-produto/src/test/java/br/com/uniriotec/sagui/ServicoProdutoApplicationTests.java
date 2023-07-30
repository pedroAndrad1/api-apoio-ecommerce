package br.com.uniriotec.sagui;

import br.com.uniriotec.sagui.model.dto.ProdutoForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

/**
 * @Author Jean Carlos
 * CLasse de testes de integração de produto serviço.
 */
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ServicoProdutoApplicationTests {
	@Container
	static MongoDBContainer mongoDbContainer = new MongoDBContainer("mongo:4.4.2");
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	private ProdutoForm produtoForm = ProdutoForm.builder()
			.nome("As crónicas de nárnia")
			.descricao("C.s.Lewis")
			.preco( BigDecimal.valueOf(45.99) )
			.ativo(true)
			.build();
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDbContainer::getReplicaSetUrl );
	}
	@Test
	void testaCriarProdutoComRetorno200() throws Exception {
		String requisicaoProdutoString = objectMapper.writeValueAsString(produtoForm);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/produto/all")
				//.contentType(MediaType.APPLICATION_JSON)
				//.content(requisicaoProdutoString)
				)
				.andExpect(status().isOk());
	}

}
