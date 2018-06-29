/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */


dojo.provide("webui.@THEME@.widget.table2RowGroup");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.table2RowGroup.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.table2RowGroup = function() {
    this.widgetType = "table2RowGroup";
    dojo.widget.Widget.call(this);

    /**
     * This function is used to generate a template based widget.
     */
    this.fillInTemplate = function() {
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
        this.domNode.setProps = webui.@THEME@.widget.table2RowGroup.setProps;

        // Set private functions (private functions/props prefixed with "_").
        this.domNode._addColumns = webui.@THEME@.widget.table2RowGroup.addColumns;
        this.domNode._addRows = webui.@THEME@.widget.table2RowGroup.addRows;
        this.domNode._resize = webui.@THEME@.widget.table2RowGroup.resize.processEvent;
        this.domNode._setHeight = webui.@THEME@.widget.table2RowGroup.setHeight;
        this.domNode._setRowsText = webui.@THEME@.widget.table2RowGroup.setRowsText;

        // Set events.
        dojo.event.connect(this.domNode, "onscroll", 
            webui.@THEME@.widget.table2RowGroup.scroll.processEvent);

        // Resize hack for Moz/Firefox.
        if (webui.@THEME@.common.browser.is_nav == true) {
            dojo.event.connect(window, "onresize",
                webui.@THEME@.widget.table2RowGroup.resize.createCallback(this.id));
        }

        // Set private properties (private functions/props prefixed with "_").
        this.domNode._first = 0; // Index used to obtain rows.
        this.domNode._currentRow = 0; // Current row in view.

        // Set properties.
        this.domNode.setProps({
            columns: this.columns,
            footerText: this.footerText,
            headerText: this.headerText,
            height: this.height,
            id: this.id,
            maxRows: (this.maxRows > 0) ? this.maxRows : 5,
            templatePath: this.templatePath,
            totalRows: (this.totalRows > 0) ? this.totalRows : 0
        });

        // Add initial rows.
        webui.@THEME@.widget.table2RowGroup.scroll.processEvent({
            currentTarget: this.domNode
        });
        return true;
    }
}

/**
 * This function is used to add columns with the following Object
 * literals.
 *
 * <ul>
 *  <li>columns</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.table2RowGroup.addColumns = function(props) {
    if (props == null || props.columns == null) {
        return false;
    }

    // Get widget.
    var widget = dojo.widget.byId(this.id);
    if (widget == null) {
        return false;
    }

    // Containers are visible if at least one header/footer exists.
    var headerVisible = false;
    var footerVisible = false;

    for (var i = 0; i < props.columns.length; i++) {
        var col = props.columns[i];
        var headerClone = widget.colHeaderNode;
        var footerClone = widget.colFooterNode;

        // Clone nodes.
        if (i + 1 < props.columns.length) {
            headerClone = headerClone.cloneNode(true);
            footerClone = footerClone.cloneNode(true);

            // Append nodes.
            widget.colHeaderContainer.insertBefore(headerClone, widget.colHeaderNode);
            widget.colFooterContainer.insertBefore(footerClone, widget.colFooterNode);
        }

        // Set properties.
        headerClone.id = col.id + "_colHeader" + i;
        footerClone.id = col.id + "_colFooter" + i;
        if (col.width) { headerClone.style.width = footerClone.style.width = col.width; }

        // Add text.
        if (col.headerText) {
            webui.@THEME@.widget.common.addFragment(headerClone, col.headerText);
            headerVisible = true;
        }
        if (col.footerText) {
            webui.@THEME@.widget.common.addFragment(footerClone, col.footerText);
            footerVisible = true;
        }
    }

    // Show containers.
    webui.@THEME@.common.setVisibleElement(widget.colHeaderContainer, headerVisible);
    webui.@THEME@.common.setVisibleElement(widget.colFooterContainer, footerVisible);
}

/**
 * This function is used to set rows with the following Object
 * literals.
 *
 * <ul>
 *  <li>first</li>
 *  <li>rows</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.table2RowGroup.addRows = function(props) {
    if (props == null || props.first == null || props.rows == null) {
        return false;
    }

    // Get nodes.
    var widget = dojo.widget.byId(this.id);
    if (widget == null) {
        return false;
    }

    // Reject duplicate AJAX requests.
    if (this._first != props.first) {
        return;
    }

    // For each row found, clone row container.
    for (var i = 0; i < props.rows.length; i++) {
        var cols = props.rows[i]; // Get next column.
        var rowId = this.id + ":" + (this._first + i);

        // Get row node.
        var rowNode = document.getElementById(this.id + "_rowNode");
        if (rowNode == null) {
            continue;
        }

        // Clone row container.
        rowNode.id = rowId + "_rowNode"; // Set id so we can locate row nodes in clone.
        var rowContainerClone = widget.rowContainer.cloneNode(true); // Clone with new row id.
        widget.tbodyContainer.appendChild(rowContainerClone); // Add row container clone.

        // Set properties.
        rowContainerClone.id = rowId;
        webui.@THEME@.common.setVisibleElement(rowContainerClone, true); // Set visible.

        // Get row node from clone.
        rowNode.id = this.id + "_rowNode"; // Restore id before retrieving new node.
        rowNode = document.getElementById(rowId + "_rowNode");
        if (rowNode == null) {
            continue;
        }

        // For each column found, clone row node.
        for (var k = 0; k < cols.length; k++) {
            var col = this._props.columns[k]; // Get default column props.
            var colId = col.id.replace(this.id, rowId);
            var rowNodeClone = rowNode;

            // Clone node.
            if (k + 1 < cols.length) {
                rowNodeClone = rowNode.cloneNode(true);
                rowContainerClone.insertBefore(rowNodeClone, rowNode);
            }

            // Set properties.
            rowNodeClone.id = colId;
            if (col.width) { rowNodeClone.style.width = col.width; }

            // Add cell data.
            webui.@THEME@.widget.common.addFragment(rowNodeClone, cols[k], "last");
        }
    }

    // Set rows text upon first display only.
    if (this._first == 0) {
        this._setHeight();
        this._setRowsText();
    }

    // Set resize event -- hack for Moz/Firefox.
    if (webui.@THEME@.common.browser.is_nav == true) {
        this._resize();
    }

    // Set first row value.
    this._first += props.rows.length;

    return true;
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
            return function(evt) {
                // Note: The evt param for this event is not required here.
                var domNode = document.getElementById(id);
                if (domNode == null) {
                    return null;
                } else {
                    domNode._resize();
                }
            };
        }
    },

    /**
     * Process resize event.
     */
    processEvent: function() {
        // Get row id.
        var rowId = this.id + ":0";

        // Get height offset of each visible column.
        for (var i = 0; i < this._props.columns.length; i++) {
            var col = this._props.columns[i]; // Get default column props.
            var colId = col.id.replace(this.id, rowId);

            // Get row node.
            var rowNode = document.getElementById(colId);
            if (rowNode == null) {
                continue;
            }

            // Get nodes.
            var columnHeaderNode = document.getElementById(col.id + "_colHeader" + i);
            var columnFooterNode = document.getElementById(col.id + "_colFooter" + i);

            // Set column header/footer width.
            if (columnHeaderNode) {
                columnHeaderNode.style.width = rowNode.offsetWidth + "px";
            }
            if (columnFooterNode) {
                columnFooterNode.style.width = rowNode.offsetWidth + "px";
            }
        }

        // Set group header/footer width.
        var widget = dojo.widget.byId(this.id);
        if (widget == null) {
            return false;
        }
        widget.groupHeaderNode.style.width = this.scrollWidth + "px";
        widget.groupFooterNode.style.width = this.scrollWidth + "px";

        return true;
    }
}

/**
 * This function is used to set the scrollable height after rows have
 * been added.
 */
webui.@THEME@.widget.table2RowGroup.setHeight = function() {
    // Get height offset of each visible row.
    var offset = 0;
    for (var i = this._currentRow; i < this._currentRow + this._props.maxRows; i++) {
        var rowContainer = document.getElementById(this.id + ":" + i);
        if (rowContainer != null) {
            offset += rowContainer.offsetHeight;
        } else {
            break;
        }
    }

    // Set height offset.
    if (offset > 0) {
        this.style.height = offset + "px";
    }
    return true;
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul>
 *  <li>columns</li>
 *  <li>footerText</li>
 *  <li>headerText</li>
 *  <li>height</li>
 *  <li>id</li>
 *  <li>maxRows</li>
 *  <li>totalRows</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.table2RowGroup.setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Save properties for later updates.
    if (this._props) {
        Object.extend(this._props, props); // Override existing values, if any.
    } else {
        this._props = props;
    }

    // Set DOM node properties.
    webui.@THEME@.widget.common.setCoreProperties(this, props);
    webui.@THEME@.widget.common.setJavaScriptProperties(this, props);

    // Set widget properties.
    var widget = dojo.widget.byId(this.id);
    if (widget == null) {
        return false;
    }

    // Add header.
    if (props.headerText) { 
        webui.@THEME@.widget.common.addFragment(widget.groupHeaderTextNode, props.headerText);
        webui.@THEME@.common.setVisibleElement(widget.groupHeaderContainer, true);
    }

    // Add footer.
    if (props.footerText) {
        webui.@THEME@.widget.common.addFragment(widget.groupFooterNode, props.footerText);
        webui.@THEME@.common.setVisibleElement(widget.groupFooterContainer, true);
    }

    // Add columns.
    if (props.columns) {
        // To do: Clear contents?
        this._addColumns({
            columns: props.columns
        });

        // Set colspan -- only works for IE.
        widget.groupHeaderNode.colSpan = props.columns.length;
        widget.groupFooterNode.colSpan = props.columns.length;
    }

    // To Do: Hack for A11Y testing.
    widget.tableContainer.summary = "This is a row group";

    return true;
}

/**
 * This function is used to set rows text (e.g., "1 - 5 of 20").
 */
webui.@THEME@.widget.table2RowGroup.setRowsText = function() {
    // Get widget.
    var widget = dojo.widget.byId(this.id);
    if (widget == null) {
        return false;
    }

    // To do: Localize & add filter text.

    // Add title augment.
    var firstRow = this._currentRow + 1;
    var lastRow = Math.min(this._props.totalRows,
        this._currentRow + this._props.maxRows);
    widget.groupHeaderRowsTextNode.innerHTML = "Items: " + firstRow + " - " + 
        lastRow + " of " + this._props.totalRows;

    return true;
}

/**
 * This closure is used to process scroll events.
 */
webui.@THEME@.widget.table2RowGroup.scroll = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_widget_table2RowGroup_scroll_begin",
    endEventTopic: "webui_widget_table2RowGroup_scroll_end",
 
    /**
     * Process scroll event.
     *
     * @param evt Event generated by scroll bar.
     */
    processEvent: function(evt) {
        if (evt == null) {
            return false;
        }

        // Get DOM node.
        var domNode;
        if (evt.currentTarget) {
            domNode = evt.currentTarget;
        } else {
            return false;
        }

        // Publish an event to retrieve rows.
        if (domNode._first < domNode._props.totalRows 
                && domNode._currentRow % domNode._props.maxRows == 0) {
            webui.@THEME@.widget.table2RowGroup.scroll.publishBeginEvent(evt);
        }

        // Set current row based on scroll position and row offset.
        var first = 0; // First row in range.
        var last = Math.min(domNode._props.totalRows,
            domNode._first + domNode._props.maxRows) - 1; // Last row in range.
        var scrollTop = domNode.scrollTop + 5; // Offset for borders.
        while (first < last) {
            var mid = Math.floor((first + last) / 2); // Index of midpoint.
            var rowContainer = document.getElementById(domNode.id + ":" + mid);
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
        domNode._currentRow = Math.max(0, first - 1);

        // Set rows text.
        domNode._setRowsText();

        return true;
    },

    /**
     * Publish an event for custom AJAX implementations to listen for.
     *
     * @param evt Event generated by scroll bar.
     */
    publishBeginEvent: function(evt) {
        dojo.event.topic.publish(webui.@THEME@.widget.table2RowGroup.scroll.beginEventTopic, evt);
        return true;
    },

    /**
     * Publish an event for custom AJAX implementations to listen for.
     *
     * @param props Key-Value pairs of properties of the widget.
     */
    publishEndEvent: function(props) {
        dojo.event.topic.publish(webui.@THEME@.widget.table2RowGroup.scroll.endEventTopic, props);
        return true;
    }
}

dojo.inherits(webui.@THEME@.widget.table2RowGroup, dojo.widget.HtmlWidget);

//-->
