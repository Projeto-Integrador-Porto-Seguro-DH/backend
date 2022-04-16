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

import com.portoseguro.projetointegrador.model.Usuario;
import com.portoseguro.projetointegrador.repository.UsuarioRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@GetMapping
	public ResponseEntity<List<Usuario>> GetAllByUsuario() {
		return ResponseEntity.ok(usuarioRepository.findAll());
	}
	
	@GetMapping("/{idUsuario}")
	public ResponseEntity<Usuario> getById(@PathVariable long id) {
		return usuarioRepository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nome/{nomeUsuario}")
	public ResponseEntity<List<Usuario>> getByName(@PathVariable String nomeUsuario) {
		return ResponseEntity.ok(usuarioRepository.findAllByNomeUsuarioContainingIgnoreCase(nomeUsuario));
		
	}
	
	@PostMapping
	public ResponseEntity<Usuario> post(@RequestBody Usuario nomeUsuario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(nomeUsuario));
	}
	
	
	@PutMapping
	public ResponseEntity<Usuario> put(@RequestBody Usuario nomeUsuario) {
		return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.save(nomeUsuario));
	}
	
	@DeleteMapping("/{idUsuario}")
	public void delete(@PathVariable long id) {
		usuarioRepository.deleteById(id);
	}
		
}
