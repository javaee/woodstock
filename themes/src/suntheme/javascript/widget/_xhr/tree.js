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


@JS_NS@._dojo.provide("@JS_NS@.widget._xhr.tree");

@JS_NS@._dojo.require("@JS_NS@.widget._xhr.common");
@JS_NS@._dojo.require("@JS_NS@.widget.tree");

/**
 * @class This class contains functions to obtain data asynchronously using JSF
 * Extensions as the underlying transfer protocol.
 * @static
 * @private
 */
@JS_NS@.widget._xhr.tree = {
    /**
     * This function is used to load child nodes of a given node asynchronously.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The HTML element Id.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _processSelectionEvent: function(props) {
        if (props == null) {
            return false;
        }
        
        if (props.submitSelection) {
            return true;
        }
        
        // Generate AJAX request.
        @JS_NS@.widget._xhr.common._doRequest({
            id: props.id,
            callback: @JS_NS@.widget._jsfx.tree._selectionCallback,
            xjson: {
                id: props.nodeId,
                nodeId: props.nodeId,
                event: "nodeSelected"
            }
        });
        return true;
    },
    
    /**
     * This function is used to load child nodes of a given node asynchronously.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The HTML element Id.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _processLoadEvent: function(props) {
        if (props == null) {
            return false;
        }
        
        // Dynamic Faces requires a DOM node as the source property.
        var domNode = document.getElementById(props.id);
        // Generate AJAX request.
        @JS_NS@.widget._xhr.common._doRequest({
            id: props.nodeId,
            callback: @JS_NS@.widget._jsfx.tree._loadContentCallback,
            xjson: {
                id: props.nodeId,
                nodeId: props.nodeId,
                rootId: props.id,
                event: "loadChildren"
            }
        });
        return true;
    },
    
    
    /**
     * This function is used to update tab with the loaded content.
     *
     * @param {String} elementId The HTML element Id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to DynaFaces.fireAjaxTransaction.
     * @param {Object} xjson The xjson argument provided to DynaFaces.fireAjaxTransaction.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _selectionCallback: function(id, content, closure, xjson) {
        if (id == null || content == null) {
            return false;
        }

        // Parse JSON text.
        var json = @JS_NS@.json.parse(content);

        // Set progress.
        var widget = @JS_NS@.widget.common.getWidget(id);
        //widget.setProps(json);

        // Publish an event for custom AJAX implementations to listen for.
        /*@JS_NS@._dojo.publish(@JS_NS@.widget.tree.event.nodeSelection.endTopic, 
          [{id: xjson.nodeId}]); */
        return true;
    },
    
    /**
     * This function is used to update tab with the loaded content.
     *
     * @param {String} elementId The HTML element Id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to DynaFaces.fireAjaxTransaction.
     * @param {Object} xjson The xjson argument provided to DynaFaces.fireAjaxTransaction.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _loadContentCallback: function(id, content, closure, xjson) {
        if (id == null || content == null) {
            return false;
        }

        // Parse JSON text.
        var json = @JS_NS@.json.parse(content);

        // update tree with newly loaded nodes.
        var widget = @JS_NS@.widget.common.getWidget(xjson.rootId);
        
        // get the children of the node which is to be loaded and add these
        // to the tree in the right place.
        
        var children = json.children;
        if (children) {
            widget.addNodes(children);
        }

        // Publish an event for custom AJAX implementations to listen for.
        // This will cause the parent node (whose children were just loaded)
        // to be visible.
        /*@JS_NS@._dojo.publish(@JS_NS@.widget.tree.event.load.endTopic, 
          [{id: xjson.nodeId}]); */
        return true;
    }
};

// Listen for tree node load event.
@JS_NS@._dojo.subscribe(@JS_NS@.widget.tree.event.load.beginTopic,
    @JS_NS@.widget._xhr.tree, "_processLoadEvent");

// Listen for tree node selection event.
@JS_NS@._dojo.subscribe(@JS_NS@.widget.tree.event.nodeSelection.beginTopic,
    @JS_NS@.widget._xhr.tree, "_processSelectionEvent");
    
// Listen for tree refresh and submit events.
@JS_NS@._dojo.subscribe(@JS_NS@.widget.tree.event.refresh.beginTopic,
    @JS_NS@.widget._xhr.common, "_processRefreshEvent");
@JS_NS@._dojo.subscribe(@JS_NS@.widget.tree.event.submit.beginTopic,
    @JS_NS@.widget._xhr.common, "_processSubmitEvent");

