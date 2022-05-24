package com.portoseguro.projetointegrador.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portoseguro.projetointegrador.model.Produto;
import com.portoseguro.projetointegrador.repository.ProdutosRepository;

@Service
@Transactional(readOnly = true)
public class ProdutoService {

	@Autowired
	ProdutosRepository produtosRepository;

	public Optional<List<Produto>> encontrarProduto() {
		List<Produto> todosProdutos = produtosRepository.findAll();

		if (todosProdutos.isEmpty()) {
			return Optional.empty();

		}

		return Optional.of(todosProdutos);

	}

	public Optional<Produto> encontrarProdutoPorId(long idProduto) {
		Optional<Produto> produtoPorId = produtosRepository.findById(idProduto);

		return produtoPorId;

	}

	public Optional<List<Produto>> encontrarProdutosPorNome(String nomeProduto) {

		List<Produto> buscaPorNome = produtosRepository.findAllByNomeProdutoContainingIgnoreCase(nomeProduto);

		if (buscaPorNome.isEmpty()) {
			return Optional.empty();

		}

		return Optional.of(buscaPorNome);
	}

	public boolean verificarProdutoExistente(Produto produto) {
		Optional<Produto> produtoExistente = produtosRepository.findByNomeProdutoIgnoreCase(produto.getNomeProduto());

		if (produtoExistente.isPresent()) {
			return true;
		}

		return false;

	}

	@Transactional
	public Produto cadastrarProduto(Produto produto) {
		
		if (verificarProdutoExistente(produto)) {
			throw new IllegalStateException("Produto " + produto.getNomeProduto() + " já existe!");
		}

		if (produto.getEstoqueProduto() > 0) {
			produto.setProdutoDisponivel(true);
		}

		return produtosRepository.save(produto);
	}

	@Transactional
	public Produto atualizarProduto(Produto produto) {

		if (!verificarProdutoExistente(produto)) {
			throw new IllegalStateException("Produto" + produto.getNomeProduto() + "não existe!");

		}

		return produtosRepository.save(produto);

	}

	@Transactional
	public void deletarProduto(Long idProduto) {
		Optional<Produto> produtoPorId = produtosRepository.findById(idProduto);

		if (produtoPorId.isEmpty()) {
			throw new IllegalStateException("Produto não existe!");
		}

		produtosRepository.deleteById(idProduto);
	}

}
