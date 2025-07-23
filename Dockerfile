# Estágio 1: Build da Aplicação com Maven
# Alterado para usar o JDK 21, conforme o seu ambiente local.
FROM maven:3.9-eclipse-temurin-21 AS build

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia apenas os arquivos necessários para baixar as dependências primeiro.
COPY pom.xml .
COPY .mvn/ .mvn/

# Baixa todas as dependências do projeto
RUN mvn dependency:go-offline

# Agora, copia o resto do código-fonte do seu projeto
COPY src/ src/

# Compila a aplicação, gera o arquivo .jar e pula os testes
RUN mvn clean install -DskipTests


# Estágio 2: Imagem Final de Execução
# Usamos uma imagem base do Java 21 (JRE) muito enxuta.
FROM eclipse-temurin:21-jre-jammy

# Define o diretório de trabalho
WORKDIR /app

# Expõe a porta que a sua API vai usar (padrão 8080).
EXPOSE 8080

# Copia o arquivo .jar que foi gerado no estágio 'build' para a imagem final.
COPY --from=build /app/target/*.jar app.jar

# Comando final que será executado quando o contêiner iniciar.
ENTRYPOINT ["java", "-jar", "app.jar"]