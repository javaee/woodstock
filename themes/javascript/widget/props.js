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

    // Anchor properties.
    anchor: {
        className:"Anc@THEME_CSS@",
        disabledClassName:"AncDis@THEME_CSS@"
    },

    // Button properties.
    button: {
        imageClassName: "Btn3@THEME_CSS@",
        imageDisabledClassName: "Btn3Dis@THEME_CSS@",
        imageHovClassName: "Btn3Hov@THEME_CSS@",
        primaryClassName: "Btn1@THEME_CSS@",
        primaryDisabledClassName: "Btn1Dis@THEME_CSS@",
        primaryHovClassName: "Btn1Hov@THEME_CSS@",
        primaryMiniClassName: "Btn1Mni@THEME_CSS@",
        primaryMiniHovClassName: "Btn1MniHov@THEME_CSS@",
        primaryMiniDisabledClassName: "Btn1MniDis@THEME_CSS@",
        secondaryClassName: "Btn2@THEME_CSS@",
        secondaryDisabledClassName: "Btn2Dis@THEME_CSS@",
        secondaryHovClassName: "Btn2Hov@THEME_CSS@",
        secondaryMiniClassName: "Btn2Mni@THEME_CSS@",
        secondaryMiniDisabledClassName: "Btn2MniDis@THEME_CSS@",
        secondaryMiniHovClassName: "Btn2MniHov@THEME_CSS@"
    },
    
    // Calendar properties.
    calendar: {
        className: "CalRootTbl@THEME_CSS@",          
        edgeDateClass: "DatOthLnk@THEME_CSS@",            
        dateClass: "DatLnk@THEME_CSS@",
        selectedClass: "DatBldLnk@THEME_CSS@",       
        todayClass: "DatCurLnk@THEME_CSS@"
    },
    
    // Checkbox properties.
    checkbox: {
        className: "CbSpn@THEME_CSS@",
        disabledClassName: "CbSpnDis@THEME_CSS@",
        labelClassName: "CbLbl@THEME_CSS@",
        labelDisabledClassName: "CbLblDis@THEME_CSS@",
        imageClassName: "CbImg@THEME_CSS@",
        imageDisabledClassName: "CbImgDis@THEME_CSS@"
    },
        
    // Checkbox Group
    checkboxGroup: {
        vertClassName: "CBGRPVert@THEME_CSS@",
        horizClassName: "CBGRPHoriz@THEME_CSS@"       
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

    // EditableField properties.
    editableField: {
        className: "EdtFld_ReadOnly@THEME_CSS@",
        editableClassName: "EdtFld_Edt@THEME_CSS@",
        disabledClassName: "EdtFldDis@THEME_CSS@"
    },

    // Hyperlink properties.
    hyperlink: {
        className:"Hyp@THEME_CSS@",
        disabledClassName:"HypDis@THEME_CSS@"
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

    // Label properties.
    label: {
        errorStyleClass: "ConErrLblTxt@THEME_CSS@",
        levelOneStyleClass: "LblLev1Txt@THEME_CSS@",
        levelTwoStyleClass: "LblLev2Txt@THEME_CSS@",
        levelThreeStyleClass: "LblLev3Txt@THEME_CSS@"
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
        titleOptionAppender: " &#8212;".unescapeHTML(), //scriptaculous method
        titleOptionPreppender: "&#8212; ".unescapeHTML()
    },

    // PasswordField properties.
    passwordField: {
        className: "TxtFld@THEME_CSS@",
        disabledClassName: "TxtFldDis@THEME_CSS@"
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
    },

    // RadioButton properties.
    radioButton: {
        className: "RbSpn@THEME_CSS@",
        disabledClassName: "RbSpnDis@THEME_CSS@",
        labelClassName: "RbLbl@THEME_CSS@",
        labelDisabledClassName: "RbLblDis@THEME_CSS@",
        imageClassName: "RbImg@THEME_CSS@",
        imageDisabledClassName: "RbImgDis@THEME_CSS@"
    },
    
    // RadioButton Group
    radioButtonGroup: {
        vertClassName: "RBGRPVert@THEME_CSS@",
        horizClassName: "RBGRPHoriz@THEME_CSS@"       
    },

    // TextArea properties.
    textArea: {
        className: "TxtAra@THEME_CSS@",
        disabledClassName: "TxtAraDis@THEME_CSS@",
        labelTopAlignStyle: "TxtAraLabel@THEME_CSS@" 
    },    

    // TextField properties.
    textField: {
        className: "TxtFld@THEME_CSS@",
        disabledClassName: "TxtFldDis@THEME_CSS@"
    }

}
