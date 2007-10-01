// widget/submitBase.js
//
// The contents of this file are subject to the terms
// of the Common Development and Distribution License
// (the License).  You may not use this file except in
// compliance with the License.
// 
// You can obtain a copy of the license at
// https://woodstock.dev.java.net/public/CDDLv1.0.html.
// See the License for the specific language governing
// permissions and limitations under the License.
// 
// When distributing Covered Code, include this CDDL
// Header Notice in each file and include the License file
// at https://woodstock.dev.java.net/public/CDDLv1.0.html.
// If applicable, add the following below the CDDL Header,
// with the fields enclosed by brackets [] replaced by
// you own identifying information:
// "Portions Copyrighted [year] [name of copyright owner]"
// 
// Copyright 2007 Sun Microsystems, Inc. All rights reserved.
//

/**
 * @name widget/submitBase.js
 * @version @THEME_VERSION@
 * @overview This module contains classes and functions for the submitBase object.
 */
dojo.provide("webui.@THEME@.widget.submitBase");

dojo.require("webui.@THEME@.widget.widgetBase");

/**
 * This function is used to construct a template based widget.
 *
 * @name webui.@THEME@.widget.submitBase
 * @inherits webui.@THEME@.widget.widgetBase
 * @constructor
 */
dojo.declare("webui.@THEME@.widget.submitBase", webui.@THEME@.widget.widgetBase);

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 */
webui.@THEME@.widget.submitBase.prototype.postCreate = function () {
    // Set public functions.
    this.domNode.submit = function(execute) { return dijit.byId(this.id).submit(execute); }

    return this.inherited("postCreate", arguments);
}

/**
 * Process submit event.
 *
 * @param {String} execute Comma separated string containing a list of 
 * client ids against which the execute portion of the request 
 * processing lifecycle must be run.
 */
webui.@THEME@.widget.submitBase.prototype.submit = function(execute) {
    // Include default AJAX implementation.
    this.ajaxify();

    // Publish an event for custom AJAX implementations to listen for.
    dojo.publish(this.event.submit.beginTopic, [{
        id: this.id,
        execute: execute,
        endTopic: this.event.submit.endTopic
    }]);
    return true;
}
