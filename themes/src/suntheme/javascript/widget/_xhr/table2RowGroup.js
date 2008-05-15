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

@JS_NS@._dojo.provide("@JS_NS@.widget._xhr.table2RowGroup");

@JS_NS@._dojo.require("@JS_NS@.json");
@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget.table2RowGroup");
@JS_NS@._dojo.require("@JS_NS@.widget._xhr.common");

/**
 * @class This class contains functions to obtain data asynchronously using XHR
 * as the underlying transfer protocol.
 * @static
 * @private
 */
@JS_NS@.widget._xhr.table2RowGroup = {
    /**
     * This function is used to process scroll events with Object literals.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The HTML element Id.
     * @config {int} row The first row to be rendered.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _processScrollEvent: function(props) {
        if (props == null) {
            return false;
        }

        // Generate AJAX request.
        @JS_NS@.widget._xhr.common._doRequest({
            id: props.id,
            callback: @JS_NS@.widget._xhr.table2RowGroup._scrollCallback,
            xjson: {
                id: props.id,
                event: "scroll",
                execute: "none",
                first: props.first
            }
        });
        return true;
    },
    
    /**
     * This function is used to process scroll events with Object literals.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The HTML element Id.
     * @config {int} row The first row to be rendered.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _processSortEvent: function(props) {
        if (props == null) {
            return false;
        }

        // Generate AJAX request.
        @JS_NS@.widget._xhr.common._doRequest({
            id: props.id,
            callback: @JS_NS@.widget._xhr.table2RowGroup._sortCallback,
            xjson: {
                id: props.id,
                event: "sort",
                execute: "none",
                colId: props.table2colId,
                sortOrder: props.sortOrder
            }
        });
        return true;
    },

    /**
     * This function is used to update widgets.
     *
     * @param {String} id The HTML widget Id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to the Ajax transaction.
     * @param {Object} xjson The xjson header provided to the Ajax transaction.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _scrollCallback: function(id, content, closure, xjson) {
        if (id == null || content == null) {
            return false;
        }

        // Parse JSON text.
        var props = @JS_NS@.json.parse(content);

        // Reject duplicate AJAX requests.
        var widget = @JS_NS@.widget.common.getWidget(id);
        if (widget.first != xjson.first) {
            return false;
        }        
        // Add rows.
        widget.addRows(props.rows);

        // Publish an event for custom AJAX implementations to listen for.
        @JS_NS@._dojo.publish(
            @JS_NS@.widget.table2RowGroup.event.scroll.endTopic, [props]);
        return true;
    },

    /**
     * This function is used to update widgets.
     *
     * @param {String} id The HTML widget Id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to the Ajax transaction.
     * @param {Object} xjson The xjson header provided to the Ajax transaction.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _sortCallback: function(id, content, closure, xjson) {
        if (id == null || content == null) {
            return false;
        }

        // Parse JSON text.
        var props = @JS_NS@.json.parse(content);

        // Reject duplicate AJAX requests.
        var widget = @JS_NS@.widget.common.getWidget(id); 
        widget.setProps(props);
  
        // Publish an event for custom AJAX implementations to listen for.
        @JS_NS@._dojo.publish(
            @JS_NS@.widget.table2RowGroup.event.sort.endTopic, [props]);
        return true;
    }
};
    
// Listen for Widget events.
@JS_NS@._dojo.subscribe(@JS_NS@.widget.table2RowGroup.event.refresh.beginTopic,
    @JS_NS@.widget._xhr.common, "_processRefreshEvent");
@JS_NS@._dojo.subscribe(@JS_NS@.widget.table2RowGroup.event.scroll.beginTopic,
    @JS_NS@.widget._xhr.table2RowGroup, "_processScrollEvent");
@JS_NS@._dojo.subscribe(@JS_NS@.widget.table2RowGroup.event.pagination.next.beginTopic,
    @JS_NS@.widget._xhr.table2RowGroup, "_processScrollEvent");
@JS_NS@._dojo.subscribe(@JS_NS@.widget.table2RowGroup.event.sort.beginTopic,
    @JS_NS@.widget._xhr.table2RowGroup, "_processSortEvent");
