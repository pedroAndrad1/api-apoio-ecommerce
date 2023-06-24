package br.com.uniriotec.sagui;

import br.com.uniriotec.sagui.processors.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@EnableTransactionManagement
public class SaguiHistoricoConversaoApplication {
    public static void main(String... args){
        SpringApplication.run(SaguiHistoricoConversaoApplication.class, args);
    }
}
