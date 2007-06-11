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

/*
 * Accordion.java
 *
 * Created on May 30, 2007, 3:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Property;
import com.sun.faces.annotation.Component;
import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;

import java.util.Stack;
import javax.faces.component.NamingContainer;
import javax.faces.event.FacesEvent;
import javax.faces.event.AbortProcessingException;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.el.ValueExpression;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ValueChangeEvent;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.FacesMessageUtils;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme; 

/**
 * An accordion container. It extends the TabContainer and adds some 
 * functionality that is specific to the Accordion. An accordion can be
 * thought of as a vertical tab set. It can contain one of more tabs
 * each of which can contain virtually any HTML markup. In general accordions
 * are used for navigational purposes - each tab contains links which when 
 * clicked takes the user to different sections (tasks) of the application.
 * In general only one accorion tab can be open at any given time. This
 * accordion, however, allows muliple tabs to be open simultaneously. In 
 * such situations the accordion also supports "expandAll" and "collapseAll"
 * icons which have their usual meaning. An accordion can be refreshed if the 
 * application using it so desires. Each attribute of the accordion is 
 * described in detail in the TLD doccument. It is adviseable to use 
 * an alternate navigational component if the number of tabs in the accordion 
 * are exceeding ten.
 */

@Component(type="com.sun.webui.jsf.Accordion", 
family="com.sun.webui.jsf.Accordion", displayName="Accordion", 
tagName="accordion", tagRendererType="com.sun.webui.jsf.widget.Accordion")
public class Accordion extends TabContainer {
    
    /**
     * Developer defined refresh facet for the accordion container. This facet
     * can be used to supply an alternate refresh icon for the accordion. 
     * Refreshing the accordion will asynchronoulsy refresh each tab within
     * the accordion component.
     */
    public static final String ACCORDION_REFRESH_FACET = "accdRefresh";
    
    /**
     * Developer defined "collapse all" icon facet for the accordion container. 
     * This facet is used to supply an alternate "collapse all" icon. Both 
     * "collapse all" and "expand all" facets appy only in the case of 
     * accordions that support multipleSelect.
     */
    public static final String ACCORDION_COLLAPSEALL_FACET = "accdCollpseAll";
    
    /**
     * Developer defined "expand all" icon facet for the accordion container. 
     * This facet is used to supply an alternate "expand all" icon. Both 
     * "collapse all" and "expand all" facets appy only in the case of 
     * accordions that support multipleSelect.
     */
    public static final String ACCORDION_EXPANDALL_FACET = "accdExpandAll";
    
   
    
    /**
     * Create a new Accordion.
     */
    public Accordion() {
        super();
        setRendererType("com.sun.webui.jsf.widget.Accordion");
    }
    
    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.Accordion";
    }

    public String getRendererType() {
        
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.Accordion";
        }
        return super.getRendererType();
        
    }
      
    /**
     * Returns true if multiple tabs can be selected at the same time.
     * By default this is set to false and only one accordion tab can be
     * selected at any given time. Note that when only a single tab is
     * selected the accordion will not supply expand/collapse icons even if 
     * the application developer provides facets for these.
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
     * Should be set to true if the accordion container should display
     * expandAll and collapseAll control features. Note that this makes sense 
     * only in the case wehere multipleSelect has been set to true. In the case
     * of single select accordions only one tab can be open at any given time.
     */
    @Property(name="toggleControls", displayName="Set expand and colapse controls", 
        isHidden=true)
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
    @Property(name="refreshButton", displayName="Display Refresh Icon", 
        isHidden=true)
    private boolean refreshButton = false;
    private boolean refreshButton_set = false;
    
    /**
     * Returns true if the Accordion container contains a refresh icon.
     * 
     */
    public boolean isRefreshButton() {
        if (this.refreshButton_set) {
            return this.refreshButton;
        }
        ValueExpression _vb = getValueExpression("refreshButton");
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
    public void setRefreshButton(boolean refreshButton) {
        this.refreshButton = refreshButton;
        this.refreshButton_set = true;
    }
    
    /**
     * The id of the selected tab. This only makes sense in the case of 
     * accordions where only a single tab can be selected at any given time.
     */
    @Property(name="selectedTabID", displayName="Selected child tab ID", category="Advanced")
    private String selectedTabID = null;
    
    /**
     * Get the selected child.
     * 
     */
    public String getSelectedTabID() {
        if (this.selectedTabID != null) {
            return this.selectedTabID;
        }
        ValueExpression _vb = getValueExpression("selectedTabID");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }
    
    /**
     * set the newly selected child
     */
    public void setSelectedTabID(String selectedTabID) {
        this.selectedTabID = selectedTabID;
    }
    
    /**
     * Return a component that implements a refresh icon.
     * If a facet named <code>accdRefresh</code> is found
     * that component is returned.</br>
     * If a facet is not found an <code>Icon</code>
     * component instance is returned with the id</br>
     * <code>getId() + "_accdRefresh"</code>.
     * <p>
     * If a facet is not defined then the returned <code>Icon</code>
     * component is created every time this method is called.
     * </p>
     * @return - required facet or an Icon instance
     */
    public UIComponent getRefreshIcon(Theme theme, FacesContext context) { 
        
        UIComponent comp = getFacet(ACCORDION_REFRESH_FACET);
        if(comp != null) {
	    return comp;
	}
        Icon icon = ThemeUtilities.getIcon(theme,
	    ThemeImages.ACCORDION_REFRESH);
	icon.setId(
	    ComponentUtilities.createPrivateFacetId(this, ACCORDION_REFRESH_FACET));
	icon.setParent(this);
        icon.setToolTip(theme.getMessage("Accordion.refresh"));
	icon.setBorder(0);            
	return icon;    
    }
        
    /**
     * Return a component that implements a expandAll icon.
     * If a facet named <code>accdExpndall</code> is found
     * that component is returned.</br>
     * If a facet is not found an <code>Icon</code>
     * component instance is returned with the id</br>
     * <code>getId() + "_accdExpndall"</code>.
     * <p>
     * If a facet is not defined then the returned <code>Icon</code>
     * component is created every time this method is called.
     * </p>
     * @return - required facet or an Icon instance
     */
    public UIComponent getExpandAllIcon(Theme theme, FacesContext context) { 
        
        UIComponent comp = getFacet(ACCORDION_EXPANDALL_FACET);
        if(comp != null) {
	    return comp;
	}

	Icon icon = ThemeUtilities.getIcon(theme,
	    ThemeImages.ACCORDION_EXPAND_ALL);
	icon.setId(
	    ComponentUtilities.createPrivateFacetId(this, ACCORDION_EXPANDALL_FACET));
	icon.setParent(this);
        icon.setToolTip(theme.getMessage("Accordion.expandAll"));
        
	icon.setBorder(0);            
	return icon;    
    }
    
    /**
     * Return a component that implements a collapseAll icon.
     * If a facet named <code>accdCollpsall</code> is found
     * that component is returned.</br>
     * If a facet is not found an <code>Icon</code>
     * component instance is returned with the id</br>
     * <code>getId() + "_accdCollpsall"</code>.
     * <p>
     * If a facet is not defined then the returned <code>Icon</code>
     * component is created every time this method is called.
     * </p>
     * @return - required facet or an Icon instance
     */
    public UIComponent getCollapseAllIcon(Theme theme, FacesContext context) { 
        
        UIComponent comp = getFacet(ACCORDION_COLLAPSEALL_FACET);
        if(comp != null) {
	    return comp;
	}

	Icon icon = ThemeUtilities.getIcon(theme,
	    ThemeImages.ACCORDION_COLLAPSE_ALL);
	icon.setId(
	    ComponentUtilities.createPrivateFacetId(this, ACCORDION_COLLAPSEALL_FACET));
	icon.setParent(this);
        icon.setToolTip(theme.getMessage("Accordion.collapseAll"));
	icon.setBorder(0);            
	return icon;    
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
        this.selectedTabID = (String) _values[3];
        this.toggleControls = ((Boolean) _values[4]).booleanValue();
        this.toggleControls_set = ((Boolean) _values[5]).booleanValue();
        this.refreshButton = ((Boolean) _values[6]).booleanValue();
        this.refreshButton_set = ((Boolean) _values[7]).booleanValue();
    }
    
    /**
     * Save the state of this component.
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[8];
        _values[0] = super.saveState(_context);
        _values[1] = this.multipleSelect ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.multipleSelect_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.selectedTabID;
        _values[4] = this.toggleControls ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.toggleControls_set ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.refreshButton ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.refreshButton_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
   
}