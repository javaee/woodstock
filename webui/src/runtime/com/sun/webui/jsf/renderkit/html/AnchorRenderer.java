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


package com.sun.webui.jsf.renderkit.html;


import com.sun.faces.annotation.Renderer;
import com.sun.webui.html.HTMLElements;
import com.sun.webui.html.HTMLAttributes;
import com.sun.webui.jsf.component.Anchor;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ClientSniffer;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme;
import java.beans.Beans;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * <p>This class is responsible for rendering the {@link Anchor} component for the
 * HTML Render Kit.</p> <p> The {@link Anchor} component can be used as an anchor</p>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Anchor"))
public class AnchorRenderer extends javax.faces.render.Renderer{
    
    /**
     * <p>The set of String pass-through attributes to be rendered.</p>
     */
    private static final String stringAttributes[] =
    { "onclick", "onDblClick", "onKeyDown", "onKeyPress", "onMouseUp", //NOI18N
      "onKeyUp", "onMouseDown", "onMouseMove", "onMouseOut", "onMouseOver", //NOI18N
      "onFocus", "onBlur", "shape", "coords", "rel", "rev", "target", "type", //NOI18N
      "style"}; //NOI18N
    
    /*
     *<p> Id of the transparent image to be rendered for IE browsers
     */    
     private static String ANCHOR_IMAGE = "_img";   //NOI18N
    
    
      // -------------------------------------------------------- Renderer Methods
      
      public boolean getRendersChildren() {
          return true;
      }    
    /**
     * <p>Render the start of the anchor component. Attributes of the
     *  component which are specified by the developer are also rendered
     *  in this function call. </p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @exception IOException if an input/output error occurs
     */
    
    public void encodeBegin(FacesContext context, UIComponent component) 
            throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException(
		"FacesContext or UIComponent is null"); //NOI18N
        }        
	if (!component.isRendered()) {
	    return;
	}
        
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(HTMLElements.A, component);
        renderAttributes(component, context, writer);              
        renderContents (component, context, writer);
    }
    
    /**
     * <p>Render the children of the Anchor element. Override thie method
     * if you want the handling of the children to be done in a different
     * way. Note that the UIParameter components that are added as children
     * are not processed here. Instead they are used while generating the url.
     * The UIParameter name and value atributes are added as request parameters
     * to the anchor's url.</p>
     * 
     * @param component The UIComponent.
     * @param context The FacesContext for the current request
     * @param writer The ResponseWriter instance.
     */    
    public void encodeChildren(FacesContext context, UIComponent component) throws
    IOException{
        Iterator children = component.getChildren().iterator();
        UIComponent child;
        
        while (children.hasNext()) {
            child = (UIComponent)children.next();
            if (child instanceof UIParameter) {
                continue;
            } 
            RenderingUtilities.renderComponent(child, context);
        }
    }
    
    /**
     * <p>Render the end of the anchor component.</p>
     * This also renders a spacer image if there are no children or
     * text provided for the anchor. This is a work around for IE browsers
     * which does not locate the anchor if nothing is specified in the body
     * of the anchor.
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @exception IOException if an input/output error occurs
     */    
    public void encodeEnd(FacesContext context, UIComponent component)
        throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException(
		"FacesContext or UIComponent is null"); //NOI18N
        }        
	if (!component.isRendered()) {
	    return;
	}
        ResponseWriter writer = context.getResponseWriter();        
        String text = (String)component.getAttributes().get("text");                               
        if (component.getChildCount() == 0 || 
                (text == null && text.length() == 0)) {          
            ClientSniffer sniffer = ClientSniffer.getInstance(context);        
            if (sniffer.isIe6up() || sniffer.isIe7() || sniffer.isIe7up()) {              
               Icon icon = new Icon();
               icon.setIcon(ThemeImages.DOT);    
               icon.setId(component.getId()+ANCHOR_IMAGE);
               RenderingUtilities.renderComponent(icon, context);   
            }
        }
        writer.endElement(HTMLElements.A);
    }

    
    /**
     *<p> Render the attributes of the anchor element. Override this method if 
     *  custom attributes need to be rendered.
     * The id attribute is handled differently over here. The client id is not
     * output as the id of the html element instead the component id is itself
     * output as the id of the html element. The name attribute if specified
     * is output as the value of the html name value. If not, the value of the
     * id attribute is used as the value of the name attribute. 
     *
     * @param component The UIComponent.
     * @param context The FacesContext for the current request
     * @param writer The ResponseWriter instance.
     */
    protected void renderAttributes (UIComponent component, FacesContext context,
            ResponseWriter writer) throws IOException {
        Map attributes = component.getAttributes();   
        if (component instanceof Anchor) {
            writer.writeAttribute(HTMLAttributes.ID, component.getId(),
                null);          
        } else {
          writer.writeAttribute(HTMLAttributes.ID, 
                 component.getClientId(context), null);   
        }
         // Let the name attribute be as is so that it can be used 
        // as a normal anchor tag.
        String name = (String)component.getAttributes().get(HTMLAttributes.NAME);
        if (name == null || name.length() == 0) {
            writer.writeAttribute(HTMLAttributes.NAME, component.getId(),
                null);
        } else {
            writer.writeAttribute(HTMLAttributes.NAME, name,
                null);            
        }
        renderStringAttributes (component, context, writer);
        String url = (String)attributes.get("url");
        if (url != null) {
            renderURL (component, context, writer, url);
        }
        Integer _tabIndex = ((Integer)attributes.get(HTMLAttributes.TABINDEX));
        if (_tabIndex != null) {
            int tabIndex = (_tabIndex).intValue();
            if (!(tabIndex == Integer.MIN_VALUE)) {
                writer.writeAttribute(HTMLAttributes.TABINDEX,
                    tabIndex , null);
            }
        }
    }
    
    
    /**
     * For an anchor element, render the text. Other renderers which inherit
     * from this renderer, may want to show something besides the text (which
     * are not the component's children. For example the imageHyperilnk.)
     * Override this method to render the contents of the component appropriately.
     * The default implementation is, the text attribute of the component is rendered
     * first followed by the children that are specified for this component.
     * @param component The UIComponent.
     * @param context The FacesContext for the current request
     * @param writer The ResponseWriter instance.*
     *
     */
    protected void renderContents (UIComponent component, FacesContext context,
            ResponseWriter writer) throws IOException {
       String text = (String)component.getAttributes().get("text");
        if (text != null && text.length() > 0) {
            text = ConversionUtilities.convertValueToString(component,
               text);
            writer.write(text);       
        }
    }
    /**
     * <p> Render the string attributes of the anchor element.
     * @param component The UIComponent.
     * @param context The FacesContext for the current request
     * @param writer The ResponseWriter instance.*
     */
    private void renderStringAttributes(UIComponent component, FacesContext context,
            ResponseWriter writer) throws IOException {

        Map attributes = component.getAttributes();        
        RenderingUtilities.writeStringAttributes(component, writer,
                stringAttributes);
        String style = (String)attributes.get("style");
        if (style != null && style.length() > 0) {
        writer.writeAttribute(HTMLAttributes.STYLE, 
                style, null);
        }
       
        String styleClass = getStyleClass(component, context, attributes);        
        if (styleClass != null) {
        writer.writeAttribute(HTMLAttributes.CLASS, 
               styleClass , null);
        }        
        String tooltip = (String)attributes.get("toolTip");
        if (tooltip != null && tooltip.length() > 0) {
            writer.writeAttribute(HTMLAttributes.TITLE, 
                    tooltip, null);
        }
        String urlLang = (String)attributes.get("urlLang");
        if (urlLang != null && urlLang.length() > 0) {
            writer.writeAttribute(HTMLAttributes.HREFLANG, 
                  urlLang, null);
        }        
    }

    
    /**
     * <p>Returns the styleclass to be rendered for the component.
     * @param component The UIComponent.</p>
     * @param context The FacesContext for the current request
     * @param writer The ResponseWriter instance.     
     */
    protected String getStyleClass (UIComponent component, FacesContext context, Map attributes) {
        Theme theme = ThemeUtilities.getTheme(context);
        String styleclass = (String)attributes.get("styleClass");
        StringBuilder style = new StringBuilder();
        if (styleclass != null) {
            style.append(styleclass)
                 .append(" ");                                    
        }                   
        boolean visible = ((Boolean)attributes.get("visible")).booleanValue();
        boolean disabled = ((Boolean)attributes.get("disabled")).booleanValue();
        if (component.getAttributes().get("url") != null && !disabled) {
             style.append(theme.getStyleClass(ThemeStyles.ANCHOR))
                  .append(" ");
        } else {
             style.append(theme.getStyleClass(ThemeStyles.ANCHOR_DISABLED))
                  .append(" ");                                    
        }                          

        if (!visible) {          
            style.append(theme.getStyleClass(ThemeStyles.HIDDEN));
        }
        return style.toString();
  }
    
    
    /**
     * <p> Render the url portion of the anchor element.</p>
     * Subclasses of this renderer can override this method if the url handling
     * is to be done differently. This method uses <code>RenderingUtilities.renderURLAttribute</code>
     * to render the url attribute if the url does not start with a "#".
     * If the url starts with a "#", the url is rendered as is.
     *
     * @param component The UIComponent.
     * @param context The FacesContext for the current request
     * @param writer The ResponseWriter instance. 
     * @param url The url string to be rendered.
     */
    protected void renderURL (UIComponent component, FacesContext context, 
            ResponseWriter writer, String url) throws IOException {        
            if (!(url.startsWith("#"))) { //NOI18N
                url = context.getApplication().getViewHandler().
                        getResourceURL(context, url);
                RenderingUtilities.renderURLAttribute(context, 
                        writer, 
                        component, 
                        "href", //NOI18N  
                        url,
                        "url"); //NOI18N                                
            } else {
                writer.writeAttribute("href", url, null);
            }
    }
       
}
