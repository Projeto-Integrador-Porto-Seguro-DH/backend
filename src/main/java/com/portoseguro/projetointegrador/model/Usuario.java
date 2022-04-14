package com.portoseguro.projetointegrador.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable {
	
	//Default serial version Id
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo_usuario")
	private UUID idUsuario;

	@NotNull(message = "O campo Nome não pode ficar em branco")
	@Size(min = 5, max = 255, message = "O campo nome deve conter entre 5 e 255 caracteres")
	@Column(name = "nome_usuario")
	private String nomeUsuario;
	
	@NotNull(message = "O campo CPF não pode ficar em branco")
	@Size(min= 11, max = 11, message = "O campo CPF deve conter 11 caracteres")
	@Column(name = "cpf_usuario", unique = true)
	private String cpfUsuario;
	
	@NotNull(message = "O campo Endereço não pode ficar em branco")
	@Size(min = 5, max = 255, message = "O campo Endereço deve conter entre 5 e 255 caracteres")
	@Column(name = "endereco_usuario")	
    private String enderecoUsuario;
	
	@NotNull(message = "O campo Email não pode ficar em branco")
	@Size(min = 10, max = 255, message = "O campo Email deve conter entre 10 e 255 caracteres")
	@Column(name = "email_usuario", unique = true)
    @Email(message = "O campo email deve conter o caracter '@'")
	private String emailUsuario;
	
	@NotNull(message = "O campo Senha não pode ficar em branco")
	@Size(min = 8, max = 16, message = "O campo senha deve conter entre 8 e 16 caracteres")
    @Column(name = "senha_usuario")
	private String senhaUsuario;

	public UUID getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(UUID idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getCpfUsuario() {
		return cpfUsuario;
	}

	public void setCpfUsuario(String cpfUsuario) {
		this.cpfUsuario = cpfUsuario;
	}

	public String getEnderecoUsuario() {
		return enderecoUsuario;
	}

	public void setEnderecoUsuario(String enderecoUsuario) {
		this.enderecoUsuario = enderecoUsuario;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String getSenhaUsuario() {
		return senhaUsuario;
	}

	public void setSenhaUsuario(String senhaUsuario) {
		this.senhaUsuario = senhaUsuario;
	}
	
	
}


