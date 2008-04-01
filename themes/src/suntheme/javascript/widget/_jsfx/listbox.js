/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
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

webui.@THEME_JS@._base.dojo.provide("webui.@THEME_JS@.widget._jsfx.listbox");

webui.@THEME_JS@._base.dojo.require("webui.@THEME_JS@.widget._jsfx.common");
webui.@THEME_JS@._base.dojo.require("webui.@THEME_JS@.widget.listbox");

// Listen for Dojo Widget events.
webui.@THEME_JS@._base.dojo.subscribe(webui.@THEME_JS@.widget.listbox.event.refresh.beginTopic,
    webui.@THEME_JS@.widget._jsfx.common, "_processRefreshEvent");
webui.@THEME_JS@._base.dojo.subscribe(webui.@THEME_JS@.widget.listbox.event.submit.beginTopic,
    webui.@THEME_JS@.widget._jsfx.common, "_processSubmitEvent");
