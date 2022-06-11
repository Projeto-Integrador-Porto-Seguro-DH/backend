package com.portoseguro.projetointegrador.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portoseguro.projetointegrador.model.Produto;

@Repository
public interface ProdutosRepository extends JpaRepository<Produto, Long> {

	public List<Produto> findAllByNomeProdutoContainingIgnoreCase(String nomeProduto);

	public Optional<Produto> findByNomeProdutoIgnoreCase(String nomeProduto);

}
