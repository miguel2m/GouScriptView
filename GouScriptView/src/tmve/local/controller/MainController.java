/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import tmve.local.main.GouScript;
import tmve.local.services.GexportParser;
import tmve.local.services.UnGzip;
import tmve.local.view.ViewFactory;

/**
 * Crontolador principal del la vista principal
 * @author P05144
 */
public class MainController extends BaseController implements Initializable {

    public static String outputDirectory ="."+File.separator+"database";
    @FXML
    private Tab gxportDbTab;
    @FXML
    private Tab gouScriptTab;

    /**
     * Constructor de la clase principal
     * @param gouScript
     * @param viewFactory
     * @param fxmlName 
     */
    public MainController(GouScript gouScript, ViewFactory viewFactory, String fxmlName) {
        super(gouScript, viewFactory, fxmlName);
        
    }
    
    private static boolean isDirEmpty(final Path directory) throws IOException {
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
            return !dirStream.iterator().hasNext();
        }
    }
    
    /**
     * Atributo para importar la base de datos (Import GXPORT)
     */
    @FXML
    private MenuItem helpItem;
    

    /**
     * METODO que dirije a la ventana de ayuda
     * @param event 
     */
    @FXML
    void onHelpItem(ActionEvent event) {
        viewFactory.showHelpWindown();
    }
    
    
    /**
     * Metodo para cerrar el programa
     * @param event 
     */
    @FXML
    void onExit(ActionEvent event) {
        Stage stage = (Stage)gxportDbTab.getTabPane().getScene().getWindow();
        viewFactory.closeStage(stage);  
       
        
    }
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
            //viewFactory.addTab(gxportDbTab, "GxportDBView.fxml");
            
            
            gouScriptTab.setOnSelectionChanged((event) -> {
                if (gouScriptTab.isSelected()) {
                    // refreshTabBData();
                    //System.out.println("gouScriptTab ");
                    Path file = Paths.get(outputDirectory+File.separator+"ADJNODE.csv");
                    if (Files.isReadable(file)) {
                        Platform.runLater(() -> {
                        viewFactory.addTab(gouScriptTab, "GouScriptView.fxml");
                    
                        });
                    }else{
                        Alert alert = new Alert(AlertType.ERROR,
                                "La base no fue cargada completamente, "
                                + "falta la tabla ADJNODE.csv ");
                        
                        alert.setTitle("Error en la base de datos");
                        alert.showAndWait();
                       
                        //Optional<ButtonType> result = alert.showAndWait();
                    }
                    
                }
            });
        
    }    
    
}
