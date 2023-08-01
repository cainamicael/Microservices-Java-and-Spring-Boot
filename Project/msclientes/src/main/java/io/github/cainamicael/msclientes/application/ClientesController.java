package io.github.cainamicael.msclientes.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("clientes")
public class ClientesController {
	
	@GetMapping 
	public String status() { //Só para testar se está funcionando
		return "OK";
	}

}
