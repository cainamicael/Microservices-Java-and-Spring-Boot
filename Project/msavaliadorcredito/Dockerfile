#Fazendo o build
FROM maven:3.8.5-openjdk-17 as build 
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

#Criando imagem com o jar
FROM openjdk:17
WORKDIR /app
COPY --from=build ./app/target/*.jar ./app.jar 

#Criando parâmetros
ARG EUREKA_SERVER=localhost
ARG RABBITMQ_SERVER=rabbitmq-host

ENTRYPOINT java -jar app.jar