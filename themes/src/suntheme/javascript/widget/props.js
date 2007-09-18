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
    
dojo.provide("webui.@THEME@.widget.props");

/**
 * This closure is used to provide theme properties.
 */
webui.@THEME@.widget.props = {

    // Accordion and tabContent properties.
    accordionTab: {
        accordionTabExpanded: "AccdTabExpanded@THEME_CSS@",
        accordionTabCollapsed: "AccdTabCollapsed@THEME_CSS@",
        accordionTabMouseOver: "AccdTabMouseOver@THEME_CSS@",
        accordionHdr: "AccdHeader@THEME_CSS@",
        accordionHdrRefresh: "AccdRefreshBtn@THEME_CSS@",
        accordionHdrOpenAll: "AccdOpenAllBtn@THEME_CSS@", 
        accordionHdrCloseApp: "AccdCloseAllBtn@THEME_CSS@",
        accordionHdrDivider: "AccdDivider@THEME_CSS@",
        accordionDownTurner: "AccdDownTurner@THEME_CSS@", 
        accordionRightTurner: "AccdRightTurner@THEME_CSS@"
    },
    
    // Drop Down properties.
    dropDown: {
        className: "MnuStd@THEME_CSS@",
        disabledClassName: "MnuStdDis@THEME_CSS@",
        optionClassName: "MnuStdOpt@THEME_CSS@",
        optionDisabledClassName: "MnuStdOptDis@THEME_CSS@",
        optionGroupClassName: "MnuStdOptGrp@THEME_CSS@",
        optionSelectedClassName: "MnuStdOptSel@THEME_CSS@",
        optionSeparatorClassName: "MnuStdOptSep@THEME_CSS@",
        titleOptionAppender: " &#8212;",
        titleOptionPreppender: "&#8212; "
    },
    
    
    // Jump drop down properties.
    jumpDropDown: {
        className: "MnuJmp@THEME_CSS@",
        disabledClassName: "",
        optionClassName: "MnuJmpOpt@THEME_CSS@",
        optionDisabledClassName: "MnuJmpOptDis@THEME_CSS@",
        optionGroupClassName: "MnuJmpOptGrp@THEME_CSS@",
        optionSelectedClassName: "MnuJmpOptSel@THEME_CSS@",
        optionSeparatorClassName: "MnuJmpOptSep@THEME_CSS@"
    },

    // Listbox properties.
    listbox: {
        className: "Lst@THEME_CSS@",
        disabledClassName: "LstDis@THEME_CSS@",
        monospaceClassName: "LstMno@THEME_CSS@",
        monospaceDisabledClassName: "LstMnoDis@THEME_CSS@",
        optionClassName: "LstOpt@THEME_CSS@",
        optionDisabledClassName: "LstOptDis@THEME_CSS@",
        optionGroupClassName: "LstOptGrp@THEME_CSS@",
        optionSelectedClassName: "LstOptSel@THEME_CSS@",
        optionSeparatorClassName: "LstOptSep@THEME_CSS@",
        titleOptionAppender: " &#8212;".unescapeHTML(), // Prototype method
        titleOptionPreppender: "&#8212; ".unescapeHTML()
    },

    // Progress bar properties.
    progressBar: {
        barContainerClassName: "barContainer@THEME_CSS@",
        busy: "BUSY",
        canceled: "canceled",
        completed: "completed",
        determinate: "DETERMINATE",
        determinateClassName: "barDeterminate@THEME_CSS@",
        failed: "failed",
        indeterminate: "INDETERMINATE",
        indeterminateClassName: "barIndeterminate@THEME_CSS@",
        indeterminatePausedClassName: "barIndeterminatePaused@THEME_CSS@",
        notstarted: "not_started",
        paused: "paused",
        resumed: "resumed",
        stopped: "stopped"
    }
        
}
