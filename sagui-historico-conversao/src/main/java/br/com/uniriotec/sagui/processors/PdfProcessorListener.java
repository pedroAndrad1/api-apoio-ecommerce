package br.com.uniriotec.sagui.processors;

import br.com.uniriotec.sagui.model.Aluno;
import br.com.uniriotec.sagui.model.AlunoToModel;
import br.com.uniriotec.sagui.model.Svg;
import br.com.uniriotec.sagui.model.SvgToModel;
import br.com.uniriotec.sagui.pdfIo.PDFIO;
import br.com.uniriotec.sagui.pdfIo.PdfMineLines;
import br.com.uniriotec.sagui.pdfIo.StudentMap;
import br.com.uniriotec.sagui.repository.AlunoRepository;
import br.com.uniriotec.sagui.repository.SvgRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;

@Component
@Slf4j
public class PdfProcessorListener {
    @Autowired
    private FileStorageProperties fileStorageProperties;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    AlunoRepository alunoRepository;
    @Autowired
    private AlunoToModel alunoToModel;
    @Autowired
    private SvgToModel svgToModel;
    @Autowired
    private SvgRepository svgRepository;

    /**
     * Listener da fila de eventos de historicos
     * @param studentRecordArchiveName nome do arquivos que foi colocado na pasta de upload
     * @throws IOException
     */
    @KafkaListener(topics = "placed_records", groupId = "recordsId")
    public void execute( String studentRecordArchiveName ) throws IOException {
        log.info( "Procerssamento de histórico iniciado ás {}", LocalDateTime.now());
        try ( DirectoryStream<Path> paths = Files.newDirectoryStream(Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize()
                    , path -> path.toString().endsWith(".pdf")) ){
            paths.forEach( file -> {
                log.info("arquivo lido {}", file.toString());
                PDFIO pdf = new PDFIO( file.toString() );

                PdfMineLines pdfMineLines = new PdfMineLines();
                StudentMap studentMap = pdfMineLines.mineLines( pdf.getLinesList() );
                log.info( "StudentMap {}", studentMap.getStudents().size() );
                // Transforma os dados do PDF em Aluno, salva e gera o svg
                studentMap.getStudents().values()
                        .stream()
                        .map( aluno -> alunoToModel.execute(aluno) )
                        .forEach( aluno -> {
                            alunoRepository.save(aluno);
                            Aluno saved = alunoRepository.findByMatricula(aluno.getMatricula() ).get();
                            alunoRepository.flush();
                            Svg svg = svgToModel.execute(saved);
                            svgRepository.save(svg);
                        });
                //Altera o nome do PDF na pasta para o padrão de pdf processado pela aplicação
                String uploadedFile = file.toFile().toString();
                String newFile = uploadedFile.replace("upload", "processed");
                newFile = newFile.replace(".pdf", "_processed.pdf");
                try{//Tenta fechar o PDF e mover para a pasta de pdfs processados
                    pdf.closeDocument();
                    Files.move(Paths.get(uploadedFile), Paths.get(newFile), StandardCopyOption.REPLACE_EXISTING);
                }catch(IOException ioe){
                    log.info("Erro ao tentar fechar e mover pdf: {}", file.toAbsolutePath().toString());
                }
            });
        }
        kafkaTemplate.send("placed_spliter", studentRecordArchiveName);
        log.info( "Procerssamento de histórico finalizado ás {}", LocalDateTime.now());
    }

}
