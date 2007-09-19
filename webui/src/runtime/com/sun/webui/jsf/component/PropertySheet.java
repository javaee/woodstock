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
 * The <code>PropertySheet</code> component is a <code>NamingContainer</code>  
 * used to layout <code>PropertySheetSection</code> components on a page.
 * Each <code>PropertySheetSection</code> may in turn have any number of
 * <code>Property</code> components within it.  This allows you to easily
 * format a page with a number of input or read-only fields.
 * <code>PropertySheetSection</code>s allow you to group <code>Property</code>
 * components together and provide a <code>label</code> for the set of
 * enclosed <code>Property</code>s.</p><p> The <code>PropertySheet</code> allows 
 * each<code>PropertySheetSection</code> to have an optional "jump link" from 
 * the top of the <code>PropertySheet</code> to each
 * <code>PropertySheetSection</code> within the <code>PropertySheet</code>.
 * This is accomplished by supplying the attribute <code>jumpLinks</code> with
 * a value of true.  If not specified, this attribute defaults to false. <p>For
 * an example, please see the documentation for the <code>propertySheet</code> 
 * Tag.</p>
 */
@Component(type="com.sun.webui.jsf.PropertySheet", family="com.sun.webui.jsf.PropertySheet", displayName="Property Sheet", tagName="propertySheet",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_property_sheet",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_property_sheet_props")
public class PropertySheet extends UIComponentBase implements NamingContainer {
    /**
     *	Constructor.
     */
    public PropertySheet() {
        super();
        setRendererType("com.sun.webui.jsf.PropertySheet");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.PropertySheet";
    }

    /**
     *	<p> This method calculates the number of visible
     *	    {@link PropertySheetSection}s.  A {@link PropertySheetSection} can
     *	    be made not visible by setting its rendered propety to false.  It
     *	    is also considered not visible if it contains no children
     *	    (sub-sections or properties).</p>
     *
     *	@return	The number of visible sections.
     */
    public int getSectionCount() {
	// Return the answer
	return getVisibleSections().size();
    }

    /**
     *	<p> This method creates a <code>List</code> of visible (rendered=true)
     *	    {@link PropertySheetSection} components.
     *	    {@link PropertySheetSection}s must also contain some content to be
     *	    considered visible.</p>
     *
     *	@return	A <code>List</code> of visible {@link PropertySheetSection}
     *		objects.
     */
    public List getVisibleSections() {
	int numChildren = getChildCount();

	// See if we've already figured this out
	if ((_visibleSections != null) && (_childCount == numChildren)) {
	    return _visibleSections;
	}
	_childCount = numChildren;

	// Make sure we have children
	if (numChildren == 0) {
	    _visibleSections = new ArrayList(0);
	    return _visibleSections;
	}

	// Add the visible sections to the result List
	UIComponent child = null;
	List visibleSections = new ArrayList();
	Iterator it = getChildren().iterator();
	while (it.hasNext()) {
	    child = (UIComponent)it.next();
	    if ((child instanceof PropertySheetSection) && 
		child.isRendered() && 
		((PropertySheetSection)child).isVisible()) {
		if (((PropertySheetSection)child).getVisibleSectionChildren().size() > 0) {
		    visibleSections.add(child);
		}
	    }
	}

	// Return the visible PropertySheetSections
	_visibleSections = visibleSections;
	return _visibleSections;
    }


    /**
     *	<p> Used to cache the visible sections.</p>
     */
    private transient List	_visibleSections = null;
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
        _visibleSections = null;
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
     * <p>This boolean attribute allows you to control whether jump links
     * will be created at the top of this <code>PropertySheet</code>
     * or not.  The default is NOT to create the links -- setting this
     * attribute to "true" turns this feature on.</p>
     */
    @Property(name="jumpLinks", displayName="Show Jump Links", category="Appearance")
    private boolean jumpLinks = false;
    private boolean jumpLinks_set = false;

    /**
     * <p>This boolean attribute allows you to control whether jump links
     * will be created at the top of this <code>PropertySheet</code>
     * or not.  The default is NOT to create the links -- setting this
     * attribute to "true" turns this feature on.</p>
     */
    public boolean isJumpLinks() {
        if (this.jumpLinks_set) {
            return this.jumpLinks;
        }
        ValueExpression _vb = getValueExpression("jumpLinks");
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
     * <p>This boolean attribute allows you to control whether jump links
     * will be created at the top of this <code>PropertySheet</code>
     * or not.  The default is NOT to create the links -- setting this
     * attribute to "true" turns this feature on.</p>
     * @see #isJumpLinks()
     */
    public void setJumpLinks(boolean jumpLinks) {
        this.jumpLinks = jumpLinks;
        this.jumpLinks_set = true;
    }

    /**
     * <p>Specifies whether to display a required field legend in the 
     * upper right area of the property sheet. This attribute should be set
     * to true if one or more properties in the property sheet sections are 
     * marked required. </p>
     */
    @Property(name="requiredFields", displayName="Required Field Legend", category="Appearance", editorClassName="com.sun.webui.jsf.component.propertyeditors.RequiredFieldsPropertyEditor")
    private String requiredFields = null;

    /**
     * <p>Specifies whether to display a required field legend in the 
     * upper right area of the property sheet. This attribute should be set
     * to true if one or more properties in the property sheet sections are 
     * marked required. </p>
     */
    public String getRequiredFields() {
        if (this.requiredFields != null) {
            return this.requiredFields;
        }
        ValueExpression _vb = getValueExpression("requiredFields");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Specifies whether to display a required field legend in the 
     * upper right area of the property sheet. This attribute should be set
     * to true if one or more properties in the property sheet sections are 
     * marked required. </p>
     * @see #getRequiredFields()
     */
    public void setRequiredFields(String requiredFields) {
        this.requiredFields = requiredFields;
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
        this.jumpLinks = ((Boolean) _values[1]).booleanValue();
        this.jumpLinks_set = ((Boolean) _values[2]).booleanValue();
        this.requiredFields = (String) _values[3];
        this.style = (String) _values[4];
        this.styleClass = (String) _values[5];
        this.visible = ((Boolean) _values[6]).booleanValue();
        this.visible_set = ((Boolean) _values[7]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[8];
        _values[0] = super.saveState(_context);
        _values[1] = this.jumpLinks ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.jumpLinks_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.requiredFields;
        _values[4] = this.style;
        _values[5] = this.styleClass;
        _values[6] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
