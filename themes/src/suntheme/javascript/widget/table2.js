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

@JS_NS@._dojo.provide("@JS_NS@.widget.table2");

@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.stateBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");

/**
 * This function is used to construct a table2 widget.
 *
 * @name @JS_NS@.widget.table2
 * @extends @JS_NS@.widget._base.widgetBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @extends @JS_NS@.widget._base.stateBase
 * @class This class contains functions for the table2 widget.
 * @constructor
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
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.table2", [
        @JS_NS@.widget._base.refreshBase, 
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.widgetBase ], {
    // Set defaults.
    _widgetType: "table2" // Required for theme properties.
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
@JS_NS@.widget.table2.event =
        @JS_NS@.widget.table2.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_table2_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_table2_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_table2_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_table2_event_state_end"
    }
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.table2.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.actions != null) { props.actions = this.actions; }
    if (this.align != null) { props.align = this.align; }
    if (this.bgColor != null) { props.bgColor = this.bgColor; }
    if (this.border != null) { props.border = this.border; }
    if (this.caption != null) { props.caption = this.caption; }
    if (this.cellpadding != null) { props.cellpadding = this.cellpadding; }
    if (this.cellspacing != null) { props.cellspacing = this.cellspacing; }
    if (this.filterText != null) { props.filterText = this.filterText; }
    if (this.frame != null) { props.frame = this.frame; }
    if (this.rowGroups != null) { props.rowGroups = this.rowGroups; }
    if (this.rules != null) { props.rules = this.rules; }
    if (this.summary != null) { props.summary = this.summary; }
    if (this.width != null) { props.width = this.width; }

    return props;
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
@JS_NS@.widget.table2.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {
        this._actionsContainer.id = this.id + "_actionsContainer";
        this._actionsNode.id = this.id + "_actionsNode";
        this._controlsNode.id = this.id + "_controlsNode";
        this._filterPanelContainer.id = this.id + "_filterPanelContainer";
        this._preferencesPanelContainer.id = this.id + "_preferencesPanelContainer";
        this._sortPanelContainer.id = this.id + "_sortPanelContainer";
        this._rowGroupsContainer.id = this.id + "_rowGroupsContainer";
        this._captionContainer.id = this.id + "_captionContainer";
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
@JS_NS@.widget.table2.prototype.setProps = function(props, notify) {
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
@JS_NS@.widget.table2.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // To do: Add tabIndex to subwidgets, but not table, tr, or td tags.
    props.tabIndex = null;

    // Set properties.
    if (props.align != null) { this._domNode.align = props.align; }
    if (props.width != null) { this._domNode.style.width = props.width; }

    // Add caption.
    if (props.caption || props.filterText && this.caption) {       
        var filterText = null;
        if (props.filterText) {
            filterText = this._theme.getMessage("table.title.filterApplied", [
                props.filterText
            ]);
        }

        // To do: Create a new title message.
        
        this._widget._addFragment(this._captionContainer, (filterText) 
            ? props.caption + filterText : props.caption);
        this._common._setVisibleElement(this._captionContainer, true);
    }

    // Add actions.
    if (props.actions) {        
        for (var i = 0; i < props.actions.length; i++) {
          this._widget._addFragment(this._actionsNode, props.actions[i], "last");
        }
        this._common._setVisibleElement(this._actionsContainer, true);
    }

    // Add row groups.
    if (props.rowGroups) {
        // Remove child nodes.
        this._widget._removeChildNodes(this._rowGroupsContainer);
 
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
            this._widget._addFragment(this._rowGroupsContainer, props.rowGroups[i], "last");

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
                @JS_NS@.widget.common.getWidget(_id)._resize();
            }, 2000);
        }
    }

    // Set more properties.
    this._setCommonProps(this._domNode, props);
    this._setEventProps(this._domNode, props);

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
