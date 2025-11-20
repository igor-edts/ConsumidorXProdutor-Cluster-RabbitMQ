# Producer Consumer RabbitMQ Cluster

Este é um projeto que mostra como um sistema de produção e consumo de mensagens funciona usando RabbitMQ.

Pense assim: **Produtores criam produtos, RabbitMQ armazena em filas, Consumidores pegam e processam.**

## O Que Você Precisa

- **Java 21** - A linguagem de programação
- **Maven** - Para compilar o projeto
- **RabbitMQ** - O sistema de filas de mensagens
- **Docker** (opcional) - Para rodar tudo junto

## Como Funciona

O projeto tem:

1. **2 Produtores** - Criam "produtos" (mensagens) o tempo todo
2. **4 Consumidores** - Pegam as mensagens e processam
3. **RabbitMQ** - A fila que armazena as mensagens entre produtores e consumidores

Tudo isso roda em paralelo, simulando um sistema real de processamento!

## Começando Rápido

### Usando Docker (Mais Fácil - 2 minutos)

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
cd producer-consumer-rabbitmq-cluster
git checkout upgrade/spring-boot-3.5.x

docker-compose up --build
```

Depois abra: http://localhost:15672 (guest/guest)

### Rodando no seu Computador (3-5 minutos)

```bash
git clone https://github.com/igor-edts/ConsumidorXProdutor-Cluster-RabbitMQ.git
cd producer-consumer-rabbitmq-cluster
git checkout upgrade/spring-boot-3.5.x

mvn clean install
mvn -pl cluster-orchestrator -am package
java -jar cluster-orchestrator/target/cluster-orchestrator-1.0-SNAPSHOT.jar
```

Depois abra: http://localhost:15672 (guest/guest)

## Passo a Passo Detalhado

**Leia [GUIA_EXECUCAO.md](GUIA_EXECUCAO.md)** para um guia completo com todas as instruções!

## O Que Você Vai Ver

Nos logs:
```
[producer-1] Produto produzido: abc123 do tipo TYPE_1 em 3000 ms
[consumer-1] Produto consumido: abc123 do tipo TYPE_1 em 6000 ms
```

No RabbitMQ Management (http://localhost:15672):
- Filas com as mensagens esperando
- Conexões ativas
- Canais abertos

## Versão Atual

- **Java**: 21 (LTS)
- **Spring Boot**: 3.5.7 (atualizado e seguro - zero vulnerabilidades!)
- **Spring Framework**: 6.2.12
- **RabbitMQ**: Suportado via Spring AMQP 3.2.8

Ver [SECURITY_VALIDATION.md](SECURITY_VALIDATION.md) para mais sobre segurança.
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
