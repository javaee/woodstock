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
import com.sun.webui.jsf.event.MethodExprValueChangeListener;
import com.sun.webui.jsf.model.OptionTitle;
import com.sun.webui.jsf.util.ConversionUtilities; 
import com.sun.webui.jsf.util.MessageUtil;
import com.sun.webui.jsf.util.ValueType; 
import com.sun.webui.jsf.util.ValueTypeEvaluator; 
import com.sun.webui.jsf.validator.MethodExprValidator;

import java.lang.reflect.Array;
import java.util.Iterator; 

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.event.ValueChangeListener;
import javax.faces.render.Renderer;
import javax.faces.validator.Validator;

/** 
 * Base component for UI components that allow the user to make a selection from
 * a set of options. 
 */ 
@Component(type="com.sun.webui.jsf.Selector", family="com.sun.webui.jsf.Selector", displayName="Selector", isTag=false,
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_selector",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_selector_props")
public class Selector extends WebuiInput implements SelectorManager {
    
    // If true, debugging statements are printed to stdout
    private static final boolean DEBUG = false;

    /**
     * Label facet name.
     */
    public final static String LABEL_FACET = "label"; //NOI18N

    /**
     * Read only separator string
     */
    private static final String READ_ONLY_SEPARATOR = ", "; //NOI18N

    private boolean multiple;

   // Holds the ValueType of this component
    protected ValueTypeEvaluator valueTypeEvaluator = null; 
    
    public Selector() { 
        valueTypeEvaluator = new ValueTypeEvaluator(this); 
        setRendererType("com.sun.webui.jsf.Selector");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Selector";
    }
    
    /**
     * <p>Return a flag indicating whether this component is responsible
     * for rendering its child components.  The default implementation
     * in {@link UIComponentBase#getRendersChildren} tries to find the
     * renderer for this component.  If it does, it calls {@link
     * Renderer#getRendersChildren} and returns the result.  If it
     * doesn't, it returns false.  As of version 1.2 of the JavaServer
     * Faces Specification, component authors are encouraged to return
     * <code>true</code> from this method and rely on {@link
     * UIComponentBase#encodeChildren}.</p>
     */
    public boolean getRendersChildren() {
        return true;
    }
        
    /**
     * Retrieve the value of this component (the "selected" property) as an  
     * object. This method is invoked by the JSF engine during the validation 
     * phase. The JSF default behaviour is for components to defer the 
     * conversion and validation to the renderer, but for the Selector based
     * components, the renderers do not share as much functionality as the 
     * components do, so it is more efficient to do it here. 
     * @param context The FacesContext of the request
     * @param submittedValue The submitted value of the component
     */
    
    public Object getConvertedValue(FacesContext context, 
                                    Object submittedValue)
        throws ConverterException {    
        return getConvertedValue(this, valueTypeEvaluator, context, submittedValue); 
    } 
    
        
   /**
     * Retrieve the value of this component (the "selected" property) as an  
     * object. This method is invoked by the JSF engine during the validation 
     * phase. The JSF default behaviour is for components to defer the 
     * conversion and validation to the renderer, but for the Selector based
     * components, the renderers do not share as much functionality as the 
     * components do, so it is more efficient to do it here. 
     * @param component The component whose value to convert
     * @param context The FacesContext of the request
     * @param submittedValue The submitted value of the component
     */
    private Object getConvertedValue(UIComponent component, 
				  ValueTypeEvaluator valueTypeEvaluator,
				  FacesContext context, 
				  Object submittedValue)

        throws ConverterException {
  
        if(DEBUG) log("getConvertedValue()", component); 

	if(!(submittedValue instanceof String[])) { 
            Object[] args = { component.getClass().getName() }; 
	    String msg = MessageUtil.getMessage
                    ("com.sun.webui.jsf.resources.LogMessages", //NOI18N
		     "Selector.invalidSubmittedValue", args); //NOI18N
                          
	    throw new ConverterException(msg);
	} 

	String[] rawValues = (String[])submittedValue; 

	// This should never happen
	//
	if(rawValues.length == 1 &&
		OptionTitle.NONESELECTED.equals(rawValues[0])) { 
            Object[] args = { OptionTitle.NONESELECTED }; 
	    String msg = MessageUtil.getMessage
                    ("com.sun.webui.jsf.resources.LogMessages", //NOI18N
		     "Selector.invalidSubmittedValue", args); //NOI18N
                          
	    throw new ConverterException(msg);
	}
	
	// If there are no elements in rawValue nothing was submitted.
	// If null was rendered, return null
	//
	if(rawValues.length == 0) {
	    if(DEBUG) log("\t no values submitted, we return null", component); 
	    if (ConversionUtilities.renderedNull(component)) {
		return null; 
	    }
	}

        // Why does getAttributes.get("multiple") not work? 
	if(((SelectorManager)component).isMultiple()) { 
	    if(DEBUG) log("\tComponent accepts multiple values", component); 

	    if(valueTypeEvaluator.getValueType() == ValueType.ARRAY) { 
		if(DEBUG) log("\tComponent value is an array", component); 
		return ConversionUtilities.convertValueToArray
		    (component, rawValues, context); 
	    } 
	    // This case is not supported yet!
	    else if(valueTypeEvaluator.getValueType() == ValueType.LIST) { 
		if(DEBUG) log("\tComponent value is a list", component); 
		/* Until this is fixed throw exception saying it is 
		   unsupported
		return ConversionUtilities.convertValueToList
		    (component, rawValues, context); 
		*/
		throw new javax.faces.FacesException(
			"List is not a supported value.");
	    } 
	    else {
                if(DEBUG) log("\tMultiple selection enabled for non-array value", 
                              component);
                Object[] params = { component.getClass().getName() };
                String msg = MessageUtil.getMessage
                        ("com.sun.webui.jsf.resources.LogMessages", //NOI18N
                         "Selector.multipleError",               //NOI18N
                         params); 
                throw new ConverterException(msg);
	    }
	}

	if(DEBUG) log("\tComponent value is an object", component); 

	// Not sure if this case is taken care of consistently
	// Need to formulate the possible states for the
	// submitted value and what they mean.
	//
	// This can overwrite an unchanged value property
	// with null when it was originally empty string.
	/*
	if(rawValues[0].length() == 0) { 
	    if(DEBUG) log("\t empty string submitted, return null", component); 
	    return null; 
	} 
	*/

	String cv = rawValues.length == 0 ? "" : rawValues[0];

	if(valueTypeEvaluator.getValueType() == ValueType.NONE) { 
            if(DEBUG) log("\t valuetype == none, return rawValue", component); 
	    return cv;
	} 

        if(DEBUG) log("\t Convert the thing...", component); 
	return ConversionUtilities.convertValueToObject
	    (component, cv, context);
    } 

    /**
     * Return a string suitable for displaying the value in read only mode.
     * The default is to separate the list values with a comma.
     *
     * @param context The FacesContext
     * @throws javax.faces.FacesException If the list items cannot be processed
     */
    // AVK - instead of doing this here, I think we 
    // should set the value to be displayed when we get the readOnly 
    // child component. It would be a good idea to separate the listItems 
    // processing for the renderer - where we have to reprocess the items
    // every time, from other times, when this may not be necessary.
    // I note that although this code has been refactored by Rick, my 
    // original code already did this so the fault is wtih me. 
    protected String getValueAsReadOnly(FacesContext context) {
        
	// The comma format READ_ONLY_SEPARATOR should be part of the theme
	// and/or configurable by the application
	//
	return getValueAsString(context, READ_ONLY_SEPARATOR, true);
    }

    /**
     * Get the value (the object representing the selection(s)) of this 
     * component as a String. If the component allows multiple selections,
     * the strings corresponding to the individual options are separated by 
     * spaces. 
     * @param context The FacesContext of the request
     * @param separator A String separator between the values
    
    public String getValueAsString(FacesContext context, String separator) { 
	 return getValueAsString(context, separator, false);
    }
 */
    /**
     * Get the value (the object representing the selection(s)) of this 
     * component as a String. If the component allows multiple selections,
     * the strings corresponding to the individual options are separated
     * by the separator argument. If readOnly is true, leading and
     * and trailing separators are omitted.
     * If readOnly is false the formatted String is suitable for decoding
     * by ListRendererBase.decode.
     *
     * @param context The FacesContext of the request
     * @param separator A String separator between the values
     * @param readOnly A readonly formatted String, no leading or trailing
     * separator string.
     */
    private String getValueAsString(FacesContext context, String separator,
		boolean readOnly) {

	// Need to distinguish null value from an empty string
	// value. See the end of this method for empty string
	// value formatting
	//
	Object value = getValue(); 
	if(value == null) { 
	    return new String(); 
	} 

	if(valueTypeEvaluator.getValueType() == ValueType.NONE) { 
	    return new String(); 
	} 
        
	if(valueTypeEvaluator.getValueType() == ValueType.INVALID) { 
	    return new String(); 
	} 

	// Multiple selections
	//
	// The format should be the same as that returned
	// from the javascript which always has a leading
	// and terminating separator. And suitable for decoding
	// by ListRendererBase.decode
	//
	if(valueTypeEvaluator.getValueType() == ValueType.LIST) { 

	    StringBuffer valueBuffer = new StringBuffer(256); 

	    java.util.List list = (java.util.List)value; 
	    Iterator valueIterator = ((java.util.List)value).iterator();
	    String valueString = null; 

	    // Leading delimiter
	    //
	    if (!readOnly && valueIterator.hasNext()) {
		valueBuffer.append(separator);
	    }

	    while(valueIterator.hasNext()) {
		valueString = ConversionUtilities.convertValueToString
			(this, valueIterator.next());
		valueBuffer.append(valueString); 
		// Add terminating delimiter
		//
                if(!readOnly || (readOnly && valueIterator.hasNext())) {
                    valueBuffer.append(separator);
                }
	    } 
	    return valueBuffer.toString(); 
	}

	if(valueTypeEvaluator.getValueType() == ValueType.ARRAY) {
	    
	    StringBuffer valueBuffer = new StringBuffer(256); 

	    int length = Array.getLength(value); 
	    Object valueObject = null;
	    String valueString = null; 
	    
	    if (!readOnly && length != 0) {
		valueBuffer.append(separator);
	    }
	    for(int counter = 0; counter < length; ++counter) { 
		valueObject = Array.get(value,counter); 
		valueString = 
		    ConversionUtilities.convertValueToString
		    (this, valueObject); 
		valueBuffer.append(valueString); 
		// Add terminating delimiter
		//
                if(!readOnly || (readOnly && counter < length - 1)) {
                    valueBuffer.append(separator);
                }
	    } 
	    return valueBuffer.toString(); 
	} 

	// Empty string looks like '<sep><sep>' or if separator == "|"
	// it'll be "||"
	//
	String cv = ConversionUtilities.convertValueToString(this, value);
	if (readOnly) {
	    return cv;
	} else {
	    StringBuffer sb = new StringBuffer(64);
	    return sb.append(separator).append(cv).append(separator).toString();
	}
    } 

    public int getLabelLevel() {

        int labelLevel = _getLabelLevel();
        if(labelLevel < 1 || labelLevel > 3) { 
            labelLevel = 2; 
            setLabelLevel(labelLevel);
        }
        return labelLevel;
    }

    /**
     * Getter for property multiple.
     * @return Value of property multiple.
     */
    public boolean isMultiple() {
        
        return this.multiple;
    }

    /**
     * Setter for property multiple.
     * @param multiple New value of property multiple.
     */
    public void setMultiple(boolean multiple) {
        if(this.multiple != multiple) {
            valueTypeEvaluator.reset();
            this.multiple = multiple;
        }
    }

    /**
     * Public method toString() 
     * @return A String representation of this component
     */
    public String toString() {
	String string = this.getClass().getName(); 
	return string; 
    }

     /**
     * private method for development time error detecting
     */
    static void log(String s, Object o) {
        System.out.println(o.getClass().getName() + "::" + s); //NOI18N
    }

    /**
     * private method for development time error detecting
     */
    void log(String s) {
        System.out.println(this.getClass().getName() + "::" + s); //NOI18N
    }

    /**
     * <p>Return <code>true</code> if the new value is different from the
     * previous value.</p>
     *
     * This only implements a compareValues for value if it is an Array.
     * If value is not an Array, defer to super.compareValues.
     * The assumption is that the ordering of the elements
     * between the previous value and the new value is determined
     * in the same manner.
     *
     * Another assumption is that the two object arguments
     * are of the same type, both arrays of both not arrays.
     *
     * @param previous old value of this component (if any)
     * @param value new value of this component (if any)
     */
    protected boolean compareValues(Object previous, Object value) {

	// Let super take care of null cases
	//
	if (previous == null || value == null) {
	    return super.compareValues(previous, value);
	}
	if (value instanceof Object[]) {
	    // If the lengths aren't equal return true
	    //
	    int length = Array.getLength(value);
	    if (Array.getLength(previous) != length) {
		return true;
	    }
	    // Each element at index "i" in previous must be equal to the
	    // elementa at index "i" in value.
	    //
	    for (int i = 0; i < length; ++i) {

		Object newValue = Array.get(value, i);
		Object prevValue = Array.get(previous, i);

		// This is probably not necessary since
		// an Option's value cannot be null
		//
		if (newValue == null) {
		    if (prevValue == null) {
			continue;
		    } else {
			return true;
		    }
		}
		if (prevValue == null) {
		    return true;
		}

		if (!prevValue.equals(newValue)) {
		    return true;
		}
	    }
	    return false;
        }
	return super.compareValues(previous, value);
    }

    public void setSelected(Object selected) {
        _setSelected(selected);
        valueTypeEvaluator.reset(); 
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
        if (name.equals("selected")) {
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
        if (name.equals("selected")) {
            super.setValueExpression("value", binding);
            return;
        }
        super.setValueExpression(name, binding);
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
     * <p>Specifies the options that the web application user can choose
     * from. The value must be one of an array, Map or Collection
     * whose members are all subclasses of<code>com.sun.webui.jsf.model.Option</code>.</p>
     */
    @Property(name="items", displayName="Items", category="Data", editorClassName="com.sun.rave.propertyeditors.binding.ValueBindingPropertyEditor")
    private Object items = null;

    /**
     * <p>Specifies the options that the web application user can choose
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
     * <p>Specifies the options that the web application user can choose
     * from. The value must be one of an array, Map or Collection
     * whose members are all subclasses of<code>com.sun.webui.jsf.model.Option</code>.</p>
     * @see #getItems()
     */
    public void setItems(Object items) {
        this.items = items;
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
    private int _getLabelLevel() {
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
     * <p>Scripting code that is executed when this element loses the focus.</p>
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
     * <p>Scripting code that is executed when the element
     * value of this component is changed.</p>
     * @see #getOnChange()
     */
    public void setOnChange(String onChange) {
        this.onChange = onChange;
    }

    /**
     * <p>Scripting code that is executed when a mouse click
     * occurs over the component.</p>
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
     * <p>Scripting code that is executed when a mouse double-click
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
     * <p>Scripting code that is executed when this component  receives focus. An
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
     * <p>Scripting code that is executed when the user presses down on a key while the
     * component has the focus.</p>
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
     * <p>Scripting code that is executed when the user presses and releases a key while
     * the component has the focus.</p>
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
     * <p>Scripting code that is executed when the user releases a key while the
     * component has the focus.</p>
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
     * <p>Scripting code that is executed when the user presses a mouse button while the
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
     * <p>Scripting code that is executed when the user moves the mouse pointer while
     * over the component.</p>
     * @see #getOnMouseMove()
     */
    public void setOnMouseMove(String onMouseMove) {
        this.onMouseMove = onMouseMove;
    }

    /**
     * <p>Scripting code that is executed when a mouse out movement
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
     * <p>Scripting code that is executed when the user moves the  mouse pointer into
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
     * <p>Scripting code that is executed when the user releases a mouse button while
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
     * <p>Scripting code that is executed when some text in this
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
     * <p>If true, the value of the component is
     * rendered as text, preceded by the label if one was defined.</p>
     *
     * @deprecated The attribute is deprected starting from version 4.1
     */
    @Property(name="readOnly", displayName="Read-only", category="Behavior")
    private boolean readOnly = false;
    private boolean readOnly_set = false;

    /**
     * <p>If true, the value of the component is
     * rendered as text, preceded by the label if one was defined.</p>
     *
     * @deprecated The attribute is deprected starting from version 4.1
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
     * <p>If true, the value of the component is
     * rendered as text, preceded by the label if one was defined.</p>
     * @see #isReadOnly()
     *
     * @deprecated The attribute is deprected starting from version 4.1
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        this.readOnly_set = true;
    }

    /**
     * <p>The object that represents the selections made from the
     * available options. If multiple selections are allowed, this
     * must be bound to an Object array, or an array of
     * primitives.</p>
     */
    @Property(name="selected", displayName="Selected", category="Data", editorClassName="com.sun.rave.propertyeditors.binding.ValueBindingPropertyEditor")
    public Object getSelected() {
        return getValue();
    }

    /**
     * <p>The object that represents the selections made from the
     * available options. If multiple selections are allowed, this
     * must be bound to an Object array, or an array of
     * primitives.</p>
     * @see #getSelected()
     */
    private void _setSelected(Object selected) {
        setValue(selected);
    }

    /**
     * <p>CSS style or styles that are applied to the outermost HTML element when the 
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
     * <p>CSS style or styles that are applied to the outermost HTML element when this 
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
     * <p>Describes the position of this element in the tabbing order of the  
     * current document. Tabbing order determines the sequence in which elements 
     * receive focus when the tab key is pressed. The value must be an integer 
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
     * viewable by the user in the rendered HTML page. If false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is true, so
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
        this.disabled = ((Boolean) _values[1]).booleanValue();
        this.disabled_set = ((Boolean) _values[2]).booleanValue();
        this.items = (Object) _values[3];
        this.label = (String) _values[4];
        this.labelLevel = ((Integer) _values[5]).intValue();
        this.labelLevel_set = ((Boolean) _values[6]).booleanValue();
        this.onBlur = (String) _values[7];
        this.onChange = (String) _values[8];
        this.onClick = (String) _values[9];
        this.onDblClick = (String) _values[10];
        this.onFocus = (String) _values[11];
        this.onKeyDown = (String) _values[12];
        this.onKeyPress = (String) _values[13];
        this.onKeyUp = (String) _values[14];
        this.onMouseDown = (String) _values[15];
        this.onMouseMove = (String) _values[16];
        this.onMouseOut = (String) _values[17];
        this.onMouseOver = (String) _values[18];
        this.onMouseUp = (String) _values[19];
        this.onSelect = (String) _values[20];
        this.readOnly = ((Boolean) _values[21]).booleanValue();
        this.readOnly_set = ((Boolean) _values[22]).booleanValue();
        this.style = (String) _values[23];
        this.styleClass = (String) _values[24];
        this.tabIndex = ((Integer) _values[25]).intValue();
        this.tabIndex_set = ((Boolean) _values[26]).booleanValue();
        this.toolTip = (String) _values[27];
        this.visible = ((Boolean) _values[28]).booleanValue();
        this.visible_set = ((Boolean) _values[29]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[30];
        _values[0] = super.saveState(_context);
        _values[1] = this.disabled ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.disabled_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.items;
        _values[4] = this.label;
        _values[5] = new Integer(this.labelLevel);
        _values[6] = this.labelLevel_set ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.onBlur;
        _values[8] = this.onChange;
        _values[9] = this.onClick;
        _values[10] = this.onDblClick;
        _values[11] = this.onFocus;
        _values[12] = this.onKeyDown;
        _values[13] = this.onKeyPress;
        _values[14] = this.onKeyUp;
        _values[15] = this.onMouseDown;
        _values[16] = this.onMouseMove;
        _values[17] = this.onMouseOut;
        _values[18] = this.onMouseOver;
        _values[19] = this.onMouseUp;
        _values[20] = this.onSelect;
        _values[21] = this.readOnly ? Boolean.TRUE : Boolean.FALSE;
        _values[22] = this.readOnly_set ? Boolean.TRUE : Boolean.FALSE;
        _values[23] = this.style;
        _values[24] = this.styleClass;
        _values[25] = new Integer(this.tabIndex);
        _values[26] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        _values[27] = this.toolTip;
        _values[28] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[29] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
