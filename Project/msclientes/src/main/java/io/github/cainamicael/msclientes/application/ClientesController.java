package io.github.cainamicael.msclientes.application;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.cainamicael.msclientes.application.representation.ClienteSaveRequest;
import io.github.cainamicael.msclientes.domain.Cliente;

@RestController
@RequestMapping("clientes")
public class ClientesController {
	
	@Autowired
	private ClienteService service;
	
	@GetMapping 
	public String status() { //Só para testar se está funcionando
		return "OK";
	}
	
	@PostMapping
	public ResponseEntity save(@RequestBody ClienteSaveRequest request) {
		Cliente cliente = request.toModel();
		service.save(cliente);
		
		
		//Construir uma url dinâmica através da url corrente - http://localhost:PORT/clientes?cpf=01234567890
		URI headerLocation = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.query("cpf={cpf}")
				.buildAndExpand(cliente.getCpf())
				.toUri();

		return ResponseEntity.created(headerLocation).build();
	}

	@GetMapping(params = "cpf")
	public ResponseEntity<?> dadosCliente(@RequestParam("cpf") String cpf) {
		var cliente = service.getByCpf(cpf);
		
		if(cliente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(cliente.get());
	}
}
