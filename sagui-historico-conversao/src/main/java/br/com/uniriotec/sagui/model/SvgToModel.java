package br.com.uniriotec.sagui.model;

import br.com.uniriotec.sagui.repository.SvgRepository;
import br.com.uniriotec.sagui.svgio.SvgHandler;
import br.com.uniriotec.sagui.svgio.UnsuportedCurso;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Esta classe é responsável por receber um aluno com as disciplinas que foram cursadas e usando os
 * dados de aprovação, reprovação e matrícula pintar o svg
 */
@Component
public class SvgToModel implements ToModel<Aluno, Svg>{
    private SvgHandler svgHandler;

    private final SvgRepository svgRepository;

    private final List<String> documentosSvg;
    private final List<String> cursos;

    @Autowired
    public SvgToModel(SvgHandler svgHandler, SvgRepository svgRepository,
                      @Value("#{'${file.svg-bsi}'.split(';')}") List<String> documentosSvg,
                      @Value("#{'${file.bsi-codigo}'.split(';')}") List<String> cursos) {
        this.svgHandler = svgHandler;
        this.svgRepository = svgRepository;
        this.documentosSvg = documentosSvg;
        this.cursos = cursos;
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, rollbackFor = {JsonMappingException.class, IOException.class})
    public Svg execute(Aluno entity) {
        if(entity.getCurso() == null )
            throw new UnsuportedCurso("O aluno não possui código de curso cadastrado, favor cadastrar curso do aluno e tentar novamente");
        int idxCurso = cursos.indexOf( entity.getCurso().trim() );
        if( idxCurso == -1 )
            throw new UnsuportedCurso("O aluno não possui código de curso suportado pela aplicação");

        String svg = svgHandler.paintSvg( entity.getDisciplinaCursadas(), documentosSvg.get(idxCurso) );
        Optional<Svg> svgFromDB = svgRepository.findByAlunoAlunoId(entity.getAlunoId());
        if( svgFromDB.isEmpty() )
            svgFromDB = Optional.of(
                    Svg.builder()
                            .svg( svg )
                            .aluno( entity )
                            .build()
            );
        else
            svgFromDB.get().setSvg( svg );
        return svgFromDB.get();
    }
}
