package com.portoseguro.projetointegrador.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

	@Entity
	@Table ()
	public class Categoria {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idCategoria;
	
	@NotNull(message = "")
	@Size(min=0, max=255)
	private Long nomeCategoria;
	
	@NotNull(message = "")
	@Size(min=0, max=255)
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
