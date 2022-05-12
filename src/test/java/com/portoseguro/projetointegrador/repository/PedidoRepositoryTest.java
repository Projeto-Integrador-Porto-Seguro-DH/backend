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

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PedidoRepositoryTest {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired	
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private DetalhePedidoRepository detalhePedidoRepository;
	
	@BeforeAll
	void start() {
		pedidoRepository.deleteAll();
		
		pedidoRepository.save(new Pedido(1234, "2022-05-08", "aprovado",
				"BLA1234567", "2022-05-08", 10.90, 120.50, 
				usuarioRepository.findAllByNomeUsuarioContainingIgnoreCase("Cyra"), detalhePedidoRepository.findAllByPedido(12340000958674839303)));
		
		pedidoRepository.save(new Pedido(5678, "2022-05-07", "aprovado",
				"BLA1238888", "2022-05-08", 5.90, 48.50, 
				usuarioRepository.findAllByNomeUsuarioContainingIgnoreCase("Biriba"), detalhePedidoRepository.findAllByPedido("BLA1238888")));	

	}
	
	@Test
	@DisplayName("Retorna o status pedido pelo código de envio")
	public void deveRetornarStatusPedido() {
		
		Optional<Pedido> pedido = pedidoRepository.findByPedido("BLA1238888");
		
		assertTrue(pedido.get().getCodigoEnvio().equals(pedido));
	}
	
	
	@Test
	@DisplayName("Retorna ")
	public void deveRetornar() {
		
		List<Pedido> listaDePedidos = pedidoRepository.findAllByPedido(null);
		
		assertEquals(2,listaDePedidos.size());
		assertTrue(listaDePedidos.get(0).getDetalhePedido());
		assertTrue(listaDePedidos.get(1).getDetalhePedido());
		
	}
	
//	@Test
//	@DisplayName("Retorna a Lista de Pedidos do usuário")
//	public void deveRetornarListaPedidoUsuario() {
//		List<Pedido> pedidosDoUsuario = pedidoRepository.findAllByPedido();
//		assertEquals(3,pedidosDoUsuario.size());
//		assertTrue(pedidosDoUsuario.get(0).getPedidoUsuario().equals());
//	
//	}
	
	
}
