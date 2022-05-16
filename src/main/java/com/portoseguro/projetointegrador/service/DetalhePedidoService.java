package com.portoseguro.projetointegrador.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portoseguro.projetointegrador.enums.StatusPedidoEnum;
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
	public ProdutosRepository produtosRepository;

	@Autowired
	public PedidoRepository pedidoRepository;

	public void calcularSubtotal(DetalhePedido detalhePedido) {
		Long idProdutoBD = detalhePedido.getProduto().getIdProduto();

		Optional<Produto> produtoUnitario = produtosRepository.findById(idProdutoBD);

		detalhePedido.setSubtotal(new BigDecimal(detalhePedido.getQuantidadeProduto())
				.multiply(produtoUnitario.get().getPrecoUnitarioProduto()));
	}

	@Transactional
	public void calcularTotalDoPedido(DetalhePedido detalhePedido) {
		// Armazena os dados do Pedido do qual o DetalhePedido está atrelado.
		Long idDoPedido = detalhePedido.getPedido().getIdPedido();
		BigDecimal valorParcialDoTotal = detalhePedido.getSubtotal();

		/*
		 * Busca no BD o Pedido através de seu ID -> Retorna um Optional<Pedido> se
		 * encontrar algo. Quando não encontra, retorna um Optional<> vazio!
		 */
		Optional<Pedido> pedidoNoBancoDeDados = pedidoRepository.findById(idDoPedido);

		/*
		 * Armazena o pedido do BD em um objeto do tipo Pedido Não dá para fazer direto
		 * usando o objeto de cima por causa do Optional<> vazio
		 */
		Pedido pedido = pedidoNoBancoDeDados.get();

		/*
		 * Verifica se o valor do envio é nulo: - Caso sim, usa o Setter para definir o
		 * valor para Zero.
		 */
		if (pedido.getValorEnvio() == null) {
			pedido.setValorEnvio(new BigDecimal(0));
		}

		/*
		 * Verifica se o valor do Total é nulo: - Caso sim, usa o Setter para definir o
		 * valor para Zero; - Busca o valor Total e atualiza. Valor Total = Subtotal +
		 * Valor do Envio
		 */
		if (pedido.getValorTotalPedido() == null) {
			pedido.setValorTotalPedido(new BigDecimal(0));
		}

		pedido.setValorTotalPedido(pedido.getValorTotalPedido().add(valorParcialDoTotal.add(pedido.getValorEnvio())));

		// Salva o Pedido novamente no BD
		pedidoRepository.save(pedido);
	}

	@Transactional
	public DetalhePedido cadastarDetalhes(DetalhePedido detalhePedido) {
		removerItemDoEstoque(detalhePedido);

		calcularSubtotal(detalhePedido);

		calcularTotalDoPedido(detalhePedido);

		return detalhePedidoRepository.save(detalhePedido);
	}

	@Transactional
	public void removerItemDoEstoque(DetalhePedido detalhePedido) {
		verificarDisponibilidadeDeProduto(detalhePedido);

		Optional<Produto> produtoNoBD = produtosRepository.findById(detalhePedido.getProduto().getIdProduto());

		Produto produto = produtoNoBD.get();

		produto.setEstoqueProduto(produto.getEstoqueProduto() - detalhePedido.getQuantidadeProduto());

		if (produto.getEstoqueProduto() == 0) {
			produto.setProdutoDisponivel(false);
		}

		produtosRepository.save(produto);
	}

	public void verificarDisponibilidadeDeProduto(DetalhePedido detalhePedido) {
		Optional<Produto> produto = produtosRepository.findById(detalhePedido.getProduto().getIdProduto());

		if (!produto.get().isProdutoDisponivel()) {
			throw new IllegalStateException("Produto não disponível no estoque!");
		}

		int diferencaEstoqueComPedido = produto.get().getEstoqueProduto() - detalhePedido.getQuantidadeProduto();

		if (diferencaEstoqueComPedido <= 0) {
			throw new IllegalStateException("Não há quantidade disponível em estoque!" + "\nQuantidade disponível: "
					+ produto.get().getEstoqueProduto());
		}
	}

	@Transactional
	public void atualizarEstoqueAoCancelarPedido(DetalhePedido detalhePedido) {
		Long idDoPedido = detalhePedido.getPedido().getIdPedido();
		Long idDoProduto = detalhePedido.getProduto().getIdProduto();

		Optional<Pedido> pedidoNoBancoDeDados = pedidoRepository.findById(idDoPedido);
		Optional<Produto> produtoNoBancoDeDados = produtosRepository.findById(idDoProduto);

		Produto produto = produtoNoBancoDeDados.get();
		Pedido pedido = pedidoNoBancoDeDados.get();

		if (pedido.getStatusPedido() != StatusPedidoEnum.CANCELADO) {
			throw new IllegalStateException("Pedido não cancelado!");
		}
		
		int estoqueAtualizado = produto.getEstoqueProduto() + detalhePedido.getQuantidadeProduto();

		produto.setEstoqueProduto(estoqueAtualizado);

		produtosRepository.save(produto);
	}

}
