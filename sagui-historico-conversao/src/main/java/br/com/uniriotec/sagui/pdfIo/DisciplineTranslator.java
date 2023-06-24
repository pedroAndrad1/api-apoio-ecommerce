package br.com.uniriotec.sagui.pdfIo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DisciplineTranslator {
	DIS("Aprovado"),
	ADI("Aprovado"),
	APV("Aprovado"),
	REF("Reprovado"),
	RPV("Reprovado"),
	REP("Reprovado"),
	TRA("Trancamento Geral"),
	TR("Trancamento de disciplina"),
	ASC("Matriculado");
	
	@Getter String statusName;
}
