// widget/table2RowGroup.js
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

/**
 * @name widget/table2RowGroup.js
 * @version @THEME_VERSION@
 * @overview This module contains classes and functions for the table2RowGroup widget.
 * @example The following code is used to create a table2RowGroup widget.
 * <p><code>
 * var widget = new webui.@THEME@.widget.table2RowGroup(props, domNode);
 * </code></p>
 */
dojo.provide("webui.@THEME@.widget.table2RowGroup");

dojo.require("webui.@THEME@.browser");
dojo.require("webui.@THEME@.common");
dojo.require("webui.@THEME@.widget.widgetBase");

/**
 * This function is used to construct a template based widget.
 *
 * @name webui.@THEME@.widget.table2RowGroup
 * @inherits webui.@THEME@.widget.widgetBase
 * @constructor
 */
dojo.declare("webui.@THEME@.widget.table2RowGroup", webui.@THEME@.widget.widgetBase, {
    // Set defaults.
    currentRow: 0, // Current row in view.
    first: 0, // Index used to obtain rows.
    widgetName: "table2RowGroup" // Required for theme properties.
});

/**
 * This function is used to set column headers and footers.
 */
webui.@THEME@.widget.table2RowGroup.prototype.addColumns = function() {
    // Clear column headers/footers.
    this.widget.removeChildNodes(this.thead);
    this.widget.removeChildNodes(this.tfoot);

    // Clone dojo attach points.
    var headerRowClone = this.colHeaderRow.cloneNode(false);
    var footerRowClone = this.colFooterRow.cloneNode(false);

    // Append row nodes.
    this.thead.appendChild(headerRowClone);
    this.tfoot.appendChild(footerRowClone);

    // Append cell nodes.
    for (var i = 0; i < this.columns.length; i++) {
        var col = this.columns[i];
        var headerCellClone = this.colHeaderCell.cloneNode(true);
        var footerCellClone = this.colFooterCell.cloneNode(true);

        // Set properties.
        headerCellClone.id = col.id + "_colHeader";
        footerCellClone.id = col.id + "_colFooter";
        if (col.width) {
            headerCellClone.style.width = col.width;
            footerCellClone.style.width = col.width;
        }

        // Add text.
        if (col.headerText) {
            // StaticText widget adds span to match styles.
            //
            // To do: Create utility to help create client-side widgets.
            this.widget.addFragment(headerCellClone,
                this.widget.getWidgetProps("staticText", {
                    id: headerCellClone.id + "Text",
                    value: col.headerText,
                }));
            headerVisible = true;
        }
        if (col.footerText) {
            // StaticText widget adds span to match styles.
            //
            // To do: Create utility to help create client-side widgets.
            this.widget.addFragment(footerCellClone,
                this.widget.getWidgetProps("staticText", {
                    id: footerCellClone.id + "Text",
                    value: col.footerText
                }));
            footerVisible = true;
        }

        // Append nodes.
        headerRowClone.appendChild(headerCellClone);
        footerRowClone.appendChild(footerCellClone);

        // Set colspan.
        this.groupHeaderCell.colSpan = this.columns.length;
    }
    return true;
}

/**
 * This function is used to add rows using the gieven array. Each row contains
 * an array of columns which holds table data.
 *
 * @param {Array} rows An array of rows.
 */
webui.@THEME@.widget.table2RowGroup.prototype.addRows = function(rows) {
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

        this.setCommonProps(rowClone, props);
        this.setEventProps(rowClone, props);
        this.setCoreProps(rowClone, props);

        // For each column found, clone the tableDataCell attach point.
        for (var k = 0; k < cols.length; k++) {
            var col = this.columns[k]; // Get current column.
            var colId = col.id.replace(this.id, rowId); // Get col id.

            // Clone node.
            var cellClone = this.tableDataCell.cloneNode(true);
            rowClone.appendChild(cellClone);

            // Set properties.
            this.setColumnProps(cellClone, col);
            cellClone.id = colId; // Override id set by setCoreProps.

            // Add cell data.
            this.widget.addFragment(cellClone, cols[k], "last");
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
        dijit.byId(_id).resize();
    }, 10);

    return true;
}

/**
 * This closure contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 *
 * @ignore
 */
webui.@THEME@.widget.table2RowGroup.prototype.event =
        webui.@THEME@.widget.table2RowGroup.event = {
    /**
     * This closure contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_table2RowGroup_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_table2RowGroup_event_refresh_end"
    },

    /**
     * This closure is used to process scroll events.
     * @ignore
     */
    scroll: {
        /** Scroll event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_table2RowGroup_event_scroll_begin",

        /** Scroll event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_table2RowGroup_event_scroll_end"
    },

    /**
     * This closure contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_table2RowGroup_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_table2RowGroup_event_state_end"
    }
}

/**
 * This function is used to get widget properties. Please see the
 * setProps() function for a list of supported properties.
 */
webui.@THEME@.widget.table2RowGroup.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

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

    return props;
}

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 */
webui.@THEME@.widget.table2RowGroup.prototype.postCreate = function () {
    // Set ids.
    if (this.id) {
        this.colFooterRow.id = this.id + "_colFooterRow";
        this.colFooterCell.id = this.id + "_colFooterCell";
        this.colHeaderRow.id = this.id + "_colHeaderRow";
        this.colHeaderCell.id = this.id + "_colHeaderCell";
        this.groupHeaderControls.id = this.id + "_groupHeaderControls";
        this.groupHeaderText.id = this.id + "_groupHeaderText";        
        this.paginationControls.id = this.id + "_paginationControls";
        this.rowsText.id = this.id + "_rowsText";
        this.table.id = this.id + "_table";
        this.tableContainer.id = this.id + "_tableContainer";
        this.tableDataRow.id = this.id + "_tableDataRow";
        this.tableDataCell.id = this.id + "_tableDataCell";
        this.tbody.id = this.id + "_tbody";
        this.tfoot.id = this.id + "_tfoot";
        this.thead.id = this.id + "_thead";
    }

    // Set events.
    dojo.connect(this.tableContainer, "onscroll", this, "scroll");

    // Resize hack for Moz/Firefox.
    if (webui.@THEME@.browser.is_nav()) {
        dojo.connect(window, "onresize", this, "resize");
    }
    return this.inherited("postCreate", arguments);
}

/**
 * Process resize event.
 */
webui.@THEME@.widget.table2RowGroup.prototype.resize = function() {
    // Update rows text.
    this.updateRowsText();

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

    // Update heqader & footer position.
    var colHeaderRow = document.getElementById(this.id + "_colHeaderRow");
    var colFooterRow = document.getElementById(this.id + "_colFooterRow");

    if (colHeaderRow) {
        // Column header height plus offset for border.
        this.tableContainer.style.marginTop = (colHeaderRow.offsetHeight - 1) + 'px';
        colHeaderRow.style.top = (this.tableContainer.offsetTop -
        colHeaderRow.offsetHeight + 1) + 'px';

        if (colFooterRow) {
            // Column footer height plus offset for border.
            this.tableContainer.style.marginBottom = colFooterRow.offsetHeight + 'px';
            colFooterRow.style.top = (this.tableContainer.offsetTop + 
            this.tableContainer.offsetHeight - 1) + 'px';
        }
    }
    return true;
}

/**
 * This function is used to set column properties with Object literals.
 *
 * @param {Node} domNode The DOM node to assign properties to.
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} [abbr]
 * @config {String} [axis]
 * @config {String} [bgColor]
 * @config {String} [char]
 * @config {String} [charOff]
 * @config {String} [className] CSS selector.
 * @config {int} [colspan]
 * @config {String} [dir] Specifies the directionality of text.
 * @config {String} [height]
 * @config {String} [id] Uniquely identifies an element within a document.
 * @config {String} [lang] Specifies the language of attribute values and content.
 * @config {boolean} [noWrap] 
 * @config {String} [onClick] Mouse button is clicked on element.
 * @config {String} [onDblClick] Mouse button is double-clicked on element.
 * @config {String} [onKeyDown] Key is pressed down over element.
 * @config {String} [onKeyPress] Key is pressed and released over element.
 * @config {String} [onKeyUp] Key is released over element.
 * @config {String} [onMouseDown] Mouse button is pressed over element.
 * @config {String} [onMouseOut] Mouse is moved away from element.
 * @config {String} [onMouseOver] Mouse is moved onto element.
 * @config {String} [onMouseUp] Mouse button is released over element.
 * @config {String} [onMouseMove] Mouse is moved while over element.
 * @config {int} [rowSpan] 
 * @config {String} [scope] 
 * @config {String} [style] Specify style rules inline.
 * @config {String} [title] Provides a title for element.
 * @config {String} [valign] 
 * @config {boolean} [visible] Hide or show element.
 * @config {String} [width]
 */
webui.@THEME@.widget.table2RowGroup.prototype.setColumnProps = function(domNode, props) {
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
    this.setCommonProps(domNode, props);
    this.setEventProps(domNode, props);
    this.setCoreProps(domNode, props);
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
 * @config {String} [align] Alignment of image input.
 * @config {String} [bgColor]
 * @config {String} [char]
 * @config {String} [charOff]
 * @config {String} [className] CSS selector.
 * @config {Array} [columns]
 * @config {String} [dir] Specifies the directionality of text.
 * @config {int} [first]
 * @config {String} [headerText]
 * @config {int} [height]
 * @config {String} [id] Uniquely identifies an element within a document.
 * @config {String} [lang] Specifies the language of attribute values and content.
 * @config {int} [maxRows] 
 * @config {String} [onClick] Mouse button is clicked on element.
 * @config {String} [onDblClick] Mouse button is double-clicked on element.
 * @config {String} [onKeyDown] Key is pressed down over element.
 * @config {String} [onKeyPress] Key is pressed and released over element.
 * @config {String} [onKeyUp] Key is released over element.
 * @config {String} [onMouseDown] Mouse button is pressed over element.
 * @config {String} [onMouseOut] Mouse is moved away from element.
 * @config {String} [onMouseOver] Mouse is moved onto element.
 * @config {String} [onMouseUp] Mouse button is released over element.
 * @config {String} [onMouseMove] Mouse is moved while over element.
 * @config {Array} [rows] 
 * @config {String} [style] Specify style rules inline.
 * @config {String} [title] Provides a title for element.
 * @config {int} [totalRows] 
 * @config {String} [valign] 
 * @config {boolean} [visible] Hide or show element.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 */
webui.@THEME@.widget.table2RowGroup.prototype.setProps = function(props, notify) {
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
    return this.inherited("setProps", arguments);
}

/**
 * This function is used to set widget properties. Please see the setProps() 
 * function for a list of supported properties.
 * <p>
 * Note: This is considered a private API, do not use. This function should only
 * be invoked via setProps().
 * </p>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @private
 */
webui.@THEME@.widget.table2RowGroup.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Add header.
    if (props.headerText) {
        this.widget.addFragment(this.groupHeaderText, props.headerText);
        webui.@THEME@.common.setVisibleElement(this.groupHeaderContainer, true);
    }

    // Set columns.
    if (props.columns && this.refreshCols != false) {
        this.addColumns();

        // To Do: Cannot refresh column headers/footers due to poor CSS styles.
        this.refreshCols = false;
    }

    // Add rows.
    if (props.rows) {
        this.first = 0; // Reset index used to obtain rows.
        this.currentRow = 0; // Reset current row in view.

        // Clear rows.
        this.widget.removeChildNodes(this.tbody);
        this.addRows(props.rows);
    }

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
    
    // Cannot call "superclass" here because properties are set on each row.
    if (props.id) { this.domNode.id = props.id; }
    
    return true;
}

/**
 * Process scroll event.
 *
 * @param {Event} event The JavaScript event
 */
webui.@THEME@.widget.table2RowGroup.prototype.scroll = function(event) {
    // Publish event to retrieve data.
    if (this.first < this.totalRows
            && this.currentRow % this.maxRows == 0) {
        // Include default AJAX implementation.
        this.ajaxify();

        // Publish an event for custom AJAX implementations to listen for.
        dojo.publish(webui.@THEME@.widget.table2RowGroup.event.scroll.beginTopic, [{
            id: this.id,
            first: this.first
        }]);
    }
       
    // Set current row based on scroll position and row offset.
    var first = 0; // First row in range.
    var last = Math.min(this.totalRows,
        this.first + this.maxRows) - 1; // Last row in range.
    var scrollTop = this.tableContainer.scrollTop + 1; // Scroll position plus offset for border.

    while (first < last) {
        var mid = Math.floor((first + last) / 2); // Index of midpoint.
        var tableDataRow = document.getElementById(this.id + ":" + mid);
        if (tableDataRow == null) {
            break;
        }
        // Test if scroll position matches row offset.
        if (scrollTop < tableDataRow.offsetTop) {
            last = mid; // Search left half.
        } else if (scrollTop >= tableDataRow.offsetTop) {
            first = mid + 1; // Search right half.
        }
    }
    this.currentRow = Math.max(0, first - 1);

    // Set rows text.
    return this.updateRowsText();
}

/**
 * This function is used to set rows text (e.g., "1 - 5 of 20").
 */
webui.@THEME@.widget.table2RowGroup.prototype.updateRowsText = function() {
    // Add title augment.
    var firstRow = this.currentRow + 1;
    var lastRow = Math.min(this.totalRows, this.currentRow + this.maxRows);

    // To do: Need to create a new rows message.

    // NOTE: If you set this value manually, text must be HTML escaped.
    var msg = this.theme.getMessage("table.title.paginated", [
        "", 
        firstRow, 
        lastRow, 
        this.totalRows, 
        ""
    ]);

    // "Items: " + firstRow + " - " + lastRow + " of " + this.totalRows);
    if (msg) {
        this.widget.addFragment(this.rowsText, msg);
    }
    return true;
}
