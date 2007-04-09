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

dojo.provide("webui.@THEME@.widget.props");

/**
 * This closure is used to provide theme properties.
 */
webui.@THEME@.widget.props = {
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

    // Checkbox properties.
    checkbox: {
        spanClassName: "CbSpn@THEME_CSS@",
        spanDisabledClassName: "CbSpnDis@THEME_CSS@",
        labelClassName: "CbLbl@THEME_CSS@",
        labelDisabledClassName: "CbLblDis@THEME_CSS@",
        imageClassName: "CbImg@THEME_CSS@",
        imageDisabledClassName: "CbImgDis@THEME_CSS@"
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

    // Label properties.
    label: {
        errorStyleClass: "ConErrLblTxt@THEME_CSS@",
        levelOneStyleClass: "LblLev1Txt@THEME_CSS@",
        levelTwoStyleClass: "LblLev2Txt@THEME_CSS@",
        levelThreeStyleClass: "LblLev3Txt@THEME_CSS@"

    }
}

//-->
