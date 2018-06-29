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

package com.sun.webui.jsf.component;

import com.sun.rave.designtime.CategoryDescriptor;
import com.sun.rave.designtime.Constants;
import java.beans.EventSetDescriptor;
import com.sun.webui.jsf.component.util.DesignUtil;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.design.CategoryDescriptors;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * BeanInfo for the {@link com.sun.webui.jsf.component.EditableList} component.
 */
public class EditableListBeanInfo extends EditableListBeanInfoBase {
    
    public EditableListBeanInfo() {
        DesignUtil.updateInputEventSetDescriptors(this);
    }
    
    public EventSetDescriptor[] getEventSetDescriptors() {
        EventSetDescriptor[] eventSetDescriptors = super.getEventSetDescriptors();
        //change validate to listValidate
        if (eventSetDescriptors != null) {
            for (EventSetDescriptor eventDescriptor : eventSetDescriptors) {
                if (eventDescriptor.getName().equals("validate")) {
                    eventDescriptor.setName("listValidate");
               
                    PropertyDescriptor lvePropertyDescriptor = DesignUtil.getPropertyDescriptor(this, "listValidatorExpression"); //NOI18N
                    eventDescriptor.setValue(Constants.EventSetDescriptor.BINDING_PROPERTY, lvePropertyDescriptor);
                }
            }
        }
        
        //create a fieldValidate event set descriptor
        EventSetDescriptor eventSetDescriptor = null;
        try {
            eventSetDescriptor = new EventSetDescriptor("fieldValidate", //NOI18N
            javax.faces.validator.Validator.class,
            new Method[] {
                javax.faces.validator.Validator.class.getMethod(
                    "validate",  //NOI18N
                    new Class[] {javax.faces.context.FacesContext.class, javax.faces.component.UIComponent.class, java.lang.Object.class, })
            },
            null,
            null);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        PropertyDescriptor eventPropertyDescriptor = DesignUtil.getPropertyDescriptor(this, "fieldValidatorExpression"); //NOI18N
        eventSetDescriptor.setValue(Constants.EventSetDescriptor.BINDING_PROPERTY, eventPropertyDescriptor);
        eventSetDescriptor.setValue(Constants.EventDescriptor.DEFAULT_EVENT_BODY,
            DesignMessageUtil.getMessage(this.getClass(), "validateHandler")); //NOI18N
        eventSetDescriptor.setValue(Constants.EventDescriptor.PARAMETER_NAMES,
            new String[] { "context", "component", "value" }); //NOI18N
        
        //copy eventSetDescriptors into a new revisedEventSetDescriptors
        EventSetDescriptor[] revisedEventSetDescriptors = new EventSetDescriptor[eventSetDescriptors.length + 1];
        for (int i = 0; i < eventSetDescriptors.length; i++) {
            revisedEventSetDescriptors[i] = eventSetDescriptors[i];
        }
        
        //add eventSetDescriptor as the last entry in revisedEventSetDescriptors
        revisedEventSetDescriptors[revisedEventSetDescriptors.length - 1] = eventSetDescriptor;
        return revisedEventSetDescriptors;
    }

    protected CategoryDescriptor[] categoryDescriptors;
    
    protected CategoryDescriptor[] getCategoryDescriptors() {
        // A hack to add the category descriptor for events. Since events are not
        // properties, they cannot be annotated with category information.
        if (categoryDescriptors == null) {
            CategoryDescriptor[] superCategoryDescriptors = super.getCategoryDescriptors();
            categoryDescriptors = new CategoryDescriptor[superCategoryDescriptors.length + 1];
            for (int i = 0, j = 0; i < superCategoryDescriptors.length; i++, j++) {
                categoryDescriptors[j] = superCategoryDescriptors[i];
                if (categoryDescriptors[j] == CategoryDescriptors.DATA)
                    categoryDescriptors[++j] = CategoryDescriptors.EVENTS;
            }
        }
        return categoryDescriptors;
    }
}
