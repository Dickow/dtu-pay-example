package com.dickow.dtu.pay.example.merchant;

import com.dickow.chortlin.shared.annotations.TraceInvocation;
import com.dickow.chortlin.shared.annotations.TraceReturn;
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
    public ResponseEntity pay(@PathVariable String merchant, @RequestBody PaymentDTO payment){
        Console.invocation(this.getClass());
        try {
            var request = HttpRequest.newBuilder(new URI(Constants.DTU_PAY_BASE_URL+merchant+"/"+payment.getToken()))
                    .POST(java.net.http.HttpRequest.BodyPublishers.ofString(String.valueOf(payment.getAmount())))
                    .header("content-type", "application/json")
                    .build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            response.body();
        }
        catch(Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
