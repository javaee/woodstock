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
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import com.sun.rave.designtime.DesignContext;

/**
 * DesignInfo for the {@link com.sun.webui.jsf.component.Bubble} component.
 * @author as199886
 */
public class BubbleDesignInfo extends AbstractDesignInfo {

    public BubbleDesignInfo() {
        super(Bubble.class);
    }

    public Result beanCreatedSetup(DesignBean bean) {
        super.beanCreatedSetup(bean);
        
        DesignContext context = bean.getDesignContext();
        // Added one layout panel so that added component can be aligned inside the bubble container.    
        if (context.canCreateBean(PanelLayout.class.getName(), bean, null)) {
            DesignBean panelBean = context.createBean(PanelLayout.class.getName(), bean, null);
            panelBean.getDesignInfo().beanCreatedSetup(panelBean);
            panelBean.getProperty("panelLayout").setValue(PanelLayout.GRID_LAYOUT);
        }
        return Result.SUCCESS;
    }    
}
