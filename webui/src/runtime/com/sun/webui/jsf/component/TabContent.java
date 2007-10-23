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
import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;

import com.sun.webui.jsf.event.MethodExprActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.NamingContainer;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.component.util.Util;

/**
 * The TabContent component represents one tab in a TabContainer. 
 * TabContent must be a child of a TabContainer, or of another TabContent.
 * Each TabContent has a title and content. The content can be an aribitrary
 * set of components or some HTML markup or both. The component traverses 
 * the JSF lifecycle when the container is refreshed, the component itself
 * is refreshed or the page containing the cotainer (hence, the component) is 
 * submitted.
 *
 */
public class TabContent extends WebuiComponent  implements NamingContainer {
    
           
    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String attributes[] = {
        "style",
    };

    /**
     * Create a new instance of TabContent.
     */
    public TabContent() {
        super();
        setRendererType("com.sun.webui.jsf.widget.TabContent");
    }
    
    /**
     * Create a new instance of TabContent with the text property set to the value
     * specified.
     */
    public TabContent(String title) {
        this();
        setTitle(title);
    }
    
    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.TabContent";
    }

    /**
     * <p>Return the renderer type associated with this component.</p>
     */
    public String getRendererType() {
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.TabContent";
        }
        return super.getRendererType();
    }
        
    /**
     * The title of this tab.
     */
    @Property(name="title", displayName="Tab Title", category="Appearance", 
        editorClassName="com.sun.rave.propertyeditors.StringPropertyEditor")
    private String title = null;
    
    /**
     * Return the title for this tab.
     */
    public String getTitle() {
        if (this.title != null) {
            return this.title;
        }
        ValueExpression _vb = getValueExpression("title");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }
    
    /**
     * Set the title for this tab.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Returns true if this tab is selected. False otherwise. Set to false by
     * default.
     */
    @Property(name="selected", displayName="Is this tab selected", isHidden=true)
    private boolean selected = false;
    private boolean selected_set = false;
    
    /**
     * Returns true if the tab is selected.
     */
    public boolean isSelected() {
        if (this.selected_set) {
            return this.selected;
        }
        ValueExpression _vb = getValueExpression("selected");
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
     * Set to true if this tab should be selected. 
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
        this.selected_set = true;
    }

    /**
     * Returns the number of children of this tab that are themselves tabs.
     */
    public int getTabChildCount() {
        if (this.getChildCount() == 0)
            return 0;
        int childTabCount = 0;
        for (UIComponent child : this.getChildren()) {
            if (child instanceof TabContent)
                childTabCount++;
        }
        return childTabCount;
    }
    
    /**
     * Returns a list of all children of this tab that are themselves tabs.
     */
    public List<TabContent> getTabChildren() {
        List<TabContent> tabChildren = new ArrayList<TabContent>();
        for (UIComponent child : this.getChildren()) {
            if (child instanceof TabContent)
                tabChildren.add((TabContent) child);
        }
        return tabChildren;
    }
    
    /**
     * Set the selected flag on the tab that is currently selected
     * and unselect the previously selected tab. This is being
     * done to maintain the same selected state on the client and
     * server side. This method should only be called in the case
     * of tab containers configured for single selection.
     */
    public void setSelectedTab(TabContent selectedTab) {
        for (TabContent child : this.getTabChildren()) {
            if (child.getId() != selectedTab.getId()) {
                child.setSelected(false);
            }
        }
    }
    
    /**
     * CSS style(s) to be applied to the outermost HTML element when this
     * component is rendered.
     */
    @Property(name="style", displayName="CSS Style(s) of the tabContent", 
    category="Appearance", editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;
    
    /**
     * CSS style(s) to be applied to the outermost div tag of the Tab's title.
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
     * CSS style or styles to be applied to the outermost div tag of the Tab title
     */
    public void setStyle(String style) {
        this.style = style;
    }
    
    /**
     * CSS style class or classes to be applied to the outermost HTML element of the 
     * TabContent.
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", 
        editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;
    
    /**
     * Returns the CSS style class(es) that have been applied to the outermost 
     * HTML element of the Tab title.
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
     * CSS style class(es) to be applied to the outermost HTML element of the 
     * TabContent.
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
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
     * <p>Indicates whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, this setting is set to true, so
     * HTML for the tab component is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    @Property(name="visible", displayName="Visible")
    private boolean visible = false;
    private boolean visible_set = false;
    
    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
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
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     * @see #isVisible()
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }
    
    /**
     * Restore the state of this component.
     */
    @Override
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.selected = ((Boolean) _values[1]).booleanValue();
        this.selected_set = ((Boolean) _values[2]).booleanValue();
        this.style = (String) _values[3];
        this.styleClass = (String) _values[4];
        this.htmlTemplate = (String) _values[5];
        this.title = (String) _values[6];
        this.visible = ((Boolean) _values[7]).booleanValue();
        this.visible_set = ((Boolean) _values[8]).booleanValue();
    }
    
    /**
     * Save the state of this component.
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[9];
        _values[0] = super.saveState(_context);
        _values[1] = this.selected ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.selected_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.style;
        _values[4] = this.styleClass;
        _values[5] = this.htmlTemplate;
        _values[6] = this.title;
        _values[7] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
    
    /**
     * Utility method that returns the tabContainer instance that contains 
     * the tab specified.
     */
    public TabContainer getTabContainer(TabContent tab) {
        TabContainer tabC = null;
        UIComponent parent = tab.getParent();
        while (tabC == null && parent != null) {
            if (parent instanceof TabContainer)
                tabC = (TabContainer) parent;
            else
                parent = parent.getParent();
        }
        return tabC;
    }
}   