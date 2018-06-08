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

import java.util.Map;
import java.util.Set;

/**
 * An interface that defines the basic metadata available for a class with one
 * or more properties. This may be a component class or a non-component base class,
 * either in the current compilation unit, or in an external library.
 *
 * @author gjmurphy
 */
public abstract class ClassInfo {
    
    /**
     * Returns the name of this class.
     */
    public abstract String getClassName();
    
    /**
     * Returns the name of this class' package.
     */
    public abstract String getPackageName();
    
    /**
     * Returns the info for this class's super class. If this class extends a 
     * class which provides no properties, then this method should return null.
     *
     * @return Value of property superClassInfo.
     */
    public abstract ClassInfo getSuperClassInfo();

    /**
     * Returns a map in which keys are property names (see {@link PropertyInfo#getName}), 
     * and values are instance of {@link PropertyInfo}. The map contains all
     * properties explicitly declared in this class (which may or may not override
     * properties in the super class), as well as all properties declared in any
     * interface which this class implements.
     *
     * @return Value of property propertyInfoMap.
     */
    public abstract Map<String,PropertyInfo> getPropertyInfoMap();

    /**
     * Returns a map in which keys are event names (see {@link EventInfo#getName}), 
     * and values are instance of {@link EventInfo}. The map contains all
     * events explicitly declared in this class, (which may or may not override
     * events in the super class), as well as all events declared in any interface
     * which this class implements.
     *
     * @return Value of property eventInfoMap.
     */
    public abstract Map<String,EventInfo> getEventInfoMap();
    
    /**
     * Returns the default property for this class, or null if the class has no
     * default property. If a default property was specified explicitly as part of a
     * class's annotations, or implicitly via introspection, it is returned.
     * Otherwise, if this class is a Faces component class and implements 
     * {@link javax.faces.component.ValueHolder}, the {@code value} property is
     * returned. Otherwise null is returned.
     */
    public abstract PropertyInfo getDefaultPropertyInfo();
    
    /**
     * Returns the default event for this class, or null if the class has no
     * default event. If a default event was specified explicitly as part of a
     * class's annotations, or implicitly via introspection, it is returned.
     * Otherwise, if this class is a Faces component class and implements 
     * {@link javax.faces.component.ValueHolder}, the {@code valueChange} event
     * is returned if it is defined; if it implements (@link javax.faces.component.ActionSource},
     * the {@code action} event is returned if it is defined.
     */
    public abstract EventInfo getDefaultEventInfo();
    
    /**
     * Returns the fully qualified name of this class.
     */
    public String getQualifiedName() {
        return getPackageName() + "." + getClassName();
    }
    
    /**
     * Returns true if the fully qualified name specified belongs to a class or interface
     * that is a superclass or superinterface of this class.
     */
    public abstract boolean isAssignableTo(String qualifiedClassName);
    
    /**
     * Returns a unique, generated key for the property specified, suitable for
     * use as a key in a properties resource bundle file, if the property corresponds
     * to a localizable annotation element. For example, the property {@link DeclaredComponentInfo#getDisplayName})
     * corresponds to the localizable annotation element {@link com.sun.faces.Component#displayName}
     * so a unique key for it may be generated by calling
     * <pre>
     *    String key = declaredComponentInfo.getKey("displayName");
     * </pre>
     * If the specified property does not correspond to a localizable annotation, 
     * returns null. If the specified property does not exist, throws {@link
     * java.lang.NoSuchMethodException}. Only properties of type {@link java.lang.String}
     * are supported.
     */
    public String getKey(String propertyName) throws NoSuchMethodException {
        String methodName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        this.getClass().getMethod(methodName);
        String baseName = this.getClassName();
        return baseName + "_" + propertyName;
    }
    
    /**
     * Returns a set of the names of all public methods accessible through this class,
     * whether declared by it, or inherited.
     */
    abstract Set<String> getMethodNameSet();
    
}
