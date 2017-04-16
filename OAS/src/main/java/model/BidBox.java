package model;

import statusData.ServerInternalStatus;

import java.io.Serializable;
import java.util.ArrayList;


public class BidBox implements Serializable {
    private ArrayList<Bid> bidsList = new ArrayList<>();
    private Bid winningBid = new Bid();
    private int key = 1;

    private int generateId(){
        return key++;
    }

    public ArrayList<Bid> getBidsList() {
        return bidsList;
    }


    public double getCurrentPrice(){
        if (winningBid == null)
            return 0;
        return winningBid.getCurrentPrice();
    }


    public ServerInternalStatus bid(Customer customer, double bidAmount, double startingPrice) {
        final double accountBalance = customer.getAccountBalance();

        /** you are the first bidder */
        if (bidsList.isEmpty() || !winningBid.getBidStatus()) {
            if (accountBalance >= bidAmount) {
                /** you putted more or equal than starting price */
                if (bidAmount >= startingPrice) {
                    int bidId = generateId();
                    winningBid = new Bid(bidId, customer.getId(), bidAmount, startingPrice, bidAmount - startingPrice, true, "You have $" + (bidAmount - startingPrice) + " For Auto Bidding");
                    bidsList.add(new Bid(bidId, customer.getId(), bidAmount, startingPrice, bidAmount - startingPrice, true, "You have $" + (bidAmount - startingPrice) + " For Auto Bidding"));
                    customer.setAccountBalance(accountBalance - bidAmount);
                    return new ServerInternalStatus(false);
                    /** you putted less than starting price */
                } else {
                    bidsList.add(new Bid(generateId(), customer.getId(), bidAmount, winningBid.getCurrentPrice(), 0, false,  "Your Biding Amount Is Less Than Starting Price"));
                    return new ServerInternalStatus(false);
                }
            }
            else{
                bidsList.add(new Bid(generateId(), customer.getId(), bidAmount, winningBid.getCurrentPrice(), 0, false,  "Your Account Balance Is Less Than Your Bid Amount"));
                return new ServerInternalStatus(false);
            }
        /** you are NOT the first bidder */
        } else {
            final double winnerCurrentPrice = winningBid.getCurrentPrice();


            /** you are the current winner bidder. And you added more money*/
            if (winningBid.getCustomerID() == customer.getId()) {
                if (accountBalance >= bidAmount) {
                    int bidId = generateId();
                    winningBid.setRemainingAmount(winningBid.getRemainingAmount() + bidAmount);
                    bidsList.add(new Bid(bidId, customer.getId(), bidAmount, winningBid.getCurrentPrice(), winningBid.getRemainingAmount(), true, "New Amount Has Been Added To Your Previous Amount"));
                    customer.setAccountBalance(accountBalance - bidAmount);
                    return new ServerInternalStatus(false);

                }else{
                    bidsList.add(new Bid(generateId(), customer.getId(), bidAmount, winningBid.getCurrentPrice(), 0, false,  "Your Account Balance Is Less Than Your Added Amount"));
                    return new ServerInternalStatus(false);
                }
            }


            if (accountBalance >= bidAmount) {
                /** you are NOT the current winner but you made bid in this auction previously, and you tried to add money but you out of game and you now putted less than current price */
                if (checkMadeCustomerBid(customer.getId())) {
                    if (bidAmount < winningBid.getCurrentPrice() + 1){
                        bidsList.add(new Bid(generateId(), customer.getId(), bidAmount, winningBid.getCurrentPrice(), 0, false,  "You Already Have Been Over Bidded And Got Your Money Back. Now, Your Bid Amount Less Than Current Price. Check Your Notifications For More Information"));
                        return new ServerInternalStatus(false);
                    }
                }


                /** you putted more money than current price */
                if (bidAmount > winnerCurrentPrice) {
                    final double winnerRemainingAmount = winningBid.getRemainingAmount();

                    /** you bidded the auto bid of previous bidder. you become winner */
                    if (bidAmount >= winnerCurrentPrice + winnerRemainingAmount + 1) {
                        int oldCustomerId = winningBid.getCustomerID();
                        double returnedAmount = winningBid.getBidAmount();
                        String notificationContext = "Unfortunately Your Bid Is Over Bidded By The User With Id " + customer.getId() + ". We Tried Hard With Auto Bidding, But The New Buyer Putted More Money";
                        String bidStatusContext = "You Even Bidded The Auto Bidding Of The Current Winner";

                        int bidId = generateId();
                        winningBid = new Bid(bidId, customer.getId(), bidAmount, (winnerCurrentPrice + winnerRemainingAmount) + 1, bidAmount - (winnerCurrentPrice + winnerRemainingAmount + 1), true, bidStatusContext);
                        bidsList.add(new Bid(bidId, customer.getId(), bidAmount, (winnerCurrentPrice + winnerRemainingAmount) + 1, bidAmount - (winnerCurrentPrice + winnerRemainingAmount + 1), true, bidStatusContext));
                        customer.setAccountBalance(accountBalance - bidAmount);
                        return new ServerInternalStatus(oldCustomerId, returnedAmount, notificationContext, true);
                    }
                    /** you could not bid the auto bidding */
                    else {
                        winningBid.setRemainingAmount(winnerRemainingAmount - (bidAmount - winnerCurrentPrice));
                        winningBid.setCurrentPrice(bidAmount);
                        bidsList.add(new Bid(generateId(), customer.getId(), bidAmount, winningBid.getCurrentPrice(), 0, false, " Your bid is auto bidded by User with Id " +winningBid.getCustomerID()));
                        return new ServerInternalStatus(false);
                    }
                }
                /** you putted less than starting price */
                else {
                    bidsList.add(new Bid(generateId(), customer.getId(), bidAmount, winningBid.getCurrentPrice(), 0, false,  "Your Bidding Amount Is Less Than Current Price"));
                    return new ServerInternalStatus(false);
                }
            } else{
                bidsList.add(new Bid(generateId(), customer.getId(), bidAmount, winningBid.getCurrentPrice(), 0, false, "Your Account Balance Is Less Than Your Bid Amount"));
                return new ServerInternalStatus(false);
            }
        }
    }

    private boolean checkMadeCustomerBid(int customerId){
        for (Bid bid : bidsList){
            if (customerId == bid.getCustomerID() && bid.getBidStatus()){
                return true;
            }
        }
        return false;
    }
    public double getRemaining(int customerId){
        if (winningBid != null && customerId == winningBid.getCustomerID() && winningBid.getBidStatus())
            return winningBid.getRemainingAmount();
        return 0;
    }

    public String getBidStatus(int customerId){
        for (int i = bidsList.size() - 1 ; i >= 0 ; i--){
            if (customerId == bidsList.get(i).getCustomerID()){
                return bidsList.get(i).getBidStatus() ? "Accepted" : "Denied";
            }
        }
        return "";
    }

    public String getBidStatusText(int customerId){
        for (int i = bidsList.size() - 1 ; i >= 0 ; i--){
            if (customerId == bidsList.get(i).getCustomerID()){
                return bidsList.get(i).getBidStatusText();
            }
        }
        return "";
    }

}
