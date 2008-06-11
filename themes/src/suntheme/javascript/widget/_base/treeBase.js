/**
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

@JS_NS@._dojo.provide("@JS_NS@.widget._base.treeBase");

/**
 * This function is used to construct an accordion widget.
 *
 * @name @JS_NS@.widget.treeBase
 * @extends @JS_NS@.widget._base.widgetBase
 * @class This class contains the core functions for a tree widget.
 * @constructor
 * @param {Object} props Key-Value pairs of properties.
 * @config {Object} rootNode The DOM node representing the root of this tree.
 * @config {boolean} multipleSelect True if multiple nodes can be selected.
 * @config {boolean} loadOnSelect Set to true if nodes can be loaded dynamically.
 * @config {Object} focusElement The DOM Node representing the element currently in focus.
 * @config {Object} submittedValue An Object representing the state of the tree. This data
 *  is should be sent to the server to synchronize the server side representation of the
 *  tree with the client side state of the tree.    
 */
@JS_NS@._dojo.declare("@JS_NS@.widget._base.treeBase",
        @JS_NS@.widget._base.widgetBase, {
    // Set defaults.
    constructor: function() {
	// If true more than one node can be selected.
	this.multipleSelect = false,
	// If true tree nodes can be loaded dynamically
	this.loadOnSelect = false;
	
        // the node currently on focus
        this.focusElement = null;
        // the root node, each tree can have atmost one of these.
        this.rootNode = null;
        
        // the set of values that would be submitted to a server
        // this reflects the state of the tree on the client side.
        
        this.submittedValue = {"selectedNodes": [],
                               "_selectedNodes": [],
                               "isExpanded": []};
    }
});

/**
 * Function to deselect a tree node. The node is removed from the list
 * of selected nodes.
 *
 * @param {String} id ID of the node to be deselected.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget._base.treeBase.prototype.deSelectNode = function(nodeId) {

    // if multiple nodes can be selected what does deselect mean?
    // may be clicking on a selected node deselects it.
    for (var i=0; i<this.submittedValue.selectedNodes.length; i++) {
      if (this.submittedValue.selectedNodes[i] == nodeId) {
        delete this.submittedValue.selectedNodes[i];
      }
    }
    
    if (this.submittedValue._selectedNodes[nodeId]) {
        delete this.submittedValue._selectedNodes[nodeId];
    } else {
        this.submittedValue._selectedNodes[nodeId] = "selected";
    }
    var selectionInfo = "";
    for (var info in this.submittedValue._selectedNodes) {
        selectionInfo += info + "\n";
    }
    this._nodeSelectionState.value = selectionInfo;
    return true;
};

/**
 * This function is used to get list of selected nodes of the tree.
 * An array of selected nodes is returned.
 *
 * @return {Array} An array of selected nodes found during a preorder traversal.
 */
@JS_NS@.widget._base.treeBase.prototype.getSelectedNodes = function() {
    
    return this.submittedValue.selectedNodes;
};

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget._base.treeBase.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

    // get the root
    if (this.rootNode != null) { props.rootNode = this.rootNode; }
    props.expanded = this.expanded;
    
    // get all children
    
    // Set properties.
    if (this.style != null) { props.style = this.style; }
    if (this.className != null) {props.className = this.className}
    return props;
};

/**
 * Function to deselect a tree node. The node is removed from the list
 * of selected nodes and the styles changed appropriately.
 *
 * @param {String} id ID of the node to be deselected.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget._base.treeBase.prototype.isNodeSelected = function(id) {
    
    for (var i=0; i<this.submittedValue.selectedNodes.length; i++) {
      if (this.submittedValue.selectedNodes[i] == id) {
        return true;
      }
    }
    return false;
};

/**
 * Function to select a tree node. A tree maintains a list of
 * selected nodes which is available via the getSelectedNodes() 
 * function.
 *
 * @param {String} id ID of the node to be selected.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget._base.treeBase.prototype.selectNode = function(nodeId) {
    
    if (this.multipleSelect) {
        var index = this.submittedValue.selectedNodes.length;
        this.submittedValue.selectedNodes[index] = nodeId;
        this.submittedValue._selectedNodes[nodeId] = "selected";
    } else {
        var oldNode = this.submittedValue.selectedNodes[0];
        delete this.submittedValue._selectedNodes[oldNode];
        this.submittedValue.selectedNodes[0] = nodeId;
        this.submittedValue._selectedNodes[nodeId] = "selected";
    }
    
    var selectionInfo = "";
    for (var info in this.submittedValue._selectedNodes) {
        selectionInfo += info + "\n";
    }
    this._nodeSelectionState.value = selectionInfo;
    return true;
};


/**
 * Function to toggle a tree node. This function saves the state of the node
 * in a hidden field that can be used to reconstruct the tree after a page
 * submit.
 *
 * @param {String} id The id of the node that is to be expanded.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget._base.treeBase.prototype.toggleNode = function(id) {
    
    var stateInfo = "";
    for (var state in this.submittedValue.isExpanded) {
        stateInfo += state + "\n";
    }
    this._nodeToggleState.value = stateInfo;
    return true;   
};


@JS_NS@.widget._base.treeBase.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }
    
    // Extend widget object for later updates.
    return this._inherited("setProps", arguments);
}


