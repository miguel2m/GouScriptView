/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.controller;

import javafx.application.HostServices;
import tmve.local.main.GouScript;
import tmve.local.view.ViewFactory;

/**
 *
 * @author P05144
 */
public abstract class BaseController {
    
    protected GouScript gouScript;
    protected  ViewFactory viewFactory;
    protected  String fxmlName;
    protected HostServices hostServices ;
    public BaseController(GouScript gouScript, ViewFactory viewFactory, String fxmlName) {
        this.gouScript = gouScript;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }

    public void setFxmlName(String fxmlName) {
        this.fxmlName = fxmlName;
    }

    public HostServices getHostServices() {
        return hostServices;
    }

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }
    
    
    
    
    
}
