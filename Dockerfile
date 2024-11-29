FROM openjdk:21
COPY "./target/gestortareas-0.0.1-SNAPSHOT.jar" "gestortareas.jar"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "gestortareas.jar"]