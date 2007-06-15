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

dojo.provide("webui.@THEME@.widget.common");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");

/**
 * This closure contains common functions of the webui.@THEME@.widget module.
 */
webui.@THEME@.widget.common = {
    /**
     * This function is used to create a widget.
     *
     * Note: The props argument must be a JSON object containing a _widgetType
     * value so the correct widget may be created. If props also contains a 
     * _module property, the specified resources shall be retrieved before 
     * creating the widget.
     *
     * Note: The position argument is passed though to Dojo's createWidget
     * function. Valid values consist of "last", "first", etc. -- see Dojo docs.
     * If the position is null, the given parentNode is replaced by the widget.
     *
     * @param props Key-Value pairs of properties.
     * @param parentNode The parent node used to add widget.
     * @param position The position (e.g., "first", "last", etc.) to add widget.
     */
    createWidget: function(props, parentNode, position) {
        if (props == null) {
            return null;
        }

        // Destroy previously created widgets, events, etc.
        webui.@THEME@.widget.common.destroyWidget(props.id);

        // Retrieve required module.
        if (props._module) {
            webui.@THEME@.widget.common.require(props._module);
        }

        // Create widget.
        var widget = dojo.widget.createWidget(props._widgetType, props,
            parentNode, position);

        // Register widget so that it may be destroyed properly.
        webui.@THEME@.widget.common.registerWidget(widget);

        return widget;
    },

    /**
     * This function is used to detroy a widget.
     *
     * @param id The widget id to destroy.
     */
    destroyWidget: function(id) {
        if (id == null) {
            return false;
        }

        // Destroy previously created widgets, events, etc.
        var widget = dojo.widget.byId(id);
        if (widget) {
            return widget.destroy();
        }
        return false;
    },   

    /**
     * This function is used to register widgets with the closest ancestor.
     *
     * Note: Registering ensures that when a widget is destroyed, all of its 
     * chlidren are destroyed as well. This includes cleaning the browser of 
     * events associated with the DOM node. It also allows children to be 
     * notified of resize events through the onResized() function.
     *
     * @param widget The widget to register.
     */
    registerWidget: function(widget) {
        if (widget == null) {
            return null;
        }

        // Search the DOM tree until a parent widget is found.
        var curNode = widget.domNode.parentNode;
        while (curNode != null) {
            var parentWidget = dojo.widget.byId(curNode.id);
            if (parentWidget) {
                // Register with ancestor widget.
                parentWidget.registerChild(widget, parentWidget.children.length);
                break;
            }
            curNode = curNode.parentNode;
        }
        return null;
    },

    /**
     * This function is used to replace a document fragment with a newly created
     * widget -- see the createWidget() function.
     *
     * Note: The fragment is used as a temporary place holder so that a widget
     * may be added to the document in the proper location. It is assumed that
     * the HTML element (i.e., document fragment) has the same id as the widget.
     *
     * Note: The props argument must be a JSON object containing a _widgetType
     * value so the correct widget may be created. If props also contains a 
     * _module property, the specified resources shall be retrieved before 
     * creating the widget.
     *
     * @param props Key-Value pairs of properties.
     */
    replaceFragment: function(props) {
        if (props == null) {
            return null;
        }
        var domNode = document.getElementById(props.id);
        return (domNode)
            ? webui.@THEME@.widget.common.createWidget(props, domNode)
            : null;
    },

    /**
     * This function is used to obtain a module resources.
     *
     * @param module The module resource to retrieve.
     */
    require: function(module) {
        if (module == null) {
            return false;
        }
        
        // Warning: Do not use dojo.require() here.
        //
        // Dojo appears to parse for dojo.require() statments when 
        // djConfig.debugAtAllCosts is true. At this time, "modules" is 
        // undefined and an exception is thrown.
        dojo.require.apply(dojo, [module]);
        return true;
    }
}

//-->
