/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import tmve.local.services.AdjnodeCsv;

/**
 * FXML Controller class
 *
 * @author P05144
 */
public class GouScriptViewController implements Initializable {
    
    private StringBuffer rncSearchName = new StringBuffer("");
    private ObservableList<String> rncPossibleSuggestions = FXCollections.observableArrayList();
    private ObservableList<String> nodebRnc = FXCollections.observableArrayList();
    private AutoCompletionBinding auto;
    @FXML
    private Button cargarNodeB;
    @FXML
    private CheckComboBox<String> nodebList;
    @FXML
    private TextField searchRnc;
    
    @FXML
    void onTextRncFieldKeyTyped(KeyEvent   event) {
        cargarNodeB.setDisable(true);
            if(searchRnc.getText().trim().isEmpty()|| searchRnc.getText() == null)
                rncSearchName =  new StringBuffer("");
            //rncSearchName.
           rncSearchName.append(event.getCharacter());
           System.out.println(rncSearchName+" "+searchRnc.getText().trim().length());
           if(searchRnc.getText().length()>1 &&searchRnc.getText().length()<=4 ){
           
            AdjnodeCsv adjnodeCsv = new AdjnodeCsv(rncSearchName.toString(),false);
            
            adjnodeCsv.start();
            
            adjnodeCsv.setOnSucceeded(d-> {
                rncPossibleSuggestions.clear();
               
                if(!CollectionUtils.isEmpty( adjnodeCsv.getValue())){
                    adjnodeCsv.getValue().forEach((t) -> {
                        
                        rncPossibleSuggestions.add(t);
                    });
                }
                System.out.println("VALUE "+adjnodeCsv.getValue());  
                auto = TextFields.bindAutoCompletion(searchRnc, rncPossibleSuggestions);
                
                /*AdjnodeCsv nodeb = new AdjnodeCsv(searchRnc.getText(),true);
                      nodeb.start();
                      nodeb.setOnSucceeded((e) -> {
                          if(!CollectionUtils.isEmpty( nodeb.getValue())){
                              Platform.runLater(() -> {
                                  nodebList.getItems().clear();
                                  nodebList.getItems().addAll(nodeb.getValue());
                              });

                          }
                      });*/
               
            });
             //rncPossibleSuggestions.forEach(System.out::println);
                cargarNodeB.setDisable(false);
           }
          
    }
   
    @FXML
    void onKeyPressed(KeyEvent event) {
        /*if(event.getCode() == KeyCode.ENTER ){
            rncSearchName = new StringBuffer(searchRnc.getText());
        }*/
        /*if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE){
            if(rncSearchName.length()>0)
               this.rncSearchName.setLength(rncSearchName.length()-1);
        }*/
    }
    @FXML
    void onCargarNodeb(ActionEvent event) {
        if(!searchRnc.getText().trim().isEmpty()){
            
            AdjnodeCsv nodeb = new AdjnodeCsv(searchRnc.getText(),true);
                       nodeb.start();
                       nodeb.setOnSucceeded((e) -> {
                           if(!CollectionUtils.isEmpty( nodeb.getValue())){
                               Platform.runLater(() -> {
                                   nodebList.getItems().clear();
                                   nodebList.getItems().addAll(nodeb.getValue());
                               });
                               
                           }
                           cargarNodeB.setDisable(true);
                       });
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarNodeB.setDisable(true);
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
