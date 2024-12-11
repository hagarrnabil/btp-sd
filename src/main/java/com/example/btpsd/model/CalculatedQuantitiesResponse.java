package com.example.btpsd.model;

public class CalculatedQuantitiesResponse {
    private int actualQuantity;
    private int remainingQuantity;
    private double total;
    private int actualPercentage;
    private double totalHeader;

    public CalculatedQuantitiesResponse(int actualQuantity, int remainingQuantity, double total, int actualPercentage, Double totalHeader) {
        this.actualQuantity = actualQuantity;
        this.remainingQuantity = remainingQuantity;
        this.total = total;
        this.actualPercentage = actualPercentage;

        // Assign totalHeader directly or default to 0.0 if null
        this.totalHeader = (totalHeader != null) ? totalHeader : 0.0;
    }


    public int getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(int actualQuantity) {
        this.actualQuantity = actualQuantity;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getActualPercentage() {
        return actualPercentage;
    }

    public void setActualPercentage(int actualPercentage) {
        this.actualPercentage = actualPercentage;
    }

    public double getTotalHeader() {
        return totalHeader;
    }

    public void setTotalHeader(double totalHeader) {
        this.totalHeader = totalHeader;
    }
}