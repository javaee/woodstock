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

package com.sun.webui.jsf.example.tree;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.component.UIComponentBase;

import com.sun.webui.jsf.example.index.IndexBackingBean;
import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.common.ClientSniffer;
import com.sun.webui.jsf.example.util.ExampleUtilities;

import com.sun.webui.jsf.component.Tree;
import com.sun.webui.jsf.component.TreeNode;
import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.jsf.component.ImageHyperlink;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.theme.ThemeImages;

/**
 * Backing bean for Dynamic Tree example.
 */
public class NavTreeBackingBean implements Serializable {

    // Outcome strings used in the faces config.
    public final static String SHOW_NAV_TREE = "showNavTree";
    public final static String SHOW_TREE_INDEX = "showTreeIndex";

    private ClientSniffer cs;

    public final static String TREE_DOCUMENT = ThemeImages.TREE_DOCUMENT;
    public final static String TREE_FOLDER = ThemeImages.TREE_FOLDER;

    private Tree tree = null;
    private int nodeClicked = 0;
    private Boolean breadcrumbsRendered = Boolean.FALSE;

    // Due to a bug, the root node is not selectable.
    private final static boolean ROOTNODESELECTABLE = false;

    /** Default constructor */
    public NavTreeBackingBean() {
	FacesContext context = FacesContext.getCurrentInstance();
	cs = new ClientSniffer(context);
    }

    /** Get the Tree object */
    public Tree getTree() {
	if (tree != null)
	    return tree;

	// Create root of tree
	tree = new Tree();
	tree.setId("navtree");
	tree.setText("Node 0");
	tree.setClientSide(true);

	// Until root node is selectable, we do not create a facet for 
	// the content, but only for the image via an ImageComponent - 
	// that is, it's not a link.
	if (ROOTNODESELECTABLE) {
	    tree.getFacets().put("content",
		nodeLink(tree.getText(), 0));
	    tree.getFacets().put("image", 
		nodeImage(tree.getText(), 0, TREE_FOLDER));
	} else {
	    ImageComponent imageComponent = new ImageComponent();
	    imageComponent.setId(nodeImageID(0));
	    tree.getFacets().put("image", imageComponent);
	    setNodeImage(tree, TREE_FOLDER);
	}
	makeToolTip(tree, TREE_FOLDER);

	// Create a series of 2nd-level child nodes, and a couple of 3rd-level
	// grand-child nodes.
	for (int i = 10; i < 21; i += 10) {
	    TreeNode n, c;

	    // 2nd-level child node
	    n = addTreeNode(tree, "Node " + i, i, TREE_FOLDER);

	    // 3rd-level grandchild nodes
	    c = addTreeNode(n, "Node " + (i + 1), (i + 1), TREE_DOCUMENT);
	    c = addTreeNode(n, "Node " + (i + 2), (i + 2), TREE_DOCUMENT);
	}

	// Create a 2nd-level folder child of node10, and then create some
	// 3rd-level grand-child nodes.
	TreeNode node10 = tree.getChildNode(treeNodeID(10));
	if (node10 != null) {
	    TreeNode node30 = addTreeNode(node10, "Node 30", 30, TREE_FOLDER);
	    addTreeNode(node30, "Node 31", 31, TREE_DOCUMENT);
	    addTreeNode(node30, "Node 32", 32, TREE_DOCUMENT);
	    addTreeNode(node30, "Node 33", 33, TREE_DOCUMENT);
	}

	return tree;
    }

    /** Set the Tree Object */
    public void setTree(Tree tree) {
	this.tree = tree;
    }
 
    /**
     * Get the 'rows' attribute value for the outer frameset based on the
     * client browser.
     */
    public String getOuterFramesetRows() {
 	if (cs.isIe6up()) {
	    return "68,*";
 	}

	// assume default is Mozilla-based
	return "75,*";
    }

    /** Return the node number of the tree node that was clicked */
    public String getNodeClicked() {
        String param = (String) FacesContext.getCurrentInstance().
	    getExternalContext().getRequestParameterMap().
	    get("nodeClicked");
        try {
	    nodeClicked = Integer.parseInt(param);
	} catch (Exception e) {
	    nodeClicked = 0;
	    return "Nothing";
	}

	return Integer.toString(nodeClicked);
    }

    /** Return the rendered status of the breadbrumbs */
    public String getBreadcrumbsRendered() {
	getNodeClicked();
	if (nodeClicked == 0)
	    breadcrumbsRendered = Boolean.FALSE;
	else
	    breadcrumbsRendered = Boolean.TRUE;
	return breadcrumbsRendered.toString();
    }

    /** Return the parentage path for the currently selected node */
    public Hyperlink[] getPageList() {

	// No path needed if not rendering breadcrumbs
	getBreadcrumbsRendered();
	if (breadcrumbsRendered == Boolean.FALSE)
	    return null;

	// No path needed for the root node
	if (nodeClicked == 0)
	    return null;

	// Get the node object of the selected node,
	// with usual protection from Murphy's Law!
	TreeNode node = tree.getChildNode(treeNodeID(nodeClicked));
	if (node == null)
	    return null;

	// Build up an array ancestry nodes starting with the selection
	ArrayList<TreeNode> ancestry = new ArrayList<TreeNode>();
	ancestry.add(node);
	TreeNode parent = TreeNode.getParentTreeNode(node);
	while (parent != null) {
	    ancestry.add(parent);
	    parent = TreeNode.getParentTreeNode(parent);
	}

	// Create the breadcrumbs for the ancestry, in the reverse
	// order of the node array list.
	Hyperlink[] pages = new Hyperlink[ancestry.size()];
	int n = 0;
	for (int i = ancestry.size() - 1; i >= 0; i--) {
	    node = (TreeNode) ancestry.get(i);
	    Hyperlink link = new Hyperlink();

	    // Breadcrumb link text same as node's content facet if it
	    // exists, otherwise from the node.
	    Hyperlink facet = (Hyperlink) node.getFacets().get("content");
	    if (facet != null)
		link.setText(facet.getText());
	    else
		link.setText(node.getText());

	    // Config what happens when this link is clicked
	    link.setTarget("contentsFrame");
	    try {
		String toolTip = MessageUtil.getMessage(
			"tree_contentBreadcrumbToolip",
			(String) link.getText());

		// Re-render the contents frame to show which node is selected.
		int nodeNum = getNodeNum(node);
		if (!ROOTNODESELECTABLE && (nodeNum == 0)) {
		    link.setUrl("content.jsp");
		    toolTip += " (root node currently not selectable)";
		}
		else
		    link.setUrl("content.jsp?nodeClicked=" + nodeNum);

		link.setToolTip(toolTip);
		link.setId(breadcrumbLinkID(nodeNum));

		// Yoke to this node in the tree.
		FacesContext context = FacesContext.getCurrentInstance();
		String clientID = node.getClientId(context);
		if (!ROOTNODESELECTABLE && (nodeNum == 0))
		    link.setOnClick("javascript:ClearHighlight(); return true;");
		else
		    link.setOnClick("javascript:YokeToNode('" + clientID + 
			"'); return true;");
	    } catch (Exception e) {
	    }

	    pages[n++] = link;
	}

	return pages;
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Action Handlers
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** Action handler when navigating to the navigation tree example */
    public String showNavTree() {
	return SHOW_NAV_TREE;
    }

    /** Action handler when navigating to the main example index. */
    public String showExampleIndex() {        
        reset();
        return IndexBackingBean.INDEX_ACTION;
    }
         
    /** Action handler when navigating to the tree example index. */
    public String showTreeIndex() {
        reset();
        return SHOW_TREE_INDEX;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** Reset everything back to initial state */
    private void reset() {
	nodeClicked = 0;
	breadcrumbsRendered = Boolean.FALSE;
	tree = null;
    }

    /** Create a new tree node child of a specified parent node */
    private TreeNode addTreeNode(TreeNode parent, String label, 
	int nodeNum, String icon) {

	TreeNode node = new TreeNode();
	node.setText(label);
	node.setId(treeNodeID(nodeNum));
	node.getFacets().put("image", nodeImage(label, nodeNum, icon));
	node.getFacets().put("content", nodeLink(label, nodeNum));
	makeToolTip(node, icon);

	parent.getChildren().add(node);
	return node;
    }

    /** Return a Hyperlink object */
    private Hyperlink nodeLink(String label, int nodeNum) {
	Hyperlink link = new Hyperlink();
	link.setId(nodeLinkID(nodeNum));
	link.setText(label);
	link.setTarget("contentsFrame");
	link.setUrl("content.jsp?nodeClicked=" + nodeNum);
	return link;
    }

    /** Return an ImageHyperlink object */
    private ImageHyperlink nodeImage(String label, int nodeNum, String icon) {
	ImageHyperlink link = new ImageHyperlink();
	link.setId(nodeImageID(nodeNum));
	_setNodeImage(link, icon);
	link.setTarget("contentsFrame");
	link.setUrl("content.jsp?nodeClicked=" + nodeNum);
	return link;
    }

    /** Set the image for a specified tree node */
    private void setNodeImage(TreeNode node, String icon) {
	_setNodeImage((Object)(node.getFacets().get("image")), icon);
	makeToolTip(node, icon);
    }

    /** Set the image for a specified image component. */
    private void _setNodeImage(Object o, String icon) {
	if (o == null)
	    return;

	if (o instanceof ImageHyperlink)
	    ((ImageHyperlink)o).setIcon(icon);
	if (o instanceof ImageComponent)
	    ((ImageComponent)o).setIcon(icon);
    }

    /** Compose the tooltip and set it on all facets of the node */
    private void makeToolTip(TreeNode node, String icon) {
	String tip = node.getText();

	Object o = (Object)(node.getFacets().get("image"));
	if (o instanceof ImageHyperlink)
	    ((ImageHyperlink)o).setToolTip(tip);
	if (o instanceof ImageComponent)
	    ((ImageComponent)o).setToolTip(tip);

	o = (Object)(node.getFacets().get("content"));
	if (o instanceof Hyperlink)
	    ((Hyperlink)o).setToolTip(tip);
	else
	    node.setToolTip(tip);
    }

    // Because we're identifying node with an integer, and JSF requires
    // IDs to be start with a letter or underscore, we provide convenience
    // methods for creating unique IDs from an integer.

    /** Return a valid component ID for a treeNode */
    private String treeNodeID(int nodeNum) {
	return "Node" + nodeNum;
    }

    /** Return a valid component ID for a treenode's content facet */
    private String nodeLinkID(int nodeNum) {
	return treeNodeID(nodeNum) + "Link";
    }

    /** Return a valid component ID for a treenode's image facet */
    private String nodeImageID(int nodeNum) {
	return treeNodeID(nodeNum) + "Image";
    }

    /** Return a valid component ID for a breadcrumb link */
    private String breadcrumbLinkID(int nodeNum) {
	return "Breadcrumb" + nodeNum;
    }

    /** Extract and return the node number from the specified component ID */
    private int getNodeNum(String id) {
	String nodeNum = id;
	nodeNum = nodeNum.replace("Node", "");
	nodeNum = nodeNum.replace("Image", "");
	nodeNum = nodeNum.replace("Link", "");
	nodeNum = nodeNum.replace("Breadcrumb", "");
	return Integer.parseInt(nodeNum);
    }

    /** Extract and return the node number from the specified TreeNode */
    private int getNodeNum(TreeNode node) {
	Object o = (Object)(node.getFacets().get("content"));
	if (o instanceof Hyperlink)
	    return getNodeNum(((Hyperlink)o).getId());

	o = (Object)(node.getFacets().get("image"));
	if (o instanceof ImageHyperlink)
	    return getNodeNum(((ImageHyperlink)o).getId());
	if (o instanceof ImageComponent)
	    return getNodeNum(((ImageComponent)o).getId());

	return getNodeNum(node.getId());
    }
}
