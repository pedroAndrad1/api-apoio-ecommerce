package br.com.uniriotec.sagui.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
@NoArgsConstructor
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USUARIO_ID", unique=true, nullable=false)
	@Getter @Setter private Long usuarioId;

	@Column(length=300)
	@Getter @Setter private String email;

	@Column(length=300)
	@Getter @Setter private String nome;

	@Column(name="OAUTH_KEY", length=1000)
	@Getter @Setter private String oauthKey;

	@Column(length=16)
	@Getter @Setter private String senha;
	
	@Column(name="LOGIN_ATTEMPTS")
	@Getter @Setter private int loginAttempts;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_LOGIN")
	@Getter @Setter private Date lastLogin;

	@Column
	@Getter @Setter private boolean enable;
	
	//bi-directional many-to-one association to Aluno
	@OneToMany(mappedBy="usuario")
	@Getter @Setter private Set<Aluno> alunos;

	@Builder
	public Usuario(Long usuarioId, String email, String nome, String oauthKey, String senha, int loginAttempts,
			Date lastLogin, boolean enable) {
		super();
		this.usuarioId = usuarioId;
		this.email = email;
		this.nome = nome;
		this.oauthKey = oauthKey;
		this.senha = senha;
		this.loginAttempts = loginAttempts;
		this.lastLogin = lastLogin;
		this.enable = enable;
	}
}