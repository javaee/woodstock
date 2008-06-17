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
/*
 * ButtonBackingBean.java
 *
 * Created on January 2, 2006, 5:18 PM
 */

package com.sun.webui.jsf.example.button;

import java.io.Serializable;

import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;

import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.OptionTitle;
import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.index.IndexBackingBean;

/**
 * Backing bean for Button example.
 *
 * Note that we must implement java.io.Serializable or
 * javax.faces.component.StateHolder in case client-side
 * state saving is used, or if server-side state saving is
 * used with a distributed system.
 */
public class ButtonBackingBean implements Serializable{
    
    private Option[] testCaseOptions = null;
    private String summary = null;
    private String detail = null;
    private boolean primaryDisabled = false;
    private boolean secondaryDisabled = false;
    
    /** Creates a new instance of ButtonBackingBean */
    public ButtonBackingBean() {
        testCaseOptions = new Option[3];
        testCaseOptions[0] = new OptionTitle(
                MessageUtil.getMessage("button_testCase_title"));
        testCaseOptions[1] = new Option("button_testCase_disableAll",
                MessageUtil.getMessage("button_testCase_disableAll"));
        testCaseOptions[2] = new Option("button_testCase_enableAll", 
                MessageUtil.getMessage("button_testCase_enableAll"));
    }
    
    /** Return the array of test case options */
    public Option[] getTestCaseOptions() {
        return testCaseOptions;
    }
    
    /** Action listener for icon button. */
    public void iconActionListener(ActionEvent e) {
        setAlertInfo(e, null);
    }
    
    /** Action listener for primary button. */
    public void primaryActionListener(ActionEvent e) {
        setAlertInfo(e, "button_primaryButtonText");
    }
    
    /** Action listener for secondary button. */
    public void secondaryActionListener(ActionEvent e) {
        setAlertInfo(e, "button_secondaryButtonText");
    }
    
    /**
     * Set the alert summary and detail messages based on the component
     * associated with the event.  The valueKey is the message key
     * for the detail message, or null if no detail is required.
     */
    private void setAlertInfo(ActionEvent e, String valueKey) {
        FacesContext context = FacesContext.getCurrentInstance();
        String clientID = e.getComponent().getClientId(context);
        
        // We only want the last part of the fully-qualified clientID
        int n = clientID.lastIndexOf(':');
        if (n >= 0) {
            clientID = clientID.substring(n + 1);
        }
        
        Object[] args = null;
        String detailKey = null;
        if (valueKey != null) {
            args = new Object[2];
            args[1] = MessageUtil.getMessage(valueKey);
            detailKey = "button_alertElementDetail";
        } else {
            args = new Object[1];
            detailKey = "button_alertElementDetailNoValue";
        }
        args[0] = clientID;
        summary = MessageUtil.getMessage("button_alertElement");
        detail = MessageUtil.getMessage(detailKey, args);
    }
    
    /** Action handler for all buttons. */
    public String actionHandler() {
        // Returning null causes page to re-render.
        return null;
    }
    
    /** Action handler for the test case dropdown menu. */
    public String testCaseActionHandler() {
        // Disable the alert by having no summary and detail.
        summary = null;
        detail = null;
        
        return null;
    }
    
    /** Return the enable/disable state of the primary button. */
    public boolean getPrimaryDisabled() {
        return primaryDisabled;
    }

    /** Set the enable/disable state of the primary button. */
    public void setPrimaryDisabled(boolean b) {
        primaryDisabled = b;
    }
    
    /** Return the enable/disable state of the secondary button. */
    public boolean getSecondaryDisabled() {
        return secondaryDisabled;
    }

    /** Set the enable/disable state of the secondary button. */
    public void setSecondaryDisabled(boolean b) {
        secondaryDisabled = b;
    }
    
    /** Return the alert summary message. */
    public String getAlertSummary() {
        return summary;
    }
    
    /** Return the alert detail message. */
    public String getAlertDetail() {
        return detail;
    }
    
    /** Return the render state of the alert. */
    public boolean getAlertRendered() {
        if (summary == null)
            return false;
        
        return true;
    }

    /** Return the selected state of the primary button's checkbox. */
    public boolean getPrimaryCBSelected() {
        // Checkbox state is inverse of button's disabled state.
        return !getPrimaryDisabled();
    }
    
    /** Set the selected state of the primary button's checkbox. */
    public void setPrimaryCBSelected(boolean b) {
        // Checkbox state is inverse of button's disabled state.
        setPrimaryDisabled(!b);
    }
    
    /** Return the selected state of the secondary button's checkbox. */
    public boolean getSecondaryCBSelected() {
        // Checkbox state is inverse of button's disabled state.
        return !getSecondaryDisabled();
    }
    
    /** Set the selected state of the secondary button's checkbox. */
    public void setSecondaryCBSelected(boolean b) {
        // Checkbox state is inverse of button's disabled state.
        setSecondaryDisabled(!b);
    }
    
    // Action handler when navigating to the main example index.
    public String showExampleIndex() {
        reset();
        return IndexBackingBean.INDEX_ACTION;
    }
    
    private void reset() {
        summary = null;
        detail = null;
        primaryDisabled = false;
        secondaryDisabled = false;
    }
    
    /** Return the state result for the primary button */
    public String getPrimaryResult() {
        String buttonLabel = MessageUtil.getMessage("button_primaryButtonText");
        if (getPrimaryDisabled()) {
            return MessageUtil.getMessage("button_resultDisabled", buttonLabel);
        } else {
            return MessageUtil.getMessage("button_resultEnabled", buttonLabel);
        }
    }
    
    /** Return the state result for the secondary button */
    public String getSecondaryResult() {
        String buttonLabel = MessageUtil.getMessage("button_secondaryButtonText");
        if (getSecondaryDisabled()) {
            return MessageUtil.getMessage("button_resultDisabled", buttonLabel);
        } else {
            return MessageUtil.getMessage("button_resultEnabled", buttonLabel);
        }
    }
}
