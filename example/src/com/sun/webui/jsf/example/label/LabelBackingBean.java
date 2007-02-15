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
 * LabelBackingBean.java
 *
 * Created on January 24, 2006, 1:09 PM
 */

package com.sun.webui.jsf.example.label;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.validator.ValidatorException;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ActionEvent;
        
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.component.Checkbox;
import com.sun.webui.jsf.component.TextField;
import com.sun.webui.jsf.component.TextArea;
import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.index.IndexBackingBean;

/**
 * Backing bean for the Label example.
 */
public class LabelBackingBean implements Serializable {
    
    private String address = "";
    private String phone = "";
    private boolean oliveSelected = false;
    private boolean mushroomSelected = false;
    private boolean pepperoniSelected = false;
    private boolean sausageSelected = false;
    private boolean anchovieSelected = false;
    private boolean anchoviesAvailable = true;
    
    // By default, errors are rendered using an inline alert as per
    // SWAED guidelines.  But this limits you to a generic message
    // when there are multiple errors on the page.  Setting this to
    // false shows all the messages listed in a messageGroup.
    private static final boolean SHOW_ERRORS_IN_ALERT = true;
    
    /** Creates a new instance of LabelBackingBean */
    public LabelBackingBean() {
    }
    
    /** Phone number validator.  This will ONLY be called if a value was specified. */
    public void phoneValidator(FacesContext context, UIComponent component, Object value) {
        // For simplicity's sake, we accept any positive integer < 1000
        try {
            int n = Integer.parseInt(value.toString());
            if ((n <= 0) || (n >= 1000))
                throw new Exception("");
        } catch (Exception e) {
            throw new ValidatorException(
                new FacesMessage(MessageUtil.getMessage(
                    "label_invalidPhone")));
        }
    }
    
    /** Address validator.   This will ONLY be called if a value was specified */
    public void addressValidator(FacesContext context, UIComponent component, Object value) {
        // For simplicity's sake, we accept any string but "XXX" as a valid address.
        if (value.toString().equals("XXX")) {
            throw new ValidatorException(
                new FacesMessage(MessageUtil.getMessage(
                    "label_invalidAddress", "XXX")));            
        }
    }

    /** Olive topping validator */
    public void oliveValidator(FacesContext context, UIComponent component, Object value) {
    }
    
    /** Mushroom topping validator */
    public void mushroomValidator(FacesContext context, UIComponent component, Object value) {
    }
    
    /** Pepperoni topping validator */
    public void pepperoniValidator(FacesContext context, UIComponent component, Object value) {
    }
    
    /** Sausage topping validator */
    public void sausageValidator(FacesContext context, UIComponent component, Object value) {
    }
    
    /** Anchovie topping validator */
    public void anchovieValidator(FacesContext context, UIComponent component, Object value) {
        if (!(value instanceof Boolean) || (value == Boolean.FALSE)) {
            return;
        }
        
        // OOPS!  We are out of anchovies.
        anchoviesAvailable = false;
        throw new ValidatorException(
            new FacesMessage(MessageUtil.getMessage("label_noAnchovies")));                                
    }    
    
    /** Get the phone number property */
    public String getPhone() {
        return phone;
    }
    
    /** Set the phone number property */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    /** Get the address property */
    public String getAddress() {
        return address;
    }
    
    /** Set the address property */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /** Get the olive topping selection */
    public boolean getOliveSelected() {
        return oliveSelected;
    }
    
    /** Set the olive topping selection */
    public void setOliveSelected(boolean b) {
        oliveSelected = b;
    }

    /** Get the mushroom topping selection */
    public boolean getMushroomSelected() {
        return mushroomSelected;
    }
    
    /** Set the mushroom topping selection */
    public void setMushroomSelected(boolean b) {
        mushroomSelected = b;
    }

    /** Get the pepperoni topping selection */
    public boolean getPepperoniSelected() {
        return pepperoniSelected;
    }    
    
    /** Set the pepperoni topping selection */
    public void setPepperoniSelected(boolean b) {
        pepperoniSelected = b;
    }

    /** Get the sausage topping selection */
    public boolean getSausageSelected() {
        return sausageSelected;
    }    
    
    /** Set the sausage topping selection */
    public void setSausageSelected(boolean b) {
        sausageSelected = b;
    }

    /** Get the anchovie topping selection */
    public boolean getAnchovieSelected() {
        return anchovieSelected;
    }    
    
    /** Set the anchovie topping selection */
    public void setAnchovieSelected(boolean b) {
        anchovieSelected = b;
    }
    
    /** Get the label for the anchovie selection */
    public String getAnchovieLabel() {
        if (anchoviesAvailable) {
            return MessageUtil.getMessage("label_anchovies");
        } else {
            return MessageUtil.getMessage("label_noAnchovies");
        }
    }
    
    /** Action handler for the Place Order button. */
    public String placeOrder() {
        return "showLabelResults";
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
        FacesContext context = FacesContext.getCurrentInstance();        
        cb = (Checkbox) context.getViewRoot().findComponent("form1:oliveTopping");
        cb.setSubmittedValue(null);
	cb.setValue(null);
        cb = (Checkbox) context.getViewRoot().findComponent("form1:mushroomTopping");
        cb.setSubmittedValue(null);
	cb.setValue(null);
        cb = (Checkbox) context.getViewRoot().findComponent("form1:pepperoniTopping");
        cb.setSubmittedValue(null);
	cb.setValue(null);
        cb = (Checkbox) context.getViewRoot().findComponent("form1:sausageTopping");
        cb.setSubmittedValue(null);
	cb.setValue(null);
        cb = (Checkbox) context.getViewRoot().findComponent("form1:anchovieTopping");
        cb.setSubmittedValue(null);
	cb.setValue(null);
        
        TextField tf = (TextField) context.getViewRoot().findComponent("form1:phoneNum");
        tf.setSubmittedValue("");
        TextArea ta = (TextArea) context.getViewRoot().findComponent("form1:address");
        ta.setSubmittedValue("");
        
        reset();
    }
    
    /** Action handler when navigating to the main example index. */
    public String showExampleIndex() {
        reset();
        return IndexBackingBean.INDEX_ACTION;
    }
    
    /** Action handler when navigating back to the Label example from results page. */
    public String showLabel() {
        reset();
        return "showLabel";
    }
    
    
    // Reset model properties for next order.
    private void reset() {
        setAddress("");
        setPhone("");
        setOliveSelected(false);
        setMushroomSelected(false);
        setPepperoniSelected(false);
        setSausageSelected(false);
        setAnchovieSelected(false);
        
        // Assume we just got a delivery!
        anchoviesAvailable = true;
    }

    /** Render the message group is there is any error on the page */
    public boolean getMessageGroupRendered() { 
        // Never render if showing error in an inline alert.
        if (SHOW_ERRORS_IN_ALERT)
            return false;
        
        FacesMessage.Severity severity = 
            FacesContext.getCurrentInstance().getMaximumSeverity();
        if (severity == null) { 
            return false; 
        }
        if (severity.compareTo(FacesMessage.SEVERITY_ERROR) == 0) { 
            return true; 
        } 
        return false; 
    }

    /** Render the alert is there is any error on the page */
    public boolean getAlertRendered() { 
        // Never render if showing errors in a message group.
        if (!SHOW_ERRORS_IN_ALERT)
            return false;
        
        FacesMessage.Severity severity = 
            FacesContext.getCurrentInstance().getMaximumSeverity();
        if (severity == null) { 
            return false; 
        }
        if (severity.compareTo(FacesMessage.SEVERITY_ERROR) == 0) { 
            return true; 
        } 
        return false; 
    }
    
    /** Return summary of pizza order */
    public String getPizza() {
        String result = "";
        if (!getOliveSelected() && !getMushroomSelected() && !getPepperoniSelected()
            && !getSausageSelected())
            result = MessageUtil.getMessage("label_plainPizza");
        else {
            result = MessageUtil.getMessage("label_toppingsPizza");
            if (getOliveSelected())
                result += " " + MessageUtil.getMessage("label_oliveResult");
            if (getMushroomSelected())
                result += " " + MessageUtil.getMessage("label_mushroomResult");
            if (getPepperoniSelected())
                result += " " + MessageUtil.getMessage("label_pepperoniResult");
            if (getSausageSelected())
                result += " " + MessageUtil.getMessage("label_sausageResult");
        }
        return result;
    }
    
    /** Return summary of delivery location */
    public String getWhere() {
        String args[] = new String[2];
        args[0] = getAddress();
        args[1] = getPhone();
        return MessageUtil.getMessage("label_whereResult", args);
    }
}
