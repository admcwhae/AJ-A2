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
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
    
    
    //fourth partition - "nutrition information & order table"
    @FXML TableView<MenuItem> nutritionInformationTableView;
        @FXML TableColumn<MenuItem, String> itemNameColumn;
        @FXML TableColumn<MenuItem, Double> energyColumn;
        @FXML TableColumn<MenuItem, Double> proteinColumn;
        @FXML TableColumn<MenuItem, Double> carbohydrateColumn;
        @FXML TableColumn<MenuItem, Double> totalFatColumn;
        @FXML TableColumn<MenuItem, Double> fibreColumn;
        @FXML TableColumn<MenuItem, Double> priceColumn;
    @FXML TableView<Order> orderInformationTableView;
        @FXML TableColumn<Order, String> customerNameColumn;
        @FXML TableColumn<Order, String> orderedItemsColumn;
    
    
    //fifth partition - "command buttons"
    @FXML Separator bottomSeparator;
    @FXML Button enterDataButton;
    @FXML Button displayChoicesButton;
    @FXML Button displayOrderButton;
    @FXML Button prepareButton;
    @FXML Button billButton;
    @FXML Button clearDisplayButton;
    @FXML Button quitButton;
    
    /**
     * Initializes the CustomerFXMLController class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //Assign toggleGroup to radio buttons
        setRadioButtonToggleGroup();
        
        //create new OrderSystem object
        orderSystem = new OrderSystem();
         
        //setup ComboBoxes
        foodComboBox.setDisable(true);
        beverageComboBox.setDisable(true);
        setupListView();
        
        //setup tableColumns
        setupTableColumns();
        
        //setup GUI listener events
        eventListenerBinder();
    }
    
    private void eventListenerBinder()
    {
        clearDisplayButton.setOnAction((ActionEvent event) -> {
            clearDisplayButtonClicked();
        });
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
        
        //disable prepare and bill buttons
        prepareButton.setDisable(true);
        billButton.setDisable(true);
        
        //set OnClick event handler for waitingOrdersList
        //lambda used in event handling
        waitingOrdersListView.setOnMouseClicked((MouseEvent event) -> {
            if(!waitingOrdersListView.getSelectionModel().isEmpty())
            {
                prepareButton.setDisable(false);
            }
            servedOrdersListView.getSelectionModel().clearSelection();
            billButton.setDisable(true);
        });
        
        //set OnClick event handler for servedOrdersList
        //lambda used in event handling
        servedOrdersListView.setOnMouseClicked((MouseEvent event) -> {
            if(!servedOrdersListView.getSelectionModel().isEmpty())
            {
                billButton.setDisable(false);
            }
            waitingOrdersListView.getSelectionModel().clearSelection();
            prepareButton.setDisable(true);
        });
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
        if (customerNameTextField.getText().isEmpty() || customerNameTextField.getText() == null)
        {
            customerNameTextField.setStyle("-fx-text-inner-color: red;");
            throw new Exception("Please enter a name.");
        }
        if( !customerNameTextField.getText().chars().allMatch( n -> Character.isLetter(n) || Character.isSpaceChar(n) ) )
        {
            customerNameTextField.setStyle("-fx-text-inner-color: red;");
            throw new Exception("Please ensure name contains alphabets or spaces only.");
        }
        
        // test table number
        if (tableNumberTextField.getText().isEmpty() || tableNumberTextField.getText() == null)
            throw new Exception("Please enter a table number.");   
        // make sure that table number is a number
        if (!tableNumberTextField.getText().chars().allMatch( n -> Character.isDigit(n) )) 
        {
            tableNumberTextField.setStyle("-fx-text-inner-color: red;");
            throw new Exception("Please ensure table number is a digit.");
        }
        // ensure table number between 1 and 8
        if (Integer.parseInt(tableNumberTextField.getText()) < 1 || Integer.parseInt(tableNumberTextField.getText()) > 8) 
        {
            tableNumberTextField.setStyle("-fx-text-inner-color: red;");
            throw new Exception("Table number must be between 1 and 8.");
        }
        
        if (radioButtonToggleGroup.getSelectedToggle() == null)
            throw new Exception("Please select one of the meal options.");
        
        }
        catch (Exception ex) {
            AlertUtility.showError(ex.getMessage());
            result = false;
        }
        return result;
    }
    
    /**
     * Method called by the GUI whenever changes are made to the first partition ("Customer Details").
     */
    @FXML
    private void firstPartitionAction()
    {
        customerNameTextField.setStyle("-fx-text-inner-color: black;");
        tableNumberTextField.setStyle("-fx-text-inner-color: black;");
        
        
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
        //create ObservableLists from ArrayList for use in comboBoxes
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
    
    private void setupTableColumns()
    {
        //for nutrition table
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("itemName"));
        energyColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, Double>("energy"));
        proteinColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, Double>("protein"));
        carbohydrateColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, Double>("carbohydrates"));
        totalFatColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, Double>("fat"));
        fibreColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, Double>("dietaryFibre"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, Double>("price"));
        
        //for order table
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("customerName"));
        orderedItemsColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("orderedItems"));
        
        //hide both tables & bottom separator bar
        nutritionInformationTableView.setVisible(false);
        orderInformationTableView.setVisible(false);
        bottomSeparator.setVisible(false);
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
    
    private void resetGUIThirdPartition()
    {
        waitingOrdersListView.getSelectionModel().clearSelection();
        servedOrdersListView.getSelectionModel().clearSelection();
    }
    
    private void resetGUIFourthPartition()
    {
        nutritionInformationTableView.setVisible(false);
        orderInformationTableView.setVisible(false);
        bottomSeparator.setVisible(false);
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
    
    @FXML
    private void displayChoicesButtonClicked()
    {
        if(foodComboBox.getSelectionModel().isEmpty() || beverageComboBox.getSelectionModel().isEmpty())
        {
            if(foodComboBox.getSelectionModel().isEmpty())
                AlertUtility.showError("A food item must be selected to View Choices.");
            else
                AlertUtility.showError("A beverage item must be selected to View Choices.");
        }
        else
        {
            orderInformationTableView.setVisible(false);
            nutritionInformationTableView.setVisible(true);
            bottomSeparator.setVisible(true);
            
            ObservableList<MenuItem> menuItemObservableList = FXCollections.observableArrayList();
            MenuItem tempMenuItem;

            //get selected Object from comboBoxes and cast to MenuItem
            menuItemObservableList.add((MenuItem)foodComboBox.getSelectionModel().getSelectedItem());
            menuItemObservableList.add((MenuItem)beverageComboBox.getSelectionModel().getSelectedItem());

            double price = menuItemObservableList.get(0).getPrice() + menuItemObservableList.get(1).getPrice();
            double energy = menuItemObservableList.get(0).getEnergy() + menuItemObservableList.get(1).getEnergy();
            double protein = menuItemObservableList.get(0).getProtein()+ menuItemObservableList.get(1).getProtein();
            double carbohydrate = menuItemObservableList.get(0).getCarbohydrates()+ menuItemObservableList.get(1).getCarbohydrates();
            double fat = menuItemObservableList.get(0).getFat()+ menuItemObservableList.get(1).getFat();
            double dietaryFibre = menuItemObservableList.get(0).getDietaryFibre()+ menuItemObservableList.get(1).getDietaryFibre();

            //tempMenuItem = new MenuItem(" ", " ", " ", Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, 0 );
            //menuItemObservableList.add(tempMenuItem);
            
            tempMenuItem = new MenuItem(" ", " ", "Total Nutrients for each Type", price, energy, protein, carbohydrate, fat, dietaryFibre, 0 );
            menuItemObservableList.add(tempMenuItem);


            nutritionInformationTableView.setItems(menuItemObservableList);
        }
    }
    
    @FXML
    private void displayOrderButtonClicked()
    {
        if(waitingOrdersListView.getSelectionModel().isEmpty() && servedOrdersListView.getSelectionModel().isEmpty())
        {
            AlertUtility.showError("An order must be selected to View Order.");
        }
        else
        {
            orderInformationTableView.setVisible(true);
            nutritionInformationTableView.setVisible(false);
            bottomSeparator.setVisible(true);
            
            ObservableList<Order> selectedOrderObservableList = FXCollections.observableArrayList();
            selectedOrderObservableList.removeAll();
            
            if(!waitingOrdersListView.getSelectionModel().isEmpty())
            {
                selectedOrderObservableList.add((Order)waitingOrdersListView.getSelectionModel().getSelectedItem());
            }
            
            if(!servedOrdersListView.getSelectionModel().isEmpty())
            {
                selectedOrderObservableList.add((Order)servedOrdersListView.getSelectionModel().getSelectedItem());
            }
            
            orderInformationTableView.setItems(selectedOrderObservableList);
        }
    }
    
    private void clearDisplayButtonClicked()
    {
        if(AlertUtility.showConfirmation("Are you sure you want to clear the display?"))
        {
            resetGUIFirstPartition();
            resetGUISecondPartition();
            resetGUIThirdPartition();
            resetGUIFourthPartition();
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
