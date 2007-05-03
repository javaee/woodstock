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

dojo.provide("webui.@THEME@.widget.hyperlink");

dojo.require("dojo.widget.*");
dojo.require("dojo.uri.Uri");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");
dojo.require("webui.@THEME@.widget.anchor");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.hyperlink.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.hyperlink = function() {
    // Set defaults.
    this.href = "#";
    this.widgetType = "hyperlink"; 

    // Register widget.
    dojo.widget.Widget.call(this);
    /**
     * This function is used to generate a template based widget.
     */
    this.fillInTemplate = function() {
        // Set public functions.
        this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }
        this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }
        this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }

        // Set private functions.
        this.addChildren = webui.@THEME@.widget.anchor.addChildren;
        this.getClassName = webui.@THEME@.widget.hyperlink.getClassName;
        this.getProps = webui.@THEME@.widget.anchor.getProps;
        this.refresh = webui.@THEME@.widget.anchor.refresh.processEvent;
        this.setAnchorProps = webui.@THEME@.widget.anchor.setAnchorProps;
        this.setProps = webui.@THEME@.widget.hyperlink.setProps;
        this.submit = webui.@THEME@.widget.hyperlink.submit;

        // Create callback function for onClick event.
        dojo.event.connect(this.domNode, "onclick",
            webui.@THEME@.widget.hyperlink.createOnClickCallback(this.id, 
                this.formId, this.params));

	// Set properties
	return this.setProps();
    }
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
        event.preventDefault();
        widget.submit(formId, params);
        return false;
    };
}

/**
 * Helper function to obtain widget class names.
 */
webui.@THEME@.widget.hyperlink.getClassName = function() {
    var className = null;
    if (this.disabled == true) {
        className = webui.@THEME@.widget.props.hyperlink.disabledClassName;
    } else {
        className = webui.@THEME@.widget.props.hyperlink.className;
    }
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
     * @param execute Comma separated string containing a list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Publish event.
        webui.@THEME@.widget.hyperlink.refresh.publishBeginEvent({
            id: this.id,
            execute: execute
        });
        return true;
    },

    /**
     * Publish an event for custom AJAX implementations to listen for.
     *
     * @param props Key-Value pairs of properties of the widget.
     */
    publishBeginEvent: function(props) {
        dojo.event.topic.publish(webui.@THEME@.widget.hyperlink.refresh.beginEventTopic, props);
        return true;
    },

    /**
     * Publish an event for custom AJAX implementations to listen for.
     *
     * @param props Key-Value pairs of properties of the widget.
     */
    publishEndEvent: function(props) {
        dojo.event.topic.publish(webui.@THEME@.widget.hyperlink.refresh.endEventTopic, props);
        return true;
    }
}

/**
 * This function is used to update widget properties with the
 * following Object literals.
 *
 * <ul>
 * <li>className</li>
 * <li>contents</li>
 * <li>dir</li>
 * <li>disabled</li>
 * <li>href</li>
 * <li>hrefLang</li>
 * <li>id</li>
 * <li>lang</li>
 * <li>onFocus</li>
 * <li>onBlur</li>
 * <li>onClick</li>
 * <li>onDblClick</li>
 * <li>onKeyDown</li>
 * <li>onKeyPress</li>
 * <li>onKeyUp</li>
 * <li>onMouseDown</li>
 * <li>onMouseOut</li>
 * <li>onMouseOver</li>
 * <li>onMouseUp</li>
 * <li>onMouseMove</li>
 * <li>style</li>
 * <li>tabIndex</li>
 * <li>title</li>
 * <li>visible</li>
 */
webui.@THEME@.widget.hyperlink.setProps = function(props){
    // Save properties for later updates.
    if (props != null) {
        // Replace contents -- do not extend.
        if (props.contents) {
            this.contents = null;
        }
        webui.@THEME@.widget.common.extend(this, props);
    } else {
        props = this.getProps(); // Widget is being initialized.
    }

    // Set style class -- must be set before calling setCoreProps().
    props.className = this.getClassName();

    // Set properties that are common to the anchor element.
    this.setAnchorProps(props); 
    this.addChildren(props);
}

/**
 * This function submits the hyperlink if the hyperlink is enabled.
 *
 * @param formId The id of the form element
 * @param params The parameters to be passed on when the hyperlink is clicked
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

dojo.inherits(webui.@THEME@.widget.hyperlink, dojo.widget.HtmlWidget);

//-->
