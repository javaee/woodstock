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
        var params;
        if (props.execute) {            
            var id = props.execute;
            var widget = woodstock.widget.common.getWidget(id);
                if (widget != null) {                  
                  params = "&params" + "=" + id;                  
                }
        }
        
        return connection.asyncRequest("/example-html/BubbleServlet", controller.refreshCallback,
            "id=" + props.id + // Widget id.
            "&execute=" + props.execute + // Comma separated list of widget IDs.
            "&event=refresh" + // Event type.
            params); // hyperlink id.
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
        
        // update bubble
        var widget = woodstock.widget.common.getWidget(props.id);   
   
        widget.setProps({            
            title: props.title,
            contents: [props.contents]
        });
        //open bubble
        document.getElementById('ww_id11').open(eventForBubble);
        return true;
    }        
    
};
    
// Listen for Widget events.
woodstock.widget.common.subscribe(woodstock.widget.bubble.event.refresh.beginTopic,
    controller, "processRefreshEvent");
// store the event object for bubble.
var eventForBubble;
// Function to refresh bubble title
function refreshBubble(id, event) {
    // Get table row group.
    var widget = woodstock.widget.common.getWidget("ww_id11");
    eventForBubble = event;
    widget.refresh(id); // Refresh widget 
}
