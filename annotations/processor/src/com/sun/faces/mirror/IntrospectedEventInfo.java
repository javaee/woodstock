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

import java.beans.EventSetDescriptor;
import java.lang.reflect.Method;

/**
 *
 * @author gjmurphy
 */
public class IntrospectedEventInfo extends EventInfo {
    
    EventSetDescriptor eventDescriptor;
    
    IntrospectedEventInfo(EventSetDescriptor eventDescriptor) {
        this.eventDescriptor = eventDescriptor;
    }

    public String getName() {
        return this.eventDescriptor.getName();
    }

    public String getDisplayName() {
        return this.eventDescriptor.getDisplayName();
    }

    public String getShortDescription() {
        return this.eventDescriptor.getShortDescription();
    }

    public String getAddListenerMethodName() {
        if (this.eventDescriptor.getAddListenerMethod() != null)
            return this.eventDescriptor.getAddListenerMethod().getName();
        return null;
    }

    public String getRemoveListenerMethodName() {
        if (this.eventDescriptor.getRemoveListenerMethod() != null)
            return this.eventDescriptor.getRemoveListenerMethod().getName();
        return null;
    }

    public String getGetListenersMethodName() {
        if (this.eventDescriptor.getGetListenerMethod() != null)
            return this.eventDescriptor.getGetListenerMethod().getName();
        return null;
    }
    
    String listenerMethodSignature;

    public String getListenerMethodSignature() {
        if (this.listenerMethodSignature == null) {
            StringBuffer buffer = new StringBuffer();
            Method listenerMethod = this.eventDescriptor.getListenerMethods()[0];
            buffer.append(listenerMethod.getReturnType().toString());
            buffer.append(" ");
            buffer.append(listenerMethod.getName());
            buffer.append("(");
            for (Class paramClass : listenerMethod.getParameterTypes()) {
                buffer.append(paramClass.getName());
                buffer.append(",");
            }
            buffer.setLength(buffer.length() - 1);
            buffer.append(")");
            this.listenerMethodSignature = buffer.toString();
        }
        return this.listenerMethodSignature;
    }

    public String getListenerClassName() {
        return this.eventDescriptor.getListenerType().getName();
    }
    
    String[] listenerMethodParameterClassNames;
    
    public String[] getListenerMethodParameterClassNames() {
        if (listenerMethodParameterClassNames == null) {
            Class[] paramClasses = 
                    this.eventDescriptor.getListenerMethods()[0].getParameterTypes();
            listenerMethodParameterClassNames = new String[paramClasses.length];
            for (int i = 0; i < paramClasses.length; i++)
                listenerMethodParameterClassNames[i] = paramClasses[i].getName();
        }
        return listenerMethodParameterClassNames;
    }

    public String getListenerMethodName() {
        return this.eventDescriptor.getListenerMethods()[0].getName();
    }

    public boolean isHidden() {
        return this.eventDescriptor.isHidden();
    }

}
