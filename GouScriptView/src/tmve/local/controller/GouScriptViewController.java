/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author P05144
 */
public class GouScriptViewController implements Initializable {
    
    private StringBuilder searchRncName = new StringBuilder("");
    
    @FXML
    private TextField searchRnc;
    
    @FXML
    void onTextRncField(KeyEvent   event) {
       
            
            searchRncName.append(event.getCharacter());
            System.out.println(searchRncName+" ");
            
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Platform.isSupported(ConditionalFeature.INPUT_METHOD);
        // TODO
        /*TextFields.bindAutoCompletion(searchRnc, t -> {
            return list.stream().filter(elem
                    -> {
                return elem.toLowerCase().startsWith(t.getUserText().toLowerCase());
            }).collect(Collectors.toList());
        });*/
    }    
    
}
