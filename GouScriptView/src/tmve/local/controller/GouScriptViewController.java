/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.controlsfx.control.textfield.TextFields;
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
           if(rncSearchName.length()==3){
            
            AdjnodeCsv adjnodeCsv = new AdjnodeCsv(rncSearchName.toString(),false);
            
            adjnodeCsv.start();
            
            adjnodeCsv.setOnSucceeded(d-> {
                rncPossibleSuggestions.clear();
               
                if(!CollectionUtils.isEmpty( adjnodeCsv.getValue())){
                    adjnodeCsv.getValue().forEach((t) -> {
                        
                        rncPossibleSuggestions.add(t);
                    });
                }
               // System.out.println("VALUE "+adjnodeCsv.getValue());  
               TextFields.bindAutoCompletion(searchRnc,rncPossibleSuggestions);
            });
            
           }
           //rncPossibleSuggestions.forEach(System.out::println);
           
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
