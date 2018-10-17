/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava_a2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author AmcwhaeLaptop
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML Button sampleButton;
    @FXML Button customerModeButton;
    @FXML Button chefModeButton;
    @FXML Button billerModeButton;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
                    Parent mainViewParent = FXMLLoader.load(getClass().getResource("CustomerFXML.fxml"));
                    Scene mainViewScene = new Scene(mainViewParent);

                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                    window.setScene(mainViewScene);
                    window.show();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    @FXML
    private void handleCustomerModeButtonAction(ActionEvent event) {
        try {
                    Parent mainViewParent = FXMLLoader.load(getClass().getResource("CustomerClientServerFXML.fxml"));
                    Scene mainViewScene = new Scene(mainViewParent);

                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                    window.setScene(mainViewScene);
                    window.show();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    @FXML
    private void handleChefModeButtonAction(ActionEvent event) {
        try {
                    Parent mainViewParent = FXMLLoader.load(getClass().getResource("ChefClientServerFXML.fxml"));
                    Scene mainViewScene = new Scene(mainViewParent);

                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                    window.setScene(mainViewScene);
                    window.show();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    @FXML
    private void handleBillerModeButtonAction(ActionEvent event) {
        try {
                    Parent mainViewParent = FXMLLoader.load(getClass().getResource("BillerClientServerFXML.fxml"));
                    Scene mainViewScene = new Scene(mainViewParent);

                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                    window.setScene(mainViewScene);
                    window.show();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
