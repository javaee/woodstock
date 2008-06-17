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

@JS_NS@._dojo.provide("@JS_NS@.widget.table");

@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.stateBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");
@JS_NS@._dojo.require("@JS_NS@.widget.tableRowGroup");

/**
 * This function is used to construct a table widget.
 *
 * @name @JS_NS@.widget.table
 * @extends @JS_NS@.widget._base.widgetBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @extends @JS_NS@.widget._base.stateBase
 * @class This class contains functions for the table widget.
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
 * @config {String} tableTips Provides tips for table.
 * @config {boolean} showTableControls Hide or show table controls button.
 * @config {boolean} showTipsControl Hide or show table tips button.
 * @config {Object} preferencesPanel preferences panel content
 * @config {Object} filterPanel filter panel content 
 * @config {Object} sortPanel sort panel content
 * @config (String) filterPanelFocusId focus id for the filter panel element.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.table", [
        @JS_NS@.widget._base.refreshBase, 
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.widgetBase ], {
    // Set defaults.    
    constructor: function() {
        this.showTableControls = false;
        this.showTipsControl = false;        
    },
    _widgetType: "table" // Required for theme properties.
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
@JS_NS@.widget.table.event =
        @JS_NS@.widget.table.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_table_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_table_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_table_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_table_event_state_end"
    }
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.table.prototype.getProps = function() {
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
    if (this.filter != null) { props.filter = this.filter; }
    if (this.filterPanel != null) { props.filterPanel = this.filterPanel; }
    if (this.showTableControls != null) { props.showTableControls = this.showTableControls; }
    if (this.showTipsControl != null) { props.showTipsControl = this.showTipsControl; }
    if (this.tips != null) { props.tips = this.tips; }
    if (this.preferencesPanel != null) { props.preferencesPanel = this.preferencesPanel; }
    if (this.columnsPanel != null) { props.columnsPanel = this.columnsPanel; }
    if (this.sortPanel != null) { props.sortPanel = this.sortPanel; }    
    if (this.filterPanelFocusId != null) { props.filterPanelFocusId = this.filterPanelFocusId; }
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
@JS_NS@.widget.table.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {
        this._actionsContainer.id = this.id + "_actionsContainer";
        this._actionsNode.id = this.id + "_actionsNode";
        this._controlsNode.id = this.id + "_controlsNode";
        this._controlsPanel.id = this.id + "_controlsPanel";
        this._preferencesPanelContainer.id = this.id + "_preferencesPanelContainer";
        this._sortPanelContainer.id = this.id + "_sortPanelContainer";
        this._rowGroupsContainer.id = this.id + "_rowGroupsContainer";
        this._captionContainer.id = this.id + "_captionContainer";        
        this._filterContainer.id = this.id + "_filterContainer";
        this._customFilterPanel.id = this.id + "_customFilterPanel";
        this._tableTipsContainer.id = this.id + "_tableTipsContainer";
        this._tableTips.id = this.id + "_tableTips";
        this._tableControlsContainer.id = this.id + "_tableControlsContainer";        
        this._columnsPanelContainer.id = this.id + "_columnsPanelContainer";
        this._searchContainer.id = this.id + "_searchContainer";
    }
    
    if (this._tableControlBtn == null) {
        this._tableControlBtn = {
                    id: this.id + "_tableControlBtn",                      
                    value: this._theme.getMessage("table2.button.tableControls"),  
                    visible: this.showTableControls,
                    onClick: "@JS_NS@.widget.common.getWidget('" + this.id + "').toggleTableControls();return false;",
                    widgetType: "button"
        };
    }
    if (this._tableTipsBtn == null) {
        this._tableTipsBtn = {
                    id: this.id + "_tableTipsBtn",                                        
                    value: this._theme.getMessage("table2.button.tableTips"),  
                    visible: this.showTipsControl,
                    onClick: "@JS_NS@.widget.common.getWidget('" + this.id + "').toggleTableTips();return false;",
                    widgetType: "button"
        };
    }
    if (this._tableTipsCloseBtn == null) {
        this._tableTipsCloseBtn = {
                    id: this.id + "_tableTipsCloseBtn",                                        
                    value: this._theme.getMessage("table2.button.closeTips"),     
                    onClick: "@JS_NS@.widget.common.getWidget('" + this.id + "').toggleTableTips();return false;",
                    widgetType: "button"
        };
    }
    if (this._preferencesBtn == null) {
        this._preferencesBtn = {
                    id: this.id + "_preferencesBtn",                                        
                    value: this._theme.getMessage("table2.button.preferences"),     
                    onClick: "@JS_NS@.widget.common.getWidget('" + this.id + "').togglePreferencesPanel();return false;",
                    visible: false,
                    widgetType: "button"
        };
    }
    if (this._multipleSortBtn == null) {
        this._multipleSortBtn = {
                    id: this.id + "_multipleSortBtn",                                        
                    value: this._theme.getMessage("table2.button.sort"),     
                    onClick: "@JS_NS@.widget.common.getWidget('" + this.id + "').toggleSortPanel();return false;",
                    visible: false,
                    widgetType: "button"
        };
    }
    if (this._columnsBtn == null) {
        this._columnsBtn = {
                    id: this.id + "_columnsBtn",                                        
                    value: this._theme.getMessage("table2.button.columns"),     
                    onClick: "@JS_NS@.widget.common.getWidget('" + this.id + "').toggleColumnsPanel();return false;",
                    visible: false,
                    widgetType: "button"
        };
    }
    if (this._searchBtn == null) {
        this._searchBtn = {
                    id: this.id + "_searchBtn",                                        
                    value: this._theme.getMessage("table2.button.search"),     
                    onClick: "@JS_NS@.widget.common.getWidget('" + this.id + "').toggleFilterPanel();return false;",
                    widgetType: "button"
        };
    }
    // Subscribe to the "filter" event present in the tableRowGroup widget.
    this._widget.subscribe(@JS_NS@.widget.tableRowGroup.event.filter.filterTextTopic,
        this, "_setFilterText");
    /** @ignore */
    this._domNode.toggleFilterPanel = function(show) { return @JS_NS@.widget.common.getWidget(this.id).toggleFilterPanel(show); };            
    /** @ignore */
    this._domNode.togglePreferencesPanel = function() { return @JS_NS@.widget.common.getWidget(this.id).togglePreferencesPanel(); };
    /** @ignore */
    this._domNode.toggleColumnsPanel = function() { return @JS_NS@.widget.common.getWidget(this.id).toggleColumnsPanel(); };
    /** @ignore */
    this._domNode.toggleSortPanel = function() { return @JS_NS@.widget.common.getWidget(this.id).toggleSortPanel(); };
    /** @ignore */
    this._domNode.toggleTableTips = function() { return @JS_NS@.widget.common.getWidget(this.id).toggleTableTips(); };
    /** @ignore */
    this._domNode.toggleTableControls = function() { return @JS_NS@.widget.common.getWidget(this.id).toggleTableControls(); };
    
    // Add search button
    if (this._searchBtn) {               
        this._widget._addFragment(this._searchContainer, this._searchBtn);                
    }
    //add prefrences button
    if (this._preferencesBtn) {
        this._widget._addFragment(this._tableControlsContainer, this._preferencesBtn, "last");
    }    
    //add columns button
    if (this._columnsBtn) {
        this._widget._addFragment(this._tableControlsContainer, this._columnsBtn, "last");
    }    
    // add multiple sort button
    if (this._multipleSortBtn) {
        this._widget._addFragment(this._tableControlsContainer, this._multipleSortBtn, "last");
    }
    // Add table control button.    
    if (this._tableControlBtn) {        
        this._widget._addFragment(this._controlsNode, this._tableControlBtn, "last");        
    }    
    // Add table tips button.    
    if (this._tableTipsBtn) {        
        this._widget._addFragment(this._controlsNode, this._tableTipsBtn, "last");
    }    
    // Add table tips close button.    
    if (this._tableTipsCloseBtn) {        
        this._widget._addFragment(this._tableTipsCloseBtnContainer, this._tableTipsCloseBtn);
    }
    if (this.tips == null) {
        this.tips = this._theme.getMessage("table2.tips.default");
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
@JS_NS@.widget.table.prototype.setProps = function(props, notify) {
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
 * This function is used to set Table Controls section visible/hidden. 
 * 
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.table.prototype.toggleTableControls = function() {
    var domNodeControls = document.getElementById(this._controlsPanel.id); 
    var domNodeTips = document.getElementById(this._tableTipsContainer.id);
    if (domNodeTips) {
        this._common._setVisibleElement(domNodeTips, false);
    }
    var flag = @JS_NS@._base.common._isVisibleElement(domNodeControls);
    // toggle filter sections.
    this._common._setVisibleElement(domNodeControls, !flag);
    return true;    
};

/**
 * This function is used to set Table tips section visible/hidden.
 * 
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.table.prototype.toggleTableTips = function() {
    var domNodeControls = document.getElementById(this._controlsPanel.id); 
    var domNodeTips = document.getElementById(this._tableTipsContainer.id);
    if (domNodeControls) {
        this._common._setVisibleElement(domNodeControls, false);
    }
    var flag = @JS_NS@._base.common._isVisibleElement(domNodeTips);
    // toggle tips sections.
    this._common._setVisibleElement(domNodeTips, !flag);
    return true;    
};

/**
 * This function is use to set Custom Filter hidden/visible. 
 * @param {boolean} show 
 * @return {boolean} true if successful; 
 */
@JS_NS@.widget.table.prototype.toggleFilterPanel = function(show) {
    var domNode = document.getElementById(this._customFilterPanel.id);  
    var flag = @JS_NS@._base.common._isVisibleElement(domNode);
    if (show != null) {
      this._common._setVisibleElement(domNode, show);
    } else {
      this._common._setVisibleElement(domNode, !flag);  
    }  
    if (flag == true) {
      var filterPanelElement = document.getElementById(this.filterPanelFocusId);
      if (filterPanelElement != null) {filterPanelElement.focus();}
    }
    return true;    
};

/**
 * This function is use to set Preferences panel hidden/visible.  
 *  
 * @return {boolean} true if successful; 
 */
@JS_NS@.widget.table.prototype.togglePreferencesPanel = function() {
    var domNodePreferences = document.getElementById(this._preferencesPanelContainer.id); 
    var domNodeSort = document.getElementById(this._sortPanelContainer.id); 
    var domNodeColumns = document.getElementById(this._columnsPanelContainer.id); 
    if (domNodeSort) {
        this._common._setVisibleElement(domNodeSort, false);
    }
    if (domNodeColumns) {
        this._common._setVisibleElement(domNodeColumns, false);
    }
    // toggle preferences section.
    var flag = @JS_NS@._base.common._isVisibleElement(domNodePreferences);
    this._common._setVisibleElement(domNodePreferences, !flag);
    return true;    
};

/**
 * This function is used to set Multiple Sort panel hidden/visible.  
 * 
 * @return {boolean} true if successful; 
 */
@JS_NS@.widget.table.prototype.toggleSortPanel = function() {
    var domNodePreferences = document.getElementById(this._preferencesPanelContainer.id); 
    var domNodeSort = document.getElementById(this._sortPanelContainer.id); 
    var domNodeColumns = document.getElementById(this._columnsPanelContainer.id); 
    if (domNodePreferences) {
        this._common._setVisibleElement(domNodePreferences, false);
    }
    if (domNodeColumns) {
        this._common._setVisibleElement(domNodeColumns, false);
    }    
    // toggle multiple sort section.
    var flag = @JS_NS@._base.common._isVisibleElement(domNodeSort);
    this._common._setVisibleElement(domNodeSort, !flag);
    return true;    
};

/**
 * This function is used to set columns panel hidden/visible.  
 * 
 * @return {boolean} true if successful; 
 */
@JS_NS@.widget.table.prototype.toggleColumnsPanel = function() {
    var domNodePreferences = document.getElementById(this._preferencesPanelContainer.id); 
    var domNodeSort = document.getElementById(this._sortPanelContainer.id); 
    var domNodeColumns = document.getElementById(this._columnsPanelContainer.id); 
    if (domNodePreferences) {
        this._common._setVisibleElement(domNodePreferences, false);
    }
    if (domNodeSort) {
        this._common._setVisibleElement(domNodeSort, false);
    }       
    // toggle columns panel section.
    var flag = @JS_NS@._base.common._isVisibleElement(domNodeColumns);
    this._common._setVisibleElement(domNodeColumns, !flag);
    return true;    
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
@JS_NS@.widget.table.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // To do: Add tabIndex to subwidgets, but not table, tr, or td tags.
    props.tabIndex = null;

    // Set properties.
    if (props.align != null) { this._domNode.align = props.align; }
    if (props.width != null) { this._domNode.style.width = props.width; }

    // Add caption.
    //if (props.caption || props.filterText && this.caption) {   
    if (props.caption) {
        this.caption = props.caption;
        
        this._widget._addFragment(this._captionContainer, props.caption);
        this._common._setVisibleElement(this._captionContainer, true);
    }

    // Add actions.
    if (props.actions) {       
        if (props.actions instanceof Array) {
            for (var i = 0; i < props.actions.length; i++) {
              this._widget._addFragment(this._actionsNode, props.actions[i], "last");
            }            
        } else {
            this._widget._addFragment(this._actionsNode, props.actions, "last");
        }
        this._common._setVisibleElement(this._actionsNode, true);
    }
    // Add basic filter
    if (props.filter) {               
        this._widget._addFragment(this._filterContainer, props.filter);                
    }
    
    // Add custom filter
    if (props.filterPanel) {               
        this._widget._addFragment(this._customFilterPanel, props.filterPanel);                
    }
    //set table control button visible/hidden
    if (props.showTableControls != null) {        
        var tableControlBtn = @JS_NS@.widget.common.getWidget(this._tableControlBtn.id);
        if (tableControlBtn) {
            tableControlBtn.setProps({visible:props.showTableControls});            
        }
    }
    //set table tips button visible/hidden
    if (props.showTipsControl != null) {        
        var tableTipsBtn = @JS_NS@.widget.common.getWidget(this._tableTipsBtn.id);
        if (tableTipsBtn) {
            tableTipsBtn.setProps({visible:props.showTipsControl});            
        }
    }
    
    // Add table tips
    if (props.tips) {
        this._widget._addFragment(this._tableTips, props.tips);
    }
    // Add preferences panel
    if (props.preferencesPanel) {
        var id = this.id + "_preferencesBtn";
        var preferencesBtn = document.getElementById(id);
        if (preferencesBtn) {
            this._common._setVisibleElement(preferencesBtn, true);
        }
        this._widget._addFragment(this._preferencesPanelContainer, props.preferencesPanel);
    }
    // Add columns panel
    if (props.columnsPanel) {
        var id = this.id + "_columnsBtn";
        var columnsBtn = document.getElementById(id);
        if (columnsBtn) {
            this._common._setVisibleElement(columnsBtn, true);
        }
        this._widget._addFragment(this._columnsPanelContainer, props.columnsPanel);
    }
    // Add multiple sort panel
    if (props.sortPanel) {        
        var id = this.id + "_multipleSortBtn";
        var multipleSortBtn = document.getElementById(id);
        if (multipleSortBtn) {
            this._common._setVisibleElement(multipleSortBtn, true);
        }
        this._widget._addFragment(this._sortPanelContainer, props.sortPanel);
    }
    if (props.filterPanelFocusId) {
        // update the this.filterPanelFocusId, will be used to set the filter panle element focus when visible. 
        this.filterPanelFocusId = props.filterPanelFocusId;        
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
            
        }
    }

    // Set more properties.
    this._setCommonProps(this._domNode, props);
    this._setEventProps(this._domNode, props);

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};

/**
 * This function is called when a day link is selected from the calendar.
 * It updates the field with the value of the clicked date.
 *
 * @param props Key-Value pairs of properties.
 * @config {String} id 
 * @config {String} filterText
 * @return {boolean} false to cancel JavaScript event.
 * @private
 */
@JS_NS@.widget.table.prototype._setFilterText = function(props) {
    
    if (props.filterText != null) {
        var filterText = null;
        if (props.filterText) {
            filterText = this._theme.getMessage("table.title.filterApplied", [
                props.filterText
            ]);
        }
        // check for the ids
        var flag = false;
        for (var i = 0; i < this.rowGroups.length; i++) {
            if (this.rowGroups[i].id == props.id) {
                flag = true;                
                break;
            }
        }
        if (flag == true) {        
            this._widget._addFragment(this._captionContainer, (filterText) 
                ? this.caption + filterText : this.caption);
            this._common._setVisibleElement(this._captionContainer, true);
        }
    }
    return false;
};
