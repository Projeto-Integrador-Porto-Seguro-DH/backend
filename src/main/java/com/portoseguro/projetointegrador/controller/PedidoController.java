package com.portoseguro.projetointegrador.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.portoseguro.projetointegrador.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@GetMapping
	public ResponseEntity<List<Pedido>> getAllPedido() {
		Optional<List<Pedido>> todosPedidos = pedidoService.encontraTodosPedidos();
		
		if (todosPedidos.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			
		}
		
		return ResponseEntity.ok(todosPedidos.get());
	}

	@GetMapping("/{idPedido}")
	public ResponseEntity<Pedido> getAllById(@PathVariable Long idPedido) {
	return pedidoRepository.findById(idPedido)
				.map(resposta -> ResponseEntity.ok().body(pedidoRepository.getById(idPedido)))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/add")
	public ResponseEntity<Pedido> postPedido(@RequestBody Pedido pedido) {
		return usuarioRepository.findById(pedido.getUsuario().getIdUsuario())
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.cadastrarPedido(pedido)))
				.orElse(ResponseEntity.notFound().build());
	}

	/*
	 * IMPLEMENTADO APENAS PARA FACILITAR OS TESTES NO POSTMAN
	 */
	@PostMapping("/list")
<<<<<<< Updated upstream
	public ResponseEntity<List<Pedido>> postListaPedido(@RequestBody List<Pedido> pedido) {
		return ResponseEntity.status(HttpStatus.CREATED).body(pedidoRepository.saveAll(pedido));
=======
	public ResponseEntity<List<Pedido>> postListaPedido(@RequestBody @Valid List<Pedido> pedido) {
		return new ResponseEntity<List<Pedido>>(pedidoRepository.saveAll(pedido), HttpStatus.CREATED);
>>>>>>> Stashed changes
	}

	@PutMapping("/update")
	public ResponseEntity<Pedido> putPedido(@RequestBody @Valid Pedido pedido) {
		return pedidoRepository.findById(pedido.getIdPedido())
				.map(resposta -> ResponseEntity.ok().body(pedidoRepository.save(pedido)))
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("delete/{idPedido}")
	public ResponseEntity<Object> deletePedido(@PathVariable Long idPedido) {
		return pedidoRepository.findById(idPedido).map(resposta -> {
			pedidoRepository.deleteById(idPedido);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}).orElse(ResponseEntity.notFound().build());
	}

}
