docker run -d --name consul -p 192.168.2.1:8300:8300   -p 192.168.2.1:8301:8301   -p 192.168.2.1:8301:8301/udp -p 192.168.2.1:8302:8302 -p 192.168.2.1:8302:8302/udp       -p 192.168.2.1:8400:8400  -p 192.168.2.1:8500:8500 -p 192.168.2.1:53:53/udp  consul
docker run -d -v /var/run/docker.sock:/tmp/docker.sock --name registrator -h registrator  gliderlabs/registrator:latest -ip=192.168.2.1 consul://192.168.2.1:8500

mvn clean package docker:build
docker run -d -P  brainstorm/cn-customer-service-1.0-uat


mvn archetype:generate \
    -DarchetypeGroupId=org.apache.camel.archetypes \
    -DarchetypeArtifactId=camel-archetype-component \
    -DarchetypeVersion=2.21.0 \
    -DgroupId=org.apache.camel.component \
    -DartifactId=camel-ben