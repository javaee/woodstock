/**
 * This is a simple XMLHttpRequestImplementation ( Ajax async post)
 * created per instructions on 
 * http://developer.mozilla.org/en/docs/AJAX:Getting_Started#Step_1_.E2.80.93_How_to_Make_an_HTTP_Request
 * 
 * This limited-features connection object can be used instead of full-featured
 * connection provided in connection.js.
 */

function connection() {}

/**
 * @description Method for initiating an asynchronous request via the XHR object.
 * @method asyncRequest
 * @public
 * @static
 * @param {string} method HTTP transaction method
 * @param {string} uri Fully qualified path of resource
 * @param {callback} callback User-defined callback function or object. 
 *        This parameter is ignored in favor of hardcoded controller.responseSuccessCallback
 * @param {string} postData POST body
 * @return {object} Returns the connection object
 */
connection.prototype.asyncRequest = function(method, uri, callback, postData) {
    var httpRequest;

    if (window.XMLHttpRequest) { // Mozilla, Safari, ...
        httpRequest = new XMLHttpRequest();
    } else if (window.ActiveXObject) { // IE
        try {
            httpRequest = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
            try {
                httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e) {}
        }
    }
    if (!httpRequest) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = function() {  ajaxCallBack(httpRequest); };
       
    // Note: POST would require Content-Type to be set.
    //
    // httpRequest.open(method , uri, true);
    // httpRequest.send(postData);
       
    httpRequest.open('GET', uri +'?'+postData, true);
    httpRequest.send(postData);
}

function ajaxCallBack(httpRequest) {
    try {
        if (httpRequest.readyState == 4) { //call is complete                
            if (httpRequest.status == 200) { //successful HTTP interaction.
                controller.responseSuccessCallback(httpRequest);
            } else {
                controller.responseFailureCallback(httpRequest);
            }
        }
    } catch( e ) {
        alert('Caught Exception: ' + e.description);
    }
}
