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
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme;

import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

/**
 * The PageAlert component displays a full page alert.
 */
@Component(type="com.sun.webui.jsf.PageAlert", family="com.sun.webui.jsf.PageAlert", displayName="Page Alert", tagName="pageAlert",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_page_alert",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_page_alert_props")
public class PageAlert extends UIComponentBase implements NamingContainer {
    /**
     * The facets...
     */
    public static final String PAGEALERT_INPUT_FACET = "pageAlertInput"; //NOI18N
    public static final String PAGEALERT_TITLE_FACET = "pageAlertTitle"; //NOI18N
    public static final String PAGEALERT_BUTTONS_FACET = "pageAlertButtons"; //NOI18N
    public static final String PAGEALERT_SEPARATOR_FACET = "pageAlertSeparator"; //NOI18N
    public static final String PAGEALERT_IMAGE_FACET = "pageAlertImage"; //NOI18N

    /**
     * Default constructor.
     */
    public PageAlert() {
        super();
        setRendererType("com.sun.webui.jsf.PageAlert");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.PageAlert";
    }

    /** 
     * Get the page alert input facet.
     * 
     * @return A Back button (or a facet with buttons).
     */
    public UIComponent getPageAlertInput() {
	return getFacet(PAGEALERT_INPUT_FACET);
    }
    
    /**
     * Return a component that implements the page alert's title text.
     * If a facet named <code>pageAlertTitle</code> is found
     * that component is returned.</br>
     * If a facet is not found an <code>StaticText</code>
     * component instance is returned with the id</br>
     * <code>getId() + "_pageAlertTitle"</code>. The <code>StaticText</code>
     * instance is * intialized with the values from
     * <p>
     * <ul>
     * <li><code>getSafeTitle()</code>
     * </ul>
     * </p>
     * <p>
     * If a facet is not defined then the returned <code>StaticText</code>
     * component is created every time this method is called.
     * </p>
     * @return - pageAlertTitle facet or a StaticText instance
     */
    public UIComponent getPageAlertTitle() {
        UIComponent titleFacet = getFacet(PAGEALERT_TITLE_FACET);
        if (titleFacet != null) {
	    return titleFacet;
	}

        StaticText title= new StaticText();
	title.setId(
	    ComponentUtilities.createPrivateFacetId(this,
	    PAGEALERT_TITLE_FACET));
	title.setParent(this);
        title.setText(getSafeTitle());
        
        return title;
    }

    /** 
     * Get buttons for the Page Alert.
     * Return a set of buttons if they were sepecifed in tha facet
     * 
     * @return A Back button (or a facet with buttons).
     */
    public UIComponent getPageAlertButtons() {
	// First check if a buttons facet was defined 
	UIComponent buttonFacet = getFacet(PAGEALERT_BUTTONS_FACET);	
	return buttonFacet;
    }

    /**
     * Return a component that implements a page separator.
     * If a facet named <code>pageAlertSeparator</code> is found
     * that component is returned.</br>
     * If a facet is not found a <code>PageSeparator</code>
     * component instance is returned with the id</br>
     * <code>getId() + "_pageAlertSeparator"</code>.
     * <p>
     * If a facet is not defined then the returned <code>PageSeparator</code>
     * component is created every time this method is called.
     * </p>
     * @return - pageAlertSeparator facet or a PageSeparator instance
     */
    public UIComponent getPageAlertSeparator() {
	// First check if a pageAlertSeparator facet was defined 
	UIComponent separatorFacet = getFacet(PAGEALERT_SEPARATOR_FACET);
	if (separatorFacet != null) {
	    return separatorFacet;
	}

	PageSeparator separator = new PageSeparator();
	separator.setId(ComponentUtilities.createPrivateFacetId(this,
		PAGEALERT_SEPARATOR_FACET));
	separator.setParent(this);
	    
	return separator;
    }
    
    /**
     * Return a component that implements a page alert image.
     * If a facet named <code>pageAlertImage</code> is found
     * that component is returned.</br>
     * If a facet is not found an <code>Icon</code>
     * component instance is returned with the id</br>
     * <code>getId() + "_pageAlertImage"</code>.
     * The <code>Icon</code> instance returned is determined from the value
     * of <code>getType()</code>. If the returned value is not a 
     * recognized value, <code>ThemeImages.ALERT_ERROR_LARGE</code> is
     * used. The <code>Icon</code> instance is initialized with the value of
     * <code>getAlt()</code>
     * <p>
     * If a facet is not defined then the returned <code>Icon</code>
     * component is created every time this method is called.
     * </p>
     * @return - pageAlertImage facet or an Icon instance
     */
    public UIComponent getPageAlertImage() {
	// First check if a PAGEALERT_IMAGE_FACET  facet was defined 
	UIComponent imageFacet = getFacet(PAGEALERT_IMAGE_FACET);
	if (imageFacet != null) {
	    return imageFacet;
	}
            
	Icon icon = ThemeUtilities.getIcon(getTheme(), getIconIdentifier());
	String alt = getAlt();
	if (alt != null) { 
	    icon.setAlt(alt);
	}
	
	icon.setId(
	    ComponentUtilities.createPrivateFacetId(this,
	    PAGEALERT_IMAGE_FACET));
	icon.setParent(this);
	
	return icon;
    }
    
    public String getSafeTitle() {
        String title = getTitle();
        if (title == null) {
            title = getAlt();
            if (title == null) {
                title = "";
            }
        }
        return title;
    }
    
    private String getIconIdentifier() {
        String type = getType();
        if (type != null) {
            type = type.toLowerCase();

            if (type.startsWith("warn")) { // NOI18N
                return ThemeImages.ALERT_WARNING_LARGE;
            } else if (type.startsWith("ques")) { // NOI18N
                return ThemeImages.ALERT_HELP_LARGE;
            } else if (type.startsWith("info")) { // NOI18N
                return ThemeImages.ALERT_INFO_LARGE;
            }
        }
        return ThemeImages.ALERT_ERROR_LARGE;
    }
    /*
     * Utility to get theme.
     */
    private Theme getTheme() {
	return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * The component identifier for this component. This value must be unique 
     * within the closest parent component that is a naming container.
     */
    @Property(name="id") 
    public void setId(String id) {
        super.setId(id);
    }

    /**
     * Use the rendered attribute to indicate whether the HTML code for the
     * component should be included in the rendered HTML page. If set to false,
     * the rendered HTML page does not include the HTML for the component. If
     * the component is not rendered, it is also not processed on any subsequent
     * form submission.
     */
    @Property(name="rendered") 
    public void setRendered(boolean rendered) {
        super.setRendered(rendered);
    }

    /**
     * <p>Alternative textual description of the image rendered by this component. The alt
     * text can be used by screen readers and in tool tips, and when image display is turned off in
     * the web browser.</p>
     */
    @Property(name="alt", displayName="Alt Text", category="Accessibility")
    private String alt = null;

    /**
     * <p>Alternative textual description of the image rendered by this component. The alt
     * text can be used by screen readers and in tool tips, and when image display is turned off in
     * the web browser.</p>
     */
    public String getAlt() {
        if (this.alt != null) {
            return this.alt;
        }
        ValueExpression _vb = getValueExpression("alt");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Alternative textual description of the image rendered by this component. The alt
     * text can be used by screen readers and in tool tips, and when image display is turned off in
     * the web browser.</p>
     * @see #getAlt()
     */
    public void setAlt(String alt) {
        this.alt = alt;
    }

    /**
     * <p>Detailed message text for the alert. This message might include more information about the alert and instructions for what to do about the alert.</p>
     */
    @Property(name="detail", displayName="Detail Message", category="Appearance")
    private String detail = null;

    /**
     * <p>Detailed message text for the alert. This message might include more information about the alert and instructions for what to do about the alert.</p>
     */
    public String getDetail() {
        if (this.detail != null) {
            return this.detail;
        }
        ValueExpression _vb = getValueExpression("detail");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Detailed message text for the alert. This message might include more information about the alert and instructions for what to do about the alert.</p>
     * @see #getDetail()
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * <p>Flag indicating that the message text should be escaped so that it is 
     * not interpreted by the browser.</p>
     */
    @Property(name="escape", displayName="Escape", category="Data")
    private boolean escape = false;
    private boolean escape_set = false;

    /**
     * <p>Flag indicating that the message text should be escaped so that it is 
     * not interpreted by the browser.</p>
     */
    public boolean isEscape() {
        if (this.escape_set) {
            return this.escape;
        }
        ValueExpression _vb = getValueExpression("escape");
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
     * <p>Flag indicating that the message text should be escaped so that it is 
     * not interpreted by the browser.</p>
     * @see #isEscape()
     */
    public void setEscape(boolean escape) {
        this.escape = escape;
        this.escape_set = true;
    }

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name="style", displayName="CSS Style(s)", category="Appearance", editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
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
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     * @see #getStyle()
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;

    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
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
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * <b>Deprecated.</b><br/><i>Use the title attribute to display the message summary in the page title.</i>
     */
    @Property(name="summary", displayName="Summary Message", category="Appearance")
    private String summary = null;

    /**
     * <b>Deprecated.</b><br/><i>Use the title attribute to display the message summary in the page title.</i>
     */
    public String getSummary() {
        if (this.summary != null) {
            return this.summary;
        }
        ValueExpression _vb = getValueExpression("summary");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <b>Deprecated.</b><br/><i>Use the title attribute to display the message summary in the page title.</i>
     * @see #getSummary()
     */
    public void setSummary(String summary) {
        this.summary = summary;
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
     * <p>The text to display as the page title</p>
     */
    @Property(name="title", displayName="Title", category="Appearance", isDefault=true)
    private String title = null;

    /**
     * <p>The text to display as the page title</p>
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
     * <p>The text to display as the page title</p>
     * @see #getTitle()
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * <p>The type or category of alert. The type attribute can be set to one of the following:  "question", "information", "warning" or "error". The default type is error.</p>
     */
    @Property(name="type", displayName="Alert Type", category="Advanced")
    private String type = null;

    /**
     * <p>The type or category of alert. The type attribute can be set to one of the following:  "question", "information", "warning" or "error". The default type is error.</p>
     */
    public String getType() {
        if (this.type != null) {
            return this.type;
        }
        ValueExpression _vb = getValueExpression("type");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return "error";
    }

    /**
     * <p>The type or category of alert. The type attribute can be set to one of the following:  "question", "information", "warning" or "error". The default type is error.</p>
     * @see #getType()
     */
    public void setType(String type) {
        this.type = type;
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
    @Property(name="visible", displayName="Visible", category="Behavior")
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
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.alt = (String) _values[1];
        this.detail = (String) _values[2];
        this.escape = ((Boolean) _values[3]).booleanValue();
        this.escape_set = ((Boolean) _values[4]).booleanValue();
        this.style = (String) _values[5];
        this.styleClass = (String) _values[6];
        this.summary = (String) _values[7];
        this.tabIndex = ((Integer) _values[8]).intValue();
        this.tabIndex_set = ((Boolean) _values[9]).booleanValue();
        this.title = (String) _values[10];
        this.type = (String) _values[11];
        this.visible = ((Boolean) _values[12]).booleanValue();
        this.visible_set = ((Boolean) _values[13]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[14];
        _values[0] = super.saveState(_context);
        _values[1] = this.alt;
        _values[2] = this.detail;
        _values[3] = this.escape ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.escape_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.style;
        _values[6] = this.styleClass;
        _values[7] = this.summary;
        _values[8] = new Integer(this.tabIndex);
        _values[9] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = this.title;
        _values[11] = this.type;
        _values[12] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[13] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
