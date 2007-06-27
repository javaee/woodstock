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
import com.sun.rave.designtime.Result;
import com.sun.webui.jsf.design.AbstractDesignInfo;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import javax.faces.component.UIComponent;

/**
 *
 * @author vm157347
 */
public class LegendDesignInfo  extends AbstractDesignInfo {
    /**
     * Construct a new <code>LegendDesignInfo</code> instance.
     */
    public LegendDesignInfo() {
        super(LegendDesignInfo.class);
    }
    
    public Result beanCreatedSetup(DesignBean bean) {
        super.beanCreatedSetup(bean);
        DesignProperty textProperty = bean.getProperty("text"); //NOI18N
        textProperty.setValue(DesignMessageUtil.getMessage(LegendDesignInfo.class, "Legend.text"));

        return Result.SUCCESS;        
    }    
    
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        return false;
    }    

}
