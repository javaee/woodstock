/**
 * This object contains functions to obtain data asynchronously using XHR
 * as the underlying transfer protocol.
 */
var tableRowGroup = {
    /**
     * This function is used to process scroll events with Object literals.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The widget Id.
     * @config {int} row The first row to be rendered.
     * @return {boolean} true if successful; otherwise, false.
     */
    processScrollEvent: function(props) {
        if (props == null) {
            return false;
        }
/*
        replaceElement: @JS_NS@.widget._jsfx.table2RowGroup._scrollCallback,
        xjson: {
            id: props.id,
            event: "scroll",
            first: props.first
        };
*/
        return true;
    },
    
    /**
     * This function is used to update widgets.
     *
     * @param {String} id The widget Id.
     * @param {String} content The content returned by the AJAX response.
     * @return {boolean} true if successful; otherwise, false.
     */
    scrollCallback: function(id, content) {
        if (id == null || content == null) {
            return false;
        }

        // Parse JSON text.
        var props = woodstock.json.parse(content);

        // Add rows.
        var widget = woodstock.widget.common.getWidget(id);
        widget.addRows(props.rows);
        return true;
    }
};
    
// Listen for Widget events.
woodstock.widget.common.subscribe(woodstock.widget.table2RowGroup.event.scroll.beginTopic,
    tableRowGroup, "processScrollEvent");
