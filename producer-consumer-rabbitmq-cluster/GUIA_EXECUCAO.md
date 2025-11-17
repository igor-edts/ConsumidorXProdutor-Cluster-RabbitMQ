# 🚀 Guia Completo de Execução - Producer Consumer RabbitMQ Cluster

## 📋 Índice
1. [Pré-requisitos](#pré-requisitos)
2. [Modo 1: Execução Local com RabbitMQ](#modo-1-execução-local-com-rabbitmq)
3. [Modo 2: Execução via Docker Compose](#modo-2-execução-via-docker-compose)
4. [Entendendo a Arquitetura](#entendendo-a-arquitetura)
5. [Monitoramento e Logs](#monitoramento-e-logs)
6. [Troubleshooting](#troubleshooting)

---

## 🔧 Pré-requisitos

### Para Modo 1 (Local)
- **Java 21 ou superior** 
  ```bash
  java -version
  # Deve exibir Java 21+
  ```
- **Maven 3.6.3 ou superior**
  ```bash
  mvn -version
  # Deve exibir Maven 3.6.3+
  ```
- **RabbitMQ Server rodando localmente** (porta 5672)
  - Windows: Baixar em https://www.rabbitmq.com/download.html
  - macOS: `brew install rabbitmq`
  - Linux: `sudo apt-get install rabbitmq-server`

### Para Modo 2 (Docker)
- **Docker Desktop instalado**
  ```bash
  docker --version
  docker-compose --version
  ```

---

## 🏃 Modo 1: Execução Local com RabbitMQ

### Passo 1: Instalar e Iniciar RabbitMQ

**Windows:**
```bash
# 1. Baixar e instalar RabbitMQ
# 2. Verificar se está rodando (task manager)
# 3. Acessar http://localhost:15672
#    Usuário: guest
#    Senha: guest
```

**macOS/Linux:**
```bash
# Iniciar RabbitMQ
rabbitmq-server

# Ou em background
sudo service rabbitmq-server start
```

### Passo 2: Clonar e Entrar no Projeto

```bash
# Clone o repositório
git clone https://github.com/igor-edts/ConsumidorXProdutor-Cluster-RabbitMQ.git

# Entre no diretório
cd producer-consumer-rabbitmq-cluster

# Checkout na branch com o upgrade
git checkout upgrade/spring-boot-3.5.x
```

### Passo 3: Compilar o Projeto

```bash
# Compilar todos os módulos
mvn clean install

# Esperado: BUILD SUCCESS
```

**Saída esperada:**
```
[INFO] Building producer-consumer-rabbitmq-cluster 1.0-SNAPSHOT
[INFO] ✓ common-lib
[INFO] ✓ producer-service-1
[INFO] ✓ consumer_service
[INFO] ✓ cluster-orchestrator
[INFO] BUILD SUCCESS
```

### Passo 4: Compilar o Orquestrador

```bash
# Compilar apenas o cluster-orchestrator
mvn -pl cluster-orchestrator -am package
```

### Passo 5: Executar o Orquestrador

```bash
# Executar o orquestrador (2 produtores + 4 consumidores)
java -jar cluster-orchestrator/target/cluster-orchestrator-1.0-SNAPSHOT.jar
```

**Saída esperada no console:**
```
[INFO] Starting ClusterOrchestratorApplication
...
[INFO] Starting producer-1 on localhost:8081
[INFO] Starting producer-2 on localhost:8082
[INFO] Starting consumer-1 on localhost:8083
[INFO] Starting consumer-2 on localhost:8084
[INFO] Starting consumer-3 on localhost:8085
[INFO] Starting consumer-4 on localhost:8086
...
[producer-1] Produto produzido: abc123 do tipo TYPE_1 em 3000 ms
[consumer-1] Produto consumido: abc123 do tipo TYPE_1 em 6000 ms
```

### Passo 6: Monitorar a Execução

**Console RabbitMQ Management:**
- Abra: http://localhost:15672
- Usuário: `guest`
- Senha: `guest`

**Visualizações úteis:**
- Abas: Queues → Visualizar filas `product.type1` e `product.type2`
- Ver mensagens sendo processadas em tempo real

---

## 🐳 Modo 2: Execução via Docker Compose

### Passo 1: Preparar o Ambiente

```bash
# Clone o repositório
git clone https://github.com/igor-edts/ConsumidorXProdutor-Cluster-RabbitMQ.git

# Entre no diretório
cd producer-consumer-rabbitmq-cluster

# Checkout na branch com o upgrade (se necessário)
git checkout upgrade/spring-boot-3.5.x
```

### Passo 2: Construir e Iniciar os Containers

```bash
# Construir as imagens e iniciar os containers
docker-compose up --build

# Para rodar em background
docker-compose up -d --build
```

**Saída esperada:**
```
Creating rabbitmq ... done
Creating cluster-orchestrator ... done
Attaching to rabbitmq, cluster-orchestrator
rabbitmq                 | Starting RabbitMQ 3-management...
cluster-orchestrator     | Starting ClusterOrchestratorApplication
...
```

### Passo 3: Verificar Status dos Containers

```bash
# Listar containers rodando
docker-compose ps

# Esperado:
# NAME                      STATUS
# rabbitmq                  Up X seconds
# cluster-orchestrator      Up X seconds
```

### Passo 4: Acessar RabbitMQ Management

- URL: http://localhost:15672
- Usuário: `guest`
- Senha: `guest`

### Passo 5: Ver Logs em Tempo Real

```bash
# Logs do orquestrador
docker-compose logs cluster-orchestrator -f

# Logs do RabbitMQ
docker-compose logs rabbitmq -f

# Logs de ambos
docker-compose logs -f
```

### Passo 6: Parar os Containers

```bash
# Parar e remover containers
docker-compose down

# Parar apenas
docker-compose stop

# Remover volumes também
docker-compose down -v
```

---

## 🏗️ Entendendo a Arquitetura

### Componentes

```
┌─────────────────────────────────────────────────────┐
│         Cluster Orchestrator (Main)                 │
│  Inicia 2 Produtores + 4 Consumidores               │
└───────────────┬─────────────────────────────────────┘
                │
        ┌───────┴───────┐
        │               │
    ┌───▼───────┐   ┌──▼─────────┐
    │ Producers │   │ Consumers  │
    │ (2x)      │   │ (4x)       │
    └───┬───────┘   └──┬─────────┘
        │               │
        └───────┬───────┘
                │
        ┌───────▼─────────┐
        │   RabbitMQ      │
        │ 2 Queues:       │
        │ • product.type1 │
        │ • product.type2 │
        └─────────────────┘
```

### Fluxo de Dados

```
1. PRODUÇÃO (a cada 1 segundo)
   ├─ Gera produto aleatório (TYPE_1 ou TYPE_2)
   ├─ Tipo 1: 3 segundos de produção
   ├─ Tipo 2: 5 segundos de produção
   └─ Envia para fila RabbitMQ

2. CONSUMO
   ├─ Consumer aguarda mensagens na fila
   ├─ Tipo 1: 6 segundos de consumo (3 * 2)
   ├─ Tipo 2: 10 segundos de consumo (5 * 2)
   └─ Registra nos logs quando consome

3. BALANCEAMENTO
   └─ RabbitMQ distribui entre 4 consumidores
```

### Módulos do Projeto

| Módulo | Função | Porta |
|--------|--------|-------|
| `common-lib` | Classes compartilhadas (Product, ProductType) | N/A |
| `producer-service-1` | Serviço que produz mensagens | 8081, 8082 |
| `consumer_service` | Serviço que consome mensagens | 8083-8086 |
| `cluster-orchestrator` | Orquestrador que inicia tudo | N/A |

---

## 📊 Monitoramento e Logs

### Visualizar Logs do Orquestrador

**Padrão de log:**
```
[producer-1] Produto produzido: <ID> do tipo <TYPE> em <TIME> ms
[consumer-1] Produto consumido: <ID> do tipo <TYPE> em <TIME> ms
```

### RabbitMQ Management Console

**Acessar filas:**
1. Vá para http://localhost:15672
2. Clique em "Queues"
3. Observe:
   - `product.type1`: Fila para produtos Tipo 1
   - `product.type2`: Fila para produtos Tipo 2
   - **Ready**: Mensagens aguardando consumo
   - **Unacked**: Mensagens sendo processadas
   - **Total**: Total de mensagens processadas

### Métricas no Console

```bash
# Para Modo Local:
# Procure por logs como:
[INFO] Produto produzido: c1d2e3f4 do tipo TYPE_1 em 3000 ms
[INFO] Produto consumido: c1d2e3f4 do tipo TYPE_1 em 6000 ms

# Taxa esperada:
# - 1 produto produzido por segundo por produtor
# - 2-3 produtos consumidos por segundo (distribuído entre 4 consumers)
```

---

## 🔍 Troubleshooting

### Problema: "Connection refused" - RabbitMQ não encontrado

**Solução:**
```bash
# Verificar se RabbitMQ está rodando (Local)
# Windows: Verificar Task Manager
# Linux/Mac: 
ps aux | grep rabbitmq

# Se não estiver rodando:
rabbitmq-server

# Ou verificar no Docker Compose:
docker-compose ps rabbitmq
```

### Problema: Porta 5672 já em uso

**Solução:**
```bash
# Windows: Encontrar e matar processo
netstat -ano | findstr :5672
taskkill /PID <PID> /F

# Linux/Mac:
lsof -i :5672
kill -9 <PID>

# Ou usar porta diferente no application.yml
```

### Problema: Java 21 não encontrado

**Solução:**
```bash
# Verificar instalação
java -version

# Se não tiver Java 21:
# Windows: Baixar de https://www.oracle.com/java/technologies/downloads/
# Linux: sudo apt-get install openjdk-21-jdk
# Mac: brew install openjdk@21

# Verificar JAVA_HOME
echo $JAVA_HOME
```

### Problema: Maven build falha

**Solução:**
```bash
# Limpar cache Maven
mvn clean

# Forçar download de dependências
mvn -U clean install

# Verificar versão do Maven
mvn -version
# Deve ser 3.6.3+
```

### Problema: Nenhuma mensagem aparece nos logs

**Solução:**
```bash
# Verificar se o cluster-orchestrator iniciou corretamente
# Procure por linha como:
# [INFO] Starting ClusterOrchestratorApplication

# Verificar RabbitMQ:
# 1. Console: http://localhost:15672
# 2. Aba "Connections" - Deve ter múltiplas conexões
# 3. Aba "Channels" - Deve ter múltiplos canais

# Tentar aumentar log verbosity:
# Adicionar ao application.yml:
# logging:
#   level:
#     root: DEBUG
```

### Problema: Docker Container sai imediatamente

**Solução:**
```bash
# Ver logs detalhados
docker-compose logs cluster-orchestrator

# Verificar se Dockerfile está correto
docker build -t test .

# Verificar conectividade:
docker run -it --rm test ping rabbitmq
```

---

## 📈 Performance e Otimizações

### Configurações Recomendadas

```yaml
# Aumentar número de consumidores
cluster-orchestrator/src/main/java/com/example/orchestrator/ClusterOrchestratorApplication.java
# Alterar: orchestrator.startConsumers(4) → orchestrator.startConsumers(8)

# Ajustar taxa de produção
producer-service-1/src/main/java/com/example/producer1/service/ProductProducerService.java
# Alterar: @Scheduled(fixedRate = 1000) → @Scheduled(fixedRate = 500)

# Aumentar prefetch (quantidade de mensagens pré-carregadas)
# Adicionar ao application.yml:
spring:
  rabbitmq:
    listener:
      simple:
        prefetch: 10
```

---

## ✅ Checklist de Execução

- [ ] Java 21+ instalado e configurado
- [ ] Maven 3.6.3+ instalado
- [ ] RabbitMQ instalado (para Modo 1) ou Docker (para Modo 2)
- [ ] Repositório clonado
- [ ] Branch `upgrade/spring-boot-3.5.x` ativa
- [ ] `mvn clean install` executado com sucesso
- [ ] Orquestrador iniciado
- [ ] RabbitMQ Management acessível
- [ ] Logs de produção/consumo aparecem
- [ ] Fila `product.type1` e `product.type2` com mensagens

---

## 📞 Suporte

Se encontrar problemas:
1. Verifique o [Troubleshooting](#troubleshooting)
2. Consulte os logs (`docker-compose logs` ou console)
3. Verifique RabbitMQ Management em http://localhost:15672
4. Abra uma issue no repositório: https://github.com/igor-edts/ConsumidorXProdutor-Cluster-RabbitMQ/issues

---

**Versão atualizada para Spring Boot 3.5.7** ✅  
Data: 17 de novembro de 2025
