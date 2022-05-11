package com.portoseguro.projetointegrador.service;

import com.portoseguro.projetointegrador.model.DetalhePedido;
import com.portoseguro.projetointegrador.model.Produto;
import com.portoseguro.projetointegrador.repository.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portoseguro.projetointegrador.model.Pedido;
import com.portoseguro.projetointegrador.repository.DetalhePedidoRepository;
import com.portoseguro.projetointegrador.repository.PedidoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

	@Autowired
	public PedidoRepository pedidoRepository;

	@Autowired
	public DetalhePedidoRepository detalhePedidoRepository;

	@Autowired
	private ProdutosRepository produtosRepository;

//	public void calcularTotalPedido(Pedido pedido) {
//		BigDecimal somaSubtotal = new BigDecimal(0);
//
//		List<DetalhePedido> listaDeDetalhesDoPedido = detalhePedidoRepository.findAllByPedido(pedido.getIdPedido());
//
//		for (int i = 0; i < listaDeDetalhesDoPedido.size(); i++) {
//			somaSubtotal.add(listaDeDetalhesDoPedido.get(i).getSubtotal());
//		}
//		pedido.setValorTotalPedido(somaSubtotal.add(pedido.getValorEnvio()));
//
//	}

	@Transactional
	public Pedido cadastrarPedido(Pedido pedido) {
		Pedido pedidoEntity = pedidoRepository.save(pedido);

		List<DetalhePedido> detalhesPedido = pedido.getDetalhePedidos();
		detalhesPedido.forEach(detalhePedido -> {
			Optional<Produto> produtoOptional = produtosRepository.findById(detalhePedido.getProduto().getIdProduto());
			detalhePedido.setPedido(pedido);
			produtoOptional.ifPresent(produto -> {
				produto.setEstoqueProduto(produto.getEstoqueProduto() - detalhePedido.getQuantidadeProduto());
				produtosRepository.save(produto);
			});
		});

		this.detalhePedidoRepository.saveAll(detalhesPedido);
		return pedidoEntity;
	}

	@Transactional
	public Boolean deletePedido(Long idPedido) {
		 pedidoRepository.findById(idPedido).map(resposta -> {
			List<DetalhePedido> detalhesPedido = resposta.getDetalhePedidos();
			detalhesPedido.forEach(detalhePedido -> {
				Optional<Produto> produtoOptional = produtosRepository.findById(detalhePedido.getProduto().getIdProduto());
				produtoOptional.ifPresent(produto -> {
					produto.setEstoqueProduto(produto.getEstoqueProduto() + detalhePedido.getQuantidadeProduto());
					produtosRepository.save(produto);
				});
			});

				pedidoRepository.deleteById(idPedido);
			return true;
		});

		 return false;
	}



}
