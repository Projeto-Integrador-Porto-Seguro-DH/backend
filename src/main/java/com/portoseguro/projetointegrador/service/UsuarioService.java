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
		
		usuario.setAdmin(false);

		return usuarioRepository.save(usuario);
	}

	public Optional<LoginDTO> logar(LoginDTO usuarioLogin) {
		Optional<Usuario> usuarioNoBD = usuarioRepository
				.findByEmailUsuarioIgnoreCase(usuarioLogin.getEmailUsuario());

		if (usuarioNoBD.isPresent()) {
		String senhaDigitada = usuarioLogin.getSenhaUsuario();
		String senhaSalvaNoBD = usuarioNoBD.get().getSenhaUsuario();


			if (compararSenhas(senhaDigitada, senhaSalvaNoBD)) {

				usuarioLogin.setToken(
						gerarBasicToken(usuarioLogin.getEmailUsuario(), usuarioLogin.getSenhaUsuario()));

				usuarioLogin.setIdUsuario(usuarioNoBD.get().getIdUsuario());
				usuarioLogin.setNomeUsuario(usuarioNoBD.get().getNomeUsuario());
				usuarioLogin.setSobrenomeUsuario(usuarioNoBD.get().getSobrenomeUsuario());

				return Optional.of(usuarioLogin);
			}
		}

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email e/ou senha digitados estão incorretos!");
	}

	@Transactional
	public Usuario atualizarUsuario(Usuario usuario) {
		if (verificarUsuarioExistente(usuario).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.");
		}

		Usuario usuarioNoBD = verificarUsuarioExistente(usuario).get();

		if (usuario.getSenhaUsuario() != null && !usuario.getSenhaUsuario().isBlank()) {

			String senhaDeComparacao = usuario.getConfirmacaoSenha();
			String senhaNova = usuario.getSenhaUsuario();
			String senhaAntiga = usuarioNoBD.getSenhaUsuario();

			/*
			 * PARA ATUALIZAR A SENHA É NECESSÁRIO ENVIAR A SENHA ATUAL JUNTO ESTE if
			 * COMPARA SE A SENHA ENVIADA ESTÁ CORRETA
			 */
			if (!compararSenhas(senhaDeComparacao, senhaAntiga)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha atual está incorreta!");
			}

			if (compararSenhas(senhaNova, senhaAntiga)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A nova senha deve ser diferente da antiga.");
			}

			verificarSenhaValida(usuario.getSenhaUsuario());

			usuarioNoBD.setSenhaUsuario(criptografarSenha(senhaNova));

		}

		// DADOS PESSOAIS
		if (usuario.getNomeUsuario() != null && usuario.getNomeUsuario() != usuarioNoBD.getNomeUsuario()) {
			usuarioNoBD.setNomeUsuario(usuario.getNomeUsuario());
		}
		if (usuario.getSobrenomeUsuario() != null
				&& usuario.getSobrenomeUsuario() != usuarioNoBD.getSobrenomeUsuario()) {
			usuarioNoBD.setSobrenomeUsuario(usuario.getSobrenomeUsuario());
		}
		if (usuario.getDataDeNascimento() != null
				&& usuario.getDataDeNascimento() != usuarioNoBD.getDataDeNascimento()) {
			usuarioNoBD.setDataDeNascimento(usuario.getDataDeNascimento());
		}
		if (usuario.getCpfUsuario() != null && usuario.getCpfUsuario() != usuarioNoBD.getCpfUsuario()) {
			usuarioNoBD.setCpfUsuario(usuario.getCpfUsuario());
		}
		if (usuario.getTelefoneUsuario() != null && usuario.getTelefoneUsuario() != usuarioNoBD.getTelefoneUsuario()) {
			usuarioNoBD.setTelefoneUsuario(usuario.getTelefoneUsuario());
		}

		usuarioNoBD.setCompartilharDadosUsuario(usuario.isCompartilharDadosUsuario());

		// ENDEREÇO
		if (usuario.getCepEndereco() != null && usuario.getCepEndereco() != usuarioNoBD.getCepEndereco()) {
			usuarioNoBD.setCepEndereco(usuario.getCepEndereco());
		}
		if (usuario.getLogradouroEndereco() != null
				&& usuario.getLogradouroEndereco() != usuarioNoBD.getLogradouroEndereco()) {
			usuarioNoBD.setLogradouroEndereco(usuario.getLogradouroEndereco());
		}
		if (usuario.getNumeroEndereco() != null && usuario.getNumeroEndereco() != usuarioNoBD.getNumeroEndereco()) {
			usuarioNoBD.setNumeroEndereco(usuario.getNumeroEndereco());
		}
		if (usuario.getBairroEndereco() != null && usuario.getBairroEndereco() != usuarioNoBD.getBairroEndereco()) {
			usuarioNoBD.setBairroEndereco(usuario.getBairroEndereco());
		}
		if (usuario.getComplementoEndereco() != null
				&& usuario.getComplementoEndereco() != usuarioNoBD.getComplementoEndereco()) {
			usuarioNoBD.setComplementoEndereco(usuario.getComplementoEndereco());
		}
		if (usuario.getCidadeEndereco() != null && usuario.getCidadeEndereco() != usuarioNoBD.getCidadeEndereco()) {
			usuarioNoBD.setCidadeEndereco(usuario.getCidadeEndereco());
		}
		if (usuario.getEstadoEndereco() != null && usuario.getEstadoEndereco() != usuarioNoBD.getEstadoEndereco()) {
			usuarioNoBD.setEstadoEndereco(usuario.getEstadoEndereco());
		}

		// ADMIN
		usuarioNoBD.setAdmin(usuario.isAdmin());

		return usuarioRepository.save(usuarioNoBD);

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
