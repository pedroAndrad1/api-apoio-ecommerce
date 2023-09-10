package br.com.uniriotec.sagui.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

import javax.annotation.PostConstruct;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.junit.jupiter.Testcontainers;

@Slf4j
@Getter
public abstract class KeycloakRequest{
    @Value("${on-premisses.keyclock.token.url}")
    private String keycloakRealm;
    @Value("${on-premisses.keyclock.grant_type}")
    private String grantType;
    @Value("${on-premisses.keyclock.client_id}")
    private String clientId;
    @Value("${on-premisses.keyclock.client_secret}")
    private String clientSecret;
    @Value("${on-premisses.keyclock.username.admin}")
    private String usernameAdmin;
    @Value("${on-premisses.keyclock.password.admin}")
    private String passwordAdmin;
    @Value("${on-premisses.keyclock.username.user}")
    private String usernameUser;
    @Value("${on-premisses.keyclock.password.user}")
    private String passwordUser;
    private final String userTypeAdmin = "admin";
    private final String userTypeUser = "user";

    public String getUserBearer(String bearerType) {

        try {
            URI authorizationURI = new URIBuilder(keycloakRealm).build();
            WebClient webclient = WebClient.builder()
                    .build();
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.put("grant_type", Collections.singletonList(grantType));
            formData.put("client_id", Collections.singletonList(clientId));
            formData.put("client_secret", Collections.singletonList(clientSecret));
            formData.put("scope", Collections.singletonList("openid"));
            if( bearerType.equals(userTypeAdmin) ){
                formData.put("username", Collections.singletonList(usernameAdmin));
                formData.put("password", Collections.singletonList(passwordAdmin));
            }else if(bearerType.equals(userTypeUser)){
                formData.put("username", Collections.singletonList(usernameUser));
                formData.put("password", Collections.singletonList(passwordUser));
            }
            String result = webclient.post()
                    .uri(authorizationURI)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JacksonJsonParser jsonParser = new JacksonJsonParser();

            return "Bearer " + jsonParser.parseMap(result)
                    .get("access_token")
                    .toString();
        } catch (URISyntaxException e) {
            log.error("Can't obtain an access token from Keycloak!", e);
        }

        return null;
    }
}
