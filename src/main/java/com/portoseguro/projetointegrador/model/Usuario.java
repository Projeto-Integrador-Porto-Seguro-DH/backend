package com.portoseguro.projetointegrador.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portoseguro.projetointegrador.enums.EstadosEnum;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

	// ATRIBUTOS
	
		// DADOS PESSOAIS
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_usuario")
	private long idUsuario;

	@Size(max = 255, message = "O campo nome deve conter no máximo 255 caracteres")
	@Column(name = "nome_usuario")
	private String nomeUsuario;

	@Size(max = 255, message = "O campo Sobrenome deve conter no máximo 255 caracteres")
	@Column(name = "sobrenome_usuario")
	private String sobrenomeUsuario;

	@Size(max = 255, message = "O campo Email deve conter no máximo 255 caracteres")
	@Column(name = "email_usuario", unique = true)
	@Email(message = "O campo Email deve conter o caracter '@'")
	private String emailUsuario;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "senha_usuario")
	private String senhaUsuario;
	
	@Transient
	private String confirmacaoSenha;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "dataDeNascimento_usuario")
	private LocalDate dataDeNascimento;

	@Column(name = "cpf_usuario", unique = true)
	private String cpfUsuario;

	@Column(name = "telefone_usuario")
	private String telefoneUsuario;

	@Column(name = "compartilharDados_usuario")
	private boolean compartilharDadosUsuario;

		// ENDEREÇO

	@Size(min = 5, max = 255, message = "O campo Endereço deve conter entre 5 e 255 caracteres")
	@Column(name = "logradouroEndereco_usuario")
	private String logradouroEndereco;

	@Column(name = "cepEndereco_usuario")
	private String cepEndereco;

	@Column(name = "numeroEndereco_usuario")
	private String numeroEndereco;

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

	
	// CONSTRUTOR

	public Usuario() {
	}

	public Usuario(String nomeUsuario, String sobrenomeUsuario, String emailUsuario, String senhaUsuario,
			boolean compartilharDadosUsuario) {
		this.nomeUsuario = nomeUsuario;
		this.sobrenomeUsuario = sobrenomeUsuario;
		this.emailUsuario = emailUsuario;
		this.senhaUsuario = senhaUsuario;
		this.compartilharDadosUsuario = compartilharDadosUsuario;
	}

	
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

	@JsonIgnore
	public String getSenhaUsuario() {
		return senhaUsuario;
	}

	public void setSenhaUsuario(String senhaUsuario) {
		this.senhaUsuario = senhaUsuario;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public LocalDate getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(LocalDate dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public String getCpfUsuario() {
		return cpfUsuario;
	}

	public void setCpfUsuario(String cpfUsuario) {
		this.cpfUsuario = cpfUsuario;
	}

	public String getTelefoneUsuario() {
		return telefoneUsuario;
	}

	public void setTelefoneUsuario(String telefoneUsuario) {
		this.telefoneUsuario = telefoneUsuario;
	}

	public boolean isCompartilharDadosUsuario() {
		return compartilharDadosUsuario;
	}

	public void setCompartilharDadosUsuario(boolean compartilharDadosUsuario) {
		this.compartilharDadosUsuario = compartilharDadosUsuario;
	}

	public String getLogradouroEndereco() {
		return logradouroEndereco;
	}

	public void setLogradouroEndereco(String logradouroEndereco) {
		this.logradouroEndereco = logradouroEndereco;
	}

	public String getCepEndereco() {
		return cepEndereco;
	}

	public void setCepEndereco(String cepEndereco) {
		this.cepEndereco = cepEndereco;
	}

	public String getNumeroEndereco() {
		return numeroEndereco;
	}

	public void setNumeroEndereco(String numeroEndereco) {
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

	public List<Pedido> getPedidoUsuario() {
		return pedidoUsuario;
	}

	public void setPedidoUsuario(List<Pedido> pedidoUsuario) {
		this.pedidoUsuario = pedidoUsuario;
	}

	
	

	

	
	
}
