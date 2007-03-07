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

import java.text.MessageFormat;
import java.io.IOException;
import java.beans.Beans;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.Widget;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.component.Icon;

import com.sun.webui.theme.Theme;
import com.sun.webui.theme.ThemeImage;
import com.sun.webui.jsf.theme.ThemeJavascript;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeTemplates;

import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.ClientSniffer;

@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.Image", 
    componentFamily="com.sun.webui.jsf.Image"))
public class ImageRenderer extends RendererBase{
    
    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String attributes[] = {
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
        "onMouseMove",
        "tabIndex",
        "hspace",
        "vspace",
        "border",
    };
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Get the Dojo modules required to instantiate the widget.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONArray getModules(FacesContext context, UIComponent component)
            throws JSONException {
        JSONArray json = new JSONArray();
        json.put(JavaScriptUtilities.getModuleName("widget.image"));
        return json;
    }

    /** 
     * Helper method to obtain component properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */    
    protected JSONObject getProperties(FacesContext context,     
         UIComponent component) throws JSONException, IOException {

        ImageComponent image = (ImageComponent) component;
        String url = image.getUrl();
        String icon = image.getIcon();
        String alt = image.getAlt();
        int height = image.getHeight();
        int width = image.getWidth();        
        Theme theme = ThemeUtilities.getTheme(context);
        
        // If the url attribute is not set, check if the icon
        // attribute is set try setting the value of the url.
        // NOTE: we do not want to execute the getResourceURL
        // statement if the image provided is an icon.         
        if (image instanceof Icon || url == null) {
            if (icon != null) {
                url = theme.getImage(icon).getPath();
            }
            if (url == null) {
                // log an error
                if (LogUtil.warningEnabled(ImageRenderer.class)) {
                    LogUtil.warning(ImageRenderer.class, "  URL  was not " +
                            "specified and generally should be"); // NOI18N

                }
            }
            // We do not want to invoke getResourceURL for an icon component.
        } else {
            url = context.getApplication().getViewHandler()
                    .getResourceURL(context, url);
        }


      // must encode the url (even though we call the function later)!  
        url = (url != null && url.trim().length() != 0)
            ? context.getExternalContext().encodeResourceURL(url) : "";
         url = WidgetUtilities.encodeURL(context, component, url);       
         
        JSONObject json = new JSONObject();
        String style = image.getStyle();                     
        String templatePath = ((Widget)image).getHtmlTemplate(); // Get HTML template.                        
        json.put("templatePath", (templatePath != null)
                ? templatePath 
                : getTheme().getPathToTemplate(ThemeTemplates.IMAGE))        
            .put("visible", image.isVisible())
            .put("alt", alt)
            .put("title", image.getToolTip());        

        if (isPngAndIE(context, url)) {            
            setPngProperties(json, width, height, theme, style, url);
        } else {        
        json.put("src", url)
            .put("style", style);
        }
        if (width > 0) {
            json.put("width", width);
        }
        
        if (height > 0) {
            json.put("height", height);
        }
       // Add core and attribute properties.
        addAttributeProperties(attributes, component, json);
        setCoreProperties(context, component, json);

        return json;
    }
    

    
    protected void setPngProperties(JSONObject json, int width, int height, 
            Theme theme, String style, String url)throws JSONException,
            IOException {
        
        StringBuffer errorMsg = new StringBuffer("Image's {0} was not") 
                .append(" specified. Using a generic")
                .append(" default value of {1}"); 
        MessageFormat mf = new MessageFormat(errorMsg.toString());                          
        String imgHeight = null;
        String imgWidth = null;

        if (width >= 0) {
            imgWidth = Integer.toString(width);
        } else {
            imgWidth = theme.getMessage("Image.defaultWidth");
            if (LogUtil.fineEnabled(ImageRenderer.class)) {
                LogUtil.fine(ImageRenderer.class, mf.format(new String[]
                {"width",imgWidth}));  //NOI18N
            }
        }

        if (height >= 0) {
            imgHeight = Integer.toString(height);
        } else {
            imgHeight =theme.getMessage("Image.defaultHeight");
            if (LogUtil.fineEnabled(ImageRenderer.class)) {
                LogUtil.fine(ImageRenderer.class, mf.format(new String[]
                {"height",imgHeight}));  //NOI18N
            }
        }
        String IEStyle = theme.getMessage("Image.IEPngCSSStyleQuirk", 
                new String[] {imgWidth, imgHeight, url});
        url = theme.getImagePath(ThemeImages.DOT);
        if (style == null) {
           style =  IEStyle;
        } else {
            style=IEStyle+style;
        }
        json.put("style", style);
        json.put("src", url);
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Helper method to get Theme objects.
    private Theme getTheme() {
        return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }    
    private boolean isPngAndIE(FacesContext context, String url) {

        ClientSniffer cs = ClientSniffer.getInstance(context);
        
        //Some time encodeResourceURL(url) adds the sessiod to the
        // image URL, make sure to take that in to account
        //
        if (url.indexOf("sessionid") != -1){ //NOI18N
            if (url.substring(0,url.indexOf(';')).
		    endsWith(".png")&& cs.isIe6up()) { //NOI18N
                return false;
            } else if (url.substring(0,url.indexOf(';')).
		    endsWith(".png")&& cs.isIe5up()) { //NOI18N
                return true;
            }
        } else{ //</RAVE>
            // IE 6 SP 2 and above seems to have fixed the problem with .png images
            // But not SP1. For things to work on IE6 one needs to upgrade to SP2.
            if (url.endsWith(".png")) {
                if (cs.isIe6up()) {
                    return false;
                } else if (cs.isIe5up()) {
                    return true;
                }
            }
        }
        
        return false;
    }    
    
}
