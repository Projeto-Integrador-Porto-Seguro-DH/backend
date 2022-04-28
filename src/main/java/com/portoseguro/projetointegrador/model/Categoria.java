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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_categoria")
	private Long idCategoria;

	@NotNull(message = "O campo nome precisa ser preenchido")
	@Size(min = 5, max = 255, message = "O campo nome deve conter entre 5 e 255 caracteres")
	@Column(name = "nome_categoria")
	private String nomeCategoria;

	@NotNull(message = "O campo descrição precisa ser preenchido")
	@Size(min = 10, max = 255, message = "O campo descrição deve conter entre 10 e 255 caracteres")
	@Column(name = "descricao_categoria")
	private String descricaoCategoria;

	@OneToMany(mappedBy = "categoria", cascade = CascadeType.PERSIST)
	@JsonIgnoreProperties(value = {"categoria", "detalhePedido"})
	private List<Produtos> produtos;

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

	public List<Produtos> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produtos> produtos) {
		this.produtos = produtos;
	}
}