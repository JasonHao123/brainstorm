package jason.app.brainstorm.network.operator;

import org.apache.camel.zipkin.starter.CamelZipkin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@SpringBootApplication
@CamelZipkin
public class NetworkOperatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetworkOperatorApplication.class, args);
    }

}

