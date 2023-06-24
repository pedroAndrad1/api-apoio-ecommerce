package br.com.unirio.sagui.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the disciplina_cursada database table.
 * 
 */
@Entity
@Table(name="disciplina_cursada")
@NamedQuery(name="DisciplinaCursada.findAll", query="SELECT d FROM DisciplinaCursada d")
@NoArgsConstructor
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@disciplina_Cursada_Id")
public class DisciplinaCursada implements Serializable, Persistable<Long> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	
	@Getter @Setter private Long disciplina_Cursada_Id;

	@Column(nullable=false, length=45)
	@Getter @Setter private String codigo;

	@Getter @Setter private double creditos;

	@Column(length=45)
	@Getter @Setter private String curso;

	@Getter @Setter private double nota;

	@Getter @Setter private int periodo = 99;

	@Column(name="QTD_CURSADA", length=45)
	@Getter @Setter private int qtdCursada;

	@Column(name="QTD_REPROVACAO", length=45)
	@Getter @Setter private int qtdReprovacao;

	@Column(length=45)
	@Getter @Setter private String situacao;

	@Column(length=4)
	@Getter @Setter private String tipo;

	@Column(length=300)
	@Getter @Setter private String titulo;

	@Column(name="VERSAO_GRADE", length=45)
	@Getter @Setter private String versaoGrade;

	//bi-directional many-to-one association to Aluno
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="DC_ALUNO_FK", nullable=false)
	@JsonIgnore
	@Getter @Setter private Aluno aluno;
	
	 @Transient
	 @Getter @Setter private boolean update;

	@Override
	public boolean isNew() {
		return this.update;
	}

	@Override
	public Long getId() {
		return this.disciplina_Cursada_Id;
	}
}