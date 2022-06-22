package com.portoseguro.projetointegrador.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.portoseguro.projetointegrador.enums.StatusPedidoEnum;
import com.portoseguro.projetointegrador.model.DetalhePedido;
import com.portoseguro.projetointegrador.model.Pedido;
import com.portoseguro.projetointegrador.model.Usuario;
import com.portoseguro.projetointegrador.repository.PedidoRepository;
import com.portoseguro.projetointegrador.repository.UsuarioRepository;

@Service
@Transactional(readOnly = true)
public class PedidoService {

	@Autowired
	public PedidoRepository pedidoRepository;

	@Autowired
	public UsuarioRepository usuarioRepository;
	
	@Autowired
	public DetalhePedidoService detalhePedidoService;
	

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
	public Pedido cadastrarPedido(long idUsuario, Pedido pedido, List<DetalhePedido> detalhes) {

		pedido.setStatusPedido(StatusPedidoEnum.REALIZADO);
		
		Usuario usuario = buscarUsuario(idUsuario).get();

		pedido.setUsuario(usuario);
		
		Pedido pedidoSalvo = pedidoRepository.save(pedido);
		
		List<DetalhePedido> listaDetalhesSalvos = new ArrayList<DetalhePedido>();

		for (int i = 0; i < detalhes.size(); i++) {
			DetalhePedido detalheSalvo = detalhePedidoService.cadastarDetalhes(
					detalhes.get(i),
					pedidoSalvo.getIdPedido());
			listaDetalhesSalvos.add(detalheSalvo);
		}
		
		pedidoSalvo.setDetalhePedido(listaDetalhesSalvos);

		return pedidoRepository.save(pedidoSalvo);

	}

	@Transactional
	public Pedido atualizarStatusPedido(Pedido pedido) {

		if (!verificarPedidoExistente(pedido)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido " + pedido.getIdPedido() + " não existe!");
		}

		Long idDoPedido = pedido.getIdPedido();

		Optional<Pedido> pedidoNoBD = pedidoRepository.findById(idDoPedido);

		if (pedidoNoBD.get().getStatusPedido() != pedido.getStatusPedido()) {
			pedidoNoBD.get().setStatusPedido(pedido.getStatusPedido());
		}

		if (pedido.getStatusPedido() == StatusPedidoEnum.CANCELADO) {
			List<DetalhePedido> listaDeDetalhes = pedidoNoBD.get().getDetalhePedido();
			
			for (int i = 0; i < listaDeDetalhes.size(); i++) {
				detalhePedidoService.reporEstoquePedidoCancelado(listaDeDetalhes.get(i));
			}
		}

		return pedidoRepository.save(pedidoNoBD.get());

	}

	public Optional<Usuario> buscarUsuario(long idUsuario) {
		return usuarioRepository.findById(idUsuario);
	}

}
