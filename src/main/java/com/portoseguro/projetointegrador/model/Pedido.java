package com.portoseguro.projetointegrador.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.portoseguro.projetointegrador.enums.FormasDePagamentoEnum;
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

	@Column(name = "status_pedido")
	private StatusPedidoEnum statusPedido;

	@Column(name = "formasDePagamento_usuario")
	private FormasDePagamentoEnum formasDePagamento;

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

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST)
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

	public void setDataPedido(LocalDateTime dataPedido) {
		this.dataPedido = dataPedido;
	}

	public StatusPedidoEnum getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(StatusPedidoEnum statusPedido) {
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

	public FormasDePagamentoEnum getFormasDePagamento() {
		return formasDePagamento;
	}

	public void setFormasDePagamento(FormasDePagamentoEnum formasDePagamento) {
		this.formasDePagamento = formasDePagamento;
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