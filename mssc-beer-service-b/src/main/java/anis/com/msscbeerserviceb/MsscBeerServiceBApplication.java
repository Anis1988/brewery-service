package anis.com.msscbeerserviceb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsscBeerServiceBApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsscBeerServiceBApplication.class, args);
    }

}
