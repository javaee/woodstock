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

import com.sun.rave.designtime.CategoryDescriptor;
import com.sun.rave.designtime.Constants;
import com.sun.rave.designtime.markup.AttributeDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Represents a property of a class from a dependant library, discovered using
 * introspection.
 *
 * @author gjmurphy
 */
public class IntrospectedPropertyInfo extends PropertyInfo {
    
    PropertyDescriptor propertyDescriptor;
    
    IntrospectedPropertyInfo(PropertyDescriptor propertyDescriptor) {
        this.propertyDescriptor = propertyDescriptor;
    }
    
    public PropertyDescriptor getPropertyDescriptor() {
        return this.propertyDescriptor;
    }

    public String getName() {
        return this.propertyDescriptor.getName();
    }
    
    public String getInstanceName() {
        String name = this.getName();
        if (PropertyInfo.JAVA_KEYWORD_PATTERN.matcher(name).matches())
            return "_" + name;
        return name;
    }

    public String getType() {
        return this.propertyDescriptor.getPropertyType().getName();
    }

    public String getWriteMethodName() {
        Method method = this.propertyDescriptor.getWriteMethod();
        if (method == null)
            return null;
        return method.getName();
    }

    public String getReadMethodName() {
        Method method = this.propertyDescriptor.getReadMethod();
        if (method == null)
            return null;
        return method.getName();
    }

    public String getShortDescription() {
        return this.propertyDescriptor.getShortDescription();
    }

    public String getDisplayName() {
        return this.propertyDescriptor.getDisplayName();
    }
    
    public boolean isHidden() {
        return this.propertyDescriptor.isHidden();
    }

    public String getEditorClassName() {
        Class editorClass = this.propertyDescriptor.getPropertyEditorClass();
        if (editorClass == null)
            return null;
        return editorClass.getName();
    }
    
    private CategoryInfo categoryInfo;
    
    public CategoryInfo getCategoryInfo() {
        return this.categoryInfo;
    }
    
    void setCategoryInfo(CategoryInfo categoryInfo) {
        this.categoryInfo = categoryInfo;
    }
    
    String getCategoryReferenceName() {
        if (this.propertyDescriptor.getValue(Constants.PropertyDescriptor.CATEGORY) != null)
            return ((CategoryDescriptor) this.propertyDescriptor.getValue(Constants.PropertyDescriptor.CATEGORY)).getName();
        return null;
    }
    
    public boolean equals(Object obj) {
        if (!(obj instanceof PropertyInfo))
            return false;
        PropertyInfo that = (PropertyInfo) obj;
        if (!this.getName().equals(that.getName()))
            return false;
        String thisReadName = this.getReadMethodName();
        String thatReadName = that.getReadMethodName();
        if (thisReadName != null && (thatReadName == null || !thatReadName.equals(thisReadName)))
            return false;
        if (thatReadName == null && thatReadName != null)
            return false;
        String thisWriteName = this.getReadMethodName();
        String thatWriteName = that.getReadMethodName();
        if (thisWriteName != null && (thatWriteName == null || !thatWriteName.equals(thisWriteName)))
            return false;
        if (thatWriteName == null && thatWriteName != null)
            return false;
        return true;
    }

    public AttributeInfo getAttributeInfo() {
        AttributeDescriptor attributeDescriptor =
                (AttributeDescriptor) this.propertyDescriptor.getValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR);
        if (attributeDescriptor == null)
            return null;
        return new IntrospectedAttributeInfo(attributeDescriptor);
    }
    
}
