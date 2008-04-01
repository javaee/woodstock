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
import com.sun.webui.html.HTMLAttributes;
import com.sun.webui.html.HTMLElements;
import com.sun.webui.jsf.component.Tree;
import com.sun.webui.jsf.component.TreeNode;
import com.sun.webui.jsf.component.TreeNode;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.theme.Theme;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.application.FacesMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>Renderer for a {@link com.sun.webui.jsf.component.Tree} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Tree"))
public class TreeRenderer extends TreeNodeRenderer {

    private static final String SKIPTREE_LINK = "skipTreeLink";
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Does nothing
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be decoded
     *
     * @exception NullPointerException if <code>context</code> or
     *  <code>component</code> is <code>null</code>
     */
    public void decode(FacesContext context, UIComponent component) {
        if (context == null || component == null) {
            throw new NullPointerException();
        }
    }

/**
  * Render a property component.
  * 
  * @param context The current FacesContext
  * @param component The Property object to render
  *
  * @exception IOException if an input/output error occurs
  */
  public void encodeEnd(FacesContext context, UIComponent component)
          throws IOException {

        Iterator messages = context.getMessages();
        if (messages != null) {
            while (messages.hasNext()) {
                FacesMessage fm = (FacesMessage)messages.next();
                LogUtil.fine(fm.getSummary());
                LogUtil.fine(fm.getDetail());
            }
        }
        
        if (context == null || component == null) {
            throw new NullPointerException();
        }

        if (!component.isRendered()) {
            return;
        }

        ResponseWriter writer = context.getResponseWriter();
        Tree node = (Tree)component;

        // Get the theme
        //
        Theme theme = ThemeUtilities.getTheme(context);

        // The title bar can be defined with either ui:tree tag attributes or facets. 
        // The title bar is rendered if the tree component includes imageURL property 
        // for the graphic, the text property for the title text, the content facet, 
        // or the image facet.

        // render outermost div of the tree.

        writer.write("\n\n\n");
        writer.startElement(HTMLElements.DIV, node);
        writer.writeAttribute(HTMLAttributes.ID, node.getClientId(context), null);
        String nodeStyleClass = theme.getStyleClass(ThemeStyles.TREE);
        if (!node.isVisible()) {
            nodeStyleClass = theme.getStyleClass(ThemeStyles.HIDDEN);
        } else if (node.getStyleClass() != null) {
            nodeStyleClass = node.getStyleClass();
        }
        writer.writeAttribute(HTMLAttributes.CLASS, nodeStyleClass, null);
        if (node.getStyle() != null) {
            writer.writeAttribute(HTMLAttributes.STYLE, node.getStyle(), null);
        }
        writer.write("\n");

        // render the skip hyper link to support A11Y
        RenderingUtilities.renderSkipLink(SKIPTREE_LINK,
                theme.getStyleClass(ThemeStyles.SKIP_WHITE), null,
                theme.getMessage("tree.skipTagAltText"), // NOI18N
                null, node, context);
        writer.write("\n");
       
        // add the spacer
        writer.write("\n");
        String rootText = node.getText();
        String rootImageURL = node.getImageURL();
        boolean hasRootContentFacet = 
            (node.getFacet(Tree.TREE_CONTENT_FACET_NAME) != null);
        boolean hasRootImageFacet = 
            (node.getFacet(Tree.TREE_IMAGE_FACET_NAME) != null);

        if ((rootText != null && rootText.length() > 0 ) ||
            rootImageURL != null || 
            hasRootImageFacet || hasRootContentFacet) {

            String titlebarSpacerDivID = node.getClientId(context) + "TitleBarSpacer";
            String titlebarDivID = node.getClientId(context) + "TitleBar";
            String lineImageDivID = node.getClientId(context) + "LineImages";
            String lineTxtDivID = node.getClientId(context) + "LineText";

            // title bar spacer
            writer.startElement(HTMLElements.DIV, node);
            writer.writeAttribute(HTMLAttributes.ID, titlebarSpacerDivID, null);
            writer.writeAttribute(HTMLAttributes.CLASS, 
                theme.getStyleClass(ThemeStyles.TREE_ROOT_ROW_HEADER), null);
            writer.endElement(HTMLElements.DIV);
            writer.write("\n"); // NOI18N

            writer.startElement(HTMLElements.DIV, node); // tree root row start
            writer.writeAttribute(HTMLAttributes.ID, titlebarDivID, null);
            writer.writeAttribute(HTMLAttributes.CLASS, 
                theme.getStyleClass(ThemeStyles.TREE_ROOT_ROW), null);
            writer.write("\n");
            Iterator imageIter = node.getImageKeys().iterator();
            if (((node.getUrl() != null) && (node.getUrl().length() > 0)) || 
                hasRootContentFacet) {
                
                renderTreeRow(node, imageIter, context, writer);

            } else {
                               
                writer.write("\n"); // NOI18N
                writer.startElement(HTMLElements.SPAN, node);
                writer.writeAttribute(HTMLAttributes.CLASS, 
                    theme.getStyleClass(ThemeStyles.TREE_TITLE), null);
                writer.write("\n"); // NOI18N
                renderTreeRow(node, imageIter, context, writer);
                writer.endElement(HTMLElements.SPAN);
            }
            writer.endElement(HTMLElements.DIV);

        }

        // Check if the TreeNode has children. If so, render each child which
        // in turn would cause each of the descendent nodes to get rendered.

        Iterator iter = node.getChildren().iterator();
        
        //writer.writeText("\n", null);
        String clientID = node.getClientId(context);
        writer.startElement(HTMLElements.DIV, node);
        writer.writeAttribute(HTMLAttributes.ID, 
            clientID+ "_children", null);
        while (iter.hasNext()) {
            UIComponent comp = (UIComponent)iter.next();
            if (comp instanceof TreeNode) {
                RenderingUtilities.renderComponent(comp, context);
            }
        }
                
        writer.endElement(HTMLElements.DIV);
        //writer.writeText("\n", null);
        
        String nodeID = null;
        if (node.getSelected() != null) {
            String childID = (String) node.getSelected();
            TreeNode childNode = node.getChildNode(childID);
            if (childNode != null) {
                nodeID = childNode.getClientId(context);
            }
        }
        
        try {
            // Render JavaScript to initialize tree.
            StringBuffer buff = new StringBuffer(256);
            JSONObject json = new JSONObject();
            json.put("id", clientID);
            
            // Append JavaScript.
            String jsObject = JavaScriptUtilities.getDomNode(context, node);
            buff.append(JavaScriptUtilities.getModule("_html.tree"))
                .append("\n") // NOI18N
                .append(JavaScriptUtilities.getModuleName("_html.tree._init")) // NOI18N
                .append("(") //NOI18N
                .append(JSONUtilities.getString(json))
                .append(");\n"); //NOI18N
                
            if (nodeID != null) {
                buff.append(jsObject)
                    .append(".selectTreeNode('")
                    .append(nodeID)
                    .append("');");
            } else {
                buff.append(jsObject)
                    .append(".updateHighlight('")
                    .append(clientID)
                    .append("');");
            }

            // Render JavaScript.
            JavaScriptUtilities.renderJavaScript(component, writer, 
                buff.toString(), JavaScriptUtilities.isParseOnLoad());

            // Render skip anchor.
            RenderingUtilities.renderAnchor(SKIPTREE_LINK, node, context);
            writer.write("\n"); // NOI18N
            writer.endElement(HTMLElements.DIV);
        } catch(Exception e) {
            LogUtil.warning(e.getMessage(), e);
        }
    }

    /**
     * Does nothing.
     * 
     * @param context The current FacesContext
     * @param component The Property object to render
     *
     * @exception IOException if an input/output error occurs
     */
    public void encodeChildren(FacesContext context, UIComponent component)
          throws IOException {
        // Do nothing...          
    }
}
