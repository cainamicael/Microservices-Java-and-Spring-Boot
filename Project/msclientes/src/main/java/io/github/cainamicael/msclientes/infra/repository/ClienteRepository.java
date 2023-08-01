package io.github.cainamicael.msclientes.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.cainamicael.msclientes.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
