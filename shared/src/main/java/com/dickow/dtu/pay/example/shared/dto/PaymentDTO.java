package com.dickow.dtu.pay.example.shared.dto;

public class PaymentDTO {
    private String token;
    private Integer amount;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
