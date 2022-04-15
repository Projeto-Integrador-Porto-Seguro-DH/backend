package com.portoseguro.projetointegrador.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_pedido")
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
	
}
