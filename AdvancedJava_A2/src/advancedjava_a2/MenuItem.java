/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava_a2;

/**
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
    
    public String getMenuDescription() {
        return menuDescription;
    }

    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getDietaryFibre() {
        return dietaryFibre;
    }

    public void setDietaryFibre(double dietaryFibre) {
        this.dietaryFibre = dietaryFibre;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }
    
    @Override
    public String toString()
    {
        return this.itemName;
    }
     
}
