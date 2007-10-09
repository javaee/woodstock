// widget/calendar.js
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
 * @name widget/calendar.js
 * @version @THEME_VERSION@
 * @overview This module contains classes and functions for the calendar widget.
 * @example The following code is used to create a calendar widget.
 * <p><code>
 * var widget = new webui.@THEME@.widget.calendar(props, domNode);
 * </code></p>
 */
dojo.provide("webui.@THEME@.widget.calendar");

dojo.require("webui.@THEME@.browser");
dojo.require("webui.@THEME@.widget.widgetBase");

/**
 * This function is used to construct a template based widget.
 *
 * @name webui.@THEME@.widget.calendar
 * @inherits webui.@THEME@.widget.widgetBase
 * @constructor
 */
dojo.declare("webui.@THEME@.widget.calendar", webui.@THEME@.widget.widgetBase, {
    // Set defaults.
    widgetName: "calendar" // Required for theme properties.
});

/**
 * Helper function to add a day link in a cell.
 *
 * @param {Node} rowNodeClone
 * @param {Object} day
 * @param {String} id
 * @param {String} className
 * @param {boolean} setFocus
 */
webui.@THEME@.widget.calendar.prototype.addDayLink = function(rowNodeClone, day, 
        id, className, setFocus) {
    // Clone <td> and <a> elements. 
    var colNodeClone = this.dayColumnContainer.cloneNode(false);
    rowNodeClone.appendChild(colNodeClone);    
    var linkNodeClone = this.dayLinkContainer.cloneNode(false);            
    colNodeClone.appendChild(linkNodeClone);
    
    // Format the date.      
    var formattedDate = this.formatDate(day.getMonth() + 1, day.getDate(), day.getFullYear()); 
  
    // set the link's properties for this day.
    linkNodeClone.title = formattedDate;
    linkNodeClone.id = id;
    linkNodeClone.href = "#";
    linkNodeClone.className = className;

    // NOTE: If you set this value manually, text must be HTML escaped.
    this.widget.addFragment(linkNodeClone, "" + day.getDate());

    var widgetId = this.id;
    linkNodeClone.onclick = function() { 
        dijit.byId(widgetId).daySelected(formattedDate); 
        return false;
    };  
    
    // If the setFocus is set to true, then when you tab out of the linkNode,
    // the focus should go to the close button. 
    if (setFocus) {
        var node = dijit.byId(linkNodeClone.id);        
        linkNodeClone.onkeydown = function(event) {
            var widget = dijit.byId(widgetId);
            
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
        }
    }
    return true;
}  

/**
 * Helper function to add days in the month -- week data rows.
 *
 * @param {Object} currentValue The current value of the text field.
 * @param {boolean} initialize Flag indicating to initialze the year and month menus
 * with the current value. The value is true only when the calendar is opened. 
 */
webui.@THEME@.widget.calendar.prototype.addDaysInMonth = function(currentValue, initialize) {
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
    var monthMenuWidget = dijit.byId(this.monthMenu.id);        
    var yearMenuWidget = dijit.byId(this.yearMenu.id);
    if (monthMenuWidget == null || yearMenuWidget == null) {
        return;
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
         this.setLimitedSelectedValue(monthMenuWidget.getSelectElement(), showMonth);
         this.setLimitedSelectedValue(yearMenuWidget.getSelectElement(), showYear);
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
    var rowNodeClone = this.weekRowContainer.cloneNode(false);
    this.tbodyContainer.appendChild(rowNodeClone); 
    rowNodeClone.id = this.id + ":row" + rowNum;
    
    // Convert to javascript month numbering.
    month--;
    
    // Calculate the first of the main month to display in "first" row.
    var first = new Date(year, month, 1);                         
    var firstDay = first.getDay();    
    var className = this.theme.getClassName("DATE_TIME_OTHER_LINK");
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
            this.addDayLink(rowNodeClone, day, linkId, className);
            day = new Date(day.getTime() + oneDayInMs);
            column++;
            linkNum++;            
        }
    }

    // Add any cells in the first row of the main month.
    while (column < 7) {
        // Set appropriate class name.
        if (day.getDate() == selected) {
            className = this.theme.getClassName("DATE_TIME_BOLD_LINK");
        } else if (day.getDate() == today) {
            className = this.theme.getClassName("DATE_TIME_TODAY_LINK");
        } else {
           className = this.theme.getClassName("DATE_TIME_LINK");
        }
            
        linkId = id + linkNum;
        this.addDayLink(rowNodeClone, day, linkId, className);        
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
        rowNodeClone = this.weekRowContainer.cloneNode(false);
        this.tbodyContainer.appendChild(rowNodeClone); 
        rowNodeClone.id = this.id + ":row" + rowNum;

        column = 0;
        while (column < 7 && day.getDate() != 1) {            
            // Set appropriate class name.
            if (day.getDate() == selected) {
                className = this.theme.getClassName("DATE_TIME_BOLD_LINK");
            } else if (day.getDate() == today) {
                className = this.theme.getClassName("DATE_TIME_TODAY_LINK");
            } else {
                className = this.theme.getClassName("DATE_TIME_LINK");
            }
                 
            linkId = id + linkNum;
            var tmpDate = new Date(day.getTime() + oneDayInMs);
            
            // Check whether this is the last date in the calendar and if so
            // set the setFocus variable to true. This will mean that when the
            // user tabs away from this day, the focus will be set on the
            // close button.
            if (tmpDate.getDate() == 1 && column == 6) {
                setFocus = true;
            } else {
                setFocus = false;
            }
            this.addDayLink(rowNodeClone, day, linkId, className, setFocus);
            day = tmpDate;
            column++;
            linkNum++;
        }
    }
    
    // Add any cells in the last row of the following month
    while (column < 7) {
        var className = this.theme.getClassName("DATE_TIME_OTHER_LINK");
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
        this.addDayLink(rowNodeClone, day, linkId, className, setFocus);                    
        day = new Date(day.getTime() + oneDayInMs);
        column++;
        linkNum++;
    }
    return true;
}

/**
 * Helper function to add the week day headers row.
 */
webui.@THEME@.widget.calendar.prototype.addWeekDays = function() {            
    var colNodeClone;
    var spanNodeClone;    
    var firstDay = this.firstDayOfWeek - 1;
    
    // Clone the <tr> node and append it to <tbody>
    var rowNodeClone = this.weekDayRow.cloneNode(false);
    this.tbodyContainer.appendChild(rowNodeClone);
        
    for (var i = 0; i < 7; i++) {
        // Clone the <th> node and append it to <tr>
        colNodeClone = this.weekDayColumn.cloneNode(false);
        rowNodeClone.appendChild(colNodeClone)
               
        // Clone the <span> node and append it to <th>
        spanNodeClone = this.weekDayContainer.cloneNode(false);
        colNodeClone.appendChild(spanNodeClone);
        
        // NOTE: If you set this value manually, text must be HTML escaped.
        this.widget.addFragment(spanNodeClone, this.weekDays[firstDay]);

        firstDay++;
        if (firstDay == 7) {
            firstDay = 0;
        }     
    }
    return true;
}

/**
 * Close the calendar if the enter key is pressed and set the initial focus
 *
 * @param {Event} event The JavaScript event.
 */            
webui.@THEME@.widget.calendar.prototype.closeCalendar = function(event) {
    var evt = (event) ? event : ((window.event) ? window.event : null);
     
    // If key pressed and enter, then close the menu
    if ((evt.type == "keydown") && (evt.keyCode == 13)) {
        this.toggleCalendar();
        document.getElementById(this.toggleLink.id).focus();        
        return false;
    } 
    this.setInitialFocus();
    return true;    
}

/**
 * Process day selected event.
 * <p>
 * When a day link is selected, an event is published which the 
 * calendarField widget will use to update its text field.
 * </p>
 *
 * @param {String} Formatted date string.
 */
webui.@THEME@.widget.calendar.prototype.daySelected = function(formattedDate) {
    this.toggleCalendar();    
    dojo.publish(webui.@THEME@.widget.calendar.event.day.selectedTopic, [{
        id: this.id,
        date:formattedDate
    }]);
    return false;
}

/**
 * This function is used to decrease the month by one.
 */
webui.@THEME@.widget.calendar.prototype.decreaseMonth = function() {
    var monthMenu = dijit.byId(this.monthMenu.id).getSelectElement();
    // If the monthMenu has no value, set it to January (that's what
    // it will have appeared like in the browser). Can happen on IE.  
    if (monthMenu.value == null) {
        monthMenu.value = monthMenu.options[0].value;
    }
    
    var month = parseInt(monthMenu.value);
    if (month == 1) {
        var yearMenu = dijit.byId(this.yearMenu.id).getSelectElement();        
         if (yearMenu.value == null) {
             // If the yearMenu has no value, set it to the first available year            
             // (that's what it will have appeared like in the browser). Can happen on IE.
             yearMenu.value = yearMenu.options[0].value;
         } else if (yearMenu.value == yearMenu.options[0].value) {
             // No need to update the calendar in this case,
             // we don't change anything.
             return;           
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
    return this.updateMonth(false);
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
webui.@THEME@.widget.calendar.prototype.event =
        webui.@THEME@.widget.calendar.event = {
    /**
     * This closure contains day event topics.
     * @ignore
     */
    day: {
        /** Day event topic for custom AJAX implementations to listen for. */
        selectedTopic: "webui_@THEME@_widget_calendar_event_selected"
    },

    /**
     * This closure contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_calendar_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_calendar_event_refresh_end"
    },

    /**
     * This closure contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_calendar_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_calendar_event_state_end"
    },

    /**
     * This closure contains toggle event topics.
     * @ignore
     */
    toggle: {
        /** Open event topic for custom AJAX implementations to listen for. */
        openTopic: "webui_@THEME@_widget_calendar_event_toggle_open",

        /** Close event topic for custom AJAX implementations to listen for. */
        closeTopic: "webui_@THEME@_widget_calendar_event_toggle_close"
    }
}

/**
 * Helper function to format the date.
 *
 * @param {String} month
 * @param {String} day
 * @param {String} year
 */
webui.@THEME@.widget.calendar.prototype.formatDate = function(month, day, year) {
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
}

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 */
webui.@THEME@.widget.calendar.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);
    
    // Set properties.
    if (this.todayDateMsg) { props.todayDateMsg = this.todayDateMsg; }
    if (this.spacerImage) { props.spacerImage = this.spacerImage; }
    if (this.topLeftImage) { props.topLeftImage = this.topLeftImage; }
    if (this.topRightImage) { props.topRightImage = this.topRightImage; }
    if (this.closeButtonLink) { props.closeButtonLink = this.closeButtonLink; }
    if (this.increaseLink) { props.increaseLink = this.increaseLink; }
    if (this.decreaseLink) { props.decreaseLink = this.decreaseLink; }
    if (this.monthMenu) { props.monthMenu = this.monthMenu; }
    if (this.yearMenu) { props.yearMenu = this.yearMenu; }   
    if (this.firstDayOfWeek) { props.firstDayOfWeek = this.firstDayOfWeek; }
    if (this.toggleLink) { props.toggleLink = this.toggleLink; }
    if (this.weekDays) { props.weekDays = this.weekDays; }    
    
    return props;
}

/**
 * Workaround IE bug where popup calendar appears under other components
 */
webui.@THEME@.widget.calendar.prototype.ieStackingContextFix = function() {
    var div = this.calendarContainer;
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
        this.ieShowShim();
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
        this.ieHideShim();
    }
    return true;
}

/**
 * Hides components unaffected by z-index
 */
webui.@THEME@.widget.calendar.prototype.ieShowShim = function() {  
    var popup = this.calendarContainer;
    var shim = this.shimContainer;

    shim.style.position = "absolute";
    shim.style.left = popup.style.left;
    shim.style.top = popup.style.top;
    shim.style.width = popup.offsetWidth;
    shim.style.height = popup.offsetHeight;
    shim.style.zIndex = popup.currentStyle.zIndex - 1;
    shim.style.display = "block";

    return true;
}

/**
 * Hide the shim iframe
 */
webui.@THEME@.widget.calendar.prototype.ieHideShim = function() {
    var shim = this.shimContainer;
    shim.style.display = "none";
    return true;
}   

/**
 * This function is used to increment the current month.
 */
webui.@THEME@.widget.calendar.prototype.increaseMonth = function() {            
    var monthMenu = dijit.byId(this.monthMenu.id).getSelectElement();          
    
    // If the monthMenu has no value, set it to January (that's what
    // it will have appeared like in the browser). Can happen on IE. 
    if (monthMenu.value == null) {
        monthMenu.value = monthMenu.options[0].value;
    }
    
    var month = parseInt(monthMenu.value);
    if (month == 12) {
        var yearMenu = dijit.byId(this.yearMenu.id).getSelectElement();
        var numOptions = yearMenu.options.length;
        if (yearMenu.value == null) {
            // If the yearMenu has no value, set it to the first available year            
            // (that's what it will have appeared like in the browser). Can happen on IE.
            yearMenu.value = yearMenu.options[0].value;
        } else if (yearMenu.value == yearMenu.options[numOptions-1].value) {
            // No need to update the calendar in this case,
            // we don't change anything.
            return;            
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
    return this.updateMonth(false);    
}

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 */
webui.@THEME@.widget.calendar.prototype.postCreate = function () {
    // Set ids.
    if (this.id) {
        this.calendarMenuContainer.id = this.id + "_calendarMenuContainer";
        this.linkNode.id = this.id + "_linkNodeContainer";
        this.todayDateContainer.id = this.id + "_todayDateContainer";
        this.closeButtonContainer.id = this.id + "_closeButtonContainer";
        this.previousLinkContainer.id = this.id + "_previousLinkContainer";
        this.monthMenuContainer.id = this.id + "_monthMenuContainer";
        this.nextLinkContainer.id = this.id + "_nextLinkContainer";
        this.yearMenuContainer.id = this.id + "_yearMenuContainer";
        this.shimContainer.id = this.id + "_shim";
    }

    // If disabledImage is null, create images from the theme.
    // When the _setProps() function is called, image widgets will be
    // instantiated via the props param. 
    if (this.toggleLink.disabledImage == null) {
	this.toggleLink.disabledImage = this.widget.getImageProps(
            "CALENDAR_BUTTON_DISABLED", {
            id: this.id + "_disabledImage", border: 0
        });
    }  
    return this.inherited("postCreate", arguments);
}

/**
 * This function is used to set the current value by parsing the field value.
 *
 * @param {String} curDate The current date.
 */
webui.@THEME@.widget.calendar.prototype.setCurrentValue = function(curDate) {   
    if (curDate == "") {
        this.currentValue = null;
        return false;
    }

    var pattern = new String(this.dateFormat);
    var yearIndex = pattern.indexOf("yyyy");
    var monthIndex = pattern.indexOf("MM");
    var dayIndex = pattern.indexOf("dd");

    // If the format is invalid, set the current value to null
    if (yearIndex < 0 || monthIndex < 0 || dayIndex < 0) {
        this.currentValue = null;
        return false;
    } 
    
    var counter = 0;
    var number;
    var selectedDate = new Date();
    var found = 0;
    var dateString;

    while (counter < curDate.length) {
        if (counter == yearIndex) {
            try {
                number = parseInt(curDate.substr(counter, 4));
                if (isNaN(number)) {
                    this.currentValue = null;
                    return false;
                }                
                var index = 0;
                var foundYear = false;               
                yearMenu = dijit.byId(this.yearMenu.id).getSelectElement();
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
            } catch(e) {}
        } else if (counter == monthIndex) {
            try {    
                dateString = curDate.substr(counter, 2);
                // This is a workaround for Firefox! 
                // parseInt() returns 0 for values 08 and 09
                // while all other leading zeros work.
                if (dateString.charAt(0) == '0') {
                    dateString = dateString.substr(1, 1);
                }
                number = parseInt(dateString);
                if (isNaN(number)) {
                    this.currentValue = null;
                    return false;
                }
                selectedDate.setMonth(number-1);
                ++found;
            } catch(e) {}
        } else if (counter == dayIndex) {
            try {
                dateString = curDate.substr(counter, 2);
                // This is a workaround for Firefox! 
                // parseInt() returns 0 for values 08 and 09
                // while all other leading zeros work.
                if (dateString.charAt(0) == '0') {
                    dateString = dateString.substr(1, 1);
                }
                number = parseInt(dateString);
                if (isNaN(number)) {
                    this.currentValue = null;
                    return false;
                }
                selectedDate.setDate(number);
                ++found;
            } catch(e) {}
        }
        ++counter;
    }

    if (found == 3) {
        this.currentValue = selectedDate;
    } else {
        this.currentValue = null;
    }    
    return true;       
}

/**
 * Helper function to set the initial focus on the menus.
 */
webui.@THEME@.widget.calendar.prototype.setInitialFocus = function() {    
    var pattern = new String(this.dateFormat);
    var yearIndex = pattern.indexOf("yyyy");
    var monthIndex = pattern.indexOf("MM");
    
    // Moving the year menu around based on the date format is not supported yet.
    // So, the code for setting focus on the year menu has been commented out.
    // if (yearIndex < monthIndex) {        
    //    var yearMenu = document.getElementById(this.calendarMonth.yearMenu.id).getSelectElement();
    //    yearMenu.focus();                 
    // } else {
        var monthMenu = dijit.byId(this.monthMenu.id).getSelectElement();          
        monthMenu.focus();
    // }
    return true;
}

/**
 * Set the value of an HTML select element, but limit value to min and max.
 *
 * @param {Node} select The HTML select element.
 * @param {String} value The selected value.
 */
webui.@THEME@.widget.calendar.prototype.setLimitedSelectedValue = function(select, value) {
    var min = select.options[0].value;
    var max = select.options[select.length - 1].value;
    if (value < min) {        
        select.value = min;
    } else if ( value > max) {        
        select.value = max;
    } else {
        this.setSelectedValue(select, value);        
    }
    return true;
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
 * @config {String} [className] CSS selector.
 * @config {Object} [closeButtonLink] 
 * @config {String} [date] 
 * @config {String} [dateFormat] 
 * @config {Object} [decreaseLink] 
 * @config {String} [id] Uniquely identifies an element within a document.
 * @config {Object} [increaseLink] 
 * @config {Object} [monthMenu]
 * @config {String} [style] Specify style rules inline.
 * @config {Object} [todayDateMsg]
 * @config {Object} [toggleLink]
 * @config {boolean} [visible] Hide or show element.
 * @config {Object} [yearMenu]
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 */
webui.@THEME@.widget.calendar.prototype.setProps = function(props, notify) {
    // Note: This function is overridden for JsDoc.
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
webui.@THEME@.widget.calendar.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.        
    if (props.todayDateMsg) {
        // NOTE: If you set this value manually, text must be HTML escaped.
        this.widget.addFragment(this.todayDateContainer, props.todayDateMsg);
    }

    if (props.spacerImage) {
        if (!dijit.byId(this.spacerImage.id)) {
            this.widget.addFragment(this.spacerImageContainer, props.spacerImage);
        }
    }

    if (props.topLeftImage) {
         if (!dijit.byId(this.topLeftImage.id)) {
            this.widget.addFragment(this.topLeftImageContainer, props.topLeftImage);
        }
    }

    if (props.topRightImage) {
        if (!dijit.byId(this.topRightImage.id)) {
            this.widget.addFragment(this.topRightImageContainer, props.topRightImage);
        }
    }

    if (props.date) {
        this.setCurrentValue(props.date);
    }

    // Set close link properties.
    if (props.closeButtonLink) {
        // Set properties.
        props.closeButtonLink.id = this.closeButtonLink.id; // Required for updateFragment().
        props.closeButtonLink.onClick = 
            "dijit.byId('" + this.id + "').toggleCalendar();return false;";
        props.closeButtonLink.onKeyDown = 
            "dijit.byId('" + this.id + "').closeCalendar(event);return false;";       

        // Update/add fragment.
        this.widget.updateFragment(this.closeButtonContainer, props.closeButtonLink);
    }

    // Set decrease link properties.
    if (props.decreaseLink) {
        // Set properties.
        props.decreaseLink.id = this.decreaseLink.id; // Required for updateFragment().
        props.decreaseLink.onClick = 
            "dijit.byId('" + this.id + "').decreaseMonth();return false;";

        // Update/add fragment.
        this.widget.updateFragment(this.previousLinkContainer, props.decreaseLink);
    }

    // Set increase link properties.
    if (props.increaseLink) {
        // Set properties.
        props.increaseLink.id = this.increaseLink.id; // Required for updateFragment().
        props.increaseLink.onClick = 
            "dijit.byId('" + this.id + "').increaseMonth();return false;"

        // Update/add fragment.
        this.widget.updateFragment(this.nextLinkContainer, props.increaseLink);
    }

    // Set month menu properties.
    if (props.monthMenu) {
        // Set properties.
        props.monthMenu.id = this.monthMenu.id; // Required for updateFragment().
        props.monthMenu.onChange =
            "dijit.byId('" + this.id + "').updateMonth(false);return false;";

        // Update/add fragment.
        this.widget.updateFragment(this.monthMenuContainer, props.monthMenu);
    }

    // Set year menu properties.
    if (props.yearMenu) {
        // Set properties.
        props.yearMenu.id = this.yearMenu.id; // Required for updateFragment().
        props.yearMenu.onChange =
            "dijit.byId('" + this.id + "').updateMonth(false);return false;";

        // Update/add fragment.
        this.widget.updateFragment(this.yearMenuContainer, props.yearMenu);
    }

    // Set toggle link properties.
    if (props.disabled != null) {
        this.disabled = new Boolean(props.disabled).valueOf(); 
    }

    // If the popup calendar is still being shown, prevent disabling of the calendar.
    // The widget can only be disabled if the popup calendar is not shown.
    if (props.toggleLink || 
        (props.disabled != null && this.calendarContainer.style.display != "block")) {

        // Ensure property exists so we can call setProps just once.
        if (props.toggleLink == null) {
            props.toggleLink = {}; // Avoid updating all props using "this" keyword.
        }

        // Set properties.
        props.toggleLink.id = this.toggleLink.id; // Required for updateFragment().
        props.toggleLink.disabled = this.disabled;
        props.toggleLink.onClick =
            "dijit.byId('" + this.id + "').toggleCalendar();return false;";

        // Update/add fragment.
        this.widget.updateFragment(this.linkNode, props.toggleLink); 
    }

    // Set more properties.
    this.setCommonProps(this.domNode, props);

    // Set remaining properties.
    return this.inherited("_setProps", arguments);
}

/**
 * This function is used to set the value of a select element.
 *
 * @param {Node} select The HTML select element.
 * @param {String} value The selected value.
 */
webui.@THEME@.widget.calendar.prototype.setSelectedValue = function(select, value) {
    for (var i = 0; i < select.length; i++) {
        if (select.options[i].value == value) {
            select.selectedIndex = i;
            return true;
        }
    }
    select.selectedIndex = -1;
    return true;
}

/**
 * Process toogle event.
 */
webui.@THEME@.widget.calendar.prototype.toggleCalendar = function() {
    var topic = (this.calendarContainer.style.display != "block")
        ? webui.@THEME@.widget.calendar.event.toggle.openTopic
        : webui.@THEME@.widget.calendar.event.toggle.closeTopic;

    // Publish an event for other widgets to listen for.
    //
    // Note: This must be done before the calendar is opened so user
    // input can be applied to the current date.
    dojo.publish(webui.@THEME@.widget.calendar.event.toggle.openTopic, [{
        id: this.id
    }]);

    // Open the calendar.
    if (this.calendarContainer.style.display != "block") {
        if (webui.@THEME@.widget.calendar.activeCalendarId != null) {
            var cal = dijit.byId(webui.@THEME@.widget.calendar.activeCalendarId);
            cal.toggleCalendar();
        }
        webui.@THEME@.widget.calendar.activeCalendarId = this.id;        
        this.calendarContainer.style.display = "block";
        this.setInitialFocus();
        this.updateMonth(true);    
    } else {
        // Hide the calendar popup
        this.calendarContainer.style.display = "none";
        webui.@THEME@.widget.calendar.activeCalendarId = null;
    }

    // Test for IE 
    if (webui.@THEME@.browser.isIe5up()) {
        this.ieStackingContextFix();
    }          
    return false;
}

/**
 * This function is used to update the calendar month.
 * It is called when the calendar is opened, the next or previous
 * links are clicked, or the month or year menus are changed.
 *
 * @param {boolean} initialize Flag indicating to initialze the year and month menus
 * with the current value. The value is true only when the calendar is opened. 
 */
webui.@THEME@.widget.calendar.prototype.updateMonth = function(initialize) {
    // Remove all the nodes of <tbody> before cloning its children.
    this.widget.removeChildNodes(this.tbodyContainer);    
    // Add week days
    this.addWeekDays();    
    // Add days of the month
    this.addDaysInMonth(this.currentValue, initialize);
    
    return true;     
}
