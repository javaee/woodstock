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
 * The TextArea component is used to create a multiple-line input field for
 * text.
 * As part of the dynamic behavior, TextArea supports autoSave: when enabled,
 * the content of the TextArea will be saved / submitted via ajax call to the server
 * every number of autoSave milliseconds. Note that only the text value of the
 * TextArea component will be saved, and other properties that could have been
 * changed on the client side only (i.e. client modified columns, rows, label) will
 * not be submitted to the server for autosave. Thus client side properties will
 * only affect the client state ( unless explicitly commited by the developer).
 * <br> As all ajaxified client-side rendered components, autoSave would trigger
 * an event on the client side that can be intercepted by the developer in order to
 * implement her own save/commit procedure.
 */
@Component(type="com.sun.webui.jsf.TextArea",
family="com.sun.webui.jsf.TextArea",
        displayName="Text Area", instanceName="textArea", tagName="textArea",
        tagRendererType="com.sun.webui.jsf.widget.TextArea",
        helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_text_area",
        propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_text_area_props")
        public class TextArea extends TextField {
    /**
     * Default constructor.
     */
    public TextArea() {
        super();
        setRendererType("com.sun.webui.jsf.widget.TextArea");
    }
    
    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.TextArea";
    }
    
    /**
     * Returns the renderer type for the component.
     *
     * Depending on the type of the request, this method will return the default
     * renderer type of "com.sun.webui.jsf.widget.TextArea" in case of full
     * render request, or "com.sun.webui.jsf.ajax.TextArea" in case of ajax
     * request. Ajax request represents a special case of request, when partial
     * data is rendered back to the client.
     */
    public String getRendererType() {
        // Ensure we have a valid Ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.TextArea";
        }
        return super.getRendererType();
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * The maximum number of characters that can be entered for this field.
     */
    @Property(name="maxLength", isHidden=true, isAttribute=true)
    public int getMaxLength() {
        return super.getMaxLength();
    }
    
    /**
     * <p>Number of rows used to render the textarea. You should set a value
     * for this attribute to ensure that it is rendered correctly in all
     * browsers.  Browsers vary in the default number of rows used for
     * textarea fields.</p>
     */
    @Property(name="rows", displayName="Rows", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int rows = Integer.MIN_VALUE;
    private boolean rows_set = false;
    
    /**
     * <p>Number of rows used to render the textarea. You should set a value
     * for this attribute to ensure that it is rendered correctly in all
     * browsers.  Browsers vary in the default number of rows used for
     * textarea fields.</p>
     */
    public int getRows() {
        if (this.rows_set) {
            return this.rows;
        }
        ValueExpression _vb = getValueExpression("rows");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return 2;
    }
    
    /**
     * <p>Number of rows used to render the textarea. You should set a value
     * for this attribute to ensure that it is rendered correctly in all
     * browsers.  Browsers vary in the default number of rows used for
     * textarea fields.</p>
     * @see #getRows()
     */
    public void setRows(int rows) {
        this.rows = rows;
        this.rows_set = true;
    }
    
    /**
     * Attribute indicating to turn on/off the Auto-save  functionality of the TextArea.
     * <br>
     * Auto-save will submit the content of the modified textArea for server side
     * processing that will be processed using JSFX partial lifecycle cycle.
     * Only modified data will be submitted, that is textArea data will be
     * submitted every autoSave milliseconds only if it has been modified within
     * last interval.
     *
     * <br>
     * AutoSave is specified in milliseconds. Value of 0 or less is interpreted
     * as no auto-save.
     * <br>
     * By default auto-save = 0, meaning no auto-save will be activated.
     */
    @Property(name="autoSave", displayName="AutoSave", category="Behavior")
    private long autoSave = 0;
    private boolean autoSave_set = false;
    
    /**
     * Test if default Ajax functionality should be turned off.
     */
    public  long getAutoSave() {
        if (this.autoSave_set) {
            return this.autoSave;
        }
        ValueExpression _vb = getValueExpression("autoSave");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return 0;
            } else {
                return ((Long) _result).longValue();
            }
        }
        return 0;
    }
    
    /**
     * Set auto-save period.
     * Values of 0 or less mean no auto-save.
     *
     * @param autoSave - time period in millisec, or 0 if no autosave required
     */
    public void setAutoSave(long autoSave) {
        this.autoSave = autoSave;
        this.autoSave_set = true;
    }
    
    /**
     * <p>SubmitForm attribute is disabled for TextArea.
     * TextArea uses enter key to open new line.
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
     * <p>SubmitForm is disabled for TextArea
     */
    public void setSubmitForm(boolean submitForm) {
        this.submitForm = submitForm;
        this.submitForm_set = true;
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // State methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.rows = ((Integer) _values[1]).intValue();
        this.rows_set = ((Boolean) _values[2]).booleanValue();
        this.autoSave = ((Long) _values[3]).longValue();
        this.autoSave_set = ((Boolean) _values[4]).booleanValue();
    }
    
    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[5];
        _values[0] = super.saveState(_context);
        _values[1] = new Integer(this.rows);
        _values[2] = this.rows_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = new Long(this.autoSave);
        _values[4] = this.autoSave_set ? Boolean.TRUE : Boolean.FALSE;
        
        return _values;
    }
}
