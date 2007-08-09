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

dojo.provide("webui.@THEME@.widget.jsfx.*");

dojo.kwCompoundRequire({
    common: [
        "webui.@THEME@.widget.jsfx.common"]
});

// For debugging only, obtain all module resources before invoking
// dojo.hostenv.writeIncludes(). This will ensure that JavaScript
// files are accessible to JavaScript debuggers.
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.accordion");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.accordionTab");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.alarm");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.anchor");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.alert");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.bubble");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.button");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.calendarField");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.checkbox");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.checkboxGroup");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.dropDown");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.editableField");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.hiddenField");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.hyperlink");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.image");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.imageButton");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.imageHyperlink");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.label");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.listbox");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.popupMenu");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.progressBar");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.radioButton");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.radioButtonGroup");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.resetButton");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.staticText");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.table2");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.table2RowGroup");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.textArea");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.jsfx.textField");
