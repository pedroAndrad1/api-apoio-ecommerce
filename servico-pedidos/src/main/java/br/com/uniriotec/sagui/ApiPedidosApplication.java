package br.com.uniriotec.sagui;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "Pedidos API", version = "2.0.4", description = "Documentation pedidos API v1.0")
)
public class ApiPedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiPedidosApplication.class, args);
				//.run(ApiPedidosApplication.class, args);
	}

}
