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
import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.jsf.component.Form;
import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.LogUtil;

import java.io.IOException;
import java.lang.NullPointerException;
import java.lang.StringBuffer;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.UICommand;
import javax.faces.component.UIForm;


import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;

/**
 * <p>This class is responsible for rendering the {@link Hyperlink} component for the
 * HTML Render Kit.</p> <p> The {@link Hyperlink} component can be used as an anchor, a
 * plain hyperlink or a hyperlink that submits the form depending on how the
 * properites are filled out for the component </p>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Hyperlink"))
public class HyperlinkRenderer extends AbstractRenderer {
    
    // -------------------------------------------------------- Static Variables
    
    //TODO: figure out a way to do anchors better than specifying the entire context path
    //TODO: use the style factories Rick is going to setup
    //TODO: move the javascript created here to a function in the default javascript.
    
    /**
     * <p>The set of boolean pass-through attributes to be rendered.
     *    <br />Note: if you add a boolean here and you want it rendered
     *    if the hyperlink is disabled then you must fix the renderer to 
     *    work properly! </p>
     */
    private static final String booleanAttributes[] =
    { "disabled" }; //NOI18N
    
    /**
     * <p>The set of integer pass-through attributes to be rendered.</p>
     */
    private static final String integerAttributes[] =
    { "tabIndex" }; //NOI18N
    
    
    /**
     * <p>The set of String pass-through attributes to be rendered.</p>
     */
    private static final String stringAttributes[] =
    { "onBlur", "onFocus", "onDblClick", "onKeyDown", "onKeyPress", "onMouseUp", //NOI18N
      "onKeyUp", "onMouseDown", "onMouseMove", "onMouseOut", "onMouseOver"}; //NOI18N

    /**
     * <p>The log message to be displayed if name and/or value attribute is null</p>
     */
    
    private static final String paramErr = "Hyperlink UIParameter child attribute name and/or value not set, id = "; //NOI18N
   
      // -------------------------------------------------------- Renderer Methods
      
      public boolean getRendersChildren() {
          return true;
      }
      
      /**
       * <p>Decode will determine if this component was the one that submitted the form.
       * It determines this by looking for the hidden field with the link's name 
       * appended with an "_submittedField"
       * If this hidden field contains the id of the component then this component submitted
       * the form.</p>
       * @param context <code>FacesContext</code> for the current request
       * @param component <code>UIComponent</code> to be decoded
       * @exception NullPointerException if <code>context</code> or
       * <code>component</code> is <code>null</code>
       */
      public void decode(FacesContext context, UIComponent component) {
          // Enforce NPE requirements in the Javadocs
          if ((context == null) || (component == null)) {
              throw new NullPointerException();
          }
          Hyperlink link = (Hyperlink) component;
                  
          if (isSubmitLink(link)) {
              String paramId = this.getSubmittedParameterId(context, component);
              String value = (String) 
                  context.getExternalContext().getRequestParameterMap().get(paramId);
              
              if ((value == null) || !value.equals(component.getClientId(context))) {
                  return;
              }

              //add the event to the queue so we know that a command happened.
              //this should automatically take care of actionlisteners and actions
              link.queueEvent(new ActionEvent(link));
          }
      }
      
      /**
       * Returns the identifier for the parameter that corresponds to the hidden field
       * used to pass the value of the component that submitted the page.
       */
      protected String getSubmittedParameterId(FacesContext context, UIComponent component) {
          return component.getClientId(context) + "_submittedLink";
      }
      
      
        /**
        * <p>Render the start of an anchor (hyperlink) tag.</p>
        * @param context <code>FacesContext</code> for the current request
        * @param component <code>UIComponent</code> to be rendered
        * @param writer <code>ResponseWriter</code> to which the element
        * start should be rendered
        * @exception IOException if an input/output error occurs
        */
       protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
            //intentionally left blank
       }
      
      
      /**
       * <p>Render the attributes for an anchor tag.  The onclick attribute will contain
       * extra javascript that will appropriately submit the form if the URL field is
       * not set.</p>
       * @param context <code>FacesContext</code> for the current request
       * @param component <code>UIComponent</code> to be rendered
       * @param writer <code>ResponseWriter</code> to which the element
       * attributes should be rendered
       * @exception IOException if an input/output error occurs
       */
    protected void renderAttributes(FacesContext context, UIComponent component,
        ResponseWriter writer) throws IOException {
            //intentionally left blank.
        }
      
    protected void finishRenderAttributes(FacesContext context, UIComponent component,
              ResponseWriter writer) throws IOException {
        
          Hyperlink link = (Hyperlink) component;
          
          // Set up local variables we will need
	  String label = ConversionUtilities.convertValueToString(component,
	      link.getText());
          if (label != null) {
              writer.writeText(label, null);
          }

      }  
         
     public  void encodeChildren(FacesContext context, UIComponent component)
        throws IOException {
         //purposefully don't want to do anything here!
         
     }
              
    /**
     * <p>Close off the anchor tag.</p>
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     * end should be rendered
     * @exception IOException if an input/output error occurs
     */
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        renderLink(context, component, writer);        
    }
    
    protected void renderLink(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        Hyperlink link = (Hyperlink) component;
        
        if (!link.isDisabled()) {
          // Start the appropriate element
          writer.startElement("a", link); //NOI18N
        } else {
          writer.startElement("span", link); //NOI18N
        }

        // Set up local variables we will need
	String label = ConversionUtilities.convertValueToString(component,
	    link.getText());

        String url   = link.getUrl();
        String target = link.getTarget();
        String tooltip = link.getToolTip();
        String onclick = link.getOnClick();
        String urlLang = link.getUrlLang();

        // Render core and pass through attributes as necessary
        String sb = getStyles(context, link);

        addCoreAttributes(context, component, writer, sb);
        addIntegerAttributes(context, component, writer, integerAttributes);
        addStringAttributes(context, component, writer, stringAttributes);

        if (!link.isDisabled()) {
            // no such thing as disabling a span so we must do this here.
            addBooleanAttributes(context, component, writer, booleanAttributes);

            // writeout href for the a tag:
            if (url != null) {
                // URL is not empty, check and see if it's an anchor, mailto,
                // or javascript -- see bugtraq #6306848 & #6322887.
                if (url.startsWith("#") //NOI18N
                        || url.startsWith("mailto:") //NOI18N
                        || url.startsWith("javascript:")) { //NOI18N
                    writer.writeURIAttribute("href", url, "url"); //NOI18N
                } else {
                    // Append context path to relative URLs -- bugtraq #6306727.
                    // Invoke the getCorrectURL method so that subclassed
                    // components may implement their own solution for 
                    // prepending the right context
                    url = getCorrectURL(context, link, url);
                    
                    RenderingUtilities.renderURLAttribute(context, 
                        writer, 
                        component, 
                        "href", //NOI18N  
                        url,
                        "url"); //NOI18N
                }
                if (onclick != null) {
                    writer.writeAttribute("onclick", onclick, "onclick");
                }
            } else {
                UIComponent form = Util.getForm(context, component);
                if (form != null)  {
                    String formClientId = form.getClientId(context);

                    StringBuffer buff = new StringBuffer(200);
                    if (onclick != null) {
                        buff.append(onclick);
                        if (!onclick.endsWith(";")) { //NOI18N
                            buff.append(";"); //NOI18N
                        }
                    }
                    buff.append("return ") //NOI18N
                        .append(JavaScriptUtilities.getModuleName("_html.hyperlink.submit")) //NOI18N
                        .append("(this, '") //NOI18N
                        .append(formClientId)
                        .append("', "); //NOI18N
                    
                    boolean didOnce = false;
                    Iterator kids = component.getChildren().iterator();
                    while (kids.hasNext()) {
                        UIComponent kid = (UIComponent) kids.next();
                        if (!(kid instanceof UIParameter)) {
                            continue;
                        }
                        String name = (String) kid.getAttributes().get("name"); //NOI18N
                        String value = (String) kid.getAttributes().get("value"); //NOI18N

                        if (name == null || value == null) {                           
                           log(paramErr+kid.getId());
                            continue;
                        }
                        if (!didOnce) {
                            buff.append("new Array(");
                        }

                        //add to map for later use.
                       if (!didOnce) {
                            buff.append("'");
                        } else {
                            buff.append(",'");
                        }
                        buff.append(name);
                        buff.append("','");
                        buff.append(value);
                        buff.append("'"); //NOI18N
                        didOnce = true;
                    }
                    
                    if (!didOnce) {
                        buff.append("null");
                    } else {
                        buff.append(")");
                    }
                    
                    buff.append(");");
                    writer.writeAttribute("onclick", buff.toString(), null);
                    writer.writeAttribute("href", "#", null); //NOI18N
                }
            }

            if (null != target) {
                writer.writeAttribute("target", target, null); //NOI18N
            }

            if (null != tooltip) {
                writer.writeAttribute("title", tooltip, null); //NOI18N
            }

            if (null != urlLang) {
                writer.writeAttribute("hreflang", urlLang, "urlLang"); //NOI18N
            }
            
        }
        //for hyperlink, this will encodeChildren as well, but not for subclasses
        //unless they explicitly do it!
        finishRenderAttributes(context, component, writer);

        renderChildren(context, component);
 
        // End the appropriate element
        if (!link.isDisabled()) {
            writer.endElement("a"); //NOI18N
         } else {
            // no need to render params for disabled link
            writer.endElement("span"); //NOI18N 
        }        
    }
    
    /**
     * This method is called by renderEnd. It is provided so renderers that
     * extend HyperlinkRenderer (such as TabRenderer) may override it in order 
     * to prevent children from always being rendered.
     *
     * @param context The current FacesContext.
     * @param component The current component.
     */
    protected void renderChildren(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeChildren(context, component);        
    }
      
      /**
       * This function returns the style classes necessary to display the {@link Hyperlink} component as it's state indicates
       * @return the style classes needed to display the current state of the component
       */
      protected String getStyles(FacesContext context, UIComponent component) {
          Hyperlink link = (Hyperlink) component;
          
          StringBuffer sb = new StringBuffer();
          Theme theme = ThemeUtilities.getTheme(context);
          if (link.isDisabled()) {
              sb.append(" "); //NOI18N
              sb.append(theme.getStyleClass(ThemeStyles.LINK_DISABLED));
          }
          return (sb.length() > 0) ? sb.toString() : null;
      }
      
      /**
       * This method returns the most appropriate URL under the 
       * circumstances. In some cases viewhandler.getActionURL() needs
       * to be invoked while in other cases viewhandler.getResourceURL() 
       * needs to be invoked. The hyperlink renderer, by default, will 
       * always use latter while generating the complete URL. Subclasses
       * of this renderer can do it their own way.
       */
      protected String getCorrectURL(FacesContext context, UIComponent component,
          String url) {
          
          if (url == null) {
              return null;
          }
          
          return context.getApplication().getViewHandler().
                        getResourceURL(context, url);
      }
      
// --------------------------------------------------------- Private Methods
      
      private boolean isSubmitLink(Hyperlink h) {
          return (h.getUrl() == null);
      }
      
    /**
     * Log an error 
     */
      private void log(String s) {
          if (LogUtil.fineEnabled(HyperlinkRenderer.class)) {
              LogUtil.fine(HyperlinkRenderer.class, s);
          }
      }

  }
