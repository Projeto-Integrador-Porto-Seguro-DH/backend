package com.portoseguro.projetointegrador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portoseguro.projetointegrador.model.Produtos;

@Repository
public interface ProdutosRepository extends JpaRepository<Produtos, Long> {

	public List<Produtos> findAllByNomeProdutoContainingIgnoreCase(String nomeproduto);

}
