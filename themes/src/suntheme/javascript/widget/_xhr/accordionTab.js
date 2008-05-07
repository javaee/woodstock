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

@JS_NS@._dojo.provide("@JS_NS@.widget._xhr.accordionTab");

@JS_NS@._dojo.require("@JS_NS@.json");
@JS_NS@._dojo.require("@JS_NS@.xhr");
@JS_NS@._dojo.require("@JS_NS@.widget.accordionTab");
@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget._xhr.common");

/**
 * @class This class contains functions to obtain data asynchronously using JSF
 * Extensions as the underlying transfer protocol.
 * @static
 * @private
 */
@JS_NS@.widget._xhr.accordionTab = {
    /**
     * This function is used to load a tab content asynchronously.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The HTML element Id.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _processLoadContentEvent: function(props) {
        if (props == null) {
            return false;
        }

        // Ensure URL has been provided.
        if (@JS_NS@._base.config.ajax.url == null) {
            console.error("URL for Ajax transaction not provided.");
            return false
        }

        // Get form.
        var form = @JS_NS@.widget.common._getForm(
            document.getElementById(props.id));
        if (form == null) {
            form = document.forms[0];
        }

        // Pass through variables.
        var _id = props.id;
        var closure = {};
        var xjson = {
            id: props.id,
            event: "scroll",
            execute: props.id // Need to decode hidden field.
        };

        // Generate AJAX request.
        @JS_NS@.xhr.get({
            error: function(content, ioArgs) {
                console.error("HTTP status code: ", ioArgs.xhr.status);
                return content;
            },
            form: form,
            headers: {
                "X-JSON": @JS_NS@.json.stringify(xjson)
            },
            load: function(content, ioArgs) {
                @JS_NS@.widget._xhr.accordionTab._loadContentCallback(_id, content, closure, xjson);
                return content;
            },
            timeout: 5000, // Time in milliseconds
            url: @JS_NS@._base.config.ajax.url
        });
        return true;
    },

    /**
     * This function is used to update tab with the loaded content.
     *
     * @param {String} id The HTML widget Id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to the Ajax transaction.
     * @param {Object} xjson The xjson header provided to the Ajax transaction.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _loadContentCallback: function(id, content, closure, xjson) {
        if (id == null || content == null) {
            return false;
        }

        // Parse JSON text.
        var json = @JS_NS@.json.parse(content);

        // Set progress.
        var widget = @JS_NS@.widget.common.getWidget(id);
        widget.setProps(json);

        // Publish an event for custom AJAX implementations to listen for.
        @JS_NS@._dojo.publish(@JS_NS@.widget.accordionTab.event.load.endTopic, [json]);
        return true;
    }
};

// Listen for Widget events.
@JS_NS@._dojo.subscribe(@JS_NS@.widget.accordionTab.event.load.beginTopic,
    @JS_NS@.widget._xhr.accordionTab, "_processLoadContentEvent");
@JS_NS@._dojo.subscribe(@JS_NS@.widget.accordionTab.event.refresh.beginTopic,
    @JS_NS@.widget._xhr.common, "_processRefreshEvent");
