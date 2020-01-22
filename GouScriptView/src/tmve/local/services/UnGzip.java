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
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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

    public UnGzip(String INPUT_GZIP_FILE, String OUTPUT_FILE) {
        this.INPUT_GZIP_FILE = INPUT_GZIP_FILE;
        this.OUTPUT_FILE = OUTPUT_FILE;
    }
    
    
    
    
    /**
     * GunZip it
     */
    public void gunzipIt(){
 
     byte[] buffer = new byte[2048];
 
     try{
 
    	 GZIPInputStream gzis = 
    		new GZIPInputStream(new FileInputStream(INPUT_GZIP_FILE));
 
    	 FileOutputStream out = 
            new FileOutputStream(OUTPUT_FILE+"/"+FilenameUtils.getBaseName(INPUT_GZIP_FILE));
 
        int len;
        while ((len = gzis.read(buffer)) > 0) {
        	out.write(buffer, 0, len);
        }
 
        gzis.close();
    	out.close();
 
    	System.out.println("Done");
    	
    }catch(IOException ex){
       ex.printStackTrace();   
    }
   } 
    
    public void unGz() {
        try{ //Este es el script 7z e -o../ *.gz -aos
                int value = new ProcessExecutor().command(
                        "."+File.separator+"lib"+File.separator+"7z.exe",
                        "x",
                        
                        INPUT_GZIP_FILE,
                        "-o"+OUTPUT_FILE,
                        "-aos"
                        /*+File.separator+FilenameUtils.getBaseName(INPUT_GZIP_FILE)*/
                       
                )
                        .readOutput(true)
                        .redirectOutput(new LogOutputStream() {
                                @Override
                                protected void processLine(String line) {
                                    //text=line;
                                    // Update UI here.
                                   
                                    System.out.println("line "+line);
                                    //label=line;
                                     //System.out.println(" "+line);
                                    //System.out.println(" "+line);
                                }
                            })
                        .destroyOnExit()
                        .execute()
                        .getExitValue();
                if (value == 0) {
                    System.out.println("Done");
                }
        }catch(Exception e){
            System.out.println(" "+e.getMessage());
        }
    }
    
    @Override
    protected Task createTask() {
         return new Task() {
            @Override
            protected Void call() throws Exception {
                unGz();
                //gunzipIt();
                return null;
            }
        };
    }
    
}
