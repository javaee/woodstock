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

dojo.provide("webui.@THEME@.widget.calendar");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.calendar = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);    
}

/**
 * Helper function to add a day link in a cell.
 */
webui.@THEME@.widget.calendar.addDayLink = function(rowNodeClone, day, id, className) {
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
    this.addFragment(linkNodeClone, "" + day.getDate());

    var widgetId = this.id;
    linkNodeClone.onclick = function() { 
        dojo.widget.byId(widgetId).dayClicked(formattedDate); 
        return false;
    };  
}  

/**
 * Helper function to add days in the month -- week data rows.
 *
 * @param currentValue The current value of the text field.
 * @param initialize Flag indicating to initialze the year and month menus
 * with the current value. The value is true only when the calendar is opened. 
 */
webui.@THEME@.widget.calendar.addDaysInMonth = function(currentValue, initialize) {
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
    var monthMenuWidget = dojo.widget.byId(this.monthMenu.id);        
    var yearMenuWidget = dojo.widget.byId(this.yearMenu.id);
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
    if (firstDay == this.firstDayOfWeek - 1) {
        // First cell on first row is the first of the current month
        day = first;
    } else {
        // First cell on first row is in previous month.
        var className = webui.@THEME@.widget.props.calendar.edgeDateClass;
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
            className = webui.@THEME@.widget.props.calendar.selectedClass;
        } else if (day.getDate() == today) {
            className = webui.@THEME@.widget.props.calendar.todayClass;
        } else {
           className = webui.@THEME@.widget.props.calendar.dateClass;
        }
            
        linkId = id + linkNum;
        this.addDayLink(rowNodeClone, day, linkId, className);        
        day = new Date(day.getTime() + oneDayInMs);
        column++;
        linkNum++;
    } 
    
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
                className = webui.@THEME@.widget.props.calendar.selectedClass;
            } else if (day.getDate() == today) {
                className = webui.@THEME@.widget.props.calendar.todayClass;
            } else {
                className = webui.@THEME@.widget.props.calendar.dateClass;
            }
                 
            linkId = id + linkNum;
            this.addDayLink(rowNodeClone, day, linkId, className);
            day = new Date(day.getTime() + oneDayInMs);
            column++;
            linkNum++;
        }
    }
    
    // Add any cells in the last row of the following month
    while (column < 7) {
        var className = webui.@THEME@.widget.props.calendar.edgeDateClass;
        linkId = id + linkNum;
        this.addDayLink(rowNodeClone, day, linkId, className);            
        day = new Date(day.getTime() + oneDayInMs);
        column++;
        linkNum++;
    }        
}

/**
 * Helper function to add the week day headers row.
 */
webui.@THEME@.widget.calendar.addWeekDays = function() {            
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
        this.addFragment(spanNodeClone, this.weekDays[firstDay]);

        firstDay++;
        if(firstDay == 7) {
            firstDay = 0;
        }     
    }        
}

/**
 * This function is called when a day link is selected .
 * It publishes an event which the calendarField widget will listen to and update
 * its text field.
 */
webui.@THEME@.widget.calendar.day = {
    dayPickedEvent: "webui_@THEME@_widget_calendar_day_picked_event",
    processEvent: function(formattedDate) {
        this.toggleCalendar();    
        dojo.event.topic.publish(webui.@THEME@.widget.calendar.day.dayPickedEvent, 
            {id:this.id,
             date:formattedDate});
        return false;
    }
}

/**
 * This function is used to decrease the month by one.
 */
webui.@THEME@.widget.calendar.decreaseMonth = function() {
    var monthMenu = dojo.widget.byId(this.monthMenu.id).getSelectElement();
    // If the monthMenu has no value, set it to January (that's what
    // it will have appeared like in the browser). Can happen on IE.  
    if (monthMenu.value == null) {
        monthMenu.value = monthMenu.options[0].value;
    }
    
    var month = parseInt(monthMenu.value);
    if (month == 1) {
        var yearMenu = dojo.widget.byId(this.yearMenu.id).getSelectElement();        
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
    this.updateMonth(false);
}

/**
 * This function is used to fill a template with widget properties.
 *
 * Note: Anything to be set only once should be added here; otherwise, the
 * setProps() function should be used to set properties.
 */
 webui.@THEME@.widget.calendar.fillInTemplate = function() {
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
    }

    // Set public functions.        
    this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }
    this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }                

    // Set properties.
    return this.setProps(this.getProps());  
}

/**
 * Helper function to format the date.
 */
webui.@THEME@.widget.calendar.formatDate = function(month, day, year) {
    var date = new String(this.dateFormat);      
    date = date.replace("yyyy", new String(year));
    if(month < 10) {
        date = date.replace("MM", "0" + new String(month));
    } else {
        date = date.replace("MM", new String(month));
    }
    if(day < 10) {
        date = date.replace("dd", "0" + new String(day));
    } else {
        date = date.replace("dd", new String(day));
    }
    return date;
}

/**
 * This function is used to get widget properties. Please see
 * webui.@THEME@.widget.calendar.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.calendar.getProps = function() {
    var props = {};
    
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
        
    // Add DOM node properties.    
    Object.extend(props, this.getCoreProps());
    Object.extend(props, this.getCommonProps());
    
    return props;
}

/**
 * This function is used to increment the current month.
 */
webui.@THEME@.widget.calendar.increaseMonth = function() {            
       var monthMenu = dojo.widget.byId(this.monthMenu.id).getSelectElement();          
    
    // If the monthMenu has no value, set it to January (that's what
    // it will have appeared like in the browser). Can happen on IE. 
    if (monthMenu.value == null) {
        monthMenu.value = monthMenu.options[0].value;
    }
    
    var month = parseInt(monthMenu.value);
    if (month == 12) {
        var yearMenu = dojo.widget.byId(widget.yearMenu.id).getSelectElement();
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
    this.updateMonth(false);    
}

/**
 * This function is used to set the current value by parsing the field value.
 */
webui.@THEME@.widget.calendar.setCurrentValue = function(curDate) {   
    if (curDate == "") {
        this.currentValue = null;
        return;
    }

    var pattern = new String(this.dateFormat);
    var yearIndex = pattern.indexOf("yyyy");
    var monthIndex = pattern.indexOf("MM");
    var dayIndex = pattern.indexOf("dd");

    // If the format is invalid, set the current value to null
    if (yearIndex < 0 || monthIndex < 0 || dayIndex < 0) {
        this.currentValue = null;
        return;
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
                    return;
                }                
                var index = 0;
                var foundYear = false;               
                yearMenu = dojo.widget.byId(this.yearMenu.id).getSelectElement();
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
                    return;
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
                    return;
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
    return;       
}

/**
 * Helper function to set the initial focus on the menus.
 */
webui.@THEME@.widget.calendar.setInitialFocus = function() {    
    var pattern = new String(this.dateFormat);
    var yearIndex = pattern.indexOf("yyyy");
    var monthIndex = pattern.indexOf("MM");
    
    // Moving the year menu around based on the date format is not supported yet.
    // So, the code for setting focus on the year menu has been commented out.
    // if(yearIndex < monthIndex) {        
    //    var yearMenu = document.getElementById(this.calendarMonth.yearMenu.id).getSelectElement();
    //    yearMenu.focus();                 
    // } else {
        var monthMenu = dojo.widget.byId(this.monthMenu.id).getSelectElement();          
        monthMenu.focus();
    // }
}

/**
 * Set the value of an HTML select element, but limit value to min and max.
 *
 * @param select The HTML select element.
 * @param value The selected value.
 */
webui.@THEME@.widget.calendar.setLimitedSelectedValue = function(select, value) {
    var min = select.options[0].value;
    var max = select.options[select.length - 1].value;
    if (value < min) {        
        select.value = min;
    } else if ( value > max) {        
        select.value = max;
    } else {
        this.setSelectedValue(select, value);        
    }
    return;
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul>
 *  <li>className</li>
 *  <li>closeButtonLink</li>
 *  <li>dateFormat</li>
 *  <li>decreaseLink</li>
 *  <li>date</li>
 *  <li>id</li>
 *  <li>increaseLink</li>
 *  <li>toggleLink</li>
 *  <li>monthMenu</li>
 *  <li>style</li>
 *  <li>todayDateMsg</li>
 *  <li>visible</li>
 *  <li>yearMenu</li> 
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.calendar.setProps = function(props) {
    if (props == null) {
        return null;
    }

    // Save properties for later updates.
    if (this.isInitialized() == true) {
        this.extend(this, props);
    }
       
    // Set DOM node properties. 
    this.setCoreProps(this.domNode, props);
    this.setCommonProps(this.domNode, props);
        
    if (props.todayDateMsg) {
        // NOTE: If you set this value manually, text must be HTML escaped.
        this.addFragment(this.todayDateContainer, props.todayDateMsg);
    }

    if (props.spacerImage) {
        if (!dojo.widget.byId(this.spacerImage.id)) {
            this.addFragment(this.spacerImageContainer, props.spacerImage);
        }
    }

    if (props.topLeftImage) {
         if (!dojo.widget.byId(this.topLeftImage.id)) {
            this.addFragment(this.topLeftImageContainer, props.topLeftImage);
        }
    }

    if (props.topRightImage) {
        if (!dojo.widget.byId(this.topRightImage.id)) {
            this.addFragment(this.topRightImageContainer, props.topRightImage);
        }
    }

    if (props.date) {
        this.setCurrentValue(props.date);
    }
        
    if (props.closeButtonLink) {
        // Update widget/add fragment.                
        var closeLinkWidget = dojo.widget.byId(this.closeButtonLink.id);        
        if (closeLinkWidget) {
            closeLinkWidget.setProps(props.closeButtonLink);          
        } else {  
            props.closeButtonLink.onClick = 
                "dojo.widget.byId('"+this.id+"').toggleCalendar();return false;";
            this.addFragment(this.closeButtonContainer, props.closeButtonLink);
        }
    }
    
    if (props.decreaseLink) {
        // Update widget/add fragment.                
        var decreaseLinkWidget = dojo.widget.byId(this.decreaseLink.id);
        if (decreaseLinkWidget) {
            decreaseLinkWidget.setProps(props.decreaseLink);          
        } else {  
            props.decreaseLink.onClick = 
                "dojo.widget.byId('"+this.id+"').decreaseMonth();return false;";
            this.addFragment(this.previousLinkContainer, props.decreaseLink);   
        }
    }
    
    if (props.increaseLink) {
        // Update widget/add fragment.                
        var increaseLinkWidget = dojo.widget.byId(this.increaseLink.id);
        if (increaseLinkWidget) {
            increaseLinkWidget.setProps(props.increaseLink);          
        } else { 
            props.increaseLink.onClick = 
                "dojo.widget.byId('"+this.id+"').increaseMonth();return false;"
            this.addFragment(this.nextLinkContainer, props.increaseLink);
        }
    }
    
    if (props.monthMenu) {
        // Update widget/add fragment.                
        var monthMenuWidget = dojo.widget.byId(this.monthMenu.id);
        if (monthMenuWidget) {
            monthMenuWidget.setProps(props.monthMenu);          
        } else {  
            props.monthMenu.onChange =
                "dojo.widget.byId('"+this.id+"').updateMonth(false);return false;";
            this.addFragment(this.monthMenuContainer, props.monthMenu);
        }
    }
    
    if (props.yearMenu) {
        // Update widget/add fragment.                
        var yearMenuWidget = dojo.widget.byId(this.yearMenu.id);
        if (yearMenuWidget) {
            yearMenuWidget.setProps(props.yearMenu);          
        } else {  
            props.yearMenu.onChange =
                "dojo.widget.byId('"+this.id+"').updateMonth(false);return false;";
            this.addFragment(this.yearMenuContainer, props.yearMenu);
        }
    }
    if (props.toggleLink) {
        var linkWidget = dojo.widget.byId(this.toggleLink.id);
        if (linkWidget) {
            linkWidget.setProps(props.toggleLink);
        } else {
            props.toggleLink.onClick = 
                "dojo.widget.byId('"+this.id+"').toggleCalendar();return false;";
            this.addFragment(this.linkNode, props.toggleLink);
        }
    }
    return props; // Return props for subclasses.
}

/**
 * This function is used to set the value of a select element.
 *
 * @param select The HTML select element.
 * @param value The selected value.
 */
webui.@THEME@.widget.calendar.setSelectedValue = function(select, value) {
    for (var i = 0; i < select.length; i++) {
        if (select.options[i].value == value) {
            select.selectedIndex = i;
            return;
        }
    }
    select.selectedIndex = -1;
}

/**
 * Toggle the visible state of calendar.
 * Also publish an event when the calendar is made visible.
 */
webui.@THEME@.widget.calendar.toggleCalendar = {
    // Published when the calendar's  visible state is set to true
    calendarOpenTopic: "webui_@THEME@_widget_calendar_toggleCalendar_open",
    // Published when the calendar's visible state is set to false
    calendarCloseTopic: "webui_@THEME@_widget_calendar_toggleCalendar_close",
    processEvent: function(execute) {
        if (this.calendarContainer.style.display != "block") {        
            this.calendarContainer.style.display = "block";
            this.setInitialFocus();
            // Publish an event for other widgets to listen for.
            dojo.event.topic.publish(
                webui.@THEME@.widget.calendar.toggleCalendar.calendarOpenTopic, {
                    id: this.id,
                    execute: execute
                });
            this.updateMonth(true);    
        } else {
            // Hide the calendar popup
            this.calendarContainer.style.display = "none";
            // publish an event for other widgets to listen for.
            dojo.event.topic.publish(
                webui.@THEME@.widget.calendar.toggleCalendar.calendarCloseTopic, {
                    id: this.id,
                    execute: execute
                });            
        }
            return false;
   }
}

/**
 * This function is used to update the calendar month.
 * It is called when the calendar is opened, the next or previous
 * links are clicked, or the month or year menus are changed.
 *
 * @param currentValue The current value of the text field.
 * @param initialize Flag indicating to initialze the year and month menus
 * with the current value. The value is true only when the calendar is opened. 
 */
webui.@THEME@.widget.calendar.updateMonth = function(initialize) {
    // Remove all the nodes of <tbody> before cloning its children.
    this.removeChildNodes(this.tbodyContainer);    
    // Add week days
    this.addWeekDays();    
    // Add days of the month
    this.addDaysInMonth(this.currentValue, initialize);
    
    return true;     
}
        
// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.calendar, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.calendar, {
    // Set private functions.
    addDayLink: webui.@THEME@.widget.calendar.addDayLink,
    addDaysInMonth: webui.@THEME@.widget.calendar.addDaysInMonth,
    addWeekDays: webui.@THEME@.widget.calendar.addWeekDays,
    dayClicked:webui.@THEME@.widget.calendar.day.processEvent,
    decreaseMonth: webui.@THEME@.widget.calendar.decreaseMonth,
    fillInTemplate: webui.@THEME@.widget.calendar.fillInTemplate,
    formatDate: webui.@THEME@.widget.calendar.formatDate,
    getProps: webui.@THEME@.widget.calendar.getProps,
    increaseMonth: webui.@THEME@.widget.calendar.increaseMonth,
    setCurrentValue: webui.@THEME@.widget.calendar.setCurrentValue,    
    setInitialFocus:webui.@THEME@.widget.calendar.setInitialFocus, 
    setLimitedSelectedValue: webui.@THEME@.widget.calendar.setLimitedSelectedValue,
    setProps: webui.@THEME@.widget.calendar.setProps,
    setSelectedValue: webui.@THEME@.widget.calendar.setSelectedValue,    
    toggleCalendar: webui.@THEME@.widget.calendar.toggleCalendar.processEvent,
    updateMonth: webui.@THEME@.widget.calendar.updateMonth,
       
    // Set defaults.
    templatePath: webui.@THEME@.theme.getTemplatePath("calendar"),
    templateString: webui.@THEME@.theme.getTemplateString("calendar"),
    widgetType: "calendar"
});
