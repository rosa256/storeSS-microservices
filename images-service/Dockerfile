FROM openjdk:8-jdk-alpine

WORKDIR /app-images-service
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /app-images-service/wait
RUN chmod +x /app-images-service
RUN chmod +x /app-images-service/wait
RUN apk --no-cache add curl

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app-images-service/

EXPOSE 8084
CMD ./wait && java -jar images-service-0.0.1-SNAPSHOT.jar