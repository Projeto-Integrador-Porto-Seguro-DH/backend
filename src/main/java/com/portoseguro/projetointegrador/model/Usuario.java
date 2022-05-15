package com.portoseguro.projetointegrador.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portoseguro.projetointegrador.enums.EstadosEnum;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

	// ATRIBUTOS

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_usuario")
	private long idUsuario;

	@NotNull(message = "O campo Nome não pode ficar em branco")
	@Size(min = 5, max = 255, message = "O campo nome deve conter entre 5 e 255 caracteres")
	@Column(name = "nome_usuario")
	private String nomeUsuario;

	@Size(min = 11, max = 11, message = "O campo CPF deve conter 11 caracteres")
	@Column(name = "cpf_usuario", unique = true)
	private String cpfUsuario;

	@Size(min = 5, max = 255, message = "O campo Endereço deve conter entre 5 e 255 caracteres")
	@Column(name = "logradouroEndereco_usuario")
	private String logradouroEndereco;

	@Size(min = 8, max = 8, message = "O Cep deve possuir 8 caracteres")
	@Column(name = "cepEndereco_usuario")
	private int cepEndereco;

	@Column(name = "numeroEndereco_usuario")
	private int numeroEndereco;

	@Size(min = 2, max = 255, message = "O Bairro deve conter entre 2 e 255 caracteres")
	@Column(name = "bairroEndereco_usuario")
	private String bairroEndereco;

	@Size(max = 255, message = "O Bairro deve conter no maximo 255 caracteres")
	@Column(name = "complementoEndereco_usuario")
	private String complementoEndereco;

	@Size(min = 3, max = 255, message = "A Cidade deve conter conter entre 2 e 255 caracteres")
	@Column(name = "cidadeEndereco_usuario")
	private String cidadeEndereco;

	@Column(name = "estadoEndereco_usuario")
	private EstadosEnum estadoEndereco;

	@NotNull(message = "O campo Email não pode ficar em branco")
	@Size(min = 10, max = 255, message = "O campo Email deve conter entre 10 e 255 caracteres")
	@Column(name = "email_usuario", unique = true)
	@Email(message = "O campo Email deve conter o caracter '@'")
	private String emailUsuario;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotNull(message = "O campo Senha não pode ficar em branco")
	@Column(name = "senha_usuario")
	private String senhaUsuario;

	// RELACIONAMENTOS

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = { "usuario" })
	private List<Pedido> pedidoUsuario;

	// GETTERS E SETTERS

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
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

	public String getLogradouroEndereco() {
		return logradouroEndereco;
	}

	public void setLogradouroEndereco(String logradouroEndereco) {
		this.logradouroEndereco = logradouroEndereco;
	}

	public int getCepEndereco() {
		return cepEndereco;
	}

	public void setCepEndereco(int cepEndereco) {
		this.cepEndereco = cepEndereco;
	}

	public int getNumeroEndereco() {
		return numeroEndereco;
	}

	public void setNumeroEndereco(int numeroEndereco) {
		this.numeroEndereco = numeroEndereco;
	}

	public String getBairroEndereco() {
		return bairroEndereco;
	}

	public void setBairroEndereco(String bairroEndereco) {
		this.bairroEndereco = bairroEndereco;
	}

	public String getComplementoEndereco() {
		return complementoEndereco;
	}

	public void setComplementoEndereco(String complementoEndereco) {
		this.complementoEndereco = complementoEndereco;
	}

	public String getCidadeEndereco() {
		return cidadeEndereco;
	}

	public void setCidadeEndereco(String cidadeEndereco) {
		this.cidadeEndereco = cidadeEndereco;
	}

	public EstadosEnum getEstadoEndereco() {
		return estadoEndereco;
	}

	public void setEstadoEndereco(EstadosEnum estadoEndereco) {
		this.estadoEndereco = estadoEndereco;
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

	public List<Pedido> getPedidoUsuario() {
		return pedidoUsuario;
	}

	public void setPedidoUsuario(List<Pedido> pedidoUsuario) {
		this.pedidoUsuario = pedidoUsuario;
	}

}
