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
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a component class or a non-component base class from a dependant library,
 * discovered using introspection.
 *
 * @author gjmurphy
 */
public class IntrospectedClassInfo extends ClassInfo {
    
    BeanInfo beanInfo;
    Map<String, PropertyInfo> propertyInfoMap;
    Map<String, EventInfo> eventInfoMap;
    
    IntrospectedClassInfo(BeanInfo beanInfo) {
        this.beanInfo = beanInfo;
    }
    
    public String getName() {
        return this.beanInfo.getBeanDescriptor().getName();
    }
    
    public String getClassName() {
        return this.beanInfo.getBeanDescriptor().getBeanClass().getSimpleName();
    }
    
    public String getPackageName() {
        return this.beanInfo.getBeanDescriptor().getBeanClass().getPackage().getName();
    }
    
    public BeanInfo getBeanInfo() {
        return beanInfo;
    }
    public ClassInfo getSuperClassInfo() {
        return null;
    }
    
    public boolean isAssignableTo(String qualifiedClassName) {
        try {
            Class superClass = Class.forName(qualifiedClassName);
            if (superClass.isAssignableFrom(this.getBeanInfo().getBeanDescriptor().getBeanClass()))
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Map<String, PropertyInfo> getPropertyInfoMap() {
        return this.propertyInfoMap;
    }
    
    void setPropertyInfoMap(Map<String, PropertyInfo> propertyInfoMap) {
        this.propertyInfoMap = propertyInfoMap;
    }
    
    public Map<String, EventInfo> getEventInfoMap() {
        return this.eventInfoMap;
    }
    
    void setEventInfoMap(Map<String, EventInfo> eventInfoMap) {
        this.eventInfoMap = eventInfoMap;
    }
    
    public PropertyInfo getDefaultPropertyInfo() {
        String defaultPropertyName = null;
        int index = this.beanInfo.getDefaultPropertyIndex();
        if (index >= 0) {
            defaultPropertyName = this.beanInfo.getPropertyDescriptors()[index].getName();
        } else {
            try {
                if (Class.forName("javax.faces.component.ValueHolder").isAssignableFrom(
                        this.getBeanInfo().getBeanDescriptor().getBeanClass()))
                    defaultPropertyName = "value";
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (defaultPropertyName == null)
            return null;
        return this.getPropertyInfoMap().get(defaultPropertyName);
    }
    
    public EventInfo getDefaultEventInfo() {
        String defaultEventName = null;
        int index = this.beanInfo.getDefaultEventIndex();
        if (index >= 0) {
            defaultEventName = this.beanInfo.getEventSetDescriptors()[index].getName();
        } else {
            try {
                Class beanClass = this.getBeanInfo().getBeanDescriptor().getBeanClass();
                if (Class.forName("javax.faces.component.ActionSource").isAssignableFrom(beanClass))
                    defaultEventName = "action";
                else if (Class.forName("javax.faces.component.EditableValueHolder").isAssignableFrom(beanClass))
                    defaultEventName = "valueChange";
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (defaultEventName == null)
            return null;
        return this.getEventInfoMap().get(defaultEventName);
    }
    
    Set<String> methodNameSet;
    
    Set<String> getMethodNameSet() {
        if (this.methodNameSet == null) {
            this.methodNameSet = new HashSet<String>();
            for (MethodDescriptor method : this.getBeanInfo().getMethodDescriptors())
                this.methodNameSet.add(method.getName());
        }
        return this.methodNameSet;
    }
    
    Set<CategoryDescriptor> categoryDescriptors;
    
    
    
}
