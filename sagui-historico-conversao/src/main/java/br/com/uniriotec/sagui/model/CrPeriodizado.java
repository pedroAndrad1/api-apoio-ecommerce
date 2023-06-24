package br.com.uniriotec.sagui.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


/**
 * The persistent class for the cr_periodizado database table.
 * 
 */
@Entity
@Table(name="cr_periodizado")
@NamedQuery(name="CrPeriodizado.findAll", query="SELECT c FROM CrPeriodizado c")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CrPeriodizado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CR_PERIODIZADO_ID", unique=true, nullable=false)
	private Long crPeriodizadoId;

	@Column(name="CARGA_CREDITOS", length=5)
	private String cargaCreditos;

	@Column(name="CARGA_HORARIA", length=5)
	private String cargaHoraria;

	@Column(nullable=false)
	private double cr;

	@Column(nullable=false, length=22)
	private String periodo;

	//bi-directional many-to-one association to Aluno
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CR_P_ALUNO_FK", nullable=false)
	private Aluno aluno;
}