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

package com.sun.webui.jsf.renderkit.ajax;
import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.Menu;
import com.sun.webui.jsf.event.ValueEvent;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.util.ComponentUtilities;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Ajax renderer for the Menu component. It has its customized decode
 * method which looks out for a partial request for type "submit" If one
 * exists, then it runs through the decode process and updates the submittedValue
 * of the component with the value present.
 * 
 * In the encodeChildren method, it also iterates through any FacesMessages that
 * can exist and adds them to the JSON object.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.ajax.Menu",
    componentFamily="com.sun.webui.jsf.Menu"))
public class MenuRenderer extends 
        com.sun.webui.jsf.renderkit.widget.MenuRenderer {
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

   /**
    * This decode is done for Ajax requests. It checks whether the ajax request
    * is of type "submit". If so, it looks for a key called "value" in the request
    * parameter map. This contains the selected value of the Menu. If this
    * key exists, then it updates teh component's value to this value.
    */
    public void decode(FacesContext context, UIComponent component) {
        if (context == null) {
            throw new NullPointerException();
        }
    
	if (!(component instanceof Menu)) {
	    throw new IllegalArgumentException(
              "MenuRenderer can only render Menu components.");
        } 
        
        Menu menu = ((Menu)component);

        // Process submit events.
        if (ComponentUtilities.isAjaxRequest(context, component, "submit")) {
            try {        
                Map map = context.getExternalContext().getRequestHeaderMap();
                JSONObject xjson = new JSONObject((String)
                    map.get(AsyncResponse.XJSON_HEADER)); 
                String value = (String) xjson.get("value");    
                if (value == null) {
                    return;
                }
                if (checkSubmittedOption(value, menu.getOptionsArray())) {
                    ValueEvent me = new ValueEvent(component);
                    me.setSelectedOption(value);
                    menu.queueEvent(me);
                }
            } catch(JSONException e) {            
            } catch(NullPointerException e) {} // JSON property may be null.
        }        
    }
    /**
     * Render the beginning of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    public void encodeBegin(FacesContext context, UIComponent component) {
        // Do nothing...
    }    
    
    /**
     * Render the children of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
      public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException();
        }

        // Output component properties if Ajax request and is refresh event.
        if (ComponentUtilities.isAjaxRequest(context, component, "refresh")) {
            super.encodeChildren(context, component);
        }
    }
    
    /**
     * Render the ending of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    public void encodeEnd(FacesContext context, UIComponent component) {
        // Do nothing...
    }    
}
