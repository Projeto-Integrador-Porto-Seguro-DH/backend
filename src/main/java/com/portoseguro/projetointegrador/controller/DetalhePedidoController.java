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

import com.portoseguro.projetointegrador.model.DetalhePedido;
import com.portoseguro.projetointegrador.repository.DetalhePedidoRepository;

@RestController
@RequestMapping("/detalhepedido")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DetalhePedidoController {

	@Autowired
	private DetalhePedidoRepository detalhePedidoRepository;

	@GetMapping
	public ResponseEntity<List<DetalhePedido>> getAllByDetalhePedido() {
		return ResponseEntity.ok(detalhePedidoRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<DetalhePedido> getAllById(@PathVariable Long id) {
		return detalhePedidoRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/add")
	public ResponseEntity<DetalhePedido> postDetalhePedido(@RequestBody DetalhePedido detalhepedido) {
		return ResponseEntity.status(HttpStatus.CREATED).body(detalhePedidoRepository.save(detalhepedido));
	}
	
	@PostMapping("/lista")
	public ResponseEntity<List<DetalhePedido>> postListDetalhePedido(@RequestBody List<DetalhePedido> detalhepedido) {
		return ResponseEntity.status(HttpStatus.CREATED).body(detalhePedidoRepository.saveAll(detalhepedido));
	}

	@PutMapping("/atualizar")
	public ResponseEntity<DetalhePedido> putDetalhePedido(@RequestBody DetalhePedido detalhepedido) {
		return ResponseEntity.status(HttpStatus.OK).body(detalhePedidoRepository.save(detalhepedido));
	}

	@DeleteMapping("/deletar/{id}")
	public void deleteDetalhePedido(@PathVariable Long id) {
		detalhePedidoRepository.deleteById(id);
	}

}
