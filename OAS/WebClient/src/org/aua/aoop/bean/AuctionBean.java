package org.aua.aoop.bean;

import model.Auction;
import org.aua.aoop.remote.EJBLocator;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;

@ManagedBean(name = "AuctionBean")
public class AuctionBean implements Serializable{
    private ArrayList<Auction> auctions;
    private ArrayList<String> searchTypes;
    private String selectedType = "All Categories";

    private int auctionId;

    @PostConstruct
    public void setDefaultAuctions(){
        this.searchTypes = EJBLocator.getInstance().fetchSearchTypes();
        this.auctions = EJBLocator.getInstance().fetchAuctions(selectedType);
    }

    public AuctionBean() {
    }

    public ArrayList<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(ArrayList<Auction> auctions) {
        this.auctions = auctions;
    }

    public ArrayList<String> getSearchTypes() {
        return searchTypes;
    }

    public void setSearchTypes(ArrayList<String> searchTypes) {
        this.searchTypes = searchTypes;
    }

    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }






    public void searchByType() {
        this.auctions = EJBLocator.getInstance().fetchAuctions(selectedType);
    }
    public double remaining(int customerId, int auctionId) {
        return EJBLocator.getInstance().fetchRemaining(auctionId, customerId);
    }

    public String status(int customerId, int auctionId) {
        return EJBLocator.getInstance().fetchStatus(auctionId, customerId);
    }

    public String statusText(int customerId, int auctionId) {
        return EJBLocator.getInstance().fetchStatusText(auctionId, customerId);
    }
}
