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

/**
 * The TabContent component represents one tab in a tab container. 
 * TabContent must be children of a TabContainer, or of another TabContent.
 * Each TabContent has a label and some content. The content can be an aribitrary
 * set of components or some XHTML markup or both. The component would go thru 
 * the usual JSF lifecycle when the container is refreshed, the component itself
 * is refreshed or the page containing the cotainer (hence, the component) is 
 * submitted.
 *
 * <p> The tabcomponent supports the following facet: </p>
 * 
 * menuFacet : this facet can be used to supply a menu as part of each tab. The menu is meant
 * to be used by the application to show different views of the same accordion content. Examples
 * would include features that edit the tab content. The menu if supplied would cause the 
 * tab to have an icon which when clicked to display the menu without disturbing the rest of
 * the container.
 */
@Component(type="com.sun.webui.jsf.TabContent", family="com.sun.webui.jsf.TabContent", 
        displayName="TabContent", tagName="tabContent",
        tagRendererType="com.sun.webui.jsf.widget.TabContent")
public class TabContent extends UIComponentBase implements NamingContainer {
    
    /**
     * Developer defined menun facet for the accordion container. 
     * This facet is used to supply a menu for the tab. This feature
     * is still not complete.
     */
    public static final String ACCORDION_TAB_MENU_FACET = "menuFacet";
    
        
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
    public TabContent(String label) {
        this();
        setLabel(label);
    }
    
    public String getFamily() {
        return "com.sun.webui.jsf.TabContent";
    }

    public String getRendererType() {
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.TabContent";
        }
        return super.getRendererType();
    }
        
    /**
     * The label of this tab.
     */
    @Property(name="label", displayName="Tab Label", category="Appearance", 
        editorClassName="com.sun.rave.propertyeditors.StringPropertyEditor")
    private String label = null;
    
    /**
     * Return the label for this tab.
     */
    public String getLabel() {
        if (this.label != null) {
            return this.label;
        }
        ValueExpression _vb = getValueExpression("label");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }
    
    /**
     * Set the label for this tab.
     */
    public void setLabel(String label) {
        this.label = label;
    }
    
    /**
     * Height of each tab content in pixels. By default the ht is set to 100 pixels.
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
        return null;
    }
    
    /**
     * Set the content height for this tab.
     */
    public void setContentHeight(String contentHeight) {
        this.contentHeight = contentHeight;
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
     * Customized implementation that allows child components to decode possible
     * submitted input only if the component is part of the currently selected
     * tab. Some input components cannot distinguish between a null submitted value
     * that is the result of the user unselecting the value (e.g. in the case of
     * a checkbox or listbox) from the case that is the result of the component
     * being hidden in an unselected tab.
     */
    @Override
    public void processDecodes(FacesContext context) {
        if (!this.isRendered())
            return;
        if (this.isSelected()) {
            // If this tab was the selected tab in the submitted page, invoke process
            // decodes on all children components
            for (UIComponent child : this.getChildren())
                child.processDecodes(context);
        } 
        try {
            decode(context);
        } catch (RuntimeException e) {
            context.renderResponse();
            LogUtil.warning("Exception while decoding component with ID" 
			+ this.getId());
	}
    }

    /**
     * CSS style(s) to be applied to the outermost HTML element when this
     * component is rendered.
     */
    @Property(name="labelStyle", displayName="CSS Style(s) of the Tab label", 
    category="Appearance", editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String labelStyle = null;
    
    /**
     * CSS style(s) to be applied to the outermost div tag of the Tab's label.
     */
    public String getLabelStyle() {
        if (this.labelStyle != null) {
            return this.labelStyle;
        }
        ValueExpression _vb = getValueExpression("labelStyle");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }
    
    /**
     * CSS style(s) to be applied to the outermost div tag of the Tab label
     * @see #getLabelStyle()
     */
    public void setLabelStyle(String labelStyle) {
        this.labelStyle = labelStyle;
    }
    
    /**
     * CSS style class(es) to be applied to the outermost HTML element of the Tab
     * label.
     */
    @Property(name="labelClass", displayName="CSS Style Class(es)", category="Appearance", 
        editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String labelClass = null;
    
    /**
     * Returns the CSS style class(es) that have been applied to the outermost 
     * HTML element of the Tab label.
     */
    public String getLabelClass() {
        if (this.labelClass != null) {
            return this.labelClass;
        }
        ValueExpression _vb = getValueExpression("labelClass");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }
    
    /**
     * CSS style class(es) to be applied to the outermost HTML element of the Tab
     * label.
     * @see #getLabelClass()
     */
    public void setLabelClass(String labelClass) {
        this.labelClass = labelClass;
    }
    
    
    /**
     * CSS style(s) to be applied to the outermost HTML element of the Tab's 
     * content.
     */
    @Property(name="contentStyle", displayName="CSS Style(s) of the Tab Content", 
    category="Appearance", editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String contentStyle = null;
    
    /**
     * CSS style(s) to be applied to the outermost div tag of the Tab content
     */
    public String getContentStyle() {
        if (this.contentStyle != null) {
            return this.contentStyle;
        }
        ValueExpression _vb = getValueExpression("contentStyle");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }
    
    /**
     * CSS style(s) to be applied to the outermost div tag of the Tab content
     * @see #getContentStyle()
     */
    public void setContentStyle(String contentStyle) {
        this.contentStyle = contentStyle;
    }
    
    /**
     * CSS style class(es) to be applied to the outermost HTML element of the Tab's
     * content.
     */
    @Property(name="contentClass", displayName="CSS Style Class(es) for tab content",
    category="Appearance", editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String contentClass = null;
    
    /**
     * Returns the CSS style class(es) that have been applied to the outermost 
     * HTML element of the Tab content.
     */
    public String getContentClass() {
        if (this.contentClass != null) {
            return this.contentClass;
        }
        ValueExpression _vb = getValueExpression("contentClass");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }
    
    /**
     * CSS style class(es) to be applied to the outermost HTML element of the Tab
     * content.
     * @see #getContentClass()
     */
    public void setContentClass(String contentClass) {
        this.contentClass = contentClass;
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
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
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
     * Restore the state of this component.
     */
    @Override
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.selected = ((Boolean) _values[1]).booleanValue();
        this.selected_set = ((Boolean) _values[2]).booleanValue();
        this.labelStyle = (String) _values[3];
        this.labelClass = (String) _values[4];
        this.contentStyle = (String) _values[5];
        this.contentClass = (String) _values[6];
        this.htmlTemplate = (String) _values[7];
        this.contentHeight = (String) _values[8];
        this.label = (String) _values[9];
        this.visible = ((Boolean) _values[10]).booleanValue();
        this.visible_set = ((Boolean) _values[11]).booleanValue();
        this.ajaxify = ((Boolean) _values[12]).booleanValue();
        this.ajaxify_set = ((Boolean) _values[13]).booleanValue();
    }
    
    /**
     * Save the state of this component.
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[14];
        _values[0] = super.saveState(_context);
        _values[1] = this.selected ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.selected_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.labelStyle;
        _values[4] = this.labelClass;
        _values[5] = this.contentStyle;
        _values[6] = this.contentClass;
        _values[7] = this.htmlTemplate;
        _values[8] = this.contentHeight;
        _values[9] = this.label;
        _values[10] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[12] = this.ajaxify ? Boolean.TRUE : Boolean.FALSE;
        _values[13] = this.ajaxify_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
    
    /**
     * Utility method that returns the tabContainer instance that contains 
     * the tab specified.
     */
    public static TabContainer getTabContainer(TabContent tab) {
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