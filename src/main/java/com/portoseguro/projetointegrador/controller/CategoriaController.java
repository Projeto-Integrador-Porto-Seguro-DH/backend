package com.portoseguro.projetointegrador.controller;

import java.util.List;

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

import com.portoseguro.projetointegrador.model.Categoria;
import com.portoseguro.projetointegrador.repository.CategoriaRepository;
import com.portoseguro.projetointegrador.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private CategoriaService categoriaService;

	@GetMapping
	public ResponseEntity<List<Categoria>> getAllCategoria() {
		return ResponseEntity.ok(categoriaRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getAllById(@PathVariable Long id) {
		return categoriaRepository.findById(id)
				.map(resposta -> ResponseEntity.ok().body(categoriaRepository.getById(id)))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nome/{nomeCategoria}")
	public ResponseEntity<List<Categoria>> getByNome(@PathVariable String nomeCategoria) {
		List<Categoria> buscaPorNome = categoriaRepository.findAllByNomeCategoriaContainingIgnoreCase(nomeCategoria);
		
		if(buscaPorNome.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.ok(buscaPorNome);
	}

	@PostMapping("/add")
	public ResponseEntity<Categoria> postCategoria(@RequestBody @Valid Categoria categoria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.cadastrarCategoria(categoria));
	}

	/*
	 * IMPLEMENTADO APENAS PARA FACILITAR OS TESTES NO POSTMAN
	 */
	@PostMapping("/list")
	public ResponseEntity<List<Categoria>> postListaCategoria(@RequestBody @Valid List<Categoria> categoria) {
		return new ResponseEntity<List<Categoria>>(categoriaRepository.saveAll(categoria), HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Categoria> putCategoria(@RequestBody @Valid Categoria categoria) {
		return categoriaRepository.findById(categoria.getIdCategoria())
				.map(resposta -> ResponseEntity.ok().body(categoriaRepository.save(categoria)))
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteCategoria(@PathVariable Long id) {
		return categoriaRepository.findById(id).map(resposta -> {
			categoriaRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}).orElse(ResponseEntity.notFound().build());
	}

}
