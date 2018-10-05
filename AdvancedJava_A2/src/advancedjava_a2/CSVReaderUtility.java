/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava_a2;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.FileReader;
import java.util.List;

/**
 *
 * @author Moiz
 */
public class CSVReaderUtility {
    
    public static void readAllDataFromCSV(String fileName) 
    { 
        try { 
  
            // Create an object of filereader class 
            // with CSV file as a parameter. 
            FileReader filereader = new FileReader(fileName); 
  
            // create csvReader object 
            // and skip first Line 
            CSVReader csvReader = new CSVReaderBuilder(filereader) 
                                      .withSkipLines(1) 
                                      .build(); 
            List<String[]> allData = csvReader.readAll(); 
  
            // print Data 
            for (String[] row : allData) { 
                for (String cell : row) { 
                    System.out.print(cell + "\t"); 
                } 
                System.out.println(); 
            } 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    }
}
