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
import com.sun.faces.annotation.PropertyCategory;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase; /* For javadoc */
import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.el.ValueExpression;


/**
 *
 * @author deep
 */

@Component(
        type="com.sun.webui.jsf.MastFooter",
        family="com.sun.webui.jsf.MastFooter",
        displayName="MastFooter Section",
        instanceName="mastFooter",
        tagName="mastFooter")
        
public class MastFooter extends javax.faces.component.UIComponentBase
	implements NamingContainer {
    
    /**
     * Creates a new instance of MastFooter
     */
    public MastFooter() {
	super();
        setRendererType("com.sun.webui.jsf.MastFooter");
    }
    
    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.MastFooter";
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
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name="style", displayName="CSS Style(s)")
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
    @Property(name="styleClass", displayName="CSS Style Class(es)")
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
     * <p>The url to the image file to use for the Corporate Image. Use this 
     * attribute to override the corporate image that is set in the theme.</p>
     */
    @Property(name="corporateImageURL", displayName="Corporate Image URL", category="Navigation", editorClassName="com.sun.rave.propertyeditors.UrlPropertyEditor")
    private String corporateImageURL = null;

    /**
     * <p>The url to the image file to use for the Corporate Image. Use this 
     * attribute to override the corporate image that is set in the theme.</p>
     */
    public String getCorporateImageURL() {
        if (this.corporateImageURL != null) {
            return this.corporateImageURL;
        }
        ValueExpression _vb = getValueExpression("corporateImageURL");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The url to the image file to use for the Corporate Image. Use this 
     * attribute to override the corporate image that is set in the theme.</p>
     * @see #getCorporateImageURL()
     */
    public void setCorporateImageURL(String corporateImageURL) {
        this.corporateImageURL = corporateImageURL;
    }
    
    /**
     * <p>The description for the Corporate Image, used as alt text for the image.</p>
     */
    @Property(name="corporateImageDescription", displayName="Corporate Image Description", category="Appearance")
    private String corporateImageDescription = null;

    /**
     * <p>The description for the Corporate Image, used as alt text for the image.</p>
     */
    public String getCorporateImageDescription() {
        if (this.corporateImageDescription != null) {
            return this.corporateImageDescription;
        }
        ValueExpression _vb = getValueExpression("corporateImageDescription");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The description for the Corporate Image, used as alt text for the image.</p>
     * @see #getCorporateImageDescription()
     */
    public void setCorporateImageDescription(String corporateImageDescription) {
        this.corporateImageDescription = corporateImageDescription;
    }

    /**
     * <p>The height to use for the Corporate Image, in pixels. 
     * Use this attribute when specifying the corporateImageURL, along with 
     * the corporateImageWidth attribute, to specify dimensions of  PNG images 
     * for use in Internet Explorer.</p>
     */
    @Property(name="corporateImageHeight", displayName="Corporate Image Height", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int corporateImageHeight = Integer.MIN_VALUE;
    private boolean corporateImageHeight_set = false;

    /**
     * <p>The height to use for the Corporate Image, in pixels. 
     * Use this attribute when specifying the corporateImageURL, along with 
     * the corporateImageWidth attribute, to specify dimensions of  PNG images 
     * for use in Internet Explorer.</p>
     */
    public int getCorporateImageHeight() {
        if (this.corporateImageHeight_set) {
            return this.corporateImageHeight;
        }
        ValueExpression _vb = getValueExpression("corporateImageHeight");
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
     * <p>The height to use for the Corporate Image, in pixels. 
     * Use this attribute when specifying the corporateImageURL, along with 
     * the corporateImageWidth attribute, to specify dimensions of  PNG images 
     * for use in Internet Explorer.</p>
     * @see #getCorporateImageHeight()
     */
    public void setCorporateImageHeight(int corporateImageHeight) {
        this.corporateImageHeight = corporateImageHeight;
        this.corporateImageHeight_set = true;
    }


    /**
     * <p>The width to use for the Corporate Image URL, in pixels. Use this 
     * attribute along with the corporateImageHeight attribute to specify 
     * dimensions of  PNG images for use in Internet Explorer.</p>
     */
    @Property(name="corporateImageWidth", displayName="Corporate Image Width", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int corporateImageWidth = Integer.MIN_VALUE;
    private boolean corporateImageWidth_set = false;

    /**
     * <p>The width to use for the Corporate Image URL, in pixels. Use this 
     * attribute along with the corporateImageHeight attribute to specify 
     * dimensions of  PNG images for use in Internet Explorer.</p>
     */
    public int getCorporateImageWidth() {
        if (this.corporateImageWidth_set) {
            return this.corporateImageWidth;
        }
        ValueExpression _vb = getValueExpression("corporateImageWidth");
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
     * <p>The width to use for the Corporate Image URL, in pixels. Use this 
     * attribute along with the corporateImageHeight attribute to specify 
     * dimensions of  PNG images for use in Internet Explorer.</p>
     * @see #getCorporateImageWidth()
     */
    public void setCorporateImageWidth(int corporateImageWidth) {
        this.corporateImageWidth = corporateImageWidth;
        this.corporateImageWidth_set = true;
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
        this.style = (String) _values[1];
        this.styleClass = (String) _values[2];
        this.corporateImageURL = (String) _values[3];
        this.visible = ((Boolean) _values[4]).booleanValue();
        this.visible_set = ((Boolean) _values[5]).booleanValue();
        this.corporateImageDescription = (String) _values[6];
        this.corporateImageHeight = ((Integer) _values[7]).intValue();
        this.corporateImageHeight_set = ((Boolean) _values[8]).booleanValue();
        this.corporateImageWidth = ((Integer) _values[9]).intValue();
        this.corporateImageWidth_set = ((Boolean) _values[10]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[11];
        _values[0] = super.saveState(_context);
        _values[1] = this.style;
        _values[2] = this.styleClass;
        _values[3] = this.corporateImageURL;
        _values[4] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.corporateImageDescription;
        _values[7] = new Integer(this.corporateImageHeight);
        _values[8] = this.corporateImageHeight_set ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = new Integer(this.corporateImageWidth);
        _values[10] = this.corporateImageWidth_set ? Boolean.TRUE : Boolean.FALSE;
        
        return _values;
    }

}
