/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.webui.jsf.theme;

/**
 * <p> This class contains javascript related theme constants.</p>
 * TODO: Eventually these need to move to a theme-based
 * resource file.
 */
public class ThemeJavascript {

    /**
     * A Javascript file that contains Dojo functions.
     */
    public static final String DOJO = "dojo";
    /**
     * A properties file key whose value is a space separated list of
     * keys identifying javascript files that are included in every page.
     */
    public static final String GLOBAL = "global";
    /**
     * A Javascript file that contains JSON functions.
     */
    public static final String JSON = "json";
    /**
     * A Javascript file that contains JSF Extensions functions.
     */
    public static final String JSFX = "jsfx";
    /**
     * The path to module resources.
     */
    public static final String MODULE_PATH = "modulePath";
    /**
     * The module to prefix to all resources.
     */
    public static final String MODULE_PREFIX = "modulePrefix";
    /**
     * A Javascript file that contains Prototype functions.
     */
    public static final String PROTOTYPE = "prototype";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Deprecations
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * A Javascript file that contains functions for manipulating
     * the AddRemove component.
     *
     * @deprecated
     */
    public static final String ADD_REMOVE = "global";
    /**
     * A Javascript file that contains general functions used by
     * simple components.
     *
     * @deprecated
     */
    public static final String BASIC = "global";
    /**
     * A javascript file that contains functions for manipulating
     * the Calendar component.
     *
     * @deprecated
     */
    public static final String CALENDAR = "global";
    /**
     * A Javascript file that contains functions for manipulating
     * cookies.
     *
     * @deprecated
     */
    public static final String COOKIE = "global";
    /**
     * A Javascript file that contains functions for manipulating
     * the common tasks section component.
     *
     * @deprecated
     */
    public static final String COMMONTASKSSECTION = "global";
    /**
     * A Javascript file that contains DynaFaces functions.
     *
     * @deprecated
     */
    public static final String DYNAFACES = "global";
    /**
     * A Javascript file that contains functions for manipulating
     * the EditableList component.
     *
     * @deprecated
     */
    public static final String EDITABLE_LIST = "global";
    /**
     * A Javascript file that contains functions for manipulating
     * the FileChooser component.
     *
     * @deprecated
     */
    public static final String FILE_CHOOSER = "global";
    /**
     * A Javascript file that contains functions for maintaining
     * the focus within the page.
     *
     * @deprecated
     */
    public static final String FOCUS_COOKIE = "global";
    /**
     * A Javascript prefix for locating function names.
     *
     * @deprecated Use MODULE_PREFIX.
     */
    public static final String JS_PREFIX = "modulePrefix";
    /**
     * A Javascript file that contains functions for manipulating
     * the OrderableList component.
     *
     * @deprecated
     */
    public static final String ORDERABLE_LIST = "global";
    /**
     * A Javascript file that contains functions for manipulating
     * the ProgressBar component.
     *
     * @deprecated
     */
    public static final String PROGRESSBAR = "global";
    /**
     * A Javascript file that contains functions for manipulating
     * the ProgressBar component based on JSF Extensions.
     *
     * @deprecated
     */
    public static final String PROGRESSBAR_DYNAFACES = "global";
    /**
     * A Javascript file that contains functions for maintaining
     * the scroll position within a page.
     *
     * @deprecated
     */
    public static final String SCROLL_COOKIE = "global";
    /**
     * A javascript file that contains functions for manipulating
     * the Scheduler component.
     *
     * @deprecated
     */
    public static final String SCHEDULER = "global";
    /**
     * A Javascript file that contains functions for manipulating
     * component styles.
     *
     * @deprecated
     */
    public static final String STYLESHEET = "global";
    /**
     * A Javascript file that contains functions for manipulating
     * the Table component.
     *
     * @deprecated
     */
    public static final String TABLE = "global";
    /**
     * A Javascript file that contains functions for manipulating
     * the Tree component.
     *
     * @deprecated
     */
    public static final String TREE = "global";
    /**
     * A Javascript file that contains functions for manipulating
     * the Wizard component.
     *
     * @deprecated
     */
    public static final String WIZARD = "global";
    /**
     * A Javascript file that contains common functions for widgets.
     *
     * @deprecated
     */
    public static final String WIDGET = "global";
    /**
     * The location of the widget module assigned via Dojo.
     *
     * @deprecated Use MODULE_PATH.
     */
    public static final String WIDGET_MODULE = "modulePath";

    /**
     * This private constructor prevents this class from being instantiated
     * directly as its only purpose is to provide image constants.
     */
    private ThemeJavascript() {
        // do nothing
    }
}
