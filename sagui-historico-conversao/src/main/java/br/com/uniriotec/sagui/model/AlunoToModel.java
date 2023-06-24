package br.com.uniriotec.sagui.model;

import br.com.uniriotec.sagui.pdfIo.*;
import br.com.uniriotec.sagui.repository.AlunoRepository;
import br.com.uniriotec.sagui.repository.CrPeriodizadoRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Classe responsável por traduzir um Aluno que foi lido do histórico para as classes de persistência.
 * Do histórico são gerados Aluno, CrPeriodizado e DisciplinaCursada. Depois que essas classes foram persistidas,
 * as disciplinas são lidas e o SVG é gerado a partir delas.
 * @author Jean Carlos
 */
@Component
public class AlunoToModel implements ToModel<StudentFromPdf, Aluno>{
    @Autowired
    AlunoRepository alunoRepository;
    @Autowired
    CrPeriodizadoRepository crPeriodizadoRepository;
    private final int INICIO_TEXTO_ENTRADA_ANO_PERIODO = 0;
    private final int FIM_TEXTO_ENTRADA_ANO_PERIODO = 5;
    private final int INICIO_TEXTO_CURSO = 5;
    private final int FIM_TEXTO_CURSO = 8;
    /**
     * Executa a tradução de um StudentFromPdf Para um Aluno
     * @param entity StudentFromPdf
     * @return Aluno
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = {ConstraintViolationException.class, Exception.class}, readOnly = false)
    public Aluno execute(StudentFromPdf entity) {
        Aluno aluno = alunoRepository.findByMatricula( entity.getMatricula() ).orElseGet( () -> Aluno.builder()
                .matricula( entity.getMatricula() )
                .email( entity.getNome()+"@uniriotec.br" )//Placeholder - o e-mail não está em lugar nenhum do histórico
                .nome( entity.getNome() )
                .entradaAnoPeriodo( entity.getMatricula().substring( INICIO_TEXTO_ENTRADA_ANO_PERIODO, FIM_TEXTO_ENTRADA_ANO_PERIODO  ) )
                .curso( entity.getMatricula().substring( INICIO_TEXTO_CURSO, FIM_TEXTO_CURSO ) )
                .cra( 0 )
                .crPeriodizadoSet(new HashSet<>())
                .disciplinaCursadaSet(new HashSet<>())
                .build());

        if (aluno.getAlunoId() == null) {
           aluno = alunoRepository.save(aluno);
           alunoRepository.flush();
        }
        //Um aluno imutável é necessário para que ele seja inserido em cada classe de relacionamento para que seja persistido no banco
        final Aluno alunoPersisted = aluno;
        //Gera lista de cr periodizado recuperado do historico
        aluno.setCrPeriodizados(  extractCrPeridizado( entity.getNotas(), alunoPersisted ) );
        //Gera a lista de disciplinbas recuperadas do histórico
        aluno.setDisciplinaCursadas( extractDisciplinaCursada( entity.getDisciplines(), alunoPersisted ) );
        return aluno;
    }

    /**
     * Extrai CrsPeriodizados da leitura do PDF e gera a lista de CrsPeriodizados para persistência.
     * @param crPeriodizadoFromPdfSet
     * @return
     */
    private Set<CrPeriodizado> extractCrPeridizado(Set<CrPeriodizadoFromPdf> crPeriodizadoFromPdfSet, Aluno alunoPersisted){
        if( alunoPersisted == null || alunoPersisted.getCrPeriodizados() == null)
            return crPeriodizadoFromPdfSet.stream()
                        .map( cr -> geraCrPeriodizado( cr, alunoPersisted ) )
                        .collect(Collectors.toSet());
        else {
            //Cria um map chave valor com o identificador de crperiodizado(periodo) e crPeriodizado como valor para ser pesquisado e evotar chamada ao banco.
            Map<String, CrPeriodizado> mapCrs = crPeriodizadoFromPdfSet.stream()
                    .map( cr -> geraCrPeriodizado( cr, alunoPersisted ) )
                    .collect( Collectors.toMap( CrPeriodizado::getPeriodo, Function.identity() ));
            //Adiciona os ids aos CrPeriodizados que já existiem em alunoPersisted
            alunoPersisted.getCrPeriodizados().stream()
                    .filter( in -> mapCrs.containsKey( in.getPeriodo() ) )
                    .forEach( in -> mapCrs.get( in.getPeriodo() ).setCrPeriodizadoId( in.getCrPeriodizadoId() ) );
            return new HashSet<>(mapCrs.values());
        }

    }

    /**
     * Traforma uma crperiodizadofrompdf em um cr periodizado.
     * @param crPeriodizadoFromPdf
     * @return
     */
    private CrPeriodizado geraCrPeriodizado( CrPeriodizadoFromPdf crPeriodizadoFromPdf, Aluno alunoPersisted) {
        return CrPeriodizado.builder()
                    .periodo( crPeriodizadoFromPdf.getDescricao() )
                    .cargaCreditos( crPeriodizadoFromPdf.getCargaDeCreditos().toString() )
                    .cargaHoraria( crPeriodizadoFromPdf.getCargaHoraria().toString() )
                    .aluno( alunoPersisted )
                    .build();
    }

    /**
     * Extrai lista de disciplinas da leitura do pdf e gera a lista de disciplinas para persistência.
     * @param disciplines
     * @param alunoPersisted
     * @return
     */
    private Set<DisciplinaCursada> extractDisciplinaCursada(DisciplineMap disciplines, Aluno alunoPersisted) {
        Set<DisciplinaCursada> disciplinasOnAluno =  Optional.of( alunoPersisted.getDisciplinaCursadas() ).orElse( new HashSet<>() );
        Map<String, DisciplinaCursada> mapDisciplinas = disciplinasOnAluno.stream()
                .collect( Collectors.toMap( DisciplinaCursada::getCodigo, Function.identity() ) );
        //Pega a lista de crPeriodizado vindado histórico e intera sobre ela
        return disciplines.getDisciplines().values().stream()
                .map( discipline -> {
                    Long id;
                    //O map pode não conter a disciplina que veio do histórico e o  método get retornar null ocasionando null pointer
                    if( mapDisciplinas == null || mapDisciplinas.get( discipline.getCode() ) == null )
                        id = null;
                    else
                        id = mapDisciplinas.get( discipline.getCode() ).getDisciplina_Cursada_Id();
                    return toDisciplinaCursada( discipline, id , alunoPersisted );
                })
                .collect( Collectors.toSet() );
    }

    /**
     * Transforma uma DisciplineFromPFD em DisciplinaCursada.
     * @param disciplineFromPdf
     * @param alunoPersisted
     * @return
     */
    private DisciplinaCursada toDisciplinaCursada( DisciplineFromPdf disciplineFromPdf, Long disciplinaId , Aluno alunoPersisted ){
        return DisciplinaCursada.builder()
                .disciplina_Cursada_Id( disciplinaId )
                .codigo( disciplineFromPdf.getCode() )
                .curso( alunoPersisted.getCurso() )
                .nota( (disciplineFromPdf.getGrade() == null)? 0 : disciplineFromPdf.getGrade() )
                .qtdCursada( disciplineFromPdf.getTimesAttended() )
                .qtdReprovacao( disciplineFromPdf.getTimesFailure() )
                .situacao( translateSituation( disciplineFromPdf.getStatusCode() ) )
                .aluno(alunoPersisted)
                .build();
    }

    /**
     * Lê o enum com as traduções de status de disciplina e retorna a trudução correta para a disciplina passada
     * @param statusCode
     * @return
     */
    private String translateSituation(String statusCode ){
        for( DisciplineTranslator dt : DisciplineTranslator.values()  )
            if( dt.name().equals( statusCode ) )
                return dt.getStatusName();
        return statusCode;
    }
}
