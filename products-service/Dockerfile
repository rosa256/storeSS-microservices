FROM openjdk:8-jdk-alpine

WORKDIR /app-products-service
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /app-products-service/wait
RUN chmod +x /app-products-service
RUN chmod +x /app-products-service/wait
RUN apk --no-cache add curl

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app-products-service

EXPOSE 8083
CMD ./wait && java -jar products-service-0.0.1-SNAPSHOT.jar