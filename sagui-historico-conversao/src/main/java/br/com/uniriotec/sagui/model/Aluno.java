package br.com.uniriotec.sagui.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the aluno database table.
 * 
 */
@Entity
@Table(name="aluno")
@NoArgsConstructor
public class Aluno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ALUNO_ID" ,unique=true, nullable=false)
	@Getter @Setter private Long alunoId;

	@Getter @Setter private double cra;

	@Column(nullable=false, length=10)
	@Getter @Setter private String curso;

	@Column(length=300)
	@Getter @Setter private String email;

	@Column(name="entrada_Ano_Periodo", nullable=false, length=5)
	@Getter @Setter private String entradaAnoPeriodo;

	@Column(nullable=false, length=45)
	@Getter @Setter private String matricula;

	@Column(name="NOME_ALUNO", length=300)
	@Getter @Setter private String nome;

	@Column(name="VERSAO_GRADE", length=45)
	@Getter @Setter private String versaoGrade;
	
	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="tutor_id")
	@Getter @Setter private Usuario usuario;

	//bi-directional many-to-one association to DisciplinaCursada
	@OneToMany(mappedBy="aluno", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Getter @Setter private Set<DisciplinaCursada> disciplinaCursadas;

	//bi-directional many-to-one association to ConfirmacaoMatricula
	@OneToMany(mappedBy="aluno")
	@Getter @Setter private Set<ConfirmacaoMatricula> confirmacaoMatriculas;

	//bi-directional many-to-one association to CrPeriodizado
	@OneToMany(mappedBy="aluno", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Getter @Setter private Set<CrPeriodizado> crPeriodizados;

	//bi-directional many-to-one association to PlanoDeIntegralizacao
	@OneToMany(mappedBy="aluno")
	@Getter @Setter private Set<PlanoDeIntegralizacao> planoDeIntegralizacaos;

	//bi-directional many-to-one association to RegrasAplicada
	@OneToMany(mappedBy="aluno")
	@Getter @Setter private Set<RegrasAplicada> regrasAplicadas;
	
	//uni-directional one-to-one association to Consolidaregra
	@OneToOne
	@JoinColumn(name="Aluno_Id", referencedColumnName="Aluno_Id")
	@Getter @Setter private ConsolidaRegra consolidaregra;

	
	//bi-directional many-to-one association to Svg
	@OneToOne(mappedBy="aluno", cascade = CascadeType.ALL)
	@Getter @Setter private Svg svg;

	@Builder
	public Aluno(Long aluno_Id, double cra, String curso, String email, String entradaAnoPeriodo, String matricula,
			String nome, String versaoGrade, Set<DisciplinaCursada> disciplinaCursadaSet, Set<CrPeriodizado> crPeriodizadoSet) {
		super();
		this.alunoId = aluno_Id;
		this.cra = cra;
		this.curso = curso;
		this.email = email;
		this.entradaAnoPeriodo = entradaAnoPeriodo;
		this.matricula = matricula;
		this.nome = nome;
		this.versaoGrade = versaoGrade;
		this.disciplinaCursadas = disciplinaCursadaSet;
		this.crPeriodizados = crPeriodizadoSet;
	}
}