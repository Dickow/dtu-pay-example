package com.dickow.dtu.pay.example.choreographychecker;

import com.dickow.chortlin.checker.checker.ChoreographyChecker;
import com.dickow.chortlin.checker.checker.factory.OnlineCheckerFactory;
import com.dickow.chortlin.checker.choreography.Choreography;
import com.dickow.chortlin.checker.correlation.builder.PathBuilder;
import com.dickow.dtu.pay.example.shared.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

import java.util.Collections;
import java.util.List;

import static com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.external;
import static com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.participant;
import static com.dickow.chortlin.checker.correlation.factory.CorrelationFactory.correlation;
import static com.dickow.chortlin.checker.correlation.factory.CorrelationFactory.defineCorrelation;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.dickow.dtu.pay.example.choreographychecker")
public class ApplicationStart {

    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

    @Bean
    public ChoreographyChecker choreographyChecker() {
        var client = external("Client");
        var merchant = participant("com.dickow.dtu.pay.example.merchant.Merchant");
        var dtuPay = participant("com.dickow.dtu.pay.example.dtu.DTUPayController");
        var dtuBank = participant("com.dickow.dtu.pay.example.dtu.DTUBankIntegration");
        var bank = participant("com.dickow.dtu.pay.example.bank.controllers.BankController");

        var cdef = defineCorrelation()
                .add(correlation(merchant.onMethod("pay"),
                        "merchantId", PathBuilder.root().node("merchant").build())
                        .extendFromInput("merchantId", PathBuilder.root().node("merchant").build())
                        .done())
                .add(correlation(dtuPay.onMethod("pay"),
                        "merchantId", PathBuilder.root().node("merchant").build())
                        .extendFromInput("userId", PathBuilder.root().node("token").build())
                        .done())
                .add(correlation(dtuBank.onMethod("transferMoney"),
                        "userId", PathBuilder.root().node("customer").build())
                        .noExtensions())
                .add(correlation(bank.onMethod("transfer"),
                        "userId", PathBuilder.root().node("transaction").node("customer").build())
                        .noExtensions())
                .finish();

        var choreography = Choreography.builder()
                .interaction(client, merchant.onMethod("pay"), "initiate payment")
                .interaction(merchant, dtuPay.onMethod("pay"), "pay")
                .interaction(dtuPay, dtuBank.onMethod("transferMoney"), "integrate with bank")
                .interaction(dtuBank, bank.onMethod("transfer"), "perform transfer at bank")
                .returnFrom(bank.onMethod("transfer"), "return once transferred")
                .returnFrom(dtuBank.onMethod("transferMoney"), "return from the DTU bank integration")
                .returnFrom(dtuPay.onMethod("pay"), "return from dtu pay")
                .returnFrom(merchant.onMethod("pay"), "finished payment")
                .end()
                .setCorrelation(cdef);

        return OnlineCheckerFactory.createOnlineChecker(List.of(choreography));
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ApplicationStart.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", Constants.CHOREOGRAPHY_CHECKER_PORT_NUMBER));
        app.run(args);
    }

}
