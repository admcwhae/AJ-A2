/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava_a2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author AmcwhaeLaptop
 */
public class DatabaseUtility{
    public static void performStatement(String statement) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        String databaseEndpoint = "advancedjavadb.ckgrcw20igrb.ap-southeast-2.rds.amazonaws.com:3306";
        String databaseName = "orderSystemDb";
        String username = "admin";
        String password = "advancedjava";
        
        String dbUrl = "jdbc:mysql://" + databaseEndpoint + "/" + databaseName + "?autoReconnect=true&useSSL=false";
        
        Connection conn = DriverManager.getConnection(dbUrl, username, password);
        try {
            /* you use the connection here */
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(statement);
            }
        } finally {
            //It's important to close the connection when you are done with it
            try { 
                conn.close(); 
            } 
            catch (Throwable e) {
                System.out.println(e); 
            }
        }
    }
}


    

