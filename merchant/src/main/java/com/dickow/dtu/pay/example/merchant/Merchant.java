package com.dickow.dtu.pay.example.merchant;

import com.dickow.chortlin.interception.annotations.TraceInvocation;
import com.dickow.chortlin.interception.annotations.TraceReturn;
import com.dickow.dtu.pay.example.merchant.dto.TransactionReceiptDTO;
import com.dickow.dtu.pay.example.shared.Console;
import com.dickow.dtu.pay.example.shared.Constants;
import com.dickow.dtu.pay.example.shared.dto.PaymentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping(value = "merchant")
public class Merchant {
    private final HttpClient httpClient = HttpClient.newHttpClient();

    @PostMapping(value = "{merchant}/pay")
    @TraceInvocation
    @TraceReturn
    public ResponseEntity<Object> pay(@PathVariable String merchant, @RequestBody PaymentDTO payment){
        Console.invocation(this.getClass());
        try {
            var request = HttpRequest.newBuilder(new URI(Constants.DTU_PAY_BASE_URL+merchant+"/"+payment.getToken()))
                    .POST(java.net.http.HttpRequest.BodyPublishers.ofString(String.valueOf(payment.getAmount())))
                    .header("content-type", "application/json")
                    .build();
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var receipt = new TransactionReceiptDTO();
        receipt.setAmount(payment.getAmount());
        receipt.setMessage(String.format(
                "Successfully paid the requested amount from customer: %s to merchant %s",
                payment.getToken(),
                merchant));
        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }
}
