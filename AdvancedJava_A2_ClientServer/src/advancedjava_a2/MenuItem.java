/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava_a2;

/**
 * A Menu Item in the Menu
 * 
 * @author Moiz
 */
public class MenuItem {
    
    private String menuDescription;
    private String mealType;
    private String itemName;
    private double price;
    private double energy;
    private double protein;
    private double carbohydrates;
    private double fat;
    private double dietaryFibre;
    private int menuItemId;

    /**
     * Default Constructor
     */
    public MenuItem() {
    }

    /**
     * MenuItem Constructor
     * 
     * @param menuDescription
     * @param mealType
     * @param itemName
     * @param price
     * @param energy
     * @param protein
     * @param carbohydrates
     * @param fat
     * @param dietaryFibre
     * @param menuItemId 
     */
    public MenuItem(String menuDescription, String mealType, String itemName, double price, double energy, double protein, double carbohydrates, double fat, double dietaryFibre, int menuItemId) {
        this.menuDescription = menuDescription;
        this.mealType = mealType;
        this.itemName = itemName;
        this.price = price;
        this.energy = energy;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.dietaryFibre = dietaryFibre;
        this.menuItemId = menuItemId;
    }   
    
    /**
     *
     * @return
     */
    public String getMenuDescription() {
        return menuDescription;
    }

    /**
     *
     * @param menuDescription
     */
    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }

    /**
     *
     * @return
     */
    public String getMealType() {
        return mealType;
    }

    /**
     *
     * @param mealType
     */
    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    /**
     * Gets the Item Name
     * 
     * @return Item Name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the Item Name
     * 
     * @param itemName
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Gets the price
     * 
     * @return
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price
     * 
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the energy value
     * 
     * @return energy
     */
    public double getEnergy() {
        return energy;
    }

    /**
     * Sets the energy value
     * 
     * @param energy
     */
    public void setEnergy(double energy) {
        this.energy = energy;
    }

    /**
     * Gets the protein value
     * 
     * @return protein
     */
    public double getProtein() {
        return protein;
    }

    /**
     * Sets the protein value
     * 
     * @param protein
     */
    public void setProtein(double protein) {
        this.protein = protein;
    }

    /**
     * Gets the carbohydrates value
     * 
     * @return carbohydrates
     */
    public double getCarbohydrates() {
        return carbohydrates;
    }

    /**
     * Sets the carbohydrates value
     * 
     * @param carbohydrates
     */
    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    /**
     * Gets the Fat value
     * 
     * @return fat
     */
    public double getFat() {
        return fat;
    }

    /**
     * Sets the Fat value
     * 
     * @param fat
     */
    public void setFat(double fat) {
        this.fat = fat;
    }

    /**
     * Gets the Dietary Fibre value
     * 
     * @return dietaryFibre
     */
    public double getDietaryFibre() {
        return dietaryFibre;
    }

    /**
     * Sets the Dietary Fibre value
     * 
     * @param dietaryFibre
     */
    public void setDietaryFibre(double dietaryFibre) {
        this.dietaryFibre = dietaryFibre;
    }

    /**
     * Gets the Menu Item ID
     * 
     * @return menuItemId
     */
    public int getMenuItemId() {
        return menuItemId;
    }

    /**
     * Sets the Menu Item ID
     * 
     * @param menuItemId
     */
    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }
    
    /**
     * Displays the name of the menu item in the GUI
     * 
     * @return String Menu item name
     */
    @Override
    public String toString()
    {
        return this.itemName;
    }
     
}
