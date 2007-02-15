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

package com.sun.webui.jsf.example.alert;

import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.validator.ValidatorException;
import javax.faces.application.FacesMessage;

import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.index.IndexBackingBean;

import java.util.Random;
import java.io.Serializable;

/**
 * Backing bean for Page Alert example.
 */
public class PageAlertBackingBean implements Serializable {
           
    // Outcome strings used in the faces config. 
    private final static String SHOW_PAGEALERT_EXAMPLE  = "showPageAlertExample";
    private final static String SHOW_PAGEALERT          = "showPageAlert";
    private final static String SHOW_ALERT_INDEX        = "showAlertIndex";
    
    // Holds value of property fieldValue. 
    private Integer fieldValue;  
    
    // Holds value of property alertTitle. 
    private String alertTitle = null;
    
    // Holds value of property alertDetail. 
    private String alertDetail = null;
    
    // Holds value of property alertType. 
    private String alertType = null;    
    
    // Random number 
    private int randomNumber = 0; 
    
    // Number of attempts 
    private int count = 0;           
    
    // User guess 
    private int userGuess;    
        
    /** Default constructor */
    public PageAlertBackingBean() {
    }
    
    /** Set the value of property fieldValue. */    
    public void setFieldValue(Integer fieldValue) {
        this.fieldValue = fieldValue;
    }
    
    /** Get the value of property fieldValue. */     
    public Integer getFieldValue() {
        return fieldValue;
    }

    /** Get the value of property alertTitle. */
    public String getAlertTitle() {
        return alertTitle;
    }
        
    /** Get the value of property alertDetail. */
    public String getAlertDetail() {
        return alertDetail;
    }
    
    /** Get the value of property alertType. */
    public String getAlertType() {
        return alertType;
    }        
    
    /** Display the inline alert if there was an error on the page. */
    public boolean getInlineAlertRendered() { 
        FacesMessage.Severity severity = 
                FacesContext.getCurrentInstance().getMaximumSeverity();
        if(severity != null &&                 
                severity.compareTo(FacesMessage.SEVERITY_ERROR) == 0) { 
            return true;
        }
        return false;
    }
    
    /** 
     * Text field validator. This will ONLY be called if a value
     * was specified. 
     */
    public void validateFieldEntry(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {   
        
        int num = ((Integer) value).intValue();       
        
        // Number must be between 1 and 10.
        if (num < 1 || num > 10) {                        
            FacesMessage message = new FacesMessage();
            message.setDetail(MessageUtil.getMessage("alert_sumException"));
	    message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);                                         
        }                
    }    
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Action Handlers
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /** Action handler when navigating to the page alert example. */
     public String showPageAlertExample() {
        return SHOW_PAGEALERT_EXAMPLE;
    }
        
    /** Action handler when navigating to page alert. */
    public String showPageAlert() {        
        return SHOW_PAGEALERT;
    }
    
    /** Action handler when navigating to the main example index. */
    public String showExampleIndex() {        
        fieldValue = null;
        resetValues();
        return IndexBackingBean.INDEX_ACTION;
    }
    
    /** Action handler when navigating to the alert example index. */
    public String showAlertIndex() {
        return SHOW_ALERT_INDEX;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Action Listeners
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        
    /** Action listener for the Enter button. */
    public void processButtonAction(ActionEvent ae) {                                        
            
        // Get the random number
        if (randomNumber == 0) {
            Random r = new Random();
	    randomNumber = r.nextInt(10) + 1;    
        }         

        try {
            // Get the value entered.
            userGuess = fieldValue.intValue();
            count++;

            // Test user guess with random number and display
            // the appropriate message.
            if (userGuess == randomNumber) {		
                setAlertInfo("information", "alert_congratHeader",
                        "alert_numberCongrats", String.valueOf(randomNumber));
                resetValues();
            } else if (count >= 3) {		
                setAlertInfo("information", "alert_gameOverHeader",
                        "alert_numberWrong", String.valueOf(randomNumber));
                resetValues();
            } else if (count == 2) {			                
                if (userGuess < randomNumber) {
                    setAlertInfo("warning", "alert_incorrectNumHeader", 
                            "alert_detHigherLastChance", null);
                } else
                    setAlertInfo("warning", "alert_incorrectNumHeader",
                            "alert_detLowerLastChance", null);
            } else {			                
                if (userGuess < randomNumber) {
                    setAlertInfo("warning", "alert_incorrectNumHeader",
                            "alert_detHigherTryAgain", null);
                } else {
                    setAlertInfo("warning", "alert_incorrectNumHeader",
                            "alert_detLowerTryAgain", null);
		}
            }
        } catch (Exception e) {
            setAlertInfo("error", "alert_incorrectNumHeader",
                    "alert_sumExceptionWithHelp", null);		
        }  
        
        // Reset the field value so that the old guess doesn't
        // remain in the field.
        fieldValue = null;  
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /** 
     * Set the alert type, title and detail message.
     *
     * @param type The alert type.     
     * @param summary The key for the alert title.
     * @param detail The alert detail message key.
     * @param detailArg The alert detail message arguments.    
     */
    private void setAlertInfo(String type, String title, 
	    String detail, String detailArg) {                
        
        alertType = type;
        alertTitle = MessageUtil.getMessage(title);
        
        String[] args = {detailArg};
        if (detailArg != null) {            
            alertDetail = MessageUtil.getMessage(detail, args);
        } else {
            alertDetail = MessageUtil.getMessage(detail);    
        }                        
    }          
    
    /** Reset to the initial values. */
    private void resetValues() {  
       	randomNumber = 0;
	count = 0;                      
    }            
}
