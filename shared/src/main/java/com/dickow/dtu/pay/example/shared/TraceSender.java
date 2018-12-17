package com.dickow.dtu.pay.example.shared;

import com.dickow.chortlin.interception.sending.ChortlinSender;
import com.dickow.chortlin.shared.trace.dto.InvocationDTO;
import com.dickow.chortlin.shared.trace.dto.ReturnDTO;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TraceSender implements ChortlinSender {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    @Override
    public void send(InvocationDTO invocationDTO) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(Constants.CHOREOGRAPHY_CHECKER_BASE_URL + "/invocation"))
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(invocationDTO)))
                    .header("content-type", "application/json")
                    .build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(ReturnDTO returnDTO) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(Constants.CHOREOGRAPHY_CHECKER_BASE_URL + "/return"))
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(returnDTO)))
                    .header("content-type", "application/json")
                    .build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
