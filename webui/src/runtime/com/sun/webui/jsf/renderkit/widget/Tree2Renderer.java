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
import com.sun.webui.jsf.component.TreeNode2;
import com.sun.webui.jsf.component.Tree2;
import com.sun.webui.jsf.component.TreeNode2;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.jsf.util.JSONUtilities;

import java.io.IOException;

import java.util.StringTokenizer;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders the Tree2 component. A Tree2 component is
 * essentially a TreeNode2 object representing the root node of the tree. 
 * 
 * The decode method takes a look at the request parameter map events 
 * associated with nodes. 
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.Tree2", 
    componentFamily="com.sun.webui.jsf.Tree2"))
public class Tree2Renderer extends TreeNode2Renderer {
    

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * This method extracts the state of the tree in the case of page
     * submits. The state of the tree is represented by the changes in 
     * toggled nodes and changes in node selection since the last page
     * submit. This data is extracted from the request parameter map.
     * The data once extracted is used to set the selected and 
     * expanded state of the tree's nodes.
     */
    public void decode(FacesContext context, UIComponent component) {
        if (context == null) {
            throw new NullPointerException();
        }
	if (!(component instanceof Tree2)) {
	    throw new IllegalArgumentException(
                "Tree2Renderer can only render Tree2 components.");
        }
        
        Tree2 tree = (Tree2) component;

        // If the tree has been submitted it should contain the following
        // data in hidden fields:
        // treeId_selectedNodes : a list of selected nodes
        // treeId_toggleState : a list of non leaf nodes that have toggled
        // In both the cases above the data would be a list of nodes separated
        // by newlines.
        
        String selectParamId = component.getClientId(context) + "_selectedNodes";
        String expandedParamId = component.getClientId(context) + "_toggleState";
        String selectedNodes = (String) 
            context.getExternalContext().getRequestParameterMap().get(selectParamId);
        String toggledNodes = (String) 
            context.getExternalContext().getRequestParameterMap().get(expandedParamId);
        if (selectedNodes != null) {
            // set the new set of selected nodes
            StringTokenizer st = new StringTokenizer(selectedNodes, "\n");
            while (st.hasMoreTokens()) {
                String clientId = st.nextToken();
                clientId = clientId.trim();
                TreeNode2 node = TreeNode2.findChildNode(context, tree, clientId);
                if (node != null) {
                    if (node.isSelected()) {
                        node.setSelected(false);
                    } else {
                        node.setSelected(true);
                    }
                }
            }
            
        }
        
        if (toggledNodes != null) {
            // update the expanded/collapsed state of these nodes.
            // set the new set of selected nodes
            StringTokenizer st = new StringTokenizer(toggledNodes, "\n");
            while (st.hasMoreTokens()) {
                String clientId = st.nextToken();
                clientId = clientId.trim();
                TreeNode2 node = TreeNode2.findChildNode(context, tree, clientId);
                if (node != null) {
                    if (node.isExpanded()) {
                        node.setExpanded(false);
                    } else {
                        node.setExpanded(true);
                    }
                }
            }
        }
    }

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
                "NodeRenderer can only render Node components.");
        }  
        Tree2 node = (Tree2) component;
       
        JSONObject json = new JSONObject();
        
        // add tree (root) specific attributes.
        String attr = String.valueOf(node.isLoadOnExpand());
        if (attr != null && attr.length() != 0) {
            json.put("loadOnExpand", attr);
        } 
        
        attr = String.valueOf(node.isMultipleSelect());
        if (attr != null && attr.length() != 0) {
            json.put("multipleSelect", attr);
        } 
        
        // delegate to superclass for adding node specific attributes.
        super.getNodeProperties(context, node, json);
        
        return json;
    }               

    
    
    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "tree";
    }
    
    
    /** 
     * Helper method to obtain Tree2 children.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * @param json JSONObject to assign properties to.
     */
    protected void addChildren(FacesContext context,
        UIComponent component, JSONObject json) 
	    throws IOException, JSONException {

        JSONArray jArray = new JSONArray();
        Tree2 root = (Tree2)component;
        
        if (root.isLeaf()) {
            return;
        }
        
        for (UIComponent kid : component.getChildren()) {
            jArray.put(WidgetUtilities.renderComponent(context, kid));
        }
        
        if (jArray.length() > 0) {
            json.put("childNodes", jArray);  
        }
    }
}