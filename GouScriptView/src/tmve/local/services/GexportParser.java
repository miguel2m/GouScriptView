/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.services;

import java.io.File;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.LogOutputStream;

/**
 *
 * @author Miguelangel
 */
public class GexportParser extends Service{

    private String INPUT_FOLDER;
    private String OUT_FOLDER;
    private Label logGexportIndicator;

    public GexportParser(String INPUT_FOLDER, String OUT_FOLDER, Label logGexportIndicator) {
        this.INPUT_FOLDER = INPUT_FOLDER;
        this.OUT_FOLDER = OUT_FOLDER;
        this.logGexportIndicator = logGexportIndicator;
    }
    
    
    
    public void gxportParser() {
        try{ //Este es el script 7z e -o../ *.gz -aos
               /* int value =*/ new ProcessExecutor().command(
                    "java", "-Xms128m", "-Xmx256m", "-Djava.awt.headless=true", "-jar",
                    "." + File.separator + "lib" + File.separator + "boda-huaweicmobjectparser.jar",
                    "-i",
                    INPUT_FOLDER,
                    "-o",
                    OUT_FOLDER
            /*+File.separator+FilenameUtils.getBaseName(INPUT_GZIP_FILE)*/
            )
                    .readOutput(true)
                    .redirectOutput(new LogOutputStream() {
                        @Override
                        protected void processLine(String line) {
                            //text=line;
                            // Update UI here.
                            Platform.runLater(() -> {

                                logGexportIndicator.setText(line);
                            });

                            //label=line;
                            //System.out.println(" "+line);
                            //System.out.println(" "+line);
                        }
                    })
                    .destroyOnExit()
                    .execute();
                        /*.getExitValue();
                if (value == 0) {
                    System.out.println("Done");
                }*/
        }catch(Exception e){
            System.out.println(" "+e.getMessage());
        }
    }
    @Override
    protected Task createTask() {
         return new Task() {
            @Override
            protected Void call() throws Exception {
                if(! isCancelled())
                gxportParser();
                //gunzipIt();
                return null;
            }
        };
    }
    
}
