package register;


import db.DBconnectionManager;
import exception.DatabaseReadError;
import statusData.RegisterStatus;

import java.io.Serializable;

public class Register implements Serializable {
    private static Register login = new Register();

    public static Register getInstance() {
        return login;
    }

    public RegisterStatus userRegister(String firstName, String lastName, String phone, String email, String password) {
        boolean isExistsEmail;
        try {
            isExistsEmail = DBconnectionManager.getInstance().isExistsEmail(email);
            if (!isExistsEmail) {
                double defaultAccountBalance = DBconnectionManager.getInstance().defaultAccountBalance();
                boolean insertResponse = DBconnectionManager.getInstance().insert(firstName, lastName, email, phone, password, defaultAccountBalance);
                if (insertResponse) {
                    return new RegisterStatus(200, "You Have Successfully Registered. Now You Can Get Started");
                } else {
                    return new RegisterStatus(501, "Registration Is Failed. Please Try Again");
                }
            } else
                return new RegisterStatus(401, "Email Address Is Already Registered");
        } catch (DatabaseReadError databaseReadError) {
            return new RegisterStatus(500, "We Could NOT Connect To Database. Please Try Again");
        }
    }
}
