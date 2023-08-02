package io.github.cainamicael.mscartoes.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Cartao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Enumerated(EnumType.STRING)
	private BandeiraCartao bandeira; //Vai ser um enum
	private BigDecimal renda;
	private BigDecimal limiteBasico;
	
	public Cartao(String nome, BandeiraCartao bandeira, BigDecimal renda, BigDecimal limiteBasico) {
		super();
		this.nome = nome;
		this.bandeira = bandeira;
		this.renda = renda;
		this.limiteBasico = limiteBasico;
	}
}
