package com.portoseguro.projetointegrador.enums;

public enum StatusPedidoEnum {
	
	REALIZADO ("Pedido Recebido"),
	PROCESSANDO_PAGAMENTO ("Processando Pagamento"),
	PAGAMENTO_CONFIRMADO ("Pagamento Confirmado"),
	PAGAMENTO_NAO_REALIZADO ("Pagamento não Identificado"),
	PAGAMENTO_NEGADO ("Pagamento Negado"),
	EMITINDO_NOTA ("Nota Fiscal Emitida"),
	PREPARANDO_ENVIO ("Pedido em Preparação"),
	ENVIADO ("Pedido Entregue a Transportadora"),
	ENTREGUE ("Pedido Entregue"),
	CANCELADO ("Solicitação de Cancelamento Recebida"),
	TROCA_DEVOLUÇÃO ("Solicitação de Troca/Devolução Recebida"),
	EM_DEVOLUÇÃO ("Pedido em Rota de Devolução"),
	DEVOLVIDO ("Pedido Recebido na Central de Distribuição"),
	ESTORNO_LIBERADO ("Estorno do Valor do Pedido Liberado"),
	CRÉDITOS_LIBERADOS ("Crédito Liberado");
	private String descricao;
	StatusPedidoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
