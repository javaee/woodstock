/*
 * ImageHyperlinkRenderer.java
 *
 * Created on April 2, 2007, 2:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.webui.jsf.renderkit.widget;
import com.sun.faces.annotation.Renderer;

import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.component.ImageHyperlink;
import com.sun.webui.jsf.component.Link;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.WidgetUtilities;

import java.util.Iterator;
import javax.faces.component.UIParameter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.ImageHyperlink", 
    componentFamily="com.sun.webui.jsf.ImageHyperlink"))
public class ImageHyperlinkRenderer extends HyperlinkRenderer{

    // Used in positioning of the text.
   private static final String LABEL_LEFT="left"; //NOI8N
   private static final String LABEL_RIGHT="right"; //NOI8N     
    
    /**
     * The imageHyperlink renderer adds the children depending on the textPosition
     * attribute. The specified image will go to the left or to the right of the
     * given text based on the value of the textPosition value. The default value
     * of the textPosition value is "right".
     *
     * @param json The json object
     * @param component The ImageHyperlink component
     * @param context The FacesContext
     */
    protected void setContents(FacesContext context, UIComponent component, 
            JSONObject json)throws JSONException, IOException{
         ImageHyperlink ilink = (ImageHyperlink) component;    
          Object text = ilink.getText();
          // Store any children that are added as children to the imageHyperlink
          JSONArray children = new JSONArray();
          json.put("contents", children);
          String label = (text == null) ? null : 
                  ConversionUtilities.convertValueToString(component, text);
          String textPosition = ilink.getTextPosition();          
          
          ImageComponent ic = ilink.getImageFacet(); 
          
        if (label!= null && textPosition.equalsIgnoreCase(LABEL_LEFT)) {
             WidgetUtilities.addProperties(children, label);
             WidgetUtilities.addProperties(children, "&nbsp;");
        }
        if (ic != null) {
             WidgetUtilities.addProperties(children,
                   WidgetUtilities.renderComponent(context, ic));                                  
        } 
        if (label!= null && textPosition.equalsIgnoreCase(LABEL_RIGHT)) {
             WidgetUtilities.addProperties(children, "&nbsp;");                      
             WidgetUtilities.addProperties(children, label);
        }          
          
         UIComponent child;    
         Iterator it = component.getChildren().iterator();        
         while (it.hasNext()) {
             child = (UIComponent)it.next();
             if (!(child instanceof UIParameter)) {
                 WidgetUtilities.addProperties(children,
                    WidgetUtilities.renderComponent(context, child));                            
             }        
         }         
    }    
}
