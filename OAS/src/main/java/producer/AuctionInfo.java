package producer;

import model.Auction;

import java.util.ArrayList;



public interface AuctionInfo {
    public ArrayList<Auction> printAuctionList(String selectedType);
    public ArrayList<String> printSearchTypes();
    public double getRemaining(int auctionId, int customerId);
    public String getBidStatus(int auctionId, int customerId);
    public String getBidStatusText(int auctionId, int customerId);
}
