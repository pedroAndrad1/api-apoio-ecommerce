package br.com.uniriotec.sagui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Order(1)
public class PublicSecurityConfiguration {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChainPublic(ServerHttpSecurity serverHttpSecurity){
        serverHttpSecurity//.anonymous().and()
                .csrf().disable()
                .authorizeExchange( exchange -> exchange.pathMatchers(  "/eureka/**","/eureka/web" ).permitAll()
                        .pathMatchers("/webjars/swagger-ui/**").permitAll()
                        .pathMatchers("/v3/api-docs/**").permitAll()
                        .pathMatchers("/v3/api-docs/produtos").permitAll()
                        .anyExchange().permitAll()
                );
        return serverHttpSecurity.build();
    }
}
