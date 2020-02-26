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

/**
 *
 * @author Miguelangel
 */
public class Auditoria extends Service<List<String>>{
    private String inputFile;
    private double baseline;
    private String param;
    private String outputFolder;
    private boolean params;

    public Auditoria(String inputFile, double baseline, String param, String outputFolder, boolean params) {
        this.inputFile = inputFile;
        this.baseline = baseline;
        this.param = param;
        this.outputFolder = outputFolder;
        this.params = params;
    }
    
    
    
    public List<String> getAudit() {
        List<String> _rncList = new ArrayList<>();
        try{ //Este es el script 7z e -o../ *.gz -aos
            
                List<String> commands = new ArrayList<>();
                    commands.add("java");
                    /*commands.add("-Xms128m");
                    commands.add("-Xmx256m");
                    commands.add("-Djava.awt.headless=true");*/
                    commands.add("-jar");
                    commands.add("." + File.separator + "lib" + File.separator + "AduitAvilaRan.jar");
                    commands.add("-i");
                    commands.add(this.inputFile);
                if (!this.params){   
                    commands.add("-a");
                    commands.add(this.param);
                    commands.add("-b");
                    commands.add(String.valueOf(this.baseline));
                    commands.add("-o");
                    commands.add(outputFolder);
                }else
                    commands.add("-c");
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
                            System.out.println(""+line);
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
              
                
                    return getAudit();
                
               
            }
        };
    }
    
}
