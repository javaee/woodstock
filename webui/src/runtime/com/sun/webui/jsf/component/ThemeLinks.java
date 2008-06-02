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
@Component(type="com.sun.webui.jsf.ThemeLinks", family="com.sun.webui.jsf.ThemeLinks", displayName="ThemeLinks", tagName="themeLinks",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_theme_links",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_theme_links_props")
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
     * @deprecated Use isStyleSheet
     */
    public boolean isStyleSheetLink() {
        return this.styleSheetLink;
    }

    /**
     * Setter for property styleSheetLink.
     * @param styleSheetLink New value of property styleSheetLink.
     * @deprecated Use setStyleSheet
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
     * Flag (true or false) indicating that debugging is enabled. This will 
     * output uncompressed JavaScript and enable debugging. The default is
     * false.
     */
    @Property(name="debug", displayName="Enable Debugging", category="Javascript")
    private boolean debug = false;
    private boolean debug_set = false;

    /**
     * Test flag indicating that debugging is enabled.
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
        return this.debug;
    }
    
    /**
     * Set flag indicating that debugging is enabled.
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
        this.debug_set = true;
    }

    /**
     * Flag (true or false) indicating to include all dijit functionality. 
     * <p>
     * For better performance, only the most commonly used features are 
     * included by default. Dojo will continue to function, but 
     * JavaScript may be retrieved using separate requests.
     * </p><p>
     * To limit the number of JavaScript requests, set dijitAll to true when
     * this functionality is needed. The default is false.
     * </p>
     * @deprecated Dojo is no longer included in the page.
     */
    @Property(name="dijitAll", displayName="Include All Dijit Functionality", category="Javascript", isHidden=true)
    private boolean dijitAll = false; 
    private boolean dijitAll_set = false; 
 
    /**
     * Test flag indicating to include all Dojo dijit functionality. 
     * 
     * @deprecated Dojo is no longer included in the page.
     */
    public boolean isDijitAll() { 
        if (this.dijitAll_set) {
            return this.dijitAll;
        }
        ValueExpression _vb = getValueExpression("dijitAll");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return this.dijitAll;
    } 

    /**
     * Set flag indicating to include all Dojo dijit functionality. 
     * 
     * @deprecated Dojo is no longer included in the page.
     */
    public void setDijitAll(boolean dijitAll) {
        this.dijitAll = dijitAll;
        this.dijitAll_set = true;
    }

    /**
     * Flag (true or false) indicating that component JavaScript should be 
     * output in page. The default value is true.
     */
    @Property(name="javaScript", displayName="Include Component JavaScript", category="Javascript")
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
        return this.javaScript;
    }

    /**
     * Flag (true or false) indicating that component and third party JavaScript
     * should be output in page. The default value is true.
     */
    public void setJavaScript(boolean javaScript) {
        this.javaScript = javaScript;
        this.javaScript_set = true;
    }

    /**
     * Flag (true or false) indicating to include JSF Extensions.
     * <p>
     * For better performance, JSF Extensions may be lazily loaded only when
     * Ajax functionality is needed. When referencing DynaFaces or Prototype 
     * APIs directly, use the JSF Extensions <jsfx:scripts> tag to include these
     * resources when the page is loaded. For example:
     * <p><pre>
     * <%@ taglib prefix="jsfx" uri="http://java.sun.com/jsf/extensions/dynafaces" %>
     * <f:view>
     * <html>
     *   <head>
     *     <jsfx:scripts />
     *   </head>
     *   <body> <!-- body omitted --> </body>
     * </html>
     * </f:view>
     * </pre></p><p>
     * In this case, set the jsfx property to false to prevent Woodstock from 
     * including JSF Extension resources.
     * </p>
     */
    @Property(name="jsfx", displayName="Include JSF Extensions", category="Javascript")
    private boolean jsfx = true; 
    private boolean jsfx_set = false; 
 
    /**
     * Test flag indicating to include JSF Extensions.
     */
    public boolean isJsfx() { 
        if (this.jsfx_set) {
            return this.jsfx;
        }
        ValueExpression _vb = getValueExpression("jsfx");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return this.jsfx;
    } 

    /**
     * Set flag indicating to include JSF Extensions.
     */
    public void setJsfx(boolean jsfx) {
        this.jsfx = jsfx;
        this.jsfx_set = true;
    }

    /**
     * Register a function to be called after the DOM has finished loading and
     * widgets declared in markup have been instantiated.
     */
    @Property(name="onLoad", displayName="Load Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onLoad = null;

    /**
     * Get a function to be called after the DOM has finished loading and
     * widgets declared in markup have been instantiated.
     */
    public String getOnLoad() {
        if (this.onLoad != null) {
            return this.onLoad;
        }
        ValueExpression _vb = getValueExpression("onLoad");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Register a function to be called after the DOM has finished loading and
     * widgets declared in markup have been instantiated.
     */
    public void setOnLoad(String onLoad) {
        this.onLoad = onLoad;
    }

    /**
     * Flag (true or false) indicating to parse HTML markup.
     * <p>
     * For better performance, HTML markup is parsed in order to create widgets 
     * more efficiently. Performance testing shows this approach is quicker than
     * using the document.getElementById() function or Level 0 DOM syntax, 
     * especially for large HTML tables. The underlying problem appears to be 
     * the extra step taken to convert HTML element IDs to a UTF-16 character 
     * encoding.
     * </p><p>
     * The parseOnLoad approach allows for progressive HTML rendering. However, 
     * partially rendered HTML may be displayed before widgets have been 
     * created. If this behavior is undesirable, set the parseOnLoad property to
     * false and widgets shall be rendered inline.
     * </p>
     */
    @Property(name="parseOnLoad", displayName="Enable Dojo Parsing", category="Javascript")
    private boolean parseOnLoad = true; 
    private boolean parseOnLoad_set = false; 
 
    /**
     * Test flag indicating to parse HTML markup.
     */
    public boolean isParseOnLoad() { 
        if (this.parseOnLoad_set) {
            return this.parseOnLoad;
        }
        ValueExpression _vb = getValueExpression("parseOnLoad");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return this.parseOnLoad;
    } 

    /**
     * Set flag indicating to parse HTML markup.
     */
    public void setParseOnLoad(boolean parseOnLoad) {
        this.parseOnLoad = parseOnLoad;
        this.parseOnLoad_set = true;
    }

    /**
     * <p>If set to true, a link element with a reference to the theme
     * stylesheet resource is rendered.</p>
     */
    @Property(name="styleSheet", displayName="Include StyleSheet Link", category="Advanced")
    private boolean styleSheet = true;
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
        return this.styleSheet;
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
     * @deprecated Not well supported by browsers.
     */
    @Property(name="styleSheetInline", displayName="Include StyleSheet Definitions Inline", category="Advanced", isHidden=true)
    private boolean styleSheetInline = false;
    private boolean styleSheetInline_set = false;

    /**
     * <p>If set to true, the theme stylesheet contents will be rendered inline 
     * instead of being linked to.</p>
     * @deprecated Not well supported by browsers.
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
     * @deprecated Not well supported by browsers.
     */
    public void setStyleSheetInline(boolean styleSheetInline) {
        this.styleSheetInline = styleSheetInline;
        this.styleSheetInline_set = true;
    }

    /**
     * Flag (true or false) indicating to include all widgets. 
     * <p>
     * For better performance, Javascript is included in the page for only the
     * most commonly used widgets (default). Other widgets will continue to 
     * function, but additional requests may be generated to include more 
     * resources. Set the webuiAll property to true to include JavaScript 
     * resources when the page is loaded, using a single request. Use this 
     * feature in combination with the webuiAjax property to include default 
     * Ajax functionality based on JSF Extensions.
     * </p>
     */
    @Property(name="webuiAll", displayName="Include All Widgets", category="Javascript")
    private boolean webuiAll = false; 
    private boolean webuiAll_set = false; 
 
    /**
     * Test flag indicating to include all webui functionality.
     */
    public boolean isWebuiAll() { 
        if (this.webuiAll_set) {
            return this.webuiAll;
        }
        ValueExpression _vb = getValueExpression("webuiAll");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return this.webuiAll;
    } 

    /**
     * Flag indicating to include all webui functionality.
     */
    public void setWebuiAll(boolean webuiAll) {
        this.webuiAll = webuiAll;
        this.webuiAll_set = true;
    }

    /**
     * Flag (true or false) indicating to include default Ajax functionality 
     * based on JSF Extensions.
     * <p>
     * For better performance, Ajax functionality is not included in the page
     * by default. Ajax features will continue to function, but additional 
     * requests are lazily generated to retrieve JavaScript resources as needed, 
     * including JSF Extensions. Set the webuiJsfx property to true to include 
     * JavaScript resources when the page is loaded, using a single request. Use
     * this feature in combination with the webuiAll property to include all tag
     * library functionality. 
     * </p>
     * @deprecated Use webuiAjax
     */
    @Property(name="webuiJsfx", displayName="Include Ajax Functionality", category="Javascript")
 
    /**
     * Test flag indicating to include default Ajax functionality.
     * @deprecated Use isWebuiAjax
     */
    public boolean isWebuiJsfx() { 
        if (this.webuiAjax_set) {
            return this.webuiAjax;
        }
        ValueExpression _vb = getValueExpression("webuiJsfx");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return this.webuiAjax;
    } 

    /**
     * Set flag indicating to include default Ajax functionality.
     * @deprecated Use setWebuiAjax
     */
    public void setWebuiJsfx(boolean webuiJsfx) {
        setWebuiAjax(webuiJsfx);
    }

    /**
     * Flag (true or false) indicating to include default Ajax functionality 
     * based on JSF Extensions.
     * <p>
     * For better performance, Ajax functionality is not included in the page
     * by default. Ajax features will continue to function, but additional 
     * requests are lazily generated to retrieve JavaScript resources as needed, 
     * including JSF Extensions. Set the webuiAjax property to true to include 
     * JavaScript resources when the page is loaded, using a single request. Use
     * this feature in combination with the webuiAll property to include all tag
     * library functionality. 
     * </p>
     */
    @Property(name="webuiAjax", displayName="Include Ajax Functionality", category="Javascript")
    private boolean webuiAjax = false; 
    private boolean webuiAjax_set = false; 
 
    /**
     * Test flag indicating to include default Ajax functionality.
     */
    public boolean isWebuiAjax() { 
        if (this.webuiAjax_set) {
            return this.webuiAjax;
        }
        ValueExpression _vb = getValueExpression("webuiAjax");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return this.webuiAjax;
    } 

    /**
     * Set flag indicating to include default Ajax functionality.
     */
    public void setWebuiAjax(boolean webuiAjax) {
        this.webuiAjax = webuiAjax;
        this.webuiAjax_set = true;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.javaScript = ((Boolean) _values[1]).booleanValue();
        this.javaScript_set = ((Boolean) _values[2]).booleanValue();
        this.jsfx = ((Boolean) _values[3]).booleanValue();
        this.jsfx_set = ((Boolean) _values[4]).booleanValue();
        this.styleSheet = ((Boolean) _values[5]).booleanValue();
        this.styleSheet_set = ((Boolean) _values[6]).booleanValue();
        this.styleSheetInline = ((Boolean) _values[7]).booleanValue();
        this.styleSheetInline_set = ((Boolean) _values[8]).booleanValue();
        this.debug = ((Boolean) _values[9]).booleanValue();
        this.debug_set = ((Boolean) _values[10]).booleanValue();
        this.dijitAll = ((Boolean) _values[11]).booleanValue();
        this.dijitAll_set = ((Boolean) _values[12]).booleanValue();
        this.onLoad = (String) _values[13];
        this.parseOnLoad = ((Boolean) _values[14]).booleanValue();
        this.parseOnLoad_set = ((Boolean) _values[15]).booleanValue();
        this.webuiAll = ((Boolean) _values[16]).booleanValue();
        this.webuiAll_set = ((Boolean) _values[17]).booleanValue();
        this.webuiAjax = ((Boolean) _values[18]).booleanValue();
        this.webuiAjax_set = ((Boolean) _values[19]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[20];
        _values[0] = super.saveState(_context);
        _values[1] = this.javaScript ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.javaScript_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.jsfx ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.jsfx_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.styleSheet ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.styleSheet_set ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.styleSheetInline ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.styleSheetInline_set ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = this.debug ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = this.debug_set ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.dijitAll ? Boolean.TRUE : Boolean.FALSE;
        _values[12] = this.dijitAll_set ? Boolean.TRUE : Boolean.FALSE;
        _values[13] = this.onLoad;
        _values[14] = this.parseOnLoad ? Boolean.TRUE : Boolean.FALSE;
        _values[15] = this.parseOnLoad_set ? Boolean.TRUE : Boolean.FALSE;
        _values[16] = this.webuiAll ? Boolean.TRUE : Boolean.FALSE;
        _values[17] = this.webuiAll_set ? Boolean.TRUE : Boolean.FALSE;
        _values[18] = this.webuiAjax ? Boolean.TRUE : Boolean.FALSE;
        _values[19] = this.webuiAjax_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
