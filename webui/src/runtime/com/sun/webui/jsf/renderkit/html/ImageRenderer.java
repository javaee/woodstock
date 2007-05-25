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

/*
 * ImageRenderer.java
 *
 * Created on November 16, 2004, 2:29 PM
 */

package com.sun.webui.jsf.renderkit.html;
import java.text.MessageFormat;
import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.theme.Theme;
import com.sun.webui.theme.ThemeImage;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.util.ClientSniffer;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import java.beans.Beans;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;

/**
 * Image renderer
 *
 * @author  Sean Comerford
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Image"))
public class ImageRenderer extends AbstractRenderer {
    
    /**
     * <p>The set of integer pass-through attributes to be rendered.</p>
     */
    private static final String integerAttributes[] = { "border", //NOI18N
            "hspace", "vspace" }; //NOI18N

    /**
     * <p>The set of String pass-through attributes to be rendered.</p>
     */
    private static final String stringAttributes[] = { "align", //NOI18N
    "onClick", "onDblClick", //NO18N
    "onMouseDown", "onMouseMove", "onMouseOut", "onMouseOver" }; //NOI18N

    /** Creates a new instance of ImageRenderer */
    public ImageRenderer() {
        // default constructor
    }

    /**
     * Render the start of the image element
     *
     * @param context The current FacesContext
     * @param component The ImageComponent object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurss
     */
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        // render start of image
        ImageComponent image = (ImageComponent) component;
        writer.startElement("img", image);  //NOI18N
    }

    /**
     * Render the image element's attributes
     *
     * @param context The current FacesContext
     * @param component The ImageComponent object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurss
     */
    protected void renderAttributes(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        // render image attrs
        ImageComponent image = (ImageComponent) component;

        String clientId = image.getClientId(context);
        if (clientId != null) {
            writer.writeAttribute("id", clientId, null);  //NOI18N
        }

        String url = image.getUrl();
        String icon = image.getIcon();
        String alt = image.getAlt();
        int height = image.getHeight();
        int width = image.getWidth();        
        Theme theme = ThemeUtilities.getTheme(context);
        if (image instanceof Icon || (icon != null && url == null)) {
	    // We just want some defaults if not specified by
	    // the component, call Theme.getImage directly instead
	    // of creating another component.
	    //
            ThemeImage themeImage = theme.getImage(icon);
            url = themeImage.getPath();
            // height
            int dim = themeImage.getHeight();
            if (height < 0 && dim >= 0) {
                height = dim;
            }
            // width
            dim = themeImage.getWidth();
            if (width < 0 && dim >= 0) {
                width = dim;
            }
            
            // alt, Here if the developer wants "" render "".
	    //
            String iconAlt = themeImage.getAlt();
            if (alt == null) {
                alt = iconAlt;
            }
            
        }  else if (url == null) {
            if (!Beans.isDesignTime()) {
                // log an error
                if (LogUtil.warningEnabled(ImageRenderer.class)) {
                    LogUtil.warning(ImageRenderer.class, "  URL  was not " +
                            "specified and generally should be"); // NOI18N
                }
            }
        } else {
            url = context.getApplication().getViewHandler()
                    .getResourceURL(context, url);
        }
        
        // must encode the url (even though we call the function later)!
        url = (url != null && url.trim().length() != 0)
            ? context.getExternalContext().encodeResourceURL(url) : "";

        String style = image.getStyle();
        StringBuffer errorMsg = new StringBuffer("Image's {0} was not") 
                .append(" specified. Using a generic")
                .append(" default value of {1}"); 
        MessageFormat mf = new MessageFormat(errorMsg.toString());         
        if (isPngAndIE(context, url)) {  
           
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
        }
        
        
        //write style class and style info
        RenderingUtilities.renderStyleClass(context, writer, image, null);
        if (style != null) {
            writer.writeAttribute("style", style, null); // NOI18N
        }
        
        RenderingUtilities.renderURLAttribute(context, writer, image, "src", //NO18N
                url, "url"); //NO18N
        
        // render alt
        if (alt != null) {
            writer.writeAttribute("alt", alt, null); // NOI18N
        } else {
            // alt is a required for XHTML compliance so output empty string
            // IS THIS ELSE NEEDED NOW THAT DESCRIPTION IS A REQUIRED PROPERTY?
            writer.writeAttribute("alt", "", null);  //NOI18N
        }

        // render the tooltip property as the image title attribute
        String toolTip = image.getToolTip();
        if (toolTip != null) {
            writer.writeAttribute("title", toolTip, null);   //NOI18N
        }

        // render the longDescription property as the image longdesc attribute
        String longDesc = image.getLongDesc();
        if (longDesc != null) {
            writer.writeAttribute("longdesc", longDesc, null); // NOI18N
        }

        // render height
        if (height >= 0) {
            writer.writeAttribute("height", Integer.toString(height), null); // NOI18N
        }

        // render width
        if (width >= 0) {
            writer.writeAttribute("width", Integer.toString(width), null); // NOI18N
        }
        
        addIntegerAttributes(context, component, writer, integerAttributes);
        addStringAttributes(context, component, writer, stringAttributes);        
    }

    /**
     * Render the end of the image element
     *
     * @param context The current FacesContext
     * @param component The ImageComponent object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurss
     */
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        // render end of image
        ImageComponent image = (ImageComponent) component;

        writer.endElement("img"); //NOI18N
    }
    
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Helper method to check whether the image specified is of type "png"
     * This menthod will return true only if the image is of type "png" and
     * the browser version is lesser than IE 7.x. These browsers seem to have
     * a problem with displaying png images.
     * @param context The FacesContext instance.
     * @param url The path to the specified image instance.
     *
     * @return A boolean value which indicates the image is of "png" type or not.
     */
    private boolean isPngAndIE(FacesContext context, String url) {
        ClientSniffer cs = ClientSniffer.getInstance(context);
        if (!cs.isIe() || cs.isIe7up()) {
                     return false;
        }         
        //Sometimes encodeResourceURL(url) adds the session id to the
        // image URL, make sure to take that in to account
        //
        if (url.indexOf("sessionid") != -1){ //NOI18N
            if (url.substring(0,url.indexOf(';')).
		    endsWith(".png")) { //NOI18N
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
