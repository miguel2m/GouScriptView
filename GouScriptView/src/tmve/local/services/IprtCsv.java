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
public class IprtCsv extends Service<List<String>>{
    private String _rnc;
    private boolean iprtSrn;
    private boolean iprtSn;
    private boolean iprtPort;
    private boolean iprtVRF;
    private short _srn;
    private short _sn;
    private String db;
    public IprtCsv(String _rnc, boolean iprtSrn, boolean iprtSn, boolean iprtPort, boolean iprtVRF, String db) {
        this._rnc = _rnc;
        this.iprtSrn = iprtSrn;
        this.iprtSn = iprtSn;
        this.iprtPort = iprtPort;
        this.iprtVRF = iprtVRF;
        this.db=db;
    }

    public short getSrn() {
        return _srn;
    }

    public void setSrn(short _srn) {
        this._srn = _srn;
    }

    public short getSn() {
        return _sn;
    }

    public void setSn(short _sn) {
        this._sn = _sn;
    }

    
   
    
    
    
    public List<String> getIprtRNC() {
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
                    commands.add(this.db+File.separator);
                    commands.add("-rnc");
                    commands.add(_rnc);
                if (iprtSrn)   commands.add("-iprtSrn");
                if (iprtSn){   
                    commands.add("-iprtSn");
                    commands.add("-srn");
                    commands.add(String.valueOf(this.getSrn()));
                }
                if (iprtPort){   
                    commands.add("-iprtPort");
                    commands.add("-srn");
                    commands.add(String.valueOf(this.getSrn()));
                    commands.add("-sn");
                    commands.add(String.valueOf(this.getSn()));
                }
                if (iprtVRF)   commands.add("-iprtVRF");
                new ProcessExecutor(commands)

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
              
                
                    return getIprtRNC();
                
               
            }

            
        
        };
    }
}
