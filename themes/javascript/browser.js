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

dojo.provide("webui.@THEME@.browser");

/**
 * This function is used to construct a javascript object for
 * testing browser properties. For example: 
 * 
 * var browser = new webui.@THEME@.browser();
 * if (broswer.is_ie6up == true) {
 *     alert("Browser is IE 6 or above");
 * }
 */
webui.@THEME@.browser = function() {
    // Convert all characters to lowercase to simplify testing.
    this.agent = navigator.userAgent.toLowerCase();

    // Note: On IE5, these return 4, so use is_ie5up to detect IE5.
    this.is_major = parseInt(navigator.appVersion);
    this.is_minor = parseFloat(navigator.appVersion);

    // Navigator version
    // Note = Opera and WebTV spoof Navigator.
    this.is_nav = this.agent.indexOf('mozilla') != -1
        && this.agent.indexOf('spoofer') == -1
        && this.agent.indexOf('compatible') == -1;
    this.is_nav4 = this.is_nav && this.is_major == 4;
    this.is_nav4up = this.is_nav && this.is_major >= 4;
    this.is_navonly = this.is_nav && this.agent.indexOf(";nav") != -1
        || this.agent.indexOf("; nav") != -1;
    this.is_nav6 = this.is_nav && this.is_major == 5;
    this.is_nav6up = this.is_nav && this.is_major >= 5;
    this.is_gecko = this.agent.indexOf('gecko') != -1;

    // IE version
    this.is_ie = this.agent.indexOf("msie") != -1
        && this.agent.indexOf("opera") == -1;
    this.is_ie3 = this.is_ie && this.is_major < 4;
    this.is_ie4 = this.is_ie && this.is_major == 4
        && this.agent.indexOf("msie 4") != -1;
    this.is_ie4up = this.is_ie && this.is_major >= 4;
    this.is_ie5 = this.is_ie && this.is_major == 4
        && this.agent.indexOf("msie 5.0") != -1;
    this.is_ie5_5 = this.is_ie && this.is_major == 4
        && this.agent.indexOf("msie 5.5") != -1;
    this.is_ie5up = this.is_ie && !this.is_ie3 && !this.is_ie4;
    this.is_ie5_5up = this.is_ie && !this.is_ie3 && !this.is_ie4
        && !this.is_ie5;
    this.is_ie6 = this.is_ie && this.is_major == 4
        && this.agent.indexOf("msie 6.") != -1;
    this.is_ie6up = this.is_ie && !this.is_ie3 && !this.is_ie4
        && !this.is_ie5 && !this.is_ie5_5;
    this.is_ie7 = this.is_ie && this.is_major == 4
        && this.agent.indexOf("msie 7.") != -1;
    this.is_ie7up = this.is_ie && !this.is_ie3 && !this.is_ie4
        && !this.is_ie5 && !this.is_ie5_5 && !this.is_ie6;

    // Platform
    this.is_linux = this.agent.indexOf("inux")!= -1;
    this.is_sun = this.agent.indexOf("sunos")!= -1;
    this.is_win = this.agent.indexOf("win")!= -1 
        || this.agent.indexOf("16bit")!= -1;
}

//-->
