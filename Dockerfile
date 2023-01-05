FROM openjdk:11

# The port your service will listen on
EXPOSE 8080

# Copy the service JAR
COPY target/heytrade-pokemon-app-1.0.0-SNAPSHOT.jar /heytrade-pokemon-app.jar

# The command to run
CMD ["java", "-jar", "heytrade-pokemon-app.jar"]
