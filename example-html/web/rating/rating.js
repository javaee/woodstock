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
     * This function is used to process submit events with Object literals. 
     * <p>
     * Note: Cannot use the _processSubmitEvent() function in the common.js file
     * as we need to override "replaceElement" with a callback functiont o receive
     * the response.
     * </p>
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The HTML element Id.
     * @config {String} endTopic The event topic to publish.
     * @config {String} execute The string containing a comma separated list 
     * of client ids against which the execute portion of the request 
     * processing lifecycle must be run.
     * @return {boolean} true if successful; otherwise, false.
     */
    processSubmitEvent: function(props) {
        if (props == null) {
            return false;
        }       

        this.endTopic = props.endTopic;

        var params = "";
        if (props.execute) {            
            var execute = props.execute.split(",");
            for (var i = 0; i < execute.length; i++) {
                var id = execute[i];
                var widget = woodstock.widget.common.getWidget(id);
                if (widget == null) {
                    continue;
                }
                params += "&" + id + "=" + widget.getProps().value;
            }
        }

        // Actual grade is is hidden field which is typically submitted automatically
        // with the form when using an Ajax framework such as DynaFaces.  Until a similar
        // feature is available in some TBD framework, we manually include the value in the request
        var hiddenFieldID = props.id + "_submitValue";
        var hiddenField = document.getElementById(hiddenFieldID);
        
        return connection.asyncRequest("/example-html/RatingServlet", controller.submitCallback,
            "id=" + props.id + // Widget id.
            "&"+hiddenFieldID + "=" + hiddenField.value +
            "&execute=" + props.execute + // Comma separated list of widget IDs.
            "&event=submit" + // Event type.
            params); // form data
    },

    /**
     * This function is a callback to respond to the end of submit request.
     *
     * @param {Object} httpRequest XHR Object.
     * @return {boolean} true if successful; otherwise, false.

     * @param {String} elementId The HTML element Id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to DynaFaces.fireAjaxTransaction.
     * @param {Object} xjson The xjson argument provided to DynaFaces.fireAjaxTransaction.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    submitCallback: function(httpRequest) {
        if (httpRequest == null) {
            return false;
        }

        // Parse JSON text.
        var props = woodstock.json.parse(httpRequest.responseText);

        // Update rating widget
        var widget = woodstock.widget.common.getWidget(props.id);   
        widget.setProps(props);
            
        // Publish an event for custom AJAX implementations to listen for.
        if (this.controller.endTopic) {
            woodstock.widget.common.publish(this.controller.endTopic, [props]);
        }
        return true;
    },
    
};

function RatingListener(ratingID, textID, sumRatingID) {
    this.ratingID = ratingID;
    this.textID = textID;
    this.sumRatingID = sumRatingID;
};
function OnSubmit(props) {
    if (props.id != this.ratingID)
        return;

    // Get rating widget
    var ratingWidget = woodstock.widget.common.getWidget(this.ratingID);
    if (ratingWidget == null)
        return;

    // Get properties for rating widget
    props = ratingWidget.getProps();

    // Get associated text widget and update with average grade.
    var textWidget = woodstock.widget.common.getWidget(this.textID);
    if (textWidget != null) {
        var s = props.averageGrade.toString();
        textWidget.setProps({"value": s});
    }

    // Return if no rating average summary widget to forward selected grade.
    if (this.sumRatingID == null)
        return;

    // Get rating widget for rating average summary
    var ratingSummaryWidget = woodstock.widget.common.getWidget(this.sumRatingID);
    if (ratingSummaryWidget == null)
        return;

    // Set selected grade on rating average summary and submit so
    // cumulative average grade can be computed.
    ratingSummaryWidget.setProps({"grade": props.grade});
    ratingSummaryWidget.submit();
}
RatingListener.prototype.onSubmit = OnSubmit;

woodstock.widget.common.addOnLoad(function() {
    // Subscribe to post-submit event rating average summary widget
    var listener = new RatingListener("ratingSummary", "textSummary", null);
    woodstock.widget.common.subscribe(
        woodstock.widget.rating.event.submit.endTopic,
        listener, listener.onSubmit);

    // Listen for Widget events.
    woodstock.widget.common.subscribe(woodstock.widget.rating.event.submit.beginTopic,
        controller, "processSubmitEvent");

    for (var i=1; i<=2; i++) {
        widget = woodstock.widget.common.getWidget("rating" + i);
        if (widget == null)
            continue;
        var listener = new RatingListener("rating" + i, "avgtext" + i, "ratingSummary");
        // Subscribe to post-submit event for this rating instance
        woodstock.widget.common.subscribe(
            woodstock.widget.rating.event.submit.endTopic,
            listener, listener.onSubmit);
    }
});
