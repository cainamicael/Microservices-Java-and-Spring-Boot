package io.github.cainamicael.mscartoes.application;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.cainamicael.mscartoes.domain.Cartao;
import io.github.cainamicael.mscartoes.infra.repository.CartaoRepository;

@Service
public class CartaoService {
	
	@Autowired
	private CartaoRepository repository;
	
	@Transactional
	public Cartao save(Cartao cartao) {
		return repository.save(cartao);
	}
	
	@Transactional
	public List<Cartao> getCartoesRendaMenorIgual(Long renda) {
		var rendaBigBecimal = BigDecimal.valueOf(renda);
		return repository.findByRendaLessThanEqual(rendaBigBecimal);
	}
	
}
