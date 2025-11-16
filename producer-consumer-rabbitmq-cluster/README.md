# Producer Consumer RabbitMQ Cluster

Este é um projeto demonstrativo de um sistema produtor-consumidor utilizando RabbitMQ em cluster. O projeto utiliza Spring Boot e está configurado com as tecnologias mais recentes.

## Tecnologias Utilizadas

- Java 21 (LTS)
- Spring Boot 3.2.0
- Spring AMQP (RabbitMQ)
- Maven
- Lombok

## Estrutura do Projeto

O projeto é composto por múltiplos módulos:

- `common-lib`: Biblioteca compartilhada contendo modelos e utilitários comuns
- `producer-service-1`: Serviço produtor que gera produtos aleatórios

## Pré-requisitos

- Java 21 ou superior
- Maven 3.6.3 ou superior
- RabbitMQ Server (cluster configurado)

## Configuração do RabbitMQ

O projeto utiliza duas filas principais:
- `product.type1`: Para produtos do Tipo 1
- `product.type2`: Para produtos do Tipo 2

## Como Executar

1. Clone o repositório:
```bash
git clone https://github.com/igor-edts/ConsumidorXProdutor-Cluster-RabbitMQ.git
```

2. Entre no diretório do projeto:
```bash
cd producer-consumer-rabbitmq-cluster
```

3. Compile o projeto:
```bash
mvn clean install
```

4. Execute o serviço produtor:

### Orquestrador (2 produtores + 4 consumidores)
Para subir tudo de uma vez num único processo (logs centralizados):
```bash
mvn -pl cluster-orchestrator -am package
java -jar cluster-orchestrator/target/cluster-orchestrator-1.0-SNAPSHOT.jar
```

## Funcionalidades

### Produtor (producer-service-1)
- Gera produtos aleatórios a cada 1 segundo
- Alterna entre produtos do Tipo 1 e Tipo 2
- Simula tempo de produção variável
- Registra logs de produção

## Configuração

As configurações do RabbitMQ e outras propriedades podem ser ajustadas no arquivo `application.yml` de cada serviço.

## Desenvolvimento

Para começar a desenvolver no projeto:

1. Importe o projeto em sua IDE favorita como um projeto Maven
2. Certifique-se de que o JDK 21 está configurado corretamente
3. Execute `mvn clean install` para baixar todas as dependências

## Recursos do Java 21

O projeto está configurado para utilizar os recursos mais recentes do Java 21, incluindo:
- Pattern Matching para switch
- Record Patterns
- Sequenced Collections
- Virtual Threads
- String Templates (Preview)

## Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
