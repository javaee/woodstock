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

// Function to obtain request parameters.
function getParameter(name) {
  name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
  var regexS = "[\\?&]" + name + "=([^&#]*)";
  var regex = new RegExp(regexS);
  var results = regex.exec(window.location.href);
  return (results != null) ? unescape(results[1]) : null;
}

// Function to initialize widgets.
function init() {
    // Get alert widget.
    var widget = woodstock.widget.common.getWidget("alert");
    if (widget == null) {
        return setTimeout("init();", 10);
    }
    // Set summary.
    var value = getParameter("detail");
    if (value != null) {        
        widget.setProps({
            detail: value
        });
    }
}

// Function to toggle disabled widget state.
function toggleDisabled(id) {
    var widget = woodstock.widget.common.getWidget(id);
    widget.setProps({
        disabled: !widget.getProps().disabled
    });
}

// Function to update label.
function updateLabel() {
    // Get button.
    var widget = woodstock.widget.common.getWidget("secondaryBtn");
    widget.refresh("textField"); // Refresh widget with text field value.
}
