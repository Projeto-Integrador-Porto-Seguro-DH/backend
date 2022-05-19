package com.portoseguro.projetointegrador.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.portoseguro.projetointegrador.model.Categoria;
import com.portoseguro.projetointegrador.repository.CategoriaRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class CategoriaControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@BeforeEach
	public void setUp() {
		categoriaRepository.deleteAll();
	}

	@Test
	@DisplayName("Deve retornar 200 OK ao encontrar todas as categorias")
	public void deveRetornarOkAoEncontrarTodasCategorias() {
		categoriaRepository.save(new Categoria(0L, "Bebidas não alcoolicas", "Categoria de bebidas não alcoolicas"));
		categoriaRepository.save(new Categoria(0L, "Bebidas alcoolicas", "Categoria de bebidas alcoolicas"));

		ResponseEntity<ArrayList<Categoria>> respostaHttp = testRestTemplate.exchange("/categorias", HttpMethod.GET,
				null, new ParameterizedTypeReference<ArrayList<Categoria>>() {
				});

		assertEquals(HttpStatus.OK, respostaHttp.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 404 NOT FOUND ao não encontrar categorias")
	public void deveRetornarNotFoundSeNaoEncontrarCategoria() {
		ResponseEntity<ArrayList<Categoria>> respostaHttp = testRestTemplate.exchange("/categorias", HttpMethod.GET,
				null, new ParameterizedTypeReference<ArrayList<Categoria>>() {
				});

		assertEquals(HttpStatus.NOT_FOUND, respostaHttp.getStatusCode());
	}

	@Test
	@DisplayName("Deve encontrar categoria pelo ID e retornar 200 OK")
	public void deveEncontrarCategoriaPeloIdERetornarOk() {

		Categoria categoriaTeste = categoriaRepository.save(new Categoria(0L, "Alimentos", "Categoria de Alimentos"));

		long idCategoria = categoriaTeste.getIdCategoria();
		ResponseEntity<Categoria> respostaHttp = testRestTemplate.getForEntity("/categorias/" + idCategoria,
				Categoria.class);

		assertEquals(HttpStatus.OK, respostaHttp.getStatusCode());

		assertThat(respostaHttp.getBody().getIdCategoria()).isEqualTo(idCategoria);

		assertThat(respostaHttp.getBody().getNomeCategoria()).isEqualTo(categoriaTeste.getNomeCategoria());
	}

	@Test
	@DisplayName("Não deve encontrar categoria pelo ID e retornar 404 Not Found")
	public void naoDeveEncontrarCategoriaPeloId() {

		long idCategoria = 1L;
		ResponseEntity<Categoria> respostaHttp = testRestTemplate.getForEntity("/categorias/" + idCategoria,
				Categoria.class);

		assertEquals(HttpStatus.NOT_FOUND, respostaHttp.getStatusCode());
	}

	@Test
	@DisplayName("Deve encontrar categoria pelo Nome e retornar 200 OK")
	public void deveEncontrarCategoriaPeloNomeERetornarOk() {

		Categoria primeiraCateg = categoriaRepository
				.save(new Categoria(0L, "Bebidas não alcoolicas", "Categoria de bebidas não alcoolicas"));
		Categoria segundaCateg = categoriaRepository
				.save(new Categoria(0L, "Bebidas alcoolicas", "Categoria de bebidas alcoolicas"));

		String endpointPorNome = "/categorias/nome/bebida";

		ResponseEntity<ArrayList<Categoria>> respostaHttp = testRestTemplate.exchange(endpointPorNome, HttpMethod.GET,
				null, new ParameterizedTypeReference<ArrayList<Categoria>>() {
				});

		ArrayList<Categoria> lista = respostaHttp.getBody();

		assertEquals(HttpStatus.OK, respostaHttp.getStatusCode());

		assertThat(lista.get(0).getIdCategoria()).isEqualTo(primeiraCateg.getIdCategoria());

		assertThat(lista.get(1).getIdCategoria()).isEqualTo(segundaCateg.getIdCategoria());
	}

	@Test
	@DisplayName("Não deve encontrar categorias pelo nome e retornar 404 Not Found")
	public void naoDeveEncontrarPeloNomeERetornarNotFound() {

		String endpointPorNome = "/categorias/nome/bebida";

		ResponseEntity<ArrayList<Categoria>> respostaHttp = testRestTemplate.exchange(endpointPorNome, HttpMethod.GET,
				null, new ParameterizedTypeReference<ArrayList<Categoria>>() {
				});

		assertEquals(HttpStatus.NOT_FOUND, respostaHttp.getStatusCode());
	}

	@Test
	@DisplayName("Deve criar uma Categoria e retornar 201 CREATED")
	public void deveCriarUmaCategoria() {

		HttpEntity<Categoria> requisicao = new HttpEntity<>(new Categoria(0L, "Alimentos", "Categoria de Alimentos"));

		ResponseEntity<Categoria> respostaHttp = testRestTemplate.exchange("/categorias/add", HttpMethod.POST,
				requisicao, Categoria.class);

		assertEquals(HttpStatus.CREATED, respostaHttp.getStatusCode());

		assertThat(respostaHttp.getBody().getNomeCategoria()).isEqualTo("Alimentos");
	}

	@Test
	@DisplayName("Ao tentar cadastrar uma categoria e ela existir, retornar 500 Internal Error")
	public void naoCriarNovaCategoriaSeJaExistir() {

		categoriaRepository.save(new Categoria(0L, "Alimentos", "Categoria de Alimentos"));

		HttpEntity<Categoria> requisicao = new HttpEntity<>(new Categoria(0L, "Alimentos", "Categoria de Alimentos"));

		ResponseEntity<Categoria> respostaHttp = testRestTemplate.exchange("/categorias/add", HttpMethod.POST,
				requisicao, Categoria.class);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, respostaHttp.getStatusCode());

	}
}
