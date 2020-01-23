/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.services;

import com.opencsv.bean.BeanVerifier;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import tmve.local.controller.MainController;
import tmve.local.model.AdjNode;

/**
 *
 * @author P05144
 */
public class AdjnodeCsv extends Service<List<AdjNode>>{
    private String _rnc;


    public AdjnodeCsv(String _rnc) {
        this._rnc = _rnc;

    }
    
    
    
    public List<AdjNode> getAdjNode()throws IOException{
        
        Path myPath = Paths.get(MainController.outputDirectory+File.separator+"ADJNODE.csv");
        List <AdjNode> aniNodes;
        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {

            HeaderColumnNameMappingStrategy<AdjNode> strategy
                    = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(AdjNode.class);
            BeanVerifier beanVerifier = (BeanVerifier) (Object t) -> {
                AdjNode node  = (AdjNode)t;
                return (node.getFilename().contains(_rnc)); //To change body of generated lambdas, choose Tools | Templates.
            };
            
            CsvToBean csvToBean = new CsvToBeanBuilder(br)
                    .withType(AdjNode.class)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withVerifier(beanVerifier)
                    .build();
            
            aniNodes= csvToBean.parse();

            
        }
        return aniNodes;
        
    }
    
    @Override
    protected Task<List<AdjNode>> createTask() {
        return new  Task<List<AdjNode>>() {
            @Override
            protected List call() throws Exception {
                return getAdjNode();
            }
        
        };
    }
   
}
