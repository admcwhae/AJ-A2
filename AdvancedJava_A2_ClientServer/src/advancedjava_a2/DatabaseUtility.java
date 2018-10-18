/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava_a2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database Helper class
 * 
 * @author AmcwhaeLaptop
 */
public class DatabaseUtility{
    
    // databse connection constants, can be edited if running on the local system
    private final static String DB_ENDPOINT = "advancedjavadb.ckgrcw20igrb.ap-southeast-2.rds.amazonaws.com";
    private final static String DB_PORT = "3306";
    private final static String DB_NAME = "orderSystemDb";
    private final static String DB_USERNAME = "admin";
    private final static String DB_PASSWORD = "advancedjava";
    
    /**
     * Runs the specified query in the database
     * 
     * @param statement
     */
    public static void performStatement(String statement) {
       try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String dbUrl = "jdbc:mysql://" + DB_ENDPOINT + ":" + DB_PORT + "/" + DB_NAME + "?autoReconnect=true&useSSL=false";

            Connection conn = DriverManager.getConnection(dbUrl, DB_USERNAME, DB_PASSWORD);
            try {
                /* you use the connection here */
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(statement);
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } finally {
                //It's important to close the connection when you are done with it
                try {
                    conn.close();
                } catch (Throwable e) {
                    System.out.println(e);
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CustomerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(CustomerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CustomerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Gets the Menu Items from Database according to the specified query
     * 
     * @param statement
     * @return List of Menu Items
     */
    public static ArrayList<MenuItem> getMenuItemsFromDatabase(String statement) {
       
        ArrayList<MenuItem> menuItemsList = new ArrayList<MenuItem>();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String dbUrl = "jdbc:mysql://" + DB_ENDPOINT + ":" + DB_PORT + "/" + DB_NAME + "?autoReconnect=true&useSSL=false";

            Connection conn = DriverManager.getConnection(dbUrl, DB_USERNAME, DB_PASSWORD);
            try {
                /* you use the connection here */
                try (Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery(statement);                                                                                  
                    
                    while(rs.next())
                    {                   
                        int menuItemId = rs.getInt("menuId");                        
                        String menuDescription = rs.getString("type");
                        String mealType = rs.getString("mealType");
                        String itemName = rs.getString("name");
                        double price = rs.getDouble("price");
                        double energy = rs.getDouble("energy");
                        double protein = rs.getDouble("protein");
                        double carbohydrates = rs.getDouble("carbohydrates");
                        double fat = rs.getDouble("fat");
                        double fibre = rs.getDouble("fibre");
                        
                        MenuItem item = new MenuItem(menuDescription, mealType, itemName, price, energy, protein, carbohydrates, fat, fibre, menuItemId);
                        
                        menuItemsList.add(item);
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } finally {
                //It's important to close the connection when you are done with it
                try {
                    conn.close();
                } catch (Throwable e) {
                    System.out.println(e);
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CustomerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(CustomerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CustomerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return menuItemsList;
    }
    
    /**
     * Gets the orders from Database according to the specified query
     * 
     * @param statement
     * @return List of Orders
     */
    public static ArrayList<Order> getOrdersFromDatabase(String statement) {
        ArrayList<Order> orders = new ArrayList<>();
        
       try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String dbUrl = "jdbc:mysql://" + DB_ENDPOINT + ":" + DB_PORT + "/" + DB_NAME + "?autoReconnect=true&useSSL=false";

            Connection conn = DriverManager.getConnection(dbUrl, DB_USERNAME, DB_PASSWORD);
            try {
                /* you use the connection here */
                try (Statement stmt = conn.createStatement()) {
                    ResultSet resultSet = stmt.executeQuery(statement);
                    while (resultSet.next()) {
                        int orderId = Integer.parseInt(resultSet.getString("orderId"));
                        String customerName = resultSet.getString("customerName");
                        int tableNumber = Integer.parseInt(resultSet.getString("tableNumber"));
                        String foodItem = resultSet.getString("foodItem");
                        String beverageItem = resultSet.getString("beverageItem");
                        String orderStatus = resultSet.getString("status");
                      
                        Order order = new Order(orderId, customerName, tableNumber, foodItem, beverageItem, orderStatus);
                        orders.add(order);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } finally {
                //It's important to close the connection when you are done with it
                try {
                    conn.close();
                } catch (Throwable e) {
                    System.out.println(e);
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CustomerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(CustomerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CustomerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return orders;
    }
}
