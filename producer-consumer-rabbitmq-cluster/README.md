# Producer Consumer RabbitMQ Cluster

Este é um projeto demonstrativo de um sistema produtor-consumidor utilizando RabbitMQ em cluster.

## Tecnologias Utilizadas

- Java 21 (LTS)
- Spring Boot 3.5.7 (última versão de patch do 3.5.x)
- Spring Framework 6.2.12
- Spring AMQP 3.2.8 (RabbitMQ)
- Maven 3.9+
- Lombok 1.18.38

## Estrutura do Projeto

O projeto é composto por múltiplos módulos:

- `common-lib`: Biblioteca compartilhada contendo modelos e utilitários comuns
- `producer-service-1`: Serviço produtor que gera produtos aleatórios
- `consumer_service`: Serviço que consome os produtos
- `cluster-orchestrator`: Serviço principal que chama os demais serviços

## Pré-requisitos

- Java 21 ou superior
- Maven 3.6.3 ou superior
- RabbitMQ Server (cluster configurado)
- Docker Desktop (opcional)

## Configuração do RabbitMQ

O projeto utiliza duas filas principais:
- `product.type1`: Para produtos do Tipo 1
- `product.type2`: Para produtos do Tipo 2

## Como Executar

**Para um guia passo a passo completo, consulte [GUIA_EXECUCAO.md](GUIA_EXECUCAO.md)**

Este guia inclui:
- ✅ Modo 1: Execução Local com RabbitMQ
- ✅ Modo 2: Execução via Docker Compose
- ✅ Entendimento da arquitetura
- ✅ Monitoramento e logs
- ✅ Troubleshooting

## Execução Rápida

### Modo 1: Local
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

4. Execute o serviço Central:

### Orquestrador (2 produtores + 4 consumidores)
Para subir todos os processos ao mesmo tempo
```bash
mvn -pl cluster-orchestrator -am package
java -jar cluster-orchestrator/target/cluster-orchestrator-1.0-SNAPSHOT.jar
```
## Modo 2 Via DOCKER
1. Clone o repositório:
```bash
git clone https://github.com/igor-edts/ConsumidorXProdutor-Cluster-RabbitMQ.git
```

2. Entre no diretório do projeto:
```bash
cd producer-consumer-rabbitmq-cluster
```
3. Gere as imagens do Projeto e do RabbitMQ
```bash
docker compose up --build
```
4. Acesse no Navegador
- http://localhost:15672
-   Usuario: guest
-   Senha: guest


## Funcionalidades

### Produtor (producer-service-1)
- Gera produtos aleatórios a cada 1 segundo
- Alterna entre produtos do Tipo 1 e Tipo 2
- Simula tempo de produção variável
- Registra logs de produção
- Consumers consomem os produtos com o dobro do tempo de criação

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

## Validação de Segurança

✅ **Spring Boot 3.5.7 - Zero CVEs**

O projeto foi atualizado para Spring Boot 3.5.7 com validação completa de segurança via OWASP Dependency-Check. Nenhuma vulnerabilidade conhecida foi detectada nas dependências.

Consulte [SECURITY_VALIDATION.md](SECURITY_VALIDATION.md) para detalhes completos.

## Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
