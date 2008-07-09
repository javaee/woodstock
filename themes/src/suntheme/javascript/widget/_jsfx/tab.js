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
 * Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 */

@JS_NS@._dojo.provide("@JS_NS@.widget._jsfx.tab");

@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget._jsfx.common");
@JS_NS@._dojo.require("@JS_NS@.widget._jsfx.dynaFaces");
@JS_NS@._dojo.require("@JS_NS@.widget.tab");
@JS_NS@._dojo.require("@JS_NS@.theme.common");

/**
 * @class This class contains functions to obtain data asynchronously using JSF
 * Extensions as the underlying transfer protocol.
 * @static
 */
@JS_NS@.widget._jsfx.tab = {
    /**
     * This function is used to process the load event.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The HTML element Id.
     * @config {String} endTopic The event topic to publish.
     * @config {String} execute The string containing a comma separated list 
     * of client ids against which the execute portion of the request 
     * processing lifecycle must be run.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _processLoadEvent: function(props) {
        if (props == null) {
           return false;
        }

        // Dynamic Faces requires a DOM node as the source property.
        var domNode = document.getElementById(props.id);

        // No JSF component/renderer yet.  Just fake it for now
        setTimeout(function() {
            var w = @JS_NS@.widget.common.getWidget(props.id);
            if (props.endTopic) {
               @JS_NS@._dojo.publish(props.endTopic, [props]);
            }        
        }, 1000);
/*
        // Generate AJAX request using the JSF Extensions library.
        DynaFaces.fireAjaxTransaction(
            (domNode) ? domNode : document.forms[0], {
            execute: (props.execute) ? props.execute : props.id,
            closure: {
                endTopic: props.endTopic
            },
            render: props.id,
            replaceElement: @JS_NS@.widget._jsfx.tab._endLoadCallBack,
            xjson: {
                id: props.id,
                event: "load"
            }
        });
*/
        return true;
    },

    /**
     * This function is a callback to respond to the end of the load request.
     *
     * @param {String} id The widget id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to the Ajax transaction.
     * @param {Object} xjson The xjson argument provided to the Ajax transaction.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _endLoadCallBack: function(id, content, closure, xjson) {
        if (id == null) {
            return false;
        }

        var props = {"id": id};
        var widget = @JS_NS@.widget.common.getWidget(id);

        if (content != null) {
            // Parse JSON text.
            props = @JS_NS@.json.parse(content);
        
            // Update tab widget
            widget.setProps(props);
        }
     
        // Publish an event for custom AJAX implementations to listen for.
        // This must be done even if there is no content in the response because
        // there are presentation characteristics that need to be turned off
        // after the load regardless of the response.
        if (closure.endTopic) {
           @JS_NS@._dojo.publish(closure.endTopic, [props]);
        }        
        return true;
    }
 }
 
 // Listen for Dojo Widget events.
@JS_NS@._dojo.subscribe(@JS_NS@.widget.tab.event.load.beginTopic,
    @JS_NS@.widget._jsfx.tab, "_processLoadEvent");
@JS_NS@._dojo.subscribe(@JS_NS@.widget.tab.event.refresh.beginTopic,
    @JS_NS@.widget._jsfx.common, "_processRefreshEvent");
