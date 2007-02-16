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

package com.sun.webui.jsf.util;

/**
 * Strategies for finding the current ClassLoader such as
 * <code>Thread.currentThread().getContextClassLoader()</code>
 * do not work during design time, where the notion of the 
 * classpath is more constrained. Please make sure you use 
 * this utility when you need to get hold of the current loader.
 */
public class ClassLoaderFinder {
    
    /**
     * <p>Optional Special ClassLoader to use. For example, at
     * designtime the Creator IDE will set it to the project classloader - 
     * and will reset it whenever
     * <code>FacesContainer.initialize()</code> is called.</p>
     */
    private static ClassLoader customClassLoader = null;
    
    /**
     * <p>Set a preferred ClassLoader to use for loading resources such
     * as themes and messages (via {@link #getCurrentLoader}). If not set 
     * (e.g. is null), the current thread's context class loader will be 
     * used.</p>
     * @param customClassLoader The new class loader to use
     */
    public static void setCustomClassLoader(ClassLoader
            customClassLoader) {
        ClassLoaderFinder.customClassLoader = customClassLoader;
    }
    
    /**
     * <p>Return the best class loader to use for loading resources.
     * This is normally the thread context class loader but can 
     * overridden using {@link #setCustomClassLoader}.
     * </p>
     * @param fallbackClass If there is no context class loader, fall
     * back to using the class loader for the given object
     * @return A ClassLoader to use for loading resources
     */
    public static ClassLoader getCurrentLoader(Object fallbackClass) {
        ClassLoader loader = customClassLoader;
        if (loader == null) {
            loader = Thread.currentThread().getContextClassLoader();
        }
        if (loader == null) {
            loader   = fallbackClass.getClass().getClassLoader();
        }
        return loader;
    }  
}

