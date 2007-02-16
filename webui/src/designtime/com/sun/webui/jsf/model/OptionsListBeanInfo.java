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
package com.sun.webui.jsf.model;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
        
import com.sun.webui.jsf.component.propertyeditors.OptionsPropertyEditor;
import com.sun.webui.jsf.component.propertyeditors.SelectedValuesPropertyEditor;

/**
 * BeanInfo for {@link com.sun.webui.jsf.model.OptionsList}.
 *
 * @author gjmurphy
 */
public abstract class OptionsListBeanInfo extends SimpleBeanInfo {
    private PropertyDescriptor[] propertyDescriptors;
    
    public PropertyDescriptor[] getPropertyDescriptors() {
        if (propertyDescriptors != null)
            return propertyDescriptors;
        try {
            PropertyDescriptor[] additionalPropertyDescriptors = 
                    getAdditionalPropertyDescriptors();
            PropertyDescriptor optionsDesc = new PropertyDescriptor( "options",
                    OptionsList.class, "getOptions", "setOptions");
            optionsDesc.setPropertyEditorClass(OptionsPropertyEditor.class);
            PropertyDescriptor selectedValueDesc = new PropertyDescriptor( "selectedValue",
                    OptionsList.class, "getSelectedValue", "setSelectedValue");
            selectedValueDesc.setPropertyEditorClass(SelectedValuesPropertyEditor.class);
            propertyDescriptors = new PropertyDescriptor[additionalPropertyDescriptors.length + 2];
            int i = 0;
            while (i < additionalPropertyDescriptors.length) {
                propertyDescriptors[i] = additionalPropertyDescriptors[i];
                i++;
            }
            propertyDescriptors[i++] = optionsDesc;
            propertyDescriptors[i++] = selectedValueDesc;
        } catch(IntrospectionException e) {
            propertyDescriptors = new PropertyDescriptor[0];
        }
        return propertyDescriptors;
    }
    
    abstract protected PropertyDescriptor[] getAdditionalPropertyDescriptors()
        throws IntrospectionException;
    
}
