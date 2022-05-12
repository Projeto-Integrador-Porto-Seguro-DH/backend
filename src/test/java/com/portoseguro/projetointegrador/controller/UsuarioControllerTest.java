package com.portoseguro.projetointegrador.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.portoseguro.projetointegrador.model.Usuario;
import com.portoseguro.projetointegrador.repository.PedidoRepository;
import com.portoseguro.projetointegrador.repository.UsuarioRepository;
import com.portoseguro.projetointegrador.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@BeforeAll
	void start() {
		usuarioRepository.deleteAll();
	}
	
	// TESTE 1: Verificação do mét cadastrarUsuario()
	
	@Test
	@Order(1)
	@DisplayName("Cadastrar o usuário")
	public void deveCadastrarUmUsuario() {	
		HttpEntity<Usuario> request = new HttpEntity<Usuario>(new Usuario(0L,"Lúcia Souza", 
				"111.222.333-44", "Rua Tal, 56", "luciasouza@gmail.com",
				"012345678", null ));
	
		ResponseEntity<Usuario> response = testRestTemplate
				.exchange("/usuarios/add", HttpMethod.POST, request, Usuario.class);

	
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		assertEquals(request.getBody().getNomeUsuario(), response.getBody().getNomeUsuario());
		assertEquals(request.getBody().getCpfUsuario(), response.getBody().getCpfUsuario());
		assertEquals(request.getBody().getEnderecoUsuario(), response.getBody().getEnderecoUsuario());
		assertEquals(request.getBody().getEmailUsuario(), response.getBody().getEmailUsuario());
		assertEquals(request.getBody().getSenhaUsuario(), response.getBody().getSenhaUsuario());
		assertEquals(request.getBody().getPedidoUsuario(), response.getBody().getPedidoUsuario());
		//Resposta esperada: 201 created
	}
	
	// TESTE 2: Verificação da não duplicidade de cadastro de usuário
	
	@Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação de cadastro de usuário")
	public void naoDeveDuplicarUsuario() {
		usuarioService.cadastrarUsuario(new Usuario(0L,"Pedro Silva", 
				"222.333.444-55", "Rua Tal, 102", "pedros@gmail.com",
				"567801234", null));
		
		HttpEntity<Usuario> request = new HttpEntity<Usuario>(new Usuario(0L,"Pedro Silva", 
				"222.333.444-55", "Rua Tal, 102", "pedros@gmail.com",
				"567801234", null));
		
		ResponseEntity<Usuario> response = testRestTemplate
				.exchange("/usuarios/add", HttpMethod.POST, request, Usuario.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	
	}
	
	@Test
	@Order(3)
	@DisplayName("Alterar o usuário")
	public void deveAlterarUsuario() {
		Optional<Usuario> createUser = Optional.of(usuarioService.cadastrarUsuario(new Usuario(0L,"Jose Pereira", 
				"222.333.444-55", "Rua Tal, 102", "joseprerira@gmail.com",
				"567801234", null)));
		
		Usuario UsuarioUpdate = new Usuario(createUser.get().getIdUsuario(0L,"Jose Pereira", 
				"222.333.444-55", "Rua Tal, 102", "joseprerira@gmail.com",
				"567801234", pedidoRepository.getById(0L)));
	}
	
	
	
	
	
	
}











