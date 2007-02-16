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
import com.sun.rave.designtime.faces.FacesDesignContext;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.component.util.DesignUtil;
import com.sun.webui.jsf.component.CommonTasksSection;
import com.sun.webui.jsf.component.CommonTasksGroup;
import com.sun.webui.jsf.design.AbstractDesignInfo;

/**
 *DesignInfo class for components that extend the {@link
 * com.sun.webui.jsf.component.CommonTask} component.
 */
public class CommonTaskDesignInfo extends AbstractDesignInfo {
    
    /** Creates a new instance of CommonTaskDesignInfo */
    public CommonTaskDesignInfo() {
              super(CommonTask.class);
    }
    
    
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {        
        return true;
    }
    
    public boolean acceptParent(DesignBean parentBean, DesignBean childBean, Class childClass) {
        Class parentClass = parentBean.getInstance().getClass();
        if(CommonTasksSection.class.equals(parentClass) || CommonTasksGroup.class.equals(parentClass)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     *
     * @param bean <code>DesignBean</code> for the newly created instance
     */
    public Result beanCreatedSetup(DesignBean bean) {
        super.beanCreatedSetup(bean);
        
         FacesDesignContext context = (FacesDesignContext) bean.getDesignContext();
         DesignProperty text = bean.getProperty("text"); // NOI18N
         text.setValue(DesignMessageUtil.getMessage(CommonTaskDesignInfo.class, "CommonTask.text")); // NOI18N
         
          return Result.SUCCESS;
    }

    
}
