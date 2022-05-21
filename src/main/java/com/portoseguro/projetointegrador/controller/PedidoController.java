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
	public ResponseEntity<List<Pedido>> encontrarPedidos() {
		Optional<List<Pedido>> todosPedidos = pedidoService.encontrarPedidos();

		if (todosPedidos.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		}

		return ResponseEntity.ok(todosPedidos.get());
	}

	@GetMapping("/{idPedido}")
	public ResponseEntity<Pedido> encontrarIdPedido(@PathVariable Long idPedido) {
		Optional<Pedido> pedidoPorId = pedidoService.encontrarPorIdPedido(idPedido);

		if (pedidoPorId.get() == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(pedidoPorId.get());

	}

	@PostMapping("/add")
	public ResponseEntity<Pedido> postPedido(@RequestBody @Valid Pedido pedido) {
		return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.cadastrarPedido(pedido));
	}

	@PutMapping("/update")
	public ResponseEntity<Pedido> putPedido(@RequestBody @Valid Pedido pedido) {
		return ResponseEntity.ok(pedidoService.atualizarPedido(pedido));
	}

	@DeleteMapping("delete/{idPedido}")
	public ResponseEntity<Object> deletePedido(@PathVariable Long idPedido) {
		pedidoService.deletarPedido(idPedido);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
