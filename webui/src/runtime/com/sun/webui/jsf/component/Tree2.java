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
 * Tree2.java
 *
 * Created on March 28, 2007, 3:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Property;
import com.sun.faces.annotation.Component;

import java.util.Vector;
import javax.faces.component.NamingContainer;
import javax.faces.event.FacesEvent;
import javax.faces.event.AbortProcessingException;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.el.ValueExpression;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ValueChangeEvent;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.FacesMessageUtils;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme; 

/**
 * An Ajax based tree component. This component is essentially a root
 * {@code TreeNode2} component. Apart from being a node it also stores
 * information about its child nodes. The information stored are:
 * a) whether multiple nodes be selected
 * b) the list of selected nodes
 * c) whether nodes should asynchronously load their children 
 *  
 * 
 * Action listeners may be registered individually with the Tree2 or 
 * with with the containing TreeNode2 components, in which case the selection will
 * be notified to the root. Typically a single TreeNode2 or the entire Tree2
 * would go through the
 * JSF lifecycle during an XHR request. However, in cases where a form with
 * a Tree2 component is submitted the root and all its child components
 * will go through the JSF lifecycle as usual. It is up to the application to 
 * decide what should be done in this scenario.
 */

/**
 * The Tree2 component is used to display a tree structure in the rendered HTML
 * page.
 */
@Component(type="com.sun.webui.jsf.Tree2", 
    family="com.sun.webui.jsf.Tree2", displayName="AjaxTree", 
    tagName="tree2", tagRendererType="com.sun.webui.jsf.widget.Tree2")
public class Tree2 extends TreeNode2 implements NamingContainer {
    
    /**
     * Create a new instance of the Tree2.
     */
    public Tree2() {
        super();
        setRendererType("com.sun.webui.jsf.widget.Tree2");
    }
     
    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Tree2";
    }
    
    /**
     * <p>Return the renderer type associated with this component.</p>
     */
    public String getRendererType() {
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.Tree2";
        }
        return super.getRendererType();
    }
    
    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     */
    @Property(name="tabIndex", displayName="Tab Index", category="Accessibility", 
        editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int tabIndex = Integer.MIN_VALUE;
    private boolean tabIndex_set = false;

    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     */
    public int getTabIndex() {
        if (this.tabIndex_set) {
            return this.tabIndex;
        }
        ValueExpression _vb = getValueExpression("tabIndex");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     * @see #getTabIndex()
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
        this.tabIndex_set = true;
    }
    
    /**
     * Alternative HTML template to be used by this component.
     */
    @Property(name="htmlTemplate", displayName="HTML Template", category="Appearance")
    private String htmlTemplate = null;

    /**
     * Get alternative HTML template to be used by this component.
     */
    public String getHtmlTemplate() {
        if (this.htmlTemplate != null) {
            return this.htmlTemplate;
        }
        ValueExpression _vb = getValueExpression("htmlTemplate");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Set alternative HTML template to be used by this component.
     */
    public void setHtmlTemplate(String htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }
    
    /**
     * Returns true if the tree's children should be loaded when only when
     * the node is expanded by the user. Set to false by default.
     * Once a node's children have been loaded after node expansion they are 
     * not reloaded during subsequent collapse/expand cycles. This state is 
     * reset of the tree is refreshed. 
     */
    @Property(name="loadOnExpand", displayName="Load on expand", category="Advanced")
    private boolean loadOnExpand = false;
    private boolean loadOnExpand_set = false;
    
    /**
     * Returns true if the node's children should be loaded on select, false otherwise.
     * This value is false by default.
     */
    public boolean isLoadOnExpand() {
        if (this.loadOnExpand_set) {
            return this.loadOnExpand;
        }
        ValueExpression _vb = getValueExpression("loadOnExpand");
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
     * Set to true if nodes should be loaded when selected. 
     */
    public void setLoadOnExpand(boolean loadOnExpand) {
        this.loadOnExpand = loadOnExpand;
        this.loadOnExpand_set = true;
    }
    
    /**
     * Returns true if multiple nodes can be selected at the same time.
     * By default this setting is false and only one tree node can be
     * selected at any given time.
     */
    @Property(name="multipleSelect", displayName="Multiple nodes selected")
    private boolean multipleSelect = false;
    private boolean multipleSelect_set = false;
    
    /**
     * Returns true if multiple nodes can be selected, false otherwise.
     * This value is false by default.
     */
    public boolean isMultipleSelect() {
        if (this.multipleSelect_set) {
            return this.multipleSelect;
        }
        ValueExpression _vb = getValueExpression("multipleSelect");
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
     * Set to true if multiple node can be selected. 
     */
    public void setMultipleSelect(boolean multipleSelect) {
        this.multipleSelect = multipleSelect;
        this.multipleSelect_set = true;
    }

    /**
     * An array of selected nodes. In some cases, only a single node can be selected
     * which specifies that the array contains just one node.
     */
    @Property(name="selectedNodeIds", displayName="Array of Selected Node IDs", category="Advanced")
    private String[] selectedNodeIds = null;
    
    /**
     * Return an array of selected node IDs. If no nodes are selected an 
     * empty array is returned.
     * 
     */
    public String[] getSelectedNodeIds() {
        if (this.selectedNodeIds != null) {
            return this.selectedNodeIds;
        }
        ValueExpression _vb = getValueExpression("selectedNodeIds");
        if (_vb != null) {
            return (String[]) _vb.getValue(getFacesContext().getELContext());
        } else {
            Vector nodeVector = new Vector();
            for (UIComponent kid : this.getChildren()) {
                if (kid instanceof TreeNode2) {
                    TreeNode2 node = (TreeNode2)kid;
                    if (node.isSelected()) {
                        nodeVector.add(node.getId());
                    }
                }
            }
            return (String []) nodeVector.toArray();
        }
    }
    
    /**
     * Set the array of selected nodes. 
     */
    public void setSelectedNodeIds(String[] selectedNodeIds) {
        this.selectedNodeIds = selectedNodeIds;
    }
    
    /**
     * Yoke to a given node. Expand all the nodes that fall on the 
     * path from this node to the root.
     * @param nodeID The ID of the node that is to be yoked.
     */
    public void yokeNode(String nodeId) {
        TreeNode2 node = TreeNode2.findChildNode(this, nodeId);
	if (node != null) {
            // It exists, so select it and expand all ancester nodes
            node.setSelected(true);
            TreeNode2[] path = this.getPath();
            for (int i=0; i< path.length; i++) {
                path[i].setExpanded(true);
            }
        }
    }
    
    /**
     * Return the root of the tree for a given node.
     * @param node The node whose root is to be found
     * @return The Tree2 object representing the root node.
     */
    public static Tree2 getRoot(UIComponent node) {
         
        if (node == null) {
            return null;
        }

        if (node instanceof Tree2) {
            return (Tree2)node;
        }
        if (node instanceof TreeNode2) {
            node = node.getParent();
            while ((node != null) && !(node instanceof Tree2)) {
                node = node.getParent();
            }
            if (node != null) {
                return (Tree2)node;
            } else {
                return null;
            }
        }
        return null;
    }
    
    /**
     * Restore the state of this component.
     */
    @Override
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.selectedNodeIds = (String[]) _values[1];
        this.htmlTemplate = (String) _values[2];
        this.tabIndex = ((Integer) _values[3]).intValue();
        this.tabIndex_set = ((Boolean) _values[4]).booleanValue();
        this.loadOnExpand = ((Boolean) _values[5]).booleanValue();
        this.loadOnExpand_set = ((Boolean) _values[6]).booleanValue();
        this.multipleSelect = ((Boolean) _values[7]).booleanValue();
        this.multipleSelect_set = ((Boolean) _values[8]).booleanValue();
    }
    
    /**
     * Save the state of this component.
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[9];
        _values[0] = super.saveState(_context);
        _values[1] = this.selectedNodeIds;
        _values[2] = this.htmlTemplate;
        _values[3] = new Integer(this.tabIndex);
        _values[4] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.loadOnExpand ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.loadOnExpand_set ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.multipleSelect ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.multipleSelect_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
      
}
