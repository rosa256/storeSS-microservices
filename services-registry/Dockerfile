FROM openjdk:8-jdk-alpine
WORKDIR /app-services-registry

# Add wait script to the image - script pulled from https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /wait
#COPY wait /app-services-registry/
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /app-services-registry/wait
RUN chmod +x /app-services-registry
RUN chmod +x /app-services-registry/wait
RUN apk --no-cache add curl

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app-services-registry/

EXPOSE 8761
CMD ./wait && java -jar services-registry-0.0.1-SNAPSHOT.jar