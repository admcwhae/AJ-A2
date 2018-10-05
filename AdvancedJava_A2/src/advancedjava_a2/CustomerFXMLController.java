/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava_a2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author akbar
 */
public class CustomerFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    @FXML TextField customerNameTextField;
    @FXML TextField tableNumberTextField;
    @FXML RadioButton breakfastRadioButton;
    @FXML RadioButton lunchRadioButton;
    @FXML RadioButton dinnerRadioButton;
    
    @FXML ComboBox foodComboBox;
    @FXML ComboBox beverageComboBox;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
