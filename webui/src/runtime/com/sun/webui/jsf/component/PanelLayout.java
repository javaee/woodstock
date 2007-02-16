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

import com.sun.faces.annotation.Attribute;
import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

/**
 * The PanelLayout component is used to display a group of related components.
 */
@Component(type="com.sun.webui.jsf.PanelLayout", family="com.sun.webui.jsf.PanelLayout", displayName="Layout Panel", instanceName="layoutPanel", tagName="panelLayout",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_layout_panel",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_panel_layout_props")
public class PanelLayout extends UIComponentBase implements NamingContainer {
    
    /**
     * Keyword that indicates flow layout.
     */
    public final static String FLOW_LAYOUT = "flow";
    
    /**
     * Keyword that indicates grid layout or absolute positioning.
     */
    public final static String GRID_LAYOUT = "grid";
    
    /**
     * Creates a new instance of PanelLayout.
     */
    public PanelLayout() {
        super();
        setRendererType("com.sun.webui.jsf.PanelLayout");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.PanelLayout";
    }
    
    /**
     * Set the layout mode for this panel, to either "grid" or "flow". The
     * default value is "grid". Setting this property to an unrecognized value
     * will cause it to reset to the default value.
     */
    public void setPanelLayout(String panelLayout) {
        if (FLOW_LAYOUT.equals(panelLayout))
            _setPanelLayout(FLOW_LAYOUT);
        else
            _setPanelLayout(GRID_LAYOUT);
    }
    
    public String getPanelLayout() {
        String panelLayout = _getPanelLayout();
        if (panelLayout == null)
            return GRID_LAYOUT;
        return panelLayout;
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
     * <p>Use absolute grid positioning, or flow layout</p>
     */
    @Property(name="panelLayout", displayName="Panel Layout", attribute=@Attribute(name="panelLayout", isBindable=false, isRequired=false))
    private String panelLayout = null;

    /**
     * <p>Use absolute grid positioning, or flow layout</p>
     */
    private String _getPanelLayout() {
        return this.panelLayout;
    }

    /**
     * <p>Use absolute grid positioning, or flow layout</p>
     * @see #getPanelLayout()
     */
    private void _setPanelLayout(String panelLayout) {
        this.panelLayout = panelLayout;
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
        this.panelLayout = (String) _values[1];
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
        _values[1] = this.panelLayout;
        _values[2] = this.style;
        _values[3] = this.styleClass;
        _values[4] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
