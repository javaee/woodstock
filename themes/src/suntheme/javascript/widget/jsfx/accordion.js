// widget/jsfx/accordion.js
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
 * @name widget/jsfx/accordion.js
 * @version @THEME_VERSION@
 * @overview This module contains the default Ajax implementation for the 
 * accordion widget.
 * <p>
 * Note: This Javascript file should be included in any page that uses the 
 * associated widget, where JSF Extensions is used as the underlying transfer
 * protocol.
 * </p>
 */
dojo.provide("webui.@THEME@.widget.jsfx.accordion");

dojo.require("webui.@THEME@.widget.jsfx.common");
dojo.require("webui.@THEME@.widget.accordion");

// Listen for Dojo Widget event signalling the widget's need to refresh.
dojo.subscribe(webui.@THEME@.widget.accordion.event.refresh.beginTopic,
    webui.@THEME@.widget.jsfx.common, "processRefreshEvent");
