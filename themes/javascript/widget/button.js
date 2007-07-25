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

dojo.provide("webui.@THEME@.widget.button");

dojo.require("dojo.widget.*");
dojo.require("dojo.uri.Uri");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.button = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
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
    return function(event) { 
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
    return function(event) { 
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
 * This function is used to fill in template properties.
 *
 * Note: This is called after the buildRendering() function. Anything to be set 
 * only once should be added here; otherwise, use the setWidgetProps() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 */
webui.@THEME@.widget.button.fillInTemplate = function(props, frag) {
    // Set ids.
    if (this.id) {
        this.domNode.name = this.id;
    }

    // Initialize the deprecated functions in formElements.js. 
    // 
    // Note: Although we now have a setProps function to update properties,
    // these functions were previously added to the DOM node; thus, we must
    // continue to be backward compatible.
    webui.@THEME@.button.init({id: this.id});

    // Set events.
    dojo.event.connect(this.domNode, "onblur",
        webui.@THEME@.widget.button.createOnBlurCallback(this.id));
    dojo.event.connect(this.domNode, "onfocus",
        webui.@THEME@.widget.button.createOnFocusCallback(this.id));
    dojo.event.connect(this.domNode, "onmouseout",
        webui.@THEME@.widget.button.createOnBlurCallback(this.id));
    dojo.event.connect(this.domNode, "onmouseover",
        webui.@THEME@.widget.button.createOnFocusCallback(this.id));

    // Set common functions.
    return webui.@THEME@.widget.button.superclass.fillInTemplate.call(this, props, frag);
}

/**
 * This function is used to obtain the outermost HTML element class name.
 */
webui.@THEME@.widget.button.getClassName = function() {
    var className = null;

    // Set default style.
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

    // Set default style.
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
 * This function is used to initialize the widget.
 *
 * Note: This is called after the fillInTemplate() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 * @param parent The parent of this widget.
 */
webui.@THEME@.widget.button.initialize = function (props, frag, parent) {
    // Initialize class names.
    if (this.src != null) {
        this.primaryClassName = webui.@THEME@.widget.props.button.imageClassName;
        this.primaryDisabledClassName = webui.@THEME@.widget.props.button.imageDisabledClassName;
        this.primaryHovClassName = webui.@THEME@.widget.props.button.imageHovClassName;

        // Currently not used in theme.
        this.primaryMiniClassName = this.primaryClassName;
        this.primaryMiniDisabledClassName = this.primaryDisabledClassName;
        this.primaryMiniHovClassName = this.primaryHovClassName;
        this.secondaryClassName = this.primaryClassName;
        this.secondaryDisabledClassName = this.primaryDisabledClassName;
        this.secondaryHovClassName = this.primaryHovClassName;
        this.secondaryMiniClassName = this.primaryClassName;
        this.secondaryMiniDisabledClassName = this.primaryDisabledClassName;
        this.secondaryMiniHovClassName = this.primaryHovClassName;
    } else {
        this.primaryClassName = webui.@THEME@.widget.props.button.primaryClassName;
        this.primaryDisabledClassName = webui.@THEME@.widget.props.button.primaryDisabledClassName;
        this.primaryHovClassName = webui.@THEME@.widget.props.button.primaryHovClassName;
        this.primaryMiniClassName = webui.@THEME@.widget.props.button.primaryMiniClassName;
        this.primaryMiniDisabledClassName = webui.@THEME@.widget.props.button.primaryMiniDisabledClassName;
        this.primaryMiniHovClassName = webui.@THEME@.widget.props.button.primaryMiniHovClassName;
        this.secondaryClassName = webui.@THEME@.widget.props.button.secondaryClassName;
        this.secondaryDisabledClassName = webui.@THEME@.widget.props.button.secondaryDisabledClassName;
        this.secondaryHovClassName = webui.@THEME@.widget.props.button.secondaryHovClassName;
        this.secondaryMiniClassName = webui.@THEME@.widget.props.button.secondaryMiniClassName;
        this.secondaryMiniDisabledClassName = webui.@THEME@.widget.props.button.secondaryMiniDisabledClassName;
        this.secondaryMiniHovClassName = webui.@THEME@.widget.props.button.secondaryMiniHovClassName;
    }
    return webui.@THEME@.widget.button.superclass.initialize.call(this, 
        props, frag, parent);
}
    
/**
 * This function is used to get widget properties. Please see the 
 * setWidgetProps() function for a list of supported properties.
 */
webui.@THEME@.widget.button.getProps = function() {
    var props = webui.@THEME@.widget.button.superclass.getProps.call(this);

    // Set properties.
    if (this.alt) { props.alt = this.alt; }
    if (this.align) { props.align = this.align; }
    if (this.disabled != null) { props.disabled = this.disabled; }
    if (this.escape != null) { props.escape = this.escape; }
    if (this.mini != null) { props.mini = this.mini; }
    if (this.primary != null) { props.primary = this.primary; }
    if (this.src) { props.src = this.src; }
    if (this.value) { props.value = this.value; }

    return props;
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.button.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_button_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_button_refresh_end",
 
    /**
     * Process refresh event.
     *
     * @param execute The string containing a comma separated list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.button");

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.button.refresh.beginEventTopic, {
                id: this.id,
                execute: execute,
                endEventTopic: webui.@THEME@.widget.button.refresh.endEventTopic
            });
        return true;
    }
}

/**
 * This function is used to set widget properties with the following 
 * Object literals.
 *
 * <ul>
 *  <li>alt</li>
 *  <li>align</li>
 *  <li>className</li>
 *  <li>dir</li>
 *  <li>disabled</li>
 *  <li>escape</li>
 *  <li>id</li>
 *  <li>lang</li>
 *  <li>mini</li>
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
 *  <li>src</li>
 *  <li>style</li>
 *  <li>tabIndex</li>
 *  <li>title</li>
 *  <li>value</li>
 *  <li>visible</li>
 * </ul>
 *
 * Note: This function should only be invoked through setProps(). Further, the
 * widget shall be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.button.setWidgetProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.alt) { this.domNode.alt = props.alt; }
    if (props.align) { this.domNode.align = props.align; }
    if (props.src) { this.domNode.src = new dojo.uri.Uri(props.src).toString(); }

    // Set disabled.
    if (props.disabled != null) { 
        this.domNode.disabled = new Boolean(props.disabled).valueOf();
    }

    // Set value (i.e., button text).
    if (props.value) {
        // If escape is true, we want the text to be displayed literally. To 
        // achieve this behavior, do nothing.
        //
        // If escape is false, we want any special sequences in the text 
        // (e.g., "&nbsp;") to be displayed as evaluated (i.e., unescaped).
        this.domNode.value = (new Boolean(this.escape).valueOf() == false)
            ? props.value.unescapeHTML() // Prototype method.
            : props.value;
    }

    // Set more properties.
    this.setCommonProps(this.domNode, props);
    this.setJavaScriptProps(this.domNode, props);

    // Set core props.
    return webui.@THEME@.widget.button.superclass.setWidgetProps.call(this, props);
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.button, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.button, {
    // Set private functions
    fillInTemplate: webui.@THEME@.widget.button.fillInTemplate,
    getClassName: webui.@THEME@.widget.button.getClassName,
    getHoverClassName: webui.@THEME@.widget.button.getHoverClassName,
    getProps: webui.@THEME@.widget.button.getProps,
    initialize: webui.@THEME@.widget.button.initialize,
    refresh: webui.@THEME@.widget.button.refresh.processEvent,
    setWidgetProps: webui.@THEME@.widget.button.setWidgetProps,

    // Set defaults.
    disabled: false,
    escape: true,
    mini: false,
    primary: true,
    templatePath: webui.@THEME@.theme.getTemplatePath("button"),
    templateString: webui.@THEME@.theme.getTemplateString("button"),
    widgetType: "button"
});
