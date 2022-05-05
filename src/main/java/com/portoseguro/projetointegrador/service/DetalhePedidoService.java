package com.portoseguro.projetointegrador.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portoseguro.projetointegrador.model.DetalhePedido;
import com.portoseguro.projetointegrador.model.Produtos;
import com.portoseguro.projetointegrador.repository.DetalhePedidoRepository;
import com.portoseguro.projetointegrador.repository.ProdutosRepository;

@Service
public class DetalhePedidoService {

	@Autowired
	public DetalhePedidoRepository detalhePedidoRepository;

	@Autowired
	public ProdutosRepository produtosRepository;

	public void calcularSubtotal(DetalhePedido detalhePedido) {
		Long idProdutoBD = detalhePedido.getProdutos().getIdProduto();

		Optional<Produtos> produtoUnitario = produtosRepository.findById(idProdutoBD);

		detalhePedido.setSubtotal(
				produtoUnitario.get().getPrecoUnitarioProduto().multiply(detalhePedido.getQuantidadeProduto()));
	}

	@Transactional
	public DetalhePedido cadastarDetalhes(DetalhePedido detalhePedido) {
		calcularSubtotal(detalhePedido);

		return detalhePedidoRepository.save(detalhePedido);
	}
	
}
