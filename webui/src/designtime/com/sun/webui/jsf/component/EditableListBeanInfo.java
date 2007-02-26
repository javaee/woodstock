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

package com.sun.webui.jsf.component;

import com.sun.rave.designtime.CategoryDescriptor;
import com.sun.rave.designtime.Constants;
import com.sun.webui.jsf.component.propertyeditors.LabelLevelsDomain;
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
        DesignUtil.applyPropertyDomain(this, "labelLevel", LabelLevelsDomain.class);
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
