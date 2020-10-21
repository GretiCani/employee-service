FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/*.jar
WORKDIR /opt/app
COPY ${JAR_FILE} employee-service.jar
ENTRYPOINT ["java","-jar","-Djava.security.egd=file:/dev/./urandom","employee-service.jar"]
