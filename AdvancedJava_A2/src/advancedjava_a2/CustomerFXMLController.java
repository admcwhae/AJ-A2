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
    Menu menu = new Menu();
    
    
    //third partion - "order status"
    @FXML ListView waitingOrdersListView;
    ObservableList<Order> waitingOrdersObservableList;
    @FXML ListView servedOrdersListView;
    ObservableList<Order> servedOrdersObservableList;
    
    
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
        //setupComboBoxes();
        foodComboBox.setDisable(true);
        beverageComboBox.setDisable(true);
        setupListView();
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
     * Sets Up the waiting orders ListView.
     */
    private void setupListView()
    {
        //creating new ObservableList from existing ArrayList
        waitingOrdersObservableList = FXCollections.observableArrayList(orderSystem.getWaitingOrders());
        
        //assign above ObservableList to waitingOrders ListView
        waitingOrdersListView.setItems(waitingOrdersObservableList);
        
        //creating new ObservableList from existing ArrayList
        servedOrdersObservableList = FXCollections.observableArrayList(orderSystem.getServedOrders());
        
        //assign above ObservableList to servedOrders ListView
        servedOrdersListView.setItems(servedOrdersObservableList);
    }
    
    /**
     * Validates the data entered by the user in the first partition (i.e. "Customer Details") of the GUI.
     */
    private boolean validateFirstPartitionData()
    {
        // TODO updated with new code, old code commented out below, need to find out how to implement lambda conditions for these. 
        boolean result = true;
        try {
        // test customer name
        if (customerNameTextField.getText().isEmpty() || customerNameTextField.getText().equals(null)) 
            throw new Exception("Please enter a name.");
        // test table number
        if (tableNumberTextField.getText().isEmpty() || tableNumberTextField.getText().equals(null))
            throw new Exception("Please enter a table number.");   
        // make sure that table number is a number
        if (!tableNumberTextField.getText().chars().allMatch(Character::isDigit)) 
            throw new Exception("Please ensure table number is a digit.");
        if (radioButtonToggleGroup.getSelectedToggle() == null)
            throw new Exception("Please select one of the meal options.");
        
        }
        catch (Exception ex) {
            AlertUtility.showError(ex.getMessage());
            result = false;
        }
        return result;
        
//        //check if data has been entered by user
//        if( customerNameTextField.getText().length() > 0 && 
//                !customerNameTextField.getText().chars().allMatch(Character::isSpaceChar) &&
//                tableNumberTextField.getText().length() > 0 &&
//                !tableNumberTextField.getText().chars().allMatch(Character::isSpaceChar) &&
//                radioButtonToggleGroup.getSelectedToggle() != null )
//        {
//            //check if entered data is valid USING LABMDA EXPRESSIONS
//            if( customerNameTextField.getText().chars().allMatch( n -> Character.isLetter(n) || Character.isSpaceChar(n) ) &&
//                    tableNumberTextField.getText().chars().allMatch(Character::isDigit) )
//            {
//                return true;
//            }
//        }
//        return false;
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
            setupComboBoxes(radioButtonToggleGroup.getSelectedToggle().getUserData().toString());
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
        boolean result = true;
        try {
            // check if food item selected
            if (foodComboBox.getSelectionModel().isEmpty())
                throw new Exception("Please select a food item.");
            // check if beverage item is selected
            if (beverageComboBox.getSelectionModel().isEmpty())
                throw new Exception("Please select a beverage item.");          
        }
        catch (Exception ex) {
            AlertUtility.showError(ex.getMessage());
            result = false;
        }
        return result;
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
    
    private void setupComboBoxes( String mealType )
    {
        ObservableList<String> options = FXCollections.observableArrayList(
            "Option 1",
            "Option 2",
            "Option 3"
        );
        
        ObservableList<MenuItem> foodList = FXCollections.observableArrayList(menu.getMenuItemsByMealType( mealType, "Food" ));
        ObservableList<MenuItem> beverageList = FXCollections.observableArrayList(menu.getMenuItemsByMealType( mealType, "Beverage" ));
        
        //adding sample values
        foodComboBox.getItems().clear();
        foodComboBox.getItems().addAll(foodList);
        foodComboBox.setDisable(true);
        
        //adding sample values
        beverageComboBox.getItems().clear();
        beverageComboBox.getItems().addAll(beverageList);
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
    
    @FXML
    private void prepareButtonClicked() {
        try {
            int listIndex = waitingOrdersListView.getSelectionModel().getSelectedIndex();
            if (listIndex < 0) 
                throw new Exception("Please select an order to prepare.");
            Order selectedOrder = orderSystem.getWaitingOrder(listIndex);
            if (AlertUtility.showConfirmation("Are you sure you want to prepare the selected order?")) {
                orderSystem.serveOrder(selectedOrder);
                setupListView();
                AlertUtility.showDialog("Order prepared.");
            }
        }
        catch (Exception ex) {
            AlertUtility.showError(ex.getMessage());
        }
    }
    
    @FXML
    private void billButtonClicked() {
        try {
            int listIndex = servedOrdersListView.getSelectionModel().getSelectedIndex();
            if (listIndex < 0) 
                throw new Exception("Please select an order to bill.");            
            Order selectedOrder = orderSystem.getServedOrder(listIndex);
            if (AlertUtility.showConfirmation("Are you sure you want to bill the selected order?")) {
                orderSystem.billOrder(selectedOrder);
                setupListView();
                AlertUtility.showDialog("Order billed.");
            }
        }
        catch (Exception ex) {
            AlertUtility.showError(ex.getMessage());
        }
    }
    
    /**
     * Contains the logic for when the quit button is clicked 
     */
    @FXML
    private void quitButtonClicked() {
        exitProgram();
        
    }
    
    /**
     * Exits the program displaying a confirmation message before hand.
     */
    private void exitProgram() {
        if (AlertUtility.showConfirmation("Are you sure you want to exit the program?")) 
            System.exit(0);
    }
}
