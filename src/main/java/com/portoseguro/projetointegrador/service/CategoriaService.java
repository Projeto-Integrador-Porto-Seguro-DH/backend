package com.portoseguro.projetointegrador.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portoseguro.projetointegrador.model.Categoria;
import com.portoseguro.projetointegrador.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Transactional
	public Categoria cadastrarCategoria(Categoria categoria) {
		
		Optional<Categoria> categoriaExistente = categoriaRepository.findByNomeCategoriaIgnoreCase(categoria.getNomeCategoria());
		
		if(categoriaExistente.isPresent()) {
			throw new IllegalStateException("Categoria " + categoria.getNomeCategoria() + " já existe!");
		}
		
		return categoriaRepository.save(categoria);
	}
	
}
