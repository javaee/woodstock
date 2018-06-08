/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.webui.jsf.example.chooseruploader;

import com.sun.webui.jsf.model.UploadedFile;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.application.FacesMessage;

import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.index.IndexBackingBean;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Backing Bean for Chooser Uploader example.
 */
public class ChooserUploaderBackingBean implements Serializable {
    
    // String constant.
    public static final String WINDOWS_OS = "WINDOW";
    
    // Holds value of property lookin.
    private File lookin = null;
    
    // Holds value of property fileChooserLookin.
    private File fileChooserLookin = null;
    
    // Holds value of property selected.
    private File selected = null;
    
    // Holds value of property uploadPath.
    private String uploadPath = null;
    
    // Holds value of property forwardName.
    private String forwardName = null;
    
    // Holds value of property uploadedFile.
    private UploadedFile uploadedFile;
    
    /** Creates a new instance of ChooserUploaderBackingBean */
    public ChooserUploaderBackingBean() {
        String osName = System.getProperty("os.name").toUpperCase();
        
            if (osName.startsWith(WINDOWS_OS)) {
                lookin = new File("c:\\\\windows");
            } else {
                lookin = new File("/usr");
            }
            fileChooserLookin = new File(lookin.toString());
    }
    
    /**
     * Getter for property uploadedFile.
     * @return Value of property uploadedFile.
     */
    public UploadedFile getUploadedFile() {
        
        return this.uploadedFile;
    }
    
    /**
     * Setter for property uploadedFile.
     * @param uploadedFile New value of property uploadedFile.
     */
    public void setUploadedFile(UploadedFile uploadedFile) {
        
        this.uploadedFile = uploadedFile;
    }
    
    /**
     *Getter for property lookin
     *@return Value of property lookin.
     */
    public File getLookin() {
           return lookin;
    }
    
    /**
     * Getter for property fileChooserLookin.     
     */
    public File getFileChooserLookin() {
        if (uploadPath != null) {
           return (new File(uploadPath));
        } else {
           return fileChooserLookin;
        }
    }
    
    /**
     *Getter method for selected.
     */
    public File getSelected() {
        return selected;
    }
    
    /**
     *Setter method for selected.
     */
    public void setSelected(File selected) {
        this.selected = selected;
    }
    
    /**
     *It creates a temp file and uploads it in a specified directory.
     */
    public void writeFile() throws Exception {
        
        if (uploadedFile == null) {
            return;
        }
        String name = uploadedFile.getOriginalName();
        if (name == null || name.length() == 0) {
            name = "tmp.tmp";
        }
        
        int index = name.indexOf(".");
        String suffix = ".tmp";
        if (index != -1) {
            suffix = name.substring(name.indexOf("."));
            if (suffix.length() == 0) {
                suffix = ".tmp";
            }
        }
        String prefix = name;
        if (index != -1) {
            prefix = name.substring(0, name.indexOf("."));
            if (prefix.length() < 3) {
                prefix = "tmp";
            }
            if (prefix.indexOf("\\") != -1) {
                prefix = prefix.replace('\\', '_');
            }
            if (prefix.indexOf(":") != -1) {
                prefix = prefix.replace(':', '_');
            }
        }
        File tempDir = null;
        if (uploadPath != null) {
           tempDir = new File(uploadPath);
        }
        File tmpFile = File.createTempFile(prefix, suffix, tempDir);
        uploadedFile.write(tmpFile);
        
    }
    
    /**
     *Getter method for uploadPath.
     */
    public String getUploadPath() {
        return uploadPath;
    }
    
    /**
     *Setter method for uploadPath.
     */
    public void setUploadPath(String path) {
        uploadPath = path;
    }
    
    /**
     *Action Listener method.
     */
    public void goToPage(ActionEvent e) {
        
        FacesContext context = FacesContext.getCurrentInstance();
        String ClientId = e.getComponent().getClientId(context);
        
        if (ClientId.equals("indexForm:fileChooser")) {
            forwardName = "fileChooser";
            
        } else if (ClientId.equals("indexForm:folderChooser")) {
            forwardName = "folderChooser";
            
        } else if (ClientId.equals("indexForm:fileUploader")) {
            forwardName = "fileUploader";
            
        } else { 
            forwardName = "fileChooserUploader";
        }
    }
    
    /**
     * Action method.
     */
    public String forwardAction() {
        return forwardName;
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
        
        lookin = null;
        selected = null;
        uploadPath = null;
        uploadedFile = null;
        return IndexBackingBean.INDEX_ACTION;
    }
    
    /**
     * Action handler when navigating to the chooser uploader example index.
     */
    public String showUploaderIndex() {
        
        lookin = null;
        selected = null;
        uploadPath = null;
        uploadedFile = null;
        return "showChooserUploader";
    }
}
