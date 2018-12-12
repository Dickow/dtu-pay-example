package com.dickow.dtu.pay.example.merchant;

import com.dickow.chortlin.interception.strategy.InterceptionConfiguration;
import com.dickow.dtu.pay.example.shared.Constants;
import com.dickow.dtu.pay.example.shared.TraceSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.dickow.dtu.pay.example.merchant")
public class ApplicationStart {

    public static void main(String[] args) {
        InterceptionConfiguration.setupInterception(new TraceSender());
        SpringApplication app = new SpringApplication(ApplicationStart.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", Constants.MERCHANT_PORT_NUMBER));
        app.run(args);
    }
}
