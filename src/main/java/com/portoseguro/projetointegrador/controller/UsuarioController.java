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
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping
	public ResponseEntity<List<Usuario>> GetAllByUsuario() {
		return ResponseEntity.ok(usuarioRepository.findAll());
	}

	@GetMapping("/{idUsuario}")
	public ResponseEntity<Usuario> getAllById(@PathVariable long idUsuario) {
		return usuarioRepository.findById(idUsuario).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nome/{nomeUsuario}")
	public ResponseEntity<List<Usuario>> getByNomeUsuario(@PathVariable String nomeUsuario) {
		return ResponseEntity.ok(usuarioRepository.findAllByNomeUsuarioContainingIgnoreCase(nomeUsuario));
	}
	
	@GetMapping("/cpf/{cpfUsuario}")
	public ResponseEntity<List<Usuario>> getByCpfUsuario(@PathVariable String cpfUsuario) {
		return ResponseEntity.ok(usuarioRepository.findAllByCpfUsuarioContainingIgnoreCase(cpfUsuario));
	}
	
	@GetMapping("/endereco/{enderecoUsuario}")
	public ResponseEntity<List<Usuario>> getByEnderecoUsuario(@PathVariable String enderecoUsuario) {
		return ResponseEntity.ok(usuarioRepository.findAllByEnderecoUsuarioContainingIgnoreCase(enderecoUsuario));
	}
	
	@GetMapping("/email/{emailUsuario}")
	public ResponseEntity<List<Usuario>> getByEmailUsuario(@PathVariable String emailUsuario) {
		return ResponseEntity.ok(usuarioRepository.findAllByEmailUsuarioContainingIgnoreCase(emailUsuario));
	}

	@PostMapping("/add")
	public ResponseEntity<Usuario> postUsuario(@RequestBody Usuario nomeUsuario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(nomeUsuario));
	}
	
	@PostMapping("/lista/")
	public ResponseEntity<List<Usuario>> postListaUsuario(@RequestBody List<Usuario> nomeUsuario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.saveAll(nomeUsuario));
	}

	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> putUsuario(@RequestBody Usuario nomeUsuario) {
		return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.save(nomeUsuario));
	}

	@DeleteMapping("/deletar/{idUsuario}")
	public void deleteUsuario(@PathVariable long idUsuario) {
		usuarioRepository.deleteById(idUsuario);
	}

}
