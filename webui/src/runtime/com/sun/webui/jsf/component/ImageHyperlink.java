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

import java.util.List;

import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * <p>Use the <code>webuijsf:imageHyperlink</code> tag to display a clickable 
 * image in the rendered HTML page. The image is surrounded by an HTML anchor, 
 * allowing the image to function as a hyperlink.&nbsp; 
 * This tag is based on a <code>webuijsf:hyperlink</code> tag and functions the same way.&nbsp; 
 * The main difference is this tag will format an image with a surrounding hyperlink.&nbsp; See
 * the <code>webuijsf:hyperlink</code>
 * tag for more examples on using a hyperlink.</span></span><br>
 * <br>
 * The <code>webuijsf:imageHyperlink</code> component can be also be used to submit forms. 
 * If the actionExpression attribute is used, the form is submitted. If the
 * url attribute is used, the link is a normal hyperlink that sends the browser to a new location.<br> 
 * The <code>webuijsf:imageHyperlink</code> can display a clickable icon image from the current theme 
 * in the rendered HTML page using the "icon" attribute. Take a look at the <code> webuijsf:image</code> 
 * tag to see how the icon attribute is to be used. The image that is specified as a part of the 
 * imageUrl or the icon attribute is shown for the enabled state of the imageHyperlink. 
 * If a <code>disabledImage</code> facet is specified, then that image will be shown when the 
 * imageHyperlink changes to the disabled state. If not, the image specified for the enabled state 
 * will be shown.<br>
 */
@Component(type="com.sun.webui.jsf.ImageHyperlink", family="com.sun.webui.jsf.ImageHyperlink", 
    displayName="Image Hyperlink", tagName="imageHyperlink",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_image_hyperlink",
     tagRendererType="com.sun.webui.jsf.widget.ImageHyperlink",        
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_image_hyperlink_props")
public class ImageHyperlink extends Hyperlink implements NamingContainer {
    private static final String IMAGE_FACET = "image"; //NOI18N

    /**
     * Used for identifying the facet in the facet map associated
     * with this component
     * This is used as a suffix combined with the id of the component.
     */
    final protected String IMAGE_FACET_SUFFIX = "_" + IMAGE_FACET; //NOI18N
    /**
     * Default constructor.
     */
    public ImageHyperlink() {
        super();
        setRendererType("com.sun.webui.jsf.widget.ImageHyperlink");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.ImageHyperlink";
    }
    
    /**
     * <p> Return the renderer type to be used for the component.</p>
     */
    public String getRendererType() {
        // Ensure we have a valid Ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.ImageHyperlink";
        }
        return super.getRendererType();
    } 

    // This facet is not meant to be overridden 
    // by others, but is only used as a storage bin for keeping the image
    // associated with the hyperlink
    // This component is actually created every time and is not
    // stored in the facet map.
    //
    /**
     * Return a component that implements an image or an icon.
     * If <code>getImageURL()</code> returns a value that is not 
     * null, an <code>ImageComponent</code> is returned.
     * If <code>getImageURL()</code> returns null and <code>getIcon()</code>
     * returns a value that is not null, an <code>Icon</code> component 
     * is returned. If both methods return null, null is returned.
     * The returned instance is intialized with the values from
     * <p>
     * <ul>
     * <li><code>getImageURL()</code></li>
     * <li><code>getIcon()</code></li>
     * <li><code>getAlign()</code></li>
     * <li><code>getBorder()</code></li>
     * <li><code>getAlt()</code></li>
     * <li><code>getHeight()</code></li>
     * <li><code>getHspace()</code></li>
     * <li><code>getVspace()</code></li>
     * <li><code>getWidth()</code></li>
     * <li><code>getDisabled</code></li>
     * <li><code>getType()</code></li>
     * </ul>
     * </p>
     * <p>
     * The returned <code>ImageComponent</code> or <code>Icon</code>
     * component is created every time this method is called.
     * </p>
     * @return ImageComponent or Icon instance
     */
    public ImageComponent getImageFacet() {

        String imageURL = getImageURL();
        String icon = getIcon();
        ImageComponent image = null;
        
	// ImageURL takes precedence
	if (imageURL != null) {
	    image = new ImageComponent();
	} else if (icon != null) {
	    image = new Icon();
	} else {
            return null;
        }

	image.setIcon(icon);
	image.setUrl(imageURL);

	setAttributes(
	    ComponentUtilities.createPrivateFacetId(this, IMAGE_FACET), image);
        
        return image;
    }
    
    protected void setAttributes(String facetId, ImageComponent image) {

	//must reset the id always due to a side effect in JSF and putting
        //components in a table.
        
        image.setId(facetId);
        image.setParent(this);
                
        // align
        String align = getAlign();
        if (align != null) {
            image.setAlign(align);
        }
        // border
        int dim = getBorder();
        if (dim >= 0) {
            image.setBorder(dim);
        }
        // description
        String description = getAlt();
        if (description != null) {
            image.setAlt(description);
        }
        // height
        dim = getHeight();
        if (dim >= 0) {
            image.setHeight(dim);
        }
        // hspace
        dim = getHspace();
        if (dim >= 0) {
            image.setHspace(dim);
        }
        // vspace
        dim = getVspace();
        if (dim >= 0) {
            image.setVspace(dim);
        }
        // width
        dim = getWidth();
        if (dim >= 0) {
            image.setWidth(dim);
        }
        // disabled (based on parent)
        Boolean disabled =
                (Boolean) getAttributes().get("disabled"); //NOI18N
        if (disabled != null) {
            image.getAttributes().put(
		"disabled", String.valueOf(disabled)); //NOI18N
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * <p>Specifies the position of the image with respect to its context.
     * Valid values are: bottom (the default); middle; top; left; right.</p>
     */
    @Property(name="onDblClick", isHidden=true, isAttribute=true)
    public String getOnDblClick() {
        return super.getOnDblClick();
    }

    /**
     * <p>Specifies the position of the image with respect to its context.
     * Valid values are: bottom (the default); middle; top; left; right.</p>
     */
    @Property(name="align", displayName="Align", category="Appearance")
    private String align = null;

    /**
     * <p>Specifies the position of the image with respect to its context.
     * Valid values are: bottom (the default); middle; top; left; right.</p>
     */
    public String getAlign() {
        if (this.align != null) {
            return this.align;
        }
        ValueExpression _vb = getValueExpression("align");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Specifies the position of the image with respect to its context.
     * Valid values are: bottom (the default); middle; top; left; right.</p>
     * @see #getAlign()
     */
    public void setAlign(String align) {
        this.align = align;
    }

    /**
     * <p>Alternative textual description of the image rendered by this component. The alt
     * text can be used by screen readers and in tool tips, and when image display is turned off in
     * the web browser.</p>
     */
    @Property(name="alt", displayName="Alt Text", category="Accessibility")
    private String alt = null;

    /**
     * <p>Alternative textual description of the image rendered by this component. The alt
     * text can be used by screen readers and in tool tips, and when image display is turned off in
     * the web browser.</p>
     */
    public String getAlt() {
        if (this.alt != null) {
            return this.alt;
        }
        ValueExpression _vb = getValueExpression("alt");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Alternative textual description of the image rendered by this component. The alt
     * text can be used by screen readers and in tool tips, and when image display is turned off in
     * the web browser.</p>
     * @see #getAlt()
     */
    public void setAlt(String alt) {
        this.alt = alt;
    }

    /**
     * <p>Specifies the width of the img border in pixels.
     * The default value for this attribute depends on the client browser</p>
     */
    @Property(name="border", displayName="Border", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int border = Integer.MIN_VALUE;
    private boolean border_set = false;

    /**
     * <p>Specifies the width of the img border in pixels.
     * The default value for this attribute depends on the client browser</p>
     */
    public int getBorder() {
        if (this.border_set) {
            return this.border;
        }
        ValueExpression _vb = getValueExpression("border");
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
     * <p>Specifies the width of the img border in pixels.
     * The default value for this attribute depends on the client browser</p>
     * @see #getBorder()
     */
    public void setBorder(int border) {
        this.border = border;
        this.border_set = true;
    }

    /**
     * <p>When specified, the width and height attributes tell the client browser to override the natural image or object size in favor of these values, specified in pixels. Some browsers might not support this behavior.</p>
     */
    @Property(name="height", displayName="Height", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int height = Integer.MIN_VALUE;
    private boolean height_set = false;

    /**
     * <p>When specified, the width and height attributes tell the client browser to override the natural image or object size in favor of these values, specified in pixels. Some browsers might not support this behavior.</p>
     */
    public int getHeight() {
        if (this.height_set) {
            return this.height;
        }
        ValueExpression _vb = getValueExpression("height");
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
     * <p>When specified, the width and height attributes tell the client browser to override the natural image or object size in favor of these values, specified in pixels. Some browsers might not support this behavior.</p>
     * @see #getHeight()
     */
    public void setHeight(int height) {
        this.height = height;
        this.height_set = true;
    }

    /**
     * <p>Specifies the amount of white space in pixels to be inserted to the left and 
     * right of the image. The default value is not specified but is 
     * generally a small, non-zero size.</p>
     */
    @Property(name="hspace", displayName="Horizontal Space", category="Advanced", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int hspace = Integer.MIN_VALUE;
    private boolean hspace_set = false;

    /**
     * <p>Specifies the amount of white space in pixels to be inserted to the left and 
     * right of the image. The default value is not specified but is 
     * generally a small, non-zero size.</p>
     */
    public int getHspace() {
        if (this.hspace_set) {
            return this.hspace;
        }
        ValueExpression _vb = getValueExpression("hspace");
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
     * <p>Specifies the amount of white space in pixels to be inserted to the left and 
     * right of the image. The default value is not specified but is 
     * generally a small, non-zero size.</p>
     * @see #getHspace()
     */
    public void setHspace(int hspace) {
        this.hspace = hspace;
        this.hspace_set = true;
    }

    /**
     * <p>The identifier of the desired theme image.</p>
     */
    @Property(name="icon", displayName="Icon", category="Appearance")
    private String icon = null;

    /**
     * <p>The identifier of the desired theme image.</p>
     */
    public String getIcon() {
        if (this.icon != null) {
            return this.icon;
        }
        ValueExpression _vb = getValueExpression("icon");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The identifier of the desired theme image.</p>
     * @see #getIcon()
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * <p>Absolute or relative URL to the image to be rendered.</p>
     */
    @Property(name="imageURL", displayName="Image Url", category="Appearance", editorClassName="com.sun.rave.propertyeditors.ImageUrlPropertyEditor")
    private String imageURL = null;

    /**
     * <p>Absolute or relative URL to the image to be rendered.</p>
     */
    public String getImageURL() {
        if (this.imageURL != null) {
            return this.imageURL;
        }
        ValueExpression _vb = getValueExpression("imageURL");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Absolute or relative URL to the image to be rendered.</p>
     * @see #getImageURL()
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * <p>Specifies where the text will be placed relative to the image. The valid 
     * values currently are "right" or "left".</p>
     */
    @Property(name="textPosition", displayName="Text Position", category="Appearance")
    private String textPosition = null;

    /**
     * <p>Specifies where the text will be placed relative to the image. The valid 
     * values currently are "right" or "left".</p>
     */
    public String getTextPosition() {
        if (this.textPosition != null) {
            return this.textPosition;
        }
        ValueExpression _vb = getValueExpression("textPosition");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return "right";
    }

    /**
     * <p>Specifies where the text will be placed relative to the image. The valid 
     * values currently are "right" or "left".</p>
     * @see #getTextPosition()
     */
    public void setTextPosition(String textPosition) {
        this.textPosition = textPosition;
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
     * <p>Specifies the amount of white space in pixels to be inserted above and below the 
     * image. The default value is not specified but is generally a small, 
     * non-zero size.</p>
     */
    @Property(name="vspace", displayName="Vertical Space", category="Advanced", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int vspace = Integer.MIN_VALUE;
    private boolean vspace_set = false;

    /**
     * <p>Specifies the amount of white space in pixels to be inserted above and below the 
     * image. The default value is not specified but is generally a small, 
     * non-zero size.</p>
     */
    public int getVspace() {
        if (this.vspace_set) {
            return this.vspace;
        }
        ValueExpression _vb = getValueExpression("vspace");
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
     * <p>Specifies the amount of white space in pixels to be inserted above and below the 
     * image. The default value is not specified but is generally a small, 
     * non-zero size.</p>
     * @see #getVspace()
     */
    public void setVspace(int vspace) {
        this.vspace = vspace;
        this.vspace_set = true;
    }

    /**
     * <p>Image width override. When specified, the width and height attributes 
     * tell user agents to override the natural image or object size in favor 
     * of these values.</p>
     */
    @Property(name="width", displayName="Width", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int width = Integer.MIN_VALUE;
    private boolean width_set = false;

    /**
     * <p>Image width override. When specified, the width and height attributes 
     * tell user agents to override the natural image or object size in favor 
     * of these values.</p>
     */
    public int getWidth() {
        if (this.width_set) {
            return this.width;
        }
        ValueExpression _vb = getValueExpression("width");
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
     * <p>Image width override. When specified, the width and height attributes 
     * tell user agents to override the natural image or object size in favor 
     * of these values.</p>
     * @see #getWidth()
     */
    public void setWidth(int width) {
        this.width = width;
        this.width_set = true;
    }
    

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.align = (String) _values[1];
        this.alt = (String) _values[2];
        this.border = ((Integer) _values[3]).intValue();
        this.border_set = ((Boolean) _values[4]).booleanValue();
        this.height = ((Integer) _values[5]).intValue();
        this.height_set = ((Boolean) _values[6]).booleanValue();
        this.hspace = ((Integer) _values[7]).intValue();
        this.hspace_set = ((Boolean) _values[8]).booleanValue();
        this.icon = (String) _values[9];
        this.imageURL = (String) _values[10];
        this.textPosition = (String) _values[11];
        this.visible = ((Boolean) _values[12]).booleanValue();
        this.visible_set = ((Boolean) _values[13]).booleanValue();
        this.vspace = ((Integer) _values[14]).intValue();
        this.vspace_set = ((Boolean) _values[15]).booleanValue();
        this.width = ((Integer) _values[16]).intValue();
        this.width_set = ((Boolean) _values[17]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[18];
        _values[0] = super.saveState(_context);
        _values[1] = this.align;
        _values[2] = this.alt;
        _values[3] = new Integer(this.border);
        _values[4] = this.border_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = new Integer(this.height);
        _values[6] = this.height_set ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = new Integer(this.hspace);
        _values[8] = this.hspace_set ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = this.icon;
        _values[10] = this.imageURL;
        _values[11] = this.textPosition;
        _values[12] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[13] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[14] = new Integer(this.vspace);
        _values[15] = this.vspace_set ? Boolean.TRUE : Boolean.FALSE;
        _values[16] = new Integer(this.width);
        _values[17] = this.width_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
