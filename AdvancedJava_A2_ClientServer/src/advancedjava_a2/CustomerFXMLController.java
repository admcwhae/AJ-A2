/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava_a2;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
 * FXML Customer Controller class
 *
 * @author akbar
 */
public class CustomerFXMLController implements Initializable {
        
    OrderSystem orderSystem;
    private ArrayList<Order> waitingOrders;
    private ArrayList<Order> servedOrders;
    PrintWriter outputToServer;
    Socket socket;
    
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
    
    public void initializeCustomer()
    {
        boolean connected = false; 
        while (!connected) {
            String serverAddress = AlertUtility.getServerInput();
            try { 
                socket = new Socket(serverAddress, 5000);
                connected = true;
                outputToServer = new PrintWriter(socket.getOutputStream());
            }
            catch (ConnectException ex) {
                System.out.println(ex.toString());
                AlertUtility.showError("Server cannot connect, please ensure that a chef module is running.");
            }
            catch (Exception ex){
                AlertUtility.showError(ex.toString());
            }
        }
    }
    
    public void initializeChef()
    {
        boolean connected = false; 
        while (!connected) {
            String serverAddress = AlertUtility.getServerInput();
            try { 
                socket = new Socket(serverAddress, 5001);
                connected = true;
                outputToServer = new PrintWriter(socket.getOutputStream());
                
                new Thread( () -> {
                    try {
                        ServerSocket serverSocket = new ServerSocket(5000);
                        Socket socket = serverSocket.accept();
                        BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            while (true) {
                                String line = inputFromClient.readLine();
                                if (Integer.parseInt(line) == 1) {
                                    // updates the list of billed orders
                                    setupListView();
                                }
                            }
                    }
                    catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                    }).start();
            }
            catch (ConnectException ex) {
                System.out.println(ex.toString());
                AlertUtility.showError("Server cannot connect, please ensure that a bill module is running.");
            }
            catch (Exception ex){
                AlertUtility.showError(ex.toString());
            }
        }
    }
    
    public void initializeBiller()
    {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(5001);
                Socket socket = serverSocket.accept();
                BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    String line = inputFromClient.readLine();
                    if (Integer.parseInt(line) == 1) {
                        // updates the list of pending orders
                        setupListView();
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }).start();
    }
    
    private void eventListenerBinder()
    {
        clearDisplayButton.setOnAction((ActionEvent event) -> {
            clearDisplayButtonClicked();
        });
        
        quitButton.setOnAction((ActionEvent event) -> {
            quitButtonClicked();
        });
        
        enterDataButton.setOnAction((ActionEvent event) -> {
            enterDataButtonClicked();
        });
        
        displayOrderButton.setOnAction((ActionEvent event) -> {
            displayOrderButtonClicked();
        });
    }
    
    //called when enterData button is clicked
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
            
            //enter order into database
            DatabaseUtility.performStatement("INSERT INTO orders (`customerName`, `tableNumber`, `foodItem`, `beverageItem`, `status`) VALUES ('" + 
                        newOrder.getCustomerName() + "', '" +
                        Integer.toString(newOrder.getTableNumber()) + "', '" +
                        newOrder.getFoodItem() + "', '" +
                        newOrder.getBeverageItem() + "', 'waiting');" 
            );
            // sends a one to the server to indicate that there is a new order to display
            outputToServer.println(1);
            outputToServer.flush();
            
            AlertUtility.showDialog("Order successfully placed.");
        }
        
        //setup ObservableList and and show in ListView
        setupListView();
    }
    
    /**
     * Sets Up the waiting orders ListView.
     */
    private void setupListView()
    {
        // Connect to database and select all the orders that have a status of pending
        String statement = "SELECT * FROM orders WHERE status = 'waiting';";
        waitingOrders = DatabaseUtility.getOrdersFromDatabase(statement);
        statement = "SELECT * FROM orders WHERE status = 'prepared';";
        servedOrders = DatabaseUtility.getOrdersFromDatabase(statement);

        //creating new ObservableList from existing ArrayList
        waitingOrdersObservableList = FXCollections.observableArrayList(waitingOrders);
        
        //assign above ObservableList to waitingOrders ListView
        waitingOrdersListView.setItems(waitingOrdersObservableList);
        
        //creating new ObservableList from existing ArrayList
        servedOrdersObservableList = FXCollections.observableArrayList(servedOrders);
        
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

    //setup radioButton toggle group
    private void setRadioButtonToggleGroup() 
    {
        breakfastRadioButton.setToggleGroup(radioButtonToggleGroup);
        breakfastRadioButton.setUserData("Breakfast");
        
        lunchRadioButton.setToggleGroup(radioButtonToggleGroup);
        lunchRadioButton.setUserData("Lunch");
        
        dinnerRadioButton.setToggleGroup(radioButtonToggleGroup);
        dinnerRadioButton.setUserData("Dinner");
    }
    
    /**
     * Populates the Food and Beverage ComboBoxes
     * 
     * @param mealType 
     */
    private void setupComboBoxes( String mealType )
    {
        //Getting food items from database
        final String SELECT_MENU_ITEMS_FOOD_QRY = "Select * FROM menu WHERE mealType = '" + mealType + "' AND type = 'Food'";
        ArrayList<MenuItem> foodItemsList = DatabaseUtility.getMenuItemsFromDatabase(SELECT_MENU_ITEMS_FOOD_QRY);
        //Getting beverage items from database
        final String SELECT_MENU_ITEMS_BEVERAGE_QRY = "Select * FROM menu WHERE mealType = '" + mealType + "' AND type = 'Beverage'";
        ArrayList<MenuItem> beverageItemsList = DatabaseUtility.getMenuItemsFromDatabase(SELECT_MENU_ITEMS_BEVERAGE_QRY);
        
        //create ObservableLists from ArrayList for use in comboBoxes
        ObservableList<MenuItem> foodList = FXCollections.observableArrayList(foodItemsList);
        ObservableList<MenuItem> beverageList = FXCollections.observableArrayList(beverageItemsList);        
        
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
            waitingOrders.get(listIndex);
            Order selectedOrder = waitingOrders.get(listIndex);
            int orderId = selectedOrder.getOrderId();
            if (AlertUtility.showConfirmation("Are you sure you want to prepare the selected order?")) {
                String statement = "UPDATE orders SET `status` = 'prepared' WHERE `orderId` = " + orderId + ";";
                DatabaseUtility.performStatement(statement);
                outputToServer.println(1);
                outputToServer.flush();
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
            Order selectedOrder = servedOrders.get(listIndex);
            int orderId = selectedOrder.getOrderId();
            if (AlertUtility.showConfirmation("Are you sure you want to bill the selected order?")) {
                //orderSystem.billOrder(selectedOrder);
                String statement = "UPDATE orders SET status = 'billed' WHERE orderId = " + orderId + ";";
                DatabaseUtility.performStatement(statement);
                setupListView();
                AlertUtility.showDialog("Order billed.");
                
                printReceipt(selectedOrder);      
            }
        }
        catch (Exception ex) {
            AlertUtility.showError(ex.getMessage());
        }
    }
    
    private void printReceipt(Order order) {
        String path = "receipt.txt";
        int orderId = order.getOrderId();       
        try {
            FileWriter write = new FileWriter(path);
            PrintWriter print = new PrintWriter(write);
            Formatter f = new Formatter(print);

            float[] prices = DatabaseUtility.getPrices(orderId);
            Date date = new Date();

            f.format("%30s\r\n", "CUSTOMER COPY");
            f.format("%29tr\r\n", date);
            f.format("%27tD\r\n", date);
            f.format("\r\n");
            f.format("%s %d\r\n", "Order:", orderId);
            f.format("%s %d\r\n", "Table:", order.getTableNumber() );
            f.format("%-5s %.15s\r\n", "Name:", order.getCustomerName() );
            f.format("%-30s %15s\r\n", "Item", "Price");
            f.format("%-30s %15s\r\n", "---------", "-----");
            f.format("%-35.35s %10.2f\r\n", order.getFoodItem(), prices[0]);
            f.format("%-35.35s %10.2f\r\n", order.getBeverageItem(), prices[1]);
            f.format("%-30s %15s\r\n", "---------", "-----");
            f.format("%-30.20s %15.2f\r\n", "GST",  prices[2] * 0.1);
            f.format("%-30.20s %15.2f\r\n", "Total",  prices[2]);
            f.format("\r\n");
            f.format("Thanks for eating with us."); 
            // save the file
            print.close();
 
            // if supported, java will open the file in a text editor to simulate printing of the receipt
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().edit(new File(path));
            }
        }
            catch (Exception ex) {
                System.out.println(ex.toString());
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
            
            tempMenuItem = new MenuItem(" ", " ", "Total Nutrients for each Type", price, energy, protein, carbohydrate, fat, dietaryFibre, 0 );
            menuItemObservableList.add(tempMenuItem);


            nutritionInformationTableView.setItems(menuItemObservableList);
        }
    }
    
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
