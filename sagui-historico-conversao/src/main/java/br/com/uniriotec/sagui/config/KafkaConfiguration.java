package br.com.uniriotec.sagui.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic placedSpliter(){
        return TopicBuilder
                .name("placed_spliter")
                .partitions(2)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic placedRecords(){
        return TopicBuilder
                .name("placed_records")
                .partitions(2)
                .replicas(1)
                .build();
    }
}
