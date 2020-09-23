package org.bigwpay.transaction.model;

public class TransactionResponse {

    private String id;
    private TransactionStatus state;
    private double amount;
    private String currency;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TransactionStatus getState() {
        return state;
    }

    public void setState(TransactionStatus state) {
        this.state = state;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
