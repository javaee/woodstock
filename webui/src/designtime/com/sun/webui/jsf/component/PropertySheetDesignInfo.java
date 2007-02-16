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
public class PropertySheetDesignInfo extends AbstractDesignInfo {
    
    public PropertySheetDesignInfo () {
        super(PropertySheet.class);
    }
    
    /**
     * On component creation, pre-populate with a property sheet section that
     * contains one property.
     */
    public Result beanCreatedSetup(DesignBean bean) {
        super.beanCreatedSetup(bean);
        DesignContext context = bean.getDesignContext();
        if (context.canCreateBean(PropertySheetSection.class.getName(), bean, null)) {
            DesignBean propertySectionBean = context.createBean(PropertySheetSection.class.getName(), bean, null);
            String suffix = DesignUtil.getNumericalSuffix(propertySectionBean.getInstanceName());
            propertySectionBean.getProperty("label").setValue(
                propertySectionBean.getBeanInfo().getBeanDescriptor().getDisplayName() + " " + suffix);
            DesignBean propertyBean = context.createBean(Property.class.getName(), propertySectionBean, null);
            suffix = DesignUtil.getNumericalSuffix(propertyBean.getInstanceName());
            propertyBean.getProperty("label").setValue(
                propertyBean.getBeanInfo().getBeanDescriptor().getDisplayName() + " " + suffix);
        }
        return Result.SUCCESS;
    }

    /**
     * A property sheet accepts only PropertySheetSection children.
     */
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        if (childClass.equals(PropertySheetSection.class))
            return true;
        return false;
    }


 protected DesignProperty getDefaultBindingProperty(DesignBean targetBean) {
        return targetBean.getProperty("requiredFields"); //NOI18N
    }

    public void propertyChanged(DesignProperty property, Object oldValue) {
        // If the value of this component's "requiredFields" property was set to equal
        // the value of its "requiredFields" property, this indicates that the user
        // wanted the widget to be preselected at run-time. If this was the case,
        // and "requiredFields" has been changed, updated "requiredFields" accordingly.
        if (property.getPropertyDescriptor().getName().equals("requiredFields")) {
            DesignProperty requiredProperty = property.getDesignBean().getProperty("requiredFields");
            if (oldValue != null && oldValue.equals(requiredProperty.getValue()))
                requiredProperty.setValue(property.getValue());
        }
        super.propertyChanged(property, oldValue);
    }


    
}
