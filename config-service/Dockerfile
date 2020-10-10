FROM openjdk:8-jdk-alpine
EXPOSE 8888
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} config-services.jar
ENTRYPOINT ["java","-jar","config-services.jar"]
