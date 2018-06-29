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

package org.example.base;

import com.sun.rave.designtime.Constants;
import com.sun.rave.designtime.markup.AttributeDescriptor;
import java.beans.BeanDescriptor;
import java.beans.EventSetDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.lang.reflect.Method;

public class IntrospectedSuperBeanBeanInfo extends SimpleBeanInfo {
    
    public BeanDescriptor getBeanDescriptor() {
        BeanDescriptor descriptor = new BeanDescriptor(IntrospectedSuperBean.class);
        descriptor.setDisplayName("Super Bean");
        return descriptor;
    }
    
    public PropertyDescriptor[] getPropertyDescriptors() {
        
        try {
            PropertyDescriptor prop = new PropertyDescriptor("introspectedListener2Expression", IntrospectedSuperBean.class, "getIntrospectedListener2Expression", "setIntrospectedListener2Expression");
            AttributeDescriptor attrib = new AttributeDescriptor("introspectedListener2Expression", false, null, true);
            prop.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR, attrib);
            return new PropertyDescriptor[]{prop};
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public EventSetDescriptor[] getEventSetDescriptors() {
        try {
            EventSetDescriptor eventSetDescriptor01 = new EventSetDescriptor("introspectedAction01",
                    IntrospectedActionListener01.class,
                    new Method[] {IntrospectedActionListener01.class.getMethod(
                            "introspectedActionHappened",
                            new Class[] {IntrospectedActionEvent01.class, })},
                    IntrospectedSuperBean.class.getMethod("addIntrospectedActionListener01", new Class[] {IntrospectedActionListener01.class}),
                    IntrospectedSuperBean.class.getMethod("removeIntrospectedActionListener01", new Class[] {IntrospectedActionListener01.class}));
            EventSetDescriptor eventSetDescriptor02 = new EventSetDescriptor("introspectedAction02",
                    IntrospectedActionListener02.class,
                    new Method[] {IntrospectedActionListener02.class.getMethod(
                            "introspectedActionHappened",
                            new Class[] {IntrospectedActionEvent02.class, })},
                    IntrospectedSuperBean.class.getMethod("addIntrospectedActionListener02", new Class[] {IntrospectedActionListener02.class}),
                    IntrospectedSuperBean.class.getMethod("removeIntrospectedActionListener02", new Class[] {IntrospectedActionListener02.class}));
            eventSetDescriptor02.setValue(Constants.EventSetDescriptor.BINDING_PROPERTY, this.getPropertyDescriptors()[0]);
            return new EventSetDescriptor[] {eventSetDescriptor01, eventSetDescriptor02};
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    
}
