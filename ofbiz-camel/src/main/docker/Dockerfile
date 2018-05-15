from  openjdk:8-jdk

copy apache-ofbiz-16.11.04.zip  / 

run   unzip  apache-ofbiz-16.11.04.zip
copy gradle-wrapper.properties /apache-ofbiz-16.11.04/gradle/wrapper/
copy gradle-2.13-bin.zip /apache-ofbiz-16.11.04/gradle/wrapper/
copy mysql-connector-java-5.1.46.jar /apache-ofbiz-16.11.04/framework/entity/lib/
copy build.gradle /apache-ofbiz-16.11.04/

EXPOSE 8088/tcp
EXPOSE 8443/tcp
VOLUME /apache-ofbiz-16.11.04/framework/entity/config
VOLUME /apache-ofbiz-16.11.04/specialpurpose
workdir  apache-ofbiz-16.11.04
run  ./gradlew loadDefault
cmd  ./gradlew ofbiz  -x test
