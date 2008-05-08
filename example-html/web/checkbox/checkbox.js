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

/**
 * This object contains functions to obtain data asynchronously using XHR
 * as the underlying transfer protocol.
 */
var controller = {
    /**
     * Called in response to a checkbox refresh event. Obtain checkbox
     * properties from the server and update the checkbox with those
     * values.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The HTML element Id.
     * @config {String} execute The string containing a comma separated list 
     * of widget ids to post data for.
     * @return {boolean} true if successful; otherwise, false.
     */
    processRefreshEvent: function(props) {
        if (props == null) {
            return false;
        }
        var params = "";
        if (props.execute) {
	    // We expect only one "execute" id, a textfield
	    // for the label value
	    //
            var execute = props.execute.split(",");
            for (var i = 0; i < execute.length; i++) {
                var id = execute[i];
                var widget = woodstock.widget.common.getWidget(id);
                if (widget == null) {
                    continue;
                }
                params += "&label=" + widget.getProps().value;
		break;
            }
        }
	// Send all the checkbox properties back
	//
	var cb = woodstock.widget.common.getWidget(props.id);
	var cbprops = woodstock.json.stringify(cb.getProps());

        return connection.asyncRequest(
		"/example-html/CheckboxServlet", 
		controller.refreshCallback,
		"cbprops=" + cbprops + params);
    },

    /**
     * Called with the XHR response from the checkbox refresh request.
     * The respose contains the updated checkbox properties.
     *
     * @param {Object} xhr XHR Object.
     * @return {boolean} true if successful; otherwise, false.
     */
    refreshCallback: function(xhr) {
        if (xhr == null) {
            return false;
        }

        // Parse JSON text.
        var props = woodstock.json.parse(xhr.responseText);

        // Refresh checkbox
        var widget = woodstock.widget.common.getWidget(props.id);
        widget.setProps(props);

        return true;
    },

    /**
     * Make this call to send the checkbox configuration
     * values to the server.
     *
     * @param {String} id The checbox widget id.
     * @return {boolean} true if successful; otherwise, false.
     */
    configureCheckbox: function(id) {

	var cb = woodstock.widget.common.getWidget(id);
	cb.refresh("ww_tflbl");

	return true;
    }
};
    
// Listen for Widget events.
woodstock.widget.common.subscribe(
	woodstock.widget.checkbox.event.refresh.beginTopic,
	controller, "processRefreshEvent");
