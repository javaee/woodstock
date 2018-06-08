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
 * A base class that defines the basic metadata available for an event set, whether
 * it belongs to a class declared in the current compilation unit, or to a class
 * in a dependant library.
 *
 * @author gjmurphy
 */
public abstract class EventInfo extends FeatureInfo {

    /**
     * Returns the simple name of the method used to add event listeners.
     */
    public abstract String getAddListenerMethodName();

    /**
     * Returns the simple name of the method used to remove event listeners.
     */
    public abstract String getRemoveListenerMethodName();

    /**
     * Returns the simple name of the method used to get event listeners. May be
     * null if there is no such method.
     */
    public abstract String getGetListenersMethodName();
    
    /**
     * Returns the signature of the singleton method defined by this event's listener
     * class or interface.
     */
    public abstract String getListenerMethodSignature();
    
    /**
     * Returns the simple name of the event listener class's singleton method.
     */
    public abstract String getListenerMethodName();
    
    /**
     * Returns an array of the fully qualified names of the singleton listener 
     * method's parameters.
     */
    public abstract String[] getListenerMethodParameterClassNames();
    
    /**
     * Returns the fully qualified name of the event listener class.
     */
    public abstract String getListenerClassName();

    
    private PropertyInfo propertyInfo;

    /**
     * If a property is used to bind to this event, returns the {@code PropertyInfo}
     * instance corresponding to the binding property. Otherwise returns null.
     */
    public PropertyInfo getPropertyInfo() {
        return this.propertyInfo;
    }

    void setPropertyInfo(PropertyInfo propertyInfo) {
        this.propertyInfo = propertyInfo;
    }

}
