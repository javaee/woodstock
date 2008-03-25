/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
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

webui.@THEME_JS@._dojo.provide("webui.@THEME_JS@.widget.table2");

webui.@THEME_JS@._dojo.require("webui.@THEME_JS@.widget.common");
webui.@THEME_JS@._dojo.require("webui.@THEME_JS@.widget.widgetBase");

/**
 * @name webui.@THEME_JS@.widget.table2
 * @extends webui.@THEME_JS@.widget.widgetBase
 * @class This class contains functions for the table2 widget.
 * @constructor This function is used to construct a table2 widget.
 */
webui.@THEME_JS@._dojo.declare("webui.@THEME_JS@.widget.table2", webui.@THEME_JS@.widget.widgetBase, {
    // Set defaults.
    widgetName: "table2" // Required for theme properties.
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
webui.@THEME_JS@.widget.table2.event =
        webui.@THEME_JS@.widget.table2.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_table2_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_table2_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_table2_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_table2_event_state_end"
    }
};

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME_JS@.widget.table2.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

    // Set properties.
    if (this.actions) { props.actions = this.actions; }
    if (this.align) { props.align = this.align; }
    if (this.bgColor) { props.bgColor = this.bgColor; }
    if (this.border) { props.border = this.border; }
    if (this.caption) { props.caption = this.caption; }
    if (this.cellpadding) { props.cellpadding = this.cellpadding; }
    if (this.cellspacing) { props.cellspacing = this.cellspacing; }
    if (this.filterText) { props.filterText = this.filterText; }
    if (this.frame) { props.frame = this.frame; }
    if (this.rowGroups) { props.rowGroups = this.rowGroups; }
    if (this.rules) { props.rules = this.rules; }
    if (this.summary) { props.summary = this.summary; }
    if (this.width) { props.width = this.width; }

    return props;
};

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.table2.prototype.postCreate = function () {
    // Set ids.
    if (this.id) {
        this.actionsContainer.id = this.id + "_actionsContainer";
        this.actionsNode.id = this.id + "_actionsNode";
        this.controlsNode.id = this.id + "_controlsNode";
        this.filterPanelContainer.id = this.id + "_filterPanelContainer";
        this.preferencesPanelContainer.id = this.id + "_preferencesPanelContainer";
        this.sortPanelContainer.id = this.id + "_sortPanelContainer";
        this.rowGroupsContainer.id = this.id + "_rowGroupsContainer";
        this.captionContainer.id = this.id + "_captionContainer";
    }
    return this.inherited("postCreate", arguments);
};

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
 * @config {Object} actions 
 * @config {String} align Alignment of image input.
 * @config {String} bgColor
 * @config {String} border
 * @config {String} caption
 * @config {String} className CSS selector.
 * @config {String} dir Specifies the directionality of text.
 * @config {String} frame 
 * @config {String} filterText 
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {String} onClick Mouse button is clicked on element.
 * @config {String} onDblClick Mouse button is double-clicked on element.
 * @config {String} onKeyDown Key is pressed down over element.
 * @config {String} onKeyPress Key is pressed and released over element.
 * @config {String} onKeyUp Key is released over element.
 * @config {String} onMouseDown Mouse button is pressed over element.
 * @config {String} onMouseOut Mouse is moved away from element.
 * @config {String} onMouseOver Mouse is moved onto element.
 * @config {String} onMouseUp Mouse button is released over element.
 * @config {String} onMouseMove Mouse is moved while over element.
 * @config {Array} rowGroups 
 * @config {String} rules 
 * @config {String} style Specify style rules inline.
 * @config {String} summary
 * @config {int} tabIndex Position in tabbing order.
 * @config {String} title Provides a title for element.
 * @config {boolean} visible Hide or show element.
 * @config {String} width
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.table2.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace actions -- do not extend.
    if (props.actions) {
        this.actions = null;
    }

    // Replace rows -- do not extend.
    if (props.rowGroups) {
        this.rowGroups = null;
    }

    // Extend widget object for later updates.
    return this.inherited("setProps", arguments);
};

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
webui.@THEME_JS@.widget.table2.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // To do: Add tabIndex to subwidgets, but not table, tr, or td tags.
    props.tabIndex = null;

    // Set properties.
    if (props.align) { this.domNode.align = props.align; }
    if (props.width) { this.domNode.style.width = props.width; }

    // Add caption.
    if (props.caption || props.filterText && this.caption) {       
        var filterText = null;
        if (props.filterText) {
            filterText = this.theme.getMessage("table.title.filterApplied", [
                props.filterText
            ]);
        }

        // To do: Create a new title message.
        
        this.widget.addFragment(this.captionContainer, (filterText) 
            ? props.caption + filterText : props.caption);
        this.common.setVisibleElement(this.captionContainer, true);
    }

    // Add actions.
    if (props.actions) {
        this.widget.addFragment(this.actionsNode, props.actions);
        this.common.setVisibleElement(this.actionsContainer, true);
    }

    // Add row groups.
    if (props.rowGroups) {
        // Remove child nodes.
        this.widget.removeChildNodes(this.rowGroupsContainer);
 
        // Add row group.
        for (var i = 0; i < props.rowGroups.length; i++) {
            // Set properties that must be applied to each HTML table element.
            props.rowGroups[i]._table = {
                bgColor: props.bgColor,
                border: props.border,
                cellpadding: props.cellpadding,
                cellspacing: props.cellspacing,
                frame: props.frame,
                summary: props.summary
            };
            this.widget.addFragment(this.rowGroupsContainer, props.rowGroups[i], "last");

            // To do: Fix me.
            // Actions my be rendered after column headers are positioned. When 
            // this happens, older offsetTop properties are no longer valid and
            // headers are off by one row, at least. We need a better way to 
            // solve this (e.g., set a fixed action bar height), but we'll call
            // resize() for now. Note that the addRows() function already calls
            // this, but with a much shorter timeout.
            var _id = props.rowGroups[i].id;
            setTimeout(function() {
                // New literals are created every time this function is called, 
                // and it's saved by closure magic.
                webui.@THEME_JS@.widget.common.getWidget(_id).resize();
            }, 2000);
        }
    }

    // Set more properties.
    this.setCommonProps(this.domNode, props);
    this.setEventProps(this.domNode, props);

    // Set remaining properties.
    return this.inherited("_setProps", arguments);
};
