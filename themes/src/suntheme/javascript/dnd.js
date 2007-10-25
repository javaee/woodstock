// dnd.js
//
// The contents of this file are subject to the terms
// of the Common Development and Distribution License
// (the License).  You may not use this file except in
// compliance with the License.
// 
// You can obtain a copy of the license at
// https://woodstock.dev.java.net/public/CDDLv1.0.html.
// See the License for the specific language governing
// permissions and limitations under the License.
// 
// When distributing Covered Code, include this CDDL
// Header Notice in each file and include the License file
// at https://woodstock.dev.java.net/public/CDDLv1.0.html.
// If applicable, add the following below the CDDL Header,
// with the fields enclosed by brackets [] replaced by
// you own identifying information:
// "Portions Copyrighted [year] [name of copyright owner]"
// 
// Copyright 2007 Sun Microsystems, Inc. All rights reserved.
//

dojo.provide("webui.@THEME@.dnd");

dojo.require("dojo.dnd.manager");
dojo.require("dojo.dnd.source");


/**
 * @name webui.@THEME@.dnd.Manager
 * @extends dojo.dnd.Manager
 * @class This class extends dojo.dnd.Manager to support additional features of
 * Woodstock drag and drop.
 */
dojo.declare("webui.@THEME@.dnd.Manager", dojo.dnd.Manager);

/** 
 * Processes startDrag event to insert dragging styles.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.dnd.Manager.prototype.startDrag = function(source, nodes, copy) {
    dojo.forEach(nodes,
        function(node) {
            dojo.addClass(node, "dojoDndWebuiItemDragged");
        }
    );
    return this.inherited("startDrag", arguments);    
}

/**
 * Processes stopDrag event to cleanup dragging styles.
 *
* @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.dnd.Manager.prototype.stopDrag = function() {
    dojo.forEach(this.nodes,
        function(node) {
            dojo.removeClass(node, "dojoDndWebuiItemDragged");
        }
    );
    return this.inherited("stopDrag", arguments);    
}

/**
 * @name webui.@THEME@.dnd.Source
 * @extends dojo.dnd.Source
 * @class This class extends dojo.dnd.Source to support additional features of
 * Woodstock drag and drop.
 * @constructor This function is used to construct a dnd source.
 * @param {Object} props Key-Value pairs of properties.
 * @config {boolean} [isSource] Can be used as a DnD source, if true; assumed to
 * be "true" if omitted.
 * @config {Array} [accept] List of accepted types (text strings) for a target; 
 * assumed to be ["text"] if omitted.
 * @config {boolean} [horizontal] A horizontal container, if true, vertical 
 * otherwise or when omitted.
 * @config {boolean} [copyOnly] Always copy items, if true, use a state of Ctrl 
 * key otherwise.
 * @config {boolean} [skipForm] Don't start the drag operation, if clicked on 
 * form elements.
 * @config {boolean} [singular] Allows selection of only one element, if true.
 * @config {Function} [creator] Function A creator function, which takes a data
 * item, and returns an object like that: {node: newNode, data: usedData, type: 
 * arrayOfStrings}.
 * @config {boolean} [_skipStartup] Skip startup(), which collects children, for
 * deferred initialization (used in the markup mode).
 * @config {Function} [onDropFunction] User defined onDrop function with 
 * signature function(source, nodes, copy){..}.
 */
dojo.declare("webui.@THEME@.dnd.Source", dojo.dnd.Source, {
    defaultTypes: [ "default" ], // default types for the source
    
    /**
     * Constructor
     *
     * @param {Object} node DOM node
     * @param {Object} props Key-Value pairs of properties as described above.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */  
    constructor: function(node, props) {
        // Replace the drag manager
        if (dojo.dnd._manager == null) {
            dojo.dnd._manager = new webui.@THEME@.dnd.Manager();
        }

        // Disable source functionality
        if (typeof props.isSource != "undefined" && props.isSource == false) {
            this.isSource = false;
            dojo.removeClass(this.node, "dojoDndSource");
        }

        // Set user's onDrop function
        this.onDropFunction  = props.onDropFunction 
            ? props.onDropFunction : null;

        return true;
    }
});

/**
 * This helper method will create a node using _normalizedCreator (which in turn 
 * will use user's creator function, if supplied) and will add it to 
 * this source container. This method allows:
 * <p><pre>
 * - explicitely provide drag item type and data to overcome limitation of dojo
 *   _normalizedCreator
 * - unlike another helper function here ( makeNodeDraggable) allow to add 
 *   items to the container uniformly, wrapping the type of item added ( i.e. 
 *   nested items may be span, div, img, etc.)
 * </pre></p>
 * @param {String} nodeContent A fragment that will be inserted into a newly 
 * created draggable node.
 * @param {Array} dragType An array of types with no spaces ( TRIMMED!).
 * @param {Object} dragData Payload data to be associated with the drag item.
 * @return {Node} The created node.
 */
webui.@THEME@.dnd.Source.prototype.addItem = function(nodeContent, dragType, dragData) { 
    var t = this._normalizedCreator([nodeContent]);        
    this.setItem(t.node.id, {
        data: dragData, 
        type: dragType   
    });
    this.parent.appendChild(t.node);
    return t.node;
}

/**
 * Dojo implementation relies either on html markup to describe which items are
 * to be draggable or on insertNodes mehods that creates new nodes within a 
 * container. This function adds a programmatic way to make existing elements of
 * the container draggable.
 *
 * @param {Node} node DOM node or id of the element within this container to be
 * a draggable element.
 * @param (Array) dragType Array of types.
 * @param (String) dragData Data associated with dragItem.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.dnd.Source.prototype.makeNodeDraggable = function(node, dragType, dragData) {
    if (dojo.byId(node)) {
        node = dojo.byId(node);  
    } else { 
        if (!node.nodeType) {
            // this is not a DOM node
            return false;
        }
    }
    if (!node.id) {
        node.id = dojo.dnd.getUniqueId();    
    }
    var type = dragType ? dragType : node.getAttribute("dndType");
    if (!type) {
        type = this.DEFAULT_TYPES;
    }
    type = (type instanceof Array) ? type : type = type.split(',');
    dojo.forEach(type, this.trim);

    var data = dragData ? dragData : node.getAttribute("dndData");
    this.setItem(node.id, {
        data: data ? data : node.innerHTML,
        type: type  
    });
    this._addItemClass(node, "");
    return true;
}

/** 
 * Makes use of webui.@THEME.dnd.Source for markup processing.
 *
 * @param {Object} props Key-Value pairs of properties.
 * @param {Node} node The DOM node.
 * @return {webui.@THEME@.dnd.Source} The Source object.
 * @private
 */
webui.@THEME@.dnd.Source.prototype.markupFactory = function(props, node) {
    props._skipStartup = true;
    return new webui.@THEME@.dnd.Source(node, props);
}

/** 
 * Processes dndDrop event by providing transparency treatment for source
 * elements.
 *
 * @param (Object) source The drag source.
 * @param (Object) nodes Array of nodes to be dropped.
 * @param (boolean) copy A flag indicating copy is desired.
 * @return {boolean} The result of user's onDropFunction.
 */
webui.@THEME@.dnd.Source.prototype.onDndDrop = function(source, nodes, copy) {
    this.inherited("onDndDrop", arguments);
    
    // We have to remove class onDndDrop here as well as in mgr
    // because onDndDrop is called before mgr.stopDrag, and transparency 
    // needs to be removed before clone is made.
    dojo.forEach(nodes,
        function(node) {
            dojo.removeClass(node, "dojoDndWebuiItemDragged");
        }
    );

    var ret = true;
    if (this.onDropFunction && this != source) {
        try {
            ret = this.onDropFunction(source, nodes, copy);
        } catch (err) {}
    }
    return ret; // Return from this method is actually ignored.
}

/**
 * This creator-wrapper function ensures that user provided creator function
 * results in providing all neccessary information for the newly created node.
 * Specifically, if type is not provided, it sets a default type on the item.
 *
 * @param (Object) data data to be used for node creation.
 * @param (String) hint hint that takes value of "avatar" when avatar is 
 * created, null otherwise.
 * @return {Node} The created node.
 */ 
webui.@THEME@.dnd.Source.prototype._normalizedCreator = function(data, hint) {
    // Adds all necessary data to the output of user-supplied creator function.
    var t = (this.creator ? this.creator : this.defaultCreator)(data, hint);
    if (!dojo.isArray(t.type)) {
        t.type = this.DEFAULT_TYPES;    
    }
    if (!t.node.id) {
        t.node.id = dojo.dnd.getUniqueId();    
    }
    dojo.addClass(t.node, "dojoDndItem");           
    return t;
}

/** 
 * Util method to trim the string. 
 * 
 * @param (String) str string to process.
 * @return {String} The trimmed string.
 * @private
 */    
webui.@THEME@.dnd.Source.prototype.trim = function(str){ 
    // TODO make a String.prototype in common.js out of this.
    str = str.replace(/^\s\s*/, '').replace(/\s\s*$/, ''); 
    return str;
}
