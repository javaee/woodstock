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

@JS_NS@._dojo.provide("@JS_NS@.widget.hyperlink");

@JS_NS@._dojo.require("@JS_NS@.widget._base.anchorBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.stateBase");

/**
 * This function is used to construct a hyperlink widget.
 *
 * @constructor
 * @name @JS_NS@.widget.hyperlink
 * @extends @JS_NS@.widget._base.anchorBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @extends @JS_NS@.widget._base.stateBase
 * @class This class contains functions for the hyperlink widget.
 * <p>
 * The hyperlink widget creates an HTML anchor that submits form data. If the 
 * disabled attribute of the hyperlink is set to true, clicking on the hyperlink
 * will not generate a request and hence the form will not be submitted or the 
 * page will not navigate to the specified url.
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
 *       id: "hyp1",
 *       contents: ["Click me"],
 *       href: "http://sun.com",
 *       target: "_blank",
 *       widgetType: "hyperlink"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 2: Update widget using the getProps and setProps functions</h3>
 * </p><p>
 * This example shows how to toggle the state of a widget using the
 * getProps and setProps functions. When the user clicks the checkbox, the
 * hyperlink is either disabled or enabled.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "hyp1",
 *       contents: ["Click me"],
 *       href: "http://sun.com",
 *       target: "_blank",
 *       widgetType: "hyperlink"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Toggle Hyperlink State" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("hyp1"); // Get hyperlink
 *       return widget.setProps({disabled: !domNode.getProps().disabled}); // Toggle state
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 3a: Asynchronously update widget using refresh function</h3>
 * </p><p>
 * This example shows how to asynchronously update a widget using the refresh 
 * function. When the user clicks on the checkbox, the hyperlink is 
 * asynchronously updated with new data.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "hyp1",
 *       contents: ["Click me"],
 *       href: "http://sun.com",
 *       target: "_blank",
 *       widgetType: "hyperlink"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Refresh Hyperlink" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("hyp1"); // Get hyperlink
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
 * This example shows how to asynchronously update an hyperlink using the refresh
 * function. The execute property of the refresh function is used to define the
 * client id which is to be submitted and updated server-side. As the user types
 * in the text field, the input value is updated server-side and the hyperlink text
 * is updated client-side -- all without a page refresh.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "hyp1",
 *       contents: ["Click me"],
 *       href: "http://sun.com",
 *       target: "_blank",
 *       widgetType: "hyperlink"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "field1",
 *       label: { value: "Change Hyperlink Text" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "textField"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("hyp1"); // Get hyperlink
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
 *            if (props.id == "hyp1") { // Do something... }
 *        }
 *    }
 *    // Subscribe to refresh event.
 *    woodstock.widget.common.subscribe(woodstock.widget.hyperlink.event.refresh.endTopic, 
 *      foo, "processRefreshEvent");
 * &lt;/script>
 * </code></pre>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} accessKey
 * @config {String} charset
 * @config {String} className CSS selector.
 * @config {Array} contents
 * @config {String} coords
 * @config {String} dir Specifies the directionality of text.
 * @config {boolean} disabled Disable element.
 * @config {String} formId The id of the HTML form element.
 * @config {String} href
 * @config {String} hrefLang
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {String} name 
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
 * @config {Array} params The parameters to be passed during request.
 * @config {String} rel
 * @config {String} rev
 * @config {String} shape
 * @config {String} style Specify style rules inline.
 * @config {int} tabIndex Position in tabbing order.
 * @config {String} title Provides a title for element.
 * @config {boolean} visible Hide or show element.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.hyperlink", [
        @JS_NS@.widget._base.refreshBase, 
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.anchorBase ], {
    // Set defaults.
    _widgetType: "hyperlink" // Required for theme properties.
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
@JS_NS@.widget.hyperlink.event =
        @JS_NS@.widget.hyperlink.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_hyperlink_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_hyperlink_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_hyperlink_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_hyperlink_event_state_end"
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
@JS_NS@.widget.hyperlink.prototype._getClassName = function() {
    var className = this._inherited("_getClassName", arguments);

    // Set default style.
    var newClassName = (this.disabled == true)
        ? this._theme.getClassName("HYPERLINK_DISABLED","")
        : this._theme.getClassName("HYPERLINK","");

    return (className)
        ? newClassName + " " + className
        : newClassName;
};

/**
 * Helper function to create callback for onClick event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.hyperlink.prototype._onClickCallback = function(event) {
    if (this.disabled == true) {
        event.preventDefault();
        return false;
    }

    // If function returns false, we must prevent the submit.
    var result = (this._domNode._onclick)
        ? this._domNode._onclick(event) : true;
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
        var form = this._widget._getForm(this._domNode);
        this.formId = (form) ? form.id : null;
    }
    return this._submitFormData(this.formId, this.params);
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
@JS_NS@.widget.hyperlink.prototype._postCreate = function () {
    // If the href attribute does not exist, set "#" as the default value of the
    // DOM node.
    this._domNode.href = "#";

    // Create callback function for onClick event.
    this._dojo.connect(this._domNode, "onclick", this, "_onClickCallback");

    return this._inherited("_postCreate", arguments);
};

/**
 * This function submits the hyperlink if the hyperlink is enabled.
 *
 * @param {String} formId The id of the HTML form element.
 * @param {Array} params The parameters to be passed during request.
 * @return {boolean} false to cancel the JavaScript event.
 * @private
 */
@JS_NS@.widget.hyperlink.prototype._submitFormData = function (formId, params) {
    if (formId == null) {
        return false;
    }
    var form = document.getElementById(formId);
    if (form == null) {
        return false;
    }
    var oldTarget = form.target;
    var oldAction = form.action;

    // Obtain HTML element for tab and common task components.
    var link = document.getElementById(this.id);
    if (link == null) {
        link = this._domNode;
    }

    // Set new action URL.
    var prefix;
    if (form.action) {
        prefix = (form.action.indexOf("?") == -1) ? "?" : "&";
        form.action += prefix + link.id + "_submittedLink=" + link.id;
    }
        
    // Set new target.
    if (link.target && link.target.length > 0) {
        form.target = link.target;
    } else {
        form.target = "_self";
    }

    // Append query params to new action URL.
    if (params != null) {
        var x;
        for (var i = 0; i < params.length; i++) {
            x = params[i];
            form.action += "&" + params[i] + "=" + params[i + 1]; 
            i++;
        }
    }

    // Submit form.
    form.submit(); 

    // Restore target and action URL.
    if (link.target != null) {
        form.target = oldTarget;
        form.action = oldAction;
    }
    return false;        
};
