/**
 * This object contains functions to obtain progress data asynchronously using XHR
 * as the underlying transfer protocol.
 */
var controller = {
    
    progressBarId: null,
    
    /**
     * This function is used to process refresh events with Object literals.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The HTML element Id.
     * @config {String} execute The string containing a comma separated list 
     * of widget ids to post data for.
     * @return {boolean} true if successful; otherwise, false.
     */
    processProgressEvent: function(props) {
        if (props == null) {
            return false;
        }
        //assuming the only progress bar on the page
        controller.progressBarId = props.id;
        
        return connection.asyncRequest(
          '/example-html/ProgressServlet', 
          controller.progressCallback,
          "id=" + props.id  // Widget id.          
        );  
        
    },

   

    /**
     * This function is used to update widgets.
     *
     * @param {Object} httpRequest XHR Object.
     * @return {boolean} true if successful; otherwise, false.
     */
    progressCallback: function(httpRequest) {
        if (httpRequest == null) {
            return false;
        }

        // Parse JSON text.
        var progress = woodstock.json.parse(httpRequest.responseText);

        if (controller.progressBarId != null) {
          // set progress
          var widget = woodstock.widget.common.getWidget(controller.progressBarId);
          widget.setProgress({progress:progress});
          return true;
        }
    }

};
    
// Listen for Widget events.
woodstock.widget.common.subscribe(woodstock.widget.progressBar.event.progress.beginTopic,
    controller, "processProgressEvent");


