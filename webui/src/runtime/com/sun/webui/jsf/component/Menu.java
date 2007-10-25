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
import com.sun.webui.jsf.util.ConversionUtilities;

import com.sun.webui.theme.Theme;

import com.sun.webui.jsf.event.MethodExprEventListener;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.event.EventListener;
import com.sun.webui.jsf.event.ValueEvent;    
import javax.faces.event.PhaseId;
import com.sun.webui.jsf.util.ComponentUtilities;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ELException;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.el.EvaluationException;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.faces.el.MethodBinding;

/**
 * <pre>
 * This class represents a Menu component.
 * The class inherits from the <code>com.sun.webui.jsf.component.WebUIInput</code>
 * and implements <code>javax.faces.component.ActionSource2.</code>. Hence, this component
 * acts both as an editableValueHolder and an ActionSource. 
 * 
 * A Menu contains a list of selectable options.
 * The options to be displayed can be specified using the "items" attribute of the component.
 * Each option should be represented by a <code>com.sun.webui.jsf.model.Option</code> instance.
 * Options can also be grouped together based on some common functionality in which case, the 
 * <code>com.sun.webui.jsf.model.OptionGroup</code> should be used for specifying the options.
 * 
 * The menu will not be visible by default, but can be shown by calling the <code>open</code>
 * method on the popupMenu widget when any of the components on the page is clicked. This method takes
 * an event attribute as an argument. This "event" attribute is the event that is generated when some
 * keyboard or mouse action  happens on the web page such as the "click" of a hyperlink. In this case,
 * if the style attribtute with menu positioning is defined, the popupMenu widget will use that to
 * position itself on the page. If not, it will use the event attribute which is passed on as argument
 * to the popupMenu to position itself on the page.
 * 
 * Whenever an option is clicked, you can access the selected option through the getSelectedOption interface
 * available on the menu's dom element. This is the default behavior of the menu element.
 * The default behavior does not have any form of communcation with the server to inform of the selected state.
 * If a communication needs to be made to the server to inform of the selected value of the menu component
 * then 
 * 
 * i) Use domNode.submit() :- The menu element has a submit() function set on its dom. Invoking this function
 *    will cause the menu widget to send an asynchronous request to the server and cause the menu component
 *    to update itself. The menu widget in this case, appends an attribute called value to the dynamic faces
 *    request made. The <code>com.sun.webui.jsf.renderkit.ajax.MenuRenderer</code> will in this case look for 
 *    this particular value and update itself accordingly.
 * 
 * ii) Set the submitForm attribute to true:- This will cause the menu component to submit the form whenever an
 *    option is selected in the menu. All the elements in the form will be submitted and all the components present
 *    in the form will go through the normal JSF processing lifecycle. 
 *    The Menu widget in this case will submit the value of the selected option similar to the way the hyperlink widget
 *    submits the form. It will append the selected value as a request parameter with the key being the client id of 
 *    the menu appended with a "_submittedValue" suffix.
 * 
 * NOTE:- 
 *  To obtain the value of the option that was clicked in the menu widget, use the eventListenerExpression attribute
 *  and invoke the getSelectedOption method on the ValueEvent object that is passed as an argument to this method binding
 * </pre>
 */
@Component(type="com.sun.webui.jsf.Menu", family="com.sun.webui.jsf.Menu",
displayName="Menu", tagName="menu", tagRendererType="com.sun.webui.jsf.widget.Menu")
public class Menu extends EventSourceImpl {

    /**
   * 
   * Creates a new instance of Menu 
   * The constructor also sets the appropriate renderer type
   * and instantiates the valueTypeEvaluator.
   */
    public Menu() {
        super();
        setRendererType("com.sun.webui.jsf.widget.Menu");
    }
    
    
    
    // ----------------------------------------------------- UIComponent Methods


    /**
   * Return the family this component belongs to.
   * This method returns "com.sun.webui.jsf.Menu".
   */
    public String getFamily() {
        return "com.sun.webui.jsf.Menu";
    }
    
    /**
     * Return the appropriate renderer type. 
     * It returns the widget renderer if the request is a normal request and returns
     * the ajax renderer if the request is a partial request.
     */
    public String getRendererType() {
        // Ensure we have a valid Ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.Menu";
        }
        return super.getRendererType();
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
     * <p>Alternative HTML template to be used by this component.</p>
     */
    @Property(name="htmlTemplate", isHidden=true, isAttribute=true, displayName="HTML Template", category="Appearance")
    private String  htmlTemplate = null;
    
    /**
     * <p>Get alternative HTML template to be used by this component.</p>
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
     * <p>Set alternative HTML template to be used by this component.</p>
     */
    public void setHtmlTemplate(String htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
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
     * <p>CSS style class or classes to be applied to the outermost HTML element when this
     * component is rendered.</p>
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;
    
    /**
     * <p>CSS style class or classes to be applied to the outermost HTML element when this
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
     * <p>CSS style class or classes to be applied to the outermost HTML element when this
     * component is rendered.</p>
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    
    /**
     * <p>Use to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default,this setting is false, so
     * HTML for the component HTML is rendered but the menu is not visible to user.
     * Note that, even If the component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    @Property(name="visible", displayName="Visible", category="Behavior")
    private boolean visible = false;
    private boolean visible_set = false;
    
    /**
     * <p>Use to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default,this setting is false, so
     * HTML for the component HTML is rendered but the menu is not visible to user.
     * Note that, even If the component is not visible, it can still be processed on subsequent form
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
        return visible;
    }
    
    /**
     * <p>Use to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default,this setting is false, so
     * HTML for the component HTML is rendered but the menu is not visible to user.
     * Note that, even If the component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     * @see #isVisible()
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }
    
    /**
   * <p>Scripting code executed when a mouse click
   * occurs over this component.The Menu element has a function called
   * getSelectedOption which can be invoked by onClick function handlers
   * to know which option has been selected. This will return the name of the
   * option that was clicked.</p>
   */
    @Property(name="onClick", displayName="Click Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onClick = null;
    
    /**
     * <p>Scripting code executed when a mouse click
     * occurs over this component.The popupMenu element has a function called
     * getSelectedOption which can be invoked by onClick function handlers
     * to know which option has been selected. This will return the name of the
     * option that was clicked.</p>
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
   * occurs over this component.The Menu element has a function called
   * getSelectedOption which can be invoked by onClick function handlers
   * to know which option has been selected. This will return the name of the
   * option that was clicked.</p>
   * 
   * 
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
     * <p>Specifies the items that the web application user can choose
     * from. The value must be one of an array, Map or Collection
     * whose members are all subclasses of<code>com.sun.webui.jsf.model.Option</code>.</p>
     */
    @Property(name="items", displayName="Items", category="Data", editorClassName="com.sun.rave.propertyeditors.binding.ValueBindingPropertyEditor")
    private Object items = null;

    /**
     * <p>Specifies the items that the web application user can choose
     * from. The value must be one of an array, Map or Collection
     * whose members are all subclasses of<code>com.sun.webui.jsf.model.Option</code>.</p>
     */
    public Object getItems() {
        if (this.items != null) {
            return this.items;
        }
        ValueExpression _vb = getValueExpression("items");
        if (_vb != null) {
            return (Object) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }
    
    /**
     * <p>Specifies the items that the web application user can choose
     * from. The value must be one of an array, Map or Collection
     * whose members are all subclasses of<code>com.sun.webui.jsf.model.Option</code>.</p>
     * @see #getItems()
     */
    public void setItems(Object items) {
        this.items = items;
    }
    

    /**
     * <p>Scripting code executed when the element value of this component is changed.</p>
     */
    @Property(name="onChange", displayName="Value Change Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onChange = null;
    
    /**
     * <p>Scripting code executed when the element value of this component is changed.</p>
     */
    public String getOnChange() {
        if (this.onChange != null) {
            return this.onChange;
        }
        ValueExpression _vb = getValueExpression("onChange");
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
    public void setOnChange(String onChange) {
        this.onChange = onChange;
    }
    
    /**
     * <p>When set to true,
     * the form is immediately submitted when the user changes the
     * selection in the menu.</p>
     */
    @Property(name="submitForm", displayName="Submit the Page on Change", category="Behavior")
    private boolean submitForm = false;
    private boolean submitForm_set = false;

    /**
     * <p>When set to true,
     * the form is immediately submitted when the user changes the
     * selection in the menu.</p>
     */
    public boolean isSubmitForm() {
        if (this.submitForm_set) {
            return this.submitForm;
        }
        ValueExpression _vb = getValueExpression("submitForm");
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
     * <p>When set to true,
     * the form is immediately submitted when the user changes the
     * selection in the menu.</p>
     */
    public void setSubmitForm(boolean submitForm) {
        this.submitForm = submitForm;
        this.submitForm_set = true;
    }
    
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Lifecycle methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * <p>Specialized decode behavior on top of that provided by the
     * superclass.
     *
     * <ul>
     *  <li>This method will skip decoding for Ajax requests of type "refresh".</li>
     * </ul>
     *
     * @param context <code>FacesContext</code> for this request.
     */
    public void processDecodes(FacesContext context) {
        if (context == null) {
            return;
        }
        // Skip processing in case of "refresh" ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this, "refresh")
                && !ComponentUtilities.isAjaxExecuteRequest(getFacesContext(), this)) {
            return;
        }
        super.processDecodes(context);
    }    
    /** 
     * Processes the component's SelectItems. Constructs an ArrayList
     * of Options.
     *
     * <ul>
     * <li>General algorithm copied from RI for getting an array of 
     *  Options.</li>
     * </ul> 
     */
    public Option[] getOptionsArray() { 

        Option[] options = null; 
        Object optionsObject = getItems(); 

        if(optionsObject instanceof Option[]) { 
            options = (Option[])optionsObject;
        } 
        else if(optionsObject instanceof Collection) { 
            Object[] objects = ((Collection)optionsObject).toArray(); 
            if(objects == null || objects.length == 0) {
                options = new Option[0];
            }
            
            int numObjects = objects.length;
            options = new Option[numObjects]; 
            for(int counter = 0; counter < numObjects; ++counter) { 
                options[counter] = (Option)objects[counter];
            }
        } 
        else if(optionsObject instanceof Map) { 
            Collection itemsCollection = ((Map)optionsObject).values(); 
            options =  (Option[])(itemsCollection.toArray()); 
        } 
        // The items attribute has not been specified
        else { 
            // do nothing
            options =  new Option[0]; 
        } 
        return options;
    }  
    
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.htmlTemplate = (String) _values[1];
        this.style = (String) _values[2];
        this.styleClass = (String) _values[3];
        this.visible = ((Boolean) _values[4]).booleanValue();
        this.visible_set = ((Boolean) _values[5]).booleanValue();
        this.onClick = (String) _values[6];
        this.onDblClick = (String) _values[7];
        this.onKeyDown = (String) _values[8];
        this.onKeyPress = (String) _values[9];
        this.onKeyUp = (String) _values[10];
        this.onMouseDown = (String) _values[11];
        this.onMouseMove = (String) _values[12];
        this.onMouseOut = (String) _values[13];
        this.onMouseOver = (String) _values[14];
        this.onMouseUp = (String) _values[15];
        this.items =  _values[16];       
        this.submitForm = ((Boolean) _values[17]).booleanValue();
        this.submitForm_set = ((Boolean) _values[18]).booleanValue();
    }
    
    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[19];
        _values[0] = super.saveState(_context);
        _values[1] = this.htmlTemplate;
        _values[2] = this.style;
        _values[3] = this.styleClass;
        _values[4] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.onClick;
        _values[7] = this.onDblClick;
        _values[8] = this.onKeyDown;
        _values[9] = this.onKeyPress;
        _values[10] = this.onKeyUp;
        _values[11] = this.onMouseDown;
        _values[12] = this.onMouseMove;
        _values[13] = this.onMouseOut;
        _values[14] = this.onMouseOver;
        _values[15] = this.onMouseUp;
        _values[16] = this.items;
        _values[17] = this.submitForm ? Boolean.TRUE : Boolean.FALSE;
        _values[18] = this.submitForm_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
