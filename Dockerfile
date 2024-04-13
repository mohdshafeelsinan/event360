FROM eclipse-temurin:17

WORKDIR /app

COPY target/otpservice-0.0.11-SNAPSHOT.jar .

EXPOSE 8080

CMD ["java", "-jar", "otpservice-0.0.11-SNAPSHOT.jar"]