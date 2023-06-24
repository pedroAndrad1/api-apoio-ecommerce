package br.com.unirio.sagui.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
@NoArgsConstructor
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@usuarioId")
public class Usuario extends RepresentationModel<Usuario> implements Serializable {
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

	//bi-directional many-to-one association to Tutoria
	@OneToMany(mappedBy="usuario", fetch=FetchType.EAGER)
	@Getter @Setter private Set<Tutoria> tutorias;
	
	//bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy="usuario", fetch=FetchType.EAGER)
	@Getter @Setter private Set<UserRole> userRoles;
	
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