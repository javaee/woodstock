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

dojo.provide("webui.@THEME@.widget.table2RowGroup");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.table2RowGroup = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * This function is used to set column headers and footers.
 */
webui.@THEME@.widget.table2RowGroup.addColumns = function() {
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
 * @param rows An array of rows.
 */
webui.@THEME@.widget.table2RowGroup.addRows = function(rows) {
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
    setTimeout(webui.@THEME@.widget.table2RowGroup.event.resize.createCallback(this.id), 0);

    return true;
}

/**
 * This closure is used to process widget events.
 */
webui.@THEME@.widget.table2RowGroup.event = {
    /**
     * This closure is used to process refresh events.
     */
    refresh: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_table2RowGroup_event_refresh_begin",
        endTopic: "webui_@THEME@_widget_table2RowGroup_event_refresh_end"
    },

    /**
     * This closure is used to process resize events.
     */
    resize: {
        /**
         * Helper function to create callback for resize event.
         *
         * @param id The HTML element id of the widget.
         */
        createCallback: function(id) {
            if (id != null) {
                // New literals are created every time this function
                // is called, and it's saved by closure magic.
                return function(event) {
                    // Note: The event param is not required here.
                    var widget = dojo.widget.byId(id);
                    if (widget) {
                        widget.resize();
                    }
                };
            }
        },

        /**
         * Process resize event.
         */
        processEvent: function() {
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
    },

    /**
     * This closure is used to process scroll events.
     */
    scroll: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_table2RowGroup_event_scroll_begin",
        endTopic: "webui_@THEME@_widget_table2RowGroup_event_scroll_end",
 
         /**
         * Helper function to create callback for scroll event.
         *
         * @param id The HTML element id of the widget.
         */
        createCallback: function(id) {
            if (id != null) {
                // New literals are created every time this function
                // is called, and it's saved by closure magic.
                return function(event) {
                    // Note: The event param is not required here.
                    var widget = dojo.widget.byId(id);
                    if (widget) {
                        widget.scroll();
                    }
                };
            }
        },

        /**
         * Process scroll event.
         *
         * @param event Event generated by scroll bar.
         */
        processEvent: function(event) {
            // Publish event to retrieve data.
            if (this.first < this.totalRows
                    && this.currentRow % this.maxRows == 0) {
                // Include default AJAX implementation.
                this.ajaxify();

                // Publish an event for custom AJAX implementations to listen for.
                dojo.event.topic.publish(
                    webui.@THEME@.widget.table2RowGroup.event.scroll.beginTopic, {
                        id: this.id,
                        first: this.first
                    });
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
            this.updateRowsText();

            return true;
        }
    },

    /**
     * This closure is used to process state change events.
     */
    state: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_table2RowGroup_event_state_begin",
        endTopic: "webui_@THEME@_widget_table2RowGroup_event_state_end"
    }
}

/**
 * This function is used to fill in template properties.
 *
 * Note: This is called after the buildRendering() function. Anything to be set
 * only once should be added here; otherwise, use the _setProps() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 */
webui.@THEME@.widget.table2RowGroup.fillInTemplate = function(props, frag) {
    webui.@THEME@.widget.table2RowGroup.superclass.fillInTemplate.call(this, props, frag);

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
    dojo.event.connect(this.tableContainer, "onscroll",
        webui.@THEME@.widget.table2RowGroup.event.scroll.createCallback(this.id));

    // Resize hack for Moz/Firefox.
    if (webui.@THEME@.common.browser.is_nav == true) {
        dojo.event.connect(window, "onresize",
            webui.@THEME@.widget.table2RowGroup.event.resize.createCallback(this.id));
    }
    return true;
}

/**
 * This function is used to get widget properties. Please see the
 * _setProps() function for a list of supported properties.
 */
webui.@THEME@.widget.table2RowGroup.getProps = function() {
    var props = webui.@THEME@.widget.table2RowGroup.superclass.getProps.call(this);

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
    if (this.totalRows) { props.totalRows = this.totalRows; }
    if (this.valign) { props.valign = this.valign; }

    return props;
}

/**
 * This function is used to set column properties with the following
 * Object literals.
 *
 * <ul>
 *  <li>abbr</li>
 *  <li>axis</li>
 *  <li>bgColor</li>
 *  <li>char</li>
 *  <li>charOff</li>
 *  <li>className</li>
 *  <li>colspan</li>
 *  <li>dir</li>
 *  <li>height</li>
 *  <li>id</li>
 *  <li>lang</li>
 *  <li>noWrap</li>
 *  <li>onClick</li>
 *  <li>onDblClick</li>
 *  <li>onKeyDown</li>
 *  <li>onKeyPress</li>
 *  <li>onKeyUp</li>
 *  <li>onMouseDown</li>
 *  <li>onMouseMove</li>
 *  <li>onMouseOut</li>
 *  <li>onMouseOver</li>
 *  <li>onMouseUp</li>
 *  <li>rowSpan</li>
 *  <li>scope</li>
 *  <li>style</li>
 *  <li>title</li>
 *  <li>valign</li>
 *  <li>visible</li>
 *  <li>width</li>
 * </ul>
 *
 * @param domNode The DOM node to assign properties to.
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.table2RowGroup.setColumnProps = function(domNode, props) {
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
 * This function is used to set widget properties. Please see the
 * _setProps() function for a list of supported properties.
 *
 * Note: This function updates the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 *
 * Note: If the notify param is true, the widget's state change event shall be
 * published. This is typically used to keep client-side state in sync with the
 * server.
 *
 * @param props Key-Value pairs of properties.
 * @param notify Publish an event for custom AJAX implementations to listen for.
 */
webui.@THEME@.widget.table2RowGroup.setProps = function(props, notify) {
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
    return webui.@THEME@.widget.table2RowGroup.superclass.setProps.call(this, props, notify);
}

/**
 * This function is used to set widget properties with the following
 * Object literals.
 *
 * <ul>
 *  <li>align</li>
 *  <li>bgColor</li>
 *  <li>char</li>
 *  <li>charOff</li>
 *  <li>className</li>
 *  <li>columns</li>
 *  <li>dir</li>
 *  <li>first</li>
 *  <li>headerText</li>
 *  <li>height</li>
 *  <li>id</li>
 *  <li>lang</li>
 *  <li>maxRows</li>
 *  <li>onClick</li>
 *  <li>onDblClick</li>
 *  <li>onKeyDown</li>
 *  <li>onKeyPress</li>
 *  <li>onKeyUp</li>
 *  <li>onMouseDown</li>
 *  <li>onMouseMove</li>
 *  <li>onMouseOut</li>
 *  <li>onMouseOver</li>
 *  <li>onMouseUp</li>
 *  <li>totalRows</li>
 *  <li>style</li>
 *  <li>title</li>
 *  <li>valign</li>
 *  <li>visible</li>
 * </ul>
 *
 * Note: This is considered a private API, do not use. This function should only
 * be invoked through postInitialize() and setProps(). Further, the widget shall
 * be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.table2RowGroup._setProps = function(props) {
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
        if (props._table.summary) { this.table.summary = props._table.summary; }
    }
    
    // Cannot call "superclass" here because properties are set on each row.
    if (props.id) { this.domNode.id = props.id; }
    
    return true;
}

/**
 * This function is used to set rows text (e.g., "1 - 5 of 20").
 */
webui.@THEME@.widget.table2RowGroup.updateRowsText = function() {

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

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.table2RowGroup, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.table2RowGroup, {
    // Set private functions.
    addColumns: webui.@THEME@.widget.table2RowGroup.addColumns,
    addRows: webui.@THEME@.widget.table2RowGroup.addRows,
    fillInTemplate: webui.@THEME@.widget.table2RowGroup.fillInTemplate,
    getProps: webui.@THEME@.widget.table2RowGroup.getProps,
    resize: webui.@THEME@.widget.table2RowGroup.event.resize.processEvent,
    scroll: webui.@THEME@.widget.table2RowGroup.event.scroll.processEvent,
    setColumnProps: webui.@THEME@.widget.table2RowGroup.setColumnProps,
    setProps: webui.@THEME@.widget.table2RowGroup.setProps,
    _setProps: webui.@THEME@.widget.table2RowGroup._setProps,
    updateRowsText: webui.@THEME@.widget.table2RowGroup.updateRowsText,

    // Set defaults.
    currentRow: 0, // Current row in view.
    first: 0, // Index used to obtain rows.
    event: webui.@THEME@.widget.table2RowGroup.event,
    widgetType: "table2RowGroup"
});
