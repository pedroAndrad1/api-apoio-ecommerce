package br.com.uniriotec.sagui.controller;

import br.com.uniriotec.sagui.service.BancoNaoModificadoException;
import br.com.uniriotec.sagui.service.ProdutoNaoEncontradoException;
import br.com.uniriotec.sagui.service.ExcecaoResposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class AconselhadorDeExcecaoControlador {
    /**
     * Trata chamadas da api que deram erro ao tentar insert, update ou delete no banco
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler( BancoNaoModificadoException.class )
    public final ResponseEntity<ExcecaoResposta> handleDataBaseNotModified(BancoNaoModificadoException exception, WebRequest request ) {
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body( ExcecaoResposta.builder()
                .timestamp( LocalDateTime.now() )
                .message( exception.getMessage()  )
                .details( request.getDescription(false))
                .build());
    }
    /**
     * Trata chamadas a api cujo produto não foi encontrado
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler( ProdutoNaoEncontradoException.class )
    public final ResponseEntity<ExcecaoResposta> handleProdutoNotFound(ProdutoNaoEncontradoException exception, WebRequest request ){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( ExcecaoResposta.builder()
                .timestamp( LocalDateTime.now() )
                .message( exception.getMessage()  )
                .details( request.getDescription(false))
                .build());
    }
}
