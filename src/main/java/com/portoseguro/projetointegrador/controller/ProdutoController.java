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

import com.portoseguro.projetointegrador.model.Produto;
import com.portoseguro.projetointegrador.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping
	public ResponseEntity<List<Produto>> encontrarProduto() {
		Optional<List<Produto>> todosProdutos = produtoService.encontrarProduto();

		if (todosProdutos.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(todosProdutos.get());
	}

	@GetMapping("/{idProduto}")
	public ResponseEntity<Produto> encontrarProdutoPorId(@PathVariable Long idProduto) {
		Optional<Produto> produtoPorId = produtoService.encontrarProdutoPorId(idProduto);

		if (produtoPorId.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(produtoPorId.get());

	}

	@GetMapping("/nome/{nomeProduto}")
	public ResponseEntity<List<Produto>> getByNomeProdutos(@PathVariable String nomeProduto) {
		Optional<List<Produto>> buscaPorNome = produtoService.encontrarProdutosPorNome(nomeProduto);

		if (buscaPorNome.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(buscaPorNome.get());

	}

	@PostMapping("/add")
	public ResponseEntity<Produto> postProduto(@RequestBody Produto produto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.cadastrarProduto(produto));
	}

	@PutMapping("/update")
	public ResponseEntity<Produto> putProdutos(@RequestBody @Valid Produto produtos) {
		return ResponseEntity.ok(produtoService.atualizarProduto(produtos));

	}

	@DeleteMapping("delete/{idProduto}")
	public ResponseEntity<Object> deleteProduto(@PathVariable Long idProduto) {
		produtoService.deletarProduto(idProduto);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
