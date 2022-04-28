package com.portoseguro.projetointegrador.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portoseguro.projetointegrador.model.Categoria;
import com.portoseguro.projetointegrador.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<Categoria>> getAllCategoria() {
		return ResponseEntity.ok(categoriaRepository.findAll());
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<Categoria> getById(@PathVariable Long id) {
		return categoriaRepository.findById(id)
				.map(resposta -> ResponseEntity.ok().body(categoriaRepository.getById(id)))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nome/{nomeCategoria}")
	public ResponseEntity<Optional<Categoria>> getByNome(@PathVariable String nomeCategoria) {
		return categoriaRepository.findByNomeCategoriaIgnoreCase(nomeCategoria)
				.map(resposta -> ResponseEntity.ok().body(categoriaRepository.findByNomeCategoriaIgnoreCase(nomeCategoria)))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Categoria> postCategoria(@RequestBody @Valid Categoria categoria) {
		return new ResponseEntity<Categoria>(categoriaRepository.save(categoria), HttpStatus.CREATED);
	}
	
	@PostMapping("/lista")
	public ResponseEntity<List<Categoria>> postListaCategoria(@RequestBody @Valid List<Categoria> categoria) {
		return new ResponseEntity<List<Categoria>>(categoriaRepository.saveAll(categoria), HttpStatus.CREATED);
	}

	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Object> deleteCategoria(@PathVariable Long id) {
		return categoriaRepository.findById(id).map(resposta -> {
			categoriaRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		}).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/atualizar")
	public ResponseEntity<Categoria> putCategoria(@RequestBody @Valid Categoria categoria) {
		return categoriaRepository.findById(categoria.getIdCategoria())
				.map(resposta -> ResponseEntity.ok().body(categoriaRepository.save(categoria)))
				.orElse(ResponseEntity.notFound().build());
	}
}
