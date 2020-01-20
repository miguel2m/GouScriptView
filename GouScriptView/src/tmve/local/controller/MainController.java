/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tmve.local.main.GouScript;
import tmve.local.view.ViewFactory;

/**
 *
 * @author P05144
 */
public class MainController extends BaseController implements Initializable {


    public MainController(GouScript gouScript, ViewFactory viewFactory, String fxmlName) {
        super(gouScript, viewFactory, fxmlName);
    } 
    @FXML
    private Label labelGxportInput;
    
    @FXML
    private MenuItem helpItem;

    @FXML
    void onHelpItem(ActionEvent event) {
        viewFactory.showHelpWindown();
    }
    
    @FXML
    private Button gxportButton;

    @FXML
    void gxportDataBase(ActionEvent event) {

    }
    
    @FXML
    private TextField textOpenFile;
    @FXML
    private Button fileOpen;
    
    @FXML
    void onFileOpen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("ENTER GEXPORT");
        Stage stage = (Stage)labelGxportInput.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        
        textOpenFile.setText(file.getAbsolutePath());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
