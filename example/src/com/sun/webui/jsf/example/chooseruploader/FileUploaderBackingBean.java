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
import javax.faces.component.UIComponent;
import javax.faces.validator.ValidatorException;

import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.index.IndexBackingBean;

import java.io.*;
import java.util.List;


/**
 * Backing Bean for File Uploader example.
 */
public class FileUploaderBackingBean implements Serializable {
    
    // Holds file name.
    private String tmpFileName = null;
    
    // Holds value of uploadPath property.
    private String uploadPath = null;
    
    // Holds value of property uploadedFile.
    private UploadedFile uploadedFile;
    
    /** Creates a new instance of ChooserUploaderBackingBean */
    public FileUploaderBackingBean() {
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
     *It creates a temp file and uploads it in default temp directory.
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
        
        File tmpFile = File.createTempFile(prefix, suffix);
        uploadedFile.write(tmpFile);
        tmpFileName = tmpFile.getAbsolutePath();
        
    }
    
    /**
     *Getter method for fileName.
     */
    public String getFileName() {
           return tmpFileName;
    }
   
    /**
     *Setter method for fileName.
     */
    public void setFileName(String fileName) {
           tmpFileName = fileName;
    }

    /**
     * Summary message for Validator exception.
     */
    public String getSummaryMsg() {
           return MessageUtil.getMessage("chooseruploader_summary");
    }

    /**
     * This method throws validator exception if specified file has zero size.
     * You can also upload empty files.This method shows the use of validator. 
     */
    public void validateFile(FacesContext context,
            UIComponent component, Object value)
            throws ValidatorException {
        
        String msgString = null;
        FacesMessage msg = null;
        
        if (value != null) {
            UploadedFile uploadedFileName = (UploadedFile) value;
            long fileSize  = uploadedFileName.getSize();
                        
            if (fileSize == 0) {
                msgString = MessageUtil.
                            getMessage("chooserUploader_invalidFile");
                
                msg = new FacesMessage(msgString);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
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
        tmpFileName = null;
        uploadPath = null;
        uploadedFile = null;
        return IndexBackingBean.INDEX_ACTION;
    }
    
    /**
     * Action handler when navigating to the chooser uploader example index.
     */
    public String showUploaderIndex() {
        tmpFileName = null;
        uploadPath = null;
        uploadedFile = null;
        return "showChooserUploader";
    }
}

