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

import java.io.IOException;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 * Component that represents a table.
 *
 * The Table2 component provides a layout mechanism for displaying table actions.
 * UI guidelines describe specific behavior that can applied to the rows and 
 * columns of data such as sorting, filtering, pagination, selection, and custom 
 * user actions. In addition, UI guidelines also define sections of the table 
 * that can be used for titles, row group headers, and placement of pre-defined
 * and user defined actions.
 * </p><p>
 * Only children of type Table2RowGroup should be processed by renderers
 * associated with this component.
 * </p>
 */
@Component(type="com.sun.webui.jsf.Table2",
    family="com.sun.webui.jsf.Table2",
    tagRendererType="com.sun.webui.jsf.widget.Table2", 
    displayName="Table2", tagName="table2")
public class Table2 extends TableBase {
        
    /** The facet name for the columns panel area. */
    public static final String COLUMNS_PANEL_FACET = "columnsPanel"; //NOI18N
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Base methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public Table2() {
        super();
        setRendererType("com.sun.webui.jsf.widget.Table2");
    }

    public FacesContext getContext() {
        return getFacesContext();
    }

    public String getFamily() {
        return "com.sun.webui.jsf.Table2";
    }

    public String getRendererType() {
        // Ensure we have a valid Ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.Table2";
        }
        return super.getRendererType();
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Child methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // TBD...

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
    
    /**
     * Text used for table tips.
     */
    @Property(name="tips", isAttribute=true, displayName="Table tips", category="Appearance")
    private String tips = null;

    /**
     * text used for table tips.
     */
    public String getTips() {
        if (this.tips != null) {
            return this.tips;
        }
        ValueExpression _vb = getValueExpression("tips");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Text used for table tips.
     */
    public void setTips(String tips) {
        this.tips = tips;
    }

    /**
     * Use the tableControls attribute to hide/display table controls button.
     */
    @Property(name="tableControls", displayName="table controls", category="Behavior")
    private boolean tableControls = false;
    private boolean tableControls_set = false;

    /**
     * Use the tableControls attribute to hide/display table controls button.
     */
    public boolean isTableControls() {
        if (this.tableControls_set) {
            return this.tableControls;
        }
        ValueExpression _vb = getValueExpression("visible");
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
     * Use the tableControls attribute to hide/display table controls button.
     */
    public void setTableControls(boolean tableControls) {
        this.tableControls = tableControls;
        this.tableControls_set = true;
    }
    
    /**
     * Use the tableControls attribute to hide/display table controls button.
     */
    @Property(name="tableTips", displayName="table tips", category="Behavior")
    private boolean tableTips = false;
    private boolean tableTips_set = false;

    /**
     * Use the tableControls attribute to hide/display table controls button.
     */
    public boolean isTableTips() {
        if (this.tableTips_set) {
            return this.tableTips;
        }
        ValueExpression _vb = getValueExpression("visible");
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
     * Use the tableControls attribute to hide/display table controls button.
     */
    public void setTableTips(boolean tableControls) {
        this.tableTips = tableControls;
        this.tableTips_set = true;
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // State methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Restore the state of this component.
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.htmlTemplate = (String) _values[1];
        
        this.tableControls = ((Boolean) _values[2]).booleanValue();
        this.tableControls_set = ((Boolean) _values[3]).booleanValue();
        this.tableTips = ((Boolean) _values[4]).booleanValue();
        this.tableTips_set = ((Boolean) _values[5]).booleanValue();
        this.tips = (String) _values[6];
    }

    /**
     * Save the state of this component.
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[7];
        _values[0] = super.saveState(_context);
        _values[1] = this.htmlTemplate;
        _values[2] = this.tableControls ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.tableControls_set ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.tableTips ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.tableTips_set ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.tips;
        return _values;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // UIComponent methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * If the rendered property is true, render the begining of the current
     * state of this UIComponent to the response contained in the specified
     * FacesContext.
     *
     * If a Renderer is associated with this UIComponent, the actual encoding 
     * will be delegated to Renderer.encodeBegin(FacesContext, UIComponent).
     *
     * @param context FacesContext for the current request.
     *
     * @exception IOException if an input/output error occurs while rendering.
     * @exception NullPointerException if FacesContext is null.
     */
    public void encodeBegin(FacesContext context) throws IOException {
        clear(); // Clear cached properties.
        super.encodeBegin(context);
    }
}
