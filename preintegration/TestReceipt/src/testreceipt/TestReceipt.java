/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testreceipt;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Formatter;
import javafx.util.converter.LocalDateTimeStringConverter;

/**
 *
 * @author Alex McWhae
 */
public class TestReceipt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String path = "receipt.txt";

        try {
            FileWriter write = new FileWriter(path);
            PrintWriter print = new PrintWriter(write);

            Formatter f = new Formatter(print);
                
            String foodItem = "Coffee made up with regular fat cows milk BAZZING" ;
            String beverageItem = "Lime cordial";
            int[] prices = new int[3];
            prices[0] = 4;
            prices[1] = 5;
            prices[2] = prices[0] + prices[1];
            
            float foodPrice = (float) prices[0];
            float totalPrice = (float) prices[2];
            float beveragePrice = (float) prices[1];
            int orderId = 1;
            int tableId = 2;
            String name = "Alexander";
            Date date = new Date();
            
            f.format("%30s\r\n", "CUSTOMER COPY");
            f.format("%29tr\r\n", date);
            f.format("%27tD\r\n", date);
            f.format("\r\n");
            f.format("%s %d\r\n", "Order", orderId );
            f.format("%s %d\r\n", "Table", tableId );
            f.format("%-5s %.10s\r\n", "Name", name );
            f.format("%-30s %15s\r\n", "---------", "-----");
            f.format("%-30s %15s\r\n", "Item", "Price");
            f.format("%-30s %15s\r\n", "---------", "-----");
            f.format("%-30.35s %10.2f\r\n", foodItem, foodPrice);
            f.format("%-35.35s %10.2f\r\n", beverageItem, beveragePrice);
            f.format("%-30s %15s\r\n", "---------", "-----");
            
            f.format("%-30.20s %15.2f\r\n", "GST",  totalPrice * 0.1);
            f.format("%-30.20s %15.2f\r\n", "Total",  totalPrice);
            f.format("\r\n");
            f.format("%35s\r\n","Thanks for eating with us.");
            
            print.close();
            
            File file = new File(path);
            
            // if supported, java will open the file in a text editor to allow for printing of the receipt
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().edit(file);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
        }
        
        
        

    }
    
}
