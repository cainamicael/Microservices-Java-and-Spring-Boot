package cio.github.cainamicael.msavaliadorcredito.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cio.github.cainamicael.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import cio.github.cainamicael.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import cio.github.cainamicael.msavaliadorcredito.domain.model.Cartao;
import cio.github.cainamicael.msavaliadorcredito.domain.model.CartaoAprovado;
import cio.github.cainamicael.msavaliadorcredito.domain.model.CartaoCliente;
import cio.github.cainamicael.msavaliadorcredito.domain.model.DadosCliente;
import cio.github.cainamicael.msavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
import cio.github.cainamicael.msavaliadorcredito.domain.model.SituacaoCliente;
import cio.github.cainamicael.msavaliadorcredito.infra.clients.CartoesResourceClient;
import cio.github.cainamicael.msavaliadorcredito.infra.clients.ClienteResourceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
	
	@Autowired
	private ClienteResourceClient clienteClient;
	
	@Autowired
	private CartoesResourceClient cartoesClient;

	
	public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
		//precisamos consultar o msclientes para obter os dados - vamos usar OpenFeign
		
		//Comunicação síncrona com o mscliente
		try {
			ResponseEntity<DadosCliente> dadosClienteResponse = clienteClient.dadosCliente(cpf);
			
			//Comunicação síncrona com o mscartoes
			ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);
			
			return SituacaoCliente
					.builder()
					.cliente(dadosClienteResponse.getBody())
					.cartoes(cartoesResponse.getBody())
					.build(); //Pegar apenas a informação do ResponseEntity	
		} catch (FeignException.FeignClientException e) {
			int status = e.status();
			
			if(HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
			
			throw new ErroComunicacaoMicroservicesException(cpf, status);
		}
	}
	
	public RetornoAvaliacaoCliente realizarAvaliacao (String cpf, Long renda) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
		try {
			ResponseEntity<DadosCliente> dadosClienteResponse = clienteClient.dadosCliente(cpf);
			ResponseEntity<List<Cartao>> cartoesResponse = cartoesClient.getCartoesRendaAte(renda);
			
			List<Cartao> cartoes = cartoesResponse.getBody();
			List<CartaoAprovado> listaCartoesAprovados = cartoes.stream().map((Cartao cartao) -> {
			    DadosCliente dadosCliente = dadosClienteResponse.getBody();
			    BigDecimal limiteBasico = cartao.getLimiteBasico();
			    BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());

			    var fator = idadeBD.divide(BigDecimal.valueOf(10));
			    BigDecimal limiteAprovado = fator.multiply(limiteBasico);

			    CartaoAprovado aprovado = new CartaoAprovado();
			    aprovado.setCartao(cartao.getNome());
			    aprovado.setBandeira(cartao.getBandeira());
			    aprovado.setLimiteAprovado(limiteAprovado);

			    return aprovado;
			}).collect(Collectors.toList());
			
			return new RetornoAvaliacaoCliente(listaCartoesAprovados);
		}  catch (FeignException.FeignClientException e) {
			int status = e.status();
			
			if(HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
	
			throw new ErroComunicacaoMicroservicesException(cpf, status);
		}
	}
}
