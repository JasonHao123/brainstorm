FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8090/tcp
ADD customer-service.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]