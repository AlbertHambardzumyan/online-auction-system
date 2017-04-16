package statusData;

import java.io.Serializable;

public class LoginStatus implements Serializable{

    private int id;
    private int status;
    private String context;

    public LoginStatus(int id, int status, String context) {
        this.id = id;
        this.status = status;
        this.context = context;
    }
    public LoginStatus(int status, String context) {
        this.status = status;
        this.context = context;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
