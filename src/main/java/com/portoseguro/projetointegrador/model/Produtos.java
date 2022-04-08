package com.portoseguro.projetointegrador.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name="tb_produto")
public class Produtos {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idProduto;
	
	@NotNull(message = "O campo nome deve ser preenchido")
	@Size(min= 10,max= 255)
	private String nomeProduto;
	
	@NotNull(message = "O campo descrição deve ser preenchido")
	@Size(min= 10,max= 255)
	private String descricaoProduto;
	
	@NotNull(message = "O campo estoque deve ser preenchido")
	@Positive(message = "O campo estoque deve receber um valor maior que 0")
    private int estoqueProduto;
	
	@NotNull(message = "O campo preço deve ser preenchido")
	@Positive(message = "O campo preço deve receber um valor maior que 0")
	private BigDecimal precoProduto;

	public Long getId() {
		return idProduto;
	}

	public void setId(Long id) {
		this.idProduto = id;
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

	public int getEstoqueProduto() {
		return estoqueProduto;
	}

	public void setEstoqueProduto(int estoqueProduto) {
		this.estoqueProduto = estoqueProduto;
	}

	public BigDecimal getPrecoProduto() {
		return precoProduto;
	}

	public void setPrecoProduto(BigDecimal precoProduto) {
		this.precoProduto = precoProduto;
	}

	@Override
	public String toString() {
		return "Produtos [id=" + idProduto + ", nomeProduto=" + nomeProduto + ", descricaoProduto=" + descricaoProduto
				+ ", estoqueProduto=" + estoqueProduto + ", precoProduto=" + precoProduto + "]";
	}
	
}
