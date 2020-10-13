FROM openjdk:8-jdk-alpine
EXPOSE 8084
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} images-service.jar
ENTRYPOINT ["java", "-jar", "images-service.jar"]