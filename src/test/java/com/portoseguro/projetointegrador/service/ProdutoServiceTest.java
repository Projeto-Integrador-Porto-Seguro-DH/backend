package com.portoseguro.projetointegrador.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.portoseguro.projetointegrador.model.Produto;
import com.portoseguro.projetointegrador.repository.ProdutosRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class ProdutoServiceTest {

	@Autowired
	private ProdutosRepository produtosRepository;

	@Autowired
	private ProdutoService produtoService;

	@BeforeEach
	public void setUp() {
		produtosRepository.deleteAll();

	}

	@Test
	@DisplayName("Encontrar o Produto")
	public void encontrarTodosOsProduto() {
		produtosRepository.save(new Produto(0L, "Café Pilão Extra Forte", "Cafeina Concentrada 90gs",
				new BigDecimal(18.90), 100, true));
		produtosRepository.save(new Produto(0L, "Café Três Corações", "Café com dupla Filtragem 150gr",
				new BigDecimal(17.90), 150, true));

		Optional<List<Produto>> produtosEncontrados = produtoService.encontrarProduto();

		assertThat(produtosEncontrados.get()).hasSize(2);
		assertEquals("Café Pilão Extra Forte", produtosEncontrados.get().get(0).getNomeProduto());
		assertEquals("Café Três Corações", produtosEncontrados.get().get(1).getNomeProduto());
	}

	@Test
	@DisplayName("Buscar Produto e Retonar Optional Vazio")
	public void buscarProdutoseRetornarOptionalVazio() {

		Optional<List<Produto>> produtosEncontrados = produtoService.encontrarProduto();

		assertThat(produtosEncontrados).isEmpty();

	}

}
