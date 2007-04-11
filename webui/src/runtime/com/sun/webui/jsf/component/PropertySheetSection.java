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

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;

/**
 * The <code>PropertySheetSection</code> component was written to be used
 * within the <code>PropertySheet</code> component.  It allows you to group
 * <code>Property<code> components together in sections within the
 * <code>PropertySheet</code> component.</p><p> When you use this component to
 * create a grouping of <code>Property</code> components, you may provide a 
 * description for the<code>PropertySheetSection</code>. This is done via the
 * <code>label</code> attribute. Set this attribute to the desired value, 
 * which, of course, may be a ValueBinding expression or a literal String.<p>For
 * an example, please see the documentation for the <code>propertySheet</code> 
 * Tag.</p>
 */
@com.sun.faces.annotation.Component(type="com.sun.webui.jsf.PropertySheetSection",
    family="com.sun.webui.jsf.PropertySheetSection",
    displayName="Property Sheet Section", instanceName="section", tagName="propertySheetSection",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_property_sheet_section")
public class PropertySheetSection extends UIComponentBase
        implements NamingContainer {
    /**
     *	Constructor.
     */
    public PropertySheetSection() {
        super();
        setRendererType("com.sun.webui.jsf.PropertySheetSection");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.PropertySheetSection";
    }

    /**
     *	<p> This method calculates the number of visible child
     *	    {@link PropertySheetSection} or {@link Property}
     *	    <code>UIComponent</code>s.  A {@link PropertySheetSection}
     *	    or {@link Property} can be made not visible by setting their
     *	    rendered property to false.</p>
     *
     *	@return The number of visible {@link PropertySheetSection} children.
     */
    public int getSectionChildrenCount() {
	// Set the output value
	return getVisibleSectionChildren().size();
    }

    /**
     *	<p> This method creates a <code>List</code> of visible (rendered=true)
     *	    child {@link PropertySheetSection} or {@link Property}
     *	    components.</p>
     *
     *	@return	<code>List</code> of child {@link PropertySheetSection} or
     *	    {@link Property} <code>UIComponent</code> objects.
     */
    public List getVisibleSectionChildren() {
	int numChildren = getChildCount();

	// See if we've already figured this out
	if ((_visibleChildren != null) && (_childCount == numChildren)) {
	    return _visibleChildren;
	}
	_childCount = numChildren;

	// Make sure we have children
	if (numChildren == 0) {
	    // Avoid creating child UIComponent List by checking for 0 sections
	    _visibleChildren = new ArrayList(0);
	    return _visibleChildren;
	}

	// Add the visible sections to the result List
	UIComponent child = null;
	_visibleChildren = new ArrayList();
	Iterator it = getChildren().iterator();
	while (it.hasNext()) {
	    child = (UIComponent)it.next();
	    if (((child instanceof Property) ||
		    (child instanceof PropertySheetSection)) &&
		    child.isRendered()) {
		_visibleChildren.add(child);
	    }
	}

	// Return the List
	return _visibleChildren;
    }


    /**
     *	<p> Used to cache the visible children.</p>
     */
    private transient List	_visibleChildren = null;
    private transient int	_childCount = -1;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // UIComponent methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * If the rendered property is true, render the begining of the current
     * state of this UIComponent to the response contained in the specified
     * FacesContext.
     *
     * If a Renderer is associated with this UIComponent, the actual encoding 
     * will be delegated to Renderer.encodeBegin(FacesContext, UIComponent).
     *
     * @param context FacesContext for the current request.
     *
     * @exception IOException if an input/output error occurs while rendering.
     * @exception NullPointerException if FacesContext is null.
     */
    public void encodeBegin(FacesContext context) throws IOException {
        // Clear cached variables -- bugtraq #6270214.
        _visibleChildren = null;
        _childCount = -1;
        super.encodeBegin(context);
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
     * <p>This attribute allows you to provide a label or title for the section
     * 	this <code>PropertySheetSection</code> defines for the<code>PropertySheet</code> component.  The value may be a literal
     * 	String, or it may be a ValueBinding expression (useful for
     * 	localization).</p>
     */
    @com.sun.faces.annotation.Property(name="label", displayName="Label", category="Appearance")
    private String label = null;

    /**
     * <p>This attribute allows you to provide a label or title for the section
     * 	this <code>PropertySheetSection</code> defines for the<code>PropertySheet</code> component.  The value may be a literal
     * 	String, or it may be a ValueBinding expression (useful for
     * 	localization).</p>
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
     * <p>This attribute allows you to provide a label or title for the section
     * 	this <code>PropertySheetSection</code> defines for the<code>PropertySheet</code> component.  The value may be a literal
     * 	String, or it may be a ValueBinding expression (useful for
     * 	localization).</p>
     * @see #getLabel()
     */
    public void setLabel(String label) {
        this.label = label;
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
     * is hidden with style attributes. By default, visible is set to true, so HTML for the component HTML is included and visible to the user. If the
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
     * is hidden with style attributes. By default, visible is set to true, so HTML for the component HTML is included and visible to the user. If the
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
     * is hidden with style attributes. By default, visible is set to true, so HTML for the component HTML is included and visible to the user. If the
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
        this.label = (String) _values[1];
        this.style = (String) _values[2];
        this.styleClass = (String) _values[3];
        this.visible = ((Boolean) _values[4]).booleanValue();
        this.visible_set = ((Boolean) _values[5]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[6];
        _values[0] = super.saveState(_context);
        _values[1] = this.label;
        _values[2] = this.style;
        _values[3] = this.styleClass;
        _values[4] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
