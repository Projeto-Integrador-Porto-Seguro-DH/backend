package com.portoseguro.projetointegrador.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portoseguro.projetointegrador.model.DetalhePedido;
import com.portoseguro.projetointegrador.model.Pedido;
import com.portoseguro.projetointegrador.repository.DetalhePedidoRepository;
import com.portoseguro.projetointegrador.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	public PedidoRepository pedidoRepository;

	@Autowired
	public DetalhePedidoRepository detalhePedidoRepository;

	public void calcularTotalPedido(Pedido pedido) {

		BigDecimal somaSubtotal = new BigDecimal(0);
		List<DetalhePedido> listaDeSubtotais = detalhePedidoRepository.findAllByPedido(pedido.getIdPedido());

		for (int i = 0; i < listaDeSubtotais.size(); i++) {
			somaSubtotal.add(listaDeSubtotais.get(i).getSubtotal());
		}
		pedido.setValorTotalPedido(somaSubtotal.add(pedido.getValorEnvio()));

	}

	@Transactional
	public Pedido cadastrarPedido(Pedido pedido) {
		List<DetalhePedido> listaDeDetalhePedido = detalhePedidoRepository.findAllByPedido(pedido.getIdPedido());

		if (listaDeDetalhePedido.size() > 0 && listaDeDetalhePedido != null) {
			calcularTotalPedido(pedido);
		}

		return pedidoRepository.save(pedido);
	
	}

}

