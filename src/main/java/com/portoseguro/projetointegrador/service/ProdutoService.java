package com.portoseguro.projetointegrador.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portoseguro.projetointegrador.model.Produto;
import com.portoseguro.projetointegrador.repository.ProdutosRepository;

public class ProdutoService {

	@Service
	@Transactional(readOnly = true)
	public class ProdutosService {

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
			List<Produto> buscaPorNome = produtosRepository.findById(nomeProduto);

			if (buscaPorNome.isEmpty()) {
				return Optional.empty();
				
			}

		public boolean verificarProdutoExistente(Produto produto) {
			Optional<Produto> produtoExistente = produtosRepository.findById(produto.getIdProduto());

			if (produtoExistente.isPresent()) {
				return true;
			}

			return false;

		}

		@Transactional
		public Produto cadastrarProduto(Produto produto) {
			produto.setStatusProduto(StatusProdutoEnum.REALIZADO);

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

}
