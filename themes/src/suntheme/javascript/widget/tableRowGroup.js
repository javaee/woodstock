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

@JS_NS@._dojo.provide("@JS_NS@.widget.tableRowGroup");

@JS_NS@._dojo.require("@JS_NS@._base.browser");
@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.stateBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");
@JS_NS@._dojo.require("@JS_NS@.theme.common");

/**
 * This function is used to construct a tableRowGroup widget.
 *
 * @name @JS_NS@.widget.tableRowGroup
 * @extends @JS_NS@.widget._base.widgetBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @extends @JS_NS@.widget._base.stateBase
 * @class This class contains functions for the tableRowGroup widget.
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
 * @config {boolean} autoStart Flag indicating to load data upon widget creation.
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
 * @config {String} filterText
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.tableRowGroup", [
        @JS_NS@.widget._base.refreshBase, 
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.widgetBase ], {
    // Set defaults.
    constructor: function() {
        this._colSortLevel = new Array(); // Array used to store sortLevel
        this._currentRow = 0; // Current row in view.
        this.first = 0; // Index used to obtain rows.                
        this._sortCount = 0; // sort count 
        this.totalRows = 0; // Available rows.
    },
    // Default sorting options.
    _primarySortOptions: [{
        "group": true,
        "value": "sort",
        "label": @JS_NS@.theme.common.getMessage("table2.sortByThisColumn"),
        "disabled": true,
        "separator": false,
        "escape": true,
    "options":[{
        "group": false,
        "value": "primaryAscending",
        "label": @JS_NS@.theme.common.getMessage("table2.sortAscending"),
        "disabled": false,
        "separator": false,
        "escape": true
    }, {
        "group": false,
        "value": "primaryDescending",
        "label": @JS_NS@.theme.common.getMessage("table2.sortDescending"),
        "disabled": false,
        "separator": true,
        "escape": true
    }]}],
    // Clear sorting options.
    _clearSortOptions: [{
        "group": false,
        "value": "clear",
        "label": @JS_NS@.theme.common.getMessage("table2.clearSort"),
        "disabled": false,
        "separator": false,
        "escape": true
    }],
    // Secondary sorting options.
    _secondarySortOptions: [{
        "group": true,
        "value": "sort",
        "label": @JS_NS@.theme.common.getMessage("table2.addColumnSort"),
        "disabled": true,
        "separator": false,
        "escape": true,
    "options":[{
        "group": false,
        "value": "ascending",
        "label": @JS_NS@.theme.common.getMessage("table2.sortAscending"),
        "disabled": false,
        "separator": false,
        "escape": true
    }, {
        "group": false,
        "value": "descending",
        "label": @JS_NS@.theme.common.getMessage("table2.sortDescending"),
        "disabled": false,
        "separator": true,
        "escape": true
    }]}],
    _widgetType: "tableRowGroup" // Required for theme properties.
});

/**
 * This function is used to calculate depth of column headers.
 * @param {Object} col column object.
 * @param {int} counter.
 * @return {int} The column header depth.
 * @private
 */
@JS_NS@.widget.tableRowGroup.prototype._getColumnHeaderRowCount = function(cols, count) {
    
    if (count == null) {
        count = 1; // at least one row.
    }
    for (var i = 0; i < cols.length; i++) {
        var col = cols[i];
        // Look at col span header depth.
        if (col.columns && col.columns.length > 0) {
            count++; // at least one more row.
            var temp = this._getColumnHeaderRowCount(col.columns, count);
            if (temp > count) {
                count = temp;
            }
        }
    }
    return count;    
};

/**
 * This function is used to set column headers and footers.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tableRowGroup.prototype._addColumns = function() {
    // Clear column headers/footers.
    this._widget._removeChildNodes(this._thead);
    this._widget._removeChildNodes(this._tfoot);

    // Clone dojo attach points.
    var headerRowClone = this._colHeaderRow.cloneNode(false);
    headerRowClone.id = this.id + "_colHeaderRow" + 0;
    var footerRowClone = this._colFooterRow.cloneNode(false);

    // Append row nodes.
    this._thead.appendChild(headerRowClone);
    this._tfoot.appendChild(footerRowClone);
   
    var headers = this._getColumnHeaderRowCount(this.columns);       
    
    // Append cell nodes.
    for (var i = 0; i < this.columns.length; i++) {
        var col = this.columns[i];
        if (col.columns && col.columns.length == 0 ) {
            col.rowSpan = headers;
        }
        if (col.columns && col.columns.length > 0 ) {
            col.colSpan = this._getColumnHeaderRowCount(this.columns);
        }    
        this._setColumnHeaderProps(col, headerRowClone);
        if (col.columns && col.columns.length > 0) {
            this._addColumnHeaders(col, 1);            
        }        
    }
    // get the leaf columns.
    var leafColArray = this._getLeafColumns(this.columns);

    // add footer text to leaf columns only.
    for (var j = 0; j < leafColArray.length; j++) {
        this._addColumnFooters(leafColArray[j], footerRowClone);        
    }    
    this._groupHeaderCell.colSpan = leafColArray.length;
    return true;
};

/**
 * This function is used to set column footers.
 * @param {Object} col column object.
 * @param {Node} footerRowClone dom element for the footer row column.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tableRowGroup.prototype._addColumnFooters = function(col, footerRowClone) {               
    // Clone dojo attach points.
    var footerCellClone = this._colFooterCell.cloneNode(true);
    footerCellClone.id = col.id + "_colFooter";
        
    if (col.width) {           
        footerCellClone.style.width = col.width;
    }
    if (col.footerText) {
        this._widget._addFragment(footerCellClone, {
            id: footerCellClone.id + "Text",
            value: col.footerText,
            widgetType: "text"
        });            
    }
    if (footerRowClone && col.footerText) {
        footerRowClone.appendChild(footerCellClone);
    }
};

/**
 * This function is used to add column headers.
 * @param {Object} cols column object.
 * @param {int} counter.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tableRowGroup.prototype._addColumnHeaders = function(cols, count) {
    // Clone dojo attach points.
    var headerRowClone = this._colHeaderRow.cloneNode(false);
    headerRowClone.id = this.id + "_colHeaderRow" + count;
    this._thead.appendChild(headerRowClone);    
    var headers = this._getColumnHeaderRowCount(cols.columns);

    for (j = 0; j < cols.columns.length; j++ ) {
        var col = cols.columns[j];
        if (col.columns && col.columns.length > 0) {
            col.colSpan = col.columns.length;
            this._setColumnHeaderProps(col, headerRowClone);
            count++;
            this._addColumnHeaders(col, count);
        } else {
            col.rowSpan = headers;
            this._setColumnHeaderProps(col, headerRowClone);
        }    
    }            
};

/**
 * This function is used to update column headers.
 * @param {Object} col column object.
 * @param {Node} headerRowClone dom element for the header row column.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tableRowGroup.prototype._setColumnHeaderProps = function(col, headerRowClone) {
    var headerCellClone = this._colHeaderCell.cloneNode(false);       
    var colHeaderLink = this._colHeaderLink.cloneNode(false);
    colHeaderLink.id = col.id + "_colHeaderLink";

    // Set properties.
    headerCellClone.id = col.id + "_colHeader";        
    headerCellClone.appendChild(colHeaderLink);
    if (col.width) {
        headerCellClone.style.width = col.width;            
    }
    if (col.colSpan > 0) {
        headerCellClone.colSpan = col.colSpan;
    }
    if (col.rowSpan > 0) {
        headerCellClone.rowSpan = col.rowSpan;
    }
    if (!col.sortLevel) {
        col.sortLevel = -1;
    }
    
    // Add text.
    if (col.headerText && col.sort == true) {
        if (colHeaderLink != null) {
            this._widget._addFragment(colHeaderLink, {
                id: headerCellClone.id + "_Text",
                onClick: "@JS_NS@.widget.common.getWidget('" + this.id +
                    "')._openSortMenu(event, '" + col.id + "', '" + 
                    col.sortLevel + "');",
                value: col.headerText,
                widgetType: "text"
            }, "last");
        }
    } else if(col.headerText && col.sort == false) {            
        if (headerCellClone != null) {
            this._widget._updateFragment(headerCellClone, headerCellClone.id, {
                id: headerCellClone.id + "_Text",                    
                value: col.headerText,
                widgetType: "text"
            });
        }    
    } 
    // Append nodes.        
    headerRowClone.appendChild(headerCellClone);        
};

/**
 * This function is used to add rows using the gieven array. Each row contains
 * an array of columns which holds table data.
 *
 * @param {Array} rows An array of rows.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.tableRowGroup.prototype.addRows = function(rows) {
    
    // Clear rows.
    //
    // Note: This cannot be done in setProps because the start function 
    // publishes a scroll event which ultimately calls addRows.
    if (this.rows == null) {        
        this.rows = rows; // Save rows for getProps() function.
        this.first = 0; // Reset index used to obtain rows.
        this._currentRow = 0; // Reset current row in view.
        this._tableContainer.scrollTop = 0; // Reset scroll position.
        this._tbody.scrollTop = 0;
        this._widget._removeChildNodes(this._tbody); // Clear template nodes.
    }
    
    // Get properties.
    var props = this.getProps();
    // Get className properties to alternate between rows.
    var classNames = (this.className) ? this.className.split(",") : null;          
    //empty table data
    if (rows == null || rows.length == 0) {        
        // Clone table data row without cells.
        var rowClone = this._tableDataRow.cloneNode(false);
        this._tbody.appendChild(rowClone);
        var rowId = this.id + ":" + 0; // Get 1st row id.
        rowClone.id = rowId;
        // Clone node.
        cellClone = this._tableDataCell.cloneNode(true);
        rowClone.appendChild(cellClone);
        
        var col = this.columns[0];
        var colId = col.id.replace(this.id, rowId); // Get col id.
        cellClone.id = colId; 
        cellClone.colSpan = (this._getLeafColumns(this.columns)).length;        
        // Add cell data.
        this._widget._addFragment(cellClone, this.emptyTableRow, "last"); 
        // remove footers for empty table data
        this._widget._removeChildNodes(this._tfoot); 
        
    }
    
    // For each row found, clone the tableDataRow attach point.
    for (var i = 0; i < rows.length; i++) {
        var cols = rows[i]; // Get columns.
        var rowId = this.id + ":" + (this.first + i); // Get row id.

        // Clone table data row without cells.
        var rowClone = this._tableDataRow.cloneNode(false);
        this._tbody.appendChild(rowClone);

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
        var cellClone;
        var colId;
        var leafColArray = this._getLeafColumns(this.columns);
        for (var k = 0; k < leafColArray.length; k++) {
            
            col = leafColArray[k];
            colId = col.id.replace(this.id, rowId); // Get col id.

            // Clone node.
            cellClone = this._tableDataCell.cloneNode(true);
            rowClone.appendChild(cellClone);

            // Set properties.
            this._setColumnProps(cellClone, col);
            cellClone.id = colId; // Override id set by _setCoreProps.
            if (col.sortLevel == 1) {
                cellClone.className = this._theme.getClassName("TABLE2_PRIMARYSORT");            
            }
            // check for emptyCell 
            if (cols[k] == null) {
                this.emptyCell.id = cellClone.id + "_emptyCell";
                cols[k] = this.emptyCell;
            }
            // Add cell data.
            //Accept array of widgets also if available for cell data
            if (cols[k] instanceof Array) {
                var colsArray = cols[k];
                for (var j = 0; j < colsArray.length; j++) {
                    this._widget._addFragment(cellClone, colsArray[j], "last");                    
                }
            } else {                
                this._widget._addFragment(cellClone, cols[k], "last");                
            }             
        }    
        
        // Save row for getProps() function.
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
        @JS_NS@.widget.common.getWidget(_id)._resize();
    }, 10);
    return true;
};

/**
 * This function is used to get the array of leaf column headers.
 *
 * @param {Array} column objects.
 * @param {Array} leaf column objects.
 * @return {Array} leaf column objects.
 */
@JS_NS@.widget.tableRowGroup.prototype._getLeafColumns = function(cols, leafColArray) {
    if (leafColArray == null) {
        leafColArray = new Array();
    }
    for (var i=0; i < cols.length; i++) {
        var col = cols[i];
        if (col.columns && col.columns.length > 0) {
            leafColArray = this._getLeafColumns(col.columns, leafColArray);
            continue;
        } else {             
                leafColArray = leafColArray.concat(col);                
        }
    }
    return leafColArray;
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
@JS_NS@.widget.tableRowGroup.event =
        @JS_NS@.widget.tableRowGroup.prototype.event = {
    /**
     * This object contains day event topics.
     * @ignore
     */
    filter: {
        /** filterText topic for custom AJAX implementations to listen for. */
        filterTextTopic: "@JS_NS@_widget_tableRowGroup_event_filterText"
    },
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_tableRowGroup_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_tableRowGroup_event_refresh_end"
    },

    /**
     * This closure is used to process scroll events.
     * @ignore
     */
    scroll: {
        /** Scroll event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_tableRowGroup_event_scroll_begin",

        /** Scroll event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_tableRowGroup_event_scroll_end"
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
            beginTopic: "@JS_NS@_widget_tableRowGroup_event_pagination_next_begin",

            /** Scroll event topic for custom AJAX implementations to listen for. */
            endTopic: "@JS_NS@_widget_tableRowGroup_event_pagination_next_end"
        },

        /**
         * This closure is used to process previous pagination button event.
         * @ignore
         */
        previous: {
            /** Scroll event topic for custom AJAX implementations to listen for. */
            beginTopic: "@JS_NS@_widget_tableRowGroup_event_pagination_previous_begin",

            /** Scroll event topic for custom AJAX implementations to listen for. */
            endTopic: "@JS_NS@_widget_tableRowGroup_event_pagination_previous_end"
        }
        
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_tableRowGroup_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_tableRowGroup_event_state_end"
    },
    
    /**
     * This object contains state event topics.
     * @ignore
     */
    sort: {
        /** sort event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_tableRowGroup_event_sort_begin",

        /** sort event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_tableRowGroup_event_sort_end"
    }
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.tableRowGroup.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.align != null) { props.align = this.align; }
    if (this.bgColor != null) { props.bgColor = this.bgColor; }
//    if (this.char != null) { props.char = this.char; } // To do: Rename -- keyword is reserved.
    if (this.charOff != null) { props.charOff = this.charOff; }
    if (this.columns != null) { props.columns = this.columns; }
    if (this.first != null) { props.first = this.first; }
    if (this.headerText != null) { props.headerText = this.headerText; }
    if (this.height != null) { props.height = this.height; }
    if (this.maxRows != null) { props.maxRows = this.maxRows; }
    if (this.rows != null) { props.rows = this.rows; }
    if (this.totalRows != null) { props.totalRows = this.totalRows; }
    if (this.valign != null) { props.valign = this.valign; }
    if (this.paginationControls != null) {props.paginationControls = this.paginationControls;}
    if (this.paginationNextButton != null) {props.paginationNextButton = this.paginationNextButton;}
    if (this.paginationPrevButton != null) {props.paginationPrevButton = this.paginationPrevButton;}
    if (this.sortPopupMenu != null) {props.sortPopupMenu = this.sortPopupMenu;}
    if (this.filterText != null) {props.filterText = this.filterText;}

    return props;
};

/**
 * This function is used to set sorting options.
 * @param {Event} event The JavaScript event.
 * @param {String} colId The column id for the sorted column.
 * @param {int} sortLevel The sortLevel for column. 
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tableRowGroup.prototype._openSortMenu = function(event, 
        colId, sortLevel) {    
    //if totalRows is zero then do not allow sorting
    if (this.totalRows <= 0) {return false;}
    var menu = @JS_NS@.widget.common.getWidget(this.sortPopupMenu.id);        
    var leafColArray = this._getLeafColumns(this.columns);
    for (var i = 0; i < leafColArray.length; i++) {
        var col = leafColArray[i];
        if (col.id == colId) {
            sortLevel = col.sortLevel;            
            break;
        }    
    }
    if (menu) {
        if (sortLevel == -1 && this._sortCount == 0) {
            menu.setProps({options:this._primarySortOptions});
        } else if (sortLevel == 1) {
            var menuOptions = (this._primarySortOptions).concat(this._clearSortOptions);
            menu.setProps({options:menuOptions});
        } else if ((sortLevel == -1 || sortLevel > 1) && this._sortCount >= 1) {
	    var menuOptions = ((this._primarySortOptions).concat(
                 this._secondarySortOptions)).concat(this._clearSortOptions);
            menu.setProps({options:menuOptions});
        }
        menu.setProps({onClick: "@JS_NS@.widget.common.getWidget('" + 
            this.id + "')._sort('" + colId + "');"});          
        menu.open(event);        
    }
    return true;
};

/**
 * Process next control button.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tableRowGroup.prototype._paginationNext = function(event) {
    // Publish event to retrieve data.
    var currentPage = Math.floor(this._currentRow / this.maxRows) + 1;
    var totalPage = Math.ceil(this.totalRows / this.maxRows);
    if (this.first < this.totalRows
            && currentPage < totalPage) {
        // Publish an event for custom AJAX implementations to listen for.
        this._publish(@JS_NS@.widget.tableRowGroup.event.pagination.next.beginTopic, [{
            id: this.id,
            first: this.first 
        }]);
    }     
    var tableDataRow = document.getElementById(this.id + ":" + 0); 
    // For firefox, first table data row gives the offsetTop value 
    // including header height.
    // zero row gives the header height for firefox. Other browsers returns zero.
    var headerHeight = tableDataRow.offsetTop;
    if (currentPage < totalPage) {          
        // Calculate current row.          
        this._currentRow = currentPage * this.maxRows;        
        // set scroll position to make the current row completely visible
        if(@JS_NS@._base.browser._isFirefox()) {
            this._tbody.scrollTop = 
                document.getElementById(this.id + ":" + this._currentRow).offsetTop - headerHeight;       
        } else {
            this._tableContainer.scrollTop = 
                document.getElementById(this.id + ":" + this._currentRow).offsetTop;        
        }
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
@JS_NS@.widget.tableRowGroup.prototype._paginationPrevious = function(event) {
    var currentPage = Math.ceil(this._currentRow / this.maxRows) + 1;
    var totalPage = Math.floor(this.totalRows / this.maxRows);
    var tableDataRow = document.getElementById(this.id + ":" + 0); 
    // For firefox, first table data row gives the offsetTop value 
    // including header height.
    // zero row gives the header height for firefox. Other browsers returns zero.
    var headerHeight = tableDataRow.offsetTop;
    if (currentPage > 1) {                 
        this._currentRow = (currentPage - 2) * this.maxRows;
        // set scroll position to make the current row completely visible
        if (@JS_NS@._base.browser._isFirefox()) {
            this._tbody.scrollTop = 
                document.getElementById(this.id + ":" + this._currentRow).offsetTop - headerHeight;       
        } else {
            this._tableContainer.scrollTop = 
                document.getElementById(this.id + ":" + this._currentRow).offsetTop;        
        }     
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
@JS_NS@.widget.tableRowGroup.prototype._postCreate = function () {
    // Set ids.    
    if (this.id) {
        this._colFooterRow.id = this.id + "_colFooterRow";
        this._colFooterCell.id = this.id + "_colFooterCell";
        this._colHeaderRow.id = this.id + "_colHeaderRow";
        this._colHeaderCell.id = this.id + "_colHeaderCell";
        this._colHeaderLink.id = this.id + "_colHeaderLink";
        this._groupHeaderControls.id = this.id + "_groupHeaderControls";
        this._groupHeaderText.id = this.id + "_groupHeaderText";        
        this._rowsText.id = this.id + "_rowsText";
        this._table.id = this.id + "_table";
        this._tableContainer.id = this.id + "_tableContainer";
        this._tableDataRow.id = this.id + "_tableDataRow";
        this._tableDataCell.id = this.id + "_tableDataCell";
        this._tbody.id = this.id + "_tbody";
        this._tfoot.id = this.id + "_tfoot";
        this._thead.id = this.id + "_thead";        
        this._paginationButtonsNode.id = this.id + "_paginationButtonsNode";           
    }

    // Set events.
    if (@JS_NS@._base.browser._isFirefox()) {
        this._dojo.connect(this._tbody, "onscroll", this, "_scroll");
    } else {
        this._dojo.connect(this._tableContainer, "onscroll", this, "_scroll");
    }

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
            title: this._theme.getMessage("table2.pagination.previous"),
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
            title: this._theme.getMessage("table2.pagination.next"),
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
    
    if (this.emptyCell == null) {
        this.emptyCell = {
                    alt: this._theme.getMessage("table.emptyTableCell"),
                    icon: "TABLE_EMPTY_CELL",
                    widgetType: "image"
        };
    }
    
    if (this.emptyTableRow == null) {
        this.emptyTableRow = {
                    id: this.id + "_emptyTableRow",
                    value: this._theme.getMessage("table.emptyData"),
                    widgetType: "text"
        };
    }
    // Resize
    this._dojo.connect(window, "onresize", this, "_resize");
    
    return this._inherited("_postCreate", arguments);
};

/**
 * Process resize event.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tableRowGroup.prototype._resize = function() {
    // Update rows text.
    this._updateRowsText();
    //handle emty table case
    if (this.totalRows <= 0) {
    //adjust height
        var tableDataRow = document.getElementById(this.id + ":" + 0);        
        if (tableDataRow != null) {
            var offset = tableDataRow.offsetHeight;
            if (offset > 0) {
                if (@JS_NS@._base.browser._isFirefox()) {
                    this._tbody.style.height = offset + "px";
                } else {
                    this._tableContainer.style.height = offset + "px";
                }    
            }
        }
        //adjust width for column headers
        var colLeafArray = this._getLeafColumns(this.columns);
        for (var i =0; i < colLeafArray.length; i++) {
            var colHeaderCell = document.getElementById(colLeafArray[i].id + "_colHeader");
            if (colHeaderCell != null) {  
                colHeaderCell.style.width = colHeaderCell.offsetWidth + "px";
            }            
        }
    } else {           
        // Get height offset of each row.
        var offset = 0;
        for (var i = this._currentRow; i < this._currentRow + this.maxRows; i++) {
            var tableDataRow = document.getElementById(this.id + ":" + i);            
            if (tableDataRow != null) {
                offset += tableDataRow.offsetHeight;
            } else {
                break;
            }
        }        
        // Set the scrollable height.
        if (offset > 0) {
            if (@JS_NS@._base.browser._isFirefox()) {
                this._tbody.style.height = offset + "px";
            } else {
                this._tableContainer.style.height = offset + "px";
            }    
        }

        // Set width of each column header & footer.
        var rowId = this.id + ":0"; // ID of first row.
        if (this.totalRows > 0) {
        // don't need to set width for column headers if no rows are available.
            for (var i = 0; i < this.columns.length; i++) {
              var col = this.columns[i]; // Get default column props.
              this.currentColumnWidth = 0;
              this._setColumnWidth(col, rowId);
          } 
        }
    }
    return true;
};

/**
 * This function sets the width for column headers and footers.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tableRowGroup.prototype._setColumnWidth = function(col, rowId) {
    if (col.columns && col.columns.length > 0) {
        this.currentColumnWidth = 0;
        for (var i=0; i < col.columns.length; i++) {
            this._setColumnWidth(col.columns[i], rowId);
        }    
        // set width for parent column headers. no need to set width for footer here
        // we do not support multi level footers 
        var colHeaderCell = document.getElementById(col.id + "_colHeader");       
        colHeaderCell.style.width = (this.currentColumnWidth) + "px";        
    } else {
        var colHeaderCell = document.getElementById(col.id + "_colHeader");
        var tableDataCell = document.getElementById(col.id.replace(this.id, rowId));
        if (!this.currentColumnWidth) {
            this.currentColumnWidth = 0;
        }
        if (tableDataCell != null) {
          this.currentColumnWidth = this.currentColumnWidth + tableDataCell.offsetWidth - 1;
          colHeaderCell.style.width = (tableDataCell.offsetWidth -1) + "px";
        }

        // footer width
        var colFooterCell = document.getElementById(col.id + "_colFooter");
        if (colFooterCell && col.footerText) {
            if (tableDataCell != null)
            colFooterCell.style.width = (tableDataCell.offsetWidth - 1) + "px";
        }
    }
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
@JS_NS@.widget.tableRowGroup.prototype._setColumnProps = function(domNode, props) {
    // Set properties.
    if (props.abbr != null) { domNode.abbr = this.abbr; }
    if (props.axis != null) { domNode.axis = this.axis; }
    if (props.bgColor != null) { domNode.bgColor = this.bgColor; }
//    if (props.char != null) { domNode.char = this.char; } // To do: Rename -- keyword is reserved.
    if (props.charOff != null) { domNode.charoff = this.charOff; }
    if (props.colspan != null) { domNode.colspan = this.colspan; }
    if (props.headers != null) { domNode.headers = this.headers; }
    if (props.height != null) { domNode.height = this.height; }
    if (props.noWrap != null) { domNode.setAttribute("nowrap", "nowrap"); }
    if (props.rowSpan != null) { domNode.rowspan = this.rowSpan; }
    if (props.scope != null) { domNode.scope = this.scope; }
    if (props.valign != null) { domNode.valign = this.valign; }
    if (props.width != null) { domNode.width = this.width; }

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
@JS_NS@.widget.tableRowGroup.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace columns -- do not extend.
    if (props.columns) {
        this.columns = null;
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
@JS_NS@.widget.tableRowGroup.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.id) { this._domNode.id = props.id; }

    // Set private properties for table widget.
    if (props._table) {
        if (props._table.bgColor) { this._table.bgColor = props._table.bgColor; }
        if (props._table.border) { this._table.border = props._table.border; }
        if (props._table.cellpadding) { this._table.cellpadding = props._table.cellpadding; }
        if (props._table.cellspacing) { this._table.cellspacing = props._table.cellspacing; }
        if (props._table.frame) { this._table.frame = props._table.frame; }
        if (props._table.rules) { this._table.rules = props._table.rules; }
        if (props._table.summary) { this._table.summary = props._table.summary; }
    }

    // Add header.
    if (props.headerText != null) {
        this._widget._updateFragment(this._groupHeaderText, this._groupHeaderText.id, props.headerText);
        this._common._setVisibleElement(this.groupHeaderContainer, true);
    }
    // Add paginationControls.    
    if (props.paginationPrevButton) {
        // set onclick for previous button.
        props.paginationPrevButton.onClick = 
            "@JS_NS@.widget.common.getWidget('" + this.id + "')._paginationPrevious();return false;";        
        this._widget._addFragment(this._paginationButtonsNode, props.paginationPrevButton,"last");
    }
    if (props.paginationNextButton) {
        // set onclick for next button.
        props.paginationNextButton.onClick = 
            "@JS_NS@.widget.common.getWidget('" + this.id + "')._paginationNext();return false;";
        this._widget._addFragment(this._paginationButtonsNode, props.paginationNextButton,"last");
    }
    if (props.paginationControls != null) {        
        this.paginationControls = props.paginationControls;
    }

    // Set enabled/disabled state for pagination controls
    this._updatePaginationControls();

    // Add popup menu
    if (props.sortPopupMenu) {
        @JS_NS@.widget.common._updateFragment(this._sortMenu, props.sortPopupMenu.id, props.sortPopupMenu);
    }
        
    // Set columns.
    if (props.columns && this.refreshCols != false) {
        this._addColumns();
        // To Do: Cannot refresh column headers/footers due to poor CSS styles.
        this.refreshCols = false;
    }
    
    // Update the sortLevel values.
    if (props.columns) {          
        var leafColArray = this._getLeafColumns(this.columns);
        for (var i=0; i < this._colSortLevel.length; i++) {
            if (leafColArray[i].sortLevel == null) {
                leafColArray[i].sortLevel = this._colSortLevel[i];
            }  
        }    
    }

    // Add rows.
    if (props.rows) {
        // Replace rows -- do not extend.
        this.rows = null;
        this.addRows(props.rows);
    }
   
    if (props.filterText != null) {
        this._publish(@JS_NS@.widget.tableRowGroup.event.filter.filterTextTopic, [{
          id: this.id,
          filterText:props.filterText
        }]);
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
@JS_NS@.widget.tableRowGroup.prototype._scroll = function(event) {
    var scrollTop = null;
    if (@JS_NS@._base.browser._isFirefox()) {
        scrollTop = this._tbody.scrollTop;        
    } else {
        scrollTop = this._tableContainer.scrollTop;        
    }

    var last = Math.min(this.totalRows, this.first + this.maxRows) - 1; // Last row in range. 
    // Publish event to retrieve data.    
    if (this.first < this.totalRows
            && this._currentRow % this.maxRows == 0) {
        // Publish an event for custom AJAX implementations to listen for.
        this._publish(@JS_NS@.widget.tableRowGroup.event.scroll.beginTopic, [{
            id: this.id,
            first: this.first
        }]);
    }
    var first = 0; // First row in range. 
    var tableDataRow = document.getElementById(this.id + ":" + 0); 
    // For firefox, first table data row gives the offsetTop value 
    // including header height.
    // zero row gives the header height for firefox. Other browsers returns zero.
    var headerHeight = tableDataRow.offsetTop; 
    var temp = 0; // temp variable    
    while (first < last) { 
        var mid = Math.floor((first + last) / 2); // Index of midpoint.         
        var tableDataRow = document.getElementById(this.id + ":" + mid);      
        if (tableDataRow != null) {
            temp = tableDataRow.offsetTop - headerHeight;            
        }
        
        if (tableDataRow == null) { 
            break; 
        }
        // Test if scroll position matches row offset. 
        if (scrollTop < temp) { 
           last = mid; // Search left half. 
        } else if (scrollTop >= temp) { 
           first = mid + 1; // Search right half. 
        }
    }    
    this._currentRow = Math.max(0, first - 1);
    var curRow = document.getElementById(this.id + ":" + this._currentRow);
    var nextRow = document.getElementById(this.id + ":" + (this._currentRow + 1));
    if (curRow && nextRow) {
        var rowHeight =  nextRow.offsetTop - curRow.offsetTop;
        var moveScroll = scrollTop - (curRow.offsetTop - headerHeight);
        if (moveScroll > (rowHeight / 2)) {
            this._currentRow = this._currentRow + 1;   
        }
    }   
    // Set rows text.    
    return this._updateRowsText();
};

/**
 * This function is used to publish event for sorting
 * 
 * @param {String} colId The column id for the sorted column.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tableRowGroup.prototype._sort = function(colId) {          
    var menu = @JS_NS@.widget.common.getWidget(this.sortPopupMenu.id);   
    var value = menu.getSelectedValue();    
    var sortLevel = -1;
    
    // Publish an event for custom AJAX implementations to listen for.
    this._publish(@JS_NS@.widget.tableRowGroup.event.sort.beginTopic, [{
        id: this.id,
        tablecolId: colId,
        sortOrder: menu.getSelectedValue()
    }]);
    
    var leafColArray = this._getLeafColumns(this.columns);

    // Update sortCount and sortLevel values.
    if ((value == "primaryAscending" || value == "primaryDescending")) {
        sortLevel = 1;
        this._sortCount = 1;
    } else if ((value == "ascending" || value == "descending")) {
        this._sortCount++;
        sortLevel = this._sortCount;
    } else if (value == "clear") {
        this._sortCount = 0;
        for (var i = 0; i < leafColArray.length; i++) {
            var col = leafColArray[i];
            col.sortLevel = -1;                                            
        }
    }

    // Update sortLevel value for column
    for (var i = 0; i < leafColArray.length; i++) {
        var col = leafColArray[i];
        // Clear other sorts if primary sort is selected.
        if (sortLevel == 1) {
            col.sortLevel = -1;
        }
        if (col.id == colId) {
            col.sortLevel = sortLevel;            
            //break;
        }    
        if (col.sortLevel) {
            this._colSortLevel[i] = col.sortLevel;
        } else {
            this._colSortLevel[i] = -1;
        }    
    }    
    return true;    
};

/**
 * This function is used to "start" the widget, after the widget has been
 * instantiated.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tableRowGroup.prototype._startup = function () {
    if (this._started == true) {
        return false;
    }
    // Retrieve rows only if initial data has not been provided.
    if (this.rows == null) {        
        // Publish an event for custom AJAX implementations to listen for.
        this._publish(@JS_NS@.widget.tableRowGroup.event.scroll.beginTopic, [{
            id: this.id,
            first: this.first
        }]);
    }
    return this._inherited("_startup", arguments);
};

/**
 * Updates pagination control buttons enabled/disabled state.
 * 
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tableRowGroup.prototype._updatePaginationControls = function() {
    if (this.paginationPrevButton && this.paginationNextButton) {
        var domNodePrev = this._widget.getWidget(this.paginationPrevButton.id);
        var domNodeNext = this._widget.getWidget(this.paginationNextButton.id);
        var currentPage = Math.ceil(this._currentRow / this.maxRows) + 1;
        var totalPage = Math.ceil(this.totalRows / this.maxRows);
        if (domNodePrev != null && domNodeNext != null) {
            if (this._currentRow / this.maxRows == 0) {
                domNodePrev.setProps({disabled:true});              
            } else {
                domNodePrev.setProps({disabled:false});  
            }            
            if (currentPage == totalPage || totalPage == 0) {
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
@JS_NS@.widget.tableRowGroup.prototype._updateRowsText = function() {
    // Add title augment.
    var firstRow = this._currentRow + 1;
    var lastRow = Math.min(this.totalRows, this._currentRow + this.maxRows);

    // To do: Need to create a new rows message.

    // NOTE: If you set this value manually, text must be HTML escaped.
    // check for emty table data case
    if (this.totalRows == 0) {
        firstRow = 0;
    }
    var msg = this._theme.getMessage("table.title.paginated", [
        "", 
        firstRow, 
        lastRow, 
        this.totalRows, 
        ""
    ]);

    // "Items: " + firstRow + " - " + lastRow + " of " + this.totalRows);
    if (msg) {
        this._widget._addFragment(this._rowsText, msg);
    }
     //set disabled/enabled state
    this._updatePaginationControls(); 
    return true;
};
