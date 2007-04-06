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

package com.sun.webui.jsf.component.util;

import com.sun.webui.jsf.util.MessageUtil;
import java.util.Locale;

/**
 * Provides access to design-time resources with a Bundle-DT baseName.
 *
 * @author Edwin Goei
 */
public class DesignMessageUtil {
    
    /**
     * Get a message from a design-time resource bundle.
     *
     * @param clazz
     *            class determines package where resources are located
     * @param key
     *            The key for the desired string.
     * @return localized String
     */
    public static String getMessage(Class clazz, String key) {
        return getMessage(clazz, key, null);
    }
    
    /**
     * Get a formatted message from a design-time resource bundle.
     *
     * @param clazz
     *            class determines package where resources are located
     * @param key
     *            The key for the desired string.
     * @param args
     *            The arguments to be inserted into the string.
     * @return localized String
     */
    public static String getMessage(Class clazz, String key, Object args[]) {
        String baseName = clazz.getPackage().getName() + ".Bundle-DT";
        
        // XXX webui-designtime is module jar now, and current (wrong) impl
        // of project classloader doesn't know about it.
        // TODO This arch (missing arch) needs to be revised together with
        // new impl of project classloaders.
        // return MessageUtil.getMessage(baseName, key, args);
        return MessageUtil.getMessage(getLocale(),
                baseName, key, args, DesignMessageUtil.class.getClassLoader());
    }
    
    // XXX Copy from webui/runtime/library MessageUtils.
    // TODO Avoid copy/paste antipattern.
    private static Locale getLocale() {
        //FacesContext context = FacesContext.getCurrentInstance();
        //if (context == null) {
        //return Locale.getDefault();
        //}
        
        //Locale locale = null;
        
        // context.getViewRoot() may not have been initialized at this point.
        //if (context.getViewRoot() != null)
        //locale = context.getViewRoot().getLocale();
        
        //return (locale != null) ? locale : Locale.getDefault();
        
        // Do not depend on FacesContext to get the locale. Here is the problem
        // When Design time container initializes it creates the RI ConfigListener,
        // which in turn instantiates all the Renderers in the Render Kit
        // Some design time renderer calls this class to loaded messages
        // This causes problem because Faces Context is not yet initialized and throws
        // Exception. Since this is a design time only renderer, I'm trying to fix the problem
        // using just Locale.getDefault() - Winston
        return Locale.getDefault();
    }
}
