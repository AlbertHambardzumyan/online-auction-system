package model;


import db.DbManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class NotificationBox implements Serializable{
    private ArrayList<Notification> notifications;

    public NotificationBox() {}

    public ArrayList<Notification> getNotifications(int customerId){
        ArrayList<Notification> tempNotifs = DbManager.getDbManager().initializeNotifications(customerId);
        Collections.reverse(tempNotifs);
        return tempNotifs;
    }
    public void createNotification( String notificationContext, int customerId){
        DbManager.getDbManager().createNotification(notificationContext, customerId);
    }
}
