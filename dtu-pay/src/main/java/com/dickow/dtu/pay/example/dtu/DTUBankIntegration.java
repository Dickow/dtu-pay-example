package com.dickow.dtu.pay.example.dtu;

import com.dickow.chortlin.interception.annotations.TraceInvocation;
import com.dickow.chortlin.interception.annotations.TraceReturn;
import com.dickow.dtu.pay.example.shared.Console;
import com.dickow.dtu.pay.example.shared.Constants;
import com.dickow.dtu.pay.example.shared.dto.TransactionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class DTUBankIntegration {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    @TraceInvocation
    @TraceReturn
    public boolean transferMoney(String merchant, String customer, Integer amount)
    {
        Console.invocation(this.getClass());
        var transaction = new TransactionDTO(merchant, customer, amount);
        var url = amount > 9999 ? Constants.BANK_BASE_URL+"vip/transactions" : Constants.BANK_BASE_URL+"transactions";

        try {
            var body = objectMapper.writeValueAsString(transaction);
            var request = HttpRequest.newBuilder(new URI(url))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .header("content-type", "application/json")
                    .build();
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch(Exception e){
            return false;
        }

        return true;
    }
}
