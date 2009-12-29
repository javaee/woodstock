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
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 * The Listbox component allows users to select one or more items from a list.
 */
@Component(type = "com.sun.webui.jsf.Listbox", family = "com.sun.webui.jsf.Listbox",
displayName = "Listbox", tagName = "listbox",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_listbox",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_listbox_props")
public class Listbox extends ListSelector {

    /**
     * Default constructor.
     */
    public Listbox() {
        super();
        setRendererType("com.sun.webui.jsf.Listbox");
    }

    /**
     * <p>Return the identifier of the component family to which this
     * component belongs.  This identifier, in conjunction with the value
     * of the <code>rendererType</code> property, may be used to select
     * the appropriate renderer for this component instance.</p>
     */
    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.Listbox";
    }

    @Override
    public int getRows() {

        int rows = super.getRows();
        if (rows < 1) {
            rows = 12;
            super.setRows(rows);
        }
        return rows;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Hide onSelect
    @Property(name = "onSelect", isHidden = true, isAttribute = false)
    @Override
    public String getOnSelect() {
        return super.getOnSelect();
    }

    // Hide value
    @Property(name = "value", isHidden = true, isAttribute = false)
    @Override
    public Object getValue() {
        return super.getValue();
    }
    /**
     * <p>When set to true, this attribute causes the list items to be rendered 
     * in a monospace font.</p>
     */
    @Property(name = "monospace", displayName = "Use Monospace Space", category = "Appearance")
    private boolean monospace = false;
    private boolean monospace_set = false;

    public boolean isMonospace() {
        if (this.monospace_set) {
            return this.monospace;
        }
        ValueExpression _vb = getValueExpression("monospace");
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
     * <p>When set to true, this attribute causes the list items to be rendered 
     * in a monospace font.</p>
     * @see #isMonospace()
     */
    public void setMonospace(boolean monospace) {
        this.monospace = monospace;
        this.monospace_set = true;
    }
    /**
     * <p>Flag indicating that the application user can make select
     * 	more than one option at a time from the listbox.</p>
     */
    @Property(name = "multiple", displayName = "Multiple", category = "Data")
    private boolean multiple = false;
    private boolean multiple_set = false;

    @Override
    public boolean isMultiple() {
        if (this.multiple_set) {
            return this.multiple;
        }
        ValueExpression _vb = getValueExpression("multiple");
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
     * <p>Flag indicating that the application user can make select
     * 	more than one option at a time from the listbox.</p>
     * @see #isMultiple()
     */
    @Override
    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
        this.multiple_set = true;
    }
    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     */
    @Property(name = "toolTip", displayName = "Tool Tip", category = "Behavior")
    private String toolTip = null;

    @Override
    public String getToolTip() {
        if (this.toolTip != null) {
            return this.toolTip;
        }
        ValueExpression _vb = getValueExpression("toolTip");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     * @see #getToolTip()
     */
    @Override
    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.monospace = ((Boolean) _values[1]).booleanValue();
        this.monospace_set = ((Boolean) _values[2]).booleanValue();
        this.multiple = ((Boolean) _values[3]).booleanValue();
        this.multiple_set = ((Boolean) _values[4]).booleanValue();
        this.toolTip = (String) _values[5];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[6];
        _values[0] = super.saveState(_context);
        _values[1] = this.monospace ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.monospace_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.multiple ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.multiple_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.toolTip;
        return _values;
    }
}
