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

import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignContext;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.Result;
import com.sun.webui.jsf.component.util.DesignUtil;
import com.sun.webui.jsf.design.AbstractDesignInfo;

/** DesignInfo class for components that extend the {@link
 * com.sun.webui.jsf.component.Property} component.
 *
 * @author gjmurphy
 */
public class PropertySheetSectionDesignInfo extends AbstractDesignInfo {
    
    public PropertySheetSectionDesignInfo() {
        super(PropertySheetSection.class);
    }
    
    public Result beanCreatedSetup(DesignBean bean) {
        super.beanCreatedSetup(bean);
        DesignContext context = bean.getDesignContext();
        DesignProperty textProperty = bean.getProperty("label"); //NOI18N
        String suffix = DesignUtil.getNumericalSuffix(bean.getInstanceName());
        textProperty.setValue(
                bean.getBeanInfo().getBeanDescriptor().getDisplayName() + " " + suffix);
        
        DesignBean propertyBean = context.createBean(Property.class.getName(), bean, null);
        suffix = DesignUtil.getNumericalSuffix(propertyBean.getInstanceName());
        propertyBean.getProperty("label").setValue(
                propertyBean.getBeanInfo().getBeanDescriptor().getDisplayName() + " " + suffix);
        return Result.SUCCESS;
    }
    
    /**
     * A property sheet section must be contained by a property sheet.
     */
    public boolean acceptParent(DesignBean parentBean, DesignBean childBean, Class childClass) {
        if (parentBean.getInstance().getClass().equals(PropertySheet.class))
            return true;
        return false;
    }
    
    /**
     * A property sheet section may contain only properties.
     */
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        if (childClass.equals(Property.class))
            return true;
        return false;
    }
    
}
