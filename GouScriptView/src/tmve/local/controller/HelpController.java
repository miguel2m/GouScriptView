/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import tmve.local.main.GouScript;
import tmve.local.view.ViewFactory;


/**
 *
 * @author P05144
 */
public class HelpController extends BaseController implements Initializable{
    
    public HelpController(GouScript gouScript, ViewFactory viewFactory, String fxmlName) {
        super(gouScript, viewFactory, fxmlName);
    }
    
    
    @FXML
    private Button closeHelpWidown;
    @FXML
    private Label about;

    @FXML
    void closeHelpWidown(ActionEvent event) {
       Stage stage = (Stage)about.getScene().getWindow();
       viewFactory.closeStage(stage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        about.setText("Miguelangel Medina - Derechos reservados Telefonica Venezuela (c)");
    }
    
}
