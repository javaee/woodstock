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

@JS_NS@._dojo.provide("@JS_NS@.widget._jsfx.textField");

@JS_NS@._dojo.require("@JS_NS@.json");
@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget._jsfx.common");
@JS_NS@._dojo.require("@JS_NS@.widget._jsfx.dynaFaces");
@JS_NS@._dojo.require("@JS_NS@.widget.textField");

/**
 * @class This class contains functions to obtain data asynchronously using JSF
 * Extensions as the underlying transfer protocol.
 * @static
 * @private
 */
@JS_NS@.widget._jsfx.textField = {
    /**
     * This function is used to update widgets with new autoComplete options list.
     *
     * @param {String} id The widget id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to the Ajax transaction.
     * @param {Object} xjson The xjson argument provided to the Ajax transaction.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _autoCompleteCallback: function(elementId, content, closure, xjson) {
        if (elementId == null || content == null) {
            return false;
        }
        
        // Parse JSON text.
        var props = @JS_NS@.json.parse(content);

        // Update text field.
        var widget = @JS_NS@.widget.common.getWidget(elementId);
        widget.setProps(props);       


        // Publish an event for custom AJAX implementations to listen for.
        @JS_NS@._dojo.publish(
            @JS_NS@.widget.textField.event.autoComplete.endTopic, [props]);
        return true;
    },

    /**
     * This function is used to process autoComplete events.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The widget id.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _processAutoCompleteEvent: function(props) {
        if (props == null) {
            return false;
        }
        
        // Dynamic Faces requires a DOM node as the source property.
        var domNode = document.getElementById(props.id);
        
        // Generate AJAX request using the JSF Extensions library.
        DynaFaces.fireAjaxTransaction(
            (domNode) ? domNode : document.forms[0], {
            execute: props.id,
            render: props.id,
            replaceElement: @JS_NS@.widget._jsfx.textField._autoCompleteCallback,
            xjson: {
                id : props.id,
                event: "autocomplete"
            }
        });
        return true;
    },

    /**
     * This function is used to process validation events with Object literals.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The widget id.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _processValidationEvent: function(props) {
        if (props == null) {
            return false;
        }
        
        // Dynamic Faces requires a DOM node as the source property.
        var domNode = document.getElementById(props.id);
        
        // Generate AJAX request using the JSF Extensions library.
        DynaFaces.fireAjaxTransaction(
            (domNode) ? domNode : document.forms[0], {
            execute: props.id,
            render: props.id,
            replaceElement: @JS_NS@.widget._jsfx.textField._validationCallback,
            xjson: {
                id : props.id,
                event: "validate"
            }
        });
        return true;
    },  

    /**
     * This function is used to update widgets.
     *
     * @param {String} id The widget id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to the Ajax transaction.
     * @param {Object} xjson The xjson argument provided to the Ajax transaction.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _validationCallback: function(elementId, content, closure, xjson) {
        if (elementId == null || content == null) {
            return false;
        }
        
        // Parse JSON text.
        var props = @JS_NS@.json.parse(content);

        // Update text field.
        var widget = @JS_NS@.widget.common.getWidget(elementId);
        widget.setProps({
            valid: props.valid,
            errorImage: {
                title: props.detail
            }
        });

        // Notify decoupled widgets.
        if (widget.notify) {
            // Update each given client ID.
            for (var i = 0; i < widget.notify.length; i++) {
                // Get widget associated with client ID.
                var curWidget = @JS_NS@.widget.common.getWidget(widget.notify[i]);
                if (curWidget && typeof curWidget._notify == "function") {
                    curWidget._notify(props);
                }
            }
        }

        // Publish an event for custom AJAX implementations to listen for.
        @JS_NS@._dojo.publish(
            @JS_NS@.widget.textField.event.validation.endTopic, [props]);
        return true;
    }    
};

// Listen for Widget events.
@JS_NS@._dojo.subscribe(@JS_NS@.widget.textField.event.refresh.beginTopic,
    @JS_NS@.widget._jsfx.common, "_processRefreshEvent");
@JS_NS@._dojo.subscribe(@JS_NS@.widget.textField.event.state.beginTopic,
    @JS_NS@.widget._jsfx.common, "_processStateEvent");
@JS_NS@._dojo.subscribe(@JS_NS@.widget.textField.event.submit.beginTopic,
    @JS_NS@.widget._jsfx.common, "_processSubmitEvent");
@JS_NS@._dojo.subscribe(@JS_NS@.widget.textField.event.validation.beginTopic,
    @JS_NS@.widget._jsfx.textField, "_processValidationEvent");
@JS_NS@._dojo.subscribe(@JS_NS@.widget.textField.event.autoComplete.beginTopic,
    @JS_NS@.widget._jsfx.textField, "_processAutoCompleteEvent");
