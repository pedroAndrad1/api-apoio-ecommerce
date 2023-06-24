package br.com.unirio.sagui.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Immutable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The persistent class for the consolidaregras database table.
 * 
 */
@Entity
@Immutable
@Table(name="consolidaregras")
@NamedQuery(name="Consolidaregra.findAll", query="SELECT c FROM ConsolidaRegra c")
@NoArgsConstructor
@ToString
public class ConsolidaRegra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ALUNO_ID")
	@Id
	@Getter @Setter private Long alunoId;

	@Column(name="ANO_ENTRADA_CHCE")
	@Getter @Setter private String anoEntradaChce;

	@Getter @Setter private Double cra;

	@Column(name="LIMITE_REP")
	@Getter @Setter private String limiteRep;

	@Column(name="MAIS_MQ5P")
	@Getter @Setter private String maisMq5p;

	@Column(name="NOME_ALUNO")
	@Getter @Setter private String nomeAluno;

	@Column(name="QTD_CHCE")
	@Getter @Setter private Long qtdChce;

	@Column(name="QTD_CHE")
	@Getter @Setter private Long qtdChe;

	@Column(name="QTD_CHO")
	@Getter @Setter private Long qtdCho;

	@Column(name="QTD_MQ5P")
	@Getter @Setter private Long qtdMq5p;

	@Column(name="QTD_PERIODOS_CHCE")
	@Getter @Setter private Long qtdPeriodosChce;

	@Column(name="SEM_ATIVIDADE_EXTENSIONISTA_CHCE")
	@Getter @Setter private String semAtividadeExtensionistaChce;

	@Column(name="SEM_ELETIVA_CHE")
	@Getter @Setter private String semEletivaChe;

	@Column(name="SEM_OPTATIVA_CHO")
	@Getter @Setter private String semOptativaCho;

	@Column(name="SOMA_PERIODIZADO")
	@Getter @Setter private Double somaPeriodizado;

	@Column(name="SOMENTE_REP")
	@Getter @Setter private String somenteRep;

	@Column(name="TRT_GERAL")
	@Getter @Setter private String trtGeral;

}