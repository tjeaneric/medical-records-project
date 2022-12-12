FROM openjdk:17-alpine
RUN apk update && apk add --no-cache tzdata
EXPOSE 3000
ARG JAR_FILE=target/artifacts/medical_records_jar/*.jar
COPY ${JAR_FILE} medical-records.jar
ENTRYPOINT ["java","-jar","/medical-records.jar"]