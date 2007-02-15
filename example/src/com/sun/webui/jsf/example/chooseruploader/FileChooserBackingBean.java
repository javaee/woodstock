/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 */

package com.sun.webui.jsf.example.chooseruploader;

import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import com.sun.webui.jsf.example.index.IndexBackingBean;
import com.sun.webui.jsf.example.common.MessageUtil;

import java.io.*;
import java.util.List;


/**
 * Backing Bean for File Chooser example.
 */
public class FileChooserBackingBean implements Serializable {
    
    // String constant.
    public static final String WINDOWS_OS = "WINDOW";
    
    // Holds value of property lookin.
    private File lookin = null;
    
    // Holds value of property selected.
    private File[] selected;
    
    /** Creates a new instance of FileChooserBackingBean */
    public FileChooserBackingBean() {
        String osName = System.getProperty("os.name").toUpperCase();
        if (osName.startsWith(WINDOWS_OS)) {
            lookin = new File("c:\\\\windows");
        } else {
            lookin = new File("/usr");
        }
    }
    
    /**
     *Getter for property lookin
     *@return Value of property lookin.
     */
    public File getLookin() {
        return lookin;
    }
    
    /**
     *returns absolute path for selected objects.
     */
    private String valueString(Object object) {
        
        if (object instanceof List) {
            List files = (List)object;
            
            Object obj = files.get(0);
            if (obj instanceof File) {
                object = files.toArray(new File[files.size()]);
            } else
                if (obj instanceof String) {
                object = files.toArray(new String[files.size()]);
                } else {
                String value = files.get(0).toString();
                for (int i = 1; i < files.size(); ++i) {
                    value = value + ", " + files.get(i).toString();
                }
                return value;
                }
        }
        
        String value = null;
        if (object instanceof File[]) {
            File[] files = (File[])object;
            value = files[0].getAbsolutePath();
            for (int i = 1; i < files.length; ++i) {
                value = value + ", " + files[i].getAbsolutePath();
            }
        } else
            if (object instanceof File) {
            File file = (File)object;
            value = file.getAbsolutePath();
            } else
                if (object instanceof String[]) {
            String[] files = (String[])object;
            value = files[0];
            for (int i = 1; i < files.length; ++i) {
                value = value + ", " + files[i];
            }
                } else
                    if (object instanceof String) {
            String file = (String)object;
            value = file;
                    }
        return value;
    }
    
    /**
     *Getter method for selected.
     */
    public File[] getSelected() {
        return selected;
    }
    
    /**
     *Setter method for selected.
     */
    public void setSelected(File[] selected) {
        this.selected = selected;
    }
    
    /**
     *Getter method for fileName(s).
     */
    public String getFileName() {
        
        if (selected != null) {
            return valueString(selected);
        } else return null;
    }
 
    /**
     * Summary message for Validator exception.
     */
    public String getSummaryMsg() {           
           return MessageUtil.getMessage("chooseruploader_summary");
    }
    
    /**
     * Checks for errors on page.
     */
    public boolean isErrorsOnPage() {
        
        FacesMessage.Severity severity =
                FacesContext.getCurrentInstance().getMaximumSeverity();
        if (severity == null) {
            return false;
        }
        if (severity.compareTo(FacesMessage.SEVERITY_ERROR) >= 0) {
            return true;
        }
        return false;
    }
       
    /**
     * Action handler when navigating to the main example index.
     */
    public String showExampleIndex() {
        
        selected = null;
        return IndexBackingBean.INDEX_ACTION;
    }
    
    /**
     * Action handler when navigating to the chooser uploader example index.
     */
    public String showUploaderIndex() {
        
        selected = null;
        return "showChooserUploader";
    }
}

