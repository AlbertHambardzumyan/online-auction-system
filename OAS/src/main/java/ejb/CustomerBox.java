package ejb;

import db.DbManager;
import exception.NoSuchCustomerException;
import login.Login;
import model.Customer;
import statusData.LoginStatus;
import model.Notification;
import register.Register;
import statusData.RegisterStatus;

import javax.ejb.Lock;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;

import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;


@Singleton
@Startup
/** force the container to initialize the bean during startup **/

public class CustomerBox implements Serializable {
    private static final Logger logger = Logger.getLogger(CustomerBox.class.getName());

    private ArrayList<Customer> customerList;



    @Lock(READ)
    /** By using a @Lock annotation  multithreaded access will be allowed to the bean */
    public ArrayList<Customer> getCustomerList() {
        customerList = DbManager.getDbManager().initializeCustomers();
        logger.info("Customer getCustomerList is Called. Connected to Db");
        return customerList;
    }

    @Lock(READ)
    /** By using a @Lock annotation  multithreaded access will be allowed to the bean */
    public double accountBalance(int customerId) {
        return getCustomer(customerId).getAccountBalance();
    }

    @Lock(READ)
    /** By using a @Lock annotation  multithreaded access will be allowed to the bean */
    public ArrayList<Notification> getNotifications(int customerId) {
        return getCustomer(customerId).getNotifications();
    }

    @Lock(READ)
    public LoginStatus login(String email, String password) {
        return Login.getInstance().userLogin(email, password);
    }
    @Lock(READ)
    public RegisterStatus register(String firstName, String lastName, String phone, String email, String password) {
        RegisterStatus registerStatus = Register.getInstance().userRegister(firstName, lastName, phone, email, password);
        if (registerStatus.getStatus() == 200){
            /** create notification for welcome */
        }
        return registerStatus;
    }

    @Lock(WRITE)
    public void createNotification(int  customerId, String notificationContext) {
         getCustomer(customerId).createNotification( notificationContext);
    }

    @Lock(WRITE)
    public void setAccountBalance(int  customerId, double returnedAmount) {
        getCustomer(customerId).returnBalance( returnedAmount);
    }

    @Lock(READ) /** only for above method usage */
    public Customer getCustomer(int customerId) throws NoSuchCustomerException {
        Customer customer = null;
        ArrayList<Customer> customers = getCustomerList();
        for (Customer tempCustomer : customers ){
            if (tempCustomer.getId() == customerId){
                customer = tempCustomer;
                break;
            }
        }
        if (customer == null) {
            throw new NoSuchCustomerException("Customer " + customerId + " does not exist!");
        }
        System.out.println("get Customer call, id: " + customer.getId());
        return customer;
    }

    @Inject
    Event<Customer> customerEvent;
}
