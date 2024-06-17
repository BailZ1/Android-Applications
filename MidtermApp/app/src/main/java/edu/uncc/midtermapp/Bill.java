package edu.uncc.midtermapp;

import java.io.Serializable;

public class Bill implements Serializable {
    private String billName;
    private double billAmount;
    private String discount;
    private String category;
    private String billDate;

    public Bill(String billName, double billAmount, String discount, String category, String billDate) {
        this.billName = billName;
        this.billAmount = billAmount;
        this.discount = discount;
        this.category = category;
        this.billDate = billDate;
    }

    // Getters and setters
    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }
}
