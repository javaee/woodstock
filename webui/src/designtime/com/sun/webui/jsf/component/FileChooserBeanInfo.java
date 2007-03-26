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

import java.beans.BeanDescriptor;

import com.sun.webui.jsf.component.propertyeditors.SortFieldDomain;
import com.sun.webui.jsf.component.util.DesignUtil;
import com.sun.webui.jsf.design.CategoryDescriptors;

import com.sun.rave.designtime.Constants;
import com.sun.rave.designtime.CategoryDescriptor;

/**
 * BeanInfo for the {@link com.sun.webui.jsf.component.FileChooser} component.
 */
public class FileChooserBeanInfo extends FileChooserBeanInfoBase {
    
    protected CategoryDescriptor[] categoryDescriptors;
    
    /**
     * Default constructor.
     */
    public FileChooserBeanInfo() {
        DesignUtil.applyPropertyDomain(this, "sortField", SortFieldDomain.class);
        // Add default body and parameter names to the event descriptors for the
        // valueChange event and the pseudo-event validate.
        DesignUtil.updateInputEventSetDescriptors(this);
    }              
    
    /**
     * Return the <code>BeanDescriptor</code> for this bean.
     */     
    public BeanDescriptor getBeanDescriptor() {
        BeanDescriptor beanDescriptor = super.getBeanDescriptor();
        
        // Do not allow the component to be resized.
        beanDescriptor.setValue(Constants.BeanDescriptor.RESIZE_CONSTRAINTS,
                new Integer(Constants.ResizeConstraints.NONE));        
        return beanDescriptor;
    }  
    
    protected CategoryDescriptor[] getCategoryDescriptors() {   
        // A hack to add the category descriptor for events and to ensure that
        // Events always occurs after Data. Since events are not properties,
        // they cannot be annotated with category information.
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
