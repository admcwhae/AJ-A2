/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava_a2;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

/**
 * A helper class that contains some methods to help display javafx dialogs
 * 
 * @author Alex McWhae
 */
public class AlertUtility {
    
    /**
     * Displays an error alert with the message supplied
     * 
     * @param message The message to display along with the error dialog
     */
    public static void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Displays an information alert with the message supplied
     * 
     * @param message The message to display along with the information dialog
     */    
    public static void showDialog(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();        
    }
    
    /**
     * Prompts the user to confirm something in a dialog, continuing with the program if yes is selected
     * 
     * @param message the message to display with the dialog
     * @return true if the user selected yes, otherwise false
     */
    public static boolean showConfirmation(String message) {
        boolean result = false;
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.showAndWait();
        
        if (alert.getResult() == ButtonType.OK) {
            result = true;
        }
        else {
            result = false;
        }
        return result;   
    }
}
