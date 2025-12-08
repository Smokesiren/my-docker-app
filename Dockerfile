# Lekka baza z Javą 21
FROM eclipse-temurin:21-jdk-alpine
# Argument z nazwą JAR-a (domyślnie jak z Mavena)
ARG JAR_FILE=target/awir-0.0.1-SNAPSHOT.jar
# Katalog roboczy w kontenerze
WORKDIR /app
# Skopiuj JAR do kontenera
COPY ${JAR_FILE} app.jar
# Zmienna na dodatkowe opcje JVM (opcjonalnie)
ENV JAVA_OPTS=""
# Aplikacja Spring Boot słucha domyślnie na 8080
EXPOSE 8099
# Komenda startowa
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
