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

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.el.ValueExpression;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

/**
 * The Anchor component is used to create an XHTML &lt;a&gt; anchor element.
 * <p>  Use the <code>webuijsf:anchor</code> tag to 
 *   <ul>
 *     <li> Create an anchor that traverses to the specified url.</li>
 *     <li> Anchor a position in the page so that you can jump to it.</li>
 *  </ul>     
 * <p>If you use the anchor component to anchor a position in the page, make sure that
 * the name attribute has a value set. When the anchor widget is created on the client
 * side, if the name attribute is specified, this value will be assigned as the id
 * of the anchor widget.</p>
 *<p>
 * The anchor component has an attribute called disabled which when set to true will
 * prevent the anchor from being generating a request when it is clicked.
 *</p>
 *<p>
 * When UIParameter components are specified as children to the anchor component, the
 * renderer processes these children and appends the name and value specified in the
 * UIParameter component as query paramters to the url.
 * </p>
 */
@Component(type="com.sun.webui.jsf.Anchor", 
    family="com.sun.webui.jsf.Anchor",
    tagRendererType="com.sun.webui.jsf.widget.Anchor",     
    displayName="Anchor", tagName="anchor",instanceName="anchor",        
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_anchor",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_anchor_props")
public class Anchor extends UIComponentBase implements ComplexComponent, 
        NamingContainer {
    /**
     * Default constructor.
     */
        public Anchor() {
        super();
        setRendererType("com.sun.webui.jsf.widget.Anchor");
    }
        
    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Anchor";
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
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
     * <p>The shape of the hot spot on the screen (for use in client-side image 
     * maps). Used with the coords attribute.Valid values are: default (entire region); rect (rectangular 
     * region); circle (circular region); and poly (polygonal region).</p>
     */
    @Property(name="shape", displayName="Shape", category="Advanced")
    private String shape = null;

    /**
     * <p>The shape of the hot spot on the screen (for use in client-side image 
     * maps). Used with the coords attribute.Valid values are: default (entire region); rect (rectangular 
     * region); circle (circular region); and poly (polygonal region).</p>
     */
    public String getShape() {
        if (this.shape != null) {
            return this.shape;
        }
        ValueExpression _vb = getValueExpression("shape");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The shape of the hot spot on the screen (for use in client-side image 
     * maps).Used with the coords attribute. Valid values are: default (entire region); rect (rectangular 
     * region); circle (circular region); and poly (polygonal region).</p>
     * @see #getShape()
     */
    public void setShape(String shape) {
        this.shape = shape;
    }
    
    /**
     *<p>Specifies the relationship between the current document and the target 
     * URL</p>
     */
    @Property(name="rel", displayName="Rel", category="Advanced")    
    private String rel = null;

    /**
     *<p>Specifies the relationship between the current document and the target 
     * URL</p>
     */    
    public String getRel() {
        if (this.rel != null) {
            return this.rel;
        }
        ValueExpression _vb = getValueExpression("rel");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;        
    }

    /**
     *<p>Specifies the relationship between the current document and the target 
     * URL</p>
     */    
    public void setRel (String rel) {
        this.rel = rel;
    }
    
    /**
     *<p>Specifies the relationship between the target URL and the current 
     * document</p>
     */
    @Property(name="rev", displayName="Rev", category="Advanced")    
    private String rev = null;

    /**
     *<p>Specifies the relationship between the target URL and the current 
     * document</p>
     */    
    public String getRev() {
        if (this.rev != null) {
            return this.rev;
        }
        ValueExpression _vb = getValueExpression("rev");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;        
    }

    /**
     *<p>Specifies the relationship between the target URL and the current 
     * document</p>
     */    
    public void setRev (String rev) {
        this.rev = rev;
    }
    
    /**
     *<p>Specifies the coordinates appropriate to the shape attribute to define
     * a region of an image for image maps</p>
     */
    @Property(name="coords", displayName="Coords", category="Advanced")    
    private String coords = null;

    /**
     *<p>Specifies the coordinates appropriate to the shape attribute to define
     * a region of an image for image maps</p>
     */    
    public String getCoords() {
        if (this.coords != null) {
            return this.coords;
        }
        ValueExpression _vb = getValueExpression("coords");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;        
    }

    /**
     *<p>Specifies the coordinates appropriate to the shape attribute to define
     * a region of an image for image maps</p>
     */    
    public void setCoords (String coords) {
        this.coords = coords;
    }
    
    /**
     * <p>The MIME content type of the resource specified by this component.</p>
     */
    @Property(name="type", displayName="Type", category="Advanced")
    private String type = null;

    /**
     * <p>The MIME content type of the resource specified by this component.</p>
     */
    public String getType() {
        if (this.type != null) {
            return this.type;
        }
        ValueExpression _vb = getValueExpression("type");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The MIME content type of the resource specified by this component.</p>
     * @see #getType()
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * <p>Absolute, relative, or context relative (starting with "/") URL to the 
     * resource selected by this anchor. If the url attribute is specified, 
     * clicking this anchor sends the browser to the new location.
     * If UIParameter components are specified as child to the anchor component,
     * then the renderer processes these name value pairs of the UIParameter components
     * and appends them as query parameters to the specified url. 
     * </p>
     */
    @Property(name="url", displayName="URL", category="Behavior", editorClassName="com.sun.rave.propertyeditors.UrlPropertyEditor")
    private String url = null;

    /**
     * <p>Absolute, relative, or context relative (starting with "/") URL to the 
     * resource selected by this anchor. If the url attribute is specified, 
     * clicking this anchor sends the browser to the new location.
     * If UIParameter components are specified as child to the anchor component,
     * then the renderer processes these name value pairs of the UIParameter components
     * and appends them as query parameters to the specified url. 
     * </p>
     */
    public String getUrl() {
        if (this.url != null) {
            return this.url;
        }
        ValueExpression _vb = getValueExpression("url");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Absolute, relative, or context relative (starting with "/") URL to the 
     * resource selected by this anchor. If the url attribute is specified, 
     * clicking this anchor sends the browser to the new location.
     * If UIParameter components are specified as child to the anchor component,
     * then the renderer processes these name value pairs of the UIParameter components
     * and appends them as query parameters to the specified url. 
     * </p>
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * <p>The language code of the resource designated by this anchor.</p>
     */
    @Property(name="urlLang", displayName="URL Lang", category="Advanced")
    private String urlLang = null;

    /**
     * <p>The language code of the resource designated by this anchor.</p>
     */
    public String getUrlLang() {
        if (this.urlLang != null) {
            return this.urlLang;
        }
        ValueExpression _vb = getValueExpression("urlLang");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The language code of the resource designated by this anchor.</p>
     * @see #getUrlLang()
     */
    public void setUrlLang(String urlLang) {
        this.urlLang = urlLang;
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
    private boolean visible = true;
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
     * <p>The text to be displayed for the anchor element.</p>
     */
    @Property(name="text", displayName="text", category="Appearance", isDefault=true,
        editorClassName="com.sun.rave.propertyeditors.StringPropertyEditor")
    private Object text = null;
    public Object getText() {
        if (this.text != null) {
            return this.text;
        }
        ValueExpression _vb = getValueExpression("text");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The text to be displayed for the anchor element.</p>
     * @see #getText()
     */
    public void setText(Object text) {
        this.text = text;
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
     * Return a component instance that can be referenced
     * by a <code>Label</code> in order to evaluate the <code>required</code>
     * and <code>valid</code> states of this component.
     *
     * <em>This implementation returns <code>null</code>.
     * <code>Anchor</code>
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
     * Returns the id of an HTML element suitable to
     * receive the focus.
     * If the <code>ComplexComponent</code> has sub-compoents, and one of 
     * the sub-components is to reveive the focus, if that sub-component
     * is a <code>ComplexComponent</code>, then
     * <code>getFocusElementId</code> must called on the sub-component and
     * the value returned. The value returned by this 
     * method call may or may not resolve to a component instance.
     *<p>
     * This implementations returns the value of
     * <code>getLabeledElementId</code>.
     *
     * @param context The FacesContext used for the request
     */
    public String getFocusElementId(FacesContext context) {
	// For now return the labeled component.
	//
	return getLabeledElementId(context);
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
     * <p>Scripting code executed when a mouse click occurs over this component.
     * If the component submits the form (by using the action attribute), the 
     * script that you use with the onClick attribute should not return from 
     * the function. When the action attribute is used, the component handles the 
     * return with a script that is appended to the anchor element's onclick 
     * property. When you supply an onClick attribute, this return script is 
     * appended after your script in the anchor's onclick. It is ok to return 
     * from your script to abort the submit process if necessary.</p>
     */
    @Property(name="onClick", displayName="Click Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onClick = null;

    /**
     * <p>Scripting code executed when a mouse click occurs over this component.
     * If the component submits the form (by using the action attribute), the 
     * script that you use with the onClick attribute should not return from 
     * the function. When the action attribute is used, the component handles the 
     * return with a script that is appended to the anchor element's onclick 
     * property. When you supply an onClick attribute, this return script is 
     * appended after your script in the anchor's onclick. It is ok to return 
     * from your script to abort the submit process if necessary.</p>
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
     * <p>Scripting code executed when a mouse click occurs over this component.
     * If the component submits the form (by using the action attribute), the 
     * script that you use with the onClick attribute should not return from 
     * the function. When the action attribute is used, the component handles the 
     * return with a script that is appended to the anchor element's onclick 
     * property. When you supply an onClick attribute, this return script is 
     * appended after your script in the anchor's onclick. It is ok to return 
     * from your script to abort the submit process if necessary.</p>
     * @see #getOnClick()
     */
    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    /**
     * <p>Scripting code executed when a mouse double click
     * occurs over this component.</p>
     */
    @Property(name="onDblClick", displayName="Double Click Script", category="Javascript", isHidden=true, isAttribute=false, 
            editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
     * <p>Scripting code executed when a mouse out movement
     * occurs over this component.</p>
     */
    @Property(name="onMouseOut", displayName="Mouse Out Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseOut = null;

    /**
     * <p>Scripting code executed when a mouse out movement
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
     * <p>Scripting code executed when a mouse out movement
     * occurs over this component.</p>
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
     *<p>Specifies the character encoding of the target URL</p>
     */
    @Property(name="charset", displayName="Charset", category="Advanced")    
    private String charset = null;

    /**
     *<p>Specifies the character encoding of the target URL/p>
     */    
    public String getCharset() {
        if (this.charset != null) {
            return this.charset;
        }
        ValueExpression _vb = getValueExpression("charset");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;        
    }

    /**
     *<p>Specifies the character encoding of the target URL/p>
     */        
    public void setCharset (String charset) {
        this.charset = charset;
    }

    /**
     *<p>This attribute assigns an access key to an element. An access key 
     * is a single character from the document character set./p>
     */        
    @Property(name="accessKey", displayName="AccessKey", category="Advanced")    
    private String accessKey = null;

    /**
     *<p>This attribute assigns an access key to an element. An access key 
     * is a single character from the document character set./p>
     */          
    public String getAccessKey() {
        if (this.accessKey != null) {
            return this.accessKey;
        }
        ValueExpression _vb = getValueExpression("accessKey");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;        
    }

    /**
     *<p>This attribute assigns an access key to an element. An access key 
     * is a single character from the document character set./p>
     */        
    public void setAccessKey (String accessKey) {
        this.accessKey = accessKey;
    }

    /**
     *<p>This attribute assigns the name property to the anchor element.
     *  If this attribute is specified, the value
     *  of this attribute is assigned as the id of the anchor widget. 
     *  This attribute should have a value assigned to it
     *  when you are using this component to anchor a position on the page.  
     *  When accessing the anchor widget through javascript, the name attribute
     *  must be given as the parameter to <code>document.getElementById()</code>
     *  The value of this name attribute must be unique for a given page.
     *  Also, do not change the value of the name attribute on the client side
     *  as the anchor is identified by this value on the client side. </p>
     */        
    @Property(name="name", displayName="Name", category="Advanced")    
    private String name = null;

    /**
     *<p>This attribute assigns the name property to the anchor element.
     *  If this attribute is specified, the value
     *  of this attribute is assigned as the id of the anchor widget. 
     *  This attribute should have a value assigned to it
     *  when you are using this component to anchor a position on the page.  
     *  When accessing the anchor widget through javascript, the name attribute
     *  must be given as the parameter to <code>document.getElementById()</code>
     *  The value of this name attribute must be unique for a given page.
     *  Also, do not change the value of the name attribute on the client side
     *  as the anchor is identified by this value on the client side. </p>
     */        
    public String getName() {
        if (this.name != null) {
            return this.name;
        }
        ValueExpression _vb = getValueExpression("name");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;        
    }

    /**
     *<p>This attribute assigns the name property to the anchor element.
     *  If this attribute is specified, the value
     *  of this attribute is assigned as the id of the anchor widget. 
     *  This attribute should have a value assigned to it
     *  when you are using this component to anchor a position on the page.  
     *  When accessing the anchor widget through javascript, the name attribute
     *  must be given as the parameter to <code>document.getElementById()</code>
     *  The value of this name attribute must be unique for a given page.
     *  Also, do not change the value of the name attribute on the client side
     *  as the anchor is identified by this value on the client side. </p>
     */        
    public void setName (String name) {
        this.name = name;
    }

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
     * <p>CSS style class(es) to be applied to the outermost HTML element when
     * this component is rendered.</p>
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;

    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when
     * this component is rendered.</p>
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
     * <p>CSS style class(es) to be applied to the outermost HTML element when
     * this component is rendered.</p>
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
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
     * <p>Flag indicating that clicking of this component by the user is not
     * currently permitted. </p>
     */
    @Property(name="disabled", displayName="Disabled", category="Behavior")
    private boolean disabled = false;
    private boolean disabled_set = false;

    /**
     * <p>Flag indicating that activation of this component by the user is not
     * currently permitted.</p>
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
     * <p>Flag indicating that activation of this component by the user is not
     * currently permitted.</p>
     * @see #isDisabled()
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
        this.disabled_set = true;
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
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.tabIndex = ((Integer)_values[1]).intValue();
        this.tabIndex_set = ((Boolean)_values[2]).booleanValue();
        this.toolTip = (String) _values[3];
        this.shape = (String) _values[4];
        this.rel = (String) _values[5];
        this.rev = (String) _values[6];
        this.coords = (String) _values[7];
        this.type = (String) _values[8];
        this.url = (String) _values[9];
        this.urlLang = (String) _values[10];
        this.visible = ((Boolean)_values[11]).booleanValue();
        this.visible_set = ((Boolean)_values[12]).booleanValue();
        this.target = (String)_values[13];
        this.text = _values[14];
        this.onBlur = (String)_values[15];
        this.onClick = (String)_values[16];
        this.onDblClick = (String)_values[17];
        this.onFocus = (String)_values[18];
        this.onKeyDown = (String)_values[19];
        this.onKeyPress = (String)_values[20];
        this.onKeyUp = (String)_values[21];
        this.onMouseDown = (String)_values[22];
        this.onMouseMove = (String)_values[23];
        this.onMouseOut = (String)_values[24];
        this.onMouseOver = (String)_values[25];        
        this.onMouseUp = (String)_values[26];
        this.charset = (String)_values[27];
        this.accessKey = (String)_values[28];
        this.name = (String)_values[29];
        this.style = (String)_values[30];
        this.styleClass = (String)_values[31];
        this.htmlTemplate = (String)_values[32];
        this.disabled = ((Boolean)_values[33]).booleanValue();
        this.disabled_set = ((Boolean)_values[34]).booleanValue();
        this.ajaxify = ((Boolean) _values[35]).booleanValue();
        this.ajaxify_set = ((Boolean) _values[36]).booleanValue();        
    }   

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[37];
        _values[0] = super.saveState(_context);
        _values[1] = new Integer(this.tabIndex);
        _values[2] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.toolTip;
        _values[4] = this.shape;
        _values[5] = this.rel;
        _values[6] = this.rev;
        _values[7] = this.coords;
        _values[8] = this.type;
        _values[9] = this.url;
        _values[10] = this.urlLang;
        _values[11] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[12] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[13] = this.target;
        _values[14] = this.text;
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
        _values[26] = this.onMouseUp ;
        _values[27] = this.charset;
        _values[28] = this.accessKey;
        _values[29] = this.name;
        _values[30] = this.style;
        _values[31] = this.styleClass;
        _values[32] = this.htmlTemplate;
        _values[33] = this.disabled ? Boolean.TRUE : Boolean.FALSE;
        _values[34] = this.disabled_set ? Boolean.TRUE : Boolean.FALSE;
        _values[35] = this.ajaxify ? Boolean.TRUE : Boolean.FALSE;
        _values[36] = this.ajaxify_set ? Boolean.TRUE : Boolean.FALSE;        
        return _values;
    }    
}

