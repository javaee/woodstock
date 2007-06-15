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
//

dojo.provide("webui.@THEME@.widget.jsfx.progressBar");

dojo.require("webui.@THEME@.widget.jsfx.*");
dojo.require("webui.@THEME@.widget.progressBar");

/**
 * This name space is used to update data asynchronously.
 */
webui.@THEME@.widget.jsfx.progressBar =  {
    /**
     * This function is used to process progress events with the following Object
     * literals.
     *
     * <ul>
     *  <li>id</li>
     * </ul>
     *
     * @param props Key-Value pairs of properties.
     */
    processProgressEvent: function(props) {
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
            replaceElement: webui.@THEME@.widget.jsfx.progressBar.progressCallback,
            xjson: {
                id: props.id,
                event: "progress"
            }
        });

        return true;
    },

    /**
     * This function is used to update progress.
     *
     * @param id The client id.
     * @param content The content returned by the AJAX response.
     * @param closure The closure argument provided to DynaFaces.fireAjaxTransaction.
     * @param xjson The zjson argument provided to DynaFaces.fireAjaxTransaction.
     */
    progressCallback: function(id, content, closure, xjson) {
        if (id == null || content == null) {
            return false;
        }

        // Parse JSON text.
        var props = JSON.parse(content);

        // Set progress.
        var widget = dojo.widget.byId(id);
        widget.setProgress({
            failedStateText : props.failedStateText,
            logMessage : props.logMessage,
            progress : props.progress,
            status: props.status,
            taskState : props.taskState,
            topText : props.topText
        });

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.progressBar.progress.endEventTopic, props);
        return true;
    }
}

// Listen for Dojo Widget events.
dojo.event.topic.subscribe(webui.@THEME@.widget.progressBar.progress.beginEventTopic,
    webui.@THEME@.widget.jsfx.progressBar, "processProgressEvent");
dojo.event.topic.subscribe(webui.@THEME@.widget.progressBar.refresh.beginEventTopic,
    webui.@THEME@.widget.jsfx.common, "processRefreshEvent");

//-->
