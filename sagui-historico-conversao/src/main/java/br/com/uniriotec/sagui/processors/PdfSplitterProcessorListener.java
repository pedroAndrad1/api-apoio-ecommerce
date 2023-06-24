package br.com.uniriotec.sagui.processors;

import br.com.uniriotec.sagui.model.Aluno;
import br.com.uniriotec.sagui.pdfIo.PDFIO;
import br.com.uniriotec.sagui.repository.AlunoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class PdfSplitterProcessorListener {
    @Autowired
    private FileStorageProperties fileStorageProperties;

    private String pdfText = null;
    private PDFTextStripper stripper = null;

    private String nameFlag = "Flag";
    private String name = "Current";
    private String matricula = null;
    private int start = 0;
    private int finsh = 0;
    List<Tupla> valores = new ArrayList<Tupla>();

    private static final Pattern studentNameRegex = Pattern.compile("Nome Aluno:\\s([a-zA-ZÀ-ú\\s]*)");
    private static final Pattern studentCodeRegex = Pattern.compile("Matrícula:\\s([0-9]{11})");
    @KafkaListener(topics = "placed_spliter", groupId = "studentsId")
    public void hadlePlacedPdf(String spliterMessage)  throws IOException{
        log.info( "Processo de separação de histórico iniciado ás {}", LocalDateTime.now());

        try ( DirectoryStream<Path> paths = Files.newDirectoryStream(Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize()
                , path -> path.toString().endsWith(".pdf")) ){
            paths.forEach( file -> {
                AtomicInteger counter = new AtomicInteger(1);
                PDFIO pdf = new PDFIO(file.toString());
                log.info(file.getFileName().toString());
                PDDocument document = pdf.getDocument();
                //Itera pelo PDF pagina por página.
                document.getPages().forEach( page -> {
                    // Na página atual cria um stipper pra poder pegar o texto da página
                    try {
                        stripper = new PDFTextStripper();
                        stripper.setSortByPosition(true);

                        if(counter.get() < document.getNumberOfPages()) {
                            stripper.setStartPage(counter.get());
                            stripper.setEndPage( counter.get() +1);
                        }else {
                            stripper.setStartPage(counter.get() - 1);
                            stripper.setEndPage( counter.get() );
                        }
                        pdfText = stripper.getText(document);
                    }catch(IOException io) {
                        io.getCause();
                    }
                    AtomicInteger numberOfLines = new AtomicInteger(0);
                    //Cria uma lista para receber as linhas de texto da página
                    List<String> list = new ArrayList<>();
                    //Adiciona a lista as linhas de texto.
                    Collections.addAll(list, pdfText.split(System.lineSeparator()));
                    //Itera pelas linhas de texto da página atual procurando a matreicula e o nome do aluno
                    //encontrando o nome e a matricula adiciona +1 a quantidade de páginas que esse aluno tem de histórico
                    list.stream().takeWhile(line -> !studentNameRegex.matcher( line ).matches() )
                            .forEach( line -> {
                                Matcher matcherMatricula = studentCodeRegex.matcher( line );
                                if( matcherMatricula.find() ) {
                                    matricula = matcherMatricula.group(1);
                                    Matcher matcherNome = studentNameRegex.matcher( list.get(numberOfLines.get() + 1) );
                                    if( matcherNome.find() ) {
                                        name = matcherNome.group(1);
                                    }
                                }
                                numberOfLines.addAndGet(1);
                            });
                    //logica de criação dos separadores
                    //Se counter = 0 só tem um aluno no pdf de histórico
                    if(counter.get() == 0) {
                        nameFlag = name;
                    }
                    //se nome que foi lido na página for igual ao nome já armazenado
                    //pega onde finaliza o ultimo adicionado a valores e pega onde ele termina pra ser onde começa
                    //e esse novo termina no contador
                    if( nameFlag.equals(name) ) {
                        finsh = counter.get();
                        if (counter.get() == document.getNumberOfPages()) {
                            valores.add( new Tupla.TuplaBuilder()
                                    .nome(nameFlag)
                                    .startAt( valores.get( valores.size() -1 ).getFinishAt() )
                                    .finishAt( counter.get() )
                                    .matricula(matricula)
                                    .build());
                        }
                    }else {
                        //inserir o novo aluno com start e finsh como delimitadores
                        if(!nameFlag.equals("Flag"))
                            valores.add( new Tupla.TuplaBuilder()
                                    .nome(nameFlag)
                                    .startAt(start)
                                    .finishAt(finsh)
                                    .matricula(matricula)
                                    .build());
                        //trocar o nome no nomeflag
                        nameFlag = name;
                        // resetar start pra finish e finish igual a start +1
                        start = counter.get()-1; finsh = counter.get();
                    }
                    //aumenta counter em 1 paraa próxima página.
                    counter.addAndGet(1);
                });
                valores.forEach(x -> {
                    log.info(x.toString());
                    Path path = Paths.get(fileStorageProperties.getPdfSplitedDir()).toAbsolutePath().normalize();
                    String saveFile = file.toString();
                    saveFile = saveFile.replace(fileStorageProperties.getProcessedDir(), fileStorageProperties.getPdfSplitedDir());
                    saveFile = saveFile.replace(file.getFileName().toString() , file.getFileName().toString().replace(".pdf", x.getNome().concat(".pdf") ));

                    try {
                        Splitter splitter = new Splitter();
                        splitter.setStartPage((int) x.getStartAt() + 1);
                        splitter.setEndPage((int) x.getFinishAt());
                        splitter.setSplitAtPage((int) x.getFinishAt());
                        List<PDDocument> splittedList = splitter.split(document);
                        for (PDDocument doc : splittedList) {
                            doc.save( saveFile );
                            doc.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                try {
                    document.close();
                    pdf.closeDocument();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.warn("It was not possible to close the pdf file:" + e.getMessage(), pdf);
                }
                try {
                    String newLocation = file.toFile().toString();
                    newLocation = newLocation.replace("processed", "historico");
                    Files.move(file,Paths.get(newLocation) , StandardCopyOption.REPLACE_EXISTING);

                } catch (IOException e) {
                    e.printStackTrace();
                    log.warn("It was not posiible to move the pdf:" + e.getMessage(), file);
                }
            });
        }
        log.info( "Processo de separação de histórico finalizado ás {}", LocalDateTime.now());
    }
}
