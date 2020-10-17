FROM openjdk:8-jdk-alpine

WORKDIR /app-orders-service
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /app-orders-service/wait
RUN chmod +x /app-orders-service
RUN chmod +x /app-orders-service/wait
RUN apk --no-cache add curl

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app-orders-service

EXPOSE 8081
CMD ./wait && java -jar orders-service*.jar
                       #orders-service-0.0.1-SNAPSHOT.jar

