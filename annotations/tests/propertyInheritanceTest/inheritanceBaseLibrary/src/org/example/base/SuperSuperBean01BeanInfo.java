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
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class SuperSuperBean01BeanInfo extends SimpleBeanInfo {
    
    public BeanDescriptor getBeanDescriptor() {
        BeanDescriptor descriptor = new BeanDescriptor(SuperSuperBean01.class);
        descriptor.setDisplayName("Super Super Bean 01");
        return descriptor;
    }
    
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor descriptor =
                    new PropertyDescriptor("one", SuperSuperBean01.class, "getOne", "setOne");
            descriptor.setDisplayName("The First");
            AttributeDescriptor attributeDescriptor = new AttributeDescriptor("one",false,null,true);
            descriptor.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR, attributeDescriptor);
            descriptor.setValue(Constants.PropertyDescriptor.CATEGORY, CategoryDescriptors.CATEGORY);
            return new PropertyDescriptor[] {descriptor};
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
