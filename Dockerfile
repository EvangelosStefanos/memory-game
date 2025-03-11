# Docker version 27.3.1
FROM maven:3.9.9-eclipse-temurin-23-alpine

WORKDIR /app

COPY src/ src/

COPY pom.xml pom.xml

# Compile to jar
RUN mvn package

# Run jar
CMD [ "java", "-cp", "target/memory-game-1.0-SNAPSHOT.jar", "org.memory.game.MemoryGame" ]
