package com.portoseguro.projetointegrador.enums;

public enum FormasDePagamentoEnum {

	CARTAO("Cartão de Crédito"),
	PICPAY("PicPay"),
	PAGSEGURO("PagSeguro"),
	PIX("Pix"),
	BOLETO("Boleto Bancário");

	private String value;

	FormasDePagamentoEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public static FormasDePagamentoEnum retornarEnum(String formaDePagamento) {
		for(FormasDePagamentoEnum forma : FormasDePagamentoEnum.values()) {
			if(forma.getValue().equalsIgnoreCase(formaDePagamento)) {
				return forma;
			}
		}
		
		
		return null;
	}
}
