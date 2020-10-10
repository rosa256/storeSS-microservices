FROM openjdk:8-jdk-alpine
EXPOSE 8761
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} services-registry.jar
ENTRYPOINT ["java","-jar", "services-registry.jar"]
