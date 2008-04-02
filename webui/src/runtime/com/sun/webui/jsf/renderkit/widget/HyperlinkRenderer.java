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

import com.sun.webui.jsf.component.util.Util;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.faces.annotation.Renderer;

/**
 * This class renders an instance of the hyperlink component.
 * It outputs all the necessary properties necessary to instantiate an hyperlink widget
 * as JSON properties. 
 * 
 * All the children of the hyperlink component that are not 
 * specified as UIParameter, are added to the "contents" JSON array. The widget renderer
 * will parse through this array and render the children. For the children of the hyperlink
 * which are instances of the UIParameter component, their name value pairs are appended
 * a query parameters to the given url. These values of the UIParameter component are
 * also sent to the widget through a "params" JSON object. If the hyperlink widget
 * submits the form, it will append these values as request parameters to the url 
 * and submit the page.
 *
 * The decode method of the HyperlinkRenderer looks for a particular name value pair match
 * in the request parameter map. If it is present, then the method determines that it 
 * was this particular instance of the hyperlink that was submitted and it queues an
 * ActionEvent.
 *
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.Hyperlink", 
    componentFamily="com.sun.webui.jsf.Hyperlink"))
public class HyperlinkRenderer extends AnchorRendererBase{
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

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
        
        String paramId = this.getSubmittedParameterId(context, component);
        String value = (String) 
            context.getExternalContext().getRequestParameterMap().get(paramId);

        if ((value == null) || !value.equals(component.getClientId(context))) {
            return;

        }
        //add the event to the queue so we know that a command happened.
        //this should automatically take care of actionlisteners and actions
        component.queueEvent(new ActionEvent(component));
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "hyperlink";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Property methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Set the attributes of the hyperlink element.
     * Override this method if custom properties need to be set for the 
     * component.
     * This method overrides the setAttributes method present in the AnchorRendererBase
     * class.It outputs the form id and the values of the UIParameter component 
     * so that they can be used by the hyperlink widget while submitting the page.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * @param json The JSON object
     */    
    protected void setAttributes(FacesContext context, UIComponent component,
            JSONObject json) throws IOException, JSONException {
        // Avoid NPE exception when parent is not set.
        UIComponent form = Util.getForm(context, component);
        if (form != null) {
            String formClientId = form.getClientId(context);
            json.put("formId", formClientId);
        }

        // Append query parameters.
        JSONArray jarray = new JSONArray();
        json.put("params", jarray);

        Iterator kids = component.getChildren().iterator();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();

            if (kid instanceof UIParameter) {
                String name = (String) kid.getAttributes().get("name"); //NOI18N
                String value = (String) kid.getAttributes().get("value"); //NOI18N
                if (name == null || value == null) {                           
                    continue;
                }
                jarray.put(name);
                jarray.put(value);
            }
        }        
        super.setAttributes(context, component, json);     
    }

    /**
     * Returns the identifier for the parameter that corresponds to the hidden field
     * used to pass the value of the component that submitted the page.
     */
    protected String getSubmittedParameterId(FacesContext context, UIComponent component) {
        return component.getClientId(context) + "_submittedLink";
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
