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

@JS_NS@._dojo.provide("@JS_NS@.widget.tab");

@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.stateBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.submitBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");

/**
 * This function is used to construct a tab widget.
 *
 * @constructor
 * @name @JS_NS@.widget.tab
 * @extends @JS_NS@.widget._base.widgetBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @extends @JS_NS@.widget._base.stateBase
 * @extends @JS_NS@.widget._base.submitBase
 * @class This class contains functions for the tab widget.
 * <p>
 * The tab widget represents a container within which an arbitrary 
 * number of components can be placed. These components will render within the 
 * content area of the given tab inside a tabset. The tab widget 
 * should be configured to have a label that will appear in the tabset body
 * even when the tab is not selected. The height of the content area is the same
 * for all tabs in a tabset and is a property that can be set on the tabset.
 * </p><p>
 * For examples on using the tab widget inside a tabset, see 
 * <a href="@JS_NS@.widget.tabset.html">@JS_NS@.widget.tabset</a>
 * </p>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {boolean} closeable  indicates whether the tab can be closed
 * @config {Array} contents array of widget objects to be rendered in form of 
 *        Key-Value pairs of properties
 * @config {String} parentID  the id of the tab's parent widget.  This property is not required
 *         if the tab properties are part of the "contents" array property of a tabset.
 *         In this case, the tabset can determine the parentID and will set this property
 *         automatically.
 * @config {boolean} selected  set the state of the tab to selected (true) or unselected (false).
 *         This only sets the selected state and the visual appearance to reflect that state.
 *         It has no impact on the tabset and it's management of the other tabs it contains.
 *         Therefore this property should not be set directly, but rather one should use the
 *         selectTab() function on the tabset to select a tab.
 * @config {String} style CSS style or styles to be applied to the outermost 
 *         HTML element when this component is rendered.
 * @config {String} title  the tab title
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.tab", [
        @JS_NS@.widget._base.refreshBase, 
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.submitBase,
        @JS_NS@.widget._base.widgetBase ], {
    // Set defaults.
    constructor: function() {
        // Set defaults for public properties that can be modified.
        this.closeable = false;
        this.parentID = null;
        this.selected = false;
        this.title = null;
        this.contents = null;
        this._loadOnSelect = false;

        // Set defaults for private data used internally by the widget.
        this._spanTextNode = null;
        this._linkTextNode = null;
        this._closed = false;
        this._tabsetParentID = null;
        this._hasChildTabs = false;
    },

    _widgetType: "tab" // Required for theme properties.
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
@JS_NS@.widget.tab.event =
        @JS_NS@.widget.tab.prototype.event = {
    /** 
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /**
         * Refresh event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li><li>
         * {String} execute Comma separated list of IDs to be processed server
         * side along with this widget.
         * </li></ul>
         *
         * @id @JS_NS@.widget.tab.event.refresh.beginTopic
         * @property {String} beginTopic
         */
        beginTopic: "@JS_NS@_widget_tab_event_refresh_begin",

        /**
         * Refresh event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li><li>
         * Please see the constructor detail for additional properties.
         * </li></ul>
         */
        endTopic: "@JS_NS@_widget_tab_event_refresh_end"
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
         * @id @JS_NS@.widget.tab.event.state.beginTopic
         * @property {String} beginTopic
         */
        beginTopic: "@JS_NS@_widget_tab_event_state_begin",

        /**
         * State event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li></ul>
         *
         * @id @JS_NS@.widget.tab.event.state.endTopic
         * @property {String} endTopic
         */
        endTopic: "@JS_NS@_widget_tab_event_state_end"
    },

    /**
     * This object contains load event topics.
     * @ignore
     */
    load: {
        /**
         * Load event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li></ul>
         *
         * @id @JS_NS@.widget.tab.event.load.beginTopic
         * @property {String} beginTopic
         */
        beginTopic: "@JS_NS@_widget_tab_event_load_begin",

        /**
         * Load event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li></ul>
         *
         * @id @JS_NS@.widget.tab.event.load.endTopic
         * @property {String} endTopic
         */
        endTopic: "@JS_NS@_widget_tab_event_load_end"
    },

    /**
     * Closed event topic for custom AJAX implementations to listen for.
     * Key-Value pairs of properties to publish include:
     * <ul><li>
     * {String} id The widget ID to process the event for.
     * </li></ul>
     *
     * @id @JS_NS@.widget.tab.event.closedTopic
     * @property {String} closedTopic
     */
    closedTopic: "@JS_NS@_widget_tab_event_closed",

    /**
     * Select event topic for custom AJAX implementations to listen for.
     * Key-Value pairs of properties to publish include:
     * <ul><li>
     * {String} id The widget ID to process the event for.
     * </li></ul>
     *
     * @id @JS_NS@.widget.tab.event.selectedTopic
     * @property {String} selectedTopic
     */
    selectedTopic: "@JS_NS@_widget_tab_event_selected"
};

/**
 * This function is used to obtain the outermost HTML element class name.
 * <p>
 * Note: Selectors should be concatenated in order of precedence (e.g., the
 * user's className property is always appended last).
 * </p>
 * @return {String} The outermost HTML element class name.
 * @private
 */
@JS_NS@.widget.tab.prototype._getClassName = function() {

    var className = this._inherited("_getClassName", arguments);

    // Use DTAB_LNKDIV if tab not selected.
    // Use DTAB_SELDIV if selected tab has no child tabs, otherwise DTAB_SELDIV2
    var key = "DTAB_LNKDIV"
    if (this.selected == true) {
        key = (this._hasChildTabs) ? "DTAB_SELDIV2" : "DTAB_SELDIV";
    }
    var newClassName = this._theme.getClassName(key, "");

    // Get classes tabset may want assigned
    var tabset = this._widget.getWidget(this.getTabsetParentID());
    if (tabset != null) {
        var cl = tabset._getClassNameForTab(this.id);
        if (cl != null) {
            newClassName += (" " + cl);
        }
    }

    return (className)
        ? newClassName + " " + className
        : newClassName;

}; // _getClassName

/**
 * Return the id of the parent widget of this tab.
 *
 * @return {String}  id of parent widget or null if no parent
 */
@JS_NS@.widget.tab.prototype.getParentID = function() {

    return this.parentID;

}; // getParentID

/**
 * Return the id of the parent tabset widget this tab is contained in.
 *
 * @return {String}  id of parent tabset widget or null if no tabset parent
 */
@JS_NS@.widget.tab.prototype.getTabsetParentID = function() {

    if (this._tabsetParentID != null)
        return this._tabsetParentID;

    var parentID = this.parentID;
    while (parentID != null) {
        var widget = this._widget.getWidget(parentID);
        if (widget == null)
            return null;
        if (widget instanceof @JS_NS@.widget.tabset) {
            this._tabsetParentID = parentID;
            return this._tabsetParentID;
        }
        parentID = widget.parentID;
    }
    return null;

}; // getTabsetParentID


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
@JS_NS@.widget.tab.prototype.setProps = function(props, notify) {

    if (props == null)
        return false;

    if (props.closeable != null)
       this.closeable = props.closeable;
    if (props.parentID != null)
       this.parentID = props.parentID;
    if (props.selected != null)
       this.selected = props.selected;
    if (props.title != null)
       this.title = props.title;
    if (props.contents != null)
       this.contents = props.contents;

    return this._inherited("setProps", arguments);

}; // setProps

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.tab.prototype.getProps = function() {

    var props = this._inherited("getProps", arguments);

    if (this.closeable != null)
        props.closeable = this.closeable;
    if (this.parentID != null)
        props.parentID = this.parentID;
    if (this.selected != null)
        props.selected = this.selected;
    if (this.title != null)
        props.title = this.title;
    if (this.contents != null)
        props.contents = this.contents;

    return props;

}; // _getProps

/**
 * Setup handler for close button mouseover events.  We setup in this manner so
 * we can use closure magic to define the scope for the handler with 
 * necessary information to properly process the event.
 *
 * @return {Object} the event handler
 * @private
 */
@JS_NS@.widget.tab.prototype._createOnTabCloseMouseOverCallback = function() {

    var _this = this;
    return function(event) {
        _this._onTabCloseMouseOverCallback(event);
        return false;
    }

}; // _createOnTabCloseMouseOverCallback

/**
 * Setup handler for close button mouseout events.  We setup in this manner so
 * we can use closure magic to define the scope for the handler with 
 * necessary information to properly process the event.
 *
 * @return {Object} the event handler
 * @private
 */
@JS_NS@.widget.tab.prototype._createOnTabCloseMouseOutCallback = function() {

    var _this = this;
    return function(event) {
        _this._onTabCloseMouseOutCallback(event);
        return false;
    }

}; // _createOnTabCloseMouseOutCallback

/**
 * Setup handler for close button click events.  We setup in this manner so
 * we can use closure magic to define the scope for the handler with 
 * necessary information to properly process the event.
 *
 * @return {Object} the event handler
 * @private
 */
@JS_NS@.widget.tab.prototype._createOnTabCloseCallback = function() {

    var _this = this;
    return function(event) {
        _this._onTabCloseCallback(event);
        return false;
    }

}; // _createOnTabCloseCallback

/**
 * Setup handler for tab click events.  We setup in this manner so
 * we can use closure magic to define the scope for the handler with 
 * necessary information to properly process the event.
 *
 * @return {Object} the event handler
 * @private
 */
@JS_NS@.widget.tab.prototype._createOnClickCallback = function() {

    var _this = this;
    return function(event) {
        _this._onClickCallback(event);
        return false;
    }

}; // _createOnClickCallback

/**
 * Setup handler for tab mouseover events for IE6 only.  We setup in this manner so
 * we can use closure magic to define the scope for the handler with 
 * necessary information to properly process the event.
 *
 * @return {Object} the event handler
 * @private
 */
@JS_NS@.widget.tab.prototype._createOnTabMouseOverCallback = function() {

    var _this = this;
    return function(event) {
        _this._onTabMouseOverCallback(event);
        return false;
    }

}; // _createOnTabMouseOverCallback

/**
 * Setup handler for tab mouseout events for IE6 only.  We setup in this manner so
 * we can use closure magic to define the scope for the handler with 
 * necessary information to properly process the event.
 *
 * @return {Object} the event handler
 * @private
 */
@JS_NS@.widget.tab.prototype._createOnTabMouseOutCallback = function() {

    var _this = this;
    return function(event) {
        _this._onTabMouseOutCallback(event);
        return false;
    }

}; // _createOnTabMouseOutCallback

/**
 * Handler for close button click events.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful, otherwise false
 * @private
 */
@JS_NS@.widget.tab.prototype._onTabCloseCallback = function(event) {

    if (this._closed == true)
        return true;

    // Restore correct close icon.
    this._onTabCloseMouseOutCallback(event);

    // Inform that the tab has been closed.
    this._publish(@JS_NS@.widget.tab.event.closedTopic,[{
        "id": this.id
    }]);  
    
    this._closed = true;
    return true;

}; // _onTabCloseCallback

/**
 * Handler for close button mouseover events.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful, otherwise false
 * @private
 */
@JS_NS@.widget.tab.prototype._onTabCloseMouseOverCallback = function(event) {

    var image = this._theme.getImage("DTAB_CLOSE_HOVER");
    this._closeIconNode.src = image.src;
    this._closeIconNode.width = image.width;
    this._closeIconNode.height = image.height;
    return true;

}; // _onTabCloseMouseOverCallback

/**
 * Handler for close button mouseout events.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful, otherwise false
 * @private
 */
@JS_NS@.widget.tab.prototype._onTabCloseMouseOutCallback = function(event) {

    var image = (this.selected == true)
        ? this._theme.getImage("DTAB_CLOSE_SELECTED")
        : this._theme.getImage("DTAB_CLOSE_UNSELECTED");
    this._closeIconNode.src = image.src;
    this._closeIconNode.width = image.width;
    this._closeIconNode.height = image.height;
    return true;

}; // _onTabCloseMouseOutCallback

/**
 * Handler for tab click events.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful, otherwise false
 * @private
 */
@JS_NS@.widget.tab.prototype._onClickCallback = function(event) {

    if (this.selected == true)
        return true;

    // Inform that the tab has been selected.
    this._publish(@JS_NS@.widget.tab.event.selectedTopic,[{
        "id": this.id
    }]);  

    return true;

}; // _onClickCallback

/**
 * Set tab selected state.
 *
 * @param {boolean} isSelected  true if selected, false if not
 * @return {boolean} true if successful, otherwise false
 * @private
 */
@JS_NS@.widget.tab.prototype._setSelected = function(isSelected) {

    this.setProps({"selected": isSelected});

    if (isSelected) {
        // Load tab content if loadOnSelect enabled and tab content not loaded.
        var tabset = this._widget.getWidget(this.getTabsetParentID());
        if ((tabset.loadOnSelect == true) && (this._loadOnSelect == false)) {
            this._loadOnSelect = true;
            this._loadContent();
        }
    }

    return true;

}; // _setSelected

/**
 * Load new tab content.
 *
 * @return {boolean} true if successful, otherwise false
 * @private
 */
@JS_NS@.widget.tab.prototype._loadContent = function() {

    this._setLoadIconOn(true);

    this._publish(@JS_NS@.widget.tab.event.load.beginTopic,[{
        "id": this.id,
        endTopic: @JS_NS@.widget.tab.event.load.endTopic
    }]);  

    return true;

}; // _loadContent

/**
 * Callback function for the end of the load event
 *
 * @param props Key-Value pairs of properties.
 * @config {String} id The HTML element Id.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tab.prototype._loadCallback = function(props) {

    this._setLoadIconOn(false);
    return true;

}; // _loadCallback

/**
 * Turn on/off the progress loading icon
 *
 * @param {boolean} on  true to turn it on, false to turn it off
 * @return true
 * @private
 */
@JS_NS@.widget.tab.prototype._setLoadIconOn = function(on) {

    if (on == false) {
        this._common._addStyleClass(this._loadIconContainer, this._theme.getClassName("DTAB_LOAD_OFF"));
    }
    var image = null;
    if (this.selected == true) {
        image = this._theme.getImage("DTAB_LOADING_SELECTED");
    } else {
        image = this._theme.getImage("DTAB_LOADING_UNSELECTED");
    }
    this._loadIconNode.src = image.src;
    this._loadIconNode.width = image.width;
    this._loadIconNode.height = image.height;
    if (on == true) {
        this._common._stripStyleClass(this._loadIconContainer, this._theme.getClassName("DTAB_LOAD_OFF"));
    }

}; // _setLoadIconOn

/**
 * Turn on/off the close button
 *
 * @param {boolean} on  true to turn it on, false to turn it off
 * @return true
 * @private
 */
@JS_NS@.widget.tab.prototype._setCloseBtnOn = function(on) {

    if (on == false) {
        this._common._addStyleClass(this._closeBtnContainer, this._theme.getClassName("DTAB_CLOSEBTN_OFF"));
    }
    var image = null;
    if (this.selected == true) {
        image = this._theme.getImage("DTAB_CLOSE_SELECTED");
    } else {
        image = this._theme.getImage("DTAB_CLOSE_UNSELECTED");
    }
    this._closeIconNode.src = image.src;
    this._closeIconNode.width = image.width;
    this._closeIconNode.height = image.height;
    if (on == true) {
        this._common._stripStyleClass(this._closeBtnContainer, this._theme.getClassName("DTAB_CLOSEBTN_OFF"));
    }

}; // _setCloseBtnOn

/**
 * Handler for tab mouseover events for IE6 only.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful, otherwise false
 * @private
 */
@JS_NS@.widget.tab.prototype._onTabMouseOverCallback = function(event) {

    this._common._addStyleClass(this._textNode, this._theme.getClassName("DTAB_IEHOVER"));
    return true;

}; // _onTabMouseOverCallback

/**
 * Handler for tab mouseout events for IE6 only.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful, otherwise false
 * @private
 */
@JS_NS@.widget.tab.prototype._onTabMouseOutCallback = function(event) {

    this._common._stripStyleClass(this._textNode, this._theme.getClassName("DTAB_IEHOVER"));
    return true;

}; // _onTabMouseOutCallback


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
@JS_NS@.widget.tab.prototype._setProps = function(props) {

    if (props == null)
        return false;

    // Contents - determine if contains child tabs
    if (props.contents != null) {
        for (var i = 0; i < this.contents.length; i++) {
            if (this.contents[i].widgetType == "tab") {
                this._hasChildTabs = true;
                break;
            }
        }
    }

    // Title
    if (props.title != null) {
        try { this._textNode.removeChild(this._spanTextNode); } catch (e) { /* do nothing */ }
        this._textContainer.className = this._theme.getClassName("DTAB_TITLE");

        // If title length drops below a threshold, then we apply some padding.
        if (this.title.length <= parseInt(this._theme.getMessage("dtab.titlePadThreshold")))
            this._common._addStyleClass(this._textContainer, this._theme.getClassName("DTAB_PAD"));

        this._spanTextNode = document.createTextNode(this.title);
        this._textNode.appendChild(this._spanTextNode);
    }

    // Close icon
    if ((props.selected != null) || (props.closeable != null)) {
        this._setCloseBtnOn(this.closeable);
    }

    // Contents
    if (props.contents != null) {
        for (var i = 0; i < this.contents.length; i++) {
            // Ensure we have a tabset
            var tabsetID = this.contents[i]._tabsetParentID;
            if (tabsetID == null)
                tabsetID = this.getTabsetParentID();
            if (tabsetID == null)
                continue;
            var tabset = this._widget.getWidget(tabsetID);
            if (tabset == null)
                continue;

            // Unlikely we need this, but Murphy's Law ...
            if (this._tabsetParentID == null)
                this._tabsetParentID = tabsetID;

            if (this._hasChildTabs) {
                if (this.contents[i].widgetType == "tab") {
                    tabset.addTab(this.contents[i]);
                }
            } else {
                if (this.contents[i].widgetType != "tab") {
                    var props = {
                        "id": this.id,
                        "contents": this.contents[i]
                    }
                    tabset.addContent(props, (i > 0));
                }
            }
        }
    }
    
    // Set more properties.
    this._setCommonProps(this._domNode, props);
    this._setEventProps(this._domNode, props);

    // Set remaining properties.
    return this._inherited("_setProps", arguments);

}; // _setProps


/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tab.prototype._postCreate = function() {

    // Set IDs for the tab's elements
    this._domNode.id = this.id;
    this._textContainer.id = this.id + "_textContainer";
    this._textNode.id = this.id + "_textNode";
    this._loadIconContainer.id = this.id + "_loadIconContainer";
    this._loadIconNode.id = this.id + "_loadIconNode";
    this._closeBtnContainer.id = this.id + "_closeBtnContainer";
    this._closeIconNode.id = this.id + "_closeIconNode";

    // Initialize load progress icon
    this._loadIconContainer.className = this._theme.getClassName("DTAB_LOAD");
    this._setLoadIconOn(false);

    // Initialize the close button
    this._closeBtnContainer.className = this._theme.getClassName("DTAB_CLOSEBTN");
    this._common._addStyleClass(this._closeBtnContainer, this._theme.getClassName("DTAB_ENABLED"));
    this._setCloseBtnOn(this.closeable);

    // Subscribe to load event topics
    this._widget.subscribe(@JS_NS@.widget.tab.event.load.endTopic, this, "_loadCallback");

    this._dojo.connect(this._textNode, "onclick", this._createOnClickCallback());
    this._dojo.connect(this._closeBtnContainer, "onclick", this._createOnTabCloseCallback());
    this._dojo.connect(this._closeBtnContainer, "onmouseover", this._createOnTabCloseMouseOverCallback());
    this._dojo.connect(this._closeBtnContainer, "onmouseout", this._createOnTabCloseMouseOutCallback());

    // IE6 does not support hover on non-anchor elements, so we must "do-it-yourself".
    if (@JS_NS@._base.browser._isIe6()) {
        this._dojo.connect(this._textContainer, "onmouseover", this._createOnTabMouseOverCallback());
        this._dojo.connect(this._textContainer, "onmouseout", this._createOnTabMouseOutCallback());
    }

    return this._inherited("_postCreate", arguments);

}; // _postCreate
