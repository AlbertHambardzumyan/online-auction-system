package org.aua.aoop.bean;

import statusData.LoginStatus;
import model.Notification;
import org.aua.aoop.remote.EJBLocator;
import statusData.RegisterStatus;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;

@ManagedBean(name = "user")
@SessionScoped
public class UserBean {

    private int customerId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private String rePassword;
    private double accountBalance;
    private boolean isLoginStatus;
    private String loginText;
    private String registerText;
    private String retrievePasswordText;
    ArrayList<Notification> notifications;

    public UserBean() {}

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsLoginStatus() {
        return isLoginStatus;
    }

    public void setIsLoginStatus(boolean loginStatus) {
        this.isLoginStatus = loginStatus;
    }

    public String getLoginText() {
        return loginText;
    }

    public void setLoginText(String loginText) {
        this.loginText = loginText;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }


    public String getRegisterText() {
        return registerText;
    }

    public void setRegisterText(String registerText) {
        this.registerText = registerText;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getRetrievePasswordText(){
        return retrievePasswordText;
    }

    public void setRetrievePasswordText(String retrievePasswordText){
        this.retrievePasswordText = retrievePasswordText;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }




    public void balance() {
        accountBalance = EJBLocator.getInstance().fetchAccountBalance(customerId);
        retrieveNotifications();
    }

    public void retrieveNotifications() {
        notifications = EJBLocator.getInstance().fetchNotifications(customerId);
    }

    public String login() {
        if (email != null && password != null && !email.trim().equals("") && !password.trim().equals("")) {
            LoginStatus isRegisteredUser = EJBLocator.getInstance().fetchLogin(email, password);
            if (isRegisteredUser.getStatus() == 200) {
                setIsLoginStatus(true);
                setLoginText("");
                customerId = isRegisteredUser.getId();
                balance();
                retrieveNotifications();
                return "valid";
            } else if (isRegisteredUser.getStatus() == 401) {
                setLoginText(isRegisteredUser.getContext());
                return "invalid";
            } else if (isRegisteredUser.getStatus() == 500) {
                setLoginText(isRegisteredUser.getContext());
                return "invalid";
            } else
                return "invalid";
        } else {
            setLoginText("Please Fill All Fields");
            return "invalid";
        }
    }

    public String register() {
        if (firstName != null && lastName != null && phone != null && email != null && password != null && rePassword != null && !firstName.trim().equals("") && !lastName.trim().equals("") && !phone.trim().equals("") && !email.trim().equals("") && !password.trim().equals("") && !rePassword.trim().equals("")) {
            RegisterStatus register = EJBLocator.getInstance().fetchRegister(firstName, lastName, phone, email, password);
            if (password.equals(rePassword)) {
                if (register.getStatus() == 200) {
                    setLoginText(register.getContext());
                    System.out.println(register.getContext());
                    setRegisterText("");
                    return "valid";
                } else if (register.getStatus() == 401) {
                    setRegisterText(register.getContext());
                    return "invalid";
                } else if (register.getStatus() == 500) {
                    setRegisterText(register.getContext());
                    return "invalid";
                } else if (register.getStatus() == 501) {
                    setRegisterText(register.getContext());
                    return "invalid";
                } else
                    return "invalid";
            } else {
                setRegisterText("Re-Enter Password Correctly");
                return "invalid";
            }
        } else {
            setRegisterText("Please Fill All Fields");
            return "invalid";
        }
    }

    public void retrievePassword(){
        setRetrievePasswordText("Your Password Has Been Sent To Your Email");
    }

    public void logout() {
        /** release 2 */
    }
}
