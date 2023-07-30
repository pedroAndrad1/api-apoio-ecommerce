package br.com.uniriotec.sagui.services;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Builder;

@Builder
public class ExcecaoResposta {
	@Getter private final LocalDateTime timestamp;
	@Getter private final String message;
	@Getter private final String details;
}