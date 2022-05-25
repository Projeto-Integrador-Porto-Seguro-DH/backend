package com.portoseguro.projetointegrador.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portoseguro.projetointegrador.enums.StatusPedidoEnum;

@Entity
@Table(name = "tb_pedido")
public class Pedido {

	// ATRIBUTOS

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_pedido")
	private Long idPedido;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
	@Column(name = "dataPedido_pedido")
	private LocalDateTime dataPedido = LocalDateTime.now();

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "status_pedido")
	private StatusPedidoEnum statusPedido;
	
	@Transient
	String mensagemDeStatusPedido;

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
	private List<DetalhePedido> detalhePedido;

	// GETTERS E SETTERS

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public LocalDateTime getDataPedido() {
		return dataPedido;
	}

	@JsonIgnore
	public StatusPedidoEnum getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(StatusPedidoEnum statusPedido) {
		this.statusPedido = statusPedido;
	}
	
	public String getMensagemDeStatusPedido() {
		return statusPedido.getDescricao();
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

	public List<DetalhePedido> getDetalhePedido() {
		return detalhePedido;
	}

	public void setDetalhePedido(List<DetalhePedido> detalhePedido) {
		this.detalhePedido = detalhePedido;
	}

}