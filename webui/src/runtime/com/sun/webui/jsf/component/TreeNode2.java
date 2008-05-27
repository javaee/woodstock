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

/*
 * TreeNode2.java
 *
 * Created on March 28, 2007, 3:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Property;
import com.sun.faces.annotation.Component;

import com.sun.webui.jsf.event.TreeNode2Event;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.LogUtil;
import javax.el.ELException;
import javax.faces.component.NamingContainer;
import javax.faces.event.FacesEvent;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PhaseId; 

/**
 * An Ajax based tree node component. This component could either be a leaf node or
 * a non leaf node in which case it can have one or more children. Nodes
 * can be selected by setting the 
 * {@code selected} property to true, which may be bound to a model value. Action 
 * listeners may be registered individually each node to indicate node selection.
 * Action listeners may also be regsitered to indicate a node toggle. This would
 * be one way of loading child nodes when the user clicks on a node's toggle
 * icon. Note that for this to work, the Tree2 components of which this node is a
 * child should have the "loadOnExpand" property set to "true".
 */

@Component(type="com.sun.webui.jsf.TreeNode2", 
    family="com.sun.webui.jsf.TreeNode2", displayName="Ajax TreeNode", 
    tagName="treeNode2", tagRendererType="com.sun.webui.jsf.widget.TreeNode2")
public class TreeNode2 extends WebuiComponent implements NamingContainer {
   
    // TreeNode2 "nodeLabel" facet
    public static final String NODE_LABEL_FACET_NAME = "label";
    
    // TreeNode2 "image" facet.
    public static final String NODE_IMAGE_FACET_NAME = "image";
    
    /**
     * Create a new instance of the TreeNode2.
     */
    public TreeNode2() {
        super();
        setRendererType("com.sun.webui.jsf.widget.TreeNode2");
    }
     
    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.TreeNode2";
    }
    
    /**
     * <p>Return the renderer type associated with this component.</p>
     */
    public String getRendererType() {
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.TreeNode2";
        }
        return super.getRendererType();
    }
    
    /**
     * If Tree2 is marked as immediate then schedule any tree related events
     * in the earliest possible phase of the JSF lifecycle.
     * 
     * @param e The Event to be queued.
     */
    public void queueEvent(FacesEvent e) {
        if (isImmediate()) {
            e.setPhaseId(PhaseId.ANY_PHASE);
        }
        else {
            e.setPhaseId(PhaseId.INVOKE_APPLICATION);
        }
	super.queueEvent(e);
    }      
    
    /**
     * If a TreeNode2 component's toggleNode was clicked or the node was
     * selected and the application had chosen to handle these events then 
     * these events need to be broadcast. 
     * @param event The FacesEvent being broadcast.
     * @throws javax.faces.event.AbortProcessingException
     */
    public void broadcast(FacesEvent event) throws AbortProcessingException {
        
        super.broadcast(event);
        MethodExpression mex = this.getToggleActionListenerExpression();
       
        if (mex != null) {
            try {
                if (event instanceof FacesEvent) {
                    Object[] objArray = {(TreeNode2Event)event};
                    mex.invoke(FacesContext.getCurrentInstance().getELContext(),
                        objArray);
                }
            } catch (Exception e) {
                LogUtil.warning(e.getMessage(), e);
            }
        } else {
            mex = this.getNodeSelectedActionListenerExpression();
            if (mex != null) {
                try {
                    if (event instanceof TreeNode2Event) {
                        Object[] objArray = {(TreeNode2Event)event};
                        mex.invoke(FacesContext.getCurrentInstance().getELContext(),
                            objArray);
                    }
                } catch (Exception e) {
                    LogUtil.warning(e.getMessage(), e);
                }
            }
        }
        
    }
    
    /**
     * <p>The immediate flag. If set to true, the processing of the node's event
     * will happen ahead of processing other validators and converters present
     * in the page whose components' immediate attributes are not set to true.
     * Tihs attribute will be meaningful only if a page submit occurs.
     * </p>
     */
    @Property(name="immediate", displayName="Immediate", category="Behavior")    
    private boolean immediate = false;
    private boolean immediate_set = false;    
    /**
     * Return a flag indicating that this event should be
     * broadcast at the next available opportunity, ususally between
     * lifecycle phases i.e. <code>PhaseId.ANY</code>.
     */
    public boolean isImmediate() {

        if (this.immediate_set) {
            return (this.immediate);
        }
        ValueExpression ve = getValueExpression("immediate");
        if (ve != null) {
            try {
                return (Boolean.TRUE.equals(
		    ve.getValue(getFacesContext().getELContext())));
            }
            catch (ELException e) {
                throw new FacesException(e);
            }
        } else {
            return (this.immediate);
        }
    }
    
    /**
     * <p>Set the "immediate execution" flag for this {@link UIComponent}.</p>
     *
     * @param immediate The new immediate execution flag
     */
    public void setImmediate(boolean immediate) {

        // if the immediate value is changing.
        if (immediate != this.immediate) {
            this.immediate = immediate;
        }
        this.immediate_set = true;
    }    

    /**
     * <p>The toggleActionListenerExpression attribute is used to specify a method 
     * to handle the tree node toggle event which is triggered when toggle node is clicked 
     * by the user. The toggleActionListenerExpression attribute value must be a
     * Unified EL expression that resolves to a method in a backing bean. The 
     * method must take a single parameter that is an ActionEvent, and its 
     * return type must be <code>void</code>. The class that defines the method 
     * must implement the <code>java.io.Serializable</code> interface or 
     * <code>javax.faces.component.StateHolder</code> interface. </p>
     * 
     */
    @Property(name="toggleActionListenerExpression", isHidden=false, 
        displayName="Toggle Node Action Listener Expression", category="Advanced")
    @Property.Method(signature="void processAction(javax.faces.event.FacesEvent)")
    private MethodExpression toggleActionListenerExpression;
    
    /**
     * <p>The toggleActionListenerExpression attribute is used to specify a method 
     * to handle the tree node toggle event which is triggered when toggle node is clicked 
     * by the user. The toggleActionListenerExpression attribute value must be a
     * Unified EL expression that resolves to a method in a backing bean.</p>
     */
    public MethodExpression getToggleActionListenerExpression() {
       return this.toggleActionListenerExpression;
    }
    
    /**
     * <p>The toggleActionListenerExpression attribute is used to specify a method 
     * to handle the tree node toggle event which is triggered when toggle node is clicked 
     * by the user. The toggleActionListenerExpression attribute value must be a
     * Unified EL expression that resolves to a method in a backing bean.</p>
     * @see #getToggleActionListenerExpression()
     */
    public void setToggleActionListenerExpression(MethodExpression me) {
        this.toggleActionListenerExpression = me;
    }

    /**
     * <p>The nodeSelectedActionListenerExpression attribute is used to specify a method 
     * to handle the tree node selection event which is triggered when a node is selected 
     * by the user. The nodeSelectedActionListenerExpression attribute value must be a
     * Unified EL expression that resolves to a method in a backing bean. The 
     * method must take a single parameter that is an ActionEvent, and its 
     * return type must be <code>void</code>. The class that defines the method 
     * must implement the <code>java.io.Serializable</code> interface or 
     * <code>javax.faces.component.StateHolder</code> interface. </p>
     * 
     */
    @Property(name="nodeSelectedActionListenerExpression", isHidden=false, 
        displayName="Toggle Node Action Listener Expression", category="Advanced")
    @Property.Method(signature="void processAction(javax.faces.event.FacesEvent)")
    private MethodExpression nodeSelectedActionListenerExpression;
    
    /**
     * <p>The nodeSelectedActionListenerExpression attribute is used to specify a method 
     * to handle the tree node selection event which is triggered when a node is selected 
     * by the user. The nodeSelectedActionListenerExpression attribute value must be a
     * Unified EL expression that resolves to a method in a backing bean.</p>
     */
    public MethodExpression getNodeSelectedActionListenerExpression() {
       return this.nodeSelectedActionListenerExpression;
    }
    
    /**
     * <p>The nodeSelectedActionListenerExpression attribute is used to specify a method 
     * to handle the tree node selection event which is triggered when a node is selected 
     * by the user. The nodeSelectedActionListenerExpression attribute value must be a
     * Unified EL expression that resolves to a method in a backing bean.</p>
     * @see #getNodeSelectedActionListenerExpression()
     */
    public void setNodeSelectedActionListenerExpression(MethodExpression me) {
        this.nodeSelectedActionListenerExpression = me;
    }
    
    
    
    /**
     * CSS style class or classes to be applied to the outermost HTML element 
     * when this component is rendered.
     */
    @Property(name="style", displayName="CSS Style(s)", category="Appearance", 
        editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;
    
    /**
     * CSS style or styles to be applied to the outermost HTML element when this
     * component is rendered.
     */
    public String getStyle() {
        if (this.style != null) {
            return this.style;
        }
        ValueExpression _vb = getValueExpression("style");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }
    
    /**
     * CSS style(s) to be applied to the outermost HTML element when this
     * component is rendered.
     * @see #getStyle()
     */
    public void setStyle(String style) {
        this.style = style;
    }
    
    /**
     * CSS style class or classes to be applied to the outermost HTML element when this
     * component is rendered.
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", 
        editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;
    
    /**
     * CSS style class(es) to be applied to the outermost HTML element when this
     * component is rendered.
     */
    public String getStyleClass() {
        if (this.styleClass != null) {
            return this.styleClass;
        }
        ValueExpression _vb = getValueExpression("styleClass");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }
    
    /**
     * CSS style class(es) to be applied to the outermost HTML element when this
     * component is rendered.
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    
    /**
     * <p>Indicates whether the accordion component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, this setting is true, so
     * HTML for the component is included and visible to the user. If the
     * accordion component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    @Property(name="visible", displayName="Visible")
    private boolean visible = false;
    private boolean visible_set = false;
    
    /**
     * <p>Indicates whether the node should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, this setting is true, so
     * HTML for the component is included and visible to the user. If the
     * accordion component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    public boolean isVisible() {
        if (this.visible_set) {
            return this.visible;
        }
        ValueExpression _vb = getValueExpression("visible");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());

            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }
    
    /**
     * <p>Set the visible attribute.</p>
     * @see #isVisible()
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }
    
    /**
     * Returns true if this node is selected. False otherwise. Set to false by
     * default.
     */
    @Property(name="selected", displayName="Is this node selected", isHidden=true)
    private boolean selected = false;
    private boolean selected_set = false;
    
    /**
     * Returns true if the node is selected.
     */
    public boolean isSelected() {
        if (this.selected_set) {
            return this.selected;
        }
        ValueExpression _vb = getValueExpression("selected");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        } 
        return false;
    }
    
    /**
     * Set to true if this node should be selected. 
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
        this.selected_set = true;
    }
    
     // expanded
    /**
    * <p>Set the expanded attribute to true to display the tree node as expanded when
    *     the component is initially rendered. When a node is expanded, its child tree nodes
    *     are displayed. By default, nodes are collapsed initially.</p>
    */
    @Property(name="expanded", displayName="Expanded", category="Appearance")
    private boolean expanded = false;
    private boolean expanded_set = false;

    /**
     * <p>Set the expanded attribute to true to display the tree node as expanded when
     *     the component is initially rendered. When a node is expanded, its child tree nodes
     *     are displayed. By default, nodes are collapsed initially.</p>
     */
    public boolean isExpanded() {
        if (this.expanded_set) {
            return this.expanded;
        }
        ValueExpression _vb = getValueExpression("expanded");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return false;
    }

    /**
     * <p>Set the expanded attribute to true to display the tree node as expanded when
     *     the component is initially rendered. When a node is expanded, its child tree nodes
     *     are displayed. By default, nodes are collapsed initially.</p>
     * @see #isExpanded()
     */
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        this.expanded_set = true;
    }

    // imageURL
    /**
    * <p>Absolute or relative URL to the image to be rendered for the tree node. 
    *         Note that you cannot use the imageURL to display a theme image in the 
    *         tree. You should use an image facet that contains a webuijsf:image or webuijsf:imageHyperlink 
    *         tag to use a theme image. The imageURL attribute is overridden by the <code>image</code> facet.</p>
    *         <p>When the imageURL attribute is used with the url attribute, the image is hyperlinked.</p>
    */
    @Property(name="imageURL", displayName="Image URL", isHidden=true, 
        isAttribute=true, category="Appearance", 
        editorClassName="com.sun.rave.propertyeditors.ImageUrlPropertyEditor")
    private String imageURL = null;

    /**
     * <p>Absolute or relative URL to the image to be rendered for the tree node. 
     *         Note that you cannot use the imageURL to display a theme image in the 
     *         tree. You should use an image facet that contains a webuijsf:image or webuijsf:imageHyperlink 
     *         tag to use a theme image. The imageURL attribute is overridden by the <code>image</code> facet.</p>
     *         <p>When the imageURL attribute is used with the url attribute, the image is hyperlinked.</p>
     */
    public String getImageURL() {
        if (this.imageURL != null) {
            return this.imageURL;
        }
        ValueExpression _vb = getValueExpression("imageURL");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Absolute or relative URL to the image to be rendered for the tree node. 
     *         Note that you cannot use the imageURL to display a theme image in the 
     *         tree. You should use an image facet that contains a webuijsf:image or webuijsf:imageHyperlink 
     *         tag to use a theme image. The imageURL attribute is overridden by the <code>image</code> facet.</p>
     *         <p>When the imageURL attribute is used with the url attribute, the image is hyperlinked.</p>
     * @see #getImageURL()
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    
    /**
    * <p>Indicates that the text that is specified with the text attribute 
    *       should be rendered as a hyperlink that resolves to the specified URL.  
    *       If the imageURL attribute is used with the url attribute, the image is
    *       hyperlinked. The url attribute does not apply to facets. </p>
    */
    @Property(name="url", displayName="Hyperlink Url", category="Behavior", 
        editorClassName="com.sun.rave.propertyeditors.UrlPropertyEditor")
    private String url = null;

    /**
     * <p>Indicates that the text that is specified with the text attribute 
     *       should be rendered as a hyperlink that resolves to the specified URL.  
     *       If the imageURL attribute is used with the url attribute, the image is
     *       hyperlinked. The url attribute does not apply to facets. </p>
     */
    public String getUrl() {
        if (this.url != null) {
            return this.url;
        }
        ValueExpression _vb = getValueExpression("url");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Indicates that the text that is specified with the text attribute 
     *       should be rendered as a hyperlink that resolves to the specified URL.  
     *       If the imageURL attribute is used with the url attribute, the image is
     *       hyperlinked. The url attribute does not apply to facets. </p>
     * @see #getUrl()
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
// target
    /**
    * <p>The resource at the specified URL is displayed in the frame that is 
    *       specified with the target attribute. Values such as "_blank" that are valid 
    *       for the target attribute of the &lt;a&gt; HTML element are also valid for this 
    *       attribute in the tree components. The target attribute is useful only with 
    *       the url attribute, and does not apply when a facet is used.
    */
    @Property(name="target", displayName="Hyperlink Target", category="Behavior")
    private String target = null;

    /**
     * <p>The resource at the specified URL is displayed in the frame that is 
     *       specified with the target attribute. Values such as "_blank" that are valid 
     *       for the target attribute of the &lt;a&gt; HTML element are also valid for this 
     *       attribute in the tree components. The target attribute is useful only with 
     *       the url attribute, and does not apply when a facet is used.
     */
    public String getTarget() {
        if (this.target != null) {
            return this.target;
        }
        ValueExpression _vb = getValueExpression("target");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The resource at the specified URL is displayed in the frame that is 
     *       specified with the target attribute. Values such as "_blank" that are valid 
     *       for the target attribute of the &lt;a&gt; HTML element are also valid for this 
     *       attribute in the tree components. The target attribute is useful only with 
     *       the url attribute, and does not apply when a facet is used.
     * @see #getTarget()
     */
    public void setTarget(String target) {
        this.target = target;
    }

    // nodeLabel
    /**
    * <p>Specifies the label for this node. If the url or action attributes 
    *     are also specified, the text is rendered as a hyperlink. If neither the url 
    *     or action attributes are specified, the specified text is rendered as static text.
    *     Users can click on the text to select the node.
    *     The nodeLabel attribute does not apply when the nodeLabel facet is used.</p>
    */
    @Property(name="label", displayName="Node Label", category="Appearance", isDefault=true)
    private String label = null;

    /**
     * <p>Get the label for this node. If the url or action attributes 
    *     are also specified, the text is rendered as a hyperlink. If neither the url 
    *     or action attributes are specified, the specified text is rendered as static text.
    *     Users can click on the text to select the node.
    *     The nodeLabel attribute does not apply when the nodeLabel facet is used.</p>
     */
    public String getLabel() {
        if (this.label != null) {
            return this.label;
        }
        ValueExpression _vb = getValueExpression("label");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Set the label for this node. If the url or action attributes 
    *     are also specified, the text is rendered as a hyperlink. If neither the url 
    *     or action attributes are specified, the specified text is rendered as static text.
    *     Users can click on the text to select the node.
    *     The nodeLabel attribute does not apply when the nodeLabel facet is used.</p>
     * @see #getNodeLabel()
     */
    public void setLabel(String label) {
        this.label = label;
    }
    
    // toolTip
    /**
    * <p>Sets the value of the title attribute for the HTML element.
    *     The specified text will display as a tooltip if the mouse cursor hovers 
    *     over the HTML element.</p>
    */
    @Property(name="toolTip", displayName="Tool Tip", category="Behavior")
    private String toolTip = null;

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     *     The specified text will display as a tooltip if the mouse cursor hovers 
     *     over the HTML element.</p>
     */
    public String getToolTip() {
        if (this.toolTip != null) {
            return this.toolTip;
        }
        ValueExpression _vb = getValueExpression("toolTip");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     *     The specified text will display as a tooltip if the mouse cursor hovers 
     *     over the HTML element.</p>
     * @see #getToolTip()
     */
    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }
    
    /**
     * Given the ID of the node and the root of the sub tree where it exists
     * this method returns the TreeNode2 object representing the node.
     * @param context The FacesContext
     * @param node  The node representing the root of the subtree where the 
     *      child node should be searched for.
     * @param nodeID The ID of the child node.
     * @return The TreeNode2 object representing the node.
     */
    public static TreeNode2 findChildNode(TreeNode2 node, String nodeId) {
        if (node == null || nodeId == null) {
            return null;
        }
        
        if (node.getChildCount() == 0) {
            return null;
        }
        
        for (UIComponent child : node.getChildren()) {
            TreeNode2 foundNode = findNode((TreeNode2)child, nodeId);
            if (foundNode != null) {
                return foundNode;
            }
        }
        
        if (nodeId.equals(node.getId())) {
            return node;
        }
        
        return null;
    }
    
    /**
     * Recursively finds a child node with a given node ID. 
     * 
     */
    private static TreeNode2 findNode(TreeNode2 node, String nodeId) {
        
        if (nodeId.equals(node.getId())) {
            return node;
        }
        
        if (node.getChildCount() == 0) {
            return null;
        }
        
        for (UIComponent child : node.getChildren()) {
            TreeNode2 tnb = (TreeNode2)child;
            TreeNode2 foundNode = findNode(tnb, nodeId);
            if (foundNode != null) {
                return foundNode;
            }
        }
        return null;
    }
    
    /**
     * Given the client ID of the node and the root of the sub tree where it exists
     * this method returns the TreeNode2 object representing the node.
     * @param context The FacesContext
     * @param node  The node representing the root of the subtree where the 
     *      child node should be searched for.
     * @param clientId The clientID of the child node.
     * @return The TreeNode2 object representing the node.
     */
    public static TreeNode2 findChildNode(FacesContext context,
            TreeNode2 node, String clientId) {
        if (node == null || clientId == null) {
            return null;
        }
        
        if (node.getChildCount() == 0) {
            return null;
        }
        
        for (UIComponent child : node.getChildren()) {
            TreeNode2 foundNode = findNode(context, (TreeNode2)child, clientId);
            if (foundNode != null) {
                return foundNode;
            }
        }
        
        if (clientId.equals(node.getClientId(context))) {
            return node;
        }
        
        return null;
    }
    
    /**
     * Recursively finds a child node with a given client ID. 
     * 
     */
    private static TreeNode2 findNode(FacesContext context, 
        TreeNode2 node, String clientId) {
        
        if (clientId.equals(node.getClientId(context))) {
            return node;
        }
        
        if (node.getChildCount() == 0) {
            return null;
        }
        
        for (UIComponent child : node.getChildren()) {
            TreeNode2 tnb = (TreeNode2)child;
            TreeNode2 foundNode = findNode(context, tnb, clientId);
            if (foundNode != null) {
                return foundNode;
            }
        }
        return null;
    }
    

    /**
     * Returns true if the node is a leaf.
     */
    public boolean isLeaf() {
        for (UIComponent child : this.getChildren()) {
            if (child instanceof TreeNode2) {
                return false;
            }
        }
        return true;
    }

    /*
     * Returns an ordered array of TreeNode2 Objects of this
     * tree path from this node to the root. The first element (index 0) 
     * is the root node. 
     *
     * @return an array of TreeNode2 ojects representing the path from the node to
     * the root.
     */
    public TreeNode2[] getPath(){ 
    
	int i = getPathCount();
        TreeNode2[] result = new TreeNode2[i--];

        for(TreeNode2 node = this; node != null; node = (TreeNode2) node.getParent()) {
            result[i--] = node;
        }
	return result;
    }

    /**
     * Returns the number of elements in the path.
     *
     * @return an int giving a count of items the path
     */
    public int getPathCount() {
        int  result = 0;
        for(UIComponent node = this; node != null; node = node.getParent()) {
            result++;
        }
	return result;
    }

    /**
     * Returns the path component at the specified index.
     *
     * @param index  an int specifying an index in the path, where
     *                 0 is the first element in the path
     * @return the Object at that index location
     * @throws IllegalArgumentException if the index is beyond the length
     *         of the path
     */
    public Object getPathComponent(int index){
        int pathLength = getPathCount();

        if(index < 0 || index >= pathLength)
            throw new IllegalArgumentException("Index " + index + " is out of the specified range");

        TreeNode2 node = this;
        for(int i = pathLength-1; i != index; i--) {
           node = (TreeNode2)node.getParent();
        }
	return node;
    }
    
    
    /**
     * Restore the state of this component.
     */
    @Override
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.style = (String) _values[1];
        this.styleClass = (String) _values[2];
        this.visible = ((Boolean) _values[3]).booleanValue();
        this.visible_set = ((Boolean) _values[4]).booleanValue();
        this.immediate = ((Boolean) _values[5]).booleanValue();
        this.immediate_set = ((Boolean) _values[6]).booleanValue();
        this.expanded = ((Boolean) _values[7]).booleanValue();
        this.expanded_set = ((Boolean) _values[8]).booleanValue();
        this.toggleActionListenerExpression = 
            (javax.el.MethodExpression)_values[9];
        this.nodeSelectedActionListenerExpression = 
            (javax.el.MethodExpression)_values[10];
        
        this.imageURL = (String) _values[11];
        this.target = (String) _values[12];
        this.label = (String) _values[13];
        this.toolTip = (String) _values[14];
        this.url = (String) _values[15];
        
    }
    
    /**
     * Save the state of this component.
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[16];
        _values[0] = super.saveState(_context);
        _values[1] = this.style;
        _values[2] = this.styleClass;
        _values[3] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.immediate ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.immediate_set ? Boolean.TRUE : Boolean.FALSE; 
        _values[7] = this.expanded ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.expanded_set ? Boolean.TRUE : Boolean.FALSE;
        
        _values[9] = this.toggleActionListenerExpression;
        _values[10] = this.nodeSelectedActionListenerExpression;
       
        _values[11] = this.imageURL;
        _values[12] = this.target;
        _values[13] = this.label;
        _values[14] = this.toolTip;
        _values[15] = this.url;
        
        return _values;
    }
      
}
