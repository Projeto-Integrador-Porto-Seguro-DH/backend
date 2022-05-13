package com.portoseguro.projetointegrador.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
	@DisplayName("Deve retornar uma Exception se tentar cadastrar usuário existente")
	public void deveRetornarExceptionSeUsuarioJaExistir() {
		Categoria categoria = categoriaRepository.save(new Categoria(0L, "Alimentos", "Categoria de Alimentos"));

		String mensagemDeException = "Categoria " + categoria.getNomeCategoria() + " já existe!";

		Exception exception = assertThrows(IllegalStateException.class, 
											() -> categoriaService.cadastrarCategoria(categoria));

		assertEquals(mensagemDeException, exception.getMessage());
	}

}
