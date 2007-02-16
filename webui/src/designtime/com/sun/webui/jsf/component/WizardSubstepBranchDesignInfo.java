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
import com.sun.rave.designtime.Result;
import com.sun.webui.jsf.design.AbstractDesignInfo;

/**
 * DesignInfo for the WizardSubstepBranch component.
 * 
 */
public class WizardSubstepBranchDesignInfo extends AbstractDesignInfo {
    
    /** Creates a new instance of WizardSubstepBranchDesignInfo */
    public WizardSubstepBranchDesignInfo() {
        
        super(WizardSubstepBranch.class);
    }
    
    
    public Result beanCreatedSetup(DesignBean bean) {
        super.beanCreatedSetup(bean);
        
        WizardSubstepBranch wsb = (WizardSubstepBranch) bean.getInstance();
        //check for id, if its null then assign "wizardSubstepBranch1" to it.
        if (wsb.getId() == null)
        wsb.setId("wizardSubstepBranch1");
        return Result.SUCCESS;
    }
    
    public boolean acceptParent(DesignBean parentBean, DesignBean childBean, Class childClass) {
        // can accept wizard or wizardBranchSteps as parent.
        if (parentBean.getInstance().getClass().equals(Wizard.class) ||
                parentBean.getInstance().getClass().equals(WizardBranchSteps.class))
            return true;
        return false;
    }
}
