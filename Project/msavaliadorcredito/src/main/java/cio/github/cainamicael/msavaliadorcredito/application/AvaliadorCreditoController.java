package cio.github.cainamicael.msavaliadorcredito.application;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cio.github.cainamicael.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import cio.github.cainamicael.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import cio.github.cainamicael.msavaliadorcredito.domain.model.SituacaoCliente;

@RestController
@RequestMapping("avaliacoes-credito")
public class AvaliadorCreditoController {
	
	@Autowired
	private AvaliadorCreditoService avaliadorCreditoService;
	
	@GetMapping
	public String status() {
		return "OK";
	}
	
	@GetMapping(value = "situacao-cliente", params = "cpf")
	public ResponseEntity<?> consultaSituacaoCliente(@RequestParam("cpf") String cpf) {
		try {
			SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
			return ResponseEntity.ok(situacaoCliente);
		} catch (DadosClienteNotFoundException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.notFound().build();
		} catch (ErroComunicacaoMicroservicesException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}
	}
}
