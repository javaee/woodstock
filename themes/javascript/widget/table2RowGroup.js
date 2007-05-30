//<!--
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
 * This function is used to add rows using the gieven array. Each row contains
 * an array of columns which holds table data.
 *
 * @param rows An array of rows.
 */
webui.@THEME@.widget.table2RowGroup.addRows = function(rows) {
    if (rows == null) {
        return false;
    }

    // For each row found, clone row container.
    for (var i = 0; i < rows.length; i++) {
        var cols = rows[i]; // Get columns.
        var rowId = this.id + ":" + (this.first + i); // Get row id.

        // Clone row container without row node.
        var rowContainerClone = this.rowContainer.cloneNode(false);
        this.tbodyContainer.appendChild(rowContainerClone);

        // Set properties.
        rowContainerClone.id = rowId;
        webui.@THEME@.common.setVisibleElement(rowContainerClone, true);
        
        // For each column found, clone row node.
        for (var k = 0; k < cols.length; k++) {
            var col = this.columns[k]; // Get current column.
            var colId = col.id.replace(this.id, rowId); // Get col id.

            // Clone node.
            var rowNodeClone = this.rowNode.cloneNode(true);
            rowContainerClone.appendChild(rowNodeClone);

            // Set properties.
            rowNodeClone.id = colId;
            if (col.width) { rowNodeClone.style.width = col.width; }

            // Add cell data.
            this.addFragment(rowNodeClone, cols[k], "last");
        }

        // Save row for destroy() function.
        if (this.first > 0) {
            this.rows[this.rows.length] = rows[i];
        }
    }

    // Set first row value.
    this.first += rows.length;

    // Adjust layout.
    setTimeout(webui.@THEME@.widget.table2RowGroup.resize.createCallback(this.id), 0);

    return true;
}

/**
 * This function is used to fill a template with widget properties.
 *
 * Note: Anything to be set only once should be added here; otherwise, the
 * setProps() function should be used to set properties.
 */
webui.@THEME@.widget.table2RowGroup.fillInTemplate = function() {
    // Set ids.
    if (this.id) {
        this.colFooterContainer.id = this.id + "_colFooterContainer";
        this.colFooterNode.id = this.id + "_colFooterNode";
        this.colHeaderContainer.id = this.id + "_colHeaderContainer";
        this.colHeaderNode.id = this.id + "_colHeaderNode";
        this.domNode.id = this.id;
        this.groupFooterContainer.id = this.id + "_groupFooterContainer";
        this.groupFooterNode.id = this.id + "_groupFooter";
        this.groupHeaderContainer.id = this.id + "_groupHeaderContainer";
        this.groupHeaderNode.id = this.id + "_groupHeader";
        this.groupHeaderTextNode.id = this.id + "_groupHeaderText";
        this.groupHeaderRowsTextNode.id = this.id + "_groupHeaderRowsTextNode";
        this.rowContainer.id = this.id + "_rowContainer";
        this.rowNode.id = this.id + "_rowNode";
        this.tableContainer.id = this.id + "_tableContainer";
        this.tbodyContainer.id = this.id + "_tbodyContainer";
        this.tfootContainer.id = this.id + "_tfootContainer";
        this.theadContainer.id = this.id + "_theadContainer";
    }

    // Set public functions.
    this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }
    this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }
    this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }

    // Set events.
    dojo.event.connect(this.domNode, "onscroll", 
        webui.@THEME@.widget.table2RowGroup.scroll.processEvent);

    // Resize hack for Moz/Firefox.
    if (webui.@THEME@.common.browser.is_nav == true) {
        dojo.event.connect(window, "onresize",
            webui.@THEME@.widget.table2RowGroup.resize.createCallback(this.id));
    }

    // Set properties.
    return this.setProps();
}

/**
 * This function is used to get widget properties. Please see
 * webui.@THEME@.widget.table2RowGroup.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.table2RowGroup.getProps = function() {
    var props = {};

    // Set properties.
    if (this.columns) { props.columns = this.columns; }
    if (this.first) { props.first = this.first; }
    if (this.footerText) { props.footerText = this.footerText; }
    if (this.headerText) { props.headerText = this.headerText; }
    if (this.height) { props.height = this.height; }
    if (this.maxRows) { props.maxRows = this.maxRows; }
    if (this.rows) { props.rows = this.rows; }
    if (this.totalRows) { props.totalRows = this.totalRows; }

    // Add DOM node properties.
    Object.extend(props, this.getCommonProps());
    Object.extend(props, this.getCoreProps());
    Object.extend(props, this.getJavaScriptProps());

    return props;
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.table2RowGroup.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_table2RowGroup_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_table2RowGroup_refresh_end",
 
    /**
     * Process refresh event.
     *
     * @param execute The string containing a comma separated list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.table2RowGroup.refresh.beginEventTopic, {
                id: this.id,
                execute: execute
            });
        return true;
    }
}

/**
 * This closure is used to process resize events.
 */
webui.@THEME@.widget.table2RowGroup.resize = {
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
        // Set height and rows text.
        this.setHeight();
        this.setRowsText();

        // Get row id of first row -- all cells must be same width.
        var rowId = this.id + ":0";

        // Get height offset of each visible column.
        for (var i = 0; i < this.columns.length; i++) {
            var col = this.columns[i]; // Get default column props.
            var colId = col.id.replace(this.id, rowId);

            // Get row node.
            var rowNode = document.getElementById(colId);
            if (rowNode == null) {
                continue;
            }

            // Get nodes.
            var columnHeaderNode = document.getElementById(col.id + "_colHeader");
            var columnFooterNode = document.getElementById(col.id + "_colFooter");

            // Set column header/footer width.
            if (columnHeaderNode) {
                columnHeaderNode.style.width = rowNode.offsetWidth + "px";
            }
            if (columnFooterNode) {
                columnFooterNode.style.width = rowNode.offsetWidth + "px";
            }
        }

        // Set group header/footer width.
        this.groupHeaderNode.style.width = this.domNode.scrollWidth + "px";
        this.groupFooterNode.style.width = this.domNode.scrollWidth + "px";

        // Set colspan -- only works for IE.
        this.groupHeaderNode.colSpan = this.columns.length;
        this.groupFooterNode.colSpan = this.columns.length;

        return true;
    }
}

/**
 * This function is used to set column headers and footers.
 */
webui.@THEME@.widget.table2RowGroup.setColumns = function() {
    // Clear column headers/footers.
    this.removeChildNodes(this.colHeaderContainer);
    this.removeChildNodes(this.colFooterContainer);

    // Containers are visible if at least one header/footer exists.
    var headerVisible = false;
    var footerVisible = false;

    for (var i = 0; i < this.columns.length; i++) {
        var col = this.columns[i];
        var headerClone = this.colHeaderNode.cloneNode(true);
        var footerClone = this.colFooterNode.cloneNode(true);

        // Set properties.
        headerClone.id = col.id + "_colHeader";
        footerClone.id = col.id + "_colFooter";
        if (col.width) {
            headerClone.style.width = col.width;
            footerClone.style.width = col.width;
        }

        // Add text.
        if (col.headerText) {
            this.addFragment(headerClone, col.headerText);
            headerVisible = true;
        }
        if (col.footerText) {
            this.addFragment(footerClone, col.footerText);
            footerVisible = true;
        }

        // Append nodes.
        this.colHeaderContainer.appendChild(headerClone);
        this.colFooterContainer.appendChild(footerClone);
    }

    // Show containers.
    webui.@THEME@.common.setVisibleElement(this.colHeaderContainer, headerVisible);
    webui.@THEME@.common.setVisibleElement(this.colFooterContainer, footerVisible);
}

/**
 * This function is used to set the scrollable height after rows have
 * been added.
 */
webui.@THEME@.widget.table2RowGroup.setHeight = function() {
    // Get height offset of each visible row.
    var offset = 0;
    for (var i = this.currentRow; i < this.currentRow + this.maxRows; i++) {
        var rowContainer = document.getElementById(this.id + ":" + i);
        if (rowContainer != null) {
            offset += rowContainer.offsetHeight;
        } else {
            break;
        }
    }

    // Set height offset.
    if (offset > 0) {
        this.domNode.style.height = offset + "px";
    }
    return true;
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul>
 *  <li>columns</li>
 *  <li>first</li>
 *  <li>footerText</li>
 *  <li>headerText</li>
 *  <li>height</li>
 *  <li>id</li>
 *  <li>maxRows</li>
 *  <li>rows</li>
 *  <li>totalRows</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.table2RowGroup.setProps = function(props) {
    // Save properties for later updates.
    if (props != null) {
        // Replace rows -- do not extend.
        if (props.rows) {
            this.rows = null;
        }
        this.extend(this, props);
    } else {
        props = this.getProps(); // Widget is being initialized.
    }

    // Set DOM node properties.
    this.setCoreProps(this.domNode, props);
    this.setCommonProps(this.domNode, props);
    this.setJavaScriptProps(this.domNode, props);

    // Add header.
    if (props.headerText) { 
        this.addFragment(this.groupHeaderTextNode, props.headerText);
        webui.@THEME@.common.setVisibleElement(this.groupHeaderContainer, true);
    }

    // Add footer.
    if (props.footerText) {
        this.addFragment(this.groupFooterNode, props.footerText);
        webui.@THEME@.common.setVisibleElement(this.groupFooterContainer, true);
    }

    // Set columns.
    if (props.columns && this.refreshCols != false) {
        this.setColumns();
    }
    // To do: Cannot refresh column headers/footers due to poor CSS styles.
    this.refreshCols = false;

    // Add rows
    if (props.rows) {
        this.first = 0; // Reset index used to obtain rows.
        this.currentRow = 0; // Reset current row in view.

        // Clear rows.
        this.removeChildNodes(this.tbodyContainer);
        this.addRows(props.rows);
    }

    // To Do: Hack for A11Y testing.
    this.tableContainer.summary = "This is a row group";

    return props; // Return props for subclasses.
}

/**
 * This function is used to set rows text (e.g., "1 - 5 of 20").
 */
webui.@THEME@.widget.table2RowGroup.setRowsText = function() {

    // To do: Localize & add filter text.

    // Add title augment.
    var firstRow = this.currentRow + 1;
    var lastRow = Math.min(this.totalRows,
        this.currentRow + this.maxRows);
    this.groupHeaderRowsTextNode.innerHTML = "Items: " + firstRow + " - " + 
        lastRow + " of " + this.totalRows;

    return true;
}

/**
 * This closure is used to process scroll events.
 */
webui.@THEME@.widget.table2RowGroup.scroll = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_table2RowGroup_scroll_begin",
    endEventTopic: "webui_@THEME@_widget_table2RowGroup_scroll_end",
 
    /**
     * Process scroll event.
     *
     * @param event Event generated by scroll bar.
     */
    processEvent: function(event) {
        if (event == null) {
            return false;
        }

        // Get widget.
        var widget;
        if (event.currentTarget) {
            widget = dojo.widget.byId(event.currentTarget.id);
        }
        if (widget == null) {
            return false;
        }

        // Publish event to retrieve data.
        if (widget.first < widget.totalRows 
                && widget.currentRow % widget.maxRows == 0) {
            // Publish an event for custom AJAX implementations to listen for.
            dojo.event.topic.publish(
                webui.@THEME@.widget.table2RowGroup.scroll.beginEventTopic, {
                    id: widget.id,
                    first: widget.first
                });
        }

        // Set current row based on scroll position and row offset.
        var first = 0; // First row in range.
        var last = Math.min(widget.totalRows,
            widget.first + widget.maxRows) - 1; // Last row in range.
        var scrollTop = widget.domNode.scrollTop + 5; // Offset for borders.
        while (first < last) {
            var mid = Math.floor((first + last) / 2); // Index of midpoint.
            var rowContainer = document.getElementById(widget.id + ":" + mid);
            if (rowContainer == null) {
                break;
            }
            // Test if scroll position matches row offset.
            if (scrollTop < rowContainer.offsetTop) {
                last = mid; // Search left half.
            } else if (scrollTop >= rowContainer.offsetTop) {
                first = mid + 1; // Search right half.
            }
        }
        widget.currentRow = Math.max(0, first - 1);

        // Set rows text.
        widget.setRowsText();

        return true;
    }
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.table2RowGroup, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.table2RowGroup, {
    // Set private functions.
    addRows: webui.@THEME@.widget.table2RowGroup.addRows,
    fillInTemplate: webui.@THEME@.widget.table2RowGroup.fillInTemplate,
    getProps: webui.@THEME@.widget.table2RowGroup.getProps,
    refresh: webui.@THEME@.widget.table2RowGroup.refresh.processEvent,
    resize: webui.@THEME@.widget.table2RowGroup.resize.processEvent,
    setColumns: webui.@THEME@.widget.table2RowGroup.setColumns,
    setHeight: webui.@THEME@.widget.table2RowGroup.setHeight,
    setProps: webui.@THEME@.widget.table2RowGroup.setProps,
    setRowsText: webui.@THEME@.widget.table2RowGroup.setRowsText,

    // Set defaults.
    first: 0, // Index used to obtain rows.
    currentRow: 0, // Current row in view.
    widgetType: "table2RowGroup"
});

//-->
