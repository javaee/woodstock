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

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 * The EditableField component renders input HTML element that is initially
 * rendered as readOnly field and becomes editable on certain client events
 * ( dblclick, space key)
 * <br>
 * EditableField Component class represents text input element, and it reuses
 * much of the TextField component functionality, as well as its renderers.
 */
@Component(type="com.sun.webui.jsf.EditableField",
    family="com.sun.webui.jsf.EditableField",
    displayName="Editable Field",
    instanceName="editableField", tagName="editableField",
    tagRendererType="com.sun.webui.jsf.widget.EditableField",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_text_field",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_text_field_props")
public class EditableField extends TextField {    
    /**
     * <p>Construct a new <code>EditableField</code>.</p>
     */
    public EditableField() {
        super();
        setRendererType("com.sun.webui.jsf.widget.EditableField");
    }
    
    /**
     * <p>Return the family for this component.</p>
     * Currently, due to unresolved one-to-one mapping between component
     * family and renderers, in order to use jsfx.TextFieldRenderer
     * this method will substitute the family of the component on the fly.
     * This may change in future when TextField and EditableField both can be made
     * to belong to the same family of components.
     */
    public String getFamily() {
        // if we have an Ajax request, use textField renderer
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.TextField";
        }
        return "com.sun.webui.jsf.EditableField";
    }
    
    /**
     * Returns the renderer type for the component.
     *
     * Depending on the type of the request, this method will return the default
     * renderer type of "com.sun.webui.jsf.widget.EditableField",
     * or "com.sun.webui.jsf.ajax.TextField" in case of ajax
     * request. Ajax request represents a special case of request, when partial
     * data is rendered back to the client.
     */
    public String getRendererType() {
        // Ensure we have a valid Ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.TextField";
        }
        return super.getRendererType();
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    
    
    /**
     * Hide autoValidate.
     * EditableField provides submit logic on onBlur event,
     * and having autoValidate as well would be unnecessary
     *
     */
    @Property(name="autoValidate",  isHidden=true, isAttribute=false)
    private boolean autoValidate = false;
    private boolean autoValidate_set = true;

    /**
     * Attribute indicating to turn on/off the autoSave functionality of the EditableField.
     * When on, an Ajax submit event will be generated every time component looses 
     * focus with modified data. If data is not modified, no Ajax request will be 
     * submitted.
     *
     * <br>
     * AutoSave will submit the content of the text field for server side processing that
     * will be processed using JSFX partial lifecycle cycle. Component on the client
     * will not be updated as per results of the submit. If validation fails, for example,
     * the server state of the component will not be updated ( UPDATE_MODEL phase is not invoked),
     * while client side component will still reflect the user modified invalid data.
     * <br>
     * By default autoSave is on.
     */
    @Property(name="autoSave", displayName="autoSave", category="Behavior")
    private boolean autoSave = true;
    private boolean autoSave_set = false;

    /**
     * Test if default Ajax functionality autoSave should is on or off.
     */
    public boolean isAutoSave() {
        if (this.autoSave_set) {
            return this.autoSave;
        }
        ValueExpression _vb = getValueExpression("autoSave");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return this.autoSave;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return this.autoSave;
    }

    /**
     * Set attribute indicating to turn on/off default autoSave functionality.
     */
    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
        this.autoSave_set = true;
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
        this.autoSave =     ((Boolean) _values[1]).booleanValue();
        this.autoSave_set = ((Boolean) _values[2]).booleanValue();
    }

    /**
     * Save the state of this component.
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[3];
        _values[0] = super.saveState(_context);
        _values[1] = this.autoSave ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.autoSave_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
