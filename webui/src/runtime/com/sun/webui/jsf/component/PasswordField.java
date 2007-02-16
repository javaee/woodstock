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
@Component(type="com.sun.webui.jsf.PasswordField", family="com.sun.webui.jsf.PasswordField", displayName="Password Field", instanceName="passwordField", tagName="passwordField",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_password_field",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_password_field_props")
public class PasswordField extends Field {
    /**
     * Default constructor.
     */
    public PasswordField() {
        super();
        setRendererType("com.sun.webui.jsf.PasswordField");
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
     * Flag indicating that an input value for this field is mandatory, and 
     * failure to provide one will trigger a validation error.
     */
    @Property(name="required") 
    public void setRequired(boolean required) {
        super.setRequired(required);
    }

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
    @Property(name="text", isHidden=true, isAttribute=false)
    public Object getText() {
        return super.getText();
    }
    
    // Hide value
    @Property(name="value", isHidden=true, isAttribute=false)
    public Object getValue() {
        return super.getValue();
    }

    /**
     * <p>Literal value to be rendered in this input field.
     * If this property is specified by a value binding
     * expression, the corresponding value will be updated
     * if validation succeeds.</p>
     */
    @Property(name="password", displayName="Password", category="Appearance", editorClassName="com.sun.rave.propertyeditors.StringPropertyEditor")
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
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[1];
        _values[0] = super.saveState(_context);
        return _values;
    }
}
