package model;

import java.io.Serializable;
import java.util.Date;

public class Bid implements Serializable {
    private int id;
    private int customerID;
    private double bidAmount;
    private double currentPrice;
    private double remainingAmount;
    private boolean bidStatus;
    private String bidStatusText;
    private Date bidDate;

    public Bid(int id, int customerID, double bidAmount, double currentPrice, double remainingAmount, boolean bidStatus, String bidStatusText) {
        this.id = id;
        this.customerID = customerID;
        this.bidAmount = bidAmount;
        this.currentPrice = currentPrice;
        this.remainingAmount = remainingAmount;
        this.bidStatus = bidStatus;
        this.bidStatusText = bidStatusText;
        setDate();
    }
    public Bid(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }


    public boolean getBidStatus() {
        return bidStatus;
    }

    public void setBidStatus(boolean bidStatus) {
        this.bidStatus = bidStatus;
    }

    public String getBidStatusText() {
        return bidStatusText;
    }

    public void setBidStatus(String bidStatusText) {
        this.bidStatusText = bidStatusText;
    }

    public Date getDate() {
        return bidDate;
    }

    public void setDate() {
        this.bidDate = new Date();
    }

}
