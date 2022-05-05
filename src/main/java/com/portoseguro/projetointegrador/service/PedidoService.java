package com.portoseguro.projetointegrador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portoseguro.projetointegrador.model.Pedido;
import com.portoseguro.projetointegrador.repository.DetalhePedidoRepository;
import com.portoseguro.projetointegrador.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	public PedidoRepository pedidoRepository;

	@Autowired
	public DetalhePedidoRepository detalhePedidoRepository;

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
		return pedidoRepository.save(pedido);
	}

}
