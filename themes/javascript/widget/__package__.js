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

dojo.provide("webui.@THEME@.widget.*");

dojo.kwCompoundRequire({
    common: [
        "webui.@THEME@.widget.common",
        "webui.@THEME@.widget.props",
        "webui.@THEME@.widget.widgetBase"]
});

// For debugging only, obtain all module resources before invoking
// dojo.hostenv.writeIncludes(). This will ensure that JavaScript
// files are accessible to JavaScript debuggers.
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.accordion");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.accordionTab");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.alarm");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.anchor");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.alert");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.bubble");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.button");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.calendar");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.calendarField");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.checkbox");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.checkboxGroup");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.dropDown");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.editableField");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.fieldBase");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.hiddenField");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.hyperlink");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.image");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.imageButton");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.imageHyperlink");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.label");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.listbox");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.progressBar");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.radioButton");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.radioButtonGroup");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.resetButton");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.staticText");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.table2");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.table2RowGroup");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.textArea");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.textField");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.widget.passwordField");

dojo.require("dojo.ns");
dojo.registerNamespace("webui.@THEME@", "webui.@THEME@.widget");
dojo.widget.manager.registerWidgetPackage("webui.@THEME@.widget");
