package com.dickow.dtu.pay.example.dtu;

import com.dickow.chortlin.shared.annotations.ChortlinOnInvoke;
import com.dickow.chortlin.shared.annotations.ChortlinOnReturn;
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

    @ChortlinOnInvoke
    @ChortlinOnReturn
    public boolean transferMoney(String merchant, String customer, Integer amount)
    {
        var transaction = new TransactionDTO(merchant, customer, amount);
        try {
            var body = objectMapper.writeValueAsString(transaction);
            var request = HttpRequest.newBuilder(new URI(Constants.BANK_BASE_URL+"transactions"))
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
