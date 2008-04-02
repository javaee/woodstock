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
 * with the containers enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 */

package com.sun.webui.jsf.renderkit.widget;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.DndContainer;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.jsf.util.JSONUtilities;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * DndContainerRenderer is renderer for DndContainer component.
 * It renders basic set of DndContainer properties in the form of json object
 * @see DndContainer
 */
@Renderer(value = @Renderer.Renders(rendererType = "com.sun.webui.jsf.widget.DndContainer", 
    componentFamily = "com.sun.webui.jsf.DndContainer"))
public class DndContainerRenderer extends RendererBase {

    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String[] stringAttributes = { 
        "horizontalMarker", 
        "copyOnly", 
        "onNodeCreateFunc", 
        "onDropFunc", 
        "style", 
        "styleClass", 
        "visible"};
    /**
     * The set of int attributes to be rendered.
     */
    private static final String[] intAttributes = {"tabIndex"};
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to obtain component properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs
     * @exception JSONException if a key/value error occurs
     */
    protected JSONObject getProperties(FacesContext context, UIComponent component) 
            throws IOException, JSONException {
        if (!(component instanceof DndContainer)) {
            throw new IllegalArgumentException("DndContainerRenderer can only render DndContainer components.");
        }
        DndContainer container = (DndContainer) component;
    

        JSONObject json = new JSONObject();


        tokenizeToArray(json, "dropTypes", container.getDropTypes());
        tokenizeToArray(json, "dragTypes", container.getDragTypes());

        
        // Add attributes.
        JSONUtilities.addStringProperties(stringAttributes, component, json);
        JSONUtilities.addIntegerProperties(intAttributes, component, json);
        setContents(context, component, json);

        return json;
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "dndContainer";
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Property methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    /**
     * Helper method to obtain container children.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * @param json JSONObject to assign properties to.
     */
    protected void setContents(FacesContext context, UIComponent component, JSONObject json) 
            throws IOException, JSONException {

        Iterator kids = component.getChildren().iterator();

        if (!kids.hasNext()) {
            return;
        }

        JSONArray jArray = new JSONArray();
        json.put("contents", jArray);

        JSONArray jArrayDragData = new JSONArray();
        json.put("contentsDragData", jArrayDragData);

        while (kids.hasNext()) {
            UIComponent child = (UIComponent) kids.next();
            if (child.isRendered()) {
                jArray.put(WidgetUtilities.renderComponent(context, child));
                
                Object dragData =  child.getClientId(context);
                jArrayDragData.put(dragData);
            }
        }
    }
    
    protected JSONObject tokenizeToArray( JSONObject json, String name, String content) 
            throws JSONException {
        
        if (content == null)
            return json;
        
        JSONArray jArray = new JSONArray();
        json.put(name, jArray);
        
        StringTokenizer st = new StringTokenizer(content, ", ");
        while (st.hasMoreTokens()) {
            jArray.put(st.nextToken());
        }
        return json;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
