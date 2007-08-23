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
/*
 * $Id: Property.java,v 1.6 2007-08-23 12:28:46 animeshkumarsahay Exp $
 */
package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Component;

import com.sun.webui.jsf.util.ComponentUtilities;

import java.beans.Beans;
import java.util.Iterator; 

import javax.el.ValueExpression;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

/**
 * The <code>Property</code> component was
 * written to be used within the<code>PropertySheetSection</code> component,
 * which is in turn used within the context of a <code>PropertySheet</code>
 * component. The<code>Property</code> component allows you to encapsulate a logic
 * "property" and help you lay it out on the page.  A "property" has a number
 * of configuration options, including: the property content; an optional
 * label; the ability to stretch the property to include the label area (in
 * addition to the content area of the "property"; the ability to mark a
 * property required; and the ability to associate help text with the property
 * to inform your end user how to interact with the property.</p><p> Help text 
 * can be provided for each property by supplying the<code>helpText</code> 
 * attribute.  This attribute may be a literal String or a 
 * <code>ValueBinding</code> expression.  The help text will appear
 * below the content of the "property".  Optionally, the helpText may also
 * be provided as a facet named "helpText".  This allows advanced users to
 * have more control over the types of content provided in the helpText
 * area.</p><p> The label may be provided via the <code>label</code> attribute.  
 * The label will be rendered to the left of the content area of the "property".  
 * The label area will not exist if the <code>overlapLabel</code> attribute is 
 * set to true.  Optionally advanced users may provide a label facet named
 * "label".  This allows developers to have more control over the content of
 * the label area.</p><p> The <code>labelAlign</code> attribute can use used to 
 * specify "left" or "right" alignment of the label table cell.</p><p> Setting 
 * the <code>noWrap</code> attribute to true specifies that the label should not
 * be wraped to a new line.</p><p> The <code>overlapLabel</code> attribute 
 * causes the content of the property to be stretched into the label area as 
 * well as the content area.  This may be useful for titles which should span 
 * the entire width, or other cases where you need the whole width of the 
 * <code>PropertySheet</code>.</p><p> The <code>requiredIndicator</code> 
 * attribute is only valid when you supply a label via an attribute (not as a 
 * facet).  When this attribute is marked <code>true</code>, the label that is
 * created will have its required attribute marked true.</p><p> For an example, 
 * please see the documentation for the <code>propertySheet</code> Tag.</p>
 */
@Component(type="com.sun.webui.jsf.Property", family="com.sun.webui.jsf.Property", displayName="Property", tagName="property",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_property",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_property_props")
public class Property extends UIComponentBase implements ComplexComponent,
	NamingContainer {

    public static final String CONTENT_FACET = "content"; //NOI18N
    public static final String HELPTEXT_FACET = "helpText"; //NOI18N
    public static final String LABEL_FACET = "label"; //NOI18N

    /**
     *	Constructor.
     */
    public Property() {
        super();
        setRendererType("com.sun.webui.jsf.Property");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Property";
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
     * The search for an suitable target for the label's "for"
     * attribute is complicated by the fact that <code>Property</code>
     * supports a <code>content</code> facet and random children.
     * The actual heuristic is implemented in
     * <code>{@link #findLabeledComponent}</code>.
     * </p>
     *
     * @param context The FacesContext used for the request
     * @return An abolute id suitable for the value of an HTML LABEL element's
     * <code>for</code> attribute.
     */
    public String getLabeledElementId(FacesContext context) {

        UIComponent labeledComponent = getLabeledComponent(context);
        if (labeledComponent == null) {
            return null;
        }
        // NOTE: Don't use ComplexComponent here, the Label component will.
        if (Beans.isDesignTime()) {
            //6474235: recalculate clientId
            UIComponent resetIdComp = labeledComponent;
            while (resetIdComp != null) {
                resetIdComp.setId(resetIdComp.getId());  
                resetIdComp = resetIdComp.getParent();
            }
        }
        if (labeledComponent instanceof ComplexComponent) {
            return ((ComplexComponent)
                labeledComponent).getLabeledElementId(context);
        } else {
            return labeledComponent.getClientId(context);
        }
    }

    /**
     * Return the actual component represented by this
     * <code>Property</code> that is referenced by <code>label</code>
     * in order to evaluate the <code>required</code> and <code>valid</code>
     * states of the component.
     *
     * @param context The current <code>FacesContext</code> instance
     * @param label The <code>Label</code> that labels this component.
     * @return a <code>UIComponent</code> in order to evaluate its
     * required and valid states.
     */
    public UIComponent getIndicatorComponent(FacesContext context,
            Label label) {
        return getLabeledComponent(context);
    }

    /**
     * Return the actual component this <code>Property</code>
     * represents. First check for the <code>content</code> facet
     * and if it exists call <code>findLabeledComponent</code>.
     * If there is no facet, call <code>findLabeledComponent</code>
     * to search through all facets and children.
     * <p>
     * <code>findLabeledComponent</code> returns the first
     * <code>EditableValue</code> facet or child.
     * </p>
     */
    private UIComponent getLabeledComponent(FacesContext context) {

        // Check for "content" facet first
        UIComponent contentFacet = getContentComponent();

        UIComponent labeledComponent = null;
        
        if (contentFacet == null) {
            // If there is no facet, assume that the content is specified 
            // as a child of this component. Search for a
            // required ComplexComponent among the children
            labeledComponent = findLabeledComponent(this, true);
        } else {
            // If a facet has been specified, see if the facet is a required
            // ComplexComponent or search for a required ComplexComponent
            // among the children of the facet component
            labeledComponent = findLabeledComponent(contentFacet, false);
        }
        return labeledComponent; 
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
     *
     * @param context The FacesContext used for the request
     */
    public String getFocusElementId(FacesContext context) {
	// Just return the same id as the labeled component for now.
	//
	return getLabeledElementId(context);
    }

    /**
     *	<p> This method calculates the proper <code>UIComponent</code> that
     *	    should be used when the label property is used with this
     *	    component.</p>
     *
     *	<p> This method provides the implementation for
     *	    {@link com.sun.webui.jsf.component.ComplexComponent}</p>
     *
     *	@param	context	    The <code>FacesContext</code>.
     *
     *	@return The <code>id</code> of the label target.
     *
     * @deprecated
     * @see #getLabeledElementId
     * @see #getFocusElementId
     */
    public String getPrimaryElementID(FacesContext context) {
	// Check for "content" facet first
	UIComponent contentFacet = getContentComponent();

	// The field component is the one that is labelled
	UIComponent labeledComponent = null;
        
	if (contentFacet == null) {
            // If there is no facet, assume that the content is specified 
            // as a child of this component. Search for a
            // required EditableValueHolderamong the children
	    //
	    labeledComponent = findLabeledComponent(this, true);
	} else {
            // If a facet has been specified, see if the facet is a required
            // EditableValueHolder or search for a required EditableValueHolder
            // among the children of the facet component
	    labeledComponent = findLabeledComponent(contentFacet, false);
	}
	
        if (labeledComponent != null) {
	    // Return an absolute path (relative is harder to calculate)
	    // NOTE: Label component does not fully support relative anyway,
	    // NOTE: the ":" I'm adding isn't necessary... however, it doesn't
	    // NOTE: hurt and if Label ever does support relative paths, the
	    // NOTE: ":" prefix is needed to specify a full path.
	    // NOTE:
	    // NOTE: Don't use ComplexComponent here, the Label component will.
            if (Beans.isDesignTime()) {
                //6474235: recalculate clientId
                UIComponent resetIdComp = labeledComponent;
                while (resetIdComp != null) {
                    resetIdComp.setId(resetIdComp.getId());  
                    resetIdComp = resetIdComp.getParent();
                }
            }
            return String.valueOf(NamingContainer.SEPARATOR_CHAR) + 
		labeledComponent.getClientId(context);
        }
        return null; 
    }

     /**
      *	<p> This method checks the component, children, and facets to see if
      *	    any of them are <code>EditableValueHolder</code>s.  The first one
      * found is returned, null
      *	    otherwise.</p>
      *
      *	@param	comp	The <code>UIComponent</code> to check.
      *	@param	skip	Flag indicating the initial component should be ignored
      *
      *	@return	The first <code>EditableValueHolder</code>, null if not found.
      */
    private UIComponent findLabeledComponent(UIComponent comp, 
	    boolean skip) {
	if (!skip) {
	    // Check to see if comp is an EditableValueHolder
	    if (comp instanceof EditableValueHolder) {
		return comp;
	    }
	}

	// Next check children and facets
	Iterator it = comp.getFacetsAndChildren();
	while (it.hasNext()) {
	    comp = findLabeledComponent((UIComponent) it.next(), false);
	    if (comp != null) {
		return comp;
	    }
	}
	// Not found
	return null;
    }

    /**
     * Return the a component that represents the content of the property.
     * If a facet called <code>content</code> does not exist <code>null</code>
     * is returned.
     */
    public UIComponent getContentComponent() {

	return getFacet(CONTENT_FACET);
    }

    /**
     * Return a component that implements help text.
     * If a facet named <code>helpText</code> is found
     * that component is returned. Otherwise a <code>HelpInline</code>
     * component is returned. It is assigned the id</br>
     * <code>getId() + "_helpText"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>HelpInline</code>
     * component is re-intialized every time this method is called.
     * </p>
     * <p>
     * If <code>getHelpeText</code> returns null, null is returned.
     * </p>
     *
     * @return a help text facet component
     */
    public UIComponent getHelpTextComponent() {

	UIComponent component = getFacet(HELPTEXT_FACET);
	if (component != null) {
	    return component;
	}

	String helpText = getHelpText();
	if (helpText == null) {
	    return null;
	}
	// Create one every time.
	//
	component = (UIComponent)new HelpInline();
	if (component == null) {
	    // log severe problem
	    return null;
	}
	// Assume helpText is literal
	//
	((HelpInline)component).setText(helpText);
	component.setId(
	    ComponentUtilities.createPrivateFacetId(this, HELPTEXT_FACET));
	component.setParent(this);
	((HelpInline)component).setType("field"); //NOI18N

	return component;

    }

    /**
     * Return a component that implements a label.
     * If a facet named <code>label</code> is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_label"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a label facet component
     */
    public UIComponent getLabelComponent() {

	UIComponent facet = getFacet(LABEL_FACET);
	if (facet != null) {
	    return facet;
	}

	// If label is null, don't return any component.
	// This may need to be revisited but is the common
	// behavior of other components, rightly or wrongly
	//
	String label = getLabel();
	if (label == null) {
	    return null;
	}
	// We know its a Label
	//
	Label component = (Label)ComponentUtilities.getPrivateFacet(this,
		LABEL_FACET, true);
	if (component == null) {
	    // This really should be done using JSF application
	    // create component, and component type.
	    //
	    component = new Label();
	    if (component == null) {
		// Log severe problem
		return null;
	    }
	    component.setId(ComponentUtilities.createPrivateFacetId(
		this, LABEL_FACET));
	    ComponentUtilities.putPrivateFacet(this, LABEL_FACET,
		component);
	}

	component.setText(label);

	// Theme should be queried for the label level if 
	// there isn't an attribute, which there should be.
	// The renderer verifies the value.
	//
	//component.setLabelLevel(getLabelLevel());

	// Currently there are heuristics implemented to try 
	// and find the labeled component, continue using that
	// for now.
	//
        // Need to set the "Property" as the labeled and indicator component.
        // It is a complex component. Otherwise we have
        // no way of finding the actual component or element
        // as the target of the label's "for" attribute. The "content" facet
        // or child may not have been instantiated yet.
        //
	component.setLabeledComponent(this);
	component.setIndicatorComponent(this);

	return component;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * The component identifier for this component. This value must be unique 
     * within the closest parent component that is a naming container.
     */
    @com.sun.faces.annotation.Property(name="id") 
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
    @com.sun.faces.annotation.Property(name="rendered") 
    public void setRendered(boolean rendered) {
        super.setRendered(rendered);
    }

    /**
     * <p>Flag indicating that the user is not permitted to activate this
     * component, and that the component's value will not be submitted with the
     * form.</p>
     */
    @com.sun.faces.annotation.Property(name="disabled", displayName="Disabled", category="Behavior")
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
     * <p>The text specified with this attribue is displayed below the content 
     * of the property in a small font. The value can be a literal String or 
     * a ValueBinding expression. If you want greater control over the content 
     * that is displayed in the help text area, use the helpText facet.</p>
     */
    @com.sun.faces.annotation.Property(name="helpText", displayName="Help Text", category="Appearance")
    private String helpText = null;

    /**
     * <p>The text specified with this attribue is displayed below the content 
     * of the property in a small font. The value can be a literal String or 
     * a ValueBinding expression. If you want greater control over the content 
     * that is displayed in the help text area, use the helpText facet.</p>
     */
    public String getHelpText() {
        if (this.helpText != null) {
            return this.helpText;
        }
        ValueExpression _vb = getValueExpression("helpText");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The text specified with this attribue is displayed below the content 
     * of the property in a small font. The value can be a literal String or 
     * a ValueBinding expression. If you want greater control over the content 
     * that is displayed in the help text area, use the helpText facet.</p>
     * @see #getHelpText()
     */
    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    /**
     * <p>Use this attribute to specify the text of the label of this
     * property. The text is displayed in the column that is
     * reserved for the label of this property row. The attribute value 
     * can be a string or a value binding expression. The label is associated with the
     * first input element in the content area of the property.  
     * To label a different component, use the label facet instead.</p>
     */
    @com.sun.faces.annotation.Property(name="label", displayName="Label", category="Appearance")
    private String label = null;

    /**
     * <p>Use this attribute to specify the text of the label of this
     * property. The text is displayed in the column that is
     * reserved for the label of this property row. The attribute value 
     * can be a string or a value binding expression. The label is associated with the
     * first input element in the content area of the property.  
     * To label a different component, use the label facet instead.</p>
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
     * <p>Use this attribute to specify the text of the label of this
     * property. The text is displayed in the column that is
     * reserved for the label of this property row. The attribute value 
     * can be a string or a value binding expression. The label is associated with the
     * first input element in the content area of the property.  
     * To label a different component, use the label facet instead.</p>
     * @see #getLabel()
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * <p>Specifies the alignment for the property label. 
     * The label occupies a cell in the first column of a table that is 
     * used to lay out the properties. Set the labelAlign attribute to make  
     * the label align to the left or right of the cell. The default alignment
     * is left. This attibute applies to labels that are specified with either 
     * the label attribute or the label facet.</p>
     */
    @com.sun.faces.annotation.Property(name="labelAlign", displayName="Label Alignment", category="Advanced")
    private String labelAlign = null;

    /**
     * <p>Specifies the alignment for the property label. 
     * The label occupies a cell in the first column of a table that is 
     * used to lay out the properties. Set the labelAlign attribute to make  
     * the label align to the left or right of the cell. The default alignment
     * is left. This attibute applies to labels that are specified with either 
     * the label attribute or the label facet.</p>
     */
    public String getLabelAlign() {
        if (this.labelAlign != null) {
            return this.labelAlign;
        }
        ValueExpression _vb = getValueExpression("labelAlign");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Specifies the alignment for the property label. 
     * The label occupies a cell in the first column of a table that is 
     * used to lay out the properties. Set the labelAlign attribute to make  
     * the label align to the left or right of the cell. The default alignment
     * is left. This attibute applies to labels that are specified with either 
     * the label attribute or the label facet.</p>
     * @see #getLabelAlign()
     */
    public void setLabelAlign(String labelAlign) {
        this.labelAlign = labelAlign;
    }

    /**
     * <p>Specifies that the label should not wrap around to another line, if set to
     * true. If the label is long, the label column in the table for the property  
     * sheet section expands to accomodate the label without wrapping to a new line.
     * This attibute applies to labels that are specified with either the label 
     * attribute or the label facet.</p>
     */
    @com.sun.faces.annotation.Property(name="noWrap", displayName="Label No-Wrap", category="Appearance")
    private boolean noWrap = false;
    private boolean noWrap_set = false;

    /**
     * <p>Specifies that the label should not wrap around to another line, if set to
     * true. If the label is long, the label column in the table for the property  
     * sheet section expands to accomodate the label without wrapping to a new line.
     * This attibute applies to labels that are specified with either the label 
     * attribute or the label facet.</p>
     */
    public boolean isNoWrap() {
        if (this.noWrap_set) {
            return this.noWrap;
        }
        ValueExpression _vb = getValueExpression("noWrap");
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
     * <p>Specifies that the label should not wrap around to another line, if set to
     * true. If the label is long, the label column in the table for the property  
     * sheet section expands to accomodate the label without wrapping to a new line.
     * This attibute applies to labels that are specified with either the label 
     * attribute or the label facet.</p>
     * @see #isNoWrap()
     */
    public void setNoWrap(boolean noWrap) {
        this.noWrap = noWrap;
        this.noWrap_set = true;
    }

    /**
     * <p>Specifies that the content of the property should occupy the label 
     * area as well as the content area, if set to true. The default value is 
     * false. This attribute is useful for properties that require the entire 
     * width of the property sheet.</p>
     */
    @com.sun.faces.annotation.Property(name="overlapLabel", displayName="Overlap Label", category="Appearance")
    private boolean overlapLabel = false;
    private boolean overlapLabel_set = false;

    /**
     * <p>Specifies that the content of the property should occupy the label 
     * area as well as the content area, if set to true. The default value is 
     * false. This attribute is useful for properties that require the entire 
     * width of the property sheet.</p>
     */
    public boolean isOverlapLabel() {
        if (this.overlapLabel_set) {
            return this.overlapLabel;
        }
        ValueExpression _vb = getValueExpression("overlapLabel");
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
     * <p>Specifies that the content of the property should occupy the label 
     * area as well as the content area, if set to true. The default value is 
     * false. This attribute is useful for properties that require the entire 
     * width of the property sheet.</p>
     * @see #isOverlapLabel()
     */
    public void setOverlapLabel(boolean overlapLabel) {
        this.overlapLabel = overlapLabel;
        this.overlapLabel_set = true;
    }

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @com.sun.faces.annotation.Property(name="style", displayName="CSS Style(s)", category="Appearance", editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
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
    @com.sun.faces.annotation.Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
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
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    @com.sun.faces.annotation.Property(name="visible", displayName="Visible", category="Behavior")
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
        this.disabled = ((Boolean) _values[1]).booleanValue();
        this.disabled_set = ((Boolean) _values[2]).booleanValue();
        this.helpText = (String) _values[3];
        this.label = (String) _values[4];
        this.labelAlign = (String) _values[5];
        this.noWrap = ((Boolean) _values[6]).booleanValue();
        this.noWrap_set = ((Boolean) _values[7]).booleanValue();
        this.overlapLabel = ((Boolean) _values[8]).booleanValue();
        this.overlapLabel_set = ((Boolean) _values[9]).booleanValue();
        this.style = (String) _values[10];
        this.styleClass = (String) _values[11];
        this.visible = ((Boolean) _values[12]).booleanValue();
        this.visible_set = ((Boolean) _values[13]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[14];
        _values[0] = super.saveState(_context);
        _values[1] = this.disabled ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.disabled_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.helpText;
        _values[4] = this.label;
        _values[5] = this.labelAlign;
        _values[6] = this.noWrap ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.noWrap_set ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.overlapLabel ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = this.overlapLabel_set ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = this.style;
        _values[11] = this.styleClass;
        _values[12] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[13] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
