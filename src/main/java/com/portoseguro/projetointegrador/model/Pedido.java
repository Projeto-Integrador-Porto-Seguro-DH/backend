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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.portoseguro.projetointegrador.repository.DetalhePedidoRepository;

@Entity
@Table(name = "tb_pedido")
public class Pedido {

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

	@Positive(message = "O valor do envio deve ser maior que 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@Column(name = "valorEnvio_pedido")
	private BigDecimal valorEnvio;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@Column(name = "valorTotalPedido_pedido")
	private BigDecimal valorTotalPedido;
	
	@Transient
	@Autowired
	public DetalhePedidoRepository detalhePedidoRepository;
	
	// RELACIONAMENTOS

	@ManyToOne
	@NotNull(message = "O pedido deve estar atrelado a um usuário!")
	@JsonIgnoreProperties("pedidoUsuario")
	private Usuario usuario;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("pedido")
	private List<DetalhePedido> detalhePedido;
	
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

	public void setValorTotalPedido() {
		BigDecimal somaSubtotal = new BigDecimal(0);
		List<DetalhePedido> listaDeSubtotais = detalhePedidoRepository.findAllByPedido(this.idPedido);
		
		for(int i = 0; i < listaDeSubtotais.size(); i++ ) {
			somaSubtotal.add(listaDeSubtotais.get(i).getSubtotal());
		}
		this.valorTotalPedido.add(somaSubtotal.add(getValorEnvio()));
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
