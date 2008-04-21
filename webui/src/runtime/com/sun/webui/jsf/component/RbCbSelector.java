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

import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.ConversionUtilities;

import java.io.IOException;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;

/**
 * This renderer meta-data is not mapped one to one with a component.
 * A renderer of this name does exist as the super class of the
 * RadioButton and Checkbox renderers.
 */
public class RbCbSelector extends Selector implements NamingContainer,
	ComplexComponent {
    /**
     * Image facet name.
     */
    public final static String IMAGE_FACET = "image"; //NOI18N

    // This is the default value for selectedValue.
    // Because of the generation and alias of "items"
    // (Need to reconsider this inheritance)
    // its not possible to set up a default value.
    // If selectedValue is not set, then allow this
    // component to behave as a boolean control.
    // The component is selected if both "selected" and
    // "selectedValue" are "true".
    //
    private final static Boolean trueSelectedValue = Boolean.TRUE;

    /**
     * Default constructor.
     */
    public RbCbSelector() {
        super();
        setRendererType("com.sun.webui.jsf.RbCbSelectorRenderer");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.RbCbSelector";
    }
    
    /**
     * Implemented by subclasses in order to reflect the selection
     * state of this component id part of a group.
     * This method is called if the component is part of a group.
     * 
     * @param context the context for this request.
     * @param groupName the value of the <code>name</code> property.
     */
    protected void addToRequestMap(FacesContext context, String groupName) {
    }

    /**
     * Encode the component.
     * <p>
     * If this component is part of a group, ensure that the initial
     * state is reflected in the request map by calling
     * <code>addToRequestMap</code>.
     * </p>
     * @param context the context for this request.
     */
    // Implement this here to initialize the RequestMap ArrayList
    // of selected grouped checkboxes, so that initially selected
    // check boxes are available on the first render cycle
    //
    public void encodeBegin(FacesContext context) throws IOException {

        if (context == null) {
            throw new NullPointerException();
        }      

	// If the checkbox or radio button isn't valid, or
	// not in a group or not
	// selected, don't put it in the RequestMap.
	//
	String groupName = getName();                
	if (groupName != null && !isValid() && !isChecked()) {
            addToRequestMap(context, groupName);
	}

        super.encodeBegin(context);
    }

    /**
     * Convert the <code>submittedValue</code> argument.
     * <p>
     * If there is a renderer for this component,
     * its <code>getConvertedValue()</code> method is called and
     * the value returned by that method is returned. 
     * </p>
     * <p>
     * If there is no renderer, and <code>submittedValue</code> is not
     * an instance of <code>String[]</code> or
     * <code>String</code> a <code>ConverterException</code> is thrown.
     * </p>
     * <p>
     * The <code>submittedValue</code> indicates selected if it is
     * <code>String[1].length() != 0</code> or 
     * <code>String.length() != 0</code>.
     * </p>
     * If not selected and <code>getSelectedValue()</code> returns an
     * instance of <code>Boolean</code>, <code>Boolean.FALSE</code> is
     * returned.
     * </p>
     * <p>
     * If not selected and it's not a boolean control then an unselected
     * value is returned appropriate for the type of the <code>selected</code>
     * property. If the type of the <code>selected</code> property evaluates
     * to a primitive type by virtue of a value binding the appropriate
     * <code>MIN_VALUE</code> constant is returned. For example if the
     * type is <code>int</code>, <code>new Integer(Integer.MIN_VALUE)</code>
     * is returned.<br/>
     * If the type is not a primitive value <code>""</code> is returned.
     * </p>
     * <p>
     * If the control is selected
     * <code>ConversionUtilities.convertValueToObject()</code> is called to
     * convert <code>submittedValue</code>.
     * </p>
     * <p>
     * If <code>ConversionUtilities.convertValueToObject()</code> returns
     * <code>submittedValue</code>, the value of the 
     * <code>getSelectedValue()</code> property
     * is returned, else the value returned by 
     * <code>ConversionUtilities.convertValueToObject()</code> is returned.
     * </p>
     * @param context the context of this request.
     * @param submittedValue the submitted String value of this component.
     */
    public Object getConvertedValue(FacesContext context, 
				    Object submittedValue)
	    throws ConverterException {

	// First defer to the renderer.
	//
	Renderer renderer = getRenderer(context);
	if (renderer != null) {
	    return renderer.getConvertedValue(context, this, submittedValue);
	}
	return getConvertedValue(context, this, submittedValue);
    }


    /**
     * Convert the <code>submittedValue</code> argument.
     * <p>
     * If <code>submittedValue</code> is not
     * an instance of <code>String[]</code> or
     * <code>String</code> a <code>ConverterException</code> is thrown.
     * </p>
     * <p>
     * The <code>submittedValue</code> indicates selected if it is
     * <code>String[1].length() != 0</code> or 
     * <code>String.length() != 0</code>.
     * </p>
     * If not selected and <code>getSelectedValue()</code> returns an
     * instance of <code>Boolean</code>, <code>Boolean.FALSE</code> is
     * returned.
     * </p>
     * <p>
     * If not selected and it's not a boolean control then an unselected
     * value is returned appropriate for the type of the <code>selected</code>
     * property. If the type of the <code>selected</code> property evaluates
     * to a primitive type by virtue of a value binding the appropriate
     * <code>MIN_VALUE</code> constant is returned. For example if the
     * type is <code>int</code>, <code>new Integer(Integer.MIN_VALUE)</code>
     * is returned.<br/>
     * If the type is not a primitive value <code>""</code> is returned.
     * </p>
     * <p>
     * If the control is selected
     * <code>ConversionUtilities.convertValueToObject()</code> is called to
     * convert <code>submittedValue</code>.
     * </p>
     * <p>
     * If <code>ConversionUtilities.convertValueToObject()</code> returns
     * <code>submittedValue</code>, the value of the 
     * <code>getSelectedValue()</code> property
     * is returned, else the value returned by 
     * <code>ConversionUtilities.convertValueToObject()</code> is returned.
     * </p>
     * @param context the context of this request.
     * @param component an RbCbSelector instance.
     * @param submittedValue the submitted String value of this component.
     */
    public Object getConvertedValue(FacesContext context, 
	    RbCbSelector component, Object submittedValue)
	    throws ConverterException {

	// This would indicate minimally not selected
	//
	if (submittedValue == null) {
	    throw new ConverterException(
	    "The submitted value is null. " + //NOI18N
	    "The submitted value must be a String or String array.");//NOI18N
	}

	// Expect a String or String[]
	// Should be made to be just String.
	//
	boolean isStringArray = submittedValue instanceof String[];
	boolean isString = submittedValue instanceof String;
	if (!(isStringArray || isString)) {
	    throw new ConverterException(
	    "The submitted value must be a String or String array.");//NOI18N
	}

	String rawValue = null;
	if (isStringArray) {
	    if (((String[])submittedValue).length > 0) {
		rawValue = ((String[])submittedValue)[0];
	    }
	} else if (isString) {
	    rawValue = (String)submittedValue;
	}

	// Need to determine if the submitted value is not checked
	// and unchanged. If it is unchecked, rawValue == null or
	// rawValue == "". Compare with the rendered value. If the
	// rendered value is "" or null, then the component is unchanged
	// and if the rendered value was not null, try and convert it.
	//
	boolean unselected = rawValue == null || rawValue.length() == 0;

	// If the component was unselected then we need to know if it
	// was rendered unselected due to a value that was an empty
	// string or null. If it is was submitted as unselected
	// and rendered as unselected, we need the rendered value that
	// implied unselected, since it may not null, just different
	// than "selectedValue"
	//
	Object newValue = null;
	Object selectedValue = getSelectedValue();
	if (unselected) {
	    newValue = ConversionUtilities.convertRenderedValue(context,
		rawValue, this);
	    // Determine the unselected value for Boolean controls
	    // if the converted value is null but the the component
	    // value wasn't rendered as null.
	    // For example if the control rendered as null, and is 
	    // still unselected, then we don't want to return FALSE
	    // for a Boolean control, since it is unchanged.
	    // But if it has changed and is unselected then return
	    // the unselected value of FALSE.
	    //
	    if (!ConversionUtilities.renderedNull(component) &&
		    selectedValue instanceof Boolean &&
		    newValue == null) {

		// return the complement of the selectedValue
		// Boolean value.
		//
		newValue = ((Boolean)selectedValue).booleanValue() ?
			Boolean.FALSE : Boolean.TRUE;
	    }
	    return getUnselectedValue(context, component, newValue);
	} else {
	    newValue = ConversionUtilities.convertValueToObject
			(component, rawValue, context);
	    return newValue == rawValue ? selectedValue : newValue;
	}
    }

    private Object getUnselectedValue(FacesContext context,
	    UIComponent component, Object noValue) {

	// Determine the type of the component's value object
        ValueExpression valueExpr =
                component.getValueExpression("value"); //NOI18N
        
	// If there's no value binding we don't care
	// since the local value is an object and can support null or ""
	//
        if (valueExpr == null) {
            return noValue;
	} 
        // We have found a valuebinding.
        Class clazz = valueExpr.getType(context.getELContext());
        
        // Null class
        if (clazz == null) {
            return noValue;
        }
	// Pass noValue for use in primitive boolean case.
	// If the "selectedValue" was Boolean.FALSE, unselected
	// will be Boolean.TRUE.
	//
	if (clazz.isPrimitive()) {
	    return getPrimitiveUnselectedValue(clazz, noValue);
	}

	// bail out
	return noValue;
    }

    private Object getPrimitiveUnselectedValue(Class clazz,
	    Object booleanUnselectedValue) {

	// it MUST be at least one of these
	//
	if (clazz.equals(Boolean.TYPE)) {
	    return booleanUnselectedValue;
	} else if (clazz.equals(Byte.TYPE)) {
	    return new Integer(Byte.MIN_VALUE);
	} else if (clazz.equals(Double.TYPE)) {
	    return new Double(Double.MIN_VALUE);
	} else if (clazz.equals(Float.TYPE)) {
	    return new Float(Float.MIN_VALUE);
	} else if (clazz.equals(Integer.TYPE)) {
	    return new Integer(Integer.MIN_VALUE);
	} else if (clazz.equals(Character.TYPE)) {
	    return new Character(Character.MIN_VALUE);
	} else if (clazz.equals(Short.TYPE)) {
	    return new Short(Short.MIN_VALUE);
	} else { 
	    // if (clazz.equals(Long.TYPE)) 
	    return new Long(Long.MIN_VALUE);
	}
    }
    /**
     * Return the value of the <code>selectedValue</code> property.
     * If <code>selectedValue</code> is null, then a <code>Boolean</code>
     * true instance is returned and the control will behave as a
     * boolean control.
     */
    @Property(name="selectedValue", category="Advanced",
        editorClassName="com.sun.rave.propertyeditors.StringPropertyEditor")
    public Object getSelectedValue() {
	Object sv = _getSelectedValue();
	return sv == null ? trueSelectedValue : sv;
    }

    // Hack to overcome introspection of "isSelected"
    //
    /**
     * Return <code>true</code> if the control is checked.
     * A control is checked when the <code>selectedValue</code> property is
     * equal to the <code>selected</code> property.
     */
    public boolean isChecked() {
	Object selectedValue = getSelectedValue();
	Object selected = getSelected();
	if (selectedValue == null || selected == null) {
	    return false;
	}
	// Need to support "selected" set to a constant String
	// such as "true" or "false" when it is a boolean control.
	// This does not include when selected is bound to a String
	//
	if (getValueExpression("selected") == null && //NOI18N
	    selected instanceof String && selectedValue instanceof Boolean) {
	    return selectedValue.equals(Boolean.valueOf((String)selected));
	} else {
	    return selected.equals(selectedValue);
	}
    }

    /**
     * Return a component that implements an image.
     * If a facet named <code>image</code> is found
     * that component is returned.</br>
     * If a facet is not found and
     * <code>getImageURL()</code> returns a non null value
     * an <code>ImageComponent</code> component instance is
     * returned with the id</br>
     * <code>getId() + "_image"</code>.</br>
     * The <code>ImageComponent</code> instance is
     * intialized with the values from
     * <p>
     * <ul>
     * <li><code>getImageURL()</code></li>
     * <li><code>getToolTip()</code> for the toolTip and alt property</li>
     * </ul>
     * </p>
     * <p>
     * If a facet is not defined then the returned <code>ImageComponent</code>
     * component is created every time this method is called.
     * </p>
     * @return - the image facet or an ImageComponent instance or null
     */
    public UIComponent getImageComponent() {
	UIComponent imageComponent = getFacet(IMAGE_FACET);
	if (imageComponent != null) {
	    return imageComponent;
	}
	return(createImageComponent());
    }

    /**
     * Return a component that implements a label.
     * If a facet named <code>label</code> is found
     * that component is returned.</br>
     * If a facet is not found and <code>getLabel()</code> returns a non
     * null value, a <code>Label</code>
     * component instance is returned with the id</br>
     * <code>getId() + "_label"</code>.</br>
     * The <code>Label</code> instance is
     * intialized with the values from
     * <p>
     * <ul>
     * <li><code>getLabel()</code></li>
     * <li><code>getLabelLevel()</code></li>
     * </ul>
     * </p>
     * <p>
     * If a facet is not defined then the returned <code>Label</code>
     * component is created every time this method is called.
     * </p>
     * @return - the label facet or a Label instance or null
     */
    public UIComponent getLabelComponent() {

	UIComponent labelComponent = getFacet(LABEL_FACET);
	if (labelComponent != null) {
	    return labelComponent;
	}
	return createLabelComponent();
    }

    /**
     * Create a <code>com.sun.webui.jsf.component.Label</code> to implement
     * a label for this component.
     * If <code>getLabel()</code> returns null, null is returned else
     * a <code>Label</code> component is created each time this method
     * is called.
     */
    protected UIComponent createLabelComponent() {

	// This diverges from previous behavior if a subsequent
	// request yields a null label. Previously if a private
	// facet was created, and getLabel() returned null
	// the previous facet was returned.
	//
	String label = getLabel();
	if (label == null) {
	    /* Not saving the facet yet.
	    ComponentUtilities.removePrivateFacet(this, LABEL_FACET);
	    */
	    return null;
	}

	Label flabel = new Label();
	if (flabel == null) {
	    return null;
	}
        flabel.setId(
	    ComponentUtilities.createPrivateFacetId(this, LABEL_FACET));

	flabel.setLabeledComponent(this);
	flabel.setIndicatorComponent(this);
	flabel.setText(label);
	flabel.setLabelLevel(getLabelLevel());
	flabel.setToolTip(getToolTip());

	/* This wasn't done previously. 
	 * But need to set parent. I'm not sure the Label
	 * for stuff will work, or even if it should for individual
	 * rb's and cb's
	 *
	ComponentUtilities.putPrivateFacet(this, LABEL_FACET, flabel);
	 */
	flabel.setParent(this);
	return flabel;
    }

    /**
     * Create a </code>com.sun.webui.jsf.component.ImageComponent</code>
     * to represent the image for this component.
     */
    protected UIComponent createImageComponent() {

	String iurl = getImageURL();
	if (iurl == null) {
	    /* Not saving the facet yet.
	    ComponentUtilities.removePrivateFacet(this, IMAGE_FACET);
	    */
	    return null;
	}

	ImageComponent image = new ImageComponent();
        image.setId(
	    ComponentUtilities.createPrivateFacetId(this, IMAGE_FACET));

	image.setUrl(getImageURL());
	image.setToolTip(getToolTip());
	image.setAlt(getToolTip());

	/* This wasn't done previously. 
	 * But need to set parent. I'm not sure the Label
	 * for stuff will work, or even if it should for individual
	 * rb's and cb's
	 *
	ComponentUtilities.putPrivateFacet(this, LABEL_FACET, flabel);
	 */
	image.setParent(this);

	return image;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ComplexComponent methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

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
     * @return An absolute id suitable for the value of an HTML LABEL element's
     * <code>for</code> attribute.
     */
    public String getLabeledElementId(FacesContext context) {       
        return this.getClientId(context);
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
    public String getPrimaryElementID(FacesContext context)  {
        return getLabeledElementId(context);
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
     * This implementations returns the value of 
     * <code>getLabeledElementId</code>.
     * </p>
     *
     * @param context The FacesContext used for the request
     */
    public String getFocusElementId(FacesContext context) {
        // For now just return the same id that is used for label.
        //
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
        if (name.equals("selectedValue")) {
            return super.getValueExpression("items");
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
        if (name.equals("selectedValue")) {
            super.setValueExpression("items", binding);
            return;
        }
        super.setValueExpression(name, binding);
    }

    /**
     * <p>
     * A context relative path of an image to be displayed with
     * the control. If you want to be able to specify attributes
     * for the image, specify an <code>image</code> facet instead
     * of the <code>imageURL</code> attribute.</p>
     */
    @Property(name="imageURL", category="Appearance", editorClassName="com.sun.rave.propertyeditors.ImageUrlPropertyEditor")
    private String imageURL = null;

    /**
     * <p>
     * A context relative path of an image to be displayed with
     * the control. If you want to be able to specify attributes
     * for the image, specify an <code>image</code> facet instead
     * of the <code>imageURL</code> attribute.</p>
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
     * <p>
     * A context relative path of an image to be displayed with
     * the control. If you want to be able to specify attributes
     * for the image, specify an <code>image</code> facet instead
     * of the <code>imageURL</code> attribute.</p>
     * @see #getImageURL()
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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
     * <p>
     * Identifies the control as participating as part
     * of a group. The <code>RadioButton</code> and <code>Checkbox</code>
     * classes determine the behavior of the group,
     * that are assigned the same value to the <code>name</code>
     * property. The value of this property must be unique for components
     * in the group, within the scope of the <code>Form</code>
     * parent component containing the grouped components.</p>
     */
    @Property(name="name", displayName="Group Name", category="Advanced")
    private String name = null;

    /**
     * <p>
     * Identifies the control as participating as part
     * of a group. The <code>RadioButton</code> and <code>Checkbox</code>
     * classes determine the behavior of the group,
     * that are assigned the same value to the <code>name</code>
     * property. The value of this property must be unique for components
     * in the group, within the scope of the <code>Form</code>
     * parent component containing the grouped components.</p>
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
     * <p>
     * Identifies the control as participating as part
     * of a group. The <code>RadioButton</code> and <code>Checkbox</code>
     * classes determine the behavior of the group,
     * that are assigned the same value to the <code>name</code>
     * property. The value of this property must be unique for components
     * in the group, within the scope of the <code>Form</code>
     * parent component containing the grouped components.</p>
     * @see #getName()
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>The object that represents the selections made from the
     * available options. If multiple selections are allowed, this
     * must be bound to ArrayList, an Object array, or an array of
     * primitives.</p>
     */
    @Property(name="selected", displayName="Selected", category="Data", editorClassName="com.sun.webui.jsf.component.propertyeditors.RbCbSelectedPropertyEditor")
    public Object getSelected() {
        return getValue();
    }

    /**
     * <p>The object that represents the selections made from the
     * available options. If multiple selections are allowed, this
     * must be bound to ArrayList, an Object array, or an array of
     * primitives.</p>
     * @see #getSelected()
     */
    public void setSelected(Object selected) {
        setValue(selected);
    }

    /**
     * <p>
     * The value of the component when it is selected. The value of this
     * property is assigned to the <code>selected</code> property when
     * the component is selected. The component is selected
     * when the <code>selected</code> property is equal to this value.</br>
     * This attribute can be bound to a <code>String</code>, or <code>
     * Object</code> value.</br>
     * If this property is not assigned a value, the component behaves
     * as a boolean component. A boolean component
     * is selected when the <code>selected</code> property is equal to a
     * true <code>Boolean</code> instance.<br>
     * If a boolean component is not selected, the <code>selected</code>
     * property value is a false <code>Boolean</code> instance.</p>
     */
    private Object _getSelectedValue() {
        return getItems();
    }

    /**
     * <p>
     * The value of the component when it is selected. The value of this
     * property is assigned to the <code>selected</code> property when
     * the component is selected. The component is selected
     * when the <code>selected</code> property is equal to this value.</br>
     * This attribute can be bound to a <code>String</code>, or <code>
     * Object</code> value.</br>
     * If this property is not assigned a value, the component behaves
     * as a boolean component. A boolean component
     * is selected when the <code>selected</code> property is equal to a
     * true <code>Boolean</code> instance.<br>
     * If a boolean component is not selected, the <code>selected</code>
     * property value is a false <code>Boolean</code> instance.</p>
     * @see #getSelectedValue()
     */
    public void setSelectedValue(Object selectedValue) {
        setItems(selectedValue);
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.imageURL = (String) _values[1];
        this.items = (Object) _values[2];
        this.name = (String) _values[3];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[4];
        _values[0] = super.saveState(_context);
        _values[1] = this.imageURL;
        _values[2] = this.items;
        _values[3] = this.name;
        return _values;
    }
}
