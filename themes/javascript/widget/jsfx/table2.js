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

dojo.provide("webui.@THEME@.widget.jsfx.table2");

dojo.require("webui.@THEME@.widget.table2");

/**
 * This function is used to obtain data asynchronously.
 */
webui.@THEME@.widget.jsfx.table2 = {
    /**
     * This function is used to process refresh events with the following Object
     * literals.
     *
     * <ul>
     *  <li>id</li>
     *  <li>execute</li>
     * </ul>
     *
     * @param props Key-Value pairs of properties.
     */
    processRefreshEvent: function(props) {
        if (props == null) {
            return false;
        }

        // Dynamic Faces requires a DOM node as the source property.
        var domNode = document.getElementById(props.id);

        // Generate AJAX request using the JSF Extensions library.
        new DynaFaces.fireAjaxTransaction(
            (domNode) ? domNode : document.forms[0], {
            execute: (props.execute) ? props.execute : "none",
            render: props.id,
            replaceElement: webui.@THEME@.widget.jsfx.table2.refreshCallback,
            xjson: {
                id: props.id,
                event: "refresh"
            }
        });
        return true;
    },

    /**
     * This function is used to refresh widgets.
     *
     * @param id The client id.
     * @param content The content returned by the AJAX response.
     * @param closure The closure argument provided to DynaFaces.fireAjaxTransaction.
     * @param xjson The xjson argument provided to DynaFaces.fireAjaxTransaction.
     */
    refreshCallback: function(id, content, closure, xjson) {
        if (id == null || content == null) {
            return false;
        }

        // Parse JSON text.
        var json = JSON.parse(content);

        // Add rows.
        var widget = dojo.widget.byId(id);
        widget.setProps(json);

        // Publish an event for custom AJAX implementations to listen for.
        webui.@THEME@.widget.table2.refresh.publishEndEvent(json);
        return true;
    }
}

// Listen for Dojo Widget events.
dojo.event.topic.subscribe(webui.@THEME@.widget.table2.refresh.beginEventTopic,
    webui.@THEME@.widget.jsfx.table2, "processRefreshEvent");

//-->
