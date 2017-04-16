package ejb;

import javax.annotation.PostConstruct;

import static javax.ejb.LockType.*;

import javax.ejb.*;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import db.DbManager;
import exception.NoSuchAuctionException;
import model.Auction;
import model.Customer;
import statusData.ServerInternalStatus;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;



@Singleton
@Startup
/** force the container to initialize the bean during startup **/
public class AuctionBox implements Serializable{
    private static final Logger logger = Logger.getLogger(AuctionBox.class.getName());
    private ArrayList<Auction> auctionList;
    private ArrayList<String> searchTypes;

    @PostConstruct
    public void setupAuction() {
        searchTypes = DbManager.getDbManager().initializeSearchTypes();
        auctionList = DbManager.getDbManager().initializeAuctions();
        logger.info("AuctionBox setupAuction is Called.  Connected to Db. AuctionList and SearchTypes are initialized.");
    }

    @Lock(READ)
    public ArrayList<String> printSearchTypes() {
        return searchTypes;
    }

    @Lock(READ)
    /** By using a @Lock annotation  multithreaded access will be allowed to the bean */
    public ArrayList<Auction> getAuctionList(String selectedType) {
        if (selectedType == null || selectedType.equals("All Categories"))
            return auctionList;
        else{
            ArrayList<Auction> tempAuctionsList = new ArrayList<>();
            for (Auction auction : auctionList){
                if ( (auction.getItem().getType()).equals(selectedType) )
                    tempAuctionsList.add(auction);
            }
            return tempAuctionsList;
        }
    }

    @Lock(READ)
    public double getRemaining(int auctionId, int customerId) {
        return  getAuction(auctionId).getRemaining(customerId);
    }

    @Lock(READ)
    public String getBidStatus(int auctionId, int customerId) {
        return  getAuction(auctionId).getBidStatus(customerId);
    }

    @Lock(READ)
    public String getBidStatusText(int auctionId, int customerId) {
        return  getAuction(auctionId).getBidStatusText(customerId);
    }


    @Lock(READ)
    private Auction getAuction(int auctionId) throws NoSuchAuctionException {
        Auction auction = null;
        for (Auction tempAuction : auctionList){
            if (tempAuction.getId() == auctionId){
                auction = tempAuction;
                break;
            }
        }
        if (auction == null) {
            throw new NoSuchAuctionException("Auction " + auctionId + " does not exist!");
        }
        return auction;
    }

    @Lock(WRITE)
    /** On the other hand, if we apply @WRITE, the single-threaded access policy is enforced */
    public ServerInternalStatus makeBid(int auctionId, Customer customer, double bidAmount) throws NoSuchAuctionException {
        final Auction auction = getAuctionStatic(auctionId, auctionList);
        final ServerInternalStatus status = auction.bid(customer, bidAmount);
        auctionEvent.fire(auction);
        return status;
    }

    @Lock(READ)
    public static Auction getAuctionStatic(int auctionId, ArrayList<Auction> auctionList) throws NoSuchAuctionException {
        Auction auction = null;
        for (Auction tempAuction : auctionList){
            if (tempAuction.getId() == auctionId){
                auction = tempAuction;
                break;
            }
        }
        if (auction == null) {
            throw new NoSuchAuctionException("Auction " + auctionId + " does not exist!");
        }
        return auction;
    }

    @Inject
    Event<Auction> auctionEvent;
}