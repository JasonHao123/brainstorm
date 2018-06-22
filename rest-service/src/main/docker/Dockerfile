from  openjdk:8-jdk

run wget http://www.apache.org/dist/ofbiz/apache-ofbiz-16.11.04.zip  -O /apache-ofbiz-16.11.04.zip
run unzip  apache-ofbiz-16.11.04.zip
run mkdir /apache-ofbiz-16.11.04/framework/entity/lib/
run wget http://search.maven.org/remotecontent?filepath=mysql/mysql-connector-java/5.1.46/mysql-connector-java-5.1.46.jar -O /apache-ofbiz-16.11.04/framework/entity/lib/mysql-connector-java-5.1.46.jar

copy component-load.xml /apache-ofbiz-16.11.04/specialpurpose/
run mkdir /apache-ofbiz-16.11.04/specialpurpose/rest-service
copy rest-service /apache-ofbiz-16.11.04/specialpurpose/rest-service/

EXPOSE 8443/tcp
VOLUME /apache-ofbiz-16.11.04/framework/entity/config
VOLUME /apache-ofbiz-16.11.04/specialpurpose
workdir  apache-ofbiz-16.11.04
run  ./gradlew loadDefault
cmd  ./gradlew ofbiz  -x test
