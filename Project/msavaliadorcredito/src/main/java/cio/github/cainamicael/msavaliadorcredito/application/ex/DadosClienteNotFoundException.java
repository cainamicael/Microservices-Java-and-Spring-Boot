package cio.github.cainamicael.msavaliadorcredito.application.ex;

public class DadosClienteNotFoundException extends Exception{
	
	public DadosClienteNotFoundException() {
		super("Dados do cliente não encontrados para o cpf informado");
	}
	
}
