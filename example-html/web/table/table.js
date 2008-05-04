/**
 * This object contains functions to obtain data asynchronously using XHR
 * as the underlying transfer protocol.
 */
var controller = {
    /**
     * This function is used to process refresh events with Object literals.
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
        // To do: Post form data -- not all widgets support value property.
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
        return connection.asyncRequest("/example-html/TableServlet", controller.refreshCallback,
            "id=" + props.id + // Widget id.
            "&execute=" + props.execute + // Comma separated list of widget IDs.
            "&event=filter" + // Event type.
            params); // Form data.
    },

    /**
     * This function is used to process scroll events with Object literals.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The widget Id.
     * @config {int} first The first row to be rendered.
     * @return {boolean} true if successful; otherwise, false.
     */
    processScrollEvent: function(props) {
        if (props == null) {
            return false;
        }
        return connection.asyncRequest("/example-html/TableServlet", controller.scrollCallback,
            "id=" + props.id + // Widget id.
            "&first=" + props.first + // First row to retrieve data.
            "&event=scroll"); // Event type.
    },

    /**
     * This function is used to update widgets.
     *
     * @param {Object} httpRequest XHR Object.
     * @return {boolean} true if successful; otherwise, false.
     */
    refreshCallback: function(httpRequest) {
        if (httpRequest == null) {
            return false;
        }

        // Parse JSON text.
        var props = woodstock.json.parse(httpRequest.responseText);

        // Add rows.
        var widget = woodstock.widget.common.getWidget(props.id);
        widget.setProps({
            rows: props.rows
        });
        return true;
    },

    /**
     * This function is used to update widgets.
     *
     * @param {Object} httpRequest XHR Object.
     * @return {boolean} true if successful; otherwise, false.
     */
    scrollCallback: function(httpRequest) {
        if (httpRequest == null) {
            return false;
        }

        // Parse JSON text.
        var props = woodstock.json.parse(httpRequest.responseText);

        // Add rows.
        var widget = woodstock.widget.common.getWidget(props.id);
        if (widget.getProps().first == 0) {
            widget.setProps({
                rows: props.rows,
                totalRows: props.totalRows
            });
        } else {
            widget.addRows(props.rows);
        }
        return true;
    }
};
    
// Listen for Widget events.
woodstock.widget.common.subscribe(woodstock.widget.table2RowGroup.event.scroll.beginTopic,
    controller, "processScrollEvent");
woodstock.widget.common.subscribe(woodstock.widget.table2RowGroup.event.refresh.beginTopic,
    controller, "processRefreshEvent");

// Function to initialize widgets.
function init() {
    // Get alert widget.
    var widget = woodstock.widget.common.getWidget("ww_id11-group0");
    if (widget == null) {
        return setTimeout("init();", 10);
    }
    // Publish event to populate table via Ajax transaction.
    woodstock.widget.common.publish(woodstock.widget.table2RowGroup.event.scroll.beginTopic, [{
        id: widget.id,
        first: 0
    }]);
}

// Function to filter table data.
function filter() {
    // Get table row group.
    var widget = woodstock.widget.common.getWidget("ww_id11-group0");
    widget.refresh("ww_id12"); // Refresh widget with text field value.
}
