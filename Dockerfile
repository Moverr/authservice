FROM openjdk:17

WORKDIR /app

COPY build/libs/auth-v1.0.0-alpha.jar app.jar

EXPOSE 8082

CMD ["java", "-jar", "/app/app.jar"]
