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

dojo.require("dojo.uri.Uri");
dojo.require("dojo.widget.*");
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
 * This closure is used to process widget events.
 */
webui.@THEME@.widget.button.event = {
    /**
     * This closure is used to process refresh events.
     */
    refresh: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_button_event_refresh_begin",
        endTopic: "webui_@THEME@_widget_button_event_refresh_end"
    },

    /**
     * This closure is used to process state change events.
     */
    state: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_button_event_state_begin",
        endTopic: "webui_@THEME@_widget_button_event_state_end"
    }
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
webui.@THEME@.widget.button.fillInTemplate = function(props, frag) {
    webui.@THEME@.widget.button.superclass.fillInTemplate.call(this, props, frag);

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

    return true;
}

/**
 * This function is used to obtain the outermost HTML element class name.
 *
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 */
webui.@THEME@.widget.button.getClassName = function() {

    var key = null;

    // If it is an image button only the BUTTON3 selectors are used.
    // There should a symmetric set of img button properties.
    // since the "mini" and "primary" values can still be set but
    // have no effect on image buttons by policy, vs by theme.
    //
    if (this.src != null) {
	key = (this.disabled == true)
	    ? "BUTTON3_DISABLED"
	    : "BUTTON3";
    } else 
    if (this.mini == true && this.primary == true) {
        key = (this.disabled == true)
            ? "BUTTON1_MINI_DISABLED" //primaryMiniDisabledClassName
            : "BUTTON1_MINI";         //primaryMiniClassName;
    } else if (this.mini == true) {
        key = (this.disabled == true)
            ? "BUTTON2_MINI_DISABLED" //secondaryMiniDisabledClassName
            : "BUTTON2_MINI";         //secondaryMiniClassName;
    } else if (this.primary == true) {
        key = (this.disabled == true)
            ? "BUTTON1_DISABLED"      //primaryDisabledClassName
            : "BUTTON1";              //primaryClassName
    } else {
        key = (this.disabled == true)
            ? "BUTTON2_DISABLED"      //secondaryDisabledClassName
            : "BUTTON2";	      //secondaryClassName
    }
    var className = this.widget.getClassName(key, "");
    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * This function is used to obtain the outermost HTML element class name.
 *
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 */
webui.@THEME@.widget.button.getHoverClassName = function() {

    var key = null;

    // If this is an image button
    //
    if (this.src != null) {
	key = "BUTTON3_HOVER";
    } else
    if (this.mini == true && this.primary == true) {
        key = "BUTTON1_MINI_HOVER"; 	//primaryMiniHovClassName;
    } else if (this.mini == true) {
        key = "BUTTON2_MINI_HOVER"; 	//secondaryMiniHovClassName;
    } else if (this.primary == true) {
        key = "BUTTON1_HOVER"; 		//primaryHovClassName;
    } else {
        key = "BUTTON2_HOVER";		//secondaryHovClassName;
    }
    var className = this.widget.getClassName(key, "");
    return (this.className)
        ? className + " " + this.className
        : className;
}
    
/**
 * This function is used to get widget properties. Please see the 
 * _setProps() function for a list of supported properties.
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
 * Note: This is considered a private API, do not use. This function should only
 * be invoked through postInitialize() and setProps(). Further, the widget shall
 * be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.button._setProps = function(props) {
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
    this.setEventProps(this.domNode, props);

    // Set remaining properties.
    return webui.@THEME@.widget.button.superclass._setProps.call(this, props);
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.button, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.button, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.button.fillInTemplate,
    getClassName: webui.@THEME@.widget.button.getClassName,
    getHoverClassName: webui.@THEME@.widget.button.getHoverClassName,
    getProps: webui.@THEME@.widget.button.getProps,
    _setProps: webui.@THEME@.widget.button._setProps,

    // Set defaults.
    disabled: false,
    escape: true,
    event: webui.@THEME@.widget.button.event,
    mini: false,
    primary: true,
    widgetType: "button"
});
