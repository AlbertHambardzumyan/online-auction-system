package model;

import statusData.ServerInternalStatus;

import java.io.Serializable;



public class Auction implements Serializable{

    private int id;
    private String startDate;
    private String duration;
    private Item item;
    private BidBox bidBox;
    private int sellerId;


    public Auction(int id, String startDate, String duration, Item item, int sellerId) {
        this.id = id;
        this.startDate = startDate;
        this.duration = duration;
        this.item = item;
        this.bidBox = new BidBox();
        this.sellerId = sellerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BidBox getBidBox() {
        return bidBox;
    }

    public void setBidBox(BidBox bidBox) {
        this.bidBox = bidBox;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }




    public ServerInternalStatus bid(Customer customer, double bidAmount){
        return getBidBox().bid(customer, bidAmount, getItem().getStartingPrice());
    }
    public double getCurrentPrice(){
        return getBidBox().getCurrentPrice();
    }
    public double getRemaining(int customerId){
        return bidBox.getRemaining(customerId);
    }
    public String getBidStatus(int customerId){
        return bidBox.getBidStatus(customerId);
    }
    public String getBidStatusText(int customerId){
        return bidBox.getBidStatusText(customerId);
    }
}
