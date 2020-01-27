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
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import org.apache.commons.collections4.CollectionUtils;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.PrefixSelectionChoiceBox;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import tmve.local.services.AdjnodeCsv;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import static tmve.local.controller.MainController.outputDirectory;
/**
 * FXML Controller class
 *
 * @author P05144
 */
public class GouScriptViewController implements Initializable {
    
    //private StringBuffer rncSearchName = new StringBuffer("");
    private ObservableList<String> rncPossibleSuggestions = FXCollections.observableArrayList();
   // private ObservableList<String> nodebRnc = FXCollections.observableArrayList();
    private AutoCompletionBinding auto;
    @FXML
    private Button cargarNodeB;
    @FXML
    private CheckComboBox<String> nodebList;
   // private PrefixSelectionChoiceBox<String> choice1 = new PrefixSelectionChoiceBox<>();
     @FXML
    private SearchableComboBox<String> searchCombox;
     @FXML
    public static GridPane gouScriptGridPanel;
    /*@FXML
    private PrefixSelectionComboBox<String> prefixSeleccion = new PrefixSelectionComboBox<>();*/
    /*@FXML
    private PrefixSelectionChoiceBox<String> nodebList;*/
    @FXML
    private TextField searchRnc;
    GlyphFont glyphFont = GlyphFontRegistry.font("FontAwesome");
    /*
    @FXML
    void onTextRncFieldKeyTyped(KeyEvent   event) {
        cargarNodeB.setDisable(false);
        //cargarNodeB.setDisable(true);
            /*if(searchRnc.getText().trim().isEmpty()|| searchRnc.getText() == null)
                rncSearchName =  new StringBuffer("");
            //rncSearchName.
           rncSearchName.append(event.getCharacter());
           System.out.println(rncSearchName+" "+searchRnc.getText().trim().length());
           if(searchRnc.getText().length()>1 &&searchRnc.getText().length()<=8 ){
           
               cargarNodeB.setDisable(false);
             //rncPossibleSuggestions.forEach(System.out::println);
                
           }*/
          
    /*}*/
   /*
    @FXML
    void onKeyPressed(KeyEvent event) {
        /*if(event.getCode() == KeyCode.ENTER ){
            rncSearchName = new StringBuffer(searchRnc.getText());
        }*/
        /*if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE){
            if(rncSearchName.length()>0)
               this.rncSearchName.setLength(rncSearchName.length()-1);
        }*/
   /* }*/
    @FXML
    void onCargarNodeb(ActionEvent event) {
        
        //if(!searchRnc.getText().trim().isEmpty()){
        //if(){
            cargarNodeB.setDisable(true);
           
            cargarNodeB.setText("Cargando");
            cargarNodeB.setGraphic(glyphFont.create(FontAwesome.Glyph.SPINNER));
            AdjnodeCsv nodeb = new AdjnodeCsv(searchCombox.getValue(),true);
                       nodeb.start();
                       nodeb.setOnSucceeded((e) -> {
                           if(!CollectionUtils.isEmpty( nodeb.getValue())){
                               Platform.runLater(() -> {
                                   nodebList.getItems().clear();
                                   nodebList.getItems().addAll(nodeb.getValue());
                               });
                               
                           }
                           
                           cargarNodeB.setVisible(false);
                           cargarNodeB.setText("Cargar");
                           cargarNodeB.setGraphic(null);
                           nodebList.setDisable(false);
                       });
        //}
    }
    @FXML
    void onSearchCombox(ActionEvent event) {
        //System.out.println("");
         nodebList.setDisable(true);
        
        cargarNodeB.setVisible(true);
        cargarNodeB.setDisable(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
                searchCombox.setDisable(true);
                cargarNodeB.setVisible(false);
                nodebList.setDisable(true);

                //Platform.isSupported(ConditionalFeature.INPUT_METHOD);
                // TODO
                /*TextFields.bindAutoCompletion(searchRnc, t -> {
                    return list.stream().filter(elem
                            -> {
                        return elem.toLowerCase().startsWith(t.getUserText().toLowerCase());
                    }).collect(Collectors.toList());
                });*/

               AdjnodeCsv adjnodeCsv = new AdjnodeCsv("rnc",false);

                    adjnodeCsv.start();

                    adjnodeCsv.setOnSucceeded(d-> {
                        rncPossibleSuggestions.clear();
                        
                        if(!CollectionUtils.isEmpty( adjnodeCsv.getValue())){
                            /*adjnodeCsv.getValue().forEach((t) -> {

                                rncPossibleSuggestions.add(t);
                            });*/
                            rncPossibleSuggestions.addAll(adjnodeCsv.getValue());
                            //prefixSeleccion.setItems(rncPossibleSuggestions);
                            //prefixSeleccion.setMaxWidth(Double.MAX_VALUE);
                           // prefixSeleccion.setEditable(true);
                           searchCombox.setItems(rncPossibleSuggestions);


                        }
                        System.out.println("VALUE "+adjnodeCsv.getValue());  
                        //auto = TextFields.bindAutoCompletion(searchRnc, rncPossibleSuggestions);
                        searchCombox.setDisable(false);

                    });
            
    }    
    
}
