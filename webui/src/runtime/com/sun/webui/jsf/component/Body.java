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
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.FocusManager;
import com.sun.webui.jsf.util.LogUtil;

import com.sun.webui.theme.Theme;

/**
 * The Body component is used to contain the other components of the page.
 */
@Component(type="com.sun.webui.jsf.Body", family="com.sun.webui.jsf.Body", displayName="Body", tagName="body",
    helpKey="projrave_ui_elements_palette_html_elements_body",
    propertiesHelpKey="projrave_ui_elements_palette_html_elements_propsheets_body_props")
public class Body extends UIComponentBase {
    
    /**
     * @deprecated
     */
    public static final String FOCUS_PARAM = 
	    "com.sun.webui.jsf_body_focusComponent";
    /**
     * @deprecated
     */
    public static final String JAVASCRIPT_OBJECT = "_jsObject";

    /**
     * Default constructor.
     */
    public Body() {
        super();
        setRendererType("com.sun.webui.jsf.Body");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Body";
    }

    /**
     * @deprecated
     * @see #getFocus
     */
    public String getFocusID(FacesContext context) {

	// Note that this code is duplicated in 
	// Body renderer because we don't want to 
	// reference a deprecated method.
	//
	String id = getFocus();
        if (id != null || id.length() == 0) {
            // Need absolute id.
	    //
	    String absid = id;
	    if (id.charAt(0) != NamingContainer.SEPARATOR_CHAR) {
		absid = 
		    String.valueOf(NamingContainer.SEPARATOR_CHAR).concat(id);
	    }
	    try {
		// Since a developer using setFocus may not be able to
		// identify a sub component of a ComplexComponent, that
		// must be done here.
		//
		UIComponent comp = findComponent(absid);
		if (comp != null && comp instanceof ComplexComponent) {
		    id = ((ComplexComponent) comp).getFocusElementId(context);
		}
	    } catch (Exception e) {
		if (LogUtil.finestEnabled()) {
		    LogUtil.finest("Body.getFocusId, couldn't find " +
			" component with id " + id);
		}
	    }
        } else {
            // Get client id cached in request map -- bugtraq #6316565.
            // Note: This must be a client Id to identify table children.
	    // This interface is expected to be the actual id and does
	    // not require checking for ComplexComponent.
	    //
            id = FocusManager.getRequestFocusElementId(context);
        }        
        return id;
    }

    /**
     * @deprecated
     */
    public String getJavaScriptObjectName(FacesContext context) {
        return getClientId(context).replace(':', '_').concat(JAVASCRIPT_OBJECT);
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
     * <p>Specify the ID of the component that should receive 
     * focus when the page is loaded. If the focus attribute is not set, or if the value
     * is null, no component has focus when the page is initially rendered. If
     * the page is submitted and reloaded, the component that submitted the page 
     * receives focus.  By setting the focus attribute, you can ensure that
     * a particular component receives focus each time.</p>
     */
    @Property(name="focus", displayName="Component to receive focus", category="Behavior", isDefault=true)
    private String focus = null;

    /**
     * <p>Specify the ID of the component that should receive 
     * focus when the page is loaded. If the focus attribute is not set, or if the value
     * is null, no component has focus when the page is initially rendered. If
     * the page is submitted and reloaded, the component that submitted the page 
     * receives focus.  By setting the focus attribute, you can ensure that
     * a particular component receives focus each time.</p>
     */
    public String getFocus() {
        if (this.focus != null) {
            return this.focus;
        }
        ValueExpression _vb = getValueExpression("focus");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Specify the ID of the component that should receive 
     * focus when the page is loaded. If the focus attribute is not set, or if the value
     * is null, no component has focus when the page is initially rendered. If
     * the page is submitted and reloaded, the component that submitted the page 
     * receives focus.  By setting the focus attribute, you can ensure that
     * a particular component receives focus each time.</p>
     * @see #getFocus()
     */
    public void setFocus(String focus) {
        this.focus = focus;
    }

    /**
     * <p>The path to an image to be used as a background for the page. </p>
     */
    @Property(name="imageURL", displayName="Image URL", category="Appearance", editorClassName="com.sun.rave.propertyeditors.ImageUrlPropertyEditor")
    private String imageURL = null;

     /**
     * <p>The path to an image to be used as a background for the page. </p>
     */
    public String getImageURL() {
        if (this.imageURL != null) {
            return this.imageURL;
        }
        ValueExpression _vb = getValueExpression("imageURL");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The path to an image to be used as a background for the page.</p>
     * @see #getImageURL()
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * <p>Scripting code executed when this element loses focus.</p>
     */
    @Property(name="onBlur", displayName="Blur Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onBlur = null;

    /**
     * <p>Scripting code executed when this element loses focus.</p>
     */
    public String getOnBlur() {
        if (this.onBlur != null) {
            return this.onBlur;
        }
        ValueExpression _vb = getValueExpression("onBlur");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when this element loses focus.</p>
     * @see #getOnBlur()
     */
    public void setOnBlur(String onBlur) {
        this.onBlur = onBlur;
    }

    /**
     * <p>Scripting code executed when a mouse click
     * occurs over this component.</p>
     */
    @Property(name="onClick", displayName="Click Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onClick = null;

    /**
     * <p>Scripting code executed when a mouse click
     * occurs over this component.</p>
     */
    public String getOnClick() {
        if (this.onClick != null) {
            return this.onClick;
        }
        ValueExpression _vb = getValueExpression("onClick");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when a mouse click
     * occurs over this component.</p>
     * @see #getOnClick()
     */
    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    /**
     * <p>Scripting code that executes when a mouse double click
     * occurs over this component.</p>
     */
    @Property(name="onDblClick", displayName="Double Click Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onDblClick = null;

    /**
     * <p>Scripting code that executes when a mouse double click
     * occurs over this component.</p>
     */
    public String getOnDblClick() {
        if (this.onDblClick != null) {
            return this.onDblClick;
        }
        ValueExpression _vb = getValueExpression("onDblClick");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code that executes when a mouse double click
     * occurs over this component.</p>
     * @see #getOnDblClick()
     */
    public void setOnDblClick(String onDblClick) {
        this.onDblClick = onDblClick;
    }

    /**
     * <p>Scripting code that executes when this component  receives focus. An
     * element receives focus when the user selects the element by pressing
     * the tab key or clicking the mouse.</p>
     */
    @Property(name="onFocus", displayName="Focus Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onFocus = null;

    /**
     * <p>Scripting code that executes when this component  receives focus. An
     * element receives focus when the user selects the element by pressing
     * the tab key or clicking the mouse.</p>
     */
    public String getOnFocus() {
        if (this.onFocus != null) {
            return this.onFocus;
        }
        ValueExpression _vb = getValueExpression("onFocus");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code that executes when this component  receives focus. An
     * element receives focus when the user selects the element by pressing
     * the tab key or clicking the mouse.</p>
     * @see #getOnFocus()
     */
    public void setOnFocus(String onFocus) {
        this.onFocus = onFocus;
    }

    /**
     * <p>Scripting code that executes when the user presses down on a key while the
     * component has focus.</p>
     */
    @Property(name="onKeyDown", displayName="Key Down Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyDown = null;

    /**
     * <p>Scripting code that executes when the user presses down on a key while the
     * component has focus.</p>
     */
    public String getOnKeyDown() {
        if (this.onKeyDown != null) {
            return this.onKeyDown;
        }
        ValueExpression _vb = getValueExpression("onKeyDown");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code that executes when the user presses down on a key while the
     * component has focus.</p>
     * @see #getOnKeyDown()
     */
    public void setOnKeyDown(String onKeyDown) {
        this.onKeyDown = onKeyDown;
    }

    /**
     * <p>Scripting code that executes when the user presses and releases a key while
     * the component has focus.</p>
     */
    @Property(name="onKeyPress", displayName="Key Press Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyPress = null;

    /**
     * <p>Scripting code that executes when the user presses and releases a key while
     * the component has focus.</p>
     */
    public String getOnKeyPress() {
        if (this.onKeyPress != null) {
            return this.onKeyPress;
        }
        ValueExpression _vb = getValueExpression("onKeyPress");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code that executes when the user presses and releases a key while
     * the component has focus.</p>
     * @see #getOnKeyPress()
     */
    public void setOnKeyPress(String onKeyPress) {
        this.onKeyPress = onKeyPress;
    }

    /**
     * <p>Scripting code that executes when the user releases a key while the
     * component has focus.</p>
     */
    @Property(name="onKeyUp", displayName="Key Up Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyUp = null;

    /**
     * <p>Scripting code that executes when the user releases a key while the
     * component has focus.</p>
     */
    public String getOnKeyUp() {
        if (this.onKeyUp != null) {
            return this.onKeyUp;
        }
        ValueExpression _vb = getValueExpression("onKeyUp");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code that executes when the user releases a key while the
     * component has focus.</p>
     * @see #getOnKeyUp()
     */
    public void setOnKeyUp(String onKeyUp) {
        this.onKeyUp = onKeyUp;
    }

    /**
     * <p>Scripting code that executes when when this page is loaded in a browser.</p>
     */
    @Property(name="onLoad", displayName="Onload Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onLoad = null;

    /**
     * <p>Scripting code that executes when when this page is loaded in a browser.</p>
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
     * <p>Scripting code that executes when when this page is loaded in a browser.</p>
     * @see #getOnLoad()
     */
    public void setOnLoad(String onLoad) {
        this.onLoad = onLoad;
    }

    /**
     * <p>Scripting code that executes when the user presses a mouse button while the
     * mouse pointer is on the component.</p>
     */
    @Property(name="onMouseDown", displayName="Mouse Down Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseDown = null;

    /**
     * <p>Scripting code that executes when the user presses a mouse button while the
     * mouse pointer is on the component.</p>
     */
    public String getOnMouseDown() {
        if (this.onMouseDown != null) {
            return this.onMouseDown;
        }
        ValueExpression _vb = getValueExpression("onMouseDown");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code that executes when the user presses a mouse button while the
     * mouse pointer is on the component.</p>
     *
     * @see #getOnMouseDown()
     */
    public void setOnMouseDown(String onMouseDown) {
        this.onMouseDown = onMouseDown;
    }

    /**
     * <p>Scripting code that executes when the user moves the mouse pointer while
     * over the component.</p>
     */
    @Property(name="onMouseMove", displayName="Mouse Move Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseMove = null;

    /**
     * <p>Scripting code that executes when the user moves the mouse pointer while
     * over the component.</p>
     */
    public String getOnMouseMove() {
        if (this.onMouseMove != null) {
            return this.onMouseMove;
        }
        ValueExpression _vb = getValueExpression("onMouseMove");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code that executes when the user moves the mouse pointer while
     * over the component.</p>
     * @see #getOnMouseMove()
     */
    public void setOnMouseMove(String onMouseMove) {
        this.onMouseMove = onMouseMove;
    }

    /**
     * <p>Scripting code that executes when a mouse out movement
     * occurs over this component.</p>
     */
    @Property(name="onMouseOut", displayName="Mouse Out Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseOut = null;

    /**
     * <p>Scripting code that executes when a mouse out movement
     * occurs over this component.</p>
     */
    public String getOnMouseOut() {
        if (this.onMouseOut != null) {
            return this.onMouseOut;
        }
        ValueExpression _vb = getValueExpression("onMouseOut");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code that executes when a mouse out movement
     * occurs over this component.</p>
     * @see #getOnMouseOut()
     */
    public void setOnMouseOut(String onMouseOut) {
        this.onMouseOut = onMouseOut;
    }

    /**
     * <p>Scripting code that executes when the user moves the  mouse pointer into
     * the boundary of this component.</p>
     */
    @Property(name="onMouseOver", displayName="Mouse In Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseOver = null;

    /**
     * <p>Scripting code that executes when the user moves the  mouse pointer into
     * the boundary of this component.</p>
     */
    public String getOnMouseOver() {
        if (this.onMouseOver != null) {
            return this.onMouseOver;
        }
        ValueExpression _vb = getValueExpression("onMouseOver");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code that executes when the user moves the  mouse pointer into
     * the boundary of this component.</p>
     * @see #getOnMouseOver()
     */
    public void setOnMouseOver(String onMouseOver) {
        this.onMouseOver = onMouseOver;
    }

    /**
     * <p>Scripting code that executes when the user releases a mouse button while
     * the mouse pointer is on the component.</p>
     */
    @Property(name="onMouseUp", displayName="Mouse Up Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseUp = null;

    /**
     * <p>Scripting code that executes when the user releases a mouse button while
     * the mouse pointer is on the component.</p>
     */
    public String getOnMouseUp() {
        if (this.onMouseUp != null) {
            return this.onMouseUp;
        }
        ValueExpression _vb = getValueExpression("onMouseUp");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code that executes when the user releases a mouse button while
     * the mouse pointer is on the component.</p>
     * @see #getOnMouseUp()
     */
    public void setOnMouseUp(String onMouseUp) {
        this.onMouseUp = onMouseUp;
    }

    /**
     * <p>Scripting code that executes when this page is unloaded from a browser as a user exits the page.</p>
     */
    @Property(name="onUnload", displayName="Unload Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onUnload = null;

    /**
     * <p>Scripting code that executes when this page is unloaded from a browser as a user exits the page.</p>
     */
    public String getOnUnload() {
        if (this.onUnload != null) {
            return this.onUnload;
        }
        ValueExpression _vb = getValueExpression("onUnload");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code that executes when this page is unloaded from a browser as a user exits the page.</p>
     * @see #getOnUnload()
     */
    public void setOnUnload(String onUnload) {
        this.onUnload = onUnload;
    }

    /**
     * <p>Indicates  whether the last 
     * element to have the focus, receives the focus the
     * next time the page is rendered. If this is set to <code>true</code> the
     * focus is preserved; if set to <code>false</code> it is not.
     * If set to <code>false</code> and the <code>focus</code> attribute
     * is set then the element identified by that id receives the
     * focus. The default value is true.</p>
     */
    @Property(name="preserveFocus", displayName="Preserve Focus", category="Behavior")
    private boolean preserveFocus = true;
    private boolean preserveFocus_set = false;

    /**
     * <p>Indicates  whether the last 
     * element to have the focus, receives the focus the
     * next time the page is rendered. If this is set to <code>true</code> the
     * focus is preserved; if set to <code>false</code> it is not.
     * If set to <code>false</code> and the <code>focus</code> attribute
     * is set then the element identified by that id receives the
     * focus. The default value is true.</p>
     */
    public boolean isPreserveFocus() {
        if (this.preserveFocus_set) {
            return this.preserveFocus;
        }
        ValueExpression _vb = getValueExpression("preserveFocus");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
	// Get the default behavior from the theme.
	//
	String defaultPreserveFocus = null;
	Theme theme =
	    ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
	try {
	    defaultPreserveFocus = theme.getMessage("body.preserveFocus");
	    if (defaultPreserveFocus != null) {
		return Boolean.valueOf(defaultPreserveFocus).booleanValue();
	    }
	} catch (Exception e) {
	}
        return this.preserveFocus;
    }

    /**
     * <p>Indicates  whether the last 
     * element to have the focus, receives the focus the
     * next time the page is rendered. If this is set to <code>true</code> the
     * focus is preserved; if set to <code>false</code> it is not.
     * If set to <code>false</code> and the <code>focus</code> attribute
     * is set then the element identified by that id receives the
     * focus. The default value is true.</p>
     */
    public void setPreserveFocus(boolean preserveFocus) {
        this.preserveFocus = preserveFocus;
        this.preserveFocus_set = true;
    }

    /**
     * <p>Use the preserveScroll attribute to indicate whether the page
     * should remember the scroll position when navigating to and from
     * the page. The default value is <code>true</code></p>
     */
    @Property(name="preserveScroll", displayName="Preserve Scroll Position", category="Behavior")
    private boolean preserveScroll = true;
    private boolean preserveScroll_set = false;

    /**
     * <p>Use the preserveScroll attribute to indicate whether the page
     * should remember the scroll position when navigating to and from
     * the page. The default value is <code>true</code></p>
     */
    public boolean isPreserveScroll() {
        if (this.preserveScroll_set) {
            return this.preserveScroll;
        }
        ValueExpression _vb = getValueExpression("preserveScroll");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
	// Get the default behavior from the theme.
	//
	String defaultPreserveScroll = null;
	Theme theme =
	    ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
	try {
	    defaultPreserveScroll = theme.getMessage("body.preserveScroll");
	    if (defaultPreserveScroll != null) {
		return Boolean.valueOf(defaultPreserveScroll).booleanValue();
	    }
	} catch (Exception e) {
	}
        return this.preserveScroll;
    }

    /**
     * <p>Use the preserveScroll attribute to indicate whether the page
     * should remember the scroll position when navigating to and from
     * the page. The default value is <code>true</code></p>
     */
    public void setPreserveScroll(boolean preserveScroll) {
        this.preserveScroll = preserveScroll;
        this.preserveScroll_set = true;
    }

    /**
     * <p>CSS style or styles to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name="style", displayName="CSS Style(s)", category="Appearance", editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;

    /**
     * <p>CSS style or styles to be applied to the outermost HTML element when this 
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
     * <p>CSS style or styles to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     * @see #getStyle()
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * <p>CSS style class or classes to be applied to the outermost HTML element when the body
     * component is rendered.</p>
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;

    /**
     * <p>CSS style class or classes to be applied to the outermost HTML element when the body
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
     * <p>CSS style class or classes to be applied to the outermost HTML element when the body
     * component is rendered.</p>
     *
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * <p>Indicates whether the component should be
     * viewable by the user in the rendered HTML page. If this is set to false, the
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
        this.focus = (String) _values[1];
        this.imageURL = (String) _values[2];
        this.onBlur = (String) _values[3];
        this.onClick = (String) _values[4];
        this.onDblClick = (String) _values[5];
        this.onFocus = (String) _values[6];
        this.onKeyDown = (String) _values[7];
        this.onKeyPress = (String) _values[8];
        this.onKeyUp = (String) _values[9];
        this.onLoad = (String) _values[10];
        this.onMouseDown = (String) _values[11];
        this.onMouseMove = (String) _values[12];
        this.onMouseOut = (String) _values[13];
        this.onMouseOver = (String) _values[14];
        this.onMouseUp = (String) _values[15];
        this.onUnload = (String) _values[16];
        this.style = (String) _values[17];
        this.styleClass = (String) _values[18];
        this.visible = ((Boolean) _values[19]).booleanValue();
        this.visible_set = ((Boolean) _values[20]).booleanValue();
	this.preserveFocus = ((Boolean) _values[21]).booleanValue();
	this.preserveFocus_set = ((Boolean) _values[22]).booleanValue();
	this.preserveScroll = ((Boolean) _values[23]).booleanValue();
	this.preserveScroll_set = ((Boolean) _values[24]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[25];
        _values[0] = super.saveState(_context);
        _values[1] = this.focus;
        _values[2] = this.imageURL;
        _values[3] = this.onBlur;
        _values[4] = this.onClick;
        _values[5] = this.onDblClick;
        _values[6] = this.onFocus;
        _values[7] = this.onKeyDown;
        _values[8] = this.onKeyPress;
        _values[9] = this.onKeyUp;
        _values[10] = this.onLoad;
        _values[11] = this.onMouseDown;
        _values[12] = this.onMouseMove;
        _values[13] = this.onMouseOut;
        _values[14] = this.onMouseOver;
        _values[15] = this.onMouseUp;
        _values[16] = this.onUnload;
        _values[17] = this.style;
        _values[18] = this.styleClass;
        _values[19] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[20] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
	_values[21] = this.preserveFocus ? Boolean.TRUE : Boolean.FALSE;
	_values[22] = this.preserveFocus_set ? Boolean.TRUE : Boolean.FALSE;
	_values[23] = this.preserveScroll ? Boolean.TRUE : Boolean.FALSE;
	_values[24] = this.preserveScroll_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
