package com.portoseguro.projetointegrador.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
				.findById(categoria.getIdCategoria());

		if (categoriaExistente.isPresent()) {
			return true;
		}

		return false;
	}

	@Transactional
	public Categoria cadastrarCategoria(Categoria categoria) {

		if (verificarCategoriaExistente(categoria)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Categoria " + categoria.getNomeCategoria() + " já existe!");
		}

		return categoriaRepository.save(categoria);
	}

	@Transactional
	public Categoria atualizarCategoria(Categoria categoria) {

		if (!verificarCategoriaExistente(categoria)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Categoria " + categoria.getNomeCategoria() + " não existe!");
		}

		Categoria categoriaNoBD = categoriaRepository.findById(categoria.getIdCategoria()).get();

		if (categoriaNoBD.getNomeCategoria() != categoria.getNomeCategoria()) {
			categoriaNoBD.setNomeCategoria(categoria.getNomeCategoria());
		}

		if (categoriaNoBD.getDescricaoCategoria() != categoria.getDescricaoCategoria()) {
			categoriaNoBD.setDescricaoCategoria(categoria.getDescricaoCategoria());
		}

		return categoriaRepository.save(categoria);
	}

	@Transactional
	public void deletarCategoria(Long id) {
		Optional<Categoria> categoriaPeloId = categoriaRepository.findById(id);

		if (categoriaPeloId.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não existe!");
		}

		categoriaRepository.deleteById(id);
	}
}
