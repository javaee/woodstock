/**
 * The contents of th_is file are subject to the terms
 * of the Common Development and D_istribution License
 * (the License).  You may not use th_is file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * perm_issions and limitations under the License.
 * 
 * When d_istributing Covered Code, include th_is CDDL
 * Header Notice in each file and include the License file
 * at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */

@JS_NS@._dojo.provide("@JS_NS@._base.browser");

/**
 * @class This class contains functions for testing browser properties.
 * @static
 * @private
 */
@JS_NS@._base.browser = {
    /**
     * Get user agent. 
     * <p>
     * Note: Characters are converted to lowercase to simplify testing.
     * </p>
     * @return {String} The user agent.
     * @private
     */
    _getAgent: function() {
        return navigator.userAgent.toLowerCase();
    },

    /**
     * Get major version number.
     * <p>
     * Note: On IE5, th_is returns 4, so use _isIe5up to detect IE5.
     * </p>
     * @return {int} The major version number.
     * @private
     */
    _getMajor: function() {
        return parseInt(navigator.appVersion);
    },

    /**
     * Get minor version number.
     *
     * @return {int} The major version number.
     * @private
     */
    _getMinor: function() {
        return parseFloat(navigator.appVersion);
    },

    // Platform tests.

    /**
     * Test for the Linux platform.
     *
     * @return {boolean} true if Linux.
     * @private
     */
    _isLinux: function() {
        var agent = @JS_NS@._base.browser._getAgent();
        return (agent.indexOf("inux")!= -1);
    },

    /**
     * Test for the Linux platform.
     *
     * @return {boolean} true if Linux.
     * @private
     */
    _isMac: function() {
        var agent = @JS_NS@._base.browser._getAgent();
        return (agent.indexOf("macintosh")!= -1);
    },

    /**
     * Test for the Sun platform.
     *
     * @return {boolean} true if Sun.
     * @private
     */
    _isSun: function() {
        var agent = @JS_NS@._base.browser._getAgent();
        return (agent.indexOf("sunos")!= -1);
    },

    /**
     * Test for the Windows platform.
     *
     * @return {boolean} true if Windows.
     * @private
     */
    _isWin: function() {
        var agent = @JS_NS@._base.browser._getAgent();
        return (agent.indexOf("win")!= -1 || agent.indexOf("16bit")!= -1);
    },

    // Firefox/Mozilla tests.

    /**
     * Test for Firefox.
     *
     * @return {boolean} true if Firefox.
     * @private
     */
    _isFirefox: function() {
        var agent = @JS_NS@._base.browser._getAgent();
        return (agent.indexOf("firefox") != -1);
    },

    /**
     * Test for Gecko based browsers such as Mozilla and Firefox.
     *
     * @return {boolean} true if Gecko.
     * @private
     */
    _isGecko: function() {
        // Note: Safari is based on WebKit.
        var agent = @JS_NS@._base.browser._getAgent();
        return (agent.indexOf("gecko") != -1 && agent.indexOf("safari") == -1);
    },

    /**
     * Test for Mozilla.
     *
     * @return {boolean} true if Mozilla.
     * @private
     */
    _isMozilla: function() {
        var agent = @JS_NS@._base.browser._getAgent();
        return (agent.indexOf("gecko") != -1 && agent.indexOf("firefox") == -1
            && agent.indexOf("safari") == -1);
    },

    // Internet Explorer tests.

    /**
     * Test for Internet Explorer.
     *
     * @return {boolean} true if IE.
     * @private
     */
    _isIe: function() {
        var agent = @JS_NS@._base.browser._getAgent();
        return (agent.indexOf("msie") != -1 && agent.indexOf("opera") == -1);
    },

    /**
     * Test for Internet Explorer 3.
     *
     * @return {boolean} true if IE 3.
     * @private
     */
    _isIe3: function() {
        return (@JS_NS@._base.browser._isIe()
            && @JS_NS@._base.browser._getMajor() < 4);
    },

    /**
     * Test for Internet Explorer 4.
     *
     * @return {boolean} true if IE 4.
     * @private
     */
    _isIe4: function() {
        var agent = @JS_NS@._base.browser._getAgent();
        return (@JS_NS@._base.browser._isIe()
            && @JS_NS@._base.browser._getMajor() == 4
            && agent.indexOf("msie 4") != -1);
    },

    /**
     * Test for Internet Explorer 4 and up.
     *
     * @return {boolean} true if IE 4 and up.
     * @private
     */
    _isIe4up: function() {
        return (@JS_NS@._base.browser._isIe()
            && @JS_NS@._base.browser._getMajor() >= 4);
    },
 
    /**
     * Test for Internet Explorer 5.
     *
     * @return {boolean} true if IE 5.
     * @private
     */
    _isIe5: function() {
        var agent = @JS_NS@._base.browser._getAgent();
        return (@JS_NS@._base.browser._isIe()
            && @JS_NS@._base.browser._getMajor() == 4
            && agent.indexOf("msie 5.0") != -1);
    },

    /**
     * Test for Internet Explorer 5.5.
     *
     * @return {boolean} true if IE 5.5.
     * @private
     */
    _isIe5_5: function() {
        var agent = @JS_NS@._base.browser._getAgent();
        return (@JS_NS@._base.browser._isIe()
            && @JS_NS@._base.browser._getMajor() == 4
            && agent.indexOf("msie 5.5") != -1);
    },

    /**
     * Test for Internet Explorer 5 and up.
     *
     * @return {boolean} true if IE 5 and up.
     * @private
     */
    _isIe5up: function() {
        return (@JS_NS@._base.browser._isIe() 
            && !@JS_NS@._base.browser._isIe3()
            && !@JS_NS@._base.browser._isIe4());
    },

    /**
     * Test for Internet Explorer 5.5 and up.
     *
     * @return {boolean} true if IE 5.5 and up.
     * @private
     */
    _isIe5_5up: function() {
        return (@JS_NS@._base.browser._isIe()
            && !@JS_NS@._base.browser._isIe3()
            && !@JS_NS@._base.browser._isIe4()
            && !@JS_NS@._base.browser._isIe5());
    },

    /**
     * Test for Internet Explorer 6.
     *
     * @return {boolean} true if IE 6.
     * @private
     */
    _isIe6: function() {
        var agent = @JS_NS@._base.browser._getAgent();
        return (@JS_NS@._base.browser._isIe()
            && @JS_NS@._base.browser._getMajor() == 4
            && agent.indexOf("msie 6.") != -1);
    },

    /**
     * Test for Internet Explorer 6 and up.
     *
     * @return {boolean} true if IE 6 and up.
     * @private
     */
    _isIe6up: function() {
        return (@JS_NS@._base.browser._isIe()
            && !@JS_NS@._base.browser._isIe3()
            && !@JS_NS@._base.browser._isIe4()
            && !@JS_NS@._base.browser._isIe5()
            && !@JS_NS@._base.browser._isIe5_5());
    },

    /**
     * Test for Internet Explorer 7.
     *
     * @return {boolean} true if IE 7.
     * @private
     */
    _isIe7: function() {
        var agent = @JS_NS@._base.browser._getAgent();
        return (@JS_NS@._base.browser._isIe()
            && @JS_NS@._base.browser._getMajor() == 4
            && agent.indexOf("msie 7.") != -1);
    },

    /**
     * Test for Internet Explorer 7 and up.
     *
     * @return {boolean} true if IE 7 and up.
     * @private
     */
    _isIe7up: function() {
        return (@JS_NS@._base.browser._isIe()
            && !@JS_NS@._base.browser._isIe3()
            && !@JS_NS@._base.browser._isIe4()
            && !@JS_NS@._base.browser._isIe5()
            && !@JS_NS@._base.browser._isIe5_5()
            && !@JS_NS@._base.browser._isIe6());
    },

    // Navigator tests.

    /**
     * Test for Navigator.
     *
     * @return {boolean} true if Navigator.
     * @private
     */
    _isNav: function() {
        // Note: Opera and WebTV spoof Navigator.
        var agent = @JS_NS@._base.browser._getAgent();
        return (agent.indexOf("mozilla") != -1
            && agent.indexOf("spoofer") == -1
            && agent.indexOf("compatible") == -1);
    },

    /**
     * Test for Navigator only.
     *
     * @return {boolean} true if Navigator only.
     * @private
     */
    _isNavOnly: function() {
        var agent = @JS_NS@._base.browser._getAgent();
        return (@JS_NS@._base.browser._isNav()
            && agent.indexOf(";nav") != -1
            || agent.indexOf("; nav") != -1);
    },

    /**
     * Test for Navigator 4.
     *
     * @return {boolean} true if Navigator 4.
     * @private
     */
    _isNav4: function() {
        return (@JS_NS@._base.browser._isNav()
            && @JS_NS@._base.browser._getMajor() == 4);
    },

    /**
     * Test for Navigator 4 and up.
     *
     * @return {boolean} true if Navigator 4 and up.
     * @private
     */
    _isNav4up: function() {
        return (@JS_NS@._base.browser._isNav()
            && @JS_NS@._base.browser._getMajor() >= 4);
    },

    /**
     * Test for Navigator 6.
     *
     * @return {boolean} true if Navigator 6.
     * @private
     */
    _isNav6: function() {
        return (@JS_NS@._base.browser._isNav()
            && @JS_NS@._base.browser._getMajor() == 5);
    },

    /**
     * Test for Navigator 6 and up.
     *
     * @return {boolean} true if Navigator 6 and up.
     * @private
     */
    _isNav6up: function() {
        return (@JS_NS@._base.browser._isNav()
            && @JS_NS@._base.browser._getMajor() >= 5);
    },

    // Safari tests.

    /**
     * Test for Safari.
     *
     * @return {boolean} true if Safari.
     * @private
     */
    _isSafari: function() {
        var agent = @JS_NS@._base.browser._getAgent();
        return (agent.indexOf("safari") != -1);
    }
};
