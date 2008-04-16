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

@JS_NS@._dojo.provide("@JS_NS@._base.dnd");

@JS_NS@._dojo.require("@JS_NS@._dojo.dnd"); // Replaced by build.
@JS_NS@._dojo.require("@JS_NS@._dojo.dnd.Manager");
@JS_NS@._dojo.require("@JS_NS@._dojo.dnd.Source");

/**
 * This function is used to construct a dnd manager.
 * 
 * @name @JS_NS@._base.dnd.Manager
 * @extends @JS_NS@._dojo.dnd.Manager
 * @class This class supports additional features of Woodstock drag and drop.
 * @constructor
 * @private
 */
@JS_NS@._dojo.declare("@JS_NS@._base.dnd.Manager",
    @JS_NS@._dojo.dnd.Manager);

// This function is not public and should not appear in the jsDoc.
/** @ignore */
@JS_NS@._base.dnd.Manager.prototype.startDrag = function () {
    this.inherited("startDrag", arguments);
    return this._startDrag();
}

/** 
 * Processes start drag event to insert dragging styles.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@._base.dnd.Manager.prototype._startDrag = function(source, nodes, copy) {
    @JS_NS@._dojo.forEach(nodes,
        function(node) {
            @JS_NS@._dojo.addClass(node, "dojoDndWebuiItemDragged");
        }
    );
    return true;
};

// This function is not public and should not appear in the jsDoc.
/** @ignore */
@JS_NS@._base.dnd.Manager.prototype.stopDrag = function () {
    this.inherited("stopDrag", arguments);
    return this._stopDrag();
}

/**
 * Processes stop drag event to cleanup dragging styles.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@._base.dnd.Manager.prototype._stopDrag = function() {
    @JS_NS@._dojo.forEach(this.nodes,
        function(node) {
            @JS_NS@._dojo.removeClass(node, "dojoDndWebuiItemDragged");
        }
    );
    return true;
};

/**
 * This function is used to construct a dnd source.
 *
 * @name @JS_NS@._base.dnd.Source
 * @extends @JS_NS@._dojo.dnd.Source
 * @class This class extends @JS_NS@._dojo.dnd.Source to support additional features of
 * Woodstock drag and drop.
 * @constructor
 * @param {Node} node DOM node
 * @param {Object} props Key-Value pairs of properties.
 * @config {boolean} isSource Can be used as a DnD source, if true; assumed to
 * be "true" if omitted.
 * @config {Array} accept List of accepted types (text strings) for a target; 
 * assumed to be ["text"] if omitted.
 * @config {boolean} horizontal A horizontal container, if true, vertical 
 * otherwise or when omitted.
 * @config {boolean} copyOnly Always copy items, if true, use a state of Ctrl 
 * key otherwise.
 * @config {boolean} skipForm Don't start the drag operation, if clicked on 
 * form elements.
 * @config {boolean} singular Allows selection of only one element, if true.
 * @config {Function} creator Function A creator function, which takes a data
 * item, and returns an object like that: {node: newNode, data: usedData, type: 
 * arrayOfStrings}.
 * @config {boolean} _skipStartup Skip startup(), which collects children, for
 * deferred initialization (used in the markup mode).
 * @config {Function} onDropFunction User defined onDrop function with 
 * signature function(source, nodes, copy){..}.
 * @private
 */
@JS_NS@._dojo.declare("@JS_NS@._base.dnd.Source",
        @JS_NS@._dojo.dnd.Source, {
    constructor: function(node, props) {
        // Replace the drag manager
        if (@JS_NS@._dojo.dnd._manager == null) {
            @JS_NS@._dojo.dnd._manager = 
                new @JS_NS@._base.dnd.Manager();
        }

        // Disable source functionality
        if (props.isSource && props.isSource == false) {
            this.isSource = false;
            @JS_NS@._dojo.removeClass(node, "dojoDndSource");
        }

        // Set user's onDrop function
        this.onDropFunction = props.onDropFunction 
            ? props.onDropFunction : null;

        return true;
    }
});

// This function is not public and should not appear in the jsDoc.
/** @ignore */
@JS_NS@._base.dnd.Manager.prototype.addItem = function () {
    this.inherited("addItem", arguments);
    return this._addItem();
}

/**
 * This helper method will create a node using _normalizedCreator (which in turn 
 * will use user's creator function, if supplied) and will add it to 
 * this source container. This method allows:
 * <p><pre>
 * - explicitely provide drag item type and data to overcome limitation of dojo
 *   _normalizedCreator
 * - unlike another helper function here ( _makeNodeDraggable) allow to add 
 *   items to the container uniformly, wrapping the type of item added ( i.e. 
 *   nested items may be span, div, img, etc.)
 * </pre></p>
 * @param {String} nodeContent A fragment that will be inserted into a newly 
 * created draggable node.
 * @param {Array} dragType An array of types with no spaces ( TRIMMED!).
 * @param {Object} dragData Payload data to be associated with the drag item.
 * @return {Node} The created node.
 * @private
 */
@JS_NS@._base.dnd.Source.prototype._addItem = function(nodeContent,
        dragType, dragData) {
    var t = this._normalizedCreator([nodeContent]);        
    this.setItem(t.node.id, {
        data: dragData, 
        type: dragType   
    });
    this.parent.appendChild(t.node);
    return t.node;
};

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
 * @private
 */
@JS_NS@._base.dnd.Source.prototype._makeNodeDraggable = function(node, dragType, dragData) {
    if (@JS_NS@._dojo.byId(node)) {
        node = @JS_NS@._dojo.byId(node);  
    } else { 
        if (!node.nodeType) {
            // this is not a DOM node
            return false;
        }
    }
    if (!node.id) {
        node.id = @JS_NS@._dojo.dnd.getUniqueId();    
    }
    var type = dragType ? dragType : node.getAttribute("dndType");
    if (!type) {
        type = this.DEFAULT_TYPES;
    }
    type = (type instanceof Array) ? type : type = type.split(',');
    @JS_NS@._dojo.forEach(type, this._trim);

    var data = dragData ? dragData : node.getAttribute("dndData");
    this.setItem(node.id, {
        data: data ? data : node.innerHTML,
        type: type  
    });
    this._addItemClass(node, "");
    return true;
};

// This function is not public and should not appear in the jsDoc.
/** @ignore */
@JS_NS@._base.dnd.Manager.prototype.markupFactory = function () {
    return this._markupFactory();
}

/** 
 * Makes use of @JS_NS@._base.dnd.Source for markup processing.
 *
 * @param {Object} props Key-Value pairs of properties.
 * @param {Node} node The DOM node.
 * @return {@JS_NS@._base.dnd.Source} The Source object.
 * @private
 */
@JS_NS@._base.dnd.Source.prototype._markupFactory = function(props, node) {
    props._skipStartup = true;
    return new @JS_NS@._base.dnd.Source(node, props);
};

// This function is not public and should not appear in the jsDoc.
/** @ignore */
@JS_NS@._base.dnd.Manager.prototype.onDndDrop = function () {
    this.inherited("onDndDrop", arguments);
    return this._onDndDrop();
}

/** 
 * Processes dndDrop event by providing transparency treatment for source
 * elements.
 *
 * @param (Object) source The drag source.
 * @param (Object) nodes Array of nodes to be dropped.
 * @param (boolean) copy A flag indicating copy is desired.
 * @return {boolean} The result of user's onDropFunction.
 * @private
 */
@JS_NS@._base.dnd.Source.prototype._onDndDrop = function(source, nodes, copy) {   
    // We have to remove class onDndDrop here as well as in mgr
    // because _onDndDrop is called before mgr.stopDrag, and transparency 
    // needs to be removed before clone is made.
    @JS_NS@._dojo.forEach(nodes,
        function(node) {
            @JS_NS@._dojo.removeClass(node, "dojoDndWebuiItemDragged");
        }
    );

    var ret = true;
    if (this.onDropFunction && 
        this != source && 
        this.containerState == "Over"
        ) {
        try {
            ret = this.onDropFunction(source, nodes, copy);
        } catch (err) {}
    }
    return ret; // Return from this method is actually ignored.
};

/**
 * This creator-wrapper function ensures that user provided creator function
 * results in providing all neccessary information for the newly created node.
 * Specifically, if type is not provided, it sets a default type on the item.
 *
 * @param (Object) data data to be used for node creation.
 * @param (String) hint hint that takes value of "avatar" when avatar is 
 * created, null otherwise.
 * @return {Node} The created node.
 * @private
 */ 
@JS_NS@._base.dnd.Source.prototype._normalizedCreator = function(data, hint) {
    // Adds all necessary data to the output of user-supplied creator function.
    var t = (this.creator ? this.creator : this.defaultCreator)(data, hint);
    if (!@JS_NS@._dojo.isArray(t.type)) {
        t.type = this.DEFAULT_TYPES;    
    }
    if (!t.node.id) {
        t.node.id = @JS_NS@._dojo.dnd.getUniqueId();    
    }
    @JS_NS@._dojo.addClass(t.node, "dojoDndItem");           
    return t;
};

/** 
 * Util method to trim the string. 
 * 
 * @param (String) str string to process.
 * @return {String} The trimmed string.
 * @private
 */    
@JS_NS@._base.dnd.Source.prototype._trim = function(str){ 
    // TODO make a String.prototype in common.js out of this.
    str = str.replace(/^\s\s*/, '').replace(/\s\s*$/, ''); 
    return str;
};
