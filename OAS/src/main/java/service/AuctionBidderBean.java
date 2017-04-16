package service;


import ejb.AuctionBox;
import ejb.CustomerBox;
import exception.NoSuchAuctionException;
import exception.NotEnoughMoneyException;
import statusData.ServerInternalStatus;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;


@Stateless
@Remote(AuctionBidder.class)
public class AuctionBidderBean implements AuctionBidder {
    private static final Logger logger = Logger.getLogger(AuctionBidderBean.class.getName());

    @EJB private AuctionBox auctionBox;
    @EJB private CustomerBox customerBox;

    @Override
    public void makeBid(int auctionId, int customerId, double bidAmount) throws NotEnoughMoneyException, NoSuchAuctionException {
         ServerInternalStatus serverInternalStatus = auctionBox.makeBid(auctionId, customerBox.getCustomer(customerId), bidAmount);
        if (serverInternalStatus.getExtract() && serverInternalStatus.getCustomerId() > 0){
            customerBox.createNotification(serverInternalStatus.getCustomerId(), serverInternalStatus.getNotificationContext());
            customerBox.setAccountBalance(serverInternalStatus.getCustomerId(),serverInternalStatus.getReturnedAmomunt() );
        }
    }
}

