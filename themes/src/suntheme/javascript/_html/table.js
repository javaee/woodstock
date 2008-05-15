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

@JS_NS@._dojo.provide("@JS_NS@._html.table");

@JS_NS@._dojo.require("@JS_NS@._base.common");
@JS_NS@._dojo.require("@JS_NS@._base.proto");
@JS_NS@._dojo.require("@JS_NS@.widget.common");

/** 
 * @class This class contains functions for table components.
 * <p>
 * Once the table is rendered, you will be able to invoke functions directly on 
 * the HTML element. For example:
 * </p><p><pre>
 * var table = document.getElementById("form1:table1");
 * var count = table.getAllSelectedRowsCount();
 * </pre></p><p>
 * Note: It is assumed that formElements.js has been included in the page. In
 * addition, all given HTML element IDs are assumed to be the outter most tag
 * enclosing the component.
 * </p>
 * @static
 * @private
 */
@JS_NS@._html.table = {
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // DOM functions
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * This function is used to confirm the number of selected components (i.e., 
     * checkboxes or radiobuttons used to de/select rows of the table), affected
     * by a delete action. This functionality requires the selectId property of
     * the tableColumn component and hiddenSelectedRows property of the
     * tableRowGroup component to be set.
     * <p>
     * If selections are hidden from view, the confirmation message indicates the
     * number of selections not displayed in addition to the total number of
     * selections. If selections are not hidden, the confirmation message indicates
     * only the total selections.
     * </p>
     *
     * @return {boolean} A value of true if the user clicks the "OK" button, or 
     * false if the user clicks the "Cancel" button.
     */
    confirmDeleteSelectedRows: function() {
        return this.confirmSelectedRows(this.deleteSelectionsMsg);
    },

    /**
     * This function is used to confirm the number of selected components (i.e., 
     * checkboxes or radiobuttons used to de/select rows of the table), affected by
     * an action such as edit, archive, etc. This functionality requires the 
     * selectId property of the tableColumn component and hiddenSelectedRows
     * property of the tableRowGroup component to be set.
     * <p>
     * If selections are hidden from view, the confirmation message indicates the
     * number of selections not displayed in addition to the total number of
     * selections. If selections are not hidden, the confirmation message indicates
     * only the total selections.
     * </p>
     *
     * @param {String} message The confirmation message (e.g., Archive all selections?).
     * @return {boolean} A value of true if the user clicks the "OK" button, or 
     * false if the user clicks the "Cancel" button.
     */
    confirmSelectedRows: function(message) {
        // Get total selections message.
        var totalSelections = this.getAllSelectedRowsCount();
        var totalSelectionsArray = this.totalSelectionsMsg.split("{0}");
        var totalSelectionsMsg = totalSelectionsArray[0] + totalSelections;

        // Append hidden selections message.
        var hiddenSelections = this.getAllHiddenSelectedRowsCount();
        if (hiddenSelections > 0) {
            // Get hidden selections message.
            var hiddenSelectionsArray = this.hiddenSelectionsMsg.split("{0}");
            var hiddenSelectionsMsg = hiddenSelectionsArray[0] + hiddenSelections;

            totalSelectionsMsg = hiddenSelectionsMsg + totalSelectionsMsg;
        }
        return (message != null)
            ? confirm(totalSelectionsMsg + message)
            : confirm(totalSelectionsMsg);
    },

    /**
     * This function is used to toggle the filter panel from the filter menu. This
     * functionality requires the filterId of the table component to be set. In 
     * addition, the selected value must be set as well to restore the default
     * selected value when the embedded filter panel is closed.
     * <p>
     * If the "Custom Filter" option has been selected, the table filter panel is 
     * toggled. In this scenario, false is returned indicating the onChange event,
     * generated by the table filter menu, should not be allowed to continue.
     * </p><p>
     * If the "Custom Filter Applied" option has been selected, no action is taken.
     * Instead, the is selected filter menu is reverted back to the "Custom Filter" 
     * selection. In this scenario, false is also returned indicating the onChange 
     * event, generated by the table filter menu, should not be allowed to continue.
     * </p><p>
     * For all other selections, true is returned indicating the onChange event, 
     * generated by the table filter menu, should be allowed to continue.
     * </p>
     *
     * @return {boolean} true if successful; otherwise, false.
     */
    filterMenuChanged: function() {
        // Validate panel IDs.
        if (this.panelToggleIds == null || this.panelToggleIds.length == 0) {
            return false;
        }

        // Get filter menu.
        var menu = this._getSelectElement(this.panelToggleIds[this.FILTER]);
        if (menu == null) {
            return true;
        }

        // Test if table filter panel should be opened.
        if (menu.options[menu.selectedIndex].value == this.customFilterOptionValue) {
            this.toggleFilterPanel();
            return false;
        } else if (menu.options[menu.selectedIndex].
                value == this.customFilterAppliedOptionValue) {
            // Set selected option.
            menu.selectedIndex = 0;
            for (var i = 0; i < menu.options.length; i++) {
                if (menu.options[i].value == this.customFilterOptionValue) {
                    menu.options[i].selected = true;
                    break;
                }
            }
            return false;
        }
        return true;
    },

    /**
     * This function is used to get the number of selected components in all row groups
     * displayed in the table (i.e., checkboxes or radiobuttons used to de/select 
     * rows of the table). This functionality requires the selectId property of the
     * tableColumn component and hiddenSelectedRows property of the table
     * component to be set.
     *
     * @return {int} The number of components selected in the current page.
     */
    getAllSelectedRowsCount: function() {
        return this.getAllHiddenSelectedRowsCount() +
            this.getAllRenderedSelectedRowsCount();
    },

    /**
     * This function is used to get the number of selected components, in all row groups
     * displayed in the table (i.e., checkboxes or radiobuttons used to de/select
     * rows of the table), currently hidden from view. This functionality requires 
     * the selectId property of the tableColumn component and hiddenSelectedRows
     * property of the table component to be set.
     *
     * @return {int} The number of selected components hidden from view.
     */
    getAllHiddenSelectedRowsCount: function() {
        var count = 0;

        // Validate group IDs.
        if (this.groupIds == null || this.groupIds.length == 0) {
            return count;
        }

        // For each group, get the row and select id.
        for (var i = 0; i < this.groupIds.length; i++) {
            count = count + this._getGroupHiddenSelectedRowsCount(this.groupIds[i]);
        }
        return count;
    },

    /**
     * This function is used to get the number of selected components, in all row groups
     * displayed in the table (i.e., checkboxes or radiobuttons used to de/select
     * rows of the table), currently rendered. This functionality requires 
     * the selectId property of the tableColumn component to be set.
     *
     * @return {int} The number of selected components hidden from view.
     */
    getAllRenderedSelectedRowsCount: function() {
        var count = 0;

        // Validate group IDs.
        if (this.groupIds == null || this.groupIds.length == 0) {
            return count;
        }

        // For each group, get the row and select id.
        for (var i = 0; i < this.groupIds.length; i++) {
            count = count + this._getGroupRenderedSelectedRowsCount(this.groupIds[i]);
        }
        return count;
    },

    /**
     * This function is used to initialize all rows displayed in the table when the
     * state of selected components change (i.e., checkboxes or radiobuttons used to
     * de/select rows of the table). This functionality requires the selectId
     * property of the tableColumn component to be set.
     *
     * @return {boolean} true if successful; otherwise, false.
     */
    initAllRows: function() {
        // Validate groupIDs.
        if (this.groupIds == null || this.groupIds.length == 0) {
            return false;
        }

        // For each group, get the row and select id.
        for (var i = 0; i < this.groupIds.length; i++) {
            this._initGroupRows(this.groupIds[i]);
        }
        return true;
    },

    /**
     * This function is used to toggle the filter panel open or closed. This
     * functionality requires the filterId of the table component to be set. In 
     * addition, the selected value must be set as well to restore the default
     * selected value when the embedded filter panel is closed.
     *
     * @return {boolean} true if successful; otherwise, false.
     */
    toggleFilterPanel: function() {
        // Validate panel IDs.
        if (this.panelIds == null || this.panelIds.length == 0) {
            return false;
        }

        // Toggle filter panel.
        this._togglePanel(this.panelIds[this.FILTER],
        this.panelFocusIds[this.FILTER], this.panelToggleIds[this.FILTER]);
        return this._resetFilterMenu(this.panelToggleIds[this.FILTER]); // Reset filter menu.
    },

    /**
     * This function is used to toggle the preferences panel open or closed. This
     * functionality requires the filterId of the table component to be set.
     *
     * @return {boolean} true if successful; otherwise, false.
     */
    togglePreferencesPanel: function() {
        // Validate panel IDs.
        if (this.panelIds == null || this.panelIds.length == 0) {
            return false;
        }

        // Toggle preferences panel.
        this._togglePanel(this.panelIds[this.PREFERENCES],
        this.panelFocusIds[this.PREFERENCES], this.panelToggleIds[this.PREFERENCES]);
        return this._resetFilterMenu(this.panelToggleIds[this.FILTER]); // Reset filter menu.
    },

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Internal functions
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * This function is used to initialize HTML element properties with Object
     * literals.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {String} id The HTML element ID for the component.
     *
     * // Panel Properties
     * @config {Array} panelIds An array of embedded panel IDs.</li>
     * @config {Array} panelFocusIds An array of IDs used to set focus for open panels.</li>
     * @config {Array} panelToggleIds An array of IDs used to toggle embedded panels.</li>
     * @config {Array} panelToggleIconsOpen An array of toggle icons for open panels.</li>
     * @config {Array} panelToggleIconsClose An array of toggle icons for closed panels.</li>
     *
     * // Filter Properties
     * @config {String} basicFilterStyleClass The style class for basic or no filters.</li>
     * @config {String} customFilterStyleClass The style class for custom filters.</li>
     * @config {String} customFilterOptionValue The custom filter menu option value.</li>
     * @config {String} customFilterAppliedOptionValue The custom filter applied menu option value.</li>
     *
     * // Sort Panel Properties
     * @config {Array} sortColumnMenuIds An array of HTML element IDs for sort column menu components.</li>
     * @config {Array} sortOrderMenuIds An array of HTML element IDs for sort order menu components.</li>
     * @config {Array} sortOrderToolTips An array of tool tips used for sort order menus.</li>
     * @config {Array} sortOrderToolTipsAscending An array of ascending tool tips used for sort order menus.</li>
     * @config {Array} sortOrderToolTipsDescending An array of descending tool tips used for sort order menus.</li>
     * @config {String} duplicateSelectionMsg The message displayed for duplicate menu selections.</li>
     * @config {String} missingSelectionMsg The message displayed for missing menu selections.</li>
     * @config {String} selectSortMenuOptionValue The sort menu option value for the select column.</li>
     * @config {boolean} hiddenSelectedRows Flag indicating that selected rows might be currently hidden from view.</li>
     * @config {boolean} paginated Flag indicating table is in pagination mode.</li>
     *
     * // Group Properties
     * @config {String} selectRowStylClass The style class for selected rows.</li>
     * @config {Array} selectIds An arrary of component IDs used to select rows of the table.</li>
     * @config {Array} groupIds An array of TableRowGroup IDs rendered for the table.</li>
     * @config {Array} rowIds An array of row IDs for rendered for each TableRowGroup.</li>
     * @config {Array} hiddenSelectedRowCounts An array of selected row counts hidden from view.</li>
     * @config {String} hiddenSelectionsMsg The hidden selections message for confirm dialog.</li>
     * @config {String} totalSelectionsMsg The total selections message for confirm dialog.</li>
     * @config {String} deleteSelectionsMsg The delete selections message for confirm dialog.</li>
     *
     * // Group Panel Properties
     * @param {String} columnFooterId ID for column footer.</li>
     * @param {String} columnHeaderId ID for column header.</li>
     * @param {String} tableColumnFooterId ID for table column footer.</li>
     * @param {String} groupFooterId ID for group footer.</li>
     * @param {String} groupPanelToggleButtonId ID for group panel toggle button.</li>
     * @param {String} groupPanelToggleButtonToolTipOpen tool tip for open row group.</li>
     * @param {String} groupPanelToggleButtonToolTipClose tool tip for closed row group.</li>
     * @param {String} groupPanelToggleIconOpen The toggle icon for open row group.</li>
     * @param {String} groupPanelToggleIconClose The toggle icon for closed row group.</li>
     * @param {String} warningIconId ID for warning icon.</li>
     * @param {String} warningIconOpen The warning icon for open row group.</li>
     * @param {String} warningIconClosed The warning icon for closed row group.</li>
     * @param {String} warningIconToolTipOpen The warning icon tool tip for open row group.</li>
     * @param {String} warningIconToolTipClose The warning icon tool tip for closed row group.</li>
     * @param {String} collapsedHiddenFieldId ID for collapsed hidden field.</li>
     * @param {String} selectMultipleToggleButtonId ID for select multiple toggle button.</li>
     * @param {String} selectMultipleToggleButtonToolTip The select multiple toggle button tool tip.</li>
     * @param {String} selectMultipleToggleButtonToolTipSelected The select multiple toggle button tool tip when selected.</li>
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _init: function(props) {
        var message = "Cannot initialize table.";
        if (props == null || props.id == null) {
            console.debug(message); // See Firebug console.
            return false;
        }
        var domNode = document.getElementById(props.id);
        if (domNode == null) {
            console.debug(message); // See Firebug console.
            return false;
        }

        // Set given properties on domNode.
        @JS_NS@._base.proto._extend(domNode, props, false);

        // Misc properties.
        domNode.SEPARATOR = ":";   // NamingContainer separator.

        // Panel toggle keys.
        domNode.SORT        = 0;
        domNode.PREFERENCES = 1;
        domNode.FILTER      = 2;

        // Sort keys.
        domNode.PRIMARY   = 0;
        domNode.SECONDARY = 1;
        domNode.TERTIARY  = 2;

        // Replace extra backslashes, JSON escapes new lines (e.g., \\n).
	domNode.hiddenSelectionsMsg = (props.hiddenSelectionsMsg != null)
            ? props.hiddenSelectionsMsg.replace(/\\n/g, "\n") : "";
        domNode.totalSelectionsMsg = (props.totalSelectionsMsg != null)
            ? props.totalSelectionsMsg.replace(/\\n/g, "\n") : "";
	domNode.missingSelectionMsg = (props.missingSelectionMsg != null)
            ? props.missingSelectionMsg.replace(/\\n/g, "\n") : "";
	domNode.duplicateSelectionMsg = (props.duplicateSelectionMsg != null)
            ? props.duplicateSelectionMsg.replace(/\\n/g, "\n") : "";
        domNode.deleteSelectionsMsg = (props.deleteSelectionsMsg != null)
            ? props.deleteSelectionsMsg.replace(/\\n/g, "\n") : "";

        // Private functions.
        domNode._getGroupSelectedRowsCount = @JS_NS@._html.table._getGroupSelectedRowsCount;
        domNode._getGroupHiddenSelectedRowsCount = @JS_NS@._html.table._getGroupHiddenSelectedRowsCount;
        domNode._getGroupRenderedSelectedRowsCount = @JS_NS@._html.table._getGroupRenderedSelectedRowsCount;
        domNode._getSelectElement = @JS_NS@._html.table._getSelectElement;
        domNode._initGroupRows = @JS_NS@._html.table._initGroupRows;
        domNode._initPrimarySortOrderMenu = @JS_NS@._html.table._initPrimarySortOrderMenu;
        domNode._initPrimarySortOrderMenuToolTip = @JS_NS@._html.table._initPrimarySortOrderMenuToolTip;
        domNode._initSecondarySortOrderMenu = @JS_NS@._html.table._initSecondarySortOrderMenu;
        domNode._initSecondarySortOrderMenuToolTip = @JS_NS@._html.table._initSecondarySortOrderMenuToolTip;
        domNode._initSortColumnMenus = @JS_NS@._html.table._initSortColumnMenus;
        domNode._initSortOrderMenu = @JS_NS@._html.table._initSortOrderMenu;
        domNode._initSortOrderMenus = @JS_NS@._html.table._initSortOrderMenus;
        domNode._initSortOrderMenuToolTip = @JS_NS@._html.table._initSortOrderMenuToolTip;
        domNode._initTertiarySortOrderMenu = @JS_NS@._html.table._initTertiarySortOrderMenu;
        domNode._initTertiarySortOrderMenuToolTip = @JS_NS@._html.table._initTertiarySortOrderMenuToolTip;
        domNode._menuChanged = @JS_NS@._html.table._menuChanged;
        domNode._resetFilterMenu = @JS_NS@._html.table._resetFilterMenu;
        domNode._selectAllRows = @JS_NS@._html.table._selectAllRows;
        domNode._selectGroupRows = @JS_NS@._html.table._selectGroupRows;
        domNode._toggleGroupPanel = @JS_NS@._html.table._toggleGroupPanel;
        domNode._togglePanel = @JS_NS@._html.table._togglePanel;
        domNode._toggleSortPanel = @JS_NS@._html.table._toggleSortPanel;
        domNode._validateSortPanel = @JS_NS@._html.table._validateSortPanel;

        // Public functions.
        domNode.confirmDeleteSelectedRows = @JS_NS@._html.table.confirmDeleteSelectedRows;
        domNode.confirmSelectedRows = @JS_NS@._html.table.confirmSelectedRows;
        domNode.filterMenuChanged = @JS_NS@._html.table.filterMenuChanged;
        domNode.getAllSelectedRowsCount = @JS_NS@._html.table.getAllSelectedRowsCount;
        domNode.getAllHiddenSelectedRowsCount = @JS_NS@._html.table.getAllHiddenSelectedRowsCount;
        domNode.getAllRenderedSelectedRowsCount = @JS_NS@._html.table.getAllRenderedSelectedRowsCount;
        domNode.initAllRows = @JS_NS@._html.table.initAllRows;
        domNode.toggleFilterPanel = @JS_NS@._html.table.toggleFilterPanel;
        domNode.togglePreferencesPanel = @JS_NS@._html.table.togglePreferencesPanel;

        return true;
    },

    /**
     * This function is used to get the number of selected components for the given row 
     * group (i.e., checkboxes or radiobuttons used to de/select rows of the table).
     * This functionality requires the selectId property of the tableColumn component
     * and the hiddenSelectedRows property of the table component to be set.
     *
     * @param {String} groupId The HTML element ID for the tableRowGroup component.
     * @return {int} The number of components selected in the current page.
     * @private
     */
    _getGroupSelectedRowsCount: function(groupId) {
        return this._getGroupHiddenSelectedRowsCount(groupId) + 
            this._getGroupRenderedSelectedRowsCount(groupId);
    },

    /**
     * This function is used to get the number of selected components, for the given row 
     * group (i.e., checkboxes or radiobuttons used to de/select rows of the table),
     * currently hidden from view. This functionality requires the selectId property
     * of the tableColumn component and hiddenSelectedRows property of the table
     * component to be set.
     *
     * @param {String} groupId The HTML element ID for the tableRowGroup component.
     * @return {int} The number of selected components hidden from view.
     * @private
     */
    _getGroupHiddenSelectedRowsCount: function(groupId) {
        var count = 0;

        // Validate group IDs.
        if (this.groupIds == null || this.groupIds.length == 0
                || groupId == null) {
            return count;
        }

        // Find the given group Id in the groupIds array.
        for (var i = 0; i < this.groupIds.length; i++) {
            // Get selectId and rowIds array associated with groupId.
            if (groupId == this.groupIds[i]) {
                count = this.hiddenSelectedRowCounts[i];
                break;
            }
        }
        return count;
    },

    /**
     * This function is used to get the number of selected components, for the given row 
     * group (i.e., checkboxes or radiobuttons used to de/select rows of the table),
     * currently rendered. This functionality requires the selectId property of the
     * tableColumn component to be set.
     *
     * @param {String} groupId The HTML element ID for the tableRowGroup component.
     * @return {int} The number of components selected in the current page.
     * @private
     */
    _getGroupRenderedSelectedRowsCount: function(groupId) {
        var count = 0;

        // Validate group IDs.
        if (this.groupIds == null || this.groupIds.length == 0
                || groupId == null) {
            return count;
        }

        // Get selectId and rowIds array associated with groupId.
        var selectId = null;
        var rowIds = null;
        for (var i = 0; i < this.groupIds.length; i++) {
            if (groupId == this.groupIds[i]) {
                selectId = this.selectIds[i];
                rowIds = this.rowIds[i];
                break;
            }
        }

        // If selectId or rowIds could not be found, do not continue.
        if (selectId == null || rowIds == null) {
            return false;
        }

        // Update the select component for each row.
        for (var k = 0; k < rowIds.length; k++) {
            var select = document.getElementById(
                this.groupIds[i] + this.SEPARATOR + rowIds[k] + 
                this.SEPARATOR + selectId);
            if (select == null) {
                continue;
            }
            var props = select.getProps();
            if (props.disabled != true && props.checked == true) {
                count++;
            }
        }
        return count;
    },

    /**
     * Use this function to access the HTML select element that makes up
     * the dropDown.
     *
     * @param {String} elementId The component id of the JSF component (this id is
     * assigned to the span tag enclosing the HTML elements that make up
     * the dropDown).
     * @return {Node} a reference to the select element.
     */
    _getSelectElement: function(elementId) { 
        var widget = @JS_NS@.widget.common.getWidget(elementId);
        if (widget) {
            return widget.getSelectElement();
        }
        return null;
    },

    /**
     * This function is used to initialize rows for the given group when the state
     * of selected components change (i.e., checkboxes or radiobuttons used to
     * de/select rows of the table). This functionality requires the selectId
     * property of the tableColumn component to be set.
     *
     * @param {String} groupId The HTML element ID for the tableRowGroup component.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _initGroupRows: function(groupId) {
        // Validate groupIDs.
        if (this.groupIds == null || this.groupIds.length == 0
                || groupId == null) {
            return false;
        }

        // Get selectId and rowIds array associated with groupId.
        var selectId = null;
        var rowIds = null;
        for (var i = 0; i < this.groupIds.length; i++) {
            if (groupId == this.groupIds[i]) {
                selectId = this.selectIds[i];
                rowIds = this.rowIds[i];
                break;
            }
        }

        // If selectId or rowIds could not be found, do not continue.
        if (selectId == null || rowIds == null) {
            return false;
        }

        // Update the select component for each row.
        var checked = true; // Checked state of multiple select toggle button.
        var selected = false; // At least one component is selected.
        for (var k = 0; k < rowIds.length; k++) {
            var select = document.getElementById(
                this.groupIds[i] + this.SEPARATOR + rowIds[k] + 
                this.SEPARATOR + selectId);
            if (select == null) {
                continue;
            }

            // Set row style class.
            var row = document.getElementById(this.groupIds[i] + 
                this.SEPARATOR + rowIds[k]);
            var props = select.getProps();
            if (props.disabled == true) {
                // Don't highlight row or set checked/selected properties.
                @JS_NS@._base.common._stripStyleClass(row, 
                    this.selectRowStyleClass);
            } else if (props.checked == true) {
                @JS_NS@._base.common._addStyleClass(row, 
                    this.selectRowStyleClass);
                selected = true;
            } else {
                @JS_NS@._base.common._stripStyleClass(row, 
                    this.selectRowStyleClass);
                checked = false;
            }
        }

        // Set multiple select toggle button state.
        var title;
        var checkbox = document.getElementById(groupId + this.SEPARATOR + 
            this.selectMultipleToggleButtonId);
        if (checkbox != null) {
            title = (checked) 
                ? this.selectMultipleToggleButtonToolTipSelected
                : this.selectMultipleToggleButtonToolTip;
            checkbox.setProps({
                "checked": checked,
                "title": title
            });
        }

        // Get flag indicating groupis collapsed.
        var prefix = groupId + this.SEPARATOR;
        var collapsed = !@JS_NS@._base.common._isVisible(prefix + rowIds[0]);

        // Set next warning image.
        var image = document.getElementById(prefix + this.warningIconId);
        if (image != null) {
            var src = this.warningIconOpen;
            title = this.warningIconToolTipOpen;

            // Don't show icon when multiple select is checked.
            if (collapsed && selected && !checked) {
                src = this.warningIconClose;
                title = this.warningIconToolTipClose;
            }
            image.setProps({
               "src": src,
               "title": title
            });
        }
        return true;
    },

    /**
     * This function is used to initialize the primary sort order menus used in the 
     * table sort panel.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _initPrimarySortOrderMenu: function() {
        return this._initSortOrderMenu(this.sortColumnMenuIds[this.PRIMARY], 
            this.sortOrderMenuIds[this.PRIMARY]);
    },  

    /**
     * This function is used to initialize the primary sort order menu tool tips 
     * used in the table sort panel.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _initPrimarySortOrderMenuToolTip: function() {
        // Get sort order menu.
        var sortOrderMenu = this._getSelectElement(
            this.sortOrderMenuIds[this.PRIMARY]);
        if (sortOrderMenu != null) {
            // IE hack so disabled option is not selected -- bugtraq #6269683.
            if (sortOrderMenu.options[sortOrderMenu.selectedIndex].disabled == true) {
                sortOrderMenu.selectedIndex = 0;
            }
        }
        return this._initSortOrderMenuToolTip(this.sortColumnMenuIds[this.PRIMARY], 
            this.sortOrderMenuIds[this.PRIMARY]);
    },

    /**
     * This function is used to initialize the secondary sort order menus used in the 
     * table sort panel.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _initSecondarySortOrderMenu: function() {
        return this._initSortOrderMenu(this.sortColumnMenuIds[this.SECONDARY], 
            this.sortOrderMenuIds[this.SECONDARY]);
    },

    /**
     * This function is used to initialize the secondary sort order menu tool tips
     * used in the table sort panel.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _initSecondarySortOrderMenuToolTip: function() {
        // Get sort order menu.
        var sortOrderMenu = this._getSelectElement(
            this.sortOrderMenuIds[this.SECONDARY]);
        if (sortOrderMenu != null) {
            // IE hack so disabled option is not selected -- bugtraq #6269683.
            if (sortOrderMenu.options[sortOrderMenu.selectedIndex].disabled == true) {
                sortOrderMenu.selectedIndex = 0;
            }
        }
        return this._initSortOrderMenuToolTip(this.sortColumnMenuIds[this.SECONDARY], 
            this.sortOrderMenuIds[this.SECONDARY]);
    },

    /**
     * This function is used to initialize the primary, secondary, and tertiary 
     * sort column menus used in the table sort panel.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _initSortColumnMenus: function() {
        // Validate sort column menu IDs.
        if (this.sortColumnMenuIds == null || this.sortColumnMenuIds.length == 0) {
            return false;
        }

        // Set initial selected option for all sort column menus.
        for (var i = 0; i < this.sortColumnMenuIds.length; i++) {
            // Get sort column menu.
            var sortColumnMenu = this._getSelectElement(this.sortColumnMenuIds[i]);
            if (sortColumnMenu == null) {
                continue;
            }

            // Set default selected value.
            sortColumnMenu.selectedIndex = 0;

            // Set selected option.
            for (var k = 0; k < sortColumnMenu.options.length; k++) {
                if (sortColumnMenu.options[k].defaultSelected == true) {
                    sortColumnMenu.options[k].selected = true;
                    break;
                }
            }
            // Ensure hidden filed values are updated.
            this._menuChanged(this.sortColumnMenuIds[i]);
        }
        return true;
    },

    /**
     * This function is used to initialize the primary, secondary, and tertiary 
     * sort order menus used in the table sort panel.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _initSortOrderMenus: function() {
        // Validate sort order menu IDs.
        if (this.sortOrderMenuIds == null || this.sortOrderMenuIds.length == 0) {
            return false;
        }

        // Set initial selected option for all sort column menus.
        for (var i = 0; i < this.sortOrderMenuIds.length; i++) {
            // Get sort order menu.
            var sortOrderMenu = this._getSelectElement(this.sortOrderMenuIds[i]);
            if (sortOrderMenu == null) {
                continue;
            }

            // Set default selected value.
            sortOrderMenu.selectedIndex = 0;

            // Get sort column menu.
            var sortColumnMenu = this._getSelectElement(this.sortColumnMenuIds[i]);
            if (sortColumnMenu != null) {
                // If the table is paginated and there are no hidden selected rows, the select
                // column cannot be sorted descending.
                if (sortColumnMenu.options[sortColumnMenu.selectedIndex].
                        value == this.selectSortMenuOptionValue
                        && !this.hiddenSelectedRows && this.paginated) {
                    sortOrderMenu.options[1].disabled = true;
                } else {
                    sortOrderMenu.options[1].disabled = false;
                }
            }

            // Set selected option.
            for (var k = 0; k < sortOrderMenu.options.length; k++) {
                if (sortOrderMenu.options[k].defaultSelected == true) {
                    sortOrderMenu.options[k].selected = true;
                    break;
                }
            }
            // Ensure hidden filed values and styles are updated.
            this._menuChanged(this.sortOrderMenuIds[i]);

            // Initialize tool tip.
            this._initSortOrderMenuToolTip(this.sortColumnMenuIds[i], this.sortOrderMenuIds[i]);
        }
        return true;
    },

    /**
     * This function is used to initialize sort order menus used in the
     * sort panel. When a sort column menu changes, the given sort order 
     * menu is initialized based on the the selected value of the given
     * sort column menu.
     *
     * @param {String} sortColumnMenuId The HTML element ID for the sort column menu component.
     * @param {String} sortOrderMenuId The HTML element ID for the sort order menu component.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _initSortOrderMenu: function(sortColumnMenuId, sortOrderMenuId) {
        if (sortColumnMenuId == null || sortOrderMenuId == null) {
            return false;
        }

        // Validate sort column menu IDs.
        if (this.sortColumnMenuIds == null || this.sortColumnMenuIds.length == 0) {
            return false;
        }

        // Validate sort order menu IDs.
        if (this.sortOrderMenuIds == null || this.sortOrderMenuIds.length == 0) {
            return false;
        }

        // Get sort column menu.
        var sortColumnMenu = this._getSelectElement(sortColumnMenuId);
        if (sortColumnMenu == null) {
            return false;
        }

        // Get sort order menu.
        var sortOrderMenu = this._getSelectElement(sortOrderMenuId);
        if (sortOrderMenu == null) {
            return false;
        }

        // Reset selected option.
        sortOrderMenu.selectedIndex = 0; // Default ascending.

        // Get sort column menu selected index.            
        var selectedIndex = (sortColumnMenu.selectedIndex > -1)
            ? sortColumnMenu.selectedIndex : 0; // Default to first option.

        // If the table is paginated and there are no hidden selected rows, the select
        // column cannot be sorted descending.
        if (sortColumnMenu.options[selectedIndex].value == this.selectSortMenuOptionValue
                && !this.hiddenSelectedRows && this.paginated) {
            sortOrderMenu.options[1].disabled = true;
        } else {
            sortOrderMenu.options[1].disabled = false;
        }

        // Attempt to match the selected index of the given sort column menu with the
        // default selected value from either sort column menu. If a match is found, the 
        // default selected value of the associated sort order menu is retrieved. This
        // default selected value is set as the current selection of the given sort 
        // order menu.
        for (var i = 0; i < this.sortColumnMenuIds.length; i++) {
            // Get the current sort column menu to test the default selected value.
            var currentSortColumnMenu = this._getSelectElement(
                this.sortColumnMenuIds[i]);
            if (currentSortColumnMenu == null) {
                continue;
            }

            // Find default selected value for the current sort column menu.
            var defaultSelected = null;
            for (var k = 0; k < currentSortColumnMenu.options.length; k++) {
                if (currentSortColumnMenu.options[k].defaultSelected == true) {
                    defaultSelected = currentSortColumnMenu.options[k].value;
                    break;
                }
            }

            // Match default selected value with selected index value.
            if (defaultSelected != null && defaultSelected ==
                    sortColumnMenu.options[selectedIndex].value) {
                // Get current sort order menu to test the default selected value.
                var currentSortOrderMenu = this._getSelectElement(
                    this.sortOrderMenuIds[i]);
                if (currentSortOrderMenu == null) {
                    continue;
                }

                // Find default selected index for the current sort order menu.
                var defaultSelectedIndex = -1;
                for (var c = 0; c < currentSortOrderMenu.options.length; c++) {
                    if (currentSortOrderMenu.options[c].defaultSelected == true) {
                        defaultSelectedIndex = c;
                        break;
                    }
                }

                // Set selected value for given sort order menu.
                if (defaultSelectedIndex > -1) {
                    sortOrderMenu.options[defaultSelectedIndex].selected = true;
                } else {
                    sortOrderMenu.options[0].selected = true; // Default ascending.
                }
                break;
            }
        }
        // Ensure hidden field values and styles are updated.
        this._menuChanged(sortOrderMenuId);

        // Set sort order menu tool tip.
        return this._initSortOrderMenuToolTip(sortColumnMenuId, sortOrderMenuId);
    },

    /**
     * This function is used to initialize sort order menu tool tips used in the 
     * sort panel. When a sort column menu changes, the given sort order 
     * menu tool tip is initialized based on the the selected value of the given
     * sort column menu.
     *
     * @param {String} sortColumnMenuId The HTML element ID for the sort column menu component.
     * @param {String} sortOrderMenuId The HTML element ID for the sort order menu component.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _initSortOrderMenuToolTip: function(sortColumnMenuId, sortOrderMenuId) {
        if (sortColumnMenuId == null || sortOrderMenuId == null) {
            return false;
        }

        // Get sort column menu.
        var sortColumnMenu = this._getSelectElement(sortColumnMenuId);
        if (sortColumnMenu == null) {
            return false;
        }

        // Get sort order menu.
        var sortOrderMenu = this._getSelectElement(sortOrderMenuId);
        if (sortOrderMenu == null) {
            return false;
        }

        // Get tool tip associated with given sort order menu.
        var toolTip = "";
        if (this.sortOrderToolTips != null && this.sortOrderToolTips.length != 0
                && this.sortOrderMenuIds != null) {
            for (var i = 0; i < this.sortOrderMenuIds.length; i++) {
                // The tool tip is at index zero, after splitting the message.
                if (sortOrderMenuId == this.sortOrderMenuIds[i]) {
                    toolTip = this.sortOrderToolTips[i].split("{0}")[0];
                    break;
                }
            }
        }

        // Get sort column menu selected index.            
        var selectedIndex = (sortColumnMenu.selectedIndex > -1)
            ? sortColumnMenu.selectedIndex : 0; // Default to first option.

        // Set tool tip.
        if (sortOrderMenu.options[sortOrderMenu.selectedIndex].value == "true") {
            sortOrderMenu.title = toolTip + this.sortOrderToolTipsDescending[selectedIndex];
        } else {
            // Default ascending.
            sortOrderMenu.title = toolTip + this.sortOrderToolTipsAscending[selectedIndex];
        }
        return true;
    },

    /**
     * This function is used to initialize the tertiary sort order menus used in the 
     * table sort panel.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _initTertiarySortOrderMenu: function() {
        return this._initSortOrderMenu(this.sortColumnMenuIds[this.TERTIARY], 
            this.sortOrderMenuIds[this.TERTIARY]);
    },

    /**
     * This function is used to initialize the tertiary sort order menu tool tips
     * used in the table sort panel.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _initTertiarySortOrderMenuToolTip: function() {
        // Get sort order menu.
        var sortOrderMenu = this._getSelectElement(
            this.sortOrderMenuIds[this.TERTIARY]);
        if (sortOrderMenu != null) {
            // IE hack so disabled option is not selected -- bugtraq #6269683.
            if (sortOrderMenu.options[sortOrderMenu.selectedIndex].disabled == true) {
                sortOrderMenu.selectedIndex = 0;
            }
        }
        return this._initSortOrderMenuToolTip(this.sortColumnMenuIds[this.TERTIARY], 
            this.sortOrderMenuIds[this.TERTIARY]);
    },

    /**
     * This function is invoked by the choice onselect action to set the
     * selected, and disabled styles.
     *
     * @param {String} elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return {boolean} true if successful; otherwise, false.
     */
    _menuChanged: function(elementId) {         
        var widget = @JS_NS@.widget.common.getWidget(elementId);
        if (widget) {
            return widget._changed();
        }
        return false;
    },

    /**
     * This function is used to reset filter drop down menu. This functionality 
     * requires the filterId of the table component to be set. In addition,
     * the selected value must be set as well to restore the default selected
     * value when the embedded filter panel is closed.
     *
     * @param {String} filterId The HTML element ID of the filter menu.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _resetFilterMenu: function(filterId) {
        if (filterId == null) {
            return false;
        }

        // Get filter menu.
        var menu = this._getSelectElement(filterId);
        if (menu == null) {
            return true;
        }

        // Get div element associated with the filter panel.
        var div = document.getElementById(this.panelIds[this.FILTER]);
        if (div == null) {
            return false;
        }

        // Set selected style.
        if (@JS_NS@._base.common._isVisibleElement(div)) {
            @JS_NS@._base.common._stripStyleClass(menu, this.basicFilterStyleClass);
            @JS_NS@._base.common._addStyleClass(menu, this.customFilterStyleClass);
        } else {
            // Reset default selected option.
            menu.selectedIndex = 0;
            for (var i = 0; i < menu.options.length; i++) {
                if (menu.options[i].defaultSelected == true) {
                    menu.options[i].selected = true;
                    break;
                }
            }
            @JS_NS@._base.common._stripStyleClass(menu, this.customFilterStyleClass);
            @JS_NS@._base.common._addStyleClass(menu, this.basicFilterStyleClass);
        }
        return true;
    },

    /**
     * This function is used to set the selected state components in all row groups
     * displayed in the table (i.e., checkboxes or radiobuttons used to de/select
     * rows of the table). This functionality requires the selectId property of
     * the tableColumn component to be set.
     *
     * @param {boolean} selected Flag indicating components should be selected.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _selectAllRows: function(selected) {
        // Validate groupIDs.
        if (this.groupIds == null || this.groupIds.length == 0) {
            return false;
        }

        // For each group, get the row and select id.
        for (var i = 0; i < this.groupIds.length; i++) {
            this._selectGroupRows(this.groupIds[i], selected);
        }
        return true;
    },

    /**
     * This function is used to set the selected state components for the given row group
     * (i.e., checkboxes or radiobuttons used to de/select rows of the table). This 
     * functionality requires the selectId property of the tableColumn component to be
     * set.
     *
     * @param {String} groupId The HTML element ID for the tableRowGroup component.
     * @param {boolean} selected Flag indicating components should be selected.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _selectGroupRows: function(groupId, selected) {
        // Validate groupIDs.
        if (this.groupIds == null || this.groupIds.length == 0
                || groupId == null) {
            return false;
        }

        // Get selectId and rowIds array associated with groupId.
        var selectId = null;
        var rowIds = null;
        for (var i = 0; i < this.groupIds.length; i++) {
            if (groupId == this.groupIds[i]) {
                selectId = this.selectIds[i];
                rowIds = this.rowIds[i];
                break;
            }
        }

        // If selectId or rowIds could not be found, do not continue.
        if (selectId == null || rowIds == null) {
            return false;
        }

        // Update the select component for each row.
        for (var k = 0; k < rowIds.length; k++) {
            var select = document.getElementById(
                this.groupIds[i] + this.SEPARATOR + rowIds[k] + this.SEPARATOR + selectId);
            if (select == null || select.getProps().disabled == true) {
                continue;
            }
            select.setProps({checked: new Boolean(selected).valueOf()});
        }
        return this._initGroupRows(groupId); // Set row highlighting.
    },

    /**
     * This function is used to toggle row group panels open or closed.
     *
     * @param {String} groupId The HTML element ID for the tableRowGroup component.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _toggleGroupPanel: function(groupId) {
        // Validate groupIDs.
        if (this.groupIds == null || this.groupIds.length == 0
                || groupId == null) {
            return false;
        }

        // Get rowIds array associated with groupId.
        var rowIds = null;
        for (var c = 0; c < this.groupIds.length; c++) {
            if (groupId == this.groupIds[c]) {
                rowIds = this.rowIds[c];
                break;
            }
        }

        // If row IDs could not be found, do not continue.
        if (rowIds == null) {
            return false;
        }

        // Get flag indicating group is collapsed.
        var prefix = groupId + this.SEPARATOR;
        var collapsed = !@JS_NS@._base.common._isVisible(prefix + rowIds[0]);

        // Get the number of column headers and table column footers for all 
        // TableRowGroup children.
        var _prefix;
        var columnHeaderId;
        var tableColumnFooterId;
        var columnHeadersCount = 0;
        var tableColumnFootersCount = 0;
        for (var i = 0; i < this.groupIds.length; i++) {
            // Only need to test first nested column header/footer; thus, index 0 is used.        
            _prefix = this.groupIds[i] + this.SEPARATOR;
            columnHeaderId = _prefix + this.columnHeaderId + this.SEPARATOR + "0";
            tableColumnFooterId = _prefix + this.tableColumnFooterId + this.SEPARATOR + "0";
            if (document.getElementById(columnHeaderId) != null) {
                columnHeadersCount++;
            }
            if (document.getElementById(tableColumnFooterId) != null) {
                tableColumnFootersCount++;
            }
        }

        // Toggle nested column footer.
        var rowIndex = 0;
        var columnFooterId;
        while (true) {
            columnFooterId = prefix + this.columnFooterId + 
                this.SEPARATOR + rowIndex++;
            if (document.getElementById(columnFooterId) == null) {
                break;
            }
            @JS_NS@._base.common._setVisible(columnFooterId, collapsed);
        }

        // Toggle column header only if multiple column headers are shown.
        if (columnHeadersCount > 1) {
            rowIndex = 0;
            while (true) {
                columnHeaderId = prefix + this.columnHeaderId + 
                    this.SEPARATOR + rowIndex++;
                if (document.getElementById(columnHeaderId) == null) {
                    break;
                }            
                @JS_NS@._base.common._setVisible(columnHeaderId, collapsed);
            }
        }

        // Toggle table column footer only if multiple column footers are shown.
        if (tableColumnFootersCount > 1) {
            rowIndex = 0;
            while (true) {
                tableColumnFooterId = prefix + this.tableColumnFooterId + 
                    this.SEPARATOR + rowIndex++;
                if (document.getElementById(tableColumnFooterId) == null) {
                    break;
                }
                @JS_NS@._base.common._setVisible(tableColumnFooterId, collapsed);
            }
        }

        // Toggle group rows.
        var rowId;
        for (var k = 0; k < rowIds.length; k++) {
            rowId = prefix + rowIds[k];
            @JS_NS@._base.common._setVisible(rowId, collapsed);
        }

        // Toggle group footers.
        @JS_NS@._base.common._setVisible(prefix + this.groupFooterId, collapsed);

        // Set next toggle button image.
        var groupPanelToggleButtonId = prefix + this.groupPanelToggleButtonId;
        var imgHyperlink = document.getElementById(groupPanelToggleButtonId);
        if (imgHyperlink != null) {
            if (collapsed) {
                imgHyperlink.setProps({
                    enabledImage: {
                        src: this.groupPanelToggleIconOpen  
                    },
                    title: this.groupPanelToggleButtonToolTipOpen
                });
            } else {
                imgHyperlink.setProps({
                    enabledImage: {
                        src: this.groupPanelToggleIconClose
                    },
                    title: this.groupPanelToggleButtonToolTipClose
                });
            }
        }

        // Set collapsed hidden field.
        var hiddenField = document.getElementById(prefix + this.collapsedHiddenFieldId);
        if (hiddenField != null) {
            hiddenField.value = !collapsed;
        }
        return this._initGroupRows(groupId); // Set next warning image.
    },

    /**
     * This function is used to toggle embedded panels.
     *
     * @param {String} panelId The panel ID to toggle.
     * @param {String} panelFocusIdOpen The ID used to set focus when panel is opened.
     * @param {String} panelFocusIdClose The ID used to set focus when panel is closed.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _togglePanel: function(panelId, panelFocusIdOpen, panelFocusIdClose) {
        if (panelId == null) {
            return false;
        }

        // Toggle the given panel, hide all others.
        for (var i = 0; i < this.panelIds.length; i++) {
            // Get div element associated with the panel.
            var div = document.getElementById(this.panelIds[i]);
            if (div == null) {
                continue;
            }

            // Set display value. Alternatively, we could set div.style.display
            // equal to "none" or "block" (i.e., hide/show).
            if (this.panelIds[i] == panelId) {
                // Set focus when panel is toggled -- bugtraq 6316565.
                var focusElement = null;

                if (@JS_NS@._base.common._isVisibleElement(div)) {
                    @JS_NS@._base.common._setVisibleElement(div, false); // Hide panel.
                    focusElement = document.getElementById(panelFocusIdClose);
                } else {
                    @JS_NS@._base.common._setVisibleElement(div, true); // Show panel.
                    focusElement = document.getElementById(panelFocusIdOpen);
                }

                // Set focus.
                if (focusElement != null) {
                    focusElement.focus();
                }
            } else {
                // Panels are hidden by default.
                @JS_NS@._base.common._setVisibleElement(div, false);
            }

            // Get image from icon hyperlink component.
            var imgHyperlink = document.getElementById(this.panelToggleIds[i]);
            if (imgHyperlink == null || imgHyperlink.getProps().enabledImage == null) {
                continue; // Filter panel facet may be in use.
            }

            // Set image.
            if (@JS_NS@._base.common._isVisibleElement(div)) {
                imgHyperlink.setProps({
                    enabledImage: {
                        src: this.panelToggleIconsOpen[i]
                    }
                });
            } else {
                imgHyperlink.setProps({
                    enabledImage: {
                        src: this.panelToggleIconsClose[i]
                    }
                });
            }
        }
        return true;
    },

    /**
     * This function is used to toggle the sort panel open or closed. This
     * functionality requires the filterId of the table component to be set.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _toggleSortPanel: function() {
        // Validate panel IDs.
        if (this.panelIds == null || this.panelIds.length == 0) {
            return false;
        }

        // Initialize sort column and order menus.
        this._initSortColumnMenus(); 
        this._initSortOrderMenus();

        // Toggle sort panel.
        this._togglePanel(this.panelIds[this.SORT], 
        this.panelFocusIds[this.SORT], this.panelToggleIds[this.SORT]);
        return this._resetFilterMenu(this.panelToggleIds[this.FILTER]); // Reset filter menu.
    },

    /**
     * This function is used to validate sort column menu selections 
     * for the sort panel.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _validateSortPanel: function() {
        // Validate sort column menu IDs.
        if (this.sortColumnMenuIds == null || this.sortColumnMenuIds.length == 0) {
            return false;
        }

        // Get sort column menus.
        var primarySortColumnMenu = this._getSelectElement(
            this.sortColumnMenuIds[this.PRIMARY]);
        var secondarySortColumnMenu = this._getSelectElement(
            this.sortColumnMenuIds[this.SECONDARY]);
        var tertiarySortColumnMenu = this._getSelectElement(
            this.sortColumnMenuIds[this.TERTIARY]);

        // Test primary and secondary menu selections.
        if (primarySortColumnMenu != null && secondarySortColumnMenu != null) {
            // Test if secondary sort is set, but primary is not.
            if (primarySortColumnMenu.selectedIndex < 1 
                    && secondarySortColumnMenu.selectedIndex > 0) {
                alert(this.missingSelectionMsg);
                return false;
            }
            // If a selection has been made, test for duplicate.
            if (primarySortColumnMenu.selectedIndex > 0
                  && primarySortColumnMenu.selectedIndex == secondarySortColumnMenu.selectedIndex) {
                alert(this.duplicateSelectionMsg);
                return false;
            }
        }

        // Test primary and tertiary menu selections.
        if (primarySortColumnMenu != null && tertiarySortColumnMenu != null) {
            // Test if tertiary sort is set, but primary is not.
            if (primarySortColumnMenu.selectedIndex < 1 
                    && tertiarySortColumnMenu.selectedIndex > 0) {
                alert(this.missingSelectionMsg);
                return false;
            }
            // If a selection has been made, test for duplicate.
            if (primarySortColumnMenu.selectedIndex > 0
                    && primarySortColumnMenu.selectedIndex == tertiarySortColumnMenu.selectedIndex) {
                alert(this.duplicateSelectionMsg);
                return false;
            }
        }

        // Test secondary and tertiary menu selections.
        if (secondarySortColumnMenu != null && tertiarySortColumnMenu != null) {
            // Test if tertiary sort is set, but secondary is not.
            if (secondarySortColumnMenu.selectedIndex < 1 
                    && tertiarySortColumnMenu.selectedIndex > 0) {
                alert(this.missingSelectionMsg);
                return false;
            }
            // If a selection has been made, test for duplicate.
            if (secondarySortColumnMenu.selectedIndex > 0
                    && secondarySortColumnMenu.selectedIndex == tertiarySortColumnMenu.selectedIndex) {
                alert(this.duplicateSelectionMsg);
                return false;
            }
        }
        return true;
    }
};
