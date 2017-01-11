/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djiordhan.logviewer;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author jordy
 */
@ManagedBean
@ViewScoped
public class IndexController implements Serializable {
    
    private static final String LOGS_DIR = "/Users/jordy/GlassFish_Server/glassfish/domains/domain1/logs/";
    private static final String FILE_NAME_PARAM_KEY = "file";
    private String string;
    private String fileName;
    private List<File> files;
    private String filePath;
    
    @PostConstruct
    public void init() {
        FacesContext fcontext = FacesContext.getCurrentInstance();
        Map paramMap = fcontext.getExternalContext().getRequestParameterMap();
        this.fileName = paramMap.containsKey(FILE_NAME_PARAM_KEY) ? (String)paramMap.get(FILE_NAME_PARAM_KEY) : "server.log";
        this.loadFromFile(LOGS_DIR + this.fileName);
        this.files = new ArrayList<>();
        this.files.addAll(Arrays.asList(new File(LOGS_DIR).listFiles()));
    }
    
    public void loadFromFile(String path) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path, new String[0]));
            this.string = new String(encoded, "utf8");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadLogs() {
        this.fileName = new File(filePath).getName();
        try {
            IndexController.redirectToInternalPage("?" + FILE_NAME_PARAM_KEY +"=" + fileName);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void redirectToInternalPage(String url) throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        ec.redirect(ec.getRequestContextPath() + url);
    }

    public String getFileName() {
        return fileName;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getString() {
        return this.string;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<File> getFiles() {
        return files;
    }
    
}
