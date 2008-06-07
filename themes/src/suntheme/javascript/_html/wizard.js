/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
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

@JS_NS@._dojo.provide("@JS_NS@._html.wizard");

@JS_NS@._dojo.require("@JS_NS@._base.browser");
@JS_NS@._dojo.require("@JS_NS@._base.common");
@JS_NS@._dojo.require("@JS_NS@._base.proto");

/** 
 * @class This class contains functions for wizard components.
 * <p>
 * The wizard JavaScript object is accessed using the getElementById()
 * function. Methods defined on that javascript object instance maybe
 * called using that identifier. For example, the following javascript
 * could be used to close and forward to a page when the wizard closes.
 * </p><p><code>
 *   <ui:wizard ...other attributes... 
 *	onPopupDismiss="document.getElementById('form1:wizard1').closeAndForward('launchform', '/faces/wizardData.jsp', true);" >
 *
 *	...wizard step tags...
 *
 *   </ui:wizard>
 * </code></p>
 * @static
 * @private
 */
@JS_NS@._html.wizard = {
    /**
     * This function is used to initialize HTML element properties with Object
     * literals.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {String} id The element id.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _init: function(props) {
        var message = "Cannot initialize wizard.";
        if (props == null || props.id == null) {
            console.debug(message); // See Firebug console.
            return false;
        }
        var domNode = document.getElementById(props.id);
        if (domNode == null) {
            console.debug(message); // See Firebug console.
            return false;
        }

        // Set given properties on domNode.
        @JS_NS@._base.proto._extend(domNode, props, false);

        // Set functions.
        domNode.nextClicked = @JS_NS@._html.wizard.nextClicked;
        domNode.previousClicked = @JS_NS@._html.wizard.previousClicked;
        domNode.cancelClicked = @JS_NS@._html.wizard.cancelClicked;
        domNode.finishClicked = @JS_NS@._html.wizard.finishClicked;
        domNode.closeClicked = @JS_NS@._html.wizard.closeClicked;
        domNode.gotoStepClicked = @JS_NS@._html.wizard.gotoStepClicked;
        domNode.closePopup = @JS_NS@._html.wizard.closePopup;
        domNode.closeAndForward = @JS_NS@._html.wizard.closeAndForward;
        domNode.wizOnLoad = @JS_NS@._html.wizard.wizOnLoad;
        domNode.resize_hack = @JS_NS@._html.wizard.resize_hack;

        return true;
    },

    /**
     * @ignore
     */
    nextClicked: function() {
        return true;
    },

    /**
     * @ignore
     */
    previousClicked: function() {
        return true;
    },

    /**
     * @ignore
     */
    cancelClicked: function() {
        return true;
    },

    /**
     * @ignore
     */
    closeClicked: function() {
        return true;
    },

    /**
     * @ignore
     */
    finishClicked: function() {
        return true;
    },

    /**
     * @ignore
     */
    gotoStepClicked: function() {
        return true;
    },

    /**
     * Close popup.
     *
     * @return {boolean} true if successful; otherwise, false.
     */
    closePopup: function() {
        window.close();
        return true;
    },

    /**
     * Close the wizard popup and forward to "submitPage" by submitting
     * "submitForm".
     * <p>
     * When the wizard closes it is often necessary to send
     * a request to a page that will make use of the data collected
     * during a wizard session. This method does this by obtaining the
     * form element "submitForm" from the page that launched the
     * the wizard. This means that the page that launched the wizard
     * popup must still be visible in a browser window. If that form
     * is found, the "action" property is set to "submitPage" and the
     * "submit" method of that "submitForm" is executed.
     * The popup window is then closed. 
     * </p><p>
     * However due to JSF's client side state saving mode an extra step
     * must be taken. If the application is operating with client side
     * state saving, JSF will ignore the "submitPage" value of the
     * submitted form's "action" property and will send the request to the
     * view defined in the saved state, saved in an element in "submitForm".
     * </p><p>
     * If the application is configured for client side state saving and
     * the "submitPage" is different from the page that lauched the wizard,
     * set "cleartState" to true. This method will clear the saved state 
     * before submitting the form. The "clearState" default value is false
     * and the saved state will not be cleared.
     * </p><p>
     * The "clearState" functionality only works with Sun's RI.
     * </p>
     *
     * @param {boolean} submitForm
     * @param {boolean} submitPage
     * @param {boolean} clearState
     * @return {boolean} true if successful; otherwise, false.
     */
    closeAndForward: function(submitForm, submitPage, clearState) {
        var f = window.opener.document.getElementById(submitForm);
        if (f == null) {
            console.debug("Can't find form " + submitForm); // See Firebug console.
            window.close();
        }

        if (clearState != null && clearState == true) {
            var elements = f.elements;
            var clientstate = null;
            for (var i = 0; i < elements.length; ++i) {
                // This only works for the Sun RI and is
                // dependent on the RIConstants.FACES_VIEW value
                // of "com.sun.faces.VIEW"
                //
                if (elements[i].name == this.facesViewState) {
                    clientstate = elements[i];
                    break;
                }
            }
            if (clientstate != null) {
                f.removeChild(clientstate);
            }
        }

        f.action = submitPage;
        f.submit();
        window.close();
        return true;
    }, 

    /**
     * This method must be assigned to the onload handler of the onLoad
     * attribute of the ui:body tag if the wizard is to operate properly on IE.
     *
     * @return {boolean} true if successful; otherwise, false.
     */
    wizOnLoad: function() {
        var stepsid = this.id + "_stepspane";
        var helpid = this.id + "_helppane";
        var wizbdyid = this.id + "_WizBdy";
        return this.resize_hack(helpid, stepsid, wizbdyid);
    },

    /**
     * used only for popup window and IE, and called by wizOnLoad.
     *
     * @return {boolean} true if successful; otherwise, false.
     */
    resize_hack: function(helpid, stepsid, wizbdyid) {
        if (@JS_NS@._base.browser._isIe5up()) {

            var bdy = document.getElementById(wizbdyid);
            if (bdy != null) {
		var newheight = document.documentElement.clientHeight;
                bdy.style.height = newheight - 145;
                if (helpid != null && helpid != '') {
                    var help = document.getElementById(helpid);
                    if (help != null) {
                        help.style.height = newheight - 90;
                    }
                }
                if (stepsid != null && stepsid != '') {
                    var steps = document.getElementById(stepsid);
                    if (steps != null) {
                        steps.style.height = newheight - 90;
                    }
                }
            }
        }
        return true;
    }
};
