FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia apenas os POMs para aproveitar cache de dependências
COPY pom.xml .
COPY common-lib/pom.xml common-lib/
COPY producer-service-1/pom.xml producer-service-1/
COPY consumer_service/pom.xml consumer_service/
COPY cluster-orchestrator/pom.xml cluster-orchestrator/

# Baixa dependências sem copiar código-fonte ainda (melhor cache)
RUN mvn -B -pl cluster-orchestrator -am -DskipTests dependency:go-offline

# Agora copia o código-fonte inteiro
COPY . .

# Build apenas o módulo do orquestrador (ele empacota produtores e consumidores)
RUN mvn -B -pl cluster-orchestrator -am -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/cluster-orchestrator/target/cluster-orchestrator-1.0-SNAPSHOT.jar app.jar

# Valores padrão esperados no docker-compose
ENV SPRING_RABBITMQ_HOST=rabbitmq \
    SPRING_RABBITMQ_PORT=5672 \
    SPRING_RABBITMQ_USERNAME=guest \
    SPRING_RABBITMQ_PASSWORD=guest

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
