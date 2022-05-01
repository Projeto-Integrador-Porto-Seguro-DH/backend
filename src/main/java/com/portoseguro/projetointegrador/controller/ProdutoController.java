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
import com.portoseguro.projetointegrador.repository.CategoriaRepository;
import com.portoseguro.projetointegrador.repository.ProdutosRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired
	private ProdutosRepository produtosRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<Produtos>> getAllProduto() {
		return ResponseEntity.ok(produtosRepository.findAll());
	}

	@GetMapping("/{idProduto}")
	public ResponseEntity<Produtos> getAllById(@PathVariable Long idProduto) {
		return produtosRepository.findById(idProduto).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nome/{nomeProduto}")
	public ResponseEntity<List<Produtos>> getByNomeProdutos(@PathVariable String nomeProduto) {
		return ResponseEntity.ok(produtosRepository.findAllByNomeProdutoContainingIgnoreCase(nomeProduto));
	}

	@PostMapping("/add")
	public ResponseEntity<Produtos> postProdutos(@RequestBody Produtos produtos) {
		return categoriaRepository.findById(produtos.getCategoria().getIdCategoria())
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(produtosRepository.save(produtos)))
				.orElse(ResponseEntity.notFound().build());
	}

	/*
	 * IMPLEMENTADO APENAS PARA FACILITAR OS TESTES NO POSTMAN
	 */
	@PostMapping("/list")
	public ResponseEntity<List<Produtos>> postListaProdutos(@RequestBody List<Produtos> produtos) {
		return ResponseEntity.status(HttpStatus.CREATED).body(produtosRepository.saveAll(produtos));
	}

	@PutMapping("/update")
	public ResponseEntity<Produtos> putProdutos(@RequestBody Produtos produtos) {
		if (produtosRepository.existsById(produtos.getIdProduto())) {
			if (produtosRepository.existsById(produtos.getCategoria().getIdCategoria()))
				return ResponseEntity.status(HttpStatus.OK).body(produtosRepository.save(produtos));
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();

	}

	@DeleteMapping("delete/{idProduto}")
	public ResponseEntity<Object> deleteProdutos(@PathVariable Long idProduto) {
		return produtosRepository.findById(idProduto).map(resposta -> {
			produtosRepository.deleteById(idProduto);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}).orElse(ResponseEntity.notFound().build());
	}

}
