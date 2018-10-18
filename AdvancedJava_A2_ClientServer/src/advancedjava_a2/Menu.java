/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava_a2;

import java.util.ArrayList;
import java.util.List;

/**
 * The Restaurant Menu
 * 
 * @author Moiz
 */
public class Menu {
    
    private ArrayList<MenuItem> menuItemsList;

    /**
     * Menu Constructor
     */
    public Menu() {
        menuItemsList = new ArrayList<MenuItem>();
        populateMenuItemsList();
    }

    /**
     * Gets the Menu Items List
     * 
     * @return List of Menu Items
     */
    public ArrayList<MenuItem> getMenuItemsList() {
        return menuItemsList;
    }

    /**
     * Sets the Menu Items List
     * 
     * @param menuItemsList
     */
    public void setMenuItemsList(ArrayList<MenuItem> menuItemsList) {
        this.menuItemsList = menuItemsList;
    }
    
    /**
     * Populates the MenuItemsList from CSV File
     */
    public void populateMenuItemsList()
    {                
        List<String[]> allItems = CSVReaderUtility.readAllDataFromCSV("Assignment2Data.csv");
        
        for (String[] row : allItems) 
        { 
            MenuItem item = new MenuItem();
            
            item.setMenuDescription(row[0]);
            item.setMealType(row[1]);
            item.setItemName(row[2]);
            item.setPrice(Double.parseDouble(row[3]));
            item.setEnergy(Double.parseDouble(row[4]));
            item.setProtein(Double.parseDouble(row[5]));
            item.setCarbohydrates(Double.parseDouble(row[6]));
            item.setFat(Double.parseDouble(row[7]));
            item.setDietaryFibre(Double.parseDouble(row[8]));
            item.setMenuItemId(Integer.parseInt(row[9]));
            
            menuItemsList.add(item);
        }
    }
    
    /**
     * Filters the MenuItems by Meal Type and Description
     * 
     * @param mealType
     * @param menuDescription
     * 
     * @return List of filtered menu items
     */
    public ArrayList<MenuItem> getMenuItemsByMealType(String mealType, String menuDescription)
    {
        ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
        
        for(MenuItem item : menuItemsList)
        {
            if(item.getMealType().equalsIgnoreCase(mealType) && item.getMenuDescription().equalsIgnoreCase(menuDescription))
            {
                menuItems.add(item);
            }
        }
        return menuItems;
    }
}
