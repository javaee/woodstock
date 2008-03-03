/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */

webui.@THEME@.dojo.provide("webui.@THEME@.browser");

/**
 * @class This class contains functions for testing browser properties.
 * @static
 */
webui.@THEME@.browser = {
    /**
     * Get user agent. 
     * <p>
     * Note: Characters are converted to lowercase to simplify testing.
     * </p>
     * @return {String} The user agent.
     */
    getAgent: function() {
        return navigator.userAgent.toLowerCase();
    },

    /**
     * Get major version number.
     * <p>
     * Note: On IE5, this returns 4, so use isIe5up to detect IE5.
     * </p>
     * @return {int} The major version number.
     */
    getMajor: function() {
        return parseInt(navigator.appVersion);
    },

    /**
     * Get minor version number.
     *
     * @return {int} The major version number.
     */
    getMinor: function() {
        return parseFloat(navigator.appVersion);
    },

    // Platform tests.

    /**
     * Test for the Linux platform.
     *
     * @return {boolean} true if Linux.
     */
    isLinux: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (agent.indexOf("inux")!= -1);
    },

    /**
     * Test for the Linux platform.
     *
     * @return {boolean} true if Linux.
     */
    isMac: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (agent.indexOf("macintosh")!= -1);
    },

    /**
     * Test for the Sun platform.
     *
     * @return {boolean} true if Sun.
     */
    isSun: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (agent.indexOf("sunos")!= -1);
    },

    /**
     * Test for the Windows platform.
     *
     * @return {boolean} true if Windows.
     */
    isWin: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (agent.indexOf("win")!= -1 || agent.indexOf("16bit")!= -1);
    },

    // Firefox/Mozilla tests.

    /**
     * Test for Firefox.
     *
     * @return {boolean} true if Firefox.
     */
    isFirefox: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (agent.indexOf("firefox") != -1);
    },

    /**
     * Test for Gecko.
     *
     * @return {boolean} true if Gecko.
     */
    isGecko: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (agent.indexOf("gecko") != -1);
    },

    /**
     * Test for Mozilla.
     *
     * @return {boolean} true if Mozilla.
     */
    isMozilla: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (agent.indexOf("gecko") != -1 && agent.indexOf("firefox") == -1
            && agent.indexOf("safari") == -1);
    },

    // Internet Explorer tests.

    /**
     * Test for Internet Explorer.
     *
     * @return {boolean} true if IE.
     */
    isIe: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (agent.indexOf("msie") != -1 && agent.indexOf("opera") == -1);
    },

    /**
     * Test for Internet Explorer 3.
     *
     * @return {boolean} true if IE 3.
     */
    isIe3: function() {
        return (webui.@THEME@.browser.isIe()
            && webui.@THEME@.browser.getMajor() < 4);
    },

    /**
     * Test for Internet Explorer 4.
     *
     * @return {boolean} true if IE 4.
     */
    isIe4: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (webui.@THEME@.browser.isIe()
            && webui.@THEME@.browser.getMajor() == 4
            && agent.indexOf("msie 4") != -1);
    },

    /**
     * Test for Internet Explorer 4 and up.
     *
     * @return {boolean} true if IE 4 and up.
     */
    isIe4up: function() {
        return (webui.@THEME@.browser.isIe()
            && webui.@THEME@.browser.getMajor() >= 4);
    },
 
    /**
     * Test for Internet Explorer 5.
     *
     * @return {boolean} true if IE 5.
     */
    isIe5: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (webui.@THEME@.browser.isIe()
            && webui.@THEME@.browser.getMajor() == 4
            && agent.indexOf("msie 5.0") != -1);
    },

    /**
     * Test for Internet Explorer 5.5.
     *
     * @return {boolean} true if IE 5.5.
     */
    isIe5_5: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (webui.@THEME@.browser.isIe()
            && webui.@THEME@.browser.getMajor() == 4
            && agent.indexOf("msie 5.5") != -1);
    },

    /**
     * Test for Internet Explorer 5 and up.
     *
     * @return {boolean} true if IE 5 and up.
     */
    isIe5up: function() {
        return (webui.@THEME@.browser.isIe() 
            && !webui.@THEME@.browser.isIe3()
            && !webui.@THEME@.browser.isIe4());
    },

    /**
     * Test for Internet Explorer 5.5 and up.
     *
     * @return {boolean} true if IE 5.5 and up.
     */
    isIe5_5up: function() {
        return (webui.@THEME@.browser.isIe()
            && !webui.@THEME@.browser.isIe3()
            && !webui.@THEME@.browser.isIe4()
            && !webui.@THEME@.browser.isIe5());
    },

    /**
     * Test for Internet Explorer 6.
     *
     * @return {boolean} true if IE 6.
     */
    isIe6: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (webui.@THEME@.browser.isIe()
            && webui.@THEME@.browser.getMajor() == 4
            && agent.indexOf("msie 6.") != -1);
    },

    /**
     * Test for Internet Explorer 6 and up.
     *
     * @return {boolean} true if IE 6 and up.
     */
    isIe6up: function() {
        return (webui.@THEME@.browser.isIe()
            && !webui.@THEME@.browser.isIe3()
            && !webui.@THEME@.browser.isIe4()
            && !webui.@THEME@.browser.isIe5()
            && !webui.@THEME@.browser.isIe5_5());
    },

    /**
     * Test for Internet Explorer 7.
     *
     * @return {boolean} true if IE 7.
     */
    isIe7: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (webui.@THEME@.browser.isIe()
            && webui.@THEME@.browser.getMajor() == 4
            && agent.indexOf("msie 7.") != -1);
    },

    /**
     * Test for Internet Explorer 7 and up.
     *
     * @return {boolean} true if IE 7 and up.
     */
    isIe7up: function() {
        return (webui.@THEME@.browser.isIe()
            && !webui.@THEME@.browser.isIe3()
            && !webui.@THEME@.browser.isIe4()
            && !webui.@THEME@.browser.isIe5()
            && !webui.@THEME@.browser.isIe5_5()
            && !webui.@THEME@.browser.isIe6());
    },

    // Navigator tests.

    /**
     * Test for Navigator.
     *
     * @return {boolean} true if Navigator.
     */
    isNav: function() {
        // Note: Opera and WebTV spoof Navigator.
        var agent = webui.@THEME@.browser.getAgent();
        return (agent.indexOf("mozilla") != -1
            && agent.indexOf("spoofer") == -1
            && agent.indexOf("compatible") == -1);
    },

    /**
     * Test for Navigator only.
     *
     * @return {boolean} true if Navigator only.
     */
    isNavOnly: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (webui.@THEME@.browser.isNav()
            && agent.indexOf(";nav") != -1
            || agent.indexOf("; nav") != -1);
    },

    /**
     * Test for Navigator 4.
     *
     * @return {boolean} true if Navigator 4.
     */
    isNav4: function() {
        return (webui.@THEME@.browser.isNav()
            && webui.@THEME@.browser.getMajor() == 4);
    },

    /**
     * Test for Navigator 4 and up.
     *
     * @return {boolean} true if Navigator 4 and up.
     */
    isNav4up: function() {
        return (webui.@THEME@.browser.isNav()
            && webui.@THEME@.browser.getMajor() >= 4);
    },

    /**
     * Test for Navigator 6.
     *
     * @return {boolean} true if Navigator 6.
     */
    isNav6: function() {
        return (webui.@THEME@.browser.isNav()
            && webui.@THEME@.browser.getMajor() == 5);
    },

    /**
     * Test for Navigator 6 and up.
     *
     * @return {boolean} true if Navigator 6 and up.
     */
    isNav6up: function() {
        return (webui.@THEME@.browser.isNav()
            && webui.@THEME@.browser.getMajor() >= 5);
    },

    // Safari tests.

    /**
     * Test for Safari.
     *
     * @return {boolean} true if Safari.
     */
    isSafari: function() {
        var agent = webui.@THEME@.browser.getAgent();
        return (agent.indexOf("safari") != -1);
    }
};
