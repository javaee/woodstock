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

package com.sun.webui.jsf.example.pagetitle;

import java.beans.*;
import java.io.Serializable;
import com.sun.webui.jsf.component.DropDown;
import com.sun.webui.jsf.component.TextField;
import com.sun.webui.jsf.model.Option;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.AbortProcessingException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import com.sun.webui.jsf.example.common.MessageUtil;

/**
 * Backing bean for Content Page Title example.
 */

public class PagetitleBackingBean implements Serializable {
    
    // Holds the text to be displayed in the alert boxes.
    private String message = null;
    private String detail = null;
    
    // Holds the texts to be displayed in the text fields.
    private String text1 = null;
    private String text2 = null;
        
    // Holds the PageViews
    private Option[] views = null;
    
    // Holds the selected item of PageView
    private String selectedItem = "View1";
    
    // Initial value for renedering the Alert box.
    private boolean isRendered = false;
    
    /** Creates a new instance of PagetitleBackingBean. */
    public PagetitleBackingBean() {
        views = new Option[3];
        views[0] = new Option("View1", MessageUtil.getMessage("pagetitle_view1"));
        views[1] = new Option("View2", MessageUtil.getMessage("pagetitle_view2"));
        views[2] = new Option("View3", MessageUtil.getMessage("pagetitle_view3"));
    }
    
    /** 
     * Returns value that decides whether the alertbox should be rendered or 
     * not.  
     */
    public boolean getIsRendered() {
        return isRendered;
    }
        
    /** Return message to be displayed in alert box. */
    public String getMessage() {
        return message;
    }
    
    /** Returns detail to be displayed in alert box. */
    public String getDetail() {
        return detail;
    }
        
    /** Returns options to be displayed in PageView dropdown menu. */
    public Option[] getViews() {
        isRendered = false;
        return views;
    }
    
    /** Return text to be displayed in textfield. */
    public String getText1() {
         return text1;
    }
    
    /** Sets text to be displayed in textfield. */
    public void setText1(String text1) {
        this.text1 = text1;
    }
    
    /** Return text to be displayed in textfield. */
    public String getText2() {
        return text2;
    }
    
    /** Sets text to be displayed in textfield. */
    public void setText2(String text2) {
        this.text2 = text2;
    }
            
    /** Returns the selected item in the PageView. */
    public String getSelectedItem() {
        return selectedItem;
    }
    
    /** Sets the selected item in the PageView. */
    public void setSelectedItem(String s) {
        selectedItem = s;
    }
    
    /** Checks if there is any error on the page. */  
    public boolean isErrorsOnPage() {
        message = MessageUtil.getMessage("pagetitle_error");
        detail = MessageUtil.getMessage("pagetitle_detail");     
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
         
    /** Message to be displayed when Save Button is clicked. */
    public void saveClicked() {
        isRendered = true;
        message = MessageUtil.getMessage("pagetitle_elementClicked");
        detail = MessageUtil.getMessage("pagetitle_saveClicked");        
    }
    
    /** Message to be displayed when Reset Button is clicked. */
    public void resetClicked(ActionEvent ae) throws NullPointerException {
        FacesContext context = FacesContext.getCurrentInstance();
        TextField tf1 = (TextField) context.getViewRoot().findComponent("form1:pagetitle:text1");
        TextField tf2 = (TextField) context.getViewRoot().findComponent("form1:pagetitle:text2");                
        tf1.setSubmittedValue(null);
        tf2.setSubmittedValue(null);
        tf2.setValue(null);
        text1 = null;
        text2 = null;
        isRendered = true;        
        message = MessageUtil.getMessage("pagetitle_elementClicked");
        detail = MessageUtil.getMessage("pagetitle_resetClicked");
    }
    
    /** Message to be displayed when the PageViews are clicked. */
    public void menuChanged(ActionEvent e) throws AbortProcessingException {
        UIComponent c = e.getComponent();        
        DropDown menu = (DropDown) c;
        isRendered = true;
        message = MessageUtil.getMessage("pagetitle_elementClicked");
        detail = MessageUtil.getMessage("pagetitle_viewClicked") + " " + 
                 menu.getValue();
    }
    
    /** Resets page defaults while navigating to the Index Page. */
    public String showIndex() {
        isRendered = false;
        text1 = null;
        text2 = null;
        selectedItem = "View1";
        return "showIndex";
    }        
}
