package com.portoseguro.projetointegrador.dto;

import com.portoseguro.projetointegrador.model.Usuario;

public class CadastroDTO {

	// ATRIBUTOS

	private String nomeUsuario;
	private String sobrenomeUsuario;
	private String emailUsuario;
	private String senhaUsuario;
	private boolean compartilharDadosUsuario;

	// MÉTODO

	public Usuario criarUsuarioModel() {
		return new Usuario(nomeUsuario, sobrenomeUsuario, emailUsuario, senhaUsuario, compartilharDadosUsuario);
	}

	// GETTERS E SETTERS

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSobrenomeUsuario() {
		return sobrenomeUsuario;
	}

	public void setSobrenomeUsuario(String sobrenomeUsuario) {
		this.sobrenomeUsuario = sobrenomeUsuario;
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

	public boolean isCompartilharDadosUsuario() {
		return compartilharDadosUsuario;
	}

	public void setCompartilharDadosUsuario(boolean compartilharDadosUsuario) {
		this.compartilharDadosUsuario = compartilharDadosUsuario;
	}

}
