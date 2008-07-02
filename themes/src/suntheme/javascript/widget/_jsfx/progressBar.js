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

@JS_NS@._dojo.provide("@JS_NS@.widget._jsfx.progressBar");

@JS_NS@._dojo.require("@JS_NS@.json");
@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget._jsfx.common");
@JS_NS@._dojo.require("@JS_NS@.widget._jsfx.dynaFaces");
@JS_NS@._dojo.require("@JS_NS@.widget.progressBar");

/**
 * @class This class contains functions to obtain data asynchronously using JSF
 * Extensions as the underlying transfer protocol.
 * @static
 * @private
 */
@JS_NS@.widget._jsfx.progressBar =  {
    /**
     * This function is used to process progress events with Object literals.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The widget id.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _processProgressEvent: function(props) {
        if (props == null) {
            return false;
        }

        // Dynamic Faces requires a DOM node as the source property.
        var domNode = document.getElementById(props.id);

        // Generate AJAX request using the JSF Extensions library.
        DynaFaces.fireAjaxTransaction(
            (domNode) ? domNode : document.forms[0], {
            execute: props.id, // Need to decode hidden field.
            render: props.id,
            replaceElement: @JS_NS@.widget._jsfx.progressBar._progressCallback,
            xjson: {
                id: props.id,
                taskState: props.taskState,
                event: "progress"
            }
        });

        return true;
    },

    /**
     * This function is used to update progress.
     *
     * @param {String} id The widget id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to the Ajax transaction.
     * @param {Object} xjson The xjson argument provided to the Ajax transaction.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _progressCallback: function(id, content, closure, xjson) {
        if (id == null || content == null) {
            return false;
        }

        // Parse JSON text.
        var props = @JS_NS@.json.parse(content);

        // Set progress.
        var widget = @JS_NS@.widget.common.getWidget(id);
        widget.setProgress({
            failedStateText : props.failedStateText,
            logMessage : props.logMessage,
            progress : props.progress,
            status: props.status,
            taskState : props.taskState,
            topText : props.topText
        });

        // Publish an event for custom AJAX implementations to listen for.
        @JS_NS@._dojo.publish(
            @JS_NS@.widget.progressBar.event.progress.endTopic, [props]);
        return true;
    }
};

// Listen for Widget events.
@JS_NS@._dojo.subscribe(@JS_NS@.widget.progressBar.event.progress.beginTopic,
    @JS_NS@.widget._jsfx.progressBar, "_processProgressEvent");
@JS_NS@._dojo.subscribe(@JS_NS@.widget.progressBar.event.refresh.beginTopic,
    @JS_NS@.widget._jsfx.common, "_processRefreshEvent");
