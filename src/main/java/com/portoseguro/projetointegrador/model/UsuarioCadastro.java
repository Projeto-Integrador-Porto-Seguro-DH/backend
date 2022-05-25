package com.portoseguro.projetointegrador.model;

public class UsuarioCadastro {

	private String nomeUsuario;
	private String sobrenomeUsuario;
	private String emailUsuario;
	private String senhaUsuario;
	
		
	public UsuarioCadastro() {
		super();

	}


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


	
}
