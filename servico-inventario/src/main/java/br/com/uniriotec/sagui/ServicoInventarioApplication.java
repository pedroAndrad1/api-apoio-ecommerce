package br.com.uniriotec.sagui;

import br.com.uniriotec.sagui.Repository.InventarioRepositorio;
import br.com.uniriotec.sagui.model.Inventario;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Slf4j
@OpenAPIDefinition(info =
@Info(title = "Inventario API", version = "2.0.4", description = "Documentation inventario API v1.0")
)
public class ServicoInventarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicoInventarioApplication.class, args);
	}
	@Bean
	public CommandLineRunner loadData(InventarioRepositorio inventarioRepositorio){
		return args -> {
			/**
			inventarioRepositorio.save(
			Inventario.builder()
					.skuCode("9780939173341")
					.quantity(100)
					.build()
			);
			inventarioRepositorio.save(
					Inventario.builder()
							.skuCode("9780939173342")
							.quantity(50)
							.build()
			);
			inventarioRepositorio.save(
					Inventario.builder()
							.skuCode("9780939173343")
							.quantity(0)
							.build()
			);**/
			List<String> skuCodes = Arrays.asList("9780939173341", "9780939173342");
			log.info( "Valor do count" +  inventarioRepositorio.countBySkuCodeInAndQuantityGreaterThan( skuCodes, 0L ) );
		};
	}
}
