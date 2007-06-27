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

dojo.provide("webui.@THEME@.theme");

dojo.require("dojo.widget.*");

webui.@THEME@.theme = {
    /**
     * This function is used to obtain a template path.
     *
     * @param key The key associated with the theme property.
     */
    getTemplatePath: function(key) {
        var template = webui.@THEME@.theme.templates[key];
        
        // Typically, template paths begin with a forward slash, http, etc.
        return (template.indexOf("<") != 0) ? template : null;
    },

    /**
     * This function is used to obtain a template string.
     *
     * @param key The key associated with the theme property.
     */
    getTemplateString: function(key) {
        var template = webui.@THEME@.theme.templates[key];

        // Typically, template strings begin with a root tag.
        return (template.indexOf("<") == 0) ? template : null;
    }
}

//
// Note: The contents below are generated at build-time -- DO NOT EDIT!
//