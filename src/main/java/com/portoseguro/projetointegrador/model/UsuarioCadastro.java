package com.portoseguro.projetointegrador.model;

public class UsuarioCadastro {

	private String nomeCompleto;
	private String email;
	private String senha;
	
		
	public UsuarioCadastro() {
		super();

	}	
	
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
