/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.input.InputMethodEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.collections4.CollectionUtils;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import tmve.local.services.Auditoria;

/**
 * FXML Controller class
 *
 * @author Miguelangel
 */
public class AuditViewController implements Initializable {

    private File inputFile;
    private File outputAudit;
    private ObservableList<String> paramPossibleSuggestions = FXCollections.observableArrayList();
    public static Auditoria _crearAuditoria;
    @FXML
    private Label labelAuditGEXPORTInput;

    @FXML
    private Button auditButton;

    @FXML
    private ProgressIndicator progressAudit;

    @FXML
    private Button cancelTaskAudit;

    @FXML
    private Label progresTaskIndicator;

    @FXML
    private Button fileComandoOpen;

    @FXML
    private Button fileOutputAudit;

    @FXML
    private TextField textOpenComando;

    @FXML
    private TextField textOutputFileAudit;

    @FXML
    private SearchableComboBox<String> parametroCombobox;

    @FXML
    private TextField textBaseline;

    @FXML
    private Button cargarParametro;

    @FXML
    private Label logGexportIndicator;

    @FXML
    void onAudit(ActionEvent event) {
        if (!textOpenComando.getText().isEmpty()) {
            if (!parametroCombobox.selectionModelProperty().getValue().isEmpty()) {
                if (!textBaseline.getText().isEmpty()) {
                    if (!textOutputFileAudit.getText().isEmpty()) {
                        auditButton.setVisible(false);
                        progresTaskIndicator.setVisible(true);
                        progressAudit.setVisible(true);
                        cancelTaskAudit.setVisible(true);

                        _crearAuditoria = new Auditoria(textOpenComando.getText(),
                                Double.parseDouble(textBaseline.getText()),
                                parametroCombobox.getValue(),
                                textOutputFileAudit.getText(),
                                false);
                        _crearAuditoria.start();
                        _crearAuditoria.setOnSucceeded((e) -> {
                            Platform.runLater(() -> {
                                progressAudit.setVisible(false);
                                logGexportIndicator.setText( _crearAuditoria.getValue().get(_crearAuditoria.getValue().size()-1));
                                //System.out.println("Termino con " + _crearAuditoria.getValue());
                                auditButton.setVisible(true);
                                cancelTaskAudit.setVisible(false);
                                parametroCombobox.setDisable(true);
                                textBaseline.clear();
                            });
                        });

                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR,
                                " Ingrese DIRECTORIO de salida ");
                        alert.initOwner(progresTaskIndicator.getScene().getWindow());
                        alert.setTitle("Ingrese DIRECTORIO de salida ");
                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            " Ingrese BASELINE ");
                    alert.initOwner(progresTaskIndicator.getScene().getWindow());
                    alert.setTitle("Ingrese BASELINE ");
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.showAndWait();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        " Seleccione el PARAMETRO a auditar ");
                alert.initOwner(progresTaskIndicator.getScene().getWindow());
                alert.setTitle("Seleccione el PARAMETRO a auditar ");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    " Seleccione el COMANDO.csv ");
            alert.initOwner(progresTaskIndicator.getScene().getWindow());
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Falta el COMANDO.csv ");
            alert.showAndWait();
        }
    }

    @FXML
    void onCancelTaskAudit(ActionEvent event) {
        Platform.runLater(() -> {
            progressAudit.setVisible(false);
            //logGexportIndicator.setText(_crearAuditoria.getValue().get(_crearAuditoria.getValue().size() - 1));
            //System.out.println("Termino con " + _crearAuditoria.getValue());
            auditButton.setVisible(true);
            cancelTaskAudit.setVisible(false);
            parametroCombobox.setDisable(true);
            textBaseline.clear();
        });
        if (_crearAuditoria.isRunning()) {
            _crearAuditoria.cancel();
        }
    }

    @FXML
    void onCargarParametro(ActionEvent event) {
        cargarParametro.setDisable(true);
        cargarParametro.setText("Cargando");
        cargarParametro.setGraphic(glyphFont.create(FontAwesome.Glyph.SPINNER));
        Auditoria _audit = new Auditoria(inputFile.getAbsolutePath(), 0, "", "", true);
        _audit.start();
        _audit.setOnSucceeded(d -> {

            if (!CollectionUtils.isEmpty(_audit.getValue())) {

                Platform.runLater(() -> {
                    parametroCombobox.setDisable(false);
                    /*if(!rncPossibleSuggestions.isEmpty())
                        rncPossibleSuggestions.clear();*/
                    //searchRnc.setDisable(false);
                    paramPossibleSuggestions.setAll(_audit.getValue());
                    //searchComboboxRNC.getItems().clear();
                    parametroCombobox.setItems(paramPossibleSuggestions);
                });

            }
            System.out.println("VALUE " + _audit.getValue());

            //parametroCombobox.setDisable(false);
            cargarParametro.setText("Cargar");
            cargarParametro.setGraphic(null);
            cargarParametro.setVisible(false);
        });
    }

    @FXML
    void onFileComandoOpen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        fileChooser.setTitle("CARGAR COMANDO.csv");
        Stage stage = (Stage) logGexportIndicator.getScene().getWindow();
        inputFile = fileChooser.showOpenDialog(stage);
        if (inputFile != null) {
            Platform.runLater(() -> {
                textOpenComando.setText(inputFile.getPath());
                cargarParametro.setVisible(true);
                cargarParametro.setDisable(false);
                
                //searchRnc.setDisable(true);
                //searchComboboxRNC.setDisable(true);
            });

        } else {
            parametroCombobox.setDisable(true);
            cargarParametro.setDisable(true);
        }
    }

    @FXML
    void onFileOutputAudit(ActionEvent event) {
        DirectoryChooser direcotryChooser = new DirectoryChooser();
        direcotryChooser.setTitle("Seleccione Carpeta destino");
        Stage stage = (Stage) logGexportIndicator.getScene().getWindow();
        outputAudit = direcotryChooser.showDialog(stage);
        if (outputAudit != null) {
            textOutputFileAudit.setText(outputAudit.getAbsolutePath());
        }
    }
    GlyphFont glyphFont = GlyphFontRegistry.font("FontAwesome");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fileComandoOpen.setGraphic(glyphFont.create(FontAwesome.Glyph.FILES_ALT));
        fileOutputAudit.setGraphic(glyphFont.create(FontAwesome.Glyph.FILES_ALT));

        cancelTaskAudit.setVisible(false);
        progressAudit.setVisible(false);
        cargarParametro.setVisible(false);
        parametroCombobox.setDisable(true);
        // force the field to be numeric only
        textBaseline.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    textBaseline.setText(oldValue);
                }
            }
        });
    }

}
