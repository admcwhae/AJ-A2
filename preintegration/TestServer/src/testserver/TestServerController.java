/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 *
 * @author AmcwhaeLaptop
 */
public class TestServerController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML 
    private TextArea textArea;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Hello");

        new Thread( () -> {
            try {
                ServerSocket serverSocket = new ServerSocket(5000);
                Socket socket = serverSocket.accept();
                textArea.appendText("Client Connected");

                BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    String line = inputFromClient.readLine();

                    System.out.println(line);
                    textArea.appendText(line);
                    
                    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                    String dbUrl = "jdbc:mysql://advancedjavadb.ckgrcw20igrb.ap-southeast-2.rds.amazonaws.com:3306/orderSystemDb?autoReconnect=true&useSSL=false";
                    Connection conn = DriverManager.getConnection(dbUrl, "admin", "advancedjava");
                    try {
                        /* you use the connection here */
                        String statement = "INSERT INTO test VALUES (3);";
                        try (Statement stmt = conn.createStatement()) {
                            stmt.executeUpdate(statement);
                        }
                    } finally {
                        //It's important to close the connection when you are done with it
                        try { conn.close(); } catch (Throwable e) {System.out.println(e); }
                    }
                }
            }
            catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }).start();
    }        
}    
    