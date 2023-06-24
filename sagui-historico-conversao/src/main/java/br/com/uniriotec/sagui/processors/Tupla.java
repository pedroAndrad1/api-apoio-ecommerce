package br.com.uniriotec.sagui.processors;

import lombok.*;

@ToString
@EqualsAndHashCode
public class Tupla {
	@Getter@Setter String matricula;
	@Getter@Setter Integer startAt;
	@Getter@Setter Integer finishAt;
	@Getter@Setter String nome;
	
	@Builder
	public Tupla(String matricula, Integer startAt, Integer finishAt, String nome) {
		this.matricula = matricula;
		this.startAt = startAt;
		this.finishAt = finishAt;
		this.nome = nome;
	}
}