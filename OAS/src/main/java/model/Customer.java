package model;

import db.DbManager;

import java.io.Serializable;
import java.util.ArrayList;


public class Customer implements Serializable {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private double accountBalance;
    private NotificationBox notificationBox;
    
    public Customer(int id, String firstName, String lastName, String email, String phone, String password, double accountBalance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.accountBalance = accountBalance;
        this.notificationBox = new NotificationBox();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getAccountBalance(){
        return accountBalance;
    }

    public boolean setAccountBalance(double accountBalance){
        if (DbManager.getDbManager().setNewBalance(accountBalance, id)) {
            this.accountBalance = accountBalance;
            return true;
        }
        else
            return false;
    }

    public NotificationBox getNotificationBox() {
        return notificationBox;
    }

    public void setNotificationBox(NotificationBox notificationBox) {
        this.notificationBox = notificationBox;
    }




    public void createNotification( String notificationContext){
        notificationBox.createNotification( notificationContext, id);
    }
    public void returnBalance(double returnedAmount){
        setAccountBalance(getAccountBalance() + returnedAmount);
    }
    public ArrayList<Notification> getNotifications(){
        return getNotificationBox().getNotifications(id);
    }
}
