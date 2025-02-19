FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY ./odc/build/libs/odc-0.0.1-SNAPSHOT.jar /app/odc.jar
COPY ./filtering /app/filtering
COPY ./data /app/data

CMD ["java", "-jar", "odc.jar"]