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

dojo.provide("webui.@THEME@.widget.button");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.button.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.button = function() {
    // Set defaults.
    this.disabled = false;
    this.mini = false;
    this.primary = true;
    this.type = "submit";
    this.widgetType = "button";

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
        this.getClassName = webui.@THEME@.widget.button.getClassName;
        this.getHoverClassName = webui.@THEME@.widget.button.getHoverClassName;
        this.getProps = webui.@THEME@.widget.button.getProps;
        this.initClassNames = webui.@THEME@.widget.button.initClassNames;
        this.refresh = webui.@THEME@.widget.button.refresh.processEvent;
        this.setProps = webui.@THEME@.widget.button.setProps;

        // Initialize the deprecated functions in formElements.js. 
        // 
        // Note: Although we now have a setProps function to update properties,
        // these functions were previously added to the DOM node; thus, we must
        // continue to be backward compatible.
        setTimeout(webui.@THEME@.widget.button.createInitCallback(this.id), 10);

        // Set events.
        dojo.event.connect(this.domNode, "onblur",
            webui.@THEME@.widget.button.createOnBlurCallback(this.id));
        dojo.event.connect(this.domNode, "onfocus",
            webui.@THEME@.widget.button.createOnFocusCallback(this.id));
        dojo.event.connect(this.domNode, "onmouseout",
            webui.@THEME@.widget.button.createOnBlurCallback(this.id));
        dojo.event.connect(this.domNode, "onmouseover",
            webui.@THEME@.widget.button.createOnFocusCallback(this.id));

        // Initialize class names.
        this.initClassNames();

        // Set properties.
        this.setProps();
        return true;
    }
}

/**
 * Helper function to create callback to initialize deprecated functions.
 *
 * @param id The HTML element id used to invoke the callback.
 */
webui.@THEME@.widget.button.createInitCallback = function(_id) {
    if (_id == null) {
        return null;
    }
    // New literals are created every time this function
    // is called, and it's saved by closure magic.
    return function(evt) { 
        return webui.@THEME@.button.init({id: _id})
    };
}

/**
 * Helper function to create callback for onBlur event.
 *
 * @param id The HTML element id used to invoke the callback.
 */
webui.@THEME@.widget.button.createOnBlurCallback = function(id) {
    if (id == null) {
        return null;
    }
    // New literals are created every time this function
    // is called, and it's saved by closure magic.
    return function(evt) { 
        var widget = dojo.widget.byId(id);
        if (widget == null) {
            return false;
        }
        if (widget.disabled == true) {
            return true;
        }

        // Set style class.
        widget.domNode.className = widget.getClassName();
        return true;
    };
}

/**
 * Helper function to create callback for onFocus event.
 *
 * @param id The HTML element id used to invoke the callback.
 */
webui.@THEME@.widget.button.createOnFocusCallback = function(id) {
    if (id == null) {
        return null;
    }
    // New literals are created every time this function
    // is called, and it's saved by closure magic.
    return function(evt) { 
        var widget = dojo.widget.byId(id);
        if (widget == null) {
            return false;
        }
        if (widget.disabled == true) {
            return true;
        }

        // Set style class.
        widget.domNode.className = widget.getHoverClassName();
        return true;
    };
}

/**
 * Helper function to obtain widget class names.
 */
webui.@THEME@.widget.button.getClassName = function() {
    var className = null;
    if (this.mini == true && this.primary == true) {
        className = (this.disabled == true)
            ? this.primaryMiniDisabledClassName
            : this.primaryMiniClassName;
    } else if (this.mini == true) {
        className = (this.disabled == true)
            ? this.secondaryMiniDisabledClassName
            : this.secondaryMiniClassName;
    } else if (this.primary == true) {
        className = (this.disabled == true)
            ? this.primaryDisabledClassName
            : this.primaryClassName;
    } else {
        className = (this.disabled == true)
            ? this.secondaryDisabledClassName
            : this.secondaryClassName;
    }
    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * Helper function to obtain widget (mouse hover) class names.
 */
webui.@THEME@.widget.button.getHoverClassName = function() {
    var className = null;
    if (this.mini == true && this.primary == true) {
        className = this.primaryMiniHovClassName;
    } else if (this.mini == true) {
        className = this.secondaryMiniHovClassName;
    } else if (this.primary == true) {
        className = this.primaryHovClassName;
    } else {
        className = this.secondaryHovClassName;
    }
    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * Helper function to obtain widget (mouse hover) class names.
 */
webui.@THEME@.widget.button.initClassNames = function() {
    // Set style classes
    if (this.icon == true) {
        this.primaryClassName = webui.@THEME@.props.button.imageClassName;
        this.primaryDisabledClassName = webui.@THEME@.props.button.imageDisabledClassName;
        this.primaryHovClassName = webui.@THEME@.props.button.imageHovClassName;

        // Currently not used in theme.
        this.primaryMiniClassName = "";
        this.primaryMiniDisabledClassName = "";
        this.primaryMiniHovClassName = "";
        this.secondaryClassName = "";
        this.secondaryDisabledClassName = "";
        this.secondaryHovClassName = "";
        this.secondaryMiniClassName = "";
        this.secondaryMiniDisabledClassName = "";
        this.secondaryMiniHovClassName = "";
    } else {
        this.primaryClassName = webui.@THEME@.props.button.primaryClassName;
        this.primaryDisabledClassName = webui.@THEME@.props.button.primaryDisabledClassName;
        this.primaryHovClassName = webui.@THEME@.props.button.primaryHovClassName;
        this.primaryMiniClassName = webui.@THEME@.props.button.primaryMiniClassName;
        this.primaryMiniDisabledClassName = webui.@THEME@.props.button.primaryMiniDisabledClassName;
        this.primaryMiniHovClassName = webui.@THEME@.props.button.primaryMiniHovClassName;
        this.secondaryClassName = webui.@THEME@.props.button.secondaryClassName;
        this.secondaryDisabledClassName = webui.@THEME@.props.button.secondaryDisabledClassName;
        this.secondaryHovClassName = webui.@THEME@.props.button.secondaryHovClassName;
        this.secondaryMiniClassName = webui.@THEME@.props.button.secondaryMiniClassName;
        this.secondaryMiniDisabledClassName = webui.@THEME@.props.button.secondaryMiniDisabledClassName;
        this.secondaryMiniHovClassName = webui.@THEME@.props.button.secondaryMiniHovClassName;
    }
}

/**
 * This function is used to get widget properties. Please see
 * webui.@THEME@.widget.button.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.button.getProps = function() {
    var props = {};

    // Set properties.
    if (this.alt) { props.alt = this.alt; }
    if (this.align) { props.align = this.align; }
    if (this.contents) { props.contents = this.contents; }
    if (this.disabled != null) { props.disabled = this.disabled; }
    if (this.mini != null) { props.mini = this.mini; }
    if (this.name) { props.name = this.name; }
    if (this.primary != null) { props.primary = this.primary; }
    if (this.type) { props.type = this.type; }
    if (this.value) { props.value = this.value; }
    if (this.title) { props.title = this.title; }

    // Add DOM node properties.
    Object.extend(props, webui.@THEME@.widget.common.getCommonProps(this));
    Object.extend(props, webui.@THEME@.widget.common.getCoreProps(this));
    Object.extend(props, webui.@THEME@.widget.common.getJavaScriptProps(this));

    return props;
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.button.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_widget_button_refresh_begin",
    endEventTopic: "webui_widget_button_refresh_end",
 
    /**
     * Process refresh event.
     *
     * @param execute Comma separated string containing a list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(_execute) {
        // Publish event.
        webui.@THEME@.widget.button.refresh.publishBeginEvent({
            id: this.id,
            execute: _execute
        });
        return true;
    },

    /**
     * Publish an event for custom AJAX implementations to listen for.
     *
     * @param props Key-Value pairs of properties of the widget.
     */
    publishBeginEvent: function(props) {
        dojo.event.topic.publish(webui.@THEME@.widget.button.refresh.beginEventTopic, props);
        return true;
    },

    /**
     * Publish an event for custom AJAX implementations to listen for.
     *
     * @param props Key-Value pairs of properties of the widget.
     */
    publishEndEvent: function(props) {
        dojo.event.topic.publish(webui.@THEME@.widget.button.refresh.endEventTopic, props);
        return true;
    }
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul>
 *  <li>alt</li>
 *  <li>align</li>
 *  <li>className</li>
 *  <li>contents</li>
 *  <li>dir</li>
 *  <li>disabled</li>
 *  <li>id</li>
 *  <li>lang</li>
 *  <li>mini</li>
 *  <li>name</li>
 *  <li>onBlur</li>
 *  <li>onClick</li>
 *  <li>onDblClick</li>
 *  <li>onFocus</li>
 *  <li>onKeyDown</li>
 *  <li>onKeyPress</li>
 *  <li>onKeyUp</li>
 *  <li>onMouseDown</li>
 *  <li>onMouseOut</li>
 *  <li>onMouseOver</li>
 *  <li>onMouseUp</li>
 *  <li>onMouseMove</li>
 *  <li>primary</li>
 *  <li>style</li>
 *  <li>title</li>
 *  <li>type</li>
 *  <li>value</li>
 *  <li>visible</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.button.setProps = function(props) {
    // Save properties for later updates.
    if (props != null) {
        webui.@THEME@.widget.common.extend(this, props);
    } else {
        props = this.getProps(); // Widget is being initialized.
    }

    // Set disabled before className.
    if (props.disabled != null) {
        if (props.disabled == true) {
            this.domNode.setAttribute("disabled", "disabled");
        } else {
            this.domNode.removeAttribute("disabled");
        }
    }

    // Set style class -- must be set before calling setCoreProps().
    props.className = this.getClassName();

    // Set DOM node properties.
    webui.@THEME@.widget.common.setCoreProps(this.domNode, props);
    webui.@THEME@.widget.common.setCommonProps(this.domNode, props);
    webui.@THEME@.widget.common.setJavaScriptProps(this.domNode, props);

    if (props.alt) { this.domNode.setAttribute("alt", props.alt); }
    if (props.align) { this.domNode.setAttribute("align", props.align); }
    if (props.name) { this.domNode.setAttribute("name", props.name); }
    if (props.value) { this.domNode.setAttribute("value", props.value); }
    if (props.type) { this.domNode.setAttribute("type", props.type); }

    // Set contents.
    if (props.contents) {
        this.domNode.innerHTML = ""; // Cannot be set null on IE.
        webui.@THEME@.widget.common.addFragment(this.domNode, props.contents, "last");
    }
    return true;
}

dojo.inherits(webui.@THEME@.widget.button, dojo.widget.HtmlWidget);

//-->
