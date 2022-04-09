package com.portoseguro.projetointegrador.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

	@Entity
	@Table (name="tb_categoria")
	public class Categoria {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="codigo_categoria")
	private Long idCategoria;
	
	@NotNull(message = "O campo nome precisa ser preenchido")
	@Size(min = 5, max = 255, message = "O campo nome deve conter entre 5 e 255 caracteres")
	@Column(name="nome_categoria")
	private Long nomeCategoria;
	
	@NotNull(message = "O campo descrição precisa ser preenchido")
	@Size(min = 10, max = 255, message = "O campo descrição deve conter entre 10 e 255 caracteres")
	@Column(name = "descricao_categoria")
    private Long descricaoCategoria;

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Long getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(Long nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	public Long getDescricaoCategoria() {
		return descricaoCategoria;
	}

	public void setDescricaoCategoria(Long descricaoCategoria) {
		this.descricaoCategoria = descricaoCategoria;
	}
    
}
