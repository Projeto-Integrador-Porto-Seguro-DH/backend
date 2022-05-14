package com.portoseguro.projetointegrador.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portoseguro.projetointegrador.model.Categoria;
import com.portoseguro.projetointegrador.repository.CategoriaRepository;

@Service
@Transactional(readOnly = true)
public class CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;

	public Optional<List<Categoria>> encontrarTodos() {
		List<Categoria> todasCategorias = categoriaRepository.findAll();

		if (todasCategorias.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(todasCategorias);
	}

	public Optional<Categoria> encontrarPorID(Long id) {
		Optional<Categoria> categoriaPeloId = categoriaRepository.findById(id);

		return categoriaPeloId;
	}

	public Optional<List<Categoria>> encontrarTodosPorNome(String nomeCategoria) {
		List<Categoria> buscaPorNome = categoriaRepository.findAllByNomeCategoriaContainingIgnoreCase(nomeCategoria);

		if (buscaPorNome.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(buscaPorNome);
	}

	public boolean verificarCategoriaExistente(Categoria categoria) {
		Optional<Categoria> categoriaExistente = categoriaRepository
				.findByNomeCategoriaIgnoreCase(categoria.getNomeCategoria());

		if (categoriaExistente.isPresent()) {
			return true;
		}

		return false;
	}

	@Transactional
	public Categoria cadastrarCategoria(Categoria categoria) {
		
		if (verificarCategoriaExistente(categoria)) {
			throw new IllegalStateException("Categoria " + categoria.getNomeCategoria() + " já existe!");
		}

		return categoriaRepository.save(categoria);
	}

	@Transactional
	public Categoria atualizarCategoria(Categoria categoria) {
		
		if (!verificarCategoriaExistente(categoria)) {
			throw new IllegalStateException("Categoria " + categoria.getNomeCategoria() + " não existe!");
		}

		return categoriaRepository.save(categoria);
	}
	
	@Transactional
	public void deletarCategoria(Long id) {
		Optional<Categoria> categoriaPeloId = categoriaRepository.findById(id);
		
		if (categoriaPeloId.isEmpty()) {
			throw new IllegalStateException("Categoria não existe!");
		}

		categoriaRepository.deleteById(id);
	}
}
