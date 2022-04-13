package com.portoseguro.projetointegrador.model;

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
public class Usuario {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo_usuario")
	private Long idUsuario;

	@NotNull(message = "O campo nome não pode ficar em branco")
	@Size(min = 1, max = 255, message = "O campo nome deve conter entre 1 e 255 caracteres")
	@Column(name = "nome_usuario")
	private String nomeUsuario;
	
	@NotNull(message = "O campo email não pode ficar em branco")
	@Size(min = 10, max = 255, message = "O campo email deve conter entre 10 e 255 caracteres")
	@Column(name = "email_usuario")
    @Email(message = "O campo email deve conter o caracter '@'")
	private String emailUsuario;
	
	@NotNull(message = "O campo CPF não pode ficar em branco")
	@Size(min= 11, max = 11, message = "O campo CPF deve conter 11 caracteres")
	@Column(name = "cpf_usuario")
	private String cpfUsuario;

	@NotNull(message = "O campo senha não pode ficar em branco")
	@Size(min = 8, max = 16, message = "O campo senha deve conter entre 8 e 16 caracteres")
    @Column(name = "senha_usuario")
	private String senhaUsuario;
	
	@NotNull(message = "O campo endereço não pode ficar em branco")
	@Size(min = 5, max = 255, message = "O campo endereço deve conter entre 5 e 255 caracteres")
	@Column(name = "endereco_usuario")	
    private String enderecoUsuario;
	
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String getCpfUsuario() {
		return cpfUsuario;
	}

	public void setCpfUsuario(String cpfUsuario) {
		this.cpfUsuario = cpfUsuario;
	}
	
	public String getSenhaUsuario() {
		return senhaUsuario;
	}

	public void setSenhaUsuario(String senhaUsuario) {
		this.senhaUsuario = senhaUsuario;
	}

	public String getEnderecoUsuario() {
		return enderecoUsuario;
	}

	public void setEnderecoUsuario(String enderecoUsuario) {
		this.enderecoUsuario = enderecoUsuario;
	}
	
}


