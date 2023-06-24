package br.com.unirio.sagui.quartz_jobs;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Tupla{
	@Getter@Setter String matricula;
	@Getter@Setter Integer startAt;
	@Getter@Setter Integer finishAt;
	@Getter@Setter String nome;
	
	@Builder
	public Tupla( String matricula, Integer startAt, Integer finishAt, String nome) { 
		this.matricula = matricula;
		this.startAt = startAt;
		this.finishAt = finishAt;
		this.nome = nome;
	}
}