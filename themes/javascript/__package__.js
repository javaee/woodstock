//<!--
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

dojo.provide("webui.@THEME@.*");

dojo.kwCompoundRequire({
    common: [
        "webui.@THEME@.props",
        "webui.@THEME@.cookie",
        "webui.@THEME@.browser",
        "webui.@THEME@.body",
        "webui.@THEME@.common",
        "webui.@THEME@.formElements"]
});

// For debugging only, obtain all module resources before invoking
// dojo.hostenv.writeIncludes(). This will ensure that JavaScript
// files are accessible to JavaScript debuggers.
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.addRemove");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.calendar");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.commonTasksSection");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.editableList");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.fileChooser");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.orderableList");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.scheduler");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.table");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.tree");
dojo.requireIf(djConfig.isDebug, "webui.@THEME@.wizard");

//-->
