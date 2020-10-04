package anis.com.mssceureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class MsscEurekaBApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsscEurekaBApplication.class, args);
    }

}
