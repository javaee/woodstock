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

import javax.el.ValueExpression;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

/**
 * The Markup component allows HTML elements to be inserted into the JSP page 
 * where HTML is not permitted inside a JSF tag.
 */
@Component(type="com.sun.webui.jsf.Markup", family="com.sun.webui.jsf.Markup", displayName="Markup", tagName="markup",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_markup",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_markup_props")
public class Markup extends UIComponentBase {

    /**
     * <p>Construct a new <code>Markup</code>.</p>
     */
    public Markup() {
        super();
        setRendererType("com.sun.webui.jsf.Markup");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Markup";
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
     * <p>Add the rest of the attribute name="value" type pairs inside this 
     * attribute.  The inserted attributes will need to be escaped.</p>
     */
    @Property(name="extraAttributes", displayName="Extra Attributes", category="Advanced")
    private String extraAttributes = null;

    /**
     * <p>Add the rest of the attribute name="value" type pairs inside this 
     * attribute.  The inserted attributes will need to be escaped.</p>
     */
    public String getExtraAttributes() {
        if (this.extraAttributes != null) {
            return this.extraAttributes;
        }
        ValueExpression _vb = getValueExpression("extraAttributes");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Add the rest of the attribute name="value" type pairs inside this 
     * attribute.  The inserted attributes will need to be escaped.</p>
     * @see #getExtraAttributes()
     */
    public void setExtraAttributes(String extraAttributes) {
        this.extraAttributes = extraAttributes;
    }

    /**
     * <p>Flag indicating that tag is a singleton tag and that it should end with
     * a trailing /</p>
     */
    @Property(name="singleton", displayName="Single Tag", category="Advanced")
    private boolean singleton = false;
    private boolean singleton_set = false;

    /**
     * <p>Flag indicating that tag is a singleton tag and that it should end with
     * a trailing /</p>
     */
    public boolean isSingleton() {
        if (this.singleton_set) {
            return this.singleton;
        }
        ValueExpression _vb = getValueExpression("singleton");
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
     * <p>Flag indicating that tag is a singleton tag and that it should end with
     * a trailing /</p>
     * @see #isSingleton()
     */
    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
        this.singleton_set = true;
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
     * <p>Name of the HTML element to render.</p>
     */
    @Property(name="tag", displayName="Tag Name", category="Advanced", isDefault=true)
    private String tag = null;

    /**
     * <p>Name of the HTML element to render.</p>
     */
    public String getTag() {
        if (this.tag != null) {
            return this.tag;
        }
        ValueExpression _vb = getValueExpression("tag");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Name of the HTML element to render.</p>
     * @see #getTag()
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.extraAttributes = (String) _values[1];
        this.singleton = ((Boolean) _values[2]).booleanValue();
        this.singleton_set = ((Boolean) _values[3]).booleanValue();
        this.style = (String) _values[4];
        this.styleClass = (String) _values[5];
        this.tag = (String) _values[6];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[7];
        _values[0] = super.saveState(_context);
        _values[1] = this.extraAttributes;
        _values[2] = this.singleton ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.singleton_set ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.style;
        _values[5] = this.styleClass;
        _values[6] = this.tag;
        return _values;
    }

}
