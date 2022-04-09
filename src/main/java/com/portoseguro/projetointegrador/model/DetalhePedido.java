package com.portoseguro.projetointegrador.model;

import java.math.BigDecimal;
import java.util.Date;

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
@Table(name = "tb_detalhePedido")
public class DetalhePedido {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo_detalhePedido")
	private Long idDetalhePedido;
	
	@NotNull(message = "O campo quantidade deve ser preenchido")
	@Positive(message = "O preço deve ser maior que 0")
	@Column(name = "quantidadeProduto_detalhePedido")
	private int quantidadeProduto;
	
	@NotNull(message = "O campo preço unitário deve ser preenchido")
	@Positive(message = "O preço deve ser maior que 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@Column(name = "precoUnitario_detalhePedido")
	private BigDecimal precoUnitario;
	
	@NotNull(message = "O campo subtotal deve ser preenchido")
	@Positive(message = "O preço deve ser maior que 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@Column(name = "subTotal_detalhePedido")
	private BigDecimal subTotal;
	
	@NotNull(message = "O campo data deve ser preenchido")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dataPedido_detalhePedido")
	private Date dataPedido;
	
	@NotNull(message = "O campo data deve ser preenchido")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dataEnvio_detalhePedido")
	private Date dataEnvio;

	public Long getIdDetalhePedido() {
		return idDetalhePedido;
	}

	public void setIdDetalhePedido(Long idDetalhePedido) {
		this.idDetalhePedido = idDetalhePedido;
	}

	public int getQuantidadeProduto() {
		return quantidadeProduto;
	}

	public void setQuantidadeProduto(int quantidadeProduto) {
		this.quantidadeProduto = quantidadeProduto;
	}

	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
	
	
	
}
