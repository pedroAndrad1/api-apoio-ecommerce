package br.com.uniriotec.sagui.service;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
public class ExcecaoResposta {
	@Getter private final LocalDateTime timestamp;
	@Getter private final String message;
	@Getter private final String details;
}