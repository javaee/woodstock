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

import javax.el.ValueExpression;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

/**
 * The Head component is used to provide information to be used in the head 
 * element.
 */
@Component(type="com.sun.webui.jsf.Head", family="com.sun.webui.jsf.Head", displayName="Head", tagName="head",
    helpKey="projrave_ui_elements_palette_html_elements_head",
    propertiesHelpKey="projrave_ui_elements_palette_html_elements_propsheets_head_props")
public class Head extends UIComponentBase {

    /**
     *Title facet identifier
     */
     public static final String TITLE_FACET="title"; 

    /**
     * <p>Construct a new <code>Head</code>.</p>
     */
    public Head() {
        super();
        setRendererType("com.sun.webui.jsf.Head");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Head";
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
     * <p>Flag (true or false) indicating that a default html base tag should be
     * shown or not. Changing this attribute could cause webuijsf:anchor to not
     * work properly. The default value is false.</p>
     */
    @Property(name="defaultBase", displayName="Default Base", category="Appearance")
    private boolean defaultBase = false;
    private boolean defaultBase_set = false;

    /**
     * <p>Flag (true or false) indicating that a default html base tag should be
     * shown or not. Changing this attribute could cause webuijsf:anchor to not work
     * properly. The default value is false.</p>
     */
    public boolean isDefaultBase() {
        if (this.defaultBase_set) {
            return this.defaultBase;
        }
        ValueExpression _vb = getValueExpression("defaultBase");
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
     * <p>Flag (true or false) indicating that a default html base tag should be
     * shown or not. Changing this attribute could cause webuijsf:anchor to not
     * work properly. The default value is false.</p>
     * @see #isDefaultBase()
     */
    public void setDefaultBase(boolean defaultBase) {
        this.defaultBase = defaultBase;
        this.defaultBase_set = true;
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
     * Flag (true or false) indicating that component and third party JavaScript
     * should be output in page. The default value is true.
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
     * Set flag indicating that component JavaScript should be output in page.
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
     * Flag (true or false) indicating if meta data should be rendered. The 
     * default value is true.
     * 
     * @deprecated Using meta tags and pragma headers are not very effective for
     * broswer caching. In fact, they are only honored by a few browser caches.
     * For more information, see http://www.mnot.net/cache_docs.
     */
    @Property(name="meta", displayName="Render Meta Data", category="Advanced")
    private boolean meta = false;
    private boolean meta_set = false;

    /**
     * Test flag indicating if the default meta data should be rendered.
     * 
     * @deprecated Using meta tags and pragma headers are not very effective for
     * broswer caching. In fact, they are only honored by a few browser caches.
     * For more information, see http://www.mnot.net/cache_docs/#META.
     */
    public boolean isMeta() {
        if (this.meta_set) {
            return this.meta;
        }
        ValueExpression _vb = getValueExpression("meta");
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
     * Set flag indicating if the default meta data should be rendered.
     * 
     * @deprecated Using meta tags and pragma headers are not very effective for
     * broswer caching. In fact, they are only honored by a few browser caches.
     * For more information, see http://www.mnot.net/cache_docs/#META.
     */
    public void setMeta(boolean meta) {
        this.meta = meta;
        this.meta_set = true;
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
     * <p>A space separated list of URL's that contains meta data information 
     * about the page</p>
     */
    @Property(name="profile", displayName="Profile", category="Advanced")
    private String profile = null;

    /**
     * <p>A space separated list of URL's that contains meta data information 
     * about the page</p>
     */
    public String getProfile() {
        if (this.profile != null) {
            return this.profile;
        }
        ValueExpression _vb = getValueExpression("profile");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>A space separated list of URL's that contains meta data information 
     * about the page</p>
     * @see #getProfile()
     */
    public void setProfile(String profile) {
        this.profile = profile;
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
     * <p>Title of the document to be displayed in the browser title bar.</p>
     */
    @Property(name="title", displayName="title", category="Appearance", isDefault=true)
    private String title = null;

    /**
     * <p>Title of the document to be displayed in the browser title bar.</p>
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
     * <p>Title of the document to be displayed in the browser title bar.</p>
     * @see #getTitle()
     */
    public void setTitle(String title) {
        this.title = title;
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
        this.defaultBase = ((Boolean) _values[1]).booleanValue();
        this.defaultBase_set = ((Boolean) _values[2]).booleanValue();
        this.profile = (String) _values[3];
        this.title = (String) _values[4];
        this.debug = ((Boolean) _values[5]).booleanValue();
        this.debug_set = ((Boolean) _values[6]).booleanValue();
        this.dijitAll = ((Boolean) _values[7]).booleanValue();
        this.dijitAll_set = ((Boolean) _values[8]).booleanValue();
        this.javaScript = ((Boolean) _values[9]).booleanValue();
        this.javaScript_set = ((Boolean) _values[10]).booleanValue();
        this.jsfx = ((Boolean) _values[11]).booleanValue();
        this.jsfx_set = ((Boolean) _values[12]).booleanValue();
        this.meta = ((Boolean) _values[13]).booleanValue();
        this.meta_set = ((Boolean) _values[14]).booleanValue();
        this.onLoad = (String) _values[15];
        this.parseOnLoad = ((Boolean) _values[16]).booleanValue();
        this.parseOnLoad_set = ((Boolean) _values[17]).booleanValue();
        this.webuiAll = ((Boolean) _values[18]).booleanValue();
        this.webuiAll_set = ((Boolean) _values[19]).booleanValue();
        this.webuiAjax = ((Boolean) _values[20]).booleanValue();
        this.webuiAjax_set = ((Boolean) _values[21]).booleanValue();
        this.styleSheet = ((Boolean) _values[22]).booleanValue();
        this.styleSheet_set = ((Boolean) _values[23]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[24];
        _values[0] = super.saveState(_context);
        _values[1] = this.defaultBase ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.defaultBase_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.profile;
        _values[4] = this.title;
        _values[5] = this.debug ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.debug_set ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.dijitAll ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.dijitAll_set ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = this.javaScript ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = this.javaScript_set ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.jsfx ? Boolean.TRUE : Boolean.FALSE;
        _values[12] = this.jsfx_set ? Boolean.TRUE : Boolean.FALSE;
        _values[13] = this.meta ? Boolean.TRUE : Boolean.FALSE;
        _values[14] = this.meta_set ? Boolean.TRUE : Boolean.FALSE;
        _values[15] = this.onLoad;
        _values[16] = this.parseOnLoad ? Boolean.TRUE : Boolean.FALSE;
        _values[17] = this.parseOnLoad_set ? Boolean.TRUE : Boolean.FALSE;
        _values[18] = this.webuiAll ? Boolean.TRUE : Boolean.FALSE;
        _values[19] = this.webuiAll_set ? Boolean.TRUE : Boolean.FALSE;
        _values[20] = this.webuiAjax ? Boolean.TRUE : Boolean.FALSE;
        _values[21] = this.webuiAjax_set ? Boolean.TRUE : Boolean.FALSE;
        _values[22] = this.styleSheet ? Boolean.TRUE : Boolean.FALSE;
        _values[23] = this.styleSheet_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
