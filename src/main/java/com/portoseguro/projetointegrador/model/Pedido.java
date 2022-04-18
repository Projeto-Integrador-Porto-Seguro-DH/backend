package com.portoseguro.projetointegrador.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_pedido")
@JsonIgnoreProperties("pedido")
public class Pedido {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo_pedido")
	private Long idPedido; 
	
	@NotNull(message = "O campo estado do pedido deve ser preenchido")
	@Column(name = "estado_pedido")
	private boolean estadoPedido;
	
	@NotNull(message = "O campo código de envio do pedido deve ser preenchido")
	@Column(name = "codigo_envio")
	private Long codigoEnvio;
	
	@OneToMany
	private List<DetalhePedido> detalhePedido;
	
	@OneToOne
	private Envio envio;

	@OneToOne
	private Usuario usuario;

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public boolean isEstadoPedido() {
		return estadoPedido;
	}

	public void setEstadoPedido(boolean estadoPedido) {
		this.estadoPedido = estadoPedido;
	}

	public Long getCodigoEnvio() {
		return codigoEnvio;
	}

	public void setCodigoEnvio(Long codigoEnvio) {
		this.codigoEnvio = codigoEnvio;
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

	public Envio getEnvio(){
		return envio;
	}

	public void setEnvio(Envio envio){
		this.envio = envio;
	}
	
}
