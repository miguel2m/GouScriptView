/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.controller;


import java.net.URL;
import java.util.ArrayList;
import static java.util.Comparator.comparing;
import java.util.ResourceBundle;
import java.util.TreeSet;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import org.controlsfx.control.textfield.TextFields;
import tmve.local.model.AdjNode;
import tmve.local.services.AdjnodeCsv;

/**
 * FXML Controller class
 *
 * @author P05144
 */
public class GouScriptViewController implements Initializable {
    
    private StringBuilder rncSearchName = new StringBuilder("");
    private ObservableList<String> rncPossibleSuggestions = FXCollections.observableArrayList();
    
    @FXML
    private TextField searchRnc;
    
    @FXML
    void onTextRncFieldKeyTyped(KeyEvent   event) {
            if(searchRnc.getText().trim().isEmpty()|| searchRnc.getText() == null)
                rncSearchName =  new StringBuilder("");
            
            rncSearchName.append(event.getCharacter());
           System.out.println(""+rncSearchName);
           if(rncSearchName.length()> 2){
            
            AdjnodeCsv adjnodeCsv = new AdjnodeCsv(rncSearchName.toString());
            
            adjnodeCsv.start();
            
            adjnodeCsv.setOnSucceeded(d-> {
              
                /*adjnodeCsv.getValue().stream()
                                                .collect(
                                                     collectingAndThen(
                                                             toCollection(() -> 
                                                                     new TreeSet<>(comparing(AdjNode::getFilename)))
                                                             ,ArrayList::new)
                                                );
                adjnodeCsv.getValue().forEach(System.out::println);*/
                System.out.println("VALUE "+adjnodeCsv.getValue());       
            });
            //TextFields.bindAutoCompletion(searchRnc,"test","temp","tempurature","table","tablet");
           }
    }
   
    @FXML
    void onKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE){
            if(!searchRnc.getText().trim().isEmpty())
               rncSearchName = new StringBuilder(searchRnc.getText());
            
        }
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
