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

package com.sun.webui.jsf.validator;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List; 
import javax.faces.application.FacesMessage;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import com.sun.webui.jsf.component.ListSelector;
import com.sun.webui.jsf.model.list.ListItem;
import com.sun.webui.theme.Theme; 
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 *  <p>	Use this validator to check the number of characters in a string when
 *	you need to set the validation messages.</p>
 *
 * @author avk
 */
public class ValueMatchesOptionsValidator implements Validator, Serializable {
    
    /**
     * <p>The converter id for this converter.</p>
     */
    public static final String VALIDATOR_ID = "com.sun.webui.jsf.ValueMatchesOptions";
    /**
     * Error message used if the value is not in the option. 
     */
    private String message = null;
    
    private static final boolean DEBUG = false;
    
    /** Creates a new instance of StringLengthValidator */
    public ValueMatchesOptionsValidator() {
    }

    /**
     *	<p> Validate the value with regard to a <code>UIComponent</code> and a
     *	    <code>FacesContext</code>.</p>
     *
     *	@param	context	    The FacesContext
     *	@param	component   The component to be validated
     *	@param	value	    The submitted value of the component
     *
     * @exception ValidatorException if the value is not valid
     */
    public void validate(FacesContext context,
            UIComponent  component,
            Object value) throws ValidatorException {
        
        if(DEBUG) log("validate()");
        
        if((context == null) || (component == null)) {
            String message = "Context or component is null";
            if(DEBUG) log("\t" + message);
            throw new NullPointerException(message);
        }
        
        if(!(component instanceof ListSelector)) {
            String message = this.getClass().getName() +
                    " can only be used with components which subclass " +
                    ListSelector.class.getName();
            if(DEBUG) log("\t" + message);
            throw new RuntimeException(message);
        }
        
        ListSelector list = (ListSelector)component;
        Object valuesAsArray = null;
        
        if(value instanceof List) {
            if(DEBUG) log("\tValue is list");
            valuesAsArray = ((List)value).toArray();
        } else if(value.getClass().isArray()) {
            if(DEBUG) log("\tValue is array");
            valuesAsArray = value;
        } else {
            if(DEBUG) log("\tValue is object");
            valuesAsArray = new Object[]{ value };
        }
        
        int numValues = Array.getLength(valuesAsArray);
        if( numValues == 0) {
            if(DEBUG) log("\tArray is empty - values are OK");
            return;
        }
        
        Object currentValue = null;
        Iterator itemsIterator = null;
        ListItem listItem = null;
        Object listObject = null;
        boolean foundValue = false;
        boolean error = false;
        
        for(int counter=0; counter< numValues; ++counter) {
            currentValue = Array.get(valuesAsArray, counter);
            itemsIterator = list.getListItems();
            foundValue = false;
            
            if(DEBUG) log("\tChecking: " + String.valueOf(currentValue));
            while(itemsIterator.hasNext()) {
                listObject = itemsIterator.next();
                if(!(listObject instanceof ListItem)) {
                    continue;
                }
                listItem = (ListItem)listObject;
                if(DEBUG) log("ListItem is " + listItem.getLabel());
                if(currentValue.equals(listItem.getValueObject())) {
                    if(DEBUG) log("Found match");
                    foundValue = true;
                    break;
                }
            }
            if(!foundValue) {
                if(DEBUG) log("No match found");
                error = true;
                break;
            }
        }
        
        if(error) {
            if(message == null) {
                Theme theme = ThemeUtilities.getTheme(context);
                message = ThemeUtilities.getTheme(context).getMessage
                        ("ListSelector.badValue");
            }
            throw new ValidatorException(new FacesMessage(message));
        }
    }

    private void log(String s) { 
        System.out.println(this.getClass().getName() + "::" + s); //NOI18N
    }

  
    /**
     * Getter for property message.
     * @return Value of property message.
     */
    public String getMessage() {

        return this.message;
    }

    /**
     * Setter for property message.
     * @param message New value of property message.
     */
    public void setMessage(String message) {

        this.message = message;
    } 
}
