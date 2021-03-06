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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_categoria")
public class Categoria {

	// ATRIBUTOS

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_categoria")
	private Long idCategoria;

	@NotNull(message = "O campo nome precisa ser preenchido")
	@Size(max = 255, message = "O campo nome deve conter no máximo 255 caracteres")
	@Column(name = "nome_categoria")
	private String nomeCategoria;

	@Size(max = 255, message = "O campo descrição deve conter no máximo 255 caracteres")
	@Column(name = "descricao_categoria")
	private String descricaoCategoria;
	
	
	// CONSTRUTORES
	
	public Categoria() {}
	
	public Categoria(Long idCategoria,
			String nomeCategoria,
			String descricaoCategoria) {
		this.idCategoria = idCategoria;
		this.nomeCategoria = nomeCategoria;
		this.descricaoCategoria = descricaoCategoria;
	}
	
	
	// RELACIONAMENTOS

	@OneToMany(mappedBy = "categoria", cascade = CascadeType.PERSIST)
	@JsonIgnoreProperties(value = { "categoria", "detalhePedido" })
	private List<Produto> produtos;

	
	// GETTERS E SETTERS

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	public String getDescricaoCategoria() {
		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria) {
		this.descricaoCategoria = descricaoCategoria;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
}