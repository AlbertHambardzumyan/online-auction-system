package db;

import exception.DatabaseReadError;
import model.Auction;
import model.Customer;
import model.Notification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class DbManager implements Serializable{
    private static DbManager dbManager = new DbManager();

    public static DbManager getDbManager(){
        return dbManager;
    }

    public ArrayList<Customer> initializeCustomers() {
        try {
            return DBconnectionManager.getInstance().initializeCustomers();
        } catch (DatabaseReadError databaseReadError) {
            return new ArrayList<>();
        }
    }
    public ArrayList<String> initializeSearchTypes() {
        try {
            return DBconnectionManager.getInstance().initializeSearchTypes();
        } catch (DatabaseReadError databaseReadError) {
            return new ArrayList<>();
        }
    }
    public ArrayList<Auction> initializeAuctions() {
        try {
            return DBconnectionManager.getInstance().initializeAuctions();
        } catch (DatabaseReadError databaseReadError) {
            return new ArrayList<>();
        }
    }
    public ArrayList<Notification> initializeNotifications(int customerId) {
        try {
            return DBconnectionManager.getInstance().initializeNotifications(customerId);
        } catch (DatabaseReadError databaseReadError) {
            return new ArrayList<>();
        }
    }
    public boolean createNotification(String notificationContext, int customerId) {
        try {
            return DBconnectionManager.getInstance().createNotification(notificationContext, customerId, new Date().toString());
        } catch (DatabaseReadError databaseReadError) {
            return false;
        }
    }

    public boolean setNewBalance(double accountBalance, int id) {
        try {
            return  DBconnectionManager.getInstance().setNewBalance(accountBalance, id);
        } catch (DatabaseReadError databaseReadError) {
            return false;
        }
    }
}
