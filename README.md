# Microservices Java and Spring Boot

O projeto de Gerenciamento de Clientes e Cartões é uma solução moderna e escalável construída utilizando a arquitetura de microservices. Essa arquitetura permite o desenvolvimento, implantação e manutenção eficiente de componentes independentes e altamente especializados, que juntos oferecem um sistema integrado para a gestão de informações de clientes e cartões de crédito. Através de uma combinação de serviços web e uma infraestrutura de mensageria assíncrona, o projeto garante uma experiência de usuário fluida e eficaz.

O projeto é composto por três microservices principais:

MSClientes: Responsável pelo gerenciamento de informações dos clientes. Fornece endpoints para criar novos clientes e buscar informações detalhadas de clientes existentes com base em seu CPF. Cada requisição é autenticada por um token JWT obtido através do Keycloak, garantindo segurança e autorização adequadas.

MSCartoes: Gerencia as informações relacionadas aos cartões de crédito. Permite a criação de novos cartões com base em critérios como nome, renda e limite. Além disso, fornece endpoints para buscar cartões com base na renda do cliente ou no CPF do cliente associado.

MSAvaliadorCredito: Realiza avaliações de crédito para os clientes e determina os limites dos cartões. Fornece endpoints para verificar a situação de um cliente, com detalhes sobre seus cartões, com base no CPF. Também executa avaliações de crédito com base na renda do cliente e oferece endpoints para salvar informações sobre as solicitações de cartão aprovadas.

A comunicação entre esses microservices é suportada pelo OpenFeign para chamadas síncronas e pelo serviço de mensageria RabbitMQ para comunicação assíncrona. Isso garante a interoperabilidade e a troca de informações entre os componentes de forma eficiente e confiável.

O projeto utiliza o Discovery Server e o API Gateway para gerenciar o roteamento e o balanceamento de carga entre os diferentes microservices, garantindo alta disponibilidade e escalabilidade. Isso permite que os usuários acessem os serviços de maneira transparente, sem precisar saber a localização física de cada microservice.

Para garantir a segurança e a autenticação, o projeto utiliza o Keycloak para gerar tokens JWT, que são necessários para acessar os endpoints dos microservices. Isso protege os dados sensíveis e garante que apenas usuários autorizados possam interagir com o sistema.

Em resumo, o projeto de Gerenciamento de Clientes e Cartões com Microservices é uma solução abrangente que oferece uma maneira eficaz e segura de criar, gerenciar e avaliar informações de clientes e cartões de crédito. Através da arquitetura de microservices, comunicação assíncrona e autenticação via tokens JWT, o projeto atende às necessidades de escalabilidade, segurança e eficiência do mundo moderno de desenvolvimento de software.

Principais tecnologias usadas:

    Spring Boot
    Spring Clould
    Spring OAuth 2
    Spring Security
    Spring Data
    Docker
    RabbitMQ
    OpenFeign
    Keycloak

O projeto conta com o Discovery Server, api gateway, um microservice para gerenciar os cartões,
um microservice para gerenciar os cliente e um microservice para se ligar com o ms de cartões e o ms de clientes

Endpoints de cada microservice

MSClientes:

Post: http://localhost:8080/clientes 

    Corpo da requisição (Retorna um header com a url para buscar o cliente pelo cpf):

    {
        "cpf" : "01234567890",
        "nome" : "Huguinho",
        "idade" : 27
    }

    Cria um novo cliente

Get: http://localhost:8080/clientes?cpf=01234567890 

    Faz a busca de um cliente pelo cpf

MSCartoes:

Post: http://localhost:8080/cartoes

    Corpo da requisição:

    {
        "nome" : "Bradesco Visa",
        "bandeira" : "VISA",
        "renda" : 3000,
        "limite" : 3500
    }

    Cria um novo cartão

Get: http://localhost:8080/cartoes?renda=5000 

    Busca um cartão pela renda do cliente

Get: http://localhost:8080/cartoes?cpf=01234567890

    Busca o(s) cartão(ões) de um cliente pelo seu cpf

MSAvaliadorCredito:

Get: http://localhost:8080/avaliacoes-credito/situacao-cliente?cpf=01234567890

    Retorna a situação de um cliente, pelo seu cpf. Exibe os dados do cliente e seus cartões

Post: http://localhost:8080/avaliacoes-credito

    Corpo da requisição:

    {
        "cpf" : "01234567890",
        "renda" : 5000
    }

    Avalia os cartões disponíveis e o limite baseado no cliente e na sua renda

Post: http://localhost:8080/avaliacoes-credito/solicitacoes-cartao

    Corpo da requisição:

    {
        "idCartao" : 1,
        "cpf" : "01234567890",
        "endereco" : "Rua x",
        "limiteLiberado" : 9450
    }

    Salva as informaçoes do cartão liberado, limite e cliente

Para fazer todas essas requisições, é preciso passar um token para a autenticação

Estou usando o keycloak para criar um token (JWT), rodando via docker

Para comunicação síncrona dos MS, estou usando o OpenFeign e para comunicação assíncrona, usando o serviço de mensageria RabbitMQ