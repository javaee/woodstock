/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2007 Sun Microsystems, Inc. All rights reserved.
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
