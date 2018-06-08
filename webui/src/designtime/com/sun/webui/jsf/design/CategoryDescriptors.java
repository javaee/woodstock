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

package com.sun.webui.jsf.design;

import com.sun.rave.designtime.CategoryDescriptor;
import com.sun.faces.annotation.PropertyCategory;
import java.util.Locale;
import java.util.ResourceBundle;

public class CategoryDescriptors {

    private static final ResourceBundle bundle =
      ResourceBundle.getBundle("com.sun.webui.jsf.design.Bundle", // NOI18N
                               Locale.getDefault(),
                               CategoryDescriptors.class.getClassLoader());

    @PropertyCategory(name="Accessibility", sortKey="h")
    public static final CategoryDescriptor ACCESSIBILITY = new CategoryDescriptor(
        bundle.getString("accessibility"), bundle.getString("accessibilityCatDesc"), false); //NOI18N

    @PropertyCategory(name="Advanced", sortKey="j")
    public static final CategoryDescriptor ADVANCED = new CategoryDescriptor(
        bundle.getString("adv"), bundle.getString("advCatDesc"), false); //NOI18N

    @PropertyCategory(name="Appearance", sortKey="b")
    public static final CategoryDescriptor APPEARANCE = new CategoryDescriptor(
        bundle.getString("appear"), bundle.getString("appearCatDesc"), true); //NOI18N

    @PropertyCategory(name="Behavior", sortKey="g")
    public static final CategoryDescriptor BEHAVIOR = new CategoryDescriptor(
        bundle.getString("behavior"), bundle.getString("behaviorCatDesc"), false); //NOI18N

    @PropertyCategory(name="Data", sortKey="d")
    public static final CategoryDescriptor DATA = new CategoryDescriptor(
        bundle.getString("data"), bundle.getString("dataCatDesc"), true); //NOI18N

    @PropertyCategory(name="Events", sortKey="e")
    public static final CategoryDescriptor EVENTS = new CategoryDescriptor(
        bundle.getString("events"), bundle.getString("eventsCatDesc"), true); //NOI18N

    @PropertyCategory(name="General", sortKey="a")
    public static final CategoryDescriptor GENERAL = new CategoryDescriptor(
        bundle.getString("general"), bundle.getString("generalCatDesc"), true); //NOI18N

    @PropertyCategory(name="Internal", sortKey="k")
    public static final CategoryDescriptor INTERNAL = new CategoryDescriptor(
        bundle.getString("internal"), bundle.getString("internalCatDesc"), false); //NOI18N

    @PropertyCategory(name="Javascript", sortKey="i")
    public static final CategoryDescriptor JAVASCRIPT = new CategoryDescriptor(
        bundle.getString("javascript"), bundle.getString("javascriptCatDesc"), false); //NOI18N
    
    @PropertyCategory(name="Layout", sortKey="c")
    public static final CategoryDescriptor LAYOUT = new CategoryDescriptor(
        bundle.getString("layout"), bundle.getString("layoutCatDesc"), false); //NOI18N

    @PropertyCategory(name="Navigation", sortKey="f")
    public static final CategoryDescriptor NAVIGATION = new CategoryDescriptor(
        bundle.getString("navigation"), bundle.getString("navigationCatDesc"), false); //NOI18N

    private static CategoryDescriptor defaultCategoryDescriptors[] = {
            GENERAL, APPEARANCE, LAYOUT, DATA, EVENTS, NAVIGATION, BEHAVIOR, ACCESSIBILITY,
            JAVASCRIPT, ADVANCED, INTERNAL
        };

}
