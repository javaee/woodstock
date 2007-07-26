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

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");
dojo.require("webui.@THEME@.widget.anchor");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.hyperlink = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * Helper function to create callback for onClick event.
 *
 * @param id The HTML element id used to invoke the callback.
 * @param linkId The id of the html anchor element
 * @param formId The id of the form element
 * @param params The parameters to be passed on when the hyperlink is clicked
 */
webui.@THEME@.widget.hyperlink.createOnClickCallback = function(id, formId, 
        params) {
    if (id == null) {
        return null;
    }
    // New literals are created every time this function
    // is called, and it's saved by closure magic.
    return function(event) { 
        var widget = dojo.widget.byId(id);
        if (widget == null || widget.disabled == true) {
            event.preventDefault();
            return false;
        }

        // If function returns false, we must prevent the submit.
        var result = (widget.domNode._onclick)
            ? widget.domNode._onclick(event) : true;
        if (result == false) {
            event.preventDefault();
            return false;
        }
        if (widget.href) {
            return false;
        }

        event.preventDefault();
        widget.submit(formId, params);
        return false;
    };
}

/**
 * This function is used to fill in template properties.
 *
 * Note: This is called after the buildRendering() function. Anything to be set 
 * only once should be added here; otherwise, use the _setProps() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 */
webui.@THEME@.widget.hyperlink.fillInTemplate = function(props, frag) {
    // If the href attribute does not exist, set "#" as the default value of the
    // DOM node.
    this.domNode.href = "#";

    // Create callback function for onClick event.
    dojo.event.connect(this.domNode, "onclick",
        webui.@THEME@.widget.hyperlink.createOnClickCallback(this.id, 
            this.formId, this.params));

    // Set common functions.
    return webui.@THEME@.widget.hyperlink.superclass.fillInTemplate.call(this, props, frag);
}

/**
 * This function is used to obtain the outermost HTML element class name.
 *
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 */
webui.@THEME@.widget.hyperlink.getClassName = function() {
    // Set default style.
    var className = (this.disabled == true)
        ? webui.@THEME@.widget.props.hyperlink.disabledClassName
        : webui.@THEME@.widget.props.hyperlink.className;

    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.hyperlink.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_hyperlink_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_hyperlink_refresh_end",
 
    /**
     * Process refresh event.
     *
     * @param execute The string containing a comma separated list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.hyperlink");

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.hyperlink.refresh.beginEventTopic, {
                id: this.id,
                execute: execute,
                endEventTopic: webui.@THEME@.widget.hyperlink.refresh.endEventTopic
            });
        return true;
    }
}

/**
 * This function submits the hyperlink if the hyperlink is enabled.
 *
 * @param formId The id of the form element
 * @param params The parameters to be passed on when the hyperlink is clicked
 * @param id The id of the hyperlink component.
 */
webui.@THEME@.widget.hyperlink.submit = function (formId, params, id) {
    var theForm = document.getElementById(formId);
    var oldTarget = theForm.target;
    var oldAction = theForm.action;
    var link = null;
    if (id) {
        link = document.getElementById(id);
    } else {
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

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.hyperlink, webui.@THEME@.widget.anchor);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.hyperlink, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.hyperlink.fillInTemplate,
    getClassName: webui.@THEME@.widget.hyperlink.getClassName,
    refresh: webui.@THEME@.widget.hyperlink.refresh.processEvent,
    submit: webui.@THEME@.widget.hyperlink.submit,

    // Set defaults.
    widgetType: "hyperlink"
});
