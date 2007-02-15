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
import javax.faces.event.ActionEvent;
import javax.faces.component.UIComponentBase;

import com.sun.webui.jsf.example.index.IndexBackingBean;
import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.util.ExampleUtilities;

import com.sun.webui.jsf.component.Tree;
import com.sun.webui.jsf.component.TreeNode;
import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.jsf.component.ImageHyperlink;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.OptionTitle;
import com.sun.webui.jsf.component.DropDown;

/**
 * Backing bean for Dynamic Tree example.
 */
public class DynamicTreeBackingBean implements Serializable {

    // Outcome strings used in the faces config.
    public final static String SHOW_DYNAMIC_TREE = "showDynamicTree";
    public final static String SHOW_TREE_INDEX = "showTreeIndex";

    // Constants for tree node images.  For all leaf nodes that require 
    // a badging of the alarm severity with the document images, we had to
    // create our own images which are loaded from within the app's
    // context.  For all others we simply create mappings to the names
    // of the themed icons.
    //
    public final static String TREE_DOCUMENT_ALARM_MAJOR = 
			"/images/tree_document_major.gif";
    public final static String TREE_DOCUMENT_ALARM_MINOR = 
			"/images/tree_document_minor.gif";
    public final static String TREE_DOCUMENT_ALARM_DOWN = 
			"/images/tree_document_down.gif";
    public final static String TREE_DOCUMENT_ALARM_CRITICAL = 
			"/images/tree_document_critial.gif";
    public final static String TREE_DOCUMENT = 
			ThemeImages.TREE_DOCUMENT;
    public final static String TREE_FOLDER = 
			ThemeImages.TREE_FOLDER;
    public final static String TREE_FOLDER_ALARM_MAJOR = 
			ThemeImages.TREE_FOLDER_ALARM_MAJOR;
    public final static String TREE_FOLDER_ALARM_MINOR = 
			ThemeImages.TREE_FOLDER_ALARM_MINOR;
    public final static String TREE_FOLDER_ALARM_DOWN = 
			ThemeImages.TREE_FOLDER_ALARM_DOWN;
    public final static String TREE_FOLDER_ALARM_CRITICAL = 
			ThemeImages.TREE_FOLDER_ALARM_CRITICAL;


    private Tree tree = null;
    private String alertDetail = null;
    private Boolean alertRendered = Boolean.FALSE;
    private Option[] testCaseOptions = null;


    /** Constructor */
    public DynamicTreeBackingBean() {
        testCaseOptions = new Option[3];
        testCaseOptions[0] = new OptionTitle(
                MessageUtil.getMessage("tree_testCase_title"));
        testCaseOptions[1] = new Option("tree_testCase_reload",
                MessageUtil.getMessage("tree_testCase_reload"));
        testCaseOptions[2] = new Option("tree_testCase_yoke34", 
                MessageUtil.getMessage("tree_testCase_yoke34"));
    }

    /** Get the Tree object */
    public Tree getTree() {
	if (tree != null)
	    return tree;

	// Create root of tree
	tree = new Tree();
	tree.setId("DynamicTree");
	tree.setText("Node 0");

	// At this time, the root node is not selectable.  Until it is,
	// we do not create a facet for the content, but only for the image
	// via an ImageComponent - that is, it's not a link.
	boolean rootNodeSelectable = false;
	if (rootNodeSelectable) {
	    tree.getFacets().put("content",
		nodeLink(tree.getText(), 0));
	    tree.getFacets().put("image", 
		nodeImage(tree.getText(), 0, TREE_FOLDER_ALARM_DOWN));
	} else {
	    ImageComponent imageComponent = new ImageComponent();
	    imageComponent.setId(nodeImageID(0));
	    tree.getFacets().put("image", imageComponent);
	    setNodeImage(tree, TREE_FOLDER_ALARM_DOWN);
	}
	makeToolTip(tree, TREE_FOLDER_ALARM_DOWN);

	// Create a series of 2nd-level child nodes, and a couple of 3rd-level
	// grand-child nodes.
	for (int i = 10; i < 51; i += 10) {
	    TreeNode n, c;

	    // 2nd-level child node
	    n = addTreeNode(tree, "Node " + i, i, TREE_FOLDER);

	    // 3rd-level grandchild nodes
	    c = addTreeNode(n, "Node " + (i + 1), (i + 1), TREE_DOCUMENT);
	    c = addTreeNode(n, "Node " + (i + 2), (i + 2), TREE_DOCUMENT);
	}

	// Create more 3rd-level grandchild nodes but with at least one
	// showing a major alarm.
	TreeNode node30 = tree.getChildNode(treeNodeID(30));
	if (node30 != null) {
	    TreeNode node31 = node30.getChildNode(treeNodeID(31));
	    if (node31 != null) {
		addTreeNode(node31, "Node 33", 33, TREE_DOCUMENT);
		addTreeNode(node31, "Node 34", 34, TREE_DOCUMENT_ALARM_MAJOR);

		// Propogate the alarm condition up the ancestry chain.
		setNodeImage(node31, TREE_FOLDER_ALARM_MAJOR);
		setNodeImage(node30, TREE_FOLDER_ALARM_MAJOR);
	    }
	}

	// Create more 3rd-level grandchild nodes but with at least one
	// showing a down alarm.
	TreeNode node50 = tree.getChildNode(treeNodeID(50));
	if (node50 != null) {
	    TreeNode node52 = node50.getChildNode(treeNodeID(52));
	    if (node52 != null) {
		addTreeNode(node52, "Node 53", 53, TREE_DOCUMENT);
		addTreeNode(node52, "Node 54", 54, TREE_FOLDER_ALARM_DOWN);

		// Propogate the alarm condition up the ancestry chain.
		setNodeImage(node52, TREE_FOLDER_ALARM_DOWN);
		setNodeImage(node50, TREE_FOLDER_ALARM_DOWN);
	    }
	}

	// Set a minor alarm on an existing leaf node and propogate up
	// the ancestry chain.
	TreeNode node40 = tree.getChildNode(treeNodeID(40));
	if (node40 != null) {
	    TreeNode node42 = node40.getChildNode(treeNodeID(42));
	    if (node42 != null) {
		setNodeImage(node42, TREE_FOLDER_ALARM_MINOR);
		setNodeImage(node40, TREE_FOLDER_ALARM_MINOR);
	    }
	}

	return tree;
    }

    /** Set the Tree Object */
    public void setTree(Tree tree) {
	this.tree = tree;
    }

    public String getAlertDetail() {
	return alertDetail;
    }

    public String getAlertRendered() {
	return alertRendered.toString();
    }
    
    /** Return the array of test case options */
    public Option[] getTestCaseOptions() {
        return testCaseOptions;
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Action Handlers
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** Action handler when navigating to the dynamic tree example */
    public String showDynamicTree() {
	return SHOW_DYNAMIC_TREE;
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

    /** Action handler when clicking on a tree node */
    public String treeNodeAction() {
	return null;
    }

    /** Action listener when clicking on a tree node */
    public void treeNodeActionListener(ActionEvent ae) {
	String componentID = ((UIComponentBase)ae.getSource()).getId();
	String nodeNum;
	try {
	    nodeNum = Integer.toString(getNodeNum(componentID));
	} catch (Exception e) {
	    nodeNum = componentID;
	}
	Object[] args = { tree.getId(), nodeNum };
	alertDetail = MessageUtil.getMessage("tree_alertDetail", args);
	alertRendered = Boolean.TRUE;
	tree.setSelected(componentID);
    }

    /** Action handler for the testcase dropdown menu */
    public String testCaseAction() {
	alertDetail = null;
	alertRendered = Boolean.FALSE;
	return null;
    }

    /** Action listener for the testcase dropdown menu */
    public void testCaseActionListener(ActionEvent ae) {
	// Get the selection from the dropdown
	DropDown dropDown = (DropDown) ae.getComponent();
	String selected = (String) dropDown.getSelected();

	if (selected.equals("tree_testCase_yoke34")) {
	    // Make sure node 34 exists.
	    String id = treeNodeID(34);
	    TreeNode node = tree.getChildNode(id);
	    if (node != null) {
		// It exists, so select it and expand all ancester nodes
		tree.setSelected(id);
		TreeNode parent = TreeNode.getParentTreeNode(node);
		while (parent != null) {
		    parent.setExpanded(true);
		    parent = TreeNode.getParentTreeNode(parent);
		}
	    }
	}
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** Reset everything back to initial state */
    private void reset() {
	alertDetail = null;
	alertRendered = Boolean.FALSE;
	tree = null;
    }

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
        ExampleUtilities.setMethodExpression(link,
	    "actionExpression", "#{DynamicTreeBean.treeNodeAction}");

	Class[] paramTypes = new Class[] {ActionEvent.class};
        ExampleUtilities.setMethodExpression(link,
	    "actionListenerExpression", "#{DynamicTreeBean.treeNodeActionListener}",
	    null, paramTypes);

	return link;
    }

    /** Return an ImageHyperlink object */
    private ImageHyperlink nodeImage(String label, int nodeNum, String icon) {
	ImageHyperlink link = new ImageHyperlink();
	link.setId(nodeImageID(nodeNum));
	_setNodeImage(link, icon);
        ExampleUtilities.setMethodExpression(link,
	    "actionExpression", "#{DynamicTreeBean.treeNodeAction}");

	Class[] paramTypes = new Class[] {ActionEvent.class};
        ExampleUtilities.setMethodExpression(link,
	    "actionListenerExpression", "#{DynamicTreeBean.treeNodeActionListener}",
	    null, paramTypes);

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

	// For themed images, we call setIcon on the ImageHyperLink.
	// Otherwise we call setImageURL.
	if (icon.equals(TREE_DOCUMENT)
	    || icon.equals(TREE_FOLDER)
	    || icon.equals(TREE_FOLDER_ALARM_MINOR)
	    || icon.equals(TREE_FOLDER_ALARM_MAJOR)
	    || icon.equals(TREE_FOLDER_ALARM_DOWN)
	    || icon.equals(TREE_FOLDER_ALARM_CRITICAL) ) {
	    if (o instanceof ImageHyperlink)
		((ImageHyperlink)o).setIcon(icon);
	    if (o instanceof ImageComponent)
		((ImageComponent)o).setIcon(icon);
	} else {
	    if (o instanceof ImageHyperlink)
		((ImageHyperlink)o).setImageURL(icon);
	    if (o instanceof ImageComponent)
		((ImageComponent)o).setUrl(icon);
	}
    }

    /** Compose the tooltip and set it on all facets of the node */
    private void makeToolTip(TreeNode node, String icon) {
	String tip = node.getText();
	if (icon.equals(TREE_FOLDER_ALARM_MINOR))
	    tip = MessageUtil.getMessage("tree_alarmMinorTip", tip);
	else if (icon.equals(TREE_FOLDER_ALARM_MAJOR))
	    tip = MessageUtil.getMessage("tree_alarmMajorTip", tip);
	else if (icon.equals(TREE_FOLDER_ALARM_DOWN))
	    tip = MessageUtil.getMessage("tree_alarmDownTip", tip);
	else if (icon.equals(TREE_FOLDER_ALARM_CRITICAL))
	    tip = MessageUtil.getMessage("tree_alarmCriticalTip", tip);
	else if (icon.equals(TREE_DOCUMENT_ALARM_MINOR))
	    tip = MessageUtil.getMessage("tree_alarmMinorTip", tip);
	else if (icon.equals(TREE_DOCUMENT_ALARM_MAJOR))
	    tip = MessageUtil.getMessage("tree_alarmMajorTip", tip);
	else if (icon.equals(TREE_DOCUMENT_ALARM_DOWN))
	    tip = MessageUtil.getMessage("tree_alarmDownTip", tip);
	else if (icon.equals(TREE_DOCUMENT_ALARM_CRITICAL))
	    tip = MessageUtil.getMessage("tree_alarmCriticalTip", tip);

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

    /** Extract and return the node number from the specified component ID */
    private int getNodeNum(String id) {
	String nodeNum = id;
	nodeNum = nodeNum.replace("Node", "");
	nodeNum = nodeNum.replace("Image", "");
	nodeNum = nodeNum.replace("Link", "");
	return Integer.parseInt(nodeNum);
    }
}
