package com.portoseguro.projetointegrador.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.portoseguro.projetointegrador.model.Usuario;
import com.portoseguro.projetointegrador.model.UsuarioLogin;
import com.portoseguro.projetointegrador.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public ResponseEntity<Usuario> cadastrarUsuario(Usuario usuario) {
		String senhaEncoder = new BCryptPasswordEncoder().encode(usuario.getSenhaUsuario());
		usuario.setSenhaUsuario(senhaEncoder);

		return usuarioRepository.findByUsuario(usuario.getNomeUsuario())
			.map(resp -> {
				usuarioRepository.save(usuario);
				return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
			})
			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
			
	}
	
	public Optional<UsuarioLogin> logar(Optional<UsuarioLogin> user){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(user.get().getNomeUsuario());
		
		if(usuario.isPresent()) {
			if(encoder.matches(user.get().getSenhaUsuario(), usuario.get().getSenhaUsuario())) {
				String auth = user.get().getNomeUsuario() + ":" + user.get().getSenhaUsuario();
				byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic" + new String(encodeAuth);
				user.get().setToken(authHeader);
				user.get().setNomeUsuario(usuario.get().getNomeUsuario());
				
				return user;
			}
		}
		return null;
		
	}
	
}
