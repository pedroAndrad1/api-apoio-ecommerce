package br.com.uniriotec.sagui.processors;

import br.com.uniriotec.sagui.model.Aluno;
import br.com.uniriotec.sagui.repository.AlunoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class QueueAlunoProcessorListener {
    @Autowired
    private AlunoRepository alunoRepository;
    @KafkaListener(topics = "placed_students", groupId = "studentsId")
    public void hadlePlacedStudent(Aluno aluno){
        log.info("Aluno recuperado da fila de eventos {}, com matricula: {}", aluno.getNome(), aluno.getMatricula());
        //alunoRepository.save(aluno);
    }
}
