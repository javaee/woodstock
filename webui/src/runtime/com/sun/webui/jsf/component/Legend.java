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
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.theme.Theme;

import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * The Legend component displays a legend to explain icons used in a page.
 */
@Component(type="com.sun.webui.jsf.Legend", family="com.sun.webui.jsf.Legend", displayName="Legend", tagName="legend",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_legend",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_legend_props")
public class Legend extends UIOutput implements NamingContainer {

    /** 
     * Facet name
     */
    public static final String LEGEND_IMAGE_FACET = "legendImage"; //NOI18N

    /**
     * Default constructor.
     */
    public Legend() {
        super();
        setRendererType("com.sun.webui.jsf.Legend");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Legend";
    }

    /**
     * Return a component that implements a legend image.
     * If a facet named <code>legendImage</code> is found
     * that component is returned.</br>
     * If a facet is not found an <code>Icon</code>
     * component instance is returned with the id</br>
     * <code>getId() + "_legendImage"</code>. The <code>Icon</code> instance
     * returned is ThemeImages.LABEL_REQUIRED_ICON.
     * <p>
     * If a facet is not defined then the returned <code>Icon</code>
     * component is created every time this method is called.
     * </p>
     * @return - legendImage facet or an Icon instance
     */
    public UIComponent getLegendImage() {
	// First check if an image facet was specified
	UIComponent imageFacet = getFacet(LEGEND_IMAGE_FACET);
	// If not create one with the default icon.
	if (imageFacet != null) {
	    return imageFacet;
	}

	Theme theme = ThemeUtilities.                    
	    getTheme(FacesContext.getCurrentInstance());           
	Icon icon = ThemeUtilities.getIcon(theme,
		ThemeImages.LEGEND_REQUIRED_ICON);
	icon.setId(
	    ComponentUtilities.createPrivateFacetId(this, LEGEND_IMAGE_FACET));
	icon.setParent(this);

	return icon;
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
     * The converter attribute is used to specify a method to translate native
     * property values to String and back for this component. The converter 
     * attribute value must be one of the following:
     * <ul>
     * <li>A JavaServer Faces EL expression that resolves to a backing bean or
     * bean property that implements the 
     * <code>javax.faces.converter.Converter</code> interface; or
     * </li><li>the ID of a registered converter (a String).</li>
     * </ul>
     */
    @Property(name="converter", isHidden=true, isAttribute=false)
    public Converter getConverter() {
        return super.getConverter();
    }
    
    // Hide value
    @Property(name="value", isHidden=true, isAttribute=false)
    public Object getValue() {
        return super.getValue();
    }

    /**
     * <p>Specifies the position of the legend. Valid values are: "right" (the default) and "left".</p>
     */
    @Property(name="position", displayName="Legend Position", category="Appearance")
    private String position = null;

    /**
     * <p>Specifies the position of the legend. Valid values are: "right" (the default) and "left".</p>
     */
    public String getPosition() {
        if (this.position != null) {
            return this.position;
        }
        ValueExpression _vb = getValueExpression("position");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Specifies the position of the legend. Valid values are: "right" (the default) and "left".</p>
     * @see #getPosition()
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name="style", displayName="CSS Style(s)", category="Appearance",
    editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
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
    @Property(name="styleClass", displayName="CSS Style Class(es)", 
            category="Appearance", 
            editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
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
     * <p>The explanatory text that is displayed in the legend. If not specified, the 
     * required field legend text is displayed.</p>
     */
    @Property(name="text", displayName="Legend Text", category="Appearance", isDefault=true,
        editorClassName="com.sun.rave.propertyeditors.StringPropertyEditor")
    private String text = null;

    /**
     * <p>The explanatory text that is displayed in the legend. If not specified, the 
     * required field legend text is displayed.</p>
     */
    public String getText() {
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
     * <p>The explanatory text that is displayed in the legend. If not specified, the required field legend text is displayed.</p>
     * @see #getText()
     */
    public void setText(String text) {
        this.text = text;
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
        this.position = (String) _values[1];
        this.style = (String) _values[2];
        this.styleClass = (String) _values[3];
        this.text = (String) _values[4];
        this.visible = ((Boolean) _values[5]).booleanValue();
        this.visible_set = ((Boolean) _values[6]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[7];
        _values[0] = super.saveState(_context);
        _values[1] = this.position;
        _values[2] = this.style;
        _values[3] = this.styleClass;
        _values[4] = this.text;
        _values[5] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
