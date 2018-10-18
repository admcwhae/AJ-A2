/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava_a2;

/**
 * A Customers Order
 * 
 * @author Alex McWhae
 */
public class Order {
    private int orderId;
    
    private String customerName;
    
    private int tableNumber;
    // breakfast, lunch or dinner
    private String mealType;
    // food item selected
    private String foodItem;
    // drink selected
    private String beverageItem;
    // string containing both food and beverage
    private String orderedItems;
    // order status, 
    private String orderStatus;
    
    /**
     * Constructor for creating an order object when customer places initially places an order
     * 
     * @param customerName The customer's name.
     * @param tableNumber The table number.
     * @param mealType The meal type (Breakfast, Lunch, or Dinner).
     * @param foodItem The food item selected from the menu.
     * @param beverageItem The beverage selected from the menu.
     */
    public Order(String customerName, int tableNumber, String mealType, String foodItem, String beverageItem) {
        this.customerName = customerName;
        this.tableNumber = tableNumber;
        this.mealType = mealType;
        this.foodItem = foodItem;
        this.beverageItem = beverageItem;
        this.orderedItems = foodItem + ", " + beverageItem;
        this.orderStatus = "waiting";
    }
    
    /**
     * Constructor for creating an order object when customer places initially places an order
     * 
     * @param orderId
     * @param customerName The customer's name.
     * @param tableNumber The table number.
     * @param foodItem The food item selected from the menu.
     * @param beverageItem The beverage selected from the menu.
     * @param orderStatus
     */
    public Order(int orderId, String customerName, int tableNumber, String foodItem, String beverageItem, String orderStatus) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.tableNumber = tableNumber;
        this.foodItem = foodItem;
        this.beverageItem = beverageItem;
        this.orderedItems = foodItem + ", " + beverageItem;
        this.orderStatus = orderStatus;
    }
    
    /**
     * Changes the order status to served
     */
    public void serveOrder() {
        this.orderStatus = "served";
    }
    
    /**
     * Sets the order status to billed
     */
    public void billOrder() {
        this.orderStatus = "billed";
    }
    
    /**
     * Displays the order in a String format for display in the GUI
     * 
     * @return String outlining the order
     */
    public String orderToString() {
        String returnString = "";
        returnString += customerName + " | ";
        returnString += "Table : " + tableNumber + " | ";
        returnString += foodItem + ", " + beverageItem;
        return returnString;
    }
    
    @Override
    public String toString() {
        return this.orderToString();
    }

    /**
     * Gets the Customer Name
     * 
     * @return customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the Customer Name
     * 
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets the table number
     * 
     * @return tableNumber
     */
    public int getTableNumber() {
        return tableNumber;
    }

    /**
     * Sets the table number
     * 
     * @param tableNumber
     */
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    /**
     * Gets the meal type
     * 
     * @return mealType
     */
    public String getMealType() {
        return mealType;
    }

    /**
     * Sets the meal type
     * 
     * @param mealType
     */
    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    /**
     * Gets the food item
     * 
     * @return foodItem
     */
    public String getFoodItem() {
        return foodItem;
    }

    /**
     * Sets the food item
     * 
     * @param foodItem
     */
    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    /**
     * Gets the beverage item
     * 
     * @return beverageItem
     */
    public String getBeverageItem() {
        return beverageItem;
    }

    /**
     * Sets the Beverage Item
     * 
     * @param beverageItem
     */
    public void setBeverageItem(String beverageItem) {
        this.beverageItem = beverageItem;
    }

    /**
     * Gets the Order Status
     * 
     * @return
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * Sets the Order Status
     * 
     * @param orderStatus
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    /**
     * Gets the Ordered items
     * @return orderedItems
     */
    public String getOrderedItems() {
        return this.orderedItems;
    }

    /**
     * Gets the Order ID
     * @return orderId
     */
    public int getOrderId() {
        return orderId;
    }
}



