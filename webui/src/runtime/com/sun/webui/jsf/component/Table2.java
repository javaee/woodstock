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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * Component that represents a table.
 */
@Component(type="com.sun.webui.jsf.Table2",
    family="com.sun.webui.jsf.Table2",
    tagRendererType="com.sun.webui.jsf.widget.Table2", 
    displayName="Table2", tagName="table2")
public class Table2 extends Table implements NamingContainer {
    /** The facet name for the actions area. */ 
    public static final String ACTIONS_FACET = "actions"; //NOI18N 
 
    /** The facet name for the title area. */ 
    public static final String TITLE_FACET = "title"; //NOI18N

    // A List containing Table2RowGroup children. 
    private List table2RowGroupChildren = null;

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

    /** 
     * Get an Iterator over the Table2RowGroup children found for this
     * component. 
     * 
     * @return An Iterator over the Table2RowGroup children. 
     */ 
    public Iterator getTable2RowGroupChildren() { 
        // Get TableRowGroup children. 
        if (table2RowGroupChildren == null) { 
            table2RowGroupChildren = new ArrayList(); 
            Iterator kids = getChildren().iterator(); 
            while (kids.hasNext()) { 
                UIComponent kid = (UIComponent) kids.next(); 
                if ((kid instanceof Table2RowGroup)) { 
                    table2RowGroupChildren.add(kid); 
                } 
            } 
        }
        return table2RowGroupChildren.iterator();
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Flag indicating to turn off default Ajax functionality. Set ajaxify to
     * false when providing a different Ajax implementation.
     */
    @Property(name="ajaxify", isHidden=true, isAttribute=true, displayName="Ajaxify", category="Javascript")
    private boolean ajaxify = true; 
    private boolean ajaxify_set = false; 
 
    /**
     * Test if default Ajax functionality should be turned off.
     */
    public boolean isAjaxify() { 
        if (this.ajaxify_set) {
            return this.ajaxify;
        }
        ValueExpression _vb = getValueExpression("ajaxify");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    } 

    /**
     * Set flag indicating to turn off default Ajax functionality.
     */
    public void setAjaxify(boolean ajaxify) {
        this.ajaxify = ajaxify;
        this.ajaxify_set = true;
    }

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

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // State methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Restore the state of this component.
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.ajaxify = ((Boolean) _values[1]).booleanValue();
        this.htmlTemplate = (String) _values[2];
    }

    /**
     * Save the state of this component.
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[3];
        _values[0] = super.saveState(_context);
        _values[1] = this.ajaxify ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.htmlTemplate;
        return _values;
    }
}
