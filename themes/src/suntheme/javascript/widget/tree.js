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

@JS_NS@._dojo.provide("@JS_NS@.widget.tree");

@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.treeBase");

/**
 * This function is used to construct an tree widget.
 *
 * @name @JS_NS@.widget.tree
 * @extends @JS_NS@.widget._base.treeBase
 * @class This class contains functions for the Ajax based tree widget.
 * @constructor
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} className CSS selector.
 * @config {Object} imageProps Image associated tree nodes
 * @config {Object} label label widget properties associated with node.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {boolean} loadOnExpand True if child nodes will be loaded when toggle icon is clicked, false by default.
 * @config {boolean} multipleSelect Set to true if multiple nodes can be selected
 * @config {String} style Specify style rules inline.
 * @config {Array} childNodes Properties constituting the tree's children
 * @config {boolean} visible Hide or show element.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.tree", [
        @JS_NS@.widget._base.refreshBase,
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.treeBase ], {
    // Set defaults.
    constructor: function() {
        this._focusNode = null;
        this._lastFocusNode = null;
        // this.loadOnExpand = false;
    },
    _widgetType: "tree" // Required for theme properties.
});

/**
 * This function should be used by developers to add nodes to a tree. This
 * function can be used to add a single node or an array of nodes
 * each of which can have one or more children. This is a public
 * function. The argument passed to the function could either be a JSON object
 * representing a tree node and its children or an array of tree node objects 
 * their children. When arbitrary nodes are being added to a tree their
 * parent node must be supplied. Otherwise, the node will not be added.
 * 
 * @param {Object} nodeProps Key-Value pairs of tree node properties or an array of node properties.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.tree.prototype.addNodes = function(nodeProps) {
    if (nodeProps == null) {
        return false;
    }
    
    // a single node is possibly being added
    if (nodeProps.length == undefined) { 
        var parentId = nodeProps.parent;
        // nodes without valid parents will not be added
        if (parentId == undefined) {
            return false;
        }
        var parentNode = document.getElementById(parentId);
        
        // find the depth of the parent.
        var depth = this._getNodeDepth(parentId); 

        // If parentNode has no children add it as the first child node,
        // else, add it as the last child of the parent node. In the latter
        // case the alignment of the previous sibling has to change 
        // since it won't be the last node any more.
        var childContainer = document.getElementById(parentId + "_children");
        if (childContainer) {
            
            this._addNodes(childContainer, nodeProps, depth, false, true);
            // get previous sibling
            // if (sibling has children) check if node is expanded
            // if node expanded change expanded image
            // if node collapsed change collapsed image
            // if leaf node change last line image
            var prevSibling = this._getPreviousSibling(nodeProps.id);
            if (prevSibling) {
                this._changeLastIcon(prevSibling.id, "middle");
                
            }
            
        } else { 
            var cc = this._childrenContainer.cloneNode(false);
            cc.id = parentId + "_children";
            this._addNodes(cc, thisNodeProps, depth, true, true);
            firstChild = false;
            
        }
    } else {  // an array of nodes are being added
    
        var firstChild = true;;
        var lastChild = false;

        for (var i=0; i< nodeProps.length; i++) {

            if (i == nodeProps.length -1) {
                lastChild = true;
            }

            var thisNodeProps = nodeProps[i];

            if (thisNodeProps.id == undefined) {
                continue;
            }

            var parentId = thisNodeProps.parent;
            // nodes without valid parents will not be added
            if (parentId == undefined) {
                continue;
            }
            var parentNode = document.getElementById(parentId);

            // find the depth of the parent.
            var depth = this._getNodeDepth(parentId); 

            // If parentNode has no children add it as the first child node,
            // else, add it as the last child of the parent node. In the latter
            // case the alignment of the previous sibling has to change 
            // since it won't be the last node any more.

            var childContainer = document.getElementById(parentId + "_children");
            if (childContainer) {
                firstChild = false;
                this._addNodes(childContainer, thisNodeProps, depth, firstChild, lastChild);
                // get previous sibling
                // if (sibling has children) check if node is expanded
                // if node expanded change expanded image
                // if node collapsed change collapsed image
                // if leaf node change last line image
                var prevSibling = this._getPreviousSibling(thisNodeProps.id);
                if (prevSibling) {
                   this._changeLastIcon(prevSibling.id, "middle");
                }

            } else {
                var cc = this._childrenContainer.cloneNode(false);
                cc.id = parentId + "_children";
                this._addNodes(cc, thisNodeProps, depth, firstChild, lastChild);
                firstChild = false;
                
            }
            parentNode._loaded = true;
        }
    }
    
};


/**
 * This function should be used to delete a node and all its 
 * children. The ID of the node that is to be deleted should 
 * be supplied. 
 *
 * @param {String} nodeID ID of the node to be deleted.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.tree.prototype.deleteNode = function(nodeId) {
    if (nodeId == null) {
        return false;
    }
    
    var thisNode = document.getElementById(nodeId);
    var parent = thisNode.parentNode;
    var prevSib = this._getPreviousSibling(nodeId);
    var nextSib = this._getNextSibling(nodeId);
    
    // node is the only child
    // delete the node
    // remove the parent node's toggle icon
    // set the last line image of the parent node based on its location
    
    if ((prevSib == undefined) && (nextSib == undefined)) {
        
        // parent.removeChild(thisNode);
        this._widget._removeChildNodes(thisNode);
        var toggleWidget = this._widget.getWidget(parent.id + "_turner");
        if (toggleWidget) {
            toogleWidget.destroy();
            var lineImgIcon = "TREE_LINE_MIDDLE_NODE";
            var imageDiv = document.getElementById(parent.id + "_nodeImages");
            var imageProps = {
              widgetType: "image",
              id: parent.id + "_lasticon",
              icon: lineImgIcon};
            this._widget._addFragment(imageDiv, imageProps, "last");
        }
    
    // node is the first child    
    } else if (prevSib == undefined) {
        var newFirstChild;
        var sibPlusOne = nextSib.nextSibling;
        
        // if the node to be deleted has child nodes remove those as well.
        
        if (nextSib.id == (nodeId + "_children")) {
            
            if (sibPlusOne) {
              newFirstchild = sibPlusOne;
              this._widget._removeChildNodes(thisNode);
              this._widget._removeChildNodes(nextSib);
            } else {    // change the line images associated with the next node.
                this._widget._removeChildNodes(thisNode);
                this._widget._removeChildNodes(nextSib);
                var toggleWidget = this._widget.getWidget(parent.id + "_turner");
                if (toggleWidget) {
                    this._widget.destroyWidget(parent.id + "_turner");
                    var lineImgIcon = "TREE_LINE_MIDDLE_NODE";
                    var imageDiv = document.getElementById(parent.id + "_nodeImages");
                    var imageProps = {
                      widgetType: "image",
                      id: parent.id + "_lasticon",
                      icon: lineImgIcon};
                    this._widget._addFragment(imageDiv, imageProps, "last");
                }
            }
            
        } else {
            this._widget._removeChildNodes(thisNode);
            newFirstChild = nextSib;
        }
        // change the last line image associated with the node.
        this._changeLastIcon(newFirstChild, "first");
        
    } else if (nextSib == undefined) {
        // this is the last node at this level/depth
        // remove this node and all its children
        this._widget._removeChildNodes(thisNode);
        
        // find the node which contains all the child nodes and delete it.
        var childWrapper = thisNode.nextSibling;
        if (childWrapper) {
            this._widget._removeChildNodes(childWrapper);
        }
        // change the previous siblings last line image
        
        this._changeLastIcon(prevSib, "last");
        
    } else {
        // this is a middle node
        // remove child nodes if any
        var childContainer = document.getElementById(nodeId + "_children");
        if (childContainer) {
            this._widget._removeChildNodes(childContainer);
        }
        // remove this node.
        this._widget._removeChildNodes(thisNode);
    }
    
};

/**
 * This function returns the parent node of a given node.
 * @param {String} nodeID ID of the node to be deleted.
 * @return {String} ID of the DOM node representing the parent node.
 */
@JS_NS@.widget.tree.prototype.getParentNodeId = function(nodeId) {
    
    if (nodeId == undefined) {
        return null;
    }
    
    if (nodeId == this.id || nodeId == (this.id + "_rootNode")) {
        return null;  //
    }
    var thisNode = document.getElementById(nodeId);
    var pNode = thisNode.parentNode;
    if (pNode.previousSibling) {
        return pNode.previousSibling.id;
    }
}

/**
 * This function is used to get the first level child nodes of 
 * a given node in the tree. An array representing the ID of the 
 * child nodes is returned.
 * @param {String} nodeID ID of the node to be deleted.
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.tree.prototype.getChildNodes = function(nodeId) {
    
    if (nodeId == undefined) {
        return null;
    }
    
    // all child nodes are contained in a domNode whose id is "nodeID_children"
    var node = document.getElementById(nodeId + "_children");
    if (node) {
        var count = 0;
        var childrenArray = [];  // the array that will be returned
        var allNodes = node.childNodes;  // get a list of all child DOM nodes
        for (var i=0; i < allNodes.length; i++) {
            var cNode = allNodes[i];
            var cNodeId = cNode.id;
            var index1 = cNodeId.lastIndexOf("_children");
            if (cNodeId.length == (index1 + 9)) {
                continue;
            }
            childrenArray[count++] = cNodeId;
        }
        return childrenArray;
    }
    return null;
};

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.tree.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.skipTreeLInk != null) { props.skipTreeLink = this.skipTreeLink; }
    if (this.tabIndex != null) { props.tabIndex = this.tabIndex; }
    if (this.parentNode != null) { props.parentNode = this.parentNode; }
    if (this.expanded != null) { props.expanded = this.expanded; }
    if (this.loadOnExpand != null) { props.loadOnExpand = this.loadOnExpand; }
    if (this.submittedValue.selectedNodes != null) { props.selected = this.submittedValue.selectedNodes; }
    if (this.style != null) { props.style = this.style; }
    if (this.hasChildren != null) { props.hasChildren = this.hasChildren; }
    if (this.childNodes != null) { props.childNodes = this.childNodes; }
    if (this.submittedValue != null) { props.submittedValue = this.submittedValue; }
    if (this.image != null) {props.image = this.image; } 
    if (this.label != null) {props.label = this.label; } 
    return props;
};

/**
 * This function is used to get the properties of a given node. The ID
 * of the node needs to be supplied. 
 * @param {String} nodeId ID of the node whose property is being returned.
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.tree.prototype.getNodeProps = function(nodeId) {

    var nodeID = nodeId;
    var imgDiv = nodeId + "_nodeImages";
    var contentDiv = nodeId + "_contentNode";
    
    if (nodeId == this.id) {
        nodeID = this._domNode.id + "_rootNode";
        imgDiv = this._domNode.id + "_rootNodeImage";
        contentDiv = this._domNode.id + "_rootNodeText";
    }
    
    var treeNode = document.getElementById(nodeID);
    var nodeProps = {};
    // Get properties.
    if (treeNode) {
        nodeProps.parentNodeId = this.getParentNodeId(nodeId);
        nodeProps.expanded = treeNode._expanded;
        
        nodeProps.selected = this.isNodeSelected(nodeId);
        
        var imageDiv = document.getElementById(imgDiv);
        if (imageDiv) {
          var imgNode = imageDiv.lastChild;
          if (imgNode) {
            if (imgNode.getProps) {
                nodeProps.image = imgNode.getProps();
            }
          }
        }
        
        var cDiv = document.getElementById(contentDiv);
        if (cDiv) {
          var nLabel = cDiv.lastChild;
          if (nLabel) {
            if (nLabel.getProps) {
              nodeProps.label = nLabel.getProps();
            } else {
              nodeProps.label = nLabel;  
            }
          }
        }
        nodeProps.childNodes = this.getChildNodes(nodeId);
        
    }
    
    return nodeProps;
};

/**
 * This private function changes the last icon of a non leaf node.
 * The position of non leaf nodes may change when sibling nodes
 * are added or deleted.  
 * @param {String} nodeId ID of the node to be deleted.
 * @param {String} position Position of node among its siblings (first, middle, last).
 * @param {String} nodeId The ID of the ndoe in question.
 * @private
 */
@JS_NS@.widget.tree.prototype._changeLastIcon = function(nodeId, position) {

    var iconId = nodeId + "_lasticon";
    var widget = this._widget.getWidget(iconId);
    var lineImgIcon;
    if (position == "last") {
        lineImgIcon = "TREE_LINE_LAST_NODE";
    } else if (position == "middle") {
        lineImgIcon = "TREE_LINE_MIDDLE_NODE";
    } else {
        lineImgIcon = "TREE_LINE_FIRST_NODE";
    }
    if (widget) {
        widget.setProps(
          {id: iconId, 
           icon: lineImgIcon});
    }
};

/**
 * This function is used to get the depth of a given node.
 * The depth represents the number of levels of hierarchy
 * between a node and the root node. For example, a root 
 * node has a depth of zero. A child of the root node has 
 * a depth of one, and so on.
 * 
 * @param {String} nodeId ID of the node to be deleted.
 * @return {integer} An integer representing the depth of the node. -1 if 
 * node is not found.
 * @private
 */
@JS_NS@.widget.tree.prototype._getNodeDepth = function(nodeId) {
    
    if (nodeId == null) {
        return -1;
    }
    
    // root node has depth 0
    if (nodeId == this.rootNode) {
        return 0;
    }
    
    var thisNode = document.getElementById(nodeId);
    var parent = thisNode.parentNode;
    var count = 1;
    while (parent.id != this.rootNode) {
        parent = parent.parentNode;
        count++;
    };
    
    // Given the way the tree nodes are organized we need to subtract 1
    // from the final result.
    return count--;
};
    

/**
 * This function is used to find the previous sibling of a 
 * given tree node. If the given node is the first child of its parent
 * a null value is returned. 
 * 
 * @param {String} nodeId ID of the node to be deleted.
 * @return {Object} A dom node representing the previous sibling of the
 * given node.
 * @private
 */
@JS_NS@.widget.tree.prototype._getPreviousSibling = function(nodeId) {
    
    if (nodeId == null) {
        return null;
    }
    // if previous sibling is the child container of a node return the sibling 
    // before that child container, if any. Else, return the previous sibling.
    var thisNode = document.getElementById(nodeId);
    var prevNode = thisNode.previousSibling;
    if (prevNode) {
        var prevNodeId = prevNode.id;
        var lastIndex = prevNodeId.lastIndexOf("_children");
        if (prevNodeId.substring(lastIndex) == "_children") {
            return prevNode.previousSibling;
        } else {
            return prevNode;
        }
    } else {
        return null;
    }
};

/**
 * This function is used to find the next sibling of a 
 * given tree node. If the given node is the last child of its parent
 * a null value is returned. 
 *
 * @param {String} nodeId ID of the node to be deleted.
 * @return {Object} A dom node representing the previous sibling of the
 * given node.
 * @private
 */
@JS_NS@.widget.tree.prototype._getNextSibling = function(nodeId) {
    
    if (nodeId == null) {
        return null;
    }
    
    var thisNode = document.getElementById(nodeId);
    
    // if next sibling is the child container of this node return the sibling 
    // after the child container, if any. Else, return the next sibling.
    if (thisNode) {
        var nextNode = thisNode.nextSibling;
        if (nextNode) {
            if (nextNode.id == nodeId + "_children") {
                return nextNode.nextSibling;
            } else {
                return nextNode;
            }
        } else {
            return null;
        }
    }

};


/**
 * This function is used to obtain the outermost HTML element class name.
 * <p>
 * Note: Selectors should be concatinated in order of precedence (e.g., the
 * user's className property is always appended last).
 * </p>
 * @return {String} The outermost HTML element class name.
 * @private
 */
@JS_NS@.widget.tree.prototype._getClassName = function() {
    var className = this._inherited("_getClassName", arguments);

    // Get theme property.
    var newClassName = this._theme.getClassName("TREE_ROW", "");

    return (className)
        ? newClassName + " " + className
        : newClassName;
};

/**
 * This function is used to obtain the appropriate toggle node depending on
 * whether the node is expanded or not.
 *
 * @param {String} nodeId ID of the node to be deleted.
 * @param {String} handleIcon The associated icon name.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tree.prototype._getToggleNodeProps = function(handleIcon, nodeId) {
  
    var nodeProps = {};
    nodeProps = { 
            widgetType: "imageHyperlink",
            id: nodeId + "_turner",
            enabledImage: {
                widgetType: "image",
                icon: handleIcon,
                id: nodeId + "_turner_image"
            },
            onClick: "@JS_NS@.widget.common.getWidget('" + this.id + "').toggleNode('"+ nodeId + "');return false;",
            title: this._theme.getMessage("Tree.expandTxt")
          };
     return nodeProps;
};
   
/**
 * This function is invoked when the user clicks on the toggle icon.
 *
 * @param {String} nodeId ID of the node to be deleted.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tree.prototype.toggleNode = function(nodeId) {
    
    var treeNode = document.getElementById(nodeId);
    var cc = document.getElementById(nodeId + "_children");
    if (treeNode) {
        // publish the toggle node begin event
        this._publish(@JS_NS@.widget.tree.event.nodeToggle.beginTopic, 
                      [{nodeId: nodeId, 
                      expanded: treeNode._expanded}]);
                                            
        if (treeNode._expanded == false) {
            // if loadOnExpand is set to true publish an event
            // that developers can use to populate the node in
            // question with child nodes. This can be done on the client
            // side as well as via JSFX if its enabled.
            var rootId = this.id;
            var _nodeId = nodeId;
            if (this.loadOnExpand == true) {
                var isLoaded = treeNode._loaded;
                if (isLoaded == null || isLoaded == false) {
                    this._publish(@JS_NS@.widget.tree.event.load.beginTopic, 
                      [{id: rootId, nodeId: _nodeId}]);

                    return false;
                } else {
                    this._updateToggleIcon(nodeId);
                    treeNode._expanded = true;
                    if (cc) {
                      cc.style.display = "block";
                    }
                }
            
            } else {
              this._updateToggleIcon(nodeId);
              treeNode._expanded = true;
              if (cc) {
                cc.style.display = "block";
              }
            }
            if (this.submittedValue.isExpanded[nodeId]) {
                delete this.submittedValue.isExpanded[nodeId];
            } else {
                this.submittedValue.isExpanded[nodeId] = "true";
            }
        } else {  // child nodes are already loaded, simply expand the parent
            this._updateToggleIcon(nodeId);
            treeNode._expanded = false;
            if (this.submittedValue.isExpanded[nodeId]) {
                delete this.submittedValue.isExpanded[nodeId];
            } else {
                this.submittedValue.isExpanded[nodeId] = "false";
            }
            if (cc) {
              cc.style.display = "none";
            }
        }
        // publish the end topic signifying the end of all things related to 
        // toggling a node.
    
        this._publish(@JS_NS@.widget.tree.event.nodeToggle.endTopic, 
          [{nodeId: _nodeId, 
            expanded: treeNode._expanded}]);
    }
    // Extend widget object for later updates.
    return this._inherited("toggleNode", arguments);
    
    
};

/**
 * This function is used to make a node visible.
 *
 * @param {Object} props Node properties.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tree.prototype._makeNodeVisible = function(props) {
      
      var treeNode = document.getElementById(props.id);
      if (treeNode) {
          treeNode._expanded = true;
          treeNode._loaded = true;
          var cc = document.getElementById(props.id + "_children");
          if (cc) {
              cc.style.display = "block";
          }
      }
      this._updateToggleIcon(props.id);
      return true;
};

/**
 * This private function updates the toggle icon.
 *
 * @param {String} nodeId ID of the node in question.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tree.prototype._updateToggleIcon = function(nodeId) {
    
    // Get the toggle imageHyperlink widget and replace its existing
    // icon with a newer one correspnding to the new state (expanded or 
    // collapsed as teh case may be). 
    var widget = this._widget.getWidget(nodeId + "_turner");
    if (widget) {
      var oldIcon = widget.getProps().enabledImage.icon;
      var newIcon;
      var index = oldIcon.indexOf("_DOWN_");
      if (index != -1) {
          newIcon = oldIcon.substring(0, index) + "_RIGHT_" + 
              oldIcon.substring(index+6, oldIcon.length);
      } else {
          index = oldIcon.indexOf("_RIGHT_");
          if (index != -1) {
            newIcon = oldIcon.substring(0, index) + "_DOWN_" + 
              oldIcon.substring(index+7, oldIcon.length);
          }
      }
      var imageProp = {
          widgetType: "image",
          id: nodeId + "_turner_image",
          icon: newIcon};
      widget.setProps({enabledImage:imageProp});
    }
};

/**
 * This function deselects a node.
 *
 * @param {String} nodeId ID of the node in question.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.tree.prototype.deSelectNode = function(nodeId) {
    
    if (nodeId != undefined) {
        var treeNode = document.getElementById(nodeId);
        if (treeNode) {
          treeNode.className = this._theme.getClassName("TREE_ROW");
        }
    }
   
    // Extend widget object for later updates.
    return this._inherited("deSelectNode", arguments);
};

/**
 * This function selects a node.
 *
 * @param {String} nodeId ID of the node in question.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.tree.prototype.selectNode = function(nodeId) {
    
    var treeNode = document.getElementById(nodeId);
    if (treeNode) {
        treeNode.className = this._theme.getClassName("TREE_SELECTED_ROW");
    }
    
    // invoke treeBase to set selection information
    this._inherited("selectNode", arguments);
    return true;
};

/**
 * This function toggles the selection.
 *
 * @param {String} nodeId ID of the node in question.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tree.prototype._updateSelection = function(nodeId) {
    
    if (this.multipleSelect) {
        if (this.isNodeSelected(nodeId)) {
            this.deSelectNode(nodeId);
        } else {
            this.selectNode(nodeId);
        }
    } else {
        if (this.submittedValue.selectedNodes[0] == undefined) {
            this.selectNode(nodeId);
        } else {
          this.deSelectNode(this.submittedValue.selectedNodes[0]);
          this.selectNode(nodeId);
        }
    }
    return true;
};


/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tree.prototype._postCreate = function () {
    
    // Set ids.
    if (this.id) {
        this._domNode.id = this.id;
        this._nodeSelectionState.id = this.id + "_selectedNodes";
        this._nodeSelectionState.name = this._nodeSelectionState.id;
        this._nodeToggleState.id = this.id + "_toggleState";
        this._nodeToggleState.name = this._nodeToggleState.id;
    }
    // create the skip hyperlink and its target
    if (this.skipLink == null) {
        this.skipLink = {
            id: this.id + "_skipTreeLink",
            enabledImage: {
                widgetType: "image",
                icon: "DOT",
                id: this.id + "_skipTreeLinkIcon"
            },
            target: this.id + "_skipLinkTarget",
            title: this._theme.getMessage("tree.skipTagAltText"),
            widgetType: "imageHyperlink"
          };
        
         this._widget._updateFragment(this._skipLinkContainer, 
                this.skipLink.id, this.skipLink);
    }
    if (this.skipLinkTarget == null) {
        // this.skipLinkTarget = this._widget.getHyperlinkProps({
        this.skipLinkTarget = {
            widgetType: "hyperlink", 
            id: this.id + "_skipLinkTarget",
            name: this.id + "_skipLinkTarget"
        };
        this._widget._updateFragment(this._skipLinkTargetContainer, 
            this.skipLinkTarget.id, this.skipLinkTarget);
    }

    this._rootNodeContainer.id = this._domNode.id + "_rootNode";
    this._rootImageContainer.id = this._domNode.id + "_rootNodeImage";
    this._rootContentContainer.id = this._domNode.id + "_rootNodeText";
    
    // Subscribe to the "end of load children" event associated with a given
    // node.
    this._widget.subscribe(@JS_NS@.widget.tree.event.load.endTopic,
        this, "_makeNodeVisible");
    
    // this._widget._removeChildNodes(this._childrenContainer);
    
    
    // Create callback function for onkeydown event.
    this._dojo.connect(this._domNode, "onkeydown", 
      this._createOnKeyDownCallBack());
    
    return this._inherited("_postCreate", arguments);
};

/**
 * This function is used to set widget properties using Object literals.
 * In the case of the tree widget
 * setProps() is invoked only the first time a tree is being laid out. 
 * Subsequently appropriate public functions need to be added to load child nodes,
 * delete nodes, update nodes etc.
 * <p>
 * Note: This function extends the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 * </p><p>
 * If the notify param is true, the widget's state change event shall be
 * published. This is typically used to keep client-side state in sync with the
 * server.
 * </p>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.tree.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }
    
    if (props.loadOnExpand != null) {
      this.loadOnExpand = new Boolean(props.loadOnExpand).valueOf();
    } else {
      this.loadOnExpand = new Boolean("false").valueOf();
    }
    
    
    // Extend widget object for later updates.
    return this._inherited("setProps", arguments);
};

/**
 * This function is used to update a node and its children. A JSON object
 * representing a single node and its children need to be supplied.
 *
 * @param {Object} nodeProps Key-Value pairs of properties of the node to be updated.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.tree.prototype.updateNode = function(nodeProps) {
    if (nodeProps == null) {
        return false;
    }
    
    // find the node that is the parent of this node.
    var parentId = props.parent;
    var parentNode = document.getElementById(parentId);

    var thisNode = document.getElementById(props.id);
    if (thisNode) {
        if (props.image) {
            if (props.image.id == undefined) {
                props.image.id = props.id + "_nodeImage";
            }
            var nodeDiv = document.getElementById(props.image.id);
            if (nodeDiv) {
              this._widget._updateFragment(nodeDiv, 
              props.image.id, props.image);
            }
        }        

        if (props.label) {
            var labelDiv = document.getElementById(props.id + "_contentNode");
            if (labelDiv) {
              this._widget._updateFragment(labelDiv, 
                labelDiv.id, props.label);
            }
        }
    } else {
        return false; // cannot update an non existant node
    }
    
    // recursively update its children if any.
    if (props.childNodes) {
        for (var i=0; i<props.childNodes.length; i++) {
            this.updateNode(props.childNodes[i]);
        }
    } else {
        return true;
    }
};



/**
 * This function is used to set widget properties. Please see the setProps() 
 * function for a list of supported properties. In the case of the tree widget
 * setProps() is invoked only the first time a tree is being laid out. 
 * Subsequently appropriate public functions need to be added to load child nodes,
 * delete nodes, update nodes etc.
 * <p>
 * Note: This function should only be invoked through setProps().
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tree.prototype._setProps = function(props) {
    
    // loadOnExpand set to false by default.
    if (props.loadOnExpand == undefined) {
        this.loadOnExpand = false;
    } else if (props.loadOnExpand == "true") {
        this.loadOnExpand = true;
    } else {
        this.loadOnExpand = false;
    }
    
    // multipleSelect set to false by default
    if (props.multipleSelect == undefined) {
        this.multipleSelect = false;
    } else if (props.multipleSelect == "true") {
        this.multipleSelect = true;
    } else {
        this.multipleSelect = false;
    }
    
    if (props == null) {
        return false;
    }
    
    if (this.rootNode == undefined) {
        // remove child nodes from the template so that we start with an 
        // empty tree.
    
        this._widget._removeChildNodes(this._childrenContainer);
    }
    
    var firstTime = false;
    if (props.parent == undefined) {
        if (this.rootNode == undefined) {
            this.rootNode = props.id;
            firstTime = true;
        } else {
            return false;  // cannot add a node without a parent.
        }
        // add the root node.
        if (props.image) {
            if (props.image.id == undefined) {
                props.image.id = this._domNode.id + "_nodeImage";
            }
            this._widget._addFragment(this._rootImageContainer, 
            props.image, "last");
        }        
        
        if (props.label) {
            this._widget._addFragment(this._rootContentContainer, 
                props.label, "last");
        }
    }
    
    // Each tree node would have a toggle icon if the node has children
    // Other image icons would be included if they are supplied by the application.
    
    // Add child nodes.
    // Unless the node is expanded show child nodes for the next level only. 
    // If the user has supplied a function to associate with node clicks
    // add that function to the onclick event associate with the node. 
    // If the application has associated a function to dynamically get the child
    // nodes when clicked associate the toggle node's onclick with this 
    // function. The user has to click on the toggle node to expand the tree.
    
    
    if (props.childNodes) {
        if (props.expanded == null || props.expanded == true || firstTime == true) {
            this._childrenContainer.style.display = "block";
        } else {
            this._childrenContainer.style.display = "none";
        }
        for (var i=0; i< props.childNodes.length; i++) {
          var thisChild = props.childNodes[i];
          //var nc = this._nodeContainer.cloneNode(false);
          var depth = 1; // nodes immediately after the root
          var firstChild = false;
          var lastChild = false;
          if (i == 0) {
              firstChild = true;
          }
          if (i+1 == props.childNodes.length) {
              lastChild = true;
          }
          this._childrenContainer.id = props.id + "_children";
          this._addNodes(this._childrenContainer, thisChild, depth, 
            firstChild, lastChild);
        }
    } 
    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};

/**
 * Recursive function to add nodes. This is a private function that 
 * performs the grunt work of adding nodes and their children while building the
 * tree. The properties of the node being created alsong with its parent, the
 * depth of the node, whether the node is the first child or the last child 
 * are supplied as arguments to the function.
 *
 * @param {Object} props Key-Value pairs of properties.
 * @param {String} parentNode the container DOM node within which this subtree should
 *        subtree should be created.
 * @param {int} depth Depth of the node to be rendered
 * @param {boolean} firstChild  True if this is the frst child of its parent
 * @param {boolean} lastChild  True if this is the last child of its parent
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tree.prototype._addNodes = function(parentNode, 
        props, depth, firstChild, lastChild) {

    if (props == undefined) {
        return;
    }    
    var nc = this._nodeContainer.cloneNode(false);
    nc.id = props.id;
    if (this.loadOnExpand) {
        nc.loadOnExpand = true;
        if (props.onToggleNodeClick) {
            nc.onToggleNodeClick = props.onToggleNodeClick;
        }
    }
    if (props.selected == null) {
        nc.className = this._theme.getClassName("TREE_ROW");
    } else {
        if (props.selected == true) {
            this._updateSelection(props.id);
            nc.className = this._theme.getClassName("TREE_SELECTED_ROW");
        } else {
            nc.className = this._theme.getClassName("TREE_ROW");
        }
    }
    if (props.publishSelectionEvent == true) {
      nc.publishSelection = props.publishSelectionEvent;
    } else {
        nc.publishSelectionEvent = false;
    }
    var imageDiv = this._lineImageContainer.cloneNode(false);
    imageDiv.id = props.id + "_nodeImages";
    
    if (depth > 1) {
      for (var d=0; d < depth-1; d++) {
          lineImgIcon = "TREE_LINE_VERTICAL";  //"TREE_BLANK"
          var imageProps = {
            widgetType: "image",
            id: props.id + "_icon" + d,
            icon: lineImgIcon
          };
          this._widget._addFragment(imageDiv, imageProps, "last");
      }
    }
    
    var cc = this._childrenContainer.cloneNode(false);
    cc.id = props.id + "_children";
    
    if (props.childNodes) {
        var toggleProps;
        
        if (props.expanded == null) {
            props.expanded = false;
        }
        
        var toggleImgIcon;
        if (props.expanded) {
            nc._expanded = true;
            if (lastChild && firstChild) {
                toggleImgIcon = "TREE_HANDLE_DOWN_MIDDLE";
            } else if (lastChild) {
                toggleImgIcon = "TREE_HANDLE_DOWN_LAST";
            } else if (firstChild) {
                toggleImgIcon = "TREE_HANDLE_DOWN_TOP";
            } else {
                toggleImgIcon = "TREE_HANDLE_DOWN_MIDDLE";
            }
            
        } else {
            nc._expanded = false;
            var toggleImgIcon;
            if (lastChild && firstChild) {
                toggleImgIcon = "TREE_HANDLE_RIGHT_MIDDLE";
            } else if (lastChild) {
                toggleImgIcon = "TREE_HANDLE_RIGHT_LAST";
            } else if (firstChild) {
                toggleImgIcon = "TREE_HANDLE_RIGHT_TOP";
            } else {
                toggleImgIcon = "TREE_HANDLE_RIGHT_MIDDLE";
            }
            
        }
        toggleProps = this._getToggleNodeProps(toggleImgIcon, props.id);
        this._widget._addFragment(imageDiv, toggleProps, "last");
        
      } else {
        if (lastChild && firstChild) {
            lineImgIcon = "TREE_LINE_LAST_NODE";
        } else if (lastChild) {
            lineImgIcon = "TREE_LINE_LAST_NODE";
        } else if (firstChild && depth == 1) {
            lineImgIcon = "TREE_LINE_FIRST_NODE";
        } else {
            lineImgIcon = "TREE_LINE_MIDDLE_NODE";
        }
        var imageProps = {
          widgetType: "image",
          id: props.id + "_lasticon",
          icon: lineImgIcon};
        this._widget._addFragment(imageDiv, imageProps, "last");
      }

    // add node image properties
    if (props.image) {
      if (props.image.id == undefined) {
        props.image.id = thisChild.id + "_nodeImage";
      }
      this._widget._addFragment(imageDiv, props.image, "last");
      var imageWidget = this._widget.getWidget(props.image.id);
      var _id = this.rootNode;
      var _nodeId = props.id;
      if (imageWidget) {
          this._dojo.connect(imageWidget._domNode, "onclick", function(_nodeId) {
            var widget = @JS_NS@.widget.common.getWidget(_id);
            widget._updateSelection(props.id);
            widget._publish(@JS_NS@.widget.tree.event.nodeSelection.beginTopic, [{
            submitSelection: nc.publishSelection, id: _id, 
            nodeId: props.id , submittedValue: widget.submittedValue}]);
            return false;
          });
      }
    }
    nc.appendChild(imageDiv);

    // add node data properties
    // canot add a label widget inside the tree as it messes up the layout 
    // completely. Reverting to default label as a some text surrounded by
    // style selectors.
    if (props.label) {
            
      var contentDiv = this._contentContainer.cloneNode(false);
      contentDiv.id = props.id + "_contentNode";
      var _id = this.rootNode;
      var _nodeId = props.id;
      this._dojo.connect(contentDiv, "onclick", function(_nodeId) {
            var widget = @JS_NS@.widget.common.getWidget(_id);
            widget._updateSelection(props.id);
            widget._publish(@JS_NS@.widget.tree.event.nodeSelection.beginTopic, [{
            submitSelection: nc.publishSelection, id: _id, 
            nodeId: props.id , submittedValue: widget.submittedValue}]);
            return false;
      });
      this._widget._addFragment(contentDiv, props.label);
      nc.appendChild(contentDiv);
    }
    
    // append data for dispaly on this node (line of the tree).
    parentNode.appendChild(nc);

    //add its children
    if (props.childNodes) {
      if (props.expanded) {
        cc.style.display = "block";
      } else {
        cc.style.display = "none";
      }
      for (var i=0; i< props.childNodes.length; i++) {
          var newChild = props.childNodes[i];
          var firstChild = false;
          var lastChild = false;
          if (i == 0) {
              firstChild = true;
          }
          if (i+1 == props.childNodes.length) {
              lastChild = true;
          }
          
          this._addNodes(cc, newChild, depth+1, firstChild, lastChild);
      }
      parentNode.appendChild(cc);
    }
};

/**
 * This function updates the focused node within the tree.
 * The old focus element is blurred and the new focus element is
 * set to focus. Appropriate style selectors are applied. 
 * @param (String) newFocusNode The new focus node.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tree.prototype._updateFocus = function(newFocusNode) {
    
};



/**
 * The callback function for key press on the tree header.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tree.prototype._createOnKeyDownCallBack = function() {
    var _id = this.id;
    return function(event) {
        var common = @JS_NS@.widget.common;
        var widget = common.getWidget(_id);

        event = common._getEvent(event);
        var keyCode = common._getKeyCode(event);
        
        // if onkeypress returns false, we do not traverse the tree
        var keyPressResult = true;
        if (this._onkeypress) {
            keyPressResult = (this._onkeypress) ? this._onkeypress() : true;
        }        
       
        if (keyPressResult != false) {
            widget._traverseTree(keyCode, event, _id);
        }
        return true;
   };
};

/**
 * Accessibility support would be provided for the tree in the following way:
 *   
 *   The following key sequence are supported for keyboard navigation purposes:
 *     - Left and Right Arrow keys would expand and collapse non leaf nodes 
 *       respectively. These will have no impact on leaf nodes.
 *     - Up and Down arrow keys would move focus to next item in the visible 
 *       part of the tree.
 *     - When focus is on the previous element (in tabbing order) on the page, 
 *       hitting the tab key would bring focus to the first node on the tree. 
 *       After that navigation through the tree would have to be done using the
 *       aforementioned keysstrokes. Hitting the tab will take focus out of 
 *       the tree and onto the next element in the tabbing sequence.
 *     - If the user tabs back into the tree focus will be on the node that was 
 *       in focus the last time the user tabbed out of the tree.
 *
 *
 * @param (String) keyCode The valye of the key which was pressed
 * @param (Event) event The key press event.
 * @param (String) nodeId The id of the tree item. 
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tree.prototype._traverseTree = function(keyCode, event, nodeId) {
    var savedFocusElement;

    if (this.focusElement != null) {
        savedFocusElement = this.focusElement;
    }

    
    if (keyCode >= 37 && keyCode <= 40) {
        var forward = true;
        if (keyCode == 37 || keyCode == 38) {
            forward = false;
        }
        var focusSet = false;
        if (forward) {  // handling the down and right arrow keys
         
         } else {  // traverse backward

         
        }

        if (@JS_NS@._base.browser._isIe5up()) {
            window.event.cancelBubble = true;
            window.event.returnValue = false;
        } else {
            event.stopPropagation();
            event.preventDefault();
        }
    } else if(keyCode == 13 || keyCode == 32) {  // handle enter and space bar
        
    } else if (keyCode == 9) {  // handle tabbing
         
    } 
    return true;
};

/**
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
@JS_NS@.widget.tree.event =
        @JS_NS@.widget.tree.prototype.event = {
    /**
     * This object contains load event topics.
     * @ignore
     */
    load: {
        /** Load event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_tree_event_load_begin",

        /** Load event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_tree_event_load_end"
    },

    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_tree_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_tree_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_tree_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_tree_event_state_end"
    },

    /**
     * This object contains node selction event topics.
     * @ignore
     */
    nodeSelection: {
        /** Action event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_tree_event_nodeSelection_begin",
       
        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_tree_event_nodeSelection_end"
    },
    
    /**
     * This object contains node toggle event topics.
     * @ignore
     */
    nodeToggle: {
        /** Action event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_tree_event_nodeToggle_begin",
       
        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_tree_event_nodeToggle_end"
    }
};
