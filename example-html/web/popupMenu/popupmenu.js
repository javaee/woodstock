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
 * Function called to display the menu when the hyperklink is clicked.
 * {Object}  event The onclick/keypress event 
 */

function displayMenu(event) {
    document.getElementById("ww_id10").open(event);    
}

/**
 * Function that is invoked when an item is selected in the menu.
 * The menu is submitted with the value
 * 
 */
function changeEvent() {
    document.getElementById("ww_id10").submit();
}

var controller = {
    
    /**
     * Function that processes the response that is got from the servlet
     * (Object) response The json response
     * {boolean} Return true if successful; otherwise, false.
     */
    processSubmitCallBack: function(response) {
        if (response == null) {
            return false;
        }
        var widget = woodstock.widget.common.getWidget("ww_id12");  

        widget.setProps(response);    
        return true;
    }
}        
        
// Listen for Widget events.
woodstock.widget.common.addOnLoad(function() {
    woodstock.widget.common.subscribe(woodstock.widget.popupMenu.event.submit.endTopic,
        controller, "processSubmitCallBack");
});
