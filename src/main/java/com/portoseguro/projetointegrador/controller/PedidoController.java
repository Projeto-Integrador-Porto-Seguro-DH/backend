package com.portoseguro.projetointegrador.controller;

import java.util.List;

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

import com.portoseguro.projetointegrador.repository.PedidoRepository;
import com.portoseguro.projetointegrador.model.Pedido;

@RestController
@RequestMapping("/pedido")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;

	@GetMapping
	public ResponseEntity<List<Pedido>> getAllDetalhePedido() {
		return ResponseEntity.ok(pedidoRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pedido> getById(@PathVariable Long id) {
		return pedidoRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Pedido> post(@RequestBody Pedido pedido) {
		return ResponseEntity.status(HttpStatus.CREATED).body(pedidoRepository.save(pedido));
	}

	@PutMapping
	public ResponseEntity<Pedido> put(@RequestBody Pedido pedido) {
		return ResponseEntity.status(HttpStatus.OK).body(pedidoRepository.save(pedido));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		pedidoRepository.deleteById(id);
	}

}
