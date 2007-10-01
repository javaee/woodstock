// browser.js
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

/**
 * @name browser.js
 * @version @THEME_VERSION@
 * @overview This module contains functions for testing browser properties.
 * @example
 * <code>
 * if (webui.@THEME@.browser.is_ie6up() == true) {
 *     alert("Browser is IE 6 or above");
 * }
 * </code>
 */
dojo.provide("webui.@THEME@.browser");

/**
 * This closure contains functions for testing browser properties.
 */
webui.@THEME@.browser = {
    /**
     * Get user agent. 
     * <p>
     * Note: Characters are converted to lowercase to simplify testing.
     * </p>
     */
    getAgent: function() {
        return navigator.userAgent.toLowerCase();
    },

    /**
     * Test major version number.
     * <p>
     * Note: On IE5, this returns 4, so use is_ie5up to detect IE5.
     * </p>
     */
    is_major: function() {
        return parseInt(navigator.appVersion);
    },

    /**
     * Test minor version number.
     */
    is_minor: function() {
        return parseFloat(navigator.appVersion);
    },

    // Platform tests.

    /**
     * Test for the Linux platform.
     */
    is_linux: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (agent.indexOf("inux")!= -1);
    },

    /**
     * Test for the Sun platform.
     */
    is_sun: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (agent.indexOf("sunos")!= -1);
    },

    /**
     * Test for the Windows platform.
     */
    is_win: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (agent.indexOf("win")!= -1 || agent.indexOf("16bit")!= -1);
    },

    // Firefox/Mozilla tests.

    /**
     * Test for Gecko.
     */
    is_gecko: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (agent.indexOf('gecko') != -1);
    },

    // Internet Explorer tests.

    /**
     * Test for Internet Explorer.
     */
    is_ie: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (agent.indexOf("msie") != -1 && agent.indexOf("opera") == -1);
    },

    /**
     * Test for Internet Explorer 3.
     */
    is_ie3: function() {
        return (webui.@THEME@.browser.is_ie() && 
            webui.@THEME@.browser.is_major() < 4);
    },

    /**
     * Test for Internet Explorer 4.
     */
    is_ie4: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (webui.@THEME@.browser.is_ie() && 
            webui.@THEME@.browser.is_major() == 4 && 
            agent.indexOf("msie 4") != -1);
    },

    /**
     * Test for Internet Explorer 4 and up.
     */
    is_ie4up: function() {
        return (webui.@THEME@.browser.is_ie() && 
            webui.@THEME@.browser.is_major() >= 4);
    },
 
    /**
     * Test for Internet Explorer 5.
     */
    is_ie5: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (webui.@THEME@.browser.is_ie() && 
            webui.@THEME@.browser.is_major() == 4 && 
            agent.indexOf("msie 5.0") != -1);
    },

    /**
     * Test for Internet Explorer 5.5.
     */
    is_ie5_5: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (webui.@THEME@.browser.is_ie() && 
            webui.@THEME@.browser.is_major() == 4 &&
            agent.indexOf("msie 5.5") != -1);
    },

    /**
     * Test for Internet Explorer 5 and up.
     */
    is_ie5up: function() {
        return (webui.@THEME@.browser.is_ie() && 
            !webui.@THEME@.browser.is_ie3() &&
            !webui.@THEME@.browser.is_ie4());
    },

    /**
     * Test for Internet Explorer 5.5 and up.
     */
    is_ie5_5up: function() {
        return (webui.@THEME@.browser.is_ie() && 
            !webui.@THEME@.browser.is_ie3() && 
            !webui.@THEME@.browser.is_ie4() && 
            !webui.@THEME@.browser.is_ie5());
    },

    /**
     * Test for Internet Explorer 6.
     */
    is_ie6: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (webui.@THEME@.browser.is_ie() && 
            webui.@THEME@.browser.is_major() == 4 && 
            agent.indexOf("msie 6.") != -1);
    },

    /**
     * Test for Internet Explorer 6 and up.
     */
    is_ie6up: function() {
        return (webui.@THEME@.browser.is_ie() && 
            !webui.@THEME@.browser.is_ie3() && 
            !webui.@THEME@.browser.is_ie4() && 
            !webui.@THEME@.browser.is_ie5() && 
            !webui.@THEME@.browser.is_ie5_5());
    },

    /**
     * Test for Internet Explorer 7.
     */
    is_ie7: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (webui.@THEME@.browser.is_ie() && 
            webui.@THEME@.browser.is_major() == 4 && 
            agent.indexOf("msie 7.") != -1);
    },

    /**
     * Test for Internet Explorer 7 and up.
     */
    is_ie7up: function() {
        return (webui.@THEME@.browser.is_ie() && 
            !webui.@THEME@.browser.is_ie3() && 
            !webui.@THEME@.browser.is_ie4() && 
            !webui.@THEME@.browser.is_ie5() && 
            !webui.@THEME@.browser.is_ie5_5() && 
            !webui.@THEME@.browser.is_ie6());
    },

    // Navigator tests.

    /**
     * Test for Navigator.
     */
    is_nav: function() {
        // Note: Opera and WebTV spoof Navigator.
        var agent = webui.@THEME@.browser.getAgent();
        return (agent.indexOf('mozilla') != -1 &&
            agent.indexOf('spoofer') == -1 &&
            agent.indexOf('compatible') == -1);
    },

    /**
     * Test for Navigator only.
     */
    is_navonly: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (webui.@THEME@.browser.is_nav() && 
            agent.indexOf(";nav") != -1 ||
            agent.indexOf("; nav") != -1);
    },

    /**
     * Test for Navigator 4.
     */
    is_nav4: function() {
        return (webui.@THEME@.browser.is_nav() && 
            webui.@THEME@.browser.is_major() == 4);
    },

    /**
     * Test for Navigator 4 and up.
     */
    is_nav4up: function() {
        return (webui.@THEME@.browser.is_nav() && 
            webui.@THEME@.browser.is_major() >= 4);
    },

    /**
     * Test for Navigator 6.
     */
    is_nav6: function() {
        return (webui.@THEME@.browser.is_nav() && 
            webui.@THEME@.browser.is_major() == 5);
    },

    /**
     * Test for Navigator 6 and up.
     */
    is_nav6up: function() {
        return (webui.@THEME@.browser.is_nav() && 
            webui.@THEME@.browser.is_major() >= 5);
    },

    // Safari tests.

    /**
     * Test for Safari.
     */
    is_safari: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (agent.indexOf('safari') != -1);
    }
}
