package br.com.uniriotec.sagui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/api/home"))
public class HomeController {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @GetMapping
    public String home(){
        kafkaTemplate.send("placed_records", "segundo_período");
        return "Historicos_começaram a ser processados";
    }
}
