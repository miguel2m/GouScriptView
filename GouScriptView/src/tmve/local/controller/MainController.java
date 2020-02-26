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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tmve.local.main.GouScript;
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
    @FXML
    private Tab auditTab;
    @FXML
     Label labelFooter;
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
    
    final Hyperlink link = new Hyperlink("https://github.com/miguel2m/GouScriptView");

    /**
     * METODO que dirije a la ventana de ayuda
     * @param event 
     */
    @FXML
    void onHelpItem(ActionEvent event) {
        //viewFactory.showHelpWindown();
        FlowPane fp = new FlowPane();
        Label lbl = new Label("Toda la ayuda la encontrarás aquí: ");
        
        fp.getChildren().addAll(lbl, link);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Info");
                alert.initOwner(gxportDbTab.getTabPane().getScene().getWindow());
                alert.setTitle("Info");
                alert.getDialogPane().setContent(fp);
                alert.getDialogPane().setHeaderText("Información");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.showAndWait();
    }
    
    
    /**
     * Metodo para cerrar el programa
     * @param event 
     */
    @FXML
    void onExit(ActionEvent event) {
        
       Stage stage = (Stage)gxportDbTab.getTabPane().getScene().getWindow();
        //event.consume();
        viewFactory.closePrimaryStage(stage);  
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
            link.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    getHostServices().showDocument(link.getText());
                    //getHostServices().showDocument(link.getText());
                }
            });
            //viewFactory.addTab(gxportDbTab, "GxportDBView.fxml");
            
            auditTab.setOnSelectionChanged((event) -> {
                if (auditTab.isSelected()) {

                        viewFactory.addTab(auditTab, "AuditView.fxml");    
                    
                }
            });
            
            gouScriptTab.setOnSelectionChanged((event) -> {
                if (gouScriptTab.isSelected()) {
                    // refreshTabBData();
                    //System.out.println("gouScriptTab ");
                    /*Path file = Paths.get(outputDirectory+File.separator+"ADJNODE.csv");
                    Path file2 = Paths.get(outputDirectory+File.separator+"IPRT.csv");
                    if (Files.isReadable(file) && Files.isReadable(file2)) {
                        Platform.runLater(() -> {*/
                        viewFactory.addTab(gouScriptTab, "GouScriptView.fxml");
                    
                    /*    });
                    }else{
                        Alert alert = new Alert(AlertType.ERROR,
                                "La base no fue cargada completamente, "
                                + "falta la tabla ADJNODE.csv o IPRT.csv ");
                        
                        alert.setTitle("Error en la base de datos");
                        alert.showAndWait();
                       
                        //Optional<ButtonType> result = alert.showAndWait();
                    }*/
                    
                    
                }
            });
        
    }    
    
}
