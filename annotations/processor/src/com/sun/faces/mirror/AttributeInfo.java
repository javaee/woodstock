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

/**
 * Defines the basic metadata available for the JSP tag attribute that corresponds 
 * to a property. See {@link PropertyInfo#getAttribute}.
 *
 * @author gjmurphy
 */
public interface AttributeInfo {
    
    /**
     * This attribute's name, which is guaranteed to be unique among all attribute
     * names within the scope of the containing component. See 
     * {@link com.sun.faces.annotation.Attribute#name}.
     */
    public String getName();

    /**
     * Returns true if this attribute is required in the JSP. See 
     * {@link com.sun.faces.annotation.Attribute#isRequired}.
     */
    public boolean isRequired();
    
    /**
     * Returns true if this attribute is bindable. See 
     * {@link com.sun.faces.annotation.Attribute#isBindable}.
     */
    public boolean isBindable();
    
    /**
     * If this attribute corresponds to a property of type {@link javax.el.MethodExpression},
     * then this method will return the signature of the method to which the expression
     * should be bound.
     */
    public abstract String getMethodSignature();
    
    /**
     * Returns the description of this attribute, appropriate for use as a description
     * element in the taglib configuration. If no tag description was set, returns the
     * doc comment of this attribute's property.
     */
    public String getDescription();
    
    /**
     * Returns the name of the method used to set the attribute value in the tag
     * handler class.
     */
    public String getWriteMethodName();
    
}
