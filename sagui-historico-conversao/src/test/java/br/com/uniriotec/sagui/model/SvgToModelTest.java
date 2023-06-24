package br.com.uniriotec.sagui.model;

import br.com.uniriotec.sagui.repository.SvgRepository;
import br.com.uniriotec.sagui.svgio.SvgHandler;
import br.com.uniriotec.sagui.svgio.UnsuportedCurso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * A classe recebe um aluno.
 * testa Aluno.curso se null ou -1 lança UnsuportedCurso
 * Chamada a svhHandler para pintar o svg Svg
 * Chamada ao banco para recuperar caso exista o svg salvo de aluno
 * testa se já existe uma SVG no banco e substitui pelo novo, se não cria um
 * retorna o Svg
 *
 * Os testes:
 * - verifica se não voltou do banco um SVG então cria um SVG
 * - verifica se voltou do banco um svg então subistitui a string que veio com o novo svg pintado
 * Serão ignorados, pois agregam pouco valor ao teste unitário desda classe já que a principal ação é
 * chamar o SvgHandler e o SvgRepository. Esses cenários são melhor servidos com um teste de integração.
 */
public class SvgToModelTest {
    @Mock
    SvgHandler svgHandler;
    @Mock
    SvgRepository svgRepository;
    @Mock
    List<String> cursos; //= Arrays.asList( 210, 211 );
    @Mock
    List<String> documentosSvg;
    @InjectMocks
    SvgToModel svgToModel;
    private final String NON_REGISTERED_COURSE = "200";

    void setup(){

    }
    @BeforeEach
    void init_mocks(){
        MockitoAnnotations.openMocks(this);
    }

    // testa aluno.curso null deve lançar excessão UnsuportedCurso
    @Test
    void quandoAlunoNull_entaoThrowUnsuportedCurso(){
        Aluno aluno = mock(Aluno.class);
        given( aluno.getCurso() ).willReturn(null);
        assertThrows( UnsuportedCurso.class, () -> svgToModel.execute( aluno )  );
    }
    //testa curso não registrado nas propriedades deve lançar excessão UnsuportedCurso
    @Test
    void quandoCursoNaoRegistrado_entaoThrowUnsuportedCurso(){
        Aluno aluno = mock(Aluno.class);
        given( aluno.getCurso() ).willReturn(NON_REGISTERED_COURSE);
        given( cursos.indexOf(NON_REGISTERED_COURSE) ).willReturn(-1);
        assertThrows( UnsuportedCurso.class, () -> svgToModel.execute( aluno )  );
    }
    //Estes testes podem ser desprezados como testes unitários pois a classe
    //verifica se não voltou do banco um SVG então cria um SVG
    //verifica se voltou do banco um svg então subistitui a string que veio com o novo svg pintado

}
