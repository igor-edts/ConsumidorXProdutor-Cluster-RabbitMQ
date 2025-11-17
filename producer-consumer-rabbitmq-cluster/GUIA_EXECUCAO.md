# Guia de Execução - Rodando o Projeto

Este guia vai te ajudar a colocar a aplicação em funcionamento. Escolha a forma que mais faz sentido pra você!

## O Que Você Vai Precisar

### Opção 1: Rodando localmente (no seu computador)

Você vai precisar de:
- **Java 21** - Se não souber o que é, instale daqui: https://www.oracle.com/java/technologies/downloads/
- **Maven** - Instalador em: https://maven.apache.org/download.cgi
- **RabbitMQ** - A fila de mensagens da aplicação. Baixe em: https://www.rabbitmq.com/download.html

Para verificar se já tem tudo:
```bash
java -version
mvn -version
```

Se aparecer um número de versão, você já tem instalado!

### Opção 2: Usando Docker (Mais fácil!)

Você só precisa:
- **Docker Desktop** - Baixe em: https://www.docker.com/products/docker-desktop

Ou na linha de comando:
```bash
docker --version
docker-compose --version
```

---

## Rodando Localmente (no seu computador)

### Passo 1: Inicie o RabbitMQ

O RabbitMQ é como um "carteiro" que entrega as mensagens entre produtores e consumidores.

**Se você está no Windows:**
- Procure por "RabbitMQ Service" no Menu Iniciar
- Clique para iniciar (se não estiver iniciado)
- Abra no navegador: http://localhost:15672
- Usuário: guest
- Senha: guest

**Se você está no Mac ou Linux:**
```bash
rabbitmq-server
```
E abra no navegador: http://localhost:15672

### Passo 2: Pegue o código do projeto

```bash
git clone https://github.com/igor-edts/ConsumidorXProdutor-Cluster-RabbitMQ.git
cd producer-consumer-rabbitmq-cluster
git checkout upgrade/spring-boot-3.5.x
```

### Passo 3: Prepare a aplicação

Este comando baixa todas as dependências (bibliotecas) que a aplicação precisa:

```bash
mvn clean install
```

Espere ele terminar. Quando ver "BUILD SUCCESS" é porque funcionou!

### Passo 4: Compile só o que vai rodar

```bash
mvn -pl cluster-orchestrator -am package
```

### Passo 5: Coloque a aplicação para funcionar!

```bash
java -jar cluster-orchestrator/target/cluster-orchestrator-1.0-SNAPSHOT.jar
```

Você vai ver aparecer coisas assim na tela:
```
[producer-1] Produto produzido: abc123 do tipo TYPE_1 em 3000 ms
[consumer-1] Produto consumido: abc123 do tipo TYPE_1 em 6000 ms
```

Se aparecer isso, significa que está funcionando! 

### Passo 6: Veja funcionando no navegador

Abra: http://localhost:15672
- Usuário: guest
- Senha: guest

Lá você vê:
- **Queues**: As filas com as mensagens esperando
- **Connections**: Quantas conexões estão ativas
- **Channels**: Os canais de comunicação

---

## Usando Docker (Mais Fácil!)

### Passo 1: Pegue o código

```bash
git clone https://github.com/igor-edts/ConsumidorXProdutor-Cluster-RabbitMQ.git
cd producer-consumer-rabbitmq-cluster
git checkout upgrade/spring-boot-3.5.x
```

### Passo 2: Coloque pra rodar!

Este comando faz tudo sozinho - baixa as imagens, instala, e inicia:

```bash
docker-compose up --build
```

Você vai ver um monte de linhas aparecendo. Não se preocupe, é normal!

Se quiser rodar em background (sem ver os logs):
```bash
docker-compose up -d --build
```

### Passo 3: Veja os containers rodando

```bash
docker-compose ps
```

Você vai ver algo assim:
```
NOME                    STATUS
rabbitmq                Up 5 seconds
cluster-orchestrator    Up 5 seconds
```

### Passo 4: Abra no navegador

Vá para: http://localhost:15672
- Usuário: guest
- Senha: guest

Lá você vê tudo funcionando!

### Passo 5: Veja os logs

Para ver o que está acontecendo:

```bash
# Ver tudo
docker-compose logs -f

# Ou só o orquestrador
docker-compose logs cluster-orchestrator -f
```

### Passo 6: Parar tudo

```bash
docker-compose down
```

---

## Como Funciona Por Baixo dos Panos

Tenha em mente que o projeto tem estes personagens:

1. **Produtores** - Criam "produtos" (mensagens) a cada segundo
2. **RabbitMQ** - A fila que armazena as mensagens
3. **Consumidores** - Pegam as mensagens e processam

```
Produtores                RabbitMQ                Consumidores
    │                      Fila 1                      │
    ├─ Produto 1 ──────────▶ product.type1 ──────────▶ Consumidor 1
    ├─ Produto 2 ──────────▶ product.type2 ──────────▶ Consumidor 2
    ├─ Produto 3 ──────────▶ product.type1 ──────────▶ Consumidor 3
    ├─ Produto 4 ──────────▶ product.type2 ──────────▶ Consumidor 4
    │                                   
    └─ (e por aí vai...)
```

### O tempo que cada coisa leva

- **Produção de Tipo 1**: 3 segundos
- **Produção de Tipo 2**: 5 segundos
- **Consumo de Tipo 1**: 6 segundos (o dobro)
- **Consumo de Tipo 2**: 10 segundos (o dobro)

Tudo isso roda em paralelo, então vários produtos podem estar sendo produzidos e consumidos ao mesmo tempo!

---

## Monitorando a Aplicação

### O Que Você Vai Ver nos Logs

Nos logs você vê mensagens assim:

```
[producer-1] Produto produzido: abc123 do tipo TYPE_1 em 3000 ms
[consumer-1] Produto consumido: abc123 do tipo TYPE_1 em 6000 ms
[producer-2] Produto produzido: def456 do tipo TYPE_2 em 5000 ms
[consumer-2] Produto consumido: def456 do tipo TYPE_2 em 10000 ms
```

Isso significa que está funcionando!

### Dashboard do RabbitMQ

Abra http://localhost:15672 no navegador (guest/guest)

Lá você pode ver:

- **Queues**: As duas filas (`product.type1` e `product.type2`) e quantas mensagens estão esperando
- **Connections**: Quantas conexões estão ativas
- **Channels**: Quantos canais abertos

---

## Se Algo Não Funcionar

### "Connection refused" ou RabbitMQ não funciona

Meio que a aplicação não conseguiu falar com o RabbitMQ.

Verifique se o RabbitMQ está rodando:
```bash
# Abra http://localhost:15672 no navegador
# Se não abrir, RabbitMQ não está rodando
```

### Porta já está em uso

Se vir "Port already in use":

```bash
# Windows
netstat -ano | findstr :5672
# Depois feche o programa que está usando

# Mac/Linux
lsof -i :5672
kill -9 <numero que aparecer>
```

### Java 21 não está instalado

```bash
java -version
# Se não mostrar 21, baixe de:
# https://www.oracle.com/java/technologies/downloads/
```

### Nada aparece na tela

- Verifique se o RabbitMQ está rodando
- Veja se não tem erro na compilação
- Tente rodar de novo

### Docker não funciona

Certifique-se que Docker Desktop está rodando:
```bash
docker --version
```

Se não tiver:
```bash
# Baixe em: https://www.docker.com/products/docker-desktop
```

---

## Ficou com Dúvida?

Você pode:

1. Verificar os logs da aplicação (procure por erros vermelhos)
2. Abrir http://localhost:15672 e ver se as filas estão criadas
3. Tentar executar tudo do zero

E pronto! Sua aplicação está funcionando! 🎉
