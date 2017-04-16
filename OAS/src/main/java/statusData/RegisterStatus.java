package statusData;

import java.io.Serializable;

public class RegisterStatus implements Serializable{
    private int status;
    private String context;

    public RegisterStatus(int status, String context) {
        this.status = status;
        this.context = context;
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
