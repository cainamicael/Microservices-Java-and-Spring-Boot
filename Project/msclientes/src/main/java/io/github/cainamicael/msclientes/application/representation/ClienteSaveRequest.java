package io.github.cainamicael.msclientes.application.representation;

import io.github.cainamicael.msclientes.domain.Cliente;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClienteSaveRequest {

	private String cpf;
	private String nome;
	private Integer idade;
	
	public Cliente toModel() {
		return new Cliente(cpf, nome, idade);
	}
}
