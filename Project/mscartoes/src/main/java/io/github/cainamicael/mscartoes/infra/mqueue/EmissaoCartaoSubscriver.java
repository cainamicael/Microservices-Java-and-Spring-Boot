package io.github.cainamicael.mscartoes.infra.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cainamicael.mscartoes.domain.Cartao;
import io.github.cainamicael.mscartoes.domain.ClienteCartao;
import io.github.cainamicael.mscartoes.domain.DadosSolicitacaoEmissaoCartao;
import io.github.cainamicael.mscartoes.infra.repository.CartaoRepository;
import io.github.cainamicael.mscartoes.infra.repository.ClienteCartaoRepository;

@Component
public class EmissaoCartaoSubscriver {
	
	@Autowired
	private CartaoRepository repository;
	
	@Autowired
	private ClienteCartaoRepository clienteCartaoRepository;

	@RabbitListener(queues = "${mq.queues.emissao-cartoes}")
	public void receberSolicitacaoEmissao(@Payload String payload) {
		try {
			//Vamos transformar a string que recebemos em um objeto DadosSolicitacaoEmissaoCartao
			var mapper = new ObjectMapper();
			DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
			
			Cartao cartao = repository.findById(dados.getIdCartao()).orElseThrow();
			ClienteCartao clienteCartao = new ClienteCartao();
			clienteCartao.setCartao(cartao);
			clienteCartao.setCpf(dados.getCpf());
			clienteCartao.setLimite(dados.getLimiteLiberado());
			
			clienteCartaoRepository.save(clienteCartao);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
