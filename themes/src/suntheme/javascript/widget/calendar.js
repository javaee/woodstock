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

@JS_NS@._dojo.provide("@JS_NS@.widget.calendar");

@JS_NS@._dojo.require("@JS_NS@._base.browser");
@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.stateBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");

/**
 * This function is used to construct a calendar widget.
 *
 * @constructor
 * @name @JS_NS@.widget.calendar
 * @extends @JS_NS@.widget._base.widgetBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @extends @JS_NS@.widget._base.stateBase
 * @class This class contains functions for the calendar widget.
 * <p>
 * The calendar widget displays an icon that displays a small calendar when 
 * clicked. Typically, this is used in conjuction with the calendarField widget
 * or a text field used to input a date. The user can either type directly into
 * the text field or select a date from the calendar display.
 * </p><p>
 * By default, the calendar widget accepts dates between the current date and 
 * hundred years out. The years shown in the calendar reflect this range. To
 * specify a different range of date, use the minDate and maxDate properties.
 * </p><p>
 * </p><p>
 * <h3>Example 1: Create widget</h3>
 * </p><p>
 * This example shows how to create a widget using a span tag as a place holder 
 * in the document. Minimally, the createWidget() function needs an id and 
 * widgetType properties.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       dateFormat: "MM/dd/yyyy",
 *       id: "cal1",
 *       maxDate: "05/12/2108",
 *       minDate: "05/12/1908",
 *       todayDateMsg: "Today: May 12, 2008",
 *       widgetType: "calendar"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 2: Update widget using the getProps and setProps functions</h3>
 * </p><p>
 * This example shows how to toggle the state of a widget using the
 * getProps and setProps functions. When the user clicks the checkbox, the
 * calendar is either disabled or enabled.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       dateFormat: "MM/dd/yyyy",
 *       id: "cal1",
 *       maxDate: "05/12/2108",
 *       minDate: "05/12/1908",
 *       todayDateMsg: "Today: May 12, 2008",
 *       widgetType: "calendar"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Toggle Calendar State" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("cal1"); // Get calendar
 *       return widget.setProps({disabled: !domNode.getProps().disabled}); // Toggle state
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 3a: Asynchronously update widget using refresh function</h3>
 * </p><p>
 * This example shows how to asynchronously update a widget using the refresh 
 * function. When the user clicks on the checkbox, the calendar is 
 * asynchronously updated with new data.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "cal1",
 *       maxDate: "05/12/2108",
 *       minDate: "05/12/1908",
 *       todayDateMsg: "Today: May 12, 2008",
 *       widgetType: "calendar"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Refresh Calendar" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("cal1"); // Get calendar
 *       return widget.refresh(); // Asynchronously refresh
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * Note that the refresh function can take an optional list of elements to 
 * execute. Thus, a comma-separated list of ids can be provided to update 
 * server-side components: refresh("id1,id2,..."). When no parameter is given, 
 * the refresh function acts as a reset. That is, the widget will be redrawn 
 * using values set server-side, but not updated.
 * </p><p>
 * <h3>Example 3b: Asynchronously update widget using refresh function</h3>
 * </p><p>
 * This example shows how to asynchronously update a button using the refresh
 * function. The execute property of the refresh function is used to define the
 * client id which is to be submitted and updated server-side. When the user 
 * clicks on the checkbox, the input value is updated server-side and the 
 * calendar is updated client-side -- all without a page refresh.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "cal1",
 *       maxDate: "05/12/2108",
 *       minDate: "05/12/1908",
 *       todayDateMsg: "Today: May 12, 2008",
 *       widgetType: "calendar"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Change Calendar Date" },
 *       onClick="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("cal1"); // Get calendar
 *       return widget.refresh("cb1"); // Asynchronously refresh while submitting checkbox value
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 4: Subscribing to event topics</h3>
 * </p><p>
 * When a widget is manipulated, some features may publish event topics for
 * custom AJAX implementations to listen for. For example, you may listen for
 * the refresh event topic using:
 * </p><pre><code>
 * &lt;script type="text/javascript">
 *    var foo = {
 *        // Process refresh event.
 *        //
 *        // @param {Object} props Key-Value pairs of properties.
 *        processRefreshEvent: function(props) {
 *            // Get the widget id.
 *            if (props.id == "cal1") { // Do something... }
 *        }
 *    }
 *    // Subscribe to refresh event.
 *    woodstock.widget.common.subscribe(woodstock.widget.calendar.event.refresh.endTopic, 
 *      foo, "processRefreshEvent");
 * &lt;/script>
 * </code></pre>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} className CSS selector.
 * @config {Object} closeButtonLink 
 * @config {String} date 
 * @config {String} dateFormat 
 * @config {Object} decreaseLink 
 * @config {String} id Uniquely identifies an element within a document.
 * @config {Object} increaseLink 
 * @config {Object} maxDate
 * @config {Object} minDate
 * @config {Object} monthMenu
 * @config {String} style Specify style rules inline.
 * @config {Object} todayDateMsg
 * @config {Object} toggleLink
 * @config {boolean} visible Hide or show element.
 * @config {Object} yearMenu
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.calendar", [
        @JS_NS@.widget._base.refreshBase, 
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.widgetBase ], {
    // Set defaults.
    _widgetType: "calendar" // Required for theme properties.
});

/**
 * Helper function to add a day link in a cell.
 *
 * @param {Node} rowNodeClone
 * @param {Object} day
 * @param {String} id
 * @param {String} className
 * @param {boolean} setFocus
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.calendar.prototype._addDayLink = function(rowNodeClone, day, 
        id, className, setFocus) {
    // Clone <td> and <a> elements. 
    var colNodeClone = this._dayColumnContainer.cloneNode(false);
    rowNodeClone.appendChild(colNodeClone);    
    var linkNodeClone = this._dayLinkContainer.cloneNode(false);            
    colNodeClone.appendChild(linkNodeClone);
    
    // Format the date.      
    var formattedDate = this._formatDate(day.getMonth() + 1, day.getDate(), day.getFullYear()); 
  
    // set the link's properties for this day.
    linkNodeClone.title = formattedDate;
    linkNodeClone.id = id;
    linkNodeClone.href = "#";
    linkNodeClone.className = className;

    // NOTE: If you set this value manually, text must be HTML escaped.
    this._widget._addFragment(linkNodeClone, "" + day.getDate());

    var widgetId = this.id;
    linkNodeClone.onclick = function() { 
        @JS_NS@.widget.common.getWidget(widgetId)._daySelected(formattedDate); 
        return false;
    };  
    
    // If the setFocus is set to true, then when you tab out of the linkNode,
    // the focus should go to the close button. 
    if (setFocus) {
        linkNodeClone.onkeydown = function(event) {
            var widget = @JS_NS@.widget.common.getWidget(widgetId);
            
            // Get hold of the close button and set focus on it.
            var evt = (event) ? event : ((window.event) ? window.event : null);
            if (evt.keyCode == 9) {
                var elem = document.getElementById(widget.closeButtonLink.id);
                if (elem != null) {
                    if (elem.focus) {
                        elem.focus();
                    }
                    if (evt.preventDefault) {
                        evt.preventDefault();
                    } else {
                        evt.returnValue = false;
                    }
                    return false;                                  
                }
            }
            return true;
        };
    }
    return true;
};

/**
 * Helper function to add days in the month -- week data rows.
 *
 * @param {Object} currentValue The current value of the text field.
 * @param {boolean} initialize Flag indicating to initialze the year and month menus
 * with the current value. The value is true only when the calendar is opened.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.calendar.prototype._addDaysInMonth = function(currentValue, initialize) {
    // Date representing a day in a month.
    var day;    
    // Number of columns in a row.
    var column = 0;
    // Row number.    
    var rowNum = 0;    
    // Today's day.
    var today = 0;
    // Selected date.
    var selected = 0;     
    // Day link number
    var linkNum = 0; 
    // Prefix used for a day link id.
    var id = this.id + "_link:";
    // Day link id. ie, id + linkNum.
    var linkId;
    // One day in milliseconds -- 1000 * 60 * 60 * 24    
    var oneDayInMs = 86400000;     

    var todayDate = new Date();
    var todayYear = todayDate.getFullYear();
    var todayMonth = todayDate.getMonth() + 1;
    var todayDay = todayDate.getDate();                  
    
    // selectedYear, selectedMonth, selectedDay:
    // The date to show as highlighted (currentValue) provided
    // that the user is viewing that month and year.
    var selectedYear = null;
    var selectedMonth = null;
    var selectedDay = null;
    if (currentValue != null) {
        selectedYear = currentValue.getFullYear();
        selectedMonth = currentValue.getMonth() + 1;
        selectedDay = currentValue.getDate();
    }
    
    // Get month and year menu widgets.
    var monthMenuWidget = this._widget.getWidget(this.monthMenu.id);        
    var yearMenuWidget = this._widget.getWidget(this.yearMenu.id);
    if (monthMenuWidget == null || yearMenuWidget == null) {
        return false;
    }
               
    if (initialize) {
         // Set showMonth as selected in the month menu
	 // Set showYear as selected in the year menu
         // Use todayMonth and todayYear if currentValue is null.
	 var showMonth = todayMonth;
	 var showYear = todayYear;
	 if (currentValue != null) {
             // We have a currentValue, so use that for showMonth and showYear
             showMonth = selectedMonth;
	     showYear = selectedYear;
         }         
         this._setLimitedSelectedValue(monthMenuWidget.getSelectElement(), showMonth);
         this._setLimitedSelectedValue(yearMenuWidget.getSelectElement(), showYear);
    }
    
    var month = parseInt(monthMenuWidget.getSelectedValue());
    var year = parseInt(yearMenuWidget.getSelectedValue());
    
    //set selected
    if (currentValue != null && selectedYear == year && selectedMonth == month) {
        selected = selectedDay;
    }
        
    //set today
    if (todayYear == year && todayMonth == month) {
        today = todayDay;
    }
    
    // Add first week data row.
    var rowNodeClone = this._weekRowContainer.cloneNode(false);
    this._tbodyContainer.appendChild(rowNodeClone); 
    rowNodeClone.id = this.id + ":row" + rowNum;
    
    // Convert to javascript month numbering.
    month--;
    
    // Calculate the first of the main month to display in "first" row.
    var first = new Date(year, month, 1);                         
    var firstDay = first.getDay();    
    var className = this._theme.getClassName("DATE_TIME_OTHER_LINK");
    if (firstDay == this.firstDayOfWeek - 1) {
        // First cell on first row is the first of the current month
        day = first;
    } else {
        // First cell on first row is in previous month.
        var backDays = (firstDay - (this.firstDayOfWeek - 1) + 7) % 7;        
        
        // Calculate the date of first cell on first row in previous month.
        day = new Date(first.getTime() - backDays * oneDayInMs);        
        
        // Generate start of first row up to first of month
        while (day.getMonth() !=  first.getMonth()) {
            linkId = id + linkNum;
            this._addDayLink(rowNodeClone, day, linkId, className);
            day = new Date(day.getTime() + oneDayInMs);
            column++;
            linkNum++;            
        }
    }

    // Add any cells in the first row of the main month.
    while (column < 7) {
        // Set appropriate class name.
        if (day.getDate() == selected) {
            className = this._theme.getClassName("DATE_TIME_BOLD_LINK");
        } else if (day.getDate() == today) {
            className = this._theme.getClassName("DATE_TIME_TODAY_LINK");
        } else {
           className = this._theme.getClassName("DATE_TIME_LINK");
        }
            
        linkId = id + linkNum;
        this._addDayLink(rowNodeClone, day, linkId, className);        
        day = new Date(day.getTime() + oneDayInMs);
        column++;
        linkNum++;
    } 
    
    // This variable is used to decide whether the focus should be set
    // on the calendar's close button when tabbing    
    var setFocus = false;            
    
    // Add intermediate rows
    while (day.getDate() != 1) {
        rowNum++;
        // Clone a <tr> node
        rowNodeClone = this._weekRowContainer.cloneNode(false);
        this._tbodyContainer.appendChild(rowNodeClone); 
        rowNodeClone.id = this.id + ":row" + rowNum;

        column = 0;
        while (column < 7 && day.getDate() != 1) {            
            // Set appropriate class name.
            if (day.getDate() == selected) {
                className = this._theme.getClassName("DATE_TIME_BOLD_LINK");
            } else if (day.getDate() == today) {
                className = this._theme.getClassName("DATE_TIME_TODAY_LINK");
            } else {
                className = this._theme.getClassName("DATE_TIME_LINK");
            }
                 
            linkId = id + linkNum;
            var tmpDate = new Date(day.getTime() + oneDayInMs);

            // On some platforms, the date is not incremented correctly (e.g.,
            // October 28th 1990, 2007, and 2012). In this case, try again.
            if (tmpDate.getDate() == day.getDate()) {
                tmpDate = new Date(tmpDate.getTime() + oneDayInMs);
            } 
            
            // Check whether this is the last date in the calendar and if so
            // set the setFocus variable to true. This will mean that when the
            // user tabs away from this day, the focus will be set on the
            // close button.
            if (tmpDate.getDate() == 1 && column == 6) {
                setFocus = true;
            } else {
                setFocus = false;
            }
            this._addDayLink(rowNodeClone, day, linkId, className, setFocus);
            day = tmpDate;
            column++;
            linkNum++;
        }
    }
    
    // Add any cells in the last row of the following month
    while (column < 7) {
        var className = this._theme.getClassName("DATE_TIME_OTHER_LINK");
        linkId = id + linkNum;
        
        // Check whether this is the last date in the calendar and if so
        // set the setFocus variable to true. This will mean that when the
        // user tabs away from this day, the focus will be set on the
        // close button.        
        if (column == 6) {
            setFocus = true;
        } else {
            setFocus = false;
        }
        this._addDayLink(rowNodeClone, day, linkId, className, setFocus);                    
        day = new Date(day.getTime() + oneDayInMs);
        column++;
        linkNum++;
    }
    return true;
};

/**
 * Helper function to add the week day headers row.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.calendar.prototype._addWeekDays = function() {            
    var colNodeClone;
    var spanNodeClone;    
    var firstDay = this.firstDayOfWeek - 1;
    
    // Clone the <tr> node and append it to <tbody>
    var rowNodeClone = this._weekDayRow.cloneNode(false);
    this._tbodyContainer.appendChild(rowNodeClone);
        
    for (var i = 0; i < 7; i++) {
        // Clone the <th> node and append it to <tr>
        colNodeClone = this._weekDayColumn.cloneNode(false);
        rowNodeClone.appendChild(colNodeClone);
               
        // Clone the <span> node and append it to <th>
        spanNodeClone = this._weekDayContainer.cloneNode(false);
        colNodeClone.appendChild(spanNodeClone);
        
        // NOTE: If you set this value manually, text must be HTML escaped.
        this._widget._addFragment(spanNodeClone, this.weekDays[firstDay]);

        firstDay++;
        if (firstDay == 7) {
            firstDay = 0;
        }     
    }
    return true;
};

/**
 * Close the calendar if the enter key is pressed and set the initial focus
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */            
@JS_NS@.widget.calendar.prototype._closeCalendar = function(event) {
    var evt = (event) ? event : ((window.event) ? window.event : null);
     
    // If key pressed and enter, then close the menu
    if ((evt.type == "keydown") && (evt.keyCode == 13)) {
        this._toggleCalendar();
        document.getElementById(this.toggleLink.id).focus();        
        return false;
    } 
    this._setInitialFocus();
    return true;    
};

/**
 * Process day selected event.
 * <p>
 * When a day link is selected, an event is published which the 
 * calendarField widget will use to update its text field.
 * </p>
 * @param {String} Formatted date string.
 * @return {boolean} false to cancel JavaScript event.
 * @private
 */
@JS_NS@.widget.calendar.prototype._daySelected = function(formattedDate) {
    this._toggleCalendar();    
    this._publish(@JS_NS@.widget.calendar.event.day.selectedTopic, [{
        id: this.id,
        date:formattedDate
    }]);
    return false;
};

/**
 * This function is used to decrease the month by one.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.calendar.prototype._decreaseMonth = function() {
    var monthMenu = this._widget.getWidget(this.monthMenu.id).getSelectElement();
    // If the monthMenu has no value, set it to January (that's what
    // it will have appeared like in the browser). Can happen on IE.  
    if (monthMenu.value == null) {
        monthMenu.value = monthMenu.options[0].value;
    }
    
    var month = parseInt(monthMenu.value);
    if (month == 1) {
        var yearMenu = this._widget.getWidget(this.yearMenu.id).getSelectElement();        
         if (yearMenu.value == null) {
             // If the yearMenu has no value, set it to the first available year            
             // (that's what it will have appeared like in the browser). Can happen on IE.
             yearMenu.value = yearMenu.options[0].value;
         } else if (yearMenu.value == yearMenu.options[0].value) {
             // No need to update the calendar in this case,
             // we don't change anything.
             return false;           
         } else {
             // Decrease the year by one and set the month to December
             var year = parseInt(yearMenu.value);
             year--;
             yearMenu.value = year;
             month = 12;
         }
    } else {
        month--;
    }
    monthMenu.value = month;    
    return this._updateMonth(false);
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
@JS_NS@.widget.calendar.event =
        @JS_NS@.widget.calendar.prototype.event = {
    /**
     * This object contains day event topics.
     * @ignore
     */
    day: {
        /** Day event topic for custom AJAX implementations to listen for. */
        selectedTopic: "@JS_NS@_widget_calendar_event_selected"
    },

    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_calendar_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_calendar_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_calendar_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_calendar_event_state_end"
    },

    /**
     * This object contains toggle event topics.
     * @ignore
     */
    toggle: {
        /** Open event topic for custom AJAX implementations to listen for. */
        openTopic: "@JS_NS@_widget_calendar_event_toggle_open",

        /** Close event topic for custom AJAX implementations to listen for. */
        closeTopic: "@JS_NS@_widget_calendar_event_toggle_close"
    }
};

/**
 * Helper function to format the date.
 *
 * @param {String} month
 * @param {String} day
 * @param {String} year
 * @return {String} The date format.
 * @private
 */
@JS_NS@.widget.calendar.prototype._formatDate = function(month, day, year) {
    var date = new String(this.dateFormat);      
    date = date.replace("yyyy", new String(year));
    if (month < 10) {
        date = date.replace("MM", "0" + new String(month));
    } else {
        date = date.replace("MM", new String(month));
    }
    if (day < 10) {
        date = date.replace("dd", "0" + new String(day));
    } else {
        date = date.replace("dd", new String(day));
    }
    return date;
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.calendar.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);
    
    // Set properties.
    if (this.todayDateMsg != null) { props.todayDateMsg = this.todayDateMsg; }
    if (this.spacerImage != null) { props.spacerImage = this.spacerImage; }
    if (this.topLeftImage != null) { props.topLeftImage = this.topLeftImage; }
    if (this.topRightImage != null) { props.topRightImage = this.topRightImage; }
    if (this.closeButtonLink != null) { props.closeButtonLink = this.closeButtonLink; }
    if (this.increaseLink != null) { props.increaseLink = this.increaseLink; }
    if (this.decreaseLink != null) { props.decreaseLink = this.decreaseLink; }
    if (this.monthMenu != null) { props.monthMenu = this.monthMenu; }
    if (this.yearMenu != null) { props.yearMenu = this.yearMenu; }   
    if (this.firstDayOfWeek != null) { props.firstDayOfWeek = this.firstDayOfWeek; }
    if (this.toggleLink != null) { props.toggleLink = this.toggleLink; }
    if (this.weekDays != null) { props.weekDays = this.weekDays; }    
    if (this.maxDate != null) { props.maxDate = this.maxDate; }
    if (this.minDate != null) { props.minDate = this.minDate; }
    
    return props;
};

/**
 * Workaround IE bug where popup calendar appears under other components.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.calendar.prototype._ieStackingContextFix = function() {
    var div = this._calendarContainer;
    if (div.style.display == "block") {
        // This popup should be displayed
        // Get the current zIndex for the div
        var divZIndex = div.currentStyle.zIndex;
        
        // Propogate the zIndex up the offsetParent tree
        var tag = div.offsetParent;
        while (tag != null) {
            var position = tag.currentStyle.position;
            if (position == "relative" || position == "absolute") {

                // Save any zIndex so it can be restored
                tag.raveOldZIndex = tag.style.zIndex;

                // Change the zIndex
                tag.style.zIndex = divZIndex;
            }
            tag = tag.offsetParent;
        }

        // Hide controls unaffected by z-index
        this._ieShowShim();
    } else {
        // This popup should be hidden so restore zIndex-s
        var tag = div.offsetParent;
        while (tag != null) {
            var position = tag.currentStyle.position;
            if (position == "relative" || position == "absolute") {
                if (tag.raveOldZIndex != null) {
                    tag.style.zIndex = tag.raveOldZIndex;
                }
            }
            tag = tag.offsetParent;
        }
        this._ieHideShim();
    }
    return true;
};

/**
 * Hides components unaffected by z-index.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.calendar.prototype._ieShowShim = function() {  
    var popup = this._calendarContainer;
    var shim = this._shimContainer;
    
    shim.style.position = "absolute";
    shim.style.left = popup.style.left;
    shim.style.top = popup.style.top;
    shim.style.width = popup.offsetWidth;
    shim.style.height = popup.offsetHeight;
    shim.style.zIndex = popup.currentStyle.zIndex - 1;
    shim.style.display = "block";

    return true;
};

/**
 * Hide the shim iframe.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.calendar.prototype._ieHideShim = function() {
    var shim = this._shimContainer;
    shim.style.display = "none";
    return true;
};

/**
 * This function is used to increment the current month.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.calendar.prototype._increaseMonth = function() {            
    var monthMenu = this._widget.getWidget(this.monthMenu.id).getSelectElement();          
    
    // If the monthMenu has no value, set it to January (that's what
    // it will have appeared like in the browser). Can happen on IE. 
    if (monthMenu.value == null) {
        monthMenu.value = monthMenu.options[0].value;
    }
    
    var month = parseInt(monthMenu.value);
    if (month == 12) {
        var yearMenu = this._widget.getWidget(this.yearMenu.id).getSelectElement();
        var numOptions = yearMenu.options.length;
        if (yearMenu.value == null) {
            // If the yearMenu has no value, set it to the first available year            
            // (that's what it will have appeared like in the browser). Can happen on IE.
            yearMenu.value = yearMenu.options[0].value;
        } else if (yearMenu.value == yearMenu.options[numOptions-1].value) {
            // No need to update the calendar in this case,
            // we don't change anything.
            return false;            
        } else {
            // Increase the year by one and set the month to January.
            var year = parseInt(yearMenu.value);
            year++;
            yearMenu.value = year;
            month = 1;
        }
    } else {
        month++;
    }
    monthMenu.value = month;   
    return this._updateMonth(false);    
};

/**
 * This function returns a JSON array of months to be displayed in the month 
 * drop down. 
 * return {Object} A JSON array of months.
 * @private
 */
@JS_NS@.widget.calendar.prototype._getMonthOptions = function() {
    var monthMenu = new Array();
    
    // Get the number of months in a calendar year.
    // Some calendars may have more than 12 months a year.
    var numOfMonths = parseInt(this._theme.getMessage("calendar.numOfMonths"));
    
    for ( var i = 0; i < numOfMonths; i++ ) {
        monthMenu[i] = {};
        monthMenu[i].value = i+1;
        monthMenu[i].disabled = false;
        monthMenu[i].separator = false;
        monthMenu[i].escape = true;
        monthMenu[i].group = false;
        monthMenu[i].label=this._theme.getMessage("calendar."+i);
    }    
    return monthMenu;
};

/**
 * This function returns a JSON array of years to be displayed in the year
 * drop down
 * @param {String} minYear the minimum year of the calendar display
 * @param {String} maxYear the maximum year of the calendar display
 * @return {Object} A JSON array of calendar years.
 * @private
 */
@JS_NS@.widget.calendar.prototype._getYearOptions = function(minYear, maxYear) {    
    var yearMenu =new Array();       
    var diff = maxYear - minYear;
    for ( var i = 0; i <= diff; i++ ) {
        yearMenu[i] = {};
        yearMenu[i].value = minYear;
        yearMenu[i].disabled = false;
        yearMenu[i].separator = false;
        yearMenu[i].escape = true;
        yearMenu[i].group = false;
        yearMenu[i].label=minYear;
        minYear++;
    }
    return yearMenu;
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
@JS_NS@.widget.calendar.prototype._postCreate = function () {
    // Set ids. 
    if (this.id) {
        this._calendarMenuContainer.id = this.id + "_calendarMenuContainer";
        this._linkNode.id = this.id + "_linkNodeContainer";
        this._todayDateContainer.id = this.id + "_todayDateContainer";
        this._closeButtonContainer.id = this.id + "_closeButtonContainer";
        this._previousLinkContainer.id = this.id + "_previousLinkContainer";
        this._monthMenuContainer.id = this.id + "_monthMenuContainer";
        this._nextLinkContainer.id = this.id + "_nextLinkContainer";
        this._yearMenuContainer.id = this.id + "_yearMenuContainer";
        this._shimContainer.id = this.id + "_shim";
    }

    // Create client side widgets for the calendar.
    // When the _setProps() function is called, these widgets will be
    // instantiated via the props param. 

    // If toggle link is null, create the image hyperlink.
    if (this.toggleLink == null) {
        this.toggleLink = {
            id: this.id + "_datePickerLink",
            contents: [],
            imagePosition: "left",
            title: this._theme.getMessage("calendar.popupImageAlt"),
            enabledImage: {
                border: 0,
                icon: "CALENDAR_BUTTON",
                id: this.id + "_datePickerLink_image",
                widgetType: "image"
            },
            disabledImage: {
                border: 0,
                icon: "CALENDAR_BUTTON_DISABLED",
                id: this.id + "_datePickerLink_image_disabled",
                widgetType: "image"
            },
            align:"middle",
            widgetType: "imageHyperlink"
        };
    }

    // Create the spacer image.
    if (this.spacerImage == null) {
        this.spacerImage = {
            icon: "DOT",
            id: this.id + ":DOT",
            widgetType: "image"
        };
    }
    
    // Create the top left image.
    if (this.topLeftImage == null) {
        this.topLeftImage = {
            icon: "SCHEDULER_TOP_LEFT",
            id: this.id + ":topLeft",
            widgetType: "image"
        };        
    }        
        
    //Create the top right image.
    if (this.topRightImage == null) {
        this.topRightImage = {
            icon: "SCHEDULER_TOP_RIGHT",
            id: this.id + ":topRight",
            widgetType: "image"
        };        
    }

    // Create the increase link imageHyperlink widget.
    if (this.increaseLink == null) {
        this.increaseLink = {
            id: this.id + ":nextMonthLink",
            enabledImage: {
                border: 0,
                icon: "SCHEDULER_FORWARD",
                id: this.id + ":nextMonthLink_image",
                widgetType: "image"
            },
            title: this._theme.getMessage("CalendarMonth.goForward"),
            widgetType: "imageHyperlink"
        };
    }   
    
    // Create the decrease link imageHyperlink widget.
    if (this.decreaseLink == null) {
        this.decreaseLink = {
            "id": this.id + ":previousMonthLink",
            enabledImage: {
                border: 0,
                icon: "SCHEDULER_BACKWARD",
                id: this.id + ":previousMonthLink_image",
                widgetType: "image"
            },
            title: this._theme.getMessage("CalendarMonth.goBack"),
            widgetType: "imageHyperlink"
        };
    }        
    
    // Create the close button link imageHyperlink widget
    if (this.closeButtonLink == null) {
        this.closeButtonLink = {
            id: this.id + ":closeButtonLink",
            enabledImage: {
                border: 0,
                icon: "CALENDAR_CLOSE_BUTTON",
                id: this.id + "closeButtonLink_close",
                widgetType: "image"
            },
            title: this._theme.getMessage("CalendarMonth.close"),
            className: this._theme.getClassName("CALENDAR_CLOSE_BUTTON"),
            widgetType: "imageHyperlink"
        };    
    }
    
    // If the dateFormatPattern is null, get one from the themes.
    if (this.dateFormat == null) {
        this.dateFormat = this._theme.getMessage("calendar.dateFormat");
    }
    
    // If the minDate and maxDate are not specified, create a default values.
    // The minDate's year is set to 100 years previous to the current year
    // and maxDate is set to 200 years forward from the minDate's year'
    var minDate = new Date();
    var maxDate = new Date();
    if (this.minDate == null) {
        minDate.setFullYear(minDate.getFullYear() - 100);
        this.minDate = this._formatDate(minDate.getMonth(), 
            minDate.getDate(), minDate.getFullYear());        
    } else {
        minDate = this._convertStringToDate(this.minDate);
    } 

    if (this.maxDate == null) {
        maxDate.setFullYear(minDate.getFullYear() + 200);
        this.maxDate = this._formatDate(maxDate.getMonth(), 
            maxDate.getDate(), maxDate.getFullYear());
    } else {
        maxDate = this._convertStringToDate(this.maxDate);
    }             
  
    // Initialize the days of the week.
    if (this.weekDays == null) {
        this.weekDays = new Array();
        this.weekDays[0] = this._theme.getMessage("CalendarMonth.weekdaySun");
        this.weekDays[1] = this._theme.getMessage("CalendarMonth.weekdayMon");
        this.weekDays[2] = this._theme.getMessage("CalendarMonth.weekdayTue");                
        this.weekDays[3] = this._theme.getMessage("CalendarMonth.weekdayWed");
        this.weekDays[4] = this._theme.getMessage("CalendarMonth.weekdayThu");
        this.weekDays[5] = this._theme.getMessage("CalendarMonth.weekdayFri");
        this.weekDays[6] = this._theme.getMessage("CalendarMonth.weekdaySat");
    }           
    
    // Get the first day of week for that particular locale.
    if (this.firstDayOfWeek == null) {
        this.firstDayOfWeek = parseInt(this._theme.getMessage("calendar.firstDayOfWeek"));
    }
    
    // This will append a localized string along with the
    // today's date
    if (this.todayDateMsg == null) {        
        var d = new Date();
        var todayDateMsg = this._theme.getMessage("CalendarMonth.todayIs");
        
        // Remove the "$0" argument used for the server side param
        var index = todayDateMsg.indexOf(":");
        this.todayDateMsg = todayDateMsg.substr(0, index+1);
        
        var month = this._theme.getMessage(
                        "calendar." + (d.getMonth()));
        month=month.substr(0,3);
        if (this.dateFormat.indexOf("MM") == 0) {
            this.todayDateMsg += " " + month + " " + d.getDay();
        } else {
            this.todayDateMsg += " " + d.getDay() + " " + month;        
        }
        this.todayDateMsg += ", "+d.getFullYear();
    }

    // Initialize the month menu if one does not exist.
    if (this.monthMenu == null) {                  
        this.monthMenu = {
            id: this.id + ":monthMenu",
            options:this._getMonthOptions(),
            title: this._theme.getMessage("CalendarMonth.selectMonth"),
            widgetType: "dropDown"
        };                  
    }
    
    // Initialize the year menu if one does not exist.
    if (this.yearMenu == null) {
        this.yearMenu = {
            id: this.id + ":yearMenu",
            options: this._getYearOptions(minDate.getYear(), maxDate.getYear()),
            title: this._theme.getMessage("CalendarMonth.selectYear"),
            widgetType: "dropDown"
        };          
    }
    return this._inherited("_postCreate", arguments);
};

/**
 * This function is used to obtain the Date object from the given string
 * date value
 *
 * @param {String} inputDate The date to be converted into Dataee object
 * @param {boolean} yearCheck Check whether the year falls within the specified range
 * @return {Object}  The Date object corresponding to the input String
 * @private
 */
@JS_NS@.widget.calendar.prototype._convertStringToDate = function(inputDate, yearCheck) {   
    if (inputDate == "") {
        property = null;
        return false;
    }
    
    var pattern = this.dateFormat;
    var yearIndex = pattern.indexOf("yyyy");
    var monthIndex = pattern.indexOf("MM");
    var dayIndex = pattern.indexOf("dd");

    // If the format is invalid, set the current value to null
    if (yearIndex < 0 || monthIndex < 0 || dayIndex < 0) {
        return null;
    } 
    
    var counter = 0;
    var number;
    var selectedDate = new Date();
    var found = 0;
    var dateString;

    while (counter < inputDate.length) {
        if (counter == yearIndex) {
            try {
                number = parseInt(inputDate.substr(counter, 4));
                if (isNaN(number)) {
                    property = null;
                    return false;
                }                
                // Check if the input date's year range is inbetween the 
                // allowed dates.   
                if (yearCheck == true) {
                    var index = 0;
                    var foundYear = false;                               
                    yearMenu = this._widget.getWidget(this.yearMenu.id).getSelectElement();
                    while (index < yearMenu.length) {
                        if (number == yearMenu.options[index].value) {
                            selectedDate.setFullYear(number);
                            ++found;
                            foundYear = true;
                            break;
                        }
                        index++;
                    }
                    if (!foundYear) {
                        break;
                    }
                } else {            
                    selectedDate.setFullYear(number);
                    ++found;
                }                    
            } catch(e) {}
        } else if (counter == monthIndex) {
            try {    
                dateString = inputDate.substr(counter, 2);
                // This is a workaround for Firefox! 
                // parseInt() returns 0 for values 08 and 09
                // while all other leading zeros work.
                if (dateString.charAt(0) == '0') {
                    dateString = dateString.substr(1, 1);
                }
                number = parseInt(dateString);
                if (isNaN(number)) {
                    property = null;
                    return false;
                }
                selectedDate.setMonth(number-1);
                ++found;
            } catch(e) {}
        } else if (counter == dayIndex) {
            try {
                dateString = inputDate.substr(counter, 2);
                // This is a workaround for Firefox! 
                // parseInt() returns 0 for values 08 and 09
                // while all other leading zeros work.
                if (dateString.charAt(0) == '0') {
                    dateString = dateString.substr(1, 1);
                }
                number = parseInt(dateString);
                if (isNaN(number)) {
                    return null;
                }
                selectedDate.setDate(number);
                ++found;
            } catch(e) {}
        }
        ++counter;
    }

    if (found == 3) {
        return selectedDate;
    } else {
        return null;
    }    
    return true;       
};

/**
 * Helper function to set the initial focus on the menus.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.calendar.prototype._setInitialFocus = function() {    
    var pattern = new String(this.dateFormat);
    var yearIndex = pattern.indexOf("yyyy");
    var monthIndex = pattern.indexOf("MM");
    
    // Moving the year menu around based on the date format is not supported yet.
    // So, the code for setting focus on the year menu has been commented out.
    // if (yearIndex < monthIndex) {        
    //    var yearMenu = document.getElementById(this.calendarMonth.yearMenu.id).getSelectElement();
    //    yearMenu.focus();                 
    // } else {
        var monthMenu = this._widget.getWidget(this.monthMenu.id).getSelectElement();          
        monthMenu.focus();
    // }
    return true;
};

/**
 * Set the value of an HTML select element, but limit value to min and max.
 *
 * @param {Node} select The HTML select element.
 * @param {String} value The selected value.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.calendar.prototype._setLimitedSelectedValue = function(select, value) {
    var min = select.options[0].value;
    var max = select.options[select.length - 1].value;
    if (value < min) {        
        select.value = min;
    } else if ( value > max) {        
        select.value = max;
    } else {
        this._setSelectedValue(select, value);        
    }
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
@JS_NS@.widget.calendar.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.        
    if (props.todayDateMsg) {
        // NOTE: If you set this value manually, text must be HTML escaped.
        this._widget._addFragment(this._todayDateContainer, props.todayDateMsg);
    }

    if (props.spacerImage) {
        if (!this._widget.getWidget(this.spacerImage.id)) {
            this._widget._addFragment(this._spacerImageContainer, props.spacerImage);
        }
    }

    if (props.topLeftImage) {
        if (!this._widget.getWidget(this.topLeftImage.id)) {
            this._widget._addFragment(this._topLeftImageContainer, props.topLeftImage);
        }
    }

    if (props.topRightImage) {
        if (!this._widget.getWidget(this.topRightImage.id)) {
            this._widget._addFragment(this._topRightImageContainer, props.topRightImage);
        }
    }

    if (props.date) {
        var selDate = this._convertStringToDate(props.date, true);
        if (selDate != null) {
            this.currentValue = selDate;
        }
    }

    // Set close link properties.
    if (props.closeButtonLink) {
        // Set properties.
        props.closeButtonLink.onClick = 
            "@JS_NS@.widget.common.getWidget('" + this.id + "')._toggleCalendar();return false;";
        props.closeButtonLink.onKeyDown = 
            "@JS_NS@.widget.common.getWidget('" + this.id + "')._closeCalendar(event);return false;";       

        // Update/add fragment.
        this._widget._updateFragment(this._closeButtonContainer, 
            this.closeButtonLink.id, props.closeButtonLink);
    }

    // Set decrease link properties.
    if (props.decreaseLink) {
        // Set properties.
        props.decreaseLink.onClick = 
            "@JS_NS@.widget.common.getWidget('" + this.id + "')._decreaseMonth();return false;";

        // Update/add fragment.
        this._widget._updateFragment(this._previousLinkContainer, 
            this.decreaseLink.id, props.decreaseLink);
    }

    // Set increase link properties.
    if (props.increaseLink) {
        // Set properties.
        props.increaseLink.onClick = 
            "@JS_NS@.widget.common.getWidget('" + this.id + "')._increaseMonth();return false;";

        // Update/add fragment.
        this._widget._updateFragment(this._nextLinkContainer, this.increaseLink.id, 
            props.increaseLink);
    }
    
    var minDate = null;
    var maxDate = null;    
    if (props.minDate || props.maxDate) {
        if (props.minDate) {
            
            // Convert the given string to a proper given date format pattern
            // and then store it.
            minDate = this._convertStringToDate(props.minDate);
            if (minDate != null) {
                this.minDate = this._formatDate(minDate.getMonth(), 
                    minDate.getDate(), minDate.getFullYear());
            }
        } 
        if (props.maxDate) {
            // Convert the given string to a proper given date format pattern
            // and then store it.            
            maxDate = this._convertStringToDate(props.maxDate);      
            if (maxDate != null) {
                this.maxDate = this._formatDate(maxDate.getMonth(), 
                    maxDate.getDate(), maxDate.getFullYear());
            }               
        } 
        
        //Recalculate the year options with new minDate and maxDate values.
        props.yearMenu = {
            id: this.id + ":yearMenu",
            options:this._getYearOptions(minDate.getFullYear(), maxDate.getFullYear()),
            title: this._theme.getMessage("CalendarMonth.selectYear"),
            widgetType: "dropDown"
        };  
        
        // update the value of yearMenu
        this.yearMenu = props.yearMenu;                                          
    }
        
    // Set month menu properties
    if (props.monthMenu) {                        
        // Set properties.
        props.monthMenu.onChange =
            "@JS_NS@.widget.common.getWidget('" + this.id + "')._updateMonth(false);return false;";
                         
        // Update/add fragment.
        this._widget._updateFragment(this._monthMenuContainer, this.monthMenu.id,
            props.monthMenu);
    }

    // Set year menu properties.
    if (props.yearMenu) {        
        // Set properties.
        props.yearMenu.onChange =
            "@JS_NS@.widget.common.getWidget('" + this.id + "')._updateMonth(false);return false;";

        // Update/add fragment.
        this._widget._updateFragment(this._yearMenuContainer, this.yearMenu.id,
            props.yearMenu);
    }

    // Set toggle link properties.
    if (props.disabled != null) {
        this.disabled = new Boolean(props.disabled).valueOf(); 
    }

    // If the popup calendar is still being shown, prevent disabling of the calendar.
    // The widget can only be disabled if the popup calendar is not shown.
    if (props.toggleLink || 
        (props.disabled != null && this._calendarContainer.style.display != "block")) {

        // Ensure property exists so we can call setProps just once.
        if (props.toggleLink == null) {
            props.toggleLink = {}; // Avoid updating all props using "this" keyword.
        }

        // Set properties.
        props.toggleLink.disabled = this.disabled;
        props.toggleLink.onClick =
            "@JS_NS@.widget.common.getWidget('" + this.id + "')._toggleCalendar();return false;";

        // Update/add fragment.
        this._widget._updateFragment(this._linkNode, this.toggleLink.id, props.toggleLink); 
    }

    // Set more properties.
    this._setCommonProps(this._domNode, props);

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};

/**
 * This function is used to set the value of a select element.
 *
 * @param {Node} select The HTML select element.
 * @param {String} value The selected value.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.calendar.prototype._setSelectedValue = function(select, value) {
    for (var i = 0; i < select.length; i++) {
        if (select.options[i].value == value) {
            select.selectedIndex = i;
            return true;
        }
    }
    select.selectedIndex = -1;
    return true;
};

/**
 * Process toogle event.
 *
 * @return {boolean} false to cancel JavaScript event.
 * @private
 */
@JS_NS@.widget.calendar.prototype._toggleCalendar = function() {
    var topic = (this._calendarContainer.style.display != "block")
        ? @JS_NS@.widget.calendar.event.toggle.openTopic
        : @JS_NS@.widget.calendar.event.toggle.closeTopic;

    // Publish an event for other widgets to listen for.
    //
    // Note: This must be done before the calendar is opened so user
    // input can be applied to the current date.
    this._publish(@JS_NS@.widget.calendar.event.toggle.openTopic, [{
        id: this.id
    }]);

    // Open the calendar.
    if (this._calendarContainer.style.display != "block") {
        if (@JS_NS@.widget.calendar.activeCalendarId != null) {
            var cal = this._widget.getWidget(@JS_NS@.widget.calendar.activeCalendarId);
            cal._toggleCalendar();
        }
        @JS_NS@.widget.calendar.activeCalendarId = this.id;        
        this._calendarContainer.style.display = "block";
        this._setInitialFocus();
        this._updateMonth(true);    
    } else {
        // Hide the calendar popup
        this._calendarContainer.style.display = "none";
        @JS_NS@.widget.calendar.activeCalendarId = null;
    }

    // Test for IE 
    if (@JS_NS@._base.browser._isIe5up()) {
        this._ieStackingContextFix();
    }          
    return false;
};

/**
 * This function is used to update the calendar month.
 * It is called when the calendar is opened, the next or previous
 * links are clicked, or the month or year menus are changed.
 *
 * @param {boolean} initialize Flag indicating to initialze the year and month menus
 * with the current value. The value is true only when the calendar is opened.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.calendar.prototype._updateMonth = function(initialize) {
    // Remove all the nodes of <tbody> before cloning its children.
    this._widget._removeChildNodes(this._tbodyContainer);    
    // Add week days
    this._addWeekDays();    
    
    // Add days of the month
    this._addDaysInMonth(this.currentValue, initialize);  
    return true;     
};
