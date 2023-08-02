package io.github.cainamicael.mscartoes.representation;

import java.math.BigDecimal;

import io.github.cainamicael.mscartoes.domain.BandeiraCartao;
import io.github.cainamicael.mscartoes.domain.Cartao;
import lombok.Data;

@Data
public class CartaoSavaRequest {

	private String nome;
	private BandeiraCartao bandeira;
	private BigDecimal renda;
	private BigDecimal limite;
	
	public Cartao toModel() {
		return new Cartao(nome, bandeira, renda, limite);
	}
	
}
