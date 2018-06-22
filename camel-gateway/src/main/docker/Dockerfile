FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8443/tcp
ADD camel-gateway.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]