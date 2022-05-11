package com.portoseguro.projetointegrador.controller;

import java.util.List;
import java.util.Optional;

import com.portoseguro.projetointegrador.model.Usuario;
import com.portoseguro.projetointegrador.repository.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portoseguro.projetointegrador.model.Pedido;
import com.portoseguro.projetointegrador.repository.PedidoRepository;
import com.portoseguro.projetointegrador.repository.UsuarioRepository;
import com.portoseguro.projetointegrador.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PedidoController {

	@Autowired
	private ProdutosRepository produtosRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PedidoService pedidoService;

	@GetMapping
	public ResponseEntity<List<Pedido>> getAllPedido() {
		return ResponseEntity.ok(pedidoRepository.findAll());
	}

	@GetMapping("/{idPedido}")
	public ResponseEntity<Pedido> getAllById(@PathVariable Long idPedido) {
		return pedidoRepository.findById(idPedido).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/add")
	@Transactional
	public ResponseEntity<Pedido> postPedido(@RequestBody Pedido pedido) {

		Optional<Usuario> usuario = usuarioRepository.findById(pedido.getUsuario().getIdUsuario());

		if (usuario.isPresent()){

			Pedido pedidoEntity = this.pedidoService.cadastrarPedido(pedido);

			return ResponseEntity.status(HttpStatus.OK).body(pedidoEntity);
		}

		return ResponseEntity.notFound().build();
	}

	/*
	 * IMPLEMENTADO APENAS PARA FACILITAR OS TESTES NO POSTMAN
	 */
	@PostMapping("/list")
	public ResponseEntity<List<Pedido>> postListaPedido(@RequestBody List<Pedido> pedido) {
		return ResponseEntity.status(HttpStatus.CREATED).body(pedidoRepository.saveAll(pedido));
	}

	@PutMapping("/update")
	public ResponseEntity<Pedido> putPedido(@RequestBody Pedido pedido) {
		if (pedidoRepository.existsById(pedido.getIdPedido())) {
			if (usuarioRepository.existsById(pedido.getUsuario().getIdUsuario()))
				return ResponseEntity.status(HttpStatus.OK).body(pedidoRepository.save(pedido));
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("delete/{idPedido}")
	public ResponseEntity<Object> deletePedido(@PathVariable Long idPedido) {

		Boolean deletedPedido = this.pedidoService.deletePedido(idPedido);
		if (deletedPedido = true) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

}
