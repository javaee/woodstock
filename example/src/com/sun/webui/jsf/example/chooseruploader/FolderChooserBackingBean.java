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

import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import com.sun.webui.jsf.model.UploadedFile;
import com.sun.webui.jsf.example.index.IndexBackingBean;
import com.sun.webui.jsf.example.common.MessageUtil;

import java.io.*;
import java.util.List;


/**
 * Backing Bean for Folder Chooser example.
 */
public class FolderChooserBackingBean implements Serializable {
    
    // String constant.
    public static final String WINDOWS_OS = "WINDOW";
    
    // Holds value of lookin property.
    private File lookin = null;
    
    // Holds value of selected property.
    private File selected = null;
    
    /** Creates a new instance of FileChooserBackingBean */
    public FolderChooserBackingBean() {
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
     *Getter method for folderName.
     */
    public String getFolderName() {
        
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

