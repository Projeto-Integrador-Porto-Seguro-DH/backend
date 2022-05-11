package com.portoseguro.projetointegrador.service;

import java.math.BigDecimal;
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
	public ProdutosRepository produtosRepository;

	@Autowired
	public PedidoRepository pedidoRepository;

	public void calcularSubtotal(DetalhePedido detalhePedido) {
		Long idProdutoBD = detalhePedido.getProduto().getIdProduto();

		Optional<Produto> produtoUnitario = produtosRepository.findById(idProdutoBD);

		detalhePedido.setSubtotal(new BigDecimal(detalhePedido.getQuantidadeProduto()).multiply(produtoUnitario.get().getPrecoUnitarioProduto()));
	}

	@Transactional
	public void calcularTotalDoPedido(DetalhePedido detalhePedido) {
		// Armazena os dados do Pedido do qual o DetalhePedido está atrelado.
		Long idDoPedido = detalhePedido.getPedido().getIdPedido();
		BigDecimal valorParcialDoTotal = detalhePedido.getSubtotal();

		/*
		 * Busca no BD o Pedido através de seu ID -> Retorna um Optional<Pedido> se encontrar algo.
		 * Quando não encontra, retorna um Optional<> vazio!
		 */
		Optional<Pedido> pedidoNoBancoDeDados = pedidoRepository.findById(idDoPedido);

		/*
		 * Armazena o pedido do BD em um objeto do tipo Pedido 
		 * Não dá para fazer direto usando o objeto de cima por causa do Optional<> vazio
		 */
		Pedido pedido = pedidoNoBancoDeDados.get();

		/*
		 * Verifica se o valor do envio é nulo:
		 * 	- Caso sim, usa o Setter para definir o valor para Zero.
		 */
		if (pedido.getValorEnvio() == null) {
			pedido.setValorEnvio(new BigDecimal(0));
		}

		/*
		 * Verifica se o valor do Total é nulo: 
		 *		- Caso sim, usa o Setter para definir o valor para Zero; 
		 *		- Busca o valor Total e atualiza. 
		 * Valor Total = Subtotal + Valor do Envio
		 */
		if (pedido.getValorTotalPedido() == null) {
			pedido.setValorTotalPedido(new BigDecimal(0));
		}

		pedido.setValorTotalPedido(pedido.getValorTotalPedido()
															.add(valorParcialDoTotal.add(pedido.getValorEnvio())));

		// Salva o Pedido novamente no BD
		pedidoRepository.save(pedido);
	}

	@Transactional
	public DetalhePedido cadastarDetalhes(DetalhePedido detalhePedido) {
		calcularSubtotal(detalhePedido);

		calcularTotalDoPedido(detalhePedido);

		return detalhePedidoRepository.save(detalhePedido);
	}

}
