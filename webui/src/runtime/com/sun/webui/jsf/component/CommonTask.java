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


import java.util.List;
import java.util.Map;
import java.beans.Beans;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase; /* For javadoc */
import javax.faces.component.NamingContainer;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;


import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.ComponentUtilities;

import com.sun.webui.jsf.component.StaticText;
import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.jsf.component.ImageComponent;

import javax.el.ValueExpression;


/**
 * The CommonTask component is used to greate a single task within
 * a CommonTasksSection or CommonTasksGroup component.
 */
@Component(
        type="com.sun.webui.jsf.CommonTask",
        family="com.sun.webui.jsf.CommonTask",
        displayName="Common Task",
        instanceName="commonTask",
        tagName="commonTask",
        helpKey="projrave_ui_elements_palette_wdstk-jsf1_2_common_task",
        propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1_2_propsheets_common_task_props")
public class CommonTask extends com.sun.webui.jsf.component.WebuiCommand 
        implements NamingContainer{
    /**
     *Common Task facet identifier
     */
    public static final String COMMONTASK_FACET="taskAction";
    
    /**
     *Info panel facet identifier
     */
    public static final String INFOPANEL_FACET="infoPanel";
    
    /**
     *Info link facet identifier
     */
    public static final String INFOLINK_FACET="infoLink";
    
    /** Creates a new instance of Task */
    public CommonTask() {
        super();
        setRendererType("com.sun.webui.jsf.CommonTask");
    }
    
    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.CommonTask";
    }
    
    // Hide value
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

    // disabled
    @Property(name="disabled", displayName="Disabled", isHidden=true, isAttribute=false)
    private boolean disabled = false;
    private boolean disabled_set = false;

    /**
     * Indicates that activation of this component by the user is not currently
     * permitted.
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
     * Indicates that activation of this component by the user is not currently
     * permitted.
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
        this.disabled_set = true;
    }

    /**
     * <p>Specifies a theme key for an image to be displayed in front of the text for
     * the task. The key <code>CTS_OVERVIEW</code> will generate 
     * an image that can be used to mark tasks that are for overview information about the task</p>
     */
    @Property(name="icon", displayName="icon", category="Appearance")
    private String icon = null;

    /**
     * <p>Specifies a theme key for an image to be displayed in front of the text for
     * the task. The key <code>CTS_OVERVIEW</code> will generate 
     * an image that can be used to mark tasks that are for overview information about the task</p>
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
    * <p>Specifies a theme key for an image to be displayed in front of the text for
     * the task. The key <code>CTS_OVERVIEW</code> will generate 
     * an image that can be used to mark tasks that are for overview information about the task</p>
     * @see #getIcon()
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * <p>Specifies the height in pixels of the image that is specified with the imageUrl attribute
     * </p>
     */    
    @Property(name="imageHeight", displayName="imageHeight" , category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int imageHeight = Integer.MIN_VALUE;
    private boolean imageHeight_set = false;

    /**
     * <p>Specifies the height in pixels of the image that is specified with the imageUrl attribute
     * </p>
     */  
    public int getImageHeight() {
        if (this.imageHeight_set) {
            return this.imageHeight;
        }
        ValueExpression _vb = getValueExpression("imageHeight");
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
     * <p>Specifies the height in pixels of the image that is specified with the imageUrl attribute
     * </p>
     * @see #getImageHeight()
     */
    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
        this.imageHeight_set = true;
    }

    /**
     * <p>The path to an image to be displayed in front of the text 
     * for the task. If both icon and imageUrl are provided, the icon takes
     * precedence over the path specified for the image.</p>
     */
    @Property(name="imageUrl", displayName="imageURL", category="Appearance", editorClassName="com.sun.rave.propertyeditors.ImageUrlPropertyEditor")
    private String imageUrl = null;

    /**
     * <p>The path to an image to be displayed in front of the text 
     * for the task. If both icon and imageUrl are provided, the icon takes
     * precedence over the path specified for the image.</p>
     */
    public String getImageUrl() {
        if (this.imageUrl != null) {
            return this.imageUrl;
        }
        ValueExpression _vb = getValueExpression("imageUrl");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The path to an image to be displayed in front of the text 
     * for the task. If both icon and imageUrl are provided, the icon takes
     * precedence over the path specified for the image.</p>
     * @see #getImageUrl()
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * <p>Specifies the width in pixels of the image that is specified with the imageUrl attribute.
     * </p>
     */    
    @Property(name="imageWidth", displayName="imageWidth", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int imageWidth = Integer.MIN_VALUE;
    private boolean imageWidth_set = false;

    /**
     * <p>Specifies the width in pixels of the image that is specified with the imageUrl attribute.
     * </p>
     */
    public int getImageWidth() {
        if (this.imageWidth_set) {
            return this.imageWidth;
        }
        ValueExpression _vb = getValueExpression("imageWidth");
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
     * <p>Specifies the width in pixels of the image that is specified with the imageUrl attribute.
     * </p>
     * @see #getImageWidth()
     */
    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
        this.imageWidth_set = true;
    }

    // immediate
    @Property(name="immediate", displayName="Immediate", isHidden=true, isAttribute=false)
    private boolean immediate = false;
    private boolean immediate_set = false;

    /**
     * Flag indicating that event handling for this component should be handled
     * immediately (in Apply Request Values phase) rather than waiting until
     * Invoke Application phase.
     */
    public boolean isImmediate() {
        if (this.immediate_set) {
            return this.immediate;
        }
        ValueExpression _vb = getValueExpression("immediate");
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
     * Flag indicating that event handling for this component should be handled
     * immediately (in Apply Request Values phase) rather than waiting until
     * Invoke Application phase.
     */
    public void setImmediate(boolean immediate) {
        this.immediate = immediate;
        this.immediate_set = true;
    }

    /**
     * <p>Specifies the text for the link that is displayed at the bottom of the task's information
     * panel. 
     * </p>
     */    
    @Property(name="infoLinkText", displayName="infoLinkText", category="Appearance")
    private String infoLinkText = null;

    /**
     * <p>Specifies the text for the link that is displayed at the bottom of the task's information
     * panel. 
     * </p>
     */
    public String getInfoLinkText() {
        if (this.infoLinkText != null) {
            return this.infoLinkText;
        }
        ValueExpression _vb = getValueExpression("infoLinkText");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Specifies the text for the link that is displayed at the bottom of the task's information
     * panel. 
     * </p>
     * @see #getInfoLinkText()
     */
    public void setInfoLinkText(String infoLinkText) {
        this.infoLinkText = infoLinkText;
    }

    /**
     * <p>Specifies the URL for the link that is displayed at the bottom of the task's 
     * information panel.
     * </p>
     */    
    @Property(name="infoLinkUrl", displayName="infoLinkUrl", category="Behavior", editorClassName="com.sun.rave.propertyeditors.UrlPropertyEditor")
    private String infoLinkUrl = null;

    /**
     * <p>Specifies the URL for the link that is displayed at the bottom of the task's 
     * information panel.
     * </p>
     */    
    public String getInfoLinkUrl() {
        if (this.infoLinkUrl != null) {
            return this.infoLinkUrl;
        }
        ValueExpression _vb = getValueExpression("infoLinkUrl");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Specifies the URL for the link that is displayed at the bottom of the task's
     * information panel.
     * </p>
     * @see #getInfoLinkUrl()
     */
    public void setInfoLinkUrl(String infoLinkUrl) {
        this.infoLinkUrl = infoLinkUrl;
    }

    /**
     * <p>Specifies the text to be displayed in the information panel for this task. </p>
     */
    @Property(name="infoText", displayName="infoText", category="Appearance")
    private String infoText = null;

    /**
     * <p>Specifies the text to be displayed in the information panel for this task. </p>
     */
    public String getInfoText() {
        if (this.infoText != null) {
            return this.infoText;
        }
        ValueExpression _vb = getValueExpression("infoText");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Specifies the text to be displayed in the information panel for this task. </p>
     * @see #getInfoText()
     */
    public void setInfoText(String infoText) {
        this.infoText = infoText;
    }

    /**
     * <p>Specifies the title text to be displayed in the information panel for this task. </p>
     */
    @Property(name="infoTitle", displayName="infoTitle", category="Appearance")
    private String infoTitle = null;

    /**
     * <p>Specifies the title text to be displayed in the information panel for this task. </p>
     */
    public String getInfoTitle() {
        if (this.infoTitle != null) {
            return this.infoTitle;
        }
        ValueExpression _vb = getValueExpression("infoTitle");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Specifies the title text to be displayed in the information panel for this task. </p>
     * @see #getInfoTitle()
     */
    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    /**
     * <p>Scripting code that is executed when this element loses the focus.</p>
     */    
    @Property(name="onBlur", displayName="Blur Script",category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onBlur = null;

    /**
     * <p>Scripting code that is executed when this element loses the focus.</p>
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
     * <p>Scripting code that is executed when this element loses the focus.</p>
     * @see #getOnBlur()
     */
    public void setOnBlur(String onBlur) {
        this.onBlur = onBlur;
    }

    /**
     * <p>Scripting code that is executed when a mouse click
     * occurs over this component.</p>
     */    
    @Property(name="onClick", displayName="Click Script",category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onClick = null;

    /**
     * <p>Scripting code that is executed when a mouse click
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
     * <p>Scripting code that is executed when a mouse click
     * occurs over this component.</p>
     * @see #getOnClick()
     */
    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    /**
     * <p>Scripting code that is executed when a mouse double click
     * occurs over this component.</p>
     */
    @Property(name="onDblClick", displayName="Double Click Script",category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onDblClick = null;

    /**
     * <p>Scripting code that is executed when a mouse double click
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
     * <p>Scripting code that is executed when a mouse double click
     * occurs over this component.</p>
     * @see #getOnDblClick()
     */
    public void setOnDblClick(String onDblClick) {
        this.onDblClick = onDblClick;
    }

    /**
     * <p>Scripting code that is executed when this component  receives the focus. An
     * element receivesthe  focus when the user selects the element by pressing
     * the tab key or clicking the mouse.</p>
     */        
    @Property(name="onFocus", displayName="Focus Script",category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onFocus = null;

    /**
     * <p>Scripting code that is executed when this component  receives the focus. An
     * element receivesthe  focus when the user selects the element by pressing
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
     * <p>Scripting code that is executed when this component  receives the focus. An
     * element receivesthe  focus when the user selects the element by pressing
     * the tab key or clicking the mouse.</p>
     * @see #getOnFocus()
     */
    public void setOnFocus(String onFocus) {
        this.onFocus = onFocus;
    }

    /**
     * <p>Scripting code that is executed when the user presses down on a key while the
     * component has focus.</p>
     */
    @Property(name="onKeyDown", displayName="Key Down Script",category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyDown = null;

    /**
     * <p>Scripting code that is executed when the user presses down on a key while the
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
     * <p>Scripting code that is executed when the user presses down on a key while the
     * component has focus.</p>
     * @see #getOnKeyDown()
     */
    public void setOnKeyDown(String onKeyDown) {
        this.onKeyDown = onKeyDown;
    }

    /**
     * <p>Scripting code that is executed when the user presses and releases a key while
     * the component has the focus.</p>
     */
    @Property(name="onKeyPress", displayName="Key Press Script",category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyPress = null;

    /**
     * <p>Scripting code that is executed when the user presses and releases a key while
     * the component has the focus.</p>
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
     * <p>Scripting code that is executed when the user presses and releases a key while
     * the component has the focus.</p>
     * @see #getOnKeyPress()
     */
    public void setOnKeyPress(String onKeyPress) {
        this.onKeyPress = onKeyPress;
    }

    /**
     * <p>Scripting code that is executed when the user releases a key while the
     * component has focus.</p>
     */
    @Property(name="onKeyUp", displayName="Key Up Script",category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyUp = null;

    /**
     * <p>Scripting code that is executed when the user releases a key while the
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
     * <p>Scripting code that is executed when the user releases a key while the
     * component has focus.</p>
     * @see #getOnKeyUp()
     */
    public void setOnKeyUp(String onKeyUp) {
        this.onKeyUp = onKeyUp;
    }

    /**
     * <p>Scripting code that is executed when the user presses a mouse button while the
     * mouse pointer is on the component.</p>
     */
    @Property(name="onMouseDown", displayName="Mouse Down Script",category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseDown = null;

    /**
     * <p>Scripting code that is executed when the user presses a mouse button while the
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
     * <p>Scripting code that is executed when the user presses a mouse button while the
     * mouse pointer is on the component.</p>
     * @see #getOnMouseDown()
     */
    public void setOnMouseDown(String onMouseDown) {
        this.onMouseDown = onMouseDown;
    }

    /**
     * <p>Scripting code that is executed when the user moves the mouse pointer while
     * it is over the component.</p>
     */    
    @Property(name="onMouseMove", displayName="Mouse Move Script",category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseMove = null;

    /**
     * <p>Scripting code that is executed when the user moves the mouse pointer while
     * it is over the component.</p>
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
     * <p>Scripting code that is executed when the user moves the mouse pointer while
     * it is over the component.</p>
     * @see #getOnMouseMove()
     */
    public void setOnMouseMove(String onMouseMove) {
        this.onMouseMove = onMouseMove;
    }

    /**
     * <p>Scripting code that is executed when the user moves the mouse pointer off this component.</p>
     */    
    @Property(name="onMouseOut", displayName="Mouse Out Script",category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseOut = null;

    /**
     * <p>Scripting code that is executed when the user moves the mouse pointer off this component.</p>
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
     * <p>Scripting code that is executed when the user moves the mouse pointer off this component.</p>
     * @see #getOnMouseOut()
     */
    public void setOnMouseOut(String onMouseOut) {
        this.onMouseOut = onMouseOut;
    }

    /**
     * <p>Scripting code that is executed when the user moves the  mouse pointer into
     * the boundary of this component.</p>
     */
    @Property(name="onMouseOver", displayName="Mouse In Script",category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseOver = null;

    /**
     * <p>Scripting code that is executed when the user moves the  mouse pointer into
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
     * <p>Scripting code that is executed when the user moves the mouse pointer into
     * the boundary of this component.</p>
     * @see #getOnMouseOver()
     */
    public void setOnMouseOver(String onMouseOver) {
        this.onMouseOver = onMouseOver;
    }

    /**
     * <p>Scripting code that is executed when the user releases a mouse button while
     * the mouse pointer is on the component.</p>
     */    
    @Property(name="onMouseUp", displayName="Mouse Up Script",category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseUp = null;

    /**
     * <p>Scripting code that is executed when the user releases a mouse button while
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
     * <p>Scripting code that is executed when the user releases a mouse button while
     * the mouse pointer is on the component.</p>
     * @see #getOnMouseUp()
     */
    public void setOnMouseUp(String onMouseUp) {
        this.onMouseUp = onMouseUp;
    }

    /**
     * <p>CSS style(s) that are applied to the outermost HTML element when this 
     * component is rendered.</p>
     */    
    @Property(name="style", displayName="CSS Style(s)", category="Appearance", editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;

    /**
     * <p>CSS style(s) that are applied to the outermost HTML element when this 
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
     * <p>CSS style(s) that are applied to the outermost HTML element when this 
     * component is rendered.</p>
     * @see #getStyle()
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * <p>CSS style class(es) that are applied to the outermost HTML element when this 
     * component is rendered.</p>
     */    
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;

    /**
     * <p>CSS style class(es) that are to be applied to the outermost HTML element when this 
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
     * <p>CSS style class(es) that are to be applied to the outermost HTML element when this 
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
     * <p>The resource at the specified URL is displayed in the frame that is 
     * specified with the target attribute. Values such as "_blank" that are 
     * valid for the target attribute of a HTML anchor element are also valid 
     * for this attribute in this component</p>
     */    
    @Property(name="target", displayName="Target", category="Behavior")
    private String target = null;

    /**
     * <p>The resource at the specified URL is displayed in the frame that is 
     * specified with the target attribute. Values such as "_blank" that are 
     * valid for the target attribute of a HTML anchor element are also valid 
     * for this attribute in this component</p>
     */
    public String getTarget() {
        if (this.target != null) {
            return this.target;
        }
        ValueExpression _vb = getValueExpression("target");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The resource at the specified URL is displayed in the frame that is 
     * specified with the target attribute. Values such as "_blank" that are 
     * valid for the target attribute of a HTML anchor element are also valid 
     * for this attribute in this component</p>
     * @see #getTarget()
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * <p>The text to be displayed for the task.</p>
     */
    @Property(name="text", displayName="text", category="Appearance", isDefault=true,
        editorClassName="com.sun.rave.propertyeditors.StringPropertyEditor")
    public Object getText() {
        return getValue();
    }

    /**
     * <p>The text to be displayed for the task.</p>
     * @see #getText()
     */
    public void setText(Object text) {
        setValue(text);
    }

    // title
    @Property(name="title", displayName="Title", isHidden=true, isAttribute=false)
    private String title = null;

    /**
     * The title.
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
     * The title.
     */
    public void setTitle(String title) {
        this.title = title;
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
     * <p>Indicates whether the component should be
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
     * <p>Indicates whether the component should be
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
     * <p>Indicates whether the component should be
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
        this.disabled = ((Boolean) _values[1]).booleanValue();
        this.disabled_set = ((Boolean) _values[2]).booleanValue();
        this.icon = (String) _values[3];
        this.imageHeight = ((Integer) _values[4]).intValue();
        this.imageHeight_set = ((Boolean) _values[5]).booleanValue();
        this.imageUrl = (String) _values[6];
        this.imageWidth = ((Integer) _values[7]).intValue();
        this.imageWidth_set = ((Boolean) _values[8]).booleanValue();
        this.immediate = ((Boolean) _values[9]).booleanValue();
        this.immediate_set = ((Boolean) _values[10]).booleanValue();
        this.infoLinkText = (String) _values[11];
        this.infoLinkUrl = (String) _values[12];
        this.infoText = (String) _values[13];
        this.infoTitle = (String) _values[14];
        this.onBlur = (String) _values[15];
        this.onClick = (String) _values[16];
        this.onDblClick = (String) _values[17];
        this.onFocus = (String) _values[18];
        this.onKeyDown = (String) _values[19];
        this.onKeyPress = (String) _values[20];
        this.onKeyUp = (String) _values[21];
        this.onMouseDown = (String) _values[22];
        this.onMouseMove = (String) _values[23];
        this.onMouseOut = (String) _values[24];
        this.onMouseOver = (String) _values[25];
        this.onMouseUp = (String) _values[26];
        this.style = (String) _values[27];
        this.styleClass = (String) _values[28];
        this.tabIndex = ((Integer) _values[29]).intValue();
        this.tabIndex_set = ((Boolean) _values[30]).booleanValue();
        this.target = (String) _values[31];
        this.title = (String) _values[32];
        this.toolTip = (String) _values[33];
        this.visible = ((Boolean) _values[34]).booleanValue();
        this.visible_set = ((Boolean) _values[35]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[36];
        _values[0] = super.saveState(_context);
        _values[1] = this.disabled ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.disabled_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.icon;
        _values[4] = new Integer(this.imageHeight);
        _values[5] = this.imageHeight_set ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.imageUrl;
        _values[7] = new Integer(this.imageWidth);
        _values[8] = this.imageWidth_set ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = this.immediate ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = this.immediate_set ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.infoLinkText;
        _values[12] = this.infoLinkUrl;
        _values[13] = this.infoText;
        _values[14] = this.infoTitle;
        _values[15] = this.onBlur;
        _values[16] = this.onClick;
        _values[17] = this.onDblClick;
        _values[18] = this.onFocus;
        _values[19] = this.onKeyDown;
        _values[20] = this.onKeyPress;
        _values[21] = this.onKeyUp;
        _values[22] = this.onMouseDown;
        _values[23] = this.onMouseMove;
        _values[24] = this.onMouseOut;
        _values[25] = this.onMouseOver;
        _values[26] = this.onMouseUp;
        _values[27] = this.style;
        _values[28] = this.styleClass;
        _values[29] = new Integer(this.tabIndex);
        _values[30] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        _values[31] = this.target;
        _values[32] = this.title;
        _values[33] = this.toolTip;
        _values[34] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[35] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
   
   /**
    * Returns a component that represents the action element for the {@link CommonTask}.<br />
    * When the user clicks on this action element, it takes him to the corresponding task.<br />
    * In the default case, when no facet is specified,<br />
    * an hyperlink is created, with the default styles <br />
    * applied to it and is returned back to the invoking function.<br />
    *
    *@return - The commonTask action component.
    *
    */
    public UIComponent getTaskAction() {
       
        UIComponent comp = getFacet(COMMONTASK_FACET);
        FacesContext context = FacesContext.getCurrentInstance();
        Theme theme = ThemeUtilities.getTheme(context);
        if (comp != null) {
            return comp;
        }
        return null;
    }
    
    /**
     * Checks whether a facet has been specified for the {@link com.sun.webui.jsf.component.Hyperlink}<br />
     * inside the info panel. If not, it checks whether the infoLinkUrl<br />
     * and infoLinkText attributes have been specified. If these attributes of<br />
     * the component have been specified, it creates an hyperlink with these<br />
     * attributes. Otherwise, it returns null<br />
     *
     *@return - The hyperlink present at the bottom of the info panel.
     */
    public UIComponent getInfoLink() {
        UIComponent comp = getFacet(INFOLINK_FACET);
        
        if (comp != null) {
            return comp;
        }       
        
         comp = ComponentUtilities.getPrivateFacet(this, 
         INFOLINK_FACET, true);
        
        
        if (getInfoLinkText() != null && getInfoLinkUrl() != null) {
           if (comp == null) {
                Hyperlink link = new Hyperlink();
                link.setId(ComponentUtilities.createPrivateFacetId(this, INFOLINK_FACET));
                ComponentUtilities.putPrivateFacet(this, INFOLINK_FACET, link);
                comp = link;
            }
           
           try {
               Hyperlink link = (Hyperlink)comp;
                link.setUrl(getInfoLinkUrl());
                link.setTarget("_blank");
                link.setText(getInfoLinkText());
                link.setTabIndex(this.getTabIndex());
           }catch (ClassCastException e) {
               // The comp object did not contain a hyperlink.
           }
        }
        return comp;
    }
    
    /**
     *Checks whether a facet has been specified for the infoPanel. 
     * TODO: Is it possible to create the default info panel here instead
     * of doing it in the renderer. There is a lot of html to be generated
     * between the components present in the panel which is making the 
     * creation of this facet over here impossible.
     *@return - A component which represents the info panel for the common task.
     */
    public UIComponent getInfoPanel() {
        UIComponent comp = getFacet(INFOPANEL_FACET);
        return comp;
    }
}

