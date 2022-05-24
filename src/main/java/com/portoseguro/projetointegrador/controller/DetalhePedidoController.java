package com.portoseguro.projetointegrador.controller;

import java.util.List;
import java.util.Optional;

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
import com.portoseguro.projetointegrador.service.DetalhePedidoService;

@RestController
@RequestMapping("/detalhepedido")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DetalhePedidoController {

	@Autowired
	private DetalhePedidoService detalhePedidoService;

	@GetMapping
	public ResponseEntity<List<DetalhePedido>> getAllDetalhePedido() {
		Optional<List<DetalhePedido>> todosDetalhes = detalhePedidoService.encontrarTodos();

		if (todosDetalhes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(todosDetalhes.get());
	}

	@GetMapping("/{idDetalhePedido}")
	public ResponseEntity<DetalhePedido> getAllById(@PathVariable Long idDetalhePedido) {
		Optional<DetalhePedido> detalhePeloID = detalhePedidoService.encontrarPorId(idDetalhePedido);

		if (detalhePeloID.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(detalhePeloID.get());
	}

	@PostMapping("/add")
	public ResponseEntity<DetalhePedido> postDetalhePedido(@RequestBody DetalhePedido detalhePedido) {
		return ResponseEntity.status(HttpStatus.CREATED).body(detalhePedidoService.cadastarDetalhes(detalhePedido));
	}

	@PutMapping("/update")
	public ResponseEntity<DetalhePedido> putDetalhePedido(@RequestBody DetalhePedido detalhePedido) {
		return ResponseEntity.ok(detalhePedidoService.atualizarDetalhePedido(detalhePedido));
	}

	@DeleteMapping("/delete/{idDetalhePedido}")
	public ResponseEntity<Object> deleteDetalhePedido(@PathVariable Long idDetalhePedido) {
		detalhePedidoService.deletarDetalhe(idDetalhePedido);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
