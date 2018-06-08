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
    public static void setCustomClassLoader(ClassLoader customClassLoader) {
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
            loader = fallbackClass.getClass().getClassLoader();
        }
        return loader;
    }
}

