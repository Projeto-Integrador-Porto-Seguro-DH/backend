package com.portoseguro.projetointegrador.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.portoseguro.projetointegrador.model.Pedido;
import com.portoseguro.projetointegrador.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@BeforeAll
	void start() {
		usuarioRepository.deleteAll();
		
		usuarioRepository.save(new Usuario (0L, "Biriba", "122.233.344-45", "Rua das Marés, 55",
				"biriba@gmail.com", "123456789", pedidoRepository.findAllByPedido(0L)));
		usuarioRepository.save(new Usuario (1L, "Lambada", "233.344.4555-56", "Rua das Marolas, 102",
				"lambada@gmail.com", "987654321", pedidoRepository.findAllByPedido(1L)));
		usuarioRepository.save(new Usuario (2L, "Cyra", "344.455.566-67", "Rua das Brisas, 48",
				"cyra@gmail.com", "001239995", pedidoRepository.findAllByPedido(2L)));
		usuarioRepository.save(new Usuario (3L, "Faísca", "455.566.677-78", "Rua da Areia, 260",
				"faisca@gmail.com", "5432100", pedidoRepository.findAllByPedido(3L)));
	
	}
	
	@Test
	@DisplayName("Retorna apenas 1 usuário")
	public void deveRetornar1Usuario() {
		
		Optional<Usuario> usuario = usuarioRepository.findByNomeUsuario("Biriba");
		
		assertTrue(usuario.get().getNomeUsuario().equals("Biriba"));
		
	}
	
	@Test
	@DisplayName("Retorna uma lista de 3 usuários")
	public void deveRetornar3Usuarios() {
		
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeUsuarioContainingIgnoreCase("String");
		
		assertEquals(4,listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNomeUsuario().equals("Biriba"));
		assertTrue(listaDeUsuarios.get(1).getNomeUsuario().equals("Lambada"));
		assertTrue(listaDeUsuarios.get(2).getNomeUsuario().equals("Cyra"));
	
	}
	
	@Test
	@DisplayName("Retorna o usuário pelo Email")
	public void deveRetornarPorEmail() {
		Optional<Usuario> usuarioPorEmail = usuarioRepository.findByEmailUsuarioContainingIgnoreCase("faisca@gmail.com");
		
		assertTrue(usuarioPorEmail.get().getEmailUsuario().equals("faisca@gmail.com"));
	
	}
	
	@Test
	@DisplayName("Retorna o usuário pelo CPF")
	public void deveRetornarPorCPF() {
		Optional<Usuario> usuarioPorCpf = usuarioRepository.findByCpfUsuario("455.566.677-78");
		
		assertTrue(usuarioPorCpf.get().getCpfUsuario().equals("455.566.677-78"));
	
	}
	
}
