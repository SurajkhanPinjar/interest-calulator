# ============================
# 1. Build Stage
# ============================
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:resolve

COPY src ./src

RUN mvn -q -e -DskipTests package


# ============================
# 2. Runtime Stage
# ============================
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8082

ENV JAVA_OPTS=""
ENV API_KEY=DEV_LOCAL_KEY_123

# Support Railway's dynamic PORT variable OR default to 8082
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-8082} -jar app.jar"]