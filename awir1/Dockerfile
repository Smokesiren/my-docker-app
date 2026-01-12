# Lekka baza z Javą 21
FROM eclipse-temurin:21-jdk-alpine

# Argument z nazwą pliku JAR
ARG JAR_FILE=target/awir2-0.0.1-SNAPSHOT.jar

# Ustaw katalog roboczy
WORKDIR /app

# Skopiuj plik JAR do kontenera
COPY ${JAR_FILE} app.jar

# Opcjonalne parametry JVM
ENV JAVA_OPTS=""

# Otwórz port 8080
EXPOSE 8099

# Komenda startowa
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
