package com.portoseguro.projetointegrador.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
		Optional<Produto> produtoExistente = produtosRepository.findById(produto.getIdProduto());

		if (produtoExistente.isPresent()) {
			return true;
		}

		return false;

	}

	@Transactional
	public Produto cadastrarProduto(Produto produto) {
		
		if (verificarProdutoExistente(produto)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto " + produto.getNomeProduto() + " já existe!");
		}

		return produtosRepository.save(produto);
	}

	@Transactional
	public Produto atualizarProduto(Produto produto) {

		if (!verificarProdutoExistente(produto)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto" + produto.getNomeProduto() + "não existe!");
		}

		Produto produtoNoBD = produtosRepository.findById(produto.getIdProduto()).get();

		if (produtoNoBD.getNomeProduto() != produto.getNomeProduto()) {
			produtoNoBD.setNomeProduto(produto.getNomeProduto());
		}
		
		if (produtoNoBD.getDescricaoProduto() != produto.getDescricaoProduto()) {
			produtoNoBD.setDescricaoProduto(produto.getDescricaoProduto());
		}
		
		if (produtoNoBD.getPrecoUnitarioProduto() != produto.getPrecoUnitarioProduto()) {
			produtoNoBD.setPrecoUnitarioProduto(produto.getPrecoUnitarioProduto());
		}
		
		if (produtoNoBD.getEstoqueProduto() != produto.getEstoqueProduto()) {
			produtoNoBD.setEstoqueProduto(produto.getEstoqueProduto());
		}
		
		if (produtoNoBD.isProdutoDisponivel() != produto.isProdutoDisponivel()) {
			produtoNoBD.setProdutoDisponivel(produto.isProdutoDisponivel());
		}
		
		if(produtoNoBD.getCategoria().getIdCategoria() != produto.getCategoria().getIdCategoria()) {
			produtoNoBD.setCategoria(produto.getCategoria());
		}
		
		return produtosRepository.save(produtoNoBD);

	}

	@Transactional
	public void deletarProduto(Long idProduto) {
		Optional<Produto> produtoPorId = produtosRepository.findById(idProduto);

		if (produtoPorId.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não existe!");
		}

		produtosRepository.deleteById(idProduto);
	}

}
