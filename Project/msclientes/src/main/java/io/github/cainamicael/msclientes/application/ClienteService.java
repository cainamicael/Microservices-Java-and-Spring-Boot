package io.github.cainamicael.msclientes.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.cainamicael.msclientes.domain.Cliente;
import io.github.cainamicael.msclientes.infra.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	@Transactional
	public Cliente save(Cliente cliente) {
		return repository.save(cliente);
	}
	
	@Transactional
	public Optional<Cliente> getByCpf(String cpf){
		return repository.findByCpf(cpf);
	}
}
