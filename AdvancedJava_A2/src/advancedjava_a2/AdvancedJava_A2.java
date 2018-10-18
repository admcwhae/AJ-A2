/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava_a2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author AmcwhaeLaptop
 */
public class AdvancedJava_A2 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest((WindowEvent event) -> {
            if (AlertUtility.showConfirmation("Are you sure you want to exit the program?"))
                System.exit(0);
        });
        
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("GeneratePinFXML.fxml"));
//            Parent mainViewParent = loader.load();
//            Scene mainViewScene = new Scene(mainViewParent);
//
//            FXMLDocumentController controller = loader.getController();
//            controller.initData(studentIDTextField.getText());
//
//            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//
//            window.setScene(mainViewScene);
//            window.show();
//        } catch (IOException ex) {
//            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
                
    }
    
}
