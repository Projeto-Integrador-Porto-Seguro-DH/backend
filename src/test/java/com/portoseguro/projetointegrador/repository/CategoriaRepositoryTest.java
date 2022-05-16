package com.portoseguro.projetointegrador.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.portoseguro.projetointegrador.model.Categoria;

@DataJpaTest
public class CategoriaRepositoryTest {

	@Autowired
	public CategoriaRepository categoriaRepository;
	
	@BeforeEach
	public void setUp() {
		categoriaRepository.deleteAll();
	}

	@Test
	@DisplayName("Não deve encontrar nada no BD")
	public void naoDeveEncontrarNada() {
		List<Categoria> lista = categoriaRepository.findAll();

		assertThat(lista).isEmpty();
	}

	@Test
	@DisplayName("Deve encontrar (uma lista de) categorias através do nome informado")
	public void deveEncontrarCategoriasAtravesDoNomeInformado() {
		Categoria primeira = categoriaRepository.save(new Categoria(0L, "Alimentos", "Categoria de alimentos"));
		Categoria segunda = categoriaRepository.save(new Categoria(0L, "Bebidas não alcoolicas", "Categoria de bebidas não alcoolicas"));
		Categoria terceira = categoriaRepository.save(new Categoria(0L, "Bebidas alcoolicas", "Categoria de bebidas alcoolicas"));
		Categoria quarta = categoriaRepository.save(new Categoria(0L, "Cosmeticos", "Categoria de cosmeticos"));
		
		List<Categoria> listaDeCategorias = categoriaRepository.findAllByNomeCategoriaContainingIgnoreCase("Bebida");
		
		assertThat(listaDeCategorias)
							.hasSize(2)
							.contains(segunda,terceira)
							.doesNotContain(primeira,quarta);
	}
	
	@Test
	@DisplayName("Deve encontrar apenas uma categoria através do nome exato")
	public void deveEncontrarUmaCategoriaPeloNomeExato() {
		categoriaRepository.save(new Categoria(0L, "Alimentos", "Categoria de alimentos"));
		
		Optional<Categoria> categoria = categoriaRepository.findByNomeCategoriaIgnoreCase("Alimentos");
		
		assertThat(categoria).isPresent();
		assertEquals("Alimentos", categoria.get().getNomeCategoria());
	}

}
