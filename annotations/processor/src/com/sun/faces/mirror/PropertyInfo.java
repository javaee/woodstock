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

package com.sun.faces.mirror;

import java.util.regex.Pattern;

/**
 * A base class that defines the basic metadata available for a property, whether
 * it belongs to a class declared in the current compilation unit, or to a class
 * in a dependant library.
 *
 * @author gjmurphy
 */
public abstract class PropertyInfo extends FeatureInfo {
    
    /**
     * Returns the name of this property as a Java instance name. This will usually
     * be the value of {@link #getName), unless that value equals a Java keyword
     * or reserverd word, in which case a "_" character will be prepended to the name.
     */
    public abstract String getInstanceName();

    /**
     * Returns the fully qualified type name of this property.
     */
    public abstract String getType();

    /**
     * Returns the simple name of this property's read method. May be null if 
     * property is write-only or if the property is inherited and the read method 
     * is defined in the super class.
     */
    public abstract String getReadMethodName();

    /**
     * Returns the simple name of this property's write method. May be null if 
     * property is read-only or if the property is inherited and the write method 
     * is defined in the super class.
     */
    public abstract String getWriteMethodName();
    
    /**
     * Returns the fully qualified type name of a property editor class to be used
     * for editing this property. If no editor was assigned to this property, 
     * returns null.
     */
    public abstract String getEditorClassName();
    
    /**
     * Returns the category info for this property, or null if this property is
     * uncategorized.
     */
    public abstract CategoryInfo getCategoryInfo();
    
    
    abstract String getCategoryReferenceName();
    
    /**
     * Returns the attribute info for this property, or null if this property does
     * not correspond to an attribute.
     */
    public abstract AttributeInfo getAttributeInfo();

}
