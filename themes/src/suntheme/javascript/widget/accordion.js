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

@JS_NS@._dojo.provide("@JS_NS@.widget.accordion");

@JS_NS@._dojo.require("@JS_NS@.widget.accordionTab");
@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.stateBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");

/**
 * This function is used to construct an accordion widget.
 *
 * @constructor
 * @name @JS_NS@.widget.accordion
 * @extends @JS_NS@.widget._base.widgetBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @extends @JS_NS@.widget._base.stateBase
 * @class This class contains functions for the accordion widget. 
 * <p>
 * The accordion widget can be thought of as a vertical tab set. This set can
 * contain one or more tabs each of which can contain virtually any HTML markup
 * and have the following characteristics:
 * <ul><li>
 * Each tab contains links to different sections(tasks) of the application.
 * </li><li>
 * Only one accordion tab can be open at any given time.
 * </li></ul>
 * The accordion widget allows muliple tabs to be open simultaneously. Consider 
 * using an alternate navigational component if the number of tabs in the 
 * accordion exceeds ten. The accordion tag also supports "expandAll" and 
 * "collapseAll" buttons. Unless otherwise specified the accordion dows not 
 * display the expand all, collapse all and refresh icons. To display these 
 * icons, set the toggleControls and refreshIcon attributes to true. Note that 
 * an accordion can be refreshed if the application using it supports this 
 * feature.
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
 *       id: "acc1",
 *       toggleControls: true,
 *       tabs: [{
 *         id: "tab1",
 *         contentHeight: "50px",
 *         tabContent: [{
 *           id: "st1",
 *           value: "This is tab one",
 *           widgetType: "text"
 *         }],
 *         title: "Tab One",
 *         widgetType: "accordionTab"
 *       }, {
 *         id: "tab2",
 *         contentHeight: "50px",
 *         tabContent: [{
 *           id: "st2",
 *           value: "This is tab two",
 *           widgetType: "text"
 *         }],
 *         title: "Tab Two",
 *         widgetType: "accordionTab"
 *       }],
 *       widgetType: "accordion"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 2: Update widget using the getProps and setProps functions</h3>
 * </p><p>
 * This example shows how to toggle the state of a widget using the
 * getProps and setProps functions. When the user clicks the checkbox, the
 * multipleSelect state is either enabled or disabled.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "acc1",
 *       toggleControls: true,
 *       tabs: [{
 *         id: "tab1",
 *         contentHeight: "50px",
 *         tabContent: [{
 *           id: "st1",
 *           value: "This is tab one",
 *           widgetType: "text"
 *         }],
 *         title: "Tab One",
 *         widgetType: "accordionTab"
 *       }, {
 *         id: "tab2",
 *         contentHeight: "50px",
 *         tabContent: [{
 *           id: "st2",
 *           value: "This is tab two",
 *           widgetType: "text"
 *         }],
 *         title: "Tab Two",
 *         widgetType: "accordionTab"
 *       }],
 *       widgetType: "accordion"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Toggle Accordion State" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("acc1"); // Get accordion
 *       return widget.setProps({multipleSelect: !domNode.getProps().multipleSelect}); // Toggle state
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 3a: Asynchronously update widget using refresh function</h3>
 * </p><p>
 * This example shows how to asynchronously update a widget using the refresh 
 * function. When the user clicks on the checkbox, the accordion is 
 * asynchronously updated with new data.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "acc1",
 *       toggleControls: true,
 *       tabs: [{
 *         id: "tab1",
 *         contentHeight: "50px",
 *         tabContent: [{
 *           id: "st1",
 *           value: "This is tab one",
 *           widgetType: "text"
 *         }],
 *         title: "Tab One",
 *         widgetType: "accordionTab"
 *       }, {
 *         id: "tab2",
 *         contentHeight: "50px",
 *         tabContent: [{
 *           id: "st2",
 *           value: "This is tab two",
 *           widgetType: "text"
 *         }],
 *         title: "Tab Two",
 *         widgetType: "accordionTab"
 *       }],
 *       widgetType: "accordion"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Refresh Accordion" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("acc1"); // Get accordion
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
 * This example shows how to asynchronously update a accordion using the refresh
 * function. The execute property of the refresh function is used to define the
 * client id which is to be submitted and updated server-side. As the user types
 * in the text field, the input value is updated server-side and the accordion text
 * is updated client-side -- all without a page refresh.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "acc1",
 *       toggleControls: true,
 *       tabs: [{
 *         id: "tab1",
 *         contentHeight: "50px",
 *         tabContent: [{
 *           id: "st1",
 *           value: "This is tab one",
 *           widgetType: "text"
 *         }],
 *         title: "Tab One",
 *         widgetType: "accordionTab"
 *       }, {
 *         id: "tab2",
 *         contentHeight: "50px",
 *         tabContent: [{
 *           id: "st2",
 *           value: "This is tab two",
 *           widgetType: "text"
 *         }],
 *         title: "Tab Two",
 *         widgetType: "accordionTab"
 *       }],
 *       widgetType: "accordion"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "field1",
 *       label: { value: "Change Accordion Title" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "textField"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("acc1"); // Get accordion
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
 *            if (props.id == "acc1") { // Do something... }
 *        }
 *    }
 *    // Subscribe to refresh event.
 *    woodstock.widget.common.subscribe(woodstock.widget.accordion.event.refresh.endTopic, 
 *      foo, "processRefreshEvent");
 * &lt;/script>
 * </code></pre>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} className CSS selector.
 * @config {Object} collapseAllImage Image associated with collapse all icon
 * @config {Object} expandAllImage Image associated with expand all icon
 * @config {String} id Uniquely identifies an element within a document.
 * @config {boolean} isRefreshIcon True if refresh icon should be set
 * @config {boolean} multipleSelect Set to true if multiple tabs can be selected
 * @config {Object} refreshImage Image associated with refresh icon.
 * @config {String} style Specify style rules inline.
 * @config {Array} tabs Tabs constituting the accordion's children
 * @config {boolean} toggleControls Set to true if expand/collapse icons should be set.
 * @config {boolean} visible Hide or show element.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.accordion", [
        @JS_NS@.widget._base.refreshBase,
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.widgetBase ], {
    // Set defaults.
    constructor: function() {
        this.duration = 250;
        this.isContainer = true;
        this.loadOnSelect = false;
        this.multipleSelect = false;
        this.focusElement = null;
    },
    _widgetType: "accordion" // Required for theme properties.
});

/**
 * Helper function to add accordion header controls
 *
 * @param {Object} props Key-Value pairs of properties.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordion.prototype._addControls = function(props) {       
    // Add expand and collapse icons only if multiple select is set to
    // true and the icons have been supplied.
    if (props.toggleControls && props.multipleSelect) {
        // Set expand all image properties.
        if (props.expandAllImage) {
            // Update/add fragment.
            this._widget._updateFragment(this._expandAllImgContainer, 
                this.expandAllImage.id, props.expandAllImage);
        }

        // Set collapse all image properties.
        if (props.collapseAllImage) {
            // Update/add fragment.
            this._widget._updateFragment(this._collapseAllImgContainer,
                this.collapseAllImage.id, props.collapseAllImage);
        }
        
        // a divider should only be added if expand/collapse icons exist.
        this._dividerNodeContainer.className = this._theme.getClassName("ACCORDION_HDR_DIVIDER");
    }

    // Set refresh image properties.
    if (props.isRefreshIcon && props.refreshImage) {
        // Update/add fragment.
        this._widget._updateFragment(this._refreshImgContainer,
            this.refreshImage.id, props.refreshImage);
    }
    return true;
};

/**
 * Close all open accordions and leave the others as is.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordion.prototype._collapseAllTabs = function(event) {
    // Iterate over all tabs.
    for (var i = 0; i < this.tabs.length; i++) {
        var widget = this._widget.getWidget(this.tabs[i].id);
        if (widget && widget.selected) {
            widget._setSelected(false);
        }
    }
    this._updateFocus(this._collapseAllContainer);
    return true;
};

/**
 * The callback function for key press on the accordion header.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordion.prototype._createOnKeyDownCallBack = function() {
    var _id = this.id;
    return function(event) {
        var elem = document.getElementById(_id);
        if (elem == null) {
            return false;
        }
        var common = @JS_NS@.widget.common;
        var widget = common.getWidget(_id);

        event = common._getEvent(event);
        var keyCode = common._getKeyCode(event);
        
        // if onkeypress returns false, we do not traverse the accordion
        var keyPressResult = true;
        if (this._onkeypress) {
            keyPressResult = (this._onkeypress) ? this._onkeypress() : true;
        }        
       
        if (keyPressResult != false) {
            widget._traverseMenu(keyCode, event, _id);
        }
        return true;
   };
};

/**
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
@JS_NS@.widget.accordion.event = 
        @JS_NS@.widget.accordion.prototype.event = {
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
         * @id @JS_NS@.widget.accordion.event.refresh.beginTopic
         * @property {String} beginTopic
         */
        beginTopic: "@JS_NS@_widget_accordion_event_refresh_begin",

        /** 
         * Refresh event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li><li>
         * Please see the constructor detail for additional properties.
         * </li></ul>
         *
         * @id @JS_NS@.widget.accordion.event.refresh.endTopic
         * @property {String} endTopic
         */
        endTopic: "@JS_NS@_widget_accordion_event_refresh_end"
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
         * @id @JS_NS@.widget.accordion.event.submit.beginTopic
         * @property {String} beginTopic
         */
        beginTopic: "@JS_NS@_widget_accordion_event_state_begin",

        /**
         * State event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li></ul>
         *
         * @id @JS_NS@.widget.accordion.event.submit.endTopic
         * @property {String} endTopic
         */
        endTopic: "@JS_NS@_widget_accordion_event_state_end"
    }
};

/**
 * Open all closed tabs and leave the others as is.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordion.prototype._expandAllTabs = function(event) {
    // Iterate over all tabs.
    for (var i = 0; i < this.tabs.length; i++) {
        var widget = this._widget.getWidget(this.tabs[i].id);
        if (widget && !widget.selected) {
            widget._setSelected(true);
        }
    }
    this._updateFocus(this._expandAllContainer);
    return true;
};

/**
 * Set the appropriate focus before invoking tabSelected. This
 * function has been put in place because tabSelected is called
 * from various places but the focus does not have to be set
 * in all the cases.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordion.prototype._focusAndSelectTab = function(props) {
    // Iterate over all tabs to ensure id is valid.
    for (var i = 0; i < this.tabs.length; i++) {
        if (props.id == this.tabs[i].id) {
            this.focusElement = this.tabs[i];
            break;   
        }
    }
    this.tabSelected(props);
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.accordion.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.collapseAllImage != null) { props.collapseAllImage = this.collapseAllImage; }
    if (this.expandAllImage != null) { props.expandAllImage = this.expandAllImage; }
    if (this.isRefreshIcon != null) { props.isRefreshIcon = this.isRefreshIcon; }
    if (this.loadOnSelect != null) { props.loadOnSelect = this.loadOnSelect; }
    if (this.multipleSelect != null) { props.multipleSelect = this.multipleSelect; }
    if (this.refreshImage != null) { props.refreshImage = this.refreshImage; }
    if (this.style != null) { props.style = this.style; }
    if (this.tabs != null) { props.tabs = this.tabs; }
    if (this.toggleControls != null) { props.toggleControls = this.toggleControls; }
    if (this.type != null) { props.type = this.type; }
 
    return props;
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
@JS_NS@.widget.accordion.prototype._getClassName = function() {
    var className = this._inherited("_getClassName", arguments);
    var newClassName = this._theme.getClassName("ACCORDION_DIV", "");
    return (className)
        ? newClassName + " " + className
        : newClassName;
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
@JS_NS@.widget.accordion.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {
        this._domNode.id = this.id;
        this._headerContainer.id = this.id + "_accHeader";
        this._expandAllContainer.id = this.id + "_expandAllNode";
        this._expandAllImgContainer.id = this._expandAllContainer.id + "_expandAllImage";
        this._collapseAllContainer.id = this.id + "_collapseAllNode";
        this._collapseAllImgContainer.id = this._collapseAllImgContainer.id + "_collapseAllImage";
        this._dividerNodeContainer.id = this.id + "_dividerNode";
        this._refreshNodeContainer.id = this.id + "_refreshNode";
    }

    // Set class names.
    this._headerContainer.className = this._theme.getClassName("ACCORDION_HDR");
    this._collapseAllContainer.className = this._theme.getClassName("ACCORDION_HDR_CLOSEALL");
    this._expandAllContainer.className = this._theme.getClassName("ACCORDION_HDR_OPENALL");
    
    // the divider should initially be hidden
    this._dividerNodeContainer.className = this._theme.getClassName("HIDDEN");
    this._refreshNodeContainer.className = this._theme.getClassName("ACCORDION_HDR_REFRESH");

    // Set events.
    var _id = this.id;
    this._dojo.connect(this._collapseAllContainer, "onclick", this, "_collapseAllTabs");
    this._dojo.connect(this._expandAllContainer, "onclick", this, "_expandAllTabs");
    this._dojo.connect(this._refreshNodeContainer, "onclick", function(event) {
        // New literals are created every time this function is called, and it's 
        // saved by closure magic.
        var widget = @JS_NS@.widget.common.getWidget(_id);
        widget._updateFocus(this._refreshNodeContainer);
        widget.refresh(_id);
        return false;
    });
    
    // Create callback function for onkeydown event.
    this._dojo.connect(this._domNode, "onkeydown", this._createOnKeyDownCallBack());
    
    // Subscribe to the "tab selected" event present in the accordion widget.
    this._widget.subscribe(@JS_NS@.widget.accordionTab.event.title.selectedTopic,
        this, "_focusAndSelectTab");

    // Generate the accordion header icons on the client side.
    if (this.toggleControls && this.multipleSelect) {
        if (this.expandAllImage == null) {
            this.expandAllImage = {
                id: this.id + "_expandImageLink",
                onClick: "return false;",
                enabledImage: {
                    icon: "ACCORDION_EXPAND_ALL",
                    id: this.id + "_expandAll",
                    widgetType: "image"
                },
                title: this._theme.getMessage("Accordion.expandAll"),
                widgetType: "imageHyperlink"
            };
        }
        
        if (this.collapseAllImage == null) {
            this.collapseAllImage = {
                id: this.id + "_collapseImageLink",
                onClick: "return false;",
                enabledImage: {
                    icon: "ACCORDION_COLLAPSE_ALL",
                    id: this.id + "_collapseAll",
                    widgetType: "image"
                },
                title: this._theme.getMessage("Accordion.collapseAll"),
                widgetType: "imageHyperlink"
            };
        }
    }

    // Set refresh image hyperlink properties.
    if (this.isRefreshIcon) {
        if (this.refreshImage == null) {
            this.refreshImage = {
                id: this.id + "_refreshImageLink",
                onClick: "return false;",
                enabledImage: {
                    icon: "ACCORDION_REFRESH",
                    id: this.id + "_refresh",
                    widgetType: "image"
                },
                title: this._theme.getMessage("Accordion.refresh"),
                widgetType: "imageHyperlink"
            };
         }
    }
    
    if (this.isRefreshIcon) {
        this._refreshNodeContainer.tabIndex = this.tabIndex;
    }
    if (this.toggleControls && this.multipleSelect) {
            this._expandAllContainer.tabIndex = this.tabIndex;
            this._collapseAllContainer.tabIndex = this.tabIndex;
    }
    if (this.tabs.length > 0) {
        for (var i=0; i< this.tabs.length; i++) {
            this.tabs[i].tabIndex = this.tabIndex;
        }
    }

    with (this._domNode.style) {
        if (position != "absolute") {
            position = "relative";
        }
        overflow = "hidden";
    }
    return this._inherited("_postCreate", arguments);
};

/**
 * This function is used to set widget properties using Object literals. Please
 * see the constructor detail for a list of supported properties.
 * <p>
 * Note: This function extends the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 * </p><p>
 * If the notify param is true, the widget's state change event shall be
 * published. This is typically used to keep client-side state in sync with the
 * server.
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.accordion.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace tabs -- do not extend.
    if (props.tabs) {
        this.tabs = null;
    }

    // Extend widget object for later updates.
    return this._inherited("setProps", arguments);
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
@JS_NS@.widget.accordion.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    if (props.tabIndex == null) {
        props.tabIndex = -1;
        this.tabIndex = -1;
    }

    // add control icons - refresh, expandall, collapseall.
    this._addControls(props); 

    // If we are coming here for
    // the first time there will be no children. The other case is when 
    // the accordion is being rerendered because of a refresh in which 
    // we want to use the latest set of children. _addFragment is supposed
    // to do that.
    if (props.tabs) {
        // Remove child nodes.
        this._widget._removeChildNodes(this._tabsContainer);

        // set the tab focus 
        var tabFocus = true;
        
        if (props.tabs.length > 0) {
            for (var i=0; i< props.tabs.length; i++) {
                props.tabs[i].tabIndex = this.tabIndex;
            }
        }
       
        // Add tabs.
        for (var i = 0; i < props.tabs.length; i++) {
            this._widget._addFragment(this._tabsContainer, props.tabs[i], "last");
        }
    }

    // Set more properties.
    this._setCommonProps(this._domNode, props);
    this._setEventProps(this._domNode, props);

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};

/**
 * Set a different look for refresh/expand/collapse icons when focus is set
 * on them.
 *
 * @param {String} nodeId The node ID of the icon.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordion.prototype._setFocusStyleClass = function(nodeId) {
    if (nodeId == this._collapseAllContainer.id) {
        //set focus style on collapseNode
        this._collapseAllContainer.className = this._theme.getClassName("ACCORDION_HDR_CLOSEALL_FOCUS");
    } else if (nodeId == this._expandAllContainer.id) {
        //set focus style on expandNode
        this._expandAllContainer.className = this._theme.getClassName("ACCORDION_HDR_OPENALL_FOCUS");
    } else if (nodeId == this._refreshNodeContainer.id) {
        //set focus style on refreshNode
        this._refreshNodeContainer.className = this._theme.getClassName("ACCORDION_HDR_REFRESH_FOCUS");
    }
    return true;
};

/**
 * Reset the styles asscociated with refresh/expand/collapse icons when these 
 * icons are blurred.
 *
 * @param {String} nodeId The node ID of the icon.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordion.prototype._setBlurStyleClass = function(nodeId) {
    if (nodeId == this._collapseAllContainer.id) {
        //set normal className on collapseNode
        this._collapseAllContainer.className = this._theme.getClassName("ACCORDION_HDR_CLOSEALL");
    } else if (nodeId == this._expandAllContainer.id) {
        //set normal className on expandNode
        this._expandAllContainer.className = this._theme.getClassName("ACCORDION_HDR_OPENALL");
    } else if (nodeId == this._refreshNodeContainer.id) {
        //set normal className on refreshNode
        this._refreshNodeContainer.className = this._theme.getClassName("ACCORDION_HDR_REFRESH");
    }
    return true;
};

/**
 * Set appropriate styles when a tab is in focus.
 *
 * @param {String} nodeId The node ID of the icon.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordion.prototype._setTabFocus = function(nodeId) {
    // update the tab with the appropriate tabIndex
    var tabWidget = this._widget.getWidget(nodeId);
    var props = {tabIndex: this.tabIndex};
    this._widget._updateFragment(this._tabsContainer, nodeId, props);

    // set the style class to indicate that the tab is in focus.
    if (tabWidget.selected) {
        tabWidget._titleContainer.className = this._theme.getClassName("ACCORDION_TABEXPANDED_FOCUS");
    } else {
        tabWidget._titleContainer.className = this._theme.getClassName("ACCORDION_TABCOLLAPSED_FOCUS");
    }
    tabWidget._domNode.focus();
    return true;
};

/**
 * Set appropriate styles when a tab loses focus.
 *
 * @param {String} nodeId The node ID of the icon.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordion.prototype._setTabBlur = function(nodeId) {
    // update the tab with the appropriate tabIndex
    var tabWidget = this._widget.getWidget(nodeId);
    
    if (tabWidget.selected) {
        tabWidget._titleContainer.className = this._theme.getClassName("ACCORDION_TABEXPANDED");
        tabWidget._turnerContainer.className = this._theme.getClassName("ACCORDION_DOWNTURNER");
    } else { 
        tabWidget._titleContainer.className = this._theme.getClassName("ACCORDION_TABCOLLAPSED");
        tabWidget._turnerContainer.className = this._theme.getClassName("ACCORDION_RIGHTTURNER");
    }    

    if (tabWidget) {
        tabWidget._titleContainer.blur();
    }
    return true;
};

/**
 * Process tab selected events.
 *
 * @param props Key-Value pairs of properties.
 * @config {String} id The id of the selected tab.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordion.prototype.tabSelected = function(props) {
    var widget = null;

    // Iterate over all tabs to ensure id is valid.
    for (var i = 0; i < this.tabs.length; i++) {
        if (props.id == this.tabs[i].id) {
            widget = this._widget.getWidget(this.tabs[i].id);
            break;   
        }
    }
    
    // Return if id was not valid.
    if (widget == null) {
            return false;
    }
    
    if (this.multipleSelect) {
        widget._setSelected(true);
    } else {
        for (var i = 0; i < this.tabs.length; i++) {
            widget = this._widget.getWidget(this.tabs[i].id);
            if (widget) {
                widget._setSelected(props.id == this.tabs[i].id);
                this.tabs[i] = widget.getProps();
            }
        }
    }
    return true;
};

/**
 * This function traverses through the accordion depending
 * on which key is pressed. If the down or right arrow key is pressed the
 * next focusable element of the accordion is focused on. If the last element 
 * was on focus this changes to the first element. If the enter or space key
 * was pressed the onclick function associated with the focused element is 
 * activated. 
 * @param (String) keyCode The valye of the key which was pressed
 * @param (Event) event The key press event.
 * @param (String) nodeId The id of the accordion item. 
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordion.prototype._traverseMenu = function(keyCode, event, nodeId) {
    var savedFocusElement;

    if (this.focusElement != null) {
        savedFocusElement = this.focusElement;
    }

    // Operations to be performed if the arrow keys are pressed.
    // down or right arrow key ==> forward
    // up or left arrow key ==> backward
    // During forward traversal control should go from the refresh icon
    // to the collapseAll icon to the expandAll icon to the tabs from
    // top to bottom. If control is on the last tab, clicking a forward
    // key will cause control to come back to the first tab and not the
    // accordion control icons. A similar but opposite behavior is 
    // exhibited when the up or left keys are pressed. Once control is
    // on the first tab backward traversal will take control to the last 
    // tab in the accordion.

    if (keyCode >= 37 && keyCode <= 40) {
        var forward = true;
        if (keyCode == 37 || keyCode == 38) {
            forward = false;
        }
        var focusSet = false;
        if (forward) {  // handling the down and right arrow keys
            if (this.isRefreshIcon) {
                if (this.focusElement.id == this._refreshNodeContainer.id) {
                    if (this.toggleControls && this.multipleSelect) {
                        this._updateFocus(this._collapseAllContainer);
                        focusSet = true;
                    } else {
                        this._updateFocus(this.tabs[0]);
                        focusSet = true;
                    }
                }
            }
            
            if (!focusSet && this.toggleControls && this.multipleSelect) {
                
                if (this.focusElement.id == this._collapseAllContainer.id) {
                    this._updateFocus(this._expandAllContainer);
                    focusSet = true;

                } else if (this.focusElement.id == this._expandAllContainer.id) {
                    this._updateFocus(this.tabs[0]);
                    focusSet = true;
                
                } else {
                    for (var i = 0; i < this.tabs.length; i++) {
                        if (this.tabs[i].id == this.focusElement.id) {
                            var newIndex = (i + 1) % this.tabs.length;
                            this._updateFocus(this.tabs[newIndex]);
                            focusSet = true;
                            break;
                        }
                    }
                }
            }
            if (!focusSet) {
                for (var i = 0; i < this.tabs.length; i++) {
                    if (this.tabs[i].id == this.focusElement.id) {
                        var newIndex = (i + 1) % this.tabs.length;
                        this._updateFocus(this.tabs[newIndex]);
                        focusSet = true;
                        break;
                    }
                }
                if (this.focusElement == null) {
                    this._updateFocus(this.tabs[0]);
                }
            }

         } else {  // traverse backward

            if (this.isRefreshIcon) {
                if (this.focusElement.id == this._refreshNodeContainer.id) {
                    var index = this.tabs.length;
                    this._updateFocus(this.tabs[index-1]);
                    focusSet = true;
                }
            }
            
            if (!focusSet && this.toggleControls && this.multipleSelect) {
                if (this.focusElement.id == this._collapseAllContainer.id) {
                    if (this.isRefreshIcon) {
                        this._updateFocus(this._refreshNodeContainer);
                        focusSet = true;
                    } else {
                        var index = this.tabs.length;
                        focusSet = true;
                        this._updateFocus(this.tabs[index-1]);
                    }
 
                } else if (this.focusElement.id == this._expandAllContainer.id) {
                    this._updateFocus(this._collapseAllContainer);
                    focusSet = true;
                }
            }
            if (!focusSet) {
                for (var i = 0; i < this.tabs.length; i++) {
                    if (this.tabs[i].id == this.focusElement.id) {
                        if (i == 0) {
                            var index = this.tabs.length;
                            this._updateFocus(this.tabs[index-1]);
                            focusSet = true;
                            break;
                            
                        } else {
                            this._updateFocus(this.tabs[i-1]);
                            focusSet = true;
                        }
                        break;
                    }
                }
                if (this.focusElement == null) { //focus on the last tab
                    var index = this.tabs.length;
                    this._updateFocus(this.tabs[index - 1]);
                    focusSet = true;
                }
            }
        }

        if (@JS_NS@._base.browser._isIe5up()) {
            window.event.cancelBubble = true;
            window.event.returnValue = false;
        } else {
            event.stopPropagation();
            event.preventDefault();
        }
    } else if(keyCode == 13 || keyCode == 32) {  // handle enter and space bar
        var actionComplete = false;
        if (this.isRefreshIcon) {
            if (this.focusElement.id == this._refreshNodeContainer.id) {
                var accWidget = this._widget.getWidget(nodeId);
                accWidget.refresh(nodeId);
                actionComplete = true;
            }    
        }
        if (!actionComplete && this.toggleControls && this.multipleSelect) {
            if (this.focusElement.id == this._collapseAllContainer.id) {
                this._collapseAllTabs();
                actionComplete = true;
                
            } else if (this.focusElement.id == this._expandAllContainer.id) {
                this._expandAllTabs();
                actionComplete = true;
            }
        } 
        if (!actionComplete) { // has to be an accordion tab
            if (this.focusElement) {
                var props;
                if (this.focusElement.selected) {
                    var widget = this._widget.getWidget(this.focusElement.id);
                    widget._setSelected(false);
                } else {
                    var widget = this._widget.getWidget(this.focusElement.id);
                    widget._setSelected(true);
                }
             
            }
        }

        if (@JS_NS@._base.browser._isIe5up()) {
            window.event.cancelBubble = true;
            window.event.returnValue = false;
        } else {
            event.stopPropagation();
            event.preventDefault();
        }
    } else if (keyCode == 9) {  // handle tabbing
        
        // Tabbing order:  refresh -> expand/collapse -> first tab header
        // -> first tabable element in tab content (if tab is open)
        // -> tab out of the accordion completely. Reverse tabbing order
        // is also supported.
        
        var forward = true;
        if (event.shiftKey) {
            forward = false;
        }
        if (this.focusElement) {
            var focusSet = false;
            if (this.isRefreshIcon) {
                if (this.focusElement.id == this._refreshNodeContainer.id) {
                    if (forward) {
                        if (this.toggleControls && this.multipleSelect) {
                            this.focusElement = this._collapseAllContainer;
                            this._setFocusStyleClass(this._collapseAllContainer.id);
                            this._setBlurStyleClass(this._refreshNodeContainer.id);
                            focusSet = true;
                            return true;
                        } else {
                            this.focusElement = this.tabs[0];
                            this._setBlurStyleClass(this._refreshNodeContainer.id);
                            this._setTabFocus(this.focusElement.id);
                            focusSet = true;
                            return true;
                        }
                    } else {
                        this.focusElement = null;
                        this._setBlurStyleClass(this._refreshNodeContainer.id);
                        focusSet = true;
                        return true;
                    }
                }
            }

            if (!focusSet && this.toggleControls && this.multipleSelect) {

                if (this.focusElement.id == this._collapseAllContainer.id) {
                    if (forward) {
                        this.focusElement = this._expandAllContainer;
                        this._setBlurStyleClass(this._collapseAllContainer.id);
                        this._setFocusStyleClass(this._expandAllContainer.id);
                        focusSet = true;
                    } else {
                        this.focusElement = this._refreshNodeContainer;
                        this._setFocusStyleClass(this._refreshNodeContainer.id);
                        this._setBlurStyleClass(this._collapseAllContainer.id);
                        focusSet = true;
                    }
                } else if (this.focusElement.id == this._expandAllContainer.id) {
                    if (forward) {
                        this.focusElement = this.tabs[0];
                        this._setTabFocus(this.focusElement.id);
                        this._setBlurStyleClass(this._expandAllContainer.id);
                        focusSet = true;
                    } else {
                        this.focusElement = this._collapseAllContainer;
                        this._setFocusStyleClass(this._collapseAllContainer.id);
                        this._setBlurStyleClass(this._expandAllContainer.id);
                        focusSet = true;
                    }
                } 
            }

            if (!focusSet) { // focus is on a tab
                if (forward) {  
                    var widget = this._widget.getWidget(this.focusElement.id);
                    if ((widget.getProps().selected == false) ||
                        (widget.focusState == "end")) {
                        
                        // simply move out of the accordion if tab is closed
                        // or tab is open but user has navigated to the end of
                        // the content section.
                        
                        for (var i = 0; i < this.tabs.length; i++) {
                            if (this.tabs[i].id == this.focusElement.id) {
                                var newIndex = (i + 1);
                                if (newIndex == this.tabs.length) {
                                    this._updateFocus(null);
                                    return true;
                                }
                                this._updateFocus(this.tabs[newIndex]);
                                focusSet = true;
                                return true;
                            }
                        }

                    }
                    focusSet = true;
                    
                } else {    // move to the expand/collapse/refresh icons
                    
                    this._setTabBlur(this.focusElement.id);
                    if (this.toggleControls && this.multipleSelect) {
                        this.focusElement = this._expandAllContainer;
                        this._setFocusStyleClass(this._expandAllContainer.id);
                    } else { 
                        this.focusElement = this._refreshNodeContainer;
                        this._setFocusStyleClass(this._refreshNodeContainer.id);
                    }
                    focusSet = true;
                    return true;
                }
            }

        } else { //get focus to the first element
            var focusSet = false;
            if (this.isRefreshIcon) {
               this.focusElement = this._refreshNodeContainer;
               this._setFocusStyleClass(this._refreshNodeContainer.id);
               focusSet = true;
            }
            if (!focusSet && this.toggleControls && this.multipleSelect) {
                this.focusElement = this._collapseAllContainer;
                this._setFocusStyleClass(this._collapseAllContainer.id);
                focusSet = true;
            } 
            if (!focusSet) {
                this.focusElement = this.tabs[0];
                this._setTabFocus(this.focusElement.id);
            }
            
        } 
    } 
    return true;
};

/**
 * This function updates the focused node within the accordion.
 * The old focus element is blurred and the new focus element is
 * set to focus. Appropriate style selectors are applied. 
 * @param (String) newFocusNode The new focus node.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordion.prototype._updateFocus = function(newFocusNode) {
    if (this.focusElement) {
        if (this.focusElement.id == this._refreshNodeContainer.id) {
            this._setBlurStyleClass(this._refreshNodeContainer.id);
        } else if (this.focusElement.id == this._collapseAllContainer.id) {
            this._setBlurStyleClass(this._collapseAllContainer.id);
        } else if (this.focusElement.id == this._expandAllContainer.id) {
            this._setBlurStyleClass(this._expandAllContainer.id);
        } else {
            for (var i = 0; i < this.tabs.length; i++) {
                if (this.tabs[i].id == this.focusElement.id) {
                    this._setTabBlur(this.tabs[i].id);
                    break;
                }
            }
        }
    }
    
    // set the new focusElement and then the associate syles etc.
    if (newFocusNode) {
        this.focusElement = newFocusNode;
        if (this.focusElement.id == this._refreshNodeContainer.id) {
                this._setFocusStyleClass(this._refreshNodeContainer.id);
        } else if (this.focusElement.id == this._collapseAllContainer.id) {
                this._setFocusStyleClass(this._collapseAllContainer.id);
        } else if (this.focusElement.id == this._expandAllContainer.id) {
                this._setFocusStyleClass(this._expandAllContainer.id);
        } else {
            for (var i = 0; i < this.tabs.length; i++) {
                if (this.tabs[i].id == this.focusElement.id) {
                    this._setTabFocus(this.tabs[i].id);
                    break;
                }
            }
        }
    }
    return true;
};
