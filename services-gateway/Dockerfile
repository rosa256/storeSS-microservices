FROM openjdk:8-jdk-alpine

WORKDIR app-services-gateway
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /app-services-gateway/wait
RUN chmod +x /app-services-gateway
RUN chmod +x /app-services-gateway/wait
RUN apk --no-cache add curl

ARG JAR_FILE=target/services-gateway*.jar
COPY ${JAR_FILE} /app-services-gateway

EXPOSE 8000
CMD ./wait && java -jar services-gateway*.jar