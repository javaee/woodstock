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

package com.sun.webui.jsf.example.common;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

/**
 * Factory class for retrieving server-side i18n messages for the Java ES(tm)
 * Monitoring Console within the JSF framework. This class does not override
 * all methods in com.sun.webui.jsf.util.MessageUtil; it merely provides a few
 * commonly used methods for getting resources using the Resources.properties
 * files for this web application.
 *
 * @version 1.3 10/10/05
 * @author  Sun Microsystems, Inc.
 */
public class MessageUtil {
    /** The ResourceBundle basename for this web app. */
    static final public String BASENAME = "com.sun.webui.jsf.example.resources.Resources";

    /** Get a message from the application's resource file. */
    static public String getMessage(String key) {
	return getMessage(key, (Object[]) null);
    }

    /** Get a formatted message from the application's resource file. */
    static public String getMessage(String key, String arg) {
	Object[] args = {
	    arg
	};
	return getMessage(key, args);
    }

    /** Get a formatted message from the application's resource file. */
    static public String getMessage(String key, Object[] args) {
	if (key == null) {
	    return key;
	}

	ResourceBundle bundle = ResourceBundle.getBundle(BASENAME, getLocale());
	if (bundle == null) {
	    throw new NullPointerException("Could not obtain resource bundle");
	}

	String message = null;
	try {
	    message = bundle.getString(key);
	} catch (MissingResourceException e) {
	}

	return getFormattedMessage((message != null) ? message : key, args);
    }

    /**
     * Format message using given arguments.
     *
     * @param message The string used as a pattern for inserting arguments.
     * @param args The arguments to be inserted into the string.
     */
    static protected String getFormattedMessage(String message, Object args[]) {
	if ((args == null) || (args.length == 0)) {
	    return message;
	}

	String result = null;
	try {
	    MessageFormat mf = new MessageFormat(message);
	    result = mf.format(args);
	} catch (NullPointerException e) {
	}

	return (result != null) ? result : message;
    }

    /** Get locale from the FacesContext object. */
    protected static Locale getLocale() {
	FacesContext context = FacesContext.getCurrentInstance();
	if (context == null) {
	    return Locale.getDefault();
	}

	// context.getViewRoot() may not have been initialized at this point.
	Locale locale = null;
	if (context.getViewRoot() != null) {
	    locale = context.getViewRoot().getLocale();
	}

	return (locale != null) ? locale : Locale.getDefault();
    }

} // MessageUtil
