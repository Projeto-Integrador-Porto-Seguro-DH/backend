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
import com.portoseguro.projetointegrador.repository.PedidoRepository;
import com.portoseguro.projetointegrador.repository.ProdutosRepository;

@RestController
@RequestMapping("/detalhepedido")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DetalhePedidoController {

	@Autowired
	private DetalhePedidoRepository detalhePedidoRepository;

	@Autowired
	private ProdutosRepository produtosRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@GetMapping
	public ResponseEntity<List<DetalhePedido>> getAllDetalhePedido() {
		return ResponseEntity.ok(detalhePedidoRepository.findAll());
	}

	@GetMapping("/{idDetalhePedido}")
	public ResponseEntity<DetalhePedido> getAllById(@PathVariable Long idDetalhePedido) {
		return detalhePedidoRepository.findById(idDetalhePedido).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/add")
	public ResponseEntity<DetalhePedido> postDetalhePedido(@RequestBody DetalhePedido detalhePedido) {
		if (produtosRepository.existsById(detalhePedido.getProdutos().getIdProduto()))
			if (pedidoRepository.existsById(detalhePedido.getPedido().getIdPedido()))
				return ResponseEntity.status(HttpStatus.CREATED).body(detalhePedidoRepository.save(detalhePedido));

		return ResponseEntity.notFound().build();
	}

	/*
	 * IMPLEMENTADO APENAS PARA FACILITAR OS TESTES NO POSTMAN
	 */
	@PostMapping("/list")
	public ResponseEntity<List<DetalhePedido>> postListDetalhePedido(@RequestBody List<DetalhePedido> detalhePedido) {
		return ResponseEntity.status(HttpStatus.CREATED).body(detalhePedidoRepository.saveAll(detalhePedido));
	}

	@PutMapping("/update")
	public ResponseEntity<DetalhePedido> putDetalhePedido(@RequestBody DetalhePedido detalhePedido) {
		if (detalhePedidoRepository.existsById(detalhePedido.getIdDetalhePedido())) {
			if (produtosRepository.existsById(detalhePedido.getProdutos().getIdProduto()))
				if (pedidoRepository.existsById(detalhePedido.getPedido().getIdPedido()))
					return ResponseEntity.status(HttpStatus.OK).body(detalhePedidoRepository.save(detalhePedido));
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/delete/{idDetalhePedido}")
	public ResponseEntity<Object> deleteDetalhePedido(@PathVariable Long idDetalhePedido) {
		return detalhePedidoRepository.findById(idDetalhePedido).map(resposta -> {
			detalhePedidoRepository.deleteById(idDetalhePedido);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}).orElse(ResponseEntity.notFound().build());
	}

}
