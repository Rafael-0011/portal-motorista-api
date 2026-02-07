# Estágio de build: Compila o projeto com JDK 21
FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /opt/portal-motorista-api

# Copia o código-fonte para o contêiner
COPY . .

# Compila o projeto
RUN bash ./mvnw dependency:go-offline
RUN bash ./mvnw clean install -DskipTests

# Estágio de execução: Usa JRE 21 para rodar a aplicação
FROM eclipse-temurin:21-jre-jammy

WORKDIR /opt/app

# Copia o JAR gerado na etapa de build para o ambiente de execução
COPY --from=builder /opt/portal-motorista-api/target/*.jar ./app.jar

# Expõe a porta 8080
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "./app.jar"]