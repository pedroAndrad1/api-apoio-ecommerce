package br.com.uniriotec.sagui.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfiguration {
    /**
     * Chama o builder do Caffeine criando uma instância cuja duração dos itens em cache é de 60 minutos
     * @return Caffeine
     */
    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES);
    }

    /**
     * Cria as áreas de Cahce que serão utilizadas na aplicação
     * @param caffeine referência ao bean craindo no métoco CacheConfiguration.cafeineConfig()
     * @return CacheManager com a referência das áreas de chache da aplicação
     */
    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.getCache("produtoCache");
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }
}
