Camel-boot
=======
Spring and camel are two super glue. Spring can easily combine different technologies together in a minutes in code level. Camel can describe the whole data/business flow using it's supported <abbr title="Domain-specific language">DSLs</abbr> lanaguages, components and <abbr title="Enterprise Integration Pattern">EIPs</abbr>. Docker is a light weight container solution, which can easily encapsulate implemented functions into a image file, it makes maintaining and scaling your backend service easily. So here I intend to combine such technogies together to make a scalable micro service implementation.

---
Spring和Camel是非常好的万能胶。Spring可以非常容易把不同技术代码结合到一起。Camel可以使用它支持的描述语言、扩展模块和<abbr title="Enterprise Integration Pattern">EIPs</abbr>来描述和实现业务流程。Docker是一个轻量容器解决方案，可以非常方便的把实现的功能打成镜像文件，它使管理和增减服务能力变得非常容易。所以我希望把这些技术结合到一起，做一个微服务实现。
---
SpringとCamelが使い易いスーパー接着剤。Springを使って異なる技術のコードを繋がるのは非常に易い。CamelのDSL言語、拡張モジュールと<abbr title="Enterprise Integration Pattern">EIPs</abbr>を使って、業務フローを表示できる。Dockerがライトウェイトコンテナで、実装した機能をイメージファイルに入れて、サービス能力の管理が易くなる。なので、この幾つかの技術を重ねて、マイクロサービスアーキテクチャを実現する

## Key Technologies
 
  * #Spring boot
  * #Camel
  * #Spring session
  * #Spring data
  * #Redis
  * #Spring security
  * #Docker
  * #Consul
  * #Registrator
  * #Zipkin
  * #<abbr title="Elasticsearch, Logstash, Kibana">ELK</abbr>
  
Quick start Guide

  1. [Install docker on your system](https://docs.docker.com/install/)
  2. Start up Consul registrator zipkin in docker with docker run command
  	docker run -d -p 9411:9411 openzipkin/zipkin
  	docker run -d --name consul -p ${your_host}:8300:8300   -p ${your_host}:8301:8301   -p ${your_host}:8301:8301/udp -p ${your_host}:8302:8302 -p ${your_host}:8302:8302/udp       -p ${your_host}:8400:8400  -p ${your_host}:8500:8500 -p ${your_host}:53:53/udp  consul

docker run -d -v /var/run/docker.sock:/tmp/docker.sock --name registrator -h registrator  gliderlabs/registrator:latest -ip=${your_host} consul://${your_host}:8500
  	
  3. [Install and start redis on your local computer](https://redis.io/topics/quickstart)
  4. Change settings in application.properties for redis,db and zipkin settings.
  5. build network-operator and customer-service using maven command
      mvn clean package docker:build
  6. Run the network-operator and customer-service in docker
  7. Start SoapUI to perform testing
  	 https://localhost:8443/camel/api/customer-service/initLogin with post method

