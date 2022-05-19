package com.portoseguro.projetointegrador.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.portoseguro.projetointegrador.model.Usuario;
import com.portoseguro.projetointegrador.model.UsuarioLogin;
import com.portoseguro.projetointegrador.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	private static final String REGEX_SENHA = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$";

	public Usuario cadastrarUsuario(Usuario usuario) {
		Optional<Usuario> user = usuarioRepository.findByNomeUsuarioIgnoreCase(usuario.getNomeUsuario());
		if (user.isPresent()) {
			throw new IllegalStateException("Usuário já existente");
		}

		if (usuario.getSenhaUsuario().matches(REGEX_SENHA)) {
			String senhaEncoder = new BCryptPasswordEncoder().encode(usuario.getSenhaUsuario());
			usuario.setSenhaUsuario(senhaEncoder);
		} else {
			throw new IllegalStateException("Senha inválida. Verifique os parâmetros.");
		}

		return usuarioRepository.save(usuario);
	}

	public Optional<UsuarioLogin> logar(Optional<UsuarioLogin> user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = usuarioRepository.findByNomeUsuarioIgnoreCase(user.get().getNomeUsuario());

		if (usuario.isPresent()) {
			if (encoder.matches(user.get().getSenhaUsuario(), usuario.get().getSenhaUsuario())) {
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
