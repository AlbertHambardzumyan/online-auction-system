package db;


import exception.DatabaseReadError;
import model.Auction;
import model.Customer;
import model.Item;
import model.Notification;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * * Created by Albert on 11/18/2015.
 */
public class DBconnectionManager {
    private static DBconnectionManager ourInstance = new DBconnectionManager();

    public static DBconnectionManager getInstance() {
        return ourInstance;
    }

    private Connection conn;

    private DBconnectionManager() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/oas", "root", "sesame");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public int isRegisteredUser(String userLogin, String userPass) throws DatabaseReadError {
        String login = userLogin.toLowerCase();
        PreparedStatement preparedStmt = null;
        ResultSet rs = null;
        try {
            /** the mysql insert statement */
            String query = "select Password, UserId from user where Email= ?";
            /** create the mysql insert preparedStatement */
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, login);

            /** execute the preparedStatement */
            rs = preparedStmt.executeQuery();
            if (rs.next()) {
                String pass = rs.getString("Password");
                int id = rs.getInt("UserId");
                return pass.equals(userPass) ? id : -1 ;
            } else
                return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseReadError();
        } finally {
            closeAll(preparedStmt, rs);
        }
    }

    public boolean isExistsEmail(String emailAddress) throws DatabaseReadError {
        String email = emailAddress.toLowerCase();
        PreparedStatement preparedStmt = null;
        ResultSet rs = null;
        try {
            /** the mysql insert statement */
            String query = "select * from user where Email= ?";
            /** create the mysql insert preparedStatement */
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, email);

            /** execute the preparedStatement */
            rs = preparedStmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseReadError();
        } finally {
            closeAll(preparedStmt, rs);
        }
    }

    public boolean insert(String firstName, String lastName, String emailAddress, String phone, String pass1, double accountBalance) throws DatabaseReadError {
        String email = emailAddress.toLowerCase();
        PreparedStatement preparedStmt = null;
        try {
            /** the mysql insert statement */
            String query = " insert into user (FirstName, LastName, Email, Phone, Password, AccountBalance)"
                    + " values (?, ?, ?, ?, ?, ?)";
            /** create the mysql insert preparedStatement */
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, firstName);
            preparedStmt.setString(2, lastName);
            preparedStmt.setString(3, email);
            preparedStmt.setString(4, phone);
            preparedStmt.setString(5, pass1);
            preparedStmt.setDouble(6, accountBalance);

            /** execute the preparedStatement */
            preparedStmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseReadError();
        } finally {
            closeAll(preparedStmt, null);
        }
    }

    public double defaultAccountBalance() throws DatabaseReadError {
        PreparedStatement preparedStmt = null;
        ResultSet rs = null;
        try {
            /** the mysql insert statement */
            String query = "select DefaultAccountBalance from AccountBalance where Id= ?";
            /** create the mysql insert preparedStatement */
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, 1);

            /** execute the preparedStatement */
            rs = preparedStmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("DefaultAccountBalance");
            } else
                return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseReadError();
        } finally {
            closeAll(preparedStmt, rs);
        }
    }

    public ArrayList<Customer> initializeCustomers() throws DatabaseReadError {
        PreparedStatement preparedStmt = null;
        ResultSet rs = null;
        try {
            /** the mysql insert statement */
            String query = "select * from user";
            /** create the mysql insert preparedStatement */
            preparedStmt = conn.prepareStatement(query);

            /** execute the preparedStatement */
            rs = preparedStmt.executeQuery();
            ArrayList<Customer> customers = new ArrayList<>();
            while (rs.next()){
                int id = rs.getInt("UserId");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");
                String phone = rs.getString("Phone");
                String password = rs.getString("Password");
                double accountBalance = rs.getDouble("AccountBalance");
                customers.add(new Customer(id, firstName, lastName, email, phone, password, accountBalance));
            }
            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseReadError();
        } finally {
            closeAll(preparedStmt, rs);
        }
    }

    public ArrayList<String> initializeSearchTypes() throws DatabaseReadError {
        PreparedStatement preparedStmt = null;
        ResultSet rs = null;
        try {
            /** the mysql insert statement */
            String query = "select * from SearchType";
            /** create the mysql insert preparedStatement */
            preparedStmt = conn.prepareStatement(query);

            /** execute the preparedStatement */
            rs = preparedStmt.executeQuery();
            ArrayList<String> searchType = new ArrayList<>();
            while (rs.next()){
                String type = rs.getString("Type");
                searchType.add(type);
            }
            return searchType;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseReadError();
        } finally {
            closeAll(preparedStmt, rs);
        }
    }

    public ArrayList<Auction> initializeAuctions() throws DatabaseReadError {
        PreparedStatement preparedStmt = null;
        ResultSet rs = null;
        try {
            /** the mysql insert statement */
            String query = "select * from auction";
            /** create the mysql insert preparedStatement */
            preparedStmt = conn.prepareStatement(query);

            /** execute the preparedStatement */
            rs = preparedStmt.executeQuery();
            ArrayList<Auction> auctions = new ArrayList<>();
            while (rs.next()){
                int id = rs.getInt("AuctionId");
                String startDate = rs.getString("StartDate");
                String duration = rs.getString("Duration");
                String itemName = rs.getString("ItemName");
                String itemType = rs.getString("ItemType");
                double itemStartingPrice = rs.getDouble("ItemStartingPrice");
                String itemDescription = rs.getString("ItemDescription");
                String itemUrl = rs.getString("ItemUrl");
                String itemCondition = rs.getString("ItemCondition");
                String itemCountry = rs.getString("ItemCountry");
                int sellerId = rs.getInt("SellerId");
                auctions.add(new Auction(id, startDate, duration, new Item(itemName, itemType, itemStartingPrice, itemDescription, itemUrl, itemCondition, itemCountry), sellerId));
            }
            return auctions;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseReadError();
        } finally {
            closeAll(preparedStmt, rs);
        }
    }

    public ArrayList<Notification> initializeNotifications(int customerId) throws DatabaseReadError {
        PreparedStatement preparedStmt = null;
        ResultSet rs = null;
        try {
            /** the mysql insert statement */
            String query = "select * from Notification Where UserId=?";
            /** create the mysql insert preparedStatement */
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,customerId);

            /** execute the preparedStatement */
            rs = preparedStmt.executeQuery();
            ArrayList<Notification> notification = new ArrayList<>();
            while (rs.next()){
                String date = rs.getString("date");
                String context = rs.getString("Context");
                boolean seen = rs.getBoolean("Seen");
                notification.add(new Notification(date, context, seen));
            }
            return notification;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseReadError();
        } finally {
            closeAll(preparedStmt, rs);
        }
    }


    public boolean setNewBalance(double accountBalance, int id) throws DatabaseReadError {
        PreparedStatement preparedStmt = null;
        try {
            /** the mysql insert statement */
            String query = "update user set AccountBalance=? where userId=?";
            /** create the mysql insert preparedStatement */
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setDouble(1, accountBalance);
            preparedStmt.setInt(2, id);

            /** execute the preparedStatement */
            preparedStmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseReadError();
        } finally {
            closeAll(preparedStmt, null);
        }
    }

    public boolean createNotification(String notificationContext, int id, String date) throws DatabaseReadError {
        PreparedStatement preparedStmt = null;
        try {
            /** the mysql insert statement */
            String query = "insert into Notification (UserId, Date, Context, Seen)"
                    + " values (?, ?, ?, ?)";
            /** create the mysql insert preparedStatement */
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id);
            preparedStmt.setString(2, date);
            preparedStmt.setString(3, notificationContext);
            preparedStmt.setBoolean(4, false);

            /** execute the preparedStatement */
            preparedStmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseReadError();
        } finally {
            closeAll(preparedStmt, null);
        }
    }

    private static void closeAll(Statement st, ResultSet rs) {
        if (st != null)
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        if (rs != null)
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
