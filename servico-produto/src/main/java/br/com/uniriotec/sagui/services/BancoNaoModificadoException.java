package br.com.uniriotec.sagui.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_MODIFIED)
public class BancoNaoModificadoException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7576493051086509296L;
	
	public BancoNaoModificadoException(String message) {
		super(message);
	}

}
