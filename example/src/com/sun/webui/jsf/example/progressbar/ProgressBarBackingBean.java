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
        
        String task = "";
        if (getComponentInstance() != null)
            task = getComponentInstance().getTaskState();
        
        if (task != null) {
            if (task.equals(ProgressBar.TASK_PAUSED)) {
                status = MessageUtil.
                            getMessage("progressbar_pausedText");
                return progressRate;
                
            }
            if (task.equals(ProgressBar.TASK_CANCELED)) {
                status = MessageUtil.
                            getMessage("progressbar_canceledText");
                return progressRate;
            }
        }
        
        progressRate = progressRate + 3;
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
        
        return progressRate;
    }
    
    /** Get the status string for ProgressBar */    
    public String getStatus() {
        
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
