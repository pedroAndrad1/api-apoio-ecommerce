package br.com.uniriotec.sagui;

import br.com.uniriotec.sagui.processors.PdfProcessorListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest( classes = {SaguiHistoricoConversaoApplication.class, PdfProcessorListener.class},
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@ActiveProfiles("teste")
public abstract class BaseTest {

}
