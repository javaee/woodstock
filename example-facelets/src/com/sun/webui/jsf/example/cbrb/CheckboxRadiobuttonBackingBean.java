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
 * CheckboxRadiobuttonBackingBean.java
 *
 * Created on January 30, 2006, 3:47 PM
 */

package com.sun.webui.jsf.example.cbrb;

import java.io.Serializable;

import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;

import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.OptionTitle;
import com.sun.webui.jsf.component.DropDown;
import com.sun.webui.jsf.component.Checkbox;
import com.sun.webui.jsf.component.RadioButton;
import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.index.IndexBackingBean;

/**
 *
 */
public class CheckboxRadiobuttonBackingBean implements Serializable {
    
    // Outcome string used in the faces config.
    public final static String SHOW_CBRB_INDEX = "showCbRbIndex";
    
    // Default selection value for the "red" checkbox.
    private static final boolean RED_DEFAULT_SELECTED = false;
    
    // Default selection values for the radio button.
    // At most, only one of these should be set to true;
    private static final boolean SERVER_DEFAULT_SELECTED = false;
    private static final boolean VOLUME_DEFAULT_SELECTED = true;
    private static final boolean POOL_DEFAULT_SELECTED = false;
    
    // Default selection values for the radio button image.
    // At most, only one of these should be set to true;
    private static final boolean SERVER_IMAGE_DEFAULT_SELECTED = false;
    private static final boolean VOLUME_IMAGE_DEFAULT_SELECTED = true;
    private static final boolean POOL_IMAGE_DEFAULT_SELECTED = false;
    
    // Initial selection values are the defaults. 
    private boolean redSelected = RED_DEFAULT_SELECTED;
    private boolean serverSelected = SERVER_DEFAULT_SELECTED;
    private boolean volumeSelected = VOLUME_DEFAULT_SELECTED;
    private boolean poolSelected = POOL_DEFAULT_SELECTED;
    private boolean serverImageSelected = SERVER_IMAGE_DEFAULT_SELECTED;
    private boolean volumeImageSelected = VOLUME_IMAGE_DEFAULT_SELECTED;
    private boolean poolImageSelected = POOL_IMAGE_DEFAULT_SELECTED;
            
    private boolean redCBDisabled = false;
    private boolean serverRBDisabled = false;
    private boolean volumeRBDisabled = false;
    private boolean poolRBDisabled = false;
    private boolean serverImageRBDisabled = false;
    private boolean volumeImageRBDisabled = false;
    private boolean poolImageRBDisabled = false;
    private Option[] testCaseOptions = null;        
    
    /** Creates a new instance of CheckboxRadiobuttonBackingBean */
    public CheckboxRadiobuttonBackingBean() {
        testCaseOptions = new Option[6];
        testCaseOptions[0] = new OptionTitle(
                MessageUtil.getMessage("cbrb_testCase_title"));
        testCaseOptions[1] = new Option("cbrb_testCase_toggleCheckboxState",
                MessageUtil.getMessage("cbrb_testCase_toggleCheckboxState"));
        testCaseOptions[2] = new Option("cbrb_testCase_toggleRadiobuttonState",
                MessageUtil.getMessage("cbrb_testCase_toggleRadiobuttonState"));
        testCaseOptions[3] = new Option("cbrb_testCase_toggleRadiobuttonImageState",
                MessageUtil.getMessage("cbrb_testCase_toggleRadiobuttonImageState"));
        testCaseOptions[4] = new Option("cbrb_testCase_disableAll",
                MessageUtil.getMessage("cbrb_testCase_disableAll"));
        testCaseOptions[5] = new Option("cbrb_testCase_enableAll", 
                MessageUtil.getMessage("cbrb_testCase_enableAll"));
    }
    
    /** Return the array of test case options */
    public Option[] getTestCaseOptions() {
        return testCaseOptions;
    }

    /** ActionListener for the test case menu */
    public void testCaseActionListener(ActionEvent e) {
        DropDown dropDown = (DropDown) e.getComponent();
        String selected = (String) dropDown.getSelected();        

        // Since the action is immediate, the components won't
        // go through the Update Model phase.  So, we need to explicitly set the 
        // values and update the model object for the given action selected.
        Checkbox cb;
        RadioButton rb;
        FacesContext context = FacesContext.getCurrentInstance();        

        if (selected.equals("cbrb_testCase_toggleCheckboxState")) {
            cb = (Checkbox) context.getViewRoot().findComponent("form1:RedCheckbox");
            cb.setDisabled(!getRedCBDisabled());
            setRedCBDisabled(!getRedCBDisabled());

        } else if (selected.equals("cbrb_testCase_toggleRadiobuttonState")) {
            rb = (RadioButton) context.getViewRoot().findComponent("form1:rbServer"); 
            rb.setDisabled(!getServerRBDisabled());
            rb = (RadioButton) context.getViewRoot().findComponent("form1:rbVolume");                        
            rb.setDisabled(!getVolumeRBDisabled());
            rb = (RadioButton) context.getViewRoot().findComponent("form1:rbPool");                        
            rb.setDisabled(!getPoolRBDisabled());

            setServerRBDisabled(!getServerRBDisabled());
            setVolumeRBDisabled(!getVolumeRBDisabled());
            setPoolRBDisabled(!getPoolRBDisabled());

        } else if (selected.equals("cbrb_testCase_toggleRadiobuttonImageState")) {
            rb = (RadioButton) context.getViewRoot().findComponent("form1:rbimServer"); 
            rb.setDisabled(!getServerImageRBDisabled());
            rb = (RadioButton) context.getViewRoot().findComponent("form1:rbimVolume");                        
            rb.setDisabled(!getVolumeImageRBDisabled());
            rb = (RadioButton) context.getViewRoot().findComponent("form1:rbimPool");                        
            rb.setDisabled(!getPoolImageRBDisabled());

            setServerImageRBDisabled(!getServerImageRBDisabled());
            setVolumeImageRBDisabled(!getVolumeImageRBDisabled());
            setPoolImageRBDisabled(!getPoolImageRBDisabled());

        } else if (selected.equals("cbrb_testCase_disableAll")) {
            cb = (Checkbox) context.getViewRoot().findComponent("form1:RedCheckbox");
            cb.setDisabled(true);
            rb = (RadioButton) context.getViewRoot().findComponent("form1:rbServer"); 
            rb.setDisabled(true);
            rb = (RadioButton) context.getViewRoot().findComponent("form1:rbVolume");                        
            rb.setDisabled(true);
            rb = (RadioButton) context.getViewRoot().findComponent("form1:rbPool");                        
            rb.setDisabled(true);
            rb = (RadioButton) context.getViewRoot().findComponent("form1:rbimServer"); 
            rb.setDisabled(true);
            rb = (RadioButton) context.getViewRoot().findComponent("form1:rbimVolume");                        
            rb.setDisabled(true);
            rb = (RadioButton) context.getViewRoot().findComponent("form1:rbimPool");                        
            rb.setDisabled(true);

            setRedCBDisabled(true);
            setServerRBDisabled(true);
            setVolumeRBDisabled(true);
            setPoolRBDisabled(true);
            setServerImageRBDisabled(true);
            setVolumeImageRBDisabled(true);
            setPoolImageRBDisabled(true);

        } else if (selected.equals("cbrb_testCase_enableAll")) {
            cb = (Checkbox) context.getViewRoot().findComponent("form1:RedCheckbox");
            cb.setDisabled(false);
            rb = (RadioButton) context.getViewRoot().findComponent("form1:rbServer"); 
            rb.setDisabled(false);
            rb = (RadioButton) context.getViewRoot().findComponent("form1:rbVolume");                        
            rb.setDisabled(false);
            rb = (RadioButton) context.getViewRoot().findComponent("form1:rbPool");                        
            rb.setDisabled(false);
            rb = (RadioButton) context.getViewRoot().findComponent("form1:rbimServer"); 
            rb.setDisabled(false);
            rb = (RadioButton) context.getViewRoot().findComponent("form1:rbimVolume");                        
            rb.setDisabled(false);
            rb = (RadioButton) context.getViewRoot().findComponent("form1:rbimPool");                        
            rb.setDisabled(false);

            setRedCBDisabled(false);
            setServerRBDisabled(false);
            setVolumeRBDisabled(false);
            setPoolRBDisabled(false);
            setServerImageRBDisabled(false);
            setVolumeImageRBDisabled(false);
            setPoolImageRBDisabled(false);
        }
    }
    
    private void reset() {
        // Set the initial selected values
        setRedSelected(RED_DEFAULT_SELECTED);
        setServerSelected(SERVER_DEFAULT_SELECTED);
        setVolumeSelected(VOLUME_DEFAULT_SELECTED);
        setPoolSelected(POOL_DEFAULT_SELECTED);
        setServerImageSelected(SERVER_IMAGE_DEFAULT_SELECTED);
        setVolumeImageSelected(VOLUME_IMAGE_DEFAULT_SELECTED);
        setPoolImageSelected(POOL_IMAGE_DEFAULT_SELECTED);

        // Set the initial states
        setRedCBDisabled(false);
        setServerRBDisabled(false);
        setVolumeRBDisabled(false);
        setPoolRBDisabled(false);
        setServerImageRBDisabled(false);
        setVolumeImageRBDisabled(false);
        setPoolImageRBDisabled(false);
    }
    
    /** ActionListener for the Reset button */
    public void resetActionListener(ActionEvent e) {
        // Since the action is immediate, the components won't
        // go through the Update Model phase. However, its submitted value
        // gets set in the Apply Request Value phase and this value is retained
        // when the page is redisplayed. 
        //
        // So, we need to explicitly erase the submitted values and then update
        // the model object with initial values.
        Checkbox cb;
        RadioButton rb;
        
        FacesContext context = FacesContext.getCurrentInstance();        
        cb = (Checkbox) context.getViewRoot().findComponent("form1:RedCheckbox");                        
        cb.setSubmittedValue(null);

        rb = (RadioButton) context.getViewRoot().findComponent("form1:rbServer");                        
        rb.setSubmittedValue(null);
        rb = (RadioButton) context.getViewRoot().findComponent("form1:rbVolume");                        
        rb.setSubmittedValue(null);
        rb = (RadioButton) context.getViewRoot().findComponent("form1:rbPool");                        
        rb.setSubmittedValue(null);

        rb = (RadioButton) context.getViewRoot().findComponent("form1:rbimServer");                        
        rb.setSubmittedValue(null);
        rb = (RadioButton) context.getViewRoot().findComponent("form1:rbimVolume");                        
        rb.setSubmittedValue(null);
        rb = (RadioButton) context.getViewRoot().findComponent("form1:rbimPool");                        
        rb.setSubmittedValue(null);
        
        reset();

    }
    
    /** Action handler for the test case dropdown menu. */
    public String testCaseActionHandler() {
        // Returning null causes page to re-render.
        return null;
    }
    
    /** Return the enable/disable state of the checkbox. */
    public boolean getRedCBDisabled() {
        return redCBDisabled;
    }
    
    /** Set the enable/disable state of the checkbox. */
    public void setRedCBDisabled(boolean b) {
        redCBDisabled = b;
    }
    
    /** Return the enable/disable state of the server radiobutton. */
    public boolean getServerRBDisabled() {
        return serverRBDisabled;
    }
    
    /** Set the enable/disable state of the server radiobutton. */
    public void setServerRBDisabled(boolean b) {
        serverRBDisabled = b;
    }
    
    /** Return the enable/disable state of the volume radiobutton. */
    public boolean getVolumeRBDisabled() {
        return volumeRBDisabled;
    }
    
    /** Set the enable/disable state of the volume radiobutton. */
    public void setVolumeRBDisabled(boolean b) {
        volumeRBDisabled = b;
    }
    
    /** Return the enable/disable state of the pool radiobutton. */
    public boolean getPoolRBDisabled() {
        return poolRBDisabled;
    }
    
    /** Set the enable/disable state of the pool radiobutton. */
    public void setPoolRBDisabled(boolean b) {
        poolRBDisabled = b;
    }
    
    /** Return the enable/disable state of the server image radiobutton. */
    public boolean getServerImageRBDisabled() {
        return serverImageRBDisabled;
    }
    
    /** Set the enable/disable state of the server image radiobutton. */
    public void setServerImageRBDisabled(boolean b) {
        serverImageRBDisabled = b;
    }
    
    /** Return the enable/disable state of the volume image radiobutton. */
    public boolean getVolumeImageRBDisabled() {
        return volumeImageRBDisabled;
    }
    
    /** Set the enable/disable state of the volume image radiobutton. */
    public void setVolumeImageRBDisabled(boolean b) {
        volumeImageRBDisabled = b;
    }
    
    /** Return the enable/disable state of the pool image radiobutton. */
    public boolean getPoolImageRBDisabled() {
        return poolImageRBDisabled;
    }
    
    /** Set the enable/disable state of the pool radiobutton. */
    public void setPoolImageRBDisabled(boolean b) {
        poolImageRBDisabled = b;
    }
    
    /** Return the selected state of the "red" checkbox */
    public boolean getRedSelected() {
        return redSelected;
    }
    
    /** Set the selected state of the "red" checkbox */
    public void setRedSelected(boolean b) {
        redSelected = b;
    }
    
    /** Return the selected state of the server radiobutton */
    public boolean getServerSelected() {
        return serverSelected;
    }
    
    /** Set the selected state of the server radiobutton */
    public void setServerSelected(boolean b) {
        serverSelected = b;
    }
    
    /** Return the selected state of the volume radiobutton */
    public boolean getVolumeSelected() {
        return volumeSelected;
    }
    
    /** Set the selected state of the volume radiobutton */
    public void setVolumeSelected(boolean b) {
        volumeSelected = b;
    }
    
    /** Return the selected state of the pool radiobutton */
    public boolean getPoolSelected() {
        return poolSelected;
    }
    
    /** Set the selected state of the volume radiobutton */
    public void setPoolSelected(boolean b) {
        poolSelected = b;
    }
    
    /** Return the selected state of the server image radiobutton */
    public boolean getServerImageSelected() {
        return serverImageSelected;
    }
    
    /** Set the selected state of the server image radiobutton */
    public void setServerImageSelected(boolean b) {
        serverImageSelected = b;
    }
    
    /** Return the selected state of the volume image radiobutton */
    public boolean getVolumeImageSelected() {
        return volumeImageSelected;
    }
    
    /** Set the selected state of the volume image radiobutton */
    public void setVolumeImageSelected(boolean b) {
        volumeImageSelected = b;
    }
    
    /** Return the selected state of the pool image radiobutton */
    public boolean getPoolImageSelected() {
        return poolImageSelected;
    }
    
    /** Set the selected state of the volume image radiobutton */
    public void setPoolImageSelected(boolean b) {
        poolImageSelected = b;
    }
    
    // Action handler when navigating to the main example index.
    public String showExampleIndex() {
        reset();
        return IndexBackingBean.INDEX_ACTION;
    }
    
    // Action handler when navigating to the checkbox and radio button example index.
    public String showCbRbIndex() {
        reset();
        return SHOW_CBRB_INDEX;
    }
    
    /** Return the state result for the checkbox */
    public String getCheckboxResult() {
        String args[] = new String[4];
        args[0] = MessageUtil.getMessage("cbrb_checkboxResult");
        args[1] = MessageUtil.getMessage("crcb_redCheckbox");
        args[2] = getRedSelected() ? MessageUtil.getMessage("cbrb_selected") : MessageUtil.getMessage("cbrb_notSelected");
        args[3] = getRedCBDisabled() ? MessageUtil.getMessage("cbrb_disabled") : MessageUtil.getMessage("cbrb_enabled");
        return MessageUtil.getMessage("cbrb_result", args);
    }
    
    /** Return the state result for the radioButton */
    public String getRadioButtonResult() {
        String args[] = new String[4];
        args[0] = MessageUtil.getMessage("cbrb_radioButtonResult");
        if (getServerSelected()) {
            args[1] = MessageUtil.getMessage("cbrb_radioButton1");
            args[3] = getServerRBDisabled() ? MessageUtil.getMessage("cbrb_disabled") : MessageUtil.getMessage("cbrb_enabled");
        }
        if (getVolumeSelected()) {
            args[1] = MessageUtil.getMessage("cbrb_radioButton2");
            args[3] = getVolumeRBDisabled() ? MessageUtil.getMessage("cbrb_disabled") : MessageUtil.getMessage("cbrb_enabled");
        }
        if (getPoolSelected()) {
            args[1] = MessageUtil.getMessage("cbrb_radioButton3");
            args[3] = getPoolRBDisabled() ? MessageUtil.getMessage("cbrb_disabled") : MessageUtil.getMessage("cbrb_enabled");
        }
        args[2] = MessageUtil.getMessage("cbrb_selected");

        return MessageUtil.getMessage("cbrb_result", args);
    }
    
    /** Return the state result for the radioButton image */
    public String getRadioButtonImageResult() {
        String args[] = new String[4];
        args[0] = MessageUtil.getMessage("cbrb_radioButtonImageResult");
        if (getServerImageSelected()) {
            args[1] = MessageUtil.getMessage("cbrb_radioButton1");
            args[3] = getServerImageRBDisabled() ? MessageUtil.getMessage("cbrb_disabled") : MessageUtil.getMessage("cbrb_enabled");
        }
        if (getVolumeImageSelected()) {
            args[1] = MessageUtil.getMessage("cbrb_radioButton2");
            args[3] = getVolumeImageRBDisabled() ? MessageUtil.getMessage("cbrb_disabled") : MessageUtil.getMessage("cbrb_enabled");
        }
        if (getPoolImageSelected()) {
            args[1] = MessageUtil.getMessage("cbrb_radioButton3");
            args[3] = getPoolImageRBDisabled() ? MessageUtil.getMessage("cbrb_disabled") : MessageUtil.getMessage("cbrb_enabled");
        }
        args[2] = MessageUtil.getMessage("cbrb_selected");

        return MessageUtil.getMessage("cbrb_result", args);
    }
}
