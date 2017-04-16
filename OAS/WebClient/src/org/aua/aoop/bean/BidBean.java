package org.aua.aoop.bean;

import org.aua.aoop.remote.EJBLocator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "bid")
@SessionScoped
public class BidBean {

    private int auctionId;
    private int customerID;
    private double bidMoney;

    public BidBean() {}

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public double getBidMoney() {
        return bidMoney;
    }

    public void setBidMoney(double bidMoney) {
        this.bidMoney = bidMoney;
    }



    public void bid() {
        EJBLocator.getInstance().fetchMakeBid(auctionId, customerID, getBidMoney());
        setBidMoney(0);
    }
}

