package br.com.uniriotec.sagui.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.data.domain.Persistable;

import jakarta.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the disciplina_cursada database table.
 * 
 */
@Entity
@Table(name="disciplina_cursada")
@NamedQuery(name="DisciplinaCursada.findAll", query="SELECT d FROM DisciplinaCursada d")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

	@Builder.Default
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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DC_ALUNO_FK", nullable=false)
	@JsonIgnore
	@Getter @Setter private Aluno aluno;
	
	 @Transient
	 @Builder.Default
	 @Getter @Setter private boolean update = true;

	@Override
	public boolean isNew() {
		return this.update;
	}

	@Override
	public Long getId() {
		return this.disciplina_Cursada_Id;
	}
}