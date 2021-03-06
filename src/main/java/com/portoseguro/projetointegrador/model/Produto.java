package com.portoseguro.projetointegrador.model;

import java.math.BigDecimal;
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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_produto")
public class Produto {

	// ATRIBUTOS

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_produto")
	private Long idProduto;

	@NotNull(message = "O campo nome deve ser preenchido")
	@Size(min = 1, max = 255, message = "O campo nome do produto deve conter no máximo 255 caracteres")
	@Column(name = "nome_produto")
	private String nomeProduto;

	@NotNull(message = "O campo descrição deve ser preenchido")
	@Size(min = 1, max = 255, message = "O campo descrição do produto deve conter no máximo 255 caracteres")
	@Column(name = "descricao_produto")
	private String descricaoProduto;

	@NotNull(message = "O campo preço deve ser preenchido")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@Column(name = "preco_produto")
	private BigDecimal precoUnitarioProduto;

	@NotNull(message = "O campo estoque deve ser preenchido")
	@Column(name = "estoque_produto")
	private int estoqueProduto;

	@Column(name = "foto_prodto")
	private String fotoProduto;

	@Column(name = "disponivel_produto", columnDefinition = "boolean default '1'")
	private boolean produtoDisponivel;

	// RELACIONAMENTOS

	@OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = { "pedido", "produto" })
	private List<DetalhePedido> detalhePedido;

	@ManyToOne
	@JsonIgnoreProperties(value = { "produtos" })
	private Categoria categoria;

	// GETTERS E SETTERS

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getDescricaoProduto() {
		return descricaoProduto;
	}

	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}

	public BigDecimal getPrecoUnitarioProduto() {
		return precoUnitarioProduto;
	}

	public void setPrecoUnitarioProduto(BigDecimal precoUnitarioProduto) {
		this.precoUnitarioProduto = precoUnitarioProduto;
	}

	public int getEstoqueProduto() {
		return estoqueProduto;
	}

	public void setEstoqueProduto(int estoqueProduto) {
		this.estoqueProduto = estoqueProduto;
	}

	public String getFotoProduto() {
		return fotoProduto;
	}

	public void setFotoProduto(String fotoProduto) {
		this.fotoProduto = fotoProduto;
	}

	public boolean isProdutoDisponivel() {
		return produtoDisponivel;
	}

	public void setProdutoDisponivel(boolean produtoDisponivel) {
		this.produtoDisponivel = produtoDisponivel;
	}

	public List<DetalhePedido> getDetalhePedido() {
		return detalhePedido;
	}

	public void setDetalhePedido(List<DetalhePedido> detalhePedido) {
		this.detalhePedido = detalhePedido;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
