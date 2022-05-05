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
import org.springframework.web.bind.annotation.RestController;

import com.portoseguro.projetointegrador.model.Usuario;
import com.portoseguro.projetointegrador.model.UsuarioLogin;
import com.portoseguro.projetointegrador.repository.UsuarioRepository;
import com.portoseguro.projetointegrador.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<List<Usuario>> getAllUsuario() {
		return ResponseEntity.ok(usuarioRepository.findAll());
	}

	@GetMapping("/{idUsuario}")
	public ResponseEntity<Usuario> getAllById(@PathVariable Long idUsuario) {
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

	/*
	 * APÓS IMPLEMENTAÇÃO DA SECURITY SERÁ CRIADO O MÉTODO LOGAR, ONDE SERÃO
	 * AUTENTICADOS O LOGIN E SENHA DO USUÁRO
	 *
	 * public ResponseEntity<UsuarioLogin> login(@RequestBody Optional<UsuarioLogin>
	 * usuarioLogin) { return }
	 */

	@PostMapping("/add")
	public ResponseEntity<Usuario> postUsuario(@RequestBody Usuario nomeUsuario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(nomeUsuario));
	}

	@PostMapping("/list/")
	public ResponseEntity<List<Usuario>> postListaUsuario(@RequestBody List<Usuario> nomeUsuario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.saveAll(nomeUsuario));
	}

	@PostMapping("/login")
	public ResponseEntity<UsuarioLogin> login(@RequestBody Optional<UsuarioLogin> user) {
		return usuarioService.logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
		return ResponseEntity.ok(usuarioService.cadastrarUsuario(usuario));
	}

	@PutMapping("/update")
	public ResponseEntity<Usuario> putUsuario(@RequestBody Usuario usuario) {
		return usuarioRepository.findById(usuario.getIdUsuario())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.save(usuario)))
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/delete/{idUsuario}")
	public ResponseEntity<Object> deleteUsuario(@PathVariable Long idUsuario) {
		return usuarioRepository.findById(idUsuario).map(resposta -> {
			usuarioRepository.deleteById(idUsuario);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}).orElse(ResponseEntity.notFound().build());
	}

}
