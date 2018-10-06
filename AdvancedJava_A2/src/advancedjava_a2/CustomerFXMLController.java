/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava_a2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    
    
    OrderSystem orderSystem;
    
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
        
        //create new OrderSystem object
        orderSystem = new OrderSystem();
        
        //setup ComboBoxes
        setupComboBoxes();
    }

    private void enterDataButtonClicked()
    {
        //check if data is valid
        if( validateFirstPartitionData() && validateSecondPartionData() )
        {
            //create new Order object
            Order newOrder = new Order(
                    customerNameTextField.getText(),
                    Integer.parseInt(tableNumberTextField.getText()),
                    radioButtonToggleGroup.getSelectedToggle().getUserData().toString(), 
                    foodComboBox.getSelectionModel().getSelectedItem().toString(),
                    beverageComboBox.getSelectionModel().getSelectedItem().toString()
            );
            
            //add above order to the orderSystem
            orderSystem.addNewOrder(newOrder);
        }
    }
    
    private boolean validateFirstPartitionData()
    {
        //check if data has been entered by user
        if( customerNameTextField.getText().length() > 0 && 
                tableNumberTextField.getText().length() > 0 &&
                radioButtonToggleGroup.getSelectedToggle() != null )
        {
            //check if entered data is valid USING LABMDA EXPRESSIONS
            if( customerNameTextField.getText().chars().allMatch(Character::isLetter) &&
                    tableNumberTextField.getText().chars().allMatch(Character::isDigit) )
            {
                return true;
            }
        }
        return false;
    }
    
    @FXML
    private void firstPartitionAction()
    {
        //check if all needed data has been entered by user
        if( customerNameTextField.getText().length() > 0 && 
                tableNumberTextField.getText().length() > 0 &&
                radioButtonToggleGroup.getSelectedToggle() != null )
        {
            //enable comboBoxes
            foodComboBox.setDisable(false);
            beverageComboBox.setDisable(false);
        }
        else
        {
            //disable comboBoxes
            foodComboBox.setDisable(true);
            beverageComboBox.setDisable(true);
        }
    }
    
    private boolean validateSecondPartionData()
    {
        //check if both comboBoxes are selected
        if( foodComboBox.getSelectionModel().isEmpty() || beverageComboBox.getSelectionModel().isEmpty() )
        {
            return false;
        }
        return true;
    }

    private void setRadioButtonToggleGroup() 
    {
        breakfastRadioButton.setToggleGroup(radioButtonToggleGroup);
        lunchRadioButton.setToggleGroup(radioButtonToggleGroup);
        dinnerRadioButton.setToggleGroup(radioButtonToggleGroup);
    }
    
    private void setupComboBoxes()
    {
        ObservableList<String> options = FXCollections.observableArrayList(
            "Option 1",
            "Option 2",
            "Option 3"
        );
        
        //adding sample values
        foodComboBox.getItems().clear();
        foodComboBox.getItems().addAll(options);
        foodComboBox.setDisable(true);
        
        //adding sample values
        beverageComboBox.getItems().clear();
        beverageComboBox.getItems().addAll(options);
        beverageComboBox.setDisable(true);
    }
    
}
