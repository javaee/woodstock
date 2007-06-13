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
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 * The HiddenField component is used to create a hidden input field.
 */
@Component(type="com.sun.webui.jsf.HiddenField", family="com.sun.webui.jsf.HiddenField", displayName="Hidden Field", tagName="hiddenField",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_hidden_field",
    tagRendererType="com.sun.webui.jsf.widget.HiddenField",    
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_hidden_field_props")
public class HiddenField extends WebuiInput {
    
    private final static boolean DEBUG = false;
    
    /** Creates a new instance of HiddenField */
    public HiddenField() {
        super();
        setRendererType("com.sun.webui.jsf.widget.HiddenField");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.HiddenField";
    }

    public String getRendererType() {
        // Ensure we have a valid Ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.HiddenField";
        }
        return super.getRendererType();
    }

    /**
     * <p>Return the value to be rendered as a string when the
     * component is readOnly. The default behaviour is to
     * invoke getValueAsString(). Override this method in case
     * a component needs specialized behaviour.</p>
     * @param context FacesContext for the current request
     * @return A String value of the component
     */
    public String getReadOnlyValueString(FacesContext context) {
        return getValueAsString(context);
    }

     /**
     * <p>Return the value to be rendered, as a String (converted
     * if necessary), or <code>null</code> if the value is null.</p>
     * @param context FacesContext for the current request
     * @return A String value of the component
     */
    public String getValueAsString(FacesContext context) { 

        if(DEBUG) log("getValueAsString()");
        
	// This is done in case the RENDER_RESPONSE is occuring
	// prematurely due to some error or an immediate condition
	// on a button. submittedValue is set to null when the
	// component has been validated. 
	// If the component has not passed through the PROCESS_VALIDATORS
	// phase then submittedValue will be non null if a value
	// was submitted for this component.
	// 
        Object submittedValue = getSubmittedValue();
        if (submittedValue != null) {
            if(DEBUG) { 
                log("Submitted value is not null " + //NOI18N
                    submittedValue.toString());
            }
            return (String) submittedValue;
        }
              
        String value = ConversionUtilities.convertValueToString(this, getText());
        if(value == null) {
            value = new String();
        }
        if(DEBUG) log("Component value is " + value); 
        return value;
    } 

    /**
     * Return the converted value of newValue.
     * If newValue is null, return null.
     * If newValue is "", check the rendered value. If the
     * the value that was rendered was null, return null
     * else continue to convert.
     */
    protected Object getConvertedValue(FacesContext context, 
                                       Object newValue) 
        throws javax.faces.convert.ConverterException {

        if(DEBUG) log("getConvertedValue()");
            
        Object value = ConversionUtilities.convertRenderedValue(context, 
                                                                newValue, this);
        
        if(DEBUG) log("\tComponent is valid " + String.valueOf(isValid())); 
        if(DEBUG) log("\tValue is " + String.valueOf(value)); 
        return value;
    }
    
    protected void log(String s) { 
        System.out.println(this.getClass().getName() + "::" + s); 
    }

    /**
     * <p>Return the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property aliases.</p>
     *
     * @param name Name of value binding expression to retrieve
     */
    public ValueExpression getValueExpression(String name) {
        if (name.equals("text")) {
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
        if (name.equals("text")) {
            super.setValueExpression("value", binding);
            return;
        }
        super.setValueExpression(name, binding);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Alternative HTML template to be used by this component.
     */
    @Property(name="htmlTemplate", isHidden=true, isAttribute=true, displayName="HTML Template", category="Appearance")
    private String htmlTemplate = null;

    /**
     * Get alternative HTML template to be used by this component.
     */

    public String getHtmlTemplate() {
        if (this.htmlTemplate != null) {
            return this.htmlTemplate;
        }
        ValueExpression _vb = getValueExpression("htmlTemplate");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Set alternative HTML template to be used by this component.
     */

    public void setHtmlTemplate(String htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }
   
    // Hide required
    @Property(name="required", isHidden=true, isAttribute=false)
    public boolean isRequired() {
        return super.isRequired();
    }
    
    // Hide value as property
    @Property(name="value", isHidden=true, isAttribute=false)
    public Object getValue() {
        return super.getValue();
    }

    /**
     * <p>Flag indicating that the hidden field should not send its value to the
     * server.</p>
     */
    @Property(name="disabled", displayName="Disabled", category="Behavior")
    private boolean disabled = false;
    private boolean disabled_set = false;

    public boolean isDisabled() {
        if (this.disabled_set) {
            return this.disabled;
        }
        ValueExpression _vb = getValueExpression("disabled");
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
     * <p>Flag indicating that the hidden field should not send its value to the
     * server.</p>
     * @see #isDisabled()
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
        this.disabled_set = true;
    }

    /**
     * <p>Literal value to be rendered in this hidden field.
     * If this property is specified by a value binding
     * expression, the corresponding value will be updated
     * if validation succeeds.</p>
     */
    @Property(name="text", displayName="Text", category="Data", isDefault=true,
        editorClassName="com.sun.rave.propertyeditors.StringPropertyEditor")
    public Object getText() {
        return getValue();
    }

    /**
     * <p>Literal value to be rendered in this hidden field.
     * If this property is specified by a value binding
     * expression, the corresponding value will be updated
     * if validation succeeds.</p>
     * @see #getText()
     */
    public void setText(Object text) {
        setValue(text);
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.disabled = ((Boolean) _values[1]).booleanValue();
        this.disabled_set = ((Boolean) _values[2]).booleanValue();
        this.htmlTemplate = (String) _values[3];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[4];
        _values[0] = super.saveState(_context);
        _values[1] = this.disabled ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.disabled_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.htmlTemplate;
        return _values;
    }
}
