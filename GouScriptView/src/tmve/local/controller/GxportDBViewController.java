/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import tmve.local.services.GexportParser;
import tmve.local.services.UnGzip;

/**
 * FXML Controller class
 *
 * @author P05144
 */
public class GxportDBViewController implements Initializable {
    private File inputDirectory;
    private File outputDirectory;
    private  UnGzip ungz;
    private  GexportParser gexportParser;
    
    /**
     * Atributo para importar la base de datos (Import GXPORT)
     */
    @FXML
    private Label labelGxportInput;
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
     * Atributo para cancelar la tarea de importacion
     */
    @FXML
    private Button cancelTask;
    /**
     * Atributo para mostrar el progreso de las tareas
     */
    @FXML
    private ProgressIndicator progressGexportDb;
    /**
     * Atributo para mostrar el log de la tarea Importar Data Base
     */
    @FXML
    private Label logGexportIndicator;
    /**
     * Atributo para mostrar el estado para importar la base de datos
     */
    @FXML
    private Label progresTaskIndicator;
    @FXML
    private TextField textOutputFile;
    @FXML
    private Button fileOutput;
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
    void onGxportDataBase(ActionEvent event) throws IOException {
        
        if(!textOpenFile.getText().equals(null) &&
                !textOpenFile.getText().isEmpty()){
            
            if (!textOutputFile.getText().isEmpty()) {

                //Is path is readable
                Path file = Paths.get(textOpenFile.getText());
                if (Files.isReadable(file)) {
                    Platform.runLater(() -> {

                        gxportButton.setVisible(false);
                        cancelTask.setVisible(true);
                        progressGexportDb.setVisible(true);
                        logGexportIndicator.setVisible(true);
                        progresTaskIndicator.setVisible(true);
                    });

                    /*FileUtils.deleteDirectory(new File(MainController.outputDirectory));
                    File directory = new File(MainController.outputDirectory);
                    //if (!directory.exists()){
                    directory.mkdir();*/
                    File extractDirectory = new File(outputDirectory.getAbsolutePath()+ File.separator + "extract");
                    if (!extractDirectory.exists()) {
                        extractDirectory.mkdir();
                    }
                    //}               
                    ungz = new UnGzip(inputDirectory.getAbsolutePath(), outputDirectory.getAbsolutePath() + File.separator + "extract", logGexportIndicator);

                    ungz.start();
                    ungz.setOnRunning(a -> {
                        progresTaskIndicator.setText("Descomprimiendo");
                    });
                    ungz.setOnSucceeded(b -> {
                        //System.out.println(textOpenFile.getText() + " DONE");
                        //gxportButton.setDisable(false);

                        gexportParser = new GexportParser(outputDirectory.getAbsolutePath() + File.separator + "extract", outputDirectory.getAbsolutePath(), logGexportIndicator);
                        gexportParser.start();
                        //progressGexportDb.visibleProperty().bind(gexportParser.runningProperty());
                        gexportParser.setOnRunning(c -> {
                            progresTaskIndicator.setText("Convirtiendo XML a CSV");
                        });
                        gexportParser.setOnSucceeded(d -> {
                            Platform.runLater(() -> {

                                gxportButton.setVisible(true);
                                cancelTask.setVisible(false);
                                //progressGexportDb.setProgress(100);
                                progressGexportDb.setVisible(false);
                                progresTaskIndicator.setVisible(false);
                                //logGexportIndicator.setVisible(false);
                                textOpenFile.clear();
                                textOutputFile.clear();
                            });
                        });
                    });
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Por favor ingrese un direcctorio DESTINO válido ");

                alert.setTitle("Ingresar un directorio DESTINO válido ");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Por favor ingrese un direcctorio GXPORT válido ");

            alert.setTitle("Ingresar un directorio GXPORT válido ");
            alert.showAndWait();
        }
    }

         
    @FXML
    void onFileOutput(ActionEvent event) {
        DirectoryChooser direcotryChooser = new DirectoryChooser();
        direcotryChooser.setTitle("Import GXPORT Data Base");
        Stage stage = (Stage)labelGxportInput.getScene().getWindow();
        outputDirectory = direcotryChooser.showDialog(stage);
        if(outputDirectory!=null)
            textOutputFile.setText(outputDirectory.getAbsolutePath());
    }
    
    
    /**
     * Metodo para cancelar las tareas de importar base de datos
     * @param event 
     */
    @FXML
    void onCancelTask(ActionEvent event) {
        Platform.runLater(() -> {
                                    
                                    gxportButton.setVisible(true);
                                    cancelTask.setVisible(false);
                                    progressGexportDb.setVisible(false);
                                    progresTaskIndicator.setVisible(false);
                                    textOpenFile.clear();
                                    textOutputFile.clear();
                                    //logGexportIndicator.setVisible(false);
                                });
        if(ungz.isRunning())
                ungz.cancel();
                
        if(gexportParser.isRunning())
            gexportParser.cancel();
    }
    GlyphFont glyphFont = GlyphFontRegistry.font("FontAwesome");
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fileOpen.setGraphic(glyphFont.create(FontAwesome.Glyph.FILES_ALT));
        fileOutput.setGraphic(glyphFont.create(FontAwesome.Glyph.FILES_ALT));
        //gxportButton.setGraphic(glyphFont.create(FontAwesome.Glyph.UPLOAD));
        cancelTask.setVisible(false);
        progressGexportDb.setVisible(false);
    }    
    
}
