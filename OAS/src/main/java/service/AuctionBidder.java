package service;

import exception.NoSuchAuctionException;
import exception.NotEnoughMoneyException;



public interface AuctionBidder {
    public void makeBid(int auctionId, int customerId, double bidAmount) throws  NotEnoughMoneyException, NoSuchAuctionException;
}
