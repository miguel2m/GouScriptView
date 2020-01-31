/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tmve.local.controller.BaseController;
import tmve.local.controller.GouScriptViewController;
import tmve.local.controller.GxportDBViewController;
import tmve.local.controller.HelpController;
import tmve.local.controller.MainController;
import tmve.local.main.GouScript;
import tmve.local.services.GexportParser;

/**
 *
 * @author P05144
 */
public class ViewFactory {
    private GouScript gouScript;

    public ViewFactory(GouScript gouScript) {
        this.gouScript = gouScript;
    }
    
   
    
    
    public void showMainWindown(){
        //System.out.println("MAIN WINDOWNS");
        MainController controller = new MainController(gouScript, this, "MainView.fxml");
        initializateMainStage(controller);
        
    }
    
    public void showHelpWindown(){
        System.out.println("HELP WINDOWNS");
        BaseController controller = new HelpController(gouScript, this, "HelpView.fxml");
        initializateStage(controller);
    }
    
    private void initializateStage(BaseController controller ){
        FXMLLoader fxmll = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
        fxmll.setController(controller);
        Parent parent = null;
        try{
            parent = fxmll.load();
        }catch(IOException io){
            io.printStackTrace();
        } 
        
        Scene scene = new Scene(parent);
        Stage  stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Avila RAN v2.0");
        stage.getIcons().add(new Image(ViewFactory.class.getResourceAsStream("assets/tower.png")));
        stage.show();
        
    }
    
    private void initializateMainStage(BaseController controller ){
        FXMLLoader fxmll = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
        fxmll.setController(controller);
        Parent parent = null;
        try{
            parent = fxmll.load();
        }catch(IOException io){
            io.printStackTrace();
        } 
        
        Scene scene = new Scene(parent);
        Stage  stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Avila RAN v2.0");
        stage.getIcons().add(new Image(ViewFactory.class.getResourceAsStream("assets/tower.png")));
        stage.show();
        stage.setOnCloseRequest((event) -> {
            event.consume();
            closePrimaryStage(stage);
        });
    }
    
    
    
    public void addTab(Tab tab, String view){
        FXMLLoader loader = new FXMLLoader();
        try {
            AnchorPane anch1 = loader.load(getClass().getResource(view));
            tab.setContent(anch1);
        } catch (IOException ex) {
            Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closePrimaryStage(Stage stageToClose) {
        //Si no hay ningun proceso corriendo se puede cerrar la ventana
        if ((GouScriptViewController.gouScriptTask == null)
                && (GxportDBViewController.ungz == null)
                && (GxportDBViewController.gexportParser == null)) {
            stageToClose.close();
        }
        //Si gouScriptTask esta corriendo se pregunta si se desea cerrar
        if (GouScriptViewController.gouScriptTask != null) {
            if (GouScriptViewController.gouScriptTask.isRunning()) {
                String msg = " El proceso GOUSCRIPT se está ejecutando...";
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.initStyle(StageStyle.DECORATED);
                alerta.initModality(Modality.APPLICATION_MODAL);
                alerta.initOwner(stageToClose);
                alerta.getDialogPane().setContentText(msg);
                alerta.getDialogPane().setHeaderText("¿Desea salir?");
                alerta.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> {
                            if (GxportDBViewController.ungz != null) {
                                if (GxportDBViewController.ungz.isRunning()) {
                                    GxportDBViewController.ungz.cancel();
                                }
                            }
                            if (GxportDBViewController.gexportParser != null) {
                                if (GxportDBViewController.gexportParser.isRunning()) {
                                    GxportDBViewController.gexportParser.cancel();
                                }
                            }
                            GouScriptViewController.gouScriptTask.cancel();
                            stageToClose.close();
                        });

            }
        }
        //Si ungz esta corriendo se pregunta si se desea cerrar
        if (GxportDBViewController.ungz != null) {
            if (GxportDBViewController.ungz.isRunning()) {
                String msg = " El proceso para EXTRAER los archivos XML se está ejecutando...";
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.initStyle(StageStyle.DECORATED);
                alerta.initModality(Modality.APPLICATION_MODAL);
                alerta.initOwner(stageToClose);
                alerta.getDialogPane().setContentText(msg);
                alerta.getDialogPane().setHeaderText("¿Desea salir?");
                alerta.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> {
                            if (GouScriptViewController.gouScriptTask != null) {
                                if (GouScriptViewController.gouScriptTask.isRunning()) {
                                    GouScriptViewController.gouScriptTask.cancel();
                                }
                            }
                            GxportDBViewController.ungz.cancel();
                            stageToClose.close();
                        });

            }
        }
        //Si gexportParser esta corriendo se pregunta si se desea cerrar
        if (GxportDBViewController.gexportParser != null) {
            if (GxportDBViewController.gexportParser.isRunning()) {
                String msg = " El proceso para convertir XML -> CSV se está ejecutando...";
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.initStyle(StageStyle.DECORATED);
                alerta.initModality(Modality.APPLICATION_MODAL);
                alerta.initOwner(stageToClose);
                alerta.getDialogPane().setContentText(msg);
                alerta.getDialogPane().setHeaderText("¿Desea salir?");
                alerta.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> {
                            if (GouScriptViewController.gouScriptTask != null) {
                                if (GouScriptViewController.gouScriptTask.isRunning()) {
                                    GouScriptViewController.gouScriptTask.cancel();
                                }
                            }
                            GxportDBViewController.gexportParser.cancel();
                            stageToClose.close();
                        });

            }
        }
        
    }
    
    public void closeStage(Stage stageToClose){
        stageToClose.close();
    }
    
}
