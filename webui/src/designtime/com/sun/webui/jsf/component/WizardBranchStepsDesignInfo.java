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
import com.sun.webui.jsf.component.WizardBranch;
import com.sun.webui.jsf.component.WizardBranchSteps;

/**
 * DesignInfo for the WizardBranchSteps component.
 * 
 */
public class WizardBranchStepsDesignInfo extends AbstractDesignInfo {
    
    /** Creates a new instance of WizardBranchStepsDesignInfo */
    public WizardBranchStepsDesignInfo() {
        
        super(WizardBranchSteps.class);
        
    }
        
    public Result beanCreatedSetup(DesignBean bean) {
        super.beanCreatedSetup(bean);
        WizardBranchSteps wbs = (WizardBranchSteps) bean.getInstance();
        //check for the id, if its null then assign "wizardBranchSteps1" to it.
        if (wbs.getId() == null)
        wbs.setId("wizardBranchSteps1");
        
        return Result.SUCCESS;
    }
    
    public boolean acceptParent(DesignBean parentBean, DesignBean childBean, Class childClass) {
        // can only accept wizardBranch as parent.
        if (parentBean.getInstance().getClass().equals(WizardBranch.class))
            return true;
        return false;
    }
}
