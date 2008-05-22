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
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */

package com.sun.webui.jsf.renderkit.ajax;

import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.TreeNode2;
import com.sun.webui.jsf.event.TreeNode2Event;
import com.sun.webui.jsf.util.ComponentUtilities;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Ajax renderer for the TreeNode2 component. It has its customized decode
 * method which looks out for a partial request for type "submit" If one
 * exists, then it runs through the decode process and updates the submittedValue
 * of the component with the value present.
 * 
 * In the encodeChildren method, it also iterates through any FacesMessages that
 * can exist and adds them to the JSON object.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.ajax.TreeNode2",
    componentFamily="com.sun.webui.jsf.TreeNode2"))
public class TreeNode2Renderer extends 
        com.sun.webui.jsf.renderkit.widget.TreeNode2Renderer {
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

   /**
    * This decode is done for Ajax requests.
    */
    public void decode(FacesContext context, UIComponent component) {
        if (context == null) {
            throw new NullPointerException();
        }
    
	if (!(component instanceof TreeNode2)) {
	    throw new IllegalArgumentException(
              "Tree2NodeRenderer can only render Node components.");
        } 
        
        TreeNode2 node = ((TreeNode2)component); 
        
        if (ComponentUtilities.isAjaxRequest(context, component, "loadChildren")) {
            try {        
                Map map = context.getExternalContext().getRequestHeaderMap();
                JSONObject xjson = new JSONObject((String)
                    map.get(AsyncResponse.XJSON_HEADER)); 
                String nodeId = (String) xjson.get("nodeId");    
                if (nodeId == null) {
                    return;
                }
                TreeNode2Event ne = new TreeNode2Event(node);
                ne.setEventType(TreeNode2Event.LOADCHILDREN_EVENT);
                node.queueEvent(ne);
                
            } catch(JSONException e) {            
            } catch(NullPointerException e) {} // JSON property may be null.
        }       
        
        if (ComponentUtilities.isAjaxRequest(context, component, "nodeSelected")) {
            try {        
                Map map = context.getExternalContext().getRequestHeaderMap();
                JSONObject xjson = new JSONObject((String)
                    map.get(AsyncResponse.XJSON_HEADER)); 
                String nodeId = (String) xjson.get("nodeId");    
                if (nodeId == null) {
                    return;
                }
                TreeNode2Event ne = new TreeNode2Event(node);
                ne.setEventType(TreeNode2Event.NODESELECTED_EVENT);
                node.queueEvent(ne);
                
            } catch(JSONException e) {            
            } catch(NullPointerException e) {} // JSON property may be null.
        }
        // Process submit events.
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
        
        } else if (ComponentUtilities.isAjaxRequest(context, component, "loadChildren")) {
            // load all its children
            super.encodeChildren(context, component);
        } else if (ComponentUtilities.isAjaxRequest(context, component, "selectNode")) {
            TreeNode2 node = (TreeNode2)component;
            node.setSelected(true);
        }
    }
    
}
