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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portoseguro.projetointegrador.dto.CadastroDTO;
import com.portoseguro.projetointegrador.dto.LoginDTO;
import com.portoseguro.projetointegrador.model.Usuario;
import com.portoseguro.projetointegrador.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<List<Usuario>> getAllUsuario() {
		Optional<List<Usuario>> todosUsuarios = usuarioService.encontrarTodos();

		if (todosUsuarios.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(todosUsuarios.get());
	}

	@GetMapping("/{idUsuario}")
	public ResponseEntity<Usuario> getById(@PathVariable Long idUsuario) {
		Optional<Usuario> usuario = usuarioService.encontrarPorID(idUsuario);

		if (usuario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(usuario.get());
	}

	@GetMapping("/email/")
	public ResponseEntity<Usuario> getByEmailUsuario(@RequestParam String email) {
		Optional<Usuario> usuario = usuarioService.encontrarPorEmail(email);

		if (usuario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(usuario.get());
	}

	@PostMapping("/login")
	public ResponseEntity<LoginDTO> login(@RequestBody Optional<LoginDTO> usuarioLogin) {
		return usuarioService.logar(usuarioLogin).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody CadastroDTO usuario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarUsuario(usuario));
	}

	@PutMapping("/update")
	public ResponseEntity<Usuario> putUsuario(@RequestBody Usuario usuario) {
		return ResponseEntity.ok(usuarioService.atualizarUsuario(usuario));
	}

	@DeleteMapping("/delete/{emailUsuario}")
	public ResponseEntity<Usuario> deleteUsuario(@PathVariable String emailUsuario) {
		usuarioService.deletarUsuario(emailUsuario);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
