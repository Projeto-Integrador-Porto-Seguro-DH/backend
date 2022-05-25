package com.portoseguro.projetointegrador.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
			throw new IllegalStateException("Produto não disponível no estoque!");
		}

		if (diferenca < 0) {
			throw new IllegalStateException(
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
	public DetalhePedido cadastarDetalhes(DetalhePedido detalheDoPedido) {
		Long idProduto = detalheDoPedido.getProduto().getIdProduto();
		int quantidadeProduto = detalheDoPedido.getQuantidadeProduto();
		Long idPedido = detalheDoPedido.getPedido().getIdPedido();

		verificarDisponibilidadeDeProduto(idProduto, quantidadeProduto);

		atualizarEstoque(idProduto, quantidadeProduto);

		BigDecimal subtotal = calcularSubtotal(detalheDoPedido, idProduto);

		calcularTotalDoPedido(idPedido, subtotal);

		return detalhePedidoRepository.save(detalheDoPedido);
	}

	/* NÃO UTILIZADO NO MOMENTO
	 * VER COMENTÁRIO ABAIXO
	 * 
	 * 
	 * public boolean verificarExistenciaDetalhePedido(DetalhePedido
	 * detalheDoPedido) { Optional<DetalhePedido> detalheExistente =
	 * detalhePedidoRepository .findById(detalheDoPedido.getIdDetalhePedido());
	 * 
	 * if (detalheExistente.isPresent()) { return true; }
	 * 
	 * return false; }
	 */

	//
	
	/*
	 * MÉTODO DE ATUALIZAR NÃO UTILIZADO Nenhum usuário deve ter autorização para
	 * atualizar o Detalhe do pedido após realizado!
	 * 
	 * 
	 * 
	 * @Transactional public DetalhePedido atualizarDetalhePedido(DetalhePedido
	 * detalheDoPedido) { boolean detalheExiste =
	 * verificarExistenciaDetalhePedido(detalheDoPedido);
	 * 
	 * if (!detalheExiste) { throw new
	 * IllegalStateException("Detalhe de pedido inexistente!"); }
	 * 
	 * Long idDoDetalhe = detalheDoPedido.getIdDetalhePedido();
	 * Optional<DetalhePedido> detalheNoBD =
	 * detalhePedidoRepository.findById(idDoDetalhe);
	 * 
	 * int quantidadeNoBD = detalheNoBD.get().getQuantidadeProduto(); int
	 * quantidadeAtualizada = detalheDoPedido.getQuantidadeProduto();
	 * 
	 * if (quantidadeAtualizada != quantidadeNoBD) { Long idDoProduto =
	 * detalheDoPedido.getProduto().getIdProduto();
	 * 
	 * atualizarEstoque(idDoProduto, quantidadeNoBD, quantidadeAtualizada);
	 * calcularSubtotal(detalheDoPedido, idDoProduto); }
	 * 
	 * return detalhePedidoRepository.save(detalheDoPedido); }
	 */

	//
	
	/*
	 * MÉTODO NÃO UTILIZADO PELOS MESMOS MOTIVOS ACIMA
	 * 
	 * 
	 * 
	 * @Transactional public void deletarDetalhe(Long idDetalhePedido) {
	 * Optional<DetalhePedido> detalhePedidoNoBD =
	 * detalhePedidoRepository.findById(idDetalhePedido); DetalhePedido
	 * detalhePedido = detalhePedidoNoBD.get();
	 * 
	 * if (detalhePedidoNoBD.isEmpty()) { throw new
	 * IllegalStateException("Detalhe de pedido inexistente!"); }
	 * 
	 * reporEstoquePedidoCancelado(detalhePedidoNoBD.get());
	 * 
	 * detalhePedidoRepository.deleteById(idDetalhePedido); }
	 */

}
