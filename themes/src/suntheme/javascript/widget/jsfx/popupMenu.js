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

dojo.provide("webui.@THEME@.widget.jsfx.popupMenu");

dojo.require("webui.@THEME@.widget.jsfx.*");
dojo.require("webui.@THEME@.widget.popupMenu");

webui.@THEME@.widget.jsfx.popupMenu = {
    /**
     * This function is used to process submit events with the following Object
     * literals.Cannot use the processSubmitEvent function in the common.js file
     * as we need an extra attribute called value to be submitted for every request.
     *
     * <ul>
     *  <li>id</li>
     *  <li>endTopic</li>
     *  <li>execute</li>
     *  <li>value</li>     
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
            replaceElement: webui.@THEME@.widget.jsfx.common.submitCallback,
            xjson: {
                id: props.id,
                endTopic: props.endTopic,
                value: props.value,
                event: "submit"
            }
        });
        return true;
    }
}
// Listen for Dojo Widget events.
dojo.event.topic.subscribe(webui.@THEME@.widget.popupMenu.event.refresh.beginTopic,
    webui.@THEME@.widget.jsfx.common, "processRefreshEvent");
dojo.event.topic.subscribe(webui.@THEME@.widget.popupMenu.event.submit.beginTopic,
    webui.@THEME@.widget.jsfx.popupMenu, "processSubmitEvent");

//-->
