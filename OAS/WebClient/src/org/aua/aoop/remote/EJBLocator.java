package org.aua.aoop.remote;

import model.Auction;
import model.BidStatus;
import statusData.LoginStatus;
import model.Notification;
import org.jboss.ejb.client.EJBClient;
import org.jboss.ejb.client.StatelessEJBLocator;
import producer.AuctionInfo;
import producer.CustomerInfo;
import service.AuctionBidder;
import statusData.RegisterStatus;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;


public class EJBLocator implements Serializable{
    private static EJBLocator ourInstance = new EJBLocator();

    public static EJBLocator getInstance() {
        return ourInstance;
    }

    private Context context = null;
    private AuctionInfo auctionInfo = null;
    private AuctionBidder auctionBidder = null;
    private CustomerInfo customerInfo = null;

    private EJBLocator() {
        final Properties jndiProperties = new Properties();
        jndiProperties.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        try {
            this.context = new InitialContext(jndiProperties);
            auctionBidder = lookupAuctionBidderEJB();
            context.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        try {
            this.context = new InitialContext(jndiProperties);
            auctionInfo = lookupAuctionInfoEJB();
            context.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        try {
            this.context = new InitialContext(jndiProperties);
            customerInfo = lookupCustomerInfoEJB();
            context.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /** lookup */
    private AuctionInfo lookupAuctionInfoEJB() throws NamingException {
        StatelessEJBLocator<AuctionInfo> locator = new StatelessEJBLocator <>(
                AuctionInfo.class, "", "OAS-ArtifactId", "AuctionInfoBean", "");
        return EJBClient.createProxy(locator);
    }

    private AuctionBidder lookupAuctionBidderEJB() throws NamingException {
       return (AuctionBidder) context.lookup("ejb:/OAS-ArtifactId/AuctionBidderBean!service.AuctionBidder");
    }

    private CustomerInfo lookupCustomerInfoEJB() throws NamingException {
        return (CustomerInfo) context.lookup("ejb:/OAS-ArtifactId/CustomerInfoBean!producer.CustomerInfo");
    }


    /** methods */

    /** AuctionBean */
    public ArrayList<Auction> fetchAuctions(String selectedType){
        return auctionInfo.printAuctionList(selectedType);
    }

    public ArrayList<String> fetchSearchTypes(){
        return auctionInfo.printSearchTypes();
    }

    public double fetchRemaining(int auctionId, int customerId){
        return auctionInfo.getRemaining(auctionId, customerId);
    }

    public String fetchStatus(int auctionId, int customerId){
        return auctionInfo.getBidStatus(auctionId, customerId);
    }

    public String fetchStatusText(int auctionId, int customerId){
        return auctionInfo.getBidStatusText(auctionId, customerId);
    }

    /** UseBean */
    public double fetchAccountBalance(int customerId){
        return customerInfo.getAccountBalance(customerId);
    }

    public ArrayList<Notification> fetchNotifications(int customerId){
        return customerInfo.getNotifications(customerId);
    }

    public LoginStatus fetchLogin(String email, String password){
        return customerInfo.login(email, password);
    }

    public RegisterStatus fetchRegister(String firstName, String lastName, String phone, String email, String password){
        return customerInfo.register(firstName, lastName, phone, email, password);
    }

    /** BidBean */
    public void fetchMakeBid(int auctionId, int customerId, double bidAmount){
       auctionBidder.makeBid(auctionId, customerId, bidAmount);
    }
}
