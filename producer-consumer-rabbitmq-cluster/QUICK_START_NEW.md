# ⚡ Comece em 2 Minutos

## Opção 1: Docker (Mais Fácil!)

Cola o comando abaixo no terminal e espera:

```bash
git clone https://github.com/igor-edts/ConsumidorXProdutor-Cluster-RabbitMQ.git
cd producer-consumer-rabbitmq-cluster
git checkout upgrade/spring-boot-3.5.x

docker-compose up --build
```

Depois abra no navegador:
- **http://localhost:15672**
- Usuário: `guest`
- Senha: `guest`

**Pronto!** Você vai ver os logs de produção e consumo aparecendo.

Para parar:
```bash
docker-compose down
```

---

## Opção 2: Rodando no Seu Computador

Se prefere sem Docker:

```bash
git clone https://github.com/igor-edts/ConsumidorXProdutor-Cluster-RabbitMQ.git
cd producer-consumer-rabbitmq-cluster
git checkout upgrade/spring-boot-3.5.x

mvn clean install
mvn -pl cluster-orchestrator -am package
java -jar cluster-orchestrator/target/cluster-orchestrator-1.0-SNAPSHOT.jar
```

**Importante:** Inicie o RabbitMQ antes!
- Windows: Procure "RabbitMQ" no Menu Iniciar
- Mac: `brew services start rabbitmq`
- Linux: `sudo systemctl start rabbitmq-server`

Depois abra no navegador:
- **http://localhost:15672**
- Usuário: `guest`
- Senha: `guest`

---

## O Que Você Vai Ver

No terminal:
```
[producer-1] Produto produzido: abc123 do tipo TYPE_1 em 3000 ms
[consumer-1] Produto consumido: abc123 do tipo TYPE_1 em 6000 ms
[producer-2] Produto produzido: def456 do tipo TYPE_2 em 5000 ms
[consumer-2] Produto consumido: def456 do tipo TYPE_2 em 10000 ms
```

No RabbitMQ Management:
- Veja as 2 filas funcionando (`product.type1` e `product.type2`)
- Veja as conexões e canais ativos
- Acompanhe as mensagens sendo processadas

---

## Se Algo der Errado

**"RabbitMQ não responde"**
- Verifique se está rodando
- No Docker: `docker-compose ps` deve mostrar rabbitmq ativo
- Localmente: Procure RabbitMQ na lista de processos

**"Porta já em uso"**
```bash
# Windows: netstat -ano | findstr :5672
# Mac/Linux: lsof -i :5672
# Depois feche o programa que está usando
```

**"Java 21 não encontrado"**
```bash
java -version
# Se não for 21, baixe em: https://www.oracle.com/java/technologies/downloads/
```

---

## Quer Mais Detalhes?

Ver [GUIA_EXECUCAO.md](GUIA_EXECUCAO.md) para um passo a passo completo

Ver [SECURITY_VALIDATION.md](SECURITY_VALIDATION.md) para validação de segurança

---

**Pronto! Aplicação rodando!** 🎉
