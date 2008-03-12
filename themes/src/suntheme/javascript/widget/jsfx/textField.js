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

webui.@THEME@.dojo.provide("webui.@THEME@.widget.jsfx.textField");

webui.@THEME@.dojo.require("webui.@THEME@.json");
webui.@THEME@.dojo.require("webui.@THEME@.widget.jsfx.common");
webui.@THEME@.dojo.require("webui.@THEME@.widget.jsfx.dynaFaces");
webui.@THEME@.dojo.require("webui.@THEME@.widget.textField");

/**
 * @class This class contains functions to obtain data asynchronously using JSF
 * Extensions as the underlying transfer protocol.
 * @static
 */
webui.@THEME@.widget.jsfx.textField = {
    /**
     * This function is used to process validation events with Object literals.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The HTML element Id.
     * @return {boolean} true if successful; otherwise, false.
     */
    processValidationEvent: function(props) {
        if (props == null) {
            return false;
        }
        
        // Dynamic Faces requires a DOM node as the source property.
        var domNode = document.getElementById(props.id);
        
        // Generate AJAX request using the JSF Extensions library.
        DynaFaces.fireAjaxTransaction(
            (domNode) ? domNode : document.forms[0], {
            execute: props.id,
            render: props.id,
            replaceElement: webui.@THEME@.widget.jsfx.textField.validationCallback,
            xjson: {
                id : props.id,
                event: "validate"
            }
        });
        return true;
    },  

    /**
     * This function is used to update widgets.
     *
     * @param {String} elementId The HTML element Id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to DynaFaces.fireAjaxTransaction.
     * @param {Object} xjson The xjson argument provided to DynaFaces.fireAjaxTransaction.
     * @return {boolean} true if successful; otherwise, false.
     */
    validationCallback: function(elementId, content, closure, xjson) {
        if (elementId == null || content == null) {
            return false;
        }
        
        // Parse JSON text.
        var props = webui.@THEME@.json.parse(content);

        // Update text field.
        var widget = webui.@THEME@.dijit.byId(elementId);
        widget.setProps({
            valid: props.valid,
            errorImage: {
                title: props.detail
            }
        });

        // Notify decoupled widgets.
        if (widget.notify) {
            // Update each given client ID.
            for (var i = 0; i < widget.notify.length; i++) {
                // Get widget associated with client ID.
                var curWidget = webui.@THEME@.dijit.byId(widget.notify[i]);
                if (curWidget && typeof curWidget.notify == "function") {
                    curWidget.notify(props);
                }
            }
        }

        // Publish an event for custom AJAX implementations to listen for.
        webui.@THEME@.dojo.publish(
            webui.@THEME@.widget.textField.event.validation.endTopic, [props]);
        return true;
    },

    /**
     * This function is used to process autoComplete events.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The HTML element Id.
     * @return {boolean} true if successful; otherwise, false.
     */
    processAutoCompleteEvent: function(props) {
        if (props == null) {
            return false;
        }
        
        // Dynamic Faces requires a DOM node as the source property.
        var domNode = document.getElementById(props.id);
        
        // Generate AJAX request using the JSF Extensions library.
        DynaFaces.fireAjaxTransaction(
            (domNode) ? domNode : document.forms[0], {
            execute: props.id,
            render: props.id,
            replaceElement: webui.@THEME@.widget.jsfx.textField.autoCompleteCallback,
            xjson: {
                id : props.id,
                event: "autocomplete"
            }
        });
        return true;
    },  

    /**
     * This function is used to update widgets with new autoComplete options list.
     *
     * @param {String} elementId The HTML element Id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to DynaFaces.fireAjaxTransaction.
     * @param {Object} xjson The xjson argument provided to DynaFaces.fireAjaxTransaction.
     * @return {boolean} true if successful; otherwise, false.
     */
    autoCompleteCallback: function(elementId, content, closure, xjson) {
        if (elementId == null || content == null) {
            return false;
        }
        
        // Parse JSON text.
        var props = webui.@THEME@.json.parse(content);

        // Update text field.
        var widget = webui.@THEME@.dijit.byId(elementId);
        widget.setProps(props);       


        // Publish an event for custom AJAX implementations to listen for.
        webui.@THEME@.dojo.publish(
            webui.@THEME@.widget.textField.event.autoComplete.endTopic, [props]);
        return true;
    }    
};

// Listen for Dojo Widget events.
webui.@THEME@.dojo.subscribe(webui.@THEME@.widget.textField.event.refresh.beginTopic,
    webui.@THEME@.widget.jsfx.common, "processRefreshEvent");
webui.@THEME@.dojo.subscribe(webui.@THEME@.widget.textField.event.state.beginTopic,
    webui.@THEME@.widget.jsfx.common, "processStateEvent");
webui.@THEME@.dojo.subscribe(webui.@THEME@.widget.textField.event.submit.beginTopic,
    webui.@THEME@.widget.jsfx.common, "processSubmitEvent");
webui.@THEME@.dojo.subscribe(webui.@THEME@.widget.textField.event.validation.beginTopic,
    webui.@THEME@.widget.jsfx.textField, "processValidationEvent");
webui.@THEME@.dojo.subscribe(webui.@THEME@.widget.textField.event.autoComplete.beginTopic,
    webui.@THEME@.widget.jsfx.textField, "processAutoCompleteEvent");
