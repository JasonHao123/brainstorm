from  openjdk:8-jdk

copy apache-ofbiz-16.11.04.java /apache-ofbiz-16.11.04.zip
run unzip  apache-ofbiz-16.11.04.zip
run mkdir /apache-ofbiz-16.11.04/framework/entity/lib/
copy mysql-connector-java-5.1.46.java /apache-ofbiz-16.11.04/framework/entity/lib/mysql-connector-java-5.1.46.jar

copy build.gradle /apache-ofbiz-16.11.04/
copy gradle-2.13-bin.java /apache-ofbiz-16.11.04/gradle/wrapper/gradle-2.13-bin.zip
copy gradle-wrapper.properties /apache-ofbiz-16.11.04/gradle/wrapper/

EXPOSE 8443/tcp
VOLUME /apache-ofbiz-16.11.04/framework/entity/config
workdir  apache-ofbiz-16.11.04
run  ./gradlew loadDefault -x test
cmd  ./gradlew ofbiz  -x test