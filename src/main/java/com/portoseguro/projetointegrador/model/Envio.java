package com.portoseguro.projetointegrador.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tb_envio")
public class Envio {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo_envio")
	private Long idEnvio;
	
	
	@NotNull(message = "O campo código do envio deve ser preenchido")
	@Column(name = "codigoEnvio_envio")
	private String codigoEnvio;
	
	@NotNull(message = "O campo valor do envio deve ser preenchido")
	@Positive(message = "O valor do envio deve ser maior que 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@Column(name = "valorEnvio_envio")
	private BigDecimal valorEnvio;

	public Long getIdEnvio() {
		return idEnvio;
	}

	public void setIdEnvio(Long idEnvio) {
		this.idEnvio = idEnvio;
	}

	public String getCodigoEnvio() {
		return codigoEnvio;
	}

	public void setCodigoEnvio(String codigoEnvio) {
		this.codigoEnvio = codigoEnvio;
	}

	public BigDecimal getValorEnvio() {
		return valorEnvio;
	}

	public void setValorEnvio(BigDecimal valorEnvio) {
		this.valorEnvio = valorEnvio;
	}

	
	
	
	
	
}
