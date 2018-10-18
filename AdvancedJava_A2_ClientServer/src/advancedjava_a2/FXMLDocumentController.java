/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava_a2;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    
    Menu menu = new Menu();
    
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
        
        //Create the database
        final String DATABASE_ORDER_SYSTEM_DB_QRY = "CREATE DATABASE IF NOT EXISTS orderSystemDb";
        DatabaseUtility.performStatement(DATABASE_ORDER_SYSTEM_DB_QRY);
        
        //Drop the old menu table
        final String DROP_TABLE_MENU_QRY = "DROP TABLE IF EXISTS `orderSystemDb`.`menu`";     
        DatabaseUtility.performStatement(DROP_TABLE_MENU_QRY);
        
        //Create the menu table
        final String TABLE_MENU_QRY = "CREATE TABLE IF NOT EXISTS `orderSystemDb`.`menu` ( `menuId` INT NOT NULL , `type` VARCHAR(10) NOT NULL , `mealType` VARCHAR(10) NOT NULL , `name` VARCHAR(100) NOT NULL , `price` DOUBLE NOT NULL , `energy` DOUBLE NOT NULL , `protein` DOUBLE NOT NULL , `carbohydrates` DOUBLE NOT NULL , `fat` DOUBLE NOT NULL , `fibre` DOUBLE NOT NULL , PRIMARY KEY (`menuId`))";
        DatabaseUtility.performStatement(TABLE_MENU_QRY);
        
        //Create the orders table
        final String TABLE_ORDERS_QRY = "CREATE TABLE IF NOT EXISTS `orderSystemDb`.`orders` ( `orderId` INT NOT NULL AUTO_INCREMENT, `customerName` VARCHAR(30) NOT NULL , `tableNumber` INT(10) NOT NULL , `foodItem` VARCHAR(100) NOT NULL REFERENCES menu(name) , `beverageItem` VARCHAR(100) NOT NULL REFERENCES menu(name), `status` VARCHAR(100) NOT NULL, PRIMARY KEY (orderId));";
        DatabaseUtility.performStatement(TABLE_ORDERS_QRY);

        //Getting all Menu Items
        ArrayList<MenuItem> menuItemsList = menu.getMenuItemsList();
        
        //Inserting all Menu Items in database
        for(MenuItem item : menuItemsList)
        {
            DatabaseUtility.performStatement("INSERT INTO menu (`menuId`, `type`, `mealType`, `name`, `price`, `energy`, `protein`, `carbohydrates`, `fat`, `fibre`) VALUES ('" + 
                    item.getMenuItemId() + "', '" +                    
                    item.getMenuDescription() + "', '" +
                    item.getMealType() + "', '" +
                    item.getItemName() + "', '" +
                    item.getPrice() + "', '" +
                    item.getEnergy() + "', '" +
                    item.getProtein() + "', '" +
                    item.getCarbohydrates() + "', '" +
                    item.getFat() + "', '" +                    
                    item.getDietaryFibre() + "');" 
            );
        }
    }    
    
}
