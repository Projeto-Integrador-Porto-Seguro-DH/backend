package com.portoseguro.projetointegrador.service;

import java.util.List;
import java.util.Optional;

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

	public Optional<List<Pedido>> encontrarPedidos() {
		List<Pedido> todosPedidos = pedidoRepository.findAll();

		if (todosPedidos.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(todosPedidos);
	}

	public Optional<Pedido> encontrarPorIdPedido(Long idPedido) {
		Optional<Pedido> pedidoPorId = pedidoRepository.findById(idPedido);

		return pedidoPorId;

	}

	public boolean verificarPedidoExistente(Pedido pedido) {
		Optional<Pedido> pedidoExistente = pedidoRepository.findById(pedido.getIdPedido());

		if (pedidoExistente.isPresent()) {
			return true;
		}

		return false;
	}

	@Transactional
	public Pedido cadastrarPedido(Pedido pedido) {
		pedido.setStatusPedido(StatusPedidoEnum.REALIZADO);

		return pedidoRepository.save(pedido);

	}

	@Transactional
	public Pedido atualizarPedido(Pedido pedido) {

		if (!verificarPedidoExistente(pedido)) {
			throw new IllegalStateException("Pedido " + pedido.getIdPedido() + " não existe!");
		}

		return pedidoRepository.save(pedido);

	}

	@Transactional
	public void deletarPedido(Long idPedido) {
		Optional<Pedido> pedidoPorId = pedidoRepository.findById(idPedido);

		if (pedidoPorId.isEmpty()) {
			throw new IllegalStateException("Pedido não existe!");
		}

		pedidoRepository.deleteById(idPedido);
	}

}
