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

/*
 * TreeNode.java
 *
 * Created on Feb 11, 2005, 10:36 PM
 */
package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.theme.ThemeImages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.ActionListener;
import javax.faces.component.NamingContainer;
import javax.faces.event.MethodExpressionActionListener;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import com.sun.webui.jsf.component.IconHyperlink;
import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.event.ToggleActionListener;
import com.sun.webui.jsf.event.TreeNodeToggleEvent;
import com.sun.webui.jsf.event.MethodExprActionListener;
import com.sun.webui.jsf.util.ComponentUtilities;

/**
 * The TreeNode component is used to insert a node in a tree structure.
 */
@Component(type="com.sun.webui.jsf.TreeNode", family="com.sun.webui.jsf.TreeNode", displayName="Tree Node", tagName="treeNode",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_tree_node",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_tree_node_props")
public class TreeNode extends UIComponentBase implements NamingContainer, Serializable {

    /**
     *	Constructor.
     */
    public TreeNode() {
	super();
        setRendererType("com.sun.webui.jsf.TreeNode");
    }
    
    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.TreeNode";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * The component identifier for this component. This value must be unique 
     * within the closest parent component that is a naming container.
     */
    @Property(name="id") 
    public void setId(String id) {
        super.setId(id);
    }

    /**
     * Use the rendered attribute to indicate whether the HTML code for the
     * component should be included in the rendered HTML page. If set to false,
     * the rendered HTML page does not include the HTML for the component. If
     * the component is not rendered, it is also not processed on any subsequent
     * form submission.
     */
    @Property(name="rendered") 
    public void setRendered(boolean rendered) {
        super.setRendered(rendered);
    }

    // actionExpression
    /**
    * <p>The actionExpression attribute is used to specify the action to take when this
    * component is activated by the user. The value of the action attribute
    * must be one of the following:</p>
    *   <ul>
    *      <li>an outcome string, used to indicate which page to display next,
    *            as defined by a navigation rule in the application configuration
    *            resource file <code>(faces-config.xml)</code>.</li>
    *         <li>a JavaServer Faces EL expression that resolves
    *             to a backing bean method. The method must take no parameters
    *             and return an outcome string. The class that defines the method
    *             must implement the <code>java.io.Serializable</code> interface or
    *            <code>javax.faces.component.StateHolder</code> interface.</li></ul> 
    * <p>In the Tree and TreeNode components, the action applies only when attributes are used to define the 
    * tree and tree nodes. When facets are used, the action attribute does not apply to the facets.</p>
    */
    @Property(name="actionExpression", displayName="Hyperlink Action", isHidden=true, isAttribute=true)
    @Property.Method(signature="java.lang.String action()")
    private javax.el.MethodExpression actionExpression = null;

 /**
 * <p>The actionExpression attribute is used to specify the action to take when this
 * component is activated by the user. The value of the action attribute
 * must be one of the following:</p>
 *   <ul>
 *      <li>an outcome string, used to indicate which page to display next,
 *            as defined by a navigation rule in the application configuration
 *            resource file <code>(faces-config.xml)</code>.</li>
 *         <li>a JavaServer Faces EL expression that resolves
 *             to a backing bean method. The method must take no parameters
 *             and return an outcome string. The class that defines the method
 *             must implement the <code>java.io.Serializable</code> interface or
 *            <code>javax.faces.component.StateHolder</code> interface.</li></ul> 
 * <p>In the Tree and TreeNode components, the action applies only when attributes are used to define the 
 * tree and tree nodes. When facets are used, the action attribute does not apply to the facets.</p>
     */
    public javax.el.MethodExpression getActionExpression() {
        if (this.actionExpression != null) {
            return this.actionExpression;
        }
        ValueExpression _vb = getValueExpression("actionExpression");
        if (_vb != null) {
            return (javax.el.MethodExpression) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
 * <p>The actionExpression attribute is used to specify the action to take when this
 * component is activated by the user. The value of the action attribute
 * must be one of the following:</p>
 *   <ul>
 *      <li>an outcome string, used to indicate which page to display next,
 *            as defined by a navigation rule in the application configuration
 *            resource file <code>(faces-config.xml)</code>.</li>
 *         <li>a JavaServer Faces EL expression that resolves
 *             to a backing bean method. The method must take no parameters
 *             and return an outcome string. The class that defines the method
 *             must implement the <code>java.io.Serializable</code> interface or
 *            <code>javax.faces.component.StateHolder</code> interface.</li></ul> 
 * <p>In the Tree and TreeNode components, the action applies only when attributes are used to define the 
 * tree and tree nodes. When facets are used, the action attribute does not apply to the facets.</p>
     * @see #getActionExpression()
     */
    public void setActionExpression(javax.el.MethodExpression actionExpression) {
        this.actionExpression = actionExpression;
    }

    /**
     * <p>The actionListenerExpression attribute is used to specify a method 
     * to handle an action event that is triggered when a component is activated 
     * by the user. The actionListenerExpression attribute value must be a
     * Unified EL expression that resolves to a method in a backing bean. The 
     * method must take a single parameter that is an ActionEvent, and its 
     * return type must be <code>void</code>. The class that defines the method 
     * must implement the <code>java.io.Serializable</code> interface or 
     * <code>javax.faces.component.StateHolder</code> interface. </p>
     * 
     *  <p>In the TreeNode component, the method specified by the 
     * actionListenerExpression atttribute is invoked when the node's handle 
     * icon is clicked.</p>
     */
    @Property(name="actionListenerExpression", isHidden=false, displayName="Action Listener Expression", category="Advanced")
    @Property.Method(signature="void processAction(javax.faces.event.ActionEvent)")
    private MethodExpression actionListenerExpression;
    
    /**
     * <p>The actionListenerExpression attribute is used to specify a method 
     * to handle an action event that is triggered when a component is activated 
     * by the user. The actionListenerExpression attribute value must be a
     * Unified EL expression that resolves to a method in a backing bean. The 
     * method must take a single parameter that is an ActionEvent, and its 
     * return type must be <code>void</code>. The class that defines the method 
     * must implement the <code>java.io.Serializable</code> interface or 
     * <code>javax.faces.component.StateHolder</code> interface. </p>
     * 
     *  <p>In the TreeNode component, the method specified by the 
     * actionListenerExpression atttribute is invoked when the node's handle 
     * icon is clicked.</p>
     */
    public MethodExpression getActionListenerExpression() {
       return this.actionListenerExpression;
    }
    
    /**
     * <p>The actionListenerExpression attribute is used to specify a method 
     * to handle an action event that is triggered when a component is activated 
     * by the user. The actionListenerExpression attribute value must be a
     * Unified EL expression that resolves to a method in a backing bean. The 
     * method must take a single parameter that is an ActionEvent, and its 
     * return type must be <code>void</code>. The class that defines the method 
     * must implement the <code>java.io.Serializable</code> interface or 
     * <code>javax.faces.component.StateHolder</code> interface. </p>
     * 
     *  <p>In the TreeNode component, the method specified by the 
     * actionListenerExpression atttribute is invoked when the node's handle 
     * icon is clicked.</p>
     * @see #getActionListenerExpression()
     */
    public void setActionListenerExpression(MethodExpression me) {
        this.actionListenerExpression = me;
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
        
        UIComponent toggleLink = this.getNodeImageHyperlink();
        Map attributes = toggleLink.getAttributes();
        attributes.put("icon", this.getHandleIcon(
                (String) attributes.get("icon")));
    }

    // imageURL
    /**
    * <p>Absolute or relative URL to the image to be rendered for the tree node. 
    *         Note that you cannot use the imageURL to display a theme image in the 
    *         tree. You should use an image facet that contains a webuijsf:image or webuijsf:imageHyperlink 
    *         tag to use a theme image. The imageURL attribute is overridden by the <code>image</code> facet.</p>
    *         <p>When the imageURL attribute is used with the url attribute, the image is hyperlinked.</p>
    */
    @Property(name="imageURL", displayName="Image URL", isHidden=true, isAttribute=true, category="Appearance", editorClassName="com.sun.rave.propertyeditors.ImageUrlPropertyEditor")
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

    // style
    /**
    * <p>CSS style(s) to be applied to the outermost HTML element when this 
    *         component is rendered.</p>
    */
    @Property(name="style", displayName="CSS Style(s)", category="Appearance", editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;

    /**
 * <p>CSS style(s) to be applied to the outermost HTML element when this 
 *         component is rendered.</p>
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
 * <p>CSS style(s) to be applied to the outermost HTML element when this 
 *         component is rendered.</p>
     * @see #getStyle()
     */
    public void setStyle(String style) {
        this.style = style;
    }

    // styleClass
    /**
    * <p>CSS style class(es) to be applied to the outermost HTML element when this 
    *        component is rendered.</p>
    */
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;

    /**
 * <p>CSS style class(es) to be applied to the outermost HTML element when this 
 *        component is rendered.</p>
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
 * <p>CSS style class(es) to be applied to the outermost HTML element when this 
 *        component is rendered.</p>
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
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

    // text
    /**
    * <p>Specifies the text for this component. If the url or action attributes 
    *     are also specified, the text is rendered as a hyperlink. If neither the url 
    *     or action attributes are specified, the specified text is rendered as static text.
    *     The text attribute does not apply when the content facet is used.</p>
    */
    @Property(name="text", displayName="Text", category="Appearance", isDefault=true)
    private String text = null;

    /**
 * <p>Specifies the text for this component. If the url or action attributes 
 *     are also specified, the text is rendered as a hyperlink. If neither the url 
 *     or action attributes are specified, the specified text is rendered as static text.
 *     The text attribute does not apply when the content facet is used.</p>
     */
    public String getText() {
        if (this.text != null) {
            return this.text;
        }
        ValueExpression _vb = getValueExpression("text");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
 * <p>Specifies the text for this component. If the url or action attributes 
 *     are also specified, the text is rendered as a hyperlink. If neither the url 
 *     or action attributes are specified, the specified text is rendered as static text.
 *     The text attribute does not apply when the content facet is used.</p>
     * @see #getText()
     */
    public void setText(String text) {
        this.text = text;
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

    // url
    /**
    * <p>Indicates that the text that is specified with the text attribute 
    *       should be rendered as a hyperlink that resolves to the specified URL.  
    *       If the imageURL attribute is used with the url attribute, the image is
    *       hyperlinked. The url attribute does not apply to facets. </p>
    */
    @Property(name="url", displayName="Hyperlink Url", category="Behavior", editorClassName="com.sun.rave.propertyeditors.UrlPropertyEditor")
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

    // visible
    /**
    * <p>Use the visible attribute to indicate whether the component should be
    *     viewable by the user in the rendered HTML page. If set to false, the
    *     HTML code for the component is present in the page, but the component
    *     is hidden with style attributes. By default, visible is set to true, so
    *     HTML for the component HTML is included and visible to the user. If the
    *     component is not visible, it can still be processed on subsequent form
    *     submissions because the HTML is present.</p>
    */
    @Property(name="visible", displayName="Visible", category="Behavior")
    private boolean visible = false;
    private boolean visible_set = false;

    /**
 * <p>Use the visible attribute to indicate whether the component should be
 *     viewable by the user in the rendered HTML page. If set to false, the
 *     HTML code for the component is present in the page, but the component
 *     is hidden with style attributes. By default, visible is set to true, so
 *     HTML for the component HTML is included and visible to the user. If the
 *     component is not visible, it can still be processed on subsequent form
 *     submissions because the HTML is present.</p>
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
 * <p>Use the visible attribute to indicate whether the component should be
 *     viewable by the user in the rendered HTML page. If set to false, the
 *     HTML code for the component is present in the page, but the component
 *     is hidden with style attributes. By default, visible is set to true, so
 *     HTML for the component HTML is included and visible to the user. If the
 *     component is not visible, it can still be processed on subsequent form
 *     submissions because the HTML is present.</p>
     * @see #isVisible()
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }

    public void broadcast(FacesEvent event) throws AbortProcessingException {
        super.broadcast(event);
        MethodExpression mex = this.getActionListenerExpression();
        boolean expandToggleIcon = true;
        if (mex != null) {
            try {
                if (event instanceof ActionEvent) {
                    Object[] objArray = {(ActionEvent)event};
                    mex.invoke(FacesContext.getCurrentInstance().getELContext(),
                        objArray);
                }
            } catch (Exception e) {
                // If there is any error associated with the 
                // invokation of the ActionListenerExpression
                // the node should not expand
                expandToggleIcon = false;
                LogUtil.warning(e.getMessage(), e);
            }
        } 
        if (expandToggleIcon) {
            // Nodes need to expand by default when Toggle icon is clicked
            this.setExpanded(!(this.isExpanded()));
        }
    }

    /**
     * This component renders its children
     */
    public boolean getRendersChildren() {
        return true;
    }
    
    /**
     *	<p> This method determines the theme images that should be drawn from
     *	    left to right (0 to x) when rendering the lines before the text
     *	    for this node.</p>
     *
     *	@return	A <code>List</code> of Strings that represent theme keys for
     *		the images to be drawn.  The first list element is the first
     *		image to display when rendering left to right.
     */
    public List getImageKeys() {
	// Walk backward up the tree, calculate the theme image
	Stack stack = new Stack();
        Stack tempStack = new Stack();
        
	Object value = null;
	Map attributes = null;
	boolean last = false;
	boolean first = true;
	boolean bottomNode = false;
        int count = 1;
        
	for (TreeNode node = getParentTreeNode(this); node != null;
		node = getParentTreeNode(node)) {
            
	    attributes = node.getAttributes();
            
            // get the list of peers and find if the current node
            // (this instance) is the last one among them
            
            TreeNode [] peerList = getChildTreeNodes(node);
            int len = peerList.length;
            TreeNode thisNode = peerList[len -1];
            if (thisNode == this) { // the current node is the last one
                bottomNode = true;
                last = true;
            }
        
            if (first) {
		// Direct parent is special
        	first = false;
	        TreeNode [] list = getChildTreeNodes(this);
                if (list != null &&  list.length > 0) {
                    String imageIcon = null;
		    // For this property, we use 'this' for attributes
		    if (((Boolean)getAttributes().get("expanded")).booleanValue()) {
                        
			if (bottomNode) {
			    imageIcon = ThemeImages.TREE_HANDLE_DOWN_LAST;
			} else {
			    imageIcon = ThemeImages.TREE_HANDLE_DOWN_MIDDLE;
			}
		    } else {
                        
			if (bottomNode) {
			    imageIcon = ThemeImages.TREE_HANDLE_RIGHT_LAST;
			} else {
			    imageIcon = ThemeImages.TREE_HANDLE_RIGHT_MIDDLE;
			}
		    }
                    stack.push(imageIcon);
                    IconHyperlink ihl = getTurnerImageHyperlink();
                    ihl.setIcon(imageIcon);
                    Tree rootNode = getAbsoluteRoot(this);
                    if (rootNode != null) {
                        if (rootNode.isClientSide()) {
                            ihl.setOnClick("return false;");
                        }
                    }
                    ihl.setBorder(0);
                    
                    tempStack.push(ihl);

		} else {
                    
                    String imageIcon = null;
                    if (bottomNode) {
                        imageIcon = ThemeImages.TREE_LINE_LAST_NODE;
                    } else {
                        imageIcon = ThemeImages.TREE_LINE_MIDDLE_NODE;
		    }
                    stack.push(imageIcon);
                    String id = "icon" + count++;
                    ImageComponent ic = (ImageComponent) Util.getChild(this, id);
                    if (ic == null ) {
                        ic = new ImageComponent();
                        
                        ic.setId(id);
                        this.getChildren().add(ic);
                    }
                    ic.setIcon(imageIcon);
                    tempStack.push(ic);
		}
	    } else {
		// We get the attributes this way because we really want to parent's values
		// to see if we have a peer
                                
                String imageIcon = null;
		value = node.getAttributes().get("lastChild");
                TreeNode [] list = getChildTreeNodes(node);
                last = (value == null) ? false : value.toString().equals("true");
		                
                if (!last && (list != null && list.length > 0)) {
                    imageIcon = ThemeImages.TREE_LINE_VERTICAL;
		    
		} else {
		    imageIcon = ThemeImages.TREE_BLANK;
		}
                stack.push(imageIcon);
                String id = "icon" + count++;
                ImageComponent ic = (ImageComponent) Util.getChild(this, id);
                if (ic == null) {
                    ic = new ImageComponent();
                    ic.setIcon(imageIcon);
                    ic.setId(id);
                    this.getChildren().add(ic);
                }
                tempStack.push(ic);
	    }
	}

	// Handle special case where this.getParent() is the root node...
	// don't draw a line up to it unless the root node has an icon.
	TreeNode parent = getParentTreeNode(this);
	if (parent instanceof Tree) {
	    // Ok, so this is a direct child of the root... but is it the first?
	    Iterator children = parent.getChildren().iterator();
	    Object child = null;
	    while (children.hasNext()) {
		child = children.next();
		if (child instanceof TreeNode) {
		    // Check to see if the child is 'this'
		    if (child == this) {
			// Ok, so this is the child that is effected... make
			// sure the root node doesn't have an icon
			String imgURL = parent.getImageURL();
			if (((imgURL == null) || imgURL.equals("")) &&
				(parent.getFacet(IMAGE_FACET_KEY) == null)) {
			    // This is the special case
			    // Get the top image and change it
			    // stack.push(topLineImageMapping.get(stack.pop()));
                            String imageIcon = (String)topLineImageMapping.get(stack.pop());
                                                        
                            // how to find the component associated with it and then
                            // change the image icon of that component?
                            Object topmost = tempStack.pop();
                            if (topmost instanceof ImageComponent) {
                                ((ImageComponent)topmost).setIcon(imageIcon);
                            } else if (topmost instanceof IconHyperlink) {
                                ((IconHyperlink)topmost).setIcon(imageIcon);
                            }
                            stack.push(imageIcon);
                            tempStack.push(topmost);
			}
		    }
		    // break b/c we only want to check the first TreeNode
		    break;
		}
	    }
	}

	// Reverse the order
	List list = new ArrayList();

        while (!tempStack.empty()) {
            list.add(tempStack.pop());
        }

	// Return the list
	return list;
    }

    /**
     * Given the ID of a child of this node this method
     * returns the TreeNode component corresponding to
     * this ID.
     * 
     * @param id The id of the child node being searched for.
     * @return the TreeNode object if found, null otherwise.
     */
    public TreeNode getChildNode(String id) {
        String thisID = getId();
        if (thisID != null && thisID.equals(id)) {
            return this;
        } else {
            if (getChildCount() == 0) {
                return null;
            } else {
                for (UIComponent kid : this.getChildren()) {
                    if (kid instanceof TreeNode) {
                        String kidId = kid.getId();
                        if (kidId != null && kidId.equals(id)) {
                            return (TreeNode)kid;
                        } else {
                            TreeNode node = (TreeNode)kid;
                            TreeNode foo = node.getChildNode(id);
                            if (foo != null) {
                                return foo;
                            }
                        }
                    } 
                }
                return null;
            }
        }
    }
    
    /**
     *	<p> This method returns the closest parent that is a TreeNode, or null
     *	    if not found.</p>
     *
     *	@param	node	The starting <code>TreeNode</code>.
     *
     *	@return	The clost parent <code>TreeNode</code>
     */
    public static TreeNode getParentTreeNode(UIComponent node) {
        if (node == null) {
            return null;
        }
        
	node = node.getParent();
	while ((node != null) && !(node instanceof TreeNode)) {
	    node = node.getParent();
	}
	return (TreeNode) node;
    }
    
    /**
     *	<p> This method returns the root Tree object reference. This method is
     *      required to find out this is a client side tree and if nodes 
     *      should expand on selection of the anything other than the handler
     *      images. The "clientSide" and "expandOnSelect" attributes of the 
     *      Tree component are used to supply this information.</p>
     *
     *
     *	@return	The root <code>Tree</code> component.
     */
    public static Tree getAbsoluteRoot(UIComponent node) {
        
        if (node == null) {
            return null;
        }
                                                                                
        if (node instanceof Tree) {
            return (Tree)node;
        }
        if (node instanceof TreeNode) {
            node = node.getParent();
            while ((node != null) && !(node instanceof Tree)) {
                node = node.getParent();
            }
            if (node != null) {
                return (Tree)node;
            } else {
                return null;
            }
        }
        return null;
    }
             
    /**
     *	<p> This method returns an array of child treeNodes only.
     *
     *	@param	node	The starting <code>TreeNode</code>.
     *
     *	@return	The clost parent <code>TreeNode</code>
     */
    public static TreeNode[] getChildTreeNodes(UIComponent node) {
        if (node == null) {
            return null;
        }
        // 
        if (node instanceof TreeNode) {
	     Iterator nodeList = node.getChildren().iterator();
             Vector childNodeList = new Vector();
             while (nodeList.hasNext()) {
                 UIComponent comp = (UIComponent) nodeList.next();
                 if (comp instanceof TreeNode) {
                     childNodeList.add(comp);
                 }
             }
             TreeNode [] arr = new TreeNode[childNodeList.size()];
             for (int i=0; i< childNodeList.size(); i++) {
                 arr[i] = (TreeNode) childNodeList.get(i);
             }
             return arr;
             
        } else {
            return null;
        }
    }

   
    /**
     * <p>Add an action listener instance for the IconHyperlink representing
     * this node's turner.</p>
     *
     * @param listener The ActionListener instance to register for turner
     * IconHyperlink clicks.
     */
    public void addActionListener(ActionListener listener) {
        addFacesListener(listener);
    }
    
    /**
     * <p>Get all ActionListener instances for this node's turner IconHyperlink
     * click.</p>
     *
     * @return ActionListener[] The list of listeners for this node's turner
     * IconHyperlink click.
     */
    public ActionListener[] getActionListeners() {
        ActionListener al[] = (ActionListener [])
            getFacesListeners(ActionListener.class);
        return (al);
    }
    
    /**
     * <p>Remove an action listener instance from the list for this node's
     * turner IconHyperlink.</p>
     *
     * @param listener The ActionListener instance to remove.
     */
    public void removeActionListener(ActionListener listener) {
        removeFacesListener(listener);
    }
    
    /**
     *	<p> This method enables the icon to switch from expanded to collapsed,
     *	    or from collapsed to expanded depending on the current state of
     *	    this component.</p>
     *
     *	@param	value	The current value of the Icon.  It will use the current
     *			value to re-use first/last information from the old key.
     *
     *	@return	The new (or same if the state hasn't changed) icon state
     */
    public String getHandleIcon(String value) {
	// Make sure we have a value
	if ((value == null) || value.trim().equals("")) {
	    value = ThemeImages.TREE_HANDLE_RIGHT_TOP_NOSIBLING;
	}

	// Convert it to the current state
	if (isExpanded()) {
            value = ThemeImages.TREE_HANDLE_DOWN_TOP_NOSIBLING;
	} else {
            value = ThemeImages.TREE_HANDLE_RIGHT_TOP_NOSIBLING;
	}
	return value;
    }

    public Hyperlink getContentHyperlink() {
        
        Hyperlink child = (Hyperlink)
	    ComponentUtilities.getPrivateFacet(this,
		"link", true);
	if (child == null) {
            child = new Hyperlink();
            child.setId(ComponentUtilities.createPrivateFacetId(this,
		"link"));
            child.addActionListener(new ToggleActionListener());
            ComponentUtilities.putPrivateFacet(this,
	        "link", child);
        }
        
        Tree root = Tree.getAbsoluteRoot(this);
        if ((root != null) && (root.isImmediate())) {
            child.setImmediate(true);
        }
        child.setText(this.getText());
        child.setUrl(this.getUrl());
        if (this.getTarget() != null) {
            child.setTarget(this.getTarget());
        }

        if (this.getActionExpression() != null) {
            child.setActionExpression(this.getActionExpression());
        
        }
        
        ActionListener[] nodeListeners = this.getActionListeners();
        if ((nodeListeners != null) && (nodeListeners.length > 0)) {
            for (int i=0; i< nodeListeners.length; i++) {
                child.addActionListener(nodeListeners[i]);
            }
        }
        
        if (this.getToolTip() != null) {
            child.setToolTip(this.getToolTip());
        }
        return child;
    }
    
    public ImageHyperlink getNodeImageHyperlink() {
        
        ImageHyperlink child = (ImageHyperlink)
	    ComponentUtilities.getPrivateFacet(this,
		"image", true);
	if (child == null) {
            child = new ImageHyperlink();
            child.setId(ComponentUtilities.createPrivateFacetId(this,
		"image"));
            child.addActionListener(new ToggleActionListener());
            ComponentUtilities.putPrivateFacet(this,
	    "image", child);
        }
        Tree root = Tree.getAbsoluteRoot(this);
        if ((root != null) && (root.isImmediate())) {
            child.setImmediate(true);
        } 
        child.setImageURL(this.getImageURL());
        child.setUrl(this.getUrl());
        child.setBorder(0);
        if (this.getTarget() != null) {
            child.setTarget(this.getTarget());
        }

        if (this.getActionExpression() != null) {
            child.setActionExpression(this.getActionExpression());
        }
        
        ActionListener[] nodeListeners = this.getActionListeners();
        if ((nodeListeners != null) && (nodeListeners.length > 0)) {
            for (int i=0; i< nodeListeners.length; i++) {
                child.addActionListener(nodeListeners[i]);
            }
        }
        
        if (this.getToolTip() != null) {
            child.setToolTip(this.getToolTip());
        }
        return child;
    }
    
    public IconHyperlink getTurnerImageHyperlink() {
        IconHyperlink ihl = 
            (IconHyperlink)ComponentUtilities.getPrivateFacet(this,
                "turner", true);
        if (ihl == null) {
            ihl = new IconHyperlink();
            ihl.setId(ComponentUtilities.createPrivateFacetId(this,
                "turner"));
            ihl.addActionListener(new ToggleActionListener());
            ComponentUtilities.putPrivateFacet(this,
                "turner", ihl);
        }
        Tree root = Tree.getAbsoluteRoot(this);
        if ((root != null) && (root.isImmediate())) {
            ihl.setImmediate(true);
        }
        return ihl;
    }
    
    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.actionExpression = (javax.el.MethodExpression) restoreAttachedState(_context, _values[1]);
        this.actionListenerExpression = (javax.el.MethodExpression)_values[2];
        this.expanded = ((Boolean) _values[3]).booleanValue();
        this.expanded_set = ((Boolean) _values[4]).booleanValue();
        this.imageURL = (String) _values[5];
        this.style = (String) _values[6];
        this.styleClass = (String) _values[7];
        this.target = (String) _values[8];
        this.text = (String) _values[9];
        this.toolTip = (String) _values[10];
        this.url = (String) _values[11];
        this.visible = ((Boolean) _values[12]).booleanValue();
        this.visible_set = ((Boolean) _values[13]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[14];
        _values[0] = super.saveState(_context);
        _values[1] = saveAttachedState(_context, actionExpression);
        _values[2] = this.actionListenerExpression;
        _values[3] = this.expanded ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.expanded_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.imageURL;
        _values[6] = this.style;
        _values[7] = this.styleClass;
        _values[8] = this.target;
        _values[9] = this.text;
        _values[10] = this.toolTip;
        _values[11] = this.url;
        _values[12] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[13] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
    
    /**
     *	<p> This Map maps the standard first line image icon to the "special
     *	    case" one.  The special case is when the root is not visible, the
     *	    icon directly below where the root should be looks different than
     *	    all others.</p>
     */
    private static Map topLineImageMapping = new HashMap(6);
    static {
	topLineImageMapping.put(ThemeImages.TREE_HANDLE_DOWN_MIDDLE,
		ThemeImages.TREE_HANDLE_DOWN_TOP);
	topLineImageMapping.put(ThemeImages.TREE_HANDLE_DOWN_LAST,
		ThemeImages.TREE_HANDLE_DOWN_TOP_NOSIBLING);
	topLineImageMapping.put(ThemeImages.TREE_HANDLE_RIGHT_MIDDLE,
		ThemeImages.TREE_HANDLE_RIGHT_TOP);
	topLineImageMapping.put(ThemeImages.TREE_HANDLE_RIGHT_LAST,
		ThemeImages.TREE_HANDLE_RIGHT_TOP_NOSIBLING);
	topLineImageMapping.put(ThemeImages.TREE_LINE_MIDDLE_NODE,
		ThemeImages.TREE_LINE_FIRST_NODE);
	topLineImageMapping.put(ThemeImages.TREE_LINE_LAST_NODE,
		ThemeImages.TREE_BLANK);
    }

    /**
     *	<p> This is the facet key used to set a custom image for this
     *	    <code>TreeNode</code>. (image)</p>
     */
    public static final String	IMAGE_FACET_KEY  =	"image";

    /**
     *	<p> This is the facet key used to define the content for the
     *	    </code>TreeNode</code>. (content)</p>
     */
    public static final String	CONTENT_FACET_KEY  =	"content";

}
