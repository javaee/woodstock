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

import com.sun.webui.jsf.design.AbstractDesignInfo;
import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.faces.FacesDesignContext;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.Result;
import com.sun.webui.jsf.component.util.DesignUtil;


/**
 *
 * DesignInfo class for versionpage component.
 */
public class VersionPageDesignInfo extends AbstractDesignInfo {
    
    /** Creates a new instance of VersionPageDesignInfo */
    public VersionPageDesignInfo() {
        super(VersionPage.class);
    }
    
   
    /**
     * Allow versionpage anywhere.
     */
    public boolean acceptParent(DesignBean parentBean, DesignBean childBean, Class childClass) {
        
        return true;
    }
    
    
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        return false;
    }
    
    public Result beanCreatedSetup(DesignBean bean) {
                
        DesignProperty styleProperty = bean.getProperty("style");
        String styleValue = (String) styleProperty.getValue();
        
        
        if (styleValue == null || styleValue.length() == 0) {
            styleValue = "width: 100%; height: 100%;";
        } else {
            //append default width
            styleValue = DesignUtil.parseStyle(styleValue,"width","width: 100%;");
            
            //append default height
            styleValue = DesignUtil.parseStyle(styleValue,"height","height: 100%;");
        }
        
        styleProperty.setValue(styleValue);
        return Result.SUCCESS;
    }
}
