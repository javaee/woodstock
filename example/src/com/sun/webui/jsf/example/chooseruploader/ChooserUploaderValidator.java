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

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.sun.webui.jsf.example.common.MessageUtil;

import java.io.File;

/**
 * Validator class for Chooser/Uploader example.
 */
public class ChooserUploaderValidator implements Validator {
    
    /** Creates a new instance of ChooserUploaderValidator */
    public ChooserUploaderValidator() {
    }
    
    /**
     * This method throws validator exception if specified directory is 
     * invalid or do not have write permission.
     */
    public void validate(FacesContext context,
            UIComponent component, Object value)
            throws ValidatorException {
        
        String msgString = null;
        FacesMessage msg = null;
        
        if (value != null) {
            String dirPath = (String) value;
            File tempDir = new File(dirPath);
            
            if (!tempDir.isDirectory()) {
                msgString = MessageUtil.
                            getMessage("chooserUploader_invalidDir");
                
                msg = new FacesMessage(msgString);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
                
            } else if (!tempDir.canWrite()) {
                msgString = MessageUtil.
                            getMessage("chooserUploader_permissionDenied");
                
                msg = new FacesMessage(msgString);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            } 
        }
    }
}
