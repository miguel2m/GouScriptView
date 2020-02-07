/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.main;

import javafx.application.Application;
import javafx.stage.Stage;
import tmve.local.view.ViewFactory;

/**
 *
 * @author P05144
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        /*Parent root = FXMLLoader.load(getClass().getResource("FXMLBase.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();*/
        ViewFactory viewFactory = new ViewFactory(new GouScript());
        viewFactory.showMainWindown(getHostServices());
        
         
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
