# ---- Build stage ----
FROM eclipse-temurin:24-jdk AS builder
WORKDIR /usr/src/app

# Copy Maven project files
COPY pom.xml .
COPY snake-game/ .

# Download dependencies first (caching)
RUN mvn dependency:go-offline -B

# Build the project
RUN mvn clean package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:24-jre
WORKDIR /usr/src/app

# Copy the built artifact
COPY --from=builder /usr/src/app/target/snake-game-1.0-SNAPSHOT.jar ./snake-game.jar

# Run the JAR
ENTRYPOINT ["java", "-jar", "snake-game.jar"]

