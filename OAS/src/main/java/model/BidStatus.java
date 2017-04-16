package model;

import java.io.Serializable;

public class BidStatus implements Serializable{
    private String context;
    private boolean status;

    public BidStatus(String context, boolean status) {
        this.context = context;
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }


}
