package com.portoseguro.projetointegrador.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.portoseguro.projetointegrador.model.DetalhePedido;
import com.portoseguro.projetointegrador.model.Pedido;
import com.portoseguro.projetointegrador.model.Produto;
import com.portoseguro.projetointegrador.repository.DetalhePedidoRepository;
import com.portoseguro.projetointegrador.repository.PedidoRepository;
import com.portoseguro.projetointegrador.repository.ProdutosRepository;

@Service
@Transactional(readOnly = true)
public class DetalhePedidoService {

	@Autowired
	public DetalhePedidoRepository detalhePedidoRepository;

	@Autowired
	public ProdutosRepository produtoRepository;

	@Autowired
	public PedidoRepository pedidoRepository;

	public Optional<List<DetalhePedido>> encontrarTodos() {
		List<DetalhePedido> todosDetalhes = detalhePedidoRepository.findAll();

		if (todosDetalhes.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(todosDetalhes);
	}

	public Optional<DetalhePedido> encontrarPorId(Long idDetalhePedido) {
		Optional<DetalhePedido> detalhePeloID = detalhePedidoRepository.findById(idDetalhePedido);

		return detalhePeloID;
	}

	public BigDecimal calcularSubtotal(DetalhePedido detalheDoPedido, Long idDoProduto) {
		Optional<Produto> produtoUnitario = produtoRepository.findById(idDoProduto);
		BigDecimal precoDoProduto = produtoUnitario.get().getPrecoUnitarioProduto();
		int quantidadePedida = detalheDoPedido.getQuantidadeProduto();

		detalheDoPedido.setSubtotal(new BigDecimal(quantidadePedida).multiply(precoDoProduto));

		return detalheDoPedido.getSubtotal();
	}

	@Transactional
	public void calcularTotalDoPedido(Long idDoPedido, BigDecimal subtotal) {
		Optional<Pedido> pedidoNoBancoDeDados = pedidoRepository.findById(idDoPedido);
		Pedido pedido = pedidoNoBancoDeDados.get();

		BigDecimal valorDoEnvio = pedido.getValorEnvio();
		BigDecimal valorTotalDoPedido = pedido.getValorTotalPedido();

		if (valorDoEnvio == null) {
			pedido.setValorEnvio(new BigDecimal(0));
		}

		if (valorTotalDoPedido == null) {
			pedido.setValorTotalPedido(new BigDecimal(0));
		}

		pedido.setValorTotalPedido(pedido.getValorTotalPedido().add(subtotal.add(pedido.getValorEnvio())));

		pedidoRepository.save(pedido);
	}

	public void verificarDisponibilidadeDeProduto(Long idDoProduto, int quantidadePedida) {
		Optional<Produto> produto = produtoRepository.findById(idDoProduto);

		boolean produtoDisponivel = produto.get().isProdutoDisponivel();
		int estoque = produto.get().getEstoqueProduto();
		int diferenca = estoque - quantidadePedida;

		if (!produtoDisponivel) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto não disponível no estoque!");
		}

		if (diferenca < 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					"Não há quantidade disponível em estoque!" + "\nQuantidade disponível: " + estoque);
		}
	}

	@Transactional
	public void atualizarEstoque(Long idDoProduto, int quantidadePedida) {
		Optional<Produto> produtoNoBD = produtoRepository.findById(idDoProduto);
		Produto produto = produtoNoBD.get();

		int estoque = produto.getEstoqueProduto();

		produto.setEstoqueProduto(estoque - quantidadePedida);

		if (produto.getEstoqueProduto() == 0) {
			produto.setProdutoDisponivel(false);
		}

		produtoRepository.save(produto);
	}

	@Transactional
	public void atualizarEstoque(Long idDoProduto, int quantidadeSalvaNoBD, int quantidadeAtualizada) {
		Optional<Produto> produtoNoBancoDeDados = produtoRepository.findById(idDoProduto);
		Produto produto = produtoNoBancoDeDados.get();

		int estoque = produto.getEstoqueProduto();
		int diferenca = quantidadeSalvaNoBD - quantidadeAtualizada;

		int estoqueAtualizado = estoque + diferenca;

		produto.setEstoqueProduto(estoqueAtualizado);

		produtoRepository.save(produto);
	}

	@Transactional
	public void reporEstoquePedidoCancelado(DetalhePedido detalheDoPedido) {
		Long idDoProduto = detalheDoPedido.getProduto().getIdProduto();
		Optional<Produto> produtoNoBancoDeDados = produtoRepository.findById(idDoProduto);
		Produto produto = produtoNoBancoDeDados.get();

		int estoque = produto.getEstoqueProduto();

		int quantidadeCancelada = detalheDoPedido.getQuantidadeProduto();

		int estoqueAtualizado = estoque + quantidadeCancelada;

		produto.setEstoqueProduto(estoqueAtualizado);

		produtoRepository.save(produto);
	}

	@Transactional
	public List<DetalhePedido> cadastarDetalhes(List<DetalhePedido> detalhesDoPedido, long idDoPedido) {
		List<DetalhePedido> listaDetalhesSalvos = new ArrayList<DetalhePedido>();
		Pedido pedidoNoBancoDeDados = pedidoRepository.findById(idDoPedido).get();

		for (int i = 0; i < detalhesDoPedido.size(); i++) {
			verificarDisponibilidadeDeProduto(detalhesDoPedido.get(i).getProduto().getIdProduto(),
					detalhesDoPedido.get(i).getQuantidadeProduto());

			atualizarEstoque(detalhesDoPedido.get(i).getProduto().getIdProduto(),
					detalhesDoPedido.get(i).getQuantidadeProduto());

			BigDecimal subtotal = calcularSubtotal(detalhesDoPedido.get(i),
					detalhesDoPedido.get(i).getProduto().getIdProduto());

			calcularTotalDoPedido(idDoPedido, subtotal);
			
			detalhesDoPedido.get(i).setPedido(pedidoNoBancoDeDados);
			
			listaDetalhesSalvos.add(detalhesDoPedido.get(i));
		}

		return detalhePedidoRepository.saveAll(listaDetalhesSalvos);
	}


}
;;