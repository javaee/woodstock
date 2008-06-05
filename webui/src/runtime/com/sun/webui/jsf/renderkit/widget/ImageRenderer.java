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

package com.sun.webui.jsf.renderkit.widget;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.component.Icon;

import com.sun.webui.theme.Theme;
import com.sun.webui.theme.ThemeImage;
import com.sun.webui.jsf.theme.ThemeImages;

import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.jsf.util.ClientSniffer;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/** 
 * This class renders an instance of the ImageComponent or the Icon component.
 * An image can either be represented by the url attribute or by the icon attribute
 * The icon attribute points to a themed image. If an icon attribute is present,
 * then the image's properties such as alt, width, height will be got directly 
 * from the theme. These default properties for the theme image can be overridden by 
 * specifying the value for these attributes on the component. 
 *
 * If a "png" type image is specified for rendering, then the image will not render
 * correctly on IE 6 and below. To fix this, an IE specific image transformation function is used. 
 * The url of the image to be displayed will be changed to a transparent pixel image which 
 * is assigned the height and width that the developer has specified for the image. 
 * If no height and width has been specified for the image then a default value of 
 * 100 px is used. The actual image to be displayed will become a part of the "png" style 
 * quirk that is used.
 * 
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.Image", 
    componentFamily="com.sun.webui.jsf.Image"))
public class ImageRenderer extends RendererBase {
    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String stringAttributes[] = {
        "align",
        "dir",
        "longDesc",
        "onBlur",
        "onClick",
        "onDblClick",
        "onFocus",
        "onKeyDown",
        "onKeyPress",
        "onKeyUp",
        "onMouseDown",
        "onMouseOut",
        "onMouseOver",
        "onMouseUp",
        "onMouseMove"        
    };
    
    /**
     * The set of int attributes to be rendered.
     */
    private static final String intAttributes[] = {
        "tabIndex",
        "hspace",
        "vspace",
        "border"
    };

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** 
     * Helper method to obtain component properties.
     * All the component properties are put into an JSON object.
     *
     * If the image provided is an instance of Icon component, and if the Icon
     * component was created by  using  ThemeUtilities.getIcon(Theme, <icon-key>)
     * the icon component instance created will be initialized with all the default
     * properties of the themed image such as height, width, alt and url.
     * 
     * Hence, while rendering an image, if the url property is not null, either
     * a) The image is an instance of imageComponent which has an url attribute specified or
     * b) The image is an instance if Icon component which was created using the
     * above mentioned method.
     *
     * In both the cases, the image represented by the url attribute can be rendered
     * safely ignoring the value represented by the icon attribute. But note that
     * in the case of an Icon component created using ThemeUtilities.getIcon(Theme, <icon-key>),
     * you do not need to call ViewHandler.getResourceURL(FacesContext, url)
     * while rendering the url since the value of the url stored in the Icon component
     * when it was created, will already have the absolute path to the image.
     *
     * If an Icon has been created in a backing bean by only setting the value of
     * the icon attribute to the themed image key, then this icon component will 
     * not have been initialized to have all the default properties of the themed
     * image. In this case, obtain an instance of the ThemeImage and then get
     * the default properties of the icon from the ThemeImage for rendering.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs
     * @exception JSONException if a key/value error occurs
     */    
    protected JSONObject getProperties(FacesContext context,     
            UIComponent component) throws JSONException, IOException {
	if (!(component instanceof ImageComponent)) {
	    throw new IllegalArgumentException(
                "ImageRenderer can only render ImageComponent components.");
        }
        ImageComponent image = (ImageComponent) component;
        String url = image.getUrl();
        String icon = image.getIcon();
        String alt = image.getAlt();
        int height = image.getHeight();
        int width = image.getWidth();  
        
        // If an icon attribute is set and the url attribute is null,
        // render the image represented by the icon attribute.
        // Else render an image represented by the url attribute.
        if ((icon != null && icon.length() > 0) &&
                (url == null || url.length() == 0)) {
	    // We just want some defaults if not specified by
	    // the component, call Theme.getImage directly instead
	    // of creating another component.
	    //
              ThemeImage themeImage = getTheme().getImage(icon);
              url = themeImage.getPath();

              // get the height of the themed icon image.
              int dim = themeImage.getHeight();
              if (height < 0 && dim >= 0) {
                  height = dim;
              }
              // get the width of the theme icon image.
              dim = themeImage.getWidth();
              if (width < 0 && dim >= 0) {
                  width = dim;
              }

              //get the  alt of the themed icon image.
              //Here if the developer wants "" render "".
              String iconAlt = themeImage.getAlt();
              if (alt == null) {
                  alt = iconAlt;
              }
              
            // If neither the url attribute nor the icon attribute have been
            // specified, log an error.
        } else if (url == null || url.length() == 0) {         
            if (LogUtil.warningEnabled(ImageRenderer.class)) {
                LogUtil.warning(ImageRenderer.class, " No image was " +
                    "specified and generally should be"); // NOI18N                        
            }
        } 

        // Send the context path to the client side widget.
        // This will be appended to the url of the image.
        String prefix = context.getExternalContext().getRequestContextPath();        

        // must encode the url (even though we call the function later)!  
        url = (url != null && url.trim().length() != 0)
            ? context.getExternalContext().encodeResourceURL(url) : "";
        url = WidgetUtilities.translateURL(context, image, url);

        String style = image.getStyle();
        JSONObject json = new JSONObject();                       
        json.put("visible", image.isVisible())
            .put("alt", alt)
            .put("title", image.getToolTip())
            .put("style", style) // Overridden by setPngProperties.
            .put("prefix", prefix);        

        // If the image object is an Icon instance, then dont output url.
        if (image instanceof Icon) {
            json.put("icon", icon);
        } else {
            json.put("src", url); // Don't output when icon is used.
        }
        
        if (width > 0) {
            json.put("width", width);
        }
        if (height > 0) {
            json.put("height", height);
        }

        String styleclass = image.getStyleClass();
        if (styleclass != null && styleclass.length() > 0) {
            json.put("className", styleclass);
        }

        // Add attributes.
        JSONUtilities.addStringProperties(stringAttributes, component, json);
        JSONUtilities.addIntegerProperties(intAttributes, component, json);
        
        return json;
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "image";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Property methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * This method adds a style quirk for IE browsers if the image type is "png".
     * The url of the image to be displayed will be changed to display a transparent
     * pixel image. The actual image to be displayed will be the part of the style quirk.
     * If no height and width is specified for this image, then a default height and
     * width of 100px will be assigned as IE does not handle the rendering of 
     * images well if the height and width are not specified.
     *
     * @param json The JSON object which contains the image's properties
     * @param width The width of the specified image.
     * @param height The height of the specified image.
     * @param theme The current theme instance to be used.
     * @param style The style that is to be applied for the image.
     * @param url The path to the specified image.
     *
     * @exception IOException if an input/output error occurs
     * @exception JSONException if a key/value error occurs
     * @deprecated This is done on the client side widget
     */
    protected void setPngProperties(JSONObject json, int width, int height, 
            Theme theme, String style, String url) throws JSONException, 
            IOException {
        String imgHeight = null;
        String imgWidth = null;

        if (width >= 0) {
            imgWidth = Integer.toString(width);
        } else {
            imgWidth = theme.getMessage("Image.defaultWidth");
        }

        if (height >= 0) {
            imgHeight = Integer.toString(height);
        } else {
            imgHeight = theme.getMessage("Image.defaultHeight");
        }
        String IEStyle = theme.getMessage("Image.IEPngCSSStyleQuirk", 
            new String[] {imgWidth, imgHeight, url});
        url = theme.getImagePath(ThemeImages.DOT);
        if (style == null) {
            style = IEStyle;
        } else {
            style = IEStyle + style;
        }
        json.put("style", style);
        json.put("src", url);
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to check whether the image specified is of type "png"
     * This menthod will return true only if the image is of type "png" and
     * the browser version is lesser than IE 7.x. These browsers seem to have
     * a problem with displaying png images.
     * 
     * @param context The FacesContext instance.
     * @param url The path to the specified image instance.
     * @return A boolean value which indicates the image is of "png" type or not.
     * @deprecated This is done on the client side widget
     */
    private boolean isPngAndIE(FacesContext context, String url) {
        ClientSniffer cs = ClientSniffer.getInstance(context);
        if (!cs.isIe() || cs.isIe7up()) {
            return false;
        }

        // Sometimes encodeResourceURL(url) adds the session id to the
        // image URL, make sure to take that in to account
        if (url.indexOf("sessionid") != -1){ //NOI18N
            if (url.substring(0,url.indexOf(';')).endsWith(".png")) { //NOI18N
                return true;
            }
        } else{ //</RAVE>
            if (url.endsWith(".png")) {
                return true;
            }
        }        
        return false;
    }
}
