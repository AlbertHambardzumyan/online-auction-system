package producer;

import ejb.CustomerBox;
import statusData.LoginStatus;
import model.Notification;
import statusData.RegisterStatus;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.io.Serializable;
import java.util.ArrayList;

@Stateless
@Remote(CustomerInfo.class) /**planning to invoke this EJB from a remote client*/
public class CustomerInfoBean implements CustomerInfo, Serializable{
    @EJB
    private CustomerBox box; /**used to safely inject an EJB into your class without the need of a manual JNDI lookup */

    @Override
     public double getAccountBalance(int customerId) {
        return box.accountBalance(customerId);
    }

    @Override
    public ArrayList<Notification> getNotifications(int customerId) {
        return box.getNotifications(customerId);
    }

    @Override
    public LoginStatus login(String email, String password) {
        return box.login(email, password);
    }
    @Override
    public RegisterStatus register(String firstName, String lastName, String phone, String email, String password){
        return box.register(firstName, lastName, phone, email, password);
    }

}

/**contains the logic to look up the list of auctions. In practice, this bean acts as a facade
 for our singleton bean */

