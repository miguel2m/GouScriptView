/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.controller;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import tmve.local.main.GouScript;
import tmve.local.services.UnGzip;
import tmve.local.view.ViewFactory;

/**
 * Crontolador principal del la vista principal
 * @author P05144
 */
public class MainController extends BaseController implements Initializable {
    private  File inputDirectory;
    private String outputDirectory ="."+File.separator+"database";
    /**
     * Constructor de la clase principal
     * @param gouScript
     * @param viewFactory
     * @param fxmlName 
     */
    public MainController(GouScript gouScript, ViewFactory viewFactory, String fxmlName) {
        super(gouScript, viewFactory, fxmlName);
    } 
    /**
     * Atributo para importar la base de datos (Import GXPORT)
     */
    @FXML
    private Label labelGxportInput;
    /**
     * Atributo para importar la base de datos (Import GXPORT)
     */
    @FXML
    private MenuItem helpItem;
    
    /**
     * Atributo para importar la base de datos (Import GXPORT)
     */
    @FXML
    private Button gxportButton;
    /**
     * Atributo para importar la base de datos (Import GXPORT)
     */
    @FXML
    private TextField textOpenFile;
    /**
     * Atributo para importar la base de datos (Import GXPORT)
     */
    @FXML
    private Button fileOpen;
    
    
    /**
     * METODO que dirije a la ventana de ayuda
     * @param event 
     */
    @FXML
    void onHelpItem(ActionEvent event) {
        viewFactory.showHelpWindown();
    }
    
    /**
     * METODO que se activa cuando el usuario presiona el boton de abrir carpeta
     * para seleccionar el directorio destino de la BASE DE DATOS
     * @param event 
     */
    @FXML
    void onFileOpen(ActionEvent event) {
        DirectoryChooser direcotryChooser = new DirectoryChooser();
        direcotryChooser.setTitle("Import GXPORT Data Base");
        Stage stage = (Stage)labelGxportInput.getScene().getWindow();
        inputDirectory = direcotryChooser.showDialog(stage);
        if(inputDirectory!=null)
            textOpenFile.setText(inputDirectory.getAbsolutePath());
    }
    
    /**
     * METODO que se activa cuando el usuario presiona crear DATA BASE
     * @param event 
     */
     @FXML
    void onGxportDataBase(ActionEvent event) {
        if(!textOpenFile.getText().equals(null)){
            gxportButton.setDisable(true);
            //Is path is readable
            Path file = Paths.get(textOpenFile.getText());
            if(Files.isReadable(file)){
                File directory = new File(outputDirectory);
                if (!directory.exists()){
                    directory.mkdir();
                    File extractDirectory = new File(outputDirectory+File.separator+"extract");
                    if (!extractDirectory.exists())
                        extractDirectory.mkdir();                   
                }               
                UnGzip ungz = new UnGzip(textOpenFile.getText(),outputDirectory+File.separator+"extract");

                        ungz.start();

                        ungz.setOnSucceeded(e -> {

                            System.out.println(textOpenFile.getText() + " DONE");

                            // TODO, . . . 
                            // You can modify any GUI element from here...
                            // ...with the values you got from the service
                        });
                //get all the files from a directory
                /*File[] fList = inputDirectory.listFiles();
                
                for (File f : fList) {
                    String ext = FilenameUtils.getExtension(f.getName());
                    
                    if (ext.equals("gz")) {
                        //UnGzip.gunzipIt(f.getAbsolutePath(),outputDirectory+"/extract");gxportButton

                        UnGzip ungz = new UnGzip(f.getAbsolutePath(),outputDirectory+File.separator+"extract");

                        ungz.start();

                        ungz.setOnSucceeded(e -> {

                            System.out.println(f.getAbsolutePath() + " DONE");

                            // TODO, . . . 
                            // You can modify any GUI element from here...
                            // ...with the values you got from the service
                        });
                                           
                    }
                }*/
            }
        }
         
    }
    
    /**
     * Metodo paa cerrar el programa
     * @param event 
     */
    @FXML
    void onExit(ActionEvent event) {
        Stage stage = (Stage)textOpenFile.getScene().getWindow();
        viewFactory.closeStage(stage);  
       
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
