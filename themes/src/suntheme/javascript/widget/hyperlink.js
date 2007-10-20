// widget/hyperlink.js
//
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

dojo.provide("webui.@THEME@.widget.hyperlink");

dojo.require("webui.@THEME@.widget.anchorBase");

/**
 * @name webui.@THEME@.widget.hyperlink
 * @extends webui.@THEME@.widget.anchorBase
 * @class This class contains functions for the hyperlink widget.
 * @constructor This function is used to construct a hyperlink widget.
 */
dojo.declare("webui.@THEME@.widget.hyperlink", webui.@THEME@.widget.anchorBase, {
    // Set defaults.
    widgetName: "hyperlink" // Required for theme properties.
});

/**
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
webui.@THEME@.widget.hyperlink.event =
        webui.@THEME@.widget.hyperlink.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_hyperlink_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_hyperlink_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_hyperlink_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_hyperlink_event_state_end"
    }
}

/**
 * This function is used to obtain the outermost HTML element class name.
 * <p>
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 * </p>
 * @return {String} The outermost HTML element class name.
 */
webui.@THEME@.widget.hyperlink.prototype.getClassName = function() {
    // Set default style.
    var className = (this.disabled == true)
        ? this.widget.getClassName("HYPERLINK_DISABLED","")
        : this.widget.getClassName("HYPERLINK","");

    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * Helper function to create callback for onClick event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.hyperlink.prototype.onClickCallback = function(event) {
    if (this.disabled == true) {
        event.preventDefault();
        return false;
    }

    // If function returns false, we must prevent the submit.
    var result = (this.domNode._onclick)
        ? this.domNode._onclick(event) : true;
    if (result == false) {
        event.preventDefault();
        return false;
    }
    if (this.href) {
        return false;
    }
    event.preventDefault();
    
    // If a form id isnt provided, use the utility function to
    // obtain the form id.
    if (this.formId == null) {
        var form = this.widget.getForm(this.domNode);
        this.formId = (form) ? form.id : null;
    }
    return this.submitFormData(this.formId, this.params);
}

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.hyperlink.prototype.postCreate = function () {
    // If the href attribute does not exist, set "#" as the default value of the
    // DOM node.
    this.domNode.href = "#";

    // Create callback function for onClick event.
    dojo.connect(this.domNode, "onclick", this, "onClickCallback");

    return this.inherited("postCreate", arguments);
}

/**
 * This function is used to set widget properties using Object literals.
 * <p>
 * Note: This function extends the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 * </p><p>
 * If the notify param is true, the widget's state change event shall be
 * published. This is typically used to keep client-side state in sync with the
 * server.
 * </p>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} [accessKey]
 * @config {String} [charset]
 * @config {String} [className] CSS selector.
 * @config {Array} [contents]
 * @config {String} [coords]
 * @config {String} [dir] Specifies the directionality of text.
 * @config {boolean} [disabled] Disable element.
 * @config {String} [formId] The id of the HTML form element.
 * @config {String} [href]
 * @config {String} [hrefLang]
 * @config {String} [id] Uniquely identifies an element within a document.
 * @config {String} [lang] Specifies the language of attribute values and content.
 * @config {String} [name] 
 * @config {String} [onBlur] Element lost focus.
 * @config {String} [onClick] Mouse button is clicked on element.
 * @config {String} [onDblClick] Mouse button is double-clicked on element.
 * @config {String} [onFocus] Element received focus.
 * @config {String} [onKeyDown] Key is pressed down over element.
 * @config {String} [onKeyPress] Key is pressed and released over element.
 * @config {String} [onKeyUp] Key is released over element.
 * @config {String} [onMouseDown] Mouse button is pressed over element.
 * @config {String} [onMouseOut] Mouse is moved away from element.
 * @config {String} [onMouseOver] Mouse is moved onto element.
 * @config {String} [onMouseUp] Mouse button is released over element.
 * @config {String} [onMouseMove] Mouse is moved while over element.
 * @config {Array} [params] The parameters to be passed during request.
 * @config {String} [rel]
 * @config {String} [rev]
 * @config {String} [shape]
 * @config {String} [style] Specify style rules inline.
 * @config {int} [tabIndex] Position in tabbing order.
 * @config {String} [title] Provides a title for element.
 * @config {boolean} [visible] Hide or show element.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.hyperlink.prototype.setProps = function(props, notify) {
    // Note: This function is overridden for JsDoc.
    return this.inherited("setProps", arguments);
}

/**
 * This function submits the hyperlink if the hyperlink is enabled.
 *
 * @param {String} formId The id of the HTML form element.
 * @param {Array} params The parameters to be passed during request.
 * @return {boolean} false to cancel the JavaScript event.
 */
webui.@THEME@.widget.hyperlink.prototype.submitFormData = function (formId, params) {
    var theForm = document.getElementById(formId);
    var oldTarget = theForm.target;
    var oldAction = theForm.action;

    // Obtain HTML element for tab and common task components.
    var link = document.getElementById(this.id);
    if (link == null) {
        link = this.domNode;
    }

    // Set new action URL.
    theForm.action += "?" + link.id + "_submittedLink=" + link.id;               

    // Set new target.
    if (link.target && link.target.length > 0) {
        theForm.target = link.target;
    } else {
        theForm.target = "_self";
    }

    // Append query params to new action URL.
    if (params != null) {
        var x;
        for (var i = 0; i < params.length; i++) {
            x = params[i];
            theForm.action += "&" + params[i] + "=" + params[i + 1]; 
            i++;
        }
    }

    // Submit form.
    theForm.submit(); 

    // Restore target and action URL.
    if (link.target != null) {
        theForm.target = oldTarget;
        theForm.action = oldAction;
    }
    return false;        
}
