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

package com.sun.faces.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that identifies a public field as being a property category instance.
 * The field must be assignable from {@link com.sun.rave.designtime.CategoryDescriptor}.
 * Property annotations may reference annotated property categories. For example,
 * the property annotation
 *
 * <pre>
 *    &#64;Property(name="myProperty",category="myCategory")
 *    private String myProperty;
 * </pre>
 *
 * contains a reference to the property category whose name is {@code myCategory},
 * which might be defined elsewhere as
 *
 * <pre>
 *    &#64;PropertyCategory("myCategory")
 *    public static final CategoryDescriptor MYCATEGORY = 
 *        new CategoryDescriptor("myCategory", "My Category", false);
 * </pre>
 * 
 * {@code PropertyCategory} must be used to annotate only publicly accessible fields
 * or static methods of type {@link com.sun.rave.designtime.CategoryDescriptor}.
 *
 * @author gjmurphy
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface PropertyCategory {
    
    /**
     * This category's unique name. The name must be unique within the scope of
     * the current compilation unit. This name should generally be exactly
     * equal to the value of {@code CategoryDescriptor.getName()}. If it is not,
     * category descriptors inherited from external libraries will not be comparable
     * to category descriptors defined in the current compilation unit.
     */
    public String name();
    
    /**
     * This category's sort key, used to determine the order in which this 
     * category should appear in a list of categories. If not specified, the sort
     * key defaults to the value of {@link #name}.
     *
     * <p>The collation sequence used for ordering is not specified.
     */
    public String sortKey() default "";
    
}
