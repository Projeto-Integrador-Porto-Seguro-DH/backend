package com.portoseguro.projetointegrador.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.portoseguro.projetointegrador.model.Produto;

@DataJpaTest
public class ProdutosRepositoryTest {

	@Autowired
	public ProdutosRepository produtosRepository;

	@BeforeEach
	public void setUp() {
		produtosRepository.deleteAll();

	}

	@Test
	@DisplayName("Encontrar Pelo Nome do Produto")
	public void localizarProdutoPorNome() {
		Produto um = produtosRepository.save(new Produto(0L, "Café Pilão Extra Forte", "Cafeina Concentrada 90gs",
				new BigDecimal(18.90), 100, true));
		Produto dois = produtosRepository.save(new Produto(0L, "Café Três Corações", "Café com dupla Filtragem 150gr",
				new BigDecimal(17.90), 150, true));

		List<Produto> buscaPorNome = produtosRepository.findAllByNomeProdutoContainingIgnoreCase("Pilão");

		assertThat(buscaPorNome).hasSize(1).contains(um).doesNotContain(dois);
	}

	@Test
	@DisplayName("Encontrar Pelo Nome Exato do Produto")
	public void localizarProdutoApenasPorNomeExato() {
		produtosRepository.save(new Produto(0L, "Café Pilão Extra Forte", "Cafeina Concentrada 90gs",
				new BigDecimal(18.90), 100, true));
		produtosRepository.save(new Produto(0L, "Café Três Corações", "Café com dupla Filtragem 150gr",
				new BigDecimal(17.90), 150, true));

		Optional<Produto> buscaPorNome = produtosRepository.findByNomeProdutoIgnoreCase("Café Pilão Extra Forte");

		assertEquals("Café Pilão Extra Forte", buscaPorNome.get().getNomeProduto());

	}
}