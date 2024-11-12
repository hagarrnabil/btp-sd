package com.example.btpsd.model;

public class TotalResult {
    private Double totalWithProfit;
    private Double amountPerUnit;

    public TotalResult(Double totalWithProfit, Double amountPerUnit) {
        this.totalWithProfit = totalWithProfit;
        this.amountPerUnit = amountPerUnit;
    }

    public Double getTotalWithProfit() {
        return totalWithProfit;
    }

    public Double getAmountPerUnit() {
        return amountPerUnit;
    }
}
