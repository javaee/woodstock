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

webui.@THEME_JS@._base.dojo.provide("webui.@THEME_JS@.widget.table2RowGroup");

webui.@THEME_JS@._base.dojo.require("webui.@THEME_JS@._base.browser");
webui.@THEME_JS@._base.dojo.require("webui.@THEME_JS@.widget.common");
webui.@THEME_JS@._base.dojo.require("webui.@THEME_JS@.widget._base.widgetBase");
webui.@THEME_JS@._base.dojo.require("webui.@THEME_JS@._base.theme.common");

/**
 * This function is used to construct a table2RowGroup widget.
 *
 * @name webui.@THEME_JS@.widget.table2RowGroup
 * @extends webui.@THEME_JS@.widget._base.widgetBase
 * @class This class contains functions for the table2RowGroup widget.
 * @constructor
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} align Alignment of image input.
 * @config {String} bgColor
 * @config {String} char
 * @config {String} charOff
 * @config {String} className CSS selector.
 * @config {Array} columns
 * @config {String} dir Specifies the directionality of text.
 * @config {int} first
 * @config {String} headerText
 * @config {int} height
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {int} maxRows 
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
 * @config {Array} rows 
 * @config {String} style Specify style rules inline.
 * @config {String} title Provides a title for element.
 * @config {int} totalRows 
 * @config {String} valign 
 * @config {boolean} visible Hide or show element.
 */
webui.@THEME_JS@._base.dojo.declare("webui.@THEME_JS@.widget.table2RowGroup",
        webui.@THEME_JS@.widget._base.widgetBase, {
    // Set defaults.
    constructor: function() {
        this.currentRow = 0; // Current row in view.
        this.first = 0; // Index used to obtain rows.
        this.sortCount = 0; // sort count 
        this.colSortLevel = new Array(); // Array used to store sortLevel
    },
    // Default sorting options.
    _primarySortOptions: [{
        "group": false,
        "value": "sort",
        "label": webui.@THEME_JS@._base.theme.common._getMessage("table2.sortByThisColumn"),
        "disabled": true,
        "separator": false,
        "escape": true
    }, {
        "group": false,
        "value": "primaryAscending",
        "label": webui.@THEME_JS@._base.theme.common._getMessage("table2.sortAscending"),
        "disabled": false,
        "separator": false,
        "escape": true
    }, {
        "group": false,
        "value": "primaryDescending",
        "label": webui.@THEME_JS@._base.theme.common._getMessage("table2.sortDescending"),
        "disabled": false,
        "separator": true,
        "escape": true
    }],
    // Clear sorting options.
    _clearSortOptions: [{
        "group": false,
        "value": "clear",
        "label": webui.@THEME_JS@._base.theme.common._getMessage("table2.clearSort"),
        "disabled": false,
        "separator": false,
        "escape": true
    }],
    // Secondary sorting options.
    _secondarySortOptions: [{
        "group": false,
        "value": "sort",
        "label": webui.@THEME_JS@._base.theme.common._getMessage("table2.addColumnSort"),
        "disabled": true,
        "separator": false,
        "escape": true
    }, {
        "group": false,
        "value": "ascending",
        "label": webui.@THEME_JS@._base.theme.common._getMessage("table2.sortAscending"),
        "disabled": false,
        "separator": false,
        "escape": true
    }, {
        "group": false,
        "value": "descending",
        "label": webui.@THEME_JS@._base.theme.common._getMessage("table2.sortDescending"),
        "disabled": false,
        "separator": true,
        "escape": true
    }],
    _widgetType: "table2RowGroup" // Required for theme properties.
});

/**
 * This function is used to set column headers and footers.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.table2RowGroup.prototype._addColumns = function() {
    // Clear column headers/footers.
    this._widget._removeChildNodes(this.thead);
    this._widget._removeChildNodes(this.tfoot);

    // Clone dojo attach points.
    var headerRowClone = this.colHeaderRow.cloneNode(false);
    var footerRowClone = this.colFooterRow.cloneNode(false);

    // Append row nodes.
    this.thead.appendChild(headerRowClone);
    this.tfoot.appendChild(footerRowClone);

    // Append cell nodes.
    for (var i = 0; i < this.columns.length; i++) {
        var col = this.columns[i];
        var headerCellClone = this.colHeaderCell.cloneNode(false);
        var footerCellClone = this.colFooterCell.cloneNode(true);
        
        var colHeaderLink = this.colHeaderLink.cloneNode(false);
        colHeaderLink.id = col.id + "_colHeaderLink";

        // Set properties.
        headerCellClone.id = col.id + "_colHeader";
        footerCellClone.id = col.id + "_colFooter";
        headerCellClone.appendChild(colHeaderLink);
        if (col.width) {
            headerCellClone.style.width = col.width;
            footerCellClone.style.width = col.width;
        }
        if (!col.sortLevel) col.sortLevel = -1;
        // Add text.
        if (col.headerText && col.sort == true) {
            // StaticText widget adds span to match styles.
            //
            // To do: Create utility to help create client-side widgets.                      
            if (colHeaderLink != null) {
                this._widget._addFragment(colHeaderLink, {
                        id: headerCellClone.id + "_Text",
                        onClick: "webui.@THEME_JS@.widget.common.getWidget('" + this.id + "')._openSortMenu(event, '" + col.id + "', '" + col.sortLevel + "');",
                        value: col.headerText,
                        widgetType: "staticText"
                    }, "last");
            }        
            
            } else if(col.headerText && col.sort == false) {            
                if (headerCellClone != null) {
                    this._widget._updateFragment(headerCellClone, headerCellClone.id, {
                            id: headerCellClone.id + "_Text",                    
                            value: col.headerText,
                            widgetType: "staticText"
                        });
                }    
            }
        
        if (col.footerText) {
            // StaticText widget adds span to match styles.
            //
            // To do: Create utility to help create client-side widgets.
            this._widget._addFragment(footerCellClone, {
                id: footerCellClone.id + "Text",
                value: col.footerText,
                widgetType: "staticText"
            });
            footerVisible = true;
        }

        // Append nodes.
        headerRowClone.appendChild(headerCellClone);
        footerRowClone.appendChild(footerCellClone);

        // Set colspan.
        this.groupHeaderCell.colSpan = this.columns.length;
    }
    return true;
};
 
/**
 * This function is used to add rows using the gieven array. Each row contains
 * an array of columns which holds table data.
 *
 * @param {Array} rows An array of rows.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.table2RowGroup.prototype.addRows = function(rows) {
    if (rows == null) {
        return false;
    }

    // Get properties.
    var props = this.getProps();
    // Get className properties to alternate between rows.
    var classNames = (this.className) ? this.className.split(",") : null;          

    // For each row found, clone the tableDataRow attach point.
    for (var i = 0; i < rows.length; i++) {
        var cols = rows[i]; // Get columns.
        var rowId = this.id + ":" + (this.first + i); // Get row id.

        // Clone table data row without cells.
        var rowClone = this.tableDataRow.cloneNode(false);
        this.tbody.appendChild(rowClone);

        // Set properties.
        props.id = rowId;

        // Set className.
        if (classNames) {
            props.className = classNames[i % classNames.length];
        }

        this._setCommonProps(rowClone, props);
        this._setEventProps(rowClone, props);
        this._setCoreProps(rowClone, props);

        // For each column found, clone the tableDataCell attach point.
        for (var k = 0; k < cols.length; k++) {
            var col = this.columns[k]; // Get current column.
            var colId = col.id.replace(this.id, rowId); // Get col id.

            // Clone node.
            var cellClone = this.tableDataCell.cloneNode(true);
            rowClone.appendChild(cellClone);

            // Set properties.
            this._setColumnProps(cellClone, col);
            cellClone.id = colId; // Override id set by _setCoreProps.
            if (col.sortLevel == 1) {
                cellClone.className = this._theme._getClassName("TABLE2_PRIMARYSORT");            
            }

            // Add cell data.
            this._widget._addFragment(cellClone, cols[k], "last");
        }

        // Save row for destroy() function.
        if (this.first > 0) {
            this.rows[this.rows.length] = rows[i];
        }
    }

    // Set first row value.
    this.first += rows.length;

    // Adjust layout.
    var _id = this.id;
    setTimeout(function() {
        // New literals are created every time this function is called, and it's 
        // saved by closure magic.
        webui.@THEME_JS@.widget.common.getWidget(_id)._resize();
    }, 10);
    return true;
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
webui.@THEME_JS@.widget.table2RowGroup.event =
        webui.@THEME_JS@.widget.table2RowGroup.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_table2RowGroup_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_table2RowGroup_event_refresh_end"
    },

    /**
     * This closure is used to process scroll events.
     * @ignore
     */
    scroll: {
        /** Scroll event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_table2RowGroup_event_scroll_begin",

        /** Scroll event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_table2RowGroup_event_scroll_end"
    },
    
    /**
     * This closure is used to process pagination button events.
     * @ignore
     */
    pagination: {
        /**
         * This closure is used to process next pagination button event.
         * @ignore
         */
        next: {
            /** Scroll event topic for custom AJAX implementations to listen for. */
            beginTopic: "webui_@THEME_JS@_widget_table2RowGroup_event_pagination_next_begin",

            /** Scroll event topic for custom AJAX implementations to listen for. */
            endTopic: "webui_@THEME_JS@_widget_table2RowGroup_event_pagination_next_end"
        },

        /**
         * This closure is used to process previous pagination button event.
         * @ignore
         */
        previous: {
            /** Scroll event topic for custom AJAX implementations to listen for. */
            beginTopic: "webui_@THEME_JS@_widget_table2RowGroup_event_pagination_previous_begin",

            /** Scroll event topic for custom AJAX implementations to listen for. */
            endTopic: "webui_@THEME_JS@_widget_table2RowGroup_event_pagination_previous_end"
        }
        
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_table2RowGroup_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_table2RowGroup_event_state_end"
    },
    
    /**
     * This object contains state event topics.
     * @ignore
     */
    sort: {
        /** sort event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_table2RowGroup_event_sort_begin",

        /** sort event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_table2RowGroup_event_sort_end"
    }
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME_JS@.widget.table2RowGroup.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.align) { props.align = this.align; }
    if (this.bgColor) { props.bgColor = this.bgColor; }
//    if (this.char) { props.char = this.char; } // To do: Rename -- keyword is reserved.
    if (this.charOff) { props.charOff = this.charOff; }
    if (this.columns) { props.columns = this.columns; }
    if (this.first) { props.first = this.first; }
    if (this.headerText) { props.headerText = this.headerText; }
    if (this.height) { props.height = this.height; }
    if (this.maxRows) { props.maxRows = this.maxRows; }
    if (this.rows) { props.rows = this.rows; }
    if (this.totalRows) { props.totalRows = this.totalRows; }
    if (this.valign) { props.valign = this.valign; }
    if (this.paginationControls != null) {props.paginationControls = this.paginationControls;}
    if (this.paginationNextButton) {props.paginationNextButton = this.paginationNextButton;}
    if (this.paginationPrevButton) {props.paginationPrevButton = this.paginationPrevButton;}
    if (this.sortPopupMenu) {props.sortPopupMenu = this.sortPopupMenu;}

    return props;
};

/**
 * Process next control button.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.table2RowGroup.prototype._paginationNext = function(event) {
    // Publish event to retrieve data.
    var currentPage = Math.floor(this.currentRow / this.maxRows) + 1;
    var totalPage = Math.floor(this.totalRows / this.maxRows);
    if (this.first < this.totalRows
            && currentPage < totalPage) {
        // Publish an event for custom AJAX implementations to listen for.
        this._publish(webui.@THEME_JS@.widget.table2RowGroup.event.pagination.next.beginTopic, [{
            id: this.id,
            first: this.first 
        }]);
    }     
    if (currentPage < totalPage) {          
        // Calculate current row.          
        this.currentRow = currentPage * this.maxRows;        
        // set scroll position to make the current row completely visible
        this.tableContainer.scrollTop =  
            document.getElementById(this.id + ":" + this.currentRow).offsetTop;
    }       
    return this._updateRowsText();
};

/**
 * Process previous control button.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.table2RowGroup.prototype._paginationPrevious = function(event) {
    var currentPage = Math.ceil(this.currentRow / this.maxRows) + 1;
    var totalPage = Math.floor(this.totalRows / this.maxRows);
    if (currentPage > 1) {                 
        this.currentRow = (currentPage - 2) * this.maxRows;
        // set scroll position to make the current row completely visible
        this.tableContainer.scrollTop = 
            document.getElementById(this.id + ":" + this.currentRow).offsetTop;        
    }    
    return this._updateRowsText();
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
webui.@THEME_JS@.widget.table2RowGroup.prototype._postCreate = function () {
    // Set ids.    
    if (this.id) {
        this.colFooterRow.id = this.id + "_colFooterRow";
        this.colFooterCell.id = this.id + "_colFooterCell";
        this.colHeaderRow.id = this.id + "_colHeaderRow";
        this.colHeaderCell.id = this.id + "_colHeaderCell";
        this.colHeaderLink.id = this.id + "_colHeaderLink";
        this.groupHeaderControls.id = this.id + "_groupHeaderControls";
        this.groupHeaderText.id = this.id + "_groupHeaderText";        
        this.rowsText.id = this.id + "_rowsText";
        this.table.id = this.id + "_table";
        this.tableContainer.id = this.id + "_tableContainer";
        this.tableDataRow.id = this.id + "_tableDataRow";
        this.tableDataCell.id = this.id + "_tableDataCell";
        this.tbody.id = this.id + "_tbody";
        this.tfoot.id = this.id + "_tfoot";
        this.thead.id = this.id + "_thead";        
        this.paginationButtonsNode.id = this.id + "_paginationButtonsNode";           
    }

    // Set events.
    this._dojo.connect(this.tableContainer, "onscroll", this, "_scroll");

    // Set pagination controls.
    if (this.paginationPrevButton == null) {
        this.paginationPrevButton = {
            id: this.id + "_paginationPrevButton",
            enabledImage: {
                icon: "TABLE2_PAGINATION_PREV",
                id: this.id + "_paginationPrevButtonImg",
                widgetType: "image"
            },
            disabledImage: {
                icon: "TABLE2_PAGINATION_PREV_DISABLED",
                id: this.id + "_paginationPrevButtonImgDis",
                widgetType: "image"
            },
            title: this._theme._getMessage("table2.pagination.previous"),
            widgetType: "imageHyperlink"
        };
    }
      
    if (this.paginationNextButton == null) {
        this.paginationNextButton = {
            id: this.id + "_paginationNextButton",
            enabledImage: {
                icon: "TABLE2_PAGINATION_NEXT",
                id: this.id + "_paginationNextButtonImg",
                widgetType: "image"
            },
            disabledImage: {
                icon: "TABLE2_PAGINATION_NEXT_DISABLED",
                id: this.id + "_paginationNextButtonImgDis",
                widgetType: "image"
            },
            title: this._theme._getMessage("table2.pagination.next"),
            widgetType: "imageHyperlink"
        };
    }
 
    if (this.sortPopupMenu == null) {
        this.sortPopupMenu = {
                    id: this.id + "_popupMenu",
                    visible: false,
                    options: this._primarySortOptions,
                    widgetType: "popupMenu"
        };
    }
    // Resize hack for Moz/Firefox.
    if (webui.@THEME_JS@._base.browser._isNav()) {
        this._dojo.connect(window, "onresize", this, "_resize");
    }        
    return this._inherited("_postCreate", arguments);
};

/**
 * Process resize event.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.table2RowGroup.prototype._resize = function() {
    // Update rows text.
    this._updateRowsText();

    // Get height offset of each row.
    var offset = 0;
    for (var i = this.currentRow; i < this.currentRow + this.maxRows; i++) {
        var tableDataRow = document.getElementById(this.id + ":" + i);
        if (tableDataRow != null) {
            offset += tableDataRow.offsetHeight;
        } else {
            break;
        }
    }

    // Set the scrollable height.
    if (offset > 0) {
        this.tableContainer.style.height = offset + "px";
    }

    // Set width of each column header & footer.
    var rowId = this.id + ":0"; // ID of first row.
    for (var i = 0; i < this.columns.length; i++) {
        var col = this.columns[i]; // Get default column props.
        var colId = col.id.replace(this.id, rowId);

        // Get row node.
        var tableDataCell = document.getElementById(colId);
        if (tableDataCell == null) {
            continue;
        }

        // Get nodes.
        var colHeaderCell = document.getElementById(col.id + "_colHeader");
        var colFooterCell = document.getElementById(col.id + "_colFooter");

        // Set width.
        if (colHeaderCell) {
            // Column width plus offset for border.
            colHeaderCell.style.width = (tableDataCell.offsetWidth - 1) + "px";
        }
        if (colFooterCell) {
            // Column width plus offset for border.
            colFooterCell.style.width = (tableDataCell.offsetWidth - 1) + "px";
        }
    }

    // Update header & footer position.
    var colHeaderRow = document.getElementById(this.id + "_colHeaderRow");
    var colFooterRow = document.getElementById(this.id + "_colFooterRow");

    var headerHeight = (colHeaderRow) ? colHeaderRow.offsetHeight : 0;
    var footerHeight = (colFooterRow) ? colFooterRow.offsetHeight : 0;

    this.tableContainer.style.marginTop = (headerHeight - 1) + 'px';
    this.tableContainer.style.marginBottom = footerHeight + 'px';

    // Column header height plus offset for border.
    if (colHeaderRow) {
        colHeaderRow.style.top = (this.tableContainer.offsetTop - 
            headerHeight + 1) + 'px';
    }

    // Column footer height plus offset for border.
    if (colFooterRow) {
        colFooterRow.style.top = (this.tableContainer.offsetTop + 
            this.tableContainer.offsetHeight - 1) + 'px';
    }
    return true;
};

/**
 * This function is used to set column properties with Object literals.
 *
 * @param {Node} domNode The DOM node to assign properties to.
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} abbr
 * @config {String} axis
 * @config {String} bgColor
 * @config {String} char
 * @config {String} charOff
 * @config {String} className CSS selector.
 * @config {int} colspan
 * @config {String} dir Specifies the directionality of text.
 * @config {String} height
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {boolean} noWrap 
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
 * @config {int} rowSpan 
 * @config {String} scope 
 * @config {String} style Specify style rules inline.
 * @config {String} title Provides a title for element.
 * @config {String} valign 
 * @config {boolean} visible Hide or show element.
 * @config {String} width
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.table2RowGroup.prototype._setColumnProps = function(domNode, props) {
    // Set properties.
    if (this.abbr) { domNode.abbr = this.abbr; }
    if (this.axis) { domNode.axis = this.axis; }
    if (this.bgColor) { domNode.bgColor = this.bgColor; }
//    if (this.char) { domNode.char = this.char; } // To do: Rename -- keyword is reserved.
    if (this.charOff) { domNode.charoff = this.charOff; }
    if (this.thisspan) { domNode.colspan = this.colspan; }
    if (this.headers) { domNode.headers = this.headers; }
    if (this.height) { domNode.height = this.height; }
    if (this.noWrap) { domNode.nowrap = "nowrap"; }
    if (this.rowSpan) { domNode.rowspan = this.rowSpan; }
    if (this.scope) { domNode.scope = this.scope; }
    if (this.valign) { domNode.valign = this.valign; }
    if (this.width) { domNode.width = this.width; }

    // Set more properties.
    this._setCommonProps(domNode, props);
    this._setEventProps(domNode, props);
    this._setCoreProps(domNode, props);

    return true;
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
webui.@THEME_JS@.widget.table2RowGroup.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace columns -- do not extend.
    if (props.columns) {
        this.columns = null;
    }

    // Replace rows -- do not extend.
    if (props.rows) {
        this.rows = null;
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
webui.@THEME_JS@.widget.table2RowGroup.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.id) { this.domNode.id = props.id; }

    // Set private properties for table widget.
    if (props._table) {
        if (props._table.bgColor) { this.table.bgColor = props._table.bgColor; }
        if (props._table.border) { this.table.border = props._table.border; }
        if (props._table.cellpadding) { this.table.cellpadding = props._table.cellpadding; }
        if (props._table.cellspacing) { this.table.cellspacing = props._table.cellspacing; }
        if (props._table.frame) { this.table.frame = props._table.frame; }
        if (props._table.rules) { this.table.rules = props._table.rules; }
        if (props._table.summary) { this.table.summary = props._table.summary; }
    }

    // Add header.
    if (props.headerText) {
        this._widget._updateFragment(this.groupHeaderText, this.groupHeaderText.id, props.headerText);
        this._common._setVisibleElement(this.groupHeaderContainer, true);
    }
    // Add paginationControl.    
    if (props.paginationPrevButton) {
        // set onclick for previous button.
        props.paginationPrevButton.onClick = 
            "webui.@THEME_JS@.widget.common.getWidget('" + this.id + "')._paginationPrevious();return false;";        
        this._widget._addFragment(this.paginationButtonsNode, props.paginationPrevButton,"last");
    }
    if (props.paginationNextButton) {
        // set onclick for next button.
        props.paginationNextButton.onClick = 
            "webui.@THEME_JS@.widget.common.getWidget('" + this.id + "')._paginationNext();return false;";
        this._widget._addFragment(this.paginationButtonsNode, props.paginationNextButton,"last");
    }
    if (props.paginationControls != null) {        
        this.paginationControls = props.paginationControls;
    }
    //set enabled/disabled state for pagination controls
    this._updatePaginationControls(); 
    //popup menu
    if (props.sortPopupMenu) {
        webui.@THEME_JS@.widget.common._updateFragment(this.sortMenu, props.sortPopupMenu.id, props.sortPopupMenu);
    }
        
    // Set columns.
    if (props.columns && this.refreshCols != false) {
        this._addColumns();
        // To Do: Cannot refresh column headers/footers due to poor CSS styles.
        this.refreshCols = false;
    }
    
    //update the sortLevel values client-side
    if (props.columns) {        
        for (var i=0; i < this.colSortLevel.length; i++) {
            if (props.columns[i].sortLevel == null) {
                props.columns[i].sortLevel = this.colSortLevel[i];
            }  
        }    
    }

    // Add rows.
    if (props.rows) {
        this.first = 0; // Reset index used to obtain rows.
        this.currentRow = 0; // Reset current row in view.
        this.tableContainer.scrollTop = 0; 
        // Clear rows.
        this._widget._removeChildNodes(this.tbody);
        this.addRows(props.rows);
    }
   
    // Cannot call "superclass" here because properties are set on each row.
    return true;
};

/**
 * Process scroll event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.table2RowGroup.prototype._scroll = function(event) {
    // Publish event to retrieve data.    
    if (this.first < this.totalRows
            && this.currentRow % this.maxRows == 0) {
        // Publish an event for custom AJAX implementations to listen for.
        this._publish(webui.@THEME_JS@.widget.table2RowGroup.event.scroll.beginTopic, [{
            id: this.id,
            first: this.first
        }]);
    }    
    var scrollTop = this.tableContainer.scrollTop;
    var rowHeight =  document.getElementById(this.id + ":" + (this.currentRow + 1)).offsetTop -
                    document.getElementById(this.id + ":" + this.currentRow).offsetTop;
    var moveScroll = scrollTop % rowHeight;
    this.currentRow = Math.floor((this.tableContainer.scrollTop) / rowHeight);    
    
    if (moveScroll > (rowHeight / 2)) {
        this.currentRow = this.currentRow + 1;   
    }   
    // Set rows text.    
    return this._updateRowsText();
};

/**
 * Updates pagination control buttons enabled/disabled state.
 * 
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.table2RowGroup.prototype._updatePaginationControls = function() {
    if (this.paginationPrevButton && this.paginationNextButton) {
        var domNodePrev = this._widget.getWidget(this.paginationPrevButton.id);
        var domNodeNext = this._widget.getWidget(this.paginationNextButton.id);

        if (domNodePrev != null && domNodeNext != null) {
            if (this.currentRow / this.maxRows == 0) {
                domNodePrev.setProps({disabled:true});              
            } else {
                domNodePrev.setProps({disabled:false});  
            } 
            if ((this.currentRow / this.maxRows) == (this.totalRows / this.maxRows) - 1) {
                domNodeNext.setProps({disabled:true});  
            } else {
                domNodeNext.setProps({disabled:false});  
            }
            domNodePrev.setProps({visible:this.paginationControls});
            domNodeNext.setProps({visible:this.paginationControls});  
        }           
    }
    return true;
};

/**
 * This function is used to set rows text (e.g., "1 - 5 of 20").
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.table2RowGroup.prototype._updateRowsText = function() {
    // Add title augment.
    var firstRow = this.currentRow + 1;
    var lastRow = Math.min(this.totalRows, this.currentRow + this.maxRows);

    // To do: Need to create a new rows message.

    // NOTE: If you set this value manually, text must be HTML escaped.
    var msg = this._theme._getMessage("table.title.paginated", [
        "", 
        firstRow, 
        lastRow, 
        this.totalRows, 
        ""
    ]);

    // "Items: " + firstRow + " - " + lastRow + " of " + this.totalRows);
    if (msg) {
        this._widget._addFragment(this.rowsText, msg);
    }
     //set disabled/enabled state
    this._updatePaginationControls(); 
    return true;
};

/**
 * This function is used to set sorting options.
 * @param {Event} event The JavaScript event.
 * @param {String} colId The column id for the sorted column.
 * @param {int} sortLevel The sortLevel for column. 
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.table2RowGroup.prototype._openSortMenu = function(event, 
        colId, sortLevel) {    
    var menu = webui.@THEME_JS@.widget.common.getWidget(this.sortPopupMenu.id);    
    for (var i = 0; i < this.columns.length; i++) {
        var col = this.columns[i];
        if (col.id == colId) {
            sortLevel = col.sortLevel;            
            break;
        }    
    }
    if (menu) {
        if (sortLevel == -1 && this.sortCount == 0) {
            menu.setProps({options:this._primarySortOptions});
        } else if (sortLevel == 1) {
            var menuOptions = (this._primarySortOptions).concat(this._clearSortOptions);
            menu.setProps({options:menuOptions});
        } else if ((sortLevel == -1 || sortLevel > 1) && this.sortCount >= 1) {
	    var menuOptions = ((this._primarySortOptions).concat(
                 this._secondarySortOptions)).concat(this._clearSortOptions);
            menu.setProps({options:menuOptions});
        }
        menu.setProps({onClick: "webui.@THEME_JS@.widget.common.getWidget('" + 
            this.id + "')._sort('" + colId + "');"});          
        menu.open(event);        
    }
    return true;
};

/**
 * This function is used to publish event for sorting
 * 
 * @param {String} colId The column id for the sorted column.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.table2RowGroup.prototype._sort = function(colId) {          
    var menu = webui.@THEME_JS@.widget.common.getWidget(this.sortPopupMenu.id);   
    var value = menu.getSelectedValue();    
    var sortLevel = -1;
    
    // Publish an event for custom AJAX implementations to listen for.
    this._publish(webui.@THEME_JS@.widget.table2RowGroup.event.sort.beginTopic, [{
        id: this.id,
        table2colId: colId,
        sortOrder: menu.getSelectedValue()
    }]);

    // Update sortCount and sortLevel values client-side    
    if ((value == "primaryAscending" || value == "primaryDescending")) {
        sortLevel = 1;
        this.sortCount = 1;
    } else if ((value == "ascending" || value == "descending")) {
        this.sortCount++;
        sortLevel = this.sortCount;
    } else if (value == "clear") {
        this.sortCount = 0;
        for (var i = 0; i < this.columns.length; i++) {
            var col = this.columns[i];
                col.sortLevel = -1;                                            
        }
    } 
    // Update sortLevel value for column
    for (var i = 0; i < this.columns.length; i++) {
        var col = this.columns[i];
        //clear other sort if primary sort is selected
        if (sortLevel == 1) {
            col.sortLevel = -1;
        }
        if (col.id == colId) {
            col.sortLevel = sortLevel;            
            //break;
        }    
        if (col.sortLevel) {
            this.colSortLevel[i] = col.sortLevel;
        } else {
            this.colSortLevel[i] = -1;
        }    
    }    
    return true;    
};
