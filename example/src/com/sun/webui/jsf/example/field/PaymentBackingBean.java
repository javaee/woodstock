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

package com.sun.webui.jsf.example.field;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;

import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.application.FacesMessage;

import com.sun.webui.jsf.component.TextField;
import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.index.IndexBackingBean;

import java.io.Serializable;

/**
 * Backing bean for Text Input Auto Validation example.
 */
public class PaymentBackingBean implements Serializable { 
    
    // Outcome strings used in the faces config. 
    public static final String SHOW_AUTO_VALIDATE = "showTextInputAutoValidate";

    // Holds the credit card number
    private String cardNumber = null;

    // Holds value of payment amount
    private String amount;
    
    /** Default constructor */
    public PaymentBackingBean() {                   
	_reset();
    }

    /** Get amount. */
    public String getAmount() {
        return amount;
    }

    /** Set amount. */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /** Set the credit card number */
    public void setCardNumber(String cardNumber) {
	this.cardNumber = cardNumber;
    }

    /** Get the credit card number */
    public String getCardNumber() {
	return cardNumber;
    }
            
    /**
     * Card validator.  This will ONLY be called if a value was specified.
     * Valid card numbers can be:
     *    16 digits, for ex. 1234567898765432
     *    19 digits, with each group of 4 digits seperated by either
     *               a hypen or space
     */
    public void cardNumberValidator(FacesContext context,
	    UIComponent component, 
	    Object value) throws ValidatorException {

	if (value == null) {
	    return;
	}

	String cardNumber = (String)value;

	try {
	    // Only 16 or 19 characters allowed
	    if ((cardNumber.length() != 16) && (cardNumber.length() != 19))
		throw new Exception("field_badCardnumberFormat");

	    // Offset between each group of 4 characters is by either 4 (if
	    // 16 characters) or 5 (if 19 characters).
	    int offset = 4;
	    if (cardNumber.length() == 19) {
		offset = 5;

	        // Only allow specific delimiter between each group
	        for (int i = 4; i < cardNumber.length(); i+=offset) {
		    char delimiter = cardNumber.charAt(i);
		    if ((delimiter != ' ') && (delimiter != '-'))
			throw new Exception("field_badCardnumberDelimiter");
	        }
	    }

	    // Ensure card number is all digits by validating each group
	    // of 4 digits.
	    for (int i = 0; i < cardNumber.length(); i+=offset) {
		try {
		    int n = Integer.parseInt(cardNumber.substring(i, i+4));
		} catch (Exception e) {
		    throw new Exception("field_nondigitCardnumber");
		}
	    }
	} catch (Exception ex) {
            FacesMessage message = new FacesMessage();
            message.setDetail(MessageUtil.getMessage(ex.getMessage()));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
	}

    }

    /**
     * Payment amount validator
     */
    public void amountValidator(FacesContext context,
	    UIComponent component, 
	    Object value) throws ValidatorException {

	if (value == null) {
	    return;
	}

	// Get currency pattern and symbol from localized resources,
	// and use them to configure a DecimalFormat.
	//
	String currencyPattern = MessageUtil.getMessage("field_currencyPattern");
	String currencySymbol =  MessageUtil.getMessage("field_currencySymbol");

	DecimalFormat form = new DecimalFormat(currencyPattern);
	DecimalFormatSymbols dfs = form.getDecimalFormatSymbols();
	dfs.setCurrencySymbol(currencySymbol);

	// Try Validating while assuming currency symbol embedded in the value
	ParsePosition p = new ParsePosition(0);
	Number number = form.parse((String)value, p);
	if (number != null)
	    return;

	// Try validating while assuming currency symbol is NOT embedded in value.
	if (((String)value).indexOf(currencySymbol) < 0) {

	    // To do this, we first strip the currency symbol from the currency
	    // pattern prior to parsing the value.
	    int n = 0;
	    while ((n = currencyPattern.indexOf(currencySymbol)) >= 0) {
		String cp = currencyPattern.substring(0, n);
		cp += (currencyPattern.substring(n + currencySymbol.length()));
		currencyPattern = cp;
	    }
	    form.applyPattern(currencyPattern);
	    number = form.parse((String)value, p);
	    if (number != null)
		return;
	}

	// Validate failed
        FacesMessage message = new FacesMessage();
        message.setDetail(MessageUtil.getMessage("field_badAmountFormat"));
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }

    /** Return payment results */
    public String getPaymentSummary() {
        Object[] args = new Object[2];
	args[0] = cardNumber;
	args[1] = amount;
        return MessageUtil.getMessage("field_paymentResult", args);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Action Handlers
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /** Action listener for the Reset button. */
    public void resetListener(ActionEvent ae) {
        // Since the action is immediate, the components won't
        // go through the Update Model phase. However, its submitted value
        // gets set in the Apply Request Value phase and this value is retained
        // when the page is redisplayed. 
        //
        // So, we need to explicitly erase the submitted values and then update
        // the model object with initial values.
        
	FacesContext context = FacesContext.getCurrentInstance();
        TextField tf = (TextField) context.getViewRoot().findComponent(
		"form:contentPageTitle:creditCard");
	if (tf != null) {
            tf.setSubmittedValue("");
	}
        
        tf = (TextField) context.getViewRoot().findComponent(
		"form:contentPageTitle:amount");
	if (tf != null) {
            tf.setSubmittedValue("");
	}
        
	_reset();
    }
    
    /** Action handler for the Reset button. */
    public String reset() {
        // Return null to cause the page to re-render.
        return null;
    }

    /** Action handler when navigating to the main example index. */
    public String showExampleIndex() {        
	_reset();
        return IndexBackingBean.INDEX_ACTION;
    }

    /** Action handler when navigating to the text input example index. */
    public String showTextInputIndex() {
	_reset();
        return TextInputBackingBean.SHOW_TEXTINPUT_INDEX;
    }

    /** Action handlet when navigating to the auto-validation page */
    public String showAutoValidate() {
	return SHOW_AUTO_VALIDATE;
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /** Reset to initial values. */
    private void _reset() {          
	amount = null;
	cardNumber = null;
    }    
}
