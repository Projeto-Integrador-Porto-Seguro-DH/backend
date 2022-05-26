package com.portoseguro.projetointegrador.service;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portoseguro.projetointegrador.dto.CadastroDTO;
import com.portoseguro.projetointegrador.dto.LoginDTO;
import com.portoseguro.projetointegrador.model.Usuario;
import com.portoseguro.projetointegrador.repository.UsuarioRepository;

@Service
@Transactional(readOnly = true)
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Optional<List<Usuario>> encontrarTodos() {
		List<Usuario> listaDeUsuarios = usuarioRepository.findAll();

		if (listaDeUsuarios.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(listaDeUsuarios);
	}

	public Optional<Usuario> encontrarPorID(Long id) {
		Optional<Usuario> usuarioPeloId = usuarioRepository.findById(id);

		return usuarioPeloId;
	}

	public Optional<Usuario> encontrarPorEmail(String emailUsuario) {
		Optional<Usuario> buscaPorEmail = usuarioRepository.findByEmailUsuarioIgnoreCase(emailUsuario);

		return buscaPorEmail;
	}

	public boolean verificarUsuarioExistente(Usuario usuario) {
		Optional<Usuario> usuarioNoBD = usuarioRepository.findByEmailUsuarioIgnoreCase(usuario.getEmailUsuario());

		if (usuarioNoBD.isPresent()) {
			return true;
		}

		return false;
	}
	
	public void verificarEmailValido(String emailUsuario) {
		final String REGEX_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

		if (!emailUsuario.matches(REGEX_EMAIL)) {
			throw new IllegalStateException("Email inválido!");
		}
	}
	
	public void verificarSenhaValida(String senhaUsuario) {
		final String REGEX_SENHA = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$";

		if (!senhaUsuario.matches(REGEX_SENHA)) {
			throw new IllegalStateException("Senha inválida. Ela deve conter no mínimo:\n"
					+ "\t- Entre 8 e 16 caracteres;\n"
					+ "\t- 1 caracter minúsculo;\n"
					+ "\t- 1 caracter maiúsculo;\n"
					+ "\t- 1 digito numérico.");
		}
	}

	@Transactional
	public Usuario cadastrarUsuario(CadastroDTO usuarioDto) {
		Usuario usuario = usuarioDto.criarUsuarioModel();
		
		verificarEmailValido(usuario.getEmailUsuario());
		
		if (verificarUsuarioExistente(usuario)) {
			throw new IllegalStateException("Usuário já existe!");
		}

		verificarSenhaValida(usuario.getSenhaUsuario());

		String senhaEncoder = new BCryptPasswordEncoder().encode(usuario.getSenhaUsuario());
		usuario.setSenhaUsuario(senhaEncoder);

		return usuarioRepository.save(usuario);
	}

	public Optional<LoginDTO> logar(Optional<LoginDTO> user) {
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
	
	@Transactional
	public Usuario atualizarUsuario(Usuario usuario) {

		if (!verificarUsuarioExistente(usuario)) {
			throw new IllegalStateException("Usuário não existe!");
		}

		return usuarioRepository.save(usuario);
	}

	@Transactional
	public void deletarUsuario(String emailUsuario) {
		Optional<Usuario> usuario = usuarioRepository.findByEmailUsuarioIgnoreCase(emailUsuario);
		Long idUsuario = usuario.get().getIdUsuario();

		if (usuario.isEmpty()) {
			throw new IllegalStateException("Usuario não existe!");
		}

		usuarioRepository.deleteById(idUsuario);
	}

}
