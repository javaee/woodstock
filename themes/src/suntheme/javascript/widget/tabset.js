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

@JS_NS@._dojo.provide("@JS_NS@.widget.tabset");

@JS_NS@._dojo.require("@JS_NS@.widget.tab");
@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.stateBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.submitBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");

/**
 * This function is used to construct a tabset widget.
 *
 * @constructor
 * @name @JS_NS@.widget.tabset
 * @extends @JS_NS@.widget._base.widgetBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @extends @JS_NS@.widget._base.stateBase
 * @extends @JS_NS@.widget._base.submitBase
 * @class This class contains functions for the tabset widget.
 * <p>
 * The tabset widget contains a collection of tabs hierarchically arranged to show
 * parent-child relationships in multiple independently-scrollable rows.  It also contains
 * a content area which can contain any number of widgets or HTML markup associated with
 * the selected tab.
 * </p><p>
 * Only one tab in a tabset is considered selected and whose content is rendered in the
 * content area - the selected tab in the bottom row that is visible.
 * </p>
 * <h3>Example 1: TBD</h3>
 * <p>This example shows ...
 * </p><pre><code>
 *</pre></code>
 * <h3>Example 2: TBD</h3>
 * <p>This example shows ...
 * </p><pre><code>
 *</pre></code>
 * <h3>Example N: Subscribing to event topics</h3>
 * <p>This example shows ...
 * </p><pre><code>
 *</pre></code>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {Array} contents array of tab objects to be rendered in form of 
 *        Key-Value pairs of properties.  See 
 *        <a href="@JS_NS@.widget.tab.html#@JS_NS@.widget.tab">@JS_NS@.widget.tab</a>
 *        for details on these properties.
 * @config {boolean} equalTabWidth  true means to set all tabs in a row to the same width.
 *        false means to allow tabs to have independent widths and to truncate tab titles
 *        as needed to fit as many tabs as possible in the available space.  If there is
 *        insufficient space, then scrolling is activated and equalTabWidth=true is enforced.
 * @config {boolean} loadOnSelect  true means to load content for a tab when it is first selected.
 *        After tabs are loaded they are not reloaded until the container is refreshed or the user 
 *        performs some action on the tab in question.
 * @config {String} contentHeight the height of the contents container.  The should be specified
 *        in any of the standard CSS forms (for example, "100px") for setting height.
 * @config {String} style CSS style or styles to be applied to the outermost 
 *        HTML element when this component is rendered.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.tabset", [
        @JS_NS@.widget._base.refreshBase, 
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.submitBase,
        @JS_NS@.widget._base.widgetBase ], {
    // Set defaults.
    constructor: function() {
        // Set defaults for public properties that can be modified.
        this.contents = null;
        this.equalTabWidth = false;
        this.loadOnSelect = true;
        this.contentHeight = null;

        // Set defaults for private data used internally by the widget.
        this._tabLevels = new Object();
        this._tabsetRows = new Object();
        this._tabContents = new Object();
        this._visibleRows = new Array();
        this._textsizeMonitorID = null;
        this._tabContentID = null;

        this._spinLock = {
            lockOn: false,
    
            lock: function() {
                if (this.lockOn) {
                    return setTimeout("this.lock", 100);
                }
                this.lockOn = true;
            },
    
            isLocked: function() { return this.lockOn; },
            unlock: function() {this.lockOn = false;}
        };
    },

    _widgetType: "tabset" // Required for theme properties.
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
@JS_NS@.widget.tabset.event =
        @JS_NS@.widget.tabset.prototype.event = {
    /** 
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /**
         * Refresh event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li><li>
         * {String} execute Comma separated list of IDs to be processed server
         * side along with this widget.
         * </li></ul>
         *
         * @id @JS_NS@.widget.tabset.event.refresh.beginTopic
         * @property {String} beginTopic
         */
        beginTopic: "@JS_NS@_widget_tabset_event_refresh_begin",

        /**
         * Refresh event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li><li>
         * Please see the constructor detail for additional properties.
         * </li></ul>
         */
        endTopic: "@JS_NS@_widget_tabset_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /**
         * State event topic for custom AJAX implementations to listen for. 
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li><li>
         * {Object} props Key-Value pairs of widget properties being updated.
         * </li></ul>
         *
         * @id @JS_NS@.widget.tab.event.state.beginTopic
         * @property {String} beginTopic
         */
        beginTopic: "@JS_NS@_widget_tabset_event_state_begin",

        /**
         * State event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li></ul>
         *
         * @id @JS_NS@.widget.tab.event.state.endTopic
         * @property {String} endTopic
         */
        endTopic: "@JS_NS@_widget_tabset_event_state_end"
    },

    /**
     * Select event topic for custom AJAX implementations to listen for.
     * Key-Value pairs of properties to publish include:
     * <ul><li>
     * {String} id The widget ID to process the event for.
     * </li></ul>
     *
     * @id @JS_NS@.widget.tabset.event.selectedTopic
     * @property {String} selectedTopic
     */
    selectedTopic: "@JS_NS@_widget_tabset_event_selected"
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
@JS_NS@.widget.tabset.prototype.setProps = function(props, notify) {

    if (props == null)
        return false;

    if (props.contents != null)
       this.contents = props.contents;
    if (props.equalTabWidth != null)
       this.equalTabWidth = props.equalTabWidth;
    if (props.loadOnSelect != null)
       this.loadOnSelect = props.loadOnSelect;
    if (props.contentHeight != null)
       this.contentHeight = props.contentHeight;

    return this._inherited("setProps", arguments);

}; // setProps

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.tabset.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    if (this.contents != null)
        props.contents = this.contents;
    if (this.equalTabWidth != null)
        props.equalTabWidth = this.equalTabWidth;
    if (this.loadOnSelect != null)
        props.loadOnSelect = this.loadOnSelect;
    if (this.contentHeight != null)
        props.contentHeight = this.contentHeight;

    return props;

}; // getProps

/**
 * Convenience function for create scroll button.
 *
 * @param {boolean} enabled  true if button is enabled, false if disabled
 * @param {String} imageKey  the theme key for the scroll button image
 * @param {Object} row the tabset row object the scroll button is contained in
 * @param {int} code indicates the scroll direction the button will perform:
 *              -1 = scroll left, 1 = scroll right,
 *              any negative value but -1 = scroll far left
 *              any positive value but 1 = scroll far right
 *              default is 0 and does not do anything
 * @return {Node} The node for the scroll button which can later be added to the DOM.
 * @private
 */
@JS_NS@.widget.tabset.prototype._createScrollButton = function(enabled, imageKey, row, code) {

    code = (code != null) ? code : 0;

    var scrollBtnContainer = this._scrollBtnContainer.cloneNode(false);
    scrollBtnContainer.className = this._theme.getClassName("DTAB_SCROLLDIV");
    if (enabled)
        this._common._addStyleClass(scrollBtnContainer, this._theme.getClassName("DTAB_ENABLED"));
    scrollBtnContainer.id = this.id + "_scrollBtnContainer" + row.level + imageKey;

    var scrollTxtNode = this._scrollTxtNode.cloneNode(false);
    scrollTxtNode.id = this.id + "_scrollTxtNode" + row.level + imageKey;
    scrollTxtNode.innerHTML = "&nbsp;";

    var scrollIconContainer = this._scrollIconContainer.cloneNode(false);
    scrollIconContainer.className = this._theme.getClassName("DTAB_SCROLLBTN");
    scrollIconContainer.id = this.id + "_scrollIconContainer" + row.level + imageKey;

    var scrollIconNode = this._scrollIconNode.cloneNode(false);
    scrollIconNode.id = this.id + "_scrollIconNode" + row.level + imageKey;
    var image = this._theme.getImage(imageKey);
    scrollIconNode.src = image.src;
    scrollIconNode.width = image.width;
    scrollIconNode.height = image.height;

    scrollIconContainer.appendChild(scrollIconNode);
    scrollBtnContainer.appendChild(scrollTxtNode);
    scrollBtnContainer.appendChild(scrollIconContainer);

    this._dojo.connect(scrollBtnContainer, "onclick", this._createScrollOnClickCallback(row, code));

    return scrollBtnContainer;

}; // _createScrollButton

/**
 * Remove all scroll buttons from the specified tabset row.
 *
 * @param {Object} row  the tabset row to remove the scrollers from.
 * @private
 */
@JS_NS@.widget.tabset.prototype._removeScrollButtons = function(row) {

    // Eat exception for any scroller which was not added to DOM.
    try { row.tabsContainer.removeChild(row.scrollFarLeftEnabledBtn); } catch (e) { /* do nothing */ }
    try { row.tabsContainer.removeChild(row.scrollFarLeftDisabledBtn); } catch (e) { /* do nothing */ }
    try { row.tabsContainer.removeChild(row.scrollLeftEnabledBtn); } catch (e) { /* do nothing */ }
    try { row.tabsContainer.removeChild(row.scrollLeftDisabledBtn); } catch (e) { /* do nothing */ }
    try { row.tabsContainer.removeChild(row.scrollRightEnabledBtn); } catch (e) { /* do nothing */ }
    try { row.tabsContainer.removeChild(row.scrollRightDisabledBtn); } catch (e) { /* do nothing */ }
    try { row.tabsContainer.removeChild(row.scrollFarRightEnabledBtn); } catch (e) { /* do nothing */ }
    try { row.tabsContainer.removeChild(row.scrollFarRightDisabledBtn); } catch (e) { /* do nothing */ }

}; // _removeScrollButtons


/**
 * Utility function for getting elements by class.
 *
 * @param {String} searchClass  className to search for
 * @param {Object} node   the element in the DOM tree to begin the search.
 *                        If null, then search the whole document.
 * @param {String} tag    filter of tag names to search;  if null search
 *                        all elements.
 * @return {Array} array of elements matching specified class
 * @private
 */
@JS_NS@.widget.tabset.prototype._getElementsByClass = function(searchClass, node, tag) {

    var classElements = new Array();
    if (node == null)
        node = document;
    if (tag == null)
        tag = '*';
    var els = node.getElementsByTagName(tag);
    var pattern = new RegExp("(^|\\s)"+searchClass+"(\\s|$)");
    for (var i = 0, j = 0; i < els.length; i++) {
        if (pattern.test(els[i].className) ) {
            classElements[j] = els[i];
            j++;
        }
    }
    return classElements;

}; // getElementsByClass

/**
 * Return the object representing a tabset row child of a given widget with the
 * specified ID.  If the row does not exist, assign it the specified level.
 *
 * @param {String} parentID  the id of the widget whose child tabset row should be returned
 * @param {int} level  the tabset level to be assigned to the row, if it does not already exist
 * @return {Object}  the tabset row object
 * @private
 */
@JS_NS@.widget.tabset.prototype._getTabsetRow = function(parentID, level) {

    if (this._tabsetRows[parentID] == null) {
        // Clone tabs container node and append as child to toplevel tabset node
        var tabsContainer = this._tabsContainer.cloneNode(false);
        tabsContainer.className = this._theme.getClassName("DTABSET_ROW");
        tabsContainer.id = this.id + "_tabsContainer" + level;
        
        // Create object for this tabset row for this level
        var row = new Object();
        row.tabsContainer = tabsContainer;
        row.lastNode = null;
        row.numTabs = 0;
        row.scroll = false;
        row.scrollFar = false;
        row.visible = false;
        row.neededWidth = 0;
        row.availableWidth = 0;
        row.widestTab = -1;
	row.originalWidestTab = -1;
	row.firstViewableTab = 0;
        row.lastViewableTab = 0;
        row.level = level;
        row.selectedID = null;
        row.origTitles = new Object();

        row.spinLock = {
            lockOn: false,

            lock: function() {
                if (this.lockOn) {
                    return setTimeout("this.lock", 100);
                }
                this.lockOn = true;
            },

            isLocked: function() { return this.lockOn; },

            unlock: function() {
                this.lockOn = false;
            }
        };

        row.selectionStack = {
            stack: new Array(),

            push: function(id) {
                // Only push if not already at top of stack
                var top = this.stack.pop();
                if (top != null)
                    this.stack.push(top);  // push it back
                if (top != id) {
                    this.stack.push(id);
                }
            },

            pop: function() {
                return this.stack.pop();
            }
        };

        // Create scroll controls for this tabset row
        row.scrollFarLeftEnabledBtn = 
            this._createScrollButton(true, "DTAB_SCROLL_FAR_LEFT_ENABLED", row, -99);
        row.scrollFarLeftDisabledBtn = 
            this._createScrollButton(false, "DTAB_SCROLL_FAR_LEFT_DISABLED", row);
        row.scrollLeftEnabledBtn = 
            this._createScrollButton(true, "DTAB_SCROLL_LEFT_ENABLED", row, -1);
        row.scrollLeftDisabledBtn = 
            this._createScrollButton(false, "DTAB_SCROLL_LEFT_DISABLED", row);
        row.scrollRightEnabledBtn = 
            this._createScrollButton(true, "DTAB_SCROLL_RIGHT_ENABLED", row, 1);
        row.scrollRightDisabledBtn = 
            this._createScrollButton(false, "DTAB_SCROLL_RIGHT_DISABLED", row);
        row.scrollFarRightEnabledBtn = 
            this._createScrollButton(true, "DTAB_SCROLL_FAR_RIGHT_ENABLED", row, 99);
        row.scrollFarRightDisabledBtn = 
            this._createScrollButton(false, "DTAB_SCROLL_FAR_RIGHT_DISABLED", row);

        this._tabsetRows[parentID] = row;
    }

    return this._tabsetRows[parentID];

}; // getTabsetRow

/**
 * Return the index of the selected tab in the tabs container for the specified row.
 *
 * @param {Object} row  the tabset row object
 * @param {int} the selected tab index, or -1 if no tab is selected.
 * @private
 */
@JS_NS@.widget.tabset.prototype._getSelectedIndex = function(row) {

    for (var i = 0; (row.selectedID != null) && (i < row.tabsContainer.childNodes.length); i++) {
        var tabNode = row.tabsContainer.childNodes[i];
        if (tabNode.id == row.selectedID) {
            return i;
        }
    }
    return -1;

}; // _getSelectedIndex

/**
 * Convenience function to compute the total available width of a given
 * tabset row.  This is typically used as the initial value to seed
 * algorithms for determining available space to render the tabs.  It
 * does represent the final available width.
 *
 * @param {Object} row  the tabset row object
 * @return {int} the available width
 * @private
 */
@JS_NS@.widget.tabset.prototype._computeAvailableWidth = function(row) {

    var availableWidth = row.tabsContainer.offsetWidth;
    availableWidth -= parseInt(this._theme.getMessage("dtab.tabsetPadLeft"))
    availableWidth -= parseInt(this._theme.getMessage("dtab.tabsetPadRight"))
    availableWidth -= 2;  // left/right-most borders of left/right-most tab or scroll button

    // Must NEVER be less than zero which would wreak havoc with sizing algoritms.
    // A zero will at least cancel the algorithms.
    if (availableWidth <= 0)
        availableWidth = 0;
    return availableWidth;

}; // _computeAvailableWIdth

/**
 * Attempt to ensure all the tabs for a given tabset row fit in the available space.  The tab
 * with the longest width is reduced first.  This process continues for each tab until the
 * resizing for any tab allows tabset row to fit in the available space, or all the tabs
 * have been resized to the minimum width.
 *
 * @param {Object} row  the tabset row object
 * @return {boolean} true if the tabs fit in the available width, false if not
 * @private
 */
@JS_NS@.widget.tabset.prototype._fitTabs = function(row) {

    // Walk thru all tabs in this tabset row and determine the tab with the widest width.
    // Any tabs that may have previous been hidden due to scrolling must be made visible
    // so it's offsetWidth can be obtained.  Also maintain a running sum of all tab widths
    // so that we know the total required width of all tabs.
    //
    var neededWidth = 0;
    var tabWidths = new Array();
    var N = -1; // index of widest child tab
    for (var i = 0; i < row.tabsContainer.childNodes.length; i++) {
        if (i == 0)
            N = 0;
        var tabNode = row.tabsContainer.childNodes[i];
        this._common._stripStyleClass(tabNode, this._theme.getClassName("HIDDEN"));

        // Restore tab to original untruncated, unexpanded state.
        if (row.origTitles[tabNode.id] != null) {
            var tabWidget = this._widget.getWidget(tabNode.id);
            tabWidget.setProps({"title": row.origTitles[tabNode.id]});
        }
        var els = this._getElementsByClass(this._theme.getClassName("DTAB_TITLE"), tabNode, null);
        if ((els == null) || (els.length == 0))
            continue;
        els[0].style.paddingLeft = null;
        els[0].style.paddingRight = null;

        if (tabNode.offsetWidth > row.tabsContainer.childNodes[N].offsetWidth)
            N = i;
        neededWidth += tabNode.offsetWidth;
        tabWidths[i] = new Object();
        tabWidths[i].width = tabNode.offsetWidth;
        tabWidths[i].tested = false;
    }

    // Get initial available to render the tabs.
    var availableWidth = this._computeAvailableWidth(row);

    // Snapshot before attempting auto-resizing
    row.widestTab = N;
    row.originalWidestTab = N;
    row.neededWidth = neededWidth;
    row.availableWidth = availableWidth;

   // No need to truncate if forcing equal tab width.
   if (this.equalTabWidth == true)
       return false;

    // Reduce the width of each tab until they all fit in the tabset available width.
    while (neededWidth > availableWidth) {
        // Determine how much is needed over what's available 
        var overage = neededWidth - availableWidth;

        // Propose to shrink widest tab by this overage amount.
        var proposedWidth = tabWidths[N].width - overage;

        // Get domNode and associated widget for widest tab
        var tabNode = row.tabsContainer.childNodes[N];
        var tabWidget = this._widget.getWidget(tabNode.id);

        // Starting with one character beyond the minimum amount a title can be
        // truncated to, truncate the title and see if it will fit, along with
        // appropriate ellipsis to indicate truncation, in the proposed width 
        // for the tab.  We do a "live" fit test - that is, set the truncated
        // title on the tab and compare the resultant offsetWidth with the
        // proposed width.  This is repeated until the offsetWidth exceeds the
        // proposed width, at which point we back off one character and set
        // the title to resulting text.
        //
        var ellipsis = this._theme.getMessage("dtab.ellipsis");
        var title = tabWidget.getProps().title;
        var minChars = parseInt(this._theme.getMessage("dtab.titleTruncationThreshold"));
        var adjusted = false;
        for (var k = minChars + 1; k < title.length; k++) {
            var s = title.substr(0, k) + ellipsis;
            tabWidget.setProps({"title": s});
            if (tabNode.offsetWidth > proposedWidth) {
                // Too big, but one less character will fit
                title = title.substr(0, k-1) + ellipsis;
                adjusted = true;
                break;
            }
        }
        tabWidths[N].tested = true;

        // Adjust needed width by the original tab width
        neededWidth -= tabWidths[N].width;

        // Set final tab title - may or may not have been truncated.
        tabWidget.setProps({"title": title});

        // Updates to account for final tab width
        tabWidths[N].width = tabNode.offsetWidth;
        neededWidth += tabWidths[N].width;

        // If tab was adjusted, then that adjustment is all that is necessary
        // so that all tabs will fit and in the available space.
        if (adjusted == true)
            break;

        // Find next widest tab, exclusive of any tab whose width has already 
        // been tested truncation.
        N = -1;
        for (var i = 0; i < tabWidths.length; i++) {
            if (tabWidths[i].tested == true)
                continue;
            if (N < 0)
                N = i;
            if (tabWidths[i].width > tabWidths[N].width)
                N = i;
        }

        // If remaining tabs are same width, stop.
        if (N < 0)
            break;
    }

    // Snapshot final values
    row.widestTab = N;
    row.neededWidth = neededWidth;
    row.availableWidth = availableWidth;

    if (row.neededWidth > row.availableWidth)
        return false;

    return true;

}; // _fitTabs

/**
 * Expand all tabs in a given tabset row to be the same width as the widest tab.
 *
 * @param {Object} row  the tabset row object
 * @private
 */
@JS_NS@.widget.tabset.prototype._equalTabs = function(row) {

    // Restore row info and each tab to it's original, pre-truncated state.  This includes
    // the original tab titles, and this MUST be done here **before** attempting to
    // equalize the tab widths in the loop further below.
    row.widestTab = Math.max(0, row.originalWidestTab);
    row.neededWidth = 0;
    for (var i = 0; i < row.tabsContainer.childNodes.length; i++) {
        var tabNode = row.tabsContainer.childNodes[i];
        if (row.origTitles[tabNode.id] != null) {
            var tabWidget = this._widget.getWidget(tabNode.id);
            tabWidget.setProps({"title": row.origTitles[tabNode.id]});
        }
    }

    var widestTabWidth = row.tabsContainer.childNodes[row.widestTab].offsetWidth;

    // To effect an equal width for each tab, we compute the "shortage" 
    // relative to the widest tab and spread that shortage equally among
    // the left/right padding of the tab title.  This is a much safer approach
    // that works cross-browser than trying to manipulate the tab width.
    // Since a tab's width could be adjusted many times until all tabs
    // have been added, this means we could be changing the style padding
    // on the container for the tab title many times.
    // Note that we have no choice but to assume pixel units, and since
    // there is no way for a developer to access the title via the
    // tab widget interface, this should be a safe assumption.
    //
    for (var i = 0; i < row.tabsContainer.childNodes.length; i++) {
        var tabNode = row.tabsContainer.childNodes[i];

        // Compute shortage relative to minimum required width
        var shortage = widestTabWidth - tabNode.offsetWidth;

        // Find container for tab title.  There should be only ONE!!
        var els = this._getElementsByClass(this._theme.getClassName("DTAB_TITLE"), tabNode, null);
        if ((els == null) || (els.length == 0))
            continue;

        // Get the current left/right padding
        var paddingLeft = els[0].style.paddingLeft;
        var paddingRight = els[0].style.paddingRight;

        // A short title will have had extra padding applied via an extra class assigned
        var classNames = this._common._splitStyleClasses(els[0]);
        var isShortTitle = this._common._checkStyleClasses(classNames, this._theme.getClassName("DTAB_PAD"));
        var padLeftKey = "dtab.titlePadLeft";
        var padRightKey = "dtab.titlePadRight";
        if (isShortTitle) {
            padLeftKey += "Short";
            padRightKey += "Short";
        }

        // If no left/right padding, then set defaults
        if ((paddingLeft == null) || (paddingLeft == ""))
            paddingLeft = parseInt(this._theme.getMessage(padLeftKey));
        if ((paddingRight == null) || (paddingRight == ""))
            paddingRight = parseInt(this._theme.getMessage(padRightKey));
  
        // Spread shortage among left/right padding.
        paddingLeft = parseInt(paddingLeft) + Math.floor(shortage/2);
        paddingRight = parseInt(paddingRight) + Math.floor(shortage/2);
  
        // Set the new padding style.
        els[0].style.paddingLeft = paddingLeft + "px";
        els[0].style.paddingRight = paddingRight + "px";

        row.neededWidth += tabNode.offsetWidth;
   }

   row.availableWidth = this._computeAvailableWidth(row);

}; // _equalTabs

/**
 * Add a tab to this tabset.
 *
 * @param {Object} props Key-Value pairs of tab properties.  See
 * <a href="@JS_NS@.widget.tab.html#@JS_NS@.widget.tab">@JS_NS@.widget.tab</a>
 * for a list of supported properties
 *
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.tabset.prototype.addTab = function(props) {

    // Assume parent is tabset, which implies add tab to level 1.
    // It also implies it's parent is selected, even though there really is no parent.
    var parentID = this.id;
    var level = 1;
    var isParentSelected = true;

    // If tab specifies a parent and it's not the tabset, then determine if
    // that parent tab is selected and get the level the tab will be added to.
    if ((props.parentID != null) && (props.parentID != this.id)) {
        parentID = props.parentID;

        // Add tab to next level after parent tab level
        level = this._tabLevels[props.parentID] + 1;

        // Determine if parent tab is selected.
        var parentWidget = this._widget.getWidget(props.parentID);
        if (parentWidget != null) {
            var parentProps = parentWidget.getProps();
            if (parentProps.selected != null)
                isParentSelected = parentWidget.getProps().selected;
        }
    }

    // Get the tabset row this tab will drop in to
    var row = this._getTabsetRow(parentID, level);

    // Log ID of selected tab for this row
    if ((props.selected != null) && (props.selected == true))
        row.selectedID = props.id;

    // Log level for this tab
    this._tabLevels[props.id] = row.level;

    // Since we will be appending this tab to the tabset row, we must 1st remove
    // special class that is set on the current last node in the row.
    // This last node could be the container for a scroller.
    if (row.lastNode != null)
        this._common._stripStyleClass(row.lastNode, this._theme.getClassName("DTAB_LAST"));

    // Remove all scrollers from the tabset row.
    this._removeScrollButtons(row);

    // If this row is not already visible and it's parent is selected, then
    // add it to the DOM and log it as a visible row.
    if (!row.visible && isParentSelected) {
        row.visible = true;
        this._domNode.appendChild(row.tabsContainer);
        this._visibleRows[row.level-1] = row;
    }

    // Save a copy of the original tab title, as will need to restore it in case
    // tab truncation is not enough to fit the tabs in the available space.
    row.origTitles[props.id] = props.title;

    // Add the tab to the DOM
    this._widget._addFragment(row.tabsContainer, props, "last");
    row.numTabs++;

    // If tab is the selected tab for this row, then push it to 
    // the selection history stack for this row.
    if (row.selectedID == props.id)
        row.selectionStack.push(props.id);

    // If the tabset row this tab was added to is visible and the
    // tab is the selected tab for this row, then select the tab.
    if (row.visible && (row.selectedID == props.id))
        this._selectTab({"id": props.id});

    if (row.visible)
        this._resizeTabs(row);

}; // addTab

/**
 * Return the contents container for a given tab.
 *
 * @param {String} tabID  the ID of the tab
 * @return {Object} the content container
 * @private
 */
@JS_NS@.widget.tabset.prototype._getContentsContainer = function(tabID) {

    if (tabID == null)
        return null;

    // If container does not exist for this tab, then clone an empty one.
    var container = this._tabContents[tabID];
    if (container == null) {
        container = this._contentsContainer.cloneNode(false);
        container.className = this._theme.getClassName("DTAB_CONTENTS");
        if (this.contentHeight == null)
            this.contentHeight = this._theme.getMessage("dtabset.contentHeight")
        container.style.height = this.contentHeight;
        container.id = tabID + "_contentsContainer";
        this._tabContents[tabID] = container;
    }
    return container;

}; // _getContentsContainer

/**
 * Set the tabset's content container to be the contents container for a given tab.
 *
 * @param {String} tabID  the ID of the tab
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tabset.prototype._setContentsContainer = function(tabID) {

    if (tabID == null)
        return false;

    var container = this._getContentsContainer(tabID);
    if (container == null)
        return false;

    this._tabContentID = tabID;
    this._domNode.appendChild(container);
    return true;

}; // _setContentsContainer

/**
 * Add content for a tab widget to the tab's content container
 *
 * @param {Object} props  Key-value pairs of properties
 * @config {String} id    tab widget ID
 * @param {boolean} append  true to append the content, false to clear the existing
 *   content first
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.tabset.prototype.addContent = function(props, append) {

    // Get the tab's content container
    var contentsContainer = this._getContentsContainer(props.id);
    if (contentsContainer == null)
        return false;

    // Add the content to the container
    if (append == true)
        this._widget._addFragment(contentsContainer, props.contents, "last");
    else
        this._widget._addFragment(contentsContainer, props.contents);

    // Don't select any tab (below) if the widget has not started.
    if (this._started == false)
        return true;

    // If the tabset row the tab is contained in is visible and the specified
    // tab is the selected tab for this row, then select the tab.
    var w = this._widget.getWidget(props.id);
    var p = w.getProps();
    var row = this._tabsetRows[p.parentID];
    if (row.visible && (row.selectedID == props.id)) {
        this._selectTab({"id": props.id});
    }

    return true;

}; // addContent

/**
 * The tabset may have specific style classes that it wants assigned to a given
 * tab.  This function is the mechanism by which a tab can obtain those classes
 * from the tab's getClassName() function.  This is a private interface between
 * the tabset and tab widgets.
 *
 * @param {String} tabID  the tab's ID
 * @return {String} list of classes to be assigned, or null if none should be assigned
 * @private
 */
@JS_NS@.widget.tabset.prototype._getClassNameForTab = function(tabID) { 

    // Don't assign anything unless the widget has been started.
    if (this._started == false)
        return null;

    // Sanity checks
    if (tabID == null)
        return null;
    var tabWidget = this._widget.getWidget(tabID);
    if (tabWidget == null) {
        return null;
    }

    // Get the tabset row containing the tab.  This is the child tabset row
    // of the tab's parent and is the row in the tabsetRows list indexed 
    // by the id of the parent of the tab.
    var props = tabWidget.getProps();
    var row = this._tabsetRows[props.parentID];
    if (row == null)
        return null;

    var classes = null;

    // If tab is last node in the row, then assign DTAB_LAST to tab.
    var tabNode = document.getElementById(tabID);
    if (tabNode == row.lastNode)
        classes = this._theme.getClassName("DTAB_LAST");

    return classes;

}; // _getClassNameForTab

/**
 * Apply tab size policy for the space available for a given tabset row.
 *
 * @param {Object} row  the tabset row object
 * @private
 */
@JS_NS@.widget.tabset.prototype._resizeTabs = function(row) {

    // If resizing 1st-level, disable the textsize monitor
    if ((row.level == 1) && (this._textsizeMonitorID != null)) {
        clearInterval(this._textsizeMonitorID);
        this._textsizeMonitorID = null;
    }

    // Reset view characteristics of row back to clean state.
    var tabNode = row.tabsContainer.childNodes[row.firstViewableTab];
    if (tabNode.id != row.selectedID)
        this._common._stripStyleClass(tabNode, this._theme.getClassName("DTAB_BOTTOM"));
    if (row.lastNode != null)
        this._common._stripStyleClass(row.lastNode, this._theme.getClassName("DTAB_LAST"));

    row.firstViewableTab = 0;
    row.lastViewableTab = row.numTabs - 1;
    row.scroll = false;
    row.scrollFar = false;

    // If all tabs do not fit in the available space or equal tab widths are requested,
    // then make all tabs equal width.
    if (!this._fitTabs(row) || (this.equalTabWidth == true)) {
        this._equalTabs(row);
    }

    // Once all the tabs in the row have been sized and the width requirement still exceeds the
    // space that is available, then activate tab scrolling.
    if (row.neededWidth > row.availableWidth) {
        row.scroll = true;

        // If number of tabs exceed a threshold, then activate tab "far" scrolling.
        if (row.numTabs >=  parseInt(this._theme.getMessage("dtab.scrollFarThreshold")))
            row.scrollFar = true;

        // Configure for a far left scroll position.
        if (row.scrollFar) {
            row.tabsContainer.appendChild(row.scrollFarLeftDisabledBtn);
            row.availableWidth -= row.scrollFarLeftDisabledBtn.offsetWidth;
        }
        row.tabsContainer.appendChild(row.scrollLeftDisabledBtn);
        row.availableWidth -= row.scrollLeftDisabledBtn.offsetWidth;
        row.tabsContainer.appendChild(row.scrollRightEnabledBtn);
        row.availableWidth -= row.scrollRightEnabledBtn.offsetWidth;
        if (row.scrollFar) {
            row.tabsContainer.appendChild(row.scrollFarRightEnabledBtn);
            row.availableWidth -= row.scrollFarRightEnabledBtn.offsetWidth;
        }

        // Available width calculations may still not be enough to satisfy IE.
        // Tests have shown that without these adjustments, the last scroller image could
        // appear directly below where it is supposed to be positioned.
        if (@JS_NS@._base.browser._isIe()) {
            // Account for DTabScrollBtn.margin-left:-1px
            row.availableWidth -= 2;  // left/right scrollers
            if (row.scrollFar)
                row.availableWidth -= 2 // far left/right scrollers

            // Above adjustment may still not be enough, so include a safety factor.
            row.availableWidth -= parseInt(this._theme.getMessage("dtab.rowWidthPadIE"));
        }

        // Hide tabs from the right that do not fit in the available space.
        var n = row.numTabs - 1;
        while (row.neededWidth > row.availableWidth) {
            tabNode = row.tabsContainer.childNodes[n];
            row.neededWidth -= tabNode.offsetWidth;
            this._common._addStyleClass(tabNode, this._theme.getClassName("HIDDEN"));
            n--;
        }
        row.lastViewableTab = n;
    }

    // Determine the last node in the tabset row and reassign special class for last node.
    row.lastNode = row.tabsContainer.childNodes[row.numTabs-1];
    if (row.scroll == true) {
        if (row.scrollFar == true)
            row.lastNode = row.scrollFarRightEnabledBtn;
        else
            row.lastNode = row.scrollRightEnabledBtn;
    }
    this._common._addStyleClass(row.lastNode, this._theme.getClassName("DTAB_LAST"));

    // If selected tab out of view, add bottom border to first viewable tab.
    var selectedTab = this._getSelectedIndex(row);
    if ((selectedTab < row.firstViewableTab) || (selectedTab > row.lastViewableTab)) {
        tabNode = row.tabsContainer.childNodes[row.firstViewableTab];
        this._common._addStyleClass(tabNode, this._theme.getClassName("DTAB_BOTTOM"));
    }

    // Scroll selected tab into view
    while (selectedTab > row.lastViewableTab)
        this._scrollRightOnClickCallback(row);

    // If resizing 1st-level, enable the textsize monitor
    if (row.level == 1)
        this._textsizeMonitorID = setInterval(this._createTextsizeMonitor(), 1000);

}; // _resizeTabs

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
@JS_NS@.widget.tabset.prototype._setProps = function(props) {

    if (props == null)
        return false;

    // Contents
    if (props.contents != null) {
        if (this.contents != null) {
            // Walk the contents hierarchy and set appropriate ancestry properties for each widget
            this._setParents(this.id, this.contents);

            this._domNode.className = this._theme.getClassName("DTABSET");
            this._domNode.id = this.id;
            for (var i = 0; i < this.contents.length; i++) {
                if (this.contents[i].widgetType == "tab") {
                    this.addTab(this.contents[i]);
                }
            }
        }
    }

    // ContentHeight - apply to all containers
    if (props.contentHeight != null) {
        for (var key in this._tabContents) {
            var container = this._tabContents[key];
            container.style.height = this.contentHeight;
        }
    }
    
    // Set more properties.
    this._setCommonProps(this._domNode, props);
    this._setEventProps(this._domNode, props);

    // Set remaining properties.
    return this._inherited("_setProps", arguments);

}; // _setProps

/**
 * Recursively walk the hierarchy of the contents array and set the parentID
 * property to the specified widget parentID and the _tabsetParentID property
 * to the id of this tabset.
 *
 * @param {String} parentID  the widget id of the parent
 * @param {Array} contents  array of widget property objects
 * @private
 */
@JS_NS@.widget.tabset.prototype._setParents = function(parentID, contents) {

    if (contents == null)
        return;
    for (var i = 0; i < contents.length; i++) {
        var props = contents[i];
        props.parentID = parentID;
        props._tabsetParentID = this.id;  // Private property between tabset and tab
        this._setParents(props.id, props.contents);
    }

}; // _setParents

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tabset.prototype._postCreate = function() {

    // Subscribe to tab selection and closed events
    this._widget.subscribe(@JS_NS@.widget.tab.event.selectedTopic, this, "_selectTab");
    this._widget.subscribe(@JS_NS@.widget.tab.event.closedTopic, this, "_closeTab");

    // Subscribe to window resize events
    this._dojo.connect(window, "onresize", this._createOnResizeCallback());

    return this._inherited("_postCreate", arguments);

}; // _postCreate

/**
 * Setup handler for window resize events.  We setup in this manner so
 * we can use closure magic to define the scope for the handler with necessary 
 * information to properly process the event.
 *
 * @return {Object} the event handler
 * @private
 */
@JS_NS@.widget.tabset.prototype._createOnResizeCallback = function() {

    var _this = this;
    return function(event) {

        // Obtain a global widget lock to prevent conflicts with textsize monitor.
        // If already locked, then skip the event
        if (_this._spinLock.isLocked()) {
            return;
        }
        _this._spinLock.lock();
        _this._repaint();
        _this._spinLock.unlock();
    }

}; // _createOnResizeCallback

/**
 * Javascript event handlers are constrained to registering for events that occur within 
 * the context of the current document. The user's setting of font size or zoom factor 
 * through the browser menu is actually occurring outside the document.  Therefore, there 
 * is no event propagated within the appropriate context for javascript to intercept.
 * To workaround this, we monitor the offsetHeight of any element that has a dependency on
 * on the offsetHeight of some text element.
 *
 * Note that for this to work on IE6, we have to return a function and use closure magic
 * for the function to be able to get a handle to this widget because passing "this" as
 * an argument to a function via setInterval() did not work.
 *
 * @private
 */
@JS_NS@.widget.tabset.prototype._createTextsizeMonitor = function() {

    var _this = this;
    return function() {

        // Obtain a global widget lock to prevent conflicts with window resize handler
        if (_this._spinLock.isLocked()) {
            return;
        }
        _this._spinLock.lock();

        // Monitor 1st viewable tab in 1st row.
        var row = _this._visibleRows[0];

        // Obtain lock on row to prevent conflicts with events (select, remove, scroll)
        // on this row.  If row is locked, then skip this event.
        if (row.spinLock.isLocked()) {
            _this._spinLock.unlock();
            return;
        }
        row.spinLock.lock();

        // Get first viewable tab
        var tabNode = row.tabsContainer.childNodes[row.firstViewableTab];
        if (tabNode == null) {
            row.spinLock.unlock();
            _this._spinLock.unlock();
            return;
        }

        // Normally we could simply monitor the offsetHeight of the tabNode.  However that may
        // change by 1 pixel with the assignment/removal of the DTabBottom style class during
        // scrolling.  So we have to monitor the container for the title.
        var els = _this._getElementsByClass(_this._theme.getClassName("DTAB_TITLE"), tabNode, null);
        if ((els == null) || (els.length == 0)) {
            row.spinLock.unlock();
            _this._spinLock.unlock();
            return;
        }
        var textContainer = els[0];
    
        // If null, then this is the 1st one when the page is loading.
        if (_this._textsizeCheckHeight == null) {
            _this._textsizeCheckHeight = textContainer.offsetHeight;
            row.spinLock.unlock();
            _this._spinLock.unlock();
            return;
        }
    
        // If no textsize-related event occurred, then do nothing.
        if (textContainer.offsetHeight == _this._textsizeCheckHeight) {
            row.spinLock.unlock();
            _this._spinLock.unlock();
            return;
        }
    
        // Release row lock
        row.spinLock.unlock();

        // Update the height and redisplay the tabset.
        _this._textsizeCheckHeight = textContainer.offsetHeight;
        _this._repaint();

        // Release global widget lock
        _this._spinLock.unlock();

    }

}; // _createTextsizeMonitor

/**
 * Redisplay the tabset.
 *
 * @private
 */
@JS_NS@.widget.tabset.prototype._repaint = function() {

    for (var i = 0; i < this._visibleRows.length; i++) {
        var row = this._visibleRows[i];
        if (row == null)
            continue;

        // Remove special class that is set on the current last node in the row.
        // This last node could be the container for a scroller.
        if (row.lastNode != null)
            this._common._stripStyleClass(row.lastNode, this._theme.getClassName("DTAB_LAST"));

        this._removeScrollButtons(row);
        this._resizeTabs(row);
    }

}; // _repaint

/**
 * This function is used to "start" the widget, after the widget has been
 * instantiated.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tabset.prototype._startup = function () {

    if (this._started == true) {
        return false;
    }

    // Tab sizing cannot happen in _postCreate() or _setProps() when the widget is
    // instantiated as nodes have not been added to the DOM and element offsetWidth 
    // (which tab sizing is completely dependent upon) is zero.  Therefore we perform
    // the initial sizing here, after the widget has been instantiated.
    this._repaint();

    return this._inherited("_startup", arguments);

}; // _startup

/**
 * Setup handler for click events on scroll controls.  We setup in this manner so
 * we can use closure magic to define the scope for the handler with necessary 
 *
 * @param {Object} row the tabset row object the scroll button is contained in
 * @param {int} code indicates the scroll direction the button will perform:
 * @return {Object} the event handler
 * @private
 */
@JS_NS@.widget.tabset.prototype._createScrollOnClickCallback = function(row, code) {
    var _row = row;
    var _code = code;
    var _this = this;
    return function(event) {
        if (_code == -1)
            _this._scrollLeftOnClickCallback(_row);
        else if (_code == 1)
            _this._scrollRightOnClickCallback(_row);
        else if (_code < 0)
            _this._scrollFarLeftOnClickCallback(_row);
        else if (_code > 0)
            _this._scrollFarRightOnClickCallback(_row);
    }
}; // _createScrollOnClickCallback

/**
 * Handler for click events on scroll left button.
 *
 * @param {Object} row the tabset row object the scroll button is contained in
 * @return true if the tabset row was scrolled left, false if not
 * @private
 */
@JS_NS@.widget.tabset.prototype._scrollLeftOnClickCallback = function(row) {

    // Wait for lock
    row.spinLock.lock();

    // No scroll if already at far left.
    if (row.firstViewableTab == 0) {
        row.spinLock.unlock();
        return false;
    }

    // Enable scroll right controllers
    if (row.lastViewableTab == (row.numTabs - 1)) {
        var lastNode = row.scrollRightEnabledButton;
        row.tabsContainer.replaceChild(
            row.scrollRightEnabledBtn, row.scrollRightDisabledBtn);
        if (row.scrollFar) {
            row.tabsContainer.replaceChild(
                row.scrollFarRightEnabledBtn, row.scrollFarRightDisabledBtn);
            lastNode = row.scrollFarRightEnabledButton;
        }
        this._common._addStyleClass(lastNode, this._theme.getClassName("DTAB_LAST"));
    }

    // Make last viewable tab hidden
    var tabNode = row.tabsContainer.childNodes[row.lastViewableTab];
    this._common._addStyleClass(tabNode, this._theme.getClassName("HIDDEN"));
    row.lastViewableTab--;

    // If current first viewable tab is not the selected tab, then remove the
    // bottom border from it
    tabNode = row.tabsContainer.childNodes[row.firstViewableTab];
    if (tabNode.id != row.selectedID) {
        this._common._stripStyleClass(tabNode, this._theme.getClassName("DTAB_BOTTOM"));
    }

    // Make tab before current first viewable tab visible
    row.firstViewableTab--;
    tabNode = row.tabsContainer.childNodes[row.firstViewableTab];
    this._common._stripStyleClass(tabNode, this._theme.getClassName("HIDDEN"));

    // If this tab now visible is not the selected tab, then apply bottom
    // border to it.
    if (tabNode.id != row.selectedID) {
        var selectedTab = this._getSelectedIndex(row);
        if ((selectedTab < row.firstViewableTab) || (selectedTab > row.lastViewableTab)) {
            this._common._addStyleClass(tabNode, this._theme.getClassName("DTAB_BOTTOM"));
        }
    }

    // If scroll now at far left, disable scroll left controllers.
    if (row.firstViewableTab == 0) {
        row.tabsContainer.replaceChild(
            row.scrollLeftDisabledBtn, row.scrollLeftEnabledBtn);
        if (row.scrollFar) {
            row.tabsContainer.replaceChild(
                row.scrollFarLeftDisabledBtn, row.scrollFarLeftEnabledBtn);
        }
    }

    row.spinLock.unlock();
    return true;

}; // _scrollLeftOnClickCallback

/**
 * Handler for click events on scroll right button.
 *
 * @param {Object} row the tabset row object the scroll button is contained in
 * @return true if the tabset row was scrolled right, false if not
 * @private
 */
@JS_NS@.widget.tabset.prototype._scrollRightOnClickCallback = function(row) {

    // Wait for lock
    row.spinLock.lock();

    // No scroll if already at far right.
    if (row.lastViewableTab == (row.numTabs - 1)) {
        row.spinLock.unlock();
        return false;
    }

    // Enable scroll left controllers
    if (row.firstViewableTab == 0) {
        row.tabsContainer.replaceChild(
            row.scrollLeftEnabledBtn, row.scrollLeftDisabledBtn);
        if (row.scrollFar) {
            row.tabsContainer.replaceChild(
                row.scrollFarLeftEnabledBtn, row.scrollFarLeftDisabledBtn);
        }
    }

    // Make first viewable tab hidden
    var tabNode = row.tabsContainer.childNodes[row.firstViewableTab];
    this._common._addStyleClass(tabNode, this._theme.getClassName("HIDDEN"));

    // If this tab now hidden is not the selected tab, then remove the 
    // bottom border from it
    if (tabNode.id != row.selectedID) {
        this._common._stripStyleClass(tabNode, this._theme.getClassName("DTAB_BOTTOM"));
    }

    // Next tab is first viewable tab.  If it is not the selected tab, then 
    // apply bottom border to it.
    row.firstViewableTab++;
    tabNode = row.tabsContainer.childNodes[row.firstViewableTab];
    if (tabNode.id != row.selectedID) {
        var selectedTab = this._getSelectedIndex(row);
        if ((selectedTab < row.firstViewableTab) || (selectedTab > row.lastViewableTab)) {
            this._common._addStyleClass(tabNode, this._theme.getClassName("DTAB_BOTTOM"));
        }
    }

    // Make tab after current last viewable tab visible
    row.lastViewableTab++;
    tabNode = row.tabsContainer.childNodes[row.lastViewableTab];
    this._common._stripStyleClass(tabNode, this._theme.getClassName("HIDDEN"));

    // If scroll now at far right, disable scroll right controllers.
    if (row.lastViewableTab == (row.numTabs - 1)) {
        var lastNode = row.scrollRightDisabledBtn;
        row.tabsContainer.replaceChild(
            row.scrollRightDisabledBtn, row.scrollRightEnabledBtn);
        if (row.scrollFar) {
            row.tabsContainer.replaceChild(
                row.scrollFarRightDisabledBtn, row.scrollFarRightEnabledBtn);
            lastNode = row.scrollFarRightDisabledBtn;
        }
        this._common._addStyleClass(lastNode, this._theme.getClassName("DTAB_LAST"));
    }

    row.spinLock.unlock();
    return true;

}; // _scrollRightOnClickCallback

/**
 * Handler for click events on scroll far right button.
 *
 * @param {Object} row the tabset row object the scroll button is contained in
 * @private
 */
@JS_NS@.widget.tabset.prototype._scrollFarLeftOnClickCallback = function(row) {

    while (this._scrollLeftOnClickCallback(row));

}; // _scrollFarLeftOnClickCallback

/**
 * Handler for click events on scroll far left button.
 *
 * @param {Object} row the tabset row object the scroll button is contained in
 * @private
 */
@JS_NS@.widget.tabset.prototype._scrollFarRightOnClickCallback = function(row) {

    while (this._scrollRightOnClickCallback(row));

}; // _scrollFarRightOnClickCallback

/**
 * Remove a tab from this tabset.  This function is private for now, but can be
 * made public if desired.
 *
 * @param {Object} props Key-Value pairs of tab properties.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.tabset.prototype._removeTab = function(props) {

    var tabWidget = this._widget.getWidget(props.id);
    if (tabWidget == null) {
        return false;
    }
    props = tabWidget.getProps();

    // Get the tabset row containing the closed tab.  This is the child tabset row
    // of the closed tab's parent and is the row in the tabsetRows list indexed 
    // by the id of the parent of the tab that was closed.
    var selectedRow = this._tabsetRows[props.parentID];
    if (selectedRow == null)
        return false;
    selectedRow.spinLock.lock();

    // If the closed tab was the selected tab for this row, remove all visible tabset rows 
    // of the closed tab below the level of the closed tab.
    if (selectedRow.selectedID == props.id) {
        for (var i = selectedRow.level; i < this._visibleRows.length; i++) {
            var row = this._visibleRows[i];
            if (row == null)
                continue;
            row.spinLock.lock()
            this._domNode.removeChild(row.tabsContainer);
            row.visible = false;
            this._visibleRows[i] = null;
            row.spinLock.unlock()
        }
        selectedRow.selectedID = null;
    }

    // Restore original tab title, in case it was truncated for tab sizing.
    tabWidget.setProps({"title": selectedRow.origTitles[props.id]});
    selectedRow.origTitles[props.id] = null;

    // Remove the tab from the container for this row.
    var tabNode = document.getElementById(props.id);
    selectedRow.tabsContainer.removeChild(tabNode);
    selectedRow.numTabs--;

    // Remove all scrollers from the row.
    this._removeScrollButtons(selectedRow);

    // Apply any tab auto-resizing/scrolling as needed.
    this._resizeTabs(selectedRow);

    if (props.selected != true) {
        selectedRow.spinLock.unlock();
        return true;
    }

    // Removed tab was selected so nullify the selected tabID for this row.
    selectedRow.selectedID = null;

    // Pop it off the selection history stack
    selectedRow.selectionStack.pop();

    // Select next tab on stack that still exists
    var tabID = null;
    while (true) {
        tabID = selectedRow.selectionStack.pop();
        if (tabID == null)
            break;
        var tabNode = document.getElementById(tabID);
        if (tabNode != null)
            break;
    }
    if (tabID == null) {
        var tabNode = selectedRow.tabsContainer.childNodes[0];
        if (tabNode != null)
            tabID = tabNode.id;
    }
    selectedRow.spinLock.unlock();
    if (tabID != null) {
        this._selectTab({"id": tabID});

        // Scroll it into view
        var selectedTab = this._getSelectedIndex(selectedRow);
        while (selectedTab > selectedRow.lastViewableTab)
            this._scrollRightOnClickCallback(selectedRow);
        while (selectedTab < selectedRow.firstViewableTab)
            this._scrollRightOnClickCallback(selectedRow);
    }

    return true;

}; // _removeTab

/**
 * Close the tab with the specified id.
 * <p>
 * The row containing the closed tab will be resized to reflect the extra 
 * space available for the remaining tabs.  This means scrolling may become 
 * deactivated if it was previously acivated.  Additionally, tab titles that 
 * may have previously been truncated may be less truncated, if at all.
 * <p>
 * Closing an unselected tab merely removes the tab from the row.
 * <p>
 * If the closed tab was selected and has child tabs, all rows below that tab 
 * are removed.  If it has no child tabs, the tab's content is removed.
 * The new selected tab for the row will be the most recently selected tab.
 * If there was no previously selected tab, then the 1st tab in the row is selected.
 *
 * @param {String} id the ID of the closed tab
 * @return {boolean} true if successful; otherwise, false. 
 */
@JS_NS@.widget.tabset.prototype.closeTab = function(id) { 

    return this._closeTab({"id": id});

}; // closeTab

/**
 * Callback function when a tab is closed.
 *
 * @param {Object} props Key-Value pairs of properties.
 * @return {boolean} true if successful; otherwise, false. 
 * @private 
 */
@JS_NS@.widget.tabset.prototype._closeTab = function(props) { 

    // Close is the same as remove
    return this._removeTab(props);

}; // _closeTab

/**
 * Return the ID of the tabset's selected tab.  This will be the tab
 * whose content is displayed below the tabset rows.
 *
 * @return {String} the id of the selected tab, otherwise null.
 */
@JS_NS@.widget.tabset.prototype.getSelectedTabID = function() {

    if (this._visibleRows.length == 0)
        return null;
    var row = this._visibleRows[this._visibleRows.length - 1];
    if (row == null)
        return null;

    return row.selectedID;

}; // _getSelectedTabID

/**
 * Select the tab with the specified id.  
 *<p>
 * The previously selected tab in the same row that contains the specified tab
 * to be selected will be unselected.  If it has child tabs, all rows below that
 * tab are removed.  If it has no child tabs, the tab's content is removed.
 * <p>
 * The specified tab is then selected.  If it has child tabs, the child tabs are
 * displayed.  This process is repeated for each selected tab in a child row until
 * a selected tab has no children.  At that point, the content of the selected tab
 * for the bottom-most row is displayed.
 *
 * @param {String} id the ID of the selected tab
 * @return {boolean} true if successful; otherwise, false. 
 */
@JS_NS@.widget.tabset.prototype.selectTab = function(id) { 

    return this._selectTab({"id": id});

}; // selectTab

/**
 * Callback function when a tab is selected.
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} id the ID of the selected tab
 * @return {boolean} true if successful; otherwise, false. 
 * @private 
 */
@JS_NS@.widget.tabset.prototype._selectTab = function(props) { 

    if ((props == null) || (props.id == null))
        return false;

    var tabWidget = this._widget.getWidget(props.id);
    if (tabWidget == null) {
        return false;
    }
    var props = tabWidget.getProps();

    // Get the tabset row containing the selected tab.  This is the child tabset row
    // of the selected tab's parent and is the row in the tabsetRows list indexed 
    // by the id of the parent of the tab that was selected.
    var selectedRow = this._tabsetRows[props.parentID];
    if (selectedRow == null)
        return false;
    selectedRow.spinLock.lock();

    // Remove the contents container for the previously selected tab.
    var contentsContainer = this._getContentsContainer(this._tabContentID);
    if (contentsContainer != null)
        this._domNode.removeChild(contentsContainer);
    this._tabContentID = null;

    // Remove all visible tabset rows of the previously selected tab below the level
    // of the newly selected tab.
    for (var i = selectedRow.level; i < this._visibleRows.length; i++) {
        var row = this._visibleRows[i];
        if (row == null)
            continue;
        row.spinLock.lock();
        this._domNode.removeChild(row.tabsContainer);
        row.visible = false;
        this._visibleRows[i] = null;
        row.spinLock.unlock();
    }

    // Unselect the previously selected tab.
    // Also make sure it's hidden if it's scrolled out of view.
    if ((selectedRow.selectedID != null) && (selectedRow.selectedID != props.id)) {
        var w = this._widget.getWidget(selectedRow.selectedID);
        w._setSelected(false);
        var selectedTab = this._getSelectedIndex(selectedRow);
        if ((selectedTab < selectedRow.firstViewableTab) || (selectedTab > selectedRow.lastViewableTab)) {
            var tabNode = selectedRow.tabsContainer.childNodes[selectedTab];
            this._common._addStyleClass(tabNode, this._theme.getClassName("HIDDEN"));
        }
    }

    // Update ID for selected tab for this row.
    selectedRow.selectedID = props.id;

    // Selected tabs do not have border bottom.
    var selectedTab = this._getSelectedIndex(selectedRow);
    var tabNode = selectedRow.tabsContainer.childNodes[selectedTab];
    this._common._stripStyleClass(tabNode, this._theme.getClassName("DTAB_BOTTOM"));
    selectedRow.spinLock.unlock();

    // Inform tab it is selected.
    tabWidget._setSelected(true);

    // Selecting the tab may cause the DTAB_LAST style class to be lost
    // if scrolling is not enabled for this row.  So reassign it.
    if (selectedRow.scroll == false)
        this._common._addStyleClass(selectedRow.lastNode, this._theme.getClassName("DTAB_LAST"));

    // Push selected tab's ID to the top of the selection history
    // stack for the selected row.
    selectedRow.selectionStack.push(props.id);

    // Get the child tabset row of the selected tab.  This will be the row
    // in the tabsetRows list indexed by the id of the tab.
    var childRow = this._tabsetRows[props.id];
    if (childRow == null) {
        // Set the tabset's content container to be the contents container for
        // this selected tab.
        this._setContentsContainer(props.id);

        // Publish event for the tabset's selected tab
        this._publish(@JS_NS@.widget.tabset.event.selectedTopic,[{
            "id": props.id
        }]);  

        return true;
    }
    childRow.spinLock.lock();

    // Remove all scrollers from the row.
    this._removeScrollButtons(childRow);

    // Make this row visible, log it as a visible tab for this level, and
    // apply any tab auto-resizing/scrolling as needed.
    childRow.visible = true;
    this._domNode.appendChild(childRow.tabsContainer);
    this._visibleRows[childRow.level-1] = childRow;
    this._resizeTabs(childRow);

    // If this child row has a selected tab then process this tab as if it were selected by the user.
    if (childRow.selectedID != null) {
        this._selectTab({"id": childRow.selectedID});
    }

    childRow.spinLock.unlock();
    return true;

}; // _selectTab
