package statusData;

import java.io.Serializable;

public class ServerInternalStatus implements Serializable {
    public ServerInternalStatus(int customerId, double returnedAmomunt, String notificationContext,  boolean extract) {
        this.customerId = customerId;
        this.returnedAmomunt = returnedAmomunt;
        this.notificationContext = notificationContext;
        this.extract = extract;
    }
    public ServerInternalStatus( boolean extract) {
        this.extract = extract;
    }

    public boolean getExtract() {
        return extract;
    }

    public void setExtract(boolean extract) {
        this.extract = extract;
    }


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getReturnedAmomunt() {
        return returnedAmomunt;
    }

    public void setReturnedAmomunt(double returnedAmomunt) {
        this.returnedAmomunt = returnedAmomunt;
    }

    public String getNotificationContext() {
        return notificationContext;
    }

    public void setNotificationContext(String notificationContext) {
        this.notificationContext = notificationContext;
    }


    private int customerId;
    private double returnedAmomunt;
    private String notificationContext;
    private boolean extract;
}
