package com.portoseguro.projetointegrador.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_pedido")
public class Pedido implements Serializable {

	// ATRIBUTOS

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_pedido")
	private Long idPedido;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dataPedido_pedido")
	private Date dataPedido = new Date(System.currentTimeMillis());

	@NotNull(message = "O campo status do pedido deve ser preenchido")
	@Column(name = "status_pedido")
	private String statusPedido;

	@Column(name = "codigo_envio")
	private String codigoEnvio;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dataEnvio_pedido")
	private Date dataEnvio;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@Column(name = "valorEnvio_pedido", columnDefinition = "decimal(19,2) default '0.00'")
	private BigDecimal valorEnvio;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@Column(name = "valorTotalPedido_pedido", columnDefinition = "decimal(19,2) default '0.00'")
	private BigDecimal valorTotalPedido;

	// RELACIONAMENTOS

	@ManyToOne
	@NotNull(message = "O pedido deve estar atrelado a um usuário!")
	@JsonIgnoreProperties("pedidoUsuario")
	private Usuario usuario;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JsonIgnoreProperties("pedido")
	private List<DetalhePedido> detalhePedidos;

	// GETTERS E SETTERS

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	public String getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(String statusPedido) {
		this.statusPedido = statusPedido;
	}

	public String getCodigoEnvio() {
		return codigoEnvio;
	}

	public void setCodigoEnvio(String codigoEnvio) {
		this.codigoEnvio = codigoEnvio;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public BigDecimal getValorEnvio() {
		return valorEnvio;
	}

	public void setValorEnvio(BigDecimal valorEnvio) {
		this.valorEnvio = valorEnvio;
	}

	public BigDecimal getValorTotalPedido() {
		return valorTotalPedido;
	}

	public void setValorTotalPedido(BigDecimal valorTotalPedido) {
		this.valorTotalPedido = valorTotalPedido;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<DetalhePedido> getDetalhePedidos() {
		return detalhePedidos;
	}

	public void setDetalhePedidos(List<DetalhePedido> detalhePedidos) {
		this.detalhePedidos = detalhePedidos;
	}
}