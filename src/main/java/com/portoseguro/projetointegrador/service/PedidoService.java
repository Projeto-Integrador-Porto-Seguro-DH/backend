package com.portoseguro.projetointegrador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portoseguro.projetointegrador.enums.StatusPedidoEnum;
import com.portoseguro.projetointegrador.model.Pedido;
import com.portoseguro.projetointegrador.repository.PedidoRepository;

@Service
@Transactional(readOnly = true)
public class PedidoService {

	@Autowired
	public PedidoRepository pedidoRepository;

	@Transactional
	public Pedido cadastrarPedido(Pedido pedido) {
		pedido.setStatusPedido(StatusPedidoEnum.REALIZADO);
		
		return pedidoRepository.save(pedido);
	}

}
