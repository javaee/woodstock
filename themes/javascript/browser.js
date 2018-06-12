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
