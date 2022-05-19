package com.portoseguro.projetointegrador.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portoseguro.projetointegrador.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	/*
	 * ESSE MÉTODO LISTA TODAS AS CATEGORIAS QUE APRESENTAREM O CONTEÚDO INSERIDO NO
	 * PATH
	 */
	public List<Categoria> findAllByNomeCategoriaContainingIgnoreCase(String nomeCategoria);

	public Optional<Categoria> findByNomeCategoriaIgnoreCase(String nomeCategoria);

}