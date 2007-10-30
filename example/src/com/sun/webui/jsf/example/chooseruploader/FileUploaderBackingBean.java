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
        
        // While FacesMessages queued from our validator will invalidate
        // the upload field and cause the label to show the invalidation,
        // the same does not occur with errors reported by the upload 
        // component itself. To make the behavior identical for all 
        // messages, we would need to create a binding for the upload instead
        // of specifying the upload in the jsp, and then invalidate the 
        // field in this method after trapping on the FacesMessage.
        // 
        // However since we chose not to create such a binding, then
        // the upload field is never invalidated when the file to be uploaded
        // exceeds maxSize.  As a result we have to reset our backing bean values
        // to their initial state because the page is submitted despite the
        // fact that we do render an error alert and an error message.  A
        // consequence of this is that the required field's label never shows the
        // invalidation.  An alternative to resetting state and to show
        // the invalidation in the label is to set then UploadFilter's
        // maxSize to the highest value possible (so the upload component
        // essentially never fails) and create a context parameter whose value
        // is less than maxSize and which is validated this bean's validator,
        // throwing an appropriate ValidatorException if the filesize
        // exceeds the value of this context-param.
        
        tmpFileName = null;
        uploadedFile = null;
       
        return true;
    }
    
    /**
     * Action handler when navigating to the main example index.
     */
    public String showExampleIndex() {
        tmpFileName = null;
        uploadedFile = null;
        return IndexBackingBean.INDEX_ACTION;
    }
    
    /**
     * Action handler when navigating to the chooser uploader example index.
     */
    public String showUploaderIndex() {
        tmpFileName = null;
        uploadedFile = null;
        return "showChooserUploader";
    }
}

