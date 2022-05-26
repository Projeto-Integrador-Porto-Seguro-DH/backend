package com.portoseguro.projetointegrador.service;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

	@Transactional
	public Usuario cadastrarUsuario(CadastroDTO usuarioDto) {
		Usuario usuario = usuarioDto.criarUsuarioModel();

		verificarEmailValido(usuario.getEmailUsuario());

		if (verificarUsuarioExistente(usuario).isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O email já está cadastrado.");
		}

		verificarSenhaValida(usuario.getSenhaUsuario());

		usuario.setSenhaUsuario(criptografarSenha(usuario.getSenhaUsuario()));

		return usuarioRepository.save(usuario);
	}

	public Optional<LoginDTO> logar(Optional<LoginDTO> usuarioLogin) {
		Optional<Usuario> usuarioNoBD = usuarioRepository
				.findByEmailUsuarioIgnoreCase(usuarioLogin.get().getEmailUsuario());

		String senhaDigitada = usuarioLogin.get().getSenhaUsuario();
		String senhaSalvaNoBD = usuarioNoBD.get().getSenhaUsuario();

		if (usuarioNoBD.isPresent()) {
			if (compararSenhas(senhaDigitada, senhaSalvaNoBD)) {

				usuarioLogin.get().setToken(
						gerarBasicToken(usuarioLogin.get().getEmailUsuario(), usuarioLogin.get().getSenhaUsuario()));

				usuarioLogin.get().setIdUsuario(usuarioNoBD.get().getIdUsuario());
				usuarioLogin.get().setNomeUsuario(usuarioNoBD.get().getNomeUsuario());
				usuarioLogin.get().setSobrenomeUsuario(usuarioNoBD.get().getSobrenomeUsuario());

				return usuarioLogin;
			}
		}

		return null;
	}

	@Transactional
	public Usuario atualizarUsuario(Usuario usuario) {
		if (verificarUsuarioExistente(usuario).isEmpty()) {

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.");

		} else {
			if (usuario.getSenhaUsuario() != null && !usuario.getSenhaUsuario().isBlank()) {

				String senhaNova = usuario.getSenhaUsuario();
				String senhaAntiga = verificarUsuarioExistente(usuario).get().getSenhaUsuario();

				if (compararSenhas(senhaNova, senhaAntiga)) {

					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"A nova senha deve ser diferente da antiga.");

				} else {

					verificarSenhaValida(usuario.getSenhaUsuario());

					usuario.setSenhaUsuario(criptografarSenha(senhaNova));
				}
			}

			return usuarioRepository.save(usuario);
		}
	}

	@Transactional
	public void deletarUsuario(String emailUsuario) {
		Optional<Usuario> usuario = usuarioRepository.findByEmailUsuarioIgnoreCase(emailUsuario);

		if (usuario.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.");
		}

		usuarioRepository.deleteById(usuario.get().getIdUsuario());
	}

	public Optional<Usuario> verificarUsuarioExistente(Usuario usuario) {
		Optional<Usuario> usuarioNoBD = usuarioRepository.findByEmailUsuarioIgnoreCase(usuario.getEmailUsuario());

		if (usuarioNoBD.isPresent()) {
			return usuarioNoBD;
		}

		return Optional.empty();
	}

	public void verificarEmailValido(String emailUsuario) {
		final String REGEX_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

		if (!emailUsuario.matches(REGEX_EMAIL)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email inválido!");
		}
	}

	public void verificarSenhaValida(String senhaUsuario) {
		final String REGEX_SENHA = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$";

		if (!senhaUsuario.matches(REGEX_SENHA)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Senha inválida. Ela deve conter: Entre 8 e 16 caracteres;" + " No mínimo 1 caracter minúsculo;"
							+ " No mínimo 1 caracter maiúsculo;" + " No mínimo 1 dígito numérico.");
		}
	}

	public boolean compararSenhas(String senhaDigitada, String senhaSalvaNoBD) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.matches(senhaDigitada, senhaSalvaNoBD);
	}

	private String criptografarSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.encode(senha);
	}

	public String gerarBasicToken(String usuario, String senha) {
		String token = usuario + ":" + senha;

		byte[] encodeAuth = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));

		return "Basic " + new String(encodeAuth);
	}

}
