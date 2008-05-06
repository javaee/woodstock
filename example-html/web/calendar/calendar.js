
/**
 * Funtion called by Drop down onSelect event>
 *
 * {String}  id Identifier of drop down widget.
 * {boolean} Return true if successful; otherwise, false.
 *
 * Obtain the selected value from the drop down list,
 * generate an XmlHttpRequest with the selected value as a
 * GET parameter, and provide a callback function to process
 * the returned JSON object to update the Calendar date value.
 *
 * We have hard-coded the URL to our Calendar servlet that
 * returns the date for a given holiday selection.  We use
 * the common connection.js function to wrap the XHR request.
 */
function updateCalendar(id) {

    // Define our XHR information.
    var url = "/example-html/HolidayServlet";
    var widget = woodstock.widget.common.getWidget("list");
    if (widget) {
        var option = widget.getSelectedValue();
	if (option == null) {
	    option = "newyear";
        }
    }
    var params = "holiday=" + option;

    // Make the XHR call, specifying the target URL, the
    // get data query parameters, and our callback function.
    return connection.asyncRequest(url, updateCalendarCallback, params);

}

/**
 * Function to handle XmlHttpRequest callback.
 *
 * (HttpResponse) response The XHR HttpResponse
 * {boolean} Return true if successful; otherwise, false.
 *
 * Retrieve the response as text in JSON literal format, convert
 * to a javascript object, and update the calendar widget by
 * resetting its properties. 
 */
function updateCalendarCallback(response) {

    if (response == null) {
	return false;
    }

    // Retrieve the returned date from the response.
    // Also resets the current date message in the date picker
    // sub-widget.  Update the calendar widget.
    var props = woodstock.json.parse(response.responseText);
    var widget = woodstock.widget.common.getWidget("calendar");
    var date = props.date;
    var today = props.today;
    if (date) {
	widget.setProps({value: date, calendar: {todayDateMsg: today}});
	return true;
    }
}
