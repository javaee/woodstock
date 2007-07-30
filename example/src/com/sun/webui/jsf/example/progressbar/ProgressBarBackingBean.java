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

package com.sun.webui.jsf.example.progressbar;

import com.sun.webui.jsf.component.ProgressBar;
import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.index.IndexBackingBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * Backing bean for ProgressBar example.
 */
public class ProgressBarBackingBean {
    
    // holds progress value.
    private int progressRate = 0;
    
    // holds status string.
    private String status = null;
    
    // Outcome strings used in the faces config.
    private final static String SHOW_PROGRESSBAR_INDEX  = "showProgressBar";
    
    /** Creates a new instance of ProgressBean */
    public ProgressBarBackingBean() {
        
    }
    
    /** This method generates the progress value. */
    public int getProgressRate() {        
        
        return progressRate;
    }
    
    /** Get the status string for ProgressBar */    
    public String getStatus() {
        
        String task = "";
        if (getComponentInstance() != null)
            task = getComponentInstance().getTaskState();
        
        progressRate = progressRate + 3;
        
        if (task != null) {
            if (task.equals(ProgressBar.TASK_PAUSED)) {
                status = MessageUtil.
                            getMessage("progressbar_pausedText");
                progressRate = progressRate - 3;
                return status;
            }
            if (task.equals(ProgressBar.TASK_CANCELED)) {
                status = MessageUtil.
                            getMessage("progressbar_canceledText");
                progressRate = progressRate - 3;
                return status;
            }
        }
                
        status = progressRate + MessageUtil.
                            getMessage("progressbar_percentText");
        
        if (progressRate > 99) {
            progressRate = 100;
        }
        
        if (progressRate == 100) {
            
            getComponentInstance().setTaskState(ProgressBar.TASK_COMPLETED);
            status = MessageUtil.
                            getMessage("progressbar_completedText");
        }
        return status;
    }
    
    /**
     * Method to get the ProgressBar instance.
     */
    public ProgressBar getComponentInstance() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIComponent comp = context.getViewRoot().findComponent("form1:progressBarContentPage:pb1");
        ProgressBar pb = (ProgressBar) comp;
        
        return pb;
    }
    
    /**
     * Action handler when navigating to the progressbar example index.
     */    
    public String showProgressBarIndex() {
        getComponentInstance().setTaskState(ProgressBar.TASK_NOT_STARTED);
        progressRate = 0;
        status = "";
        return SHOW_PROGRESSBAR_INDEX;
    }
    
    /**
     * Action handler when navigating to the main example index.
     */
    public String showExampleIndex() {
        
        getComponentInstance().setTaskState(ProgressBar.TASK_NOT_STARTED);
        progressRate = 0;
        status = "";
        return IndexBackingBean.INDEX_ACTION;
    }
    
}
