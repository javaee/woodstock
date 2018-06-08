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

import java.util.HashMap;
import java.util.Map;

/** 
 * Represents an attribute for a property declared on a class in the current 
 * compilation unit.
 *
 * @author gjmurphy
 */
public class DeclaredAttributeInfo implements AttributeInfo {
        
    static final String NAME = "name";
    static final String IS_REQUIRED = "isRequired";
    static final String IS_BINDABLE = "isBindable";
    
    Map<String,Object> annotationValueMap;
    PropertyInfo parentPropertyInfo;
    
    DeclaredAttributeInfo(PropertyInfo parentPropertyInfo) {
        this(null, parentPropertyInfo);
    }
    
    DeclaredAttributeInfo(AttributeInfo attributeInfo) {
        this.annotationValueMap = new HashMap<String,Object>();
        this.annotationValueMap.put(NAME, attributeInfo.getName());
        this.annotationValueMap.put(IS_REQUIRED, attributeInfo.isRequired());
        this.annotationValueMap.put(IS_BINDABLE, attributeInfo.isBindable());
        this.setDescription(attributeInfo.getDescription());
        this.setMethodSignature(attributeInfo.getMethodSignature());
        if (attributeInfo instanceof DeclaredAttributeInfo)
            this.parentPropertyInfo = ((DeclaredAttributeInfo) attributeInfo).parentPropertyInfo;
    }
    
    DeclaredAttributeInfo(Map<String,Object> annotationValueMap, PropertyInfo parentPropertyInfo) {
        this.annotationValueMap = annotationValueMap;
        this.parentPropertyInfo = parentPropertyInfo;
    }

    public String getName() {
        if (this.annotationValueMap == null)
            return this.parentPropertyInfo.getName();
        String name = (String) this.annotationValueMap.get(NAME);
        return name == null ? this.parentPropertyInfo.getName() : name;
    }

    public boolean isRequired() {
        if (this.annotationValueMap == null)
            return false;
        return Boolean.TRUE.equals(this.annotationValueMap.get(IS_REQUIRED)) ? true : false;
    }

    public boolean isBindable() {
        if (this.annotationValueMap == null)
            return true;
        return Boolean.FALSE.equals(this.annotationValueMap.get(IS_BINDABLE)) ? false : true;
    }

    private String methodSignature;
    
    public String getMethodSignature() {
        return this.methodSignature;
    }
    
    void setMethodSignature(String methodSignature) {
        this.methodSignature = methodSignature;
    }
    
    private String description;
    
    public String getDescription() {
        if (description == null && this.parentPropertyInfo != null) {
            return ((DeclaredPropertyInfo) this.parentPropertyInfo).getDocComment();
        }
        return this.description;
    }
    
    void setDescription(String description) {
        this.description = description;
    }

    public String getWriteMethodName() {
        String name = this.getName();
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    
}
