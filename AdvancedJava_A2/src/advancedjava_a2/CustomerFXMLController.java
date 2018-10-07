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
    ObservableList<Order> waitingOrdersObservableList;
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
    
    /**
     * Initializes the CustomerFXMLController class.
     */
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

    //called by the UI
    @FXML
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
            
            //reset first and second GUI Partitions
            resetGUIFirstPartition();
            resetGUISecondPartition();
        }
        
        //setup ObservableList and and show in ListView
        setupListView();
    }
    
    /**
     * Sets Up the pending orders ListView.
     */
    private void setupListView()
    {
        //creating new ObservableList from existing ArrayList
        waitingOrdersObservableList = FXCollections.observableArrayList(orderSystem.getWaitingOrders());
        
        //assign above ObservableList to pendingOrders ListView
        pendingOrdersListView.setItems(waitingOrdersObservableList);
    }
    
    /**
     * Validates the data entered by the user in the first partition (i.e. "Customer Details") of the GUI.
     */
    private boolean validateFirstPartitionData()
    {
        //check if data has been entered by user
        if( customerNameTextField.getText().length() > 0 && 
                !customerNameTextField.getText().chars().allMatch(Character::isSpaceChar) &&
                tableNumberTextField.getText().length() > 0 &&
                !tableNumberTextField.getText().chars().allMatch(Character::isSpaceChar) &&
                radioButtonToggleGroup.getSelectedToggle() != null )
        {
            //check if entered data is valid USING LABMDA EXPRESSIONS
            if( customerNameTextField.getText().chars().allMatch( n -> Character.isLetter(n) || Character.isSpaceChar(n) ) &&
                    tableNumberTextField.getText().chars().allMatch(Character::isDigit) )
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Method called by the GUI whenever changes are made to the first partition ("Customer Details").
     */
    @FXML
    private void firstPartitionAction()
    {
        //check if all needed data has been entered by user
        if( customerNameTextField.getText().length() > 0 && 
                !customerNameTextField.getText().chars().allMatch(Character::isSpaceChar) &&
                tableNumberTextField.getText().length() > 0 &&
                !tableNumberTextField.getText().chars().allMatch(Character::isSpaceChar) &&
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
    
    /**
     * Validates the data entered by the user in the second partition (i.e. "Choose Menu Items") of the GUI. 
     */
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
        breakfastRadioButton.setUserData("Breakfast");
        
        lunchRadioButton.setToggleGroup(radioButtonToggleGroup);
        lunchRadioButton.setUserData("Lunch");
        
        dinnerRadioButton.setToggleGroup(radioButtonToggleGroup);
        dinnerRadioButton.setUserData("Dinner");
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
    
    private void resetGUIFirstPartition()
    {
        customerNameTextField.clear();
        tableNumberTextField.clear();
        breakfastRadioButton.setSelected(false);
        lunchRadioButton.setSelected(false);
        dinnerRadioButton.setSelected(false);
    }
    
    private void resetGUISecondPartition()
    {
        foodComboBox.getSelectionModel().clearSelection();
        foodComboBox.setDisable(true);
        beverageComboBox.getSelectionModel().clearSelection();
        beverageComboBox.setDisable(true);
    }
}
