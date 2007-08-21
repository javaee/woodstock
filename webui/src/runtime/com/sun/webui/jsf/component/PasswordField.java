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

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.util.ConversionUtilities;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 * The PasswordField component is used to create a password textfield.
 */
@Component(
    type="com.sun.webui.jsf.PasswordField", 
    family="com.sun.webui.jsf.PasswordField", displayName="Password Field", 
    instanceName="passwordField", tagName="passwordField",
    tagRendererType="com.sun.webui.jsf.widget.PasswordField",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_password_field",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_password_field_props")
    
public class PasswordField extends Field {
    /**
     * Default constructor.
     */
    public PasswordField() {
        super();
        setRendererType("com.sun.webui.jsf.widget.PasswordField");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.PasswordField";
    }

   /**
     * <p>Return the value to be rendered as a string when the
     * component is readOnly. The value will be 
    * represented using asterisks.</p>
     * @param context FacesContext for the current request
     * @return A String value of the component
     */
    public String getReadOnlyValueString(FacesContext context) {
        
        String value = ConversionUtilities.convertValueToString(this, getValue());
        if(value == null) {
            return new String();
        }
        char[] chars = value.toCharArray();
        for(int i=0; i< chars.length; ++i) {
            chars[i] = '*';
        }
        return new String(chars);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    /**
     * <p>Return the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property aliases.</p>
     *
     * @param name Name of value binding expression to retrieve
     */
    public ValueExpression getValueExpression(String name) {
        if (name.equals("password")) {
            return super.getValueExpression("value");
        }
        return super.getValueExpression(name);
    }

    /**
     * <p>Set the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property
     * aliases.</p>
     *
     * @param name    Name of value binding to set
     * @param binding ValueExpression to set, or null to remove
     */
    public void setValueExpression(String name,ValueExpression binding) {
        if (name.equals("password")) {
            super.setValueExpression("value", binding);
            return;
        }
        super.setValueExpression(name, binding);
    }
    
    // Hide text
    @Property(name="text", isHidden=true, isAttribute=false, editorClassName="com.sun.rave.propertyeditors.StringPropertyEditor")
    public Object getText() {
        return super.getText();
    }
    
    // Hide value
    @Property(name="value", isHidden=true, isAttribute=false)
    public Object getValue() {
        return super.getValue();
    }

    /**
     * <p>Value binding. While no password data will be rendered on the client 
     * side, if this property is specified by a value binding
     * expression, the corresponding value will be updated
     * if validation succeeds.</p>
     */
    @Property(name="password", displayName="Password", category="Appearance",
    editorClassName="com.sun.rave.propertyeditors.StringPropertyEditor") 
    public Object getPassword() {
        return getValue();
    }

    /**
     * <p>Literal value to be rendered in this input field.
     * If this property is specified by a value binding
     * expression, the corresponding value will be updated
     * if validation succeeds.</p>
     * @see #getPassword()
     */
    public void setPassword(Object password) {
        setValue(password);
    }

    /**
     * <p>Flag indicating whether pressing enter in this text field would allow
     * browser to submit the enclosing form ( for all input fields with the exception of TextArea
     * which uses enter key to open a new line) <br>     
     * If set to false, the browser will be prevented from submitting the form on enter in all circumstances.
     * If set to true, the form will be submitted on enter in all circumstances.
     * The default value for this attribute is "false", i.e.
     * default browser auto submit feature will be disabled. 
     * 
     * </p>
     */
    @Property(name="submitForm", isHidden=true, displayName="Submit Form", category="Behavior")
    protected boolean submitForm = false;
    protected boolean submitForm_set = false;
    
    /**
     * <p>Flag indicating whether pressing enter in this text field would allow
     * browser to submit the enclosing form.</p>
     */
    public boolean isSubmitForm() {
        if (this.submitForm_set) {
            return this.submitForm;
        }
        ValueExpression _vb = getValueExpression("submitForm");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return false;
    }
    
    /**
     * <p>Flag indicating whether pressing enter in this text field would allow
     * browser to submit the enclosing form.</p>
     * @see #isSubmitForm()
     */
    public void setSubmitForm(boolean submitForm) {
        this.submitForm = submitForm;
        this.submitForm_set = true;
    }
        
   /**
     * <p>Returns true if submitForm attribute has been explicitely set,
     * either through binding, value expression, or programmatically.
     * Returns false otherwise.</p>
     */
    public boolean isSubmitFormSet() {
        if (this.submitForm_set) {
            return true;
        }
        ValueExpression _vb = getValueExpression("submitForm");
        if (_vb != null && _vb.getValue(getFacesContext().getELContext()) != null)
            return true;
        
        return false;
        
    }        
    
 // --------------------------

    /**
     * <p>Return the empty Srting to be rendered
     * This is done in order to avoid
     * sending secret password back to the client where it can be sniffed by viewing the
     * source code of the page. 
     * <br>Sending masked string
     * such as set of asterisks would have confused the issue further as it could create an impression that
     * password is saved on the client in the field in some meaningful state, which in reality it will be not.
     * Thus, the password field will be rendered empty after each page submit,
     * prompting user ( or browser if such functionality is enabled ) to reenter password</p>
     * @param context FacesContext for the current request
     * @return A String value of the component
     */
    public String getValueAsString(FacesContext context) {
            return new String();       
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // State methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Restore the state of this component.
     */
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.submitForm = ((Boolean) _values[1]).booleanValue();
        this.submitForm_set = ((Boolean) _values[2]).booleanValue();
 }

    /**
     * Save the state of this component.
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[3];
        _values[0] = super.saveState(_context);
        _values[1] = this.submitForm ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.submitForm_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }    
    
}
