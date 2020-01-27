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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.apache.commons.collections4.CollectionUtils;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import tmve.local.services.AdjnodeCsv;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import tmve.local.services.GouScript;
import tmve.local.services.IprtCsv;
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
    private Button crearGouScript;
    @FXML
    private Button cargarSRN;
    @FXML
    private Button cargarSN;
    @FXML
    private Button cargarPort;
    @FXML
    private Button cargarVRF;
    @FXML
    private CheckComboBox<String> nodebList;
   // private PrefixSelectionChoiceBox<String> choice1 = new PrefixSelectionChoiceBox<>();
    @FXML
    private SearchableComboBox<String> searchComboboxRNC;
    @FXML
    private SearchableComboBox<String> searchComboboxSRN;
    @FXML
    private SearchableComboBox<String> searchComboboxSN;
    @FXML
    private SearchableComboBox<String> searchComboboxPORT;
    @FXML
    private SearchableComboBox<String> searchComboboxVRF;
    @FXML
    public static GridPane gouScriptGridPanel;
    @FXML
    private ProgressIndicator gouScriptProgress;
    @FXML
    private Label progressIndicator;
     
    private ValidationSupport validationSupport = new ValidationSupport();;
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
            AdjnodeCsv nodeb = new AdjnodeCsv(searchComboboxRNC.getValue(),true);
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
    void onSearchComboboxRNC(ActionEvent event) {
        
        nodebList.setDisable(true);

        cargarNodeB.setVisible(true);
        cargarNodeB.setDisable(false);

        
        searchComboboxSRN.setDisable(true);
        searchComboboxSN.setDisable(true);
        searchComboboxPORT.setDisable(true);
        searchComboboxVRF.setDisable(true);
        
        cargarSRN.setVisible(true);
        cargarSN.setVisible(true);
        cargarPort.setVisible(true);
        cargarVRF.setVisible(true);

    }
    
    @FXML
    void onCrearGouScript (ActionEvent event) {
        //validationSupport.setErrorDecorationEnabled(true);
        //validationSupport.setErrorDecorationEnabled(true);
        //validationSupport.redecorate();
        
        
        crearGouScript.setVisible(false);
        gouScriptProgress.setVisible(true);
        //System.out.println("Se creó el GOUSCRIPT");
        nodebList.getCheckModel().getCheckedItems().forEach(System.out::println);
        /*nodebList.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
         public void onChanged(ListChangeListener.Change<? extends String> c) {

             System.out.println("");nodebList.getCheckModel().getSelectedItems();
         }
     });*/
       /*if(validationSupport.isInvalid())
            System.out.println("Errores ");*/
        System.out.println(""+searchComboboxRNC.selectionModelProperty().getValue().getSelectedItem());
        if (searchComboboxRNC.selectionModelProperty().getValue().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    " Seleccione la RNC ");

            alert.setTitle("Seleccione la RNC");
            alert.showAndWait();
            
        } else {
            if (nodebList.getCheckModel().getCheckedItems().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Seleccione la lista de NodeB ");

                alert.setTitle("Seleccione la lista de NODB");
                alert.showAndWait();
            } else {
                if (searchComboboxSRN.selectionModelProperty().getValue().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Seleccione el SRN ");

                    alert.setTitle("Seleccione el SRN ");
                    alert.showAndWait();
                } else {
                    if (searchComboboxSN.selectionModelProperty().getValue().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR,
                                "Seleccione el SN ");

                        alert.setTitle("Seleccione el SN ");
                        alert.showAndWait();
                    } else {
                        if (searchComboboxPORT.selectionModelProperty().getValue().isEmpty()) {
                            Alert alert = new Alert(Alert.AlertType.ERROR,
                                    "Seleccione el Puerto ");

                            alert.setTitle("Seleccione el Puerto ");
                            alert.showAndWait();
                        } else {
                            if (searchComboboxVRF.selectionModelProperty().getValue().isEmpty()) {
                                Alert alert = new Alert(Alert.AlertType.ERROR,
                                        "Seleccione la VRF IP ");

                                alert.setTitle("Seleccione la VRF IP  ");
                                alert.showAndWait();
                            } else {
                                GouScript gouScript = new GouScript(searchComboboxRNC.getValue(),
                                        Short.parseShort(searchComboboxSRN.getValue()),
                                        Short.parseShort(searchComboboxSN.getValue()),
                                        Short.parseShort(searchComboboxPORT.getValue()),
                                        searchComboboxVRF.getValue(),
                                        nodebList.getCheckModel().getCheckedItems(),
                                        progressIndicator);

                                gouScript.start();
                                gouScript.setOnSucceeded((e) -> {
                                    gouScriptProgress.setProgress(gouScript.getValue());
                                    System.out.println("Termino con " + gouScript.getValue());
                                    //crearGouScript.setVisible(true);

                                });
                            }
                        }
                    }
                }
            }

        }
        gouScriptProgress.setVisible(false);
        crearGouScript.setVisible(true);
       
       
       //validationSupport.setErrorDecorationEnabled(true);
       
       
       
    }
    @FXML
    void onCargarSRN (ActionEvent event) {
        cargarSRN.setDisable(true);
        cargarSRN.setText("Cargando");
        cargarSRN.setGraphic(glyphFont.create(FontAwesome.Glyph.SPINNER));
        IprtCsv iprtCsvSRN = new IprtCsv(searchComboboxRNC.getValue(), true, false, false, false);
            iprtCsvSRN.start();
            iprtCsvSRN.setOnSucceeded((a) -> {

                if (!CollectionUtils.isEmpty(iprtCsvSRN.getValue())) {
                    Platform.runLater(() -> {
                        searchComboboxSRN.setDisable(false);
                        if (!searchComboboxSRN.getItems().isEmpty()) {
                            searchComboboxSRN.getItems().clear();
                        }
                        searchComboboxSRN.getItems().addAll(iprtCsvSRN.getValue());
                    });
                }
                //validationSupport.registerValidator(searchComboboxSRN, Validator.createEmptyValidator("Seleccione SRN"));
                cargarSRN.setVisible(false);
                cargarSRN.setDisable(false);
                cargarSRN.setText("Cargar");
                cargarSRN.setGraphic(null);
                searchComboboxSRN.setDisable(false);
            });
        
    }
    @FXML
    void onCargarSN (ActionEvent event) {
        cargarSN.setDisable(true);
        cargarSN.setText("Cargando");
        cargarSN.setGraphic(glyphFont.create(FontAwesome.Glyph.SPINNER));
        IprtCsv iprtCsvSN = new IprtCsv(searchComboboxRNC.getValue(), false, true, false, false);
            iprtCsvSN.start();
            iprtCsvSN.setOnSucceeded((a) -> {

                if (!CollectionUtils.isEmpty(iprtCsvSN.getValue())) {
                    Platform.runLater(() -> {
                        searchComboboxSN.setDisable(false);
                        if (!searchComboboxSN.getItems().isEmpty()) {
                            searchComboboxSN.getItems().clear();
                        }
                        searchComboboxSN.getItems().addAll(iprtCsvSN.getValue());
                    });
                }
                cargarSN.setVisible(false);
                cargarSN.setDisable(false);
                cargarSN.setText("Cargar");
                cargarSN.setGraphic(null);
                searchComboboxSN.setDisable(false);
                //validationSupport.registerValidator(searchComboboxSN, Validator.createEmptyValidator("Seleccione SN"));
            });
    }
    @FXML
    void onCargarPort (ActionEvent event) {
        cargarPort.setDisable(true);
        cargarPort.setText("Cargando");
        cargarPort.setGraphic(glyphFont.create(FontAwesome.Glyph.SPINNER));
        IprtCsv iprtCsvPORT = new IprtCsv(searchComboboxRNC.getValue(), false, false, true, false);
            iprtCsvPORT.start();
            iprtCsvPORT.setOnSucceeded((a) -> {

                if (!CollectionUtils.isEmpty(iprtCsvPORT.getValue())) {
                    Platform.runLater(() -> {
                        searchComboboxPORT.setDisable(false);
                        if (!searchComboboxPORT.getItems().isEmpty()) {
                            searchComboboxPORT.getItems().clear();
                        }
                        searchComboboxPORT.getItems().addAll(iprtCsvPORT.getValue());
                    });
                }
                cargarPort.setVisible(false);
                cargarPort.setDisable(false);
                cargarPort.setText("Cargar");
                cargarPort.setGraphic(null);
                searchComboboxPORT.setDisable(false);
                //validationSupport.registerValidator(searchComboboxPORT, Validator.createEmptyValidator("Seleccione Puerto"));
            });
    }
    @FXML
    void onCargarVRF (ActionEvent event) {
        cargarVRF.setDisable(true);
        cargarVRF.setText("Cargando");
        cargarVRF.setGraphic(glyphFont.create(FontAwesome.Glyph.SPINNER));
        IprtCsv iprtCsvVRF = new IprtCsv(searchComboboxRNC.getValue(), false, false, false, true);
            iprtCsvVRF.start();
            iprtCsvVRF.setOnSucceeded((a) -> {

                if (!CollectionUtils.isEmpty(iprtCsvVRF.getValue())) {
                    Platform.runLater(() -> {
                        searchComboboxVRF.setDisable(false);
                        if (!searchComboboxVRF.getItems().isEmpty()) {
                            searchComboboxVRF.getItems().clear();
                        }
                        searchComboboxVRF.getItems().addAll(iprtCsvVRF.getValue());
                    });
                }
                cargarVRF.setVisible(false);
                cargarVRF.setDisable(false);
                cargarVRF.setText("Cargar");
                cargarVRF.setGraphic(null);
                
                searchComboboxVRF.setDisable(false);

                //validationSupport.registerValidator(searchComboboxVRF, Validator.createEmptyValidator("Seleccione VRF IP"));
            });
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                //validationSupport = new ValidationSupport();
                //validationSupport.setErrorDecorationEnabled(false);
                //validationSupport.setErrorDecorationEnabled(true);
                
                //validationSupport.registerValidator(nodebList, Validator.createEmptyValidator("Seleccione la lista de NodeB"));
                
                

               
                searchComboboxRNC.setDisable(true);
                cargarNodeB.setVisible(false);
                nodebList.setDisable(true);
                
                searchComboboxSRN.setDisable(true);
                searchComboboxSN.setDisable(true);
                searchComboboxPORT.setDisable(true);
                searchComboboxVRF.setDisable(true);
                
                cargarSRN.setVisible(false);
                cargarSN.setVisible(false);
                cargarPort.setVisible(false);
                cargarVRF.setVisible(false);
                
                gouScriptProgress.setVisible(false);
                progressIndicator.setVisible(false);
                //crearGouScript.setDisable(true);
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
                            Platform.runLater(() -> {
                            rncPossibleSuggestions.addAll(adjnodeCsv.getValue());
                            //prefixSeleccion.setItems(rncPossibleSuggestions);
                            //prefixSeleccion.setMaxWidth(Double.MAX_VALUE);
                           // prefixSeleccion.setEditable(true);
                           searchComboboxRNC.setItems(rncPossibleSuggestions);
                            });


                        }
                        System.out.println("VALUE "+adjnodeCsv.getValue());  
                        //auto = TextFields.bindAutoCompletion(searchRnc, rncPossibleSuggestions);
                        searchComboboxRNC.setDisable(false);
                        //validationSupport.registerValidator(searchComboboxRNC, Validator.createEmptyValidator("Seleccione una RNC"));
                    });
                   
            
    }    
    
}
