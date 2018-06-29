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

import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.declaration.Declaration;
import com.sun.mirror.type.InterfaceType;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a component class or a non-component base class declared in the current
 * compilation unit. This class offers several different "views" of its properties,
 * which can be useful during source code generation. In addition to providing a
 * map of all declared properties, it provides a map which contains all inherited
 * properties.
 *
 *
 * @author gjmurphy
 */
public class DeclaredClassInfo extends DeclaredTypeInfo {
    
    DeclaredClassInfo(ClassDeclaration decl) {
        super(decl);
    }
    
    /**
     * Returns this class's default property. If no default property was declared
     * explicitly via a {@link com.sun.faces.annotation.Property} annotation, and
     * this type implements or extends {@link javax.faces.component.ValueHolder},
     * then the {@code value} property is made default.
     */
    public PropertyInfo getDefaultPropertyInfo() {
        PropertyInfo defaultPropertyInfo = super.getDefaultPropertyInfo();
        if (defaultPropertyInfo == null) {
            if (this.getSuperClassInfo() != null) {
                defaultPropertyInfo = this.getSuperClassInfo().getDefaultPropertyInfo();
            }
            if (defaultPropertyInfo == null) {
                for (InterfaceType interfaceType : this.decl.getSuperinterfaces()) {
                    if (interfaceType.getDeclaration().getQualifiedName().equals("javax.faces.component.ValueHolder")) {
                        defaultPropertyInfo = this.getPropertyInfoMap().get("value");
                        if (defaultPropertyInfo == null)
                            defaultPropertyInfo = this.getInheritedPropertyInfoMap().get("value");
                    }
                }
            }
        }
        return defaultPropertyInfo;
    }
    
    /**
     * Returns this class's default Event. If no default Event was declared
     * explicitly via a {@link com.sun.faces.annotation.Event} annotation, and
     * this type implements or extends {@link javax.faces.component.ValueHolder},
     * then the {@code value} Event is made default.
     */
    public EventInfo getDefaultEventInfo() {
        EventInfo defaultEventInfo = super.getDefaultEventInfo();
        if (defaultEventInfo == null) {
            if (this.getSuperClassInfo() != null) {
                defaultEventInfo = this.getSuperClassInfo().getDefaultEventInfo();
            }
            if (defaultEventInfo == null) {
                for (InterfaceType interfaceType : this.decl.getSuperinterfaces()) {
                    if (interfaceType.getDeclaration().getQualifiedName().equals("javax.faces.component.EditableValueHolder")) {
                        defaultEventInfo = this.getEventInfoMap().get("valueChange");
                        if (defaultEventInfo == null)
                            defaultEventInfo = this.getInheritedEventInfoMap().get("valueChange");
                    }
                    if (interfaceType.getDeclaration().getQualifiedName().equals("javax.faces.component.ActionSource")) {
                        defaultEventInfo = this.getEventInfoMap().get("action");
                        if (defaultEventInfo == null)
                            defaultEventInfo = this.getInheritedEventInfoMap().get("action");
                    }
                }
            }
        }
        return defaultEventInfo;
    }
    
    private Map<String,PropertyInfo> inheritedPropertyInfoMap;
    
    /**
     * Returns a map of all properties inherited by this class, but not overriden
     * by properties in this class.
     */
    public Map<String,PropertyInfo> getInheritedPropertyInfoMap() {
        if (this.inheritedPropertyInfoMap == null) {
            ClassInfo classInfo = this.getSuperClassInfo();
            this.inheritedPropertyInfoMap = new HashMap<String,PropertyInfo>();
            while (classInfo != null) {
                for (PropertyInfo propertyInfo : classInfo.getPropertyInfoMap().values()) {
                    String name = propertyInfo.getName();
                    if (!this.propertyInfoMap.containsKey(name) && !this.inheritedPropertyInfoMap.containsKey(name))
                        this.inheritedPropertyInfoMap.put(name, propertyInfo);
                }
                classInfo = classInfo.getSuperClassInfo();
            }
        }
        return this.inheritedPropertyInfoMap;
    }
    
    private Map<String,EventInfo> inheritedEventInfoMap;
    
    /**
     * Returns a map of all events inherited by this class, but not overriden
     * by properties in this class.
     */
    public Map<String,EventInfo> getInheritedEventInfoMap() {
        if (this.inheritedEventInfoMap == null) {
            ClassInfo classInfo = this.getSuperClassInfo();
            this.inheritedEventInfoMap = new HashMap<String,EventInfo>();
            while (classInfo != null) {
                for (EventInfo eventInfo : classInfo.getEventInfoMap().values()) {
                    String name = eventInfo.getName();
                    if (!this.eventInfoMap.containsKey(name) && !this.inheritedEventInfoMap.containsKey(name))
                        this.inheritedEventInfoMap.put(name, eventInfo);
                }
                classInfo = classInfo.getSuperClassInfo();
            }
        }
        return this.inheritedEventInfoMap;
    }
    
    public boolean equals(Object obj) {
        if (!(obj instanceof DeclaredClassInfo))
            return false;
        DeclaredClassInfo that = (DeclaredClassInfo) obj;
        if (!this.getClassName().equals(that.getClassName()))
            return false;
        if (this.getClassName() == null && that.getClassName() != null)
            return false;
        if (!this.getClassName().equals(that.getClassName()))
            return false;
        return true;
    }
    
}
