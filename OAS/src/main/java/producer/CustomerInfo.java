package producer;


import statusData.LoginStatus;
import model.Notification;
import statusData.RegisterStatus;

import java.util.ArrayList;


public interface CustomerInfo {
    public double getAccountBalance(int customerId);
    public ArrayList<Notification> getNotifications(int customerId);
    public LoginStatus login(String email, String password);
    public RegisterStatus register(String firstName, String lastName, String phone, String email, String password);
}
