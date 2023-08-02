package io.github.cainamicael.mscartoes.application;

import java.util.List;

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
import io.github.cainamicael.mscartoes.representation.CartaoSavaRequest;

@RestController
@RequestMapping("cartoes")
public class CartoesResource {
	
	@Autowired
	private CartaoService service;
	
	@GetMapping
	public String status() {
		return "OK";
	}
	
	@PostMapping
	public ResponseEntity<?> cadastra(@RequestBody CartaoSavaRequest request) {
		Cartao cartao = request.toModel();
		service.save(cartao);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(params = "renda")
	public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda ) {
		List<Cartao> list = service.getCartoesRendaMenorIgual(renda);
		return ResponseEntity.ok(list);
	}
}
