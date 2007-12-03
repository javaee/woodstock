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
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 * An accordion tab component. It extends the generic tabContent 
 * The AccordionTab component represents one tab in the accordion. 
 * AccordionTab components must be children of Accordion components.
 * Each AccordionTab has a title and some content. The content can be an aribitrary
 * set of components or some XHTML markup or both. The component would go through 
 * the usual JSF lifecycle when the Accordion is refreshed, the component itself
 * is refreshed or the page containing the Accordion (hence, the component) is 
 * submitted.
 */
@Component(type="com.sun.webui.jsf.AccordionTab", 
    family="com.sun.webui.jsf.AccordionTab", displayName="AccordionTab", 
    tagName="accordionTab", tagRendererType="com.sun.webui.jsf.widget.AccordionTab")
public class AccordionTab extends TabContent {
    
    /**
     * Create a new AccordionTab.
     */
    public AccordionTab() {
        super();
        setRendererType("com.sun.webui.jsf.widget.AccordionTab");
    }
    
    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.AccordionTab";
    }

    /**
     * <p>Return the renderer type associated with this component.</p>
     */
    public String getRendererType() {
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.AccordionTab";
        }
        return super.getRendererType();
    }
    
    /**
     * The client ID of the first element to focus on when the user tabs to
     * an open(selected) accordionTab. This attribute helps make
     * the accordionTab accessible. Once inside the accordionTab's content
     * section one can tab through all the tabable elements until it reaches
     * the end after which pressing the tab key will cause the accordion to lose
     * focus.
     */
    @Property(name="focusId", displayName="Focus Element ID", category="Appearance", 
        editorClassName="com.sun.rave.propertyeditors.StringPropertyEditor")
    private String focusId = null;
    
    /**
     * Return the client ID of the first element to focus on in the accordionTab's
     * content area.
     */
    public String getFocusId() {
        if (this.focusId != null) {
            return this.focusId;
        }
        ValueExpression _vb = getValueExpression("focusId");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }
    
    /**
     * Set the client ID of the first element to focus on for the accordionTab.
     */
    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }
    
    /**
     * The height of each tab content. This height is applied to the style of
     * the content section of AccordionTab. It can be listed either in pixels, 
     * or in em or en. By default the height is set to 100 pixels.
     */
    @Property(name="contentHeight", displayName="Tab Content Height", category="Appearance", 
        editorClassName="com.sun.rave.propertyeditors.StringPropertyEditor")
    private String contentHeight = null;
    
    /**
     * Return the content height for this tab.
     */
    public String getContentHeight() {
        if (this.contentHeight != null) {
            return this.contentHeight;
        }
        ValueExpression _vb = getValueExpression("contentHeight");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        
        // we should get here only if getValueExpression and this.contentHeight
        // failed to produce a reasonable content height
        
        // Return the themed value. By default this is set to "100px".
        String defaultValue = "100px";
        try {
            defaultValue = ThemeUtilities.getTheme(FacesContext.getCurrentInstance())
                .getMessage("AccordionTab.contentHeight"); //NOI18N
        } catch ( Exception e) {} //do nothing
        return defaultValue;
    }
    
    /**
     * Set the content height for this tab.
     */
    public void setContentHeight(String contentHeight) {
        this.contentHeight = contentHeight;
    }
        
    /**
     * Use when the AccordionTab is itself a container of AccordionTabs.
     * Returns true if multiple tabs can be selected at the same time.
     * By default this setting is false and only one accordion tab can be
     * selected at any given time. Note that when only a single tab is
     * selected the accordion will not supply expand or collapse icons even if 
     * the application developer provides facets for these icons.
     */
    @Property(name="multipleSelect", displayName="Multiple tab selected", isHidden=true)
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
     * Restore the state of this component.
     */
    @Override
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.contentHeight = (String) _values[1];
        this.multipleSelect = ((Boolean) _values[2]).booleanValue();
        this.multipleSelect_set = ((Boolean) _values[3]).booleanValue();
        this.focusId = (String) _values[4];
    }
    
    /**
     * Save the state of this component.
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[5];
        _values[0] = super.saveState(_context);
        _values[1] = this.contentHeight;
        _values[2] = this.multipleSelect ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.multipleSelect_set ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.focusId;
        return _values;
    }
}
