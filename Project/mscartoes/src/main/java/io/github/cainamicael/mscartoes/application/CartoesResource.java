		package io.github.cainamicael.mscartoes.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.cainamicael.mscartoes.domain.Cartao;
import io.github.cainamicael.mscartoes.domain.ClienteCartao;
import io.github.cainamicael.mscartoes.representation.CartaoPorClienteResponse;
import io.github.cainamicael.mscartoes.representation.CartaoSavaRequest;

@RestController
@RequestMapping("cartoes")
public class CartoesResource {
	
	@Autowired
	private CartaoService cartaoService;
	
	@Autowired
	private ClienteCartaoService clienteCartaoService;
	
	@GetMapping
	public String status() {
		return "OK";
	}
	
	@PostMapping
	public ResponseEntity<?> cadastra(@RequestBody CartaoSavaRequest request) {
		Cartao cartao = request.toModel();
		cartaoService.save(cartao);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(params = "renda")
	public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda ) {
		List<Cartao> list = cartaoService.getCartoesRendaMenorIgual(renda);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(params = "cpf")
	public ResponseEntity<List<CartaoPorClienteResponse>> getCartoesByCliente(@RequestParam("cpf") String cpf) {
		List<ClienteCartao> lista = clienteCartaoService.listCartoesByCpf(cpf);
		List<CartaoPorClienteResponse> resultList = lista.stream().map(CartaoPorClienteResponse::fromModel).collect(Collectors.toList());
		return ResponseEntity.ok(resultList);
	}
}
