# ─── Build stage ───
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN ./mvnw -ntp package -DskipTests 2>/dev/null || (apk add --no-cache maven && mvn -ntp package -DskipTests)

# ─── Runtime stage ───
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
  CMD wget -qO- http://localhost:8080/api/v1/health || exit 1
ENTRYPOINT ["java", "-jar", "app.jar"]
