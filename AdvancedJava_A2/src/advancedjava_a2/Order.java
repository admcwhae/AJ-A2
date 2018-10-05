/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava_a2;

/**
 *
 * @author Alex McWhae
 */
public class Order {
    private String customerName;
    
    private int tableNumber;
    // breakfast, lunch or dinner
    private String mealType;
    // food item selected
    private String foodItem;
    // drink selected
    private String beverageItem;
    // order status, 
    private String orderStatus;
    
    /**
     * Constructor for creating an order object when customer places initially places an order
     * 
     * @param customerName
     * @param tableNumber
     * @param mealType
     * @param foodItem
     * @param beverageItem 
     */
    public Order(String customerName, int tableNumber, String mealType, String foodItem, String beverageItem) {
        this.customerName = customerName;
        this.tableNumber = tableNumber;
        this.mealType = mealType;
        this.foodItem = foodItem;
        this.beverageItem = beverageItem;
        this.orderStatus = "waiting";
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public String getBeverageItem() {
        return beverageItem;
    }

    public void setBeverageItem(String beverageItem) {
        this.beverageItem = beverageItem;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}



