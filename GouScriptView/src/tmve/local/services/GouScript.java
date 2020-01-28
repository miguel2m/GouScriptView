/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.LogOutputStream;
import tmve.local.controller.MainController;

/**
 *
 * @author P05144
 */
public class GouScript extends Service<Integer>{
private String _rnc;
private short _srn;
private short _sn;
private short port;
private String _vrf;
private List<String> inputNodes ;
private Label progressIndicator;

    public GouScript(String _rnc, short _srn, short _sn, short port, String _vrf, List<String> inputNodes, Label progressIndicator) {
        this._rnc = _rnc;
        this._srn = _srn;
        this._sn = _sn;
        this.port = port;
        this._vrf = _vrf;
        this.inputNodes = inputNodes;
        this.progressIndicator = progressIndicator;
    }

    

    
    

    public int createGouScript() {
       int out =-1;
        try{ //Este es el script 7z e -o../ *.gz -aos
                File directory = new File("."+File.separator+"output");
                if (!directory.exists())
                    directory.mkdir();
                List<String> commands = new ArrayList<>();
                    commands.add("java");
                    commands.add("-Xms128m");
                    commands.add("-Xmx256m");
                    commands.add("-Djava.awt.headless=true");
                    commands.add("-jar");
                    commands.add("." + File.separator + "lib" + File.separator + "GouScript.jar");
                    commands.add("-db");
                    commands.add(MainController.outputDirectory+File.separator);
                    commands.add("-rnc");
                    commands.add(_rnc);
                    commands.add("-i");
                    inputNodes.forEach((t) -> {
                        String [] nodeB = t.split("-");
                        commands.add(nodeB[0]);
                    });
                    commands.add("-srn");
                    commands.add(String.valueOf(_srn));
                    commands.add("-sn");
                    commands.add(String.valueOf(_sn));
                    commands.add("-p");
                    commands.add(String.valueOf(port));
                    commands.add("-vrf");
                    commands.add(_vrf);
                    commands.add("-o");
                     commands.add("."+File.separator+"output");

                out = new ProcessExecutor(commands)

                    .readOutput(true)
                    .redirectOutput(new LogOutputStream() {
                        @Override
                        protected void processLine(String line) {
                            //text=line;
                            // Update UI here.
                            Platform.runLater(() -> {
                                progressIndicator.setText(line);
                            });
                            System.out.println("line "+line);
                            //label=line;
                            //System.out.println(" "+line);
                            //System.out.println(" "+line);
                        }
                    })
                    .destroyOnExit()
                    .execute().getExitValue();
                        /*.getExitValue();
                if (value == 0) {
                    System.out.println("Done");
                }*/
        }catch(Exception e){
            System.out.println(" "+e.getMessage());
        }
        return out;
    }
    @Override
    protected Task<Integer> createTask() {
        return new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
               return createGouScript();
            }
        };
    }
    
    
}
