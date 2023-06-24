package br.com.uniriotec.sagui.processors;

import br.com.uniriotec.sagui.BaseTest;
import br.com.uniriotec.sagui.model.Aluno;
import br.com.uniriotec.sagui.repository.AlunoRepository;
import br.com.uniriotec.sagui.repository.SvgRepository;
import br.com.uniriotec.sagui.svgio.SvgIo;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste de integração do consumidor de evento kafka
 * Pdf usado nesse teste possui 25 disciplinas cursadas
 * Esse teste, caso o pdf mude as constantes precisam ser atualizadas.
 */
@EmbeddedKafka( partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
@Slf4j
//@Sql(scripts = {"classpath:data/schema.sql", "classpath:data/data.sql"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PdfProcessorListenerIntegrationTest  extends BaseTest{
    private static final String PDF_PROCESSOR_LISTENER_KAFKA_TOPIC = "placed_records";

    @ClassRule
    private static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, false, 2, PDF_PROCESSOR_LISTENER_KAFKA_TOPIC);

    public static final String FIXED_MATRICULA = "20202210032";
    public static final int FIXED_DISCIPLINE_NUMBER = 25;
    public static final int FIXED_NUMBER_OF_STUDENTS = 1;


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @SpyBean
    @Autowired
    private PdfProcessorListener pdfProcessorListener;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private SvgRepository svgRepository;
    private final String FIXED_PDF = "C:\\storage\\working-area\\natalha.pdf";
    private final String FiXED_SVG_EM_BRANCO = "C:\\storage\\svg\\bsi.svg";


    //O Pdf que foi carregado aqui será o mesmo pdf colocado na pasta alvo para o teste de integração
    private FixtureForPdfProcessor fixtureForPdfProcessor;
    private String fixtureForsvgBaseBsi;
    @BeforeAll
    void setup(){
        fixtureForPdfProcessor = new FixtureForPdfProcessor( FIXED_PDF );
        SvgIo svgIo = new SvgIo(FiXED_SVG_EM_BRANCO);
        fixtureForsvgBaseBsi = svgIo.toString();
    }
    @Test
    @Order(1)
    public void dadoFixture_quandoPdfCarregado_entaoVerificaConteudo(){
        assertEquals(FIXED_NUMBER_OF_STUDENTS, fixtureForPdfProcessor.getStudentMap().getStudents().size(), "Número de estudantes no pdf lido" );
        assertTrue( fixtureForPdfProcessor.getStudentMap().getStudents().containsKey(FIXED_MATRICULA) , "Precisa ter um aluno no mapa" );
        assertEquals( fixtureForPdfProcessor.getStudentMap().getStudents().get(FIXED_MATRICULA).getDisciplines().getDisciplines().size(), FIXED_DISCIPLINE_NUMBER, "A quantidade de disciplinas deve ser igual a quantidade de disciplinas no pdf" );
    }
    @Test
    @Order(2)
    public void dadoEventoPlacedHistorico_quandoEventoDisparado_entaoVerificaPersistencia() throws InterruptedException {
        assertFalse( alunoRepository.existsByMatricula(FIXED_MATRICULA), "Aluno não deve estar persistido no banco");
        //pdfProcessorListener.execute( FIXED_MATRICULA );
        kafkaTemplate.send(PDF_PROCESSOR_LISTENER_KAFKA_TOPIC, FIXED_MATRICULA);
        Thread.sleep(1000L);
        assertTrue( alunoRepository.existsByMatricula(FIXED_MATRICULA), "Aluno deve estar incluído no banco" );
    }
    @Test
    @Order(3)
    public void dadoAlunoSalvoNoBd_entaoVerificaSeEIgualAoAlunoFixado(){
        Optional<Aluno> aluno = alunoRepository.findByMatricula( FIXED_MATRICULA );
        assertTrue( aluno.isPresent(), "Aluno deve retornar do banco" );
        assertNotNull( aluno.get().getAlunoId(), "Identificador deve estar preenchido" );
        assertEquals( 1L, aluno.get().getAlunoId(), "Id do aluno deve ser 1" );
        assertEquals( fixtureForPdfProcessor.getStudentMap().getStudents().get(FIXED_MATRICULA).getMatricula(), aluno.get().getMatricula(), "Matricula deve ser igual a matricula fixada vinda do pdf" );
        assertEquals( fixtureForPdfProcessor.getStudentMap().getStudents().get(FIXED_MATRICULA).getNome(), aluno.get().getNome(), "Nome deve ser igual ao do aluno fixado" );
        List<String> disciplinas = fixtureForPdfProcessor.getStudentMap().getStudents().get(FIXED_MATRICULA).getDisciplines().getDisciplines().keySet().stream()
                .toList();
        aluno.get().getDisciplinaCursadas().forEach(disciplina -> {
            assertTrue(disciplinas.contains(disciplina.getCodigo()), "disciplina" + disciplina.getCodigo() + "deveria estar no banco");
        });
        String svg = svgRepository.findByAlunoAlunoId( aluno.get().getAlunoId() ).get().getSvg();
        assertNotEquals( fixtureForsvgBaseBsi, svg, "Strings de svg devem ser diferentes" );
    }

}
