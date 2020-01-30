/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.services;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.LogOutputStream;
import tmve.local.controller.MainController;

/**
 *
 * @author P05144
 */
public class AdjnodeCsv extends Service<List<String>>{
    private String _rnc;
    private boolean node;
    private String db;

    public AdjnodeCsv(String _rnc, boolean node, String db) {
        this._rnc = _rnc;
        this.node = node;
        this.db = db;
    }
    
    public List<String> getAdjNode() {
        List<String> _rncList = new ArrayList<>();
        try{ //Este es el script 7z e -o../ *.gz -aos
            
                List<String> commands = new ArrayList<>();
                    commands.add("java");
                    commands.add("-Xms128m");
                    commands.add("-Xmx256m");
                    commands.add("-Djava.awt.headless=true");
                    commands.add("-jar");
                    commands.add("." + File.separator + "lib" + File.separator + "ReadGxportCsvDB.jar");
                    commands.add("-db");
                    commands.add(this.db + File.separator);
                    commands.add("-rnc");
                    commands.add(_rnc);
                if (node)   commands.add("-n");
               /* int value =*/ new ProcessExecutor(commands)/*.command(
                    "java", "-Xms128m", "-Xmx256m", "-Djava.awt.headless=true", "-jar",
                    "." + File.separator + "lib" + File.separator + "ReadGxportCsvDB.jar",
                    "-db",
                    MainController.outputDirectory+File.separator,
                    "-rnc",
                    _rnc
            /*+File.separator+FilenameUtils.getBaseName(INPUT_GZIP_FILE)*/
            /*)*/
                    .readOutput(true)
                    .redirectOutput(new LogOutputStream() {
                        @Override
                        protected void processLine(String line) {
                            //text=line;
                            // Update UI here.

                            _rncList.add(line);
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
        return _rncList;
    }
    
    @Override
    protected Task<List<String>> createTask() {
        return new  Task<List<String>>() {
            @Override
            protected List call()  {
              
                
                    return getAdjNode();
                
               
            }

            
        
        };
    }
   
}
