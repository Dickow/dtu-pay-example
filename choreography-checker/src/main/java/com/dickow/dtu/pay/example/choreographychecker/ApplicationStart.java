package com.dickow.dtu.pay.example.choreographychecker;

import com.dickow.chortlin.checker.checker.ChoreographyChecker;
import com.dickow.chortlin.checker.checker.factory.OnlineCheckerFactory;
import com.dickow.chortlin.checker.choreography.Choreography;
import com.dickow.dtu.pay.example.bank.controllers.BankController;
import com.dickow.dtu.pay.example.dtu.DTUBankIntegration;
import com.dickow.dtu.pay.example.dtu.DTUPayController;
import com.dickow.dtu.pay.example.merchant.Merchant;
import com.dickow.dtu.pay.example.shared.Constants;
import com.dickow.dtu.pay.example.shared.dto.PaymentDTO;
import com.dickow.dtu.pay.example.shared.dto.TransactionDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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
    public ChoreographyChecker choreographyChecker() {
        var client = external("Client");
        var merchant = participant(Merchant.class);
        var dtuPay = participant(DTUPayController.class);
        var dtuBank = participant(DTUBankIntegration.class);
        var bank = participant(BankController.class);

        var cdef = defineCorrelation()
                .add(correlation(merchant.onMethod("pay", Merchant::pay),
                        "merchantId", (String merchantId, PaymentDTO payment) -> merchantId)
                        .extendFromInput("merchantId", (String merchantId, PaymentDTO payment) -> merchantId)
                        .done())
                .add(correlation(dtuPay.onMethod("pay", DTUPayController::pay),
                        "merchantId", (String merchantId, Integer amount, String token) -> merchantId)
                        .extendFromInput("userId", (String merchantId, Integer amount, String token) -> token)
                        .done())
                .add(correlation(dtuBank.onMethod("transferMoney", DTUBankIntegration::transferMoney),
                        "userId", (String merchantId, String customer, Integer amount) -> customer)
                        .noExtensions())
                .add(correlation(bank.onMethod("transfer", BankController::transfer),
                        "userId", TransactionDTO::getCustomer)
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
