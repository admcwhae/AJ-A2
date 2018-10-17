/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava_a2;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Moiz
 */
public class CSVReaderUtility {
    
    /**
     * Read all the data from a CSV file and returns it as a list
     * 
     * @param File Name
     * 
     * @return List containing all data
     */
    public static List<String[]> readAllDataFromCSV(String fileName) 
    { 
        List<String[]> allData = new ArrayList<String[]>();
        
        try { 
  
            // Create an object of filereader class 
            // with CSV file as a parameter. 
            FileReader filereader = new FileReader(fileName); 
  
            // create csvReader object 
            // and skip first Line 
            CSVReader csvReader = new CSVReaderBuilder(filereader) 
                                      .withSkipLines(1) 
                                      .build(); 
            allData =  csvReader.readAll(); 
   
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
        
        return allData;
    }
}
