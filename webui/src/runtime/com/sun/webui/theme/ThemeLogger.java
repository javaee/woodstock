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
package com.sun.webui.theme;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The <code>ThemeLogger</code> class is a convenince static class that
 * the theme infrastructure uses to log events based on 
 * <code>java.util.logging.Level</code>.
 */
public class ThemeLogger {

    /**
     * This package is the <code>Logger</code> name.
     */
    protected static String LOGGER_NAME = "com.sun.webui.theme";
    /**
     * This identifier is used to tag all messages logged by
     * the <code>ThemeLoggger</code>. It is used to conform to the
     * logging conventions established by Application Server.
     */
    // See com.sun.webui.jsf.util.LogUtil.java
    //
    protected static String LOGGER_KEY = "WEBUITHEME001";
    /**
     * The string that that separates a log message from its key,
     * according to the Application Server convention.
     */
    protected static String LOGGER_MSG_SEPARATOR = ": ";

    /**
     * The default logging level.
     * This implementation defines it as <code>Level.FINEST</code>.
     */
    protected static Level DEFAULT_LEVEL = Level.FINEST;

    /**
     * The theme package's <code>ResourceBundle</code>, this implementation
     * defines it as "com.sun.webui.theme.LogMessages";
     */
    protected static String BUNDLE = "com.sun.webui.theme.LogMessages";

    /**
     * The log message if <code>BUNDLE</code> cannot be loaded.
     */
    protected static String noBundleLogMessage;

    /**
     * The message prefix of key and separator.
     */
    private static String msgPrefix;

    static {
	StringBuilder sb = new StringBuilder();
	sb.append(LOGGER_KEY)
	  .append(LOGGER_MSG_SEPARATOR);
	msgPrefix = sb.toString();
	sb.setLength(0);
	sb.append(msgPrefix)
	  .append("ResourceBundle.getBundle(")
	  .append(BUNDLE)
	  .append(") failed.");
	noBundleLogMessage = sb.toString();
    }
	 
    /**
     * Log a message according to the given level, and forward 
     * throwable to the logger.
     * <code>msg</code> is interpreted as a <code>ResourceBundle</code>
     * defined in the <code>THEME_BUNDLE</code> resource bundle. If it is
     * not defined, then the <code>msg<cocde> is used literally. An
     * attempt is made to format it if <code>args</code> is not null.
     */
    public static void log(Level level, String msg, Object[] args, 
	    Throwable throwable) {

	Logger logger = Logger.getLogger(LOGGER_NAME);
	if (msg == null || msg.length() == 0) {
	    if (throwable != null) {
		logger.log(level == null ? DEFAULT_LEVEL : level,
		    msgPrefix, throwable);
	    }
	    return;
	}

	ResourceBundle bundle = null;
	try {
	    bundle = ResourceBundle.getBundle(BUNDLE);
	} catch (MissingResourceException mre) {
	    logger.log(Level.WARNING, noBundleLogMessage, mre);
	}
	if (bundle != null) {
	    try {
		String tmpMsg = bundle.getString(msg);
		if (tmpMsg != null && tmpMsg.length() != 0) {
		    msg = tmpMsg;
		}
	    } catch (Exception e) {
		// Don't care
	    }
	}
	if (args != null) {
	    try {
		msg = MessageFormat.format(msg, args);
	    } catch (Exception e) {
		// Don't care
	    }
	}
	if (level == null) {
	    level = DEFAULT_LEVEL;
	}
	if (throwable != null) {
	    logger.log(level, msgPrefix + msg, throwable);
	} else {
	    logger.log(level, msgPrefix + msg);
	}
    }
}
