package cio.github.cainamicael.msavaliadorcredito.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cio.github.cainamicael.msavaliadorcredito.domain.model.CartaoCliente;
import cio.github.cainamicael.msavaliadorcredito.domain.model.DadosCliente;
import cio.github.cainamicael.msavaliadorcredito.domain.model.SituacaoCliente;
import cio.github.cainamicael.msavaliadorcredito.infra.clients.CartoesResourceClient;
import cio.github.cainamicael.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
	
	@Autowired
	private ClienteResourceClient clienteClient;
	
	@Autowired
	private CartoesResourceClient cartoesClient;
	
	public SituacaoCliente obterSituacaoCliente(String cpf) {
		//precisamos consultar o msclientes para obter os dados - vamos usar OpenFeign
		
		//Comunicação síncrona com o mscliente
		ResponseEntity<DadosCliente> dadosClienteResponse = clienteClient.dadosCliente(cpf);
		
		//Comunicação síncrona com o mscartoes
		ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);
		
		return SituacaoCliente
				.builder()
				.cliente(dadosClienteResponse.getBody())
				.cartoes(cartoesResponse.getBody())
				.build(); //Pegar apenas a informação do ResponseEntity	
	}
}
