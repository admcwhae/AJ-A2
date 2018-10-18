/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava_a2;

import java.util.ArrayList;

/**
 * Ordering System of the Restaurant
 * 
 * @author Alex McWhae
 */
public class OrderSystem {
    // List of orders with waiting state
    private ArrayList<Order> waitingOrders;
    // List of orders with served state
    private ArrayList<Order> servedOrders;
    // List of orders with billed state
    private ArrayList<Order> billedOrders;
    
    
    
    /**
     * Constructor for OrderSystem
     */
    public OrderSystem() {
        this.waitingOrders = new ArrayList<>();
        this.servedOrders = new ArrayList<>();
        this.billedOrders = new ArrayList<>();
    }
    
    /**
     * Adds an order to the list of orders 
     * 
     * @param order the order to be added to the program
     */
    public void addNewOrder(Order order) {
        waitingOrders.add(order);
    }
    
    /**
     * Gets the waiting order
     * 
     * @param index
     * @return order
     */
    public Order getWaitingOrder(int index) {
        Order returnOrder = null;
        returnOrder = waitingOrders.get(index);
        return returnOrder;
    }
    
    /**
     * Gets the served order
     * 
     * @param index
     * @return order
     */
    public Order getServedOrder(int index) {
        Order returnOrder = null;
        returnOrder = servedOrders.get(index);
        return returnOrder;
    }
    
    /**
     * Serves the order
     * 
     * @param waitingOrder
     */
    public void serveOrder(Order waitingOrder) {
        // Removes order from waiting orders list
        waitingOrders.remove(waitingOrder);
        // Adds order to served orders list
        servedOrders.add(waitingOrder);
        // Sets the status in the order to served
        waitingOrder.serveOrder();
    }
    
    /**
     * Bills the order
     * 
     * @param servedOrder
     */
    public void billOrder(Order servedOrder) {
        // Removes order from served orders list
        servedOrders.remove(servedOrder);
        // Adds order to billed orders list
        billedOrders.add(servedOrder);
        // Sets the status in the order to billed
        servedOrder.billOrder();        
    }

    /**
     * Gets the list of waiting orders
     * 
     * @return waitingOrders
     */
    public ArrayList<Order> getWaitingOrders() {
        return waitingOrders;
    }

    /**
     * Gets the list of Served orders
     * 
     * @return servedOrders
     */
    public ArrayList<Order> getServedOrders() {
        return servedOrders;
    }

    /**
     * Gets the list of billed orders
     * 
     * @return billedOrders
     */
    public ArrayList<Order> getBilledOrders() {
        return billedOrders;
    }
    
    
}
