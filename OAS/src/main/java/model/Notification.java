package model;


import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable{
    private String date;
    private String context;
    private boolean seen;

    public Notification(String context) {
        this.date = new Date().toString();
        this.seen = false;
        this.context = context;
    }
    public Notification(Notification notification) {
        this.date = notification.getDate();
        this.seen = notification.getSeen();
        this.context = notification.getContext();
    }
    public Notification(String date, String context, boolean seen) {
        this.date = date;
        this.context = context;
        this.seen = seen;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public boolean getSeen() {
        return seen;
    }

    public void setSeen(boolean isSeen) {
        this.seen = isSeen;
    }

}
