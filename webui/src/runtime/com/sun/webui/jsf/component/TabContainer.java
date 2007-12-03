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
 * TabContainer.java
 *
 * Created on March 28, 2007, 3:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Property;
import com.sun.faces.annotation.Component;

import java.util.Vector;
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
 * A container for one or more tabs, accordion tabs or menu item
 * components. The currently selected element is specified via the
 * {@code selected} property, which may be bound to a model value. Action 
 * listeners may be registered individually with a contained element; or, an 
 * action listener may be registered with the containing TabContainer, 
 * in which case it is notified of all tab selection actions. Each tab in
 * the container can have arbitrary HTML elements or components or a combination
 * of both. Typically a single tab or the entire container would go through the
 * JSF lifecycle during an XHR request. However, in cases where a form with
 * a contianer component is submitted the container and all its child components
 * will go through the JSF lifecycle as usual. It is up to the application to 
 * decide what should be done in this scenario.
 */

public class TabContainer extends WebuiComponent implements NamingContainer {
    
    /**
     * Create a new instance of the TabContainer.
     */
    public TabContainer() {
        super();
        
    }
     
    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.TabContainer";
    }
    
    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     */
    @Property(name="tabIndex", displayName="Tab Index", category="Accessibility", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int tabIndex = Integer.MIN_VALUE;
    private boolean tabIndex_set = false;

    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     */
    public int getTabIndex() {
        if (this.tabIndex_set) {
            return this.tabIndex;
        }
        ValueExpression _vb = getValueExpression("tabIndex");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     * @see #getTabIndex()
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
        this.tabIndex_set = true;
    }
    
    /**
     * Alternative HTML template to be used by this component.
     */
    @Property(name="htmlTemplate", displayName="HTML Template", category="Appearance")
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
     * Returns true if the tabs should be loaded when first selected.
     * After tabs are loaded they are not reloaded until the container is
     * refreshed or the user performs some action on the tab in question. 
     */
    @Property(name="loadOnSelect", displayName="Load on select", isHidden=true)
    private boolean loadOnSelect = false;
    private boolean loadOnSelect_set = false;
    
    /**
     * Returns true if the tab should be loaded on select, false otherwise.
     * This value is false by default.
     */
    public boolean isLoadOnSelect() {
        if (this.loadOnSelect_set) {
            return this.loadOnSelect;
        }
        ValueExpression _vb = getValueExpression("loadOnSelect");
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
     * Set to true if tabs should be loaded when selected. 
     */
    public void setLoadOnSelect(boolean loadOnSelect) {
        this.loadOnSelect = loadOnSelect;
        this.loadOnSelect_set = true;
    }
    
    /**
     * CSS style class or classes to be applied to the outermost HTML element 
     * when this component is rendered.
     */
    @Property(name="style", displayName="CSS Style(s)", category="Appearance", 
        editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;
    
    /**
     * CSS style or styles to be applied to the outermost HTML element when this
     * component is rendered.
     */
    public String getStyle() {
        if (this.style != null) {
            return this.style;
        }
        ValueExpression _vb = getValueExpression("style");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }
    
    /**
     * CSS style(s) to be applied to the outermost HTML element when this
     * component is rendered.
     * @see #getStyle()
     */
    public void setStyle(String style) {
        this.style = style;
    }
    
    /**
     * CSS style class or classes to be applied to the outermost HTML element when this
     * component is rendered.
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", 
        editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;
    
    /**
     * CSS style class(es) to be applied to the outermost HTML element when this
     * component is rendered.
     */
    public String getStyleClass() {
        if (this.styleClass != null) {
            return this.styleClass;
        }
        ValueExpression _vb = getValueExpression("styleClass");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }
    
    /**
     * CSS style class(es) to be applied to the outermost HTML element when this
     * component is rendered.
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    
    /**
     * <p>Indicates whether the accordion component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, this setting is true, so
     * HTML for the component is included and visible to the user. If the
     * accordion component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    @Property(name="visible", displayName="Visible")
    private boolean visible = false;
    private boolean visible_set = false;
    
    /**
     * <p>Indicates whether the accordion component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, this setting is true, so
     * HTML for the component is included and visible to the user. If the
     * accordion component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    public boolean isVisible() {
        if (this.visible_set) {
            return this.visible;
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
        return true;
    }
    
    /**
     * <p>Set the visible attribute.</p>
     * @see #isVisible()
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }
    
    
    /**
     * An array of selected tabs. In some cases, only a single tab can be selected
     * which specifies that the array contains just one tab.
     */
    @Property(name="selectedTabs", displayName="Array of Selected Tabs", category="Advanced")
    private TabContent[] selectedTabs = null;
    
    /**
     * Return an array of selected tab content. If no tabs are selected an 
     * empty array is returned.
     * 
     */
    public TabContent[] getSelectedTabs() {
        if (this.selectedTabs != null) {
            return this.selectedTabs;
        }
        ValueExpression _vb = getValueExpression("selectedTabs");
        if (_vb != null) {
            return (TabContent[]) _vb.getValue(getFacesContext().getELContext());
        } else {
            Vector tabVector = new Vector();
            TabContent[] tabContents;
            for (UIComponent kid : this.getChildren()) {
                if (kid instanceof TabContent) {
                    TabContent tc = (TabContent)kid;
                    if (tc.isSelected()) {
                        tabVector.add(tc);
                    }
                }
            }
            return (TabContent []) tabVector.toArray();
        }
    }
    
    /**
     * Set the array of selected tabs. 
     */
    public void setSelectedTabs(TabContent[] selectedTabs) {
        this.selectedTabs = selectedTabs;
    }
    
    
    /**
     * Set the selected flag on the tab that is currently selected
     * and unselect the previously selected tab. This is being
     * done to maintain the same selected state on the client and
     * server side. This method should only be called in the case
     * of tab containers configured for single selection.
     */
    public void setSelectedTab(TabContent selectedTab) {
        for (UIComponent child : this.getChildren()) {
            if (child instanceof TabContent) {
                if (child.getId() != selectedTab.getId()) {
                    ((TabContent)child).setSelected(false);
                }
            }
        }
    }
    
    /**
     * Returns the tab with the id specified that is a child of the tab specified. If
     * no such descendant tab exists, returns null. If the tab specified contains more
     * than one tab with the same id, the tab returned will be the first encountered
     * in document order.
     */
    public static TabContent findChildTab(TabContent tab, String tabId) {
        if (tab == null || tabId == null) {
            return null;
        }
        
        if (tab.getTabChildCount() == 0) {
            return null;
        }
        
        for (TabContent child : tab.getTabChildren()) {
            TabContent foundTab = TabContainer.findTab(child, tabId);
            if (foundTab != null) {
                return foundTab;
            }
        }
        
        if (tabId.equals(tab.getId())) {
            return tab;
        }
        
        return null;
    }
    
    private static TabContent findTab(TabContent tab, String tabId) {
        
        if (tabId.equals(tab.getId())) {
            return tab;
        }
        
        if (tab.getTabChildCount() == 0) {
            return null;
        }
        
        for (TabContent child : tab.getTabChildren()) {
            TabContent foundTab = TabContainer.findTab(child, tabId);
            if (foundTab != null) {
                return foundTab;
            }
        }
        return null;
    }
    
    /**
     * Restore the state of this component.
     */
    @Override
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.selectedTabs = (TabContent[]) _values[1];
        this.htmlTemplate = (String) _values[2];
        this.style = (String) _values[3];
        this.styleClass = (String) _values[4];
        this.loadOnSelect = ((Boolean) _values[5]).booleanValue();
        this.loadOnSelect_set = ((Boolean) _values[6]).booleanValue();
        this.visible = ((Boolean) _values[7]).booleanValue();
        this.visible_set = ((Boolean) _values[8]).booleanValue();
        this.tabIndex = ((Integer) _values[9]).intValue();
        this.tabIndex_set = ((Boolean) _values[10]).booleanValue();
    }
    
    /**
     * Save the state of this component.
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[11];
        _values[0] = super.saveState(_context);
        _values[1] = this.selectedTabs;
        _values[2] = this.htmlTemplate;
        _values[3] = this.style;
        _values[4] = this.styleClass;
        _values[5] = this.loadOnSelect ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.loadOnSelect_set ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = new Integer(this.tabIndex);
        _values[10] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
      
}
