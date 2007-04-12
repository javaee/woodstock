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
import com.sun.webui.jsf.component.util.DesignUtil;
import java.beans.BeanDescriptor;
import com.sun.rave.designtime.Constants;
import com.sun.webui.jsf.component.propertyeditors.LabelLevelsDomain;
import com.sun.webui.jsf.design.CategoryDescriptors;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * BeanInfo for the {@link com.sun.webui.jsf.component.DropDown} component.
 */
public class DropDownBeanInfo extends DropDownBeanInfoBase {
    
    public DropDownBeanInfo() {
        DesignUtil.applyPropertyDomain(this, "labelLevel", LabelLevelsDomain.class);
        BeanDescriptor beanDescriptor = super.getBeanDescriptor();
        DesignUtil.hideProperties(this, new String[]{"submitForm"});
        DesignUtil.updateInputEventSetDescriptors(this);
        beanDescriptor.setValue(Constants.BeanDescriptor.INLINE_EDITABLE_PROPERTIES,
            new String[] { "label://label" }); // NOI18N
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
    
    public BeanDescriptor getBeanDescriptor() {
        BeanDescriptor beanDescriptor = super.getBeanDescriptor();
        // Do not allow component to be resized.
        beanDescriptor.setValue(Constants.BeanDescriptor.RESIZE_CONSTRAINTS,
                new Integer(Constants.ResizeConstraints.NONE));
        return beanDescriptor;
    }
    
}
