package cio.github.cainamicael.msavaliadorcredito.infra.mqueue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cio.github.cainamicael.msavaliadorcredito.domain.model.DadosSolicitacaoEmissaoCartao;

@Component
public class SolicitacaoEmissaoCartaoPublisher {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private Queue queueEmissaoCartoes; //Injeta a fila que criamos na configuracao
	
	
	public void solicitarCartao(DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
		var json = convertIntoJson(dados);
		rabbitTemplate.convertAndSend(queueEmissaoCartoes.getName(), json); //A fila e o json
	}
	
	//Vamos transformar nosso objeto de parâmetro em json
	private String convertIntoJson(DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		var json = mapper.writeValueAsString(dados);
		return json;
	}
}
