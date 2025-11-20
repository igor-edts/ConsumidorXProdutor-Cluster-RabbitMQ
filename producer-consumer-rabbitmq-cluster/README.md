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