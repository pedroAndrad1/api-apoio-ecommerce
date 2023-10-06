package br.com.uniriotec.sagui.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProdutoNaoEncontradoException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5866689211635260190L;

	public ProdutoNaoEncontradoException(String exception) {
		super(exception);
	}
}
