# ⚡ Comece em 2 Minutos

## Opção 1: Docker (Mais Fácil!)

Cola o comando abaixo no terminal e espera:

```bash
git clone https://github.com/igor-edts/ConsumidorXProdutor-Cluster-RabbitMQ.git
cd ConsumidorXProdutor-Cluster-RabbitMQ
git checkout upgrade/spring-boot-3.5.x

docker-compose up --build
```

**Pronto!** A aplicação já está rodando! 🚀

Você vai ver nos logs:
```
[producer-1] Produto produzido: abc123 do tipo TYPE_1 em 3000 ms
[consumer-1] Produto consumido: abc123 do tipo TYPE_1 em 6000 ms
```

Depois abra no navegador:
- **http://localhost:15672**
- Usuário: `guest`
- Senha: `guest`

**Para parar:**
```bash
# Aperte Ctrl + C no terminal
# Ou em outro terminal rode:
docker-compose down
```

**Para rodar novamente (sem rebuild):**
```bash
docker-compose up
```