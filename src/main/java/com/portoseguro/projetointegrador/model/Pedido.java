package com.portoseguro.projetointegrador.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_pedido")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_pedido")
	private Long idPedido;

	@NotNull(message = "O campo data deve ser preenchido")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dataPedido_detalhePedido")
	private Date dataPedido;

	@NotNull(message = "O campo status do pedido deve ser preenchido")
	@Column(name = "status_pedido")
	private String statusPedido;

	@NotNull(message = "O campo código de envio do pedido deve ser preenchido")
	@Column(name = "codigo_envio")
	private String codigoEnvio;

	@NotNull(message = "O campo data deve ser preenchido")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dataEnvio_detalhePedido")
	private Date dataEnvio;

	@NotNull(message = "O campo valor do envio deve ser preenchido")
	@Positive(message = "O valor do envio deve ser maior que 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@Column(name = "valorEnvio_envio")
	private BigDecimal valorEnvio;

	@OneToOne
	@JsonIgnoreProperties("pedido")
	private Usuario usuario;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("detalhePedido")
	private List<DetalhePedido> detalhePedido;

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

	public List<DetalhePedido> getDetalhePedido() {
		return detalhePedido;
	}

	public void setDetalhePedido(List<DetalhePedido> detalhePedido) {
		this.detalhePedido = detalhePedido;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
