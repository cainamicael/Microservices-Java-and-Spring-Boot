package io.github.cainamicael.mscartoes.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.cainamicael.mscartoes.domain.ClienteCartao;
import io.github.cainamicael.mscartoes.infra.repository.ClienteCartaoRepository;

@Service
public class ClienteCartaoService {
	
	@Autowired
	private ClienteCartaoRepository repository;
	
	public List<ClienteCartao> listCartoesByCpf(String cpf) {
		return repository.findByCpf(cpf);
	}

}
