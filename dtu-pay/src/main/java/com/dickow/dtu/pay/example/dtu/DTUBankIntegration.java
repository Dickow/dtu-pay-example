package com.dickow.dtu.pay.example.dtu;

import com.dickow.dtu.pay.example.shared.Constants;
import com.dickow.dtu.pay.example.shared.dto.TransactionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class DTUBankIntegration {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public boolean transferMoney(String merchant, String customer, Integer amount)
    {
        var transaction = new TransactionDTO(merchant, customer, amount);
        try {
            var body = objectMapper.writeValueAsString(transaction);
            var request = HttpRequest.newBuilder(new URI(Constants.BANK_BASE_URL+"transactions"))
                    .POST(HttpRequest.BodyPublisher.fromString(body))
                    .header("content-type", "application/json")
                    .build();
            httpClient.send(request, HttpResponse.BodyHandler.asString());
        }
        catch(Exception e){
            return false;
        }

        return true;
    }
}