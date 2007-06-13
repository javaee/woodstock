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
import com.sun.webui.jsf.util.ComponentUtilities;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * The Button component is used to display an input button.
 */
@Component(type="com.sun.webui.jsf.Button",
    family="com.sun.webui.jsf.Button",
    tagRendererType="com.sun.webui.jsf.widget.Button",
    displayName="Button", tagName="button",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_button",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_button_props")
public class Button extends WebuiCommand implements ComplexComponent {
    /** The component id for button contents. */
    public static final String CONTENTS_ID = "_contents"; //NOI18N

    /**
     * Default constructor.
     */
    public Button() {
        super();
        setRendererType("com.sun.webui.jsf.widget.Button");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Button";
    }

    public String getRendererType() {
        // Ensure we have a valid Ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.Button";
        }
        return super.getRendererType();
    }

    /**
     * Returns the absolute ID of an HTML element suitable for use as
     * the value of an HTML LABEL element's <code>for</code> attribute.
     * If the <code>ComplexComponent</code> has sub-compoents, and one of 
     * the sub-components is the target of a label, if that sub-component
     * is a <code>ComplexComponent</code>, then
     * <code>getLabeledElementId</code> must called on the sub-component and
     * the value returned. The value returned by this 
     * method call may or may not resolve to a component instance.
     *
     * @param context The FacesContext used for the request
     * @return An abolute id suitable for the value of an HTML LABEL element's
     * <code>for</code> attribute.
     */
    public String getLabeledElementId(FacesContext context) {
        return getClientId(context);
    }

    /**
     * Returns the id of an HTML element suitable to
     * receive the focus.
     * If the <code>ComplexComponent</code> has sub-compoents, and one of 
     * the sub-components is to reveive the focus, if that sub-component
     * is a <code>ComplexComponent</code>, then
     * <code>getFocusElementId</code> must called on the sub-component and
     * the value returned. The value returned by this 
     * method call may or may not resolve to a component instance.
     * <p>
     * This implementation returns the value of
     * <code>getLabeledElementId</code>.
     * </p>
     *
     * @param context The FacesContext used for the request
     */
    public String getFocusElementId(FacesContext context) {
	return getLabeledElementId(context);
    }

    /**
     * Return a component instance that can be referenced
     * by a <code>Label</code> in order to evaluate the <code>required</code>
     * and <code>valid</code> states of this component.
     *
     * <em>This implementation returns <code>null</code>. <code>Button</code>
     * does not support the required or valid states</em>
     *
     * @param context The current <code>FacesContext</code> instance
     * @param label The <code>Label</code> that labels this component.
     * @return a <code>UIComponent</code> in order to evaluate the
     * required and valid states.
     */
    public UIComponent getIndicatorComponent(FacesContext context,
	    Label label) {
	return null;
    }

    /**
     * Implement this method so that it returns the DOM ID of the 
     * HTML element which should receive focus when the component 
     * receives focus, and to which a component label should apply. 
     * Usually, this is the first element that accepts input. 
     * 
     * @param context The FacesContext for the request
     * @return The client id, also the JavaScript element id
     *
     * @deprecated
     * @see #getLabeledElementId
     * @see #getFocusElementId
     */
    public String getPrimaryElementID(FacesContext context) {
         return getLabeledElementId(context);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Overwrite value annotation
    @Property(name="value", isHidden=true, isAttribute=false)
    public Object getValue() {
        return super.getValue();
    }

    /**
     * <p>Return the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property aliases.</p>
     *
     * @param name Name of value binding expression to retrieve
     */
    public ValueExpression getValueExpression(String name) {
        if (name.equals("text")) {
            return super.getValueExpression("value");
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
        if (name.equals("text")) {
            super.setValueExpression("value", binding);
            return;
        }
        super.setValueExpression(name, binding);
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
     * <p>Indicates that activation of this component by the user is not currently 
     * permitted. In this component library, the disabled attribute also causes 
     * the button to be renderered using a particular style.</p>
     */
    @Property(name="disabled", displayName="Disabled", category="Behavior")
    private boolean disabled = false;
    private boolean disabled_set = false;

    /**
     * <p>Indicates that activation of this component by the user is not currently 
     * permitted. In this component library, the disabled attribute also causes 
     * the button to be renderered using a particular style.</p>
     */
    public boolean isDisabled() {
        if (this.disabled_set) {
            return this.disabled;
        }
        ValueExpression _vb = getValueExpression("disabled");
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
     * <p>Indicates that activation of this component by the user is not currently 
     * permitted. In this component library, the disabled attribute also causes 
     * the button to be renderered using a particular style.</p>
     * @see #isDisabled()
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
        this.disabled_set = true;
    }

    /**
     * <p>Escape the html text so it won't be interpreted by the browser as HTML. When 
     * the <code>escape</code> value is set to false, an HTML <code>button</code> 
     * element is rendered, instead of an HTML <code>input</code> element. And the 
     * <code>alt</code> attribute does not apply.</p>
     */
    @Property(name="escape", displayName="Escape", category="Appearance")
    private boolean escape = false;
    private boolean escape_set = false;

    /**
     * <p>Escape the html text so it won't be interpreted by the browser as HTML. When 
     * the <code>escape</code> value is set to false, an HTML <code>button</code> 
     * element is rendered, instead of an HTML <code>input</code> element. And the 
     * <code>alt</code> attribute does not apply.</p>
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
     * <p>Escape the html text so it won't be interpreted by the browser as HTML. When 
     * the <code>escape</code> value is set to false, an HTML <code>button</code> 
     * element is rendered, instead of an HTML <code>input</code> element. And the 
     * <code>alt</code> attribute does not apply.</p>
     * @see #isEscape()
     */
    public void setEscape(boolean escape) {
        this.escape = escape;
        this.escape_set = true;
    }

    /**
     * Alternative HTML template to be used by this component.
     */
    @Property(name="htmlTemplate", isHidden=true, isAttribute=true, displayName="HTML Template", category="Appearance")
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
     * <p>The identifier key of a theme image to be used for the button. </p>
     */
    @Property(name="icon", displayName="Icon", category="Appearance", isHidden=true, isAttribute=true)
    private String icon = null;

    /**
     * <p>The identifier key of a theme image to be used for the button. </p>
     */
    public String getIcon() {
        if (this.icon != null) {
            return this.icon;
        }
        ValueExpression _vb = getValueExpression("icon");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The identifier key of a theme image to be used for the button. </p>
     * @see #getIcon()
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * <p>Resource path of an image to be displayed to create the visual appearance of 
     * this button instead of the standard button image. Either the 
     * <code>imageURL</code> or <code>text</code> attributes must be specified. When 
     * an <code>imageURL</code> value is given, the button type is set to 
     * <code>image</code>.</p>
     */
    @Property(name="imageURL", displayName="Image URL", category="Appearance", editorClassName="com.sun.rave.propertyeditors.ImageUrlPropertyEditor")
    private String imageURL = null;

    /**
     * <p>Resource path of an image to be displayed to create the visual appearance of 
     * this button instead of the standard button image. Either the 
     * <code>imageURL</code> or <code>text</code> attributes must be specified. When 
     * an <code>imageURL</code> value is given, the button type is set to 
     * <code>image</code>.</p>
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
     * <p>Resource path of an image to be displayed to create the visual appearance of 
     * this button instead of the standard button image. Either the 
     * <code>imageURL</code> or <code>text</code> attributes must be specified. When 
     * an <code>imageURL</code> value is given, the button type is set to 
     * <code>image</code>.</p>
     * @see #getImageURL()
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * Indicates that the button should be rendered using a different style 
     * than normal buttons. By default, a button that specifies the mini
     * attribute looks the same as a normal button. You must set your
     * own CSS style to render a mini button. 
     */
    @Property(name="mini", displayName="Is Mini", category="Appearance")
    private boolean mini = false;
    private boolean mini_set = false;

    /**
     * Indicates that the button should be rendered using a different style 
     * than normal buttons. By default, a button that specifies the mini
     * attribute looks the same as a normal button. You must set your
     * own CSS style to render a mini button. 
     */
    public boolean isMini() {
        if (this.mini_set) {
            return this.mini;
        }
        ValueExpression _vb = getValueExpression("mini");
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
     * Indicates that the button should be rendered using a different style 
     * than normal buttons. By default, a button that specifies the mini
     * attribute looks the same as a normal button. You must set your
     * own CSS style to render a mini button. 
     * @see #isMini()
     */
    public void setMini(boolean mini) {
        this.mini = mini;
        this.mini_set = true;
    }

    /**
     * <p>Indicates that padding should not be applied to the button text. By 
     * default, whitespace characters are padded to button text greater than 
     * or equal to 4 characters in length. If the value is set to true, no 
     * padding is applied.</p>
     */
    @Property(name="noTextPadding", displayName="No Text Padding", category="Appearance")
    private boolean noTextPadding = false;
    private boolean noTextPadding_set = false;

    /**
     * <p>Indicates that padding should not be applied to the button text. By 
     * default, whitespace characters are padded to button text greater than 
     * or equal to 4 characters in length. If the value is set to true, no 
     * padding is applied.</p>
     */
    public boolean isNoTextPadding() {
        if (this.noTextPadding_set) {
            return this.noTextPadding;
        }
        ValueExpression _vb = getValueExpression("noTextPadding");
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
     * <p>Indicates that padding should not be applied to the button text. By 
     * default, whitespace characters are padded to button text greater than 
     * or equal to 4 characters in length. If the value is set to true, no 
     * padding is applied.</p>
     * @see #isNoTextPadding()
     */
    public void setNoTextPadding(boolean noTextPadding) {
        this.noTextPadding = noTextPadding;
        this.noTextPadding_set = true;
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
     * <p>Scripting code executed when a mouse double click
     * occurs over this component.</p>
     */
    @Property(name="onDblClick", displayName="Double Click Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onDblClick = null;

    /**
     * <p>Scripting code executed when a mouse double click
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
     * <p>Scripting code executed when a mouse double click
     * occurs over this component.</p>
     * @see #getOnDblClick()
     */
    public void setOnDblClick(String onDblClick) {
        this.onDblClick = onDblClick;
    }

    /**
     * <p>Scripting code executed when this component  receives focus. An
     * element receives focus when the user selects the element by pressing
     * the tab key or clicking the mouse.</p>
     */
    @Property(name="onFocus", displayName="Focus Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onFocus = null;

    /**
     * <p>Scripting code executed when this component  receives focus. An
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
     * <p>Scripting code executed when this component  receives focus. An
     * element receives focus when the user selects the element by pressing
     * the tab key or clicking the mouse.</p>
     * @see #getOnFocus()
     */
    public void setOnFocus(String onFocus) {
        this.onFocus = onFocus;
    }

    /**
     * <p>Scripting code executed when the user presses down on a key while the
     * component has focus.</p>
     */
    @Property(name="onKeyDown", displayName="Key Down Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyDown = null;

    /**
     * <p>Scripting code executed when the user presses down on a key while the
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
     * <p>Scripting code executed when the user presses down on a key while the
     * component has focus.</p>
     * @see #getOnKeyDown()
     */
    public void setOnKeyDown(String onKeyDown) {
        this.onKeyDown = onKeyDown;
    }

    /**
     * <p>Scripting code executed when the user presses and releases a key while
     * the component has focus.</p>
     */
    @Property(name="onKeyPress", displayName="Key Press Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyPress = null;

    /**
     * <p>Scripting code executed when the user presses and releases a key while
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
     * <p>Scripting code executed when the user presses and releases a key while
     * the component has focus.</p>
     * @see #getOnKeyPress()
     */
    public void setOnKeyPress(String onKeyPress) {
        this.onKeyPress = onKeyPress;
    }

    /**
     * <p>Scripting code executed when the user releases a key while the
     * component has focus.</p>
     */
    @Property(name="onKeyUp", displayName="Key Up Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyUp = null;

    /**
     * <p>Scripting code executed when the user releases a key while the
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
     * <p>Scripting code executed when the user releases a key while the
     * component has focus.</p>
     * @see #getOnKeyUp()
     */
    public void setOnKeyUp(String onKeyUp) {
        this.onKeyUp = onKeyUp;
    }

    /**
     * <p>Scripting code executed when the user presses a mouse button while the
     * mouse pointer is on the component.</p>
     */
    @Property(name="onMouseDown", displayName="Mouse Down Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseDown = null;

    /**
     * <p>Scripting code executed when the user presses a mouse button while the
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
     * <p>Scripting code executed when the user presses a mouse button while the
     * mouse pointer is on the component.</p>
     * @see #getOnMouseDown()
     */
    public void setOnMouseDown(String onMouseDown) {
        this.onMouseDown = onMouseDown;
    }

    /**
     * <p>Scripting code executed when the user moves the mouse pointer while
     * over the component.</p>
     */
    @Property(name="onMouseMove", displayName="Mouse Move Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseMove = null;

    /**
     * <p>Scripting code executed when the user moves the mouse pointer while
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
     * <p>Scripting code executed when the user moves the mouse pointer while
     * over the component.</p>
     * @see #getOnMouseMove()
     */
    public void setOnMouseMove(String onMouseMove) {
        this.onMouseMove = onMouseMove;
    }

    /**
     * <p>Scripting code executed when the user moves the mouse pointer off this component.</p>
     */
    @Property(name="onMouseOut", displayName="Mouse Out Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseOut = null;

    /**
     * <p>Scripting code executed when the user moves the mouse pointer off this component.</p>
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
     * <p>Scripting code executed when the user moves the mouse pointer off this component.</p>
     * @see #getOnMouseOut()
     */
    public void setOnMouseOut(String onMouseOut) {
        this.onMouseOut = onMouseOut;
    }

    /**
     * <p>Scripting code executed when the user moves the  mouse pointer into
     * the boundary of this component.</p>
     */
    @Property(name="onMouseOver", displayName="Mouse In Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseOver = null;

    /**
     * <p>Scripting code executed when the user moves the  mouse pointer into
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
     * <p>Scripting code executed when the user moves the  mouse pointer into
     * the boundary of this component.</p>
     * @see #getOnMouseOver()
     */
    public void setOnMouseOver(String onMouseOver) {
        this.onMouseOver = onMouseOver;
    }

    /**
     * <p>Scripting code executed when the user releases a mouse button while
     * the mouse pointer is on the component.</p>
     */
    @Property(name="onMouseUp", displayName="Mouse Up Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseUp = null;

    /**
     * <p>Scripting code executed when the user releases a mouse button while
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
     * <p>Scripting code executed when the user releases a mouse button while
     * the mouse pointer is on the component.</p>
     * @see #getOnMouseUp()
     */
    public void setOnMouseUp(String onMouseUp) {
        this.onMouseUp = onMouseUp;
    }

    /**
     * <p>Indicates that the button is the most commonly used button within a group.</p>
     */
    @Property(name="primary", displayName="Is Primary", category="Appearance")
    private boolean primary = false;
    private boolean primary_set = false;

    /**
     * <p>Indicates that the button is the most commonly used button within a group.</p>
     */
    public boolean isPrimary() {
        if (this.primary_set) {
            return this.primary;
        }
        ValueExpression _vb = getValueExpression("primary");
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
     * <p>Indicates that the button is the most commonly used button within a group.</p>
     * @see #isPrimary()
     */
    public void setPrimary(boolean primary) {
        this.primary = primary;
        this.primary_set = true;
    }

    /**
     * <p>Indicates that the button should be a HTML reset button. By default, 
     * this value is false and the button is created as a submit button. If the
     * value is set to true, no action listener will be invoked.</p>
     */
    @Property(name="reset", displayName="Is Reset", category="Behavior")
    private boolean reset = false;
    private boolean reset_set = false;

    /**
     * <p>Indicates that the button should be a HTML reset button. By default, 
     * this value is false and the button is created as a submit button. If the
     * value is set to true, no action listener will be invoked.</p>
     */
    public boolean isReset() {
        if (this.reset_set) {
            return this.reset;
        }
        ValueExpression _vb = getValueExpression("reset");
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
     * <p>Indicates that the button should be a HTML reset button. By default, 
     * this value is false and the button is created as a submit button. If the
     * value is set to true, no action listener will be invoked.</p>
     * @see #isReset()
     */
    public void setReset(boolean reset) {
        this.reset = reset;
        this.reset_set = true;
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
     * <p>Text to display on the button. Either the 
     * <code>imageURL</code> or <code>text</code> attributes must be specified. When 
     * an <code>imageURL</code> value is given, the button type is set to 
     * <code>image</code>.</p>
     */
    @Property(name="text", displayName="Button Text", category="Appearance", isDefault=true,
        editorClassName="com.sun.rave.propertyeditors.StringPropertyEditor")
    public Object getText() {
        return getValue();
    }

    /**
     * <p>Text to display on the button. Either the 
     * <code>imageURL</code> or <code>text</code> attributes must be specified. When 
     * an <code>imageURL</code> value is given, the button type is set to 
     * <code>image</code>.</p>
     * @see #getText()
     */
    public void setText(Object text) {
        setValue(text);
    }

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     */
    @Property(name="toolTip", displayName="Tool Tip", category="Behavior")
    private String toolTip = null;

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     */
    public String getToolTip() {
        if (this.toolTip != null) {
            return this.toolTip;
        }
        ValueExpression _vb = getValueExpression("toolTip");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     * @see #getToolTip()
     */
    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
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
        this.disabled = ((Boolean) _values[2]).booleanValue();
        this.disabled_set = ((Boolean) _values[3]).booleanValue();
        this.escape = ((Boolean) _values[4]).booleanValue();
        this.escape_set = ((Boolean) _values[5]).booleanValue();
        this.icon = (String) _values[6];
        this.imageURL = (String) _values[7];
        this.mini = ((Boolean) _values[8]).booleanValue();
        this.mini_set = ((Boolean) _values[9]).booleanValue();
        this.noTextPadding = ((Boolean) _values[10]).booleanValue();
        this.noTextPadding_set = ((Boolean) _values[11]).booleanValue();
        this.onBlur = (String) _values[12];
        this.onClick = (String) _values[13];
        this.onDblClick = (String) _values[14];
        this.onFocus = (String) _values[15];
        this.onKeyDown = (String) _values[16];
        this.onKeyPress = (String) _values[17];
        this.onKeyUp = (String) _values[18];
        this.onMouseDown = (String) _values[19];
        this.onMouseMove = (String) _values[20];
        this.onMouseOut = (String) _values[21];
        this.onMouseOver = (String) _values[22];
        this.onMouseUp = (String) _values[23];
        this.primary = ((Boolean) _values[24]).booleanValue();
        this.primary_set = ((Boolean) _values[25]).booleanValue();
        this.reset = ((Boolean) _values[26]).booleanValue();
        this.reset_set = ((Boolean) _values[27]).booleanValue();
        this.style = (String) _values[28];
        this.styleClass = (String) _values[29];
        this.tabIndex = ((Integer) _values[30]).intValue();
        this.tabIndex_set = ((Boolean) _values[31]).booleanValue();
        this.toolTip = (String) _values[32];
        this.visible = ((Boolean) _values[33]).booleanValue();
        this.visible_set = ((Boolean) _values[34]).booleanValue();
        this.htmlTemplate = (String) _values[35];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[36];
        _values[0] = super.saveState(_context);
        _values[1] = this.alt;
        _values[2] = this.disabled ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.disabled_set ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.escape ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.escape_set ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.icon;
        _values[7] = this.imageURL;
        _values[8] = this.mini ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = this.mini_set ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = this.noTextPadding ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.noTextPadding_set ? Boolean.TRUE : Boolean.FALSE;
        _values[12] = this.onBlur;
        _values[13] = this.onClick;
        _values[14] = this.onDblClick;
        _values[15] = this.onFocus;
        _values[16] = this.onKeyDown;
        _values[17] = this.onKeyPress;
        _values[18] = this.onKeyUp;
        _values[19] = this.onMouseDown;
        _values[20] = this.onMouseMove;
        _values[21] = this.onMouseOut;
        _values[22] = this.onMouseOver;
        _values[23] = this.onMouseUp;
        _values[24] = this.primary ? Boolean.TRUE : Boolean.FALSE;
        _values[25] = this.primary_set ? Boolean.TRUE : Boolean.FALSE;
        _values[26] = this.reset ? Boolean.TRUE : Boolean.FALSE;
        _values[27] = this.reset_set ? Boolean.TRUE : Boolean.FALSE;
        _values[28] = this.style;
        _values[29] = this.styleClass;
        _values[30] = new Integer(this.tabIndex);
        _values[31] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        _values[32] = this.toolTip;
        _values[33] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[34] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[35] = this.htmlTemplate;
        return _values;
    }
}
