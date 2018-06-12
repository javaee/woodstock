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


dojo.provide("webui.@THEME@.widget.common");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");

/**
 * Define webui.@THEME@.widget.common name space.
 */
webui.@THEME@.widget.common = {
    /**
     * Helper function to add a widget or string to the given
     * parent node. If props is an object containing a _widgetType
     * value, a widget will be added to the given parent node per the
     * specified position. If props contains a _modules array,
     * the given resources shall be retrieved before creating the
     * widget. If props is a string, it will be added as the contents
     * of the given parent node.
     *
     * Note: The position argument can be null for strings. However,
     * if strings must be appended to the same DOM node, use "last".
     *
     * @param parentNode The parent node used to add widget.
     * @param props Key-Value pairs of properties.
     * @param position The position (e.g., first, last, etc.) to add widget.
     */
    addFragment: function(parentNode, props, position) {
        if (props == null || parentNode == null) {
            return false;
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
            // }, parentNode, position);
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
            // setTimeout(function() {var bar = document.getElementById('bar');},10);
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

            // Append html if postion is given.
            var html = props.stripScripts();
            if (position && parentNode.innerHTML) {
                html = parentNode.innerHTML + html;
            }

            parentNode.innerHTML = html;
            setTimeout(function() {props.evalScripts()}, 10);
        } else {
            if (props._widgetType == null) {
                return false;
            } else {
                // Retrieve required modules.
                if (props._modules) {
                    for (var i = 0; i < props._modules.length; i++) {
                        webui.@THEME@.widget.common.require(props._modules[i]);
                    }
                }

                // Create widget.
                dojo.widget.createWidget(props._widgetType, props, parentNode,
                    (position) ? position : null); // Replace existing node, if null.
            }
        }
        return true;
    },

    /**
     * Helper function to create a widget. This function assumes that
     * an HTML element is used as a place holder for the document
     * fragment (i.e., widget).
     *
     * Note: If props does not contain id and _widgetType properties,
     * a widget shall not be created. If props contains a _modules
     * array, the given resources shall be retrieved before creating
     * the widget.
     *
     * @param props Key-Value pairs of properties.
     */
    createWidget: function(props) {
        if (props == null || props.id == null || props._widgetType == null) {
            return false;
        }
        var oldNode = document.getElementById(props.id);
        if (oldNode == null) {
            return false;
        } else {
            // Retrieve required modules.
            if (props._modules) {
                for (var i = 0; i < props._modules.length; i++) {
                    webui.@THEME@.widget.common.require(props._modules[i]);
                }
            }

            // Create widget.
            var newNode = dojo.widget.createWidget(props._widgetType, props)
            if (newNode != null) {
                // Replace existing child, if any.
                oldNode.parentNode.replaceChild(newNode.domNode, oldNode);
            } else {
                return false;
            }
        }
        return true;
    },

    /**
     * Helper function to obtain a module resource.
     *
     * @param module The module resource to retrieve.
     */
    require: function(module) {
        if (module == null) {
            return false;
        }

        // Warning: Do not use dojo.require() here.
        //
        // Dojo appears to parse JavaScript for dojo.require()
        // statments when djConfig.debugAtAllCosts is true. At
        // the time, "modules" is undefined and an error is thrown.
        dojo.require.apply(dojo, [module]);
        return true;
    },

    /**
     * This function is used to set core properties for the given DOM
     * node with the following Object literals.
     *
     * <ul>
     *  <li>accesskey</li>
     *  <li>className</li>
     *  <li>dir</li>
     *  <li>id</li>
     *  <li>lang</li>
     *  <li>style</li>
     *  <li>title</li>
     *  <li>tabIndex</li>
     *  <li>visible</li>
     * </ul>
     *
     * @param domNode The DOM node to assign properties to.
     * @param props Key-Value pairs of properties.
     */
    setCoreProperties: function(domNode, props) {
        if (domNode == null || props == null) {
            return false;
        }
        if (props.accesskey) { domNode.setAttribute("accesskey", props.accesskey); }
        if (props.className) { domNode.setAttribute("class", props.className); }
        if (props.dir) { domNode.setAttribute("dir", props.dir); }
        if (props.id) { domNode.setAttribute("id", props.id); }
        if (props.lang) { domNode.setAttribute("lang", props.lang); }
        if (props.style) { 
            domNode.style.cssText = props.style; // Required for IE?
        }
        if (props.title) { domNode.setAttribute("title", props.title); }
        if (props.tabIndex) { domNode.setAttribute("tabindex", props.tabIndex); }
        if (props.visible != null) {
            webui.suntheme.common.setVisibleElement(domNode, props.visible);
        }
        return true;
    },

    /**
     * This function is used to set JavaScript properties for the given DOM
     * node with the following Object literals.
     *
     * <ul>
     *  <li>onBlur</li>
     *  <li>onClick</li>
     *  <li>onDblClick</li>
     *  <li>onFocus</li>
     *  <li>onKeyDown</li>
     *  <li>onKeyPress</li>
     *  <li>onKeyUp</li>
     *  <li>onMouseDown</li>
     *  <li>onMouseOut</li>
     *  <li>onMouseOver</li>
     *  <li>onMouseUp</li>
     *  <li>onMouseMove</li>
     * </ul>
     *
     * @param domNode The DOM node to assign properties to.
     * @param props Key-Value pairs of properties.
     */
    setJavaScriptProperties: function(domNode, props) {
        if (domNode == null || props == null) {
            return false;
        }

        // Note: IE does not recognize JSON strings as JavaScript. In order
        // for events to work properly, an anonymous function must be created.
        var is_ie = webui.@THEME@.common.browser.is_ie;
        if (props.onBlur) { 
            domNode.setAttribute("onblur", (is_ie && typeof props.onBlur == 'string')
                ? new Function("event", props.onBlur)
                : props.onBlur);
        }
        if (props.onClick) {
            domNode.setAttribute("onclick", (is_ie && typeof props.onClick == 'string')
                ? new Function("event", props.onClick)
                : props.onClick);
        }
        if (props.onDblClick) {
            domNode.setAttribute("ondblclick", (is_ie && typeof props.onDblClick == 'string')
                ? new Function("event", props.onDblClick)
                : props.onDblClick);
        }
        if (props.onFocus) {
            domNode.setAttribute("onfocus", (is_ie && typeof props.onFocus == 'string')
                ? new Function("event", props.onFocus)
                : props.onFocus);
        }
        if (props.onKeyDown) {
            domNode.setAttribute("onkeydown", (is_ie && typeof props.onKeyDown == 'string')
                ? new Function("event", props.onKeyDown)
                : props.onKeyDown);
        }
        if (props.onKeyPress) {
            domNode.setAttribute("onkeypress", (is_ie && typeof props.onKeyPress == 'string')
                ? new Function("event", props.onKeyPress)
                : props.onKeyPress);
        }
        if (props.onKeyUp) {
            domNode.setAttribute("onkeyup", (is_ie && typeof props.onKeyUp == 'string')
                ? new Function("event", props.onKeyUp)
                : props.onKeyUp);
        }
        if (props.onMouseDown) {
            domNode.setAttribute("onmousedown", (is_ie && typeof props.onMouseDown == 'string')
                ? new Function("event", props.onMouseDown)
                : props.onMouseDown);
        }
        if (props.onMouseOut) {
            domNode.setAttribute("onmouseout", (is_ie && typeof props.onMouseOut == 'string')
                ? new Function("event", props.onMouseOut)
                : props.onMouseOut);
        }
        if (props.onMouseOver) {
            domNode.setAttribute("onmouseover", (is_ie && typeof props.onMouseOver == 'string')
                ? new Function("event", props.onMouseOver)
                : props.onMouseOver);
        }
        if (props.onMouseUp) {
            domNode.setAttribute("onmouseup", (is_ie && typeof props.onMouseUp == 'string')
                ? new Function("event", props.onMouseUp)
                : props.onMouseUp);
        }
        if (props.onMouseMove) {
            domNode.setAttribute("onmousemove", (is_ie && typeof props.onMouseMove == 'string')
                ? new Function("event", props.onMouseMove)
                : props.onMouseMove);
        }
        return true;
    }
}

//-->
