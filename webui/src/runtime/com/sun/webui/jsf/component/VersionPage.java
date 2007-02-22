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
import javax.faces.component.NamingContainer;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

/**
 * The VersionPage component is used to display a version page.
 */
@Component(type="com.sun.webui.jsf.VersionPage", family="com.sun.webui.jsf.VersionPage", displayName="Version Page", tagName="versionPage",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_version_page",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_version_page_props")
public class VersionPage extends UIOutput implements NamingContainer {

    /**
     * <p>Construct a new <code>VersionPage</code>.</p>
     */
    public VersionPage() {
        super();
        setRendererType("com.sun.webui.jsf.VersionPage");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.VersionPage";
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

    // Hide converter
    @Property(isHidden=true, isAttribute=false)
    public Converter getConverter() {
        return super.getConverter();
    }
    
    // Hide value
    @Property(isHidden=true, isAttribute=false)
    public Object getValue() {
        return super.getValue();
    }
    
    /**
     * <p>The application copyright information.</p>
     */
    @Property(name="copyrightString", displayName="Copyright String", category="Appearance")
    private String copyrightString = null;

    /**
     * <p>The application copyright information.</p>
     */
    public String getCopyrightString() {
        if (this.copyrightString != null) {
            return this.copyrightString;
        }
        ValueExpression _vb = getValueExpression("copyrightString");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The application copyright information.</p>
     * @see #getCopyrightString()
     */
    public void setCopyrightString(String copyrightString) {
        this.copyrightString = copyrightString;
    }

    /**
     * <p>The description to use for the Product Name Image displayed in the version page.</p>
     */
    @Property(name="productImageDescription", displayName="Product Image Description", category="Appearance")
    private String productImageDescription = null;

    /**
     * <p>The description to use for the Product Name Image displayed in the version page.</p>
     */
    public String getProductImageDescription() {
        if (this.productImageDescription != null) {
            return this.productImageDescription;
        }
        ValueExpression _vb = getValueExpression("productImageDescription");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The description to use for the Product Name Image displayed in the version page.</p>
     * @see #getProductImageDescription()
     */
    public void setProductImageDescription(String productImageDescription) {
        this.productImageDescription = productImageDescription;
    }

    /**
     * <p>The height to use for the Product Name Image</p>
     */
    @Property(name="productImageHeight", displayName="Product Image Height", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int productImageHeight = Integer.MIN_VALUE;
    private boolean productImageHeight_set = false;

    /**
     * <p>The height to use for the Product Name Image</p>
     */
    public int getProductImageHeight() {
        if (this.productImageHeight_set) {
            return this.productImageHeight;
        }
        ValueExpression _vb = getValueExpression("productImageHeight");
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
     * <p>The height to use for the Product Name Image</p>
     * @see #getProductImageHeight()
     */
    public void setProductImageHeight(int productImageHeight) {
        this.productImageHeight = productImageHeight;
        this.productImageHeight_set = true;
    }

    /**
     * <p>The url to use for the Product Name Image</p>
     */
    @Property(name="productImageURL", displayName="Product Image URL", category="Navigation", editorClassName="com.sun.rave.propertyeditors.UrlPropertyEditor")
    private String productImageURL = null;

    /**
     * <p>The url to use for the Product Name Image</p>
     */
    public String getProductImageURL() {
        if (this.productImageURL != null) {
            return this.productImageURL;
        }
        ValueExpression _vb = getValueExpression("productImageURL");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The url to use for the Product Name Image</p>
     * @see #getProductImageURL()
     */
    public void setProductImageURL(String productImageURL) {
        this.productImageURL = productImageURL;
    }

    /**
     * <p>The width to use for the Product Name Image</p>
     */
    @Property(name="productImageWidth", displayName="Product Image Width", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int productImageWidth = Integer.MIN_VALUE;
    private boolean productImageWidth_set = false;

    /**
     * <p>The width to use for the Product Name Image</p>
     */
    public int getProductImageWidth() {
        if (this.productImageWidth_set) {
            return this.productImageWidth;
        }
        ValueExpression _vb = getValueExpression("productImageWidth");
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
     * <p>The width to use for the Product Name Image</p>
     * @see #getProductImageWidth()
     */
    public void setProductImageWidth(int productImageWidth) {
        this.productImageWidth = productImageWidth;
        this.productImageWidth_set = true;
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
     * <p>The name of version information file containing the formatted application version and copyright message.</p>
     */
    @Property(name="versionInformationFile", displayName="Version Information File", category="Appearance", isHidden=true, isAttribute=false)
    private String versionInformationFile = null;

    /**
     * <p>The name of version information file containing the formatted application version and copyright message.</p>
     */
    public String getVersionInformationFile() {
        if (this.versionInformationFile != null) {
            return this.versionInformationFile;
        }
        ValueExpression _vb = getValueExpression("versionInformationFile");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The name of version information file containing the formatted application version and copyright message.</p>
     * @see #getVersionInformationFile()
     */
    public void setVersionInformationFile(String versionInformationFile) {
        this.versionInformationFile = versionInformationFile;
    }

    /**
     * <p>The application version.</p>
     */
    @Property(name="versionString", displayName="Version String", category="Appearance")
    private String versionString = null;

    /**
     * <p>The application version.</p>
     */
    public String getVersionString() {
        if (this.versionString != null) {
            return this.versionString;
        }
        ValueExpression _vb = getValueExpression("versionString");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The application version.</p>
     * @see #getVersionString()
     */
    public void setVersionString(String versionString) {
        this.versionString = versionString;
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
    @Property(name="visible", displayName="Visible")
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
        this.copyrightString = (String) _values[1];
        this.productImageDescription = (String) _values[2];
        this.productImageHeight = ((Integer) _values[3]).intValue();
        this.productImageHeight_set = ((Boolean) _values[4]).booleanValue();
        this.productImageURL = (String) _values[5];
        this.productImageWidth = ((Integer) _values[6]).intValue();
        this.productImageWidth_set = ((Boolean) _values[7]).booleanValue();
        this.style = (String) _values[8];
        this.styleClass = (String) _values[9];
        this.versionInformationFile = (String) _values[10];
        this.versionString = (String) _values[11];
        this.visible = ((Boolean) _values[12]).booleanValue();
        this.visible_set = ((Boolean) _values[13]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[14];
        _values[0] = super.saveState(_context);
        _values[1] = this.copyrightString;
        _values[2] = this.productImageDescription;
        _values[3] = new Integer(this.productImageHeight);
        _values[4] = this.productImageHeight_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.productImageURL;
        _values[6] = new Integer(this.productImageWidth);
        _values[7] = this.productImageWidth_set ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.style;
        _values[9] = this.styleClass;
        _values[10] = this.versionInformationFile;
        _values[11] = this.versionString;
        _values[12] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[13] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }

}
