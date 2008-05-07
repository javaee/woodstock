/*
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
 * Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 */

package com.sun.webui.jsf.theme;

/**
 * <p> This class contains javascript related theme constants.</p>
 * TODO: Eventually these need to move to a theme-based
 * resource file.
 */

public class ThemeJavascript {
    /**
     * A key that defines the bootstrap JavaScript file.
     */
    public static final String BOOTSTRAP = "bootstrap";

    /**
     * A key that defines the uncompressed bootstrap JavaScript file.
     */
    public static final String BOOTSTRAP_UNCOMPRESSED = "bootstrapUncompressed";

    /**
     * A properties file key whose value is a space separated list of
     * keys identifying javascript files that are included in every page.
     */
    public static final String GLOBAL = "global";

    /**
     * A key that defines the JSF Extensions JavaScript file.
     */
    public static final String JSFX = "jsfx";

    /**
     * A key that defines the uncompressed JSF Extensions JavaScript file.
     */
    public static final String JSFX_UNCOMPRESSED = "jsfxUncompressed";

    /**
     * A key that defines the JSF Extensions module.
     */
    public static final String JSFX_MODULE = "jsfxModule";

    /**
     * A key that defines the path for the webui module.
     */
    public static final String MODULE_PATH = "modulePath";

    /**
     * A key that defines the path for the uncompressed webui module.
     */
    public static final String MODULE_PATH_UNCOMPRESSED = "modulePathUncompressed";

    /**
     * A key that defines the webui module.
     */
    public static final String MODULE = "module";

    /**
     * A key that defines the Prototype JavaScfript file.
     */
    public static final String PROTOTYPE = "prototype";

    /**
     * A key that defines the uncompressed Prototype JavaScfript file.
     */
    public static final String PROTOTYPE_UNCOMPRESSED = "prototypeUncompressed";

    /**
     * A key that defines the javascript theme bundle. This is 
     * the basename of the file found in the nls directories.
     */
    public static final String THEME_BUNDLE = "themeBundle";

    /**
     * A key that defines the theme module.
     */
    public static final String THEME_MODULE = "themeModule";

    /**
     * A key that defines the path for the theme module.
     */
    public static final String THEME_MODULE_PATH = "themeModulePath";

    /**
     * A key that defines the path for the uncompressed theme module.
     */
    public static final String THEME_MODULE_PATH_UNCOMPRESSED = 
	"themeModulePathUncompressed";

    /**
     * A Javascript file that contains common webui functions, but without the 
     * default Ajax implementation.
     */
    public static final String WEBUI = "webui";

    /**
     * A Javascript file that contains common uncompressed webui functions, but
     * without the default Ajax implementation.
     */
    public static final String WEBUI_UNCOMPRESSED = "webuiUncompressed";

    /**
     * A Javascript file that contains all webui functions, but without the 
     * default Ajax implementation.
     */
    public static final String WEBUI_ALL = "webuiAll";

    /**
     * A Javascript file that contains all uncompressed webui functions, but
     * without the default Ajax implementation.
     */
    public static final String WEBUI_ALL_UNCOMPRESSED = "webuiAllUncompressed";

    /**
     * A Javascript file that contains common webui functions and the default
     * Ajax implementation based on JSF Extensions.
     */
    public static final String WEBUI_JSFX = "webuiJsfx";

    /**
     * A Javascript file that contains common uncompressed webui functions and 
     * the default Ajax implementation based on JSF Extensions.
     */
    public static final String WEBUI_JSFX_UNCOMPRESSED = "webuiJsfxUncompressed";

    /**
     * A Javascript file that contains all webui functions and the default Ajax 
     * implementation based on JSF Extensions.
     */
    public static final String WEBUI_JSFX_ALL = "webuiJsfxAll";

    /**
     * A Javascript file that contains all uncompressed webui functions and the
     * default Ajax implementation based on JSF Extensions.
     */
    public static final String WEBUI_JSFX_ALL_UNCOMPRESSED = "webuiJsfxAllUncompressed";

    /**
     * A Javascript file that contains common webui functions and the default
     * Ajax implementation based on XHR.
     */
    public static final String WEBUI_XHR = "webuiXhr";

    /**
     * A Javascript file that contains common uncompressed webui functions and 
     * the default Ajax implementation based on XHR.
     */
    public static final String WEBUI_XHR_UNCOMPRESSED = "webuiXhrUncompressed";

    /**
     * A Javascript file that contains all webui functions and the default Ajax 
     * implementation based on XHR.
     */
    public static final String WEBUI_XHR_ALL = "webuiXhrAll";

    /**
     * A Javascript file that contains all uncompressed webui functions and the
     * default Ajax implementation based on XHR.
     */
    public static final String WEBUI_XHR_ALL_UNCOMPRESSED = "webuiXhrAllUncompressed";

    /**
     * A key that defines the XHR module.
     */
    public static final String XHR_MODULE = "xhrModule";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Deprecations
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * A Javascript file that contains functions for manipulating
     * the AddRemove component.
     *
     * @deprecated
     */
    public static final String ADD_REMOVE = GLOBAL;

    /**
     * A Javascript file that contains general functions used by
     * simple components.
     *
     * @deprecated
     */
    public static final String BASIC = GLOBAL;

    /**
     * A javascript file that contains functions for manipulating
     * the Calendar component.
     *
     * @deprecated
     */
    public static final String CALENDAR = GLOBAL;

    /**
     * A Javascript file that contains functions for manipulating
     * cookies.
     *
     * @deprecated
     */
    public static final String COOKIE = GLOBAL;

    /**
     * A Javascript file that contains functions for manipulating
     * the common tasks section component.
     *
     * @deprecated
     */
    public static final String COMMONTASKSSECTION = GLOBAL;

    /**
     * A Javascript file that contains common Dojo dijit functions.
     * 
     * @deprecated Dojo is no longer included in page.
     */
    public static final String DIJIT = "dijit";

    /**
     * A Javascript file that contains common uncompressed Dojo digit functions.
     * 
     * @deprecated Dojo is no longer included in page.
     */
    public static final String DIJIT_UNCOMPRESSED = "dijitUncompressed";

    /**
     * A Javascript file that contains all Dojo dijit functions.
     * 
     * @deprecated Dojo is no longer included in page.
     */
    public static final String DIJIT_ALL = "dijitAll";

    /**
     * A Javascript file that contains all uncompressed Dojo digit functions.
     * 
     * @deprecated Dojo is no longer included in page.
     */
    public static final String DIJIT_ALL_UNCOMPRESSED = "dijitAllUncompressed";

    /**
     * A Javascript file that contains Dojo functions.
     * 
     * @deprecated Dojo is no longer included in page.
     */
    public static final String DOJO = "dojo";

    /**
     * A Javascript file that contains uncompressed Dojo functions.
     * 
     * @deprecated Dojo is no longer included in page.
     */
    public static final String DOJO_UNCOMPRESSED = "dojoUncompressed";

    /**
     * The path to custom Dojo module.
     * 
     * @deprecated Dojo is no longer included in page.
     */
    public static final String DOJO_MODULE_PATH = "dojoModulePath";

    /**
     * The path to uncompressed, custom Dojo module.
     * 
     * @deprecated Dojo is no longer included in page.
     */
    public static final String DOJO_MODULE_PATH_UNCOMPRESSED = "dojoModulePathUncompressed";

    /**
     * A Javascript file that contains DynaFaces functions.
     *
     * @deprecated
     */
    public static final String DYNAFACES = GLOBAL;

    /**
     * A Javascript file that contains functions for manipulating
     * the EditableList component.
     *
     * @deprecated
     */
    public static final String EDITABLE_LIST = GLOBAL;
    
    /**
     * A Javascript file that contains functions for manipulating
     * the FileChooser component.
     *
     * @deprecated
     */
    public static final String FILE_CHOOSER = GLOBAL;
    
    /**
     * A Javascript file that contains functions for maintaining
     * the focus within the page.
     *
     * @deprecated
     */
    public static final String FOCUS_COOKIE = GLOBAL;

    /**
     * A Javascript file that contains JSON functions.
     * 
     * @deprecated JSON is no longer included in page.
     */
    public static final String JSON = "json";

    /**
     * A Javascript file that contains uncompressed JSON functions.
     * 
     * @deprecated JSON is no longer included in page.
     */
    public static final String JSON_UNCOMPRESSED = "jsonUncompressed";

    /**
     * A Javascript prefix for locating function names.
     *
     * @deprecated Use MODULE_PREFIX.
     */
    public static final String JS_PREFIX = MODULE;

    /**
     * The module to prefix to webui resources.
     * 
     * @deprecated Use MODULE.
     */
    public static final String MODULE_PREFIX = MODULE;

    /**
     * A Javascript file that contains functions for manipulating
     * the OrderableList component.
     *
     * @deprecated
     */
    public static final String ORDERABLE_LIST = GLOBAL;

    /**
     * A Javascript file that contains functions for manipulating
     * the ProgressBar component.
     *
     * @deprecated
     */
    public static final String PROGRESSBAR = GLOBAL;

    /**
     * A Javascript file that contains functions for manipulating
     * the ProgressBar component based on JSF Extensions.
     *
     * @deprecated
     */
    public static final String PROGRESSBAR_DYNAFACES = GLOBAL;

    /**
     * A Javascript file that contains functions for maintaining
     * the scroll position within a page.
     *
     * @deprecated
     */
    public static final String SCROLL_COOKIE = GLOBAL;
    
    /**
     * A javascript file that contains functions for manipulating
     * the Scheduler component.
     *
     * @deprecated
     */
    public static final String SCHEDULER = GLOBAL;
    
    /**
     * A Javascript file that contains functions for manipulating
     * component styles.
     *
     * @deprecated
     */
    public static final String STYLESHEET = GLOBAL;

    /**
     * A Javascript file that contains functions for manipulating
     * the Table component.
     *
     * @deprecated
     */
    public static final String TABLE = GLOBAL;

    /**
     * A Javascript file that contains functions for manipulating
     * the Tree component.
     *
     * @deprecated
     */
    public static final String TREE = GLOBAL;

    /**
     * A Javascript file that contains functions for manipulating
     * the Wizard component.
     *
     * @deprecated
     */
    public static final String WIZARD = GLOBAL;

    /**
     * A Javascript file that contains common functions for widgets.
     *
     * @deprecated
     */
    public static final String WIDGET = GLOBAL;

    /**
     * The location of the widget module assigned via Dojo.
     *
     * @deprecated Use MODULE_PATH.
     */
    public static final String WIDGET_MODULE = MODULE_PATH;

    /**
     * This private constructor prevents this class from being instantiated
     * directly as its only purpose is to provide image constants.
     */
    private ThemeJavascript() {
	// do nothing
    }
}
