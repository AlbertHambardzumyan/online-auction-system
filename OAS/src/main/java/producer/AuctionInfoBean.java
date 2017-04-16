package producer;

import ejb.AuctionBox;
import model.Auction;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.io.Serializable;
import java.util.ArrayList;


@Stateless
@Remote(AuctionInfo.class) /**planning to invoke this EJB from a remote client*/
public class AuctionInfoBean implements AuctionInfo, Serializable {
    @EJB private AuctionBox box;

    @Override
    public ArrayList<Auction> printAuctionList(String selectedType) {
        return box.getAuctionList(selectedType);
    }

    @Override
    public ArrayList<String> printSearchTypes() {
        return box.printSearchTypes();
    }

    @Override
    public double getRemaining(int auctionId, int customerId) {
        return box.getRemaining(auctionId, customerId);
    }

    @Override
    public String getBidStatus(int auctionId, int customerId){
        return box.getBidStatus(auctionId, customerId);
    }

    @Override
    public String getBidStatusText(int auctionId, int customerId){
        return box.getBidStatusText(auctionId, customerId);
    }

}

/**contains the logic to look up the list of auctions, get auction types, retrieve remaining amount and bid status
 * for particular auction
 *  In practice, this bean acts as a facade for our singleton bean */

