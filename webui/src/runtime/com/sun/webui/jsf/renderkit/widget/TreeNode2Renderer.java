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

package com.sun.webui.jsf.renderkit.widget;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.Tree2;
import com.sun.webui.jsf.component.TreeNode2;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.jsf.util.JSONUtilities;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders an instance of the TreeNode2 component.
 * It traverses through the children of the TreeNode2 object and 
 * generates JSON properties for each child recursively.
 * 
 * The decode method takes a look at the request parameter map events 
 * associated with nodes.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.TreeNode2", 
    componentFamily="com.sun.webui.jsf.TreeNode2"))
public class TreeNode2Renderer extends RendererBase {
    /**
     * The set of pass-through attributes to be rendered.
     */
    protected static final String[] stringAttributes = {
        "style"
    };

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
   
    /** 
     * Helper method to obtain component properties.
     * Get the list of options that the user has specified. Traverse
     * through the list and add them to the JSON property list. This function
     * invokes the <code> getOptionProperties</code> for achieving this.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs
     * @exception JSONException if a key/value error occurs
     */    
    protected JSONObject getProperties(FacesContext context,     
            UIComponent component) throws JSONException, IOException {
	if (!(component instanceof TreeNode2)) {
	    throw new IllegalArgumentException(
                "TreeNode2Renderer can only render TreeNode2 components.");
        }  
        
        TreeNode2 node = (TreeNode2) component;
        JSONObject json = new JSONObject();
        getNodeProperties(context, node, json);
        return json;
    }               

    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "treeNode2";
    }
    
     
    /** 
     * Helper method to obtain the node's children.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * @param json JSONObject to assign properties to.
     */
    protected void addChildren(FacesContext context,
        UIComponent component, JSONObject json) 
	    throws IOException, JSONException {

        JSONArray jArray = new JSONArray();
        // json.put("children", jArray);

        if (component instanceof TreeNode2) {
            TreeNode2 node = (TreeNode2)component;
            
            // if node is a leaf node, return.
            if (node.isLeaf()) {
                return;
            }
            
            // check if loadOnexpand is set to true. 
            // if so, add children only of node is in expanded state
            Tree2 rootNode = Tree2.getRoot(node);
            if (rootNode.isLoadOnExpand()) {
                if (!node.isExpanded()) {
                    // return an empty array of children props
                    json.put("childNodes", jArray); 
                    return;
                }
            } 
        }
        
        json.put("childNodes", jArray);
        for (UIComponent kid : component.getChildren()) {
            jArray.put(WidgetUtilities.renderComponent(context, kid));
        }
    }
    
    /**
     * Private method to return the JSON properties associated with a 
     * tree node.
     */
    protected void getNodeProperties(FacesContext context,     
        TreeNode2 node, JSONObject json) throws JSONException, IOException {
        
        String attr = node.getStyleClass();
        if (attr != null && attr.length() != 0) {
            json.put("className", attr);
        } 
        
        attr = node.getToolTip();
        if (attr != null && attr.length() != 0) {
            json.put("toolTip", attr);
        } 
        
        attr = String.valueOf(node.isSelected());
        if (attr != null && attr.length() != 0) {
            json.put("selected", Boolean.parseBoolean(attr));
        } 
        
        attr = String.valueOf(node.isVisible());
        if (attr != null && attr.length() != 0) {
            json.put("visible", attr);
        } 
        
        attr = String.valueOf(node.isExpanded());
        if (attr != null && attr.length() != 0) {
            json.put("expanded", Boolean.parseBoolean(attr));
        } 
        json.put("parent", node.getParent().getClientId(context));
        
        Object mex = node.getAttributes().get("nodeSelectedActionListenerExpression");
        if (mex != null) {
            json.put("publishSelectionEvent", true);
        } else {
            json.put("publishSelectionEvent", false);
        }
        
        UIComponent labelFacet = node.getFacet(TreeNode2.NODE_LABEL_FACET_NAME);
        if (labelFacet != null) {
            json.put("label", WidgetUtilities.renderComponent(context, labelFacet));
        } else {
            json.put("label", node.getLabel());
        }
        
        UIComponent imageFacet = node.getFacet(TreeNode2.NODE_IMAGE_FACET_NAME);
        if (imageFacet != null) {
            json.put("image", WidgetUtilities.renderComponent(context, imageFacet));
        } else {
            String imageURL = node.getImageURL();
            if (imageURL != null && imageURL.length() > 0) {
                JSONObject imageObj = new JSONObject();
                imageObj.put("id", node.getId() + "_image")
                        .put("widgetType", "image")
                        .put("visible", true)
                        .put("border", 0)
                        .put("src", imageURL);
                json.put("image", imageObj);
            }
        }
            
        if (!node.isLeaf()) {
            addChildren(context, node, json);
        }
        
        // Add attributes.
        JSONUtilities.addStringProperties(stringAttributes, node, json);
    }
}    
    