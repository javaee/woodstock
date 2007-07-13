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
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.sun.webui.jsf.component.ComplexComponent;

/**
 * The RadioButtonGroup component is used to display two or more radio buttons
 * in a grid layout in the rendered HTML page.
 */
@Component(type="com.sun.webui.jsf.RadioButtonGroup", family="com.sun.webui.jsf.RadioButtonGroup", displayName="Radio Button Group", tagName="radioButtonGroup",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_radiobutton_group",
    tagRendererType="com.sun.webui.jsf.widget.RadioButtonGroup",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_radiobutton_group_props")
public class RadioButtonGroup extends Selector implements NamingContainer,
	ComplexComponent {
    /**
     * Default constructor.
     */
    public RadioButtonGroup() {
        super();
	setMultiple(false);
        setRendererType("com.sun.webui.jsf.widget.RadioButtonGroup");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.RadioButtonGroup";
    }
    
     public String getRendererType() {
        // Ensure we have a valid Ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.RadioButtonGroup";
        }
        return super.getRendererType();
    }


    /**
     * Implement this method so that it returns the DOM ID of the 
     * HTML element which should receive focus when the component 
     * receives focus, and to which a component label should apply. 
     * Usually, this is the first element that accepts input. 
     * 
     * @param context The FacesContext for the request
     * @return The client id, also the JavaScript element id
     * @deprecated
     * @see #getLabeledElementId
     * @see #getFocusElementId
     */
    public String getPrimaryElementID(FacesContext context) {
	return this.getClientId(context);
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
	 // Return the first radio button id. We don't support children
	 // yet and the Renderer creates the radio buttons on the fly
	 // But we know what the id  that the renderer creates
	 // for the first radio button, hack, certainly.
	 //
	 return getFirstRbId(context);
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
	 // Return the first radio button id. We don't support children
	 // yet and the Renderer creates the radio buttons on the fly
	 // But we know what the id  that the renderer creates
	 // for the first radio button, hack, certainly.
	 //
	 return getFirstRbId(context);
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

    // Unfortunately we have to sneak and know that the new
    // widget rb renderer fixes a problem with rendering rb's.
    // First it makes the containing element have the component's
    // id, and then appends "RadioButton.RB_ID" to the component's
    // id. Since we don't have the RB's in the component tree
    // we can't call getLabeledElementId on the radio button.
    // So we hard code appending the "RB_ID" here.
    //
    private String getFirstRbId(FacesContext context) {
	StringBuilder sb = new StringBuilder(getClientId(context))
	    .append(String.valueOf(NamingContainer.SEPARATOR_CHAR))
	    .append(getId()).append("_0").append(RadioButton.RB_ID);
	return sb.toString();
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    // Hide onBlur
    @Property(name="onBlur", isHidden=true, isAttribute=false)
    public String getOnBlur() {
        return super.getOnBlur();
    }
    
    // Hide onChange
    @Property(name="onChange", isHidden=true, isAttribute=false)
    public String getOnChange() {
        return super.getOnChange();
    }
    
    // Hide onFocus
    @Property(name="onFocus", isHidden=true, isAttribute=false)
    public String getOnFocus() {
        return super.getOnFocus();
    }
    
    // Hide onSelect
    @Property(name="onSelect", isHidden=true, isAttribute=false)
    public String getOnSelect() {
        return super.getOnSelect();
    }
    
    // Hide value
    @Property(name="value", isHidden=true, isAttribute=false)
    public Object getValue() {
        return super.getValue();
    }

    /**
     * Defines how many columns may be used to layout the radio buttons. The
     * value must be greater than or equal to one. The default value is one. 
     * Invalid values are ignored and the value is set to one.
     */
    @Property(name="columns", displayName="Columns", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int columns = Integer.MIN_VALUE;
    private boolean columns_set = false;

    /**
     * <p>Defines how many columns may be used to layout the radio buttons.
     * 	The value must be greater than or equal to one. The
     * 	default value is one. Invalid values are ignored and the value
     * 	is set to one.</p>
     */
    public int getColumns() {
        if (this.columns_set) {
            return this.columns;
        }
        ValueExpression _vb = getValueExpression("columns");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return 1;
    }

    /**
     * <p>Defines how many columns may be used to layout the radio buttons.
     * 	The value must be greater than or equal to one. The
     * 	default value is one. Invalid values are ignored and the value
     * 	is set to one.</p>
     * @see #getColumns()
     */
    public void setColumns(int columns) {
        this.columns = columns;
        this.columns_set = true;
    }

    // visible
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
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.columns = ((Integer) _values[1]).intValue();
        this.columns_set = ((Boolean) _values[2]).booleanValue();
        this.visible = ((Boolean) _values[3]).booleanValue();
        this.visible_set = ((Boolean) _values[4]).booleanValue();
        this.htmlTemplate = (String) _values[5];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[6];
        _values[0] = super.saveState(_context);
        _values[1] = new Integer(this.columns);
        _values[2] = this.columns_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.htmlTemplate;
        return _values;
    }
}
