package cio.github.cainamicael.msavaliadorcredito.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cio.github.cainamicael.msavaliadorcredito.domain.model.DadosCliente;

@FeignClient(value = "msclientes", path = "/clientes") //Estamos referenciando o ms e o endpoint
public interface ClienteResourceClient {
	
	//Só precisamos colocar a assinatura do método que vamos usar para obter os dados via endpoint
	@GetMapping(params = "cpf")
	ResponseEntity<DadosCliente> dadosCliente(@RequestParam("cpf") String cpf);

}
