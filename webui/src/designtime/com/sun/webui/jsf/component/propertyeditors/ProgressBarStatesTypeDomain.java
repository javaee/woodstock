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

package com.sun.webui.jsf.component.propertyeditors;

import com.sun.rave.propertyeditors.domains.Domain;
import com.sun.rave.propertyeditors.domains.Element;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.component.ProgressBar;

public class ProgressBarStatesTypeDomain extends Domain {
    
    private static Element[] elements = new Element[] {
        new Element(ProgressBar.TASK_NOT_STARTED, 
                DesignMessageUtil.getMessage(ProgressBarStatesTypeDomain.class, "ProgressBar.notStarted")), //NOI18N
        new Element(ProgressBar.TASK_RUNNING, 
                DesignMessageUtil.getMessage(ProgressBarStatesTypeDomain.class, "ProgressBar.running")), //NOI18N
        new Element(ProgressBar.TASK_PAUSED, 
                DesignMessageUtil.getMessage(ProgressBarStatesTypeDomain.class, "ProgressBar.paused")), //NOI18N
        new Element(ProgressBar.TASK_RESUMED, 
                DesignMessageUtil.getMessage(ProgressBarStatesTypeDomain.class, "ProgressBar.resumed")), //NOI18N
        new Element(ProgressBar.TASK_CANCELED, 
                DesignMessageUtil.getMessage(ProgressBarStatesTypeDomain.class, "ProgressBar.canceled")), //NOI18N
        new Element(ProgressBar.TASK_COMPLETED, 
                DesignMessageUtil.getMessage(ProgressBarStatesTypeDomain.class, "ProgressBar.completed")), //NOI18N    
        new Element(ProgressBar.TASK_FAILED, 
                DesignMessageUtil.getMessage(ProgressBarStatesTypeDomain.class, "ProgressBar.failed")) //NOI18N              
    };
    
    public Element[] getElements() {
        return ProgressBarStatesTypeDomain.elements;
    }
    
}
