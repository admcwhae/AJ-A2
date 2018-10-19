/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testclient;

import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author AmcwhaeLaptop
 */
public class TestClientController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML 
    private Button orderButton;
    
    private PrintWriter outputToServer;
    
    @FXML
    private void handleOrderButton(ActionEvent event) {
        label.setText("Sent Message");
        outputToServer.println(1);
        outputToServer.flush();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            
            Socket socket = new Socket("localhost", 5000);
            outputToServer = new PrintWriter(socket.getOutputStream());

        }
        catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }    
    
}
