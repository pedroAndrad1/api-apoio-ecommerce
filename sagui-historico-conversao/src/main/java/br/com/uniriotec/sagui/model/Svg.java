package br.com.uniriotec.sagui.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the svg database table.
 * 
 */
@Entity
@NamedQuery(name="Svg.findAll", query="SELECT s FROM Svg s")
@NoArgsConstructor
public class Svg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SGV_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter@Setter private Long sgvId;

	//@Lob
	@Getter@Setter private String svg;

	//bi-directional one-to-one association to Aluno
	@OneToOne
	@JoinColumn(name="ALUNO_ID")
	@Getter@Setter private Aluno aluno;

	@Builder
	public Svg(Aluno aluno, String svg) {
		super();
		this.aluno = aluno;
		this.svg = svg;
	}
}