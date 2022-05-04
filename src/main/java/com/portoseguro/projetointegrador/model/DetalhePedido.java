package com.portoseguro.projetointegrador.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_detalhePedido")
public class DetalhePedido {

	// ATRIBUTOS

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_detalhePedido")
	private Long idDetalhePedido;

	@NotNull(message = "O campo quantidade deve ser preenchido")
	@Positive(message = "O preço deve ser maior que 0")
	@Column(name = "quantidadeProduto_detalhePedido")
	private BigDecimal quantidadeProduto;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@Column(name = "subTotal_detalhePedido")
	private BigDecimal subtotal;

	// RELACIONAMENTOS

	@ManyToOne
	@JsonIgnoreProperties("detalhePedido")
	private Pedido pedido;

	@ManyToOne
	@JsonIgnoreProperties("detalhePedido")
	private Produtos produtos;
	
	// GETTERS E SETTERS

	public Long getIdDetalhePedido() {
		return idDetalhePedido;
	}

	public void setIdDetalhePedido(Long idDetalhePedido) {
		this.idDetalhePedido = idDetalhePedido;
	}

	public BigDecimal getQuantidadeProduto() {
		return quantidadeProduto;
	}

	public void setQuantidadeProduto(BigDecimal quantidadeProduto) {
		this.quantidadeProduto = quantidadeProduto;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Produtos getProdutos() {
		return produtos;
	}

	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
	}

}
