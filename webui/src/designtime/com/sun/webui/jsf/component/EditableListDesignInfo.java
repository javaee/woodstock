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
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.faces.FacesDesignContext;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.rave.designtime.Result;
import com.sun.webui.jsf.design.AbstractDesignInfo;

/**
 * DesignInfo for the {@link com.sun.webui.jsf.component.EditableList} component.
 *
 * @author gjmurphy
 */
public class EditableListDesignInfo extends EditableValueHolderDesignInfo {
    
    public EditableListDesignInfo() {
        super(EditableList.class);
    }

    protected DesignProperty getDefaultBindingProperty(DesignBean bean) {
        return bean.getProperty("list"); //NOI18N
    }

    public Result beanCreatedSetup(DesignBean bean) {
        super.beanCreatedSetup(bean);
        
        FacesDesignContext context = (FacesDesignContext) bean.getDesignContext();
        
        DesignProperty fieldLabel = bean.getProperty("fieldLabel"); // NOI18N
        fieldLabel.setValue(DesignMessageUtil.getMessage(EditableListDesignInfo.class, "EditableList.fieldLabel")); // NOI18N
        
        DesignProperty listLabel = bean.getProperty("listLabel"); // NOI18N
        listLabel.setValue(DesignMessageUtil.getMessage(EditableListDesignInfo.class, "EditableList.listLabel")); //NOI18N
        
        return Result.SUCCESS;
    }
    
    /**
     * EditableList component doesn't need to support the "autosubmit" behavior,
     * so disabling the default behavior.
     *
     * @param bean <code>DesignBean</code> for the newly created instance 
     *
     */
    
     public boolean supportsAutoSubmit(DesignBean bean) {
         return false;
    }
}
