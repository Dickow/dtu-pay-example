package com.dickow.dtu.pay.example.dtu;

import com.dickow.chortlin.interception.configuration.InterceptionConfiguration;
import com.dickow.dtu.pay.example.shared.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.dickow.dtu.pay.example.dtu", "com.dickow.dtu.pay.example.dtu.advice"})
public class ApplicationStart {

    public static void main(String[] args) {
        InterceptionConfiguration.setupForHttpInterception(Constants.configuration);
        SpringApplication app = new SpringApplication(ApplicationStart.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", Constants.DTU_PAY_PORT_NUMBER));
        app.run(args);
    }
}
