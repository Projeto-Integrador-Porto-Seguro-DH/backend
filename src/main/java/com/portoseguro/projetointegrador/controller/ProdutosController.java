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

import com.portoseguro.projetointegrador.model.Produtos;
import com.portoseguro.projetointegrador.repository.ProdutosRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutosController {

	@Autowired
	private ProdutosRepository produtosRepository;

	@GetMapping
	public ResponseEntity<List<Produtos>> getAll() {
		return ResponseEntity.ok(produtosRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produtos> GetById(@PathVariable long id) {
		return produtosRepository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nome/{nomeProduto}")
	public ResponseEntity<List<Produtos>> GetByNomeProduto(@PathVariable String nomeProduto) {
		return ResponseEntity.ok(produtosRepository.findAllByNomeProdutoContainingIgnoreCase(nomeProduto));
	}

	@PostMapping
	public ResponseEntity<Produtos> postProdutos(@RequestBody Produtos produtos) {
		return ResponseEntity.status(HttpStatus.CREATED).body(produtosRepository.save(produtos));
	}
	
	@PostMapping("/lista")
	public ResponseEntity<List<Produtos>> postListaProdutos(@RequestBody List<Produtos> produtos) {
		return ResponseEntity.status(HttpStatus.CREATED).body(produtosRepository.saveAll(produtos));
	}

	@PutMapping("/atualizar")
	public ResponseEntity<Produtos> putProdutos(@RequestBody Produtos produtos) {
		return ResponseEntity.status(HttpStatus.OK).body(produtosRepository.save(produtos));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		produtosRepository.deleteById(id);
	}

}
