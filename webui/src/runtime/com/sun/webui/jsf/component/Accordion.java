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
import com.sun.faces.annotation.Component;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme; 

import javax.el.ValueExpression;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * An accordion container. It extends the TabContainer and adds some 
 * functionality that is specific to the Accordion. An accordion can be
 * thought of as a vertical tab set. It can contain one of more accordion tabs
 * each of which can contain virtually any HTML markup. In general accordions
 * are used for navigational purposes - each tab contains links which when 
 * clicked takes the user to different sections (tasks) of the application.
 * The Accordian allows one or more tabs to be open at any given time. When the
 * accordion is configured to allow multiple tabs to be open at any given time 
 * it supports "expandAll" and "collapseAll" icons. The expandAll icon when clicked
 * will expand all tabs, and colapseAll, will collapse all open tabs. 
 * An accordion can be refreshed. 
 * Refreshing the accordion will cause it to render itself and all its children
 * again. The Accordion can also be refreshed to go through all the steps of 
 * the JSF lifecycle as opposed to just the render response phase.
 * It is adviseable to use an alternate navigational component if the number of
 * tabs in the accordion are exceeding ten.
 */
@Component(type="com.sun.webui.jsf.Accordion", 
    family="com.sun.webui.jsf.Accordion", displayName="Accordion", 
    tagName="accordion", tagRendererType="com.sun.webui.jsf.widget.Accordion")
public class Accordion extends TabContainer {
    
    /**
     * Create a new Accordion.
     */
    public Accordion() {
        super();
        setRendererType("com.sun.webui.jsf.widget.Accordion");
    }
    
    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Accordion";
    }

    /**
     * <p>Return the renderer type associated with this component.</p>
     */
    public String getRendererType() {
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.Accordion";
        }
        return super.getRendererType();
    }
      
    /**
     * Returns true if multiple tabs can be selected at the same time.
     * By default this setting is false and only one accordion tab can be
     * selected at any given time. Note that when only a single tab is
     * selected the accordion will not supply expand/collapse icons even if 
     * the application developer provides facets for these.
     */
    @Property(name="multipleSelect", displayName="Multiple tab selected")
    private boolean multipleSelect = false;
    private boolean multipleSelect_set = false;
    
    /**
     * Returns true if multiple tabs can be selected, false otherwise.
     * This value is false by default.
     */
    public boolean isMultipleSelect() {
        if (this.multipleSelect_set) {
            return this.multipleSelect;
        }
        ValueExpression _vb = getValueExpression("multipleSelect");
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
     * Set to true if multiple tabs can be selected. 
     */
    public void setMultipleSelect(boolean multipleSelect) {
        this.multipleSelect = multipleSelect;
        this.multipleSelect_set = true;
    }

    /**
     * Set to true if the accordion container should display
     * expandAll and collapseAll control features. Note that this setting applies
     * only in the case where multipleSelect has been set to true. In the case
     * of single select accordions only one tab can be open at any given time.
     */
    @Property(name="toggleControls", displayName="Set expand and colapse controls")
    private boolean toggleControls = false;
    private boolean toggleControls_set = false;
    
    /**
     * Returns true if the Accordion container contains expand/collapse
     * controls. 
     */
    public boolean isToggleControls() {
        if (this.toggleControls_set) {
            return this.toggleControls;
        }
        ValueExpression _vb = getValueExpression("toggleControls");
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
     * Set to true if Accordion container should contain expand/collapse
     * controls. 
     */
    public void setToggleControls(boolean toggleControls) {
        this.toggleControls = toggleControls;
        this.toggleControls_set = true;
    }
    
    
    /**
     * Should be set to true if the accordion container is to display
     * a refresh icon. A refresh icon would generally be provided on
     * the accordion header. When clicked it would refresh the accordion
     * and its tabs asynchronously.
     */
    @Property(name="refreshIcon", displayName="Display Refresh Icon")
    private boolean refreshIcon = false;
    private boolean refreshIcon_set = false;
    
    /**
     * Returns true if the Accordion container contains a refresh icon.
     * 
     */
    public boolean isRefreshIcon() {
        if (this.refreshIcon_set) {
            return this.refreshIcon;
        }
        ValueExpression _vb = getValueExpression("refreshIcon");
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
     * Set to true if Accordion container should contain a refresh icons.
     * 
     */
    public void setRefreshIcon(boolean refreshIcon) {
        this.refreshIcon = refreshIcon;
        this.refreshIcon_set = true;
    }
    
    /**
     * Restore the state of this component.
     */
    @Override
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.multipleSelect = ((Boolean) _values[1]).booleanValue();
        this.multipleSelect_set = ((Boolean) _values[2]).booleanValue();
        this.toggleControls = ((Boolean) _values[3]).booleanValue();
        this.toggleControls_set = ((Boolean) _values[4]).booleanValue();
        this.refreshIcon = ((Boolean) _values[5]).booleanValue();
        this.refreshIcon_set = ((Boolean) _values[6]).booleanValue();
    }
    
    /**
     * Save the state of this component.
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[7];
        _values[0] = super.saveState(_context);
        _values[1] = this.multipleSelect ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.multipleSelect_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.toggleControls ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.toggleControls_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.refreshIcon ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.refreshIcon_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
   
}