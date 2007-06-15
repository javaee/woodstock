//<!--
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

// This Javascript file should be included in any page that uses the associated
// component, where JSF Extensions is used as the underlying transfer protocol.

dojo.provide("webui.@THEME@.widget.jsfx.textField");

dojo.require("webui.@THEME@.widget.jsfx.*");
dojo.require("webui.@THEME@.widget.textField");

/**
 * This function is used to obtain data asynchronously.
 */
webui.@THEME@.widget.jsfx.textField = {
    /**
     * This function is a validation event processor using props
     * instead of event.
     *
     * @param props Key-Value pairs of properties.
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
     * @param elementId The HTML element Id.
     * @param content The content returned by the AJAX response.
     * @param closure The closure argument provided to DynaFaces.fireAjaxTransaction.
     * @param xjson The xjson argument provided to DynaFaces.fireAjaxTransaction.
     */
    validationCallback: function(elementId, content, closure, xjson) {
        if (elementId == null || content == null) {
            return false;
        }
        
        // Parse JSON text.
        var props = JSON.parse(content);

        // Update text field.
        var widget = dojo.widget.byId(elementId);
        widget.setProps({
            valid: props.valid,
            errorImage: {
                title: props.detail
            }
        });

        // Notify widgets.
        if (widget.notify) {
            // Update each given client ID.
            for (var i = 0; i < widget.notify.length; i++) {
                // Get widget associated with client ID.
                var curWidget = dojo.widget.byId(widget.notify[i]);
                if (curWidget == null 
                        || typeof curWidget.validate != "function") {
                    continue;
                }
                curWidget.validate(props);
            }
        }

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.textField.validation.endEventTopic, props);
        return true;
    }
}

// Listen for Dojo Widget events.
dojo.event.topic.subscribe(webui.@THEME@.widget.textField.refresh.beginEventTopic,
    webui.@THEME@.widget.jsfx.common, "processRefreshEvent");
dojo.event.topic.subscribe(webui.@THEME@.widget.textField.submit.beginEventTopic,
    webui.@THEME@.widget.jsfx.common, "processSubmitEvent");
dojo.event.topic.subscribe(webui.@THEME@.widget.textField.validation.beginEventTopic,
    webui.@THEME@.widget.jsfx.textField, "processValidationEvent");

//-->
