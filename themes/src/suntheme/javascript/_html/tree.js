/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
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

@JS_NS@._dojo.provide("@JS_NS@._html.tree");

@JS_NS@._dojo.require("@JS_NS@._base.proto");
@JS_NS@._dojo.require("@JS_NS@.theme.common");
@JS_NS@._dojo.require("@JS_NS@.widget.common");

/** 
 * @class This class contains functions for tree components.
 * @static
 * @private
 */ 
@JS_NS@._html.tree = {
    /**
     * This function is used to initialize HTML element properties with Object
     * literals.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {String} id The element id.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _init: function(props) {
        var message = "Cannot initialize tree.";
        if (props == null || props.id == null) {
            console.debug(message); // See Firebug console.
            return false;
        }
        var domNode = document.getElementById(props.id);
        if (domNode == null) {
            console.debug(message); // See Firebug console.
            return false;
        }

        // Set given properties on domNode.
        @JS_NS@._base.proto._extend(domNode, props, false);

	// Set functions.
        domNode.clearAllHighlight = @JS_NS@._html.tree.clearAllHighlight;
        domNode.clearHighlight = @JS_NS@._html.tree.clearHighlight;
        domNode.expandCollapse = @JS_NS@._html.tree.expandCollapse;
        domNode.expandTurner = @JS_NS@._html.tree.expandTurner;
        domNode.findContainingTreeNode = @JS_NS@._html.tree.findContainingTreeNode;
        domNode.findNodeByTypeAndProp = @JS_NS@._html.tree.findNodeByTypeAndProp;
        domNode.getCookieValue = @JS_NS@._html.tree.getCookieValue;
        domNode.getHighlightTreeBgColor = @JS_NS@._html.tree.getHighlightTreeBgColor;
        domNode.getHighlightTreeTextColor = @JS_NS@._html.tree.getHighlightTreeTextColor;
        domNode.getNormalTreeTextColor = @JS_NS@._html.tree.getNormalTreeTextColor;
        domNode.getParentTreeNode = @JS_NS@._html.tree.getParentTreeNode;
        domNode.getSelectedTreeNode = @JS_NS@._html.tree.getSelectedTreeNode;
        domNode.getTree = @JS_NS@._html.tree.getTree;
        domNode.highlight = @JS_NS@._html.tree.highlight;
        domNode.highlightParent = @JS_NS@._html.tree.highlightParent;
        domNode.isAnHref = @JS_NS@._html.tree.isAnHref;
        domNode.isTreeHandle = @JS_NS@._html.tree.isTreeHandle;
        domNode.onTreeNodeClick = @JS_NS@._html.tree.onTreeNodeClick;
        domNode.selectTreeNode = @JS_NS@._html.tree.selectTreeNode;
        domNode.setCookieValue = @JS_NS@._html.tree.setCookieValue;
        domNode.treecontent_submit = @JS_NS@._html.tree.treecontent_submit;
        domNode.treeNodeIsExpanded = @JS_NS@._html.tree.treeNodeIsExpanded;
        domNode.unhighlightParent = @JS_NS@._html.tree.unhighlightParent;
        domNode.updateHighlight = @JS_NS@._html.tree.updateHighlight;

        return true;
    },

    /**
     * Set cookie.
     *
     * @param {String} cookieName
     * @param {String} val
     * @return {boolean} true if successful; otherwise, false.
     */
    setCookieValue: function(cookieName, val) {

	/*
	Fix for bug 6476859 :On initial visit to page, 
        getting selected node returns value from previous 
	session.
	The cookie value should be stored with the global path 
        so that it is applicable for all pages on site.
	*/

	document.cookie = cookieName + "=" + val +";path=/;";
        return true;
    },

    /**
     * Get cookie.
     *
     * @param {String} cookieName
     * @return {String} The cookie value.
     */
    getCookieValue: function(cookieName) {
        var docCookie = document.cookie;
        var pos= docCookie.indexOf(cookieName+"=");

        if (pos == -1) {
            return null;
        }

        var start = pos+cookieName.length+1;
        var end = docCookie.indexOf(";", start );

        if (end == -1) {
            end= docCookie.length;
        }
        return docCookie.substring(start, end);
    },

    /**
     * This function expands or collapses the given tree node.  It expects the
     * source of the given event object (if supplied) to be a tree handle
     * image.  It will change this image to point in the correct direction
     * (right or down).  This implementation depends on the tree handle image
     * names including "tree_handleright" and "tree_handledown" in them.
     * Swapping "right" and "down" in these names must change the handle
     * direction to right and down respectively.
     *
     * @param {Node} treeNode
     * @param {String} imageId
     * @param {Event} event
     * @return {boolean} true if successful; otherwise, false.
     */
    expandCollapse: function(treeNode, imageId, event) {
        var tree = this.getTree(treeNode);
        var childNodes = document.getElementById(treeNode.id+"_children");
        if (childNodes) {
            // Get the event source
            if (!event) {
                event = window.event;
            }
            
            var elt = document.getElementById(imageId);
            
            // get the image widget to compare the actual icon values
            // as opposed to checking the name of the final rendered image.
            
            var imgWidget = @JS_NS@.widget.common.getWidget(imageId);
            var imgProps = imgWidget.getProps();
            var nodeIcon = imgProps.icon;
            
            // First, unhighlight the parent if applicable
            this.unhighlightParent(this.getSelectedTreeNode(tree.id));

            // Change the style to cause the expand / collapse & switch the image
            var display = childNodes.style.display;
            
            if (display == "none") {
                childNodes.style.display = "block";
                if (nodeIcon == "TREE_HANDLE_RIGHT_MIDDLE") {
                    nodeIcon = "TREE_HANDLE_DOWN_MIDDLE";    
                } else if (nodeIcon == "TREE_HANDLE_RIGHT_TOP") {
                    nodeIcon = "TREE_HANDLE_DOWN_TOP";
                } else if (nodeIcon == "TREE_HANDLE_RIGHT_LAST") {
                    nodeIcon = "TREE_HANDLE_DOWN_LAST";
                } else if (nodeIcon == "TREE_HANDLE_RIGHT_TOP_NOSIBLING") {
                    nodeIcon = "TREE_HANDLE_DOWN_TOP_NOSIBLING";
                }
            } else {
                childNodes.style.display = "none";
                if (nodeIcon == "TREE_HANDLE_DOWN_MIDDLE" ) {
                    nodeIcon = "TREE_HANDLE_RIGHT_MIDDLE";    
                } else if (nodeIcon == "TREE_HANDLE_DOWN_TOP" ) {
                    nodeIcon = "TREE_HANDLE_RIGHT_TOP";
                } else if (nodeIcon == "TREE_HANDLE_DOWN_LAST" ) {
                    nodeIcon = "TREE_HANDLE_RIGHT_LAST";
                } else if (nodeIcon == "TREE_HANDLE_DOWN_TOP_NOSIBLING") {
                    nodeIcon = "TREE_HANDLE_RIGHT_TOP_NOSIBLING" ;
                }
            }
            // update the image property to reflect the icon change
            imgWidget.setProps({icon: nodeIcon});

            // Last, update the visible parent of the selected node if now hidden
            this.highlightParent(this.getSelectedTreeNode(tree.id));
        }
        return true;
    },

    /**
     * This function returns the Tree for the given TreeNode.  From
     * a DOM point of view, the tree directly contains all its children
     * (excluding *_children div tags.  This will return the first
     * parentNode that is a div w/ an id != "*_children".
     *
     * @param {Node} treeNode
     * @return {Node} The tree for the given TreeNode.
     */
    getTree: function(treeNode) {
        var tree = treeNode.parentNode;
        var SUFFIX = new String("_children");

        while (tree) {
            // Ignore all div's ending w/ SUFFIX
            if ((tree.nodeName == "DIV")
                    && (tree.id.substr(tree.id.length-SUFFIX.length) != SUFFIX)) {
		break;
            }
            tree = tree.parentNode;
        }
        return tree;
    },

    /**
     * This method handles TreeNode onClick events.  It takes the TreeNode
     * &lt;div&gt; object that was clicked on in order to process the
     * highlighting changes that are necessary.  This object may be obtained by
     * calling <code>getElementById("&lt;TreeNode.getClientId()&gt;")</code>.
     * If this function is invoked from the TreeNode &lt;div&gt; object itself
     * (as is the case when this method is implicitly called), the TreeNode
     * object is simply the <code>this</code> variable.
     *
     * @param {Node} treeNode
     * @param {String} imageId
     * @param {Event} event
     * @return {boolean} true if successful; otherwise, false.
     */
    onTreeNodeClick: function(treeNode, imageId, event) {
        // Check for Tree Handles
        // The handle image and its surrounding area represents the 
        // handler section of the tree node. Clicking on this area
        // alone should cause the node to toggle.
        if (this.isTreeHandle(event)) {
            this.expandCollapse(treeNode, imageId, event);
            return true;
        }

        // Make sure they clicked on an href
        if (!this.isAnHref(event)) {
            // Do nothing
            return true;
        }

        // If we're here, we should select the TreeNode
        return this.selectTreeNode(treeNode.id);
    },

    /**
     * This function may be used to select the given TreeNode. 
     * It will clear the previous TreeNode and select the 
     * given one. The parameter passed to the function is
     * the id of the currently selected treeNode - the
     * value of the tree component from a JSF perspective.
     * This node may not be availbe in the DOM tree as the
     * user may have clicked on the expanded parent node
     * and closed it. In this case the parent node needs
     * to show up in bold.
     *
     * @param {String} treeNodeId
     * @return {boolean} true if successful; otherwise, false.
     */
    selectTreeNode: function(treeNodeId) {
        // Find the top of the tree
	var treeNode = document.getElementById(treeNodeId);

	if (treeNode) {
            var tree = this.getTree(treeNode);

            // Clear the old highlighting
            this.clearAllHighlight(tree.id);

            // Mark the node as selected
            this.setCookieValue(tree.id+"-hi", treeNode.id);

            // first highlight is as a parent
            // when the left frame loads the nodes will 
            // be hightlighted correctly
            this.highlightParent(treeNode);
            this.highlight(treeNode);

            // onClick handler should proceed with hyperlink
            return true;
	} else {
	    // get the parent node ID and highlight the parent.
	    // var arr = treeNodeId.split(":");
	    // var lastBitOfId = arr[arr.length - 1].length;
	    // var lastIndex = treeNodeId.length - lastBitOfId.length;
	    var lastIndex = treeNodeId.lastIndexOf(":");
	    parentNodeId = treeNodeId.substr(0, lastIndex);
	    var parentNode = 
		document.getElementById(parentNodeId);
	    if (parentNode) {	
	        parentNode.style.fontWeight = "bold";
	    }
	    return false;
	}
    },

    /**
     * This function returns the selected TreeNode given the treeId of the
     * Tree.
     *
     * @param {String} treeId
     * @return {Node}The selected TreeNode.
     */
    getSelectedTreeNode: function(treeId) {
        var id = this.getCookieValue(treeId+"-hi");
        if (id) {
            return document.getElementById(id);
        }
        return null;
    },

    /**
     * Clear all highlighted nodes.
     *
     * @param {String} cookieId
     * @return {boolean} true if successful; otherwise, false.
     */
    clearAllHighlight: function(cookieId) {
        // Clear
        var selectedNode = this.getSelectedTreeNode(cookieId);
        this.clearHighlight(selectedNode);
        this.setCookieValue(cookieId+"-hi", "");

        // FIXME: Fix this...
        // this.clearHighlight(document.getElementById(currentHighlightParent));
        return true;
    },

    /**
     * Clear highlighted node
     *
     * @param {Node} node
     * @return {boolean} true if successful; otherwise, false.
     */
    clearHighlight: function(node) {
        if (node) {
	    node.className = 
                @JS_NS@.theme.common.getClassName("TREE_ROW");
        }
        return true;
    },

    /**
     * This function determines if the event source was a tree handle image.
     * This implementation depends on the tree handle image file name
     * containing "tree_handle" and no other images containing this
     * string.
     *
     * @param {Event} event
     * @return {boolean} true if event was generated by tree handle.
     */
    isTreeHandle: function(event) {
        if (!event) {
            event = window.event;
            if (!event) {
                return false;
            }
        }
        var elt = (event.target) ? event.target : event.srcElement;

        // Ignore Tree Handles b/c they should not update highlighting
        
        if (elt.nodeName == "IMG") {
            var imgWidget = @JS_NS@.widget.common.getWidget(elt.id);
            var imgProps = imgWidget.getProps();
            var nodeIcon = imgProps.icon;
            if (nodeIcon.indexOf("TREE_HANDLE_") != -1) {
                return true;
            }
        } else if (elt.nodeName == "A") {
            // User might have been pressing enter around an image.
            // Note: I have never managed to get control to come here.
            
            aID = elt.id;
            var lastIndex = aID.lastIndexOf("_handle");
            if (lastIndex == -1) {
                return false;
            }
            var result = aID.substring(lastIndex, aID.length - 1);
            if (result == "_handle") {
                return true; 
            }
        }
        // Not a tree handle
        return false;
    },

    /**
     * This method checks to see if the event.target is an href, or if any of
     * the parent nodes which contain it is an href.  To be an href, it must be
     * an "A" tag with an "href" attribute containing atleast 4 characters.
     * (Note: Browsers will add on the protocol if you supply a relative URL
     * such as one starting with a '#', '/', or filename).
     *
     * @param {Event} event
     * @return {boolean} true if event was generated by a link.
     */
    isAnHref: function(event) {
        if (!event) {
            event = window.event;
            if (!event) {
                return false;
            }
        }
        var elt = (event.target) ? event.target : event.srcElement;

        // Look for parent href
        while (elt != null) {
            if (elt.nodeName == "A") {
                // Creates a String containing the url
                var url = new String(elt);
                if (url.length > 4) {
                    // All URLs are atleast this long
                    return true;
                }
            }
            elt = elt.parentNode;
        }
        // Not an href
        return false;
    },

    /**
     * This function updates the highlighting for the given Tree client id.
     * This function provides a way to restore the highlighting when a Tree is
     * reloaded in a window (necessary each page load).
     *
     * @param {String} cookieId
     * @return {boolean} true if successful; otherwise, false.
     */
    updateHighlight: function(cookieId) {
        var selNode = this.getSelectedTreeNode(cookieId);
        this.highlight(selNode);

        // FIXME: This doesn't work if the TreeNode element doesn't exist 
        // (which is the case for the server-side tree)
        return this.highlightParent(selNode);
    },

    /**
     * This function highlights the given <code>TreeNode</code>.  The
     * <code>obj</code> passed in is actually the &lt;div&gt; around the html
     * for the <code>TreeNode</code> and may be obtained by calling
     * <code>getElementById("&lt;TreeNode.getClidentId()&gt;")</code>.
     *
     * @param {Node} node
     * @return {boolean} true if successful; otherwise, false.
     */
    highlight: function(node) {
        if (node) {
	    node.className = 
                @JS_NS@.theme.common.getClassName("TREE_SELECTED_ROW");
            return true;
        }
        return false;
    },

    /**
     * This function finds the handler image ICON associated with a given 
     * tree node. The ICON value is used to identify if the node in 
     * question is expanded or not. This is a private function and should
     * not be used by developers on the client side.
     *
     * @param {Node} node
     * @return {boolean} true if the node has an image whose ICON 
     *        indicates the node is expanded.
     */
    findNodeByTypeAndProp: function(node) {
        if (node == null) {        
            return true;
        }
        // First check to see if node is a handler image.
        // Then check if it is of the right type. "RIGHT" icon
        // type indicates the node is not expanded.
        if (node.nodeName == "IMG") {
        
            var imgWidget = @JS_NS@.widget.common.getWidget(node.id);
            var imgProps = imgWidget.getProps();
            var nodeIcon = imgProps.icon;

            if ((nodeIcon == "TREE_HANDLE_RIGHT_MIDDLE") ||
                (nodeIcon == "TREE_HANDLE_RIGHT_TOP") ||
                (nodeIcon == "TREE_HANDLE_RIGHT_LAST") ||
                (nodeIcon == "TREE_HANDLE_RIGHT_TOP_NOSIBLING")) {
                
                return false;
                
            } else if ((nodeIcon == "TREE_HANDLE_DOWN_MIDDLE") ||
                (nodeIcon == "TREE_HANDLE_DOWN_TOP") ||
                (nodeIcon == "TREE_HANDLE_DOWN_LAST") ||
                (nodeIcon == "TREE_HANDLE_DOWN_TOP_NOSIBLING")) {
                
                return true;
            }
        }        
        // Not what we want, walk its children if any
        // Return true for when null conditions arise.
        var nodeList = node.childNodes;
        if (!nodeList || (nodeList.length == 0)) {
            return true;
        }
        var result;
        for (var count = 0; count<nodeList.length; count++) {
            // Recurse
            result = this.findNodeByTypeAndProp(nodeList[count]);
            if (result) {
                // Propagate the result
                return result;
            }
        }
        // Not found
        return true;
    },

    /**
     * This function determines if the given TreeNode is expanded.  It returns
     * <code>true</code> if it is, <code>false</code> otherwise.
     *
     * @param {Node} treeNode
     * @return {boolean} true if TreeNode is expanded.
     */
    treeNodeIsExpanded: function(treeNode) {
        // Find the div containing the handler images for this TreeNode row
        // and pass it to a function that looks for the right image within
        // the div and returns true if the image has been found and is of the
        // type that indicates the node is expanded, false otherwise.
        var node = document.getElementById(treeNode.id + "LineImages");
        return this.findNodeByTypeAndProp(node);
        
    },

    /**
     * This function returns the parent TreeNode of the given TreeNode.
     *
     * @param {Node} treeNode
     * @return {Node} The parent TreeNode.
     */
    getParentTreeNode: function(treeNode) {
        // Get the parent id
        var parentId = treeNode.parentNode.id;
        var childrenIdx = parentId.indexOf("_children");
        if (childrenIdx == -1) {
            return null;
        }
        // This is really a peer div id to what we really want... remove _children
        parentId = parentId.substring(0, childrenIdx);
        // Return the parent TreeNode
        return document.getElementById(parentId);
    },

    /**
     * Unhighlight parent node.
     *
     * @param {Node} childNode
     * @return {boolean} true if successful; otherwise, false.
     */
    unhighlightParent: function(childNode) {
        if (!childNode) {
            return false;
        }

        // First find the parent node and make sure it is collapsed (we don't
        // highlight parent nodes when the selected node is visible)
        var parentNode = this.getParentTreeNode(childNode);
        var highlight = null;
        while (parentNode != null) {
            if (!this.treeNodeIsExpanded(parentNode)) {
                highlight = parentNode;
            }
            parentNode = this.getParentTreeNode(parentNode);
        }
        if (highlight) {
            highlight.style.fontWeight = "normal";
        }
        return true;
    },

    /**
     * Highlight parent node.
     *
     * @param {Node} childNode
     * @return {boolean} true if successful; otherwise, false.
     */
    highlightParent: function(childNode) {
        if (!childNode) {
            return false;
        }

        // First find the parent node and make sure it is collapsed (we don't
        // highlight parent nodes when the selected node is visible)
        var parentNode = this.getParentTreeNode(childNode);
        var highlight = null;
        while (parentNode != null) {
            if (!this.treeNodeIsExpanded(parentNode)) {
                highlight = parentNode;
            }
            parentNode = this.getParentTreeNode(parentNode);
        }
        if (highlight) {
            highlight.style.fontWeight = "bold";
        }
        return true;
    },

    /**
     * Get normal tree text color.
     *
     * @return {String} The text color.
     */
    getNormalTreeTextColor: function() {
        return "#003399";
    },

    /**
     * Get highlight background color.
     *
     * @return {String} The background color.
     */
    getHighlightTreeBgColor: function() {
        return "#CBDCAF";  // ~greenish color
    },

    /**
     * Get highlight tree text color.
     *
     * @return {String} The text color.
     */
    getHighlightTreeTextColor: function() {
        return "#000000";  // black
    },

    /**
     * Returns the TreeNode that contains the given link. This assumes the link
     * is a direct child of the node.
     *
     * @param {Node} link
     * @return {Node} The TreeNode containing the given link.
     */
    findContainingTreeNode: function(link) {     
        var linkId = link.id;
        var nodeId = linkId.substring(0, linkId.lastIndexOf(":"));
        return document.getElementById(nodeId);
    },

    /**
     * If the Tree's expandOnSelect property is true, this method is called to 
     * expand the turner of the tree node with the given labelLink.
     *
     * @param {Node} labelLink
     * @param {String} turnerId
     * @param {String} imageId
     * @param {boolean} isClientSide
     * @param {Event} event
     * @return {boolean} true if successful; otherwise, false.
     */
    expandTurner: function(labelLink, turnerId, imageId, isClientSide, event) {
        var labelLinkId = labelLink.id;
	var formId = labelLinkId.substring(0, labelLinkId.indexOf(":"));
	var node = this.findContainingTreeNode(labelLink);
	var turnerLink = document.getElementById(turnerId); 

	if (turnerLink == null) {
            return false;
	}
	if (!this.treeNodeIsExpanded(node)) {
            // folder is currently closed, expand it
            if (isClientSide) {
		this.expandCollapse(node, imageId, event);      
            } else {
		turnerLink.onclick();
            }    
        }
        return true;
    },

    /**
     * When the tree node link has an associated action, this method should
     * be called to ensure selection highlighting and (if necessary) node 
     * expansion occurs.
     * <p>
     * If the developer specifies the content facet for a given TreeNode, he 
     * should call this function from his facet hyperlink's onClick.
     * </p>
     *
     * @param {String} nodeId
     * @return {boolean} true if successful; otherwise, false.
     */
    treecontent_submit: function(nodeId) {
	if (nodeId == null) {
            return false;
        }
	var node = document.getElementById(nodeId);
	var tree = this.getTree(node);

	// update the current selection
	this.selectTreeNode(node.id);

	// set a cookie that the Tree's decode method will 
	// inspect and expand the corresponding node if necessary
	return this.setCookieValue(tree.id + "-expand", nodeId);
    }
};
