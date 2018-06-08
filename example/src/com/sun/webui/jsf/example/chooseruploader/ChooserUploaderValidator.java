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
