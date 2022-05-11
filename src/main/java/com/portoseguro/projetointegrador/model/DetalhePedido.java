package com.portoseguro.projetointegrador.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tb_detalhePedido")
public class DetalhePedido implements Serializable {

	// ATRIBUTOS

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_detalhePedido")
	private Long idDetalhePedido;

	@NotNull(message = "O campo quantidade deve ser preenchido")
	@Positive(message = "O preço deve ser maior que 0")
	@Column(name = "quantidadeProduto_detalhePedido")
	private int quantidadeProduto;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@Column(name = "subTotal_detalhePedido")
	private BigDecimal subtotal;

	// RELACIONAMENTOS

	@ManyToOne(fetch = FetchType.LAZY)
	private Pedido pedido;

	@ManyToOne(fetch = FetchType.LAZY)
	private Produto produto;
	
	// GETTERS E SETTERS

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

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
}
