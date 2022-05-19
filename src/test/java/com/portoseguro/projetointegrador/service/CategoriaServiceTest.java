package com.portoseguro.projetointegrador.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

import com.portoseguro.projetointegrador.model.Categoria;
import com.portoseguro.projetointegrador.repository.CategoriaRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class CategoriaServiceTest {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private CategoriaService categoriaService;

	@BeforeEach
	public void setUp() {
		categoriaRepository.deleteAll();
	}

	@Test
	@DisplayName("Buscar todas categorias e retornar nada por não ter encontrado")
	public void buscarTodasCategoriasERetornarNada() {
		Optional<List<Categoria>> listaVazia = Optional.of(categoriaRepository.findAll());
		
		assertThat(listaVazia.get()).isEmpty();
	}
	
	@Test
	@DisplayName("Buscar todas categorias e retornar uma lista")
	public void buscarTodasCategoriasERetornarLista() {
		Categoria primeira = categoriaRepository.save(new Categoria(0L, "Alimentos", "Categoria de alimentos"));
		Categoria segunda = categoriaRepository.save(new Categoria(0L, "Bebidas não alcoolicas", "Categoria de bebidas não alcoolicas"));
		Categoria terceira = categoriaRepository.save(new Categoria(0L, "Bebidas alcoolicas", "Categoria de bebidas alcoolicas"));
		Categoria quarta = categoriaRepository.save(new Categoria(0L, "Cosmeticos", "Categoria de cosmeticos"));
		
		List<Categoria> listaDeCategorias = categoriaRepository.findAll();
		
		assertThat(listaDeCategorias)
								.hasSize(4);
		
		assertThat(listaDeCategorias.get(0).getNomeCategoria())
												.isEqualTo(primeira.getNomeCategoria());
		
		assertThat(listaDeCategorias.get(1).getNomeCategoria())
												.isEqualTo(segunda.getNomeCategoria());
		
		assertThat(listaDeCategorias.get(2).getNomeCategoria())
												.isEqualTo(terceira.getNomeCategoria());
		
		assertThat(listaDeCategorias.get(3).getNomeCategoria())
												.isEqualTo(quarta.getNomeCategoria());
	}
	
	@Test
	@DisplayName("Deve retornar uma Exception se tentar cadastrar usuário existente")
	public void deveRetornarExceptionSeUsuarioJaExistir() {
		Categoria categoria = categoriaRepository.save(new Categoria(0L, "Alimentos", "Categoria de Alimentos"));

		String mensagemDeException = "Categoria " + categoria.getNomeCategoria() + " já existe!";

		Exception exception = assertThrows(IllegalStateException.class, 
											() -> categoriaService.cadastrarCategoria(categoria));

		assertEquals(mensagemDeException, exception.getMessage());
	}

}
