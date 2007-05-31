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
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * Represents an input field whose content will be included when the surrounding
 * form is submitted.
 */
@Component(type="com.sun.webui.jsf.Field", 
    family="com.sun.webui.jsf.Field", displayName="Field", isTag=false,
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_field",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_field_props")
public class Field extends HiddenField implements ComplexComponent,
        NamingContainer {
    public static final String READONLY_ID = "_readOnly"; //NOI18N
    public static final String LABEL_ID = "_label"; //NOI18N
    public static final String INPUT_ID = "_field"; //NOI18N
    public static final String READONLY_FACET = "readOnly"; //NOI18N
    public static final String LABEL_FACET = "label";
    
    private static final boolean DEBUG = false;
    
    /** Creates a new instance of FieldBase */
    public Field() {
        super();
        setRendererType("com.sun.webui.jsf.Field");
    }
    
    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Field";
    }
    
    /**
     * Return a component that implements a label for this <code>Field</code>.
     * If a facet named <code>label</code> is found
     * that component is returned.</br>
     * If a facet is not found a <code>Label</code>
     * component instance is created and returned with the id</br>
     * <code>getId() + "_label"</code>. The <code>Label</code>
     * instance is intialized with the following values
     * <p>
     * <ul>
     * <li><code>getLabelLevel()</code></li>
     * <li><code>style</code> parameter</li>
     * <li><code>getLabel()</code></li>
     * <li><code>setLabeledComponent(this)</code></li>
     * </ul>
     * </p>
     * <p>
     * If a facet is not defined then the returned <code>Label</code>
     * component is created every time this method is called.
     * </p>
     * @return - label facet or a Label instance
     */
    public UIComponent getLabelComponent(FacesContext context, String style) {
        
        if(DEBUG) log("getLabelComponent()");
        
        // Check if the page author has defined a label facet
        UIComponent labelComponent = getFacet(LABEL_FACET); //NOI18N
        if (labelComponent != null) {
            if (DEBUG) {
                log("\tFound facet."); //NOI18N
            }
            return labelComponent;
        }
        
        // If the page author has not defined a label facet,
        // Create one every time.
        //
        String label = getLabel();
        labelComponent = createLabel(label, style, context); //NOI18N\
        
        return labelComponent;
    }
    
    /**
     * Return a component that implements a read only version of
     * of this <code>Field</code>.
     * If a facet named <code>readOnly</code> is found
     * that component is returned.</br>
     * If a facet is not found a <code>StaticText</code>
     * component instance is created and returned with the id</br>
     * <code>getId() + "_alertImage"</code>. The <code>StaticText</code>
     * instance is intialized with the component's value as a
     * <code>String</code>.
     * <p>
     * If a facet is not defined then the returned <code>StaticText</code>
     * component is created every time this method is called.
     * </p>
     * @return - alertImage facet or an Icon instance
     */
    public UIComponent getReadOnlyComponent(FacesContext context) {
        
        if(DEBUG) log("getReadOnlyComponent()");
        
        // Check if the page author has defined a label facet
        UIComponent textComponent = getFacet(READONLY_FACET); //NOI18N
        if (textComponent != null) {
            if(DEBUG) {
                log("\tFound facet."); //NOI18N
            }
            return textComponent;
        }
        
        // If the page author has not defined a readOnly facet,
        // create a static text component
        textComponent = createText(getReadOnlyValueString(context));
        
        return textComponent;
    }
    
    /**
     * Create a Label component every time unless labelString is null
     * or the empty string.
     */
    private UIComponent createLabel(String labelString, String style,
            FacesContext context) {
        
        if(DEBUG) log("createLabel()");
        
        // If we find a label, define a component and add it as a
        // private facet
        //
        
        // We need to allow an empty string label since this
        // could mean that there is value binding and a
        // message bundle hasn't loaded yet, but there
        // is a value binding since the javax.el never returns
        // null for a String binding.
        //
        if(labelString == null /*|| labelString.length() < 1*/) {
            if(DEBUG) log("\tNo label");
            // Remove any previously created one.
            //
            ComponentUtilities.removePrivateFacet(this, LABEL_FACET);
            return null;
        } else if(DEBUG) {
            log("\tLabel is " + labelString);  //NOI18N
        }
        
        Label label = (Label)ComponentUtilities.getPrivateFacet(this,
                LABEL_FACET, true);
        if (label == null) {
            label = new Label();
            label.setId(
                    ComponentUtilities.createPrivateFacetId(this, LABEL_FACET));
            ComponentUtilities.putPrivateFacet(this, LABEL_FACET, label);
        }
        label.setLabelLevel(getLabelLevel());
        label.setStyleClass(style);
        label.setText(labelString);

        // Need to verify what the new meaning of "readOnly"
        // means for the textfield. If the HTML element is
        // read only can a label element's for attribute reference it ?
        //
        label.setIndicatorComponent(this);
        label.setLabeledComponent(this);
        
        return label;
    }
    
    /**
     * Create a StaticText component every time and do not
     * save it in the facet map.
     */
    private UIComponent createText(String string) {
        
        if(DEBUG) log("createText()");
        
        // If we find a label, define a component and add it to the
        // children, unless it has been added in a previous cycle
        // (the component is being redisplayed).
        
        if(string == null || string.length() < 1) {
            // TODO - maybe print a default?
            string = new String();
        }
        StaticText text = new StaticText();
        text.setText(string);
        text.setId(
                ComponentUtilities.createPrivateFacetId(this, READONLY_FACET));
        text.setParent(this);
        
        return text;
    }
    
    /**
     * Log an error - only used during development time.
     */
    protected void log(String s) {
        System.out.println(this.getClass().getName() + "::" + s); //NOI18N
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
     * <p>
     * If <code>isReadOnly</code> returns true, then the 
     * <code>getReadOnlyComponent</code> method is called. If the
     * component instance returned is a <code>ComplexComponent</code>
     * then <code>getLabeledElementId</code> is called on it and the
     * value returned, else its client id is returned.
     * </p>
     *
     * @param context The FacesContext used for the request
     * @return An abolute id suitable for the value of an HTML LABEL element's
     * <code>for</code> attribute.
     */
    public String getLabeledElementId(FacesContext context) {

        // If this component has a label either as a facet or
        // an attribute, return the id of the input element
        // that will have the "INPUT_ID" suffix. IF there is no
        // label, then the input element id will be the component's
        // client id.
        //
        if (isReadOnly()) {
	    UIComponent readOnlyComponent = getReadOnlyComponent(context);
	    if (readOnlyComponent instanceof ComplexComponent) {
		return ((ComplexComponent)readOnlyComponent).
			getLabeledElementId(context);
	    } else {
		return readOnlyComponent != null ?
		    readOnlyComponent.getClientId(context) : null;
	    }
        }

        // To ensure we get the right answer call getLabelComponent.
        // This checks for a developer facet or the private label facet.
        // It also checks the label attribute. This is better than
        // relying on "getLabeledComponent" having been called
        // like this method used to do.
        //
        String clntId = this.getClientId(context);
        return clntId.concat(this.INPUT_ID);
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
     * This implementation returns the value <code>getLabeledElementId</code>
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
     * @param context The current <code>FacesContext</code> instance
     * @param label The <code>Label</code> that labels this component.
     * @return a <code>UIComponent</code> in order to evaluate the
     * required and valid states.
     */
    public UIComponent getIndicatorComponent(FacesContext context,
            Label label) {
	return this;
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
        // In case callers can't handle null when this component
        // is read only. don't return getLabeledElementId here.
        //
        String clntId = this.getClientId(context);
        return clntId.concat(this.INPUT_ID);
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
     * Flag indicating that an input value for this field is mandatory, and
     * failure to provide one will trigger a validation error.
     */
    @Property(name="required", isHidden=false, isAttribute=true, category="Data")
    public boolean isRequired() {
        return super.isRequired();
    }
    
    // Hide value
    @Property(name="value", isHidden=true, isAttribute=false)
    public Object getValue() {
        return super.getValue();
    }
    
    /**
     * <p>Number of character columns used to render this
     * field. The default is 20.</p>
     */
    @Property(name="columns", displayName="Columns", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int columns = Integer.MIN_VALUE;
    private boolean columns_set = false;
    
    /**
     * <p>Number of character columns used to render this
     * field. The default value is based on resource string in
     * theme/messages#textInput.defaultColumns <br> 
     * If resource string  is not found there, the default columns are set to 20</p>
     */
    public int getColumns() {
        if (this.columns_set) {
            return this.columns;
        } else {
            ValueExpression _vb = getValueExpression("columns");
            if (_vb != null) {
                Object _result = _vb.getValue(getFacesContext().getELContext());
                if (_result != null) {
                    try {
                        return ((Integer) _result).intValue();
                    }   catch (Exception e) {} //do nothing
                }
            }
        }
        //we should get here only if getValueExpression and this.columns
        //failed to produce a reasonable number
        
        // Return the default value.
        int cols = 20;
        try {
            String defaultValue = ThemeUtilities.getTheme(FacesContext.getCurrentInstance())
                .getMessage("textInput.defaultColumns"); //NOI18N
            if (defaultValue != null || defaultValue.length() > 0) {
                cols = new Integer(defaultValue).intValue();
            }
        } catch ( Exception e) {} //do nothing
        
        return cols;
    }
    
    /**
     * <p>Number of character columns used to render this
     * field. The default is 20.</p>
     * @see #getColumns()
     */
    public void setColumns(int columns) {
        this.columns = columns;
        this.columns_set = true;
    }
    
    /**
     * <p>Flag indicating that the user is not permitted to activate this
     * component, and that the component's value will not be submitted with the
     * form.</p>
     */
    @Property(name="disabled", displayName="Disabled", category="Behavior")
    private boolean disabled = false;
    private boolean disabled_set = false;
    
    /**
     * <p>Flag indicating that the user is not permitted to activate this
     * component, and that the component's value will not be submitted with the
     * form.</p>
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
     * <p>Flag indicating that the user is not permitted to activate this
     * component, and that the component's value will not be submitted with the
     * form.</p>
     * @see #isDisabled()
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
        this.disabled_set = true;
    }
    
    /**
     * <p>If set, a label is rendered adjacent to the component with the
     * value of this attribute as the label text.</p>
     */
    @Property(name="label", displayName="Label", category="Appearance")
    private String label = null;
    
    /**
     * <p>If set, a label is rendered adjacent to the component with the
     * value of this attribute as the label text.</p>
     */
    public String getLabel() {
        if (this.label != null) {
            return this.label;
        }
        ValueExpression _vb = getValueExpression("label");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }
    
    /**
     * <p>If set, a label is rendered adjacent to the component with the
     * value of this attribute as the label text.</p>
     * @see #getLabel()
     */
    public void setLabel(String label) {
        this.label = label;
    }
    
    /**
     * <p>Sets the style level for the generated label, provided the
     * label attribute has been set. Valid values are 1 (largest), 2 and
     * 3 (smallest). The default value is 2.</p>
     */
    @Property(name="labelLevel", displayName="Label Level", category="Appearance")
    private int labelLevel = Integer.MIN_VALUE;
    private boolean labelLevel_set = false;
    
    /**
     * <p>Sets the style level for the generated label, provided the
     * label attribute has been set. Valid values are 1 (largest), 2 and
     * 3 (smallest). The default value is 2.</p>
     */
    public int getLabelLevel() {
        if (this.labelLevel_set) {
            return this.labelLevel;
        }
        ValueExpression _vb = getValueExpression("labelLevel");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return 2;
    }
    
    /**
     * <p>Sets the style level for the generated label, provided the
     * label attribute has been set. Valid values are 1 (largest), 2 and
     * 3 (smallest). The default value is 2.</p>
     * @see #getLabelLevel()
     */
    public void setLabelLevel(int labelLevel) {
        this.labelLevel = labelLevel;
        this.labelLevel_set = true;
    }
    
    /**
     * <p>The maximum number of characters that can be entered for this field.</p>
     */
    @Property(name="maxLength", displayName="Maximum Length", category="Behavior", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int maxLength = Integer.MIN_VALUE;
    private boolean maxLength_set = false;
    
    /**
     * <p>The maximum number of characters that can be entered for this field.</p>
     */
    public int getMaxLength() {
        if (this.maxLength_set) {
            return this.maxLength;
        }
        ValueExpression _vb = getValueExpression("maxLength");
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
     * <p>The maximum number of characters that can be entered for this field.</p>
     * @see #getMaxLength()
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        this.maxLength_set = true;
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
     * <p>Scripting code executed when the element
     * value of this component is changed.</p>
     */
    @Property(name="onChange", displayName="Value Change Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onChange = null;
    
    /**
     * <p>Scripting code executed when the element
     * value of this component is changed.</p>
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
     * <p>Scripting code executed when the element
     * value of this component is changed.</p>
     * @see #getOnChange()
     */
    public void setOnChange(String onChange) {
        this.onChange = onChange;
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
     * <p>Scripting code executed when some text in this
     * component value is selected.</p>
     */
    @Property(name="onSelect", displayName="Text Selected Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onSelect = null;
    
    /**
     * <p>Scripting code executed when some text in this
     * component value is selected.</p>
     */
    public String getOnSelect() {
        if (this.onSelect != null) {
            return this.onSelect;
        }
        ValueExpression _vb = getValueExpression("onSelect");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }
    
    /**
     * <p>Scripting code executed when some text in this
     * component value is selected.</p>
     * @see #getOnSelect()
     */
    public void setOnSelect(String onSelect) {
        this.onSelect = onSelect;
    }
    
    /**
     * <p>Flag indicating that modification of this component by the
     * user is not currently permitted, but that it will be
     * included when the form is submitted.</p>
     */
    @Property(name="readOnly", displayName="Read Only", category="Behavior")
    private boolean readOnly = false;
    private boolean readOnly_set = false;
    
    /**
     * <p>Flag indicating that modification of this component by the
     * user is not currently permitted, but that it will be
     * included when the form is submitted.</p>
     */
    public boolean isReadOnly() {
        if (this.readOnly_set) {
            return this.readOnly;
        }
        ValueExpression _vb = getValueExpression("readOnly");
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
     * <p>Flag indicating that modification of this component by the
     * user is not currently permitted, but that it will be
     * included when the form is submitted.</p>
     * @see #isReadOnly()
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        this.readOnly_set = true;
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
     * <p>Literal value to be rendered in this input field.
     * If this property is specified by a value binding
     * expression, the corresponding value will be updated
     * if validation succeeds.</p>
     */
    @Property(name="text", displayName="Text", category="Appearance",
    editorClassName="com.sun.rave.propertyeditors.StringPropertyEditor")
    public Object getText() {
        return getValue();
    }
    
    /**
     * <p>Literal value to be rendered in this input field.
     * If this property is specified by a value binding
     * expression, the corresponding value will be updated
     * if validation succeeds.</p>
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
     * <p>Flag indicating that any leading and trailing blanks will be
     * trimmed prior to conversion to the destination data type.
     * Default value is true.</p>
     */
    @Property(name="trim", displayName="Trim", category="Behavior")
    private boolean trim = false;
    private boolean trim_set = false;
    
    /**
     * <p>Flag indicating that any leading and trailing blanks will be
     * trimmed prior to conversion to the destination data type.
     * Default value is true.</p>
     */
    public boolean isTrim() {
        if (this.trim_set) {
            return this.trim;
        }
        ValueExpression _vb = getValueExpression("trim");
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
     * <p>Flag indicating that any leading and trailing blanks will be
     * trimmed prior to conversion to the destination data type.
     * Default value is true.</p>
     * @see #isTrim()
     */
    public void setTrim(boolean trim) {
        this.trim = trim;
        this.trim_set = true;
    }
    
    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page.</p>
     */
    @Property(name="visible", displayName="Visible", category="Behavior")
    private boolean visible = false;
    private boolean visible_set = false;
    
    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page.</p>
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
     * viewable by the user in the rendered HTML page.</p>
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
        this.columns = ((Integer) _values[1]).intValue();
        this.columns_set = ((Boolean) _values[2]).booleanValue();
        this.disabled = ((Boolean) _values[3]).booleanValue();
        this.disabled_set = ((Boolean) _values[4]).booleanValue();
        this.label = (String) _values[5];
        this.labelLevel = ((Integer) _values[6]).intValue();
        this.labelLevel_set = ((Boolean) _values[7]).booleanValue();
        this.maxLength = ((Integer) _values[8]).intValue();
        this.maxLength_set = ((Boolean) _values[9]).booleanValue();
        this.onBlur = (String) _values[10];
        this.onChange = (String) _values[11];
        this.onClick = (String) _values[12];
        this.onDblClick = (String) _values[13];
        this.onFocus = (String) _values[14];
        this.onKeyDown = (String) _values[15];
        this.onKeyPress = (String) _values[16];
        this.onKeyUp = (String) _values[17];
        this.onMouseDown = (String) _values[18];
        this.onMouseMove = (String) _values[19];
        this.onMouseOut = (String) _values[20];
        this.onMouseOver = (String) _values[21];
        this.onMouseUp = (String) _values[22];
        this.onSelect = (String) _values[23];
        this.readOnly = ((Boolean) _values[24]).booleanValue();
        this.readOnly_set = ((Boolean) _values[25]).booleanValue();
        this.style = (String) _values[26];
        this.styleClass = (String) _values[27];
        this.tabIndex = ((Integer) _values[28]).intValue();
        this.tabIndex_set = ((Boolean) _values[29]).booleanValue();
        this.toolTip = (String) _values[30];
        this.trim = ((Boolean) _values[31]).booleanValue();
        this.trim_set = ((Boolean) _values[32]).booleanValue();
        this.visible = ((Boolean) _values[33]).booleanValue();
        this.visible_set = ((Boolean) _values[34]).booleanValue();
    }
    
    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[35];
        _values[0] = super.saveState(_context);
        _values[1] = new Integer(this.columns);
        _values[2] = this.columns_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.disabled ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.disabled_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.label;
        _values[6] = new Integer(this.labelLevel);
        _values[7] = this.labelLevel_set ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = new Integer(this.maxLength);
        _values[9] = this.maxLength_set ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = this.onBlur;
        _values[11] = this.onChange;
        _values[12] = this.onClick;
        _values[13] = this.onDblClick;
        _values[14] = this.onFocus;
        _values[15] = this.onKeyDown;
        _values[16] = this.onKeyPress;
        _values[17] = this.onKeyUp;
        _values[18] = this.onMouseDown;
        _values[19] = this.onMouseMove;
        _values[20] = this.onMouseOut;
        _values[21] = this.onMouseOver;
        _values[22] = this.onMouseUp;
        _values[23] = this.onSelect;
        _values[24] = this.readOnly ? Boolean.TRUE : Boolean.FALSE;
        _values[25] = this.readOnly_set ? Boolean.TRUE : Boolean.FALSE;
        _values[26] = this.style;
        _values[27] = this.styleClass;
        _values[28] = new Integer(this.tabIndex);
        _values[29] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        _values[30] = this.toolTip;
        _values[31] = this.trim ? Boolean.TRUE : Boolean.FALSE;
        _values[32] = this.trim_set ? Boolean.TRUE : Boolean.FALSE;
        _values[33] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[34] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
