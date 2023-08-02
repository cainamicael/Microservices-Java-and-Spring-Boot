package io.github.cainamicael.msclouldgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class MsclouldgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsclouldgatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder
				.routes()
				//Definindo rotas - Toda vez que a gente entrar nessa rota, vai ser redirecionado para a url (LoadBalancer do micro serviço), com o LoadBalancer
					.route(r -> r.path("/clientes/**").uri("lb://msclientes")) //O ** quer dizer qq coisa que venha depois e lb é o loadbalancer
					.route(r -> r.path("/cartoes/**").uri("lb://mscartoes")) //O ** quer dizer qq coisa que venha depois e lb é o loadbalancer
				.build();
	}
}
