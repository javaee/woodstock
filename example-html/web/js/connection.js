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
var connection = {
    /**
     * Function used to instantiate an asynchronous request via the XHR object.
     *
     * @param {string} url Fully qualified resource path.
     * @param {callback} callback User-defined callback function.
     * @param {string} params Query parameters to append with url.
     * @return {boolean} true if successful; otherwise, false.
     */
    asyncRequest: function(url, callback, params) {
        var httpRequest;

        // Get XHR object.
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
        if (httpRequest == null) {
            alert("Cannot create an XMLHttp instance.");
            return false;
        }

        // Assign callback.
        if (typeof callback == "function") {
            httpRequest.onreadystatechange = function() {
                if (httpRequest.readyState == 4) { // Call is complete                
                    if (httpRequest.status == 200) { // Successful HTTP interaction.
                        callback(httpRequest);
                    }
                }
            };
        }
       
        // Note: POST would require Content-Type to be set.
        //
        // httpRequest.open(method , uri, true);
        // httpRequest.send(postData);
        
        httpRequest.open('GET', (params != null) ? url + '?' + params : url, true);
        httpRequest.send(params);
        return true;
    }
};
