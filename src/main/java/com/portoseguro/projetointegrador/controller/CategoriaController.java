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

import com.portoseguro.projetointegrador.model.Categoria;
import com.portoseguro.projetointegrador.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping
	public ResponseEntity<List<Categoria>> getAllCategoria() {
		Optional<List<Categoria>> todasCategorias = categoriaService.encontrarTodos();

		if (todasCategorias.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(todasCategorias.get());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getAllById(@PathVariable Long id) {
		Optional<Categoria> categoria = categoriaService.encontrarPorID(id);

		if (categoria.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(categoria.get());
	}

	@GetMapping("/nome/{nomeCategoria}")
	public ResponseEntity<List<Categoria>> getByNome(@PathVariable String nomeCategoria) {
		Optional<List<Categoria>> buscaPorNome = categoriaService.encontrarTodosPorNome(nomeCategoria);

		if (buscaPorNome.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(buscaPorNome.get());
	}

	@PostMapping("/add")
	public ResponseEntity<Categoria> postCategoria(@RequestBody @Valid Categoria categoria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.cadastrarCategoria(categoria));
	}

	@PutMapping("/update")
	public ResponseEntity<Categoria> putCategoria(@RequestBody @Valid Categoria categoria) {
		return ResponseEntity.ok(categoriaService.atualizarCategoria(categoria));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteCategoria(@PathVariable Long id) {
		categoriaService.deletarCategoria(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
