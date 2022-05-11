package com.portoseguro.projetointegrador.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.portoseguro.projetointegrador.model.Categoria;
import com.portoseguro.projetointegrador.repository.CategoriaRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoriaControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@BeforeEach
	public void start() {
		categoriaRepository.deleteAll();
	}

	@Test
	@Order(1)
	@DisplayName("Deve encontrar todas as categorias")
	public void deveEncontrarTodasCategorias() {
		ResponseEntity<?> respostaHttp = testRestTemplate.getForEntity("/categorias", ArrayList.class);

		assertEquals(HttpStatus.OK, respostaHttp.getStatusCode());
	}

	@Test
	@Order(2)
	@DisplayName("Deve encontrar categoria pelo ID")
	public void deveEncontrarCategoriaPeloId() {

		Categoria categoriaTeste = categoriaRepository.save(new Categoria(0L,"Alimentos","Categoria de Alimentos"));
		
		long idCategoria = 1;
		ResponseEntity<Categoria> respostaHttp = testRestTemplate.getForEntity("/categorias/" + idCategoria, Categoria.class);

		assertThat(respostaHttp.getBody().getIdCategoria()).isEqualTo(idCategoria);
		assertThat(respostaHttp.getBody().getNomeCategoria()).isEqualTo(categoriaTeste.getNomeCategoria());
	}
}
