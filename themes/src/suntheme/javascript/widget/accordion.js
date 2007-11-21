// widget/accordion.js
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

dojo.provide("webui.@THEME@.widget.accordion");

dojo.require("webui.@THEME@.widget.accordionTab");
dojo.require("webui.@THEME@.widget.widgetBase");

/**
 * @name webui.@THEME@.widget.accordion
 * @extends webui.@THEME@.widget.widgetBase
 * @class This class contains functions for the accordion widget.
 * @constructor This function is used to construct an accordion widget.
 */
dojo.declare("webui.@THEME@.widget.accordion", webui.@THEME@.widget.widgetBase, {
    // Set defaults.
    duration: 250,
    isContainer: true,
    loadOnSelect: false,
    multipleSelect: false,
    widgetName: "accordion" // Required for theme properties.
});

/**
 * Helper function to add accordion header controls
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {Object} collapseAllImage 
 * @config {Object} expandAllImage 
 * @config {Object} isRefreshIcon 
 * @config {Object} multipleSelect 
 * @config {Object} refreshImage 
 * @config {Object} toggleControls
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.accordion.prototype.addControls = function(props) {       
    // Add expand and collapse icons only if multiple select is set to
    // true and the icons have been supplied.
    if (props.toggleControls && props.multipleSelect) {
        // Set expand all image properties.
        if (props.expandAllImage) {
            // Set properties.
            // props.expandAllImage.id = this.expandAllImage.id; // Required for updateFragment().

            // Update/add fragment.
            this.widget.updateFragment(this.expandAllImgContainer, props.expandAllImage);
        }

        // Set collapse all image properties.
        if (props.collapseAllImage) {
            // Update/add fragment.
            this.widget.updateFragment(this.collapseAllImgContainer, props.collapseAllImage);
        }
        
        // a divider should only be added if expand/collapse icons exist.
        this.dividerNodeContainer.className = this.theme.getClassName("ACCORDION_HDR_DIVIDER");
    }

    // Set refresh image properties.
    if (props.isRefreshIcon && props.refreshImage) {
        // Update/add fragment.
        this.widget.updateFragment(this.refreshImgContainer, props.refreshImage);
    }
    return true;
}

/**
 * Close all open accordions and leave the others as is.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.accordion.prototype.collapseAllTabs = function(event) {
    // Iterate over all tabs.
    for (var i = 0; i < this.tabs.length; i++) {
        var widget = dijit.byId(this.tabs[i].id);
        if (widget && widget.selected) {
            widget.setSelected(false);
        }
    }
    return true;
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
webui.@THEME@.widget.accordion.event = 
        webui.@THEME@.widget.accordion.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_accordion_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_accordion_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_accordion_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_accordion_event_state_end"
    }
}

/**
 * Open all closed tabs and leave the others as is.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.accordion.prototype.expandAllTabs = function(event) {
    // Iterate over all tabs.
    for (var i = 0; i < this.tabs.length; i++) {
        var widget = dijit.byId(this.tabs[i].id);
        if (widget && !widget.selected) {
            widget.setSelected(true);
        }
    }
    return true;
}

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME@.widget.accordion.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

    // Set properties.
    if (this.collapseAllImage != null) { props.collapseAllImage = this.collapseAllImage; }
    if (this.expandAllImage != null) { props.expandAllImage = this.expandAllImage; }
    if (this.isRefreshIcon != null) { props.isRefreshIcon = this.isRefreshIcon; }
    if (this.loadOnSelect) { props.loadOnSelect = this.loadOnSelect; }
    if (this.multipleSelect) { props.multipleSelect = this.multipleSelect; }
    if (this.refreshImage != null) { props.refreshImage = this.refreshImage; }
    if (this.style != null) { props.style = this.style; }
    if (this.tabs != null) { props.tabs = this.tabs; }
    if (this.toggleControls) { props.toggleControls = this.toggleControls; }
    if (this.type) { props.type = this.type; }
 
    return props;
}

/**
 * This function is used to obtain the outermost HTML element class name.
 * <p>
 * Note: Selectors should be concatinated in order of precedence (e.g., the
 * user's className property is always appended last).
 * </p>
 * @return {String} The outermost HTML element class name.
 */
webui.@THEME@.widget.accordion.prototype.getClassName = function() {
    // Get theme property.
    var className = this.theme.getClassName("ACCORDION_DIV", "");
    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.accordion.prototype.postCreate = function () {
    with (this.domNode.style) {
        if (position != "absolute") {
            position = "relative";
        }
        overflow = "hidden";
    }

    // Set ids.
    if (this.id) {
        this.domNode.id = this.id;
        this.headerContainer.id = this.id + "_accHeader";
        this.expandAllContainer.id = this.id + "_expandAllNode";
        this.expandAllImgContainer.id = this.expandAllContainer.id + "_expandAllImage";
        this.collapseAllContainer.id = this.id + "_collapseAllNode";
        this.collapseAllImgContainer.id = this.collapseAllImgContainer.id + "_collapseAllImage";
        this.dividerNodeContainer.id = this.id + "_dividerNode";
        this.refreshNodeContainer.id = this.id + "_refreshNode";
    }

    // Set class names.
    this.headerContainer.className = this.theme.getClassName("ACCORDION_HDR");
    this.collapseAllContainer.className = this.theme.getClassName("ACCORDION_HDR_CLOSEALL");
    this.expandAllContainer.className = this.theme.getClassName("ACCORDION_HDR_OPENALL");
    
    // the divider should initially be hidden
    this.dividerNodeContainer.className = this.theme.getClassName("HIDDEN");
    this.refreshNodeContainer.className = this.theme.getClassName("ACCORDION_HDR_REFRESH");

    // Set events.
    var _id = this.id;
    dojo.connect(this.collapseAllContainer, "onclick", this, "collapseAllTabs");
    dojo.connect(this.expandAllContainer, "onclick", this, "expandAllTabs");
    dojo.connect(this.refreshNodeContainer, "onclick", function(event) {
        // New literals are created every time this function is called, and it's 
        // saved by closure magic.
        var widget = dijit.byId(_id);
        widget.refresh(_id);
    });

    // Subscribe to the "dayClicked" event present in the calendar widget.
    dojo.subscribe(webui.@THEME@.widget.accordionTab.event.title.selectedTopic,
        this, "tabSelected");

    // Generate the accordion header buttons on the client side.

    if (this.toggleControls && this.multipleSelect) {
        if (this.expandAllImage == null) {
            var btnTitle = this.theme.getMessage("Accordion.expandAll");
            this.expandAllImage = this.widget.getImageProps("ACCORDION_EXPAND_ALL", {
                id: this.id + "_expandAll", 
                title: btnTitle,
                alt: btnTitle
            });
        }
        
        if (this.collapseAllImage == null) {
            var btnTitle = this.theme.getMessage("Accordion.collapseAll");
            this.collapseAllImage = this.widget.getImageProps("ACCORDION_COLLAPSE_ALL", {
                id: this.id + "_collapseAll", 
                title: btnTitle,
                alt: btnTitle
            });
        }
    }
    // Set refresh image properties.
    if (this.isRefreshIcon) {
        if (this.refreshImage == null) {
            var btnTitle = this.theme.getMessage("Accordion.refresh");
            this.refreshImage = this.widget.getImageProps("ACCORDION_REFRESH", {
                id: this.id + "_refresh", 
                title: btnTitle,
                alt: btnTitle
            });
         }
    }
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
 * @config {String} className CSS selector.
 * @config {Object} collapseAllImage 
 * @config {Object} expandAllImage 
 * @config {String} id Uniquely identifies an element within a document.
 * @config {boolean} isRefreshIcon 
 * @config {boolean} loadOnSelect 
 * @config {boolean} multipleSelect 
 * @config {Object} refreshImage 
 * @config {String} style Specify style rules inline.
 * @config {Array} tabs 
 * @config {boolean} toggleControls
 * @config {String} type 
 * @config {boolean} visible Hide or show element.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.accordion.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace tabs -- do not extend.
    if (props.tabs) {
        this.tabs = null;
    }

    // Extend widget object for later updates.
    return this.inherited("setProps", arguments);
}

/**
 * This function is used to set widget properties. Please see the setProps() 
 * function for a list of supported properties.
 * <p>
 * Note: This function should only be invoked through setProps().
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME@.widget.accordion.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // add control icons - refresh, expandall, collapseall.
    this.addControls(props); 

    // If we are coming here for
    // the first time there will be no children. The other case is when 
    // the accordion is being rerendered because of a refresh in which 
    // we want to use the latest set of children. addFragment is supposed
    // to do that.
    if (props.tabs) {
        // Remove child nodes.
        this.widget.removeChildNodes(this.tabsContainer);

        // Add tabs.
        for (var i = 0; i < props.tabs.length; i++) {
            this.widget.addFragment(this.tabsContainer, props.tabs[i], "last");
        }
    }

    // Set more properties.
    this.setCommonProps(this.domNode, props);
    this.setEventProps(this.domNode, props);

    // Set remaining properties.
    return this.inherited("_setProps", arguments);
}

/**
 * Process tab selected events.
 *
 * @param props Key-Value pairs of properties.
 * @config {String} id The id of the selected tab.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.accordion.prototype.tabSelected = function(props) {
    var widget = null;

    // Iterate over all tabs to ensure id is valid.
    for (var i = 0; i < this.tabs.length; i++) {
        if (props.id == this.tabs[i].id) {
            widget = dijit.byId(this.tabs[i].id);
            break;   
        }
    }
    
    // Return if id was not valid.
    if (widget == null) {
        return false;
    }

    if (this.multipleSelect) {
        widget.setSelected(true);
    } else {
        for (var i = 0; i < this.tabs.length; i++) {
            widget = dijit.byId(this.tabs[i].id);
            if (widget) {
                widget.setSelected(props.id == this.tabs[i].id);
            }
        }
    }
    return true;
}
