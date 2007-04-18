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

dojo.provide("webui.@THEME@.props");

/**
 * This closure is used to provide theme properties.
 */
webui.@THEME@.props = {
    // Common properties.
    hiddenClassName: "hidden@THEME_CSS@", // Use webui.@THEME@.common.setVisibleElement

    /**
     * Button properties.
     *
     * @deprecated See webui.@THEME@.widget.props.button
     */
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

    /**
     * Drop Down properties.
     *
     * @deprecated See webui.@THEME@.widget.props.dropDown
     */
    dropDown: {
        className: "MnuStd@THEME_CSS@",
        disabledClassName: "MnuStdDis@THEME_CSS@",
        optionClassName: "MnuStdOpt@THEME_CSS@",
        optionDisabledClassName: "MnuStdOptDis@THEME_CSS@",
        optionGroupClassName: "MnuStdOptGrp@THEME_CSS@",
        optionSelectedClassName: "MnuStdOptSel@THEME_CSS@",
        optionSeparatorClassName: "MnuStdOptSep@THEME_CSS@"
    },

    /**
     * Field properties.
     *
     * @deprecated See webui.@THEME@.widget.props.textField
     */
    field: {
        areaClassName: "TxtAra@THEME_CSS@",
        areaDisabledClassName: "TxtAraDis@THEME_CSS@",
        fieldClassName: "TxtFld@THEME_CSS@",
        fieldDisabledClassName: "TxtFldDis@THEME_CSS@"
    },

    /**
     * Jump drop down properties.
     *
     * @deprecated See webui.@THEME@.widget.props.jumpDropDown
     */
    jumpDropDown: {
        className: "MnuJmp@THEME_CSS@",
        disabledClassName: "",
        optionClassName: "MnuJmpOpt@THEME_CSS@",
        optionDisabledClassName: "MnuJmpOptDis@THEME_CSS@",
        optionGroupClassName: "MnuJmpOptGrp@THEME_CSS@",
        optionSelectedClassName: "MnuJmpOptSel@THEME_CSS@",
        optionSeparatorClassName: "MnuJmpOptSep@THEME_CSS@"
    },

    /**
     * Listbox properties.
     *
     * @deprecated See webui.@THEME@.widget.props.listbox
     */
    listbox: {
        className: "Lst@THEME_CSS@",
        disabledClassName: "LstDis@THEME_CSS@",
        monospaceClassName: "LstMno@THEME_CSS@",
        monospaceDisabledClassName: "LstMnoDis@THEME_CSS@",
        optionClassName: "LstOpt@THEME_CSS@",
        optionDisabledClassName: "LstOptDis@THEME_CSS@",
        optionGroupClassName: "LstOptGrp@THEME_CSS@",
        optionSelectedClassName: "LstOptSel@THEME_CSS@",
        optionSeparatorClassName: "LstOptSep@THEME_CSS@"
    },

    // Tree properties.
    tree: {
        selectedTreeRowClass: "TreeSelRow@THEME_CSS@",
        treeRowClass: "TreeRow@THEME_CSS@"
    }
}

//-->
