package io.github.cainamicael.mscartoes.infra.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmissaoCartaoSubscriver {

	@RabbitListener(queues = "${mq.queues.emissao-cartoes}")
	public void receberSolicitacaoEmissao(@Payload String payload) {
		System.out.println(payload);
	}
}
