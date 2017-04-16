package login;

import db.DBconnectionManager;
import exception.DatabaseReadError;
import statusData.LoginStatus;

import java.io.Serializable;

public class Login implements Serializable {
    private static Login login = new Login();

    public static Login getInstance() {
        return login;
    }

    public LoginStatus userLogin(String email, String password) {
        int isRegisteredUser;
        try {
            isRegisteredUser = DBconnectionManager.getInstance().isRegisteredUser(email, password);
            if (isRegisteredUser != -1)
                return new LoginStatus(isRegisteredUser, 200, "Welcome");
            else
                return new LoginStatus(401, "Username Or Password Is Wrong");
        } catch (DatabaseReadError databaseReadError) {
            return new LoginStatus(500, "We Could NOT Connect To Database. Please Try Again");
        }
    }
}
