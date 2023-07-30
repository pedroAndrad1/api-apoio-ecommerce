package br.com.uniriotec.sagui;



import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "Produto API", version = "2.0.4", description = "Documentation product API v1.0")
)
public class ServicoProdutoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicoProdutoApplication.class, args);
	}
}
