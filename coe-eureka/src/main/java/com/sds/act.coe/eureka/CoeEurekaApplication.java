package com.sds.act.coe.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class CoeEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoeEurekaApplication.class, args);
    }
}
