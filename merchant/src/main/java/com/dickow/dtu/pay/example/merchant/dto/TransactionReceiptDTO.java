package com.dickow.dtu.pay.example.merchant.dto;

public class TransactionReceiptDTO {
    private int amount;
    private String message;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
