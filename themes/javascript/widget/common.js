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

dojo.provide("webui.@THEME@.widget.common");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.theme.*");

/**
 * This closure contains common functions of the webui.@THEME@.widget module.
 */
webui.@THEME@.widget.common = {
    /**
     * This function is used to add a widget, HTML fragment, or static string to
     * the given domNode.
     *
     * If props is a JSON object, containing a widgetName property, a widget
     * shall be created. The newly created widget shall be added as a child of 
     * the given domNode. If props also contains a module property, the 
     * specified resource shall be retrieved before creating the widget.
     *
     * If props is a JSON object, containing a fragment property instead of
     * widgetName, it shall be added as the innerHTML of the given domNode. 
     * If props also contains an escape property, that shall take precedence
     * over the addFragment's escape param.
     *
     * If props is a string, it shall be added as the innerHTML of the given 
     * domNode. By default, all strings shall be HTML escaped.
     *
     * The position param is passed to Dojo's createWidget function. Valid
     * values consist of "last", "first", etc. -- see Dojo docs. In general, 
     * position only applies when creating widgets; however, if the position
     * param is null, existing child nodes are removed from the domNode. Note
     * that the domNode is never removed because it may be used as a place
     * holder.
     *
     * @param domNode The DOM node used to add widget.
     * @param props Key-Value pairs of properties.
     * @param position The position (e.g., "first", "last", etc.) to add widget.
     * @param escape HTML escape static strings -- default is true.
     * @returns The newly added widget or HTML string.
     */
    addFragment: function(domNode, props, position, escape) {
        if (domNode == null || props == null) {
            return null;
        }

        // If position is null, remove existing nodes. The contents shall be
        // replaced by the newly created widget.
        if (position == null) {
            position = "last"; // Default.
            webui.@THEME@.widget.common.removeChildNodes(domNode);
        }

        // Add fragment.
        if (typeof props == 'string') {
            // Strip script fragments, set innerHTML property, and
            // eval scripts using a timeout.
            //
            // Note that using Dojo's ContentPane widget would have
            // been more preferable than creating a new dependency
            // based on Prototype. However, as of version .4.1, Dojo
            // still does not use a timeout to eval JavaScript; thus,
            // IE generates errors with innerHTML. For example: 
            //
            // var pane = dojo.widget.createWidget("ContentPane", { 
            //     executeScripts: true,
            //     scriptSeparation: false
            // }, domNode, position);
            // pane.setContent(props);
            //
            // "The problem has to do with the browser's poor
            // threading model. Basically, once some JavaScript code
            // is running, all other threads of execution are on hold
            // until the running thread is finished with it's
            // task. This includes whatever thread of execution that
            // updates the DOM model when new content is inserted via
            // innerHTML. For example if you do:
            //
            // foo.innerHTML = '<span id="bar">Bar</span>';
            // var bar = document.getElementById('bar');
            //
            // This code will sometimes fail because although you
            // added the content via innerHTML the DOM may not have
            // been updated and therefore the "bar" element does not
            // exist in the DOM yet. To work around this you can do:
            //
            // foo.innerHTML = '<span id="bar">Bar</span>';
            // setTimeout(function() {
            //     var bar = document.getElementById('bar');
            // }, 10);
            //
            // This will work because in 10 milliseconds whatever
            // event handler is running will have finished, the DOM
            // will update, then the callback will execute and have no
            // problem accessing the DOM element."
            //
            // The full discussion on this topic can be found at:
            //
            // http://www.ruby-forum.com/topic/73990
            //
            if (escape != null && new Boolean(escape).valueOf() == false) {
                webui.@THEME@.widget.common.appendHTML(domNode, 
                    props.stripScripts());

                // Evaluate JavaScript.
                setTimeout(function() {props.evalScripts()}, 10);
            } else {
                // Static strings must be HTML escaped by default.
                webui.@THEME@.widget.common.appendHTML(domNode, 
                    dojo.string.escape("html", props));
            }
            return props;
        } else if (props.widgetName) {
            // Create widget.
            return webui.@THEME@.widget.common.createWidget(domNode, props, position);
        } else if (props.fragment) {
            // Add fragment -- props.escape takes precedence.
            return webui.@THEME@.widget.common.addFragment(domNode, props.fragment, position, 
                (props.escape != null) ? props.escape : escape);
        }
        return null;
    },

    /**
     * This function is used to append HTML strings to the innerHTML property of
     * the given domNode.
     *
     * Concatenating innerHTML with new strings does not always work. When
     * adding multiple HTML elements to domNode, we can get into a situation
     * where domNode.innerHTML may not yet contain all the changes made to 
     * the previously added DOM node. Therefore, we shall wrap new strings in an
     * HTML span element so it may be added as a child of domNode.
     *
     * @param domNode The DOM node used to append string.
     * @param html The HTML string to append.
     */
    appendHTML: function(domNode, html) {
        if (domNode.innerHTML != null && domNode.innerHTML.length > 0) {
            var span = document.createElement('span');            
            span.innerHTML = html;
            domNode.appendChild(span);
        } else {
            // Don't need span when innerHTML is empty.
            domNode.innerHTML = html;
        }
        return true;
    },

    /**
     * This function is used to create a widget.
     *
     * Note: The props argument must be a JSON object containing a widgetName
     * value so the correct widget may be created. If props also contains a 
     * module property, the specified resources shall be retrieved before 
     * creating the widget.
     *
     * Note: The position argument is passed though to Dojo's createWidget
     * function. Valid values consist of "last", "first", etc. -- see Dojo docs.
     * If the position is null, the given domNode is replaced by the widget.
     *
     * @param domNode The DOM node used to add widget.
     * @param props Key-Value pairs of properties.
     * @param position The position (e.g., "first", "last", etc.) to add widget.
     * @returns The newly created widget.
     */
    createWidget: function(domNode, props, position) {
        if (props == null) {
            return null;
        }

        // Destroy previously created widgets, events, etc.
        webui.@THEME@.widget.common.destroyWidget(props.id);

        // Retrieve required module.
        if (props.module) {
            webui.@THEME@.widget.common.require(props.module);
        }

        // Create widget.
        var widget = dojo.widget.createWidget(props.widgetName, props,
            domNode, position);

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
     * This function is used to extend the given object with Key-Value pairs of
     * properties. If a property is an object containing Key-Value pairs itself,
     * this function is called recursively to preserve data which is not
     * explicitly extended.
     *
     * Note: If only top level properties must be replaced, use Prototype's 
     * Object.extend function.
     *
     * @param obj The object to extend.
     * @param props Key-Value pairs of properties.
     */
    extend: function(obj, props) {
        if (obj == null || props == null) {
            return false;
        }
        for (var property in props) {
            if (obj[property] && typeof obj[property] == "object") {
                webui.@THEME@.widget.common.extend(obj[property], props[property]);
            } else {
                obj[property] = props[property];
            }
        }
        return true;
    },
    
    /**
     * This function returns Object literals for a theme based image widget.
     *
     * It adds the necessary "module" and "widgetName" and theme properties for
     * "imageKey". If "imageKey" doesn't exist in the theme, return null.
     *
     * @param key A key defining a theme "images" property.
     * @param props Extra image properties.
     */
    getImage: function(key, props) {
        var image = webui.@THEME@.theme.common.getImage(key);
        if (image == null) {
            return null;
        }

        // Set default module and widget name.
        image.module = "webui.@THEME@.widget.image";
        image.widgetName = "webui.@THEME@:image";

        // Add extra properties
        if (props != null) {
            webui.@THEME@.widget.common.extend(image, props);
        }
        return image;
    },

    /**
     * Get array containing the absolute left and top position of the given DOM
     * node relative to the browser window.
     *
     * @param domNode The DOM node compute position for.
     */
    getPosition: function(domNode) {
        var leftPos = topPos = 0;
        if (domNode.offsetParent) {
            leftPos = domNode.offsetLeft;
            topPos = domNode.offsetTop;
            while (domNode = domNode.offsetParent) {
                leftPos += domNode.offsetLeft;
                topPos += domNode.offsetTop;
            }
        }
        return [leftPos, topPos];
    },

    /**
     * Get the page height, handling standard noise to mitigate browser
     * differences.
     */
    getPageHeight: function() {
        // Mozilla browsers.
        if (window.innerHeight) {
            return window.innerHeight;
        }

        // IE strict mode
        if (document.documentElement.clientHeight > 0) {
            return document.documentElement.clientHeight; 
        }                

        // IE quirks mode.
        if (document.body.clientHeight) {
            return document.body.clientHeight;
        }
        return null;
    },

    /**
     * Get the page width, handling standard noise to mitigate browser 
     * differences.
     */
    getPageWidth: function() {
        // Mozilla browsers.
        if (window.innerWidth) {
            return window.innerWidth;
        }

        // IE strict mode.
        if (document.documentElement.clientWidth > 0) {
            return document.documentElement.clientWidth; 
        }

        // IE quirks mode.
        if (document.body.clientWidth) {
            return document.body.clientWidth;
        }
        return null;
    },

    /**
     * This function is used to obtain a template path, or returns null
     * if key is not found or is not a path, i.e. begins with "<".
     *
     * @param key A key defining a theme "templates" property.
     */
    getTemplatePath: function(key) {
        var template = webui.@THEME@.theme.common.getTemplate(key);
        if (webui.@THEME@.widget.common.isTemplatePath(template)) {
            return webui.@THEME@.theme.common.getPrefix() + "/" + template;
        } else {
            return null;
        }
    },

    /**
     * This function is used to obtain a template string, or returns null
     * if key is not found or is not a string, i.e. does not begin with "<".
     *
     * @param key A key defining a theme "templates" property.
     */
    getTemplateString: function(key) {
        var template = webui.@THEME@.theme.common.getTemplate(key);
        if (!webui.@THEME@.widget.common.isTemplatePath(template)) {
            return template;
        } else {
            return null;
        }
    },

    /**
     * This function is used to test template strings. Return true if the
     * "template" is a template path, and false if it is a template String. 
     * Returns false if the value is null or the empty string.
     */
    isTemplatePath: function(template) {
        return (template != null && template.charAt(0) != '<');
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
            return false;
        }
        
        // Search the DOM tree for an ancestor widget. We need to perform this 
        // search for widgets created via HTML fragments. When using strings, 
        // JavsScript is evaluated and the widget parent is not known.
        //
        // Note: In order to find an ancestor, the DOM node id must be set prior
        // to creating widget children (e.g., via the fillInTemplate() functon).
        var curNode = widget.domNode.parentNode;
        while (curNode != null) {
            var parentWidget = dojo.widget.byId(curNode.id);
            if (parentWidget) {
                // Register with ancestor widget.
                parentWidget.registerChild(widget, parentWidget.children.length);
                return true;
            }
            curNode = curNode.parentNode;
        }
        return false;
    },

    /**
     * This function is used to remove child nodes from given DOM node.
     *
     * Note: Child nodes may be cleared using the innerHTML property. However,
     * IE fails when this property is set via the widget's fillInTemplate 
     * function. In this case, DOM nodes shall be removed manually using the 
     * Node APIs.
     *
     * @param domNode The DOM node to remove child nodes.
     */
    removeChildNodes: function(domNode) {
        if (domNode == null) {
            return false;
        }

        // To do: Should we destroy widgets here? Unless new widgets use the
        // same id, the old widgets may never be removed.

        try {
            domNode.innerHTML = ""; // Cannot be null on IE.
        } catch (e) {
            // Iterate over child nodes.
            while (domNode.hasChildNodes()) {
                var node = domNode.childNodes[0];
                domNode.removeChild(node);
            }
        }
        return true;
    },

    /**
     * This function is used to replace an HTML element with a newly created
     * widget -- see the createWidget() function.
     *
     * Note: The element is used as a temporary place holder so that a widget
     * may be added to the document in the proper location. It is assumed that
     * the HTML element (i.e., document fragment) has the same id as the widget.
     *
     * Note: The props argument must be a JSON object containing a widgetName
     * value so the correct widget may be created. If props also contains a 
     * module property, the specified resources shall be retrieved before 
     * creating the widget.
     *
     * @param props Key-Value pairs of properties.
     */
    replaceElement: function(props) {
        if (props == null) {
            return null;
        }
        var domNode = document.getElementById(props.id);
        return (domNode)
            ? webui.@THEME@.widget.common.createWidget(domNode, props)
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
    },

    /**
     * This function sleeps for specified milli seconds.
     * 
     * @param delay 
     */
    sleep:  function(delay) {
        var start = new Date();
        var exitTime = start.getTime() + delay;

        while(true) {
            start = new Date();
            if (start.getTime() > exitTime) {
                return true;
            }
        }
        return false;
    },

    /**
     * This function is used to update a widget, HTML fragment, or static
     * string for the given domNode.
     *
     * Note: If the widget associated with props.id already exists, the widget's 
     * setProps() function is invoked with the given props param. If the widget 
     * does not exist, the widget object is instantiated via the addFragment()
     * function -- all params are passed through.
     *
     * @param domNode The DOM node used to add widget.
     * @param props Key-Value pairs of properties.
     * @param position The position (e.g., "first", "last", etc.) to add widget.
     * @param escape HTML escape static strings -- default is true.
     */
    updateFragment: function(domNode, props, position, escape) {
        if (props == null) {
            return false;
        }

        // Ensure props is not a string.
        var widget = (typeof props != 'string') 
            ? dojo.widget.byId(props.id) : null;

        // Update widget or add fragment.
        if (widget && typeof widget.setProps == "function") {
            widget.setProps(props);
        } else {
            webui.@THEME@.widget.common.addFragment(domNode, props, position, escape);
        }
        return true;
    }
}
