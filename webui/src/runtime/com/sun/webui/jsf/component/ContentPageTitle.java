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
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

/**
 * The ContentPageTitle component is used to display a page title.
 */
@Component(type="com.sun.webui.jsf.ContentPageTitle", family="com.sun.webui.jsf.ContentPageTitle", displayName="Content Area", instanceName="contentArea", tagName="contentPageTitle",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_content_page_title",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_content_page_title_props")
public class ContentPageTitle extends UIComponentBase 
        implements NamingContainer {
    public static final String CONTENT_BOTTOM_SEPARATOR = 
            "pageSeparator"; //NOI18N

    /**
     * Default Constructor.
     */
    public ContentPageTitle() {
        super();
        setRendererType("com.sun.webui.jsf.ContentPageTitle");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.ContentPageTitle";
    }

    /**
     * Return a component that implements an page separator image.
     * If a facet named <code>pageSeparator</code> is found
     * that component is returned.</br>
     * If a facet is not found a <code>PageSeparator</code>
     * component instance is returned with the id</br>
     * <code>getId() + "_pageSeparator"</code>.
     * <p>
     * If a facet is not defined then the returned <code>Icon</code>
     * component is created every time this method is called.
     * </p>
     * @return - pageSeparator facet or a PageSeparator instance
     */
    public UIComponent getBottomPageSeparator() {
        // First check if a buttons facet was defined 
        UIComponent bottomFacet =  getFacet(CONTENT_BOTTOM_SEPARATOR);
        if (bottomFacet != null) {
            return bottomFacet;
        }

        bottomFacet = new PageSeparator();
        bottomFacet.setId(ComponentUtilities.createPrivateFacetId(this,
                CONTENT_BOTTOM_SEPARATOR));
        bottomFacet.setParent(this);

        return bottomFacet;
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
     * <p>The help text to display just below the page title.</p>
     */
    @Property(name="helpText", displayName="Help Text", category="Appearance")
    private String helpText = null;

    /**
     * <p>The help text to display just below the page title.</p>
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
     * <p>The help text to display just below the page title.</p>
     * @see #getHelpText()
     */
    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    /**
     * <p>Indicates that the page title separator should be displayed, when set 
     * to true. The separator is a thin line that displays by default when 
     * bottom buttons are used. Set this attibute to false if the separator 
     * should not be displayed. This attribute also determines whether to 
     * display the pageSeparator facet.</p>
     */
    @Property(name="separator", displayName="Separator")
    private boolean separator = false;
    private boolean separator_set = false;

    /**
     * <p>Indicates that the page title separator should be displayed, when set 
     * to true. The separator is a thin line that displays by default when 
     * bottom buttons are used. Set this attibute to false if the separator 
     * should not be displayed. This attribute also determines whether to 
     * display the pageSeparator facet.</p>
     */
    public boolean isSeparator() {
        if (this.separator_set) {
            return this.separator;
        }
        ValueExpression _vb = getValueExpression("separator");
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
     * <p>Indicates that the page title separator should be displayed, when set 
     * to true. The separator is a thin line that displays by default when 
     * bottom buttons are used. Set this attibute to false if the separator 
     * should not be displayed. This attribute also determines whether to 
     * display the pageSeparator facet.</p>
     * @see #isSeparator()
     */
    public void setSeparator(boolean separator) {
        this.separator = separator;
        this.separator_set = true;
    }

    /**
     * <p>CSS style or styles to be applied to the outermost HTML element when this 
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
     * <p>CSS style or styles to be applied to the outermost HTML element when this 
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
     * <p>The text that is displayed as the page title.</p>
     */
    @Property(name="title", displayName="Title", category="Appearance")
    private String title = null;

    /**
     * <p>The text that is displayed as the page title.</p>
     */
    public String getTitle() {
        if (this.title != null) {
            return this.title;
        }
        ValueExpression _vb = getValueExpression("title");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The text that is displayed as the page title.</p>
     * @see #getTitle()
     */
    public void setTitle(String title) {
        this.title = title;
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
    @Property(name="visible", displayName="Visible")
    private boolean visible = false;
    private boolean visible_set = false;

    /**
     * <p>Indicates whether the component should be
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
     * <p>Indicates whether the component should be
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
        this.helpText = (String) _values[1];
        this.separator = ((Boolean) _values[2]).booleanValue();
        this.separator_set = ((Boolean) _values[3]).booleanValue();
        this.style = (String) _values[4];
        this.styleClass = (String) _values[5];
        this.title = (String) _values[6];
        this.visible = ((Boolean) _values[7]).booleanValue();
        this.visible_set = ((Boolean) _values[8]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[9];
        _values[0] = super.saveState(_context);
        _values[1] = this.helpText;
        _values[2] = this.separator ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.separator_set ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.style;
        _values[5] = this.styleClass;
        _values[6] = this.title;
        _values[7] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
