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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author akbar
 */
public class CustomerFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    //first partition - "customer details"
    @FXML TextField customerNameTextField;
    @FXML TextField tableNumberTextField;
    final ToggleGroup radioButtonToggleGroup = new ToggleGroup();
    @FXML RadioButton breakfastRadioButton;
    @FXML RadioButton lunchRadioButton;
    @FXML RadioButton dinnerRadioButton;
    
    
    //second partition - "choose menu items"
    @FXML ComboBox foodComboBox;
    @FXML ComboBox beverageComboBox;
    
    
    //third partion - "order status"
    @FXML ListView pendingOrdersListView;
    @FXML ListView servedOrdersListView;
    
    //fourth partition - "nutrition information"
    @FXML TableView nutritionInformationTableView;
    
    //fifth partition - "command buttons"
    @FXML Button enterDataButton;
    @FXML Button displayChoicesButton;
    @FXML Button displayOrderButton;
    @FXML Button prepareButton;
    @FXML Button billButton;
    @FXML Button clearDisplayButton;
    @FXML Button quitButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //Assign toggleGroup to radio buttons
        setRadioButtonToggleGroup();
    }    

    private void setRadioButtonToggleGroup() {
        breakfastRadioButton.setToggleGroup(radioButtonToggleGroup);
        breakfastRadioButton.setSelected(true);
        lunchRadioButton.setToggleGroup(radioButtonToggleGroup);
        dinnerRadioButton.setToggleGroup(radioButtonToggleGroup);
    }    
    
}
