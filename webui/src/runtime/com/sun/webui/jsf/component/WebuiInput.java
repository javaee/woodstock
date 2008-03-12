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

package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.event.MethodExprValueChangeListener;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.validator.MethodExprValidator;

import java.util.List;

import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Base class for components which need to extend UIInput.
 */
public class WebuiInput extends UIInput {
    /**
     * Specifies a method to translate native
     * property values to String and back for this component. The converter 
     * attribute value must be one of the following:
     * <ul>
     * <li>A JavaServer Faces EL expression that resolves to a backing bean or
     * bean property that implements the 
     * <code>javax.faces.converter.Converter</code> interface; or
     * </li><li>the ID of a registered converter (a String).</li>
     * </ul>
     */
    @Property(name="converter") 
    public void setConverter(Converter converter) {
        super.setConverter(converter);
    }

    /**
     * The component identifier for this component. This value must be unique 
     * within the closest parent component that is a naming container.
     */
    @Property(name="id") 
    public void setId(String id) {
        super.setId(id);
    }

    /**
     * Flag indicating that event handling for this component should be handled
     * immediately (in Apply Request Values phase) rather than waiting until 
     * Invoke Application phase.
     */
    @Property(name="immediate") 
    public void setImmediate(boolean immediate) {
        super.setImmediate(immediate);
    }

    /**
     * Indicates whether the HTML code for the
     * component should be included in the rendered HTML page. If set to false,
     * the rendered HTML page does not include the HTML for the component. If
     * the component is not rendered, it is also not processed on any subsequent
     * form submission.
     */
    @Property(name="rendered") 
    public void setRendered(boolean rendered) {
        super.setRendered(rendered);
    }

    /**
     * Flag indicating that an input value for this field is mandatory, and 
     * failure to provide one will trigger a validation error.
     */
    @Property(name="required") 
    public void setRequired(boolean required) {
        super.setRequired(required);
    }

    /**
     * {@inheritDoc}
     **/
    //Override to annotate
    @Property(isHidden=true, isAttribute=false)
    public MethodBinding getValidator() {
        return super.getValidator();
    }
    
    /**
     * {@inheritDoc}
     **/
    //Override to annotate
    @Property(isHidden=true, isAttribute=false)
    public MethodBinding getValueChangeListener() {
        return super.getValueChangeListener();
    }

    /**
     * Specifies a method to handle a value-change event that is triggered
     * when the user enters data in the input component. The  
     * attribute value must be a JavaServer Faces EL expression that
     * resolves to a backing bean method. The method must take a single
     * parameter of type <code>javax.faces.event.ValueChangeEvent</code>,
     * and its return type must be void. The backing bean where the
     * method is defined must implement <code>java.io.Serializable</code>
     * or <code>javax.faces.component.StateHolder</code>.
     */
    @Property(name="valueChangeListenerExpression", isHidden=true, displayName="Value Change Listener Expression", category="Advanced", editorClassName="com.sun.rave.propertyeditors.MethodBindingPropertyEditor")
    @Property.Method(event="valueChange")
    private MethodExpression valueChangeListenerExpression;
    
    /**
     * <p>Get the <code>valueChangeListenerExpression</code>. 
     * The corresponding listener will be called from the 
     * <code>broadcast</code> method.</p>
     */
    public MethodExpression getValueChangeListenerExpression() {
       return this.valueChangeListenerExpression;
    }
    
    /** 
     * <p>Set the <code>valueChangeListenerExpression</code>.
     * The corresponding listener will be called from the 
     * <code>broadcast</code> method.</p>
     */
    public void setValueChangeListenerExpression(MethodExpression me) {
        this.valueChangeListenerExpression = me;
    }

    /**
     * Used to specify a method in a backing bean to validate input
     * to the component. The value must be a JavaServer Faces
     * EL expression that resolves to a public method with
     * return type void. The method must take three parameters:
     * <ul>
     * <li>a <code>javax.faces.context.FacesContext</code></li>
     * <li>a <code>javax.faces.component.UIComponent</code> (the component whose
     * data is to be validated)</li>
     * <li>a <code>java.lang.Object</code> containing the data to be validated.
     * </li>
     * </ul> 
     * <p>The backing bean where the method is defined must implement 
     * <code>java.io.Serializable</code> or
     * <code>javax.faces.component.StateHolder</code>.</p>
     * <p>The method is invoked during the Process Validations Phase.</p> 
     */
    @Property(name="validatorExpression", displayName="Validator Expression", category="Data", editorClassName="com.sun.rave.propertyeditors.ValidatorPropertyEditor")
    @Property.Method(event="validate")
    private MethodExpression validatorExpression;
    
    /**
     * <p>Get the <code>validatorExpression</code>.
     * The corresponding validator will be called from the
     * <code>validateValue</code> method.</p>
     *
     */
    public MethodExpression getValidatorExpression() {
       return this.validatorExpression;
    }
    
    /**
     * <p>Set the <code>validatorExpression</code>.
     * The corresponding validator will be called from the
     * <code>validateValue</code> method.</p>
     *
     */
    public void setValidatorExpression(MethodExpression me) {
        this.validatorExpression = me;
    }
    
    /**
     * {@inheritDoc}
     **/
    public void broadcast(FacesEvent event) throws AbortProcessingException {
        // Perform standard superclass processing
        super.broadcast(event);

        if (event instanceof ValueChangeEvent) {
            MethodExpression vclExpression = getValueChangeListenerExpression();
            if (vclExpression != null) {
                ValueChangeListener vcl = new MethodExprValueChangeListener(vclExpression);
                //just to be sure, use the semantics of the inherited broadcast method
                if (event.isAppropriateListener(vcl)) {
                    event.processListener(vcl);
                }
            }
        }
    }
    
    /**
     * {@inheritDoc}
     **/
    protected void validateValue(FacesContext context, Object newValue) {
        // Perform standard superclass processing
        super.validateValue(context, newValue);
        
	// If our value is valid and not empty, try to call validatorExpression
	if (isValid() && !isEmpty(newValue)) {
            MethodExpression vExpression = getValidatorExpression();
	    if (vExpression != null) {
                Validator validator = new MethodExprValidator(vExpression);
                try { 
                    validator.validate(context, this, newValue);
                }
                catch (ValidatorException ve) {
                    // If the validator throws an exception, we're
                    // invalid, and we need to add a message
                    setValid(false);
                    FacesMessage message = null;
                    String validatorMessageString = getValidatorMessage();

                    if (null != validatorMessageString) {
                        message = new FacesMessage(validatorMessageString, 
                                validatorMessageString);
                    }
                    else {
                        message = ve.getFacesMessage();
                    }
                    if (message != null) {
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        context.addMessage(getClientId(context), message);
                    }
                }
	    }
	}
    }
    
    /**
     * <p>Emulate the corresponding private method in <code>UIInput</code>.
     * Use protected access since this method is called by
     * <code>validateValue</code>, which has protected access.</p>
     */
    protected boolean isEmpty(Object value) {

	if (value == null) {
	    return (true);
	} else if ((value instanceof String) &&
		   (((String) value).length() < 1)) {
	    return (true);
	} else if (value.getClass().isArray()) {
	    if (0 == java.lang.reflect.Array.getLength(value)) {
		return (true);
	    }
	}
	else if (value instanceof List) {
	    if (0 == ((List) value).size()) {
		return (true);
	    }
	}
	return (false);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Lifecycle methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * <p>Specialized decode behavior on top of that provided by the
     * superclass.
     *
     * <ul>
     *  <li>This method will skip decoding for Ajax requests of type "refresh".</li>
     * </ul>
     *
     * @param context <code>FacesContext</code> for this request.
     */
    public void processDecodes(FacesContext context) {
        if (context == null) {
            return;
        }
        // Skip processing in case of "refresh" ajax request.
        if ( ComponentUtilities.isAjaxRequest(getFacesContext(), this, "refresh")
                && !ComponentUtilities.isAjaxExecuteRequest(getFacesContext(), this)) {
            return;
        }
        super.processDecodes(context);
    }

    /**
     * <p>Specialized validation behavior on top of that provided by the
     * superclass.
     *
     * <ul>
     *  <li>This method will skip decoding for Ajax requests of type "refresh".</li>
     * </ul>
     *
     * @param context <code>FacesContext</code> for this request.
     */
    public void processValidators(FacesContext context) {
        if (context == null) {
            return;
        }
        if ( ComponentUtilities.isAjaxRequest(getFacesContext(), this, "autocomplete"))   {
            return; // Skip processing for ajax based autocomplete events.
        }
        // Skip procesing in case of "refresh" ajax request.
        if ( ComponentUtilities.isAjaxRequest(getFacesContext(), this, "refresh") 
                && !ComponentUtilities.isAjaxExecuteRequest(getFacesContext(), this)) {
            return; // Skip processing for ajax based validation events.
        }
        super.processValidators(context);
    }
    
    /**
     * <p>Specialized model update behavior on top of that provided by the
     * superclass.
     *
     * <ul>
     *  <li>This method will skip decoding for Ajax requests of type "refresh".</li>
     * </ul>
     *
     * @param context <code>FacesContext</code> for this request.
     */
    public void processUpdates(FacesContext context) {
        if (context == null) {
            return;
        }
        // Skip processing in case of "refresh" ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this, "refresh")
                && !ComponentUtilities.isAjaxExecuteRequest(getFacesContext(), this)) {
            return;
        }
        super.processUpdates(context);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // State methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * {@inheritDoc}
     **/
    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;

        super.restoreState(context, values[0]);
        valueChangeListenerExpression =(MethodExpression) restoreAttachedState(context, values[1]);
        validatorExpression = (MethodExpression)restoreAttachedState(context, values[2]);
        
    }
    
    /**
     * {@inheritDoc}
     **/
    public Object saveState(FacesContext context) {
        Object values[] = new Object[3];
        values[0] = super.saveState(context);
        values[1] = saveAttachedState(context, valueChangeListenerExpression);
        values[2] = saveAttachedState(context, validatorExpression);
        
        return values;
    }
}
