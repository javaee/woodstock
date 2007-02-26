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
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

/**
 * The HelpWindow component displays a link that opens a popup window for a help
 * system.
 */
@Component(type="com.sun.webui.jsf.HelpWindow", family="com.sun.webui.jsf.HelpWindow", displayName="Help Window", tagName="helpWindow",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_help_window",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_help_window_props")
public class HelpWindow extends IconHyperlink {
    public static final String DEFAULT_JSP_PATH =
        "/com_sun_webui_jsf/help/";
    
    public static final String DEFAULT_NAVIGATION_JSP = "navigator.jsp";
    public static final String DEFAULT_STATUS_JSP = "status.jsp";
    public static final String DEFAULT_BUTTONNAV_JSP = "buttonnav.jsp";
    public static final String DEFAULT_BUTTONFRAME_JSP = "buttonFrame.jsp";
    public static final String DEFAULT_TIPS_FILE = "tips.jsp";
    /** Creates a new instance of HelpWindow */
    public HelpWindow() {
        super();        
        setRendererType("com.sun.webui.jsf.HelpWindow");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.HelpWindow";
    }

    private String _getUrl() {
        
        if (super.getUrl() != null) {
            return super.getUrl();
        }
        //
        // Every request to a JSF based JSP page
        // should be checked to ensure that it invokes 
        // "ViewHandler.getActionURL(FacesContext ctx, String viewId)".  
        // This is the *only* way this should be implemented as it fixes the bug, 
        // and follows JSF's design allowing the ViewHandler to translate 
        // the url as required.                      
        
        String jspPath = DEFAULT_JSP_PATH;
        FacesContext context = FacesContext.getCurrentInstance();
        
        // Path prefix if set should be done from the JavaHelpBackingBean only. 
        // Having two different places to set the same info is confusing. By default
        // it is assumed that the path is set to <appcontext>/com_sun_webui_jsf/help.
        // If developer wants the path prefix to be set to anything else the default
        // should be overidden by supplying a managed bean property for "jspPath".
        // The assumtion here is that all help related data reside in the same
        // place for a given app.
        
        Application app = context.getApplication();
        ValueExpression vb = 
            app.getExpressionFactory().createValueExpression(getFacesContext()
                .getELContext(), "#{JavaHelpBean.jspPath}", Object.class);
            
        if (vb.getValue(context.getELContext()) != null) {                
            jspPath = ((String) vb.getValue(context.getELContext())).concat(jspPath);                
        }
        
        StringBuffer url = new StringBuffer(jspPath);
        url.append("helpwindow.jsp");
        
        // renderer will assign the required request parameters and after 
        // invoking handler.getActionUrl()
        
        return url.toString();
    }
    
    private String _getIcon() {        
        if (isLinkIcon()) {
            // return the default help window link icon
            return ThemeImages.HREF_LINK;
        }
        
        // don't display an icon
        return ThemeImages.DOT;
    }
    
    private Object _getText() {
        if (super.getText() != null) {
            return super.getText();
        }
        
        Theme t = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        return t.getMessage("help.help");
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * <p>Return the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property aliases.</p>
     *
     * @param name Name of value binding expression to retrieve
     */
    public ValueExpression getValueExpression(String name) {
        if (name.equals("linkText")) {
            return super.getValueExpression("text");
        }
        return super.getValueExpression(name);
    }

    /**
     * <p>Set the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property
     * aliases.</p>
     *
     * @param name    Name of value binding to set
     * @param binding ValueExpression to set, or null to remove
     */
    public void setValueExpression(String name,ValueExpression binding) {
        if (name.equals("linkText")) {
            super.setValueExpression("text", binding);
            return;
        }
        super.setValueExpression(name, binding);
    }
    
    // Hide actionExpression
    @Property(name="actionExpression", isHidden=true, isAttribute=false)
    public MethodExpression getActionExpression() {
        return super.getActionExpression();
    }

    /**
     * Scripting code executed when a mouse double click occurs over this
     * component.
     */
    @Property(name="onDblClick", isHidden=false, isAttribute=true)
    public String getOnDblClick() {
        return super.getOnDblClick();
    }
    
    // Hide actionListenerExpression
    @Property(name="actionListenerExpression", isHidden=true, isAttribute=false)
    public MethodExpression getActionListenerExpression() {
        return super.getActionListenerExpression();
    }
    
    // Hide Align
    @Property(name="Align", isHidden=true, isAttribute=false)
    public String getAlign() {
        return super.getAlign();
    }
    
    // Hide alt
    @Property(name="alt", isHidden=true, isAttribute=false)
    public String getAlt() {
        return super.getAlt();
    }
    
    // Hide border
    @Property(name="border", isHidden=true, isAttribute=false)
    public int getBorder() {
        return super.getBorder();
    }
    
    // Hide height
    @Property(name="height", isHidden=true, isAttribute=false)
    public int getHeight() {
        return super.getHeight();
    }
    
    // Hide hspace
    @Property(name="hspace", isHidden=true, isAttribute=false)
    public int getHspace() {
        return super.getHspace();
    }
    
    // Hide icon
    @Property(name="icon", isHidden=true, isAttribute=false)
    public String getIcon() {
        return _getIcon();
    }
    
    // Hide imageURL
    @Property(name="imageURL", isHidden=true, isAttribute=false)
    public String getImageURL() {
        return super.getImageURL();
    }
    
    // Hide immediate
    @Property(name="immediate", isHidden=true, isAttribute=false)
    public boolean isImmediate() {
        return super.isImmediate();
    }
    
    // Hide target
    @Property(name="target", isHidden=false, isAttribute=true)
    public String getTarget() {
        if (super.getTarget() != null) {
            return super.getTarget();
        }
        
        return "help_window";
    }
    
    public void setTarget(String value) {
        super.setTarget(value);
    }
    
    // Hide text
    @Property(name="text", isHidden=true, isAttribute=false,
        editorClassName="com.sun.rave.propertyeditors.StringPropertyEditor")
    public Object getText() {
        return _getText();
    }
    
    // hide textPosition
    @Property(name="textPosition", isHidden=true, isAttribute=false)
    public String getTextPosition() {
        return super.getTextPosition();
    }
    
    // Hide type
    @Property(name="type", isHidden=true, isAttribute=false)
    public String getType() {
        return super.getType();
    }
    
    // Hide url
    @Property(name="url", isHidden=true, isAttribute=false)
    public String getUrl() {
        return _getUrl();
    }
    
    // Hide urlLang
    @Property(name="urlLang", isHidden=true, isAttribute=false)
    public String getUrlLang() {
        return super.getUrlLang();
    }
    
    // Hide value
    @Property(name="value", isHidden=true, isAttribute=false)
    public Object getValue() {
        return super.getValue();
    }
    
    // Hide vspace
    @Property(name="vspace", isHidden=true, isAttribute=false)
    public int getVspace() {
        return super.getVspace();
    }

    /**
     * <p>The help file to be displayed in the help window content 
     * frame when the help link is clicked. The value can be a relative path or 
     * a file name.</p>
     */
    @Property(name="helpFile", displayName="Help File")
    private String helpFile = null;

    public String getHelpFile() {
        if (this.helpFile != null) {
            return this.helpFile;
        }
        ValueExpression _vb = getValueExpression("helpFile");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The help file to be displayed in the help window content 
     * frame when the help link is clicked. The value can be a relative path or 
     * a file name.</p>
     * @see #getHelpFile()
     */
    public void setHelpFile(String helpFile) {
        this.helpFile = helpFile;
    }

    /**
     * <p>The context relative path to the help set to be displayed. This attribute
     * overrides any value set for the helpSetPath property in the 
     * application's HelpBackingBean instance.</p>
     */
    @Property(name="helpSetPath", displayName="Help Set Path")
    private String helpSetPath = null;

    public String getHelpSetPath() {
        if (this.helpSetPath != null) {
            return this.helpSetPath;
        }
        ValueExpression _vb = getValueExpression("helpSetPath");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The context relative path to the help set to be displayed. This attribute
     * overrides any value set for the helpSetPath property in the 
     * application's HelpBackingBean instance.</p>
     * @see #getHelpSetPath()
     */
    public void setHelpSetPath(String helpSetPath) {
        this.helpSetPath = helpSetPath;
    }

    // linkIcon
    /**
     * <p>Set linkIcon to true to display the default icon in front of the 
     * text for the help window link. The icon is useful in inline help
     * links to the help window. By default the value is false.</p>
     */
    @Property(name="linkIcon", displayName="Link Icon")
    private boolean linkIcon = false;
    private boolean linkIcon_set = false;

    public boolean isLinkIcon() {
        if (this.linkIcon_set) {
            return this.linkIcon;
        }
        ValueExpression _vb = getValueExpression("linkIcon");
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
     * <p>Set linkIcon to true to display the default icon in front of the 
     * text for the help window link. The icon is useful in inline help
     * links to the help window. By default the value is false.</p>
     * @see #isLinkIcon()
     */
    public void setLinkIcon(boolean linkIcon) {
        this.linkIcon = linkIcon;
        this.linkIcon_set = true;
    }

    /**
     * <p>The text to display for the hyperlink that opens the help window.</p>
     */
    @Property(name="linkText", displayName="Link Text")
    public String getLinkText() {
        return (String) getText();
    }

    /**
     * <p>The text to display for the hyperlink that opens the help window.</p>
     * @see #getLinkText()
     */
    public void setLinkText(String linkText) {
        setText((Object) linkText);
    }

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name="style", displayName="CSS Style(s)")
    private String style = null;

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
    @Property(name="styleClass", displayName="CSS Style Class(es)")
    private String styleClass = null;

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
     * <p>The text to display in the browser window frame for the help window. 
     * This text is rendered in the HTML title element.</p>
     */
    @Property(name="windowTitle", displayName="Help Window Title")
    private String windowTitle = null;

    public String getWindowTitle() {
        if (this.windowTitle != null) {
            return this.windowTitle;
        }
        ValueExpression _vb = getValueExpression("windowTitle");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        // else return the default help window title.
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        return theme.getMessage("help.help");
    }

    /**
     * <p>The text to display in the browser window frame for the help window. 
     * This text is rendered in the HTML title element.</p>
     * @see #getWindowTitle()
     */
    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }

    /**
     * <p> If developer defined onClick handler is not specified
     * return the javascript code segment that defines the
     * default helpWindow behaviour.</p>
     */
    public String getOnClick() {
        String clickHandler = super.getOnClick();
        if (clickHandler != null && clickHandler.length() > 0) {
            return clickHandler;
        } else {
            StringBuffer onClick = new StringBuffer("javascript: ");
            onClick.append("var win = window.open('','")
                .append(getTarget())
                .append("','height=500,")
                .append("width=750,top='+((screen.height-(screen.height/1.618))-")
                .append("(500/2))+',left='+((screen.width-750)/2)+',resizable');")
                .append("win.focus()");
            return onClick.toString();
        }
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.helpFile = (String) _values[1];
        this.helpSetPath = (String) _values[2];
        this.linkIcon = ((Boolean) _values[3]).booleanValue();
        this.linkIcon_set = ((Boolean) _values[4]).booleanValue();
        this.style = (String) _values[5];
        this.styleClass = (String) _values[6];
        this.visible = ((Boolean) _values[7]).booleanValue();
        this.visible_set = ((Boolean) _values[8]).booleanValue();
        this.windowTitle = (String) _values[9];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[10];
        _values[0] = super.saveState(_context);
        _values[1] = this.helpFile;
        _values[2] = this.helpSetPath;
        _values[3] = this.linkIcon ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.linkIcon_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.style;
        _values[6] = this.styleClass;
        _values[7] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = this.windowTitle;
        return _values;
    }
}
