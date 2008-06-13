/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */

@JS_NS@._dojo.provide("@JS_NS@.widget.button");

@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.stateBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");

/**
 * This function is used to construct a button widget.
 * 
 * @constructor
 * @name @JS_NS@.widget.button
 * @extends @JS_NS@.widget._base.widgetBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @extends @JS_NS@.widget._base.stateBase
 * @class This class contains functions for the button widget.
 * <p>
 * The button widget can render a button as a primary button or a secondary
 * button. A primary button is intended to be used for buttons that are the most
 * commonly used on a page or section of a page, and are rendered to be more
 * visually prominent than secondary buttons. The primary attribute is false by
 * default, which renders a secondary button.
 * </p><p>
 * <h3>Example 1: Create widget</h3>
 * </p><p>
 * This example shows how to create a widget using a span tag as a place holder 
 * in the document. Minimally, the createWidget() function needs an id and 
 * widgetType properties.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "btn1",
 *       value: "This is a button",
 *       widgetType: "button"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 2: Update widget using the getProps and setProps functions</h3>
 * </p><p>
 * This example shows how to toggle the state of a widget using the
 * getProps and setProps functions. When the user clicks the checkbox, the
 * button is either disabled or enabled.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "btn1",
 *       value: "This is a button",
 *       widgetType: "button"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Toggle Button State" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("btn1"); // Get button
 *       return widget.setProps({disabled: !domNode.getProps().disabled}); // Toggle state
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 3a: Asynchronously update widget using refresh function</h3>
 * </p><p>
 * This example shows how to asynchronously update a widget using the refresh 
 * function. When the user clicks on the checkbox, the button is 
 * asynchronously updated with new data.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "btn1",
 *       value: "This is a button",
 *       widgetType: "button"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Refresh Button" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("btn1"); // Get button
 *       return widget.refresh(); // Asynchronously refresh
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * Note that the refresh function can take an optional list of elements to 
 * execute. Thus, a comma-separated list of ids can be provided to update 
 * server-side components: refresh("id1,id2,..."). When no parameter is given, 
 * the refresh function acts as a reset. That is, the widget will be redrawn 
 * using values set server-side, but not updated.
 * </p><p>
 * <h3>Example 3b: Asynchronously update widget using refresh function</h3>
 * </p><p>
 * This example shows how to asynchronously update a button using the refresh
 * function. The execute property of the refresh function is used to define the
 * client id which is to be submitted and updated server-side. As the user types
 * in the text field, the input value is updated server-side and the button
 * label is updated client-side -- all without a page refresh.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "btn1",
 *       value: "This is a button",
 *       widgetType: "button"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "field1",
 *       label: { value: "Change Button Label" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "textField"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("btn1"); // Get button
 *       return widget.refresh("field1"); // Asynchronously refresh while submitting field value
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * Note that the refresh function can optionally take a list of elements to 
 * execute. Thus, a comma-separated list of ids can be provided to update 
 * server-side components: refresh("id1,id2,...").
 * </p><p>
 * <h3>Example 4: Subscribing to event topics</h3>
 * </p><p>
 * When a widget is manipulated, some features may publish event topics for
 * custom AJAX implementations to listen for. For example, you may listen for
 * the refresh event topic using:
 * </p><pre><code>
 * &lt;script type="text/javascript">
 *    var foo = {
 *        // Process refresh event.
 *        //
 *        // @param {Object} props Key-Value pairs of properties.
 *        processRefreshEvent: function(props) {
 *            // Get the widget id.
 *            if (props.id == "btn1") { // Do something... }
 *        }
 *    }
 *    // Subscribe to refresh event.
 *    woodstock.widget.common.subscribe(woodstock.widget.button.event.refresh.endTopic, 
 *      foo, "processRefreshEvent");
 * &lt;/script>
 * </code></pre>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} alt Alternate text for image input.
 * @config {String} align Alignment of image input.
 * @config {String} className CSS selector.
 * @config {String} dir Specifies the directionality of text.
 * @config {boolean} disabled Disable element.
 * @config {String} escape HTML escape value (default).
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {boolean} mini Set button as mini if true (N/A with src property).
 * @config {String} onBlur Element lost focus.
 * @config {String} onClick Mouse button is clicked on element.
 * @config {String} onDblClick Mouse button is double-clicked on element.
 * @config {String} onFocus Element received focus.
 * @config {String} onKeyDown Key is pressed down over element.
 * @config {String} onKeyPress Key is pressed and released over element.
 * @config {String} onKeyUp Key is released over element.
 * @config {String} onMouseDown Mouse button is pressed over element.
 * @config {String} onMouseOut Mouse is moved away from element.
 * @config {String} onMouseOver Mouse is moved onto element.
 * @config {String} onMouseUp Mouse button is released over element.
 * @config {String} onMouseMove Mouse is moved while over element.
 * @config {String} prefix The application context path of image.
 * @config {boolean} primary Set button as primary if true (N/A with src property).
 * @config {boolean} reset Set button as reset if true (overrides submit type).
 * @config {boolean} submit Set button as submit if true (default). If false,
 * the underlying HTML input type will be button.
 * @config {String} src Source for image (overrides submit and reset types).
 * @config {String} style Specify style rules inline.
 * @config {int} tabIndex Position in tabbing order.
 * @config {String} title Provides a title for element.
 * @config {String} value Value of input.
 * @config {boolean} visible Hide or show element.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.button", [
        @JS_NS@.widget._base.refreshBase, 
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.widgetBase ], {
    // Set defaults.
    constructor: function() {
        this.disabled = false;
        this.escape = true;
        this.mini = false;
        this.primary = true;
        this.reset = false;
        this.submit = true;
    },
    _widgetType: "button" // Required for theme properties.
});

/**
 * This function is used to render the widget from a template.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.button.prototype._buildRendering = function () {
    // On IE, the HTML input type property must be set when the DOM node is 
    // created. Therefore, we're using HTML templates to define the type.
    if (this.src != null) {
        this._templateType = "imageButton";
    } else if (new Boolean(this.reset).valueOf() == true) {
        this._templateType = "resetButton";
    } else if (new Boolean(this.submit).valueOf() == true) {
        this._templateType = "submitButton";
    }
    return this._inherited("_buildRendering", arguments);
}

/**
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
@JS_NS@.widget.button.event =
        @JS_NS@.widget.button.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** 
         * Refresh event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} execute Comma separated list of IDs to be processed server
         * side along with this widget.
         * </li><li>
         * {String} id The widget ID to process the event for.
         * </li></ul>
         *
         * @id @JS_NS@.widget.button.event.refresh.beginTopic
         * @property {String} beginTopic
         */
        beginTopic: "@JS_NS@_widget_button_event_refresh_begin",

        /** 
         * Refresh event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li><li>
         * Please see the constructor detail for additional properties.
         * </li></ul>
         *
         * @id @JS_NS@.widget.button.event.refresh.endTopic
         * @property {String} endTopic
         */
        endTopic: "@JS_NS@_widget_button_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /**
         * State event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li><li>
         * {Object} props Key-Value pairs of widget properties being updated.
         * </li></ul>
         *
         * @id @JS_NS@.widget.button.event.submit.beginTopic
         * @property {String} beginTopic
         */
        beginTopic: "@JS_NS@_widget_button_event_state_begin",

        /**
         * State event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li></ul>
         *
         * @id @JS_NS@.widget.button.event.submit.endTopic
         * @property {String} endTopic
         */
        endTopic: "@JS_NS@_widget_button_event_state_end"
    }
};

/**
 * This function is used to obtain the outermost HTML element class name.
 * <p>
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 * </p>
 * @return {String} The outermost HTML element class name.
 * @private
 */
@JS_NS@.widget.button.prototype._getClassName = function() {
    var className = this._inherited("_getClassName", arguments);
    var key = null;

    // If dealing with an image button, only the BUTTON3 selectors are used.
    // Note that the "mini" and "primary" values can still be set but
    // have no effect on image buttons by policy Vs by theme.
    if (this.src != null) {
        key = (this.disabled == true)
            ? "BUTTON3_DISABLED"
            : "BUTTON3";
    } else if (this.mini == true && this.primary == true) {
        key = (this.disabled == true)
            ? "BUTTON1_MINI_DISABLED" // primaryMiniDisabledClassName
            : "BUTTON1_MINI";         // primaryMiniClassName;
    } else if (this.mini == true) {
        key = (this.disabled == true)
            ? "BUTTON2_MINI_DISABLED" // secondaryMiniDisabledClassName
            : "BUTTON2_MINI";         // secondaryMiniClassName;
    } else if (this.primary == true) {
        key = (this.disabled == true)
            ? "BUTTON1_DISABLED"      // primaryDisabledClassName
            : "BUTTON1";              // primaryClassName
    } else {
        key = (this.disabled == true)
            ? "BUTTON2_DISABLED"      // secondaryDisabledClassName
            : "BUTTON2";	      // secondaryClassName
    }

    var newClassName = this._theme.getClassName(key, "");
    return (className)
        ? newClassName + " " + className
        : newClassName;
};

/**
 * This function is used to obtain the outermost HTML element class name during
 * an onFocus or onMouseOver event.
 * <p>
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 * </p>
 * @return {String} The outermost HTML element class name.
 * @private
 */
@JS_NS@.widget.button.prototype._getHoverClassName = function() {
    // Cannot call this._inherited("_getClassName", arguments) here.
    var className = @JS_NS@.widget.button.superclass._getClassName.apply(this, arguments);
    var key = null;

    // If dealing with an image button, only the BUTTON3 selectors are used.
    // Note that the "mini" and "primary" values can still be set but
    // have no effect on image buttons by policy Vs by theme.
    if (this.src != null) {
        key = "BUTTON3_HOVER";
    } else if (this.mini == true && this.primary == true) {
        key = "BUTTON1_MINI_HOVER"; 	// primaryMiniHovClassName;
    } else if (this.mini == true) {
        key = "BUTTON2_MINI_HOVER"; 	// secondaryMiniHovClassName;
    } else if (this.primary == true) {
        key = "BUTTON1_HOVER"; 		// primaryHovClassName;
    } else {
        key = "BUTTON2_HOVER";		// secondaryHovClassName;
    }

    var newClassName = this._theme.getClassName(key, "");
    return (className)
        ? newClassName + " " + className
        : newClassName;
};
    
/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @id @JS_NS@.widget.button.getProps
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.button.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.alt != null) { props.alt = this.alt; }
    if (this.align != null) { props.align = this.align; }
    if (this.disabled != null) { props.disabled = this.disabled; }
    if (this.escape != null) { props.escape = this.escape; }
    if (this.mini != null) { props.mini = this.mini; }
    if (this.primary != null) { props.primary = this.primary; }
    if (this.src != null) { props.src = this.src; }
    if (this.value != null) { props.value = this.value; }

    return props;
};

/**
 * Helper function to create callback for onBlur event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.button.prototype._onBlurCallback = function(event) {
    if (this.disabled == true) {
        return true;
    }
    // Prevent errors during page submit, when modules have not been loaded.
    try {
        // Set style class.
        this._domNode.className = this._getClassName();
    } catch (err) {}
    return true;
};

/**
 * Helper function to create callback for onFocus event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.button.prototype._onFocusCallback = function(event) {
    if (this.disabled == true) {
        return true;
    }
    // Prevent errors during page submit, when modules have not been loaded.
    try {
        // Set style class.
        this._domNode.className = this._getHoverClassName();
    } catch (err) {}
    return true;
};

/**
 * This function is used to fill in remaining template properties, after the
 * _buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.button.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {
        this._domNode.name = this.id;
    }

    // Initialize deprecated public functions. 
    //
    // Note: Although we now have a setProps function to update properties,
    // these functions were previously added to the DOM node; thus, we must
    // continue to be backward compatible.
    
    /** @ignore */
    this._domNode.isSecondary = function() { return !(@JS_NS@.widget.common.getWidget(this.id).getProps().primary); };
    /** @ignore */
    this._domNode.setSecondary = function(secondary) { return @JS_NS@.widget.common.getWidget(this.id).setProps({primary: !secondary}); };
    /** @ignore */
    this._domNode.isPrimary = function() { return @JS_NS@.widget.common.getWidget(this.id).getProps().primary; };
    /** @ignore */
    this._domNode.setPrimary = function(primary) { return @JS_NS@.widget.common.getWidget(this.id).setProps({primary: primary}); };
    /** @ignore */
    this._domNode.isMini = function() { return @JS_NS@.widget.common.getWidget(this.id).getProps().mini; };
    /** @ignore */
    this._domNode.setMini = function(mini) { return @JS_NS@.widget.common.getWidget(this.id).setProps({mini: mini}); };
    /** @ignore */
    this._domNode.getDisabled = function() { return @JS_NS@.widget.common.getWidget(this.id).getProps().disabled; };
    /** @ignore */
    this._domNode.setDisabled = function(disabled) { return @JS_NS@.widget.common.getWidget(this.id).setProps({disabled: disabled}); };
    /** @ignore */
    this._domNode.getVisible = function() { return @JS_NS@.widget.common.getWidget(this.id).getProps().visible; };
    /** @ignore */
    this._domNode.setVisible = function(show) { return @JS_NS@.widget.common.getWidget(this.id).setProps({visible: show}); };
    /** @ignore */
    this._domNode.getText = function() { return @JS_NS@.widget.common.getWidget(this.id).getProps().value; };
    /** @ignore */
    this._domNode.setText = function(text) { return @JS_NS@.widget.common.getWidget(this.id).setProps({value: text}); };
    this._domNode.doClick = this._domNode.click;

    // Set events.
    this._dojo.connect(this._domNode, "onblur", this, "_onBlurCallback");
    this._dojo.connect(this._domNode, "onfocus", this, "_onFocusCallback");
    this._dojo.connect(this._domNode, "onmouseout", this, "_onBlurCallback");
    this._dojo.connect(this._domNode, "onmouseover", this, "_onFocusCallback");

    return this._inherited("_postCreate", arguments);
};

/**
 * This function is used to set widget properties. Please see the constructor 
 * detail for a list of supported properties.
 * <p>
 * Note: This function should only be invoked through setProps().
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.button.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.alt != null) { this._domNode.alt = props.alt; }
    if (props.align != null) { this._domNode.align = props.align; }

    // Set disabled.
    if (props.disabled != null) { 
        this._domNode.disabled = new Boolean(props.disabled).valueOf();
    }

    // Set image source.
    if (props.src) {
        // If context path is provided, then check whether the image has
        // context path already appended and if not, append it.
        if (this.prefix) {
            props.src = 
                @JS_NS@.widget.common._appendPrefix(this.prefix, props.src);                
        }
        this._domNode.src = props.src; 
    }

    // Set value -- an empty string is valid.
    if (props.value != null) {
        // If escape is true, we want the text to be displayed literally. To 
        // achieve this behavior, do nothing.
        //
        // If escape is false, we want any special sequences in the text 
        // (e.g., "&nbsp;") to be displayed as evaluated (i.e., unescaped).
        this._domNode.value = (new Boolean(this.escape).valueOf() == false)
            ? this._proto._unescapeHTML(props.value)
            : props.value;
    }

    // Set more properties.
    this._setCommonProps(this._domNode, props);
    this._setEventProps(this._domNode, props);

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
