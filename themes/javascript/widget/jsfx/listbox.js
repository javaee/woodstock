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

dojo.provide("webui.@THEME@.widget.jsfx.listbox");

dojo.require("webui.@THEME@.widget.listbox");

/**
 * This function is used to obtain data asynchronously.
 */
webui.@THEME@.widget.jsfx.listbox = {
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
        DynaFaces.fireAjaxTransaction(
            (domNode) ? domNode : document.forms[0], {
            execute: (props.execute) ? props.execute : "none",
            render: props.id,
            replaceElement: webui.@THEME@.widget.jsfx.listbox.refreshCallback,
            xjson: {
                id: props.id,
                event: "refresh"
            }
        });
        return true;
    },
    
    /**
     * This function is used to process submit events with the following Object
     * literals.
     *
     * <ul>
     *  <li>id</li>
     *  <li>execute</li>
     * </ul>
     *
     * @param props Key-Value pairs of properties.
     */
    processSubmitEvent: function(props) {
        if (props == null) {
            return false;
        }
        
        // Dynamic Faces requires a DOM node as the source property.
        var domNode = document.getElementById(props.id);
        
        // Generate AJAX request using the JSF Extensions library.
        DynaFaces.fireAjaxTransaction(
        (domNode) ? domNode : document.forms[0], {
            execute: (props.execute) ? props.execute : props.id,
            render: props.id,
            replaceElement: webui.@THEME@.widget.jsfx.listbox.submitCallback,
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
        var props = JSON.parse(content);

        // Add rows.
        var widget = dojo.widget.byId(id);
        widget.setProps(props);

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.listbox.refresh.endEventTopic, props);
        return true;
    },
    
    /**
     * This function is used to update the widget after it has been submitted
     * and rendered back.
     * Note that the client state of the widget could have been changed
     * independently of the state (client changes do not get commited to the 
     * server), so refreshing of the client side upon every autoSubmit would
     * potentially alter client rendered component.
     *
     * The default implementation does nothing but publishes the end submit event.
     *
     * @param id The client id.
     * @param content The content returned by the AJAX response.
     * @param closure The closure argument provided to DynaFaces.fireAjaxTransaction.
     * @param xjson The xjson argument provided to DynaFaces.fireAjaxTransaction.
     */
    submitCallback: function(id, content, closure, xjson) {
        if (id == null || content == null) {
            return false;
        }        
        // Parse JSON text.
        var props = JSON.parse(content);
        
        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.listbox.submit.endEventTopic, props);
        return true;
    }
}

// Listen for Dojo Widget events.
dojo.event.topic.subscribe(webui.@THEME@.widget.listbox.refresh.beginEventTopic,
    webui.@THEME@.widget.jsfx.listbox, "processRefreshEvent");
dojo.event.topic.subscribe(webui.@THEME@.widget.listbox.submit.beginEventTopic,
    webui.@THEME@.widget.jsfx.listbox, "processSubmitEvent");

//-->
