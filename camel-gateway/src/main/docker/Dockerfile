FROM openjdk:8-jdk-alpine
copy ofbiz-vmapacheorg.crt /
run keytool -import -trustcacerts -keystore /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/cacerts -storepass changeit -noprompt -alias ofbiz -file /ofbiz-vmapacheorg.crt

VOLUME /tmp
EXPOSE 8080/tcp
ADD camel-gateway.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]