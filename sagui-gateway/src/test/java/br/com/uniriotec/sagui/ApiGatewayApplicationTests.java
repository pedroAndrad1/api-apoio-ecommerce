package br.com.uniriotec.sagui;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest( classes = {ApiGatewayApplication.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class ApiGatewayApplicationTests {
    @Autowired
    private WebTestClient webTestClient;

    @LocalServerPort
    private int lsp;
    @Test
    void corsTest(){
        WebTestClient.bindToServer()
                .baseUrl("http://localhost:"+lsp)
                .build()
                .get()
                .uri("/api/produtos")
                .header("origin", "http://localhost:3000")
                .exchange()
                .expectHeader()
                .valueEquals("access-control-allow-origin", "*");
    }

}
