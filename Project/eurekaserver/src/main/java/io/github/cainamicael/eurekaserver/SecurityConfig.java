package io.github.cainamicael.eurekaserver;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() //Porque não estamos usando formulário
			.authorizeHttpRequests().anyRequest().authenticated() //Dizendo que qualquer requisição, precisa-se de uma autenticação
			.and()
			.httpBasic(); //Autenticação básica
	}

}
