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
	@Size(min = 4, max = 15, message = "O campo código de envio deve conter entre 10 e 255 caracteres")
	@Column(name = "codigo_envio")
	private int codigoEnvio;

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

	public int getCodigoEnvio() {
		return codigoEnvio;
	}

	public void setCodigoEnvio(int codigoEnvio) {
		this.codigoEnvio = codigoEnvio;
	}
	
}
