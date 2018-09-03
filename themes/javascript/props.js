/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */


//dojo.provide("webui.@THEME@.props");
define( function() {

/**
 * This closure is used to provide theme properties.
 */
    return {
    // Common properties.
    hiddenClassName: "hidden@THEME_CSS@", // Use webui.@THEME@.common.setVisibleElement

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

    // Drop Down properties.
    dropDown: {
        className: "MnuStd@THEME_CSS@",
        disabledClassName: "MnuStdDis@THEME_CSS@",
        optionClassName: "MnuStdOpt@THEME_CSS@",
        optionDisabledClassName: "MnuStdOptDis@THEME_CSS@",
        optionGroupClassName: "MnuStdOptGrp@THEME_CSS@",
        optionSelectedClassName: "MnuStdOptSel@THEME_CSS@",
        optionSeparatorClassName: "MnuStdOptSep@THEME_CSS@"
    },

    // Field properties.
    field: {
        areaClassName: "TxtAra@THEME_CSS@",
        areaDisabledClassName: "TxtAraDis@THEME_CSS@",
        fieldClassName: "TxtFld@THEME_CSS@",
        fieldDisabledClassName: "TxtFldDis@THEME_CSS@"
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
        optionSeparatorClassName: "LstOptSep@THEME_CSS@"
    },

    // Tree properties.
    tree: {
        selectedTreeRowClass: "TreeSelRow@THEME_CSS@",
        treeRowClass: "TreeRow@THEME_CSS@"
    }
}
});

//-->
