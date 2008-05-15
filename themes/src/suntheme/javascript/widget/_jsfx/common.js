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

@JS_NS@._dojo.provide("@JS_NS@.widget._jsfx.common");

@JS_NS@._dojo.require("@JS_NS@.json");
@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget._jsfx.dynaFaces");

/**
 * @class This class contains functions to obtain data asynchronously using JSF
 * Extensions as the underlying transfer protocol.
 * @static
 * @private
 */
@JS_NS@.widget._jsfx.common = {
    /**
     * This function is used to process refresh events with Object literals.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The widget id.
     * @config {String} endTopic The event topic to publish.
     * @config {String} execute The string containing a comma separated list 
     * of client ids against which the execute portion of the request 
     * processing lifecycle must be run.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _processRefreshEvent: function(props) {
        if (props == null) {
            return false;
        }

        // Dynamic Faces requires a DOM node as the source property.
        var domNode = document.getElementById(props.id);

        // Generate AJAX request using the JSF Extensions library.
        DynaFaces.fireAjaxTransaction(
            (domNode) ? domNode : document.forms[0], {
            execute: (props.execute) ? props.execute : "none",
            closure: {
                endTopic: props.endTopic
            },
            render: props.id,
            replaceElement: @JS_NS@.widget._jsfx.common._refreshCallback,
            xjson: {
                id: props.id,
                event: "refresh"
            }
        });
        return true;
    },

    /**
     * This function is used to process state change events with Object literals.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The widget id.
     * @config {String} endTopic The event topic to publish.
     * @config {Object} props Key-Value pairs of widget properties to update.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _processStateEvent: function(props) {
        if (props == null) {
            return false;
        }

        // Dynamic Faces requires a DOM node as the source property.
        var domNode = document.getElementById(props.id);

        // Generate AJAX request using the JSF Extensions library.
        DynaFaces.fireAjaxTransaction(
            (domNode) ? domNode : document.forms[0], {
            execute: "none",
            closure: {
                endTopic: props.endTopic
            },
            render: props.id,
            replaceElement: @JS_NS@.widget._jsfx.common._stateCallback,
            xjson: {
                id: props.id,
                event: "state",
                props: props.props // Widget properties to update.
            }
        });
        return true;
    },

    /**
     * This function is used to process submit events with Object literals.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The widget id.
     * @config {String} endTopic The event topic to publish.
     * @config {String} execute The string containing a comma separated list 
     * of client ids against which the execute portion of the request 
     * processing lifecycle must be run.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _processSubmitEvent: function(props) {
        if (props == null) {
            return false;
        }

        // Dynamic Faces requires a DOM node as the source property.
        var domNode = document.getElementById(props.id);

        // Generate AJAX request using the JSF Extensions library.
        DynaFaces.fireAjaxTransaction(
            (domNode) ? domNode : document.forms[0], {
            execute: (props.execute) ? props.execute : props.id,
            closure: {
                endTopic: props.endTopic
            },
            render: props.id,
            replaceElement: @JS_NS@.widget._jsfx.common._submitCallback,
            xjson: {
                id: props.id,
                event: "submit"
            }
        });
        return true;
    }, 
   
    /**
     * This function is used to refresh widgets.
     *
     * @param {String} id The widget id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to the Ajax transaction.
     * @param {Object} xjson The xjson argument provided to the Ajax transaction.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _refreshCallback: function(id, content, closure, xjson) {
        if (id == null || content == null) {
            return false;
        }

        // Parse JSON text.
        var props = @JS_NS@.json.parse(content);

        // Add rows.
        var widget = @JS_NS@.widget.common.getWidget(id);
        widget.setProps(props);

        // Publish an event for custom AJAX implementations to listen for.
        if (closure.endTopic) {
            @JS_NS@._dojo.publish(closure.endTopic, [props]);
        }
        return true;
    },

    /**
     * This function is a callback to respond to the end of state request.
     * It will only publish submit end event without updating the widget itself.
     *
     * @param {String} id The widget id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to the Ajax transaction.
     * @param {Object} xjson The xjson argument provided to the Ajax transaction.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _stateCallback: function(id, content, closure, xjson) {
        if (id == null || content == null) {
            return false;
        }

        // Parse JSON text.
        var props = @JS_NS@.json.parse(content);
            
        // Publish an event for custom AJAX implementations to listen for.
        if (closure.endTopic) {
            @JS_NS@._dojo.publish(closure.endTopic, [props]);
        }
        return true;
    },

    /**
     * This function is a callback to respond to the end of submit request.
     * It will only publish submit end event without updating the widget itself.
     *
     * @param {String} id The widget id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to the Ajax transaction.
     * @param {Object} xjson The xjson argument provided to the Ajax transaction.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _submitCallback: function(id, content, closure, xjson) {
        if (id == null || content == null) {
            return false;
        }

        // Parse JSON text.
        var props = @JS_NS@.json.parse(content);
            
        // Publish an event for custom AJAX implementations to listen for.
        if (closure.endTopic) {
            @JS_NS@._dojo.publish(closure.endTopic, [props]);
        }
        return true;
    }
};
