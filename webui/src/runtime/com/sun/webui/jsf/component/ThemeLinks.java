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
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

/**
 * The ThemeLicnks component is used to create references to theme resources on 
 * a page in a portlet environment, where the Head component cannot be used.
 */
@Component(type = "com.sun.webui.jsf.ThemeLinks", family = "com.sun.webui.jsf.ThemeLinks",
displayName = "ThemeLinks", tagName = "themeLinks",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_theme_links",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_theme_links_props")
public class ThemeLinks extends UIComponentBase {

    /**
     * Holds value of property styleSheetLink.
     */
    private boolean styleSheetLink = true;

    /**
     * Default constructor.
     */
    public ThemeLinks() {
        super();
        setRendererType("com.sun.webui.jsf.ThemeLinks");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.ThemeLinks";
    }

    /**
     * Getter for property styleSheetLink.
     * @return Value of property styleSheetLink.
     */
    public boolean isStyleSheetLink() {
        return this.styleSheetLink;
    }

    /**
     * Setter for property styleSheetLink.
     * @param styleSheetLink New value of property styleSheetLink.
     */
    public void setStyleSheetLink(boolean styleSheetLink) {
        this.styleSheetLink = styleSheetLink;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * The component identifier for this component. This value must be unique 
     * within the closest parent component that is a naming container.
     */
    @Property(name = "id")
    @Override
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
    @Property(name = "rendered")
    @Override
    public void setRendered(boolean rendered) {
        super.setRendered(rendered);
    }
    /**
     * Flag (true or false) indicating that Dojo debugging is enabled. The 
     * default value is false.
     */
    @Property(name = "debug", displayName = "Enable Dojo Debugging", category = "Advanced")
    private boolean debug = false;
    private boolean debug_set = false;

    /**
     * Test flag indicating that Dojo debugging is enabled.
     */
    public boolean isDebug() {
        if (this.debug_set) {
            return this.debug;
        }
        ValueExpression _vb = getValueExpression("debug");
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
     * Set flag indicating that Dojo debugging is enabled.
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
        this.debug_set = true;
    }
    /**
     * Flag (true or false) indicating that component JavaScript should be 
     * output in page. The default value is true.
     */
    @Property(name = "javaScript", displayName = "Include Component JavaScript", category = "Advanced")
    private boolean javaScript = true;
    private boolean javaScript_set = false;

    /**
     * Test flag indicating that component JavaScript should be output in page.
     */
    public boolean isJavaScript() {
        if (this.javaScript_set) {
            return this.javaScript;
        }
        ValueExpression _vb = getValueExpression("javaScript");
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
     * Set flag indicating that component JavaScript should be output in page.
     */
    public void setJavaScript(boolean javaScript) {
        this.javaScript = javaScript;
        this.javaScript_set = true;
    }
    /**
     * Flag (true or false) indicating that Dojo should search for dojoType 
     * widget tags. Page load time is proportional to the number of nodes on the
     * page. The default value is false.
     */
    @Property(name = "parseWidgets", displayName = "Parse Dojo Widgets", category = "Advanced")
    private boolean parseWidgets = false;
    private boolean parseWidgets_set = false;

    /**
     * Test flag indicating that Dojo should search for dojoType widget tags.
     */
    public boolean isParseWidgets() {
        if (this.parseWidgets_set) {
            return this.parseWidgets;
        }
        ValueExpression _vb = getValueExpression("parseWidgets");
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
     * Set flag indicating that Dojo should search for dojoType widget tags.
     */
    public void setParseWidgets(boolean parseWidgets) {
        this.parseWidgets = parseWidgets;
        this.parseWidgets_set = true;
    }
    /**
     * <p>If set to true, a link element with a reference to the theme
     * stylesheet resource is rendered.</p>
     */
    @Property(name = "styleSheet", displayName = "Include StyleSheet Link", category = "Advanced")
    private boolean styleSheet = false;
    private boolean styleSheet_set = false;

    /**
     * <p>If set to true, a link element with a reference to the theme
     * stylesheet resource is rendered.</p>
     */
    public boolean isStyleSheet() {
        if (this.styleSheet_set) {
            return this.styleSheet;
        }
        ValueExpression _vb = getValueExpression("styleSheet");
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
     * <p>If set to true, a link element with a reference to the theme
     * stylesheet resource is rendered.</p>
     * @see #isStyleSheet()
     */
    public void setStyleSheet(boolean styleSheet) {
        this.styleSheet = styleSheet;
        this.styleSheet_set = true;
    }
    /**
     * <p>If set to true, the theme stylesheet contents will be rendered inline 
     * instead of being linked to.</p>
     */
    @Property(name = "styleSheetInline", displayName = "Include StyleSheet Definitions Inline", category = "Advanced")
    private boolean styleSheetInline = false;
    private boolean styleSheetInline_set = false;

    /**
     * <p>If set to true, the theme stylesheet contents will be rendered inline 
     * instead of being linked to.</p>
     */
    public boolean isStyleSheetInline() {
        if (this.styleSheetInline_set) {
            return this.styleSheetInline;
        }
        ValueExpression _vb = getValueExpression("styleSheetInline");
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
     * <p>If set to true, the theme stylesheet contents will be rendered inline 
     * instead of being linked to.</p>
     * @see #isStyleSheetInline()
     */
    public void setStyleSheetInline(boolean styleSheetInline) {
        this.styleSheetInline = styleSheetInline;
        this.styleSheetInline_set = true;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.javaScript = ((Boolean) _values[1]).booleanValue();
        this.javaScript_set = ((Boolean) _values[2]).booleanValue();
        this.styleSheet = ((Boolean) _values[3]).booleanValue();
        this.styleSheet_set = ((Boolean) _values[4]).booleanValue();
        this.styleSheetInline = ((Boolean) _values[5]).booleanValue();
        this.styleSheetInline_set = ((Boolean) _values[6]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[7];
        _values[0] = super.saveState(_context);
        _values[1] = this.javaScript ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.javaScript_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.styleSheet ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.styleSheet_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.styleSheetInline ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.styleSheetInline_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
