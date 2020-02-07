/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import org.apache.commons.io.FilenameUtils;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.LogOutputStream;

/**
 *
 * @author P05144
 */
public class UnGzip extends Service{

    private String INPUT_GZIP_FILE;
    private String OUTPUT_FILE;
    private Label logGexportIndicator;

    public UnGzip(String INPUT_GZIP_FILE, String OUTPUT_FILE, Label logGexportIndicator) {
        this.INPUT_GZIP_FILE = INPUT_GZIP_FILE;
        this.OUTPUT_FILE = OUTPUT_FILE;
        this.logGexportIndicator = logGexportIndicator;
    }
    /**
     * Extract GZip
     */
    public void unGzip() {
        try{ //Este es el script 7z e -o../ *.gz -aos
               /* int value =*/ new ProcessExecutor().command(
                    "java", "-Xms128m", "-Xmx256m", "-Djava.awt.headless=true", "-jar",
                    "." + File.separator + "lib" + File.separator + "ExtractArchive.jar",
                    "-i",
                    INPUT_GZIP_FILE,
                    "-o",
                    OUTPUT_FILE
            /*+File.separator+FilenameUtils.getBaseName(INPUT_GZIP_FILE)*/
            )
                    .readOutput(true)
                    .redirectOutput(new LogOutputStream() {
                        @Override
                        protected void processLine(String line) {
                            Platform.runLater(() -> {

                                logGexportIndicator.setText(line);
                            });
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
    /**
     * UnGzip
     * @deprecated 
     */
    public void unGz() {
        try{ //Este es el script 7z e -o../ *.gz -aos
               /* int value =*/ new ProcessExecutor().command(
                    "." + File.separator + "lib" + File.separator + "7z.exe",
                    "x",
                    INPUT_GZIP_FILE + File.separator + "*.gz",
                    "-o" + OUTPUT_FILE,
                    "-aos"
            /*+File.separator+FilenameUtils.getBaseName(INPUT_GZIP_FILE)*/
            )
                    .readOutput(true)
                    .redirectOutput(new LogOutputStream() {
                        @Override
                        protected void processLine(String line) {
                            Platform.runLater(() -> {

                                logGexportIndicator.setText(line);
                            });
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
                if(!isCancelled())
                unGzip();
                //gunzipIt();
                return null;
            }
        };
    }
    
}
